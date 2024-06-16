package com.airfrance.batch.compref.addnewklcompref.utils;

import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.MarketLanguage;

import java.util.Date;
import java.util.Set;

public class GenerateTestData {
    public final static Integer ID = 18;

    private final static String COM_TYPE = "KL_PART";
    private final static String MODIFICATION_SITE = "REPIN-2564";
    private final static String MODIFICATION_SIGNATURE = "KL_PART_INIT";

    /**
     * create test data for MarketLanguage
     * @return Set<MarketLanguage>
     */
    public static Set<MarketLanguage> createNewMarketLanguagesForTest(){
        MarketLanguage marketLanguage = new MarketLanguage();
        marketLanguage.setComPrefId(ID);
        marketLanguage.setMarket("NL");
        marketLanguage.setLanguage("NL");
        marketLanguage.setOptIn("Y");
        marketLanguage.setDateOfConsent(new Date());
        marketLanguage.setCommunicationMedia1("m1");
        marketLanguage.setCommunicationMedia2("m2");
        marketLanguage.setCommunicationMedia3("m3");
        marketLanguage.setCommunicationMedia4("m4");
        marketLanguage.setCommunicationMedia5("m5");
        marketLanguage.setCreationDate(new Date());
        marketLanguage.setCreationSignature("MJAD");
        marketLanguage.setCreationSite("QVI");
        marketLanguage.setModificationDate(new Date());
        marketLanguage.setModificationSignature(MODIFICATION_SIGNATURE);
        marketLanguage.setModificationSite(MODIFICATION_SITE);
        return Set.of(marketLanguage);
    }

    /**
     * create test data for CommunicationPreferences
     * @return CommunicationPreferences
     */
    public static CommunicationPreferences createKLComprefForTest() {
        CommunicationPreferences compref = new CommunicationPreferences();
        compref.setComPrefId(ID);
        compref.setAccountIdentifier("123");
        compref.setGin("123456789");
        compref.setDomain("S");
        compref.setComType(COM_TYPE);
        compref.setComGroupType("N");
        compref.setMedia1("m1");
        compref.setMedia2("m2");
        compref.setMedia3("m3");
        compref.setMedia4("m4");
        compref.setMedia5("m5");
        compref.setSubscribe("Y");
        compref.setCreationDate(new Date());
        compref.setDateOptinPartners(new Date());
        compref.setDateOfEntry(new Date());
        compref.setModificationDate(new Date());
        compref.setModificationSignature(MODIFICATION_SIGNATURE);
        compref.setModificationSite(MODIFICATION_SITE);
        compref.setOptinPartners(null);
        compref.setCreationSignature(MODIFICATION_SIGNATURE);
        compref.setCreationSite("QVI");
        compref.setChannel("B2C");
        return compref;
    }


}
