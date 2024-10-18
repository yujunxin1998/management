package com.yjx.admin.exception;

import com.yjx.common.base.response.IResponseCode;
public class UserNotFoundException extends AdminException {
    public UserNotFoundException(IResponseCode responseCode) {
        super(responseCode);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
