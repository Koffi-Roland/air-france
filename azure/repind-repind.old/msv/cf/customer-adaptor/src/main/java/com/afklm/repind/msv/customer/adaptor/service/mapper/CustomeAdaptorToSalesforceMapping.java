package com.afklm.repind.msv.customer.adaptor.service.mapper;

import com.afklm.repind.common.entity.individual.ProfilsEntity;
import com.afklm.repind.common.entity.preferences.PreferenceDataEntity;
import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.repository.individual.ProfilsRepository;
import com.afklm.repind.common.repository.preferences.CommunicationPreferencesRepository;
import com.afklm.repind.common.repository.preferences.PreferenceDataRepository;
import com.afklm.repind.msv.customer.adaptor.model.DataModel;
import com.afklm.repind.msv.customer.adaptor.model.criteria.UpsertIndividusRequestCriteria;
import com.afklm.repind.msv.customer.adaptor.model.error.BusinessError;
import com.afklm.repind.msv.customer.adaptor.model.repind.*;
import com.afklm.repind.msv.customer.adaptor.model.salesforce.Profiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("CustomeAdaptorToSalesforceMapping")
public class CustomeAdaptorToSalesforceMapping {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomeAdaptorToSalesforceMapping.class);
    @Autowired
    private PreferenceDataRepository preferenceDataRepository;
    @Autowired
    private CommunicationPreferencesRepository communicationPreferencesRepository;
    @Autowired
    private ProfilsRepository profilsRepository;

    public UpsertIndividusRequestCriteria getIndividusRequestCriteria(IndividusDataModel individusDataModel, String uuid) throws BusinessException {

        LOGGER.info("[+] uuid={}  - Start mapping for Salesforce upsert Profiles: {}", uuid, individusDataModel);

        UpsertIndividusRequestCriteria individusCriteria = new UpsertIndividusRequestCriteria();
        List<DataModel> models = new ArrayList<>();
        Map<String,String> keys = new HashMap<>();
        Map<String,String> targetValues = new HashMap<>();

        keys.put(Profiles.Guid.name(), uuid);

        if (individusDataModel.getTmpProfile() != null){
            targetValues.putAll(mapToTmpFields(individusDataModel.getTmpProfile()));
            LOGGER.info("[+] uuid={} GIN={} - Successfully mapped Tmp fields from input to sfmc fields", uuid, individusDataModel.getGin());
        }
        if(individusDataModel.getIndividus() != null){
            targetValues.putAll(mapToIndividus(individusDataModel.getIndividus()));
            LOGGER.info("[+] uuid={} GIN={} - Successfully mapped Individus fields from input to sfmc fields", uuid, individusDataModel.getGin());
        }else if(individusDataModel.getEmails() != null){
            targetValues.putAll(mapToEmails(individusDataModel.getEmails()));
            LOGGER.info("[+] uuid={} GIN={} - Successfully mapped Emails fields from input to sfmc fields", uuid, individusDataModel.getGin());
        }else if(individusDataModel.getMarketLanguages() != null){
            LOGGER.info("[+] uuid={} GIN={} - Successfully mapped MarketLanguage fields from input to sfmc fields", uuid, individusDataModel.getGin());
            targetValues.putAll(mapToMarketLanguage(individusDataModel.getMarketLanguages()));
        }else if(individusDataModel.getContracts() != null){
            targetValues.putAll(mapToContracts(individusDataModel.getContracts()));
            LOGGER.info("[+] uuid={} GIN={} - Successfully mapped contracts fields from input to sfmc fields", uuid, individusDataModel.getGin());
        }else if(individusDataModel.getPreference() != null){
            targetValues.putAll(mapToPreferences(individusDataModel.getPreference()));
            LOGGER.info("[+] uuid={} GIN={} - Successfully mapped Preference fields from input to sfmc fields", uuid, individusDataModel.getGin());
        }else if(individusDataModel.getPostalAddress() != null){
            targetValues.putAll(mapToPostalAddress(individusDataModel.getPostalAddress()));
            LOGGER.info("[+] uuid={} GIN={} - Successfully mapped PostalAddress fields from input to sfmc fields", uuid, individusDataModel.getGin());
        }

        targetValues.put(Profiles.GIN.name(), individusDataModel.getGin());
        targetValues.put(Profiles.Action.name(), individusDataModel.getAction());
        targetValues.put(Profiles.Table.name(), individusDataModel.getTableName());

        DataModel mappingProfiles = new DataModel();
        mappingProfiles.setKeys(keys);
        mappingProfiles.setValues(targetValues);

        models.add(mappingProfiles);

        individusCriteria.setIndividusList(models);
        individusCriteria.setEligible(individusDataModel.isEligible());

        return individusCriteria;
    }

    public Map<String,String> mapToIndividus(Individus individus) {
        ProfilsEntity profilsEntity = profilsRepository.getProfilsEntityByGin(individus.getIdentifier());

        Map<String,String> values = new HashMap<>();
        values.put(Profiles.Firstname.name(), individus.getFirstname());
        values.put(Profiles.Surname.name(), individus.getLastname());
        values.put(Profiles.Gender.name(), individus.getGender());
        values.put(Profiles.Birthdate.name(), individus.getBirthdate());
        values.put(Profiles.Civility.name(), individus.getCivility());
        if(profilsEntity != null ){
            values.put(Profiles.Language.name(), profilsEntity.getCodeLangue());
        }
        return values;
    }

    public Map<String, String> mapToPostalAddress(PostalAddress postalAddress) {
        Map<String,String> values = new HashMap<>();
        if( !("V".equals(postalAddress.getMediumStatus())) ){
            values.put(Profiles.Country.name(), null);
            values.put(Profiles.Zip_code.name(), null);
            values.put(Profiles.City.name(), null);
        }else{
            values.put(Profiles.Country.name(), postalAddress.getCountryCode());
            values.put(Profiles.Zip_code.name(), postalAddress.getZipCode());
            values.put(Profiles.City.name(), postalAddress.getCity());
        }

        return values;
    }

    public Map<String, String> mapToPreferences(Preference preferences) throws BusinessException {
        List<PreferenceDataEntity> preferenceDataList = preferenceDataRepository.getPreferenceDataEntitiesByPreferenceId(Long.parseLong(preferences.getIdentifier()));
        Map<String, String> values = new HashMap<>();

        if(preferenceDataList != null){
            preferenceDataList.forEach(preferenceData -> {
                String key = preferenceData.getKey();
                String value = preferenceData.getValue();

                switch (key) {
                    case "preferredDestinationContinent" ->
                            values.put(Profiles.preferred_destination_continent.name(), value);
                    case "preferredDestinationCity" -> values.put(Profiles.preferred_destination_city.name(), value);
                    case "departureAirportKL" -> values.put(Profiles.departure_airport_kl.name(), value);
                    case "holidayType" -> values.put(Profiles.holiday_type.name(), value);
                    case "preferredAirport" -> values.put(Profiles.preferred_departure_airport.name(), value);
                    case "countryOfResidence" -> values.put(Profiles.country_residence.name(), value);
                    default -> LOGGER.info("[-] Preference data key is not defined.");
                }
            });
        }else{
            LOGGER.error("[-] Exception - No Preference data eligible to send to sfmc");
            throw new BusinessException(BusinessError.NO_ELIGIBLE_DATA_FOUND_ERROR);
        }

        return values;
    }

    public Map<String,String> mapToMarketLanguage(MarketLanguages marketLanguage) {
        String subscriptionType = communicationPreferencesRepository.getComPrefByComPrefId(Long.valueOf(marketLanguage.getComPrefId())).getComType();

        Map<String, String> values = new HashMap<>();

        values.put(Profiles.GIN.name(), marketLanguage.getGin());

        if(subscriptionType != null){
            values.put(Profiles.Subscription_Type.name(), subscriptionType);
        }

        values.put(Profiles.Country_Code.name(), marketLanguage.getMarket());
        values.put(Profiles.Language_Code.name(), marketLanguage.getLanguage());
        values.put(Profiles.Adhoc_SubscriptionCode.name(), marketLanguage.getCreationSignature());
        values.put(Profiles.Status.name(), marketLanguage.getOptin());
        values.put(Profiles.Signup_Source.name(), marketLanguage.getCreationSignature());
        values.put(Profiles.SignUpDate.name(), marketLanguage.getCreationDate());
        values.put(Profiles.Update_Source.name(), marketLanguage.getModificationSignature());
        values.put(Profiles.Update_Date.name(), marketLanguage.getModificationDate());
        values.put(Profiles.Optin_Date.name(), marketLanguage.getOptinDate());

        return values;
    }

    public Map<String,String> mapToEmails(Emails emails) {
        Map<String,String> values = new HashMap<>();
        if( !("V".equals(emails.getMediumStatus())) ){
            values.put(Profiles.Email_Address.name(), null);
            values.put(Profiles.Email_Address_status.name(), null);
        }else{
            values.put(Profiles.Email_Address.name(), emails.getEmail());
            values.put(Profiles.Email_Address_status.name(), emails.getMediumStatus());
        }
        return values;
    }

    public Map<String,String> mapToContracts(Contracts contracts) throws BusinessException {
        Map<String,String> values = new HashMap<>();

        if("FP".equals(contracts.getType())){
            values.put(Profiles.CIN.name(), contracts.getNumber());
            values.put(Profiles.FB_Enrollment_Date.name(), contracts.getCreationDate());
        } else if ( "MA".equals(contracts.getType())) {
            values.put(Profiles.MyAccountID.name(), contracts.getNumber());
            values.put(Profiles.MA_Enrollment_Date.name(), contracts.getCreationDate());
        } else{
            LOGGER.error("[-] Exception - Contract is not supported");
            throw new BusinessException(BusinessError.CONTRACT_NOT_SUPPORTED_BUSINESS_ERROR);
        }
        return values;
    }

    public Map<String,String> mapToTmpFields(TmpProfile tmpProfile){
        Map<String,String> values = new HashMap<>();

        values.put(Profiles.GIN.name(), tmpProfile.getGin());
        values.put(Profiles.Firstname.name(), tmpProfile.getFirstname());
        values.put(Profiles.Surname.name(), tmpProfile.getLastname());
        values.put(Profiles.Gender.name(), tmpProfile.getGender());
        values.put(Profiles.Civility.name(), tmpProfile.getCivility());
        values.put(Profiles.Birthdate.name(), tmpProfile.getBirthdate());

        values.put(Profiles.Email_Address.name(), tmpProfile.getEmail());
        values.put(Profiles.Email_Address_status.name(), tmpProfile.getEmailStatus());

        values.put(Profiles.Zip_code.name(), tmpProfile.getZipCode());
        values.put(Profiles.City.name(), tmpProfile.getCity());
        values.put(Profiles.Country.name(), tmpProfile.getCountry());

        values.put(Profiles.CIN.name(), tmpProfile.getCin());
        values.put(Profiles.FB_Enrollment_Date.name(), tmpProfile.getFbEnrollmentDate());
        values.put(Profiles.MyAccountID.name(), tmpProfile.getMyAccountId());
        values.put(Profiles.MA_Enrollment_Date.name(), tmpProfile.getMaEnrollmentDate());

        return values;
    }

}
