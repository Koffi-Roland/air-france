package com.afklm.repind.msv.handicap.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperHandicapUpdateResponse {
	
	public String gin;
	public String query;
	
}