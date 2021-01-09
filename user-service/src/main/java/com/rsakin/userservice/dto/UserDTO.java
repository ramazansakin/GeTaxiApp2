package com.rsakin.userservice.dto;

import com.rsakin.userservice.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private Integer id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private Address address;

}
