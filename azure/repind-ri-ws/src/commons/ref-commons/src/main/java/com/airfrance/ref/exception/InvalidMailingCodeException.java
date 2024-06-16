package com.airfrance.ref.exception;

public class InvalidMailingCodeException extends RefException {
	
	private static final long serialVersionUID = 9146699976286222105L;

	public InvalidMailingCodeException(String msg) {
		super(msg);
	}
	
	public InvalidMailingCodeException(Throwable root) {
		super(root);
	}

	public InvalidMailingCodeException(String msg, Throwable root) {
		super(msg, root);
	}
}