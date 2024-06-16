package com.afklm.repind.v6.createorupdateindividualws;

import com.afklm.repind.utils.CreateOrUpdateIndividualMapperV6;
import com.afklm.repind.v6.createorupdateindividualws.helpers.AddressNormalizationHelper;
import com.afklm.repind.v6.createorupdateindividualws.helpers.BusinessExceptionHelper;
import com.afklm.repind.v6.createorupdateindividualws.helpers.CommunicationPreferencesHelper;
import com.afklm.repind.v6.createorupdateindividualws.helpers.PrefilledNumbersHelper;
import com.afklm.repind.v6.createorupdateindividualws.transformers.IndividuRequestTransformV6;
import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w000413.v2.StoreMarketingPreferencesCustomerV2;
import com.afklm.soa.stubs.w000442.v6.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v6.CreateUpdateIndividualServiceV6;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v6.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v6.request.PostalAddressRequest;
import com.afklm.soa.stubs.w000442.v6.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.Civilian;
import com.afklm.soa.stubs.w000442.v6.sicindividutype.IndividualInformations;
import com.afklm.soa.stubs.w000442.v6.sicmarketingtype.MaccTravelCompanion;
import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.contract.SaphirContractNotFoundException;
import com.airfrance.ref.exception.email.SharedEmailException;
import com.airfrance.ref.exception.external.*;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.ref.type.NATFieldsEnum;
import com.airfrance.ref.type.UpdateModeEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.PrefilledNumbersDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.individu.myaccountcustomerdata.ReturnDetailsDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.refTable.RefTableREF_CIVILITE;
import com.airfrance.repind.entity.refTable.RefTableREF_CODE_TITRE;
import com.airfrance.repind.service.adresse.internal.AdresseDS;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.consent.internal.ConsentDS;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.ws.internal.helpers.CreateOrUpdateAnIndividualHelper;
import com.airfrance.repind.service.ws.internal.helpers.PostalAddressHelper;
import com.airfrance.repind.util.LoggerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@DependsOn("consumerW000413V02")
@Service("passenger_CreateUpdateIndividualService-v6Bean")
@WebService(endpointInterface="com.afklm.soa.stubs.w000442.v6.CreateUpdateIndividualServiceV6", targetNamespace = "http://www.af-klm.com/services/passenger/CreateUpdateIndividualService-v6/wsdl", serviceName = "CreateUpdateIndividualServiceService-v6", portName = "CreateUpdateIndividualService-v6-soap11http")
@Slf4j
public class CreateOrUpdateIndividualImplV6 implements CreateUpdateIndividualServiceV6  {

	@Autowired
	protected IndividuDS individuDS;
	
	@Autowired
	protected MyAccountDS accountDS;
	
	@Autowired
	protected AccountDataDS accountDataDS;
	
	@Autowired
	protected TelecomDS telecomDS;
	
	@Autowired
	protected AdresseDS adresseDS;
	
	@Autowired
	protected EmailDS emailDS;
	
	@Autowired
	protected ExternalIdentifierDS externalIdentifierDS;
	
	@Autowired
	protected DelegationDataDS delegationDataDS;
	
	@Autowired
	private ConsentDS consentDS;
		
	@Autowired
	protected BusinessExceptionHelper businessExceptionHelperV6;
	
	@Autowired
	protected AddressNormalizationHelper addressNormalizationHelperV6;
	
	@Autowired
	protected PrefilledNumbersHelper prefilledNumbersHelper;
	
	@Autowired
	protected CommunicationPreferencesHelper communicationPreferencesHelper;
	
	@Autowired
	@Qualifier("consumerW000413V02")
	private StoreMarketingPreferencesCustomerV2 storeMarketingPreferenceCustomer;
	
	@Autowired
	protected PostalAddressHelper postalAddressHelper;

	@Autowired
	protected CreateOrUpdateAnIndividualHelper createOrUpdateAnIndividualHelper;
	
	@Autowired
	protected CreateOrUpdateIndividualMapperV6 createOrUpdateIndividualMapperV6;
	
