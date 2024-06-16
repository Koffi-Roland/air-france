package com.afklm.repind.msv.provide.contract.data.ut;

import com.afklm.repind.common.entity.role.RoleContract;
import com.afklm.repind.common.entity.role.RoleTravelers;
import com.afklm.repind.common.entity.role.RoleUCCR;
import com.afklm.repind.common.enums.ContractType;
import com.afklm.repind.msv.provide.contract.data.helper.GenerateTestData;
import com.afklm.repind.msv.provide.contract.data.models.stubs.Contract;
import com.afklm.repind.msv.provide.contract.data.transform.ContractTransform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TransformerTest {
    @Autowired
    private ContractTransform contractTransform;

    @Test
    void testMapRoleContractToContract() {
        Contract res = new Contract();
        RoleContract test = GenerateTestData.createRoleContrat();
        contractTransform.mapRoleContractToContract(res, test);

        assertAll(
                () -> assertEquals(res.getValidityEndDate(), test.getDateFinValidite().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()),
                () -> assertEquals(res.getValidityStartDate(), test.getDateDebutValidite().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()),
                () -> assertEquals(res.getIataCode(), test.getIata()),
                () -> assertEquals(res.getContractStatus(), test.getEtat()),
                () -> assertEquals(res.getContractType(), test.getTypeContrat()),
                () -> assertEquals(res.getContractSubType(), test.getSousType()),
                () -> assertEquals(res.getCompanyCode(), test.getCodeCompagnie())
        );

        testSignature(res, test.getDateCreation(), test.getSiteCreation(), test.getSignatureCreation(), test.getDateModification(), test.getSiteModification(), test.getSignatureModification());
    }

    @Test
    void testMapRoleUCCRToContract() {
        Contract res = new Contract();
        RoleUCCR test = GenerateTestData.createRoleUCCR();
        contractTransform.mapRoleUCCRToContract(res, test);

        assertAll(
                () -> assertEquals(res.getValidityEndDate(), test.getFinValidite().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()),
                () -> assertEquals(res.getValidityStartDate(), test.getDebutValidite().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()),
                () -> assertEquals(res.getContractNumber(), test.getUccrId()),
                () -> assertEquals(res.getContractStatus(), test.getEtat()),
                () -> assertEquals(res.getContractType(), ContractType.ROLE_UCCR.toString()),
                () -> assertEquals(res.getCorporateEnvironmentID(), test.getCorporateEnvironmentID())
        );

        testSignature(res, test.getDateCreation(), test.getSiteCreation(), test.getSignatureCreation(), test.getDateModification(), test.getSiteModification(), test.getSignatureModification());
    }

    @Test
    void testMapRoleTravelerToContract() {
        Contract res = new Contract();
        RoleTravelers test = GenerateTestData.createRoleTravelers();
        contractTransform.mapRoleTravelerToContract(res, test);

        assertAll(
                () -> assertEquals(res.getLastRecognitionDate(), test.getLastRecognitionDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()),
                () -> assertEquals(res.getMatchingRecognition(), test.getMatchingRecognitionCode()),
                () -> assertEquals(res.getContractType(), ContractType.ROLE_TRAVELERS.toString())
        );

        testSignature(res, test.getDateCreation(), test.getSiteCreation(), test.getSignatureCreation(), test.getDateModification(), test.getSiteModification(), test.getSignatureModification());
    }

    void testSignature(Contract toTest, Date dateCreation, String siteCreation, String signatureCreation, Date dateModification, String siteModification, String signatureModification) {
        assertAll(
                () -> assertEquals(toTest.getSignatureCreation().getDate(), dateCreation.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()),
                () -> assertEquals(toTest.getSignatureCreation().getSignature(), signatureCreation),
                () -> assertEquals(toTest.getSignatureCreation().getSite(), siteCreation),
                () -> assertEquals(toTest.getSignatureModification().getDate(), dateModification.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()),
                () -> assertEquals(toTest.getSignatureModification().getSignature(), signatureModification),
                () -> assertEquals(toTest.getSignatureModification().getSite(), siteModification)
        );
    }

    @Test
    void testMapWhenRoleIsNull(){
        assertAll(
                () -> assertNull(contractTransform.mapRoleContractToContract(new Contract(),null)),
                () -> assertNull(contractTransform.mapRoleTravelerToContract(new Contract(),null)),
                () -> assertNull(contractTransform.mapRoleUCCRToContract(new Contract(),null))
        );
    }
}
