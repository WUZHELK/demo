package com.example.demo.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 0L;

    private Integer userId;

    private String userName;

    private String userSex;

    private Integer userAge;

    private String cardNo;

    private long telNo;

    private String createBy;

    private Date createTime;

    private String modifyBy;

    private Date modifyTime;

    private String deleteFlag;

}
