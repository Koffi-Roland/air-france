package com.afklm.repind.msv.graphql.bff.example.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
public class EmailTest {

    private static final Integer VERSION = 1;
    private static final String CODE_MEDIUM = "D";
    private static final String STATUS_MEDIUM = "V";
    private static final String EMAIL = "ri@airfrance.fr";
    private static final String EMAIL_OPTIN = "N";

    @Test
    @DisplayName("Unit test on email")
    public void getEmailTest()
    {
       Email email = this.buildMockEmail();
        assertAll(
                () -> assertEquals(email.getEmail(), EMAIL),
                () -> assertEquals(email.getMediumCode(), CODE_MEDIUM),
                () -> assertEquals(email.getMediumStatus(), STATUS_MEDIUM),
                () -> assertEquals(email.getVersion(), VERSION),
                () -> assertEquals(email.getEmailOptin(), EMAIL_OPTIN)
                );
    }

    private Email buildMockEmail()
    {
        Email email = new Email();
        email.setEmail(EMAIL);
        email.setEmailOptin(EMAIL_OPTIN);
        email.setVersion(VERSION);
        email.setMediumCode(CODE_MEDIUM);
        email.setMediumStatus(STATUS_MEDIUM);

        return email;
    }
}
