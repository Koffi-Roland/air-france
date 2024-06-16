package com.airfrance.repind.entity.reference;

import java.io.Serializable;

public class RefMarketCountryLanguageId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String codeMarket;
	
    private String langNotIso;
    
	private String context;

	public String getCodeMarket() {
		return codeMarket;
	}

	public void setCodeMarket(String codeMarket) {
		this.codeMarket = codeMarket;
	}

	public String getLangNotIso() {
		return langNotIso;
	}

	public void setLangNotIso(String langNotIso) {
		this.langNotIso = langNotIso;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
}
