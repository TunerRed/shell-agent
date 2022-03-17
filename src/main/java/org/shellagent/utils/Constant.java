package org.shellagent.utils;

/**
 * 常量列表
 * */
public class Constant {
    public class ResultCode {
        public static final int
        SUCCESS = 200,

        LOGIN_FAILED = 101,
        USER_LOGIN_FAILED = 102,
        FILE_ERROR = 120,

        NOT_GRANT = 401,
        NOT_FOUND = 404,

        INTERNAL_ERROR = 500,
        DEVELOPING = 501,

        ARGS_ERROR = 1551,
        SHELL_ERROR = 1552;
    }
    public class ResultMsg {
        public static final String
            SUCCESS = "200",
            LOGIN_FAILED = "登录失败",
            NOT_GRANT = "未授权操作",
            DEVELOPING = "接口开发中",
            ARGS_ERROR = "参数错误",
            PACKING = "仓库正在打包，请稍后再试",
            SHELL_ERROR = "脚本执行错误";
    }
    public class PropertyType {
        public static final String
            IP = "SERVER_IP",
            DEPLOY_PATH = "DEPLOY_PATH",
            BACKUP_PATH = "BACKUP_PATH",
            RUN_PATH = "RUN_PATH",
            LOG_PATH = "LOG_PATH",
            USERNAME = "SERVER_USER",
            PASSWORD = "SERVER_PASS",
            JAR_RENAME = "RENAME";
    }
    public class PropertyKey {
        public static final String
            FRONTEND = "FRONTEND",
            SERVICE = "SERVICE",
            JAR_PREFIX = "PREFIX",
            JAR_SUFFIX = "SUFFIX";
    }
    public class RequestArg {
        public static final String
            Auth = "Authorization";
    }
    public class SQLEnum {
        public static final String
            ROOT_USER = "1";
    }
}
