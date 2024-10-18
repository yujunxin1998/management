package com.yjx.admin.exception;

import com.yjx.common.base.response.IResponseCode;
import com.yjx.common.web.exception.BizBaseException;

public class AdminException extends BizBaseException {
    public AdminException(IResponseCode responseCode) {
        super(responseCode);
    }

    public AdminException(String message) {
        super(message);
    }

    public AdminException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminException(Throwable cause) {
        super(cause);
    }
}
