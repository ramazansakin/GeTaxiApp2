package com.rsakin.userservice.entity;

import com.rsakin.userservice.util.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

}
