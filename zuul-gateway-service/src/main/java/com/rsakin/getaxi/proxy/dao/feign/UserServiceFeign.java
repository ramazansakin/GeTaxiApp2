package com.rsakin.getaxi.proxy.dao.feign;

import com.rsakin.getaxi.proxy.dao.model.User;
import com.rsakin.getaxi.proxy.dao.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-service", url = "localhost:9001/api/user")
public interface UserServiceFeign {

    @GetMapping(value = "/username/{username}")
    ResponseEntity<User> findByUsername(@PathVariable("username") String username);

    @PostMapping(value = "/create")
    ResponseEntity<UserDTO> save(@RequestBody User user);
}
