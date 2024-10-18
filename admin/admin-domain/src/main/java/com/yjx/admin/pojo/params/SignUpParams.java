package com.yjx.admin.pojo.params;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("注册参数类")
public class SignUpParams {

    private String username;

    private String password;

    private String email;

    private String code;
}
