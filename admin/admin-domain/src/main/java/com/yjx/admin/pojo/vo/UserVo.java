package com.yjx.admin.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("userVo类")
public class UserVo {
    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;

    @ApiModelProperty(value = "邮箱")
    private String email;
}
