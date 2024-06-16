package com.afklm.repind.v5.provideindividualdata;

import com.afklm.repind.helpers.ProvideIndividualDataHelper;
import com.afklm.repind.v5.provideindividualdata.helpers.BusinessExceptionHelper;
import com.afklm.repind.v5.provideindividualdata.helpers.MarketingDataHelper;
import com.afklm.repind.v5.provideindividualdata.helpers.PaymentPreferencesDataHelper;
import com.afklm.repind.v5.provideindividualdata.transformers.ProvideIndividualDataTransformV5;
import com.afklm.repind.v5.provideindividualdata.type.MaskedPaymentPreferences;
import com.afklm.repind.v5.provideindividualdata.type.ScopesToProvideEnum;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v5.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v5.ProvideIndividualDataServiceV5;
import com.afklm.soa.stubs.w000418.v5.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v5.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v5.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000418.v5.response.DelegationDataResponse;
import com.afklm.soa.stubs.w000418.v5.response.PostalAddressResponse;
import com.afklm.soa.stubs.w000418.v5.siccommontype.Requestor;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.DelegationTypeEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.channel.ChannelToCheckDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.adh.individualinformation.IndividualInformationRequestDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.channel.internal.ChannelToCheckDS;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.*;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.util.GaugeUtils;
import com.airfrance.repind.util.SicStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>Title : ProvideIndividualDataImpl.java</p>
 * 
 * <p>Copyright : Copyright (c) 2015</p>
 * <p>Company : AIRFRANCE</p>
 * 
 * @author t449753
 */
@Service("passenger_ProvideIndividualData-v05Bean")
@WebService(endpointInterface="com.afklm.soa.stubs.w000418.v5.ProvideIndividualDataServiceV5", targetNamespace = "http://www.af-klm.com/services/passenger/ProvideIndividualDataService-v5/wsdl", serviceName = "ProvideIndividualDataServiceService-v5", portName = "ProvideIndividualDataService-v5-soap11http")
@Slf4j
public class ProvideIndividualDataV5Impl implements ProvideIndividualDataServiceV5 {

	@Autowired
	protected MyAccountDS myAccountDS;
	
//	@Autowired
//	protected IProspectDS prospectDS;

	@Autowired
	protected PrefilledNumbersDS prefilledNumbersDS;
	
	@Autowired
	protected AccountDataDS accountDataDS;
	
	@Autowired
	protected TelecomDS telecomDS;
	
	@Autowired
	protected DelegationDataDS delegationDataDS;
	
	@Autowired
	protected ExternalIdentifierDS externalIdentifierDS;
	
	@Autowired
	protected CommunicationPreferencesDS communicationPreferencesDS;
	
	@Autowired
	protected RoleDS roleDS;
	
	@Autowired
	protected EmailDS emailDS;
	
	@Autowired
	protected PostalAddressDS postalAddressDS;
	
	@Autowired
	protected ChannelToCheckDS channelToCheckDS;

	@Autowired
	protected BusinessExceptionHelper businessExceptionHelperV5;
	
	@Autowired
	protected MarketingDataHelper marketingDataHelperV5;
	
	@Autowired
	protected PaymentPreferencesDataHelper paymentPreferencesDataHelperV5;

	@Autowired
	protected PreferenceDS preferenceDS;
	
	@Autowired
	protected IndividuDS individuDS;

	@Resource
	private WebServiceContext context;

	
    public final static String NOT_COLLECTED = "NOT COLLECTED";
    public final static String POSTAL_CODE_9999 = "99999";
	
