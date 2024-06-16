package com.afklm.repind.msv.search.individual.model.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessError implements IError {

    //@formatter:off
    API_MISSING_REQUEST_PARAMETER(new RestError(ErrorMessage.ERROR_MESSAGE_400 , HttpStatus.BAD_REQUEST)),
    API_CONSTRAINT_VIOLATION(new RestError( ErrorMessage.ERROR_MESSAGE_412 , HttpStatus.PRECONDITION_FAILED)),
    API_GIN_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_001, HttpStatus.PRECONDITION_FAILED)),
    API_CIN_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_002, HttpStatus.PRECONDITION_FAILED)),
    API_EMAIL_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_003 , HttpStatus.PRECONDITION_FAILED)),
    API_SOCIAL_IDENTIFIER_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_005 , HttpStatus.PRECONDITION_FAILED)),
    API_SOCIAL_TYPE_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_006 , HttpStatus.PRECONDITION_FAILED)),
    API_LASTNAME_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_007 , HttpStatus.PRECONDITION_FAILED)),
    API_FIRSTNAME_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_008 , HttpStatus.PRECONDITION_FAILED));

    //@formatter:on

    private RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}