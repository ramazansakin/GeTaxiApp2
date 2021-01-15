package com.rsakin.getaxi.locationproviderservice.model;

import lombok.Value;

@Value
public class LocationDTO {

    int userId;
    long latitude;
    long longitude;

}