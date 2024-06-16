package com.airfrance.repind.dao.reference;

public interface RefMarketCountryLanguageRepositoryCustom {
	
	String findCodeIsoByMarketLangNotIsoContext(String market, String nonIsoLang, String context);
	
}
