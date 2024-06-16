package com.afklm.spring.security.habile;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

import static com.afklm.spring.security.habile.GlobalConfiguration.NO_CREDENTIAL;

/**
 * AF/KLM Habile/Ping implementation/representation of the connected user.
 *
 * @author TECC
 */
public class HabileUserDetails extends User {
    static final long serialVersionUID = 1;
    private String lastname;
    private String firstname;
    private String email;
    private String idToken;

    /**
     * Constructor
     * 
     * @param username the username, as mXXXXXX
     * @param authorities authorities granted to the user
     * @param lastname the user's last name
     * @param firstname the user's first name
     * @param idToken the user's idToken in case of new Ping process
     */
    public HabileUserDetails(
            String username,
            String lastname,
            String firstname,
            Collection<? extends GrantedAuthority> authorities,
            String idToken) {

        super(username, NO_CREDENTIAL, true, true, true, true, authorities);
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = "";
        this.idToken = idToken;
    }

    /**
     * Constructor
     * 
     * @param username the username, as mXXXXXX
     * @param authorities authorities granted to the user
     * @param lastname the user's last name
     * @param email the user's email address
     * @param firstname the user's first name
     * @param idToken the user's idToken in case of new Ping process
     */
    public HabileUserDetails(
            String username,
            String lastname,
            String firstname,
            String email,
            Collection<? extends GrantedAuthority> authorities,
            String idToken) {

        this(username, lastname, firstname, authorities, idToken);
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
