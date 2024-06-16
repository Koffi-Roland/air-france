package com.afklm.rigui.exception;

public class MissingMediumCodeException extends RefException{

	private static final long serialVersionUID = 1046570769173751402L;
	private static final String ERROR_MESSAGE = "Missing medium code exception";
	
	public MissingMediumCodeException(String msg) {
		super(ERROR_MESSAGE, msg);
	}

	public MissingMediumCodeException(Throwable root) {
		super(root);
	}

	public MissingMediumCodeException(String msg, Throwable root) {
		super(msg, root);
	}
	
}
