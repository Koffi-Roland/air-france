package com.airfrance.ref.exception;

public class InvalidApplicationCodeException extends RefException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7830504861588711478L;

	public InvalidApplicationCodeException(String msg) {
		super(msg);
	}
	
	public InvalidApplicationCodeException(Throwable root) {
		super(root);
	}

	public InvalidApplicationCodeException(String msg, Throwable root) {
		super(msg, root);
	}
}