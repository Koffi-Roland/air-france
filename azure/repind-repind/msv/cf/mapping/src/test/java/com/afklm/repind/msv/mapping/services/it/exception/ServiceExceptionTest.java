package com.afklm.repind.msv.mapping.services.it.exception;

import com.afklm.repind.msv.mapping.model.error.ErrorType;
import com.afklm.repind.msv.mapping.model.error.RestError;
import com.afklm.repind.msv.mapping.services.exception.IServiceExceptionBuilder;
import com.afklm.repind.msv.mapping.services.exception.ServiceException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ServiceExceptionTest {

    @Test
    public void testServiceExceptionConstructor() {
        // Create a mock IServiceExceptionBuilder object
        IServiceExceptionBuilder builder = mock(IServiceExceptionBuilder.class);
        when(builder.getCause()).thenReturn(new RuntimeException("Test cause"));
        when(builder.getDescription()).thenReturn("Test description");
        when(builder.getRestError()).thenReturn(new RestError("Test code", "Test message", "description", ErrorType.WARNING));
        when(builder.getHttpStatus()).thenReturn(HttpStatus.valueOf(500));

        // Create a ServiceException object with the mock builder
        ServiceException exception = new ServiceException(builder);

        // Verify that the properties of the exception are set correctly
        Assert.assertEquals("Test cause", exception.getCause().getMessage());
        Assert.assertEquals("Test description", exception.getRestError().getDescription());
        Assert.assertEquals("Test code", exception.getRestError().getCode());
        Assert.assertEquals("Test message", exception.getRestError().getName());
        Assert.assertEquals("500 INTERNAL_SERVER_ERROR", exception.getStatus().toString());


        // Create mock arguments
        RestError error = new RestError("Test code", "Test message", "description", ErrorType.WARNING);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Throwable cause = new RuntimeException("Test cause");

        // Create a ServiceException object with the mock arguments
        ServiceException exception2 = new ServiceException(error, status, cause);

        // Verify that the properties of the exception are set correctly
        Assert.assertEquals(cause, exception2.getCause());
        Assert.assertEquals(error, exception2.getRestError());
        Assert.assertEquals("500 INTERNAL_SERVER_ERROR", exception2.getStatus().toString());
    }
}
