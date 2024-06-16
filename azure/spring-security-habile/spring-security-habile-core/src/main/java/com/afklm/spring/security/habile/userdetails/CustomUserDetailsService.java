package com.afklm.spring.security.habile.userdetails;

import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.web.UserResource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static com.afklm.spring.security.habile.GlobalConfiguration.ROLE_PREFIX;

/**
 * Interface to be implemented by a bean in order to allow customization of
 * {@link HabileUserDetails}
 * 
 * @author m408461
 *
 */
public interface CustomUserDetailsService {

    /**
     * Method called during user authentication before setting the Spring
     * security principal.
     * 
     * @param userDetails
     * @return
     */
    default public UserDetails enrichUserDetails(HabileUserDetails userDetails) {
        return userDetails;
    }

    /**
     * Method called by our /me endpoint in order to provide a representation
     * of the Principal when needed by a frontend.
     * 
     * @param userDetails
     * @return
     */
    default public Object render(UserDetails userDetails) {
        if (userDetails instanceof HabileUserDetails) {
            HabileUserDetails authentication = (HabileUserDetails) userDetails;
            UserResource user = new UserResource();
            user.setUsername(authentication.getUsername());
            user.setLastname(authentication.getLastname());
            user.setFirstname(authentication.getFirstname());
            user.setEmail(authentication.getEmail());
            user.setIdToken(authentication.getIdToken());
            authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .forEach(auth -> {
                    if (auth.startsWith(ROLE_PREFIX)) {
                        user.getRoles().add(auth.substring(5));
                    } else {
                        user.getPermissions().add(auth);
                    }
                });
            return user;
        } else {
            return userDetails;
        }
    }
}
