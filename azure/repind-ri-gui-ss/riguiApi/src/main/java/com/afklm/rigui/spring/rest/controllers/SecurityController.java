package com.afklm.rigui.spring.rest.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

import com.afklm.rigui.accesskeytransformer.AccessKey;
import com.afklm.rigui.accesskeytransformer.ProfileAccessKey;
import com.afklm.rigui.accesskeytransformer.UserProfilesAccessKey;
import com.afklm.rigui.springsecurity.userdetailsservice.AuthenticatedUserDTO;
import com.afklm.rigui.wrapper.security.WrapperSecurityInfo;

/**
 * @author AF-KLM
 *
 */
@RestController
public class SecurityController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);

	final UserProfilesAccessKey accessKeyConfiguration;

	public SecurityController(UserProfilesAccessKey accessKeyConfiguration) {
		this.accessKeyConfiguration = accessKeyConfiguration;
	}

	public UserProfilesAccessKey securitiesGet(@AuthenticationPrincipal AuthenticatedUserDTO authenticatedUserDTO) {
		List<String> profiles = authenticatedUserDTO.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority) // extraction of the name of the authority
				.filter(authName -> authName.startsWith("ROLE_")) // // a profile is prefixed by ROLE_
				.map(authName -> authName.substring(5)) // removal of the ROLE_ prefix
				.collect(Collectors.toList());

		UserProfilesAccessKey res = new UserProfilesAccessKey();

		res.setAccessKeyConfiguration(
				accessKeyConfiguration.getAccessKeyConfiguration()
						.stream()
						.filter(pak -> profiles.contains(pak.getProfile()))
						.collect(Collectors.toList()));

		return res;
	}
	
	private WrapperSecurityInfo extractSecurityInfo(AuthenticatedUserDTO user) {
		if (user == null) return null;
		WrapperSecurityInfo wrapper = new WrapperSecurityInfo();
		UserProfilesAccessKey userProfilesAccessKey = user.getUserProfilesAccessKey();
		List<String> roles = new ArrayList<>();
		List<String> permissions = new ArrayList<>();
		for (ProfileAccessKey profileAccessKey : userProfilesAccessKey.getAccessKeyConfiguration()) {
			permissions.add(profileAccessKey.getProfile());
			int accessKeyLstLength = profileAccessKey.getAccessKeyLst().length;
			for (int i = 0 ; i < accessKeyLstLength ; i++) {
				AccessKey item = profileAccessKey.getAccessKeyLst()[i];
				roles.add(item.getAccessKey());
			}
		}
		wrapper.roles = roles;
		wrapper.permissions = permissions;
		return wrapper;
	}
	
	
}
