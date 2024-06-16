package com.airfrance.repind.entity.reference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(RefMarketCountryLanguageId.class)
@Table(name="REF_MARKET_COUNTRY_LANGUAGE")
public class RefMarketCountryLanguage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7497022543449417739L;
	
	@Id
	@Column(name="SCODE_MARKET", nullable=false)
    private String codeMarket;
	
	@Id
	@Column(name="SCODE_LANGUAGE_NO_ISO", nullable=false)
    private String langNotIso;
	
	@Id
	@Column(name="SCODE_CONTEXT", nullable=false)
	private String context;
	
	@Column(name="SCODE_LANGUAGE_ISO", nullable=false)
	private String langIso;

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

	public String getLangIso() {
		return langIso;
	}

	public void setLangIso(String langIso) {
		this.langIso = langIso;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	
}
