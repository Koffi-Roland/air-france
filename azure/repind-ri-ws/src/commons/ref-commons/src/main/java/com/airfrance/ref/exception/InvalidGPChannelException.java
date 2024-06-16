package com.airfrance.ref.exception;

public class InvalidGPChannelException extends RefException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8990216192050495097L;

	public InvalidGPChannelException(String msg) {
		super(msg);
	}
	
	public InvalidGPChannelException(Throwable root) {
		super(root);
	}

	public InvalidGPChannelException(String msg, Throwable root) {
		super(msg, root);
	}
}