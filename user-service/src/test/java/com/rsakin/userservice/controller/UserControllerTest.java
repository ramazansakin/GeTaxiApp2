package com.rsakin.userservice.controller;

import com.rsakin.userservice.dto.UserDTO;
import com.rsakin.userservice.entity.User;
import com.rsakin.userservice.exception.NotFoundException;
import com.rsakin.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void printApplicationContext() {
    }

    @Test
    void should_getAll() throws Exception {
        // stubbing
        List<UserDTO> sampleUserDtoList = getSampleUserDtoList(5);

        // when
        Mockito.when(userService.getAll()).thenReturn(sampleUserDtoList);

        // then
        String url = "/api/user/all";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void should_throw_exception_getAll() throws Exception {
        // when
        Mockito.when(userService.getAll())
                .thenThrow(new NotFoundException("user"));

        // then
        String url = "/api/user/all";
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void should_getOne() throws Exception {
        // when
        UserDTO stubUser = getSampleUser(1, "name", "last", "user", "mail@com");
        Mockito.when(userService.getOne(any(Integer.class)))
                .thenReturn(stubUser);

        // then
        String url = "/api/user/{id}";
        mockMvc.perform(get(url, stubUser.getId()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id", is(stubUser.getId())))
                .andExpect(jsonPath("$.email", is(stubUser.getEmail())));
    }

    @Test
    void should_NOT_getOne() throws Exception {
        // when
        Mockito.when(userService.getOne(any(Integer.class)))
                .thenThrow(new NotFoundException("user not found"));

        // then
        String url = "/api/user/{id}";
        mockMvc.perform(get(url, 1))
                .andExpect(status().isNotFound());
    }

    private List<UserDTO> getSampleUserDtoList(int number) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            userDTOS.add(getSampleUser(i, "name" + i,
                    "lastname" + i, "user" + i,
                    "mail" + i + "@com"));
        }
        return userDTOS;
    }

    private List<User> getSampleUsers(int number) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            User sample = User.builder()
                    .id(i)
                    .username("user" + i)
                    .email("mail" + i + "@com")
                    .build();
            userList.add(sample);
        }
        return userList;
    }

    private UserDTO getSampleUser(int number, String name, String last, String username, String email) {
        return UserDTO.builder()
                .id(number)
                .name(name)
                .lastname(last)
                .username(username)
                .email(email)
                .build();
    }

}