package com.rsakin.getaxi.locationproviderservice.consumer;

import com.rsakin.getaxi.locationproviderservice.model.LocationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class LocationConsumer {

    @KafkaListener(topics = "t_locations", containerFactory = "locationKafkaListenerFactory", groupId = "group_json")
    public void consumeJson(Map<Integer, LocationDTO> locationMap) {
        log.info("Consumed Location Map : {}", locationMap);
    }

}
