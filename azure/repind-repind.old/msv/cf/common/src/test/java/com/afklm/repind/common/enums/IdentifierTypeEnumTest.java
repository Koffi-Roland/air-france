package com.afklm.repind.common.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class IdentifierTypeEnumTest {

    @Test
    void testEnum() {
        assertEquals(0, IdentifierTypeEnum.GIN.ordinal());
        assertEquals(1, IdentifierTypeEnum.CIN.ordinal());
    }
}
