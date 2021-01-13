package com.rsakin.userservice.dto;

import com.rsakin.userservice.entity.Address;
import com.rsakin.userservice.entity.UserRole;
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
    private UserRole role;
    private Address address;

}
