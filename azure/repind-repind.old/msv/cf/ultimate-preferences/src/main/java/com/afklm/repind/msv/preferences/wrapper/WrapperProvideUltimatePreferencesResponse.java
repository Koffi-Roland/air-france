package com.afklm.repind.msv.preferences.wrapper;

import com.afklm.repind.msv.preferences.model.GetUltimatePreferencesModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperProvideUltimatePreferencesResponse {
	
	public List<GetUltimatePreferencesModel> extendedPreferences;
	public boolean retour;
	public String query;
	
}
