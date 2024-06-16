package com.afklm.rigui.springsecurity.userdetailsservice;

import java.util.Collection;

import com.afklm.spring.security.habile.HabileUserDetails;
import org.springframework.security.core.GrantedAuthority;

import com.afklm.rigui.accesskeytransformer.UserProfilesAccessKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Authenticated User
 * @author m405991
 *
 */
@JsonIgnoreProperties({"password", "authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
public class AuthenticatedUserDTO extends HabileUserDetails {

	private static final long serialVersionUID = 1L;
	private UserProfilesAccessKey userProfilesAccessKey;
	private final String firstName;
	private final String lastName;

	/**
	 * Constructor
	 * @param username
	 * @param password
	 * @param authorities
	 * @param userProfilesAccessKey
	 * @param firstName
	 * @param lastName
	 */
	public AuthenticatedUserDTO(String username, String password,
			Collection<? extends GrantedAuthority> authorities, UserProfilesAccessKey userProfilesAccessKey, String firstName, String lastName) {
		super(username, lastName, firstName, authorities, password);
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
