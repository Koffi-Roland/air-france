package com.afklm.repind.msv.delete.myAccount.model.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessError implements IError {

    API_GIN_IS_MISSING(new RestError( ErrorMessage.ERROR_MESSAGE_400_001 , HttpStatus.BAD_REQUEST)),
    CANNOT_DELETE_AS_OTHER_CONTRACT_EXIST(new RestError(ErrorMessage.ERROR_MESSAGE_403_001, HttpStatus.FORBIDDEN)),
    ACCOUNT_DATA_NOT_FOUND(new RestError(ErrorMessage.ERROR_MESSAGE_404_001 , HttpStatus.NOT_FOUND)),
    API_GIN_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_001 , HttpStatus.PRECONDITION_FAILED));



    private RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}