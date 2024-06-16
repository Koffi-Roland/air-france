package com.afklm.cati.common.resources;

import com.afklm.cati.common.spring.rest.resources.RefComPrefResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class RefComPrefResourceTest {

    private static final String COM_GROUPE_TYPE = "N";
    private static final String COM_TYPE = "AF";
    private static final String DOMAIN = "AF";
    private static final String DESCRIPTION = "Description";
    private static final String MARKET = "GB";
    private static final String FIELD = "Y";
    private static final String LANGUAGE = "FR";
    private static final String SIGNATURE_MODIFICATION = "REPIND/IHM";
    private static final String MODIFICATION_SITE = "KLM";

    @Test
    @DisplayName("Unit test ref communication preferences resource")
    public void comPrefResourceTest() {
        RefComPrefResource refComPrefResource = this.mockRefComPrefResource();
        assertAll(
                () -> assertEquals(refComPrefResource.getMarket(), MARKET),
                () -> assertEquals(refComPrefResource.getComType(), COM_TYPE),
                () -> assertEquals(refComPrefResource.getSignatureModification(), SIGNATURE_MODIFICATION),
                () -> assertEquals(refComPrefResource.getSiteCreation(), MODIFICATION_SITE),
                () -> assertEquals(refComPrefResource.getComGroupeType(), COM_GROUPE_TYPE),
                () -> assertEquals(refComPrefResource.getFieldN(),FIELD),
                () -> assertEquals(refComPrefResource.getFieldA(),FIELD),
                () -> assertEquals(refComPrefResource.getFieldT(),FIELD),
                () -> assertEquals(refComPrefResource.getDomain(), DOMAIN),
                () -> assertEquals(refComPrefResource.getDefaultLanguage1(), LANGUAGE),
                () -> assertEquals(refComPrefResource.getMandatoryOptin(), FIELD),
                () -> assertEquals(refComPrefResource.getDescription(), DESCRIPTION)

                );
    }

    private RefComPrefResource mockRefComPrefResource() {

        RefComPrefResource refComPrefResource = new RefComPrefResource();
        Date date = new Date();
        refComPrefResource.setMarket(MARKET);
        refComPrefResource.setDefaultLanguage1(LANGUAGE);
        refComPrefResource.setDescription(DESCRIPTION);
        refComPrefResource.setFieldA(FIELD);
        refComPrefResource.setFieldN(FIELD);
        refComPrefResource.setFieldT(FIELD);
        refComPrefResource.setMandatoryOptin(FIELD);
        refComPrefResource.setComGroupeType(COM_GROUPE_TYPE);
        refComPrefResource.setComType(COM_TYPE);
        refComPrefResource.setDomain(DOMAIN);
        refComPrefResource.setDateCreation(date);
        refComPrefResource.setDateModification(date);
        refComPrefResource.setSignatureCreation(SIGNATURE_MODIFICATION);
        refComPrefResource.setSignatureModification(SIGNATURE_MODIFICATION);
        refComPrefResource.setSiteCreation(MODIFICATION_SITE);
        refComPrefResource.setSiteModification(MODIFICATION_SITE);
        return refComPrefResource;
    }
}
