package com.rsakin.userservice.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Integer id) {
        super("User not found by id : " + id);
    }
}
