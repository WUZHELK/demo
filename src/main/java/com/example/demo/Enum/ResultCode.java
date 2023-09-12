package com.example.demo.Enum;

import com.example.demo.response.StatusCode;
import lombok.Getter;

/**
 * 统一返回对象
 * @author wuzhe
 * @date 2023-9-12 16:46:50
 */
@Getter
public enum ResultCode implements StatusCode {

    RESULT_SUCCESS(91000, "返回成功！"),
    RESULT_FAIL(91999, "返回失败！"),
    RESULT_VALIDATE(91111, "参数校验失败！"),
    RESULT_ERROR(91888, "发生异常！");

    private int code;

    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
