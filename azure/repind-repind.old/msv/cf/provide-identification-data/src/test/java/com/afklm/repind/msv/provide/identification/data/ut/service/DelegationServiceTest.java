package com.afklm.repind.msv.provide.identification.data.ut.service;

import com.afklm.repind.common.entity.identifier.AccountIdentifier;
import com.afklm.repind.common.entity.individual.ComplementaryInformationEntity;
import com.afklm.repind.common.entity.individual.DelegationData;
import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.repository.identifier.AccountIdentifierRepository;
import com.afklm.repind.common.repository.individual.ComplementaryInformationRepository;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.msv.provide.identification.data.helper.ProvideHelper;
import com.afklm.repind.msv.provide.identification.data.service.DelegationService;
import com.afklm.repind.msv.provide.identification.data.service.TelecomService;
import com.afklm.repind.msv.provide.identification.data.transform.DelegationTransform;
import com.afklm.repind.msv.provide.identification.data.utils.GenerateTestData;
import com.afklm.soa.stubs.r000378.v1.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DelegationServiceTest {

    @InjectMocks
    private DelegationService delegationService;

    @Mock
    private IndividuRepository individuRepository;

    @Mock
    private AccountIdentifierRepository accountIdentifierRepository;

    @Mock
    private ComplementaryInformationRepository complementaryInformationRepository;

    @Mock
    private TelecomService telecomService;

    @Mock
    private ProvideHelper provideHelper;

    @Mock
    private DelegationTransform delegationTransform;

    @Test
    void testSetDelegateResponse() throws ParseException {
        Individu mockedIndividu = GenerateTestData.buildIndividual();
        AccountIdentifier mockedAccountIdentifier = GenerateTestData.buildAccountIdentifier();
        List<Telecom> mockedTelecomList = telecomService.convertTelecomsListToTelecomList(GenerateTestData.TelecomsList());
        List<DelegationData> mockedDelegateList = GenerateTestData.buildDelegatesList();
        DelegationData mockedDelegate1 = mockedDelegateList.get(0);
        DelegationData mockedDelegate2 = mockedDelegateList.get(1);
        List<ComplementaryInformationEntity> complementaryInformationEntityEntityList = GenerateTestData.buildComplementaryInformationList();
        List<ComplementaryInformationEntity> complementaryInformationEntityEntityEmptyList = new ArrayList<>();
        List<com.afklm.soa.stubs.r000378.v1.model.ComplementaryInformation> complementaryInformationList = GenerateTestData.buildTransformedComplementaryInformationList();
        List<com.afklm.soa.stubs.r000378.v1.model.ComplementaryInformation> complementaryInformationEmptyList = new ArrayList<>();

        when(individuRepository.getIndividuByGin("400210364791")).thenReturn(mockedIndividu);
        when(individuRepository.getIndividuByGin("400210364756")).thenReturn(mockedIndividu);

        when(accountIdentifierRepository.findBySgin("400210364791")).thenReturn(mockedAccountIdentifier);
        when(accountIdentifierRepository.findBySgin("400210364756")).thenReturn(mockedAccountIdentifier);

        when(telecomService.convertTelecomsListToTelecomList(telecomService.getTelecomsByGin("400210364791"))).thenReturn(mockedTelecomList);
        when(telecomService.convertTelecomsListToTelecomList(telecomService.getTelecomsByGin("400210364756"))).thenReturn(mockedTelecomList);

        when(provideHelper.removeSpecialCharacter(mockedIndividu.getPrenomTypo1())).thenReturn(mockedIndividu.getPrenomTypo1());
        when(provideHelper.removeSpecialCharacter(mockedIndividu.getNomTypo1())).thenReturn(mockedIndividu.getNomTypo1());

        when(delegationTransform.setDelegationIndividualData(mockedIndividu, mockedAccountIdentifier, mockedIndividu.getNomTypo1(), mockedIndividu.getPrenomTypo1())).thenReturn(buildMockedDelegationIndividualData(mockedIndividu, mockedAccountIdentifier, mockedIndividu.getNomTypo1(), mockedIndividu.getPrenomTypo1()));

        when(delegationTransform.setDelegationStatusDataForDelegate(mockedDelegate1)).thenReturn(buildMockedDelegationStatusData(mockedDelegate1));
        when(delegationTransform.setDelegationStatusDataForDelegate(mockedDelegate2)).thenReturn(buildMockedDelegationStatusData(mockedDelegate2));

        when(delegationTransform.setSignature(mockedDelegate1)).thenReturn(buildMockedSignature(mockedDelegate1));
        when(delegationTransform.setSignature(mockedDelegate2)).thenReturn(buildMockedSignature(mockedDelegate2));

        when(delegationTransform.setDelegate(buildMockedDelegationStatusData(mockedDelegate1), buildMockedDelegationIndividualData(mockedIndividu, mockedAccountIdentifier, mockedIndividu.getNomTypo1(), mockedIndividu.getPrenomTypo1()), mockedTelecomList, complementaryInformationList, buildMockedSignature(mockedDelegate1))).thenReturn(buildMockedDelegate(buildMockedDelegationStatusData(mockedDelegate1), buildMockedDelegationIndividualData(mockedIndividu, mockedAccountIdentifier, mockedIndividu.getNomTypo1(), mockedIndividu.getPrenomTypo1()), mockedTelecomList, complementaryInformationList, buildMockedSignature(mockedDelegate1)));
        when(delegationTransform.setDelegate(buildMockedDelegationStatusData(mockedDelegate2), buildMockedDelegationIndividualData(mockedIndividu, mockedAccountIdentifier, mockedIndividu.getNomTypo1(), mockedIndividu.getPrenomTypo1()), mockedTelecomList, complementaryInformationEmptyList, buildMockedSignature(mockedDelegate2))).thenReturn(buildMockedDelegate(buildMockedDelegationStatusData(mockedDelegate2), buildMockedDelegationIndividualData(mockedIndividu, mockedAccountIdentifier, mockedIndividu.getNomTypo1(), mockedIndividu.getPrenomTypo1()), mockedTelecomList, complementaryInformationEmptyList, buildMockedSignature(mockedDelegate2)));

        when(complementaryInformationRepository.findAllByDelegationDataId("111905")).thenReturn(complementaryInformationEntityEntityList);
        when(complementaryInformationRepository.findAllByDelegationDataId("111908")).thenReturn(complementaryInformationEntityEntityEmptyList);

        when(delegationTransform.setComplementaryInformations(complementaryInformationEntityEntityList)).thenReturn(complementaryInformationList);
        when(delegationTransform.setComplementaryInformations(complementaryInformationEntityEntityEmptyList)).thenReturn(complementaryInformationEmptyList);

        DelegationDataResponse delegationDataResponse = new DelegationDataResponse();
        delegationService.setDelegateResponse(delegationDataResponse, mockedDelegateList);

        Assertions.assertEquals(2, delegationDataResponse.getDelegates().size());

        Delegate delegate1 = delegationDataResponse.getDelegates().get(0);
        testSignature(delegate1.getSignature());
        testDelegationIndividualDataAndStatus(delegate1.getDelegationIndividualData(), delegate1.getDelegationStatusData(), buildMockedDelegationIndividualData(GenerateTestData.buildIndividual(), GenerateTestData.buildAccountIdentifier(), GenerateTestData.buildIndividual().getNomTypo1(), GenerateTestData.buildIndividual().getPrenomTypo1()), buildMockedDelegationStatusData(mockedDelegate1));
        testTelecoms(delegate1.getTelecoms(), telecomService.convertTelecomsListToTelecomList(GenerateTestData.TelecomsList()));

        Delegate delegate2 = delegationDataResponse.getDelegates().get(1);
        testSignature(delegate2.getSignature());
        testDelegationIndividualDataAndStatus(delegate2.getDelegationIndividualData(), delegate2.getDelegationStatusData(), buildMockedDelegationIndividualData(GenerateTestData.buildIndividual(), GenerateTestData.buildAccountIdentifier(), GenerateTestData.buildIndividual().getNomTypo1(), GenerateTestData.buildIndividual().getPrenomTypo1()), buildMockedDelegationStatusData(mockedDelegate2));
        testTelecoms(delegate2.getTelecoms(), telecomService.convertTelecomsListToTelecomList(GenerateTestData.TelecomsList()));
    }

    @Test
    void testSetDelegatorResponse() throws ParseException {
        Individu mockedIndividu = GenerateTestData.buildIndividual();
        AccountIdentifier mockedAccountIdentifier = GenerateTestData.buildAccountIdentifier();
        List<Telecom> mockedTelecomList = telecomService.convertTelecomsListToTelecomList(GenerateTestData.TelecomsList());
        List<DelegationData> mockedDelegatorList = GenerateTestData.buildDelegatorsList();
        DelegationData mockedDelegator1 = mockedDelegatorList.get(0);
        DelegationData mockedDelegator2 = mockedDelegatorList.get(1);

        when(individuRepository.getIndividuByGin("400410598103")).thenReturn(mockedIndividu);
        when(individuRepository.getIndividuByGin("400410574833")).thenReturn(mockedIndividu);

        when(accountIdentifierRepository.findBySgin("400410598103")).thenReturn(mockedAccountIdentifier);
        when(accountIdentifierRepository.findBySgin("400410574833")).thenReturn(mockedAccountIdentifier);

        when(telecomService.convertTelecomsListToTelecomList(telecomService.getTelecomsByGin("400410598103"))).thenReturn(mockedTelecomList);
        when(telecomService.convertTelecomsListToTelecomList(telecomService.getTelecomsByGin("400410574833"))).thenReturn(mockedTelecomList);

        when(provideHelper.removeSpecialCharacter(mockedIndividu.getPrenomTypo1())).thenReturn(mockedIndividu.getPrenomTypo1());
        when(provideHelper.removeSpecialCharacter(mockedIndividu.getNomTypo1())).thenReturn(mockedIndividu.getNomTypo1());

        when(delegationTransform.setDelegationIndividualData(mockedIndividu, mockedAccountIdentifier, mockedIndividu.getNomTypo1(), mockedIndividu.getPrenomTypo1())).thenReturn(buildMockedDelegationIndividualData(mockedIndividu, mockedAccountIdentifier, mockedIndividu.getNomTypo1(), mockedIndividu.getPrenomTypo1()));

        when(delegationTransform.setDelegationStatusDataForDelegator(mockedDelegator1)).thenReturn(buildMockedDelegationStatusData(mockedDelegator1));
        when(delegationTransform.setDelegationStatusDataForDelegator(mockedDelegator2)).thenReturn(buildMockedDelegationStatusData(mockedDelegator2));

        when(delegationTransform.setSignature(mockedDelegator1)).thenReturn(buildMockedSignature(mockedDelegator1));
        when(delegationTransform.setSignature(mockedDelegator2)).thenReturn(buildMockedSignature(mockedDelegator2));

        when(delegationTransform.setDelegator(buildMockedDelegationStatusData(mockedDelegator1), buildMockedDelegationIndividualData(mockedIndividu, mockedAccountIdentifier, mockedIndividu.getNomTypo1(), mockedIndividu.getPrenomTypo1()), mockedTelecomList, buildMockedSignature(mockedDelegator1))).thenReturn(buildMockedDelegator(buildMockedDelegationStatusData(mockedDelegator1), buildMockedDelegationIndividualData(mockedIndividu, mockedAccountIdentifier, mockedIndividu.getNomTypo1(), mockedIndividu.getPrenomTypo1()), mockedTelecomList, buildMockedSignature(mockedDelegator1)));
        when(delegationTransform.setDelegator(buildMockedDelegationStatusData(mockedDelegator2), buildMockedDelegationIndividualData(mockedIndividu, mockedAccountIdentifier, mockedIndividu.getNomTypo1(), mockedIndividu.getPrenomTypo1()), mockedTelecomList, buildMockedSignature(mockedDelegator2))).thenReturn(buildMockedDelegator(buildMockedDelegationStatusData(mockedDelegator2), buildMockedDelegationIndividualData(mockedIndividu, mockedAccountIdentifier, mockedIndividu.getNomTypo1(), mockedIndividu.getPrenomTypo1()), mockedTelecomList, buildMockedSignature(mockedDelegator2)));

        DelegationDataResponse delegationDataResponse = new DelegationDataResponse();
        delegationService.setDelegatorResponse(delegationDataResponse, mockedDelegatorList);

        Assertions.assertEquals(2, delegationDataResponse.getDelegators().size());

        Delegator delegator1 = delegationDataResponse.getDelegators().get(0);
        testSignature(delegator1.getSignature());
        testDelegationIndividualDataAndStatus(delegator1.getDelegationIndividualData(), delegator1.getDelegationStatusData(), buildMockedDelegationIndividualData(GenerateTestData.buildIndividual(), GenerateTestData.buildAccountIdentifier(), GenerateTestData.buildIndividual().getNomTypo1(), GenerateTestData.buildIndividual().getPrenomTypo1()), buildMockedDelegationStatusData(mockedDelegator1));
        testTelecoms(delegator1.getTelecoms(), telecomService.convertTelecomsListToTelecomList(GenerateTestData.TelecomsList()));

        Delegator delegator2 = delegationDataResponse.getDelegators().get(1);
        testSignature(delegator2.getSignature());
        testDelegationIndividualDataAndStatus(delegator2.getDelegationIndividualData(), delegator2.getDelegationStatusData(), buildMockedDelegationIndividualData(GenerateTestData.buildIndividual(), GenerateTestData.buildAccountIdentifier(), GenerateTestData.buildIndividual().getNomTypo1(), GenerateTestData.buildIndividual().getPrenomTypo1()), buildMockedDelegationStatusData(mockedDelegator2));
        testTelecoms(delegator2.getTelecoms(), telecomService.convertTelecomsListToTelecomList(GenerateTestData.TelecomsList()));
    }

    private void testTelecoms(List<Telecom> telecoms, List<Telecom> test) {
        Assertions.assertEquals(test.size(), telecoms.size());
        for (int i = 0; i < telecoms.size(); i++) {
            Assertions.assertEquals(test.get(i).getCountryCode(), telecoms.get(i).getCountryCode());
            Assertions.assertEquals(test.get(i).getMediumCode(), telecoms.get(i).getMediumCode());
            Assertions.assertEquals(test.get(i).getMediumStatus(), telecoms.get(i).getMediumStatus());
            Assertions.assertEquals(test.get(i).getPhoneNumber(), telecoms.get(i).getPhoneNumber());
            Assertions.assertEquals(test.get(i).getTerminalType(), telecoms.get(i).getTerminalType());
            Assertions.assertEquals(test.get(i).getVersion(), telecoms.get(i).getVersion());
        }
    }

    private void testDelegationIndividualDataAndStatus(DelegationIndividualData delegationIndividualData, DelegationStatusData delegationStatusData, DelegationIndividualData testIndividu, DelegationStatusData testStatusData) {
        Assertions.assertEquals(testIndividu.getAccountIdentifier(), delegationIndividualData.getAccountIdentifier());
        Assertions.assertEquals(testIndividu.getCivility(), delegationIndividualData.getCivility());
        Assertions.assertEquals(testIndividu.getEmailIdentifier(), delegationIndividualData.getEmailIdentifier());
        Assertions.assertEquals(testIndividu.getFbIdentifier(), delegationIndividualData.getFbIdentifier());
        Assertions.assertEquals(testIndividu.getFirstName(), delegationIndividualData.getFirstName());
        Assertions.assertEquals(testIndividu.getFirstNameSC(), delegationIndividualData.getFirstNameSC());
        Assertions.assertEquals(testIndividu.getLastName(), delegationIndividualData.getLastName());
        Assertions.assertEquals(testIndividu.getLastNameSC(), delegationIndividualData.getLastNameSC());

        Assertions.assertEquals(testStatusData.getDelegationStatus(), delegationStatusData.getDelegationStatus());
        Assertions.assertEquals(testStatusData.getDelegationType(), delegationStatusData.getDelegationType());
        Assertions.assertEquals(testStatusData.getGin(), delegationStatusData.getGin());
    }

    private void testSignature(Signature signature) throws ParseException {
        LocalDate date = LocalDate.of(2022, 9, 26);
        Assertions.assertEquals("CustomerAPI", signature.getSignatureCreation().getSignature());
        Assertions.assertEquals("AMS", signature.getSignatureCreation().getSite());
        Assertions.assertEquals(date, signature.getSignatureCreation().getDate());

        Assertions.assertEquals("CustomerAPI", signature.getSignatureModification().getSignature());
        Assertions.assertEquals("AMS", signature.getSignatureModification().getSite());
        Assertions.assertEquals(date, signature.getSignatureModification().getDate());
    }

    private DelegationStatusData buildMockedDelegationStatusData(DelegationData delegationData) {
        DelegationStatusData delegationStatusData = new DelegationStatusData();

        delegationStatusData.setDelegationStatus(delegationData.getStatus());
        delegationStatusData.setDelegationType(delegationData.getType());
        delegationStatusData.setGin(delegationData.getDelegate().getGin());

        return delegationStatusData;
    }

    private Signature buildMockedSignature(DelegationData delegationData) {
        Signature res = new Signature();

        SignatureCreation creation = new SignatureCreation();
        creation.setDate(delegationData.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        creation.setSite(delegationData.getSiteCreation());
        creation.setSignature(delegationData.getSignatureCreation());

        SignatureModification modification = new SignatureModification();
        modification.setDate(delegationData.getDateModification().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        modification.setSignature(delegationData.getSignatureModification());
        modification.setSite(delegationData.getSiteModification());

        res.setSignatureCreation(creation);
        res.setSignatureModification(modification);
        return res;
    }

    private DelegationIndividualData buildMockedDelegationIndividualData(Individu individu, AccountIdentifier accountIdentifier, String lastNameWithoutSpecialChar, String firstNameWithoutSpecialChar) {
        DelegationIndividualData res = new DelegationIndividualData();

        res.setCivility(individu.getCivilite());
        res.setLastNameSC(individu.getNomTypo1());
        res.setLastName(lastNameWithoutSpecialChar);
        res.setFirstNameSC(individu.getPrenomTypo1());
        res.setFirstName(firstNameWithoutSpecialChar);
        res.setAccountIdentifier(accountIdentifier.getAccountId());
        res.setFbIdentifier(accountIdentifier.getFbIdentifier());
        res.setEmailIdentifier(accountIdentifier.getEmailIdentifier());

        return res;
    }
    private Delegator buildMockedDelegator(DelegationStatusData delegationStatusData, DelegationIndividualData delegationIndividualData, List<Telecom> telecomList, Signature signature) {
        Delegator res = new Delegator();

        res.setDelegationStatusData(delegationStatusData);
        res.setDelegationIndividualData(delegationIndividualData);
        res.setSignature(signature);
        res.setTelecoms(telecomList);

        return res;
    }

    private Delegate buildMockedDelegate(DelegationStatusData delegationStatusData, DelegationIndividualData delegationIndividualData, List<Telecom> telecomList, List<com.afklm.soa.stubs.r000378.v1.model.ComplementaryInformation> complementaryInformationList, Signature signature) {
        Delegate res = new Delegate();

        res.setDelegationStatusData(delegationStatusData);
        res.setDelegationIndividualData(delegationIndividualData);
        res.setSignature(signature);
        res.setTelecoms(telecomList);
        res.setComplementaryInformation(complementaryInformationList);

        return res;
    }

}
