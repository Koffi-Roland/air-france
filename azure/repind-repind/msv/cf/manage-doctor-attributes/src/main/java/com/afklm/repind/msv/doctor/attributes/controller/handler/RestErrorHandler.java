package com.afklm.repind.msv.doctor.attributes.controller.handler;

import com.afklm.repind.msv.doctor.attributes.model.error.BusinessError;
import com.afklm.repind.msv.doctor.attributes.model.error.ErrorDetail;
import com.afklm.repind.msv.doctor.attributes.model.error.FullRestError;
import com.afklm.repind.msv.doctor.attributes.model.error.SystemError;
import com.afklm.repind.msv.doctor.attributes.utils.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception Handler management
 *
 * @author t528182
 *
 */
@ControllerAdvice
@Slf4j
public class RestErrorHandler {

	/**
	 * Get errors from BindingResult
	 * 
	 * @param bindingResult
	 * @return List<ErrorMessage>
	 */
	protected List<ErrorDetail> getErrorMessageList(final BindingResult bindingResult) {

		final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		final List<ObjectError> globalErrors = bindingResult.getGlobalErrors();
		final List<ErrorDetail> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
		ErrorDetail error;
		for (final FieldError fieldError : fieldErrors) {
			error = new ErrorDetail();
			error.setCode(fieldError.getCode());
			error.setDefaultMessage(fieldError.getDefaultMessage());
			error.setField(fieldError.getField());
			error.setRejectedValue(fieldError.getRejectedValue());
			errors.add(error);
		}
		for (final ObjectError objectError : globalErrors) {
			error = new ErrorDetail();
			error.setCode(objectError.getCode());
			error.setDefaultMessage(objectError.getDefaultMessage());
			error.setField(objectError.getObjectName());
			error.setRejectedValue(null);
			errors.add(error);
		}

		return errors;
	}

	@ExceptionHandler(BindException.class)
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {

		final List<ErrorDetail> errors = getErrorMessageList(ex.getBindingResult());

		return new ResponseEntity<>(
				new FullRestError.Builder().setRestError(BusinessError.API_CONSTRAINT_VIOLATION.getRestError())
						.setErrorMessage(errors).setErrorMessage(errors).setStatus(status).build(),
				status);

	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		final String unsupported = "Unsupported content type: " + ex.getContentType();
		final String supported = "Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());

		return new ResponseEntity<>(new FullRestError.Builder()
				.setRestError(
						SystemError.API_SYSTEM_UNSUPPORTED_CONTENT_TYPE.getRestError().withDescription(unsupported))
				.setTechnicalDetails(unsupported + " - " + supported).setStatus(status).build(), status);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		final Throwable mostSpecificCause = ex.getMostSpecificCause();
		String message;
		if (mostSpecificCause.getClass() != null) {
			message = mostSpecificCause.getClass().getName() + ":" + mostSpecificCause.getMessage();
		} else {
			message = ex.getMessage();
		}
		return new ResponseEntity<>(
				new FullRestError.Builder().setRestError(SystemError.API_SYSTEM_MESSAGE_NOT_READABLE.getRestError())
						.setTechnicalDetails(message).setStatus(status).build(),
				status);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		final List<ErrorDetail> errors = getErrorMessageList(ex.getBindingResult());

		return new ResponseEntity<>(
				new FullRestError.Builder().setRestError(BusinessError.API_CONSTRAINT_VIOLATION.getRestError())
						.setErrorMessage(errors).setStatus(status).build(),
				status);

	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {

		return new ResponseEntity<>(new FullRestError.Builder()
				.setRestError(
						BusinessError.API_MISSING_REQUEST_PARAMETER.getRestError().withDescription(ex.getMessage()))
				.setThrowable(ex).setStatus(status).build(), status);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return new ResponseEntity<>(
				new FullRestError.Builder().setRestError(SystemError.API_SYSTEM_NO_HANDLER_FOUND.getRestError())
						.setTechnicalDetails(ex.getLocalizedMessage()).setThrowable(ex)
						.setRequestedURI(ex.getRequestURL()).setStatus(status).build(),
				status);
	}

	@ExceptionHandler(TypeMismatchException.class)
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException e, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {

		final ServletWebRequest servlet = (ServletWebRequest) request;
		String uri = servlet.getRequest().getRequestURI();
		if (servlet.getRequest().getQueryString() != null && servlet.getRequest().getQueryString().length() > 0) {
			uri += "?" + servlet.getRequest().getQueryString();
		}
		return new ResponseEntity<>(new FullRestError.Builder()
				.setRestError(BusinessError.API_TYPE_MISMATCH.getRestError()
						.withDescription("Invalid type parameter value '" + e.getValue() + "' must be a "
								+ e.getRequiredType() + " on request " + uri))
				.setThrowable(e).setStatus(status).build(), status);
	}

	/**
	 * processError from an Exception
	 * 
	 * @param e
	 *            Exception
	 * @return ResponseEntity<FullRestError>
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<FullRestError> processError(final Exception e) {
		log.error("Unexpected Exception thrown : " + e.getMessage(),e);
		return new ResponseEntity<>(new FullRestError.Builder()
				.setRestError(SystemError.API_SYSTEM_INTERNAL_SERVER_ERROR.getRestError().withDescription(e.toString()))
				.setThrowable(e).setStatus(HttpStatus.INTERNAL_SERVER_ERROR).build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * processError from an ServiceException
	 * 
	 * @param e
	 *            ServiceException
	 * @return ResponseEntity<FullRestError>
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<FullRestError> processError(final BusinessException e) {
		log.error("Business Exception thrown : " +e.getMessage(),e);
		return new ResponseEntity<>(
				new FullRestError.Builder().setRestError(e.getRestError()).setStatus(e.getStatus()).build(),
				e.getStatus());
	}

}
