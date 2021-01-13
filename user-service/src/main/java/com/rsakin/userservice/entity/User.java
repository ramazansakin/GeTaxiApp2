package com.rsakin.userservice.entity;

import com.rsakin.userservice.util.annotation.ValidPassword;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @NotNull(message = "role can not be null")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotNull(message = "Password can not be null")
    @ValidPassword
    private String password;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

}
