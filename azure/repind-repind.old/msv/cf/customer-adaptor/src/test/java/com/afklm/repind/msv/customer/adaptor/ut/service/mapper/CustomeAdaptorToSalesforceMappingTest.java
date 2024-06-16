package com.afklm.repind.msv.customer.adaptor.ut.service.mapper;

import com.afklm.repind.common.entity.individual.ProfilsEntity;
import com.afklm.repind.common.entity.preferences.CommunicationPreferencesEntity;
import com.afklm.repind.common.entity.preferences.PreferenceDataEntity;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.individual.ProfilsRepository;
import com.afklm.repind.common.repository.preferences.CommunicationPreferencesRepository;
import com.afklm.repind.common.repository.preferences.PreferenceDataRepository;
import com.afklm.repind.msv.customer.adaptor.model.repind.*;
import com.afklm.repind.msv.customer.adaptor.model.salesforce.Profiles;
import com.afklm.repind.msv.customer.adaptor.service.mapper.CustomeAdaptorToSalesforceMapping;
import com.afklm.repind.msv.customer.adaptor.utils.GenerateTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CustomeAdaptorToSalesforceMappingTest {

    @Mock
    private PreferenceDataRepository preferenceDataRepository;

    @Mock
    private CommunicationPreferencesRepository communicationPreferencesRepository;

    @Mock
    private ProfilsRepository profilsRepository;

    @InjectMocks
    CustomeAdaptorToSalesforceMapping customeAdaptorToSalesforceMapping;

    @Test
    void testMapToIndividus() {
        Individus individus = new Individus();
        String gin = "400410574567";
        individus.setIdentifier(gin);
        individus.setFirstname("Mohamed");
        individus.setLastname("Jadar");
        individus.setGender("M");
        individus.setBirthdate("1999-06-05");
        individus.setCivility("MR");

        ProfilsEntity profilsEntity = new ProfilsEntity();
        profilsEntity.setCodeLangue("EN");

        when(profilsRepository.getProfilsEntityByGin(anyString())).thenReturn(profilsEntity);

        Map<String, String> result = customeAdaptorToSalesforceMapping.mapToIndividus(individus);

        assertNotNull(result);
        assertEquals(individus.getFirstname(), result.get(Profiles.Firstname.name()));
        assertEquals(individus.getLastname(), result.get(Profiles.Surname.name()));
        assertEquals(individus.getGender(), result.get(Profiles.Gender.name()));
        assertEquals(individus.getBirthdate(), result.get(Profiles.Birthdate.name()));
        assertEquals(individus.getCivility(), result.get(Profiles.Civility.name()));
        assertEquals(profilsEntity.getCodeLangue(), result.get(Profiles.Language.name()));
    }

    @Test
    void testMapToPostalAddress_statusV(){
        // Create a mock PostalAddress object
        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setCountryCode("FR");
        postalAddress.setMediumCode("P");
        postalAddress.setMediumStatus("V");
        postalAddress.setZipCode("06200");
        postalAddress.setCity("NICE");

        // Call the method
        Map<String, String> result = customeAdaptorToSalesforceMapping.mapToPostalAddress(postalAddress);

        // Assert the result
        assertNotNull(result);
        assertEquals(postalAddress.getCountryCode(), result.get(Profiles.Country.name()));
        assertEquals(postalAddress.getZipCode(), result.get(Profiles.Zip_code.name()));
        assertEquals(postalAddress.getCity(), result.get(Profiles.City.name()));
    }

    @Test
    void testMapToPostalAddress_statusX(){
        // Create a mock PostalAddress object
        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setCountryCode("FR");
        postalAddress.setMediumCode("P");
        postalAddress.setMediumStatus("X");
        postalAddress.setZipCode("06200");
        postalAddress.setCity("NICE");

        // Call the method
        Map<String, String> result = customeAdaptorToSalesforceMapping.mapToPostalAddress(postalAddress);

        // Assert the result
        assertNotNull(result);
        assertNull(result.get(Profiles.Country.name()));
        assertNull(result.get(Profiles.Zip_code.name()));
        assertNull(result.get(Profiles.City.name()));
    }

    @Test
    void testMapToPreferences() throws BusinessException {
        Preference preference = new Preference();
        preference.setIdentifier("12345");

        PreferenceDataEntity preferenceData1 = new PreferenceDataEntity();
        preferenceData1.setKey("preferredDestinationContinent");
        preferenceData1.setValue("Europe");
        PreferenceDataEntity preferenceData2 = new PreferenceDataEntity();
        preferenceData2.setKey("holidayType");
        preferenceData2.setValue("CITYTRIP");
        PreferenceDataEntity preferenceData3 = new PreferenceDataEntity();
        preferenceData3.setKey("departureAirportKL");
        preferenceData3.setValue("Amsterdam");

        List<PreferenceDataEntity> preferenceDataList = new ArrayList<>(List.of(preferenceData1, preferenceData2, preferenceData3));

        when(preferenceDataRepository.getPreferenceDataEntitiesByPreferenceId(anyLong())).thenReturn(preferenceDataList);

        Map<String, String> result = customeAdaptorToSalesforceMapping.mapToPreferences(preference);

        assertNotNull(result);
        assertEquals("Europe", result.get(Profiles.preferred_destination_continent.name()));
        assertEquals("CITYTRIP", result.get(Profiles.holiday_type.name()));
        assertEquals("Amsterdam", result.get(Profiles.departure_airport_kl.name()));
        assertFalse(result.containsKey(Profiles.preferred_destination_city.name()));
    }

    @Test
    void testMapToMarketLanguage() {
        CommunicationPreferencesEntity comPref = new CommunicationPreferencesEntity();
        comPref.setComPrefId(12345L);
        comPref.setComType("KL");

        MarketLanguages marketLanguage = new MarketLanguages();
        marketLanguage.setComPrefId("12345");
        marketLanguage.setMarket("EN");
        marketLanguage.setLanguage("EN");
        marketLanguage.setCreationSignature("QVI");
        marketLanguage.setOptin("Y");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        marketLanguage.setCreationDate(timestamp.toString());
        marketLanguage.setModificationSignature("QVI");
        marketLanguage.setModificationDate(timestamp.toString());
        marketLanguage.setOptinDate(timestamp.toString());

        when(communicationPreferencesRepository.getComPrefByComPrefId(anyLong())).thenReturn(comPref);

        Map<String, String> result = customeAdaptorToSalesforceMapping.mapToMarketLanguage(marketLanguage);

        assertNotNull(result);
        assertEquals(comPref.getComType(), result.get(Profiles.Subscription_Type.name()));
        assertEquals(marketLanguage.getMarket(), result.get(Profiles.Country_Code.name()));
        assertEquals(marketLanguage.getLanguage(), result.get(Profiles.Language_Code.name()));
        assertEquals(marketLanguage.getCreationSignature(), result.get(Profiles.Adhoc_SubscriptionCode.name()));
        assertEquals(marketLanguage.getOptin(), result.get(Profiles.Status.name()));
        assertEquals(marketLanguage.getCreationSignature(), result.get(Profiles.Signup_Source.name()));
        assertEquals(marketLanguage.getCreationDate(), result.get(Profiles.SignUpDate.name()));
        assertEquals(marketLanguage.getModificationSignature(), result.get(Profiles.Update_Source.name()));
        assertEquals(marketLanguage.getModificationDate(), result.get(Profiles.Update_Date.name()));
        assertEquals(marketLanguage.getOptinDate(), result.get(Profiles.Optin_Date.name()));
    }

    @Test
    void testMapToEmails_statusV(){
        Emails email = new Emails();
        email.setEmail("mjadar@airfrance.fr");
        email.setMediumStatus("V");

        Map<String, String> result = customeAdaptorToSalesforceMapping.mapToEmails(email);

        assertNotNull(result);
        assertEquals(email.getEmail(), result.get(Profiles.Email_Address.name()));
        assertEquals(email.getMediumStatus(), result.get(Profiles.Email_Address_status.name()));
    }

    @Test
    void testMapToEmails_statusX(){
        Emails email = new Emails();
        email.setEmail("mjadar@airfrance.fr");
        email.setMediumStatus("X");

        Map<String, String> result = customeAdaptorToSalesforceMapping.mapToEmails(email);

        assertNotNull(result);
        assertNull(result.get(Profiles.Email_Address.name()));
        assertNull(result.get(Profiles.Email_Address_status.name()));
    }

    @Test
    void testMapToContractsForFPContract() throws BusinessException {
        Contracts contract = new Contracts();
        contract.setType("FP");
        contract.setNumber("123");
        contract.setCreationDate("2023-08-29");

        Map<String, String> result = customeAdaptorToSalesforceMapping.mapToContracts(contract);

        assertEquals(contract.getNumber(), result.get(Profiles.CIN.name()));
        assertEquals(contract.getCreationDate(), result.get(Profiles.FB_Enrollment_Date.name()));
    }

    @Test
    void testMapToContractsForMAContract() throws BusinessException {
        Contracts contract = new Contracts();
        contract.setType("MA");
        contract.setNumber("456");
        contract.setCreationDate("2023-08-29");

        Map<String, String> result = customeAdaptorToSalesforceMapping.mapToContracts(contract);

        assertEquals(contract.getNumber(), result.get(Profiles.MyAccountID.name()));
        assertEquals(contract.getCreationDate(), result.get(Profiles.MA_Enrollment_Date.name()));
    }

    @Test
    void testMapToContractsForOtherContractType(){
        Contracts contracts = new Contracts();
        contracts.setType("Other");
        contracts.setNumber("789");
        contracts.setCreationDate("2023-08-29");

        Assertions.assertThrows(BusinessException.class, () -> {
            customeAdaptorToSalesforceMapping.mapToContracts(contracts);
        });
    }

    @Test
    void testMapToTmpFields(){
        TmpProfile tmpProfile = GenerateTestData.buildMockedTmpProfile();

        Map<String, String> result = customeAdaptorToSalesforceMapping.mapToTmpFields(tmpProfile);

        assertNotNull(result);
        assertEquals(tmpProfile.getGin(), result.get(Profiles.GIN.name()));
        assertEquals(tmpProfile.getFirstname(), result.get(Profiles.Firstname.name()));
        assertEquals(tmpProfile.getLastname(), result.get(Profiles.Surname.name()));
        assertEquals(tmpProfile.getGender(), result.get(Profiles.Gender.name()));
        assertEquals(tmpProfile.getCivility(), result.get(Profiles.Civility.name()));
        assertEquals(tmpProfile.getBirthdate(), result.get(Profiles.Birthdate.name()));
        assertEquals(tmpProfile.getEmail(), result.get(Profiles.Email_Address.name()));
        assertEquals(tmpProfile.getEmailStatus(), result.get(Profiles.Email_Address_status.name()));
        assertEquals(tmpProfile.getZipCode(), result.get(Profiles.Zip_code.name()));
        assertEquals(tmpProfile.getCity(), result.get(Profiles.City.name()));
        assertEquals(tmpProfile.getCountry(), result.get(Profiles.Country.name()));
        assertEquals(tmpProfile.getCin(), result.get(Profiles.CIN.name()));
        assertEquals(tmpProfile.getFbEnrollmentDate(), result.get(Profiles.FB_Enrollment_Date.name()));
        assertEquals(tmpProfile.getMyAccountId(), result.get(Profiles.MyAccountID.name()));
        assertEquals(tmpProfile.getMaEnrollmentDate(), result.get(Profiles.MA_Enrollment_Date.name()));

    }
}
