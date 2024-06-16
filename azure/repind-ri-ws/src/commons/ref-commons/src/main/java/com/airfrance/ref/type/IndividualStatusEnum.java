package com.airfrance.ref.type;

public enum IndividualStatusEnum {

	DECEASED("D"),
	FORGOTTEN("F"),
	TEMPORARY("P"),
	MERGED("T"),
	VALID("V"),
	DELETED("X");   

	private String key = "";

	IndividualStatusEnum(String key){
		this.key = key;
	}
	
	public static IndividualStatusEnum fromString(String value) {
	    for (IndividualStatusEnum b : IndividualStatusEnum.values()) {
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