	public ProvideIndividualInformationResponse provideIndividualDataByIdentifier(ProvideIndividualInformationRequest request) throws BusinessErrorBlocBusinessException, SystemException {

		// REPIND-1441 : Trace who are consumming and what
		TraceInput(request);
		
		ProvideIndividualInformationResponse response = null;
		boolean isFBRecognitionActivate = false;

		try {

			String gin = null;
			IndividuDTO individuDTO = null;
			AccountDataDTO accountDataDTO = null;
			List<TelecomsDTO> telecomsDTOList = null;
			Boolean isIndividu = true;
			Boolean isProspect = false;

			checkRequest(request);

			IndividualInformationRequestDTO requestDTO = new IndividualInformationRequestDTO();
			
			// REPIND-1546 : NPE on SONAR
			if (request != null) {
				requestDTO = new IndividualInformationRequestDTO(request.getIdentificationNumber(), request.getOption());
			}

			// get scopeToProvide
			Set<ScopesToProvideEnum> scopesToProvideSet = ScopesToProvideEnum.getEnumSet(request.getScopeToProvide());

			if(scopesToProvideSet == null && getScope(request) != null) {
				throw new InvalidParameterException("scope is not used anymore, please use scopeToProvide");
			}

			// avtivate scopes for Individual if requested
			activateIndividualScopes(scopesToProvideSet);

			int percentageMarketingData = 0;
			int percentagePaymentPreferences = 0;

			/* ==================================================================================== *
			 * INDIVIDUAL DATA																		*
			 * ==================================================================================== */			
			individuDTO = myAccountDS.searchIndividualInformation(requestDTO);
			if(individuDS.isIndividualNotProvide(individuDTO))
				throw new NotFoundException("");
			
			
			// REPIND-555 : We save old TargetPopulation to restore it later
			String populationTargeted = requestDTO.getPopulationTargeted();
			
			// REPIND-555 : Migration SIC UTF8 in SIC
			// ProspectDTO prospectDTO = null;
			if(individuDTO == null) {
				// prospectDTO = getProspectDTO(requestDTO);
				// individuDTO = ProvideIndividualDataTransformV5.transformToIndividuDTO(prospectDTO);
				requestDTO.setPopulationTargeted("W");
				individuDTO = myAccountDS.searchIndividualInformation(requestDTO);
				requestDTO.setPopulationTargeted(populationTargeted);
				// isIndividu = false;
			}
			
			// isProspect only if individual is WhiteWinger
			isProspect = individuDTO == null ? false : "W".equals(individuDTO.getType());
			
			response = ProvideIndividualDataTransformV5.transformToProvideIndividualInformationResponse(individuDTO, scopesToProvideSet, isIndividu);

			checkResponse(response);

			// get gin
			// REPIND-1398 : Test SONAR NPE
			if (individuDTO != null) {
				gin = individuDTO.getSgin();
			}
			if(isScopesToProvideRequested(scopesToProvideSet)) {
				// add postal address
				if(!isProspect && isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.POSTAL_ADDRESS))) {
					List<PostalAddressDTO> postalAddressDTOList = postalAddressDS.findPostalAddress(gin);
					ProvideIndividualDataTransformV5.transformToPostalAddressResponse(postalAddressDTOList, response.getPostalAddressResponse());
					whiteningPostalAddressContent(request, response);
				} 
				// REPIND-555 : Prospect will become an Individual with normal data (postal and comm pref) 
				// add localization data
				if(isProspect && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.LOCALIZATION))) {
					List<PostalAddressDTO> postalAddressDTOList = postalAddressDS.findPostalAddress(gin);
					ProvideIndividualDataTransformV5.transformToLocalizationResponse(postalAddressDTOList, response.getLocalizationResponse());
					// ProvideIndividualDataTransformV5.transformToLocalizationResponse(prospectDTO.getProspectLocalizationDTO(), response.getLocalizationResponse());
				}
				/* 
				 * TODO Check if is necessary
				 */
				// add preference data
