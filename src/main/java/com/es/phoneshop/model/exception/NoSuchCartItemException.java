package com.es.phoneshop.model.exception;

public class NoSuchCartItemException extends RuntimeException {
    public NoSuchCartItemException() {
        super();
    }

    public NoSuchCartItemException(String message) {
        super(message);
    }

    public NoSuchCartItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
