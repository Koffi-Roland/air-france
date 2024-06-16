package com.afklm.cati.common.spring.rest.exceptions;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ErrorResource {

	private String label;
	private String detailMessage;
	private HttpStatus status;
	
	public ErrorResource(String label, String detailMessage, HttpStatus status) {
		super();
		this.label = label;
		this.detailMessage = detailMessage;
		this.status = status;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDetailMessage() {
		return detailMessage;
	}
	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
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
