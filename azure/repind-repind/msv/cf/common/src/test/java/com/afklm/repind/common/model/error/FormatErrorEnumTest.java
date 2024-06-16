package com.afklm.repind.common.model.error;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class FormatErrorEnumTest {

    @Test
    void testEnum() {
        assertEquals(FormatError.API_CIN_MISMATCH.getRestError().getHttpStatus(),
                new RestError(FormatErrorMessage.ERROR_MESSAGE_412_002, HttpStatus.PRECONDITION_FAILED).getHttpStatus());
        assertEquals(FormatError.API_CIN_MISMATCH.getRestError().getDescription(),
                new RestError(FormatErrorMessage.ERROR_MESSAGE_412_002, HttpStatus.PRECONDITION_FAILED).getDescription());
        //
        assertEquals(FormatError.API_EMAIL_MISMATCH.getRestError().getHttpStatus(),
                new RestError(FormatErrorMessage.ERROR_MESSAGE_412_001, HttpStatus.PRECONDITION_FAILED).getHttpStatus());
        assertEquals(FormatError.API_EMAIL_MISMATCH.getRestError().getDescription(),
                new RestError(FormatErrorMessage.ERROR_MESSAGE_412_001, HttpStatus.PRECONDITION_FAILED).getDescription());
    }
}
