package com.afklm.repind.msv.search.gin.by.contract.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class WrapperSearchGinByContractResponse {
	
	@ApiModelProperty(required = true) private List<String> gins = new ArrayList<>();

	public void addGins(Collection<String> iGins){
		if(iGins != null){
			gins.addAll(iGins);
		}
	}
}
