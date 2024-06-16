package com.afklm.repind.msv.customer.adaptor.ut.service.filter;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.exception.DataInitializationException;
import com.afklm.repind.msv.customer.adaptor.model.criteria.UpsertIndividusRequestCriteria;
import com.afklm.repind.msv.customer.adaptor.model.repind.*;
import com.afklm.repind.msv.customer.adaptor.model.salesforce.Profiles;
import com.afklm.repind.msv.customer.adaptor.service.filters.FilterAndProcessIndividusService;
import com.afklm.repind.msv.customer.adaptor.service.filters.FilteringRulesService;
import com.afklm.repind.msv.customer.adaptor.service.mapper.CustomeAdaptorToSalesforceMapping;
import com.afklm.repind.msv.customer.adaptor.utils.GenerateTestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FilterAndProcessIndividusServiceTest {

    @Mock
    private FilteringRulesService filteringRulesService;

    @Mock
    private CustomeAdaptorToSalesforceMapping toSalesforceMapping;

    private ObjectMapper objectMapper;

    @Spy
    @InjectMocks
    private FilterAndProcessIndividusService filterAndProcessIndividusService;

    private static final String UUID = "123-456-789";
    private static final String IDENTIFIER = "123456";
    private static final String SGIN = "400410574567";
    private static final String PAYLOAD = "payload";
    private static final String CONTENT_DATA = "CONTENT_DATA";
    private static final String GIN = "GIN";


    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        filterAndProcessIndividusService.setObjectMapper(objectMapper);
    }

    @Test
    void testGetIndividusAndFilterData_JsonParseException() {
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", "value");
        Assertions.assertThrows(DataInitializationException.class, () -> {
            filterAndProcessIndividusService.getIndividusAndFilterData(consumerRecord, UUID);
        });
    }

    @Test
    void testGetIndividusAndFilterData_ProcessIndividus() throws JsonProcessingException, DataInitializationException {
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordIndividus());
        JsonNode jsonNode = objectMapper.readValue(preprocessContentJson(consumerRecord.value()), JsonNode.class);
        Individus individu = objectMapper.treeToValue(jsonNode.get(CONTENT_DATA), Individus.class);

        IndividusDataModel individusDataModel = filterAndProcessIndividusService.getIndividusAndFilterData(consumerRecord, UUID);

        assertNotNull(individusDataModel);
        verify(filterAndProcessIndividusService).processIndividu(any(Individus.class), eq(UUID));
        assertEquals(individu.getIdentifier(), individusDataModel.getGin());
        assertEquals(individu.getIdentifier(), individusDataModel.getIndividus().getIdentifier());
        assertEquals(individu.getBirthdate(), individusDataModel.getIndividus().getBirthdate());
        assertEquals(individu.getFirstname(), individusDataModel.getIndividus().getFirstname());
        assertEquals(individu.getLastname(), individusDataModel.getIndividus().getLastname());
        assertNull(individusDataModel.getEmails());
    }

    @Test
    void testGetIndividusAndFilterData_ProcessEmails() throws JsonProcessingException, DataInitializationException {
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordEmail());
        JsonNode jsonNode = objectMapper.readValue(preprocessContentJson(consumerRecord.value()), JsonNode.class);
        Emails emails = objectMapper.treeToValue(jsonNode.get(CONTENT_DATA), Emails.class);

        IndividusDataModel individusDataModel = filterAndProcessIndividusService.getIndividusAndFilterData(consumerRecord, UUID);

        assertNotNull(individusDataModel);
        verify(filterAndProcessIndividusService).processEmail(any(Emails.class), eq(UUID));
        assertEquals(emails.getGin(), individusDataModel.getGin());
        assertEquals(emails.getIdentifier(), individusDataModel.getEmails().getIdentifier());
        assertEquals(emails.getEmail(), individusDataModel.getEmails().getEmail());
        assertEquals(emails.getMediumStatus(), individusDataModel.getEmails().getMediumStatus());
        assertNull(individusDataModel.getIndividus());
    }

    @Test
    void testGetIndividusAndFilterData_ProcessContract() throws JsonProcessingException, DataInitializationException {
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordContract());
        JsonNode jsonNode = objectMapper.readValue(preprocessContentJson(consumerRecord.value()), JsonNode.class);
        Contracts contract = objectMapper.treeToValue(jsonNode.get(CONTENT_DATA), Contracts.class);

        IndividusDataModel individusDataModel = filterAndProcessIndividusService.getIndividusAndFilterData(consumerRecord, UUID);

        assertNotNull(individusDataModel);
        verify(filterAndProcessIndividusService).processContract(any(Contracts.class), eq(UUID));
        assertEquals(jsonNode.get(GIN).asText(), individusDataModel.getGin());
        assertEquals(contract.getIdentifier(), individusDataModel.getContracts().getIdentifier());
        assertEquals(contract.getNumber(), individusDataModel.getContracts().getNumber());
        assertEquals(contract.getType(), individusDataModel.getContracts().getType());
    }

    @Test
    void testGetIndividusAndFilterData_ProcessPreference() throws JsonProcessingException, DataInitializationException {
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordPreference());
        JsonNode jsonNode = objectMapper.readValue(preprocessContentJson(consumerRecord.value()), JsonNode.class);
        Preference preference = objectMapper.treeToValue(jsonNode.get(CONTENT_DATA), Preference.class);

        IndividusDataModel individusDataModel = filterAndProcessIndividusService.getIndividusAndFilterData(consumerRecord, UUID);

        assertNotNull(individusDataModel);
        verify(filterAndProcessIndividusService).processPreference(any(Preference.class), eq(UUID));
        assertEquals(preference.getGin(), individusDataModel.getGin());
        assertEquals(preference.getIdentifier(), individusDataModel.getPreference().getIdentifier());
        assertEquals(preference.getType(), individusDataModel.getPreference().getType());
    }

     @Test
    void testGetIndividusAndFilterData_ProcessMarketLanguage() throws JsonProcessingException, DataInitializationException {
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordMarketLanguage());
        JsonNode jsonNode = objectMapper.readValue(preprocessContentJson(consumerRecord.value()), JsonNode.class);
        MarketLanguages marketLanguage = objectMapper.treeToValue(jsonNode.get(CONTENT_DATA), MarketLanguages.class);
        String gin = jsonNode.get(GIN).asText();
        IndividusDataModel individusDataModel = filterAndProcessIndividusService.getIndividusAndFilterData(consumerRecord, UUID);

        assertNotNull(individusDataModel);
        verify(filterAndProcessIndividusService).processMarketLanguage(any(MarketLanguages.class), eq(UUID));
        assertEquals(gin, individusDataModel.getGin());
         assertEquals(marketLanguage.getMarket(), individusDataModel.getMarketLanguages().getMarket());
         assertEquals(marketLanguage.getLanguage(), individusDataModel.getMarketLanguages().getLanguage());
         assertEquals(marketLanguage.getOptin(), individusDataModel.getMarketLanguages().getOptin());
         assertEquals(marketLanguage.getCreationSignature(), individusDataModel.getMarketLanguages().getCreationSignature());
         assertEquals(marketLanguage.getCreationDate(), individusDataModel.getMarketLanguages().getCreationDate());
         assertEquals(marketLanguage.getModificationSignature(), individusDataModel.getMarketLanguages().getModificationSignature());
         assertEquals(marketLanguage.getModificationDate(), individusDataModel.getMarketLanguages().getModificationDate());
         assertEquals(marketLanguage.getOptinDate(), individusDataModel.getMarketLanguages().getOptinDate());

    }

     @Test
    void testGetIndividusAndFilterData_ProcessPostalAddress() throws JsonProcessingException, DataInitializationException {
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordPostalAddress());
        JsonNode jsonNode = objectMapper.readValue(preprocessContentJson(consumerRecord.value()), JsonNode.class);
        PostalAddress postalAddress = objectMapper.treeToValue(jsonNode.get(CONTENT_DATA), PostalAddress.class);

        IndividusDataModel individusDataModel = filterAndProcessIndividusService.getIndividusAndFilterData(consumerRecord, UUID);

        assertNotNull(individusDataModel);
        verify(filterAndProcessIndividusService).processPostalAddress(any(PostalAddress.class), eq(UUID));
        assertEquals(postalAddress.getGin(), individusDataModel.getGin());
        assertEquals(postalAddress.getMediumCode(), individusDataModel.getPostalAddress().getMediumCode());
        assertEquals(postalAddress.getMediumStatus(), individusDataModel.getPostalAddress().getMediumStatus());
        assertEquals(postalAddress.getStreetNumber(), individusDataModel.getPostalAddress().getStreetNumber());
        assertEquals(postalAddress.getCity(), individusDataModel.getPostalAddress().getCity());
        assertEquals(postalAddress.getZipCode(), individusDataModel.getPostalAddress().getZipCode());
        assertEquals(postalAddress.getCountryCode(), individusDataModel.getPostalAddress().getCountryCode());
    }

    @Test
    void filterAndProcessIndividusKafkaData_Individu_Eligible() throws BusinessException, DataInitializationException {
        String uuid = UUID;
        boolean eligible = true;

        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordIndividus());
        IndividusDataModel mockedIndividusDataModel = GenerateTestData.buildMockedIndividusDataModelForIndividus(eligible);
        UpsertIndividusRequestCriteria mockedUpsertIndividusRqCriteria = GenerateTestData.buildMockedUpsertIndividusRqCriteriaForIndividus(mockedIndividusDataModel, uuid);
        Individus individus = mockedIndividusDataModel.getIndividus();

        Mockito.doReturn(mockedIndividusDataModel)
                .when(filterAndProcessIndividusService)
                .getIndividusAndFilterData(consumerRecord,uuid);
        when(toSalesforceMapping.getIndividusRequestCriteria(mockedIndividusDataModel, uuid)).thenReturn(mockedUpsertIndividusRqCriteria);

        UpsertIndividusRequestCriteria upsertIndividusRqCriteria = filterAndProcessIndividusService.filterAndProcessIndividusKafkaData(consumerRecord, uuid);
        Map<String, String> keys = upsertIndividusRqCriteria.getIndividusList().get(0).getKeys();
        Map<String, String> values = upsertIndividusRqCriteria.getIndividusList().get(0).getValues();


        assertNotNull(upsertIndividusRqCriteria);
        assertTrue(upsertIndividusRqCriteria.isEligible());
        assertEquals(uuid, keys.get(Profiles.Guid.name()));
        assertEquals(individus.getIdentifier(), keys.get(Profiles.GIN.name()));
        assertEquals(individus.getFirstname(), values.get(Profiles.Firstname.name()));
        assertEquals(individus.getLastname(), values.get(Profiles.Surname.name()));
        assertEquals(individus.getGender(), values.get(Profiles.Gender.name()));
        assertEquals(individus.getBirthdate(), values.get(Profiles.Birthdate.name()));
        assertEquals(individus.getCivility(), values.get(Profiles.Civility.name()));
        assertEquals("FR", values.get(Profiles.Language.name()));
    }

    @Test
    void filterAndProcessIndividusKafkaData_Individu_Ineligible() throws BusinessException, DataInitializationException {
        boolean eligible = false;

        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordIndividus());
        IndividusDataModel mockedIndividusDataModel = GenerateTestData.buildMockedIndividusDataModelForIndividus(eligible);

        Mockito.doReturn(mockedIndividusDataModel)
                .when(filterAndProcessIndividusService)
                .getIndividusAndFilterData(consumerRecord,UUID);

        UpsertIndividusRequestCriteria upsertIndividusRqCriteria = filterAndProcessIndividusService.filterAndProcessIndividusKafkaData(consumerRecord, UUID);

        assertNotNull(upsertIndividusRqCriteria);
        assertFalse(upsertIndividusRqCriteria.isEligible());
        assertNull(upsertIndividusRqCriteria.getIndividusList());
    }

    @Test
    void filterAndProcessIndividusKafkaData_Email_Eligible() throws BusinessException, DataInitializationException {
        String uuid = UUID;
        boolean eligible = true;

        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordEmail());
        IndividusDataModel mockedIndividusDataModel = GenerateTestData.buildMockedIndividusDataModelForEmail(eligible);
        UpsertIndividusRequestCriteria mockedUpsertIndividusRqCriteria = GenerateTestData.buildMockedUpsertIndividusRqCriteriaForEmails(mockedIndividusDataModel, uuid);
        Emails email = mockedIndividusDataModel.getEmails();

        Mockito.doReturn(mockedIndividusDataModel)
                .when(filterAndProcessIndividusService)
                .getIndividusAndFilterData(consumerRecord,uuid);
        when(toSalesforceMapping.getIndividusRequestCriteria(mockedIndividusDataModel, uuid)).thenReturn(mockedUpsertIndividusRqCriteria);

        UpsertIndividusRequestCriteria upsertIndividusRqCriteria = filterAndProcessIndividusService.filterAndProcessIndividusKafkaData(consumerRecord, uuid);
        Map<String, String> keys = upsertIndividusRqCriteria.getIndividusList().get(0).getKeys();
        Map<String, String> values = upsertIndividusRqCriteria.getIndividusList().get(0).getValues();

        assertNotNull(upsertIndividusRqCriteria);
        assertTrue(upsertIndividusRqCriteria.isEligible());
        assertEquals(uuid, keys.get(Profiles.Guid.name()));
        assertEquals(email.getGin(), keys.get(Profiles.GIN.name()));
        assertEquals(email.getEmail(), values.get(Profiles.Email_Address.name()));
        assertEquals(email.getMediumStatus(), values.get(Profiles.Email_Address_status.name()));
    }

    @Test
    void filterAndProcessIndividusKafkaData_Email_Ineligible() throws BusinessException, DataInitializationException {
        boolean eligible = false;

        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordEmail());
        IndividusDataModel mockedIndividusDataModel = GenerateTestData.buildMockedIndividusDataModelForEmail(eligible);

        Mockito.doReturn(mockedIndividusDataModel)
                .when(filterAndProcessIndividusService)
                .getIndividusAndFilterData(consumerRecord,UUID);

        UpsertIndividusRequestCriteria upsertIndividusRqCriteria = filterAndProcessIndividusService.filterAndProcessIndividusKafkaData(consumerRecord, UUID);

        assertNotNull(upsertIndividusRqCriteria);
        assertFalse(upsertIndividusRqCriteria.isEligible());
        assertNull(upsertIndividusRqCriteria.getIndividusList());
    }

    @Test
    void testProcessIndividu_Eligible() {
        Individus individu = new Individus();
        individu.setIdentifier(SGIN);

        when(filteringRulesService.ruleComprefIsNotKLSubscribe(anyString())).thenReturn(false);
        when(filteringRulesService.ruleOptinValue(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefSubscriptionNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefMarketLanguageNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleRoleContract(anyString())).thenReturn(false);
        when(filteringRulesService.ruleBirthdateMajor(anyString())).thenReturn(false);
        when(filteringRulesService.ruleEmailValidity(anyString())).thenReturn(false);

        IndividusDataModel result = filterAndProcessIndividusService.processIndividu(individu, UUID);

        assertNotNull(result);
        assertTrue(result.isEligible());
        assertEquals(SGIN, result.getGin());
    }

    @Test
    void testProcessIndividu_Ineligible() {
        Individus individu = new Individus();
        individu.setIdentifier(SGIN);

        when(filteringRulesService.ruleComprefIsNotKLSubscribe(anyString())).thenReturn(false);
        when(filteringRulesService.ruleOptinValue(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefSubscriptionNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefMarketLanguageNotExist(anyString())).thenReturn(true);
        when(filteringRulesService.ruleRoleContract(anyString())).thenReturn(false);
        when(filteringRulesService.ruleBirthdateMajor(anyString())).thenReturn(false);
        when(filteringRulesService.ruleEmailValidity(anyString())).thenReturn(false);

        IndividusDataModel result = filterAndProcessIndividusService.processIndividu(individu, UUID);

        assertNotNull(result);
        assertFalse(result.isEligible());
        assertEquals(SGIN, result.getGin());
    }
    @Test
    void testProcessMarketLanguage_Eligible() {
        MarketLanguages marketLanguages = new MarketLanguages();
        marketLanguages.setComPrefId("12345");
        marketLanguages.setGin(SGIN);
        marketLanguages.setMarket("FR");
        marketLanguages.setId(IDENTIFIER);

        when(filteringRulesService.ruleComprefIsNotKLSubscribeByComprefId(anyLong())).thenReturn(false);
        when(filteringRulesService.ruleRoleContract(anyString())).thenReturn(false);
        when(filteringRulesService.ruleBirthdateMajor(anyString())).thenReturn(false);
        when(filteringRulesService.ruleEmailValidity(anyString())).thenReturn(false);

        IndividusDataModel result = filterAndProcessIndividusService.processMarketLanguage(marketLanguages, UUID);

        assertNotNull(result);
        assertTrue(result.isEligible());
        assertEquals(SGIN, result.getGin());
    }

    @Test
    void testProcessMarketLanguage_Ineligible() {
        MarketLanguages marketLanguages = new MarketLanguages();
        marketLanguages.setGin(SGIN);
        marketLanguages.setComPrefId("12345");
        marketLanguages.setMarket("FR");
        marketLanguages.setLanguage("FR");
        marketLanguages.setId(IDENTIFIER);

        when(filteringRulesService.ruleComprefIsNotKLSubscribeByComprefId(anyLong())).thenReturn(false);
        when(filteringRulesService.ruleRoleContract(anyString())).thenReturn(false);
        when(filteringRulesService.ruleBirthdateMajor(anyString())).thenReturn(true);
        when(filteringRulesService.ruleEmailValidity(anyString())).thenReturn(false);

        IndividusDataModel result = filterAndProcessIndividusService.processMarketLanguage(marketLanguages, UUID);

        assertNotNull(result);
        assertFalse(result.isEligible());
        assertEquals(SGIN, result.getGin());
    }

    @Test
    void testProcessContract_Eligible() {
        // Create a mock Contracts object
        Contracts contract = new Contracts();
        contract.setGin(SGIN);

        when(filteringRulesService.ruleComprefIsNotKLSubscribe(anyString())).thenReturn(false);
        when(filteringRulesService.ruleOptinValue(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefSubscriptionNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefMarketLanguageNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleBirthdateMajor(anyString())).thenReturn(false);
        when(filteringRulesService.ruleEmailValidity(anyString())).thenReturn(false);

        IndividusDataModel result = filterAndProcessIndividusService.processContract(contract, UUID);

        assertNotNull(result);
        assertTrue(result.isEligible());
        assertEquals(SGIN, result.getGin());
    }
    @Test
    void testProcessContract_Ineligible() {
        // Create a mock Contracts object
        Contracts contract = new Contracts();
        contract.setGin(SGIN);

        when(filteringRulesService.ruleComprefIsNotKLSubscribe(anyString())).thenReturn(false);
        when(filteringRulesService.ruleOptinValue(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefSubscriptionNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefMarketLanguageNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleBirthdateMajor(anyString())).thenReturn(false);
        when(filteringRulesService.ruleEmailValidity(anyString())).thenReturn(true);

        IndividusDataModel result = filterAndProcessIndividusService.processContract(contract, UUID);

        assertNotNull(result);
        assertFalse(result.isEligible());
        assertEquals(SGIN, result.getGin());
    }

    @Test
    void testProcessPostalAddress_Eligible() {
        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setGin(SGIN);

        when(filteringRulesService.ruleComprefIsNotKLSubscribe(anyString())).thenReturn(false);
        when(filteringRulesService.ruleOptinValue(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefSubscriptionNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefMarketLanguageNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleRoleContract(anyString())).thenReturn(false);
        when(filteringRulesService.ruleBirthdateMajor(anyString())).thenReturn(false);
        when(filteringRulesService.ruleEmailValidity(anyString())).thenReturn(false);
        when(filteringRulesService.rulePostalAddressPro(any(PostalAddress.class))).thenReturn(false);

        IndividusDataModel result = filterAndProcessIndividusService.processPostalAddress(postalAddress, UUID);

        assertNotNull(result);
        assertTrue(result.isEligible());
        assertEquals(SGIN, result.getGin());
    }

    @Test
    void testProcessPostalAddress_Ineligible_byOptinNo() {
        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setGin(SGIN);

        when(filteringRulesService.ruleComprefIsNotKLSubscribe(anyString())).thenReturn(false);
        when(filteringRulesService.ruleOptinValue(anyString())).thenReturn(true);
        when(filteringRulesService.ruleComprefSubscriptionNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefMarketLanguageNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleRoleContract(anyString())).thenReturn(false);
        when(filteringRulesService.ruleBirthdateMajor(anyString())).thenReturn(false);
        when(filteringRulesService.ruleEmailValidity(anyString())).thenReturn(false);
        when(filteringRulesService.rulePostalAddressPro(any(PostalAddress.class))).thenReturn(false);

        IndividusDataModel result = filterAndProcessIndividusService.processPostalAddress(postalAddress, UUID);

        assertNotNull(result);
        assertFalse(result.isEligible());
        assertEquals(SGIN, result.getGin());
    }

    @Test
    void testProcessPostalAddress_Ineligible_byStatusInvalid() {
        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setGin(SGIN);

        when(filteringRulesService.ruleComprefIsNotKLSubscribe(anyString())).thenReturn(false);
        when(filteringRulesService.ruleOptinValue(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefSubscriptionNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefMarketLanguageNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleRoleContract(anyString())).thenReturn(false);
        when(filteringRulesService.ruleBirthdateMajor(anyString())).thenReturn(false);
        when(filteringRulesService.ruleEmailValidity(anyString())).thenReturn(false);
        when(filteringRulesService.rulePostalAddressPro(any(PostalAddress.class))).thenReturn(false);
        when(filteringRulesService.rulePostalAddressInvalid(any(PostalAddress.class))).thenReturn(true);

        IndividusDataModel result = filterAndProcessIndividusService.processPostalAddress(postalAddress, UUID);

        assertNotNull(result);
        assertFalse(result.isEligible());
        assertEquals(SGIN, result.getGin());
    }

    @Test
    void testProcessPreference_Eligible() {
        Preference preference = new Preference();
        preference.setGin(SGIN);

        when(filteringRulesService.ruleComprefIsNotKLSubscribe(anyString())).thenReturn(false);
        when(filteringRulesService.ruleOptinValue(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefSubscriptionNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefMarketLanguageNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleRoleContract(anyString())).thenReturn(false);
        when(filteringRulesService.ruleBirthdateMajor(anyString())).thenReturn(false);
        when(filteringRulesService.ruleEmailValidity(anyString())).thenReturn(false);

        IndividusDataModel result = filterAndProcessIndividusService.processPreference(preference, UUID);

        assertNotNull(result);
        assertTrue(result.isEligible());
        assertEquals(SGIN, result.getGin());
    }

    @Test
    void testProcessPreference_Ineligible() {
        Preference preference = new Preference();
        preference.setGin(SGIN);

        when(filteringRulesService.ruleComprefIsNotKLSubscribe(anyString())).thenReturn(false);
        when(filteringRulesService.ruleOptinValue(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefSubscriptionNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefMarketLanguageNotExist(anyString())).thenReturn(true);
        when(filteringRulesService.ruleRoleContract(anyString())).thenReturn(false);
        when(filteringRulesService.ruleBirthdateMajor(anyString())).thenReturn(false);
        when(filteringRulesService.ruleEmailValidity(anyString())).thenReturn(false);

        IndividusDataModel result = filterAndProcessIndividusService.processPreference(preference, UUID);

        assertNotNull(result);
        assertFalse(result.isEligible());
        assertEquals(SGIN, result.getGin());
    }

    @Test
    void testProcessEmail_Eligible() {
        // Create a mock Emails object
        Emails email = new Emails();
        email.setGin(SGIN);

        when(filteringRulesService.ruleComprefIsNotKLSubscribe(anyString())).thenReturn(false);
        when(filteringRulesService.ruleOptinValue(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefSubscriptionNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefMarketLanguageNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleRoleContract(anyString())).thenReturn(false);
        when(filteringRulesService.ruleBirthdateMajor(anyString())).thenReturn(false);
        when(filteringRulesService.ruleEmailPro(any(Emails.class))).thenReturn(false);

        IndividusDataModel result = filterAndProcessIndividusService.processEmail(email, UUID);

        assertNotNull(result);
        assertTrue(result.isEligible());
        assertEquals(SGIN, result.getGin());
    }

    @Test
    void testProcessEmail_Ineligible() {
        // Create a mock Emails object
        Emails email = new Emails();
        email.setGin(SGIN);

        when(filteringRulesService.ruleComprefIsNotKLSubscribe(anyString())).thenReturn(true);
        when(filteringRulesService.ruleOptinValue(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefSubscriptionNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleComprefMarketLanguageNotExist(anyString())).thenReturn(false);
        when(filteringRulesService.ruleRoleContract(anyString())).thenReturn(false);
        when(filteringRulesService.ruleBirthdateMajor(anyString())).thenReturn(false);
        when(filteringRulesService.ruleEmailPro(any(Emails.class))).thenReturn(false);


        IndividusDataModel result = filterAndProcessIndividusService.processEmail(email, UUID);

        assertNotNull(result);
        assertFalse(result.isEligible());
        assertEquals(SGIN, result.getGin());
    }


    private String preprocessContentJson(String record){
        // Define a regular expression pattern to capture content between curly braces including the braces themselves
        Pattern pattern =   Pattern.compile("\"\\{.*}\"");

        // Match the pattern in the JSON string
        Matcher matcher = pattern.matcher(record);

        // Check if a match is found
        if (matcher.find()) {
            String matchedText = matcher.group(0);
            String replacedText  =  matchedText.substring(1, matchedText.length() - 1);
            record = record.replaceAll(Pattern.quote(matchedText), replacedText);
        }
        return record;
    }

}
