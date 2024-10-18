package com.yjx.common.base.response;

import lombok.Data;

import java.util.Date;

/**
 * 响应报文，统一封装类
 * @author yjxbz
 * @param <T> 响应数据的类型
 */
@Data
public class APIResponse<T> {

    private Integer code;
    private String msg;
    private String trace;
    private T data;
    private long timestamp;
    private long count;

    public APIResponse() {
        this.timestamp = (new Date()).getTime();
    }

    public APIResponse(IResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.msg = responseCode.getMsg();
        this.timestamp = (new Date()).getTime();
    }

    public APIResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.trace = msg;
        this.timestamp = (new Date()).getTime();
    }

    public APIResponse(IResponseCode responseCode, T data) {
        this.data = data;
        this.code = responseCode.getCode();
        this.msg = responseCode.getMsg();
        this.timestamp = (new Date()).getTime();
    }

    public APIResponse(IResponseCode responseCode, String msg, T data) {
        this.code = responseCode.getCode();
        this.trace = msg;
        this.data = data;
        this.timestamp = (new Date()).getTime();
    }

    public APIResponse(IResponseCode responseCode, String msg, long count, T data) {
        this.code = responseCode.getCode();
        this.trace = msg;
        this.count = count;
        this.data = data;
        this.timestamp = (new Date()).getTime();
    }

    public APIResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public APIResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
