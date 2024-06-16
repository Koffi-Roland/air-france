package com.airfrance.ref.type;

public enum SignatureTypeEnum {

	CREATION("C"),
	MODIFICATION("M");
	
	private String type;
	
	SignatureTypeEnum(String type) {
		this.type = type;
	}

	public String toString() {
		return type;
	}
	
}
