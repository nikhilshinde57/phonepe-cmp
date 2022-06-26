package com.phonepe.cmp.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOperationException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public InvalidOperationException(final String errorMsg, final Throwable errorObject) {
        super(errorMsg, errorObject);
    }

    public InvalidOperationException(final String errorMsg) {
        super(errorMsg);
    }
}
