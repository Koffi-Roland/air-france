package com.afklm.repind.common.model.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiError implements IError {

    API_MISSING_REQUEST_PARAMETER(new RestError(ApiErrorMessage.ERROR_MESSAGE_400 , HttpStatus.BAD_REQUEST)),

    API_CONSTRAINT_VIOLATION(new RestError(ApiErrorMessage.ERROR_MESSAGE_412 , HttpStatus.PRECONDITION_FAILED));

    private RestError restError;

    ApiError(RestError restError) {
        this.restError = restError;
    }
}