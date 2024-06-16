package com.afklm.repind.msv.mapping.services.builder;

public enum WebService {
	
	CREATE_OR_UPDATE_INDIVIDUAL("business.400.001."),
	SEARCH_INDIVIDUAL_BY_MULTICRITERIA("business.400.002."),
	CREATE_OR_UPDATE_CONTRACT("business.400.003.");
	
	private final String codePrefix;	
	
	WebService(String codePrefix) {
		this.codePrefix = codePrefix;
	}
	
	public String getCodePrefix() {
		return this.codePrefix;
	}
}
