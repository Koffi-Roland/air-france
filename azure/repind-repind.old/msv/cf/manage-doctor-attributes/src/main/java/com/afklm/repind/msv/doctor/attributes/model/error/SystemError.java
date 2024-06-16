package com.afklm.repind.msv.doctor.attributes.model.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum SystemError implements IError {

    API_SYSTEM_INTERNAL_SERVER_ERROR( new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_001 , HttpStatus.INTERNAL_SERVER_ERROR)),

    API_SYSTEM_NO_HANDLER_FOUND(
			new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_002 , HttpStatus.INTERNAL_SERVER_ERROR)),

    API_SYSTEM_NO_SUCH_REQUEST_HANDLING_METHOD(
			new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_003 , HttpStatus.INTERNAL_SERVER_ERROR)),

    API_SYSTEM_UNSUPPORTED_CONTENT_TYPE(
			new RestError( ErrorMessage.ERROR_MESSAGE_SYSTEM_004 ,HttpStatus.UNSUPPORTED_MEDIA_TYPE)),

    API_SYSTEM_MESSAGE_NOT_READABLE(
			new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_005 , HttpStatus.INTERNAL_SERVER_ERROR)),

    API_SYSTEM_FAULT(new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_006, HttpStatus.INTERNAL_SERVER_ERROR)),

    API_SYSTEM_UNKNOW_ERROR(new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_007 , HttpStatus.INTERNAL_SERVER_ERROR)),

    SYSTEM_UNEXPECTED_ERROR(new RestError(ErrorMessage.ERROR_MESSAGE_SYSTEM_008 , HttpStatus.INTERNAL_SERVER_ERROR));

    @Getter  private RestError restError;

    SystemError(RestError restError) {
        this.restError = restError;
    }
}
