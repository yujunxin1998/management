package com.yjx.auth.service;

import com.diit.yjx.dto.OAuth2ClientDTO;
import com.diit.yjx.feign.OAuthClientFeignClient;
import com.yjx.auth.enums.PasswordEncoderTypeEnum;
import com.yjx.common.base.response.APIResponse;
import com.yjx.common.base.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

/**
 * @author yjxbz
 */
@Service("ClientDetailsServiceImpl")
@RequiredArgsConstructor
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final OAuthClientFeignClient oAuthClientFeignClient;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        // 实际上这里应该通过Feign接口从管理端获取
        APIResponse<OAuth2ClientDTO> result = oAuthClientFeignClient.getOAuth2ClientById(clientId);
        // 授权模式password-密码模式,client_credentials,refresh_token-刷新token,authorization_code-授权码模式
        if (ResponseCode.SUCCESS.getCode()==200) {
            OAuth2ClientDTO client = (OAuth2ClientDTO) result.getData();
            BaseClientDetails clientDetails = new BaseClientDetails(
                    client.getClientId(),
                    client.getResourceIds(),
                    client.getScope(),
                    client.getAuthorizedGrantTypes(),
                    client.getAuthorities(),
                    client.getWebServerRedirectUri());
            clientDetails.setClientSecret(PasswordEncoderTypeEnum.NOOP.getPrefix() + client.getClientSecret());
            clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
            clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
            return clientDetails;
        } else {
            throw new NoSuchClientException(result.getMsg());
        }
    }
}
