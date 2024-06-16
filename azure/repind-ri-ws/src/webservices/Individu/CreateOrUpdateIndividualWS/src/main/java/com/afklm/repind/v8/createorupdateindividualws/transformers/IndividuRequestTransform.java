package com.afklm.repind.v8.createorupdateindividualws.transformers;

import com.afklm.soa.stubs.w000442.v8.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v8.request.Delegate;
import com.afklm.soa.stubs.w000442.v8.request.Delegator;
import com.afklm.soa.stubs.w000442.v8.request.*;
import com.afklm.soa.stubs.w000442.v8.siccommontype.RequestorV2;
import com.afklm.soa.stubs.w000442.v8.siccommontype.Signature;
import com.afklm.soa.stubs.w000442.v8.sicindividutype.*;
import com.airfrance.repind.dto.individu.AlertDTO;
import com.airfrance.repind.dto.individu.AlertDataDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.util.SicStringUtils;
import com.airfrance.repindutf8.util.SicUtf8StringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IndividuRequestTransform {

	public static List<AlertDTO> transformToAlertDTO(AlertRequest ws) {
		
		if (ws==null) {
			return null;
		}
		List<AlertDTO> dtoList = new ArrayList<AlertDTO>();
		
		for(Alert al : ws.getAlert()) {
			dtoList.add(transformToAlertDTO(al));
		}
		
		return dtoList;
	}
	

	public static AlertDTO transformToAlertDTO(Alert ws) {
		
		if (ws==null) {
			return null;
		}

		AlertDTO dto = new AlertDTO();
		if(ws.getAlertId() != null){
			dto.setAlertId(Integer.parseInt(ws.getAlertId()));
		}
		dto.setType(ws.getType());
		dto.setOptIn(ws.getOptIn());
		dto.setAlertDataDTO(transformToAlertDataDTO(ws.getAlertData()));
		return dto;
	}
	
	public static Set<AlertDataDTO> transformToAlertDataDTO(List<AlertData> wsList) {
		
		if(wsList==null || wsList.isEmpty()) {
			return null;
		}
		
		Set<AlertDataDTO> dtoList = new HashSet<AlertDataDTO>();
		
		for(AlertData ws : wsList) {
			dtoList.add(transformToAlertDataDTO(ws));
		}
		
		return dtoList;
		
	}
	
	public static AlertDataDTO transformToAlertDataDTO(AlertData ws) {
		
		if (ws==null) {
			return null;
		}

		AlertDataDTO dto = new AlertDataDTO();
		dto.setKey(ws.getKey());
		dto.setValue(ws.getValue());
		
		return dto;
	}
	
	public static CreateUpdateIndividualRequestDTO transformRequestWSToDTO(CreateUpdateIndividualRequest request) {

		if(request==null) {
			return null;
		}
		
		CreateUpdateIndividualRequestDTO dto = new CreateUpdateIndividualRequestDTO();
		dto.setAccountDelegationDataRequestDTO(transformAccountDelegDataRequestWSToDTO(request.getAccountDelegationDataRequest()));
		dto.setAlertRequestDTO(transformAlertRequestWSToDTO(request.getAlertRequest()));
		dto.setCommunicationPreferencesRequestDTO(transformCommPrefRequestWSToDTO(request.getComunicationPreferencesRequest()));
		dto.setEmailRequestDTO(transformEmailRequestWSToDTO(request.getEmailRequest()));
		dto.setExternalIdentifierRequestDTO(transformExternalIdentifierRequestWSToDTO(request.getExternalIdentifierRequest()));
		dto.setIndividualRequestDTO(transformIndividualRequestWSToDTO(request.getIndividualRequest()));
		dto.setNewsletterMediaSending(request.getNewsletterMediaSending());
		dto.setPostalAddressRequestDTO(transformPostalAddressRequestWSToDTO(request.getPostalAddressRequest()));
		dto.setPreferenceRequestDTO(transformPreferenceRequestWSToDTO(request.getPreferenceRequest()));
		dto.setPrefilledNumbersRequestDTO(transformPrefilledNumbersRequestWSToDTO(request.getPrefilledNumbersRequest()));
		dto.setProcess(request.getProcess());
		dto.setRequestorDTO(transformRequestorRequestWSToDTO(request.getRequestor()));
		dto.setStatus(request.getStatus());
		dto.setTelecomRequestDTO(transformTelecomRequestWSToDTO(request.getTelecomRequest()));
		dto.setUpdateCommunicationPrefMode(request.getUpdateCommunicationPrefMode());
		dto.setUpdatePrefilledNumbersMode(request.getUpdatePrefilledNumbersMode());
		dto.setUtfRequestDTO(transformUtfRequestWSToDTO(request.getUtfRequest()));
		
		return dto;
	}
	
	/*
	 * UTF DATA
	 * 
	 */
	public static UtfRequestDTO transformUtfRequestWSToDTO(UtfRequest request) {

		if(request==null) {
			return null;
		}
		
		UtfRequestDTO dto = new UtfRequestDTO();
		dto.setUtfDTO(transformUtfWSToDTO(request.getUtf()));
		
		return dto;
	}
	
	public static List<UtfDTO> transformUtfWSToDTO(List<Utf> request) {

		if(request==null) {
			return null;
		}
		
		List<UtfDTO> dto = new ArrayList<UtfDTO>();
		
		for (Utf utfRequest : request) {
			UtfDTO utfDTO = new UtfDTO();
			if(utfRequest.getId() != null) {
				utfDTO.setId(Long.parseLong(utfRequest.getId()));
			}
			utfDTO.setType(utfRequest.getType());
			utfDTO.setUtfDatasDTO(transformUtfDatasWSToDTO(utfRequest.getUtfDatas()));
			dto.add(utfDTO);
		}
		
		return dto;
	}
	
	public static UtfDatasDTO transformUtfDatasWSToDTO(UtfDatas request) {

		if(request==null) {
			return null;
		}
		
		UtfDatasDTO dto = new UtfDatasDTO();
		dto.setUtfDataDTO(transformUtfDataWSToDTO(request.getUtfData()));
		
		return dto;
	}
	
	public static List<utfDataDTO> transformUtfDataWSToDTO(List<UtfData> request) {

		if(request==null) {
			return null;
		}

		List<utfDataDTO> dto = new ArrayList<utfDataDTO>();
		
		for (UtfData utfData : request) {
			utfDataDTO utfDataDTO = new utfDataDTO();
			utfDataDTO.setKey(utfData.getKey());
			utfDataDTO.setValue(utfData.getValue());
			dto.add(utfDataDTO);
		}
		
		return dto;
	}
	
	/*
	 * TELECOM
	 * 
	 */
	public static List<TelecomRequestDTO> transformTelecomRequestWSToDTO(List<TelecomRequest> request) {

		if(request==null) {
			return null;
		}
		
		List<TelecomRequestDTO> dto = new ArrayList<TelecomRequestDTO>();
		for (TelecomRequest telecomRequest : request) {
			TelecomRequestDTO telDTO = new TelecomRequestDTO();
			telDTO.setTelecomDTO(transformTelecomWSToDTO(telecomRequest.getTelecom()));
			dto.add(telDTO);
		}
		
		return dto;
	}
	
	public static TelecomDTO transformTelecomWSToDTO(Telecom request) {

		if(request==null) {
			return null;
		}
		TelecomDTO dto = new TelecomDTO();
		dto.setCountryCode(request.getCountryCode());
		dto.setMediumCode(request.getMediumCode());
		dto.setMediumStatus(request.getMediumStatus());
		dto.setPhoneNumber(request.getPhoneNumber());
		dto.setTerminalType(request.getTerminalType());
		dto.setVersion(request.getVersion());
		
		return dto;
	}

	/*
	 * REQUESTOR
	 * 
	 */
	public static RequestorDTO transformRequestorRequestWSToDTO(RequestorV2 request) {

		if(request==null) {
			return null;
		}
		
		RequestorDTO dto = new RequestorDTO();
		dto.setApplicationCode(request.getApplicationCode());
		dto.setChannel(request.getChannel());
		dto.setContext(request.getContext());
		dto.setIpAddress(request.getIpAddress());
		dto.setLoggedGin(request.getLoggedGin());
		dto.setManagingCompany(request.getManagingCompany());
		dto.setMatricule(request.getMatricule());
		dto.setOfficeId(request.getOfficeId());
		dto.setReconciliationDataCIN(request.getReconciliationDataCIN());
		dto.setSignature(request.getSignature());
		dto.setSite(request.getSite());
		dto.setToken(request.getToken());
		
		return dto;
	}

	/*
	 * PREFILLED NUMBERS
	 * 
	 */
	public static List<PrefilledNumbersRequestDTO> transformPrefilledNumbersRequestWSToDTO(List<PrefilledNumbersRequest> request) {

		if(request==null) {
			return null;
		}
		
		List<PrefilledNumbersRequestDTO> dto = new ArrayList<PrefilledNumbersRequestDTO>();
		
		for (PrefilledNumbersRequest prefilledNumbersRequest : request) {
			PrefilledNumbersRequestDTO pnDTO = new PrefilledNumbersRequestDTO();
			pnDTO.setPrefilledNumbersDTO(transformPrefilledNumbersWSToDTO(prefilledNumbersRequest.getPrefilledNumbers()));
			dto.add(pnDTO);
		}
		
		return dto;
	}
	
	
	public static com.airfrance.repind.dto.ws.PrefilledNumbersDTO transformPrefilledNumbersWSToDTO(PrefilledNumbers request) {

		if(request==null) {
			return null;
		}
		
		com.airfrance.repind.dto.ws.PrefilledNumbersDTO dto = new com.airfrance.repind.dto.ws.PrefilledNumbersDTO();
		dto.setContractNumber(request.getContractNumber());
		dto.setContractType(request.getContractType());
		
		return dto;
	}
	

	/*
	 * PREFERENCE
	 * 
	 */
	public static PreferenceRequestDTO transformPreferenceRequestWSToDTO(PreferenceRequest request) {

		if(request==null) {
			return null;
		}
		
		PreferenceRequestDTO dto = new PreferenceRequestDTO();
		dto.setPreferenceDTO(transformPreferenceWSToDTO(request.getPreference()));
		
		return dto;
	}
	
	
	public static List<PreferenceDTO> transformPreferenceWSToDTO(List<PreferenceV2> request) {

		if(request==null) {
			return null;
		}
		
		List<PreferenceDTO> dto = new ArrayList<PreferenceDTO>();
		
		for (PreferenceV2 pref : request) {
			PreferenceDTO prefDTO = new PreferenceDTO();
			if(pref.getId() != null) {
				prefDTO.setId(Long.parseLong(pref.getId()));
			}
			if(pref.getLink() != null) {
				prefDTO.setLink(Long.parseLong(pref.getLink()));
			}
			prefDTO.setPreferencesDatasDTO(transformPreferenceDatasWSToDTO(pref.getPreferenceDatas()));
			prefDTO.setType(pref.getType());
			
			dto.add(prefDTO);
		}
		
		return dto;
	}
	
	public static PreferenceDatasDTO transformPreferenceDatasWSToDTO(PreferenceDatasV2 request) {

		if(request==null) {
			return null;
		}
		
		PreferenceDatasDTO dto = new PreferenceDatasDTO();
		dto.setPreferenceDataDTO(transformPreferenceDataWSToDTO(request.getPreferenceData()));
		
		return dto;
	}
	
	public static List<PreferenceDataDTO> transformPreferenceDataWSToDTO(List<PreferenceDataV2> request) {

		if(request==null) {
			return null;
		}
		
		List<PreferenceDataDTO> dto = new ArrayList<PreferenceDataDTO>();
		
		for (PreferenceDataV2 prefData : request) {
			PreferenceDataDTO prefDTO = new PreferenceDataDTO();
			prefDTO.setKey(prefData.getKey());
			prefDTO.setValue(prefData.getValue());
			
			dto.add(prefDTO);
		}
		
		return dto;
	}
	

	/*
	 * POSTAL ADDRESS
	 * 
	 */
	public static List<PostalAddressRequestDTO> transformPostalAddressRequestWSToDTO(List<PostalAddressRequest> request) {

		if(request==null) {
			return null;
		}
		
		List<PostalAddressRequestDTO> dto = new ArrayList<PostalAddressRequestDTO>();
		
		for (PostalAddressRequest postalAddressRequest : request) {
			PostalAddressRequestDTO paDTO = new PostalAddressRequestDTO();
			
			paDTO.setPostalAddressContentDTO(transformPostalAddressContentWSToDTO(postalAddressRequest.getPostalAddressContent()));
			paDTO.setPostalAddressPropertiesDTO(transformPostalAddressPropertiesWSToDTO(postalAddressRequest.getPostalAddressProperties()));
			paDTO.setUsageAddressDTO(transformPostalAddressUsageWSToDTO(postalAddressRequest.getUsageAddress()));
			
			dto.add(paDTO);
		}
		
		return dto;
	}

	public static PostalAddressContentDTO transformPostalAddressContentWSToDTO(PostalAddressContent request) {

		if(request==null) {
			return null;
		}
		
		PostalAddressContentDTO dto = new PostalAddressContentDTO();
		if (request.getAdditionalInformation() != null) {
			dto.setAdditionalInformation(request.getAdditionalInformation().toUpperCase());
		}
		if (request.getCity() != null) {
			dto.setCity(request.getCity().toUpperCase());
		}
		if (request.getCorporateName() != null) {
			dto.setCorporateName(request.getCorporateName().toUpperCase());
		}
		if (request.getCountryCode() != null) {
			dto.setCountryCode(request.getCountryCode().toUpperCase());
		}
		if (request.getDistrict() != null) {
			dto.setDistrict(request.getDistrict().toUpperCase());
		}
		if (request.getNumberAndStreet() != null) {
			dto.setNumberAndStreet(request.getNumberAndStreet().toUpperCase());
		}
		if (request.getStateCode() != null) {
			dto.setStateCode(request.getStateCode().toUpperCase());
		}
		if (request.getZipCode() != null) {
			dto.setZipCode(request.getZipCode().toUpperCase());
		}
		
		return dto;
	}

	public static PostalAddressPropertiesDTO transformPostalAddressPropertiesWSToDTO(PostalAddressProperties request) {

		if(request==null) {
			return null;
		}
		
		PostalAddressPropertiesDTO dto = new PostalAddressPropertiesDTO();
		dto.setIndicAdrNorm(request.isIndicAdrNorm());
		if (request.getMediumCode() != null) {
			dto.setMediumCode(request.getMediumCode().toUpperCase());
		}
		if (request.getMediumStatus() != null) {
			dto.setMediumStatus(request.getMediumStatus().toUpperCase());
		}
		dto.setVersion(request.getVersion());
		
		return dto;
	}

	public static UsageAddressDTO transformPostalAddressUsageWSToDTO(UsageAddress request) {

		if(request==null) {
			return null;
		}
		
		UsageAddressDTO dto = new UsageAddressDTO();
		if (request.getAddressRoleCode() != null) {
			dto.setAddressRoleCode(request.getAddressRoleCode().toUpperCase());
		}
		if (request.getApplicationCode() != null) {
			dto.setApplicationCode(request.getApplicationCode().toUpperCase());
		}
		dto.setUsageNumber(request.getUsageNumber());
		
		return dto;
	}
	
	/*
	 * INDIVIDUAL
	 * 
	 */
	public static IndividualRequestDTO transformIndividualRequestWSToDTO(IndividualRequest request) {

		if(request==null) {
			return null;
		}
		
		IndividualRequestDTO dto = new IndividualRequestDTO();
		dto.setIndividualInformationsDTO(transformIndividualInformationsRequestWSToDTO(request.getIndividualInformations()));
		dto.setIndividualProfilDTO(transformIndividualProfilRequestWSToDTO(request.getIndividualProfil()));
		dto.setTitleCode(transformCivilianRequestWSToDTO(request.getCivilian()));
		
		return dto;
	}
	
	public static IndividualInformationsDTO transformIndividualInformationsRequestWSToDTO(IndividualInformationsV3 request) {

		if(request==null) {
			return null;
		}
		
		IndividualInformationsDTO dto = new IndividualInformationsDTO();
		dto.setBirthDate(request.getBirthDate());
		
		String civility = request.getCivility();
		if(civility != null){
			civility = StringUtils.upperCase(civility).trim();
		}
		
		dto.setCivility(civility);
		dto.setFirstNamePseudonym(request.getFirstNamePseudonym());
		dto.setFirstNameSC(request.getFirstNameSC());
		dto.setFlagNoFusion(request.isFlagNoFusion());
		dto.setFlagThirdTrap(request.isFlagThirdTrap());
		dto.setGender(request.getGender());
		dto.setIdentifier(request.getIdentifier());
		dto.setLanguageCode(request.getLanguageCode());
		dto.setLastNamePseudonym(request.getLastNamePseudonym());
		dto.setLastNameSC(request.getLastNameSC());
		dto.setMiddleNameSC(request.getMiddleNameSC());
		dto.setNationality(request.getNationality());
		dto.setPersonalIdentifier(request.getPersonalIdentifier());
		dto.setPopulationType(request.getPopulationType());
		dto.setSecondFirstName(request.getSecondFirstName());
		dto.setSecondNationality(request.getSecondNationality());
		dto.setStatus(request.getStatus());
		dto.setVersion(request.getVersion());				
		
		return dto;
	}
	
	public static IndividualProfilDTO transformIndividualProfilRequestWSToDTO(IndividualProfilV3 request) {

		if(request==null) {
			return null;
		}
		
		IndividualProfilDTO dto = new IndividualProfilDTO();
		dto.setCarrierCode(request.getCarrierCode());
		dto.setChildrenNumber(request.getChildrenNumber());
		dto.setCivilianCode(request.getCivilianCode());
		dto.setCustomerSegment(request.getCustomerSegment());
		dto.setEmailOptin(request.getEmailOptin());
		dto.setLanguageCode(request.getLanguageCode());
		dto.setProAreaCode(request.getProAreaCode());
		dto.setProAreaWording(request.getProAreaWording());
		dto.setProFunctionCode(request.getProFunctionCode());
		dto.setProFunctionWording(request.getProFunctionWording());
		dto.setStudentCode(request.getStudentCode());
		
		return dto;
	}
	
	public static String transformCivilianRequestWSToDTO(Civilian request) {

		if(request==null) {
			return null;
		}
		
		String titleCode = request.getTitleCode();
		
		return titleCode;
	}
	
	/*
	 * EXTERNAL IDENTIFIER
	 * 
	 */
	public static List<ExternalIdentifierRequestDTO> transformExternalIdentifierRequestWSToDTO(List<ExternalIdentifierRequest> request) {
		

		if(request==null) {
			return null;
		}
		
		List<ExternalIdentifierRequestDTO> dto = new ArrayList<ExternalIdentifierRequestDTO>();
		
		for (ExternalIdentifierRequest extIdR : request) {
			ExternalIdentifierRequestDTO extIdDTO = new ExternalIdentifierRequestDTO();
			extIdDTO.setExternalIdentifierDataDTO(transformExternalIdentifierDataRequestWSToDTO(extIdR.getExternalIdentifierData()));
			extIdDTO.setExternalIdentifierDTO(transformExternalIdentifierWSToDTO(extIdR.getExternalIdentifier()));
			
			dto.add(extIdDTO);
		}
		
		
		return dto;
	}
	
	public static List<com.airfrance.repind.dto.ws.ExternalIdentifierDataDTO> transformExternalIdentifierDataRequestWSToDTO(List<ExternalIdentifierData> request) {

		if(request==null) {
			return null;
		}
		
		List<com.airfrance.repind.dto.ws.ExternalIdentifierDataDTO> dto = new ArrayList<com.airfrance.repind.dto.ws.ExternalIdentifierDataDTO>();
		
		for (ExternalIdentifierData extIdD : request) {
			com.airfrance.repind.dto.ws.ExternalIdentifierDataDTO extIdDDTO = new com.airfrance.repind.dto.ws.ExternalIdentifierDataDTO();
			extIdDDTO.setKey(extIdD.getKey());
			extIdDDTO.setValue(extIdD.getValue());
			
			dto.add(extIdDDTO);
		}
		
		
		return dto;
	}
	
	public static com.airfrance.repind.dto.ws.ExternalIdentifierDTO transformExternalIdentifierWSToDTO(ExternalIdentifier request) {

		if(request==null) {
			return null;
		}
		
		com.airfrance.repind.dto.ws.ExternalIdentifierDTO dto = new com.airfrance.repind.dto.ws.ExternalIdentifierDTO();
		dto.setIdentifier(request.getIdentifier());
		dto.setType(request.getType());
		
		return dto;
	}
	
	/*
	 * EMAIL
	 * 
	 */
	public static List<EmailRequestDTO> transformEmailRequestWSToDTO(List<EmailRequest> request) {

		if(request==null) {
			return null;
		}
		
		List<EmailRequestDTO> dto = new ArrayList<EmailRequestDTO>();
		
		for (EmailRequest emailRequest : request) {
			EmailRequestDTO emDTO = new EmailRequestDTO();
			emDTO.setEmailDTO(transformEmailWSToDTO(emailRequest.getEmail()));
			dto.add(emDTO);
		}
		
		
		return dto;
	}
	
	public static com.airfrance.repind.dto.ws.EmailDTO transformEmailWSToDTO(Email request) {

		if(request==null) {
			return null;
		}
		
		com.airfrance.repind.dto.ws.EmailDTO dto = new com.airfrance.repind.dto.ws.EmailDTO();
		//REPIND-1288: Put in lower case email
		// REPIND-1767 : Detect UTF8 and let passed that to be store in UTF-8
		if (SicUtf8StringUtils.isNonASCII(request.getEmail())) {
			dto.setEmail(request.getEmail());
		} else {
			dto.setEmail(SicStringUtils.normalizeEmail(request.getEmail()));
		}
		dto.setEmailOptin(request.getEmailOptin());
		dto.setMediumCode(request.getMediumCode());
		dto.setMediumStatus(request.getMediumStatus());
		dto.setVersion(request.getVersion());
		
		return dto;
	}
	
	/*
	 * ALERT
	 * 
	 */
	public static AlertRequestDTO transformAlertRequestWSToDTO(AlertRequest request) {

		if(request==null) {
			return null;
		}
		
		AlertRequestDTO dto = new AlertRequestDTO();
		dto.setAlertDTO(transformAlertWSToDTO(request.getAlert()));
		
		
		return dto;
	}

	public static List<com.airfrance.repind.dto.ws.AlertDTO> transformAlertWSToDTO(List<Alert> request) {

		if(request==null) {
			return null;
		}
		
		List<com.airfrance.repind.dto.ws.AlertDTO> dto = new ArrayList<com.airfrance.repind.dto.ws.AlertDTO>();
		for (Alert al : request) {
			com.airfrance.repind.dto.ws.AlertDTO alDTO = new com.airfrance.repind.dto.ws.AlertDTO();
			alDTO.setAlertDataDTO(transformAlertDataWSToDTO(al.getAlertData()));
			if(StringUtils.isNotBlank(al.getAlertId())) {
				alDTO.setAlertId(SicStringUtils.getIntegerFromString(al.getAlertId()));
			}
			alDTO.setOptin(al.getOptIn());
			alDTO.setType(al.getType());
			
			dto.add(alDTO);
			
		}
		
		return dto;
	}

	public static List<com.airfrance.repind.dto.ws.AlertDataDTO> transformAlertDataWSToDTO(List<AlertData> request) {

		if(request==null) {
			return null;
		}
		
		List<com.airfrance.repind.dto.ws.AlertDataDTO> dto = new ArrayList<com.airfrance.repind.dto.ws.AlertDataDTO>();
		for (AlertData ald : request) {
			com.airfrance.repind.dto.ws.AlertDataDTO aldDTO = new com.airfrance.repind.dto.ws.AlertDataDTO();
			aldDTO.setKey(ald.getKey());
			aldDTO.setValue(ald.getValue());
			dto.add(aldDTO);
		}
		
		return dto;
	}

	
	/*
	 * DELEGATION DATA
	 * 
	 */
	public static AccountDelegationDataRequestDTO transformAccountDelegDataRequestWSToDTO(AccountDelegationDataRequest request) {

		if(request==null) {
			return null;
		}
		
		AccountDelegationDataRequestDTO dto = new AccountDelegationDataRequestDTO();
		AccountDelegationDataDTO accDelDataDTO = new AccountDelegationDataDTO();
		accDelDataDTO.setDelegateDTO(transformDelegateRequestWSToDTO(request.getDelegate()));
		accDelDataDTO.setDelegatorDTO(transformDelegatorRequestWSToDTO(request.getDelegator()));
		dto.setAccountDelegationDataDTO(accDelDataDTO);
		
		
		return dto;
	}

	public static List<DelegatorDTO> transformDelegatorRequestWSToDTO(List<Delegator> request) {

		if(request==null) {
			return null;
		}
		
		List<DelegatorDTO> dto = new ArrayList<DelegatorDTO>();
		for (Delegator delegator : request) {
			DelegatorDTO delDTO = new DelegatorDTO();
			delDTO.setDelegatorDataDTO(transformDelegatorDataRequestWSToDTO(delegator.getDelegationData()));
			
			dto.add(delDTO);
			
		}
		
		return dto;
	}

	public static DelegatorDataDTO transformDelegatorDataRequestWSToDTO(DelegationData request) {

		if(request==null) {
			return null;
		}
		
		DelegatorDataDTO dto = new DelegatorDataDTO();
		dto.setDelegationAction(request.getDelegationAction());
		dto.setDelegationType(request.getDelegationType());
		dto.setGin(request.getGin());
		
		return dto;
	}
	
	public static List<DelegateDTO> transformDelegateRequestWSToDTO(List<Delegate> request) {

		if(request==null) {
			return null;
		}
		
		List<DelegateDTO> dto = new ArrayList<DelegateDTO>();
		for (Delegate delegate : request) {
			DelegateDTO delDTO = new DelegateDTO();
			delDTO.setDelegateDataDTO(transformDelegateDataRequestWSToDTO(delegate.getDelegationData()));
			delDTO.setComplementaryInformationDTO(transformComplemInfoRequestWSToDTO(delegate.getComplementaryInformation()));
			
			dto.add(delDTO);
			
		}
		
		return dto;
	}

	public static DelegateDataDTO transformDelegateDataRequestWSToDTO(DelegationData request) {

		if(request==null) {
			return null;
		}
		
		DelegateDataDTO dto = new DelegateDataDTO();
		dto.setDelegationAction(request.getDelegationAction());
		dto.setDelegationType(request.getDelegationType());
		dto.setGin(request.getGin());
		
		return dto;
	}

	public static List<com.airfrance.repind.dto.ws.ComplementaryInformationDTO> transformComplemInfoRequestWSToDTO(List<ComplementaryInformation> request) {

		if(request==null) {
			return null;
		}
		
		List<com.airfrance.repind.dto.ws.ComplementaryInformationDTO> dto = new ArrayList<com.airfrance.repind.dto.ws.ComplementaryInformationDTO>();
		
		for (ComplementaryInformation complemInfo : request) {
			
			com.airfrance.repind.dto.ws.ComplementaryInformationDTO ciDTO = new com.airfrance.repind.dto.ws.ComplementaryInformationDTO();
			ciDTO.setComplementaryInformationDatasDTO(transformComplemInfoDatasRequestWSToDTO(complemInfo.getComplementaryInformationDatas()));
			ciDTO.setType(complemInfo.getType());
			
			dto.add(ciDTO);
			
		}
		
		return dto;
	}

	public static com.airfrance.repind.dto.ws.ComplementaryInformationDatasDTO transformComplemInfoDatasRequestWSToDTO(ComplementaryInformationDatas request) {

		if(request==null) {
			return null;
		}
		
		com.airfrance.repind.dto.ws.ComplementaryInformationDatasDTO dto = new com.airfrance.repind.dto.ws.ComplementaryInformationDatasDTO();
		dto.setComplementaryInformationDataDTO(transformComplemInfoDataRequestWSToDTO(request.getComplementaryInformationData()));
		
		return dto;
	}

	public static List<com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO> transformComplemInfoDataRequestWSToDTO(List<ComplementaryInformationData> request) {

		if(request==null) {
			return null;
		}
		
		List<com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO> dto = new ArrayList<com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO>();
		
		for (ComplementaryInformationData complemInfo : request) {
			
			com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO cidDTO = new com.airfrance.repind.dto.ws.ComplementaryInformationDataDTO();
			cidDTO.setKey(complemInfo.getKey());
			cidDTO.setValue(complemInfo.getValue());
			
			dto.add(cidDTO);
			
		}
		
		return dto;
	}
	
	/*
	 * COMMUNICATION PREFRENCES
	 * 
	 */
	public static List<CommunicationPreferencesRequestDTO> transformCommPrefRequestWSToDTO(List<ComunicationPreferencesRequest> request) {

		if(request==null || request.isEmpty()) {
			return null;
		}
		
		List<CommunicationPreferencesRequestDTO> dto = new ArrayList<CommunicationPreferencesRequestDTO>();
		
		for (ComunicationPreferencesRequest comunicationPreferencesRequest : request) {
			if(comunicationPreferencesRequest.getCommunicationPreferences() != null){
				CommunicationPreferences cpr = comunicationPreferencesRequest.getCommunicationPreferences();

				com.airfrance.repind.dto.ws.CommunicationPreferencesDTO cpDTO = new com.airfrance.repind.dto.ws.CommunicationPreferencesDTO();
				cpDTO.setCommunicationGroupeType(cpr.getCommunicationGroupeType());
				cpDTO.setCommunicationType(cpr.getCommunicationType());
				cpDTO.setDateOfConsent(cpr.getDateOfConsent());
				cpDTO.setDateOfConsentPartner(cpr.getDateOfConsentPartner());
				cpDTO.setDateOfEntry(cpr.getDateOfEntry());
				cpDTO.setDomain(cpr.getDomain());
				cpDTO.setMarketLanguageDTO(transformMarketLanguageRequestWSToDTO(cpr.getMarketLanguage()));
				cpDTO.setMediaDTO(transformMediaRequestWSToDTO(cpr.getMedia()));
				cpDTO.setOptIn(cpr.getOptIn());
				cpDTO.setOptInPartner(cpr.getOptInPartner());
				cpDTO.setSubscriptionChannel(cpr.getSubscriptionChannel());

				CommunicationPreferencesRequestDTO cprDTO = new CommunicationPreferencesRequestDTO();
				cprDTO.setCommunicationPreferencesDTO(cpDTO);

				dto.add(cprDTO);
			}

			
		}
		
		return dto;
	}
	

	public static List<com.airfrance.repind.dto.ws.MarketLanguageDTO> transformMarketLanguageRequestWSToDTO(List<MarketLanguage> request) {

		if(request==null) {
			return null;
		}
		
		List<com.airfrance.repind.dto.ws.MarketLanguageDTO> dto = new ArrayList<com.airfrance.repind.dto.ws.MarketLanguageDTO>();
		
		for (MarketLanguage mlRequest : request) {

			com.airfrance.repind.dto.ws.MarketLanguageDTO mlDTO = new com.airfrance.repind.dto.ws.MarketLanguageDTO();
			mlDTO.setDateOfConsent(mlRequest.getDateOfConsent());
			mlDTO.setLanguage(mlRequest.getLanguage().toString());
			mlDTO.setMarket(mlRequest.getMarket());
			mlDTO.setMediaDTO(transformMediaRequestWSToDTO(mlRequest.getMedia()));
			mlDTO.setSignatureDTOList(transformSignatureRequestWSToDTO(mlRequest.getSignature()));
			mlDTO.setOptIn(mlRequest.getOptIn());
			
			dto.add(mlDTO);
			
		}
		return dto;
	}

	public static com.airfrance.repind.dto.ws.MediaDTO transformMediaRequestWSToDTO(Media request) {

		if(request==null) {
			return null;
		}
		
		com.airfrance.repind.dto.ws.MediaDTO dto = new com.airfrance.repind.dto.ws.MediaDTO();
		dto.setMedia1(request.getMedia1());
		dto.setMedia2(request.getMedia2());
		dto.setMedia3(request.getMedia3());
		dto.setMedia4(request.getMedia4());
		dto.setMedia5(request.getMedia5());
		
		
		return dto;
	}

	public static List<SignatureDTO> transformSignatureRequestWSToDTO(List<Signature> request) {

		if(request==null) {
			return null;
		}
		List<com.airfrance.repind.dto.ws.SignatureDTO> signatureDTOList = new ArrayList<>();
		for(Signature signature : request) {
			com.airfrance.repind.dto.ws.SignatureDTO dto = new com.airfrance.repind.dto.ws.SignatureDTO();
			dto.setSignatureType(signature.getSignatureType());
			dto.setSignatureSite(signature.getSignatureSite());
			dto.setSignature(signature.getSignature());
			dto.setDate(signature.getDate());
			signatureDTOList.add(dto);
		}
		return signatureDTOList;
	}


}
