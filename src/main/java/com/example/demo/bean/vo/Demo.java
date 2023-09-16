package com.example.demo.bean.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Demo {

    @ExcelProperty(value = "用户名")
    private String username;

    @ExcelProperty(value = "密码")
    private String password;

    @ExcelProperty(value = "年龄")
    private Integer age;

    @ExcelProperty(value = "性别")
    private String gender;
}
