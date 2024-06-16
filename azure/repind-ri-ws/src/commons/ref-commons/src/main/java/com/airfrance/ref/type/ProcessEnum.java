package com.airfrance.ref.type;

public enum ProcessEnum {

	// N(null, "Individual"),		// Default individual
	I("I", "INDIVIDUAL"),		// Store individual by default
	T("T", "TRAVELER"),
	W("W", "PROSPECT"),
	A("A", "ALERT"),
	E("E", "EXTERNAL IDENTIFIER"),
	// 'K' can be a Unacccompanied minor or a person accompanying minor 
	// (see JIRA ticket REPIND-907)
	K("K", "KID_SOLO"),
	H("H", "HIDDEN"), 	// For crisis event !
	C("C", "CALLER");	// REPIND-1808 : Create a Caller in RI database
	
	private String code;
	
	private String libelle;

	ProcessEnum(String code, String libelle) {
		this.setCode(code);
		this.setLibelle(libelle);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	
	
}
