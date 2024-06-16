package com.afklm.repind.msv.search.gin.by.lastname.firstname.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class CheckerUtilsTest {

    private static String Lastname = "TEST";
    private static String Firstname = "Test";

    @Test
    void lastnameCheckerSuccessTest() throws BusinessException {
        Assert.assertEquals(Lastname , CheckerUtils.checkLastname(Lastname));
    }

    @Test
    void lastnameCheckerNullTest() {
        Throwable exception = assertThrows(BusinessException.class, () -> CheckerUtils.checkLastname(null));
        assertEquals("Missing Request Parameter", exception.getMessage());
    }

    @Test
    void lastnameCheckerEmptyTest() {
        Throwable exception = assertThrows(BusinessException.class, () -> CheckerUtils.checkLastname(""));
        assertEquals("Missing Request Parameter", exception.getMessage());
    }

    @Test
    void lastnameCheckerBadFormatTest() {
        Throwable exception = assertThrows(BusinessException.class, () -> CheckerUtils.checkLastname("José2"));
        assertEquals("Invalid value for the 'lastname' parameter, lastname must be valid", exception.getMessage());
    }

    @Test
    void firstnameCheckerSuccessTest() throws BusinessException {
        Assert.assertEquals(Firstname , CheckerUtils.checkLastname(Firstname));
    }

    @Test
    void firstnameCheckerNullTest() {
        Throwable exception = assertThrows(BusinessException.class, () -> CheckerUtils.checkFirstname(null));
        assertEquals("Missing Request Parameter", exception.getMessage());
    }

    @Test
    void firstnameCheckerEmptyTest() {
        Throwable exception = assertThrows(BusinessException.class, () -> CheckerUtils.checkFirstname(""));
        assertEquals("Missing Request Parameter", exception.getMessage());
    }

    @Test
    void firstnameCheckerBadFormatTest() {
        Throwable exception = assertThrows(BusinessException.class, () -> CheckerUtils.checkFirstname("Albert 2"));
        assertEquals("Invalid value for the 'firstname' parameter, firstname must be valid", exception.getMessage());
    }
}
