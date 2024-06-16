package com.afklm.spring.security.habile.proxy.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulation configuration
 * 
 * Holds parameters used for configuration: users, public and anonymous path patterns
 * 
 * @author m405991
 * @author m408461
 *
 */
public class SimulConfiguration {
    private String[] anonymousPaths = new String[] {};
    private String[] publicPaths = new String[] {};
    private List<UserInformation> users = new ArrayList<>();

    public List<UserInformation> getUsers() {
        return users;
    }

    public void setUsers(List<UserInformation> users) {
        this.users = users;
    }

    public String[] getAnonymousPaths() {
        return anonymousPaths;
    }

    public void setAnonymousPaths(String[] anonymousPaths) {
        this.anonymousPaths = anonymousPaths;
    }

    public String[] getPublicPaths() {
        return publicPaths;
    }

    public void setPublicPaths(String[] publicPaths) {
        this.publicPaths = publicPaths;
    }
}
