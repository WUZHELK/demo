package com.example.demo.advice;

import com.example.demo.Enum.ResultCode;
import com.example.demo.exception.APIException;
import com.example.demo.response.ResponseResult;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller异常返回结果处理
 * @author wuzhe
 * @date 2023-9-12 16:43:05
 */
@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * Validation校验异常返回
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class})
    public ResponseResult MethValidateExceptionHandler(BindException e){
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return new ResponseResult(ResultCode.RESULT_VALIDATE, objectError.getDefaultMessage());
    }

    /**
     * 自定义异常返回结果
     * @param e
     * @return
     */
    @ExceptionHandler({APIException.class})
    public ResponseResult MethAPIExceptionHandler(APIException e){
        return new ResponseResult(e.getCode(), e.getMsg(), e.getMessage());
    }

}
