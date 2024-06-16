package com.airfrance.repind.exception.agence;

import com.airfrance.ref.exception.jraf.JrafDomainException;

public class RA2AgencyNotAllowedException extends JrafDomainException {

	private static final long serialVersionUID = -6180291018632604383L;
	
	public RA2AgencyNotAllowedException(String msg) {
		super(msg);
	}

	public RA2AgencyNotAllowedException(Throwable root) {
		super(root);
	}

	public RA2AgencyNotAllowedException(String msg, Throwable root) {
		super(msg, root);
	}

}
