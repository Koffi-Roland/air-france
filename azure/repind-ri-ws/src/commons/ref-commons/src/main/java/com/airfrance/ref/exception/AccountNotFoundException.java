package com.airfrance.ref.exception;


public class AccountNotFoundException extends RefException {

	private static final long serialVersionUID = -1473133931052957542L;

	private static final String ERROR_MESSAGE = "Account not found";
	
	public AccountNotFoundException(String msg) {
		super(ERROR_MESSAGE, msg);
	}
	
}
