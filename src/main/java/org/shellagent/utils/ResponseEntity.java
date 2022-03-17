package org.shellagent.utils;

public class ResponseEntity {

    private int resultCode;
    private String resultMsg;
    private Object resultData;

    public int getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public Object getResultData() {
        return resultData;
    }

    protected void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    protected void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    protected void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    protected ResponseEntity() {
        resultCode = Constant.ResultCode.SUCCESS;
        resultMsg = "ok";
    }
}
