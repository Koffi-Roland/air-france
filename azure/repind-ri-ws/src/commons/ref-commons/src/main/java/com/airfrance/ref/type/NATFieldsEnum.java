package com.airfrance.ref.type;

/**
 * N.A.T Fields 
 * 
 */
public enum NATFieldsEnum {

	AIRFRANCE("A"), NONE("N"), ALL("T");

	private String value;

	NATFieldsEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
