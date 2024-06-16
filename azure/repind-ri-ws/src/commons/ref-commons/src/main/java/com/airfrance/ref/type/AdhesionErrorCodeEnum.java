package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum AdhesionErrorCodeEnum {

	ERROR_001,
	ERROR_003,
	ERROR_120,
	ERROR_131,
	ERROR_133,
	ERROR_140,
	ERROR_209,
	ERROR_277,
	ERROR_902,
	ERROR_905,
	ERROR_932,
	OTHER;

	public static AdhesionErrorCodeEnum getEnum(String name) {
		
		AdhesionErrorCodeEnum enumType;
		
		try {
			enumType = getEnumMandatory(name);
		} catch(MissingParameterException e) {
			enumType = OTHER; // default value
		} catch(InvalidParameterException e) {
			enumType = OTHER; // default value
		}
		
		return enumType;
	}
	
	public static AdhesionErrorCodeEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing adhesion error code");
		}
		
		for (AdhesionErrorCodeEnum e : values()) {
	        if (e.name().contains(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid adhesion error code: "+name);
	}
	
}
