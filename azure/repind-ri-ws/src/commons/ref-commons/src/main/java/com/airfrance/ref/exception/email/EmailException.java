package com.airfrance.ref.exception.email;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class EmailException extends JrafDomainRollbackException {

	private static final long serialVersionUID = 2358389776993453399L;
	
	
	private String email;
		

	public EmailException(String msg, String email){
		super(msg);
		this.email = email;		
	}
	public EmailException(Throwable root, String email) {
		super(root);
		this.email = email;		
	}

	public EmailException(String msg, Throwable root, String email) {
		super(msg, root);
		this.email = email;	
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}		
}
