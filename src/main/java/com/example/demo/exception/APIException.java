package com.example.demo.exception;

import com.example.demo.Enum.ExcCode;
import com.example.demo.response.StatusCode;
import lombok.Getter;

@Getter
public class APIException extends RuntimeException{

    private int code;
    private String msg;

    // 手动设置异常
    public APIException(StatusCode statusCode, String message) {
        // message用于用户设置抛出错误详情，例如：当前价格-5，小于0
        super(message);
        // 状态码
        this.code = statusCode.getCode();
        // 状态码配套的msg
        this.msg = statusCode.getMsg();
    }

    // 默认异常使用BIS_ERROR状态码
    public APIException(String message) {
        super(message);
        this.code = ExcCode.BIS_ERROR.getCode();
        this.msg = ExcCode.BIS_ERROR.getMsg();
    }

}
