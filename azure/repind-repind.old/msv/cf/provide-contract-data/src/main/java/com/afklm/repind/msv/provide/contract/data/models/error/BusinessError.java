package com.afklm.repind.msv.provide.contract.data.models.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
/*
 * A class who contain all our potential error
 */
public enum BusinessError implements IError {

    CONTRACT_NOT_FOUND(new RestError(ErrorMessage.CONTRACT_ERROR_MESSAGE_404 , HttpStatus.NOT_FOUND)),
    MISSING_PARAMETER(new RestError(ErrorMessage.MISSING_PARAMETER_403,HttpStatus.FORBIDDEN)),
    PARAMETER_TYPE_INVALID(new RestError(ErrorMessage.PARAMETER_TYPE_INVALID_403,HttpStatus.FORBIDDEN)),
    PARAMETER_CIN_INVALID(new RestError(ErrorMessage.PARAMETER_CIN_INVALID_403,HttpStatus.FORBIDDEN)),
    PARAMETER_GIN_INVALID(new RestError(ErrorMessage.PARAMETER_GIN_INVALID_403, HttpStatus.FORBIDDEN));

    private final RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}