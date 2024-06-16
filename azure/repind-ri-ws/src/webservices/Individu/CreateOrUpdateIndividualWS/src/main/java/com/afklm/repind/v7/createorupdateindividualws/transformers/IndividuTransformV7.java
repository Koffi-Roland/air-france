package com.afklm.repind.v7.createorupdateindividualws.transformers;

import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.request.ComunicationPreferencesRequest;
import com.afklm.soa.stubs.w000442.v7.request.PreferenceDataRequest;
import com.afklm.soa.stubs.w000442.v7.request.TelecomRequest;
import com.afklm.soa.stubs.w000442.v7.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.*;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.type.TerminalTypeEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.preference.PreferenceDataDTO;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class IndividuTransformV7 {

	public static String clientNumberToString(String clientNumber) {
		if (clientNumber != null){
			String gin = clientNumber;
			while(gin.length() < 12){
				gin='0'+gin;
			}
			return gin;
		}
		return null;
	}
	
	public static String clientNumberLongToString(Long clientNumber){
		if (clientNumber != null) {
			String gin = clientNumber.toString();
			while(gin.length() < 12){
				gin='0'+gin;
			}
			return gin;
		}
		return null;
	}

	public static ComunicationPreferencesRequest dtoProspectToDtoIndividuCommPrefs(CreateUpdateIndividualRequest request){
		ComunicationPreferencesRequest cpReq = new ComunicationPreferencesRequest();
		CommunicationPreferences cpOut = new CommunicationPreferences();

		if (request.getComunicationPreferencesRequest() != null && request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences() != null) {
			CommunicationPreferences cp = request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences();

			if (cp.getCommunicationGroupeType() != null) {
				cpOut.setCommunicationGroupeType(cp.getCommunicationGroupeType());
			}
			else {
				// in the previous version it was .setComType(null)... fixed
				cpOut.setCommunicationGroupeType(null);
			}
			if (cp.getCommunicationType() != null) {
				cpOut.setCommunicationType(cp.getCommunicationType());
			}
			else {
				cpOut.setCommunicationType(null);
			}
			if (cp.getDateOfConsent() != null) {
				cpOut.setDateOfConsent(cp.getDateOfConsent());
			}
			else {
				cpOut.setDateOfConsent(null);
			}
			if (cp.getDateOfConsentPartner() != null) {
				cpOut.setDateOfConsentPartner(cp.getDateOfConsentPartner());
			}
			else {
				cpOut.setDateOfConsentPartner(null);
			}
			if (cp.getDateOfEntry() != null) {
				cpOut.setDateOfEntry(cp.getDateOfEntry());
			}
			else {
				cpOut.setDateOfEntry(null);
			}
			if (cp.getDomain() != null) {
				cpOut.setDomain(cp.getDomain());
			}
			else {
				cpOut.setDomain(null);
			}
			if (cp.getOptIn() != null) {
				cpOut.setOptIn(cp.getOptIn());
			}
			else {
				cpOut.setOptIn(null);
			}
			if (cp.getOptInPartner() != null) {
				cpOut.setOptInPartner(cp.getOptInPartner());
			}
			else{
				cpOut.setOptInPartner(null);
			}
			if (cp.getSubscriptionChannel() != null) {
				cpOut.setSubscriptionChannel(cp.getSubscriptionChannel());
			}
			else {
				cpOut.setSubscriptionChannel(null);
			}
			if (cp.getMedia() != null) {
				Media media = new Media();

				if (cp.getMedia().getMedia1() != null) {
					media.setMedia1(cp.getMedia().getMedia1());
				}
				else {
					media.setMedia1(null);
				}
				if (cp.getMedia().getMedia2() != null) {
					media.setMedia2(cp.getMedia().getMedia2());
				}
				else {
					media.setMedia2(null);
				}
				if (cp.getMedia().getMedia3() != null) {
					media.setMedia3(cp.getMedia().getMedia3());
				}
				else {
					media.setMedia3(null);
				}
				if (cp.getMedia().getMedia4() != null) {
					media.setMedia4(cp.getMedia().getMedia4());
				}
				else {
					media.setMedia4(null);
				}
				if (cp.getMedia().getMedia5() != null) {
					media.setMedia5(cp.getMedia().getMedia5());
				}
				else {
					media.setMedia5(null);
				}

				cpOut.setMedia(media);
			}
			if (cp.getMarketLanguage() != null) {
				MarketLanguage ml = new MarketLanguage();
				if (cp.getMarketLanguage().get(0).getMarket() != null) {
					ml.setMarket(cp.getMarketLanguage().get(0).getMarket());
				}
				if (cp.getMarketLanguage().get(0).getLanguage() != null) {
					ml.setLanguage(cp.getMarketLanguage().get(0).getLanguage());
				}
				if (cp.getMarketLanguage().get(0).getOptIn() != null) {
					ml.setOptIn(cp.getMarketLanguage().get(0).getOptIn());
				}
				if (cp.getMarketLanguage().get(0).getDateOfConsent() != null) {
					ml.setDateOfConsent(cp.getMarketLanguage().get(0).getDateOfConsent());
				}
				if (cp.getMarketLanguage().get(0).getMedia() != null) {
					Media med = new Media();
					if (cp.getMarketLanguage().get(0).getMedia().getMedia1() != null) {
						med.setMedia1(cp.getMarketLanguage().get(0).getMedia().getMedia1());
					}
					if (cp.getMarketLanguage().get(0).getMedia().getMedia2() != null) {
						med.setMedia2(cp.getMarketLanguage().get(0).getMedia().getMedia2());
					}
					if (cp.getMarketLanguage().get(0).getMedia().getMedia3() != null) {
						med.setMedia3(cp.getMarketLanguage().get(0).getMedia().getMedia3());
					}
					if (cp.getMarketLanguage().get(0).getMedia().getMedia4() != null) {
						med.setMedia4(cp.getMarketLanguage().get(0).getMedia().getMedia4());
					}
					if (cp.getMarketLanguage().get(0).getMedia().getMedia5() != null) {
						med.setMedia5(cp.getMarketLanguage().get(0).getMedia().getMedia5());
					}
					ml.setMedia(med);
				}

				cpOut.getMarketLanguage().add(ml);
			}
			cpReq.setCommunicationPreferences(cpOut);
		}
		else {
			return null;
		}

		return cpReq;
	}

	public static TelecomRequest dtoProspectToDtoTelecom(CreateUpdateIndividualRequest request)
			throws InvalidParameterException, MissingParameterException {
		TelecomRequest telReq = null;

		if ((request.getTelecomRequest() != null || !request.getTelecomRequest().isEmpty()) && request.getTelecomRequest().get(0).getTelecom() != null ) {
			telReq = new TelecomRequest();
			Telecom tel = new Telecom();

			if (request.getTelecomRequest().get(0).getTelecom().getCountryCode() != null) {
				tel.setCountryCode(request.getTelecomRequest().get(0).getTelecom().getCountryCode());
			}
			else {
				tel.setCountryCode(null);
			}
			if(request.getTelecomRequest().get(0).getTelecom().getMediumStatus() != null) {
				tel.setMediumStatus(request.getTelecomRequest().get(0).getTelecom().getMediumStatus());
			}
			else {
				tel.setMediumStatus(null);
			}
			if(request.getTelecomRequest().get(0).getTelecom().getPhoneNumber() != null) {
				tel.setPhoneNumber(request.getTelecomRequest().get(0).getTelecom().getPhoneNumber());
			}
			else {
				tel.setPhoneNumber(null);
			}
			if (request.getTelecomRequest().get(0).getTelecom().getTerminalType() != null) {
				tel.setTerminalType(TerminalTypeEnum
						.getEnumMandatory(request.getTelecomRequest().get(0).getTelecom().getTerminalType())
						.toString());
			}
			else {
				tel.setTerminalType(null);
			}
			// in the requestV2 MediumCode is not present, but it is mandatory for the WS, so it's set to D
			if (request.getTelecomRequest().get(0).getTelecom().getMediumCode() != null) {
				tel.setMediumCode(request.getTelecomRequest().get(0).getTelecom().getMediumCode());
			}
			else {
				tel.setMediumCode("D");
			}

			telReq.setTelecom(tel);
		}
		return telReq;
	}

	/*
	 * TODO To confirm with Loha
	 */
	public static PreferenceDataRequest dtoProspectToDtoPreference(CreateUpdateIndividualRequest request) throws InvalidParameterException {
		PreferenceDataRequest prefReq = null;
		
		if (request.getPreferenceDataRequest() != null && request.getPreferenceDataRequest().getPreference() != null 
				&& request.getPreferenceDataRequest().getPreference().get(0) != null && request.getPreferenceDataRequest().getPreference().get(0).getPreferenceData() != null) {
			prefReq = new PreferenceDataRequest();
			Preference pref = new Preference();

			String preferredAirport = getProspectPrefferedAirportFromWs(request.getPreferenceDataRequest().getPreference());
			
			if(!StringUtils.isEmpty(preferredAirport)) {
				
				// REPIND-555 : Change preferences from prospect to individual
				pref.setTypePreference("TPC");
			
				PreferenceData prefDataPA = new PreferenceData();
				prefDataPA.setKey("preferredAirport");
				prefDataPA.setValue(preferredAirport);
				
				pref.getPreferenceData().add(prefDataPA);
				
			}
			
			prefReq.getPreference().add(pref);	
		}
		return prefReq;
	}
	
	public static IndividuDTO wsdlProspectToProspectDto(CreateUpdateIndividualRequest request) throws InvalidParameterException{
		String gin ="";
		IndividuDTO individuDto = new IndividuDTO();
		
		if(request.getIndividualRequest() != null && request.getIndividualRequest().getIndividualInformations() != null){
			IndividualInformationsV3 info = request.getIndividualRequest().getIndividualInformations();

			if (info.getIdentifier() != null) {
				gin = info.getIdentifier();
				individuDto.setSgin(gin);
			}

			if (info.getCivility() != null) {
				individuDto.setCivilite(StringUtils.upperCase(info.getCivility()));
			}
			if (info.getGender() != null) {
				individuDto.setSexe(info.getGender());
			}
			if (info.getLastNameSC() != null) {
				individuDto.setNom(info.getLastNameSC());
				individuDto.setNomSC(info.getLastNameSC());
			}
			if (info.getFirstNameSC() != null) {
				individuDto.setPrenom(info.getFirstNameSC());
				individuDto.setPrenomSC(info.getFirstNameSC());
			}
			if (info.getMiddleNameSC() != null) {
				individuDto.setSecondPrenom(info.getMiddleNameSC());
			}
			if (info.getBirthDate() != null) {
				individuDto.setDateNaissance(info.getBirthDate());
			}
			if (info.getNationality() != null) {
				individuDto.setNationalite(info.getNationality());
			}
			if (info.getSecondNationality() != null) {
				individuDto.setAutreNationalite(info.getSecondNationality());
			}
		}
		
		// Prospect data
		if ((request.getEmailRequest() != null && !request.getEmailRequest().isEmpty()) && request.getEmailRequest().get(0).getEmail() != null) {
			
			EmailDTO emailDTO = new EmailDTO();
			// REPIND-1288 : Store Email Address on Lower Case
			emailDTO.setEmail(SicStringUtils.normalizeEmail(request.getEmailRequest().get(0).getEmail().getEmail()));
			emailDTO.setSgin(gin);
			
			// Set value by default
			emailDTO.setAutorisationMailing("A");
			emailDTO.setCodeMedium("D");
			emailDTO.setStatutMedium("V");
			
			Set<EmailDTO> setEmailDTO = new HashSet<EmailDTO>();
			setEmailDTO.add(emailDTO);

			individuDto.setEmaildto(setEmailDTO);
			
		}

		if (request.getIndividualRequest() != null && request.getIndividualRequest().getIndividualProfil() != null) {
			if(request.getIndividualRequest().getIndividualProfil().getLanguageCode() != null) {
				individuDto.setCodeLangue(request.getIndividualRequest().getIndividualProfil().getLanguageCode());
			}
		}
		
		//Preferred airport
		// REPIND-555 : Change preferences for prospect
		if (request.getPreferenceDataRequest() != null) {
			if(request.getPreferenceDataRequest().getPreference() != null && !request.getPreferenceDataRequest().getPreference().isEmpty()) {
				List<PreferenceDTO> listPreferenceDTO = new ArrayList<PreferenceDTO>();
				
				// Preference for preferredAirport
				PreferenceDTO preferenceDTO = new PreferenceDTO();
				preferenceDTO.setType("TPC");
				
				Set<PreferenceDataDTO> setPreferenceDataDTO = new HashSet<PreferenceDataDTO>();
				
				// PreferenceData for preferredAirport
				PreferenceDataDTO preferredAirport = new PreferenceDataDTO();
				preferredAirport.setKey("preferredAirport");
				preferredAirport.setValue(getProspectPrefferedAirportFromWs(request.getPreferenceDataRequest().getPreference()));
				preferredAirport.setPreferenceDTO(preferenceDTO);
				
				// PreferenceData for type : We do not need anymore to identify origin of the preference
				// PreferenceDataDTO typePreference = new PreferenceDataDTO();
				// typePreference.setKey("type");
				// typePreference.setValue("WWP");
				// typePreference.setPreferenceDTO(preferenceDTO);
				
				setPreferenceDataDTO.add(preferredAirport);
				// setPreferenceDataDTO.add(typePreference);
				
				preferenceDTO.setPreferenceDataDTO(setPreferenceDataDTO);
				
				if (!"".equals(gin)){
					preferenceDTO.setGin(gin);
				}
				
				listPreferenceDTO.add(preferenceDTO);
				
				individuDto.setPreferenceDTO(listPreferenceDTO);
			}
		}
		
		PostalAddressDTO postalAddressDTO = null;
		
		if (request.getPostalAddressRequest() != null && !request.getPostalAddressRequest().isEmpty()) {
			
			postalAddressDTO = new PostalAddressDTO();
			
			if (!"".equals(gin)){
				postalAddressDTO.setSgin(gin);
			}
			if (request.getPostalAddressRequest().get(0) != null) {
				PostalAddressContent addressContent = request.getPostalAddressRequest().get(0).getPostalAddressContent();
				PostalAddressProperties addressProp = request.getPostalAddressRequest().get(0).getPostalAddressProperties();
				boolean postalAddressEmpty = true;

				if (addressContent != null) {
					if (addressContent.getNumberAndStreet() != null && !addressContent.getNumberAndStreet().equalsIgnoreCase("")) {
						postalAddressDTO.setSno_et_rue(addressContent.getNumberAndStreet());	
						postalAddressEmpty = false;
					}
					if (addressContent.getZipCode() != null && !addressContent.getZipCode().equalsIgnoreCase("")) {
						postalAddressDTO.setScode_postal(addressContent.getZipCode());	
						postalAddressEmpty = false;
					}
					if (addressContent.getCity() != null && !addressContent.getCity().equalsIgnoreCase("")) {
						postalAddressDTO.setSville(addressContent.getCity());	
						postalAddressEmpty = false;
					}
					if(addressContent.getStateCode() != null && !addressContent.getStateCode().equalsIgnoreCase("")) {
						postalAddressDTO.setScode_province(addressContent.getStateCode());	
						postalAddressEmpty = false;
					}	
					if (addressContent.getCountryCode() != null && !addressContent.getCountryCode().equalsIgnoreCase("")) {
						postalAddressDTO.setScode_pays(addressContent.getCountryCode());	
						postalAddressEmpty = false;
					}
				}
				if (addressProp != null) {
					if (addressProp.getMediumStatus() != null && !addressProp.getMediumStatus().equalsIgnoreCase("")) {
						postalAddressDTO.setSstatut_medium(addressProp.getMediumStatus());
					}
					else {
						postalAddressDTO.setSstatut_medium("V");
					}
					if (addressProp.getMediumCode() != null && !addressProp.getMediumCode().equalsIgnoreCase("")) {
						postalAddressDTO.setScode_medium(addressProp.getMediumCode());
					}
					else {
						postalAddressDTO.setScode_medium("D");
					}
				} else {
					postalAddressDTO.setSstatut_medium("V");
					postalAddressDTO.setScode_medium("D");
				}
				postalAddressDTO.setIcod_err(0);
				postalAddressDTO.setSforcage("O");
				
				if(postalAddressEmpty == true) {
					postalAddressDTO = null;
				}
			}
		}
		
		List<PostalAddressDTO> listAdrPostaleDTO = new ArrayList<PostalAddressDTO>();
		if(postalAddressDTO != null) {
			listAdrPostaleDTO.add(postalAddressDTO);
		}
		
		if(listAdrPostaleDTO.size() != 0) {
			individuDto.setPostaladdressdto(listAdrPostaleDTO);
		}

		//CommunicationPréférences
		if(request.getComunicationPreferencesRequest() != null && !request.getComunicationPreferencesRequest().isEmpty()){
			List<CommunicationPreferencesDTO> comPrefsDto = new ArrayList<CommunicationPreferencesDTO>();
			
			CommunicationPreferences comPref = request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences();
			
			CommunicationPreferencesDTO comPrefDto = new CommunicationPreferencesDTO();

			if (!"".equals(gin)) {
					comPrefDto.setGin(gin);
			}
			if (comPref.getDomain() != null) {
				comPrefDto.setDomain(comPref.getDomain());
			}
			if (comPref.getCommunicationGroupeType() != null) {
					comPrefDto.setComGroupType(comPref.getCommunicationGroupeType());
			}
			if (comPref.getCommunicationType() != null) {
					comPrefDto.setComType(comPref.getCommunicationType());
			}
			if (comPref.getOptIn() != null) {
					comPrefDto.setSubscribe(comPref.getOptIn());
			}
			if (comPref.getDateOfConsent() != null) {
					comPrefDto.setDateOptin(comPref.getDateOfConsent());
			}
			if (comPref.getSubscriptionChannel() != null) {
					comPrefDto.setChannel(comPref.getSubscriptionChannel());
			}
			if (comPref.getOptInPartner() != null) {
				comPrefDto.setOptinPartners(comPref.getOptInPartner());
			}
			if (comPref.getDateOfConsentPartner() != null) {
					comPrefDto.setDateOptinPartners(comPref.getDateOfConsentPartner());
			}
			if (comPref.getDateOfEntry() != null) {
				comPrefDto.setDateOfEntry(comPref.getDateOfEntry());
			}
					
			if(comPref.getMedia()!=null){
				if (comPref.getMedia().getMedia1() != null) {
						comPrefDto.setMedia1(comPref.getMedia().getMedia1());
				}
				if (comPref.getMedia().getMedia2() != null) {
						comPrefDto.setMedia2(comPref.getMedia().getMedia2());
				}
				if (comPref.getMedia().getMedia3() != null) {
						comPrefDto.setMedia3(comPref.getMedia().getMedia3());
				}
				if (comPref.getMedia().getMedia4() != null) {
						comPrefDto.setMedia4(comPref.getMedia().getMedia4());
				}
				if (comPref.getMedia().getMedia5() != null) {
						comPrefDto.setMedia5(comPref.getMedia().getMedia5());
				}
			}
				
			//market language data:
			if (comPref.getMarketLanguage() != null && !comPref.getMarketLanguage().isEmpty()) {
				
				Set<MarketLanguageDTO> mlsDto = new HashSet<MarketLanguageDTO>();
				
				for (MarketLanguage ml : comPref.getMarketLanguage()) {
				
					MarketLanguageDTO mlDto = new MarketLanguageDTO();
					if (ml.getMarket() != null) {
						mlDto.setMarket(ml.getMarket());
					}
					if (ml.getLanguage() != null) {
						mlDto.setLanguage(ml.getLanguage().value());
					}
					if (ml.getOptIn() != null) {
						mlDto.setOptIn(ml.getOptIn());
					}
					if (ml.getDateOfConsent() != null) {
						mlDto.setDateOfConsent(ml.getDateOfConsent());
					}
					else if (comPref.getDateOfConsent() != null) {
						mlDto.setDateOfConsent(comPref.getDateOfConsent());
					}
						
					if(ml.getMedia()!=null){
						if (ml.getMedia().getMedia1() != null) {
								mlDto.setMedia1(ml.getMedia().getMedia1());
						}
						if (ml.getMedia().getMedia2() != null) {
								mlDto.setMedia2(ml.getMedia().getMedia2());
						}
						if (ml.getMedia().getMedia3() != null) {
								mlDto.setMedia3(ml.getMedia().getMedia3());
						}
						if (ml.getMedia().getMedia4() != null) {
								mlDto.setMedia4(ml.getMedia().getMedia4());
						}
						if (ml.getMedia().getMedia5() != null) {
								mlDto.setMedia5(ml.getMedia().getMedia5());
						}
					}
					mlsDto.add(mlDto);
					
					if (mlsDto != null && !mlsDto.isEmpty()) {
						comPrefDto.setMarketLanguageDTO(mlsDto);
					}
				}
				comPrefsDto.add(comPrefDto);
			}
			individuDto.setCommunicationpreferencesdto(comPrefsDto);
		}
		
		return individuDto;
	}

	private static String getProspectPrefferedAirportFromWs(List<Preference> prefFromWs) throws InvalidParameterException {
		if (prefFromWs.size() > 1) {
			throw new InvalidParameterException("Only 1 preference allowed for prospect");
		}

		if (!"WW".equalsIgnoreCase(prefFromWs.get(0).getTypePreference())) {
			throw new InvalidParameterException("Not a prospect preference type");
		}

		String preferredAirport = "";
		for (PreferenceData prefData : prefFromWs.get(0).getPreferenceData()) {
			if ("PA".equalsIgnoreCase(prefData.getKey())) {
				preferredAirport = prefData.getValue();
				if (preferredAirport != null && preferredAirport.length() > 3) {
					throw new InvalidParameterException("Preferred airport max length is 3 characters");
				}
				// REPIND-1003 : Repair blocker
				if(preferredAirport != null) {
					preferredAirport = preferredAirport.toUpperCase();
				}
			}
			else {
				throw new InvalidParameterException("Only a preferred airport is allowed for prospect preferences");
			}
		}
		return preferredAirport;
	}
	
	/**
	 * Cette fonction permet d'affecter les signatures à toutes les entity d'un prospect dans le cadre d'une création de prospect
	 * La signature comprends:
	 * -creationSignature
	 * -creationSite
	 * -creationDate
	 * dans chacune des tables :
	 * -prospect
	 * -prospectlocalization
	 * -prospecttelecoms
	 * -communicationpreferences
	 * -marketlanguage
	 * @param prospect
	 * @param requestor
	 * @return
	 */
	// REPIND-555: Prospect migration
	public static IndividuDTO wsdlSignatureToDtoCreation(IndividuDTO prospect,RequestorV2 requestor){
		//affectation de la signature de création
		if(requestor!=null){
			if(requestor.getSignature()!=null && !"".equals(requestor.getSignature())){
				prospect.setSignatureCreation(requestor.getSignature());
				if(prospect.getPostaladdressdto()!=null) {
					if(prospect.getPostaladdressdto().get(0)!=null) {
						prospect.getPostaladdressdto().get(0).setSignature_creation(requestor.getSignature());
					}
				}
				if(prospect.getTelecoms()!=null) {
					if(prospect.getTelecoms().size() > 0) {
						prospect.getTelecoms().iterator().next().setSsignature_creation(requestor.getSignature());
					}
				}
				if(prospect.getCommunicationpreferencesdto()!=null && !prospect.getCommunicationpreferencesdto().isEmpty()){
					for(CommunicationPreferencesDTO comPrefDto : prospect.getCommunicationpreferencesdto()){
						comPrefDto.setCreationSignature(requestor.getSignature());
						if(comPrefDto.getMarketLanguageDTO()!=null && !comPrefDto.getMarketLanguageDTO().isEmpty()){
							for(MarketLanguageDTO mlDto: comPrefDto.getMarketLanguageDTO()) {
								mlDto.setCreationSignature(requestor.getSignature());
							}
						}
					}
				}		
				if(prospect.getEmaildto()!=null && !prospect.getEmaildto().isEmpty()){
					prospect.getEmaildto().iterator().next().setSignatureCreation(requestor.getSignature());
				}
				if(prospect.getPreferenceDTO()!=null && !prospect.getPreferenceDTO().isEmpty()){
					for(PreferenceDTO prefDto : prospect.getPreferenceDTO()){
						prefDto.setSignatureCreation(requestor.getSignature());
						
						// To valid with Loha and Maxime but no signature for PreferenceData for now
//						if(prefDto.getPreferenceDataDTO()!=null && !prefDto.getPreferenceDataDTO().isEmpty()){
//							for(PreferenceDataDTO prefDataDto: prefDto.getPreferenceDataDTO()) {
//								prefDataDto.setSignatureCreation(requestor.getSignature());
//							}
//						}
					}
				}
			}
			//affectation du site de création
			if(requestor.getSite()!=null && !"".equals(requestor.getSite())){
				prospect.setSiteCreation(requestor.getSite());
				if(prospect.getPostaladdressdto()!=null) {
					if(prospect.getPostaladdressdto().get(0)!=null) {
						prospect.getPostaladdressdto().get(0).setSsite_creation(requestor.getSite());
					}
				}
				if(prospect.getTelecoms()!=null) {
					if(prospect.getTelecoms().iterator().next()!=null) {
						prospect.getTelecoms().iterator().next().setSsite_creation(requestor.getSite());
					}
				}
				if(prospect.getCommunicationpreferencesdto()!=null && !prospect.getCommunicationpreferencesdto().isEmpty()){
					for(CommunicationPreferencesDTO comPrefDto : prospect.getCommunicationpreferencesdto()){
						comPrefDto.setCreationSite(requestor.getSite());
						if(comPrefDto.getMarketLanguageDTO()!=null && !comPrefDto.getMarketLanguageDTO().isEmpty()){
							for(MarketLanguageDTO mlDto: comPrefDto.getMarketLanguageDTO()) {
								mlDto.setCreationSite(requestor.getSite());
							}
						}
					}
				}	
				if(prospect.getEmaildto()!=null && !prospect.getEmaildto().isEmpty()){
					prospect.getEmaildto().iterator().next().setSiteCreation(requestor.getSite());
				}
				if(prospect.getPreferenceDTO()!=null && !prospect.getPreferenceDTO().isEmpty()){
					for(PreferenceDTO prefDto : prospect.getPreferenceDTO()){
						prefDto.setSiteCreation(requestor.getSite());
						// To valid with Loha and Maxime but no signature for PreferenceData for now
//						if(prefDto.getPreferenceDataDTO()!=null && !prefDto.getPreferenceDataDTO().isEmpty()){
//							for(PreferenceDataDTO prefDataDto: prefDto.getPreferenceDataDTO()) {
//								prefDataDto.setSiteCreation(requestor.getSite());
//							}
//						}
					}
				}
			}
			//affectation de la date de creation
			prospect.setDateCreation(new Date());
			if(prospect.getPostaladdressdto()!=null) {
				if(prospect.getPostaladdressdto().get(0)!=null) {
					prospect.getPostaladdressdto().get(0).setDdate_creation(new Date());
				}
			}
			if(prospect.getTelecoms()!=null) {
				if(prospect.getTelecoms().iterator().next()!=null) {
					prospect.getTelecoms().iterator().next().setDdate_creation(new Date());
				}
			}
			if(prospect.getCommunicationpreferencesdto()!=null && !prospect.getCommunicationpreferencesdto().isEmpty()){
				for(CommunicationPreferencesDTO comPrefDto : prospect.getCommunicationpreferencesdto()){
					comPrefDto.setCreationDate(new Date());
					if(comPrefDto.getMarketLanguageDTO()!=null && !comPrefDto.getMarketLanguageDTO().isEmpty()){
						for(MarketLanguageDTO mlDto: comPrefDto.getMarketLanguageDTO()) {
							mlDto.setCreationDate(new Date());
						}
					}
				}
			}
			if(prospect.getEmaildto()!=null && !prospect.getEmaildto().isEmpty()){
				prospect.getEmaildto().iterator().next().setDateCreation(new Date());
			}
			if(prospect.getPreferenceDTO()!=null && !prospect.getPreferenceDTO().isEmpty()){
				for(PreferenceDTO prefDto : prospect.getPreferenceDTO()){
					prefDto.setDateCreation(new Date());
					// To valid with Loha and Maxime but no signature for PreferenceData for now
//					if(prefDto.getPreferenceDataDTO()!=null && !prefDto.getPreferenceDataDTO().isEmpty()){
//						for(PreferenceDataDTO prefDataDto: prefDto.getPreferenceDataDTO()) {
//							prefDataDto.setDateCreation(new Date());
//						}
//					}
				}
			}
		}

		return prospect;
	}

	/**
	 * Cette fonction permet d'affecter les signatures à toutes les entity d'un prospect dans le cadre d'une modification de prospect
	 * La signature comprends:
	 * -modificationSignature
	 * -modificationSite
	 * -modificationDate
	 * dans chacune des tables :
	 * -prospect
	 * -prospectlocalization
	 * -prospecttelecoms
	 * -communicationpreferences
	 * -marketlanguage
	 * @param prospect
	 * @param requestor
	 * @return
	 */
	// REPIND-555: Prospect migration
	public static IndividuDTO wsdlSignatureToDtoUpdate(IndividuDTO prospect,RequestorV2 requestor){
		//affectation de la signature de modification
		if(requestor!=null){
			if(requestor.getSignature()!=null && !"".equals(requestor.getSignature())){
				prospect.setSignatureModification(requestor.getSignature());
				if(prospect.getPostaladdressdto()!=null) {
					if(prospect.getPostaladdressdto().get(0)!=null) {
						prospect.getPostaladdressdto().get(0).setSsignature_modification(requestor.getSignature());
					}
				}
				if(prospect.getTelecoms()!=null) {
					if(prospect.getTelecoms().iterator().next()!=null){
						prospect.getTelecoms().iterator().next().setSsignature_modification(requestor.getSignature());
					}
				}
				if(prospect.getCommunicationpreferencesdto()!=null && !prospect.getCommunicationpreferencesdto().isEmpty()){
					for(CommunicationPreferencesDTO comPrefDto : prospect.getCommunicationpreferencesdto()){
						comPrefDto.setModificationSignature(requestor.getSignature());
						if(comPrefDto.getMarketLanguageDTO()!=null && !comPrefDto.getMarketLanguageDTO().isEmpty()){
							for(MarketLanguageDTO mlDto: comPrefDto.getMarketLanguageDTO()) {
								mlDto.setModificationSignature(requestor.getSignature());
							}
						}
					}
				}	
				if(prospect.getEmaildto()!=null && !prospect.getEmaildto().isEmpty()){
					prospect.getEmaildto().iterator().next().setSignatureModification(requestor.getSignature());
				}
				if(prospect.getPreferenceDTO()!=null && !prospect.getPreferenceDTO().isEmpty()){
					for(PreferenceDTO prefDto : prospect.getPreferenceDTO()){
						prefDto.setSignatureModification(requestor.getSignature());
						// To valid with Loha and Maxime but no signature for PreferenceData for now
//						if(prefDto.getPreferenceDataDTO()!=null && !prefDto.getPreferenceDataDTO().isEmpty()){
//							for(PreferenceDataDTO prefDataDto: prefDto.getPreferenceDataDTO()) {
//								prefDataDto.setSignatureModification(requestor.getSignature());
//							}
//						}
					}
				}
			}
			//affectation du site de modification
			if(requestor.getSite()!=null && !"".equals(requestor.getSite())){
				prospect.setSiteModification(requestor.getSite());
				if(prospect.getPostaladdressdto()!=null) {
					if(prospect.getPostaladdressdto().get(0)!=null) {
						prospect.getPostaladdressdto().get(0).setSsite_modification(requestor.getSite());
					}
				}
				if(prospect.getTelecoms()!=null) {
					if(prospect.getTelecoms().iterator().next()!=null) {
						prospect.getTelecoms().iterator().next().setSsite_modification(requestor.getSite());
					}
				}
				if(prospect.getCommunicationpreferencesdto()!=null && !prospect.getCommunicationpreferencesdto().isEmpty()){
					for(CommunicationPreferencesDTO comPrefDto : prospect.getCommunicationpreferencesdto()){
						comPrefDto.setModificationSite(requestor.getSite());
						if(comPrefDto.getMarketLanguageDTO()!=null && !comPrefDto.getMarketLanguageDTO().isEmpty()){
							for(MarketLanguageDTO mlDto: comPrefDto.getMarketLanguageDTO()) {
								mlDto.setModificationSite(requestor.getSite());
							}
						}
					}
				}	
				if(prospect.getEmaildto()!=null && !prospect.getEmaildto().isEmpty()){
					prospect.getEmaildto().iterator().next().setSiteModification(requestor.getSite());
				}
				if(prospect.getPreferenceDTO()!=null && !prospect.getPreferenceDTO().isEmpty()){
					for(PreferenceDTO prefDto : prospect.getPreferenceDTO()){
						prefDto.setSiteModification(requestor.getSite());
						// To valid with Loha and Maxime but no signature for PreferenceData for now
//						if(prefDto.getPreferenceDataDTO()!=null && !prefDto.getPreferenceDataDTO().isEmpty()){
//							for(PreferenceDataDTO prefDataDto: prefDto.getPreferenceDataDTO()) {
//								prefDataDto.setSiteModification(requestor.getSite());
//							}
//						}
					}
				}
			}
			//affectation de la date de creation
			prospect.setDateModification(new Date());
			if(prospect.getPostaladdressdto()!=null) {
				if(prospect.getPostaladdressdto().get(0)!=null) {
					prospect.getPostaladdressdto().get(0).setDdate_modification(new Date());
				}
			}
			if(prospect.getTelecoms()!=null) {
				if(prospect.getTelecoms().iterator().next()!=null) {
					prospect.getTelecoms().iterator().next().setDdate_modification(new Date());
				}
			}
			if(prospect.getCommunicationpreferencesdto()!=null && !prospect.getCommunicationpreferencesdto().isEmpty()){
				for(CommunicationPreferencesDTO comPrefDto : prospect.getCommunicationpreferencesdto()){
					comPrefDto.setModificationDate(new Date());
					if(comPrefDto.getMarketLanguageDTO()!=null && !comPrefDto.getMarketLanguageDTO().isEmpty()){
						for(MarketLanguageDTO mlDto: comPrefDto.getMarketLanguageDTO()) {
							mlDto.setModificationDate(new Date());
						}
					}
				}
			}
			if(prospect.getEmaildto()!=null && !prospect.getEmaildto().isEmpty()){
				prospect.getEmaildto().iterator().next().setDateModification(new Date());
			}
			if(prospect.getPreferenceDTO()!=null && !prospect.getPreferenceDTO().isEmpty()){
				for(PreferenceDTO prefDto : prospect.getPreferenceDTO()){
					prefDto.setDateModification(new Date());
					// To valid with Loha and Maxime but no signature for PreferenceData for now
//					if(prefDto.getPreferenceDataDTO()!=null && !prefDto.getPreferenceDataDTO().isEmpty()){
//						for(PreferenceDataDTO prefDataDto: prefDto.getPreferenceDataDTO()) {
//							prefDataDto.setDateModification(new Date());
//						}
//					}
				}
			}
		}

		return prospect;
	}

	// REPIND-555: Prospect migration
	public static IndividuDTO wsdlStatusToDtoCreation(IndividuDTO prospect){
		if((prospect.getStatutIndividu())==null){
			prospect.setStatutIndividu("V");
		}
		if(prospect.getPostaladdressdto()!=null){
			if(prospect.getPostaladdressdto().get(0)!=null) {
				if(prospect.getPostaladdressdto().get(0).getScode_medium()==null)
					prospect.getPostaladdressdto().get(0).setScode_medium("D");
				if(prospect.getPostaladdressdto().get(0).getSstatut_medium()==null)
					prospect.getPostaladdressdto().get(0).setSstatut_medium("V");
			}
		}
		if(prospect.getTelecoms()!=null){
			if(prospect.getTelecoms().iterator().next().getScode_medium()==null)
				prospect.getTelecoms().iterator().next().setScode_medium("D");
			if(prospect.getTelecoms().iterator().next().getSstatut_medium()==null)
				prospect.getTelecoms().iterator().next().setSstatut_medium("V");
			if(prospect.getTelecoms().iterator().next().getSterminal()==null)
				prospect.getTelecoms().iterator().next().setSterminal("T");
		}
		return prospect;
	}

	public static List<TelecomsDTO> transformToProspectTelecomsDTO(List<TelecomRequest> telecomRequest) throws InvalidParameterException, MissingParameterException {
		List<TelecomsDTO> dtos = new ArrayList<TelecomsDTO>();
		if (telecomRequest == null) {
			return null;
		}

		if (telecomRequest.size() > 1) {
			throw new InvalidParameterException("Only 1 telcom allowed for prospect");
		}

		Telecom tel = telecomRequest.get(0).getTelecom();

		if (tel == null) {
			return null;
		}
		else {
			TelecomsDTO dto = new TelecomsDTO();
			dto.setCountryCode(tel.getCountryCode());
			dto.setScode_region(tel.getCountryCode());
			dto.setSindicatif(tel.getCountryCode());
			dto.setSnumero(tel.getPhoneNumber());
			dto.setSterminal(TerminalTypeEnum.getEnumMandatory(tel.getTerminalType()).toString());
			dto.setSstatut_medium(tel.getMediumStatus());
			dtos.add(dto);
		}
		return dtos;
	}
}
