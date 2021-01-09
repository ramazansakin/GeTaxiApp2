package com.rsakin.userservice.service;

import com.rsakin.userservice.dto.UserDTO;
import com.rsakin.userservice.entity.User;
import com.rsakin.userservice.exception.UserNotFoundException;
import com.rsakin.userservice.repository.UserRepository;
import com.rsakin.userservice.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    AddressService addressService;

    UserServiceImpl userService;


    @Before
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        userService = new UserServiceImpl(userRepository, addressService);
        userService.setModelMapper(modelMapper);
    }

    @Test
    public void should_getAllUsers() {
        // stub
        List<User> sampleUserList = getSampleUserList(8);

        // when
        // Mockito.when(modelMapper.map(org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any())).thenReturn(temp);
        Mockito.when(userRepository.findAll()).thenReturn(sampleUserList);

        // then
        List<UserDTO> userDTOList = userService.getAll();

        assertEquals(8, userDTOList.size());
        assertEquals("name0", userDTOList.get(0).getName());
        assertEquals("lastname0", userDTOList.get(0).getLastname());

    }

    @Test
    public void should_getOneUser() {
        // stub
        User sampleUser = getSampleUserList(1).get(0);

        // when
        Mockito.when(userRepository.findById(sampleUser.getId())).thenReturn(java.util.Optional.of(sampleUser));

        // then
        UserDTO one = userService.getOne(sampleUser.getId());

        assertEquals("name0", one.getName());
        assertEquals("lastname0", one.getLastname());

    }

    @Test
    public void should_createOneUser() {
        // stub
        User sampleUser = getSampleUserList(1).get(0);

        // when
        Mockito.when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        // then
        UserDTO one = userService.addOne(sampleUser);

        assertEquals(sampleUser.getName(), one.getName());
        assertEquals(sampleUser.getLastname(), one.getLastname());
        assertEquals(sampleUser.getEmail(), one.getEmail());

    }

    @Test
    public void should_NOT_updateUser() {
        // stub
        User user = getSampleUserList(1).get(0);

        // then
        UserNotFoundException userNotFoundException = assertThrows(
                UserNotFoundException.class,
                () -> userService.updateOne(user),
                "Expected doThing() to throw, but it didn't"
        );

        assertTrue(userNotFoundException.getMessage().contains("User not found"));
    }

    @Test
    public void should_deleteUser() {
        // stub
        User user = getSampleUserList(1).get(0);

        // when
        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(java.util.Optional.of(user));
        // doNothing().when(userRepository).delete(user);

        UserDTO one = userService.getOne(user.getId());
        addressService.deleteOne(one.getId());

        assertNull(addressService.getOne(user.getId()));
    }

    private List<User> getSampleUserList(int number) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            userList.add(getSampleUser(i, "name" + i,
                    "lastname" + i, "user" + i,
                    "mail" + i + "@com"));
        }
        return userList;
    }

    private User getSampleUser(int number, String name, String last, String username, String email) {
        return User.builder()
                .id(number)
                .name(name)
                .lastname(last)
                .username(username)
                .email(email)
                .build();
    }

}