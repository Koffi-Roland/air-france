package com.afklm.repind.common.model.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FormatError implements IError {

    API_EMAIL_MISMATCH(new RestError(FormatErrorMessage.ERROR_MESSAGE_412_001 , HttpStatus.PRECONDITION_FAILED)),
    API_CIN_MISMATCH(new RestError(FormatErrorMessage.ERROR_MESSAGE_412_002 , HttpStatus.PRECONDITION_FAILED));

    private RestError restError;

    FormatError(RestError restError) {
        this.restError = restError;
    }
}