package com.airfrance.ref.type;

public enum ForgetEnum {

	ASKED("A", "FORGET_ASKED"),
	CONFIRMED("C", "FORGET_CONFIRMED"),		
	FORCED("F", "FORGET_FORCED"),
	PROCESSED("P", "FORGET_PROCESSED"),
	STATUS("F", "Forgotten");
	
	private String code;
	
	private String libelle;

	ForgetEnum(String code, String libelle) {
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