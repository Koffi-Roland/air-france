package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class SeveralIndividualException extends JrafDomainRollbackException {

	private static final long serialVersionUID = 12243581003639369L;

	public SeveralIndividualException(String arg0) {
		super(arg0);
	}

	public SeveralIndividualException(Throwable arg0) {
		super(arg0);
	}

	public SeveralIndividualException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
