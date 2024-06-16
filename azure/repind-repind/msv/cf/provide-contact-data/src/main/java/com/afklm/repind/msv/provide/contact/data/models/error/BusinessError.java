package com.afklm.repind.msv.provide.contact.data.models.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessError implements IError {

    EMAILS_NOT_FOUND(new RestError(ErrorMessage.EMAILS_ERROR_MESSAGE_404 , HttpStatus.NOT_FOUND)),
    POSTAL_ADDRESS_NOT_FOUND(new RestError(ErrorMessage.POSTAL_ADDRESS_ERROR_MESSAGE_404 , HttpStatus.NOT_FOUND)),
    TELECOMS_NOT_FOUND(new RestError(ErrorMessage.TELECOMS_ERROR_MESSAGE_404 , HttpStatus.NOT_FOUND)),
    GENERAL_NOT_FOUND(new RestError(ErrorMessage.GENERAL_ERROR_MESSAGE_404 , HttpStatus.NOT_FOUND)),
    GIN_FORMAT_WRONG(new RestError(ErrorMessage.GIN_FORMAT_WRONG_404 , HttpStatus.NOT_FOUND));

    private RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}
