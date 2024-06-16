package com.airfrance.ref.exception.contract;

import com.airfrance.ref.exception.RefException;

public class RoleTravelerNotCompatibleWithProspectException extends RefException {

	private static final long serialVersionUID = -4236849271560531834L;
	

	private static final String ERROR_MESSAGE = "Role Traveler not compatible with Prospect";
	
	public RoleTravelerNotCompatibleWithProspectException(String msg) {
		super(ERROR_MESSAGE, msg);
	}
	
}
