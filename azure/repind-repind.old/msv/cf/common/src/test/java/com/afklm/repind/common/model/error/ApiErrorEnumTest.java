package com.afklm.repind.common.model.error;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ApiErrorEnumTest {

    @Test
    void testEnum() {
        assertEquals(ApiError.API_CONSTRAINT_VIOLATION.getRestError().getHttpStatus(),
                new RestError(ApiErrorMessage.ERROR_MESSAGE_412, HttpStatus.PRECONDITION_FAILED).getHttpStatus());
        assertEquals(ApiError.API_CONSTRAINT_VIOLATION.getRestError().getDescription(),
                new RestError(ApiErrorMessage.ERROR_MESSAGE_412, HttpStatus.PRECONDITION_FAILED).getDescription());
        //
        assertEquals(ApiError.API_MISSING_REQUEST_PARAMETER.getRestError().getHttpStatus(),
                new RestError(ApiErrorMessage.ERROR_MESSAGE_400, HttpStatus.BAD_REQUEST).getHttpStatus());
        assertEquals(ApiError.API_MISSING_REQUEST_PARAMETER.getRestError().getDescription(),
                new RestError(ApiErrorMessage.ERROR_MESSAGE_400, HttpStatus.BAD_REQUEST).getDescription());
    }
}

