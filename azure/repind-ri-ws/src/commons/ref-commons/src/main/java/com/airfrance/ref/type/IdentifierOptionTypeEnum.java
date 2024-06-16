
package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;


public enum IdentifierOptionTypeEnum {

	GIN("GIN"),
    FLYING_BLUE("FP"),
    SUSCRIBER("RP"),
    SAPHIR("S"),
    AMEX("AX"),
    GIGYAID("GID"),
    FACEBOOKID("FB"),
    TWITTERID("TWT"),
    UCCRID("UCR"),
    
    ALL_CONTRACTS("AC"),
    ANY_MYACCOUNT("AI");

    private String type;
    
    IdentifierOptionTypeEnum(String type) {
		this.type = type;
	}
    
    public static IdentifierOptionTypeEnum getEnum(String name) throws InvalidParameterException {
		
    	IdentifierOptionTypeEnum enumType;
		
		try {
			enumType = getEnumMandatory(name);
		} catch(MissingParameterException e) {
			enumType = null; // default value
		}
		
		return enumType;
	}
	
	public static IdentifierOptionTypeEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing identifier option");
		}
		
		for (IdentifierOptionTypeEnum e : values()) {
	        if (e.type.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid identifier option: "+name);
	}
	
	public String toString() {
		return type;
	}

}
