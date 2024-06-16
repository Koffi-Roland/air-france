package com.afklm.repind.msv.consent.model;

import java.util.Date;

public class UpdateConsentModel {

    private Long id;
    private String gin;
    private String isConsent;
    private Date dateConsent;
    private String application;
    
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGin() {
		return gin;
	}
	public void setGin(String gin) {
		this.gin = gin;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	
}