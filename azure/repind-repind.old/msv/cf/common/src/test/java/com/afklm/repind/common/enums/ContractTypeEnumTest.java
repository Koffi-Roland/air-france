package com.afklm.repind.common.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class ContractTypeEnumTest {

    @Test
    void testToString() {
        assertEquals("D", ContractType.CONTRACT_DOCTOR.toString());
        assertEquals("C", ContractType.ROLE_CONTRACT.toString());
        assertEquals("U", ContractType.ROLE_UCCR.toString());
        assertEquals("T", ContractType.ROLE_TRAVELERS.toString());
    }

    @Test
    void testFromLabel() {
        assertEquals(ContractType.CONTRACT_DOCTOR, ContractType.fromLabel("D"));
        assertEquals(ContractType.ROLE_CONTRACT, ContractType.fromLabel("C"));
        assertEquals(ContractType.ROLE_UCCR, ContractType.fromLabel("U"));
        assertEquals( ContractType.ROLE_TRAVELERS, ContractType.fromLabel("T"));
        assertEquals(ContractType.ROLE_TRAVELERS, ContractType.fromLabel("T"));
        assertNull(ContractType.fromLabel("Toto"));
    }
}
