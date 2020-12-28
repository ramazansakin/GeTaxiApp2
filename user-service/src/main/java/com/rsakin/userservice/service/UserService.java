package com.rsakin.userservice.service;

import com.rsakin.userservice.dto.UserDTO;
import com.rsakin.userservice.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<UserDTO> getAll();

    UserDTO getOne(Integer id);

    UserDTO getUserByEmail(String email);

    UserDTO addOne(User address);

    UserDTO updateOne(User address);

    Map<String, String> deleteOne(Integer id);

    List<UserDTO> getUsersByAddress(Integer address_id);

    List<UserDTO> getUsersByAddressCityName(String city);

    User findByUsername(String username);
}
