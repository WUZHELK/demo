package com.example.demo.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeiMeiFileDTO implements Serializable {

    private static final long serialVersionUID = -584685452145L;

    @NotBlank(message = "导入文件名不能为空")
    private String fileInName;

    @NotBlank(message = "生成文件名不能为空")
    private String fileOutName;
}
