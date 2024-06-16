package com.afklm.repind.msv.preferences.services.builder;

import java.util.Set;

public class ModelPreference {
	
	private Long preferenceId;
    private String gin;
    private String type;
    private Integer link;
    private ModelSignature signature;
    private Set<ModelPreferenceData> preferenceData;
    
	public Long getPreferenceId() {
		return preferenceId;
	}
	public void setPreferenceId(Long preferenceId) {
		this.preferenceId = preferenceId;
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
	public Integer getLink() {
		return link;
	}
	public void setLink(Integer link) {
		this.link = link;
	}
	public ModelSignature getSignature() {
		return signature;
	}
	public void setSignature(ModelSignature signature) {
		this.signature = signature;
	}
	public Set<ModelPreferenceData> getPreferenceData() {
		return preferenceData;
	}
	public void setPreferenceData(Set<ModelPreferenceData> preferenceData) {
		this.preferenceData = preferenceData;
	}
}
