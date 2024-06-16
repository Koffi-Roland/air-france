package com.afklm.repind.msv.provide.identification.data.models.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
/*
 * A class who contain all our potential error
 */
public enum BusinessError implements IError {

    ACCOUNT_NOT_FOUND(new RestError(ErrorMessage.ACCOUNT_ERROR_MESSAGE_404 , HttpStatus.NOT_FOUND)),
    DELEGATION_NOT_FOUND(new RestError(ErrorMessage.DELEGATION_ERROR_MESSAGE_404 , HttpStatus.NOT_FOUND)),
    IDENTIFICATION_NOT_FOUND(new RestError(ErrorMessage.IDENTIFICATION_ERROR_MESSAGE_404 , HttpStatus.NOT_FOUND)),
    GENERAL_NOT_FOUND(new RestError(ErrorMessage.GENERAL_ERROR_MESSAGE_404 , HttpStatus.NOT_FOUND)),

    PARAMETER_GIN_INVALID(new RestError(ErrorMessage.PARAMETER_GIN_INVALID_ERROR_MESSAGE_404, HttpStatus.NOT_FOUND));

    private RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}