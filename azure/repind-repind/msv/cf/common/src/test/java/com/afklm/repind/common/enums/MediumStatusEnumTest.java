package com.afklm.repind.common.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class MediumStatusEnumTest {

    @Test
    void testToString() {
        assertEquals("H", MediumStatusEnum.HISTORIZED.toString());
        assertEquals("I", MediumStatusEnum.INVALID.toString());
        assertEquals("X", MediumStatusEnum.REMOVED.toString());
        assertEquals("T", MediumStatusEnum.TEMPORARY.toString());
        assertEquals("S", MediumStatusEnum.SUSPENDED.toString());
        assertEquals("V", MediumStatusEnum.VALID.toString());
    }
}
