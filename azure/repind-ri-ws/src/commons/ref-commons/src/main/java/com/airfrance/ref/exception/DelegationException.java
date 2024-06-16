package com.airfrance.ref.exception;

import com.airfrance.ref.exception.jraf.JrafDomainException;

public class DelegationException extends JrafDomainException {

	private static final long serialVersionUID = 501547289928740079L;

	public DelegationException(String msg) {
		super(msg);
	}

}
