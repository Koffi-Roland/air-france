package com.afklm.repind.common.entity.contact;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

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
    void testClass() {
        EmailEntity email = new EmailEntity();
        //set
        email.setVersion(1);
        email.setStatutMedium("statusMedium");
        email.setSiteModification("siteModification");
        email.setSiteCreation("siteCreation");
        email.setSignatureModification("signatureModification");
        email.setSignatureCreation("signatureCreation");
        email.setDescriptifComplementaire("descriptifComplementaire");
        email.setDateModification(new Date(1999, 1, 1));
        email.setDateCreation(new Date(1999, 1, 2));
        email.setCodeMedium("codeMedium");
        email.setCleTemp(2);
        email.setCleRole(3);
        email.setAutorisationMailing("autorisationMailinbg");
        // get
        assertEquals(1, email.getVersion());
        assertEquals("statusMedium", email.getStatutMedium());
        assertEquals("siteModification", email.getSiteModification());
        assertEquals("siteCreation", email.getSiteCreation());
        assertEquals("signatureModification", email.getSignatureModification());
        assertEquals("signatureCreation", email.getSignatureCreation());
        assertEquals("descriptifComplementaire", email.getDescriptifComplementaire());
        assertEquals(new Date(1999, 1, 1), email.getDateModification());
        assertEquals(new Date(1999, 1, 2), email.getDateCreation());
        assertEquals("codeMedium", email.getCodeMedium());
        assertEquals(2, email.getCleTemp());
        assertEquals(3, email.getCleRole());
        assertEquals("autorisationMailinbg", email.getAutorisationMailing());
    }
}
