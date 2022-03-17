package org.shellagent.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    private ResponseEntity responseEntity;
    Map<String, Object> data;

    public ResponseBuilder() {
        responseEntity = new ResponseEntity();
    }

    public ResponseBuilder setCode(int resultCode) {
        responseEntity.setResultCode(resultCode);
        return this;
    }
    public ResponseBuilder setMsg(String resultMsg) {
        responseEntity.setResultMsg(resultMsg);
        return this;
    }

    /**
     * 设置返回的resultData.
     * @param object 要返回的对象
     * @return 返回builder自身，方便套娃
     * */
    public ResponseBuilder setData(Object object) {
        responseEntity.setResultData(object);
        if (data != null && !data.isEmpty())
            data.clear();
        return this;
    }
    /**
     * 设置要返回的resultData.
     * 当要返回的信息中有多个对象时使用该方法逐个存储
     * 其实按照规范应当使用OutDTO之类
     * @param name 返回json的名字
     * @param value 返回json对应的值，应当为对象而不是字符串，否则前端会读取失败
     * @return 返回builder自身，方便套娃
     * */
    public ResponseBuilder putItem(String name, Object value) {
        if (data == null)
            data = new HashMap<>();
        data.put(name, value);
        return this;
    }
    /**
     * 返回给前端的对象.
     * 在设置完一系列参数后返回，默认'200,ok,null'
     * */
    public ResponseEntity getResponseEntity() {
        if (data != null && data.size() != 0)
            responseEntity.setResultData(data);
        return responseEntity;
    }
}
