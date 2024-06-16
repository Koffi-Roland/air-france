package com.afklm.spring.security.habile.demo.web;

import com.afklm.spring.security.habile.HabileUserDetails;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Demo controller
 * 
 * @author M405991
 *
 */
@RestController
public class DemoController {

    /**
     * Saves the given parameter within the session
     * @param what the value to be stored
     * @param session the {@link HttpSession}
     * @return a hardcoded value
     */
    @PutMapping("/data/{what}")
    public String setData(@PathVariable String what, HttpSession session) {
        session.setAttribute("SS4H-DATA", what);
        return "data";
    }

    /**
     * User endpoint in order to check that we are able to customize the Spring
     * Principal.
     * 
     * @return hardcoded value
     */
    @GetMapping({"/api/custom-me", "/anonymous/custom-me"})
    public HabileUserDetails me(@AuthenticationPrincipal HabileUserDetails authentication) {
        return authentication;
    }

    /**
     * User endpoint accessible with VIEW authority
     * 
     * @return hardcoded value
     */
    @GetMapping("/api/user")
    @PreAuthorize("hasAuthority('VIEW')")
    public String user() {
        return "user";
    }

    /**
     * Admin endpoint accessible with UPDATE authority
     * 
     * @return hardcoded value
     */
    @GetMapping("/api/admin")
    @PreAuthorize("hasAuthority('UPDATE')")
    public String admin() {
        return "admin";
    }

    /**
     * Update endpoint accessible with UPDATE authority
     * 
     * @param message message
     */
    @PostMapping("/api/update")
    @PreAuthorize("hasAuthority('UPDATE')")
    public void update(String message) {
        // Nothing done yet
    }

    /**
     * Redirect endpoint that returns 302 and hard-coded location to <a href="http://localhost:8080/me">http://localhost:8080/me</a>.
     *
     */
    @GetMapping("/redirect-me")
    public void handleFoo(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:8080/me");
    }
}
