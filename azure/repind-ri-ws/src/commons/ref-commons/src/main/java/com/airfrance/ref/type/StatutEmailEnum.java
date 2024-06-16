package com.airfrance.ref.type;

public enum StatutEmailEnum {
	T("T"),
	V("V"),
	I("I"),
	X("X");
	
	private String value;
	
	StatutEmailEnum(String value) {
		this.value = value;
	}
	
	public String getStatus(){
		return value;
	}
}
