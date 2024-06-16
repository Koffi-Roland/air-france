package com.airfrance.ref.type;
	
public enum IndividuKeyEnum {
	BIRTHDATE("dateOfBirth"),
	SPOKEN_LANGUAGE("spokenLanguage");

	private String key = "";

	IndividuKeyEnum(String key){
		this.key = key;
	}
	
	public static IndividuKeyEnum fromString(String value) {
	    for (IndividuKeyEnum b : IndividuKeyEnum.values()) {
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



