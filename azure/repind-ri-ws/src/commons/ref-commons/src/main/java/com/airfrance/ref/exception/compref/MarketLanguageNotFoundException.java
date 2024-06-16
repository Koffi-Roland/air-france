package com.airfrance.ref.exception.compref;

public class MarketLanguageNotFoundException extends MarketLanguageException {

	private static final long serialVersionUID = -5811164515837102208L;
	
	public MarketLanguageNotFoundException(String msg, String gin, String market, String language) {
		super(msg, gin, market, language);		
	}

}
