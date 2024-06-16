package com.afklm.repind.msv.provide.individual.score.service;

import com.afklm.repind.common.entity.contact.EmailEntity;
import com.afklm.repind.common.entity.contact.PostalAddress;
import com.afklm.repind.common.entity.contact.Telecoms;
import com.afklm.repind.common.repository.contact.EmailRepository;
import com.afklm.repind.common.repository.contact.PostalAddressRepository;
import com.afklm.repind.common.repository.contact.TelecomsRepository;
import com.afklm.repind.common.repository.identifier.AccountIdentifierRepository;
import com.afklm.repind.common.repository.identifier.ExternalIdentifierRepository;
import com.afklm.repind.common.repository.individual.DelegationDataRepository;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.common.repository.individual.ProfilsRepository;
import com.afklm.repind.common.repository.preferences.CommunicationPreferencesRepository;
import com.afklm.repind.common.repository.preferences.PreferenceRepository;
import com.afklm.repind.common.repository.profileComplianceScore.RefPcsFactorRepository;
import com.afklm.repind.common.repository.profileComplianceScore.RefPcsScoreRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.msv.provide.individual.score.utils.GenerateTestData;
import com.afklm.repind.msv.provide.individual.score.wrapper.WrapperProvideIndividualScoreResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProvideIndividualScoreServiceTest {

    @InjectMocks
    private ProvideIndividualScoreService provideIndividualScoreServiceTest;

    @Mock
    private IndividuRepository individuRepository;

    @Mock
    private ProfilsRepository profilsRepository;

    @Mock
    private RefPcsScoreRepository refPcsScoreRepository;

    @Mock
    private RefPcsFactorRepository refPcsFactorRepository;

    @Mock
    private RoleContractRepository roleContractRepository;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private AccountIdentifierRepository accountIdentifierRepository;

    @Mock
    private TelecomsRepository telecomsRepository;

    @Mock
    private PostalAddressRepository postalAddressRepository;

    @Mock
    private ExternalIdentifierRepository externalIdentifierRepository;

    @Mock
    private PreferenceRepository preferenceRepository;

    @Mock
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    @Mock
    private DelegationDataRepository delegationDataRepository;

    @BeforeEach
    public void setup() {
        List<EmailEntity> emailListTest = Arrays.asList(new EmailEntity());
        List<Telecoms> telecomsList = Arrays.asList(new Telecoms());
        List<PostalAddress> adressList = Arrays.asList(new PostalAddress());

        when(refPcsScoreRepository.findByCode(any())).thenReturn(GenerateTestData.buildRefPcsScore());
        when(refPcsFactorRepository.findByCode(any())).thenReturn(GenerateTestData.buildRefPcsFactor());
        when(individuRepository.getIndividuBySgin(any())).thenReturn(GenerateTestData.buildIndividual());
        when(profilsRepository.getProfilsEntityByGin(any())).thenReturn(GenerateTestData.buildProfil());
        when(accountIdentifierRepository.findBySgin(any())).thenReturn(GenerateTestData.buildAccountIdentifier());
        when(emailRepository.findByGinAndCodeMediumAndStatutMedium(any(), any(), any())).thenReturn(emailListTest);
        when(telecomsRepository.findByGinAndCodeMediumAndStatutMediumAndTerminal(any(), any(), any(), any())).thenReturn(telecomsList);
        when(postalAddressRepository.findByGinAndStatutMediumIn(any(), any())).thenReturn(adressList);
        when(externalIdentifierRepository.findByGin(any())).thenReturn(GenerateTestData.buildExternalIdList());
        when(preferenceRepository.getPreferenceEntitiesByGin(any())).thenReturn(GenerateTestData.buildPreferenceList());
        when(communicationPreferencesRepository.getCommunicationPreferencesByGin(any())).thenReturn(GenerateTestData.buildComPrefList());
        when(delegationDataRepository.getDelegationDataEntitiesByGinDelegate(any())).thenReturn(GenerateTestData.buildDelegationList());
        when(delegationDataRepository.getDelegationDataEntitiesByGinDelegator(any())).thenReturn(GenerateTestData.buildDelegationList());
        when(roleContractRepository.findByGin(any())).thenReturn(GenerateTestData.buildRoleContractList());
        when(roleContractRepository.findByGin(any())).thenReturn(GenerateTestData.buildRoleContractList());
    }

    @Test
    void calculateContractScoreTest() {
        assertEquals(1, provideIndividualScoreServiceTest.calculateContractScore("123456789012"));
    }

    @Test
    void calculateIdenityTest() {
        assertEquals(4, provideIndividualScoreServiceTest.calculateIdentityScore("123456789012"));
    }

    @Test
    void calculateProfileScoreTest() {
        assertEquals(1, provideIndividualScoreServiceTest.calculateProfileScore("123456789012"));

    }

    @Test
    void calculateContactScoreTest() {
        assertEquals(5, provideIndividualScoreServiceTest.calculateContactScore("123456789012"));
    }

    @Test
    void calculateExternalIdScoreTest() {
        assertEquals(1, provideIndividualScoreServiceTest.calculateExternalIdScore("123456789012"));
    }

    @Test
    void calculatePreferencesScoreTest() {
        assertEquals(1, provideIndividualScoreServiceTest.calculatePreferencesScore("123456789012"));
    }

    @Test
    void calculateComPrefScoreTest() {
        assertEquals(2, provideIndividualScoreServiceTest.calculateComPrefScore("123456789012"));
    }

    @Test
    void calculateDelegationScoreTest() {
        assertEquals(2, provideIndividualScoreServiceTest.calculateDelegationScore("123456789012"));
    }

    @Test
    void calculateRoleScoreTest() {
        assertEquals(1, provideIndividualScoreServiceTest.calculateRoleScore("123456789012"));
    }

    @Test
    void calculateNonContractScoreTest() {
        assertEquals(17, provideIndividualScoreServiceTest.calculateNonContractScore("123456789012"));
    }

    @Test
    void calculatePcsScoreTest() {
        List<WrapperProvideIndividualScoreResponse> response = provideIndividualScoreServiceTest.calculatePcsScore(Arrays.asList("123456789012,021512748555"));
        assertNotNull(response);

    }
}
