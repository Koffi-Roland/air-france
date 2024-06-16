package com.afklm.spring.security.habile.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afklm.spring.security.habile.user.CompleteHabileUserDetails;
import com.afklm.spring.security.habile.userdetails.CustomUserDetailsService;

import reactor.core.publisher.Mono;

/**
 * This class is used to provide a REST controller exposing some details about the currently authenticated user.
 *
 * @author TECC SE
 */
@RestController
public class UserController {
    
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    public UserController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
    /**
     * Endpoint returning data regarding the currently authenticated user. To prevent leaking any sensitive user data,
     * a {@link UserResource} acts as a proxy between the provided {@link CompleteHabileUserDetails} and the returned data.
     *
     * @param authentication Principal of the currently authenticated user.
     * @return Mono of {@link UserResource} containing the relevant and non sensitive properties of the currently
     * authenticated user.
     */
    @GetMapping("/me")
    public Mono<Object> me(@AuthenticationPrincipal UserDetails authentication) {
        return Mono.just(authentication).map(customUserDetailsService::render);
    }
}
