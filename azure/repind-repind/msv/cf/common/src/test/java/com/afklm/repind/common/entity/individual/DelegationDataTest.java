package com.afklm.repind.common.entity.individual;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class DelegationDataTest {

    @Test
    void testClass() {
        DelegationData delegationData = new DelegationData();
        // set
        delegationData.setSiteModification("siteModification");
        delegationData.setSiteCreation("siteCreation");
        delegationData.setSignatureModification("signatureModification");
        delegationData.setDateModification(new Date(1999, 1, 1));
        delegationData.setSignatureCreation("signatureCreation");
        delegationData.setDateCreation(new Date(1999, 1, 2));
        delegationData.setStatus("status");
        delegationData.setType("type");
        delegationData.setDelegationDataId("ginDelegationDataId");
        // get
        assertEquals("siteModification", delegationData.getSiteModification());
        assertEquals("siteCreation", delegationData.getSiteCreation());
        assertEquals("signatureModification", delegationData.getSignatureModification());
        assertEquals(new Date(1999, 1, 1), delegationData.getDateModification());
        assertEquals("signatureCreation", delegationData.getSignatureCreation());
        assertEquals(new Date(1999, 1, 2), delegationData.getDateCreation());
        assertEquals("status", delegationData.getStatus());
        assertEquals("type", delegationData.getType());
        assertEquals("ginDelegationDataId", delegationData.getDelegationDataId());
    }

}
