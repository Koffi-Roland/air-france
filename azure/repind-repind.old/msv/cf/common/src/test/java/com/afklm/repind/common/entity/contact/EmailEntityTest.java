package com.afklm.repind.common.entity.contact;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
class EmailEntityTest {

    @Test
    void testEquals() {
        EmailEntity email = new EmailEntity();
        email.setAin("123456789");
        EmailEntity email2 = new EmailEntity();
        email2.setAin("123456789");
        EmailEntity email3 = new EmailEntity();
        email3.setAin("123456782229");
        EmailEntity email4 = new EmailEntity();
        assertEquals(email, email);
        assertEquals(email, email2);
        assertNotEquals(email, email4);
        assertNotEquals(null, email);
        assertNotEquals(email, email3);
    }

    @Test
    void getSain() {
        EmailEntity email = new EmailEntity();
        email.setAin("123456789");
        assertEquals("123456789", email.getAin());
    }

    @Test
    void getStatutMedium() {
        EmailEntity email = new EmailEntity();
        email.setStatutMedium("V");
        assertEquals("V", email.getStatutMedium());
    }

    @Test
    void getEmail() {
        EmailEntity email = new EmailEntity();
        email.setEmail("tes@test.com");
        assertEquals("tes@test.com", email.getEmail());
    }
}