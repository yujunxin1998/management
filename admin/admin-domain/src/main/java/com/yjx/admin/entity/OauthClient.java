package com.yjx.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yjxbz
 */
@Data
@ApiModel("oauth_clients")
public class OauthClient {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "客户端ID")
    private String clientId;

    @ApiModelProperty(value = "客户端密钥")
    private String clientSecret;

    @ApiModelProperty(value = "重定向地址")
    private String redirectUrl;

    @ApiModelProperty(value = "")
    private String grantTypes;

    @ApiModelProperty(value = "作用域")
    private String scopes;
}
