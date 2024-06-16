package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum TerminalTypeEnum {

	FIX("T"),
	MOBILE("M"),
	TELEX("X"),
	FAX("F");	
	
	private String type;
	
	TerminalTypeEnum(String type) {
		this.type = type;
	}

	public static TerminalTypeEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing terminal type");
		}
		
		for (TerminalTypeEnum e : values()) {
	        if (e.type.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid terminal type: "+name);
	}
	
	public String toString() {
		return type;
	}
	
}
