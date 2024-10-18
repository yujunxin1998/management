package com.yjx.common.web.exception;

import com.yjx.common.base.response.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yjxbz
 */
@Slf4j
@ResponseBody
@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

    /**
     * 全局异常拦截 一般异常
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public final APIResponse<?> handleAllExceptions(Exception ex) {
        log.error("[RuntimeException]",ex);
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        return new APIResponse<>(ResponseCode.FAIL,ex);
    }

    /**
     * 全局异常处理器
     * @param ex
     * @return APIResponse
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public APIResponse<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.error("非法参数异常，异常原因: {}",ex.getMessage(),ex);
        return new APIResponse<>(ResponseCode.MethodArgumentNotValidException,ex.getMessage());
    }

    /**
     * 全局异常处理器
     * @param ex
     * @return APIResponse
     */
    @ExceptionHandler(BizBaseException.class)
    public APIResponse<?> customerExceptionHandler(BizBaseException ex) {
        log.error("[customerException]",ex);
        return new APIResponse<>(ex.getResponseCode());
    }
}
