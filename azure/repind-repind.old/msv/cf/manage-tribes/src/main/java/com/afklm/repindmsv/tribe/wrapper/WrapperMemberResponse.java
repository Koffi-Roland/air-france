package com.afklm.repindmsv.tribe.wrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperMemberResponse {
	
	public String tribeId;
	public String status;
	public List<String> roles;
	public String gin;
	
}