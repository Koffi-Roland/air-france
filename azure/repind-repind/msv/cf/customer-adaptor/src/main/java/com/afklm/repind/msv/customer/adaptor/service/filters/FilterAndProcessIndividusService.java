package com.afklm.repind.msv.customer.adaptor.service.filters;

import com.afklm.repind.common.exception.BusinessException;
import com.afklm.repind.common.exception.DataInitializationException;
import com.afklm.repind.msv.customer.adaptor.model.criteria.UpsertIndividusRequestCriteria;
import com.afklm.repind.msv.customer.adaptor.model.repind.*;
import com.afklm.repind.msv.customer.adaptor.service.mapper.CustomeAdaptorToSalesforceMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.afklm.repind.msv.customer.adaptor.helper.Constant.*;

@Service
public class FilterAndProcessIndividusService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FilteringRulesService filteringRulesService;

    @Autowired
    CustomeAdaptorToSalesforceMapping toSalesforceMapping;


    private static final Logger LOGGER = LoggerFactory.getLogger(FilterAndProcessIndividusService.class);

    /**
     * Method takes as input consumer record, will process it, verify eligibilty then creae request to be sent to Sfmc (salesforce)
     * @param consumerRecord input consumer record from kafka topic
     * @param uuid unique identifier for the request
     * @return UpsertIndividusRequestCriteria
     * @throws BusinessException exception
     * @throws DataInitializationException exception
     */
    public UpsertIndividusRequestCriteria filterAndProcessIndividusKafkaData(ConsumerRecord<String, String> consumerRecord, String uuid) throws BusinessException, DataInitializationException {
        UpsertIndividusRequestCriteria upsertIndividusRequestCriteria = new UpsertIndividusRequestCriteria();

        //retrieve objects list in the kafka message grouped by gin
        IndividusDataModel individusDataModel = getIndividusAndFilterData(consumerRecord, uuid);

        if(individusDataModel == null){
            throw new IllegalArgumentException("[-] Individus data model is null.");
        }

        if(individusDataModel.isEligible()){
            // fetch temporary fields from other tables and build TmpProfile entity
            TmpProfile tmpProfile = filteringRulesService.fetchTmpProfile(individusDataModel.getGin());
            individusDataModel.setTmpProfile(tmpProfile);
            //Create Individus Request Data Criteria
            upsertIndividusRequestCriteria = toSalesforceMapping.getIndividusRequestCriteria(individusDataModel, uuid);
        }else{
            upsertIndividusRequestCriteria.setEligible(false);
            upsertIndividusRequestCriteria.setMessage(individusDataModel.getMessage());
        }
        upsertIndividusRequestCriteria.setGin(individusDataModel.getGin());

        return upsertIndividusRequestCriteria;
    }


    /**
     * this method reads the consumerRecord in input and choose the right way to process based on the field <TABLENAME> in the payload
     * @param consumerRecord record from consumer
     * @param uuid  unique identifier for the request
     * @return IndividusDataModel
     */
    public IndividusDataModel getIndividusAndFilterData(ConsumerRecord<String, String> consumerRecord, String uuid) throws DataInitializationException{
        LOGGER.info("[+] Start running filters.. uuid={}", uuid);

        try {
            //Retrieve the object list from the kafka message + preprocess to make field CONTENT_DATA as json (workaround)
            JsonNode jsonValues = objectMapper.readTree(preprocessContentJson(consumerRecord.value()));
            if (jsonValues == null) {
                LOGGER.warn("[-] uuid={} - objectMapper.readTree - Jsonvalues are null", uuid);
                throw new DataInitializationException("[-] Json values is null could not preprocess data.");
            }

            JsonNode payload = jsonValues.get(CONTENT_DATA);
            String tableName = jsonValues.get(TABLE_NAME).asText();
            String action    = jsonValues.get(ACTION_TYPE).asText();
            String gin       = jsonValues.get(GIN).asText();

            if (tableName == null || payload == null || action==null) {
                LOGGER.info("[-] uuid={} - getIndividusAndFilterData() - tableName or payload is null", uuid);
                throw new DataInitializationException("[-] Payload/fields are empty or null.");
            }

            IndividusDataModel individusDataModel;
            filteringRulesService.clearMessage();

            switch (tableName) {
                case EMAILS -> {
                    // mapping email object from kafka message
                    Emails email = objectMapper.treeToValue(payload, Emails.class);
                    individusDataModel = processEmail(email, uuid);
                }
                case INDIVIDUS -> {
                    // mapping individu object from kafka message
                    Individus individu = objectMapper.treeToValue(payload, Individus.class);
                    individusDataModel = processIndividu(individu, uuid);
                }
                case ROLE_CONTRATS -> {
                    Contracts contract = objectMapper.treeToValue(payload, Contracts.class);
                    individusDataModel = processContract(contract, uuid);
                }
                case PREFERENCE -> {
                    Preference preference = objectMapper.treeToValue(payload, Preference.class);
                    individusDataModel = processPreference(preference, uuid);
                }
                case MARKET_LANGUAGE -> {
                    MarketLanguages marketLanguages = objectMapper.treeToValue(payload, MarketLanguages.class);
                    marketLanguages.setGin(jsonValues.get(GIN).asText());
                    individusDataModel = processMarketLanguage(marketLanguages, uuid);
                }
                case ADR_POST -> {
                    PostalAddress postalAddress = objectMapper.treeToValue(payload, PostalAddress.class);
                    individusDataModel = processPostalAddress(postalAddress, uuid);
                }
                default ->{
                    String logMsg = "[-] uuid=" + uuid + " Unrecognized table " + tableName + " Unrecognized tableName, could not initialize IndividusDataModel.";
                    throw new DataInitializationException(logMsg);
                }
            }
            if(individusDataModel != null){
                individusDataModel.setAction(action);
                individusDataModel.setTableName(tableName);
                individusDataModel.setGin(gin);
                individusDataModel.setMessage(filteringRulesService.getMessage());
                LOGGER.info("[+] uuid={} getIndividusAndFilterData() -> individusDataModel : -> {}", uuid, individusDataModel);
            }
            return individusDataModel;
        }catch (JsonProcessingException e){
            throw new DataInitializationException("[-] Error processing JSON, couldn't initialize DataModel.");
        }
    }

    /**
     * This method takes as input a model of type Individus and then check for rules if it is eligible to send to sfmc
     * @param individu individus model
     * @param uuid unique identifier for the request
     * @return IndividusDataModel
     */
    public IndividusDataModel processIndividu(Individus individu, String uuid) {
        IndividusDataModel individusDataModel = new IndividusDataModel();
        String gin = individu.getIdentifier();
        individusDataModel.setGin(gin);
        if (gin == null
                || filteringRulesService.ruleComprefIsNotKLSubscribe(gin)
                || filteringRulesService.ruleOptinValue(gin)
                || filteringRulesService.ruleComprefSubscriptionNotExist(gin)
                || filteringRulesService.ruleComprefMarketLanguageNotExist(gin)
                || filteringRulesService.ruleRoleContract(gin)
                || filteringRulesService.ruleBirthdateMajor(gin)
                || filteringRulesService.ruleEmailValidity(gin)) {
            LOGGER.info("[processIndividu] - uuid={} GIN={} for Individu is not eligible to send to SFMC", uuid, gin);
            individusDataModel.setEligible(false);
        } else {
            LOGGER.info("[processIndividu] - uuid={} GIN={} for Individu is eligible to send to SFMC", uuid, gin);
            individusDataModel.setIndividus(individu);
            individusDataModel.setEligible(true);
        }
        return individusDataModel;
    }


    /**
     * This method takes as input a model of type MarketLanguages and then check for rules if it is eligible to send to sfmc
     * @param marketLanguages Market_Language model
     * @param uuid unique identifier for the request
     * @return IndividusDataModel
     */
    public IndividusDataModel processMarketLanguage(MarketLanguages marketLanguages, String uuid) {
        IndividusDataModel individusDataModel = new IndividusDataModel();
        String gin = marketLanguages.getGin();
        individusDataModel.setGin(gin);
        Long comprefId = Long.valueOf(marketLanguages.getComPrefId());
        if (gin == null
                || filteringRulesService.ruleComprefIsNotKLSubscribeByComprefId(comprefId)
                || filteringRulesService.ruleRoleContract(gin)
                || filteringRulesService.ruleBirthdateMajor(gin)
                || filteringRulesService.ruleEmailValidity(gin)) {
            LOGGER.info("[processMarketLanguage] - uuid={} GIN={} for MarketLanguages is not eligible to send to SFMC", uuid, gin);
            individusDataModel.setEligible(false);
        } else {
            LOGGER.info("[processMarketLanguage] - uuid={} GIN={} for MarketLanguages is eligible to send to SFMC", uuid, gin);
            individusDataModel.setMarketLanguages(marketLanguages);
            individusDataModel.setEligible(true);
        }
        return individusDataModel;
    }

    /**
     * This method takes as input a model of type Contracts and then check for rules if it is eligible to send to sfmc
     * @param contract Contract model
     * @param uuid unique identifier for the request
     * @return IndividusDataModels
     */
    public IndividusDataModel processContract(Contracts contract, String uuid) {
        IndividusDataModel individusDataModel = new IndividusDataModel();
        String gin = contract.getGin();
        individusDataModel.setGin(gin);
        if (gin == null
                || filteringRulesService.ruleComprefIsNotKLSubscribe(gin)
                || filteringRulesService.ruleOptinValue(gin)
                || filteringRulesService.ruleComprefSubscriptionNotExist(gin)
                || filteringRulesService.ruleComprefMarketLanguageNotExist(gin)
                || filteringRulesService.ruleBirthdateMajor(gin)
                || filteringRulesService.ruleEmailValidity(gin)) {
            LOGGER.info("[processContract] - uuid={} GIN={} for Contract is not eligible to send to SFMC", uuid, gin);
            individusDataModel.setEligible(false);
        } else {
            LOGGER.info("[processContract] - uuid={} GIN={} for Contract is eligible to send to SFMC", uuid, gin);
            individusDataModel.setContracts(contract);
            individusDataModel.setEligible(true);
        }
        return individusDataModel;
    }

    /**
     * This method takes as input a model of type PostalAddress and then check for rules if it is eligible to send to sfmc
     * @param postalAddress Postal Address model
     * @param uuid unique identifier for the request
     * @return IndividusDataModel
     */
    public IndividusDataModel processPostalAddress(PostalAddress postalAddress, String uuid) {
        IndividusDataModel individusDataModel = new IndividusDataModel();
        String gin = postalAddress.getGin();
        individusDataModel.setGin(gin);
        if (gin == null
                || filteringRulesService.ruleComprefIsNotKLSubscribe(gin)
                || filteringRulesService.ruleOptinValue(gin)
                || filteringRulesService.ruleComprefSubscriptionNotExist(gin)
                || filteringRulesService.ruleComprefMarketLanguageNotExist(gin)
                || filteringRulesService.ruleRoleContract(gin)
                || filteringRulesService.ruleBirthdateMajor(gin)
                || filteringRulesService.ruleEmailValidity(gin)
                || filteringRulesService.rulePostalAddressPro(postalAddress)
                ||  filteringRulesService.rulePostalAddressInvalid(postalAddress)) {
            LOGGER.info("[processPostalAddress] - uuid={} GIN={} for PostalAddress is not eligible to send to SFMC", uuid, gin);
            individusDataModel.setEligible(false);
        } else {
            LOGGER.info("[processPostalAddress] - uuid={} GIN={} for PostalAddress is eligible to send to SFMC", uuid, gin);
            individusDataModel.setPostalAddress(postalAddress);
            individusDataModel.setEligible(true);
        }
        return individusDataModel;
    }

    /**
     * This method takes as input a model of type Preference and then check for rules if it is eligible to send to sfmc
     * @param preference Preference model
     * @param uuid unique identifier for the request
     * @return IndividusDataModel
     */
    public IndividusDataModel processPreference(Preference preference, String uuid) {
        IndividusDataModel individusDataModel = new IndividusDataModel();
        String gin = preference.getGin();
        individusDataModel.setGin(gin);
        if (gin == null
                || filteringRulesService.ruleComprefIsNotKLSubscribe(gin)
                || filteringRulesService.ruleOptinValue(gin)
                || filteringRulesService.ruleComprefSubscriptionNotExist(gin)
                || filteringRulesService.ruleComprefMarketLanguageNotExist(gin)
                || filteringRulesService.ruleRoleContract(gin)
                || filteringRulesService.ruleBirthdateMajor(gin)
                || filteringRulesService.ruleEmailValidity(gin)) {
            LOGGER.info("[processPreference] - uuid={} GIN={} for Preference is not eligible to send to SFMC", uuid, gin);
            individusDataModel.setEligible(false);
        } else {
            LOGGER.info("[processPreference] - uuid={} GIN={} for Preference is eligible to send to SFMC", uuid, gin);
            individusDataModel.setPreference(preference);
            individusDataModel.setEligible(true);
        }
        return individusDataModel;
    }

    /**
     * This method takes as input a model of type Emails and then check for rules if it is eligible to send to sfmc
     * @param email Email model
     * @param uuid unique identifier for the request
     * @return IndividusData model
     */
    public IndividusDataModel processEmail(Emails email, String uuid) {
        IndividusDataModel individusDataModel = new IndividusDataModel();
        String gin =  email.getGin();
        individusDataModel.setGin(gin);
       if (gin == null
               || filteringRulesService.ruleComprefIsNotKLSubscribe(gin)
               || filteringRulesService.ruleOptinValue(gin)
               || filteringRulesService.ruleComprefSubscriptionNotExist(gin)
               || filteringRulesService.ruleComprefMarketLanguageNotExist(gin)
               || filteringRulesService.ruleRoleContract(gin)
               || filteringRulesService.ruleBirthdateMajor(gin)
               || filteringRulesService.ruleEmailPro(email)
       ) {
           LOGGER.info("[processEmail] - uuid={} GIN={} for Email is not eligible to send to SFMC", uuid, gin);
           individusDataModel.setEligible(false);
       } else {
           LOGGER.info("[processEmail] - uuid={} GIN={} for Email is eligible to send to SFMC", uuid, gin);
           individusDataModel.setEmails(email);
           individusDataModel.setEligible(true);
        }
       return individusDataModel;
    }

    public String getConsumerRecordTestDataIndividus(){
        return """
            {
                "AIN": "BHc=",
                "ACTION_DATE": 1693320343000,
                "GIN": "400424668522",
                "ACTION_TYPE": "UPDATE",
                "TABLE_NAME": "INDIVIDUS",
                "CONTENT_DATA": "{"identifier":"400392865772","civility":"M.","lastname":"TOMPONIONY","firstname":"LOHARANO","gender":"M","birthdate":"03-JAN-86","secondFirstname":"","status":"V","ginFusion":"","lastnameSC":"TOMPONIONY","firstnameSC":"LOHARANO","nationality":"","creationDate":"21-APR-14","creationSignature":"AF","creationSite":"GMMAL","modificationDate":"29-AUG-23","modificationSignature":"REPIND/IHM","modificationSite":"QVI","type":"I"}"
            }
            """;
    }


    /**
     * This method preprocess a record and remove brackets " when whe have json content.
     * @param inputRecord String input record
     * @return
     */
    private String preprocessContentJson(String inputRecord){
        // Define a regular expression pattern to capture content between curly braces including the braces themselves
        Pattern pattern =   Pattern.compile("\"\\{.*?}\"");

        // Match the pattern in the JSON string
        Matcher matcher = pattern.matcher(inputRecord);

        // Check if a match is found
        if (matcher.find()) {
            String matchedText = matcher.group(0);
            String replacedText  =  matchedText.substring(1, matchedText.length() - 1);
            replacedText = replacedText.replace("\\","");
            inputRecord = inputRecord.replaceAll(Pattern.quote(matchedText), replacedText);
        }
        return inputRecord;
    }

    public void setObjectMapper(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
}
