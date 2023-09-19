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
public class FileAddressEntity implements Serializable {

    @ExcelProperty(value = "id")
    private String xuhao;

    @ExcelProperty(value = "订单号")
    private String orderNum;

    @ExcelProperty(value = "菜鸟地址")
    private String cnAddress;

    @ExcelProperty(value = "菜鸟名称")
    private String cnName;

    @ExcelProperty(value = "菜鸟手机号")
    private String cnTel;

}
