package com.afklm.repind.msv.search.gin.by.lastname.firstname.model.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessError implements IError {

    API_LASTNAME_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_001 , HttpStatus.PRECONDITION_FAILED)),
    API_FIRSTNAME_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_002 , HttpStatus.PRECONDITION_FAILED));


    private RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}