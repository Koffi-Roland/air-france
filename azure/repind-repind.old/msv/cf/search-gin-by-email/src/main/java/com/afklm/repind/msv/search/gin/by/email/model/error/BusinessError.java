package com.afklm.repind.msv.search.gin.by.email.model.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessError implements IError {

    API_EMAIL_IS_MISSING(new RestError(ErrorMessage.ERROR_MESSAGE_400_001 , HttpStatus.BAD_REQUEST)),

    API_EMAIL_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_001 , HttpStatus.PRECONDITION_FAILED));

    private RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}