package com.airfrance.ref.type;

public enum ApplicationCodeEnum {
	
	ISI("ISI"),		// Store individual by default
	BDC("BDC"),
	GP("GP"),
	RPD("RPD"); 
	
	private String code;
	
	ApplicationCodeEnum(String code) {
		this.setCode(code);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}