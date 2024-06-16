package com.afklm.repind.msv.consent.model;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetConsentDataModel {

	private Long id;
	private String type;
	private String isConsent;
	private String dateConsent;
    private SignatureModel signature;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsConsent() {
		return isConsent;
	}
	public void setIsConsent(String isConsent) {
		this.isConsent = isConsent;
	}
	public String getDateConsent() {
		return dateConsent;
	}
	public void setDateConsent(String dateConsent) {
		this.dateConsent = dateConsent;
	}
	public SignatureModel getSignature() {
		return signature;
	}
	public void setSignature(SignatureModel signature) {
		this.signature = signature;
	}
    
    
}