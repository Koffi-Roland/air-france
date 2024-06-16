package com.airfrance.repind.exception.firme;

import com.airfrance.ref.exception.jraf.JrafDomainException;

public class InvalidParametersException extends JrafDomainException{

	private static final long serialVersionUID = -5014441750188529843L;

	public InvalidParametersException(String msg) {
		super(msg);
	}

}
