package com.afklm.rigui.exception;

import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;

public class AlreadyExistException extends JrafDomainRollbackException {

	private static final long serialVersionUID = 5409057807254840706L;

	public AlreadyExistException(String msg) {
		super(msg);
	}

	public AlreadyExistException(Throwable root) {
		super(root);
	}

	public AlreadyExistException(String msg, Throwable root) {
		super(msg, root);
	}

}
