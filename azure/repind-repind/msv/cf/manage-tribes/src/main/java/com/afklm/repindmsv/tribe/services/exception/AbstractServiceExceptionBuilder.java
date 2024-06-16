package com.afklm.repindmsv.tribe.services.exception;

/**
 * AbstractServiceExceptionBuilder
 *
 * @author m312812
 *
 */
public abstract class AbstractServiceExceptionBuilder implements IServiceExceptionBuilder {

	private final Exception exception;

	/**
	 * AbstractServiceExceptionBuilder constructor from an Exception
	 * 
	 * @param exception
	 *            Exception
	 */
	public AbstractServiceExceptionBuilder(final Exception exception) {
		this.exception = exception;
	}

	@Override
	public Throwable getCause() {
		return exception;
	}

	@Override
	public String getDescription() {
		return exception.getLocalizedMessage();
	}

}
