package com.airfrance.ref.exception;

public class ValidContractFoundException extends RefException {
	
	private static final long serialVersionUID = -2106759691425042895L;
	private static final String ERROR_MESSAGE = "Valid or open contract found";
	
	public ValidContractFoundException(String msg) {
		super(ERROR_MESSAGE, msg);
	}

	public ValidContractFoundException(Throwable root) {
		super(root);
	}

	public ValidContractFoundException(String msg, Throwable root) {
		super(msg, root);
	}
}