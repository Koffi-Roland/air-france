package com.airfrance.ref.type;
	
public enum EmailKeyEnum {
	EMAIL_ADDRESS("emailAddress");
	
	private String key = "";

	EmailKeyEnum(String key){
		this.key = key;
	}
	
	public static EmailKeyEnum fromString(String value) {
	    for (EmailKeyEnum b : EmailKeyEnum.values()) {
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



