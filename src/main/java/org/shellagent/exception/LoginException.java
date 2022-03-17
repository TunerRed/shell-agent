package org.shellagent.exception;

import org.shellagent.utils.Constant;

public class LoginException extends Exception {
    private int resultCode;
    public LoginException () {
        super("登录失败");
        resultCode = Constant.ResultCode.USER_LOGIN_FAILED;
    }
    public LoginException(String resultMsg) {
        super(resultMsg);
        resultCode = Constant.ResultCode.USER_LOGIN_FAILED;
    }
    public LoginException(int resultCode, String resultMsg) {
        super(resultMsg);
        this.resultCode = resultCode;
    }
    public int getResultCode() {
        return resultCode;
    }
}
