package org.delonce.exception;

public class ObjectByIdNotFoundException extends RuntimeException {
    public ObjectByIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
