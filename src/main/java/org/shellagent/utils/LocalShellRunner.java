package org.shellagent.utils;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.jetbrains.annotations.NotNull;
import org.shellagent.entity.Property;
import org.shellagent.exception.MyException;
import org.shellagent.services.PropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.LinkedList;
import java.util.List;

/**
 * 运行脚本的工具类，包括登录和退出.
 * @author codev
 * */
public class LocalShellRunner {

    private static boolean success;

    private static Connection conn = null;
    private static LinkedList<String> resultMsg;

    // 命令超时时间
    private static final long TIME_OUT = 1000 * 60 * 30;

    private static Logger logger = LoggerFactory.getLogger(LocalShellRunner.class);

    private LocalShellRunner() throws MyException {}

    /**
     * 执行命令并获取输出.
     * @param cmd 要执行的命令
     * @return 命令是否执行成功
     * */
    private static boolean runCommandImmadiately(String cmd) throws MyException {
        resultMsg=new LinkedList<>();
        resultMsg.clear();
        success = false;
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader in = new LineNumberReader(ir);
            String line;
            while ((line = in.readLine()) != null) {
                resultMsg.add(line);
            }
        } catch (IOException e) {
            logger.error("Session执行失败" + e.getMessage());
            throw new MyException(Constant.ResultCode.INTERNAL_ERROR, "远程会话异常："+e.getMessage());
        } catch (NullPointerException e) {
            logger.error("空指针："+e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("wdnmd ShellRunner又双叒叕意外的Exception");
            e.printStackTrace();
        }
        return success;
    }

    public static boolean runCommand(String cmd) throws MyException {
        return runCommandImmadiately(cmd);
    }

    /**
     * 获取shell执行的输出信息.
     * @return  shell执行的输出信息
     * */
    public static LinkedList<String> getResult() {
        if (resultMsg == null || resultMsg.size() == 0)
            return null;
        return (LinkedList<String>) resultMsg.clone();
    }
    public static String[] getResultArray() {
        if (resultMsg == null)
            return null;
        return getResult().toArray(new String[]{});
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

}
