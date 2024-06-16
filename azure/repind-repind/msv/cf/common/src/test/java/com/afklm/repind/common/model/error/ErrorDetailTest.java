package com.afklm.repind.common.model.error;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorDetailTest {

    @Test
    void testClass() {
        ErrorDetail error = new ErrorDetail();
        error.setCode("code");
        error.setField("field");
        error.setDefaultMessage("default");
        error.setRejectedValue("my value");
        assertEquals("code", error.getCode());
        assertEquals("field", error.getField());
        assertEquals("default", error.getDefaultMessage());
        assertEquals("my value", error.getRejectedValue());
    }
}
