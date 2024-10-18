package com.yjx.admin.pojo.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

@Data
@ApiModel("校验验证码并修改密码")
public class VerifyCodeAndUpdatePassword {

    @ApiModelProperty(value = "用户邮箱地址", required = true)
    private String toEmail;

    @ApiModelProperty(value = "邮箱验证码",required = true)
    private String code;

    @ApiModelProperty(value = "新的用户密码",required = true)
    private String newPassword;

    @ApiModelProperty(value = "用户名",required = true)
    private String username;
}
