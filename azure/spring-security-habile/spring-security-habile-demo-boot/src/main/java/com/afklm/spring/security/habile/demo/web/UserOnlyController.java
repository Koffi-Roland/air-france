package com.afklm.spring.security.habile.demo.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Demo controller
 * 
 * @author m408461
 *
 */
@RestController
@RequestMapping("api/user-only")
public class UserOnlyController {
    /**
     * Endpoint in order to check that we are restricted access to sub
     * request patterns by configuration.
     * 
     * @return hardcoded value
     */
    @GetMapping("/hello")
    public String me() {
        return "hello";
    }

    @GetMapping("/byebye")
    public String byebye() {
        return "say bye bye get!";
    }
    /**
     * Endpoint in order to check that we are restricted access to sub
     * request patterns by configuration including http method.
     *
     * @return hardcoded value
     */
    @PostMapping("/hello")
    public String mePost() {
        return "hello post";
    }

    /**
     * Endpoint in order to check that we are restricted access to sub
     * request patterns by configuration including http method.
     *
     * @return hardcoded value
     */
    @PutMapping("/hello")
    public String mePut() {
        return "hello put";
    }

    /**
     * Endpoint in order to check that we are restricted access to sub
     * request patterns by configuration including http method.
     *
     * @return hardcoded value
     */
    @DeleteMapping("/byebye")
    public String meDelete() {
        return "bye bye";
    }

    /**
     * Endpoint that handles every HTTP method and that is used to check
     * authorization from configuration files (roles and/or authorities).
     * 
     * @param request The incoming HTTP request.
     * @return A message with the HTTP request method used.
     */
    @RequestMapping("/howareyou")
    public String howAreYou(HttpServletRequest request) {
        return String.format("Fine %s", request.getMethod());
    }
}
