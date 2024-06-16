package com.afklm.rigui.enums;

import com.afklm.rigui.exception.InvalidParameterException;
import com.afklm.rigui.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum DelegationActionEnum {

	CANCELLED("C"),
	DELETED("D"),
	ACCEPTED("A"),
	REJECTED("R"),
	INVITED("I");
	
	private String status;
	
	DelegationActionEnum(String status) {
		this.status = status;
	}
	
	public static DelegationActionEnum getEnum(String name) throws InvalidParameterException {
		
		DelegationActionEnum enumType;
		
		try {
			enumType = getEnumMandatory(name);
		} catch(MissingParameterException e) {
			enumType = null; // default value
		}
		
		return enumType;
	}
	
	public static DelegationActionEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing delegation action");
		}
		
		for (DelegationActionEnum e : values()) {
	        if (e.status.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid delegation action: "+name);
	}
	
	public String getStatus(){
		return status;
	}
	
	public String toString() {
		return getStatus();
	}
	
}
