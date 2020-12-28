package com.rsakin.userservice.service;

import com.rsakin.userservice.entity.Address;

import java.util.List;

public interface AddressService {

    List<Address> getAll();

    Address getOne(Integer id);

    Address addOne(Address address);

    Address updateOne(Address address);

    boolean deleteOne(Integer id);
}
