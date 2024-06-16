package com.afklm.repind.common.model.error;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class FullRestErrorTest {

    @Test
    void testClass() {
        // setup
        ErrorDetail detail1 = new ErrorDetail();
        detail1.setDefaultMessage("default1");
        ErrorDetail detail2 = new ErrorDetail();
        detail2.setDefaultMessage("default2");
        Throwable throwable = new Throwable("messageThrow");
        // build
        FullRestError error = new FullRestError.Builder()
                .setStatus(HttpStatus.ACCEPTED)
                .setRestError(new RestError(ApiErrorMessage.ERROR_MESSAGE_400, HttpStatus.BAD_REQUEST))
                .setErrorMessage(List.of(detail1, detail2))
                .setRequestedURI("requestedUri")
                .setThrowable(throwable)
                .setTechnicalDetails("technicalDetails")
                .build();
        assertEquals(HttpStatus.ACCEPTED, error.getStatus());
        assertEquals(HttpStatus.BAD_REQUEST, error.getError().getHttpStatus());
        assertEquals(ApiErrorMessage.ERROR_MESSAGE_400.getDescription(), error.getError().getDescription());
        assertEquals("default1", error.getErrorMessage().get(0).getDefaultMessage());
        assertEquals("default2", error.getErrorMessage().get(1).getDefaultMessage());
        assertEquals("requestedUri", error.getRequestedURI());
        assertEquals("technicalDetails", error.getTechnicalDetails());
        assertEquals("messageThrow", error.getThrowable().getMessage());
    }
}
