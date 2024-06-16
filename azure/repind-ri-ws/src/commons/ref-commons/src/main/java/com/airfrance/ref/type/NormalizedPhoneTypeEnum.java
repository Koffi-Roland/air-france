package com.airfrance.ref.type;

public enum NormalizedPhoneTypeEnum {
	
	FIX("T"),
	MOBILE("M"),
	UNKNOWN("U");
	
	private String type;
	
	NormalizedPhoneTypeEnum(String type) {
		this.type = type;
	}

	public String toString() {
		return type;
	}
	
}
