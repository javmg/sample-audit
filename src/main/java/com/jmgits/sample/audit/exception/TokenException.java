package com.jmgits.sample.audit.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ResponseStatus(UNAUTHORIZED)
public class TokenException extends RuntimeException {

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
