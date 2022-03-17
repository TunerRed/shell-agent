package org.shellagent.exception;

import org.shellagent.utils.Constant;
import org.shellagent.utils.ResponseBuilder;
import org.shellagent.utils.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
@ResponseBody
public class ErrorHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.servlet.multipart.max-file-size}")
    String maxFileSize;

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity errorHandler (Exception e) {
        logger.error("意外的Exception："+e.getMessage());
        e.printStackTrace();
        return new ResponseBuilder().setCode(Constant.ResultCode.INTERNAL_ERROR).setMsg(e.getMessage()).getResponseEntity();
    }

    @ExceptionHandler(value = MyException.class)
    public ResponseEntity errorHandler (MyException e) {
        return new ResponseBuilder().setCode(e.getResultCode()).setMsg(e.getMessage()).getResponseEntity();
    }

    @ExceptionHandler(value = LoginException.class)
    public ResponseEntity errorHandler (LoginException e) {
        return new ResponseBuilder().setCode(e.getResultCode()).setMsg(e.getMessage()).getResponseEntity();
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity errorHandler (MaxUploadSizeExceededException e) {
        return new ResponseBuilder().setCode(Constant.ResultCode.FILE_ERROR).setMsg("文件大小超出限制:"+maxFileSize).getResponseEntity();
    }

    @ExceptionHandler(value = MockException.class)
    public ResponseEntity errorHandler (MockException e) {
        logger.debug("模拟数据返回");
        return new ResponseBuilder().setMsg(e.getMessage()).setData(e.getResultData()).getResponseEntity();
    }
}
