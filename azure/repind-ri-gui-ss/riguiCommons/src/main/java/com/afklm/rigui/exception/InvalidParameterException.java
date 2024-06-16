package com.afklm.rigui.exception;


public class InvalidParameterException extends RefException {

	private static final long serialVersionUID = -8657627578076392034L;

	private static final String ERROR_MESSAGE = "Invalid parameter";
	
	public InvalidParameterException(String msg) {
		super(ERROR_MESSAGE, msg);
	}
	
	public InvalidParameterException(Throwable root) {
		super(root);
	}

	public InvalidParameterException(String msg, Throwable root) {
		super(msg, root);
	}
	
}
