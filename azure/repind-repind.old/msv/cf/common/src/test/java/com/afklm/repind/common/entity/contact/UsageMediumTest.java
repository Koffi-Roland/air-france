package com.afklm.repind.common.entity.contact;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class UsageMediumTest {

    @Test
    void testClass() {
        UsageMedium usageMedium = new UsageMedium();
        // set
        usageMedium.setRole5("role5");
        usageMedium.setRole4("role4");
        usageMedium.setRole3("role3");
        usageMedium.setRole2("role2");
        usageMedium.setRole1("role1");
        usageMedium.setRin("rin");
        usageMedium.setNum(1);
        usageMedium.setCon("con");
        usageMedium.setCodeApplication("codeAppplication");
        usageMedium.setAinTel("ainTel");
        usageMedium.setAinEmail("ainEmail");
        usageMedium.setAinAdr("ainAdr");
        // get
        assertEquals("role5",  usageMedium.getRole5());
        assertEquals("role4",  usageMedium.getRole4());
        assertEquals("role3",  usageMedium.getRole3());
        assertEquals("role2",  usageMedium.getRole2());
        assertEquals("role1",  usageMedium.getRole1());
        assertEquals("rin",  usageMedium.getRin());
        assertEquals(1,  usageMedium.getNum());
        assertEquals("con",  usageMedium.getCon());
        assertEquals("codeAppplication",  usageMedium.getCodeApplication());
        assertEquals("ainTel",  usageMedium.getAinTel());
        assertEquals("ainEmail",  usageMedium.getAinEmail());
        assertEquals("ainAdr",  usageMedium.getAinAdr());
    }
}
