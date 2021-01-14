package com.rsakin.userservice.controller;

import com.rsakin.userservice.dto.UserDTO;
import com.rsakin.userservice.entity.User;
import com.rsakin.userservice.model.Location;
import com.rsakin.userservice.service.UserService;
import com.rsakin.userservice.simu.LocationSimulator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RefreshScope
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private final MessageSource messageSource;

    private final LocationSimulator locationSimulator;

    @GetMapping("/welcome")
    public void welcome() {
        log.info("label_de.properties : {}", messageSource.getMessage("welcome.message",
                new Object[]{""}, Locale.GERMAN));
        log.info("label.properties : {}", messageSource.getMessage("welcome.message",
                new Object[]{""}, Locale.ENGLISH));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> all = userService.getAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable @Range(min = 1) Integer id) {
        UserDTO one = userService.getOne(id);
        return new ResponseEntity<>(one, HttpStatus.OK);
    }

    @GetMapping("/user-by-email/{email}")
    public ResponseEntity<UserDTO> getByEmail(@PathVariable @Email String email) {
        UserDTO userByEmail = userService.getUserByEmail(email);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> save(@RequestBody @Valid User user) {
        UserDTO actual = userService.addOne(user);
        return new ResponseEntity<>(actual, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> update(@RequestBody @Valid User user) {
        UserDTO actual = userService.updateOne(user);
        return new ResponseEntity<>(actual, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable @Range(min = 1) Integer id) {
        Map<String, String> stringStringMap = userService.deleteOne(id);
        return new ResponseEntity<>(stringStringMap, HttpStatus.OK);
    }

    @GetMapping("/all/address/{id}")
    public ResponseEntity<List<UserDTO>> getAllUsersWithAddress(@PathVariable @Range(min = 1, max = 200) Integer id) {
        List<UserDTO> usersByAddress = userService.getUsersByAddress(id);
        return new ResponseEntity<>(usersByAddress, HttpStatus.OK);
    }

    @GetMapping("/all/address/city/{city_name}")
    public ResponseEntity<List<UserDTO>> getAllUsersWithAddress(@PathVariable String city_name) {
        List<UserDTO> usersByAddressCityName = userService.getUsersByAddressCityName(city_name);
        return new ResponseEntity<>(usersByAddressCityName, HttpStatus.OK);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/driver-locations")
    public ResponseEntity<List<Location>> getAllDriverLocations() {
        List<Location> allDriverLocations = locationSimulator.getAllDriverLocations();
        return new ResponseEntity<>(allDriverLocations, HttpStatus.OK);
    }
}
