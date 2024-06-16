package com.airfrance.ref.type.external;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import org.apache.commons.lang.StringUtils;

public enum PnmAirlineEnum {

	AIR_FRANCE("AF"),
	KLM("KL");
	
	private String airline;
	
	PnmAirlineEnum(String airline) {
		this.airline = airline;
	}
	
	
	
	public static PnmAirlineEnum getEnumMandatory(String airline) throws InvalidParameterException, MissingParameterException {
		
		if(StringUtils.isEmpty(airline)) {
			throw new MissingParameterException("Missing PNM airline");
		}
		
		for (PnmAirlineEnum e : values()) {
	        if (e.airline.equals(airline)) {
	            return e;
	        }
	    }
		
		throw new InvalidParameterException("Invalid PNM airline : "+airline);
	}
	
	public static boolean isValid(String airline) {
		
		boolean valid = true;
		
		try {
			getEnumMandatory(airline);
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
		return airline;
	}
	
}
