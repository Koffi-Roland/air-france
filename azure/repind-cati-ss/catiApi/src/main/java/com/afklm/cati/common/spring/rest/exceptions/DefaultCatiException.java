package com.afklm.cati.common.spring.rest.exceptions;

public class DefaultCatiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4084469028531589930L;
	
	public DefaultCatiException(ErrorResource errorResource) {
		super();
		this.errorResource = errorResource;
	}

	private final ErrorResource errorResource;

	public ErrorResource getErrorResource() {
		return errorResource;
	}

}
