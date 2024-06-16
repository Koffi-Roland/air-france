package com.airfrance.batch.prospect.processor;

import com.airfrance.batch.prospect.bean.AlimentationProspectItem;
import com.airfrance.batch.prospect.bean.ProspectInputRecord;
import com.airfrance.ref.type.YesNoFlagEnum;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Collections;
import java.util.Date;

import static com.airfrance.batch.prospect.helper.AlimentationProspectConstant.*;
import static com.airfrance.repind.util.SicDateUtils.localDateToCalendar;

public class AlimentationProspectItemProcessor implements ItemProcessor<ProspectInputRecord, AlimentationProspectItem> {

    private static final Log LOG = LogFactory.getLog(AlimentationProspectItemProcessor.class);

    @Override
    public AlimentationProspectItem process(ProspectInputRecord inputRecord) throws Exception {
        if (StringUtils.isNotBlank(inputRecord.getEmail())) {
            return mapRecordToItem(inputRecord);
        }
        else {
            return null;
        }
    }

    private AlimentationProspectItem mapRecordToItem(ProspectInputRecord inputRecord) {
        LOG.info("START mapping record for customer: " + inputRecord.getEmail());

        AlimentationProspectItem item = new AlimentationProspectItem();
        CreateUpdateIndividualRequestDTO requestDTO;

        // Default requestor to call Webservice DS
        requestDTO = initRequestDTO(inputRecord);

        // Email
        mapEmail(inputRecord, requestDTO, item);

        // Individual Data
        mapIndividualInformations(inputRecord, requestDTO);
        
        // individual Communication Preferences
        mapComPref(inputRecord, requestDTO);

        // Preference Airport
        mapPreference(inputRecord, requestDTO);

        item.setRequestWS(requestDTO);

        LOG.info("END mapping record for customer: " + inputRecord.getEmail());
        return item;
    }

    private void mapPreference(ProspectInputRecord inputRecord, CreateUpdateIndividualRequestDTO requestDTO) {
        if (StringUtils.isNotBlank(inputRecord.getDepartureAirport())) {
            PreferenceRequestDTO preferenceRequestDTO = new PreferenceRequestDTO();
            PreferenceDTO preferenceDTO = new PreferenceDTO();
            PreferenceDatasDTO preferenceDatasDTO = new PreferenceDatasDTO();
            PreferenceDataDTO preferenceDataDTO = new PreferenceDataDTO();

            preferenceDTO.setType(TRAVEL_PREF);
            preferenceDataDTO.setKey(PREFERRED_AIRPORT);
            preferenceDataDTO.setValue(inputRecord.getDepartureAirport());

            preferenceDatasDTO.setPreferenceDataDTO(Collections.singletonList(preferenceDataDTO));
            preferenceDTO.setPreferencesDatasDTO(preferenceDatasDTO);
            preferenceRequestDTO.setPreferenceDTO(Collections.singletonList(preferenceDTO));
            requestDTO.setPreferenceRequestDTO(preferenceRequestDTO);
        }
    }

    private void mapComPref(ProspectInputRecord inputRecord, CreateUpdateIndividualRequestDTO requestDTO) {
        MarketLanguageDTO marketLanguageDTO = new MarketLanguageDTO();
        CommunicationPreferencesDTO comPrefDTO = new CommunicationPreferencesDTO();
        CommunicationPreferencesRequestDTO communicationPreferencesRequestDTO = new CommunicationPreferencesRequestDTO();
        Date consentDate = new Date();
        String optin = YesNoFlagEnum.NO.toString();

        if (StringUtils.isNotBlank(inputRecord.getOptin())) {
            optin = inputRecord.getOptin();
        }

        // MARKET LANGUAGE
        if (StringUtils.isNotBlank(inputRecord.getCountryCode())) {
            marketLanguageDTO.setMarket(inputRecord.getCountryCode());
        }
        if (StringUtils.isNotBlank(inputRecord.getLanguageCode())) {
            marketLanguageDTO.setLanguage(inputRecord.getLanguageCode());
        }
        if (inputRecord.getDateOfConsent() != null) {
            consentDate = localDateToCalendar(inputRecord.getDateOfConsent()).getTime();
        }

        marketLanguageDTO.setDateOfConsent(consentDate);
        marketLanguageDTO.setOptIn(optin);

        // COMMUNICATION PREFERENCE
        comPrefDTO.setDateOfConsent(consentDate);
        comPrefDTO.setOptIn(YesNoFlagEnum.NO.toString());
        comPrefDTO.setDomain(SALES_DOMAIN);
        comPrefDTO.setCommunicationGroupeType(NEWSLETTER_TYPE);
        comPrefDTO.setCommunicationType(inputRecord.getSubscriptionType());
        comPrefDTO.setOptInPartner(YesNoFlagEnum.NO.toString());
        comPrefDTO.setDateOfConsentPartner(consentDate);

        if (StringUtils.isNotBlank(inputRecord.getOptin())) {
            comPrefDTO.setOptIn(inputRecord.getOptin());
        }
        if (StringUtils.isNotBlank(inputRecord.getSource())) {
            comPrefDTO.setSubscriptionChannel(inputRecord.getSource());
        }

        comPrefDTO.setMarketLanguageDTO(Collections.singletonList(marketLanguageDTO));
        communicationPreferencesRequestDTO.setCommunicationPreferencesDTO(comPrefDTO);
        requestDTO.setCommunicationPreferencesRequestDTO(Collections.singletonList(communicationPreferencesRequestDTO));
    }

