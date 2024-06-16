package com.afklm.spring.security.habile.demo.microservices.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MicroServicesRegistry {

    public static void main(String[] args) {
        SpringApplication.run(MicroServicesRegistry.class, args);
    }
}
