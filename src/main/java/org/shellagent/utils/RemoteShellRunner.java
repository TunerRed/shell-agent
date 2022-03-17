package org.shellagent.utils;

import ch.ethz.ssh2.ChannelCondition;
import org.jetbrains.annotations.NotNull;
import org.shellagent.entity.Property;
import org.shellagent.exception.MyException;
import org.shellagent.services.PropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * 运行脚本的工具类，包括登录和退出.
 * @author codev
 * */
public class RemoteShellRunner {
    private String host;
    private String username;
    private String password;

    private boolean success;

    private Connection conn = null;
    private LinkedList<String> resultMsg;
    private StringBuffer errorMsg;

    // 命令超时时间
    private static final long TIME_OUT = 1000 * 60 * 30;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public RemoteShellRunner(String host, String username, String password) throws MyException {
        this.init(host, username, password);
    }

    /**
     * 一般情况下不会只获取用户名密码就行了，还需要其他路径信息。
     * 所以这里不再多查询一次数据库，直接传list进来
     * */
    public RemoteShellRunner(String host, PropertyService propertyService, List<Property> serverInfo) throws MyException {
        init(host,propertyService.getValueByType(serverInfo, Constant.PropertyType.USERNAME),
                propertyService.getValueByType(serverInfo, Constant.PropertyType.PASSWORD));
    }

    /**
     * 只需要用户名密码进行登录操作的情况
     * */
    public RemoteShellRunner(String host, PropertyService propertyService) throws MyException {
        List<Property> serverInfo = propertyService.getServerInfo(host);
        init(host,propertyService.getValueByType(serverInfo, Constant.PropertyType.USERNAME),
                propertyService.getValueByType(serverInfo, Constant.PropertyType.PASSWORD));
    }

    public void init(@NotNull String host,@NotNull String username,@NotNull String password) throws MyException {
        this.host=host;
        this.username=username;
        this.password= EncUtil.decode(password);
        if (this.host.equals("")||this.username.equals("")||this.password.equals(""))
            throw new MyException(Constant.ResultCode.ARGS_ERROR, "缺少必要的远程登录信息");
        logger.debug("主机密码："+this.password);
        resultMsg=new LinkedList<>();
        errorMsg=new StringBuffer();
    }

    /**
     * 登录远程桌面.
     * 执行命令前先确定有成功登录
     * @return 是否登录成功
     * */
    public boolean login() throws MyException {
        try {
            conn = new Connection(host);
            conn.connect();
            if (conn.authenticateWithPassword(username,password)){
                logger.info("登录成功:"+conn.getHostname());
            } else {
                logger.error("登录失败["+host+"]:"+conn.getConnectionInfo());
                throw new MyException(Constant.ResultCode.LOGIN_FAILED, "登录["+host+"]认证失败|");
            }
        } catch (IOException e) {
            logger.error("登录失败:"+e.getMessage());
            exit();
            throw new MyException(Constant.ResultCode.LOGIN_FAILED, "远程登录失败："+host);
        }
        return true;
    }

    /**
     * 退出远程桌面.
     * */
    public void exit() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
                logger.info("退出登录");
            } catch (Exception e){
                logger.error("退出登录失败:"+e.getMessage());
            }
        }
    }

    /**
     * 在远程桌面，执行命令并获取输出.
     * @param cmd 要执行的命令
     * @return 命令是否执行成功
     * */
    private boolean runCommandImmadiately(String cmd) throws MyException {
        resultMsg.clear();
        if (errorMsg != null && errorMsg.length() > 0)
            errorMsg.delete(0, errorMsg.length()-1);
        Session session = null;
        success = false;
        try {
            session = conn.openSession();
            session.execCommand(cmd);
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout()),"UTF-8"));
            BufferedReader stderrReader = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStderr()),"UTF-8"));
            String line;
            while ((line=stdoutReader.readLine())!=null) {
//                logger.info(line);
                resultMsg.add(line);
            }
            while ((line=stderrReader.readLine())!=null) {
                logger.error(line);
                errorMsg.append(line+"\n");
            }
            // 获取ExitStatus前似乎要先wait.
            session.waitForCondition(ChannelCondition.EXIT_STATUS|ChannelCondition.CLOSED|ChannelCondition.EOF, TIME_OUT);
            // 脚本如果没有写exit 0则会返回null
            success = (session.getExitStatus() == null || session.getExitStatus() == 0);
        } catch (IOException e) {
            logger.error("Session执行失败" + e.getMessage());
            throw new MyException(Constant.ResultCode.INTERNAL_ERROR, "远程会话异常："+e.getMessage());
        } catch (NullPointerException e) {
            logger.error("空指针："+e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("wdnmd ShellRunner又双叒叕意外的Exception");
            errorMsg.append("ShellRunner又双叒叕意外的Exception"+e.getMessage()+"\n");
            e.printStackTrace();
        }finally {
            if (session != null) {
                session.close();
            }
        }
        return success;
    }

    public boolean runCommand(String cmd) throws MyException {
        return runCommandImmadiately(cmd);
    }

    /**
     * 获取shell执行的输出信息.
     * @return  shell执行的输出信息
     * */
    public LinkedList<String> getResult() {
        if (resultMsg == null || resultMsg.size() == 0)
            return null;
        return (LinkedList<String>) resultMsg.clone();
    }
    public String[] getResultArray() {
        if (resultMsg == null)
            return null;
        return getResult().toArray(new String[]{});
    }

    /**
     * 获取shell执行的错误信息.
     * 在脚本中会将错误以 1>&2 的形式输出
     * 多数情况下脚本会直接'exit 1'
     * @return  shell执行的错误信息
     * */
    public String getError() {
        return errorMsg.toString().length() > 10000?errorMsg.toString().substring(0, 10000):errorMsg.toString();
    }

    /**
     * 拼接参数的小工具.
     * 有参数为空时直接报错并输出整个参数列表
     * @param args 命令参数
     * */
    public static String appendArgs(String[] args) throws MyException {
        StringBuffer argsBuffer = new StringBuffer();
        boolean _throw = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null || args[i].isEmpty())
                _throw = true;
            argsBuffer.append(" "+args[i]);
        }
        if (_throw) {
            throw new MyException(Constant.ResultCode.ARGS_ERROR, "脚本缺少必要的参数:"+argsBuffer.toString());
        }
        return argsBuffer.toString();
    }

    /**
     * 执行命令是否成功.
     * 如果脚本执行了exit 1，则判定为不成功
     * */
    public boolean isSuccess() {
        return success;
    }
    public Connection getConn() {
        return conn;
    }
}
