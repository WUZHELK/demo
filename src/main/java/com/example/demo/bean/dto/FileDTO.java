package com.example.demo.bean.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FileDTO implements Serializable {

    private static final long serialVersionUID = -235461321345L;

    @NotBlank(message = "间隔符不能为空")
    private String delimiter;

    @NotBlank(message = "结束符不能为空")
    private String endLimiter;

    @NotNull(message = "数据量不能为空")
    private Integer writeNum;

    @NotNull(message = "文件数量不能为空")
    private Integer totalSize;

    @NotNull(message = "主键下标不能为空")
    private Integer primaryIndex;

    @NotBlank(message = "文件名不能为空")
    private String fileName;

}
