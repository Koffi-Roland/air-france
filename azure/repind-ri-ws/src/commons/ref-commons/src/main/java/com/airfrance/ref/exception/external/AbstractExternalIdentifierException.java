package com.airfrance.ref.exception.external;

import com.airfrance.ref.exception.RefException;

public abstract class AbstractExternalIdentifierException extends RefException {

	private static final long serialVersionUID = -3534152391299006568L;
	
	public AbstractExternalIdentifierException(String msg){
		super(msg);
	}
	
	public AbstractExternalIdentifierException(String errorMessage, String errorValue){
		super(errorMessage, errorValue);
	}
	
}
