package com.afklm.rigui.spring.rest.resources;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Objects;

/**
 * Common Resource
 * @author m405991
 *
 */
@JsonInclude(Include.NON_NULL)
public class RiguiCommonResource extends RepresentationModel<TrackingResource> {

	private Long techId;

	public Long getTechId() {
		return techId;
	}

	public void setTechId(Long techId) {
		this.techId = techId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		RiguiCommonResource that = (RiguiCommonResource) o;
		return Objects.equals(techId, that.techId);
	}
}
