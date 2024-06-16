package com.afklm.repind.msv.manage.individual.identifier.model.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessError implements IError {

    EMAIL_ALREADY_USED_BY_FB_MEMBERS(new RestError(ErrorMessage.ERROR_MESSAGE_400_382 , HttpStatus.BAD_REQUEST)),
    GIN_NOT_FOUND(new RestError(ErrorMessage.ERROR_MESSAGE_404_001 , HttpStatus.NOT_FOUND));



    private RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}