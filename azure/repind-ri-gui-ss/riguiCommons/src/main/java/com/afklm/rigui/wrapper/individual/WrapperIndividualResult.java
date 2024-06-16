package com.afklm.rigui.wrapper.individual;

import java.util.List;

import com.afklm.rigui.model.individual.ModelIndividualResult;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WrapperIndividualResult {

	public List<ModelIndividualResult> individuals;
	
}
