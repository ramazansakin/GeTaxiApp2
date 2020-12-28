package com.rsakin.userservice.exception;

public class NotValidPasswordException extends RuntimeException {
    public NotValidPasswordException(String message) {
        super(message);
    }
}
