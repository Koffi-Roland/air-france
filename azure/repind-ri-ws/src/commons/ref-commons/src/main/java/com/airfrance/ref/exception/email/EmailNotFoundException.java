package com.airfrance.ref.exception.email;

public class EmailNotFoundException extends EmailException {
	
	private static final long serialVersionUID = -2285747495868768153L;
		
	public EmailNotFoundException(String msg, String email){
		super(msg,email);
	}
}
