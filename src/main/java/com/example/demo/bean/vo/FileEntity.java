package com.example.demo.bean.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity implements Serializable {

    @ExcelProperty(value = "姓名")
    private String name;

    @ExcelProperty(value = "年龄")
    private int age;

    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "操作时间")
    private Date time;

}
