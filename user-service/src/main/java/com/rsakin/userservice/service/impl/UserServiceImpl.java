package com.rsakin.userservice.service.impl;

import com.rsakin.userservice.dto.UserDTO;
import com.rsakin.userservice.entity.Address;
import com.rsakin.userservice.entity.User;
import com.rsakin.userservice.exception.InvalidRequestException;
import com.rsakin.userservice.exception.NotFoundException;
import com.rsakin.userservice.exception.UserNotFoundException;
import com.rsakin.userservice.repository.UserRepository;
import com.rsakin.userservice.service.AddressService;
import com.rsakin.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AddressService addressService;

    private ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    @Cacheable(value = "users")
    @Override
    public List<UserDTO> getAll() {
        List<User> all = userRepository.findAll();
        return all.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getOne(Integer id) {
        Optional<User> byId = userRepository.findById(id);
        User user = byId.orElseThrow(() -> new UserNotFoundException(id));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User byEmail = userRepository.findByEmail(email);
        return modelMapper.map(byEmail, UserDTO.class);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public UserDTO addOne(User user) {
        // commented out because gateway service encode/decode the pass
//        String encryptedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encryptedPassword);
        User save = userRepository.save(user);
        return modelMapper.map(save, UserDTO.class);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public UserDTO updateOne(User user) {
        // we need to check dto id or put another validation
        // to prevent db null field exceptions
        // we can customize the exception to handle on
        // genericExpHandler specifically
        if (user.getId() == null)
            throw new InvalidRequestException(user.toString());
        // check whether there is a such user or not
        getOne(user.getId());
        User save = userRepository.save(user);
        return modelMapper.map(save, UserDTO.class);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public Map<String, String> deleteOne(Integer id) {
        // check whether there is a such user or not
        Optional<User> byId = userRepository.findById(id);
        User user = byId.orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
        Map<String, String> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE.toString());
        return response;
    }

    @Override
    public List<UserDTO> getUsersByAddress(Integer address_id) {
        Address one = addressService.getOne(address_id);
        List<User> allByAddress = userRepository.getAllByAddress(one);
        return allByAddress.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getUsersByAddressCityName(String city) {
        List<User> allByAddressCity = userRepository.getAllByAddress_City(city);
        return allByAddressCity.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String username) {
        User byUsername = userRepository.findByUsername(username);
        if (byUsername == null)
            throw new NotFoundException("User not found with username : " + username);
        return byUsername;
    }
}
