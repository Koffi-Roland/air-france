package com.afklm.repind.msv.consent.wrapper;

import java.util.List;

import com.afklm.repind.msv.consent.model.GetConsentModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperGetConsentResponse {
	
	public String gin;
	public List<GetConsentModel> consent;
	
}