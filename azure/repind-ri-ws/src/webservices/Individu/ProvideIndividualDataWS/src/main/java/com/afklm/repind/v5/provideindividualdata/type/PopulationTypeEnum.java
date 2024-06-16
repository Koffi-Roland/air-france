package com.afklm.repind.v5.provideindividualdata.type;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum PopulationTypeEnum {

	INDIVIDU("I"),
	WHITE_WINGERS("W"),
	TRAVELER("T"),
	SOCIAL_ONLY("S");
	
	private String type;

	PopulationTypeEnum(String type) {
		this.type = type;
	}

	public static PopulationTypeEnum getEnumMandatory(String name) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(name)) {
			throw new MissingParameterException("Missing population type");
		}
		
		for (PopulationTypeEnum e : values()) {
	        if (e.type.equals(name)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid population type: "+name);
	}
	
	public String toString() {
		return type;
	}
	
}
