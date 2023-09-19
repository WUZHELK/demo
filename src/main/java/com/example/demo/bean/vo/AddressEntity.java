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
