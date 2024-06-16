package com.afklm.cati.common.resources;

import com.afklm.cati.common.spring.rest.resources.RefComPrefGroupInfoResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class RefComPrefGroupInfoResourceTest {

    private static final Long NUMBER = 2L;
    private static final String SIGNATURE_MODIFICATION = "REPIND/IHM";
    private static final String MODIFICATION_SITE = "KLM";
    private static final String TEST = "Test";

    @Test
    @DisplayName("Unit test ref communication preferences group info resource")
    public void comPrefGroupInfoResourceTest() {
        RefComPrefGroupInfoResource refComPrefGroupInfoResource = this.mockRefComPrefGroupInfoResource();
        assertAll(
                () -> assertEquals(refComPrefGroupInfoResource.getCode(), TEST),
                () -> assertEquals(refComPrefGroupInfoResource.getLibelleEN(), TEST),
                () -> assertEquals(refComPrefGroupInfoResource.getLibelleFR(), TEST),
                () -> assertEquals(refComPrefGroupInfoResource.getDefaultOption(), TEST),
                () -> assertEquals(refComPrefGroupInfoResource.getMandatoryOption(), TEST),
                () -> assertEquals(refComPrefGroupInfoResource.getSignatureCreation(), SIGNATURE_MODIFICATION),
                () -> assertEquals(refComPrefGroupInfoResource.getSignatureModification(), SIGNATURE_MODIFICATION),
                () -> assertEquals(refComPrefGroupInfoResource.getSiteCreation(), MODIFICATION_SITE),
                () -> assertEquals(refComPrefGroupInfoResource.getSiteModification(), MODIFICATION_SITE),
                () -> assertEquals(refComPrefGroupInfoResource.getNbCompref(), NUMBER),
                () -> assertEquals(refComPrefGroupInfoResource.getNbProduct(), NUMBER)
        );
    }

    private RefComPrefGroupInfoResource mockRefComPrefGroupInfoResource() {

        RefComPrefGroupInfoResource refComPrefGroupInfoResource = new RefComPrefGroupInfoResource();
        refComPrefGroupInfoResource.setCode(TEST);
        Date date = new Date();
        refComPrefGroupInfoResource.setDateCreation(date);
        refComPrefGroupInfoResource.setLibelleEN(TEST);
        refComPrefGroupInfoResource.setDefaultOption(TEST);
        refComPrefGroupInfoResource.setDateModification(date);
        refComPrefGroupInfoResource.setLibelleFR(TEST);
        refComPrefGroupInfoResource.setMandatoryOption(TEST);
        refComPrefGroupInfoResource.setSiteCreation(MODIFICATION_SITE);
        refComPrefGroupInfoResource.setSiteModification(MODIFICATION_SITE);
        refComPrefGroupInfoResource.setSignatureModification(SIGNATURE_MODIFICATION);
        refComPrefGroupInfoResource.setSignatureCreation(SIGNATURE_MODIFICATION);
        refComPrefGroupInfoResource.setNbCompref(NUMBER);
        refComPrefGroupInfoResource.setNbProduct(NUMBER);
        return refComPrefGroupInfoResource;

    }
}
