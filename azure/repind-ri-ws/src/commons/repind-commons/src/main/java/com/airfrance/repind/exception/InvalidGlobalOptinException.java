package com.airfrance.repind.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class InvalidGlobalOptinException extends JrafDomainRollbackException {

	private static final long serialVersionUID = 4244525142571368585L;

	public InvalidGlobalOptinException(String arg0) {
		super(arg0);
	}
	
	public InvalidGlobalOptinException(Throwable arg0) {
		super(arg0);
	}

	public InvalidGlobalOptinException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
}
