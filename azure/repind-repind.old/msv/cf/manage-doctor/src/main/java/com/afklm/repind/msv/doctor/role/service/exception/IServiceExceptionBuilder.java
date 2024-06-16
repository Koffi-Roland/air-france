package com.afklm.repind.msv.doctor.role.service.exception;

import com.afklm.repind.common.model.error.RestError;
import org.springframework.http.HttpStatus;

public interface IServiceExceptionBuilder {

    /**
     * Get the cause
     *
     * @return Throwable
     */
    public Throwable getCause();

    /**
     * Get the description
     *
     * @return the description
     */
    public String getDescription();

    /**
     * Get http status
     *
     * @return HttpStatus
     */
    public HttpStatus getHttpStatus();

    /**
     * Get the Rest Error
     *
     * @return RestError
     */
    public RestError getRestError();
}