//				if(isProspect && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.PREFERENCE))) {
//				 	response.setPreferencesResponse(ProvideIndividualDataTransformV5.transformToPreferencesResponse(individuDTO.getPreferenceDTO()));
//				 	// response.setPreferencesResponse(ProvideIndividualDataTransformV5.transformToPreferencesResponse(prospectDTO.getProspectLocalizationDTO()));
				// add preference data and marketing
				if(scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.PREFERENCE) || scopesToProvideSet.contains(ScopesToProvideEnum.MARKETING)) {					
					PreferenceDTO preferenceDTO = new PreferenceDTO();
					preferenceDTO.setGin(gin);
					List<PreferenceDTO> preferenceDTOList = preferenceDS.findByExample(preferenceDTO);
					
					// Normalize all preference data key
					List<PreferenceDTO> normalizedPrefDtoList = new ArrayList<>();
					if (preferenceDTOList != null) {
						for (PreferenceDTO prefDtoFromDB : preferenceDTOList) {
							PreferenceDTO normalizedPrefDto = marketingDataHelperV5.normalizePreferenceDataKey(prefDtoFromDB);
							normalizedPrefDtoList.add(normalizedPrefDto);
						}
					}
					
					//Transform TDC and APC to MarketingResponse
					//Transform TPC (WWP) to PreferenceResponse
					ProvideIndividualDataTransformV5.transformPreferenceDTOListToResponse(normalizedPrefDtoList, response);
					
//					//Should be not used anymore after migrating of Prospects from UTF8 to SIC
//					if(!isIndividu)  {
//						response.setPreferencesResponse(ProvideIndividualDataTransformV5.transformToPreferencesResponse(prospectDTO.getProspectLocalizationDTO()));
//					}
					if (isProspect) {
						response.setMarketingDataResponse(null);
					}
					

				}
				// add mails
				if(scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.EMAIL)) {
					List<EmailDTO> emailDTOList = null;
					if(isIndividu) {
						emailDTOList = emailDS.findEmail(gin);
					} else if(individuDTO.getEmaildto() != null) {
						emailDTOList = new ArrayList<EmailDTO>(individuDTO.getEmaildto());
					}
					ProvideIndividualDataTransformV5.transformToEmailResponse(emailDTOList, response.getEmailResponse());
				}
				// add role contracts
				if(isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.CONTRACT))) {
					List<RoleContratsDTO> roleContratsDTOList = roleDS.findRoleContrats(gin, isFBRecognitionActivate);
					ProvideIndividualDataTransformV5.transformToContractResponse(roleContratsDTOList, response.getContractResponse(), isFBRecognitionActivate);
				}
				// add account data
				if(isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.ACCOUNT))) {
					accountDataDTO = accountDataDS.getOnlyAccountByGin(gin);
					response.setAccountDataResponse(ProvideIndividualDataTransformV5.transformToAccountDataResponse(accountDataDTO));
				}
				// add telecom data with usage code management 
				if(scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.TELECOM)) {
					if(isIndividu) {
						telecomsDTOList = telecomDS.findLatestByUsageCode(gin);
					} else if(individuDTO.getTelecoms() != null) {
						telecomsDTOList = new ArrayList<TelecomsDTO>(individuDTO.getTelecoms());
					}
					ProvideIndividualDataTransformV5.transformToTelecomResponse(telecomsDTOList, response.getTelecomResponse());
				}
				// add delegation data
				if(isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.DELEGATION))) {
					List<DelegationDataDTO> delegatorList = delegationDataDS.findDelegator(gin);
					List<DelegationDataDTO> delegateList = delegationDataDS.findDelegate(gin);
					
					/*
					 * Specific code for KID SOLO
					 * We must not provide kid solo delegations for this version
					 * To be removed once we want those delegations
					 * */
					List<DelegationTypeEnum> delegeationTypesToRemove = new ArrayList<DelegationTypeEnum>(Arrays.asList(DelegationTypeEnum.UNACOMPAGNED_MINOR, DelegationTypeEnum.UNACOMPAGNED_MINOR_ATTENDANT));
					ProvideIndividualDataHelper.deleteDelegationFromReponseByTypesDelegation(delegateList, delegeationTypesToRemove);
					ProvideIndividualDataHelper.deleteDelegationFromReponseByTypesDelegation(delegatorList, delegeationTypesToRemove);
					
					DelegationDataResponse delegationDataResponse = ProvideIndividualDataTransformV5.dtoTOws(delegatorList, delegateList);
					response.setDelegationDataResponse(delegationDataResponse);
				}
				// add prefilled numbers
				if(isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.PREFILLED_NUMBER))) {
					List<PrefilledNumbersDTO> prefilledNumbersDTOList = prefilledNumbersDS.findPrefilledNumbers(gin);
					ProvideIndividualDataTransformV5.transformToPrefilledNumbers(prefilledNumbersDTOList, response.getPrefilledNumbersResponse());
				}
				// add external identifiers
				if(isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.EXTERNAL_IDENTIFIER))) {
					List<ExternalIdentifierDTO> externalIdentifierDTOList = externalIdentifierDS.findExternalIdentifier(gin);
					ProvideIndividualDataTransformV5.transformToExternalIdentifierResponse(externalIdentifierDTOList, response.getExternalIdentifierResponse());
				}
				// add communication preferences
				if(scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.COMMUNICATIION_PREFERENCE)) {
					List<CommunicationPreferencesDTO> communicationPreferencesDTOList = null;
					if(isIndividu) {
						communicationPreferencesDTOList = communicationPreferencesDS.findCommunicationPreferences(gin);
					} else if (individuDTO.getCommunicationpreferencesdto() != null) {
						communicationPreferencesDTOList = new ArrayList<CommunicationPreferencesDTO>(individuDTO.getCommunicationpreferencesdto());
					}
					ProvideIndividualDataTransformV5.transformToCommunicationPreferencesResponse(communicationPreferencesDTOList, response.getCommunicationPreferencesResponse());
				}
				// add warnings
				Set<WarningDTO> warningDTOSet = myAccountDS.checkValidAccount(gin);
				ProvideIndividualDataTransformV5.transformToWarningResponse(warningDTOSet, response.getWarningResponse());
			}

			/* ==================================================================================== *
			 * MARKETING DATA																		*
			 * ==================================================================================== */

			/*if(isIndividu && (scopesToProvideSet != null && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.MARKETING)))) {

				MarketingDataResponse marketingData = marketingDataHelperV5.provideMarketingData(gin);
				response.setMarketingDataResponse(marketingData);

				percentageMarketingData = marketingDataHelperV5.calculateMarketingInformationGauge(marketingData);
			}*/

			/* ==================================================================================== *
			 * PAYMENT PREFERENCES																	*
			 * ==================================================================================== */
			//REPIND-1230: In case of exception, we catch it in order to provide a valid answer
			if (isIndividu) {
				try {
					MaskedPaymentPreferences paymentPreferencesResponse = paymentPreferencesDataHelperV5.provideMaskedPaymentPreferences(gin);
					percentagePaymentPreferences = paymentPreferencesDataHelperV5.computePaymentPreferencesGauge(paymentPreferencesResponse);
				} catch (JrafDomainException e) {
					log.warn("Unable to get PaymentPreferences for the GIN: {}",gin);
				}
				// compute gauge
				if(response.getAccountDataResponse()!=null && response.getAccountDataResponse().getAccountData()!=null) {
					// REPIND-1546 : NPE on SONAR
					int percentageFullProfil = GaugeUtils.calculPercentageFullProfil(individuDTO, accountDataDTO, (individuDTO == null ? new ArrayList<PostalAddressDTO>() : individuDTO.getPostaladdressdto()), telecomsDTOList, percentageMarketingData, percentagePaymentPreferences);
					response.getAccountDataResponse().getAccountData().setPercentageFullProfil(percentageFullProfil);
				}
			}

		}
		// ERROR 001 : NOT FOUND
		catch(NotFoundException e) {
			// REPIND-255 : Suppression du log ERROR car c'est un cas normal
			log.info("provideIndividualDataByIdentifier : {}",e.getMessage());
			businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_001, e.getMessage());
		}
		// ERROR 133 : MISSING PARAMETER
		catch(MissingParameterException e) {
			log.error("provideIndividualDataByIdentifier ERROR", e);
			businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_133, e.getMessage());
		}
		// ERROR 932 : INVALID PARAMETER
		catch(InvalidParameterException e) {
			log.error("provideIndividualDataByIdentifier ERROR", e);
			businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_932, e.getMessage());
		}
		// ERROR 905 : TECHNICAL ERROR
		catch (JrafDomainException e) {
			log.error("provideIndividualDataByIdentifier ERROR", e);
			businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
		}

		return response;
	}

	protected void whiteningPostalAddressContent(ProvideIndividualInformationRequest request, ProvideIndividualInformationResponse response) throws JrafDomainException {
		if(request.getRequestor() != null && response.getPostalAddressResponse() != null && !response.getPostalAddressResponse().isEmpty()) {
			// we search channels to check in Data Base
			List<ChannelToCheckDTO> channelToCheckDTOList = channelToCheckDS.findAll();
			Boolean isChannelToCheck = channelToCheckDTOContains(request.getRequestor().getChannel(), channelToCheckDTOList);
			if(isChannelToCheck) {
				for(PostalAddressResponse postalAddressResponseLoop : response.getPostalAddressResponse()) {
					if(postalAddressResponseLoop.getPostalAddressContent() != null) {
						if(isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getAdditionalInformation(), NOT_COLLECTED)) {
							postalAddressResponseLoop.getPostalAddressContent().setAdditionalInformation(null);
						}
						if(isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getNumberAndStreet(), NOT_COLLECTED)) {
							postalAddressResponseLoop.getPostalAddressContent().setNumberAndStreet(null);
						}
						if(isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getDistrict(), NOT_COLLECTED)) {
							postalAddressResponseLoop.getPostalAddressContent().setDistrict(null);
						}
						if(isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getCity(), NOT_COLLECTED)) {
							postalAddressResponseLoop.getPostalAddressContent().setCity(null);
							if(isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getZipCode(), POSTAL_CODE_9999)) {
								postalAddressResponseLoop.getPostalAddressContent().setZipCode(null);
							}
						}
					}
				}
			}
		}
	}

	private boolean isEqualStrings(String stringToCompare, String string) {
		if(stringToCompare != null && stringToCompare.trim().equals(string)) {
			return true;
		}
		return false;
	}

	private Boolean channelToCheckDTOContains(String channel, List<ChannelToCheckDTO> channelToCheckDTOList) {
		if(channel != null && channelToCheckDTOList != null && !channelToCheckDTOList.isEmpty()) {
			for(ChannelToCheckDTO channelToCheckDTOLoop : channelToCheckDTOList) {
				if(channel.trim().equals(channelToCheckDTOLoop.getChannel().trim())){
					return true;
				}
			}
		}
		return false;
	}

	// REPIND-555 : On recupere les données en passant par les Individus avec populationTargeted W
