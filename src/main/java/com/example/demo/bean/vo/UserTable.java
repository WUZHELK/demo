package com.example.demo.bean.vo;


import com.example.demo.annotation.DataField;

import java.util.Date;

/**
 * 用户信息表单头
 *
 * @author wuzhe
 * @date 2023-9-12 10:52:59
 */
public class UserTable {

    @DataField(name = "userId", title = "用户ID")
    private Integer userId;

    @DataField(name = "userName", title = "用户名")
    private String userName;

    @DataField(name = "userSex", title = "性别")
    private String userSex;

    @DataField(name = "userAge", title = "年龄")
    private Integer userAge;

    @DataField(name = "cardNo", title = "证件号")
    private String cardNo;

    @DataField(name = "telNo", title = "手机号")
    private long telNo;

    @DataField(name = "createBy", title = "创建人")
    private String createBy;

    @DataField(name = "createTime", title = "创建时间")
    private Date createTime;

    @DataField(name = "modifyBy", title = "更新人")
    private String modifyBy;

    @DataField(name = "modifyTime", title = "更新时间")
    private Date modifyTime;

}
