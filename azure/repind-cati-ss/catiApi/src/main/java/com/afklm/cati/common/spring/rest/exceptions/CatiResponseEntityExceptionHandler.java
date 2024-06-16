package com.afklm.cati.common.spring.rest.exceptions;

import com.afklm.cati.common.util.DozerListsMapper;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class CatiResponseEntityExceptionHandler {

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    private DozerListsMapper<FieldError, ValidationErrorResource> errorListsMapper = new DozerListsMapper<FieldError, ValidationErrorResource>();

    @ExceptionHandler(value = {DefaultCatiException.class})
    protected ResponseEntity<ErrorResource> handleConflict(DefaultCatiException ex, WebRequest request) {
        return new ResponseEntity<ErrorResource>(ex.getErrorResource(), ex.getErrorResource().getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResource processValidationError(RuntimeException ex) {
        return new ErrorResource("uncatched exception", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorListResource processValidationError(MethodArgumentNotValidException ex) {
        List<ValidationErrorResource> validationErrorResources = errorListsMapper.mapEntityIterableToBeanList(dozerBeanMapper, ex.getBindingResult().getFieldErrors(), ValidationErrorResource.class);
        return new ErrorListResource(validationErrorResources, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorListResource processValidationError(ConstraintViolationException ex) {
        List<ValidationErrorResource> validationErrorResources = errorListsMapper.mapEntitySetToBeanList(dozerBeanMapper, ex.getConstraintViolations(), ValidationErrorResource.class);
        return new ErrorListResource(validationErrorResources, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResource processValidationError(org.springframework.security.access.AccessDeniedException ex) {
        return new ErrorResource("uncatched exception", ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
