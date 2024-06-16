package com.afklm.repind.common.entity.role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class BusinessRoleTest {

    @Test
    void testClass() {
        BusinessRole role = new BusinessRole();
        //set
        role.setCleRole(1);
        role.setDateCreation(new Date(1999,1,1));
        role.setGinInd("ginId");
        role.setType("type");
        role.setNumeroContrat("numeroContract");
        role.setDateModification(new Date(1999,1,2));
        role.setSiteModification("siteModification");
        role.setSiteCreation("siteCreation");
        role.setSignatureModification("signatureModification");
        role.setSignatureCreation("signatureCreation");
        //get
        assertEquals(1, role.getCleRole());
        assertEquals(new Date(1999,1,1), role.getDateCreation());
        assertEquals("ginId", role.getGinInd());
        assertEquals("type", role.getType());
        assertEquals("numeroContract", role.getNumeroContrat());
        assertEquals(new Date(1999,1,2), role.getDateModification());
        assertEquals("siteModification", role.getSiteModification());
        assertEquals("siteCreation", role.getSiteCreation());
        assertEquals("signatureModification", role.getSignatureModification());
        assertEquals("signatureCreation", role.getSignatureCreation());
    }
}
