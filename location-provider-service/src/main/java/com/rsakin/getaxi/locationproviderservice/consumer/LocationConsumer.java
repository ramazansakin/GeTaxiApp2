package com.rsakin.getaxi.locationproviderservice.consumer;

import com.rsakin.getaxi.locationproviderservice.model.Location;
import com.rsakin.getaxi.locationproviderservice.model.LocationDTO;
import com.rsakin.getaxi.locationproviderservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationConsumer {

    private final LocationService locationService;

    private final ModelMapper modelMapper;

    @KafkaListener(topics = "t_locations", containerFactory = "locationKafkaListenerFactory", groupId = "group_json")
    public void consumeLocations(Map<Integer, LocationDTO> locationMap) {
        log.info("Consumed Location Map : {}", locationMap);
        locationMap.forEach((userId, locationDTO) -> {
            Location byUserId = locationService.getByUserId(userId);
            // If still there is, update it or save new one
            if (byUserId != null) {
                // update
                locationService.save(byUserId);
            } else {
                // add new
                locationService.save(modelMapper.map(locationDTO, Location.class));
            }
        });
    }

}
