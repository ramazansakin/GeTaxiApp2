package com.rsakin.userservice.repository;

import com.rsakin.userservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> getAllByCity(String city);

    List<Address> getAllByBuildingNoAndDoorNo(Integer buildingNo, Integer doorNo);

    Address getFirstByCityContaining(String city_prefix);
}
