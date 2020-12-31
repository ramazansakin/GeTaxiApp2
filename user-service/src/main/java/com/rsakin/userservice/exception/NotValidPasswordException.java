package com.rsakin.userservice.exception;

import javax.validation.ValidationException;

public class NotValidPasswordException extends ValidationException {
    public NotValidPasswordException(String message) {
        super(message);
    }
}
