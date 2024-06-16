package com.afklm.repind.common.model.error;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class SystemErrorEnumTest {

    @Test
    void testEnum() {
        assertEquals(SystemError.API_SYSTEM_INTERNAL_SERVER_ERROR.getRestError().getHttpStatus(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_001, HttpStatus.INTERNAL_SERVER_ERROR).getHttpStatus());
        assertEquals(SystemError.API_SYSTEM_INTERNAL_SERVER_ERROR.getRestError().getDescription(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_001, HttpStatus.INTERNAL_SERVER_ERROR).getDescription());
        //
        assertEquals(SystemError.API_SYSTEM_NO_HANDLER_FOUND.getRestError().getHttpStatus(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_002, HttpStatus.INTERNAL_SERVER_ERROR).getHttpStatus());
        assertEquals(SystemError.API_SYSTEM_NO_HANDLER_FOUND.getRestError().getDescription(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_002, HttpStatus.INTERNAL_SERVER_ERROR).getDescription());
        //
        assertEquals(SystemError.API_SYSTEM_NO_SUCH_REQUEST_HANDLING_METHOD.getRestError().getHttpStatus(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_003, HttpStatus.INTERNAL_SERVER_ERROR).getHttpStatus());
        assertEquals(SystemError.API_SYSTEM_NO_SUCH_REQUEST_HANDLING_METHOD.getRestError().getDescription(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_003, HttpStatus.INTERNAL_SERVER_ERROR).getDescription());
        //
        assertEquals(SystemError.API_SYSTEM_UNSUPPORTED_CONTENT_TYPE.getRestError().getHttpStatus(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_004, HttpStatus.UNSUPPORTED_MEDIA_TYPE).getHttpStatus());
        assertEquals(SystemError.API_SYSTEM_UNSUPPORTED_CONTENT_TYPE.getRestError().getDescription(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_004, HttpStatus.UNSUPPORTED_MEDIA_TYPE).getDescription());
        //
        assertEquals(SystemError.API_SYSTEM_MESSAGE_NOT_READABLE.getRestError().getHttpStatus(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_005, HttpStatus.INTERNAL_SERVER_ERROR).getHttpStatus());
        assertEquals(SystemError.API_SYSTEM_MESSAGE_NOT_READABLE.getRestError().getDescription(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_005, HttpStatus.INTERNAL_SERVER_ERROR).getDescription());
        //
        assertEquals(SystemError.API_SYSTEM_FAULT.getRestError().getHttpStatus(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_006, HttpStatus.INTERNAL_SERVER_ERROR).getHttpStatus());
        assertEquals(SystemError.API_SYSTEM_FAULT.getRestError().getDescription(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_006, HttpStatus.INTERNAL_SERVER_ERROR).getDescription());
        //
        assertEquals(SystemError.API_SYSTEM_UNKNOW_ERROR.getRestError().getHttpStatus(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_007, HttpStatus.INTERNAL_SERVER_ERROR).getHttpStatus());
        assertEquals(SystemError.API_SYSTEM_UNKNOW_ERROR.getRestError().getDescription(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_007, HttpStatus.INTERNAL_SERVER_ERROR).getDescription());
        //
        assertEquals(SystemError.API_CLIENT_CALL_ERROR.getRestError().getHttpStatus(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_008, HttpStatus.INTERNAL_SERVER_ERROR).getHttpStatus());
        assertEquals(SystemError.API_CLIENT_CALL_ERROR.getRestError().getDescription(),
                new RestError(SystemErrorMessage.ERROR_MESSAGE_SYSTEM_008, HttpStatus.INTERNAL_SERVER_ERROR).getDescription());
    }
}
