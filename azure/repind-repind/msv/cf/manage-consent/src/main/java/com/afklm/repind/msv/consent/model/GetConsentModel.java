package com.afklm.repind.msv.consent.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetConsentModel {
	
    private String type;
    private SignatureModel signature;
    private List<GetConsentDataModel> data;
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public SignatureModel getSignature() {
		return signature;
	}
	public void setSignature(SignatureModel signature) {
		this.signature = signature;
	}
	public List<GetConsentDataModel> getData() {
		return data;
	}
	public void setData(List<GetConsentDataModel> data) {
		this.data = data;
	}
    
    

}