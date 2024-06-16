package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum DelegationReturnStatusEnum {

	ACCEPTED("A"),
	RECEIVED("R"),
	SENT("S");
	
	private String status;
	
	DelegationReturnStatusEnum(String status) {
		this.status = status;
	}
	
	public static DelegationReturnStatusEnum getEnum(String name) throws InvalidParameterException {
		
		DelegationReturnStatusEnum enumType;
		
		try {
			enumType = getEnumMandatory(name);
		} catch(MissingParameterException e) {
			enumType = null; // default value
		}
		
		return enumType;
	}
	
	public static DelegationReturnStatusEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing delegation return status");
		}
		
		for (DelegationReturnStatusEnum e : values()) {
	        if (e.status.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid delegation return status: "+name);
	}
	
	public String toString() {
		return status;
	}
	
}
