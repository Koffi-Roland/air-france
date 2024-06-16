package com.afklm.cati.common.exception;

/**
 * <p>
 * Title : CatiCommonsException.java
 * </p>
 * Exception to commons part
 * <p>
 * Copyright : Copyright (c) 2013
 * </p>
 * <p>
 * Company : AIRFRANCE-KLM
 * </p>
 */
public class CatiCommonsException extends Throwable {

	/** Serial version UID */
	private static final long serialVersionUID = 7334600182659600271L;

	/**
	 * Default constructor
	 */
	public CatiCommonsException() {
		super();
	}

	/**
	 * Exception with message
	 * 
	 * @param message
	 *            the message of the exception
	 */
	public CatiCommonsException(String message) {
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
	public CatiCommonsException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
