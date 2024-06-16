package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum ScopeEnum {
	
	IDENTIFICATION_DATA("IDENTIFICATION_DATA"),
	MARKETING_DATA("MARKETING_DATA"),
	FB_CONTRACT_DATA("FB_CONTRACT_DATA"),
	PAYMENT_PREFERENCES_DATA("PAYMENT_PREFERENCES_DATA");
	
	private String scope;
	
	ScopeEnum(String scope) {
		this.scope = scope;
	}
	
	public static ScopeEnum getEnum(String name) throws InvalidParameterException {
		
		ScopeEnum enumType;
		
		try {
			enumType = getEnumMandatory(name);
		} catch(MissingParameterException e) {
			enumType = null; // default value
		}
		
		return enumType;
	}
	
	public static ScopeEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing scope");
		}
		
		for (ScopeEnum e : values()) {
	        if (e.scope.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid scope: "+name);
	}
	
	public String toString() {
		return scope;
	}
	
}
