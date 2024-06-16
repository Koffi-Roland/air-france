package com.afklm.repind.msv.mapping.entity;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name = "REF_MARKET_COUNTRY_LANGUAGE")
public class RefMarketCountryLanguage implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RefMarketCountryLanguageId refMarketCountryLanguageId;

	/**
	 * @return the refMarketCountryLanguageId
	 */
	public RefMarketCountryLanguageId getRefMarketCountryLanguageId() {
		return refMarketCountryLanguageId;
	}

	/**
	 * @param refMarketCountryLanguageId
	 *            the refMarketCountryLanguageId to set
	 */
	public void setRefMarketCountryLanguageId(RefMarketCountryLanguageId refMarketCountryLanguageId) {
		this.refMarketCountryLanguageId = refMarketCountryLanguageId;
	}

}
