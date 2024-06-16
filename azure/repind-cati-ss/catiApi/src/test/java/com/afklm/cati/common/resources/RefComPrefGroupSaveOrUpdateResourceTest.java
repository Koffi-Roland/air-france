package com.afklm.cati.common.resources;

import com.afklm.cati.common.spring.rest.resources.RefComPrefGroupSaveOrUpdateResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class RefComPrefGroupSaveOrUpdateResourceTest {

    private static final Integer ID = 2;
    private static final Long TECH_ID = 2L;
    private static final String SIGNATURE_MODIFICATION = "REPIND/IHM";
    private static final String MODIFICATION_SITE = "KLM";

    @Test
    @DisplayName("Unit test ref communication preferences save or update resource")
    public void comPrefGroupSaveOrUpdateResourceTest() {
        RefComPrefGroupSaveOrUpdateResource refComPrefGroupSaveOrUpdateResource = this.mockRefComPrefGroupSaveOrUpdateResource();
        assertAll(
                () -> assertEquals(refComPrefGroupSaveOrUpdateResource.getRefRefComPrefGroupInfoId(), ID),
                () -> assertEquals(refComPrefGroupSaveOrUpdateResource.getSignatureCreation(), SIGNATURE_MODIFICATION),
                () -> assertEquals(refComPrefGroupSaveOrUpdateResource.getSignatureModification(), SIGNATURE_MODIFICATION),
                () -> assertEquals(refComPrefGroupSaveOrUpdateResource.getSiteCreation(), MODIFICATION_SITE),
                () -> assertEquals(refComPrefGroupSaveOrUpdateResource.getSiteModification(), MODIFICATION_SITE),
                () -> assertThat(refComPrefGroupSaveOrUpdateResource.getListComPrefDgt().size()).isEqualTo(1),
                () -> assertEquals(refComPrefGroupSaveOrUpdateResource.getTechId(), TECH_ID)
        );
    }
    private RefComPrefGroupSaveOrUpdateResource mockRefComPrefGroupSaveOrUpdateResource() {

        RefComPrefGroupSaveOrUpdateResource refComPrefGroupSaveOrUpdateResource = new RefComPrefGroupSaveOrUpdateResource();

        Date date = new Date();
        refComPrefGroupSaveOrUpdateResource.setDateCreation(date);
        refComPrefGroupSaveOrUpdateResource.setDateModification(date);
        refComPrefGroupSaveOrUpdateResource.setSignatureModification(SIGNATURE_MODIFICATION);
        refComPrefGroupSaveOrUpdateResource.setSignatureCreation(SIGNATURE_MODIFICATION);
        refComPrefGroupSaveOrUpdateResource.setSiteCreation(MODIFICATION_SITE);
        refComPrefGroupSaveOrUpdateResource.setSiteModification(MODIFICATION_SITE);
        refComPrefGroupSaveOrUpdateResource.setRefRefComPrefGroupInfoId(ID);
        refComPrefGroupSaveOrUpdateResource.setTechId(TECH_ID);
        refComPrefGroupSaveOrUpdateResource.setListComPrefDgt(List.of(1));
        return refComPrefGroupSaveOrUpdateResource;

    }
}
