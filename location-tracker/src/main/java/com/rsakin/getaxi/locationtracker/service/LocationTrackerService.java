package com.rsakin.getaxi.locationtracker.service;

import com.rsakin.getaxi.locationtracker.feign.UserServiceFeignClient;
import com.rsakin.getaxi.locationtracker.model.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.naming.ServiceUnavailableException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocationTrackerService {

    private final KafkaTemplate<String, Object> kafkaLocationProducer;

    private final UserServiceFeignClient userServiceFeignClient;

    private static final String TOPIC_LOCATIONS = "t_locations";

    // run every 15 secs
    @Scheduled(fixedRate = 15 * 1000)
    public void updateAllDriverLocations() {
        try {
            ResponseEntity<Map<Integer, Location>> allDriverLocations = userServiceFeignClient.getAllDriverLocations();
            log.info("All updated driver locations sent to kafka : {}", allDriverLocations);
            kafkaLocationProducer.send(TOPIC_LOCATIONS, allDriverLocations.getBody());
        } catch (ServiceUnavailableException e) {
            log.error("Service throw exception while getting locations", e);
        }
    }

}
