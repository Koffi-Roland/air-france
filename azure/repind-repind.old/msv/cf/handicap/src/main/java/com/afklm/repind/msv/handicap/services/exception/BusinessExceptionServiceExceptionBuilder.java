package com.afklm.repind.msv.handicap.services.exception;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.http.HttpStatus;

import com.afklm.repind.msv.handicap.model.error.BusinessErrorList;
import com.afklm.repind.msv.handicap.model.error.RestError;

/**
 * BusinessExceptionServiceExceptionBuilder
 *
 * @author m312812
 *
 */
public class BusinessExceptionServiceExceptionBuilder extends AbstractServiceExceptionBuilder {

	/**
	 * Constructor from an Exception
	 * 
	 * @param exception
	 */
	public BusinessExceptionServiceExceptionBuilder(final Exception exception) {
		super(exception);
	}

	@Override
	public String getDescription() {
		return getFaultCode() + ":" + getFaultDescription();
	}

	protected String getFaultCode() {
		// exception.getFaultInfo().getErrorCode().value()
		final Object faultInfo = getFaultInfo();
		final Object errorCode = invokeMethod(faultInfo, "getErrorCode");
		return invokeMethod(errorCode, "value") + "";
	}

	protected String getFaultDescription() {
		// return exception.getFaultInfo().getFaultDescription();
		final Object faultInfo = getFaultInfo();
		return invokeMethod(faultInfo, "getFaultDescription") + "";
	}

	private Object getFaultInfo() {
		return invokeMethod(getCause(), "getFaultInfo");
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.PRECONDITION_FAILED;
	}

	@Override
	public RestError getRestError() {
		return BusinessErrorList.API_BUSINESS_ERROR.getError();
	}

	private Object invokeMethod(final Object object, final String methodName) {
		try {
			final Method method = object.getClass().getMethod(methodName);
			return method.invoke(object);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new RuntimeException("Unable to invoke method " + methodName + " on " + object, e);
		}
	}
}
