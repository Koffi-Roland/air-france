package com.afklm.rigui.exception.compref;

import com.afklm.rigui.exception.RefException;

public class InvalidPermissionIdException extends RefException {

	
	private static final long serialVersionUID = -3639654583483376899L;
	
	private static final String ERROR_MESSAGE = "The permission id provided doesn’t exist";
	
	public InvalidPermissionIdException(String msg) {
		super(ERROR_MESSAGE, msg);
	}
	
}
