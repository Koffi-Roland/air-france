package com.afklm.repind.msv.manage.external.identifier.models.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
/*
 * A class who contain all our potential error
 */
public enum BusinessError implements IError {

    ALL_DATA_NOT_FOUND(new RestError(ErrorMessage.EXTERNAL_IDENTIFIER_ERROR_404, HttpStatus.NOT_FOUND)),
    PARAMETER_GIN_INVALID(new RestError(ErrorMessage.PARAMETER_GIN_INVALID_ERROR_MESSAGE_400, HttpStatus.BAD_REQUEST)),
    MISSING_PARAMETER_IDENTIFIER(new RestError(ErrorMessage.MISSING_IDENTIFIER_ERROR_MESSAGE_400, HttpStatus.BAD_REQUEST)),
    MISSING_PARAMETER_TYPE(new RestError(ErrorMessage.MISSING_TYPE_ERROR_MESSAGE_400, HttpStatus.BAD_REQUEST)),
    WRONG_PARAMETER_TYPE(new RestError(ErrorMessage.WRONG_TYPE_ERROR_MESSAGE_400, HttpStatus.BAD_REQUEST));

    private final RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}