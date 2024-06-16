package com.afklm.spring.security.habile.demo.microservices.svca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
public class MicroServiceA {

    private final EurekaClient eurekaClient;

    public MicroServiceA(@Lazy EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(MicroServiceA.class, args);
    }

    // Generic HTTP method handler
    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String resource(HttpServletRequest request) {
        final InstanceInfo instanceInfo = eurekaClient.getApplicationInfoManager().getInfo();
        return """
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
            request.getMethod(),
            request.getProtocol(),
            request.getRequestURI(),
            request.getRequestId(),
            request.getRemoteUser(),
            request.getRemoteAddr(),
            request.getRemotePort(),
            request.getLocalAddr(),
            request.getLocalPort());
    }
}
