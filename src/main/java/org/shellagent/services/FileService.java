package org.shellagent.services;

import ch.ethz.ssh2.SCPClient;
import org.shellagent.exception.MyException;
import org.shellagent.utils.Constant;
import org.shellagent.utils.RemoteShellRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.LinkedList;

@Service
public class FileService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${local.path.shell}")
    String BASE_PATH;

    /**
     * 上传脚本至指定服务器的home目录下.
     * @param shellRunner 建立了与远程桌面的连接
     * @param scriptName 要上传的脚本名称
     * @param localPath 待上传脚本在本地的位置（jar内路径）
     * @return 脚本是否上传成功（可能没有内存空间无法写入）
     * */
    public boolean uploadScript (RemoteShellRunner shellRunner, String scriptName, String localPath) throws MyException {
        //拼接完整的脚本目录
        String shell = localPath==null?BASE_PATH +"/"+ scriptName:BASE_PATH +"/"+ localPath +"/"+ scriptName;
        logger.debug("上传shell脚本: "+ shell);
        ClassPathResource resource = new ClassPathResource(shell);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (resource == null || resource.getInputStream() == null)
                throw new MyException(Constant.ResultCode.INTERNAL_ERROR,"Script cannot be found:"+shell);
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String sbReadLine;
            while ((sbReadLine=bufferedReader.readLine())!=null)
                stringBuilder.append(sbReadLine+" \n");
            File scriptFile = new File(shell);
            if (!scriptFile.exists()) {
                new File(scriptFile.getParent()).mkdirs();
                scriptFile.createNewFile();
            }
            FileOutputStream writer = new FileOutputStream(scriptFile,false);
            writer.write(stringBuilder.toString().getBytes("utf-8"));
            writer.close();
        } catch (IOException e){
            throw new MyException(Constant.ResultCode.INTERNAL_ERROR,"生成脚本异常："+e.getMessage());
        }
        uploadFile(shellRunner, shell, "");
//        logger.debug("脚本上传完成");
        return true;
    }

    /**
     * 上传多个文件至目标主机.
     * 不包括文件夹
     * @param shellRunner 远程连接
     * @param localPath 文件在本地的存放路径
     * @param remotePath 要上传的远程路径，空字符串表示上传到默认$HOME目录
     * @param suffix 上传文件后缀，空字符串表示所有文件
     * @return 上传的文件列表
     * */
    public String[] uploadFiles (RemoteShellRunner shellRunner, String localPath, String remotePath, String suffix) throws MyException {
        String[] uploadNameList;
        try {
            File filePath = new File(localPath);
            if (filePath.canRead() && filePath.isDirectory()) {
                File[] fileList = filePath.listFiles();
                LinkedList<String> targetFiles = new LinkedList<>();
                for (int i = 0; i < fileList.length; i++) {
                    if (fileList[i].isFile() && fileList[i].toString().endsWith(suffix)) {
                        targetFiles.add(fileList[i].getPath());
                    }
                }
                if (targetFiles.size() == 0) {
                    throw new MyException(Constant.ResultCode.NOT_FOUND, "本地目录下未发现可用的文件:"+localPath);
                }
                uploadNameList = new String[targetFiles.size()];
                targetFiles.toArray(uploadNameList);
            } else {
                throw new MyException(Constant.ResultCode.INTERNAL_ERROR, "异常:不可操作的本地目录:"+localPath);
            }
            SCPClient scpClient = shellRunner.getConn().createSCPClient();
            scpClient.put(uploadNameList,remotePath);
            logger.info("文件上传完成");
        } catch (IOException e) {
            throw new MyException(Constant.ResultCode.SHELL_ERROR, "无法创建scp连接(可能为空间满):"+e.getMessage());
        }
        // 返回文件列表包含路径，要去掉
        if (uploadNameList != null)
            for (int i = 0; i < uploadNameList.length; i++)
                uploadNameList[i] = uploadNameList[i].substring(uploadNameList[i].lastIndexOf('/') + 1);
        return uploadNameList;
    }

    /**
     * 上传文件至目标主机.
     * 不包括文件夹
     * @param shellRunner 远程连接
     * @param localFileWithPath 文件在本地的位置，包含文件自身
     * @param remotePath 要上传的远程路径，空字符串表示上传到默认$HOME目录
     * */
    public void uploadFile (RemoteShellRunner shellRunner, String localFileWithPath, String remotePath) throws MyException {
        try {
            File fileName = new File(localFileWithPath);
            if (!fileName.exists() || !fileName.isFile() || !fileName.canRead()) {
                throw new FileNotFoundException("找不到指定文件: "+fileName);
            }
            SCPClient scpClient = shellRunner.getConn().createSCPClient();
            scpClient.put(fileName.getAbsolutePath(),remotePath);
        } catch (IOException e) {
            throw new MyException(Constant.ResultCode.SHELL_ERROR, "无法创建scp连接(可能为空间满):"+e.getMessage());
        }
    }

    public void downloadFile(RemoteShellRunner shellRunner, String localPath, String remotePath, String remoteFile) throws MyException {
        try {
            File dir = new File(localPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            SCPClient scpClient = shellRunner.getConn().createSCPClient();
            scpClient.get(remotePath+"/"+remoteFile,localPath);
        } catch (IOException e) {
            logger.error("远程下载文件失败:"+e.getMessage());
            throw new MyException(Constant.ResultCode.LOGIN_FAILED, "远程下载文件失败:"+e.getMessage());
        }
    }
}
