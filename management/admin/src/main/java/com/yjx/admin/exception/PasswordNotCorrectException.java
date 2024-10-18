package com.yjx.admin.exception;

import com.yjx.common.base.response.IResponseCode;

public class PasswordNotCorrectException extends AdminException {
    public PasswordNotCorrectException(IResponseCode responseCode) {
        super(responseCode);
    }

    public PasswordNotCorrectException(String message) {
        super(message);
    }

    public PasswordNotCorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordNotCorrectException(Throwable cause) {
        super(cause);
    }
}
