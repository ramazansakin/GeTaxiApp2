package com.rsakin.getaxi.proxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DAOUser {

    private long id;
    private String username;
    private String password;

}
