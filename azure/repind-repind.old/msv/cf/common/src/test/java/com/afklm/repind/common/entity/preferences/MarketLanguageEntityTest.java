package com.afklm.repind.common.entity.preferences;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class MarketLanguageEntityTest {

    @Test
    void testClass() {
        MarketLanguageEntity marketLanguage = new MarketLanguageEntity();
        // set
        marketLanguage.setSiteModification("siteModification");
        marketLanguage.setSiteCreation("siteCreation");
        marketLanguage.setSignatureModification("signatureModification");
        marketLanguage.setSignatureCreation("signatureCreation");
        marketLanguage.setMedia5("media5");
        marketLanguage.setMedia4("media4");
        marketLanguage.setMedia3("media3");
        marketLanguage.setMedia2("media2");
        marketLanguage.setMedia1("media1");
        marketLanguage.setLanguageCode("languageCode");
        marketLanguage.setDateOptin(new Timestamp(new Date(1999,1,1).getTime()));
        marketLanguage.setMarket("market");
        marketLanguage.setOptin("optin");
        marketLanguage.setDateModification(new Timestamp(new Date(1999,1,2).getTime()));
        marketLanguage.setMarketLanguageId(1);
        marketLanguage.setDateCreation(new Timestamp(new Date(1999,1,3).getTime()));
        marketLanguage.setComPrefId(1L);
        // get
        assertEquals("siteModification", marketLanguage.getSiteModification());
        assertEquals("siteCreation", marketLanguage.getSiteCreation());
        assertEquals("signatureModification", marketLanguage.getSignatureModification());
        assertEquals("signatureCreation", marketLanguage.getSignatureCreation());
        assertEquals("media5", marketLanguage.getMedia5());
        assertEquals("media4", marketLanguage.getMedia4());
        assertEquals("media3", marketLanguage.getMedia3());
        assertEquals("media2", marketLanguage.getMedia2());
        assertEquals("media1", marketLanguage.getMedia1());
        assertEquals("languageCode", marketLanguage.getLanguageCode());
        assertEquals(new Timestamp(new Date(1999,1,1).getTime()), marketLanguage.getDateOptin());
        assertEquals("market", marketLanguage.getMarket());
        assertEquals("optin", marketLanguage.getOptin());
        assertEquals(new Timestamp(new Date(1999,1,2).getTime()), marketLanguage.getDateModification());
        assertEquals(1, marketLanguage.getMarketLanguageId());
        assertEquals(new Timestamp(new Date(1999,1,3).getTime()), marketLanguage.getDateCreation());
        assertEquals(1L, marketLanguage.getComPrefId());

    }
}
