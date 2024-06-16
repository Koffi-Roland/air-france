package com.afklm.spring.security.habile.proxy.model;

import com.afklm.spring.security.habile.proxy.controller.UserController;

/**
 * Class representing data returned by the {@link UserController}.
 *
 * @author TECCSE
 */
public class UserResource {

    private String username = "not authenticated yet";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
