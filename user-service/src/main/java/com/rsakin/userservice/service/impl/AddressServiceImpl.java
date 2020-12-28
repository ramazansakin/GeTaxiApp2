package com.rsakin.userservice.service.impl;

import com.rsakin.userservice.entity.Address;
import com.rsakin.userservice.exception.AddressNotFoundException;
import com.rsakin.userservice.exception.InvalidRequestException;
import com.rsakin.userservice.repository.AddressRepository;
import com.rsakin.userservice.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Cacheable(value = "addresses")
    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address getOne(Integer id) {
        Optional<Address> byId = addressRepository.findById(id);
        return byId.orElseThrow(() -> new AddressNotFoundException(id));
    }

    @CacheEvict(value = "addresses", allEntries = true)
    @Override
    public Address addOne(Address address) {
        return addressRepository.save(address);
    }

    @CacheEvict(value = "addresses", allEntries = true)
    @Override
    public Address updateOne(Address address) {
        if (address.getId() == null)
            throw new InvalidRequestException("Id must not be null for update entity");
        getOne(address.getId());
        return addressRepository.save(address);
    }

    @CacheEvict(value = "addresses", allEntries = true)
    @Override
    public boolean deleteOne(Integer id) {
        Address one = getOne(id);
        addressRepository.save(one);
        return true;
    }
}
