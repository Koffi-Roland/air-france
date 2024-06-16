package com.afklm.rigui.enums;

public enum SiteEnum {
	BATCHQVI("BATCH_QVI");
	
	private String value;
	
	SiteEnum(String val) {
		this.value = val;
	}
	
	public String toString() {
		return value;
	}
}
