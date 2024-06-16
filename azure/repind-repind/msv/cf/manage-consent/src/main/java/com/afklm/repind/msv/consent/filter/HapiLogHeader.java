package com.afklm.repind.msv.consent.filter;

public class HapiLogHeader {

	private String key;
	private String value;

	public HapiLogHeader(final String key, final String value) {

		this.key = key;
		this.value = value;

	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public void setValue(final String value) {
		this.value = value;
	}

}
