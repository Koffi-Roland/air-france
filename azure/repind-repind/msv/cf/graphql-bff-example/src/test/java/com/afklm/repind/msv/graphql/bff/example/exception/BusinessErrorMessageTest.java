package com.afklm.repind.msv.graphql.bff.example.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test business error response
 */
@ExtendWith(SpringExtension.class)
public class BusinessErrorMessageTest {

    /**
     * Business precondition status
     */
    private static final String PRECONDITION = "PRECONDITION_FAILED";
    /**
     * Bad request status
     */
    private static final String BAD_REQUEST = "BAD_REQUEST";

    @InjectMocks
    private BusinessErrorMessageResolver businessErrorMessageResolver;

    @Test
    @DisplayName("handle precondition test")
    public void handlePreconditionTest()
    {
       String status = businessErrorMessageResolver.handlePrecondition(PRECONDITION);
        assertNotEquals(null, status);
        assertEquals(BAD_REQUEST,status);

    }

}
