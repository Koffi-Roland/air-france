package com.airfrance.ref.type;

public enum PreferenceTypeEnum {
	TRAVEL_DOC         ("TDC"),
	TRAVEL_ADR         ("TAC"),
	TRAVEL_COMPANION   ("TCC"),
	TRAVEL_PREF        ("TPC"),
	APIS_DATA          ("ADC"),
	TRAVEL_C_PERS_INFO ("TCP"),
	EMERG_CTC          ("ECC"),
	TUTOR_UM           ("TUM"),
	HANDICAP           ("HDC"),
	PERSONAL_INFO      ("PIC");

	private String key = "";

	PreferenceTypeEnum(String key){
		this.key = key;
	}
	
	public static PreferenceTypeEnum fromString(String value) {
	    for (PreferenceTypeEnum b : PreferenceTypeEnum.values()) {
	      if (b.key.equalsIgnoreCase(value)) {
	        return b;
	      }
	    }
	    return null;
	  }

	public String toString(){
		return key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}