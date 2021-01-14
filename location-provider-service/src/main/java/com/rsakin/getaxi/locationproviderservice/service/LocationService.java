package com.rsakin.getaxi.locationproviderservice.service;

import com.rsakin.getaxi.locationproviderservice.exception.LocationNotFoundException;
import com.rsakin.getaxi.locationproviderservice.model.Location;
import com.rsakin.getaxi.locationproviderservice.repo.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        Iterable<Location> all = locationRepository.findAll();
        List<Location> locationList = new ArrayList<>();
        all.forEach(locationList::add);
        return locationList;
    }

    public Location getById(Long id) {
        Optional<Location> byId = locationRepository.findById(id);
        return byId.orElseThrow(() -> new LocationNotFoundException("for location id : " + id));
    }

    public Location getByUserId(int userId) {
        Optional<Location> byUserId = locationRepository.findByUserId(userId);
        return byUserId.orElseThrow(() -> new LocationNotFoundException("for user id : " + userId));
    }

    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public Boolean delete(Location location) {
        Location byId = getById(location.getId());
        locationRepository.delete(byId);
        return true;
    }
}
