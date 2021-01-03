package com.rsakin.userservice.controller;

import com.rsakin.userservice.dto.UserDTO;
import com.rsakin.userservice.entity.User;
import com.rsakin.userservice.filter.RequestFilter;
import com.rsakin.userservice.repository.UserRepository;
import com.rsakin.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RequestFilter requestFilter;

    @BeforeEach
    void setup() {
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

    private List<UserDTO> getSampleUserDtoList(int number) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            userDTOS.add(getSampleUser("name" + i,
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

    private UserDTO getSampleUser(String name, String last, String username, String email) {
        return UserDTO.builder()
                .name(name)
                .lastname(last)
                .username(username)
                .email(email)
                .build();
    }

    @Test
    void should_get() {
    }

    @Test
    void testGet() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAllUsersWithAddress() {
    }

    @Test
    void testGetAllUsersWithAddress() {
    }

    @Test
    void findByUsername() {
    }
}