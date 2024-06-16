package com.afklm.spring.security.habile.demo.microservices.svcb;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class MicroServiceB {

    private static final String UNAVAILABLE = "N/A";

    private final EurekaClient eurekaClient;

    public MicroServiceB(@Lazy EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceB.class, args);
    }

    // Generic HTTP method handler
    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> resource(ServerHttpRequest request, Principal user) {
        final InstanceInfo instanceInfo = eurekaClient.getApplicationInfoManager().getInfo();
        return Mono.just("""
            {
                "group": "%s",
                "service": "%s",
                "instance": "%s",
                "method": "%s",
                "protocol": "%s",
                "request_uri": "%s", 
                "request_id": "%s", 
                "remote_user": "%s",
                "remote_ip": "%s",
                "remote_port": %d,
                "local_ip": "%s",
                "local_port": %d
            }""".formatted(
            instanceInfo.getAppGroupName(),
            instanceInfo.getAppName(),
            instanceInfo.getId(),
            request.getMethod().name(),
            UNAVAILABLE,
            request.getURI(),
            request.getId(),
            user,
            request.getRemoteAddress().getAddress(),
            request.getRemoteAddress().getPort(),
            request.getLocalAddress().getAddress(),
            request.getLocalAddress().getPort()));

    }
}
