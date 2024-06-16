package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class BadIdentifierException extends JrafDomainRollbackException {

	private static final long serialVersionUID = -911879697152672452L;

	public BadIdentifierException(String arg0) {
		super(arg0);
	}

	public BadIdentifierException(Throwable arg0) {
		super(arg0);
	}

	public BadIdentifierException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
