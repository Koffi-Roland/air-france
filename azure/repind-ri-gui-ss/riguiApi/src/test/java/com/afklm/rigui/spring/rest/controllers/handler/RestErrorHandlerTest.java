package com.afklm.rigui.spring.rest.controllers.handler;

import com.afklm.rigui.model.error.FullRestError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.net.URISyntaxException;

@ExtendWith(SpringExtension.class)
class RestErrorHandlerTest {
/* TODO: TEST ERROR BUT WITH A MVC : https://www.baeldung.com/spring-rest-template-error-handling
    private final RestErrorHandler restErrorHandler = new RestErrorHandler();

    @Test
    public void processError() {
        Exception e = new Exception();
        ResponseEntity<FullRestError> error = restErrorHandler.processError(e);
    }

    @Test
    public void handleBindException() throws URISyntaxException {
        BindException bindException = new BindException("target", "name");
        RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, new URI("url"));
        request.
    restErrorHandler.handleBindException(bindException);
        //restErrorHandler.handleBindException();
    }

    @Test
    public void handleHttpMessageNotReadable() {
        restErrorHandler.handleHttpMessageNotReadable();
    }

    @Test
    public void handleHttpMediaTypeNotSupported() {
        restErrorHandler.handleHttpMediaTypeNotSupported();
    }

    @Test
    public void handleMethodArgumentNotValid() {
        restErrorHandler.handleMethodArgumentNotValid();
    }

    @Test
    public void handleMissingServletRequestParameter() {
        restErrorHandler.handleMissingServletRequestParameter();
    }*/
}
