package com.afklm.repindmsv.tribe.controller.checker;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repindmsv.tribe.model.StatusEnum;
import com.afklm.repindmsv.tribe.model.error.ErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class CheckerServiceTest {

    @InjectMocks
    CheckerService checkerService;

    @Test
    void nameChecker() throws BusinessException {
        Throwable exception = assertThrows(BusinessException.class, () -> checkerService.nameChecker(""));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_400_002.getDescription(), exception.getMessage());

        assertEquals("test", checkerService.nameChecker("test"));
    }

    @Test
    void typeChecker() throws BusinessException {
        Throwable exception = assertThrows(BusinessException.class, () -> checkerService.typeChecker(""));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_400_003.getDescription(), exception.getMessage());

        assertEquals("test", checkerService.typeChecker("test"));
    }

    @Test
    void ginChecker() throws BusinessException {
        Throwable exception = assertThrows(BusinessException.class, () -> checkerService.ginChecker(""));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_400_001.getDescription(), exception.getMessage());

        exception = assertThrows(BusinessException.class, () -> checkerService.ginChecker("123456789"));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_412_002.getDescription(), exception.getMessage());

        exception = assertThrows(BusinessException.class, () -> checkerService.ginChecker("12345 6789101"));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_412_002.getDescription(), exception.getMessage());

        assertEquals("123456789101", checkerService.ginChecker("123456789101"));
    }

    @Test
    void applicationChecker() throws BusinessException {
        Throwable exception = assertThrows(BusinessException.class, () -> checkerService.applicationChecker(""));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_400_005.getDescription(), exception.getMessage());

        assertEquals("test", checkerService.applicationChecker("test"));
    }

    @Test
    void tribeIdChecker() throws BusinessException {
        Throwable exception = assertThrows(BusinessException.class, () -> checkerService.tribeIdChecker(""));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_400_006.getDescription(), exception.getMessage());

        assertEquals("test", checkerService.tribeIdChecker("test"));
    }

    @Test
    void statusChecker() throws BusinessException {
        Throwable exception = assertThrows(BusinessException.class, () -> checkerService.statusChecker(""));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_400_007.getDescription(), exception.getMessage());

        assertEquals("V", checkerService.statusChecker(StatusEnum.VALIDATED.getName()));

        exception  = assertThrows(BusinessException.class, () -> checkerService.statusChecker("test"));
        Assertions.assertEquals(ErrorMessage.ERROR_MESSAGE_412_008.getDescription(), exception.getMessage());
    }
}