package com.afklm.cati.common.spring.rest.resources;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.hateoas.RepresentationModel;

@JsonInclude(Include.NON_NULL)
public class CatiCommonResource extends RepresentationModel<CatiCommonResource> {

	private Long techId;

	public Long getTechId() {
		return techId;
	}

	public void setTechId(Long techId) {
		this.techId = techId;
	}

}
