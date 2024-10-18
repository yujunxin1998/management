package com.yjx.admin.util;

import com.yjx.common.base.response.IResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode implements IResponseCode {
    SUCCESS(200, "成功"),
    FAIL(500,"服务器内部错误"),
    VERIFY_CODE_ERROR(666,"验证码校验错误"),
    USERNAME_OR_PASSWORD_ERROR(601, "用户名或密码错误"),
    USER_NOT_EXIST(602, "用户不存在"),
    USER_NOT_FOUND(601,"未找到用户"),
    CLIENT_AUTHENTICATION_FAILED(603, "客户端认证失败"),
    ACCESS_UNAUTHORIZED(604, "未授权"),
    USERNAME_NOT_MATCH_EMAIL(607, "用户名与邮箱不匹配"),
    USER_ALREADY_EXIST(608,"用户已经存在"),
    EMAIL_ALREADY_EXIST(609,"邮箱已经被注册")

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