/*	@Deprecated
	private ProspectDTO getProspectDTO(IndividualInformationRequestDTO requestDTO) throws BusinessErrorBlocBusinessException {
		ProspectDTO searchProspect = new ProspectDTO();
		List<ProspectDTO> findProspect = null;
		if("GIN".equals(requestDTO.getOption())){
			try {
				if(SicUtf8StringUtils.isValidGIN(requestDTO.getIdentificationNumber())) {
					searchProspect.setGin(Long.valueOf(requestDTO.getIdentificationNumber()));
					findProspect = prospectDS.findByExample(searchProspect);
				} else{
					businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_932, "INVALID IDENTIFICATION NUMBER");
				}
			} catch (JrafApplicativeException e) {
				log.error("provideIndividualDataByIdentifier ERROR", e);
				businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
			} catch (JrafDomainException e) {
				log.error("provideIndividualDataByIdentifier ERROR", e);
				businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
			}
		} else {
			log.error("Parameter Exception : Option need to be a GIN");
			businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_001, "NO MATCH FOUND");
		}

		if(findProspect!=null && !findProspect.isEmpty()) {
			// severals results -> take first and log a warning
			if(findProspect.size()>1){
				log.warn("Prospect search returned several results: "+searchProspect.getGin()+" | "+searchProspect.getEmail());
			}
			return findProspect.get(0);
		} else {
			log.error("provideIndividualDataByIdentifier ERROR : NOT FOUND");
			businessExceptionHelperV5.throwBusinessException(BusinessErrorCodeEnum.ERROR_001, "NO MATCH FOUND");
		}

		return null;
	}
*/
	protected void checkRequest(ProvideIndividualInformationRequest request) throws MissingParameterException {
		
		if(StringUtils.isEmpty(request.getIdentificationNumber())) {
			throw new MissingParameterException("Identification number is mandatory");
		}
	
		if(StringUtils.isEmpty(request.getOption())) {
			throw new MissingParameterException("Option is mandatory");
		}
		
	}
	
	protected void checkResponse(ProvideIndividualInformationResponse response) throws NotFoundException {
		
		if(response==null || response.getIndividualResponse()==null || response.getIndividualResponse().getIndividualInformations()==null) {
			throw new NotFoundException("Unable to find individual");
		}
		
	}
	
	private String getScope(ProvideIndividualInformationRequest request) {
		
		if(request==null) {
			return null;
		}
		Requestor requestor = request.getRequestor();
		
		if(requestor==null) {
			return null;
		}
		
		return requestor.getScope();
		
	}
	
	private boolean isScopesToProvideRequested(Set<ScopesToProvideEnum> scopesToProvideSet) {
		if(scopesToProvideSet != null && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.POSTAL_ADDRESS) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.EMAIL) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.CONTRACT) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.ACCOUNT) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.SOCIAL_NETWORK) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.TELECOM) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.DELEGATION) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.PREFILLED_NUMBER) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.EXTERNAL_IDENTIFIER) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.COMMUNICATIION_PREFERENCE) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.LOCALIZATION) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.PREFERENCE) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.MARKETING))) {
			return true;
		}
		return false;
	}
	
	private void activateIndividualScopes(Set<ScopesToProvideEnum> scopesToProvideSet) {
		if(scopesToProvideSet != null && scopesToProvideSet.contains(ScopesToProvideEnum.INDIVIDUAL)) {
			scopesToProvideSet.add(ScopesToProvideEnum.IDENTIFICATION);
			scopesToProvideSet.add(ScopesToProvideEnum.POSTAL_ADDRESS);
			scopesToProvideSet.add(ScopesToProvideEnum.EMAIL);
			scopesToProvideSet.add(ScopesToProvideEnum.CONTRACT);
			scopesToProvideSet.add(ScopesToProvideEnum.ACCOUNT);
			scopesToProvideSet.add(ScopesToProvideEnum.SOCIAL_NETWORK);
			scopesToProvideSet.add(ScopesToProvideEnum.TELECOM);
			scopesToProvideSet.add(ScopesToProvideEnum.DELEGATION);
			scopesToProvideSet.add(ScopesToProvideEnum.PREFILLED_NUMBER);
			scopesToProvideSet.add(ScopesToProvideEnum.EXTERNAL_IDENTIFIER);
			scopesToProvideSet.add(ScopesToProvideEnum.COMMUNICATIION_PREFERENCE);
			scopesToProvideSet.add(ScopesToProvideEnum.LOCALIZATION);
			scopesToProvideSet.add(ScopesToProvideEnum.PREFERENCE);
			scopesToProvideSet.add(ScopesToProvideEnum.MARKETING);
		}
	}
	
	public PaymentPreferencesDataHelper getPaymentPreferencesDataHelperV5() {
		return paymentPreferencesDataHelperV5;
	}


	public void setPaymentPreferencesDataHelperV5(PaymentPreferencesDataHelper paymentPreferencesDataHelperV5) {
		this.paymentPreferencesDataHelperV5 = paymentPreferencesDataHelperV5;
	}

	// REPIND-1441 : Trace who are consuming us and for what 
	private void TraceInput(ProvideIndividualInformationRequest request) {
		
		String retour = "W000418V5; ";

		// CONSUMER ID
		retour += SicStringUtils.TraceInputConsumer(context);

		// SITE + SIGNATURE + APPLICATION CODE
		if (request != null) {
			
			if (request.getRequestor() != null) {
				
				retour += SicStringUtils.TraceInputRequestor(
						request.getRequestor().getChannel(), request.getRequestor().getMatricule(), request.getRequestor().getOfficeId(),
						request.getRequestor().getSite(), request.getRequestor().getSignature(), request.getRequestor().getApplicationCode());
			}
			
			// SCOPE TO PROVIDE
			retour += SicStringUtils.TraceInputScopeToProvide(request.getScopeToProvide());
			
			// INPUT
			retour += SicStringUtils.TraceInputOptionIdentificationNumber(request.getOption(), request.getIdentificationNumber());
		}
		
		log.info(retour);
	}
}
