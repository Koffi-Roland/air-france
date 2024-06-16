package com.afklm.repind.common.exception;

import com.afklm.repind.common.model.error.ApiError;
import com.afklm.repind.common.model.error.ApiErrorMessage;
import com.afklm.repind.common.model.error.RestError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class BusinessExceptionTest {

    @Test
    void testClass() {
        BusinessException exception = new BusinessException(ApiError.API_MISSING_REQUEST_PARAMETER);
        assertEquals(ApiError.API_MISSING_REQUEST_PARAMETER.getRestError().getHttpStatus(), exception.getStatus());
        BusinessException exceptionWithThrow = new BusinessException(ApiError.API_CONSTRAINT_VIOLATION, new Throwable("message"));
        assertEquals("message", exceptionWithThrow.getCause().getMessage());
    }
}

