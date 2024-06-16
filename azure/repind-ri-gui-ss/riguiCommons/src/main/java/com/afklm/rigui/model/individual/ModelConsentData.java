package com.afklm.rigui.model.individual;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelConsentData {

	public Long id;
	public String type;
	public String isConsent;
	public String dateConsent;
	private ModelSignature signature;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the dateConsent
	 */
	public String getDateConsent() {
		return dateConsent;
	}

	/**
	 * @param dateConsent the dateConsent to set
	 */
	public void setDateConsent(String dateConsent) {
		this.dateConsent = dateConsent;
	}

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
	 * @return the consent
	 */
	public String getIsConsent() {
		return isConsent;
	}
	
	/**
	 * @param consent
	 *            the consent to set
	 */
	public void setIsConsent(String isConsent) {
		this.isConsent = isConsent;
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
}