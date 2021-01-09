package com.rsakin.getaxi.discovery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaServer
@RestController
public class DiscoveryApplication {

    @Value("${sample.profile-msg:Message could not be retrieved}")
    private String profileMsg;

    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryApplication.class).run(args);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hi() {
        return new ResponseEntity<>(profileMsg, HttpStatus.OK);
    }

}
