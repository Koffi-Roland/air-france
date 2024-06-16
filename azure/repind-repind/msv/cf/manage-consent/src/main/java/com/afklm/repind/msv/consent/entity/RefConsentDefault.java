package com.afklm.repind.msv.consent.entity;

import jakarta.persistence.*;

import java.io.Serializable;



@Entity


@Table(name = "REF_CONSENT_DEFAULT")
public class RefConsentDefault implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
	private RefConsentTypeDataTypeId refConsentDefaultId;

	@Column(name = "SDEFAULT_CONSENT_VALUE")
	private String defaultConsentValue;

	/**
	 * @return the refConsentDefaultId
	 */
	public RefConsentTypeDataTypeId getRefConsentDefaultId() {
		return refConsentDefaultId;
	}

	/**
	 * @param refConsentDefaultId
	 *            the refConsentDefaultId to set
	 */
	public void setRefConsentDefaultId(RefConsentTypeDataTypeId refConsentDefaultId) {
		this.refConsentDefaultId = refConsentDefaultId;
	}

	/**
	 * @return the defaultConsentValue
	 */
	public String getDefaultConsentValue() {
		return defaultConsentValue;
	}

	/**
	 * @param defaultConsentValue
	 *            the defaultConsentValue to set
	 */
	public void setDefaultConsentValue(String defaultConsentValue) {
		this.defaultConsentValue = defaultConsentValue;
	}
	
}
