package com.airfrance.batch.compref.injestadhocdata.helper;

import com.airfrance.batch.compref.injestadhocdata.bean.AdhocDataItem;
import com.airfrance.batch.compref.injestadhocdata.bean.InputRecord;
import com.airfrance.batch.compref.injestadhocdata.enums.SiteEnum;
import com.airfrance.batch.compref.injestadhocdata.property.InjestAdhocDataPropoerty;
import com.airfrance.batch.utils.SicUtils;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.type.CivilityEnum;
import com.airfrance.ref.type.YesNoFlagEnum;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.util.NormalizedStringUtilsV2;
import com.airfrance.repind.util.SicStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.airfrance.batch.compref.injestadhocdata.helper.Constant.*;
import static com.airfrance.batch.utils.IConstants.DATE_JSON_FORMATTER;

@Slf4j
public class FileParserHelper {

	public static List<String> getMissingFields(InputRecord input) {
		List<String> missingFields = new ArrayList<>();
		if (input != null) {
			Field[] fields = input.getClass().getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				try {
					if (f.getAnnotations().length > 0) {
						String value = (String) f.get(input);
						if (StringUtils.isBlank(value)) {
							missingFields.add(f.getName());
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException | SecurityException e) {
					log.error(e.getMessage());
				}
			}
		}
		return missingFields;
	}

	public static AdhocDataItem populateIndividual(InputRecord input, String isoLanguage,
												   InjestAdhocDataPropoerty property) {
		AdhocDataItem item = new AdhocDataItem();
		CreateUpdateIndividualRequestDTO individual = initIndividual(input, property);

		item.setGin(input.getGin());
		item.setIndividual(individual);
		String email = SicStringUtils.normalizeEmail(input.getEmailAddress());
		item.setEmail(email);
		item.setMarket(input.getCountryCode());
		item.setLanguage(input.getLanguageCode());
		setEmail(individual, email);
		setPersonalInformation(input, individual);
		setCommunicationPreferences(input, individual, isoLanguage);
		setPreferences(input, individual);
		return item;
	}

	private static void setPreferences(InputRecord input, CreateUpdateIndividualRequestDTO individual) {
		if (StringUtils.isNotBlank(input.getPreferredDepartureAirport())) {
			PreferenceRequestDTO preferenceRequestDTO = new PreferenceRequestDTO();
			PreferenceDTO preferenceDTO = new PreferenceDTO();
			PreferenceDatasDTO preferenceDatasDTO = new PreferenceDatasDTO();
			PreferenceDataDTO preferenceDataDTO = new PreferenceDataDTO();
			preferenceDatasDTO.setPreferenceDataDTO(Collections.singletonList(preferenceDataDTO));
			preferenceDTO.setPreferencesDatasDTO(preferenceDatasDTO);
			preferenceRequestDTO.setPreferenceDTO(Collections.singletonList(preferenceDTO));
			individual.setPreferenceRequestDTO(preferenceRequestDTO);

			preferenceDTO.setType(TRAVEL_PREFERENCE);
			if (input.getSubscriptionType().equals("AF")){
				preferenceDataDTO.setKey(PREFERRED_AIRPORT_AF);
			} else if (input.getSubscriptionType().equals("KL") || input.getSubscriptionType().equals("KL_PART")) {
				preferenceDataDTO.setKey(PREFERRED_AIRPORT_KL);
			}
			preferenceDataDTO.setValue(input.getPreferredDepartureAirport());

		}
	}

	private static void setCommunicationPreferences(InputRecord input, CreateUpdateIndividualRequestDTO individual,
													String isoLanguage) {
		MarketLanguageDTO ml = new MarketLanguageDTO();
		CommunicationPreferencesDTO comPref = new CommunicationPreferencesDTO();
		CommunicationPreferencesRequestDTO communicationPreferences = new CommunicationPreferencesRequestDTO();
		comPref.setMarketLanguageDTO(Collections.singletonList(ml));
		communicationPreferences.setCommunicationPreferencesDTO(comPref);
		individual.setCommunicationPreferencesRequestDTO(Collections.singletonList(communicationPreferences));

		ml.setOptIn(input.getStatus());
		ml.setMarket(input.getCountryCode());
		ml.setLanguage(isoLanguage);
		Date consentDate = SicUtils.encodeDate(DATE_JSON_FORMATTER, input.getDateOfConsent());
		ml.setDateOfConsent(consentDate);

		comPref.setDateOfConsent(consentDate);
		comPref.setOptIn(input.getStatus());
		comPref.setDomain(input.getDomain());
		comPref.setCommunicationGroupeType(input.getGroupType());
		comPref.setCommunicationType(input.getSubscriptionType());
		comPref.setOptInPartner(YesNoFlagEnum.NO.toString());
		comPref.setDateOfConsentPartner(consentDate);
	}

	private static void setEmail(CreateUpdateIndividualRequestDTO individual, String address) {
		EmailDTO email = new EmailDTO();
		email.setEmail(address);
		EmailRequestDTO emailRequest = new EmailRequestDTO();
		emailRequest.setEmailDTO(email);
		individual.setEmailRequestDTO(Collections.singletonList(emailRequest));
	}

	private static CreateUpdateIndividualRequestDTO initIndividual(InputRecord input,
																   InjestAdhocDataPropoerty property) {

		CreateUpdateIndividualRequestDTO requestDTO = new CreateUpdateIndividualRequestDTO();
		RequestorDTO requestor = new RequestorDTO();
		requestDTO.setRequestorDTO(requestor);

		requestor.setChannel(property.getChannel());
		requestor.setContext(property.getCreationContext());
		requestor.setSignature(input.getSource());
		String site = property.getSite().get(SiteEnum.valueOf(input.getSubscriptionType()));
		requestor.setSite(site);
		requestor.setReconciliationDataCIN(input.getCin());

		return requestDTO;
	}

	private static void setPersonalInformation(InputRecord input, CreateUpdateIndividualRequestDTO individual) {
		IndividualRequestDTO individualRequestDto = new IndividualRequestDTO();
		IndividualInformationsDTO individualInformationsDto = new IndividualInformationsDTO();
		IndividualProfilDTO individualProfilDto = new IndividualProfilDTO();
		individualRequestDto.setIndividualInformationsDTO(individualInformationsDto);
		individualRequestDto.setIndividualProfilDTO(individualProfilDto);
		individual.setIndividualRequestDTO(individualRequestDto);

		individualInformationsDto.setIdentifier(input.getGin());
		individualInformationsDto.setCivility(populateCivility(input.getCivility()));
		String birthdate = input.getBirthdate();
		individualInformationsDto
				.setBirthDate(StringUtils.isBlank(birthdate) ? null : SicUtils.encodeDate(DATE_JSON_FORMATTER, birthdate));
		individualInformationsDto.setFirstNameSC(normalizeString(input.getFirstname()));
		individualInformationsDto.setLastNameSC(normalizeString(input.getSurname()));
		individualProfilDto.setLanguageCode(input.getLanguageCode());
	}

	private static String populateCivility(String civility) {
		try {
			return CivilityEnum.getEnumMandatory(StringUtils.upperCase(civility)).toString();
		} catch (InvalidParameterException | MissingParameterException e) {
			return CivilityEnum.M_.toString();
		}
	}

	private static String normalizeString(String toNormalize) {
		return NormalizedStringUtilsV2.normalizeName(SicStringUtils.replaceAccentAndRemoveSpecialChars(toNormalize));
	}

	private FileParserHelper() {
		throw new IllegalStateException("Utility class");
	}
}
