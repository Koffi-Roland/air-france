package com.afklm.spring.security.habile.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@RestController
public class PublicController {
    @PutMapping("/public/data/{what}")
    public String setData(@PathVariable String what, HttpSession session) {
        session.setAttribute("SS4H-DATA", what);
        return "data";
    }

    @GetMapping({"/public/data", "/anonymous/data"})
    public String getData(HttpServletRequest request) {
        return Optional.ofNullable(request)
            .map(HttpServletRequest::getSession)
            .map(session -> session.getAttribute("SS4H-DATA"))
            .map(String.class::cast)
            .orElse("NoData");
    }
}
