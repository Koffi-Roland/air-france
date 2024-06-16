package com.airfrance.ref.exception;

public class FonctionProException extends RefException {
		
	private static final long serialVersionUID = -5748397733205352880L;

	public FonctionProException(String msg, String value) {
		super(msg, value);
	}

	public FonctionProException(String errorCode, Throwable root) {
		super(root);
	}

	public FonctionProException(String errorCode, String msg, Throwable root) {
		super(msg, root);
	}
	
}
