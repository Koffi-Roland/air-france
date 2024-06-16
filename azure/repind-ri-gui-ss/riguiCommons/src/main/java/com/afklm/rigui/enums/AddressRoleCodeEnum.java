package com.afklm.rigui.enums;

import com.afklm.rigui.exception.InvalidParameterException;
import com.afklm.rigui.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum AddressRoleCodeEnum {

	PRINCIPAL("M"),
	SECONDARY("C");
	
	private String code;
	
	AddressRoleCodeEnum(String code) {
		this.code = code;
	}
	
	public static AddressRoleCodeEnum getEnum(String name) throws InvalidParameterException {
		
		AddressRoleCodeEnum enumType;
		
		try {
			enumType = getEnumMandatory(name);
		} catch(MissingParameterException e) {
			enumType = null; // default value
		}
		
		return enumType;
	}
	
	public static AddressRoleCodeEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing address role code");
		}
		
		for (AddressRoleCodeEnum e : values()) {
	        if (e.code.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid address role code: "+name);
	}
	
	public String toString() {
		return code;
	}
	
}
