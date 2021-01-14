package com.rsakin.getaxi.locationproviderservice.exception;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(String cause) {
        super("Not found location " + cause);
    }
}
