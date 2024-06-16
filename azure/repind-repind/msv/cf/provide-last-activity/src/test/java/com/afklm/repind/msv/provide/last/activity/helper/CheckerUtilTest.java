package com.afklm.repind.msv.provide.last.activity.helper;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.msv.provide.last.activity.model.error.ErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit testing checker help - CheckerUtil
 */
@ExtendWith(MockitoExtension.class)
public class CheckerUtilTest {

    /**
     * Checker util service
     */

    @InjectMocks
    private  CheckerUtil checkerUtil;


    @Test
    public void ginCheckerSuccessTest() throws BusinessException {
        Assertions.assertEquals("110000038701", checkerUtil.ginChecker("110000038701"));
    }
    @Test
    public void ginCheckerInvalidGinTest() {
        Throwable exception = assertThrows(BusinessException.class, () -> checkerUtil.ginChecker("110000038701000"));
        assertEquals(ErrorMessage.ERROR_MESSAGE_412_001.getDescription(), exception.getMessage());
    }

    @Test
    public void ginCheckerMandatoryTest() {
        Throwable exception = assertThrows(BusinessException.class, () -> checkerUtil.ginChecker(" "));
        assertEquals(ErrorMessage.ERROR_MESSAGE_400_001.getDescription(), exception.getMessage());
    }

}
