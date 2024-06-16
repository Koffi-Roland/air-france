package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;


public enum GenderEnum {

	MALE("M"),
	FEMALE("F"),
	UNKNOWN("U"),
	NONBINARY("X");
	
	private String gender;
	
	GenderEnum(String type) {
		this.gender = type;
	}
	
	public static GenderEnum getEnum(String name) throws InvalidParameterException {
		
		GenderEnum enumType;
		
		try {
			enumType = getEnumMandatory(name);
		} catch(MissingParameterException e) {
			enumType = UNKNOWN; // default value
		}
		
		return enumType;
	}
	
	public static GenderEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing gender");
		}
		
		for (GenderEnum e : values()) {
	        if (e.gender.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid gender: "+name);
	}
	
	public String toString() {
		return gender;
	}
	
}
