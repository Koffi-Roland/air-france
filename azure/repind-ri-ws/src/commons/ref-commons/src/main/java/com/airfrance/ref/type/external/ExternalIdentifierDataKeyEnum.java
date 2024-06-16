package com.airfrance.ref.type.external;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum ExternalIdentifierDataKeyEnum {

	PNM_NAME("PNM_NAME"),
	APPLICATION_LANGUAGE_CODE("APP_LANGUAGE_CODE"),
	APPLICATION_NAME("APP_NAME"),
	PNM_AIRLINE("PNM_AIRLINE"),
	DEVICE_NAME("DEVICE_NAME"),
	OPTIN("OPTIN"),
	USED_BY_AF("USED_BY_AF"),
	USED_BY_KL("USED_BY_KL");
	
	private String key;
	
	ExternalIdentifierDataKeyEnum(String key) {
		this.key = key;
	}
	
	public static ExternalIdentifierDataKeyEnum getEnum(String name) throws InvalidParameterException {
		
		ExternalIdentifierDataKeyEnum enumType;
		
		try {
			enumType = getEnumMandatory(name);
		} catch(MissingParameterException e) {
			enumType = null; // default value
		}
		
		return enumType;
	}
	
	public static ExternalIdentifierDataKeyEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing external identifier data key");
		}
		
		for (ExternalIdentifierDataKeyEnum e : values()) {
	        if (e.key.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid external identifier data key : "+name);
	}
	
	public static boolean isValid(String key) {
		
		boolean valid = true;
		
		try {
			getEnumMandatory(key);
		}
		catch(MissingParameterException e) {
			valid = false;
		}
		catch(InvalidParameterException e) {
			valid  = false;
		}
		
		return valid;
	}
	
	public String toString() {
		return key;
	}
	
}
