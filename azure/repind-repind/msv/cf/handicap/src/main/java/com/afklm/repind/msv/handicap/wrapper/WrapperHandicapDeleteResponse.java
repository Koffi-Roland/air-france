package com.afklm.repind.msv.handicap.wrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperHandicapDeleteResponse {
	
	public Long id;
	public List<String> keys;
}