	private final String WEBSERVICE_IDENTIFIER = "W000442";
	private final String SITE = "VLB";
	
	public CreateUpdateIndividualResponse createIndividual(CreateUpdateIndividualRequest request) throws BusinessErrorBlocBusinessException, SystemException {
		
		log.info("##### START CreateOrUpdateAnIndividual-V6 ###############################");
	
		CreateUpdateIndividualResponse response = new CreateUpdateIndividualResponse();
		response.setSuccess(true);			
		
		try {

			// 1. CHECKING INPUTS => EITHER CREATION OR UPDATE 
			checkInput(request);	
			
			// CHECK + NORMALIZE POSTAL ADDRESS 
			/*
			response = normalizePostalAddress(request);
			
			// NORMALIZATION FAILED -> RETUNING DIRECTLY SOFT COMPUTING RESPONSE + SUCCESS SET TO FALSE 
			if (!response.isSuccess()) {
				LOGGER.error("Postal address normalisation failed");
				return response;				
			}
			
			*/
		
			SignatureDTO signatureAPP = IndividuRequestTransformV6.transformToSignatureAPP(request.getRequestor());
			SignatureDTO signatureWS = IndividuRequestTransformV6.transformToSignatureWS(WEBSERVICE_IDENTIFIER, SITE);
			boolean isCreated = false;
			
			// NORMALIZE TELECOM
			List<TelecomsDTO> telecomFromWSList = normalizeTelecoms(request);
			
			// GETTING GIN FROM INPUT -> IF DEFINED IT IS AN UPDATE, ELSE THIS IS A CREATION
			String gin = getGin(request);

			// INDIVIDUAL CREATION (GIN not filled) -> S08924
			if(StringUtils.isEmpty(gin)) {
				gin = createIndividualData(request, response);	
				isCreated = true;
			} 
			// INDIVIDUAL UPDATE (GIN filled) -> S08924
			else {
				updateIndividualData(request);
			}					
						
			// ===== TELECOM DATA ====================================================================
			
			telecomDS.updateNormalizedTelecomWithUsageCode(gin, telecomFromWSList, signatureAPP, signatureWS);
			
			// ===== EMAIL DATA  =====================================================================
			List<EmailDTO> emailListFromWS = IndividuRequestTransformV6.transformToEmailDTO(request.getEmailRequest());
			setDefaultValueEmails(emailListFromWS);
			emailDS.updateEmail(gin, emailListFromWS, signatureAPP);
			accountDataDS.updateLoginEmail(gin, emailListFromWS, signatureAPP, request.getStatus());

			// ===== POSTAL ADDRESS  =================================================================
			List<PostalAddressDTO> postalAddressList = null;
			
			if (request.getPostalAddressRequest() != null) {
				String codeAppliMetier = "";
				if (request.getRequestor() != null && request.getRequestor().getApplicationCode() != null) {
					 codeAppliMetier = request.getRequestor().getApplicationCode();
				}
				
				postalAddressList = new ArrayList<PostalAddressDTO>();
				
				for (PostalAddressRequest parDto : request.getPostalAddressRequest()) {
					postalAddressList.add(IndividuRequestTransformV6.transformToPostalAddressDTO(parDto, signatureAPP));
				}
				
				if (!postalAddressList.isEmpty() && (codeAppliMetier == null || "".equalsIgnoreCase(codeAppliMetier))) {
					codeAppliMetier = "RPD";
				}

				try {
					postalAddressHelper.createOrUpdate(gin, postalAddressList, codeAppliMetier, signatureAPP);
				}catch (AddressNormalizationException e){
					response.setSuccess(false);
					response.setGin(null); //Reset gin to return to the client
					return response; //Allow us in case of dqe normalization error to return false to the client instead of exception
				}
			}
			
			// ===== DELEGATION DATA =================================================================
			
			String managingCompany = null;
			if(request.getRequestor() != null) {
				managingCompany = request.getRequestor().getManagingCompany();
			}
			List<DelegationDataDTO> delegateList = IndividuRequestTransformV6.transformToDelegate(request.getAccountDelegationDataRequest(), gin);
			delegationDataDS.updateDelegationData(gin, delegateList, signatureAPP, managingCompany);
			
			List<DelegationDataDTO> delegatorList = IndividuRequestTransformV6.transformToDelegator(request.getAccountDelegationDataRequest(), gin);
			delegationDataDS.updateDelegationData(gin, delegatorList, signatureAPP, managingCompany);
			


			// ===== EXTERNAL IDENTIFIERS ============================================================
			
			List<ExternalIdentifierDTO> externalIdentifierListFromWS = IndividuRequestTransformV6.transformToExternalIdentifierDTO(request.getExternalIdentifierRequest());
			externalIdentifierDS.updateExternalIdentifier(gin, externalIdentifierListFromWS, signatureAPP);
			
			// ===== PREFILLED NUMBERS ===============================================================
			
			UpdateModeEnum updateModePrefilled = UpdateModeEnum.getEnum(request.getUpdatePrefilledNumbersMode());
			List<PrefilledNumbersDTO> prefilledNumbersDTO = IndividuRequestTransformV6.wsdlTOPrefilledNumbersdto(request.getPrefilledNumbersRequest());
			processPrefilledNumbers(gin, prefilledNumbersDTO, updateModePrefilled, signatureAPP);

			
			// ===== MARKETING DATA ==================================================================
			// REPIND-1225: Preferences are now stored in RI DB instead of BDM
			//callMarketingData(request);
			
			// ===== BUILDING RESPONSE ==================================================================		
			if (isCreated && consentDS.ginHasNoConsents(gin)) {
				
				//REPIND-1647: Create Default Consent when creating new individual
				try {
					consentDS.createDefaultConsents(gin, request.getRequestor().getSignature());
				} catch (Exception e) {
					log.error(LoggerUtils.buidErrorMessage(e), e);
				}
				
				response.setGin(gin);
			}
		} 
		// ERROR 001 : INDIVIDUAL NOT FOUND
		catch (NotFoundException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_001, e.getMessage());
		}
		// ERROR 003 : SIMULTANEOUS UPDATE
		catch (SimultaneousUpdateException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_003, e.getMessage());
		}
		// ERROR 133 : MISSING PARAMETER
		catch(MissingParameterException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_133, e.getMessage());
		}
		// ERROR 135 : INVALID LANGUAGE CODE
		catch(InvalidLanguageCodeException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_135, e.getMessage());
		}
		// 382 : SHARED EMAIL
		catch (SharedEmailException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_382, e.getMessage() + ": " + e.getEmail());
		}
		// 384 : ALREADY EXISTS
		catch (AlreadyExistException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_384, e.getMessage());
		}
		// ERROR 385 : ACCOUNT NOT FOUND
		catch(AccountNotFoundException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_385, e.getMessage());
		}	
		// 387 : MY ACCOUNT ONLY
		catch (OnlyMyAccountException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_387, e.getMessage());
		}
		// 538 : SAPHIR NUMBER NOT MATCHING WITH INDIVIDUAL DATAS
		catch (com.airfrance.ref.exception.contract.NotConsistentSaphirNumberException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_538, e.getMessage());
		}
		// 551 : MAXIMUM NUMBER OF SUBSCRIBED NEWLETTER SALES REACHED
		catch (MaximumSubscriptionsException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_551, e.getMessage());
		}
		// ERROR 701 : PHONE NUMBER TOO LONG
		catch (TooLongPhoneNumberException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_701, e.getMessage());
		}
		// ERROR 702 : PHONE NUMBER TOO SHORT
		catch (TooShortPhoneNumberException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_702, e.getMessage());
		}
		// ERROR 703 : INVALID PHONE NUMBER
		catch (InvalidPhoneNumberException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_703, e.getMessage());
		}
		// 706 : DELEGATION GIN NOT FOUND
		catch (DelegationGinNotFoundException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_706, e.getMessage());
		}
		// 707 : DELEGATION GINS IDENTICAL
		catch (DelegationGinsIdenticalException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_707, e.getMessage());
		}
		// 708 : DELEGATION GIN WITHOUT ACCOUNT
		catch (DelegationGinWithoutAccountException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_708, e.getMessage());
		}
		// 709 : DELEGATION STATUS NOT AUTHORIZED
		catch (DelegationActionException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_709, e.getMessage());
		}
		// 710 : DELEGATION ERROR
		catch (DelegationException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_710, e.getMessage());
		}
		// ERROR 711 : INVALID PHONE COUNTRY CODE
		catch (InvalidCountryCodeException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_711, e.getMessage());
		}
		// ERROR 705 : NO NORMALIZED
		catch (NormalizedPhoneNumberException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_705, e.getMessage());
		}	
		// ERROR 712 : SAPHIR CONTRACT NOT FOUND
		catch (SaphirContractNotFoundException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_712, e.getMessage());
		}	
		// ERROR 713 : INVALID EXTERNAL IDENTIFIER
		catch (InvalidExternalIdentifierException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_713, e.getMessage());
		}
		// ERROR 714 : INVALID EXTERNAL IDENTIFIER TYPE
		catch (InvalidExternalIdentifierTypeException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_714, e.getMessage());
		}
		// ERROR 715 : INVALID EXTERNAL IDENTIFIER DATA KEY
		catch (InvalidExternalIdentifierDataKeyException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_715, e.getMessage());
		}
		// ERROR 716 : INVALID EXTERNAL IDENTIFIER DATA VALUE
		catch (InvalidExternalIdentifierDataValueException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_716, e.getMessage());
		}
		// ERROR 717 : INVALID PNM ID
		catch (InvalidPnmIdException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_717, e.getMessage());
		}
		// ERROR 718 : MAXIMUM NUMBER OF PNM ID REACHED
		catch (MaxNbOfPnmIdReachedException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_718, e.getMessage());
		}
		// ERROR 932 : INVALID PARAMETER
		catch(InvalidParameterException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_932, e.getMessage());
		}
		// 905 : MARKETING ERROR
		catch (MarketingErrorException e) {
			log.error("Problem during updateMyAccountCustomer : ", e);
			businessExceptionHelperV6.throwMarketingBusinessException(BusinessErrorCodeEnum.ERROR_905, e);
		}
		// ERROR 905 : TECHNICAL ERROR
		catch (JrafDomainException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
		}		
		// ERROR 905 : TECHNICAL ERROR
		catch (JrafApplicativeException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
		}
		// ERROR 905 : TECHNICAL ERROR
		catch (Exception e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			businessExceptionHelperV6.throwBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
		}
		
			log.info("##### END CreateOrUpdateAnIndividual-V6 #################################");
		
		return response;
	}
	
	

	private void setDefaultValueEmails(List<EmailDTO> emailListFromWS) {
		if(emailListFromWS == null || emailListFromWS.isEmpty()) {return;}
		for (EmailDTO emailFromWS : emailListFromWS) {	
			// Set default value for NAT
			if (StringUtils.isEmpty(emailFromWS.getAutorisationMailing())) {
				emailFromWS.setAutorisationMailing(NATFieldsEnum.NONE.getValue());
			}
			if(StringUtils.isEmpty(emailFromWS.getStatutMedium())) {
				emailFromWS.setStatutMedium(MediumStatusEnum.VALID.toString());
			}
		}			
	}

	/**
	 * CHECKING INPUT : checking Individual Request information 
	 * 
	 * @param request
	 * @throws MissingParameterException
	 */
	protected void checkInput(CreateUpdateIndividualRequest request) throws MissingParameterException, InvalidParameterException {
		
		if(request==null || request.getIndividualRequest()==null || request.getIndividualRequest().getIndividualInformations()==null) {
			throw new MissingParameterException("The individual informations are mandatory");
		}

		// CHECKING IF SPECIAL CHARACTERS IN TRAVEL COMPANIONS DATA
		if (request.getMarketingDataRequest() != null &&
				request.getMarketingDataRequest().getMarketingInformation() != null &&
				request.getMarketingDataRequest().getMarketingInformation().getTravelCompanion() != null) {
			checkTravelCompanionCharacters(request);
		}
		
		IndividualInformations individualInformations = request.getIndividualRequest().getIndividualInformations();
		
		// IF IDENTIFIER IS FILLED IN => OK
		if(StringUtils.isNotEmpty(individualInformations.getIdentifier())) {
			return;
		}
		
		
		
		// REPIND-1671 : Check the Constraint : scivilite in ('M.','MISS','MR','MRS','MS') 
		if (!StringUtils.isEmpty(individualInformations.getCivility())){							// Check if CIVILITY have been filled

			individualInformations.setCivility(individualInformations.getCivility().trim().toUpperCase());			
			
			RefTableREF_CIVILITE iRefTableREF_CIVILITE = RefTableREF_CIVILITE.instance();
			if (! iRefTableREF_CIVILITE.estValide(individualInformations.getCivility(), "")) {		// Check if this value is correct or not
				
				log.warn("The field civility not valid '" + individualInformations.getCivility() + "'");
				throw new MissingParameterException("The field civility not valid");				// We must raised an error because of ORACLE violated constraint
			}
		}		
		
		// REPIND-1671 : Check the Constraint : scode_title in REF_CODE_TITRE
		Civilian civilian = request.getIndividualRequest().getCivilian();
		if (civilian != null && !StringUtils.isEmpty(civilian.getTitleCode())){		
			
			civilian.setTitleCode(civilian.getTitleCode().trim().toUpperCase());
			request.getIndividualRequest().setCivilian(civilian);
			
			// Check if TITLE have been filled
			RefTableREF_CODE_TITRE iRefTableREF_CODE_TITRE = RefTableREF_CODE_TITRE.instance();
			if (! iRefTableREF_CODE_TITRE.estValide(civilian.getTitleCode(), "")) {					// Check if this value is correct or not
				
				log.warn("The field title not valid '" + civilian.getTitleCode() + "'");
				throw new MissingParameterException("The field title not valid");					// We must raised an error because of ORACLE violated constraint
			}
		}
		
		if(StringUtils.isEmpty(individualInformations.getStatus())){
			throw new MissingParameterException("The field status is mandatory");
		}	
		
//		if(StringUtils.isEmpty(individualInformations.getCivility())){
//			throw new MissingParameterException("The field civility is mandatory");
//		}
			
		if(StringUtils.isEmpty(individualInformations.getLastNameSC())){
			throw new MissingParameterException("The field lastname is mandatory");
		}
			
		if(StringUtils.isEmpty(individualInformations.getFirstNameSC())){
			throw new MissingParameterException("The field firstname is mandatory");
		}		
		
		// INDVIDUAL CREATION => CAN NOT CREATE EXTRA DATA WHILE CREATING AN INDIVIDUAL. ONLY EMAILS,TELECOM AND POSTAL ADDRESSES CAN BE CREATED
		if(request.getComunicationPreferencesRequest().isEmpty() && request.getPrefilledNumbersRequest().isEmpty() &&
				request.getExternalIdentifierRequest().isEmpty() && request.getAccountDelegationDataRequest()==null &&
				request.getMarketingDataRequest()==null){
			return;
		}
			
		if(!request.getExternalIdentifierRequest().isEmpty()){
			throw new InvalidParameterException("Unable to create an individual with external identifiers (account data mandatory)");
		}
		
		if(!request.getComunicationPreferencesRequest().isEmpty()){
			throw new InvalidParameterException("Unable to create an individual with communication preferences");
		}
		
		if(!request.getPrefilledNumbersRequest().isEmpty()){
			throw new InvalidParameterException("Unable to create an individual with prefilled numbers");
		}
			
		if(request.getMarketingDataRequest()!=null){
			throw new InvalidParameterException("Unable to create an individual with marketing data");
			
		}
		if(request.getAccountDelegationDataRequest()!=null){
			throw new InvalidParameterException("Unable to create an individual with account delegation data");
		}
		
	}

