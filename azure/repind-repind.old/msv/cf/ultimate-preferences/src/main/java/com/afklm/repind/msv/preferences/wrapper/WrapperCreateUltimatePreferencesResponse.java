package com.afklm.repind.msv.preferences.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperCreateUltimatePreferencesResponse {
	
/*	public String gin;
	public String type;
	public String key;
	public String value;
*/
	public boolean retour;
	public String query;
}