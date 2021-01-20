package com.rsakin.getaxi.proxy.service;

import com.rsakin.getaxi.proxy.dao.feign.UserServiceFeign;
import com.rsakin.getaxi.proxy.dao.model.User;
import com.rsakin.getaxi.proxy.dao.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    @Qualifier("user-service")
    private final UserServiceFeign userServiceFeign;

    private final PasswordEncoder bcryptEncoder;

    private static final Map<String, User> userCache = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user;
        if (userCache.get(username) != null) {
            user = userCache.get(username);
        } else {
            ResponseEntity<User> byUsername = userServiceFeign.findByUsername(username);
            user = byUsername.getBody();
        }
        if (user == null) {
            throw new AuthenticationServiceException("Not authenticated  user [ " + username + " ]");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public UserDTO save(User user) {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        ResponseEntity<UserDTO> userDTOResponseEntity = userServiceFeign.save(user);
        UserDTO body = userDTOResponseEntity.getBody();
        if (body != null) {
            userCache.put(user.getUsername(), user);
            return body;
        }
        return null;
    }
}