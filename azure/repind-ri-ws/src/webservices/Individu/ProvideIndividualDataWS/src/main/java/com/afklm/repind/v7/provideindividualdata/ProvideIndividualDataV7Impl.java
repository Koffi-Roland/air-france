package com.afklm.repind.v7.provideindividualdata;

import com.afklm.repind.v7.provideindividualdata.helpers.*;
import com.afklm.repind.v7.provideindividualdata.transformers.ProvideIndividualDataTransformV7;
import com.afklm.repind.v7.provideindividualdata.type.MaskedPaymentPreferences;
import com.afklm.repind.v7.provideindividualdata.type.PopulationTargetedEnum;
import com.afklm.repind.v7.provideindividualdata.type.ScopesToProvideEnum;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000418.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000418.v7.ProvideIndividualDataServiceV7;
import com.afklm.soa.stubs.w000418.v7.data.ProvideIndividualInformationRequest;
import com.afklm.soa.stubs.w000418.v7.data.ProvideIndividualInformationResponse;
import com.afklm.soa.stubs.w000418.v7.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000418.v7.response.DelegationDataResponse;
import com.afklm.soa.stubs.w000418.v7.response.PostalAddressResponse;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.util.LoggerUtils;
import com.airfrance.ref.type.BussinessRoleTypeEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.channel.ChannelToCheckDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.adh.individualinformation.IndividualInformationRequestDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.role.RoleTravelersDTO;
import com.airfrance.repind.dto.role.RoleUCCRDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.channel.internal.ChannelToCheckDS;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.*;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.service.role.internal.RoleTravelersDS;
import com.airfrance.repind.service.role.internal.RoleUCCRDS;
import com.airfrance.repind.util.GaugeUtils;
import com.airfrance.repind.util.SicStringUtils;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * <p>Title : ProvideIndividualDataImpl.java</p>
 *
 * <p>Copyright : Copyright (c) 2015</p>
 * <p>Company : AIRFRANCE</p>
 *
 * @author T412211
 */
@Service("passenger_ProvideIndividualData-v07Bean")
@WebService(endpointInterface="com.afklm.soa.stubs.w000418.v7.ProvideIndividualDataServiceV7", targetNamespace = "http://www.af-klm.com/services/passenger/ProvideIndividualDataService-v7/wsdl", serviceName = "ProvideIndividualDataServiceService-v7", portName = "ProvideIndividualDataService-v7-soap11http")
@Slf4j
public class ProvideIndividualDataV7Impl implements ProvideIndividualDataServiceV7 {

	@Autowired
	protected MyAccountDS myAccountDS;

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
	protected RoleUCCRDS roleUCCR;

	@Autowired
	protected RoleTravelersDS roleTravelersDS;

	@Autowired
	protected EmailDS emailDS;

	@Autowired
	protected PostalAddressDS postalAddressDS;

	@Autowired
	protected ChannelToCheckDS channelToCheckDS;

	@Autowired
	protected PreferenceDS preferenceDS;

	@Autowired
	protected AlertDS alertDS;
	
	@Autowired
	protected IndividuDS individuDS;
	
	@Autowired 
	protected VariablesDS variablesDS;

	@Autowired
	protected BusinessRoleDS businessRoleDS;

	@Autowired
	protected BusinessExceptionHelper businessExceptionHelperV5;

	@Autowired
	protected PreferenceHelper preferenceHelperV7;

	@Autowired
	protected PaymentPreferencesDataHelper paymentPreferencesDataHelperV5;

	@Autowired
	private LoggerUtils loggerUtils;

	@Qualifier("UtfHelper-ProvideV7")
	@Autowired
	private UtfHelper utfHelper;

	@Autowired
	private ForgetStatusHelper forgetStatusHelperV7;

	public static final String NOT_COLLECTED = "NOT COLLECTED";
	public static final String POSTAL_CODE_9999 = "99999";

	private static final String GDPR_CONTEXT = "FORGET_STATUS";

	@Resource
	protected WebServiceContext context;

