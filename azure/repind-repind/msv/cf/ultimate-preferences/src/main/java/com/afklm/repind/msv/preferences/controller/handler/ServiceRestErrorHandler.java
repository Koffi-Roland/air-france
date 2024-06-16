package com.afklm.repind.msv.preferences.controller.handler;

import com.afklm.repind.common.controller.advice.RestErrorHandler;
import com.afklm.repind.msv.preferences.model.error.FullRestError;
import com.afklm.repind.msv.preferences.services.exception.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception Handler management
 *
 * @author t528182
 *
 */
@ControllerAdvice
public class ServiceRestErrorHandler extends RestErrorHandler {

    /**
     * processError from an ServiceException
     *
     * @param e
     *            ServiceException
     * @return ResponseEntity<FullRestError>
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<FullRestError> processError(final ServiceException e) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                new FullRestError.Builder().setRestError(e.getRestError()).setStatus(e.getStatus()).build(),
                headers,
                e.getStatus());
    }

}