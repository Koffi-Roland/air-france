package com.afklm.repind.msv.inferred.wrapper;

import java.util.List;

import com.afklm.repind.msv.inferred.model.GetInferredDataModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperProvideInferredDataResponse {
	
	public List<GetInferredDataModel> inferredData;
	public String query;
	
}