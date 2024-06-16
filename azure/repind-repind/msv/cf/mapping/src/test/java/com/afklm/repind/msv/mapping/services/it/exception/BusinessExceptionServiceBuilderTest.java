package com.afklm.repind.msv.mapping.services.it.exception;


import com.afklm.repind.msv.mapping.model.error.BusinessErrorList;
import com.afklm.repind.msv.mapping.services.exception.BusinessExceptionServiceExceptionBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BusinessExceptionServiceBuilderTest {

    @Test
    public void testBusinessExceptionServiceExceptionBuilder() {
        Exception exception = new Exception("Test Exception");
        BusinessExceptionServiceExceptionBuilder builder = new BusinessExceptionServiceExceptionBuilder(exception);
        Assert.assertEquals(exception, builder.getCause());
    }

    @Test
    public void testGetHttpStatus(){
        Exception exception = new Exception("Test Exception");
        BusinessExceptionServiceExceptionBuilder builder = new BusinessExceptionServiceExceptionBuilder(exception);
        Assert.assertEquals(HttpStatus.PRECONDITION_FAILED,builder.getHttpStatus());
    }

    @Test
    public void testGetRestError(){
        Exception exception = new Exception("Test Exception");
        BusinessExceptionServiceExceptionBuilder builder = new BusinessExceptionServiceExceptionBuilder(exception);
        Assert.assertEquals(BusinessErrorList.API_BUSINESS_ERROR.getError(),builder.getRestError());
    }
}
