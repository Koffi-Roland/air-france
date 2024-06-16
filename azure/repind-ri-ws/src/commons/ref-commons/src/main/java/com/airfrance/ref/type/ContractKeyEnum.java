package com.airfrance.ref.type;
	
public enum ContractKeyEnum {
	FLYING_BLUE_NUMBER("contractType"),
	NUM_CONTRACT("contractNumber");
	
	private String key = "";

	ContractKeyEnum(String key){
		this.key = key;
	}
	
	public static ContractKeyEnum fromString(String value) {
	    for (ContractKeyEnum b : ContractKeyEnum.values()) {
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



