package com.afklm.cati.common.resources;
import com.afklm.cati.common.spring.rest.resources.RefComPrefDgtResource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class RefComPrefDgtResourceTest {

    private static final Integer REF_COM_PREF_DGT_ID  = 1;
    private static final String COM_GROUPE_TYPE = "N";
    private static final String COM_TYPE = "AF";
    private static final String DOMAIN = "AF";
    private static final String DESCRIPTION = "Description";
    private static final String TEST = "Test";


    @Test
    @DisplayName("Unit test ref communication preferences DGT resource")
    public void comPrefGroupDGTResourceTest() {
        RefComPrefDgtResource refComPrefDgtResource = this.mockRefComPrefDgtResource();
        assertAll(
                () -> assertEquals(refComPrefDgtResource.getRefComPrefDgtId(), REF_COM_PREF_DGT_ID),
                () -> assertEquals(refComPrefDgtResource.getDescription(), DESCRIPTION),
                () -> assertEquals(refComPrefDgtResource.getDomain(), DOMAIN),
                () -> assertEquals(refComPrefDgtResource.getType(), COM_TYPE),
                () -> assertEquals(refComPrefDgtResource.getGroupType(), COM_GROUPE_TYPE),
                () -> assertEquals(refComPrefDgtResource.getSignatureCreation(), TEST),
                () -> assertEquals(refComPrefDgtResource.getSiteCreation(), TEST),
                () -> assertEquals(refComPrefDgtResource.getSiteModification(), TEST)
                );
    }

    private RefComPrefDgtResource mockRefComPrefDgtResource() {

        RefComPrefDgtResource refComPrefDgtResource = new RefComPrefDgtResource();
        refComPrefDgtResource.setRefComPrefDgtId(REF_COM_PREF_DGT_ID);
        refComPrefDgtResource.setDescription(DESCRIPTION);
        refComPrefDgtResource.setGroupType(COM_GROUPE_TYPE);
        refComPrefDgtResource.setType(COM_TYPE);
        refComPrefDgtResource.setDomain(DOMAIN);
        Date date = new Date();
        refComPrefDgtResource.setDateCreation(date);
        refComPrefDgtResource.setDateModification(date);
        refComPrefDgtResource.setSignatureCreation(TEST);
        refComPrefDgtResource.setSignatureModification(TEST);
        refComPrefDgtResource.setSiteCreation(TEST);
        refComPrefDgtResource.setSiteModification(TEST);

        return refComPrefDgtResource;
    }
}
