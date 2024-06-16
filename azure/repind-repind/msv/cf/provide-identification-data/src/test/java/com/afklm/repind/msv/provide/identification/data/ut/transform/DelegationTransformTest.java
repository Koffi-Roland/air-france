package com.afklm.repind.msv.provide.identification.data.ut.transform;

import com.afklm.repind.msv.provide.identification.data.helper.ProvideHelper;
import com.afklm.repind.msv.provide.identification.data.transform.DelegationTransform;
import com.afklm.repind.msv.provide.identification.data.utils.GenerateTestData;
import com.afklm.soa.stubs.r000378.v1.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DelegationTransformTest {

    @Autowired
    private DelegationTransform delegationTransform;
    @Autowired
    private ProvideHelper provideHelper;

    @Test
    void testSetSignature() {
        LocalDate date = LocalDate.of(2022, 9, 26);
        Signature signatureATester = delegationTransform.setSignature(GenerateTestData.buildDelegationDataEntity());

        Assertions.assertEquals(date, signatureATester.getSignatureCreation().getDate());
        Assertions.assertEquals("CustomerAPI", signatureATester.getSignatureCreation().getSignature());
        Assertions.assertEquals("AMS", signatureATester.getSignatureCreation().getSite());

        Assertions.assertEquals(date, signatureATester.getSignatureModification().getDate());
        Assertions.assertEquals("CustomerAPI", signatureATester.getSignatureModification().getSignature());
        Assertions.assertEquals("AMS", signatureATester.getSignatureModification().getSite());
    }

    @Test
    void testSetDelegationIndividualData() {
        DelegationIndividualData delegationIndividualData = delegationTransform.setDelegationIndividualData(GenerateTestData.buildIndividual(), GenerateTestData.buildAccountIdentifier(), provideHelper.removeSpecialCharacter(GenerateTestData.buildIndividual().getNomTypo1()), provideHelper.removeSpecialCharacter(GenerateTestData.buildIndividual().getPrenomTypo1()));

        Assertions.assertEquals("MR", delegationIndividualData.getCivility());

        Assertions.assertEquals("SMITH",delegationIndividualData.getLastName());
        Assertions.assertEquals("SMITH",delegationIndividualData.getLastNameSC());

        Assertions.assertEquals("JOHN",delegationIndividualData.getFirstName());
        Assertions.assertEquals("JOHN",delegationIndividualData.getFirstNameSC());

        Assertions.assertEquals("786442AH",delegationIndividualData.getAccountIdentifier());
        Assertions.assertEquals("002101358216",delegationIndividualData.getFbIdentifier());
        Assertions.assertEquals("jsmith@fwellc.com",delegationIndividualData.getEmailIdentifier());
    }

    @Test
    void testSetDelegationStatusData() {
        DelegationStatusData delegationStatusData = delegationTransform.setDelegationStatusDataForDelegate(GenerateTestData.buildDelegationDataEntity());

        Assertions.assertEquals("A", delegationStatusData.getDelegationStatus());
        Assertions.assertEquals("UM", delegationStatusData.getDelegationType());
        Assertions.assertEquals("400210364791", delegationStatusData.getGin());

        delegationStatusData = delegationTransform.setDelegationStatusDataForDelegator(GenerateTestData.buildDelegationDataEntity());

        Assertions.assertEquals("A", delegationStatusData.getDelegationStatus());
        Assertions.assertEquals("UM", delegationStatusData.getDelegationType());
        Assertions.assertEquals("400410574103", delegationStatusData.getGin());
    }

    @Test
    void testCreateComplementaryInformationData() {
        ComplementaryInformationData complementaryInformationData = delegationTransform.createComplementaryInformationData(GenerateTestData.buildComplementaryInformationList().get(0));

        Assertions.assertEquals("phoneNumber", complementaryInformationData.getKey());
        Assertions.assertEquals("874586512",complementaryInformationData.getValue());
    }

    @Test
    void testCreateComplementaryInformation() {
        ComplementaryInformation complementaryInformation = delegationTransform.createComplementaryInformation(GenerateTestData.buildComplementaryInformationList().get(0));

        Assertions.assertEquals("TEL",complementaryInformation.getType());
        Assertions.assertEquals("phoneNumber", complementaryInformation.getComplementaryInformationDatas().get(0).getKey());
        Assertions.assertEquals("874586512",complementaryInformation.getComplementaryInformationDatas().get(0).getValue());
    }

    @Test
    void testSetComplementaryInformation() {
        List<ComplementaryInformation> complementaryInformationList = delegationTransform.setComplementaryInformations(GenerateTestData.buildComplementaryInformationList());
        ComplementaryInformation complementaryInformation = complementaryInformationList.get(0);
        Assertions.assertEquals(1,complementaryInformationList.size());

        Assertions.assertEquals("TEL",complementaryInformation.getType());

        Assertions.assertEquals("phoneNumber", complementaryInformation.getComplementaryInformationDatas().get(0).getKey());
        Assertions.assertEquals("874586512",complementaryInformation.getComplementaryInformationDatas().get(0).getValue());

        Assertions.assertEquals("terminalType", complementaryInformation.getComplementaryInformationDatas().get(1).getKey());
        Assertions.assertEquals("M",complementaryInformation.getComplementaryInformationDatas().get(1).getValue());

        Assertions.assertEquals("countryCode", complementaryInformation.getComplementaryInformationDatas().get(2).getKey());
        Assertions.assertEquals("33",complementaryInformation.getComplementaryInformationDatas().get(2).getValue());
    }
}
