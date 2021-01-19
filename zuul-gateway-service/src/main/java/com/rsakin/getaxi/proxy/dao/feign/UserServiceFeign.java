package com.rsakin.getaxi.proxy.dao.feign;

import com.rsakin.getaxi.proxy.dao.fallback.UserServiceFeignFallback;
import com.rsakin.getaxi.proxy.dao.model.User;
import com.rsakin.getaxi.proxy.dao.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient( name = "user-service", fallback = UserServiceFeignFallback.class)
public interface UserServiceFeign {

    @GetMapping(value = "/api/user/username/{username}")
    ResponseEntity<User> findByUsername(@PathVariable("username") String username);

    @PostMapping(value = "/api/user/create")
    ResponseEntity<UserDTO> save(@RequestBody User user);

}
