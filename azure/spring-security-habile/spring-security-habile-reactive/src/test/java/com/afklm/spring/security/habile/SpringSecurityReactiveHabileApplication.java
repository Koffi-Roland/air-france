package com.afklm.spring.security.habile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

/**
 * Entry point to start the application.
 * 
 * @author TECC
 */
@SpringBootApplication
@RestController
public class SpringSecurityReactiveHabileApplication {
    
    public static final String MSG_TO_CLIENT = "This is a message sent to all clients when the server is receiving a msg.";
    
	/**
	 * Main entry point
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityReactiveHabileApplication.class, args);
	}
	
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/fake/user")
    public Mono<String> ok() {
        return Mono.just("ok");
    }
}
