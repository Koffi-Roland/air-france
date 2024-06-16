package com.afklm.rigui.exception;

import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;

public class SeveralAccountDataException extends JrafDomainRollbackException {

	private static final long serialVersionUID = -8521936547826436781L;

	public SeveralAccountDataException(String msg) {
		super(msg);
	}

	public SeveralAccountDataException(Throwable root) {
		super(root);
	}

	public SeveralAccountDataException(String msg, Throwable root) {
		super(msg, root);
	}

}
