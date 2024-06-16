package com.airfrance.ref.exception;

public class MissingMailingDataException extends RefException {
	
	private static final long serialVersionUID = -3092404305224637179L;
	private static final String ERROR_MESSAGE = "Missing medium code exception";
	
	public MissingMailingDataException(String msg) {
		super(ERROR_MESSAGE, msg);
	}

	public MissingMailingDataException(Throwable root) {
		super(root);
	}

	public MissingMailingDataException(String msg, Throwable root) {
		super(msg, root);
	}
}