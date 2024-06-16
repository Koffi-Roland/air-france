package com.afklm.cati.common.resources;

import com.afklm.cati.common.spring.rest.resources.RefGroupProductResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)

public class RefGroupProductResourceTest {

    private static final Integer GROUPE_ID = 1;
    private static final String CODE = "AF09";
    private static final String TEST = "Test";
    private static final String SIGNATURE_MODIFICATION = "REPIND/IHM";
    private static final String MODIFICATION_SITE = "KLM";

    @Test
    @DisplayName("Unit test ref group product resource")
    public void groupProductResourceTest() {
        RefGroupProductResource refGroupProductResource = this.mockRefGroupProductResource();
        assertAll(
                () -> assertEquals(refGroupProductResource.getLibelleEN(), TEST),
                () -> assertEquals(refGroupProductResource.getLibelleFR(), TEST),
                () -> assertEquals(refGroupProductResource.getDefaultOption(), TEST),
                () -> assertEquals(refGroupProductResource.getSignatureModification(), SIGNATURE_MODIFICATION),
                () -> assertEquals(refGroupProductResource.getSiteCreation(), MODIFICATION_SITE),
                () -> assertEquals(refGroupProductResource.getSignatureCreation(), SIGNATURE_MODIFICATION),
                () -> assertEquals(refGroupProductResource.getSiteModification(), MODIFICATION_SITE),
                () -> assertEquals(refGroupProductResource.getCode(), CODE),
                () -> assertEquals(refGroupProductResource.getIdGroup(),GROUPE_ID),
                () -> assertEquals(refGroupProductResource.getMandatoryOption(), TEST)
        );
    }
    private RefGroupProductResource mockRefGroupProductResource() {

        RefGroupProductResource refGroupProductResource = new RefGroupProductResource();
        Date date = new Date();
        refGroupProductResource.setDateCreation(date);
        refGroupProductResource.setDateModification(date);
        refGroupProductResource.setLibelleEN(TEST);
        refGroupProductResource.setLibelleFR(TEST);
        refGroupProductResource.setDefaultOption(TEST);
        refGroupProductResource.setMandatoryOption(TEST);
        refGroupProductResource.setSignatureCreation(SIGNATURE_MODIFICATION);
        refGroupProductResource.setSignatureModification(SIGNATURE_MODIFICATION);
        refGroupProductResource.setSiteCreation(MODIFICATION_SITE);
        refGroupProductResource.setSiteModification(MODIFICATION_SITE);
        refGroupProductResource.setCode(CODE);
        refGroupProductResource.setIdGroup(GROUPE_ID);
        return refGroupProductResource;
    }
}
