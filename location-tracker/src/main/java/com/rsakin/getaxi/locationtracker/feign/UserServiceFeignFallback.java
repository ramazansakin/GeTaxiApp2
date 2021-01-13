package com.rsakin.getaxi.locationtracker.feign;

import com.rsakin.getaxi.locationtracker.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.naming.ServiceUnavailableException;
import java.util.Map;

@Slf4j
@Component
public class UserServiceFeignFallback implements UserServiceFeignClient {

    private static final String INFO_MSG = "User Service is temporarily unavailable: ";

    @Override
    public ResponseEntity<Map<Integer, Location>> getAllDriverLocations() throws ServiceUnavailableException {
        throw new ServiceUnavailableException(INFO_MSG);
    }
}