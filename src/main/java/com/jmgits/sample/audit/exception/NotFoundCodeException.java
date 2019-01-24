package com.jmgits.sample.audit.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class NotFoundCodeException extends AbstractCodeException {

    public NotFoundCodeException(String code, String message) {
        super(message, code);
    }

    public NotFoundCodeException(String code, String message, Throwable cause) {
        super(message, cause, code);
    }

    @Override
    public int getStatus() {
        return 404;
    }
}
