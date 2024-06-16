package com.afklm.repind.msv.inferred.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperInferredDataCreateResponse {
	
	public String gin;
	public String query;
	
}