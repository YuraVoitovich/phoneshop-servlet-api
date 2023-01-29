package com.es.phoneshop.model.exception;


public class NoSuchOrderException extends RuntimeException {
    public NoSuchOrderException() {
        super();
    }

    public NoSuchOrderException(String message) {
        super(message);
    }

    public NoSuchOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
