package com.example.demo.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("com_user_tbl")
@Data
public class UserBean {

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "user_sex")
    private String userSex;

    @TableField(value = "user_age")
    private Integer userAge;

    @TableField(value = "card_no")
    private String cardNo;

    @TableField(value = "tel_no")
    private long telNo;

    @TableField(value = "create_by")
    private String createBy;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "modify_by")
    private String modifyBy;

    @TableField(value = "modify_time")
    private Date modifyTime;

    @TableField(value = "delete_flag")
    private String deleteFlag;
}
