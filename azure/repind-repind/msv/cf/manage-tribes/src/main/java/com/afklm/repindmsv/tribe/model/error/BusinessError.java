package com.afklm.repindmsv.tribe.model.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum BusinessError implements IError {

    API_MISSING_REQUEST_PARAMETER(new RestError(ErrorMessage.ERROR_MESSAGE_400_000, HttpStatus.BAD_REQUEST)),

    GIN_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_001 , HttpStatus.BAD_REQUEST)),

    NAME_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_002 , HttpStatus.BAD_REQUEST)),

    TYPE_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_003 , HttpStatus.BAD_REQUEST)),

    MANAGER_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_004 , HttpStatus.BAD_REQUEST)),

    APPLICATION_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_005 , HttpStatus.BAD_REQUEST)),

    TRIBE_ID_IS_MISSING(new RestError(ErrorMessage.ERROR_MESSAGE_400_006, HttpStatus.BAD_REQUEST)),

    STATUS_IS_MISSING(new RestError(ErrorMessage.ERROR_MESSAGE_400_007, HttpStatus.BAD_REQUEST)),

    API_TRIBE_NOT_FOUND(new RestError(ErrorMessage.ERROR_MESSAGE_404_002, HttpStatus.NOT_FOUND)),

    API_MEMBER_NOT_FOUND(new RestError(ErrorMessage.ERROR_MESSAGE_404_003, HttpStatus.NOT_FOUND)),

    API_PARAMETER_TYPE_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_001, HttpStatus.NOT_FOUND)),


    GIN_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_002, HttpStatus.PRECONDITION_FAILED)),

    STATUS_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_008, HttpStatus.PRECONDITION_FAILED)),
    API_INVALID_ID(new RestError(ErrorMessage.ERROR_MESSAGE_412_018, HttpStatus.PRECONDITION_FAILED)),

    API_CONSTRAINT_VIOLATION(new RestError(ErrorMessage.ERROR_MESSAGE_412_000, HttpStatus.PRECONDITION_FAILED)),



    API_BUSINESS_ERROR(new RestError(ErrorMessage.API_BUSINESS_ERROR,HttpStatus.PRECONDITION_FAILED));
    ;


    @Getter  private RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }

}
