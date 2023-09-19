package com.example.demo.bean.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity implements Serializable {

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

    @ExcelProperty(value = "省")
    private String province;

    @ExcelProperty(value = "市")
    private String city;

    @ExcelProperty(value = "区")
    private String area;

    @ExcelProperty(value = "街道")
    private String street;

    @ExcelProperty(value = "详细地址")
    private String address;

    @ExcelProperty(value = "备用")
    private String backup;

}
