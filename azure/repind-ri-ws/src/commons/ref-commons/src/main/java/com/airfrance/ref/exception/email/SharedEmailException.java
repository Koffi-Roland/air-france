package com.airfrance.ref.exception.email;

public class SharedEmailException extends EmailException {

	private static final long serialVersionUID = 8106238135670556879L;	
	
	public SharedEmailException(String msg, String email) {
		super(msg, email);
	}
}
