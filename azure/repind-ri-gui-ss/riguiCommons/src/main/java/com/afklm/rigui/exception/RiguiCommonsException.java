package com.afklm.rigui.exception;

/**
 * <p>
 * Title : riguiCommonsException.java
 * </p>
 * Exception to commons part
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public class RiguiCommonsException extends Exception {

	/** Serial version UID */
	private static final long serialVersionUID = 7334600182659600271L;

	/**
	 * Default constructor
	 */
	public RiguiCommonsException() {
		super();
	}

	/**
	 * Exception with message
	 * 
	 * @param message
	 *            the message of the exception
	 */
	public RiguiCommonsException(String message) {
		super(message);
	}

	/**
	 * Exception with message and original exception
	 * 
	 * @param message
	 *            the message of the exception
	 * @param throwable
	 *            original exception
	 */
	public RiguiCommonsException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
