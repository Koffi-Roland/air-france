package com.afklm.repind.common.entity.identifier;

import com.afklm.repind.common.entity.contact.EmailEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
public class ExternalIdentifierTest {
    @Test
    void testClass() {
        ExternalIdentifier externalIdentifier = new ExternalIdentifier();
        //Set
        externalIdentifier.setIdentifierId(1L);
        externalIdentifier.setIdentifier("ID");
        externalIdentifier.setType("type");
        externalIdentifier.setLastSeenDate(new Date(1999, 1, 1));
        externalIdentifier.setDateCreation(new Date(1999, 1, 2));
        externalIdentifier.setDateModification(new Date(1999, 1, 3));
        externalIdentifier.setSignatureCreation("SignatureCreation");
        externalIdentifier.setSiteCreation("SiteCreation");
        externalIdentifier.setSiteModification("SiteModification");
        //Get
        assertEquals(1L, externalIdentifier.getIdentifierId());
        assertEquals("ID", externalIdentifier.getIdentifier());
        assertEquals("type", externalIdentifier.getType());
        assertEquals(new Date(1999, 1, 1), externalIdentifier.getLastSeenDate());
        assertEquals(new Date(1999, 1, 2), externalIdentifier.getDateCreation());
        assertEquals(new Date(1999, 1, 3), externalIdentifier.getDateModification());
        assertEquals("SignatureCreation", externalIdentifier.getSignatureCreation());
        assertEquals("SiteCreation", externalIdentifier.getSiteCreation());
        assertEquals("SiteModification", externalIdentifier.getSiteModification());
    }
}
