package com.rsakin.userservice.service;

import com.rsakin.userservice.dto.UserDTO;
import com.rsakin.userservice.entity.User;
import com.rsakin.userservice.repository.UserRepository;
import com.rsakin.userservice.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    UserServiceImpl userService;

    List<UserDTO> userDTOS = new ArrayList<>();
    List<User> sampleUserList = new ArrayList<>();
    UserDTO userDTO = new UserDTO();

    @Before
    public void setup() {
        userDTOS = getSampleUserDtoList(8);
        sampleUserList = getSampleUserList(8);

        when(modelMapper.map(any(), any())).thenReturn(userDTO);
    }

    @Test
    public void should_getAllUsers() {

        // stub

        // when
        Mockito.doReturn(userDTOS)
                .when(userRepository).findAll();

        // then
        userService.getAll();

//        assertEquals(8, userDTOList.size());
//        assertNotNull(userDTOList.get(0));
//        assertEquals("name0", userDTOList.get(0).getName());
//        assertEquals("last0", userDTOList.get(0).getLastname());
//
    }


    private List<UserDTO> getSampleUserDtoList(int number) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            userDTOS.add(getSampleUserDTO(i, "name" + i,
                    "lastname" + i, "user" + i,
                    "mail" + i + "@com"));
        }
        return userDTOS;
    }

    private UserDTO getSampleUserDTO(int number, String name, String last, String username, String email) {
        return UserDTO.builder()
                .id(number)
                .name(name)
                .lastname(last)
                .username(username)
                .email(email)
                .build();
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