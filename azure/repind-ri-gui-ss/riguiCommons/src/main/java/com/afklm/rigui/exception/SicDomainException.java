package com.afklm.rigui.exception;

import com.afklm.rigui.exception.jraf.JrafDomainException;

public class SicDomainException extends JrafDomainException {

	private static final long serialVersionUID = 3322732602272490952L;

	public SicDomainException(String arg0) {
		super(arg0);
	}

	public SicDomainException(Throwable arg0) {
		super(arg0);
	}

	public SicDomainException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