// REPIND-1687 : disable duplicate normalization of postal address
	/**
	 * NORMALIZE POSTAL ADDRESSES : checking postal addresses and normalization step via SOFT COMPUTING
	 * 
	 * @param request
	 * @throws JrafDomainException
	 */
//	protected CreateUpdateIndividualResponse normalizePostalAddress(CreateUpdateIndividualRequest request) throws JrafDomainException {
//		
//		// GET POSTAL ADDRESS TO BE NORMALIZED
//		Set<AdressePostaleDTO> adressePostaleDTOSet = S08924RequestTransformV6.transformToAdressePostaleDTO(request.getPostalAddressRequest());
//				
//		// SOFT RESPONSE
//		return addressNormalizationHelperV6.checkNormalizedAddress(adressePostaleDTOSet);	
//	}
	
	/**
	 * NORMALIZE TELECOMS : checking telecom data and normalization step
	 * 
	 * @param request
	 * @throws JrafDomainException
	 */
	protected List<TelecomsDTO> normalizeTelecoms(CreateUpdateIndividualRequest request) throws JrafDomainException {
		
		// GET TELECOM TO BE NORMALIZED
		List<TelecomsDTO> telecomFromWSList = IndividuRequestTransformV6.transformToTelecomsDTO(request.getTelecomRequest());
		
		// CHECKING INPUT : V6 ONLY: USAGE CODE MANAGEMENT 
		telecomDS.checkTelecomsByUsageCode(telecomFromWSList);
			
		// TELECOM NORMALIZATION STEP
		List<TelecomsDTO> normalizedTelecomList = telecomDS.normalizePhoneNumber(telecomFromWSList);		
		return normalizedTelecomList;
	}
	
	/**
	 * CREATE INDIVIDUAL DATA 
	 * 
	 * @param request
	 * @throws JrafDomainException
	 */
	protected String createIndividualData(CreateUpdateIndividualRequest request, CreateUpdateIndividualResponse response) throws JrafDomainException, JrafApplicativeException {
		
		// TRANSFORM REQUEST
		CreateUpdateIndividualRequestDTO requestDTO = createOrUpdateIndividualMapperV6.wsRequestToCommon(request);
		CreateModifyIndividualResponseDTO responseDTO = createOrUpdateIndividualMapperV6.wsResponseToCommon(response);
		
		log.debug("requestDTO : " + requestDTO);
		// PRE-CREATION OF INDIVIDUAL (FB enrollment / upgrade B2C)
		createOrUpdateAnIndividualHelper.prepareIndividualCreation(requestDTO, responseDTO);
		responseDTO = individuDS.createOrUpdateIndividual(requestDTO);
		// GET GIN AFTER CREATION
		return getGin(responseDTO);
	
	}
	
	/**
	 * UPDATE INDIVIDUAL DATA : Individual data, postal address, email, communication preferences 
	 * 
	 * @param request
	 * @throws JrafDomainException
	 */
	protected void updateIndividualData(CreateUpdateIndividualRequest request) throws JrafDomainException {
		
		IndividuDTO individuDTO = IndividuRequestTransformV6.transformToIndividuDTO(request);
		SignatureDTO signatureAPP = IndividuRequestTransformV6.transformToSignatureAPP(request.getRequestor());
		List<PostalAddressDTO> postalAddressDTOList = IndividuRequestTransformV6.transformToPostalAddressDTO(request.getPostalAddressRequest(), signatureAPP);
		List<EmailDTO> emailDTOList = IndividuRequestTransformV6.transformToEmailDTO(request.getEmailRequest());
				
		//accountDS.updateIndividual(individuDTO, postalAddressDTOList, emailDTOList, null, signatureAPP);	
		
		
		List<CommunicationPreferencesDTO> comPrefsDTOList = IndividuRequestTransformV6.transformToComPrefsDTO(request.getComunicationPreferencesRequest(),request.getRequestor());
		// GETTING UPDATE MODE FOR COMMUNICATION PREFERENCES
		UpdateModeEnum updateModeCommPrefs = UpdateModeEnum.getEnum(request.getUpdateCommunicationPrefMode());
		// GETTING NEWSLETTER MEDIASENDING FLAG
		String newsletterMediaSending = StringUtils.isNotEmpty(request.getNewsletterMediaSending()) ? request.getNewsletterMediaSending() : "";
		// GETTING MYACCOUNT STATUS
		String status = StringUtils.isNotEmpty(request.getStatus()) ? request.getStatus() : null;
		
		// CHECKING VALIDITY RELATED TO COMMUNICATIONS PREFERENCES
		communicationPreferencesHelper.checkCommPrefList(comPrefsDTOList, updateModeCommPrefs);
		
		// Preferences and Marketing
		List<PreferenceDTO> preferenceDTOList = IndividuRequestTransformV6.transformBDMToPreferenceDTO(request.getMarketingDataRequest(), signatureAPP);
		individuDTO.setPreferenceDTO(preferenceDTOList);
		
		ReturnDetailsDTO resultDetails = accountDS.updateMyAccountCustomer(individuDTO, postalAddressDTOList, null, emailDTOList, null, comPrefsDTOList, newsletterMediaSending, signatureAPP, status, updateModeCommPrefs.toString(), null, null);
		log.info(resultDetails.toString());
	}
		
	private String getGin(CreateUpdateIndividualRequest request) {
		
		if(request==null || request.getIndividualRequest()==null || request.getIndividualRequest().getIndividualInformations()==null) {
			return null;
		}
		
		return request.getIndividualRequest().getIndividualInformations().getIdentifier();
	}
	
	private String getGin(CreateModifyIndividualResponseDTO responseDTO) {
		
		if(responseDTO==null || responseDTO.getIndividu()==null) {
			return null;
		}
		
		return responseDTO.getIndividu().getNumeroClient();
	}
		
	/**
	 * PROCESS PREFILLED NUMBERS TO UPDATE
	 * 
	 * @param gin
	 * @param updateMode
	 * @param signature
	 * @throws BusinessException
	 */
	protected void processPrefilledNumbers(String gin,List<PrefilledNumbersDTO> prefilledNumbersList, UpdateModeEnum updateMode, SignatureDTO signature) throws JrafDomainException {

		if (updateMode == UpdateModeEnum.REPLACE) {
			prefilledNumbersHelper.clearPrefilledNumbers(gin);
		}

		if (prefilledNumbersList == null || prefilledNumbersList.isEmpty()) {
			return;
		}

		prefilledNumbersHelper.checkPrefilledNumbers(prefilledNumbersList, gin);
		
		prefilledNumbersHelper.updatePrefilledNumbers(gin,prefilledNumbersList, signature);

	}

	protected void checkTravelCompanionCharacters(CreateUpdateIndividualRequest request) throws java.security.InvalidParameterException {
		List<MaccTravelCompanion> travelCompanionList = request.getMarketingDataRequest().getMarketingInformation()
				.getTravelCompanion();

		for (MaccTravelCompanion travelCompanion : travelCompanionList) {
			if (travelCompanion.getFirstName() != null) {
				boolean hasSpecialChars = !travelCompanion.getFirstName().matches("^[A-Za-z\\s]+$");
				if (hasSpecialChars){
					throw new java.security.InvalidParameterException("The Travel Companion firstname must not contain any special characters (only letters and spaces allowed)");
				}
			}
			if (travelCompanion.getLastName() != null) {
				boolean hasSpecialChars = !travelCompanion.getLastName().matches("^[A-Za-z\\s]+$");
				if (hasSpecialChars){
					throw new java.security.InvalidParameterException("The Travel Companion lastname must not contain any special characters (only letters and spaces allowed)");
				}
			}
		}
	}
	
	public ConsentDS getConsentDS() {
		return consentDS;
	}

	public void setConsentDS(ConsentDS consentDS) {
		this.consentDS = consentDS;
	}
}
