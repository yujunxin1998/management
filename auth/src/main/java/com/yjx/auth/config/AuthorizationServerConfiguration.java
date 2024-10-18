package com.yjx.auth.config;

import com.yjx.auth.entity.SystemUserDetails;
import com.yjx.auth.service.ClientDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.util.CollectionUtils;


import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final ClientDetailsServiceImpl clientDetailsService;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    /**
     * OAuth2客户端
     * @param clients
     * @throws Exception
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 配置授权(authorization)以及令牌(token)的访问端点和令牌服务(token services)
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // token增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccesssTokenConvertor());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        // 获取原有默认授权模式(授权码模式、密码模式、客户端模式、简化模式)的授权者
        List<TokenGranter> tokenGranters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
        CompositeTokenGranter compositeTokenGranter = new CompositeTokenGranter(tokenGranters);
        endpoints
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccesssTokenConvertor())
                .tokenEnhancer(tokenEnhancerChain)
                .tokenGranter(compositeTokenGranter)
                .reuseRefreshTokens(true)
                .tokenServices(tokenServices(endpoints));
    }


    public DefaultTokenServices tokenServices(AuthorizationServerEndpointsConfigurer endpoints){
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccesssTokenConvertor());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        return tokenServices;
    }

    /**
     * JWT内容增强
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer(){
        return (accessToken,authentication) ->{
            Map<String,Object> additionalInfo = CollectionUtils.newHashMap(32);
            Object principal = authentication.getUserAuthentication().getPrincipal();
            if(principal instanceof SystemUserDetails){
                SystemUserDetails sysUserDetails = (SystemUserDetails) principal;
                // 塞入一些额外的用户信息，对token内容进行扩充
                additionalInfo.put("userId",sysUserDetails.getUserId());
                additionalInfo.put("username",sysUserDetails.getUsername());
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            }
            return accessToken;
        };
    }


    /**
     * 使用非对称加密算法对Token签名
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccesssTokenConvertor(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 密钥库中获取密钥对(公钥+私钥)
     * @return KeyPair
     */
    @Bean
    public KeyPair keyPair(){
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"),"123456".toCharArray());
        return factory.getKeyPair("jwt","123456".toCharArray());
    }
}