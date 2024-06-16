package com.airfrance.repind.exception.firme;

import com.airfrance.ref.exception.jraf.JrafDomainException;

public class InvalidFirmTypeException extends JrafDomainException {

	private static final long serialVersionUID = -6180291018632604383L;
	
	public InvalidFirmTypeException(String msg) {
		super(msg);
	}

	public InvalidFirmTypeException(Throwable root) {
		super(root);
	}

	public InvalidFirmTypeException(String msg, Throwable root) {
		super(msg, root);
	}

}
