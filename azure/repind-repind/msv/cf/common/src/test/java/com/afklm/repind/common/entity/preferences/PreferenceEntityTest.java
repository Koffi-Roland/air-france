package com.afklm.repind.common.entity.preferences;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class PreferenceEntityTest {

    @Test
    void testClass() {
        PreferenceEntity pref = new PreferenceEntity();
        // set
        pref.setSiteModification("siteModification");
        pref.setSiteCreation("siteCreation");
        pref.setSignatureModification("signatureModification");
        pref.setLink(1L);
        pref.setDateModification(new Date(1999,1,1));
        pref.setDateCreation(new Date(1999,1,2));
        // get
        assertEquals("siteModification", pref.getSiteModification());
        assertEquals("siteCreation", pref.getSiteCreation());
        assertEquals("signatureModification", pref.getSignatureModification());
        assertEquals(1L, pref.getLink());
        assertEquals(new Date(1999,1,1), pref.getDateModification());
        assertEquals(new Date(1999,1,2), pref.getDateCreation());
    }
}
