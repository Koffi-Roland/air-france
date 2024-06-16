package com.afklm.rigui.spring.rest.exceptions;

/**
 * Default Exception
 * @author m405991
 *
 */
public class DefaultRiguiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4084469028531589930L;
	
	/**
	 * Constructor
	 * @param errorResource
	 */
	public DefaultRiguiException(ErrorResource errorResource) {
		super();
		this.errorResource = errorResource;
	}

	private final ErrorResource errorResource;

	public ErrorResource getErrorResource() {
		return errorResource;
	}

}
