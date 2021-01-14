package com.rsakin.getaxi.locationproviderservice.exception;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(int userId) {
        super("Not found location for user id : " + userId);
    }
}
