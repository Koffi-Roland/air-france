package com.airfrance.repind.exception;

import com.airfrance.ref.exception.jraf.JrafDomainException;

public class TooManyResultsDomainException extends JrafDomainException {

	private static final long serialVersionUID = 6017970889238908111L;

	public TooManyResultsDomainException(String msg) {
		super(msg);
	}

	public TooManyResultsDomainException(Throwable root) {
		super(root);
	}

	public TooManyResultsDomainException(String msg, Throwable root) {
		super(msg, root);
	}

}
