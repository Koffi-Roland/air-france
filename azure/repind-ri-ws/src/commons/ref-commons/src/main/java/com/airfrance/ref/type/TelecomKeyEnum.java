package com.airfrance.ref.type;
	
public enum TelecomKeyEnum {
	TERMINAL_TYPE("terminalType"),
	COUNTRY_CODE("countryCode"),
	PHONE_NUMBER("phoneNumber");   

	private String key = "";

	TelecomKeyEnum(String key){
		this.key = key;
	}
	
	public static TelecomKeyEnum fromString(String value) {
	    for (TelecomKeyEnum b : TelecomKeyEnum.values()) {
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



