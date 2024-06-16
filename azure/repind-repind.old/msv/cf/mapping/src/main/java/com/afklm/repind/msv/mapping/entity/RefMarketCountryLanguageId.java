package com.afklm.repind.msv.mapping.entity;

import jakarta.persistence.*;

import java.io.Serializable;


@Embeddable
public class RefMarketCountryLanguageId implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * scodeMarket
	 */
	@Column(name = "SCODE_MARKET", length = 5, nullable = false)
	private String scodeMarket;

	/**
	 * scodeLanguageNoISO
	 */
	@Column(name = "SCODE_LANGUAGE_NO_ISO", length = 5, nullable = false)
	private String scodeLanguageNoISO;

	/**
	 * scodeLanguageISO
	 */
	@Column(name = "SCODE_LANGUAGE_ISO", length = 5, nullable = false)
	private String scodeLanguageISO;

	/**
	 * context
	 */
	@Column(name = "SCODE_CONTEXT", length = 10, nullable = false)
	private String scodeContext;

	/**
	 * @return the scodeMarket
	 */
	public String getScodeMarket() {
		return scodeMarket;
	}

	/**
	 * @param scodeMarket
	 *            the scodeMarket to set
	 */
	public void setScodeMarket(String scodeMarket) {
		this.scodeMarket = scodeMarket;
	}

	/**
	 * @return the scodeLanguageNOISO
	 */
	public String getScodeLanguageNOISO() {
		return scodeLanguageNoISO;
	}

	/**
	 * @param scodeLanguageNOISO
	 *            the scodeLanguageNOISO to set
	 */
	public void setScodeLanguageNOISO(String scodeLanguageNOISO) {
		this.scodeLanguageNoISO = scodeLanguageNOISO;
	}

	/**
	 * @return the scodeLanguageISO
	 */
	public String getScodeLanguageISO() {
		return scodeLanguageISO;
	}

	/**
	 * @param scodeLanguageISO
	 *            the scodeLanguageISO to set
	 */
	public void setScodeLanguageISO(String scodeLanguageISO) {
		this.scodeLanguageISO = scodeLanguageISO;
	}

	/**
	 * @return the scodeContext
	 */
	public String getScodeContext() {
		return scodeContext;
	}

	/**
	 * @param scodeContext
	 *            the scodeContext to set
	 */
	public void setScodeContext(String scodeContext) {
		this.scodeContext = scodeContext;
	}

}
