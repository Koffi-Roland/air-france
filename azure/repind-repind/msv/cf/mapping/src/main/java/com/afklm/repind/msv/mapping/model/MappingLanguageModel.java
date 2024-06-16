package com.afklm.repind.msv.mapping.model;

public class MappingLanguageModel {

	private String market;
	private String codeLanguageNoISO;
	private String codeLanguageISO;
	private String context;

	/**
	 * @return the market
	 */
	public String getMarket() {
		return market;
	}

	/**
	 * @param market
	 *            the market to set
	 */
	public void setMarket(String market) {
		this.market = market;
	}

	/**
	 * @return the codeLanguageNoISO
	 */
	public String getCodeLanguageNoISO() {
		return codeLanguageNoISO;
	}

	/**
	 * @param codeLanguageNoISO
	 *            the codeLanguageNoISO to set
	 */
	public void setCodeLanguageNoISO(String codeLanguageNoISO) {
		this.codeLanguageNoISO = codeLanguageNoISO;
	}

	/**
	 * @return the codeLanguageISO
	 */
	public String getCodeLanguageISO() {
		return codeLanguageISO;
	}

	/**
	 * @param codeLanguageISO
	 *            the codeLanguageISO to set
	 */
	public void setCodeLanguageISO(String codeLanguageISO) {
		this.codeLanguageISO = codeLanguageISO;
	}

	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

}