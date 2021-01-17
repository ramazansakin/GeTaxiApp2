package com.rsakin.getaxi.proxy.dao.model;

import com.rsakin.getaxi.proxy.util.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private String name;
    private String lastname;

    @NotNull(message = "Username can not be null")
    private String username;

    @NotNull(message = "Email can not be null")
    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "Password can not be null")
    @ValidPassword
    private String password;

    @NotNull(message = "role can not be null")
    private String role;

}