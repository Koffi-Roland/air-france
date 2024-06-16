package com.afklm.repind.common.entity.role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class RoleUCCRTest {

    @Test
    void testClass() {
        RoleUCCR role = new RoleUCCR();
        //set
        role.setType("type");
        role.setUccrId("uccrId");
        role.setSiteModification("siteModification");
        role.setSiteCreation("siteCreation");
        role.setSignatureModification("signatureModification");
        role.setSignatureCreation("signatureCreation");
        role.setFinValidite(new Date(1999,1,1));
        role.setGin("123456789");
        role.setEtat("etat");
        role.setDebutValidite(new Date(1999,1,2));
        role.setDateModification(new Date(1999,1,3));
        role.setDateCreation(new Date(1999,1,4));
        role.setCorporateEnvironmentID("corporateEnvironmentID");
        //get
        assertEquals("type", role.getType());
        assertEquals("uccrId", role.getUccrId());
        assertEquals("siteModification", role.getSiteModification());
        assertEquals("siteCreation", role.getSiteCreation());
        assertEquals("signatureModification", role.getSignatureModification());
        assertEquals("signatureCreation", role.getSignatureCreation());
        assertEquals(new Date(1999,1,1), role.getFinValidite());
        assertEquals("123456789", role.getGin());
        assertEquals("etat", role.getEtat());
        assertEquals(new Date(1999,1,2), role.getDebutValidite());
        assertEquals(new Date(1999,1,3), role.getDateModification());
        assertEquals(new Date(1999,1,4), role.getDateCreation());
        assertEquals("corporateEnvironmentID", role.getCorporateEnvironmentID());
    }
}
