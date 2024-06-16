package com.afklm.repind.msv.preferences.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUltimatePreferencesModel {
	
    private String preferenceId;    
    private String subPreferenceId;
    private String value;
    
    // DEPRECATED 
    private String gin;
    private String type;
    private Long link;
    private SignatureModel signature;
    private List<UltimatePreferencesModel> data;
    
        
	public String getPreferenceId() {
		return preferenceId;
	}
	public void setPreferenceId(String preferenceId) {
		this.preferenceId = preferenceId;
	}

	public String getSubPreferenceId() {
		return subPreferenceId;
	}
	public void setSubPreferenceId(String subPreferenceId) {
		this.subPreferenceId = subPreferenceId;
	}
	
	public Object getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
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
	public Long getLink() {
		return link;
	}
	public void setLink(Long link) {
		this.link = link;
	}
	public SignatureModel getSignature() {
		return signature;
	}
	public void setSignature(SignatureModel signature) {
		this.signature = signature;
	}
	public List<UltimatePreferencesModel> getData() {
		return data;
	}
	public void setData(List<UltimatePreferencesModel> data) {
		this.data = data;
	}
    
    

}
