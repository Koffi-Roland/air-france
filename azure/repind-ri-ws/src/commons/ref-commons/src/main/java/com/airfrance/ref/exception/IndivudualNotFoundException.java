package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;

public class IndivudualNotFoundException extends JrafDomainRollbackException {

	private static final long serialVersionUID = -7570825132009841115L;

	public IndivudualNotFoundException(String msg) {
		super(msg);
	}
	
	public IndivudualNotFoundException(Throwable root) {
		super(root);
	}

	public IndivudualNotFoundException(String msg, Throwable root) {
		super(msg, root);
	}
}
