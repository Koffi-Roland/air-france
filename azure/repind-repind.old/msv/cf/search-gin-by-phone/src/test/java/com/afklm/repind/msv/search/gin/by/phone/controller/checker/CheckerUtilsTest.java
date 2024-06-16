package com.afklm.repind.msv.search.gin.by.phone.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CheckerUtilsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static String PHONE = "+33606060606";

    public void throwException(Class iException , String iMessage){
        thrown.expect(iException);
        thrown.expectMessage(iMessage);
    }

    @Test
    public void phoneCheckerSuccessTest() throws BusinessException {
        Assert.assertEquals(PHONE , CheckerUtils.checkPhone(PHONE));
    }

    @Test
    public void phoneCheckerNullTest() throws BusinessException {
        throwException(BusinessException.class , "Phone is mandatory");
        CheckerUtils.checkPhone(null);
    }

    @Test
    public void phoneCheckerEmptyTest() throws BusinessException {
        throwException(BusinessException.class , "Phone is mandatory");
        CheckerUtils.checkPhone("");
    }
}
