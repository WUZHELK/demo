package com.example.demo.Enum;

import com.example.demo.response.StatusCode;
import lombok.Getter;

/**
 * 异常类枚举值
 * @author wuzhe
 * @date 2023-9-12 16:46:07
 */
@Getter
public enum ExcCode implements StatusCode {

    SYS_ERROR(80000, "系统异常"),
    BIS_ERROR(81111,"业务异常"),
    VALIDATION_ERROR(82222, "校验异常");

    private int code;

    private String msg;

    ExcCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
