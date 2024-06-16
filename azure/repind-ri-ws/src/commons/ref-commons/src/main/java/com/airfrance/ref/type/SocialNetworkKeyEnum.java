package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum SocialNetworkKeyEnum {

	USED_BY_AF("USED_BY_AF"),
	USED_BY_KL("USED_BY_KL");
	
	private String key;
	
	SocialNetworkKeyEnum(String key) {
		this.key = key;
	}
	
	
	
	public static SocialNetworkKeyEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing social network key");
		}
		
		for (SocialNetworkKeyEnum e : values()) {
	        if (e.key.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid social network key: "+name);
	}
	
	public String toString() {
		return key;
	}
	
}
