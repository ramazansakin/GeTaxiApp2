package com.rsakin.getaxi.locationtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class LocationTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationTrackerApplication.class, args);
    }

}
