package com.afklm.repind.msv.provide.preference.data.models.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessError implements IError {

    COMM_PREFS_NOT_FOUND(new RestError(ErrorMessage.COMM_PREFS_ERROR_MESSAGE_404 , HttpStatus.NOT_FOUND)),
    PREFS_NOT_FOUND(new RestError(ErrorMessage.PREFS_ERROR_MESSAGE_404 , HttpStatus.NOT_FOUND)),
    GENERAL_NOT_FOUND(new RestError(ErrorMessage.GENERAL_ERROR_MESSAGE_404 , HttpStatus.NOT_FOUND)),
    GIN_FORMAT_WRONG(new RestError(ErrorMessage.GIN_FORMAT_WRONG_404 , HttpStatus.NOT_FOUND));

    private RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}
