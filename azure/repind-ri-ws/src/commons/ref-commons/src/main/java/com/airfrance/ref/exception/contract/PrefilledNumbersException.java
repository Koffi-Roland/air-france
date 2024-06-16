package com.airfrance.ref.exception.contract;

import com.airfrance.ref.exception.jraf.JrafDomainException;

public class PrefilledNumbersException extends JrafDomainException {

	private static final long serialVersionUID = 6753495843578476880L;
	
	public PrefilledNumbersException(String msg) {
		super(msg);
	}
	
}
