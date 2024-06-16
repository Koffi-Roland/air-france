package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum MediumCodeEnum {

	HOME("D"),
	BUSINESS("P"),
	RELATION("R"),
	LOCALISATION("L"),
	MAILING("M");
	
	private String code;
	
	MediumCodeEnum(String code) {
		this.code = code;
	}
	
	
	
	public static MediumCodeEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing medium code");
		}
		
		for (MediumCodeEnum e : values()) {
	        if (e.code.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid medium code: "+name);
	}
	
	public String toString() {
		return code;
	}
	
}
