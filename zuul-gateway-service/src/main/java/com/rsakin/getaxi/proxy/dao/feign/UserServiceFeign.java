package com.rsakin.getaxi.proxy.dao.feign;

import com.rsakin.getaxi.proxy.dao.model.User;
import com.rsakin.getaxi.proxy.dao.model.UserDTO;
import com.rsakin.getaxi.proxy.exception.ServiceUnavailableException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-service", url = "localhost:9001/api/user", fallback = UserServiceFeign.UserFeignClientFallback.class)
public interface UserServiceFeign {

    @GetMapping(value = "/username/{username}")
    ResponseEntity<User> findByUsername(@PathVariable("username") String username);

    @PostMapping(value = "/create")
    ResponseEntity<UserDTO> save(@RequestBody User user);

    @Component
    class UserFeignClientFallback implements UserServiceFeign {

        @Override
        public ResponseEntity<User> findByUsername(String username) {
            throw new ServiceUnavailableException("User Service is unavailable now.");
        }

        @Override
        public ResponseEntity<UserDTO> save(User user) {
            throw new ServiceUnavailableException("User Service is unavailable now.");
        }
    }
}
