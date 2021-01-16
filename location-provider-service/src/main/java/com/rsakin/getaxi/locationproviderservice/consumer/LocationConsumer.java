package com.rsakin.getaxi.locationproviderservice.consumer;

import com.rsakin.getaxi.locationproviderservice.exception.LocationNotFoundException;
import com.rsakin.getaxi.locationproviderservice.model.Location;
import com.rsakin.getaxi.locationproviderservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationConsumer {

    private final LocationService locationService;

    @KafkaListener(topics = "t_locations", containerFactory = "locationKafkaListenerFactory", groupId = "group_json")
    public void consumeLocations(List<Map<String, Object>> locationMap) {
        log.info("Consumed Location Map : {}", locationMap);

        locationMap.forEach(stringObjectMap -> {
            Integer userId = (Integer) stringObjectMap.get("userId");
            Integer latitude = (Integer) stringObjectMap.get("latitude");
            Integer longitude = (Integer) stringObjectMap.get("longitude");
            try {

                Location byUserId = locationService.getByUserId(userId);
                // if location is already there, update it
                byUserId.setLatitude(latitude);
                byUserId.setLongitude(longitude);
                locationService.save(byUserId);
            } catch (LocationNotFoundException ex) {
                // when location not found, save it
                Location newLocation = new Location();
                newLocation.setUserId(userId);
                newLocation.setLatitude(latitude);
                newLocation.setLongitude(longitude);
                locationService.save(newLocation);
            }

        });

        log.info("Total number of locations before update : {}", locationMap.size());
        log.info("Total number of locations after update : {}", locationService.getAllLocations().size());
    }

}
