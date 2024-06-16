package com.afklm.repind.msv.provide.identification.data.ut.transform;

import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.msv.provide.identification.data.transform.IdentificationTransform;
import com.afklm.repind.msv.provide.identification.data.utils.GenerateTestData;
import com.afklm.soa.stubs.r000378.v1.model.IdentificationDataResponse;
import com.afklm.soa.stubs.r000378.v1.model.IndividualInformations;
import com.afklm.soa.stubs.r000378.v1.model.Signature;
import com.afklm.soa.stubs.r000378.v1.model.UsageClient;
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
class IdentificationTransformTest {
    @Autowired
    private IdentificationTransform identificationTransform;


    @Test
    void testSignature() {
        LocalDate date = LocalDate.of(2013, 12, 6);
        IdentificationDataResponse identificationDataResponse = new IdentificationDataResponse();
        identificationTransform.setSignature(identificationDataResponse, GenerateTestData.buildIndividual());
        Signature signature = identificationDataResponse.getSignature();

        Assertions.assertEquals(date, signature.getSignatureCreation().getDate());
        Assertions.assertEquals("WEB",signature.getSignatureCreation().getSignature());
        Assertions.assertEquals("ISI",signature.getSignatureCreation().getSite());

        Assertions.assertEquals(date, signature.getSignatureModification().getDate());
        Assertions.assertEquals("WEB",signature.getSignatureModification().getSignature());
        Assertions.assertEquals("ISI",signature.getSignatureModification().getSite());
    }

    @Test
    void testUsagesClient() {
        IdentificationDataResponse identificationDataResponse = new IdentificationDataResponse();
        identificationTransform.setUsagesClient(identificationDataResponse,GenerateTestData.buildUsageClientList());
        List<UsageClient> usagesClient = identificationDataResponse.getUsagesClient();

        Assertions.assertEquals(1,usagesClient.size());

        UsageClient tmp = usagesClient.get(0);
        Assertions.assertEquals("65267858",tmp.getSrin());
        Assertions.assertEquals("ISI",tmp.getApplicationCode());
        Assertions.assertEquals("O",tmp.getAuthorizedModification());
        Assertions.assertEquals(LocalDate.of(2013, 12, 6),tmp.getLastModificationDate());
    }

    @Test
    void testIndividualInformation() {
        IdentificationDataResponse identificationDataResponse = new IdentificationDataResponse();
        identificationTransform.setIndividualInformations(identificationDataResponse,GenerateTestData.buildIndividual(),GenerateTestData.buildLanguageCode());

        IndividualInformations individualInformations = identificationDataResponse.getIndividualInformations();

        Assertions.assertEquals("400410574103", individualInformations.getIdentifier());
        Assertions.assertEquals("1020014902",individualInformations.getPersonalIdentifier());
        Assertions.assertEquals("3", individualInformations.getVersion());

        Assertions.assertEquals("M", individualInformations.getGender());
        Assertions.assertEquals("MR",individualInformations.getCivility());

        Assertions.assertEquals(LocalDate.of(1960, 2, 1), individualInformations.getBirthdate());

        Assertions.assertFalse(individualInformations.getFlagThirdTrap());
        Assertions.assertFalse(individualInformations.getFlagNoFusion());

        Assertions.assertEquals("I",individualInformations.getPopulationType());
        Assertions.assertEquals("V", individualInformations.getStatus());

        Assertions.assertEquals("EN",individualInformations.getLanguageCode());
        Assertions.assertEquals("CJU",individualInformations.getTitleCode());

        Assertions.assertEquals("JOHN",individualInformations.getFirstNameSC());
        Assertions.assertEquals("JOHN",individualInformations.getFirstNameNormalized());
        Assertions.assertEquals("JOHN",individualInformations.getFirstNamePseudonym());

        Assertions.assertEquals("SMITH",individualInformations.getLastNameSC());
        Assertions.assertEquals("SMITH",individualInformations.getLastNameNormalized());
        Assertions.assertEquals("SMITH",individualInformations.getLastNamePseudonym());

        Assertions.assertEquals("MICHAEL", individualInformations.getSecondFirstName());
    }

    @Test
    void testNullCaseSetIndividualFlag(){
        IndividualInformations individualInformations = new IndividualInformations();
        Individu tmp = GenerateTestData.buildIndividual();
        tmp.setNonFusionnable(null);
        tmp.setTierUtiliseCommePiege(null);
        identificationTransform.setIndividualFlags(individualInformations,tmp);

        Assertions.assertFalse(individualInformations.getFlagNoFusion());
        Assertions.assertFalse(individualInformations.getFlagThirdTrap());
    }

    @Test
    void testTrueCaseSetIndividualFlag(){
        IndividualInformations individualInformations = new IndividualInformations();
        Individu tmp = GenerateTestData.buildIndividual();
        tmp.setNonFusionnable("O");
        tmp.setTierUtiliseCommePiege("O");
        identificationTransform.setIndividualFlags(individualInformations,tmp);

        Assertions.assertTrue(individualInformations.getFlagNoFusion());
        Assertions.assertTrue(individualInformations.getFlagThirdTrap());
    }

    @Test
    void testFalseCaseSetIndividualFlag(){
        IndividualInformations individualInformations = new IndividualInformations();
        identificationTransform.setIndividualFlags(individualInformations,GenerateTestData.buildIndividual());

        Assertions.assertFalse(individualInformations.getFlagNoFusion());
        Assertions.assertFalse(individualInformations.getFlagThirdTrap());
    }
}
