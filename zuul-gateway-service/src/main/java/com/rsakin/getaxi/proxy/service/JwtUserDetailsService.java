package com.rsakin.getaxi.proxy.service;

import com.rsakin.getaxi.proxy.dao.feign.UserServiceFeign;
import com.rsakin.getaxi.proxy.dao.model.User;
import com.rsakin.getaxi.proxy.dao.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceFeign userServiceFeign;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        ResponseEntity<User> byUsername = userServiceFeign.findByUsername(username);
        User user = byUsername.getBody();
        if (user == null) {
            throw new AuthenticationServiceException("Not authenticated  user [ " + username + " ]");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public UserDTO save(User user) {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        ResponseEntity<UserDTO> userDTOResponseEntity = userServiceFeign.save(user);
        return userDTOResponseEntity.getBody();
    }
}