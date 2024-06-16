package com.afklm.spring.security.habile.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing data returned by the {@link UserController}
 * 
 * @author TECC
 *
 */
public class UserResource {
    private String username;
    private String lastname;
    private String firstname;
    private String email;
    private String idToken;

    private List<String> roles = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
