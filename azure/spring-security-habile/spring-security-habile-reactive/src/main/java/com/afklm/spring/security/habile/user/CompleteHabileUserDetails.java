package com.afklm.spring.security.habile.user;

import com.afklm.spring.security.habile.GlobalConfiguration;
import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.utils.LazyCalculation;
import com.afklm.spring.security.habile.utils.StreamHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class representing an authenticated Habile user whom roles have been matched with the application using SS4H Reactive.
 * Will be stored as a {@link org.springframework.security.core.annotation.AuthenticationPrincipal}
 */
public class CompleteHabileUserDetails extends HabileUserDetails {
    static final long serialVersionUID = 1;
    private List<GrantedAuthority> allAuthorities;
    private LazyCalculation<List<GrantedAuthority>> roles;

    /**
     * Used to construct a CompleteHabileUserDetails instance
     *
     * @param username    the username provided in the request
     * @param lastname    the last name retrieved from the authentication service
     * @param firstname   the first name retrieved from the authentication service
     * @param email       the email address retrieved from the authentication service
     * @param authorities the authorities retrieved from the authentication service
     */
    public CompleteHabileUserDetails(String username, String lastname, String firstname, String email, Collection<? extends GrantedAuthority> authorities, String token) {
        super(username, lastname, firstname, email, authorities, token);
        roles = new LazyCalculation<>();
        allAuthorities = new ArrayList<>();
    }

    /**
     * Builder to populate a {@link CompleteHabileUserDetails} based on a {@link HabileUserDetails}
     *
     * @param userDetails used to populate
     * @return new populated instance of {@link CompleteHabileUserDetails}
     */
    public static CompleteHabileUserDetails from(HabileUserDetails userDetails) {
        return new CompleteHabileUserDetails(
                userDetails.getUsername(),
                userDetails.getLastname(),
                userDetails.getFirstname(),
                userDetails.getEmail(),
                userDetails.getAuthorities(),
                userDetails.getIdToken());
    }

    /**
     * Used to provide the expanded authorities
     *
     * @param authorities the list of expanded authorities
     * @return the modified instance of {@link CompleteHabileUserDetails}
     */
    public CompleteHabileUserDetails withAllAuthorities(List<? extends GrantedAuthority> authorities) {
        allAuthorities = StreamHelper.cast(authorities, GrantedAuthority.class);
        return this;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return allAuthorities;
    }

    public Collection<GrantedAuthority> getRoles() {
        return roles.getOrCompute(() ->
                getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .filter(authority -> authority.startsWith(GlobalConfiguration.ROLE_PREFIX))
                        .map(role -> role.substring(GlobalConfiguration.ROLE_PREFIX.length()))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }

    public Collection<? extends GrantedAuthority> getBaseRoles() {
        return super.getAuthorities();
    }

    public Collection<GrantedAuthority> getPermissions() {
        return StreamHelper.applyTo(allAuthorities, elements -> elements
                .filter(o -> !o.getAuthority().startsWith(GlobalConfiguration.ROLE_PREFIX)));
    }

}
