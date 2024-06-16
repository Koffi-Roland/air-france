package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class ReconciliationProcessException extends JrafDomainRollbackException {

	private static final long serialVersionUID = -4448261417390587386L;

	public ReconciliationProcessException(String msg) {
		super(msg);
	}
	
	public ReconciliationProcessException(Throwable msg) {
		super(msg);
	}
	
	public ReconciliationProcessException(String msg, Throwable msg1) {
		super(msg, msg1);
	}
}