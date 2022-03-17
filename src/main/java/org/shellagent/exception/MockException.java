package org.shellagent.exception;

public class MockException extends Exception {
    Object resultData;
    String resultMsg;

    public MockException(Object resultData, String resultMsg) {
        this.resultData = resultData;
        this.resultMsg = resultMsg;
    }
    public MockException(Object resultData) {
        this.resultData = resultData;
        this.resultMsg = "模拟数据";
    }
    public MockException() {
        this(null);
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
