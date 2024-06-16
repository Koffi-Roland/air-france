package com.airfrance.ref.type;


public enum PostalAddressKeyEnum {
	
	NUMBER_AND_STREET("numberAndStreet"),
	ADDITIONAL_INFORMATION("additionalInformation"),
	CITY("city"),
	DISTRICT("district"),
	ZIPCODE("zipCode"),
	STATE("state"),
	COUNTRY("country");

	private String key = "";
	
	PostalAddressKeyEnum(String key){
		this.key = key;
	}
	
	public static PostalAddressKeyEnum fromString(String value) {
	    for (PostalAddressKeyEnum b : PostalAddressKeyEnum.values()) {
	      if (b.key.equalsIgnoreCase(value)) {
	        return b;
	      }
	    }
	    return null;
	  }

	public String toString(){
		return name();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}



