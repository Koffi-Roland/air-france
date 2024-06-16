package com.afklm.repind.msv.search.gin.by.email.controller.checker;

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

    private static String EMAIL = "toto@airfrance.fr";

    public void throwException(Class iException , String iMessage){
        thrown.expect(iException);
        thrown.expectMessage(iMessage);
    }

    @Test
    public void roleIdCheckerSuccessTest() throws BusinessException {
        Assert.assertEquals(EMAIL , CheckerUtils.checkEmail(EMAIL));
    }

    @Test
    public void roleIdCheckerNullTest() throws BusinessException {
        throwException(BusinessException.class , "Gin is mandatory");
        CheckerUtils.checkEmail(null);
    }

    @Test
    public void roleIdCheckerEmptyTest() throws BusinessException {
        throwException(BusinessException.class , "Gin is mandatory");
        CheckerUtils.checkEmail("");
    }

    @Test
    public void roleIdCheckerBadFormatTest() throws BusinessException {
        throwException(BusinessException.class , "Invalid value for the 'email' parameter, email must be valid");
        CheckerUtils.checkEmail("lol@@toto.fr");
    }
}
