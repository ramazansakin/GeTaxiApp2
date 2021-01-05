package com.rsakin.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rsakin.userservice.dto.UserDTO;
import com.rsakin.userservice.entity.Address;
import com.rsakin.userservice.entity.User;
import com.rsakin.userservice.exception.AddressNotFoundException;
import com.rsakin.userservice.service.AddressService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    // constants
    public static final int TEST_ADDRESS_COUNT = 8;

    @Test
    void should_getAll() throws Exception {
        // stubbing
        List<Address> sampleAddressList = getSampleAddressList(TEST_ADDRESS_COUNT);

        // when
        Mockito.when(addressService.getAll()).thenReturn(sampleAddressList);

        // then
        String url = "/api/address/all";
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                // check the response address size
                .andExpect(jsonPath("$", hasSize(TEST_ADDRESS_COUNT)));
    }

    @Test
    void should_getOneById() throws Exception {
        // stub
        Address stubAddress = getSampleAddressList(1).get(0);

        // when
        Mockito.when(addressService.getOne(any(Integer.class)))
                .thenReturn(stubAddress);

        // then
        String url = "/api/address/{id}";
        mockMvc.perform(get(url, stubAddress.getId()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.city", is(stubAddress.getCity())))
                .andExpect(jsonPath("$.doorNo", is(stubAddress.getDoorNo())));
    }

    @Test
    void should_NOT_getOneById() throws Exception {

        // when
        Mockito.when(addressService.getOne(any(Integer.class)))
                .thenThrow(new AddressNotFoundException(any()));

        // then
        String url = "/api/address/{id}";
        mockMvc.perform(get(url, 1))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void should_crate() throws Exception {
        // stubbing
        Address sampleAddress = getSampleAddressList(1).get(0);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(sampleAddress);

        // when
        Mockito.when(addressService.addOne(sampleAddress))
                .thenReturn(sampleAddress);

        // then
        String url = "/api/address/create";
        mockMvc.perform(post(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city", is(sampleAddress.getCity())))
                .andExpect(jsonPath("$.doorNo", is(sampleAddress.getDoorNo())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void should_update() throws Exception {
        // stubbing
        Address oldAddress = new Address(1,"city", "street", 1,1);

        Address newAddress = new Address(1,"new-city", "street", 5,5);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(oldAddress);


        // when
        // changed name "name" to "new-name"
        Mockito.doReturn(newAddress).when(addressService).updateOne(oldAddress);

        // then
        String url = "/api/address/update";
        mockMvc.perform(put(url)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.doorNo", is(newAddress.getDoorNo())))
                .andExpect(jsonPath("$.buildingNo", is(newAddress.getBuildingNo())))
                .andExpect(jsonPath("$.city", is(newAddress.getCity())));
    }

    @Test
    void should_delete() throws Exception {
        // stubbing
        Address sampleAddress = new Address(1,"city", "street", 1,1);
        Boolean response = Boolean.TRUE;

        // when
        Mockito.doReturn(response).when(addressService).deleteOne(sampleAddress.getId());

        // then
        String url = "/api/address/delete/{id}";
        mockMvc.perform(delete(url, sampleAddress.getId()))
                .andExpect(status().isOk());

    }

    // sample address list creation
    private List<Address> getSampleAddressList(int number) {
        List<Address> addressList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            // sample test addresses
            addressList.add(new Address(1, "City-" + i, "Street-" + i, i + 1, (i + 1) * 5));
        }
        return addressList;
    }

}