package com.afklm.rigui.enums;

import com.afklm.rigui.exception.InvalidParameterException;
import com.afklm.rigui.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;


public enum OuiNonFlagEnum {

	OUI("O"),
	NON("N");
	
	private String flag;
	
	OuiNonFlagEnum(String flag) {
		this.flag = flag;
	}
	
	public static OuiNonFlagEnum getEnum(String name) throws InvalidParameterException {
		
		OuiNonFlagEnum enumType;
		
		try {
			enumType = getEnumMandatory(name);
		} catch(MissingParameterException e) {
			enumType = null; // default value
		}
		
		return enumType;
	}
	
	public static OuiNonFlagEnum getEnum(Boolean flag) throws InvalidParameterException {
		
		OuiNonFlagEnum enumType;
		
		try {
			enumType = getEnumMandatory(flag);
		} catch(MissingParameterException e) {
			enumType = null; // default value
		}
		
		return enumType;
	}
	
	public static OuiNonFlagEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing oui/non flag");
		}
		
		for (OuiNonFlagEnum e : values()) {
	        if (e.flag.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid oui/non flag: "+name);
	}
	
	public static OuiNonFlagEnum getEnumMandatory(Boolean flag) throws InvalidParameterException, MissingParameterException {
		
		if(flag==null) {
			throw new MissingParameterException("Missing oui/non flag");
		}
		
		return flag ? OUI : NON;
	}

	public String toString() {
		return flag;
	}
	
	public Boolean toBoolean() {
		return (this==OUI) ? true : false;
	}
	
	public static String getStringValue(Boolean flag) {
		
		if(flag==null) {
			return null;
		}
		
		return flag ? OUI.toString() : NON.toString();
	}
	
}
