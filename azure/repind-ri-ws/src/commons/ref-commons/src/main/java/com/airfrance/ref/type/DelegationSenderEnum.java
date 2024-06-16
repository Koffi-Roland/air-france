package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum DelegationSenderEnum {

	DELEGATOR("DGR"),
	DELEGATE("DGE");
	
	private String status;
	
	DelegationSenderEnum(String status) {
		this.status = status;
	}
	
	
	
	public static DelegationSenderEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing delegation sender");
		}
		
		for (DelegationSenderEnum e : values()) {
	        if (e.status.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid delegation sender: "+name);
	}
	
	public String toString() {
		return status;
	}
	
}
