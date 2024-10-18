package com.yjx.admin.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.diit.yjx.dto.OAuth2ClientDTO;
import com.yjx.admin.entity.OauthClient;
import com.yjx.admin.util.ResponseCode;
import com.yjx.common.base.response.APIResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yjx.admin.service.OAuthService;
@Api(tags = "鉴权支撑控制器")
@Slf4j
@RestController
@RequestMapping("/oauth-clients")
@AllArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/getOAuth2ClientById")
    @ApiOperation("根据clientId获取鉴权信息")
    public APIResponse<OAuth2ClientDTO> getOAuthClientById(String clientId){
        OauthClient client = oAuthService.getById(clientId);
        Assert.notNull(client, "OAuth2 客户端不存在");
        OAuth2ClientDTO oAuth2ClientDTO = new OAuth2ClientDTO();
        BeanUtil.copyProperties(client, oAuth2ClientDTO);
        return new APIResponse<>(ResponseCode.SUCCESS,oAuth2ClientDTO);
    }
}
