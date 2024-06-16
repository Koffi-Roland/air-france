package com.afklm.repind.msv.mapping.services.it.exception;

import com.afklm.repind.msv.mapping.model.error.SystemErrorList;
import com.afklm.repind.msv.mapping.services.exception.ExceptionServiceExceptionBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ExceptionServiceBuilderTest {

    @Test
    public void testExceptionServiceExceptionBuilder() {
        Exception exception = new Exception("Test Exception");
        ExceptionServiceExceptionBuilder builder = new ExceptionServiceExceptionBuilder(exception);
        Assert.assertEquals(exception, builder.getCause());
    }

    @Test
    public void testGetHttpStatus() {
        Exception exception = new Exception("Test Exception");
        ExceptionServiceExceptionBuilder builder = new ExceptionServiceExceptionBuilder(exception);
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,builder.getHttpStatus());
    }

    @Test
    public void testGetRestError() {
        Exception exception = new Exception("Test Exception");
        ExceptionServiceExceptionBuilder builder = new ExceptionServiceExceptionBuilder(exception);
        Assert.assertEquals(SystemErrorList.API_SYSTEM_UNKNOW_ERROR.getError(),builder.getRestError());
    }
}
