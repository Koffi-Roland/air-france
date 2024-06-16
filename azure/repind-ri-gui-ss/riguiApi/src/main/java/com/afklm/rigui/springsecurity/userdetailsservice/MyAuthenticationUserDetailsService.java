package com.afklm.rigui.springsecurity.userdetailsservice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.afklm.rigui.accesskeytransformer.ProfileAccessKey;
import com.afklm.rigui.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.rigui.spring.rest.resources.HabileResource;
import com.afklm.spring.security.habile.HabileUserDetails;
import com.afklm.spring.security.habile.userdetails.CustomUserDetailsService;


/**
 * A custom authentication user details service.
 *
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
		return customUserDetails;
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

			UserProfilesAccessKey profiles =  authentication.getUserProfilesAccessKey();
			List<ProfileAccessKey> listKeys = profiles.getAccessKeyConfiguration();
			listKeys
					.stream()
					.map(ProfileAccessKey::getProfile)
					.forEach(auth -> {
						user.getPermissions().add(auth);
					});

/*            if (authentication.getUserProfilesAccessKey() != null) {

                user.getPermissions().add("P_RIGUI_SUPERADMIN");

            }*/

			return user;
		} else {
			return userDetails;
		}
	}

}
