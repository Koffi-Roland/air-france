package com.afklm.cati.common.spring.rest.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ErrorListResource {

	private List<ValidationErrorResource> validationErrorResources;
	private HttpStatus status;
	
	public ErrorListResource( List<ValidationErrorResource> validationErrorResources, HttpStatus status) {
		super();
		this.validationErrorResources = validationErrorResources;
		this.status = status;
	}
	
	public List<ValidationErrorResource> getValidationErrorResources() {
		return validationErrorResources;
	}

	public void setValidationErrorResources(
			List<ValidationErrorResource> validationErrorResources) {
		this.validationErrorResources = validationErrorResources;
	}

	@JsonIgnore
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
	/**
	 * Get status code
	 */
	public int getStatusCode() {
		return status.value();
	}
	
}
