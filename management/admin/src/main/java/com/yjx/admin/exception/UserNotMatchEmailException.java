package com.yjx.admin.exception;

import com.yjx.admin.util.ResponseCode;
import com.yjx.common.base.response.IResponseCode;

public class UserNotMatchEmailException extends AdminException {

    public UserNotMatchEmailException(IResponseCode responseCode) {
        super(responseCode);
    }

    public UserNotMatchEmailException(String message) {
        super(message);
    }

    public UserNotMatchEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotMatchEmailException(Throwable cause) {
        super(cause);
    }
}
