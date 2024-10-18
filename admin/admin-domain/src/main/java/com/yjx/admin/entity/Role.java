package com.yjx.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户角色类")
public class Role {

    @ApiModelProperty("角色id")
    private Integer id;

    @ApiModelProperty("角色名")
    private String roleName;
}
