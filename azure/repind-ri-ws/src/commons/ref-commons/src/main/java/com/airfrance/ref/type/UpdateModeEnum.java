package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;


public enum UpdateModeEnum {

	REPLACE("R", "CR"),
	UPDATE("U", "UPD"),
	DELETE("D", "DEL");
	
	private String mode;
	private String oldMode;
	
	UpdateModeEnum(String mode, String oldMode) {
		this.mode = mode;
		this.oldMode = oldMode;
	}
	
	public static UpdateModeEnum getEnum(String name) throws InvalidParameterException {
		
		UpdateModeEnum enumType;
		
		try {
			enumType = getEnumMandatory(name);
		} catch(MissingParameterException e) {
			enumType = UPDATE; // default value
		}
		
		return enumType;
	}
	
	public static UpdateModeEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing update mode");
		}
		
		for (UpdateModeEnum e : values()) {
	        if (e.mode.equals(name) || e.oldMode.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid update mode: "+name);
	}

	public String toString() {
		return mode;
	}
	
	public String toOldString() {
		return oldMode;
	}
	
}
