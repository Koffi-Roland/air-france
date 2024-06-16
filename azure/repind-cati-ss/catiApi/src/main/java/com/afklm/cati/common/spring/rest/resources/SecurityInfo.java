package com.afklm.cati.common.spring.rest.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SecurityInfo {

	private String firstname;
	private String lastname;
	private List<String> roles;
	private List<String> permissions;

}
