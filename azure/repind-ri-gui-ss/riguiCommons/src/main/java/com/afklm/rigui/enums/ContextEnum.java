package com.afklm.rigui.enums;

public enum ContextEnum {

	// N(null, "Individual"),		// Default individual
	I("I", "INDIVIDUAL"),			// Store individual by default
	T("T", "TRAVELER"),
	W("W", "PROSPECT"),
	E("E", "EXTERNAL"),
	S("S", "SOCIAL"),
	C("C", "CLAIM"),
	R("R", "FB_ENROLMENT"),
	H("H", "B2C_HOME_PAGE"),
	B("B", "B2C_BOOKING_PROCESS"),
	D("D", "B2C_HOME_PAGE_RECONCILIATION"),
	CSM("CSM", "BATCH_SCM_QVI"),
	FF("FF", "FORGET_FORCED"),
	FA("FA", "FORGET_ASKED"),
	FC("FC", "FORGET_CONFIRM");
	
	private String code;
	
	private String libelle;

	ContextEnum(String code, String libelle) {
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
	
	public static ContextEnum getEnumFromLibelle(String context) {
		if(FC.getLibelle().equals(context)){
			return FC;
		} else if(FA.getLibelle().equals(context)){
			return FA;
		} else if(FF.getLibelle().equals(context)){
			return FF;
		} else if (T.getLibelle().equals(context)) {
			return T;
		} else if (E.getLibelle().equals(context)) {
			return E;
		} else if (S.getLibelle().equals(context)) {
			return S;
		} else if (W.getLibelle().equals(context)) {
			return W;
		} else {
			return I;
		}
	}
	
}
