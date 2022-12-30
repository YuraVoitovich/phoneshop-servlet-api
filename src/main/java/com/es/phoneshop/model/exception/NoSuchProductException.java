package com.es.phoneshop.model.exception;


public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException() {
        super();
    }

    public NoSuchProductException(String message) {
        super(message);
    }

    public NoSuchProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
