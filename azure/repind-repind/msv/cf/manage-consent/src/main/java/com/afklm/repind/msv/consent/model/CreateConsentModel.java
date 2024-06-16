package com.afklm.repind.msv.consent.model;

import java.util.List;

public class CreateConsentModel {
	
    private String gin;
    private String type;
    private String application;
    private List<CreateConsentDataModel> data;
    
	public String getGin() {
		return gin;
	}
	public void setGin(String gin) {
		this.gin = gin;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<CreateConsentDataModel> getData() {
		return data;
	}
	public void setData(List<CreateConsentDataModel> data) {
		this.data = data;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	
}