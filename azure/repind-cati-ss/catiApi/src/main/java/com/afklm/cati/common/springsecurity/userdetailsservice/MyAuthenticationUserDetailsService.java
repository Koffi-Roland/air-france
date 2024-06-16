package com.afklm.cati.common.springsecurity.userdetailsservice;

import com.afklm.cati.common.accesskeytransformer.ProfileAccessKey;
import com.afklm.cati.common.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.userdetails.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.afklm.cati.common.spring.rest.resources.HabileResource;

import java.util.List;
import java.util.stream.Collectors;


/**
 * A custom authentication user details service.
 */
@Component
public class MyAuthenticationUserDetailsService implements CustomUserDetailsService {

    public static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    @Lazy
    private UserProfilesAccessKey accessKeyConfiguration;

    @Override
    public UserDetails enrichUserDetails(HabileUserDetails userDetails) {

        List<String> profiles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority) // extraction of the name of the authority
                .filter(authName -> authName.startsWith("ROLE_")) // // a profile is prefixed by ROLE_
                .map(authName -> authName.substring(5)) // removal of the ROLE_ prefix
                .collect(Collectors.toList());

        UserProfilesAccessKey accessKeys = new UserProfilesAccessKey();
        accessKeys.setAccessKeyConfiguration(
                this.accessKeyConfiguration.getAccessKeyConfiguration()
                        .stream()
                        .filter(pak -> profiles.contains(pak.getProfile()))
                        .collect(Collectors.toList()));

        AuthenticatedUserDTO customUserDetails = new AuthenticatedUserDTO(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities(),
                accessKeys,
                userDetails.getFirstname(),
                userDetails.getLastname());
        return  customUserDetails;
    }

    @Override
    public Object render(UserDetails userDetails) {
        if (userDetails instanceof AuthenticatedUserDTO) {
            AuthenticatedUserDTO authentication = (AuthenticatedUserDTO) userDetails;
            HabileResource user = new HabileResource();
            user.setUserName(authentication.getUsername());
            user.setLastName(authentication.getLastName());
            user.setFirstName(authentication.getFirstName());
            authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .forEach(auth -> {
                        user.getRoles().add(auth);
                    });

            UserProfilesAccessKey profiles = authentication.getUserProfilesAccessKey();
            List<ProfileAccessKey> listKeys = profiles.getAccessKeyConfiguration();
            listKeys
                    .stream()
                    .map(ProfileAccessKey::getProfile)
                    .forEach(auth -> {
                        user.getPermissions().add(auth);
                    });
            /*
            if (authentication.getUserProfilesAccessKey() != null) {
                user.getPermissions().add("P_CATI_SUPERADMIN");
            }
            */
            return user;
        } else {
            return userDetails;
        }
    }

}
