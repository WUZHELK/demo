package com.example.demo.response;

import com.example.demo.Enum.ResultCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult<T> implements Serializable{

    private int code;

    private String msg;

    private T data;

    /**
     * 手动设置返回对象
     *
     * @param code
     * @param msg
     * @param data
     */
    public ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 默认返回成功状态码，数据对象
     *
     * @param data
     */
    public ResponseResult(T data) {
        this.code = ResultCode.RESULT_SUCCESS.getCode();
        this.msg = ResultCode.RESULT_SUCCESS.getMsg();
        this.data = data;
    }

    /**
     * 返回指定状态码，数据对象
     *
     * @param statusCode
     * @param data
     */
    public ResponseResult(StatusCode statusCode, T data) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }

    /**
     * 只返回状态码
     *
     * @param statusCode
     */
    public ResponseResult(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = null;
    }

}
