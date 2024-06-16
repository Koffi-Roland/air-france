package com.afklm.repind.common.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class TerminalTypeEnumTest {
    @Test
    void testEnum() {
        assertEquals("F", TerminalTypeEnum.FAX.toString());
        assertEquals("T", TerminalTypeEnum.FIX.toString());
        assertEquals("X", TerminalTypeEnum.TELEX.toString());
        assertEquals("M", TerminalTypeEnum.MOBILE.toString());
    }
}
