package com.rsakin.getaxi.locationproviderservice.model;

import lombok.Value;

@Value
public class Location {

    int userId;
    long latitude;
    long longitude;

}