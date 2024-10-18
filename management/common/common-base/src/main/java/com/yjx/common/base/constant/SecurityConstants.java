package com.yjx.common.base.constant;

/**
 * @author yjxbz
 */
public interface SecurityConstants {
    /**
     * 认证请求头key
     */
    String AUTHORIZATION_KEY = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_PREFIX = "Bearer ";

    /**
     * basic认证前缀
     */
    String BASIC_PREFIX = "Basic ";

    /**　
     * JWT载体key
     */
    String JWT_PAYLOAD_KEY = "payload";

    /**
     * JWT 唯一标识
     */
    String JWT_JTI = "jti";
    /**
     * JWT 唯一标识
     */
    String JWT_EXP = "exp";

    /**
     * JWT 存储权限属性
     */
    String JWT_AUTHORITIES_KEY = "authorities";

    String JWT_API_PATTERN = "/*/app-api/**";

    /**
     * JWT 黑名单TOKEN前缀
     */
    String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";

    String AUTHORITY_PREFIX = "ROLE_";
}
