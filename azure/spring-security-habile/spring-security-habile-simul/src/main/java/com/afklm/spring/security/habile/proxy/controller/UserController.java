package com.afklm.spring.security.habile.proxy.controller;

import com.afklm.spring.security.habile.proxy.model.UserResource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

/**
 * User Controller
 *
 * @author TECCSE
 *
 */
@RestController
public class UserController {

    /**
     * Get information about current connected user
     * 
     * @param principal
     * @return details of the connected user
     */
    @GetMapping("/mock/me")
    public UserResource me(Principal principal) {

        UserResource user = new UserResource();

        // Only for fun
        Optional.ofNullable(principal).map(Principal::getName).ifPresent(user::setUsername);

        return user;
    }

}
