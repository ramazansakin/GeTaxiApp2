package com.rsakin.getaxi.proxy.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class JwtResponse implements Serializable {

    private final String username;
    private final String token;
}
