package com.rsakin.getaxi.proxy.dao.fallback;

import com.rsakin.getaxi.proxy.dao.feign.UserServiceFeign;
import com.rsakin.getaxi.proxy.dao.model.User;
import com.rsakin.getaxi.proxy.dao.model.UserDTO;
import com.rsakin.getaxi.proxy.exception.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserServiceFeignFallback implements UserServiceFeign {

    private static final String INFO_MSG = "User Service is temporarily unavailable. Please try again soon.";

    @Override
    public ResponseEntity<User> findByUsername(String username) {
        throw new ServiceUnavailableException(INFO_MSG);
    }

    @Override
    public ResponseEntity<UserDTO> save(User user) {
        throw new ServiceUnavailableException(INFO_MSG);
    }
}