package com.airfrance.ref.type.external;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum PnmNameEnum {

	AD4PUSH("AD4S"),
	CAPPTAIN("CAPP");
	
	private String name;
	
	PnmNameEnum(String name) {
		this.name = name;
	}
	
	
	
	public static PnmNameEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing PNM name");
		}
		
		for (PnmNameEnum e : values()) {
	        if (e.name.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid PNM name : "+name);
	}
	
	public static boolean isValid(String name) {
		
		boolean valid = true;
		
		try {
			getEnumMandatory(name);
		}
		catch(MissingParameterException e) {
			valid = false;
		}
		catch(InvalidParameterException e) {
			valid  = false;
		}
		
		return valid;
	}
	
	public String toString() {
		return name;
	}
	
}
