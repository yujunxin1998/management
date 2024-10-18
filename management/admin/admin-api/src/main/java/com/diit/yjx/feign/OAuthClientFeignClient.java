package com.diit.yjx.feign;

import com.diit.yjx.dto.OAuth2ClientDTO;
import com.yjx.common.base.response.APIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 获取客户端信息的feign
 * @author yjxbz
 */
@FeignClient(value = "admin", contextId = "oauth-client")
public interface OAuthClientFeignClient {

    @GetMapping("/oauth-clients/getOAuth2ClientById")
    APIResponse<OAuth2ClientDTO> getOAuth2ClientById(@RequestParam(value = "clientId") String clientId);
}