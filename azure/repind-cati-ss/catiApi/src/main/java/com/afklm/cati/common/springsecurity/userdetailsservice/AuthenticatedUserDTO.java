package com.afklm.cati.common.springsecurity.userdetailsservice;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;

public class AuthenticatedUserDTO extends User {

    private static final long serialVersionUID = 1L;
    private UserProfilesAccessKey userProfilesAccessKey;
    private String firstName;
    private String lastName;

    public AuthenticatedUserDTO(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            UserProfilesAccessKey userProfilesAccessKey,
            String firstName,
            String lastName) {
        super(username, password, authorities);
        this.userProfilesAccessKey = userProfilesAccessKey;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserProfilesAccessKey getUserProfilesAccessKey() {
        return userProfilesAccessKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
