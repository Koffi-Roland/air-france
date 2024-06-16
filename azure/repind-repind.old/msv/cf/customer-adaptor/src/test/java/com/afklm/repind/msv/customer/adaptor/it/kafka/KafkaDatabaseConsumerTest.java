package com.afklm.repind.msv.customer.adaptor.it.kafka;

import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.individual.ProfilsRepository;
import com.afklm.repind.common.repository.preferences.CommunicationPreferencesRepository;
import com.afklm.repind.msv.customer.adaptor.kafka.KafkaDatabaseConsumer;
import com.afklm.repind.msv.customer.adaptor.model.criteria.UpsertIndividusRequestCriteria;
import com.afklm.repind.msv.customer.adaptor.model.repind.*;
import com.afklm.repind.msv.customer.adaptor.model.salesforce.Profiles;
import com.afklm.repind.msv.customer.adaptor.service.SendIndividusDataToSfmcService;
import com.afklm.repind.msv.customer.adaptor.utils.GenerateTestData;
import com.afklm.repind.msv.customer.adaptor.wrapper.individus.WrapperUpsertIndividusResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@TestPropertySource("/application.yml")
@SpringBootTest
@Sql(scripts = "classpath:/clean.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql("classpath:/init.sql")
@Sql(scripts = "classpath:/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KafkaDatabaseConsumerTest {

    @MockBean
    private SendIndividusDataToSfmcService sendToSfmcMS;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProfilsRepository profilsRepository;
    @Autowired
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    @SpyBean
    private KafkaDatabaseConsumer kafkaDatabaseConsumer;


    @Test
    void filterAndProcessKafkaMsg_Individus_Ok() throws BusinessException, JsonProcessingException {
        // Prepare test data for the Kafka message and mock dependencies.
        String gin = "400410574999";
        String contentData = "{\"identifier\":\"400410574999\",\"civility\":\"M.\",\"lastname\":\"jadar\",\"firstname\":\"mohamed\",\"gender\":\"M\",\"birthdate\":\"05-JUN-99\",\"secondFirstname\":\"\",\"status\":\"V\",\"ginFusion\":\"\",\"lastnameSC\":\"TOMPONIONY\",\"firstnameSC\":\"LOHARANO\",\"nationality\":\"\",\"creationDate\":\"21-APR-14\",\"creationSignature\":\"AF\",\"creationSite\":\"GMMAL\",\"modificationDate\":\"29-AUG-23\",\"modificationSignature\":\"REPIND/IHM\",\"modificationSite\":\"QVI\",\"type\":\"I\"}";
        Individus individus = objectMapper.readValue(contentData, Individus.class);
        String message = GenerateTestData.buildMockedConsumerRecordIndividus(gin, contentData);
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", message);


        // Mocking call to salesforce API
        ResponseEntity<WrapperUpsertIndividusResponse> responseOK = new ResponseEntity<>(null, HttpStatus.OK);
        when(sendToSfmcMS.sendIndividusDataToSfmc(any(UpsertIndividusRequestCriteria.class), any(String.class))).thenReturn(responseOK);

        // Execute the filterAndProcessKafkaMsg method to process the Kafka message.
        ResponseEntity<WrapperUpsertIndividusResponse> wrapperUpsertIndividusResponseResponseEntity = kafkaDatabaseConsumer.filterAndProcessKafkaMsg(consumerRecord);
        UpsertIndividusRequestCriteria upsertIndividusRqCriteria = kafkaDatabaseConsumer.getUpsertedIndividu();

        Map<String, String> values = upsertIndividusRqCriteria.getIndividusList().get(0).getValues();

        // Assertions
        assertNotNull(upsertIndividusRqCriteria);
        assertEquals(HttpStatus.OK, wrapperUpsertIndividusResponseResponseEntity.getStatusCode());

        assertTrue(upsertIndividusRqCriteria.isEligible());
        assertEquals(individus.getIdentifier(), values.get(Profiles.GIN.name()));
        assertEquals(individus.getFirstname(), values.get(Profiles.Firstname.name()));
        assertEquals(individus.getLastname(), values.get(Profiles.Surname.name()));
        assertEquals(individus.getGender(), values.get(Profiles.Gender.name()));
        assertEquals(individus.getBirthdate(), values.get(Profiles.Birthdate.name()));
        assertEquals(individus.getCivility(), values.get(Profiles.Civility.name()));
        assertEquals(profilsRepository.getProfilsEntityByGin(gin).getCodeLangue(), values.get(Profiles.Language.name()));
    }
    @Test
    void filterAndProcessKafkaMsg_Individus_NotOk(){
        // Prepare test data for the Kafka message and mock dependencies.
        String gin = "400410574998";
        String contentData = "{\"identifier\":\"400410574998\",\"civility\":\"M.\",\"lastname\":\"diallo\",\"firstname\":\"thierno\",\"gender\":\"M\",\"birthdate\":\"05-JUN-99\",\"secondFirstname\":\"\",\"status\":\"V\",\"ginFusion\":\"\",\"lastnameSC\":\"TOMPONIONY\",\"firstnameSC\":\"LOHARANO\",\"nationality\":\"\",\"creationDate\":\"21-APR-14\",\"creationSignature\":\"AF\",\"creationSite\":\"GMMAL\",\"modificationDate\":\"29-AUG-23\",\"modificationSignature\":\"REPIND/IHM\",\"modificationSite\":\"QVI\",\"type\":\"I\"}";
        String message = GenerateTestData.buildMockedConsumerRecordIndividus(gin, contentData);
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", message);

        kafkaDatabaseConsumer.filterAndProcessKafkaMsg(consumerRecord);
        UpsertIndividusRequestCriteria upsertIndividusRqCriteria = kafkaDatabaseConsumer.getUpsertedIndividu();

        // Assertions
        assertNotNull(upsertIndividusRqCriteria);
        assertFalse(upsertIndividusRqCriteria.isEligible());
    }

    @Test
    void filterAndProcessKafkaMsg_Email_Ok() throws BusinessException, JsonProcessingException {
        // Prepare test data for the Kafka message and mock dependencies.
        String gin = "400410574999";
        String contentData = "{\"identifier\":\"97310899\",\"gin\":\"400410574999\",\"mediumCode\":\"D\",\"mediumStatus\":\"V\",\"email\":\"mohamed.jadar@airfrance.fr\",\"description\":\"\",\"creationDate\":\"29-AUG-23\",\"creationSignature\":\"icare\",\"creationSite\":\"QVI\",\"modificationDate\":\"29-AUG-23\",\"modificationSignature\":\"icare\",\"modificationSite\":\"QVI\"}";
        Emails email = objectMapper.readValue(contentData, Emails.class);
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordEmail(gin, contentData));

        // Mocking call to salesforce API
        ResponseEntity<WrapperUpsertIndividusResponse> responseOK = new ResponseEntity<>(null, HttpStatus.OK);
        when(sendToSfmcMS.sendIndividusDataToSfmc(any(UpsertIndividusRequestCriteria.class), any(String.class))).thenReturn(responseOK);

        // Execute the filterAndProcessKafkaMsg method to process the Kafka message.
        ResponseEntity<WrapperUpsertIndividusResponse> wrapperUpsertIndividusResponseResponseEntity = kafkaDatabaseConsumer.filterAndProcessKafkaMsg(consumerRecord);
        UpsertIndividusRequestCriteria upsertIndividusRqCriteria = kafkaDatabaseConsumer.getUpsertedIndividu();

        Map<String, String> values = upsertIndividusRqCriteria.getIndividusList().get(0).getValues();

        // Assertions
        assertNotNull(upsertIndividusRqCriteria);
        assertEquals(HttpStatus.OK, wrapperUpsertIndividusResponseResponseEntity.getStatusCode());

        assertNotNull(upsertIndividusRqCriteria);
        assertTrue(upsertIndividusRqCriteria.isEligible());
        assertEquals(email.getGin(), values.get(Profiles.GIN.name()));
        assertEquals(email.getEmail(), values.get(Profiles.Email_Address.name()));
        assertEquals(email.getMediumStatus(), values.get(Profiles.Email_Address_status.name()));
    }

    @Test
    void filterAndProcessKafkaMsg_Contract_Ok() throws BusinessException, JsonProcessingException {
        // Prepare test data for the Kafka message and mock dependencies.
        String gin = "400392865772";
        String contentData = "{\"identifier\":\"54995980\",\"gin\":\"400392865772\",\"number\":\"002093711464\",\"type\":\"FP\",\"subType\":null,\"company\":\"BB\",\"status\":\"C\",\"validityStartDate\":\"2023-09-08T14:47:11\",\"validityEndDate\":\"2026-09-30T16:48:52\",\"creationDate\":\"2023-09-08T14:47:11\",\"creationSignature\":\"TEST\",\"creationSite\":\"QVI\",\"modificationDate\":\"2023-09-08T14:49:04\",\"modificationSignature\":\"TEST\",\"modificationSite\":\"QVI\"}";
        Contracts contract = objectMapper.readValue(contentData, Contracts.class);
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordContract(gin, contentData));

        // Mocking call to salesforce API
        ResponseEntity<WrapperUpsertIndividusResponse> responseOK = new ResponseEntity<>(null, HttpStatus.OK);
        when(sendToSfmcMS.sendIndividusDataToSfmc(any(UpsertIndividusRequestCriteria.class), any(String.class))).thenReturn(responseOK);

        // Execute the filterAndProcessKafkaMsg method to process the Kafka message.
        ResponseEntity<WrapperUpsertIndividusResponse> wrapperUpsertIndividusResponseResponseEntity = kafkaDatabaseConsumer.filterAndProcessKafkaMsg(consumerRecord);
        UpsertIndividusRequestCriteria upsertIndividusRqCriteria = kafkaDatabaseConsumer.getUpsertedIndividu();

        Map<String, String> values = upsertIndividusRqCriteria.getIndividusList().get(0).getValues();

        // Assertions
        assertNotNull(upsertIndividusRqCriteria);
        assertEquals(HttpStatus.OK, wrapperUpsertIndividusResponseResponseEntity.getStatusCode());

        assertNotNull(upsertIndividusRqCriteria);
        assertTrue(upsertIndividusRqCriteria.isEligible());
        assertEquals(contract.getGin(), values.get(Profiles.GIN.name()));
        assertEquals(contract.getNumber(), values.get(Profiles.CIN.name()));
        assertEquals(contract.getCreationDate(), values.get(Profiles.FB_Enrollment_Date.name()));
    }

    @Test
    void filterAndProcessKafkaMsg_Preference_Ok() throws BusinessException, JsonProcessingException {
        // Prepare test data for the Kafka message and mock dependencies.
        String gin = "400410574999";
        String contentData = "{\"identifier\":1099999,\"gin\":\"400410574999\",\"type\":\"TPC\",\"linkIdentifier\":null}";
        Preference preference = objectMapper.readValue(contentData, Preference.class);
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordPreference(gin, contentData));

        // Mocking call to salesforce API
        ResponseEntity<WrapperUpsertIndividusResponse> responseOK = new ResponseEntity<>(null, HttpStatus.OK);
        when(sendToSfmcMS.sendIndividusDataToSfmc(any(UpsertIndividusRequestCriteria.class), any(String.class))).thenReturn(responseOK);

        // Execute the filterAndProcessKafkaMsg method to process the Kafka message.
        ResponseEntity<WrapperUpsertIndividusResponse> wrapperUpsertIndividusResponseResponseEntity = kafkaDatabaseConsumer.filterAndProcessKafkaMsg(consumerRecord);
        UpsertIndividusRequestCriteria upsertIndividusRqCriteria = kafkaDatabaseConsumer.getUpsertedIndividu();

        Map<String, String> values = upsertIndividusRqCriteria.getIndividusList().get(0).getValues();

        // Assertions
        assertNotNull(upsertIndividusRqCriteria);
        assertEquals(HttpStatus.OK, wrapperUpsertIndividusResponseResponseEntity.getStatusCode());

        assertNotNull(upsertIndividusRqCriteria);
        assertTrue(upsertIndividusRqCriteria.isEligible());
        assertEquals(preference.getGin(), values.get(Profiles.GIN.name()));
        assertEquals("CDG", values.get(Profiles.preferred_departure_airport.name()));
        assertEquals("FCO", values.get(Profiles.departure_airport_kl.name()));
    }

    @Test
    void filterAndProcessKafkaMsg_PostalAdress_Ok() throws BusinessException, JsonProcessingException {
        // Prepare test data for the Kafka message and mock dependencies.
        String gin = "400410574999";
        String contentData = "{\"identifier\":\"1071899999\",\"gin\":\"400410574999\",\"mediumCode\":\"D\",\"mediumStatus\":\"V\",\"streetNumber\":\"141 WEST 54TH STREET\",\"additionalInformation\":null,\"district\":null,\"city\":\"BERLIN\",\"zipCode\":null,\"stateCode\":null,\"countryCode\":\"DE\",\"creationDate\":\"2022-05-09T16:47:20\",\"creationSignature\":\"CustomerAPI\",\"creationSite\":\"AMS\",\"modificationDate\":\"2023-09-08T12:15:09\",\"modificationSignature\":\"REPIND/IHM\",\"modificationSite\":\"QVI\"}";
        PostalAddress postalAddress = objectMapper.readValue(contentData, PostalAddress.class);
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordPostalAddress(gin, contentData));

        // Mocking call to salesforce API
        ResponseEntity<WrapperUpsertIndividusResponse> responseOK = new ResponseEntity<>(null, HttpStatus.OK);
        when(sendToSfmcMS.sendIndividusDataToSfmc(any(UpsertIndividusRequestCriteria.class), any(String.class))).thenReturn(responseOK);

        // Execute the filterAndProcessKafkaMsg method to process the Kafka message.
        ResponseEntity<WrapperUpsertIndividusResponse> wrapperUpsertIndividusResponseResponseEntity = kafkaDatabaseConsumer.filterAndProcessKafkaMsg(consumerRecord);
        UpsertIndividusRequestCriteria upsertIndividusRqCriteria = kafkaDatabaseConsumer.getUpsertedIndividu();

        Map<String, String> values = upsertIndividusRqCriteria.getIndividusList().get(0).getValues();

        // Assertions
        assertNotNull(upsertIndividusRqCriteria);
        assertEquals(HttpStatus.OK, wrapperUpsertIndividusResponseResponseEntity.getStatusCode());

        assertNotNull(upsertIndividusRqCriteria);
        assertTrue(upsertIndividusRqCriteria.isEligible());
        assertEquals(postalAddress.getGin(), values.get(Profiles.GIN.name()));
        assertEquals(postalAddress.getCountryCode(), values.get(Profiles.Country.name()));
        assertEquals(postalAddress.getZipCode(), values.get(Profiles.Zip_code.name()));
        assertEquals(postalAddress.getCity(), values.get(Profiles.City.name()));

    }

    // keep test disabled until we modify behavior of sending new compref to sfmc from SALESFORCE
    @Test
    void filterAndProcessKafkaMsg_MarketLanguage_Ok() throws BusinessException, JsonProcessingException {
        // Prepare test data for the Kafka message and mock dependencies.
        String gin = "400410574999";
        String contentData = "{\"id\":73666999,\"comPrefId\":106187999,\"market\":\"NL\",\"language\":\"NL\",\"optin\":\"N\",\"optinDate\":\"2013-06-25T09:19:02.508000\", \"creationDate\":\"2013-06-25T09:19:02.508000\", \"creationSignature\":\"QVI\", \"creationSite\":\"QVI\", \"modificationDate\":\"2013-06-25T09:19:02.508000\", \"modificationSignature\":\"QVI\",\"modificationSite\":\"QVI\"}";
        MarketLanguages marketLanguage = objectMapper.readValue(contentData, MarketLanguages.class);
        ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>("topic", 0, 0, "key", GenerateTestData.buildMockedConsumerRecordMarketLanguage(gin, contentData));

        // Mocking call to salesforce API
        ResponseEntity<WrapperUpsertIndividusResponse> responseOK = new ResponseEntity<>(null, HttpStatus.OK);
        when(sendToSfmcMS.sendIndividusDataToSfmc(any(UpsertIndividusRequestCriteria.class), any(String.class))).thenReturn(responseOK);

        // Execute the filterAndProcessKafkaMsg method to process the Kafka message.
        ResponseEntity<WrapperUpsertIndividusResponse> wrapperUpsertIndividusResponseResponseEntity = kafkaDatabaseConsumer.filterAndProcessKafkaMsg(consumerRecord);
        UpsertIndividusRequestCriteria upsertIndividusRqCriteria = kafkaDatabaseConsumer.getUpsertedIndividu();

        Map<String, String> values = upsertIndividusRqCriteria.getIndividusList().get(0).getValues();

        CommunicationPreferencesEntity comPref = communicationPreferencesRepository.getComPrefByComPrefId(Long.valueOf(marketLanguage.getComPrefId()));

        // Assertions
        assertNotNull(upsertIndividusRqCriteria);
        assertEquals(HttpStatus.OK, wrapperUpsertIndividusResponseResponseEntity.getStatusCode());

        assertNotNull(upsertIndividusRqCriteria);
        assertTrue(upsertIndividusRqCriteria.isEligible());
        assertEquals(comPref.getIndividu().getGin(), values.get(Profiles.GIN.name()));
        assertEquals(marketLanguage.getMarket(), values.get(Profiles.Country_Code.name()));
        assertEquals(marketLanguage.getLanguage(), values.get(Profiles.Language_Code.name()));
        assertEquals(marketLanguage.getCreationSignature(), values.get(Profiles.Adhoc_SubscriptionCode.name()));
        assertEquals(marketLanguage.getOptin(), values.get(Profiles.Status.name()));
        assertEquals(marketLanguage.getCreationSignature(), values.get(Profiles.Signup_Source.name()));
        assertEquals(marketLanguage.getCreationDate(), values.get(Profiles.SignUpDate.name()));
        assertEquals(marketLanguage.getModificationSignature(), values.get(Profiles.Update_Source.name()));
        assertEquals(marketLanguage.getModificationDate(), values.get(Profiles.Update_Date.name()));
        assertEquals(marketLanguage.getOptinDate(), values.get(Profiles.Optin_Date.name()));
    }

}
