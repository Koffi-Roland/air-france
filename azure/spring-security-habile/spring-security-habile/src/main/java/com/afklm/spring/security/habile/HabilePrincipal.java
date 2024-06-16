package com.afklm.spring.security.habile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * Habile Principal representation.
 */
public class HabilePrincipal implements Principal {
    private final String userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final List<String> profiles = new ArrayList<>();

    /**
     * Default constructor
     * 
     * @param userId user ID
     * @param firstName first name
     * @param lastName last name
     * @param email may be null
     * @param profiles profiles
     */
    public HabilePrincipal(String userId, String firstName, String lastName, String email, List<String> profiles) {
        super();
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.profiles.addAll(profiles);
    }

    /**
     * Default constructor with no email, anull one will be provided
     * 
     * @param userId user ID
     * @param firstName first name
     * @param lastName last name
     * @param profiles profiles
     */
    public HabilePrincipal(String userId, String firstName, String lastName, List<String> profiles) {
        this(userId, firstName, lastName, null, profiles);
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getProfiles() {
        return profiles;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return this.userId;
    }
}