package com.afklm.repind.common.entity.identifier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ExternalIdentifierDataTest {

    @Test
    void testClass() {
        ExternalIdentifierData entity = new ExternalIdentifierData();
        //Set
        entity.setIdentifierId(1L);
        entity.setIdentifierDataId(2L);
        entity.setKey("key");
        entity.setValue("value");
        entity.setDateCreation(new Date(1999, 1, 1));
        entity.setDateModification(new Date(1999, 1, 2));
        entity.setSignatureCreation("SignatureCreation");
        entity.setSiteCreation("SiteCreation");
        entity.setSiteModification("SiteModification");
        //Get
        assertEquals(1L, entity.getIdentifierId());
        assertEquals(2L, entity.getIdentifierDataId());
        assertEquals("key", entity.getKey());
        assertEquals("value", entity.getValue());
        assertEquals(new Date(1999,1,1), entity.getDateCreation());
        assertEquals(new Date(1999,1,2), entity.getDateModification());
        assertEquals("SignatureCreation", entity.getSignatureCreation());
        assertEquals("SiteCreation", entity.getSiteCreation());
        assertEquals("SiteModification", entity.getSiteModification());
    }
}
