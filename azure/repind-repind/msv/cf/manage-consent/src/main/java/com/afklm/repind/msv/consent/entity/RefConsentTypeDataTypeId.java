package com.afklm.repind.msv.consent.entity;

import jakarta.persistence.*;

import java.io.Serializable;


@Embeddable
public class RefConsentTypeDataTypeId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="SCONSENT_TYPE")
    private String consentType;

    @Column(name="SCONSENT_DATA_TYPE")
    private String consentDataType;

	public String getConsentType() {
		return consentType;
	}

	public void setConsentType(String consentType) {
		this.consentType = consentType;
	}

	public String getConsentDataType() {
		return consentDataType;
	}

	public void setConsentDataType(String consentDataType) {
		this.consentDataType = consentDataType;
	}
    
}
