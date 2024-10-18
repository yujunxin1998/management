package com.yjx.common.base.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ResponseCode implements IResponseCode{
    SUCCESS(200, "成功"),
    FAIL(500,"服务器内部错误"),
    SYSTEM_EXECUTION_ERROR(999, "系统执行出错"),
    USERNAME_OR_PASSWORD_ERROR(601, "用户名或密码错误"),
    USER_NOT_EXIST(602, "用户不存在"),
    CLIENT_AUTHENTICATION_FAILED(603, "客户端认证失败"),
    ACCESS_UNAUTHORIZED(604, "未授权"),
    TOKEN_INVALID_OR_EXPIRED(605, "token非法或失效"),
    TOKEN_ACCESS_FORBIDDEN(606, "token禁止访问"),
    FLOW_LIMITING(771, "系统限流"),
    DEGRADATION(772, "系统功能降级"),
    SERVICE_NO_AUTHORITY(773, "服务未授权"),
    ;

    private Integer code;

    private String msg;

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + '\"' +
                ", \"msg\":\"" + msg + '\"' +
                '}';
    }
    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
