package com.afklm.repind.msv.customer.adaptor.ut.service.filter;

import com.afklm.repind.common.entity.individual.Individu;
import com.afklm.repind.common.entity.individual.ProfilsEntity;
import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import com.afklm.repind.common.entity.preferences.MarketLanguageEntity;
import com.afklm.repind.common.repository.contact.EmailRepository;
import com.afklm.repind.common.repository.individual.IndividuRepository;
import com.afklm.repind.common.repository.individual.ProfilsRepository;
import com.afklm.repind.common.repository.preferences.CommunicationPreferencesRepository;
import com.afklm.repind.common.repository.preferences.MarketLanguageRepository;
import com.afklm.repind.common.repository.role.RoleContractRepository;
import com.afklm.repind.msv.customer.adaptor.model.repind.Emails;
import com.afklm.repind.msv.customer.adaptor.model.repind.PostalAddress;
import com.afklm.repind.msv.customer.adaptor.model.repind.TmpProfile;
import com.afklm.repind.msv.customer.adaptor.service.filters.FilteringRulesService;
import com.afklm.repind.msv.customer.adaptor.utils.GenerateTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FilteringRulesServiceTest {

    @Mock
    private CommunicationPreferencesRepository communicationPreferencesRepository;
    @Mock
    private MarketLanguageRepository marketLanguageRepository;
    @Mock
    private RoleContractRepository roleContractRepository;
    @Mock
    private IndividuRepository individuRepository;
    @Mock
    private ProfilsRepository profilsRepository;
    @Mock
    private EmailRepository emailRepository;

    @InjectMocks
    private FilteringRulesService filteringRulesService;

    private static final String SGIN = "400410574567";
    private static final String COMTYPE_AF = "AF";
    private static final String COMTYPE_KL = "KL";
    private static final String COM_GROUP_TYPE = "N";
    private static final String DOMAIN = "S";
    private static final Long COMPREF_ID = 123456L;
    private static final Integer MARKET_LANGUAGE_ID = 123456;

    private Individu idv;

    @BeforeEach
    public void init(){
        idv = new Individu();
        idv.setGin(SGIN);
    }
    @Test
    void testRuleComprefIsNotKLSubscribe_EmptyList() {
        List<CommunicationPreferencesEntity> comPrefList = new ArrayList<>();
        given(communicationPreferencesRepository.getCommunicationPreferencesByIndividuGin(anyString()))
                .willReturn(comPrefList);
        boolean result = filteringRulesService.ruleComprefIsNotKLSubscribe(SGIN);
        assertTrue(result); // Expecting true since none of the elements match the condition
    }

    @Test
    void ruleComprefIsNotKLSubscribeByComprefId_EmptyCompref() {
        CommunicationPreferencesEntity comPref = new CommunicationPreferencesEntity();
        given(communicationPreferencesRepository.getComPrefByComPrefId(anyLong()))
                .willReturn(comPref);
        boolean result = filteringRulesService.ruleComprefIsNotKLSubscribeByComprefId(COMPREF_ID);
        assertTrue(result); // Expecting true since none of the elements match the condition
    }

    @Test
    void testRuleComprefIsNotKLSubscribe_NoMatchingElements() {
        List<CommunicationPreferencesEntity> comPrefList = List.of(CommunicationPreferencesEntity.builder()
                 .comPrefId(COMPREF_ID)
                .individu(idv)
                .comType(COMTYPE_AF)
                .build()
        );
        given(communicationPreferencesRepository.getCommunicationPreferencesByIndividuGin(anyString()))
                .willReturn(comPrefList);
        boolean result = filteringRulesService.ruleComprefIsNotKLSubscribe(SGIN);
        assertTrue(result); // Expecting true since none of the elements match the condition
    }

    @Test
    void testRuleComprefIsNotKLSubscribeByComprefId_NoMatchingElements() {
        CommunicationPreferencesEntity comPref = CommunicationPreferencesEntity.builder()
                .comPrefId(COMPREF_ID)
                .individu(idv)
                .comType(COMTYPE_AF)
                .build();

        given(communicationPreferencesRepository.getComPrefByComPrefId(anyLong()))
                .willReturn(comPref);
        boolean result = filteringRulesService.ruleComprefIsNotKLSubscribeByComprefId(COMPREF_ID);
        assertTrue(result); // Expecting true since none of the elements match the condition
    }

    @Test
    void testRuleComprefIsNotKLSubscribe_MatchingElementExists() {
        List<CommunicationPreferencesEntity> comPrefList = List.of(CommunicationPreferencesEntity.builder()
                 .comPrefId(COMPREF_ID)
                .individu(idv)
                .comType(COMTYPE_KL)
                .domain("S")
                .comGroupType("N")
                .build()
        );
        given(communicationPreferencesRepository.getCommunicationPreferencesByIndividuGin(anyString()))
                .willReturn(comPrefList);
        boolean result = filteringRulesService.ruleComprefIsNotKLSubscribe(SGIN);
        assertFalse(result); // Expecting false : means gin will not be excluded by this rule
    }

    @Test
    void testRuleComprefIsNotKLSubscribeByComprefId_MatchingElementExists() {
        CommunicationPreferencesEntity comPref = CommunicationPreferencesEntity.builder()
                .comPrefId(COMPREF_ID)
                .individu(idv)
                .comType(COMTYPE_KL)
                .domain("S")
                .comGroupType("N")
                .build();

        given(communicationPreferencesRepository.getComPrefByComPrefId(anyLong()))
                .willReturn(comPref);
        boolean result = filteringRulesService.ruleComprefIsNotKLSubscribeByComprefId(COMPREF_ID);
        assertFalse(result); // Expecting false : means gin will not be excluded by this rule
    }


    @Test
    void testRuleComprefSubscriptionNotExist_EmptyList() {
        List<CommunicationPreferencesEntity> comPrefList = new ArrayList<>();
        given(communicationPreferencesRepository.getCommunicationPreferencesByIndividuGin(anyString()))
                .willReturn(comPrefList);
        boolean result = filteringRulesService.ruleComprefSubscriptionNotExist(SGIN);
        assertTrue(result);
    }

    @Test
    void testRuleComprefSubscriptionNotExist_NoMatchingElements_byOptin() {
        List<CommunicationPreferencesEntity> comPrefList = List.of(CommunicationPreferencesEntity.builder()
                .comPrefId(COMPREF_ID)
                .individu(idv)
                .comType(COMTYPE_KL)
                .comGroupType(COM_GROUP_TYPE)
                .domain(DOMAIN)
                .subscribe(null)
                .dateOptin(new Timestamp(System.currentTimeMillis()))
                .build()
        );
        List<MarketLanguageEntity> marketLanguageList = List.of(MarketLanguageEntity.builder()
                .comPrefId(COMPREF_ID)
                .marketLanguageId(MARKET_LANGUAGE_ID)
                .market("FR")
                .languageCode("FR")
                .dateOptin(new Timestamp(System.currentTimeMillis()))
                .optin(null)
                .build()
        );

        given(communicationPreferencesRepository.getCommunicationPreferencesByIndividuGin(anyString()))
                .willReturn(comPrefList);
        given(marketLanguageRepository.getMarketLanguageEntitiesByComPrefId(anyLong()))
                .willReturn(marketLanguageList);

        boolean result = filteringRulesService.ruleComprefSubscriptionNotExist(SGIN);
        assertTrue(result);
    }

    @Test
    void testRuleComprefSubscriptionNotExist_NoMatchingElements_byDateOptin() {
        List<CommunicationPreferencesEntity> comPrefList = List.of(CommunicationPreferencesEntity.builder()
                .comPrefId(COMPREF_ID)
                .individu(idv)
                .comType(COMTYPE_KL)
                .comGroupType(COM_GROUP_TYPE)
                .domain(DOMAIN)
                .subscribe("Y")
                .dateOptin(null)
                .build()
        );
        List<MarketLanguageEntity> marketLanguageList = List.of(MarketLanguageEntity.builder()
                .comPrefId(COMPREF_ID)
                .marketLanguageId(MARKET_LANGUAGE_ID)
                .market("FR")
                .languageCode("FR")
                .dateOptin(null)
                .optin("Y")
                .build()
        );

        given(communicationPreferencesRepository.getCommunicationPreferencesByIndividuGin(anyString()))
                .willReturn(comPrefList);
        given(marketLanguageRepository.getMarketLanguageEntitiesByComPrefId(anyLong()))
                .willReturn(marketLanguageList);

        boolean result = filteringRulesService.ruleComprefSubscriptionNotExist(SGIN);
        assertTrue(result);
    }

    @Test
    void testRuleComprefSubscriptionNotExist_MatchingElements() {
        List<CommunicationPreferencesEntity> comPrefList = List.of(CommunicationPreferencesEntity.builder()
                .comPrefId(COMPREF_ID)
                .individu(idv)
                .comType(COMTYPE_KL)
                .domain(DOMAIN)
                .comGroupType(COM_GROUP_TYPE)
                .subscribe("Y")
                .dateOptin(new Timestamp(System.currentTimeMillis()))
                .build()
        );
        List<MarketLanguageEntity> marketLanguageList = List.of(MarketLanguageEntity.builder()
                .comPrefId(COMPREF_ID)
                .marketLanguageId(MARKET_LANGUAGE_ID)
                .market("FR")
                .languageCode("FR")
                .dateOptin(new Timestamp(System.currentTimeMillis()) )
                .optin("Y")
                .build()
        );

        given(communicationPreferencesRepository.getCommunicationPreferencesByIndividuGin(anyString()))
                .willReturn(comPrefList);
        given(marketLanguageRepository.getMarketLanguageEntitiesByComPrefId(anyLong()))
                .willReturn(marketLanguageList);

        boolean result = filteringRulesService.ruleComprefSubscriptionNotExist(SGIN);
        assertFalse(result);
    }


    @Test
    void testRuleComprefMarketLanguageNotExist_eligible() {
        given(communicationPreferencesRepository.checkComPrefMarketLanguageNotExist(anyString())).willReturn(false);
        boolean result = filteringRulesService.ruleComprefMarketLanguageNotExist(SGIN);
        assertFalse(result);
    }

    @Test
    void testRuleComprefMarketLanguageNotExist_notEligible() {
        given(communicationPreferencesRepository.checkComPrefMarketLanguageNotExist(anyString())).willReturn(true);
        boolean result = filteringRulesService.ruleComprefMarketLanguageNotExist(SGIN);
        assertTrue(result);
    }

    @Test
    void testRuleRoleContract_eligible() {
        given(roleContractRepository.checkRoleContratIsNotEligibleToSendToSFMCByGin(anyString())).willReturn(false);
        boolean result = filteringRulesService.ruleRoleContract(SGIN);
        assertFalse(result);
    }

    @Test
    void testRuleRoleContract_notEligible() {
        given(roleContractRepository.checkRoleContratIsNotEligibleToSendToSFMCByGin(anyString())).willReturn(true);
        boolean result = filteringRulesService.ruleRoleContract(SGIN);
        assertTrue(result);
    }

    @Test
    void testRuleBirthdateMajor_eligible() {
        given(individuRepository.checkBirthDateIsNotEligibleToSendToSFMCByGin(anyString())).willReturn(false);
        boolean result = filteringRulesService.ruleBirthdateMajor(SGIN);
        assertFalse(result);
    }

    @Test
    void testRuleBirthdateMajor_notEligible() {
        given(individuRepository.checkBirthDateIsNotEligibleToSendToSFMCByGin(anyString())).willReturn(true);
        boolean result = filteringRulesService.ruleBirthdateMajor(SGIN);
        assertTrue(result);
    }

    @Test
    void testRuleEmailValidity_eligible() {
        given(emailRepository.checkEmailIsDirectAndValid(anyString())).willReturn(false);
        boolean result = filteringRulesService.ruleEmailValidity(SGIN);
        assertFalse(result);
    }

    @Test
    void testRuleEmailValidity_notEligible() {
        given(emailRepository.checkEmailIsDirectAndValid(anyString())).willReturn(true);
        boolean result = filteringRulesService.ruleEmailValidity(SGIN);
        assertTrue(result);
    }

    @Test
    void testRuleEmailPro_eligible() {
        Emails email = Emails.builder()
                .email("ri@repind.com")
                .mediumCode("D")
                .mediumStatus("V")
                .build();
        boolean result = filteringRulesService.ruleEmailPro(email);
        assertFalse(result);
    }
    @Test
    void testRuleEmailPro_notEligible() {
        Emails email = Emails.builder()
                .email("ri@repind.com")
                .mediumCode("P")
                .mediumStatus("V")
                .build();
        boolean result = filteringRulesService.ruleEmailPro(email);
        assertTrue(result);
    }

    @Test
    void testRulePostalAddressPro_eligible() {
        PostalAddress adrPost = PostalAddress.builder()
                .gin(SGIN)
                .mediumCode("D")
                .mediumStatus("V")
                .city("Valbonne")
                .zipCode("06560")
                .countryCode("FR")
                .build();
        boolean result = filteringRulesService.rulePostalAddressPro(adrPost);
        assertFalse(result);
    }
    @Test
    void testRulePostalAddressPro_notEligible() {
        PostalAddress adrPost = PostalAddress.builder()
                .gin(SGIN)
                .mediumCode("P")
                .mediumStatus("V")
                .city("Valbonne")
                .zipCode("06560")
                .countryCode("FR")
                .build();
        boolean result = filteringRulesService.rulePostalAddressPro(adrPost);
        assertTrue(result);
    }

    @Test
    void testRulePostalAddressPro_notEligible_statusInvalid() {
        PostalAddress adrPost = PostalAddress.builder()
                .gin(SGIN)
                .mediumCode("D")
                .mediumStatus("I")
                .city("Valbonne")
                .zipCode("06560")
                .countryCode("FR")
                .build();
        boolean result = filteringRulesService.rulePostalAddressInvalid(adrPost);
        assertTrue(result);
    }

    @Test
    void testFetchTmpProfile(){
        Individu individuFromDB = GenerateTestData.buildMockedIndividusForTmpProfile();
        ProfilsEntity profilsEntity = new ProfilsEntity(individuFromDB.getGin(), "FR");

        given(individuRepository.getIndividuByGin(anyString())).willReturn(individuFromDB);
        given(profilsRepository.getProfilsEntityByGin(anyString())).willReturn(profilsEntity);

        TmpProfile tmpProfile = filteringRulesService.fetchTmpProfile(individuFromDB.getGin());

        assertNotNull(tmpProfile);
        assertEquals(individuFromDB.getGin(), tmpProfile.getGin());
        assertEquals(individuFromDB.getPrenom(), tmpProfile.getFirstname());
        assertEquals(individuFromDB.getNom(), tmpProfile.getLastname());
        assertEquals(individuFromDB.getDateNaissance().toString(), tmpProfile.getBirthdate());
        assertEquals(individuFromDB.getSexe(), tmpProfile.getGender());
        assertEquals(individuFromDB.getCivilite(), tmpProfile.getCivility());
        assertEquals(profilsEntity.getCodeLangue(), tmpProfile.getCodeLanguage());

        assertTrue(individuFromDB.getEmails().stream().anyMatch(email-> email.getEmail().equals(tmpProfile.getEmail()) && email.getStatutMedium().equals(tmpProfile.getEmailStatus())));
    }

}
