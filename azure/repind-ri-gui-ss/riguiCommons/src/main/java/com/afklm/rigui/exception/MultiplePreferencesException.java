package com.afklm.rigui.exception;

import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;

public class MultiplePreferencesException extends JrafDomainRollbackException {

	private static final long serialVersionUID = 1208463277194502903L;

	public MultiplePreferencesException(String msg) {
		super(msg);
	}
	
	public MultiplePreferencesException(Throwable msg) {
		super(msg);
	}
	
	public MultiplePreferencesException(String msg, Throwable msg1) {
		super(msg, msg1);
	}
	
}
