package com.afklm.spring.security.habile.web;

import com.afklm.spring.security.habile.userdetails.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller
 * 
 * @author m405991
 *
 */
@RestController("habileUserController")
public class UserController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Get information about current connected user
     * 
     * @param authentication authentication
     * @return id of the connected user
     */
    @GetMapping("/me")
    public Object me(@AuthenticationPrincipal UserDetails authentication) {
        return userDetailsService.render(authentication);
    }

}
