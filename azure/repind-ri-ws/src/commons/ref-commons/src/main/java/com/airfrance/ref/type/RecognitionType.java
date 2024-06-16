package com.airfrance.ref.type;

public enum RecognitionType {

	FLE("FLE", "Individual was found by automatic matching process on firstname, lastname and email"),
	FLT("FLE", "Individual was found by automatic matching process on firstname, lastname and telecom"),
	EI ("EI", "Individual was found by automatic matching process on external identifier"),
	NIF ("NIF", "No matching found for the individual, new insertion"),
	MIF ("MIF", "Multiple individuals found, no insertion, no update"),
	FOR ("FOR", "Individual has been inserted by force"),
	DEF ("DEF", "Individual has been inserted by default");	// No value have been sent to WS

	
	private String code;

	private String libelle;

	RecognitionType(String code, String libelle) {
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
