package com.afklm.rigui.model.individual;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelConsent {
	
    public String type;
	private ModelSignature signature;
	public Set<ModelConsentData> data;
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the signature
	 */
	public ModelSignature getSignature() {
		return signature;
	}
	
	/**
	 * @param signature
	 *            the signature to set
	 */
	public void setSignature(ModelSignature signature) {
		this.signature = signature;
	}
	
	/**
	 * @return the data
	 */
	public Set<ModelConsentData> getData() {
		return data;
	}
	
	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Set<ModelConsentData> data) {
		this.data = data;
	}
}