    private void mapIndividualInformations(ProspectInputRecord inputRecord, CreateUpdateIndividualRequestDTO requestDTO) {
        boolean dataInserted = false;

        IndividualRequestDTO individualRequestDTO = new IndividualRequestDTO();
        IndividualInformationsDTO individualInformationsDTO = new IndividualInformationsDTO();
        IndividualProfilDTO individualProfilDTO = new IndividualProfilDTO();

        if (StringUtils.isNotBlank(inputRecord.getCivility())) {
            dataInserted = true;
            individualInformationsDTO.setCivility(inputRecord.getCivility());
        }
        if (inputRecord.getDateOfBirth() != null) {
            dataInserted = true;
            individualInformationsDTO.setBirthDate(localDateToCalendar(inputRecord.getDateOfBirth()).getTime());
        }
        if (StringUtils.isNotBlank(inputRecord.getFirstName())) {
            dataInserted = true;
            individualInformationsDTO.setFirstNameSC(inputRecord.getFirstName());
        }
        if (StringUtils.isNotBlank(inputRecord.getLastName())) {
            dataInserted = true;
            individualInformationsDTO.setLastNameSC(inputRecord.getLastName());
        }
        if (StringUtils.isNotBlank(inputRecord.getLanguageCode())) {
            dataInserted = true;
            individualProfilDTO.setLanguageCode(inputRecord.getLanguageCode());
        }

        if (dataInserted) {
            individualRequestDTO.setIndividualInformationsDTO(individualInformationsDTO);
            individualRequestDTO.setIndividualProfilDTO(individualProfilDTO);
            requestDTO.setIndividualRequestDTO(individualRequestDTO);
        }
    }

    private void mapEmail(ProspectInputRecord inputRecord, CreateUpdateIndividualRequestDTO requestDTO, AlimentationProspectItem item) {
        String email = "";

        if (StringUtils.isNotBlank(inputRecord.getEmail())) {
            email = SicStringUtils.normalizeEmail(inputRecord.getEmail());
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setEmail(email);

            EmailRequestDTO emailRequestDTO = new EmailRequestDTO();
            emailRequestDTO.setEmailDTO(emailDTO);
            requestDTO.setEmailRequestDTO(Collections.singletonList(emailRequestDTO));
        }

        item.setEmail(email);
    }

    private CreateUpdateIndividualRequestDTO initRequestDTO(ProspectInputRecord inputRecord) {

        CreateUpdateIndividualRequestDTO requestDTO = new CreateUpdateIndividualRequestDTO();
        requestDTO.setProcess(WS_PROSPECT_PROCESS);

        RequestorDTO requestor = new RequestorDTO();
        requestor.setChannel(B2C);

        requestor.setContext(CREATION_CONTEXT);
        requestor.setSignature(SIGNATURE_WS);
        requestor.setSite(SITE_WS);

        if (StringUtils.isNotBlank(inputRecord.getGin())) {
            requestor.setLoggedGin(inputRecord.getGin());
        }
        if (StringUtils.isNotBlank(inputRecord.getFbNumber())) {
            requestor.setReconciliationDataCIN(inputRecord.getFbNumber());
        }

        requestDTO.setRequestorDTO(requestor);
        return requestDTO;
    }
}
