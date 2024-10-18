package com.yjx.auth.enums;

import lombok.Getter;

/**
 *@description: 密码加密类型枚举
 *@author:     yujunxin
 *@createTime: 2024/6/20 9:03
 *@version:    1.0
 */
@Getter
public enum PasswordEncoderTypeEnum {
    BCRYPT("{bcrypt}","BCRYPT加密"),
    NOOP("{noop}","无加密明文");
    private final String prefix;
    PasswordEncoderTypeEnum(String prefix, String desc){
        this.prefix=prefix;
    }
}
