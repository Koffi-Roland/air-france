package com.afklm.repind.common.entity.role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class RoleTravelersTest {

    @Test
    void testClass() {
        RoleTravelers role = new RoleTravelers();
        //set
        role.setSiteModification("siteModification");
        role.setSiteCreation("siteCreation");
        role.setSignatureModification("signatureModification");
        role.setSignatureCreation("signatureCreation");
        role.setMatchingRecognitionCode("matchingRecognitionMode");
        role.setLastRecognitionDate(new Date(1999,1,1));
        role.setDateModification(new Date(1999,1,2));
        role.setDateCreation(new Date(1999,1,3));
        // get
        assertEquals("siteModification", role.getSiteModification());
        assertEquals("siteCreation", role.getSiteCreation());
        assertEquals("signatureModification", role.getSignatureModification());
        assertEquals("signatureCreation", role.getSignatureCreation());
        assertEquals("matchingRecognitionMode", role.getMatchingRecognitionCode());
        assertEquals(new Date(1999,1,1), role.getLastRecognitionDate());
        assertEquals(new Date(1999,1,2), role.getDateModification());
        assertEquals(new Date(1999,1,3), role.getDateCreation());
    }
}
