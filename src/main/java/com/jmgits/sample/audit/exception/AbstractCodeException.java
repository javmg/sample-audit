package com.jmgits.sample.audit.exception;

import lombok.Getter;

@Getter
public abstract class AbstractCodeException extends RuntimeException {

    private final String code;

    public AbstractCodeException(String message, String code) {
        super(message);
        this.code = code;
    }

    public AbstractCodeException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }

    public abstract int getStatus();

    public String toString() {
        String s = getClass().getName() + " [" + code + "]";

        String message = getLocalizedMessage();

        return (message != null) ? (s + ": " + message) : s;
    }
}
