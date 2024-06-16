package com.afklm.repindmsv.tribe.wrapper;

import java.util.List;

import com.afklm.repindmsv.tribe.model.TribeModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperTribesResponse {
	
	public List<TribeModel> tribes;
	
}