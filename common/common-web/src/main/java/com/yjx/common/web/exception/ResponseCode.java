package com.yjx.common.web.exception;


import com.yjx.common.base.response.IResponseCode;

public enum ResponseCode implements IResponseCode {

    MethodArgumentNotValidException(403, "失败"),
    FAIL(500,"失败");


    private Integer code;
    private String msg;


    ResponseCode(Integer code,String msg ) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
