package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class IdentifierAlreadyUsedException extends JrafDomainRollbackException {

	private static final long serialVersionUID = 6524846046397102969L;

	public IdentifierAlreadyUsedException(String msg) {
		super(msg);
	}

	public IdentifierAlreadyUsedException(Throwable root) {
		super(root);
	}

	public IdentifierAlreadyUsedException(String msg, Throwable root) {
		super(msg, root);
	}

}