	@Override
	public ProvideIndividualInformationResponse provideIndividualDataByIdentifier(ProvideIndividualInformationRequest request) throws BusinessErrorBlocBusinessException, SystemException {

		UUID uuid = UUID.randomUUID();
		String uuidAsString = uuid.toString();
		String providerStopWatchName = uuidAsString + "PIDv7";

		traceInput(request);

		ProvideIndividualInformationResponse response = null;
		boolean isFBRecognitionActivate = false;

		try {

			String gin;
			IndividuDTO individuDTO;
			AccountDataDTO accountDataDTO = null;
			List<TelecomsDTO> telecomsDTOList = null;
			boolean isIndividu = true;
			boolean isProspect = false;

			boolean haveCommunicationPreferences;
			boolean haveRoleTravelers;
			boolean haveExternalIdentifier;

			checkRequest(request);

			IndividualInformationRequestDTO requestDTO = new IndividualInformationRequestDTO(request.getIdentificationNumber(), request.getOption());

			// Process for GDPR retrieve status
			// REPIND-1546 : NPE on SONAR
			if (request != null && request.getRequestor() != null && request.getRequestor().getContext() != null) {
				if (GDPR_CONTEXT.equals(request.getRequestor().getContext())) {
					String provideIndividualForgetStatusStopWatchName = uuidAsString + "ProvideIndividualForgetStatus";

					response = forgetStatusHelperV7.provideIndividualForgetStatus(requestDTO);

					return response;
				}
			}

			// get scopeToProvide
			Set<ScopesToProvideEnum> scopesToProvideSet = ScopesToProvideEnum.getEnumSet(request.getScopeToProvide());

			// avtivate scopes for Individual if requested
			activateIndividualScopes(scopesToProvideSet);

			int percentageMarketingData = 0;
			int percentagePaymentPreferences = 0;

			/* ==================================================================================== *
			 * INDIVIDUAL DATA																		*
			 * ==================================================================================== */
			// Search an Individual in SIC
			String searchIndividualInformationAllStopWatchName = uuidAsString + "searchIndividualInformationAll";

			individuDTO = myAccountDS.searchIndividualInformationAll(requestDTO);

			// test for forgetMe and crisis event
			if (individuDS.isStatutNotReturned(individuDTO))
				throw new NotFoundException("");

			response = ProvideIndividualDataTransformV7.transformToProvideIndividualInformationResponse(individuDTO, scopesToProvideSet, isIndividu);

			checkResponse(response);

			// get gin
			gin = individuDTO.getSgin();

			if (individuDTO.getType() != null) {
				isProspect = individuDTO.getType().equalsIgnoreCase("W");
			}

			////////////////////////////////////////////////////////////////
			// We have to get this attributes to determine populationType //
			////////////////////////////////////////////////////////////////


			// Traveler
			String findRoleTravelersStopWatchName = uuidAsString + "findRoleTravelers";

			List<RoleTravelersDTO> roleTravelersDTOList = roleTravelersDS.findRoleTravelers(gin);

			haveRoleTravelers = roleTravelersDTOList != null && !roleTravelersDTOList.isEmpty();

			// Communication Preferences
			String findCommunicationPreferencesStopWatchName = uuidAsString + "findCommunicationPreferences";

			List<CommunicationPreferencesDTO> communicationPreferencesDTOList = communicationPreferencesDS.findCommunicationPreferences(gin);

			if (individuDTO.getCommunicationpreferencesdto() != null) {
				communicationPreferencesDTOList = new ArrayList<>(individuDTO.getCommunicationpreferencesdto());
			}
			haveCommunicationPreferences = communicationPreferencesDTOList != null && !communicationPreferencesDTOList.isEmpty();

			// External Identifier : Request PNM_ID and GIGYA or FACEBOOK or TWITTER
			String findExternalIdentifierALLStopWatchName = uuidAsString + "findExternalIdentifierALL";

			List<ExternalIdentifierDTO> externalIdentifierDTOList = externalIdentifierDS.findExternalIdentifierALL(gin);

			haveExternalIdentifier = externalIdentifierDTOList != null && !externalIdentifierDTOList.isEmpty();

			////////////////////////////////////////////////////////////////


			if (isScopesToProvideRequested(scopesToProvideSet)) {
				// add postal address
				if (isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.POSTAL_ADDRESS))) {

					String findPostalAddressStopWatchName = uuidAsString + "findPostalAddress";

					List<PostalAddressDTO> postalAddressDTOList = postalAddressDS.findPostalAddress(gin);

					ProvideIndividualDataTransformV7.transformToPostalAddressResponse(postalAddressDTOList, response.getPostalAddressResponse());

					String whiteningPostalAddressContentStopWatchName = uuidAsString + "WhiteningPostalAddressContent";

					whiteningPostalAddressContent(request, response);

				}

				// add preference data
				if (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.PREFERENCE) || scopesToProvideSet.contains(ScopesToProvideEnum.MARKETING)) {
					PreferenceDTO preferenceDTO = new PreferenceDTO();
					preferenceDTO.setGin(gin);

					String preferenceDSfindByExampleStopWatchName = uuidAsString + "preferenceDSFindByExample";

					List<PreferenceDTO> preferenceDTOList = preferenceDS.findByExample(preferenceDTO);

					preferenceDTOList = preferenceHelperV7.cleanNonPrintableChars(preferenceDTOList);

					// Normalize all preference data key
					List<PreferenceDTO> normalizedPrefDtoList = new ArrayList<>();
					if (preferenceDTOList != null) {
						for (PreferenceDTO prefDtoFromDB : preferenceDTOList) {

							String normalizePreferenceDataKeyStopWatchName = uuidAsString + "NormalizePreferenceDataKey";

							PreferenceDTO normalizedPrefDto = preferenceHelperV7.normalizePreferenceDataKey(prefDtoFromDB);

							normalizedPrefDtoList.add(normalizedPrefDto);
						}
					}


					if (isProspect) {

						// TODO
						ProvideIndividualDataTransformV7.transformProspectPreferredAirportToPreferenceResponse(normalizedPrefDtoList, response);
					} else {

						//TODO
						ProvideIndividualDataTransformV7.transformPreferenceDTOListToPreferenceResponse(normalizedPrefDtoList, response);
					}

				}
				// add mails
				if(scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.EMAIL)) {
					List<EmailDTO> emailDTOList = null;
					if(isIndividu) {

						String findEmailStopWatchName = uuidAsString + "FindEmail";

						emailDTOList = emailDS.findEmail(gin);

					} else if(individuDTO.getEmaildto() != null) {
						emailDTOList = new ArrayList<>(individuDTO.getEmaildto());
					}
					ProvideIndividualDataTransformV7.transformToEmailResponse(emailDTOList, response.getEmailResponse());
				}
				// add role contracts
				if(isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.CONTRACT))) {

					String findRoleContratsStopWatchName = uuidAsString + "FindRoleContrats";

					List<RoleContratsDTO> roleContratsDTOList = roleDS.findRoleContrats(gin, isFBRecognitionActivate);

					ProvideIndividualDataTransformV7.transformToContractResponse(roleContratsDTOList, response.getContractResponse(), isFBRecognitionActivate);

					// add Doctor REPIND-2077
					String getDoctorRoleByGinIndStopWatchName = uuidAsString + "getDoctorRoleByGinInd";

					List<BusinessRoleDTO> businessRoleDTOList = businessRoleDS.getDoctorRoleByGinInd(gin);

					if (businessRoleDTOList != null && !businessRoleDTOList.isEmpty()) {
						ProvideIndividualDataTransformV7.transformToDoctorResponse(businessRoleDTOList, response.getContractResponse());
					}

				}

				// add UCCR contracts
				if(isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.UCCR_CONTRACT))) {

					String findByGinAndTypeStopWatchName = uuidAsString + "findByGinAndType";

					List<BusinessRoleDTO> businessRoleUccrList = businessRoleDS.findByGinAndType(gin, BussinessRoleTypeEnum.ROLE_UCCR.code());

					if (CollectionUtils.isNotEmpty(businessRoleUccrList)) {

						String getRoleUccrFromBusinessRoleStopWatchName = uuidAsString + "getRoleUccrFromBusinessRole";

						List<RoleUCCRDTO> roleUCCRDTOList = roleUCCR.getRoleUccrFromBusinessRole(businessRoleUccrList);

						ProvideIndividualDataTransformV7.transformRoleUCCRToContractResponse(roleUCCRDTOList, response.getContractResponse());
					}
				}
				// add account data
				if(isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.ACCOUNT))) {

					String getOnlyAccountByGinStopWatchName = uuidAsString + "getOnlyAccountByGin";

					accountDataDTO = accountDataDS.getOnlyAccountByGin(gin);

					response.setAccountDataResponse(ProvideIndividualDataTransformV7.transformToAccountDataResponse(accountDataDTO));
				}
				// add social data
				// Do we need to query this fields (Scope or Target)
				if(isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) ||
						scopesToProvideSet.contains(ScopesToProvideEnum.SOCIAL_NETWORK) ||
						PopulationTargetedEnum.E.toString().equals(requestDTO.getPopulationTargeted()))) {
				}
				// add telecom data with usage code management
				if(scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.TELECOM)) {
					if(isIndividu) {

						String findLatestByUsageCodeStopWatchName = uuidAsString + "findLatestByUsageCode";

						telecomsDTOList = telecomDS.findLatestByUsageCode(gin);

					} else if(individuDTO.getTelecoms() != null) {
						telecomsDTOList = new ArrayList<>(individuDTO.getTelecoms());
					}
					ProvideIndividualDataTransformV7.transformToTelecomResponse(telecomsDTOList, response.getTelecomResponse());
				}
				// add delegation data
				if(isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.DELEGATION))) {

					String findDelegatorStopWatchName = uuidAsString + "findDelegator";

					List<DelegationDataDTO> delegatorList = delegationDataDS.findDelegator(gin);
					List<DelegationDataDTO> delegateList = delegationDataDS.findDelegate(gin);

					DelegationDataResponse delegationDataResponse = ProvideIndividualDataTransformV7.dtoTOws(delegatorList, delegateList);
					response.setDelegationDataResponse(delegationDataResponse);
				}
				// add prefilled numbers
				if(isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.PREFILLED_NUMBER))) {

					String findPrefilledNumbersStopWatchName = uuidAsString + "findPrefilledNumbers";

					List<PrefilledNumbersDTO> prefilledNumbersDTOList = prefilledNumbersDS.findPrefilledNumbers(gin);

					ProvideIndividualDataTransformV7.transformToPrefilledNumbers(prefilledNumbersDTOList, response.getPrefilledNumbersResponse());
				}
				// add external identifiers
				if (isIndividu && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) ||
						scopesToProvideSet.contains(ScopesToProvideEnum.EXTERNAL_IDENTIFIER) ||
						PopulationTargetedEnum.E.toString().equals(requestDTO.getPopulationTargeted()))
						&& (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) ||
						scopesToProvideSet.contains(ScopesToProvideEnum.EXTERNAL_IDENTIFIER))) {
					// Do we need to return the result
					ProvideIndividualDataTransformV7.transformToExternalIdentifierResponse(externalIdentifierDTOList, response.getExternalIdentifierResponse());
				}
				// add communication preferences
				if(scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.COMMUNICATIION_PREFERENCE)) {
					ProvideIndividualDataTransformV7.transformToCommunicationPreferencesResponse(communicationPreferencesDTOList, response.getCommunicationPreferencesResponse());
				}

				// add traveler data
				// Do we need to query this fields (Scope or Target)
				if((scopesToProvideSet.contains(ScopesToProvideEnum.ALL) ||
						scopesToProvideSet.contains(ScopesToProvideEnum.TRAVELER)) &&
						("A".equals(requestDTO.getPopulationTargeted()) ||
								(PopulationTargetedEnum.T.toString()).equals(requestDTO.getPopulationTargeted()))) {
					// Do we need to return the result
					ProvideIndividualDataTransformV7.transformToTravelerResponse(roleTravelersDTOList, response.getContractResponse());
				}

				// add alert data informations
				if (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) || scopesToProvideSet.contains(ScopesToProvideEnum.ALERT)) {
					AlertDTO search = new AlertDTO();
					search.setSgin(gin);
					search.setOptIn("Y");

					String alertDSfindByExampleStopWatchName = uuidAsString + "alertDSfindByExample";

					List<AlertDTO> alertDTOList = alertDS.findByExample(search);

					ProvideIndividualDataTransformV7.transformToAlert(alertDTOList, response);
				}

				// add warnings
				String checkValidAccountStopWatchName = uuidAsString + "checkValidAccount";

				Set<WarningDTO> warningDTOSet = myAccountDS.checkValidAccount(gin);

				ProvideIndividualDataTransformV7.transformToWarningResponse(warningDTOSet, response.getWarningResponse());
			}

			// REPIND-1271
			// Limit for Preferences is 100, if more raised an error
			int maxOutputPreferences = 100;
			if (response.getPreferenceResponse() != null && response.getPreferenceResponse().getPreference() != null && response.getPreferenceResponse().getPreference().size() > maxOutputPreferences) {
				throw new JrafDomainException("Too many preferences to provide");
			}

			/* ======================================================================
			 * UTF8 STRINGS
			 * ======================================================================
			 */
			//REPIND-1230: In case of exception, we catch it in order to provide a valid answer
			try {
				response.setUtfResponse(utfHelper.getByGin(gin));
			} catch (JrafDomainException | UtfException e) {
				log.warn("Unable to get Utf Data for the GIN: {}",gin);
			}


			if (response != null && response.getIndividualResponse() != null && response.getIndividualResponse().getIndividualInformations() != null )  {

				String populationType = response.getIndividualResponse().getIndividualInformations().getPopulationType();

				// We search if individual has attributes
				if (haveRoleTravelers && !populationType.contains("T")) {
					populationType += "T";
				}
				if (haveExternalIdentifier && !populationType.contains("E")) {
					populationType += "E";
				}
				if (haveCommunicationPreferences && !populationType.contains("W")) {
					populationType += "W";
				}

				if ("".equals(populationType)) {
					populationType = "I";
				}

				response.getIndividualResponse().getIndividualInformations().setPopulationType(populationType);
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
		catch (InvalidParameterException e) {
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
						if(isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getAdditionalInformation(), ProvideIndividualDataV7Impl.NOT_COLLECTED)) {
							postalAddressResponseLoop.getPostalAddressContent().setAdditionalInformation(null);
						}
						if(isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getNumberAndStreet(), ProvideIndividualDataV7Impl.NOT_COLLECTED)) {
							postalAddressResponseLoop.getPostalAddressContent().setNumberAndStreet(null);
						}
						if(isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getDistrict(), ProvideIndividualDataV7Impl.NOT_COLLECTED)) {
							postalAddressResponseLoop.getPostalAddressContent().setDistrict(null);
						}
						if(isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getCity(), ProvideIndividualDataV7Impl.NOT_COLLECTED)) {
							postalAddressResponseLoop.getPostalAddressContent().setCity(null);
							if(isEqualStrings(postalAddressResponseLoop.getPostalAddressContent().getZipCode(), ProvideIndividualDataV7Impl.POSTAL_CODE_9999)) {
								postalAddressResponseLoop.getPostalAddressContent().setZipCode(null);
							}
						}
					}
				}
			}
		}
	}

	private boolean isEqualStrings(String stringToCompare, String string) {
		return stringToCompare != null && stringToCompare.trim().equals(string);
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

	protected void checkRequest(ProvideIndividualInformationRequest request) throws MissingParameterException {

		if(StringUtils.isEmpty(request.getIdentificationNumber())) {
			throw new MissingParameterException("Identification number is mandatory");
		}

		if (StringUtils.isEmpty(request.getOption())) {
			throw new MissingParameterException("Option is mandatory");
		} else if ("GIN".equalsIgnoreCase(request.getOption()) && (StringUtils.isNotBlank(request.getIdentificationNumber()) && request.getIdentificationNumber().length() < 12)) {
			// REPIND-1019 : must return individual even if GIN is not 12 char long
			request.setIdentificationNumber(StringUtils.leftPad(request.getIdentificationNumber(), 12, "0"));
		}

	}

	protected void checkResponse(ProvideIndividualInformationResponse response) throws NotFoundException {

		if(response==null || response.getIndividualResponse()==null || response.getIndividualResponse().getIndividualInformations()==null) {
			throw new NotFoundException("Unable to find individual");
		}

	}

	private boolean isScopesToProvideRequested(Set<ScopesToProvideEnum> scopesToProvideSet) {
		return scopesToProvideSet != null && (scopesToProvideSet.contains(ScopesToProvideEnum.ALL) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.IDENTIFICATION) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.POSTAL_ADDRESS) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.EMAIL) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.CONTRACT) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.ACCOUNT) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.TRAVELER) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.SOCIAL_NETWORK) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.TELECOM) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.DELEGATION) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.PREFILLED_NUMBER) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.EXTERNAL_IDENTIFIER) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.COMMUNICATIION_PREFERENCE) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.LOCALIZATION) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.PREFERENCE) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.MARKETING) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.UCCR_CONTRACT) ||
				scopesToProvideSet.contains(ScopesToProvideEnum.ALERT));
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
			scopesToProvideSet.add(ScopesToProvideEnum.UCCR_CONTRACT);
			scopesToProvideSet.add(ScopesToProvideEnum.ALERT);
		}
	}
	
	public PaymentPreferencesDataHelper getPaymentPreferencesDataHelperV5() {
		return paymentPreferencesDataHelperV5;
	}

	public void setPaymentPreferencesDataHelperV5(PaymentPreferencesDataHelper paymentPreferencesDataHelperV5) {
		this.paymentPreferencesDataHelperV5 = paymentPreferencesDataHelperV5;
	}
	
	public UtfHelper getUtfHelper() {
		return utfHelper;
	}

	public void setUtfHelper(UtfHelper utfHelper) {
		this.utfHelper = utfHelper;
	}

	// REPIND-1441 : Trace who are consuming us and for what 
	private void traceInput(ProvideIndividualInformationRequest request) {

		String retour = "W000418V7; ";

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
