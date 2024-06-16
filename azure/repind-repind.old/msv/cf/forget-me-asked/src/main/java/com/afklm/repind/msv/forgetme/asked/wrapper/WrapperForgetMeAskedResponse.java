package com.afklm.repind.msv.forgetme.asked.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WrapperForgetMeAskedResponse {
	
	@ApiModelProperty(required = true) private List<WrapperForgottenIndividual> askedIndividuals = new ArrayList<>();

	public WrapperForgetMeAskedResponse withAskedIndividuals(Collection<WrapperForgottenIndividual> WrapperForgottenIndividual){
		if(WrapperForgottenIndividual !=null){
			this.addAskedIndividuals(WrapperForgottenIndividual);
		}
		return this;
	}

	public void addAskedIndividuals(Collection<WrapperForgottenIndividual> iForgottenIndividuals){
		if(iForgottenIndividuals != null){
			askedIndividuals.addAll(iForgottenIndividuals);
		}
	}
}
