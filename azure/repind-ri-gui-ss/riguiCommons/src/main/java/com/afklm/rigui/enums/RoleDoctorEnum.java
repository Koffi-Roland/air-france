package com.afklm.rigui.enums;

public enum RoleDoctorEnum {

	DR("DR", "ProductType Role Doctor"),
	D("D", "Doctor contract type"),
	C("C", "Doctor Status");

	private String code;

	private String libelle;

	RoleDoctorEnum(String code, String libelle) {
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
