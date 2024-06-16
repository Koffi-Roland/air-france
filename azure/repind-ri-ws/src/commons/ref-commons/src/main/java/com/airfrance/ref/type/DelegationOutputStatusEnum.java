package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum DelegationOutputStatusEnum {

	SENT("S"),
	RECEIVED("R"),
	REFUSED("RF"),
	REJECTED("RJ"),
	ACCEPTED("A"),
	CANCELLED("C");
	
	private String status;
	
	DelegationOutputStatusEnum(String status) {
		this.status = status;
	}
	
	
	
	public static DelegationOutputStatusEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing delegation status");
		}
		
		for (DelegationOutputStatusEnum e : values()) {
	        if (e.status.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid delegation output status: "+name);
	}
	
	public String toString() {
		return status;
	}
	
}
