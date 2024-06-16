package com.afklm.repind.msv.consent.model;

import java.util.Date;

public class CreateConsentDataModel {

	private String type;
	private String isConsent;
	private Date dateConsent;
	
	public CreateConsentDataModel() {
		super();
	}

	public CreateConsentDataModel(String type, String isConsent, Date dateConsent) {
		super();
		this.type = type;
		this.isConsent = isConsent;
		this.dateConsent = dateConsent;
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
	public Date getDateConsent() {
		return dateConsent;
	}
	public void setDateConsent(Date dateConsent) {
		this.dateConsent = dateConsent;
	}
    
}