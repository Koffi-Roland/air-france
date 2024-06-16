package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class BadDateFormatException extends JrafDomainRollbackException {

	private static final long serialVersionUID = -2208033042442441461L;

	public BadDateFormatException(String arg0) {
		super(arg0);
	}
	
	public BadDateFormatException(Throwable arg0) {
		super(arg0);
	}

	public BadDateFormatException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
