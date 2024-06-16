package com.airfrance.ref.type;


public enum ComplementaryInformationTypeEnum {
	
	PHONE("TEL"),
	POSTAL_ADDRESS("PAC"),
	EMAIL("EMA"),
	CONTRACT("CON"),
	OTHER("OTH"),
	INDIVIDU("IND");

	private String key = "";

	ComplementaryInformationTypeEnum(String key){
		this.key = key;
	}

	public static ComplementaryInformationTypeEnum fromString(String value) {
	    for (ComplementaryInformationTypeEnum b : ComplementaryInformationTypeEnum.values()) {
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





