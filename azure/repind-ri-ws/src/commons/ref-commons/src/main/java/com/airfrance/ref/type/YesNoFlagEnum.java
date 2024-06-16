package com.airfrance.ref.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;


public enum YesNoFlagEnum {

	YES("Y"),
	NO("N"),
	TRUE("true"),
	FALSE("false");
	
	private String flag;
	
	YesNoFlagEnum(String flag) {
		this.flag = flag;
	}
	
	
	
	
	
	public static YesNoFlagEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing yes/no flag");
		}
		
		for (YesNoFlagEnum e : values()) {
	        if (e.flag.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid yes/no flag: "+name);
	}
	
	

	public String toString() {
		return flag;
	}
	
	
	
	public static boolean isValid(String flag) {
		
		boolean valid = true;
		
		try {
			getEnumMandatory(flag);
		}
		catch(MissingParameterException e) {
			valid  = false;
		}
		catch(InvalidParameterException e) {
			valid  = false;
		}
		
		return valid;
	}
	
	
	
}
