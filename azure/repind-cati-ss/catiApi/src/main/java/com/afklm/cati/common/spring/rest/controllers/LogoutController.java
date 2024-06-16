package com.afklm.cati.common.spring.rest.controllers;

import com.afklm.spring.security.habile.GenericHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class LogoutController {

    @GetMapping("ldap/logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse resp) throws IOException {

        String logoutUrl = request.getHeader(GenericHeaders.SM_LOGOUT);
        if (logoutUrl == null) {
            logoutUrl = request.getHeader(GenericHeaders.X_LOGOUT_URL);
        }

        HttpSession session = request.getSession(false);
        SecurityContextHolder.clearContext();
        if (session != null) {
            session.invalidate();
        }


        resp.sendRedirect(logoutUrl);
    }

}
