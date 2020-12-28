package com.rsakin.userservice.repository;

import com.rsakin.userservice.entity.Address;
import com.rsakin.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    List<User> getAllByAddress(Address address);

    List<User> getAllByAddress_City(String city);

    User findByUsername(String username);

}
