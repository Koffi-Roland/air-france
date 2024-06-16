package com.afklm.repind.common.entity.preferences;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class PreferenceDataEntityTest {

    @Test
    void testClass() {
        PreferenceDataEntity pref = new PreferenceDataEntity();
        // set
        pref.setValue("value");
        pref.setSiteModification("siteModification");
        pref.setSiteCreation("siteCreation");
        pref.setSignatureModification("signatureModification");
        pref.setDateModification(new Date(1999,1,1));
        pref.setDateCreation(new Date(1999,1,2));
        pref.setSignatureCreation("signatureCreation");
        pref.setSiteCreation("siteCreation");
        // get
        assertEquals("value", pref.getValue());
        assertEquals("siteModification", pref.getSiteModification());
        assertEquals("siteCreation", pref.getSiteCreation());
        assertEquals("signatureModification", pref.getSignatureModification());
        assertEquals(new Date(1999,1,1), pref.getDateModification());
        assertEquals(new Date(1999,1,2), pref.getDateCreation());
        assertEquals("signatureCreation", pref.getSignatureCreation());
        assertEquals("siteCreation", pref.getSiteCreation());
    }
}
