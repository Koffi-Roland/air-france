package com.afklm.rigui.exception;

import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;

public class InvalidMailingException extends JrafDomainRollbackException {

	private static final long serialVersionUID = -8657627578076392034L;

	public InvalidMailingException(String msg) {
		super(msg);
	}
	
	public InvalidMailingException(Throwable root) {
		super(root);
	}

	public InvalidMailingException(String msg, Throwable root) {
		super(msg, root);
	}
}
