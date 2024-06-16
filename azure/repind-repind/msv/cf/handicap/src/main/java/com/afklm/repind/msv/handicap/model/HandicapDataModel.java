package com.afklm.repind.msv.handicap.model;

public class HandicapDataModel {

	private String key;
	private String value;
	private SignatureModel signature;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public SignatureModel getSignature() {
		return signature;
	}
	public void setSignature(SignatureModel signature) {
		this.signature = signature;
	}
}