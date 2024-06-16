package com.afklm.rigui.spring.rest.exceptions;

import java.util.List;




import javax.validation.ConstraintViolationException;

import org.dozer.DozerBeanMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.afklm.rigui.util.DozerListsMapper;

/**
 * Exception handler
 * @author m405991
 *
 */
@ControllerAdvice
public class RiguiResponseEntityExceptionHandler {

	@Autowired
	private DozerBeanMapper dozerBeanMapper;
	
	private DozerListsMapper<FieldError,ValidationErrorResource> errorListsMapper = new DozerListsMapper<FieldError,ValidationErrorResource>();
	
    /**
     * DefaultSic2Exception handling
     * @param ex
     * @return ErrorResource
     */
    @ExceptionHandler(value = { DefaultRiguiException.class })
    public ResponseEntity<ErrorResource> handleConflict(DefaultRiguiException ex) {
        return new ResponseEntity<ErrorResource>(ex.getErrorResource(), ex.getErrorResource().getStatus());
    }
    
    /**
     * RuntimeException handling
     * @param ex
     * @return ErrorResource
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResource processValidationError(RuntimeException ex) {
    	return new ErrorResource("uncatched exception",ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * MethodArgumentNotValidException handling
     * @param ex
     * @return ErrorListResource
     */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorListResource processValidationError(MethodArgumentNotValidException ex) {
		List<ValidationErrorResource> validationErrorResources = errorListsMapper.mapEntityIterableToBeanList(dozerBeanMapper, ex.getBindingResult().getFieldErrors(),ValidationErrorResource.class);
        return new ErrorListResource(validationErrorResources, HttpStatus.BAD_REQUEST);
	}

	/**
	 * ConstraintViolationException handling
	 * @param ex
	 * @return ErrorListResource
	 */
	@ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorListResource processValidationError(ConstraintViolationException ex) {
		List<ValidationErrorResource> validationErrorResources = errorListsMapper.mapEntitySetToBeanList(dozerBeanMapper, ex.getConstraintViolations(), ValidationErrorResource.class); 
        return new ErrorListResource(validationErrorResources, HttpStatus.BAD_REQUEST);
    }
	
	/**
	 * AccessDeniedException handling
	 * @param ex
	 * @return ErrorResource
	 */
	@ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorResource processValidationError(org.springframework.security.access.AccessDeniedException ex) {
		return new ErrorResource("uncatched exception", ex.getMessage(), HttpStatus.UNAUTHORIZED);
	}
    
}
