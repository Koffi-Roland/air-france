package com.afklm.repind.msv.search.gin.by.social.media.model.error;

import com.afklm.repind.common.model.error.IError;
import com.afklm.repind.common.model.error.RestError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BusinessError implements IError {
    /**
     * list of Business Errors
     */

    API_SOCIAL_IDENTIFIER_MISMATCH(new RestError(ErrorMessage.ERROR_MESSAGE_412_001 , HttpStatus.PRECONDITION_FAILED)),

    UNKNOWN_EXTERNAL_ID_TYPE(new RestError(ErrorMessage.ERROR_MESSAGE_404_001 , HttpStatus.NOT_FOUND)),

    GIN_NOT_FOUND(new RestError(ErrorMessage.ERROR_MESSAGE_404 , HttpStatus.NOT_FOUND));

    private RestError restError;

    BusinessError(RestError restError) {
        this.restError = restError;
    }
}