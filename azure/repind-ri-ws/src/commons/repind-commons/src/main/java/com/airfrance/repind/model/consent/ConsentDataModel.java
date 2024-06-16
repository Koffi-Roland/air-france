package com.airfrance.repind.model.consent;

import java.util.Date;

public class ConsentDataModel {
	private Date dateConsent;
	private String isConsent;
	private String type;

	public ConsentDataModel() {
		super();
	}

	public Date getDateConsent() {
		return dateConsent;
	}

	public void setDateConsent(Date dateConsent) {
		this.dateConsent = dateConsent;
	}

	public String getIsConsent() {
		return isConsent;
	}

	public void setIsConsent(String isConsent) {
		this.isConsent = isConsent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
