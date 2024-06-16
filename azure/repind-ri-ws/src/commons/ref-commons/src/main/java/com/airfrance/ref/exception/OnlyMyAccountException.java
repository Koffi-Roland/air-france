package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class OnlyMyAccountException extends JrafDomainRollbackException {

	private static final long serialVersionUID = 2278432785748397658L;

	public OnlyMyAccountException(String arg0) {
		super(arg0);
	}

	public OnlyMyAccountException(Throwable arg0) {
		super(arg0);
	}

	public OnlyMyAccountException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
