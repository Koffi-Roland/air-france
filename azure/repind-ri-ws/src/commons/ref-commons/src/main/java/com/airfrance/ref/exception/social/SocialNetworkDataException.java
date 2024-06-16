package com.airfrance.ref.exception.social;

import com.airfrance.ref.exception.jraf.JrafDomainException;

public class SocialNetworkDataException extends JrafDomainException {

	private static final long serialVersionUID = 7220539743215138897L;
	
	public SocialNetworkDataException(String msg) {
		super(msg);
	}
	
}
