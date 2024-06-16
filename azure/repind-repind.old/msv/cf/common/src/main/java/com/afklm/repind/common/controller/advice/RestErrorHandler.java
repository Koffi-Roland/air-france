package com.afklm.repind.common.controller.advice;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.model.error.ApiError;
import com.afklm.repind.common.model.error.ErrorDetail;
import com.afklm.repind.common.model.error.FullRestError;
import com.afklm.repind.common.model.error.SystemError;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class RestErrorHandler extends ResponseEntityExceptionHandler {

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


   // @ExceptionHandler(BindException.class)
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
                                                         final HttpStatus status, final WebRequest request) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        final List<ErrorDetail> errors = getErrorMessageList(ex.getBindingResult());

        return new ResponseEntity<>(
                new FullRestError.Builder().setRestError(ApiError.API_CONSTRAINT_VIOLATION.getRestError())
                        .setErrorMessage(errors).setErrorMessage(errors).setStatus(status).build(),
                headers,
                status);

    }

    //@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
                                                                     final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        final String unsupported = "Unsupported content type: " + ex.getContentType();
        final String supported = "Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());

        return new ResponseEntity<>(new FullRestError.Builder()
                .setRestError(
                        SystemError.API_SYSTEM_UNSUPPORTED_CONTENT_TYPE.getRestError().withDescription(unsupported))
                .setTechnicalDetails(unsupported + " - " + supported).setStatus(status).build(), headers, status);
    }

   // @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
                                                                  final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        final Throwable mostSpecificCause = ex.getMostSpecificCause();
        String message;
        if (mostSpecificCause.getClass() != null) {
            message = mostSpecificCause.getClass().getName() + ":" + mostSpecificCause.getMessage();
            //We delete value in message returned to avoid security issue
            if (((InvalidFormatException) mostSpecificCause).getValue() != null) {
                message = StringUtils.remove(message,
                        ((InvalidFormatException) mostSpecificCause).getValue().toString());
            }
        } else {
            message = ex.getMessage();
        }
        return new ResponseEntity<>(
                new FullRestError.Builder().setRestError(SystemError.API_SYSTEM_MESSAGE_NOT_READABLE.getRestError())
                        .setTechnicalDetails(message).setStatus(status).build(),
                headers,
                status);

    }

    //@ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        final List<ErrorDetail> errors = getErrorMessageList(ex.getBindingResult());

        return new ResponseEntity<>(
                new FullRestError.Builder().setRestError(ApiError.API_CONSTRAINT_VIOLATION.getRestError())
                        .setErrorMessage(errors).setStatus(status).build(), headers,
                status);

    }

    //  @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
            final WebRequest request) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new FullRestError.Builder()
                .setRestError(
                        ApiError.API_MISSING_REQUEST_PARAMETER.getRestError().withDescription(ex.getMessage()))
                .setThrowable(ex).setStatus(status).build(), headers, status);
    }

    // @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
                                                                   final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                new FullRestError.Builder().setRestError(SystemError.API_SYSTEM_NO_HANDLER_FOUND.getRestError())
                        .setTechnicalDetails(ex.getLocalizedMessage()).setThrowable(ex)
                        .setRequestedURI(ex.getRequestURL()).setStatus(status).build(),
                headers,
                status);
    }

    //@ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException e, final HttpHeaders headers,
                                                        final HttpStatus status, final WebRequest request) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ServletWebRequest servlet = (ServletWebRequest) request;
        String uri = servlet.getRequest().getRequestURI();
        if (servlet.getRequest().getQueryString() != null && servlet.getRequest().getQueryString().length() > 0) {
            uri += "?" + servlet.getRequest().getQueryString();
        }
        return new ResponseEntity<>(new FullRestError.Builder()
                .setRestError(ApiError.API_CONSTRAINT_VIOLATION.getRestError()
                        .withDescription("Invalid type parameter value '" + e.getValue() + "' must be a "
                                + e.getRequiredType() + " on request " + uri))
                .setThrowable(e).setStatus(status).build(), headers, status);
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.error("Unexpected Exception thrown : " +e.getMessage(),e);
        return new ResponseEntity<>(new FullRestError.Builder()
                .setRestError(SystemError.API_SYSTEM_INTERNAL_SERVER_ERROR.getRestError().withDescription(e.toString()))
                .setThrowable(e).setStatus(HttpStatus.INTERNAL_SERVER_ERROR).build(),headers, HttpStatus.INTERNAL_SERVER_ERROR);
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        log.error("Business Exception thrown : " +e.getMessage(),e);
        return new ResponseEntity<>(
                new FullRestError.Builder().setRestError(e.getRestError()).setStatus(e.getStatus()).build(),
                headers,
                e.getStatus());
    }

}
