package com.rsakin.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "City can not be null")
    private String city;

    @NotNull(message = "Street can not be null")
    private String street;

    @Column(name = "building_no")
    private Integer buildingNo;

    @Column(name = "door_no")
    private Integer doorNo;

}
