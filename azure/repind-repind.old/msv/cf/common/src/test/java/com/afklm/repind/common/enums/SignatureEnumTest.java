package com.afklm.repind.common.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class SignatureEnumTest {
    @Test
    void testToString() {
        assertEquals("C", SignatureEnum.CREATION.toString());
        assertEquals("M", SignatureEnum.MODIFICATION.toString());
    }

}
