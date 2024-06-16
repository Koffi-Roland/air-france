package com.airfrance.batch.common.service.pcs;

import com.airfrance.batch.common.service.ProvideIndividualScoreService;
import com.airfrance.batch.common.wrapper.WrapperProvideIndividualScoreResponse;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.adresse.TelecomsRepository;
import com.airfrance.repind.dao.delegation.DelegationDataRepository;
import com.airfrance.repind.dao.external.ExternalIdentifierRepository;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.preference.PreferenceRepository;
import com.airfrance.repind.dao.profil.ProfilsRepository;
import com.airfrance.repind.dao.reference.RefPcsFactorRepository;
import com.airfrance.repind.dao.reference.RefPcsScoreRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.adresse.PostalAddress;
import com.airfrance.repind.entity.adresse.Telecoms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
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
    private RoleContratsRepository roleContractRepository;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private AccountDataRepository accountIdentifierRepository;

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
        List<Email> emailListTest = List.of(new Email());
        List<Telecoms> telecomsList = List.of(new Telecoms());
        List<PostalAddress> adressList = List.of(new PostalAddress());

        when(refPcsScoreRepository.findByCode(any())).thenReturn(GenerateTestData.buildRefPcsScore());
        when(refPcsFactorRepository.findByCode(any())).thenReturn(GenerateTestData.buildRefPcsFactor());
        when(individuRepository.findBySgin(any())).thenReturn(GenerateTestData.buildIndividual());
        when(profilsRepository.findBySgin(any())).thenReturn(GenerateTestData.buildProfil());
        when(accountIdentifierRepository.findBySgin(any())).thenReturn(GenerateTestData.buildAccountIdentifier());
        when(emailRepository.findBySginAndCodeMediumAndStatutMedium(any(), any(), any())).thenReturn(emailListTest);
        when(telecomsRepository.findBySginAndScodeMediumAndStatutMediumAndSterminal(any(), any(), any(), any())).thenReturn(telecomsList);
        when(postalAddressRepository.findByGinAndStatus(any(), any())).thenReturn(adressList);
        when(externalIdentifierRepository.findByGin(any())).thenReturn(GenerateTestData.buildExternalIdList());
        when(preferenceRepository.findByGin(any())).thenReturn(GenerateTestData.buildPreferenceList());
        when(communicationPreferencesRepository.findByGin(any())).thenReturn(GenerateTestData.buildComPrefList());
        when(delegationDataRepository.findDelegatesByGin(any())).thenReturn(GenerateTestData.buildDelegationList());
        when(delegationDataRepository.findDelegatorsByGin(any())).thenReturn(GenerateTestData.buildDelegationList());
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
        List<WrapperProvideIndividualScoreResponse> response = provideIndividualScoreServiceTest.calculatePcsScore(List.of("123456789012,021512748555"));
        assertNotNull(response);

    }
}
