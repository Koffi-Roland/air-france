package com.airfrance.ref.type;
	
public enum OtherKeyEnum {
	REMARK("remark");
	
	private String key = "";

	OtherKeyEnum(String key){
		this.key = key;
	}
	
	public static OtherKeyEnum fromString(String value) {
	    for (OtherKeyEnum b : OtherKeyEnum.values()) {
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



