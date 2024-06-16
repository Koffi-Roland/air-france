package com.afklm.repind.msv.preferences.mashery.interceptor;

/**
 *
 * @author x074151 (Suresh van Rookhuizen)
 */

public class InvalidParameterException extends RuntimeException {
	private static final long serialVersionUID = -896293871223038822L;

	private final String fieldName;
	private final Object fieldValue;

	/**
	 * Default one.
	 */
	public InvalidParameterException() {
		super();
		fieldName = null;
		fieldValue = null;
	}

	/**
	 * @param fieldName
	 *            String
	 * @param fieldValue
	 *            String
	 */
	public InvalidParameterException(final String fieldName, final Object fieldValue) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	/**
	 * @param fieldName
	 *            String
	 * @param fieldValue
	 *            String
	 * @param message
	 *            String
	 */
	public InvalidParameterException(final String fieldName, final Object fieldValue, final String message) {
		super(message);
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @return the fieldValue
	 */
	public Object getFieldValue() {
		return fieldValue;
	}

}
