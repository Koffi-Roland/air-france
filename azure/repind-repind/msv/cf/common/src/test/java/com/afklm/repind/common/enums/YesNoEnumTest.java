package com.afklm.repind.common.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class YesNoEnumTest {

    @Test
    void testGetValue() {
        assertEquals(YesNoEnum.YES, YesNoEnum.getValue("O"));
        assertEquals(YesNoEnum.NO, YesNoEnum.getValue("N"));
        assertNull(YesNoEnum.getValue("Invalid value"));
    }

    @Test
    void testToBoolean() {
        assertTrue(YesNoEnum.YES.toBoolean());
        assertFalse(YesNoEnum.NO.toBoolean());
    }

}
