package com.rsakin.userservice.simu;

import com.rsakin.userservice.entity.User;
import com.rsakin.userservice.model.Location;
import com.rsakin.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationSimulator {

    private final UserService userService;
    private Map<Integer, Location> driversLocationCache = new ConcurrentHashMap<>();

    // Turkey latitude-longitude coordinates
    private static final double MIN_LAT = 36;
    private static final double MAX_LAT = 42;
    private static final double MIN_LONG = 26;
    private static final double MAX_LONG = 45;

    // run every 15 secs
    @Scheduled(fixedRate = 15 * 1000)
    public void simulateDriverLocations() {
        logCronTime();
        // get all available drivers
        List<User> allDrivers = userService.getAllDrivers();
        simulateLocations(allDrivers);
        log.info("Updated locations ---> " + driversLocationCache.toString());
    }

    // TODO : location simulations can be consistent
    private void simulateLocations(List<User> allDrivers) {
        allDrivers.forEach(driver -> {
            Location location = Location.builder()
                    .userId(driver.getId())
                    .latitude(getRandomLocation(MIN_LAT, MAX_LAT))
                    .longitude(getRandomLocation(MIN_LONG, MAX_LONG))
                    .build();
            driversLocationCache.put(driver.getId(), location);
        });
    }

    private int getRandomLocation(double min, double max) {
        Random randomLoc = new Random();
        return (int) ((randomLoc.nextDouble() * (max - min)) + min);
    }

    private void logCronTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        log.info("Cron job triggered at :: " + strDate);
    }

    // TODO : test the locations can be modified ?
    public Map<Integer, Location> getAllDriverLocations() {
        return new ConcurrentHashMap<>(driversLocationCache);
    }

}
