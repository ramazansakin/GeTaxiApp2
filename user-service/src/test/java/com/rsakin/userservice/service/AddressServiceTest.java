package com.rsakin.userservice.service;

import com.rsakin.userservice.entity.Address;
import com.rsakin.userservice.exception.AddressNotFoundException;
import com.rsakin.userservice.repository.AddressRepository;
import com.rsakin.userservice.service.impl.AddressServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AddressServiceTest {

    @InjectMocks
    AddressServiceImpl addressService;

    @Mock
    AddressRepository addressRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_getAllAddresses() {

        // stub
        List<Address> addressList = getSampleAddressList(5);

        // when
        Mockito.when(addressRepository.findAll())
                .thenReturn(addressList);

        // then
        List<Address> addresses = addressService.getAll();

        assertEquals(5, addresses.size());
        assertNotNull(addresses.get(0));
        assertEquals("City-0", addresses.get(0).getCity());
        assertEquals(new Integer(1), addresses.get(0).getBuildingNo());

        verify(addressRepository, times(1)).findAll();

    }

    @Test
    public void should_createAddress() {

        // stub
        Address address = getSampleAddressList(1).get(0);

        // when
        Mockito.when(addressRepository.save(address))
                .thenReturn(address);

        // then
        Address returnedAddress = addressService.addOne(address);

        assertNotNull(returnedAddress);
        assertEquals("City-0", returnedAddress.getCity());
        verify(addressRepository, times(1)).save(address);

    }

    @Test
    public void should_updateAddress() {

        // stub
        Address oldAddress = getSampleAddressList(2).get(0);
        Address newAddress = getSampleAddressList(2).get(1);
        newAddress.setId(1);

        // when
        Mockito.when(addressRepository.findById(oldAddress.getId()))
                .thenReturn(java.util.Optional.of(oldAddress));
        Mockito.when(addressRepository.save(Objects.requireNonNull(oldAddress)))
                .thenReturn(newAddress);

        // then
        Address address = addressService.updateOne(oldAddress);

        assertNotNull(address);
        assertEquals(newAddress.getCity(), address.getCity());
        assertEquals(newAddress.getStreet(), address.getStreet());
        assertEquals(newAddress.getBuildingNo(), address.getBuildingNo());

    }

    @Test
    public void should_NOT_updateAddress() {
        // stub
        Address address = getSampleAddressList(1).get(0);

        // then
        AddressNotFoundException addressNotFoundException = assertThrows(
                AddressNotFoundException.class,
                () -> addressService.updateOne(address),
                "Expected doThing() to throw, but it didn't"
        );

        assertTrue(addressNotFoundException.getMessage().contains("Address not found"));
    }

    @Test
    public void testDeleteCustomer() {
        // stub
        Address address = getSampleAddressList(1).get(0);

        //stub the data
        Mockito.when(addressRepository.findById(address.getId()))
                .thenReturn(java.util.Optional.of(address));
        doNothing().when(addressRepository).delete(address);

        addressService.deleteOne(address.getId());
    }

    // sample address list creation
    private List<Address> getSampleAddressList(int number) {
        List<Address> addressList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            // sample test addresses
            addressList.add(new Address(i + 1, "City-" + i, "Street-" + i, i + 1, (i + 1) * 5));
        }
        return addressList;
    }

}