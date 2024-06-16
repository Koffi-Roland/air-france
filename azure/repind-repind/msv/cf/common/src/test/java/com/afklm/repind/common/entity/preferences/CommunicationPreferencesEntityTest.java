package com.afklm.repind.common.entity.preferences;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class CommunicationPreferencesEntityTest {

    @Test
    void testClass() {
        CommunicationPreferencesEntity com = new CommunicationPreferencesEntity();
        // set
        com.setSubscribe("subscribe");
        com.setSiteModification("siteModification");
        com.setSiteCreation("siteCreation");
        com.setSignatureModification("signatureModification");
        com.setSignatureCreation("signatureCreation");
        com.setOptinPartners("optinPartners");
        com.setMedia5("media5");
        com.setMedia4("media4");
        com.setMedia3("media3");
        com.setMedia2("media2");
        com.setMedia1("media1");
        com.setDomain("domain");
        com.setDateOptinPartners(new Timestamp(new Date(1999,1,1).getTime()));
        com.setDateOptin(new Timestamp(new Date(1999,1,2).getTime()));
        com.setDateModification(new Timestamp(new Date(1999,1,3).getTime()));
        com.setDateEntry(new Timestamp(new Date(1999,1,4).getTime()));
        com.setDateCreation(new Timestamp(new Date(1999,1,5).getTime()));
        com.setComType("comType");
        com.setComPrefId(1L);
        com.setComGroupType("comGroupType");
        com.setChannel("channel");
        com.setAccountIdentifier("accountIdentifier");
        com.setSubscribe("subscribe");
        com.setSiteModification("siteModification");
        com.setSiteCreation("siteCreation");
        com.setSignatureModification("signatureModification");
        com.setSignatureCreation("signatureCreation");
        // get
        assertEquals("subscribe", com.getSubscribe());
        assertEquals("siteModification", com.getSiteModification());
        assertEquals("siteCreation", com.getSiteCreation());
        assertEquals("signatureModification", com.getSignatureModification());
        assertEquals("signatureCreation", com.getSignatureCreation());
        assertEquals("optinPartners", com.getOptinPartners());
        assertEquals("media5", com.getMedia5());
        assertEquals("media4", com.getMedia4());
        assertEquals("media3", com.getMedia3());
        assertEquals("media2", com.getMedia2());
        assertEquals("media1", com.getMedia1());
        assertEquals("domain", com.getDomain());
        assertEquals(new Timestamp(new Date(1999,1,1).getTime()), com.getDateOptinPartners());
        assertEquals(new Timestamp(new Date(1999,1,2).getTime()), com.getDateOptin());
        assertEquals(new Timestamp(new Date(1999,1,3).getTime()), com.getDateModification());
        assertEquals(new Timestamp(new Date(1999,1,4).getTime()), com.getDateEntry());
        assertEquals(new Timestamp(new Date(1999,1,5).getTime()), com.getDateCreation());
        assertEquals("comType", com.getComType());
        assertEquals(1L, com.getComPrefId());
        assertEquals("comGroupType", com.getComGroupType());
        assertEquals("channel", com.getChannel());
        assertEquals("accountIdentifier", com.getAccountIdentifier());
        assertEquals("subscribe", com.getSubscribe());
        assertEquals("siteModification", com.getSiteModification());
        assertEquals("siteCreation", com.getSiteCreation());
        assertEquals("signatureModification", com.getSignatureModification());
        assertEquals("signatureCreation", com.getSignatureCreation());
    }
}
