package com.yjx.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yjx.admin.util.Md5Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@TableName("user")
@ApiModel("系统用户")
public class SystemUser {

    @ApiModelProperty(value = "用户ID", required = true)
    @TableId
    private Long id;

    @ApiModelProperty(value = "用户名", required = true)
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "盐值", required = true)
    @TableField("salt")
    private String salt;

    @ApiModelProperty(value = "电话号码")
    @TableField("phone_number")
    private String phoneNumber;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "是否管理员")
    @TableField("if_admin")
    private Boolean isAdmin;

    @ApiModelProperty(value = "是否VIP")
    @TableField("if_vip")
    private Boolean isVip;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_person")
    private String createPerson;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_person")
    private String updatePerson;

    public void fieldFill(String email, String username, String password) {
        this.email = email;
        this.username = username;
        // 生成6位随机salt
        this.salt = UUID.randomUUID().toString().replaceAll("-","").substring(0,6);
        this.password = Md5Util.encryptWithSalt(password,salt);

        // 刚注册的账号默认为非管理员以及非VIP
        this.isAdmin = false;
        this.isVip = false;

        this.createTime = new Date();
        this.createPerson = username;
        this.updateTime = new Date();
        this.updatePerson = username;
    }
}
