package com.rsakin.userservice.exception;

public class InvalidRequestException extends RuntimeException{
    public InvalidRequestException(String requestBody) {
        super("Invalid request body : " + requestBody);
    }
}
