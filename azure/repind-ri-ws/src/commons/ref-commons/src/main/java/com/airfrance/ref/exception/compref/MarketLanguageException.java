package com.airfrance.ref.exception.compref;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class MarketLanguageException extends JrafDomainRollbackException {

	private static final long serialVersionUID = 331336702280390793L;
	private String comPrefId;
	private String market;
	private String language;
	
	public MarketLanguageException(String msg, String comPrefId, String market, String language) {
		super(msg);
		this.comPrefId = comPrefId;
		this.market = market;
		this.language = language;
	}

	public MarketLanguageException(Throwable root, String comPrefId, String market, String language) {
		super(root);
		this.comPrefId = comPrefId;
		this.market = market;
		this.language = language;
	}

	public MarketLanguageException(String msg, Throwable root,String comPrefId, String market, String language) {
		super(msg, root);
		this.comPrefId = comPrefId;
		this.market = market;
		this.language = language;
	}

	public String getComPrefId() {
		return comPrefId;
	}

	public String getMarket() {
		return market;
	}

	public String getLanguage() {
		return language;
	}

	public void setComPrefId(String comPrefId) {
		this.comPrefId = comPrefId;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
