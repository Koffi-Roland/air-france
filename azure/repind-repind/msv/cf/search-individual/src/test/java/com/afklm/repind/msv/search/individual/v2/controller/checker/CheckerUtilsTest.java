package com.afklm.repind.msv.search.individual.v2.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.search.individual.model.error.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.afklm.repind.msv.search.individual.v2.controller.checker.CheckerUtils.checkCin;
import static com.afklm.repind.msv.search.individual.v2.controller.checker.CheckerUtils.checkEmail;
import static com.afklm.repind.msv.search.individual.v2.controller.checker.CheckerUtils.checkPhone;
import static com.afklm.repind.msv.search.individual.v2.controller.checker.CheckerUtils.checkSocialIdentifier;
import static com.afklm.repind.msv.search.individual.v2.controller.checker.CheckerUtils.checkSocialType;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class CheckerUtilsTest {

    @Test
    void testCheckCin() throws BusinessException {
        assertNull(checkCin(null));
        assertNull(checkCin(""));

        Throwable exception = assertThrows(BusinessException.class, () -> checkCin("Invalid_Cin"));
        assertEquals(ErrorMessage.ERROR_MESSAGE_412_002.getDescription(), exception.getMessage());

        checkCin("1234567890");
    }

    @Test
    void testCheckEmail() throws BusinessException {
        assertNull(checkEmail(null));
        assertNull(checkEmail(""));

        Throwable exception = assertThrows(BusinessException.class, () -> checkEmail("InvalidEmail"));
        assertEquals(ErrorMessage.ERROR_MESSAGE_412_003.getDescription(), exception.getMessage());

        checkEmail("Valid@Email.com");
    }

    @Test
    void testCheckPhone() {
        assertNull(checkPhone(null));
        assertNull(checkPhone(""));
    }

    @Test
    void testCheckSocialType() {
        assertNull(checkSocialType(null));
        assertNull(checkSocialType(""));
        checkSocialType("PNM_ID");
        // ok
    }

    @Test
    void testCheckSocialIdentifier() throws BusinessException {
        assertNull(checkSocialIdentifier(null));
        assertNull(checkSocialIdentifier(""));
        checkSocialIdentifier("06146c2e-a7*22-47c4-97af-dd02e9d1d8fa@@");
        // ok
    }
}
