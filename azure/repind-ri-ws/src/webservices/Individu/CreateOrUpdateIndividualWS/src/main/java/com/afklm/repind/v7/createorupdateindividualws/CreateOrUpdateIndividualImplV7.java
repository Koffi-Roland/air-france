package com.afklm.repind.v7.createorupdateindividualws;

import com.afklm.repind.utils.CreateOrUpdateIndividualMapperV7;
import com.afklm.repind.v7.createorupdateindividualws.helpers.*;
import com.afklm.repind.v7.createorupdateindividualws.transformers.IndividuRequestTransformV7;
import com.afklm.repind.v7.createorupdateindividualws.transformers.IndividuTransformV7;
import com.afklm.soa.stubs.w000442.v7.BusinessErrorBlocBusinessException;
import com.afklm.soa.stubs.w000442.v7.CreateUpdateIndividualServiceV7;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualRequest;
import com.afklm.soa.stubs.w000442.v7.data.CreateUpdateIndividualResponse;
import com.afklm.soa.stubs.w000442.v7.request.EmailRequest;
import com.afklm.soa.stubs.w000442.v7.request.ExternalIdentifierRequest;
import com.afklm.soa.stubs.w000442.v7.request.PostalAddressRequest;
import com.afklm.soa.stubs.w000442.v7.request.TelecomRequest;
import com.afklm.soa.stubs.w000442.v7.response.BusinessErrorCodeEnum;
import com.afklm.soa.stubs.w000442.v7.response.InformationResponse;
import com.afklm.soa.stubs.w000442.v7.siccommontype.Information;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.Civilian;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.ExternalIdentifier;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.ExternalIdentifierData;
import com.afklm.soa.stubs.w000442.v7.sicindividutype.IndividualInformationsV3;
import com.afklm.soa.stubs.w000442.v7.sicmarketingtype.MaccTravelCompanion;
import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.contract.SaphirContractNotFoundException;
import com.airfrance.ref.exception.email.SharedEmailException;
import com.airfrance.ref.exception.external.*;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.ref.type.NATFieldsEnum;
import com.airfrance.ref.type.ProcessEnum;
import com.airfrance.ref.type.UpdateModeEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.PostalAddressResponseDTO;
import com.airfrance.repind.dto.individu.myaccountcustomerdata.ReturnDetailsDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.refTable.RefTableREF_CIVILITE;
import com.airfrance.repind.entity.refTable.RefTableREF_CODE_TITRE;
import com.airfrance.repind.exception.AddressNormalizationCustomException;
import com.airfrance.repind.service.adresse.internal.AdresseDS;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.consent.internal.ConsentDS;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.reference.internal.RefTypExtIDDS;
import com.airfrance.repind.service.reference.internal.ReferenceDS;
import com.airfrance.repind.service.ws.internal.helpers.CreateOrUpdateAnIndividualHelper;
import com.airfrance.repind.service.ws.internal.helpers.PostalAddressHelper;
import com.airfrance.repind.util.LoggerUtils;
import com.airfrance.repind.util.SicStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.text.ParseException;
import java.util.*;

import static com.afklm.repind.v7.createorupdateindividualws.transformers.IndividuResponseTransformV7.transformToPostalAddressResponse;

@Service("passenger_CreateUpdateIndividualService-v7Bean")
@WebService(endpointInterface="com.afklm.soa.stubs.w000442.v7.CreateUpdateIndividualServiceV7", targetNamespace = "http://www.af-klm.com/services/passenger/CreateUpdateIndividualService-v7/wsdl", serviceName = "CreateUpdateIndividualServiceService-v7", portName = "CreateUpdateIndividualService-v7-soap11http")
@Slf4j
public class CreateOrUpdateIndividualImplV7 implements CreateUpdateIndividualServiceV7  {

	@Autowired
	protected IndividuDS individuDS;

	@Autowired
	protected MyAccountDS myAccountDS;

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
	protected ReferenceDS referencesDS;

	@Autowired
	protected RefTypExtIDDS refTypExtIDDS;
	
	@Autowired
	private ConsentDS consentDS;

	@Autowired
	protected BusinessExceptionHelper businessExceptionHelperV7;

	@Autowired
	protected AddressNormalizationHelper addressNormalizationHelperV7;

	@Autowired
	protected PrefilledNumbersHelper prefilledNumbersHelper;

	@Autowired
	protected AlertHelper alertHelper;

	@Autowired
	protected CommunicationPreferencesHelper communicationPreferencesHelper;

	@Autowired
	protected CreateOrUpdateProspectHelper createOrUpdateProspectHelperV7;

	@Autowired
	protected UltimateHelper ultimateHelper;
	
	@Autowired
	protected PostalAddressHelper postalAddressHelper;
	
	@Autowired
	protected CreateOrUpdateAnIndividualHelper createOrUpdateAnIndividualHelper;
	
	@Autowired
	protected CreateOrUpdateIndividualMapperV7 createOrUpdateIndividualMapperV7;

	private final String WEBSERVICE_IDENTIFIER = "W000442";
	private final String SITE = "VLB";
	
	@Override
	public CreateUpdateIndividualResponse createIndividual(CreateUpdateIndividualRequest request) throws BusinessErrorBlocBusinessException {

		log.info("##### START CreateOrUpdateAnIndividual-V7 ###############################");
		if(request == null){
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_932, "Request must not be null");
		}
		
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

			// GETTING GIN FROM INPUT -> IF DEFINED IT IS AN UPDATE, ELSE THIS IS A CREATION
			String gin = getGin(request);

			// INDIVIDUAL CREATION (GIN not filled) -> S08924
			boolean isCreated = false;

			if(StringUtils.isEmpty(gin)) {
				gin = createIndividualData(request, response);
				isCreated = true;
			}
			// INDIVIDUAL UPDATE (GIN filled)
			else {
				updateIndividualData(request, response);
			}

			SignatureDTO signatureAPP = IndividuRequestTransformV7.transformToSignatureAPP(request.getRequestor());
			SignatureDTO signatureWS = IndividuRequestTransformV7.transformToSignatureWS(WEBSERVICE_IDENTIFIER, SITE);

		
			
			if (request.getProcess() == null || !ProcessEnum.W.getCode().equalsIgnoreCase(request.getProcess())) {

				// ===== TELECOM DATA ====================================================================

				// NORMALIZE TELECOM
				List<TelecomsDTO> telecomFromWSList = normalizeTelecoms(request);
				telecomDS.updateNormalizedTelecomWithUsageCode(gin, telecomFromWSList, signatureAPP, signatureWS);
				
				// ===== EMAIL DATA  =====================================================================
				List<EmailDTO> emailListFromWS = IndividuRequestTransformV7.transformToEmailDTO(request.getEmailRequest());
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
					boolean adrHandlingSuccess = true;
					
					for (PostalAddressRequest parDto : request.getPostalAddressRequest()) {
						postalAddressList.add(IndividuRequestTransformV7.transformToPostalAddressDTO(parDto, signatureAPP));
					}
					
					if (!postalAddressList.isEmpty() && (codeAppliMetier == null || "".equalsIgnoreCase(codeAppliMetier))) {
						codeAppliMetier = "RPD";
					}

					PostalAddressResponseDTO adrBlock = null;

					try {
						adrHandlingSuccess = postalAddressHelper.createOrUpdate(gin, postalAddressList, codeAppliMetier, signatureAPP);
					}catch (AddressNormalizationCustomException e){
						adrHandlingSuccess = false; //Allow us in case of dqe normalization error to return false to the client instead of exception
						response.setGin(null); //Reset gin to return to the client
						adrBlock= e.getPostalAddressResponseDTO();
					}

					int dqeResponse = postalAddressHelper.mapDqeResponse(adrBlock);

					// Fail to create, update or delete postal address
					if (response != null && (!adrHandlingSuccess || dqeResponse > 1)) {
						response.setSuccess(false);
						log.error("Fail to create or update postal address");
						
						InformationResponse infosResp = new InformationResponse();
						Information information = new Information();
						
						information.setInformationCode("140");
						information.setInformationDetails("Fail to create or update postal address");
						infosResp.setInformation(information);
						if (response.getInformationResponse() != null) {
							response.getInformationResponse().add(infosResp);
						}
						else {
							Set<InformationResponse> infosList = new HashSet<InformationResponse>();
							infosList.add(infosResp);
							response.getInformationResponse().addAll(infosList);
						}

						// Custom Response for error normalization
						response.getPostalAddressResponse().add(transformToPostalAddressResponse(adrBlock));
						return response;
					}
				}
				
				// ===== DELEGATION DATA =================================================================

				String managingCompany = null;
				if(request.getRequestor() != null) {
					managingCompany = request.getRequestor().getManagingCompany();
				}

				List<DelegationDataDTO> delegateList = IndividuRequestTransformV7.transformToDelegate(request.getAccountDelegationDataRequest(), gin);
				List<DelegationDataDTO> delegatorList = IndividuRequestTransformV7.transformToDelegator(request.getAccountDelegationDataRequest(), gin);

				// =================================================
				// ========= ULTIMATE ==============================
				// =================================================
				ultimateHelper.createUltimateFamilyLinks(delegatorList, signatureAPP, managingCompany, gin);
				ultimateHelper.createUltimateFamilyLinks(delegateList, signatureAPP, managingCompany, gin);

				List<DelegationDataDTO> nonUltimateDelegateList = UltimateHelper.NonUltimateDelegations(delegateList);
				List<DelegationDataDTO> nonUltimateDelegatorList = UltimateHelper.NonUltimateDelegations(delegatorList);

				//Those two variables (delegateList & delegatorList) MUST NOT be used outside the Ultimate block.
				//(So we set them to null)
				//The classic delegation update must be done ONLY with NON-ULTIMATE delegation
				//
				//If someone do a classic delegation update with ultimate delegation, a LOT of problems are
				//to be expected soon after. (Ultimate business rules not being applyied)
				//
				delegateList = null;
				delegatorList = null;
				// =======================================================
				// =======================================================

				delegationDataDS.updateDelegationData(gin, nonUltimateDelegateList, signatureAPP, managingCompany);
				delegationDataDS.updateDelegationData(gin, nonUltimateDelegatorList, signatureAPP, managingCompany);

				// ===== EXTERNAL IDENTIFIERS ============================================================
				List<ExternalIdentifierDTO> externalIdentifierListFromWS = IndividuRequestTransformV7.transformToExternalIdentifierDTO(request.getExternalIdentifierRequest());
				externalIdentifierDS.updateExternalIdentifier(gin, externalIdentifierListFromWS, signatureAPP, request.getProcess());

				// ===== PREFILLED NUMBERS ===============================================================

				UpdateModeEnum updateModePrefilled = UpdateModeEnum.getEnum(request.getUpdatePrefilledNumbersMode());
				List<PrefilledNumbersDTO> prefilledNumbersDTO = IndividuRequestTransformV7.wsdlTOPrefilledNumbersdto(request.getPrefilledNumbersRequest());
				processPrefilledNumbers(gin, prefilledNumbersDTO, updateModePrefilled, signatureAPP);

				// ===== ALERT ============================================================================
				callAlertProcessing(gin, request, signatureAPP);

				// ===== MARKETING DATA ==================================================================
				// REPIND-1225: Preferences are now stored in RI DB instead of BDM
				//callMarketingData(request);
			}

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
		// ERROR ? (need to receive a number code) : ULTIMATE ERROR
		catch(UltimateException e ){
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.OTHER, e.getMessage());
		}
		// ERROR 001 : INDIVIDUAL NOT FOUND
		catch (NotFoundException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_001, e.getMessage());
		}
		// ERROR 003 : SIMULTANEOUS UPDATE
		catch (SimultaneousUpdateException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_003, e.getMessage());
		}
		// ERROR 133 : MISSING PARAMETER
		catch(MissingParameterException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_133, e.getMessage());
		}
		// ERROR 135 : INVALID LANGUAGE CODE
		catch(InvalidLanguageCodeException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_135, e.getMessage());
		}
		// 382 : SHARED EMAIL
		catch (SharedEmailException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_382, e.getMessage() + ": " + e.getEmail());
		}
		// 384 : ALREADY EXISTS
		catch (AlreadyExistException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_384, e.getMessage());
		}
		// ERROR 385 : ACCOUNT NOT FOUND
		catch(AccountNotFoundException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_385, e.getMessage());
		}
		// 387 : MY ACCOUNT ONLY
		catch (OnlyMyAccountException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_387, e.getMessage());
		}
		// 538 : SAPHIR NUMBER NOT MATCHING WITH INDIVIDUAL DATAS
		catch (com.airfrance.ref.exception.contract.NotConsistentSaphirNumberException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_538, e.getMessage());
		}
		// 550 : NEW DATA REQUIRED FOR RECONCILIATION PROCESS
		catch (ReconciliationProcessException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_550, e.getMessage());
		}
		// 551 : MAXIMUM NUMBER OF SUBSCRIBED NEWLETTER SALES REACHED
		catch (MaximumSubscriptionsException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_551, e.getMessage());
		}
		// ERROR 701 : PHONE NUMBER TOO LONG
		catch (TooLongPhoneNumberException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_701, e.getMessage());
		}
		// ERROR 702 : PHONE NUMBER TOO SHORT
		catch (TooShortPhoneNumberException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_702, e.getMessage());
		}
		// ERROR 703 : INVALID PHONE NUMBER
		catch (InvalidPhoneNumberException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_703, e.getMessage());
		}
		// 706 : DELEGATION GIN NOT FOUND
		catch (DelegationGinNotFoundException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_706, e.getMessage());
		}
		// 707 : DELEGATION GINS IDENTICAL
		catch (DelegationGinsIdenticalException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_707, e.getMessage());
		}
		// 708 : DELEGATION GIN WITHOUT ACCOUNT
		catch (DelegationGinWithoutAccountException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_708, e.getMessage());
		}
		// 709 : DELEGATION STATUS NOT AUTHORIZED
		catch (DelegationActionException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_709, e.getMessage());
		}
		// 710 : DELEGATION ERROR
		catch (DelegationException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_710, e.getMessage());
		}
		// ERROR 711 : INVALID PHONE COUNTRY CODE
		catch (InvalidCountryCodeException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_711, e.getMessage());
		}
		// ERROR 705 : NO NORMALIZED
		catch (NormalizedPhoneNumberException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_705, e.getMessage());
		}
		// ERROR 712 : SAPHIR CONTRACT NOT FOUND
		catch (SaphirContractNotFoundException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_712, e.getMessage());
		}
		// ERROR 713 : INVALID EXTERNAL IDENTIFIER
		catch (ExternalIdentifierAlreadyUsedException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_713, e.getMessage());
		}
		// ERROR 713 : INVALID EXTERNAL IDENTIFIER
		catch (InvalidExternalIdentifierException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_713, e.getMessage());
		}
		// ERROR 714 : INVALID EXTERNAL IDENTIFIER TYPE
		catch (InvalidExternalIdentifierTypeException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_714, e.getMessage());
		}
		// ERROR 715 : INVALID EXTERNAL IDENTIFIER DATA KEY
		catch (InvalidExternalIdentifierDataKeyException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_715, e.getMessage());
		}
		// ERROR 716 : INVALID EXTERNAL IDENTIFIER DATA VALUE
		catch (InvalidExternalIdentifierDataValueException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_716, e.getMessage());
		}
		// ERROR 717 : INVALID PNM ID
		catch (InvalidPnmIdException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_717, e.getMessage());
		}
		// ERROR 718 : MAXIMUM NUMBER OF PNM ID REACHED
		catch (MaxNbOfPnmIdReachedException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_718, e.getMessage());
		}
		// ERROR 932 : INVALID PARAMETER
		catch(InvalidParameterException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_932, e.getMessage());
		}
		// 905 : MARKETING ERROR
		catch (MarketingErrorException e) {
			log.error("Problem during updateMyAccountCustomer : ", e);
			throw businessExceptionHelperV7.createMarketingBusinessException(BusinessErrorCodeEnum.ERROR_905, e);
		}
		// ERROR 905 : TECHNICAL ERROR
		catch (JrafDomainException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
		}
		// ERROR 905 : TECHNICAL ERROR
		catch (JrafApplicativeException e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
		}
		// ERROR 905 : TECHNICAL ERROR
		catch (Exception e) {
			log.error(LoggerUtils.buidErrorMessage(e), e);
			throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, e.getMessage());
		}

		log.info("##### END CreateOrUpdateAnIndividual-V7 #################################");
		return response;
	}

	// Vérifie si il y a des alertes dans la request à traiter pour Insert/Update
	private void callAlertProcessing(String gin, CreateUpdateIndividualRequest request, SignatureDTO signature) throws JrafDomainException, ParseException {
		if (request != null && request.getComunicationPreferencesRequest() != null && !request.getComunicationPreferencesRequest().isEmpty()) {
			alertHelper.processAlert(gin, request, signature);
		}
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
	 * @throws BusinessErrorBlocBusinessException
	 * @throws JrafApplicativeException
	 * @throws BusinessException
	 * @throws JrafDomainException
	 */
	protected void checkInput(CreateUpdateIndividualRequest request) throws BusinessErrorBlocBusinessException, JrafApplicativeException, JrafDomainException {

		if (request != null) {

			// CHECKING IF SPECIAL CHARACTERS IN TRAVEL COMPANIONS DATA
			if (request.getMarketingDataRequest() != null &&
					request.getMarketingDataRequest().getMarketingInformation() != null &&
					request.getMarketingDataRequest().getMarketingInformation().getTravelCompanion() != null){
				checkTravelCompanionCharacters(request);
			}

			if(ProcessEnum.W.getCode().equals(request.getProcess())) {
				// Traitement pour les prospects
				if (request.getRequestor() != null  && request.getRequestor().getContext() != null) {
					if (!request.getRequestor().getContext().equalsIgnoreCase("B2C_HOME_PAGE") &&
							!request.getRequestor().getContext().equalsIgnoreCase("B2C_HOME_PAGE_RECONCILIATION") &&
							!request.getRequestor().getContext().equalsIgnoreCase("B2C_BOOKING_PROCESS")) {
						throw new InvalidParameterException("Unknown context parameter for prospect");
					}
				}
				else {
					throw new MissingParameterException("Missing context information");
				}

				// Check caracteres autorises
				if (request.getIndividualRequest() != null && request.getIndividualRequest().getIndividualInformations() != null) {
					if (request.getIndividualRequest().getIndividualInformations().getFirstNameSC() != null && ! SicStringUtils.isISOLatinString(request.getIndividualRequest().getIndividualInformations().getFirstNameSC().toString())
							|| request.getIndividualRequest().getIndividualInformations().getMiddleNameSC() != null && ! SicStringUtils.isISOLatinString(request.getIndividualRequest().getIndividualInformations().getMiddleNameSC().toString())
							|| request.getIndividualRequest().getIndividualInformations().getLastNameSC() != null && ! SicStringUtils.isISOLatinString(request.getIndividualRequest().getIndividualInformations().getLastNameSC().toString())
							) {
						throw new InvalidParameterException("Characters not authorized in firstname/lastname or middlename");
					}
				}

				// Email mandatory for creation
				if (request.getEmailRequest() != null && !request.getEmailRequest().isEmpty()) {
					if (request.getEmailRequest().size() > 1) {
						throw new InvalidParameterException("Only 1 email usage for prospect");
					}
					else {
						if (request.getEmailRequest().get(0).getEmail() != null
								&& request.getEmailRequest().get(0).getEmail().getEmail() != null) {
							if (!SicStringUtils.isValidEmail(request.getEmailRequest().get(0).getEmail().getEmail())) {
								throw new InvalidParameterException("Invalid email");
							} else {
								request.getEmailRequest().get(0).getEmail()
										.setEmail(request.getEmailRequest().get(0).getEmail().getEmail().toLowerCase());
							}
						}
					}
				}
				else if (request.getIndividualRequest() == null || request.getIndividualRequest().getIndividualInformations() == null || request.getIndividualRequest().getIndividualInformations().getIdentifier() == null) {
					throw new MissingParameterException("Email mandatory for prospect creation");
				}

				if (request.getRequestor() != null && request.getRequestor().getLoggedGin() != null) {
					try {
						request.getRequestor().setLoggedGin(IndividuTransformV7.clientNumberToString(request.getRequestor().getLoggedGin()));
						if (!SicStringUtils.isValidFbIdentifier(request.getRequestor().getLoggedGin())) {
							throw new InvalidParameterException("Invalid FB identifier");
						}
					} catch (JrafApplicativeException e) {
						log.error(LoggerUtils.buidErrorMessage(e), e);
						throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
					} catch (NumberFormatException e) {
						log.error(LoggerUtils.buidErrorMessage(e), e);
						throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
					}
				}

				if (request.getRequestor() != null && request.getRequestor().getReconciliationDataCIN() != null) {
					try {
						request.getRequestor().setReconciliationDataCIN(IndividuTransformV7.clientNumberToString(request.getRequestor().getReconciliationDataCIN()));
						if (!SicStringUtils.isValidFbIdentifier(request.getRequestor().getReconciliationDataCIN())) {
							throw new InvalidParameterException("Invalid FB identifier");
						}
					} catch (JrafApplicativeException e) {
						log.error(LoggerUtils.buidErrorMessage(e), e);
						throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
					} catch (NumberFormatException e) {
						log.error(LoggerUtils.buidErrorMessage(e), e);
						throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
					}
				}

				// Compref mandatory for creation
				if (request.getComunicationPreferencesRequest() != null && !request.getComunicationPreferencesRequest().isEmpty()) {
					if (request.getComunicationPreferencesRequest().size() == 1) {
						if (request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences() != null) {
							try {
								if ((!referencesDS.refComPrefExistForIndividual(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getDomain(), request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getCommunicationGroupeType(), request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getCommunicationType())
										) && !ProcessEnum.A.getCode().equals(request.getProcess())) {
									throw new InvalidParameterException("Domain, group type or type unknown");
								}
							} catch (JrafDomainException e) {
								log.error(LoggerUtils.buidErrorMessage(e), e);
								throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
							}

							if (request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage() == null || request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage().isEmpty()) {
								throw new MissingParameterException("Market language mandatory for prospects");
							}
							else {   // Verification de doublons sur les marchés/langues dans l'input
								List<com.afklm.soa.stubs.w000442.v7.sicindividutype.MarketLanguage> tempsML = request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage();
								boolean twiceML = false;
								for (int indexReq = 0; indexReq < request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage().size(); indexReq++) {
									for (int indexTemp = indexReq + 1; indexTemp < tempsML.size(); indexTemp++) {
										if (tempsML.get(indexTemp).getLanguage() == request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage().get(indexReq).getLanguage()
												&& tempsML.get(indexTemp).getMarket().equalsIgnoreCase(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage().get(indexReq).getMarket())) {
											twiceML = true;
										}
									}
								}
								if (twiceML) {
									throw new InvalidParameterException("Duplicate market/language set");
								}
							}

							if(StringUtils.isEmpty(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getDomain())) {
								throw new MissingParameterException("The field domain is mandatory");
							}

							if(StringUtils.isEmpty(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getCommunicationGroupeType())) {
								throw new MissingParameterException("The field group type is mandatory");
							}

							if(StringUtils.isEmpty(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getCommunicationType())) {
								throw new MissingParameterException("The field type is mandatory");
							}

							if(StringUtils.isEmpty(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getOptIn())) {
								throw new MissingParameterException("The field optin is mandatory");
							}

							if(StringUtils.isEmpty(request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getSubscriptionChannel())) {
								throw new MissingParameterException("The field subscription channel is mandatory");
							}

							for (com.afklm.soa.stubs.w000442.v7.sicindividutype.MarketLanguage ml : request.getComunicationPreferencesRequest().get(0).getCommunicationPreferences().getMarketLanguage()) {
								if(StringUtils.isEmpty(ml.getMarket())) {
									throw new MissingParameterException("The field market is mandatory");
								}
								if(ml.getLanguage() == null ) {
									throw new MissingParameterException("The field language is mandatory");
								}
								if(StringUtils.isEmpty(ml.getOptIn())) {
									throw new MissingParameterException("The field optin for market language is mandatory");
								}
								if(ml.getDateOfConsent() == null) {
									throw new MissingParameterException("The field date of consent for market language is mandatory");
								}
							}
						}
					}
					else {
						throw new InvalidParameterException("Only 1 communication preference allowed for prospect");
					}
				}
				else if ((request.getIndividualRequest() == null || request.getIndividualRequest().getIndividualInformations() == null || request.getIndividualRequest().getIndividualInformations().getIdentifier() == null)
						/* && request.getAlertRequest() == null */ ) { //We can create a prospect with an alert and without ComPref
					throw new MissingParameterException("Communication preference mandatory for prospect creation");
				}

				if (request.getTelecomRequest() != null && !request.getTelecomRequest().isEmpty()) {
					if (request.getTelecomRequest().size() > 1) {
						throw new InvalidParameterException("Only 1 telecom allowed for prospect");
					}
				}

				// Only preferred airport field is allowed for a prospect preference
				if (request.getPreferenceDataRequest() != null) {
					if (request.getPreferenceDataRequest().getPreference() != null && !request.getPreferenceDataRequest().getPreference().isEmpty()) {
						if (request.getPreferenceDataRequest().getPreference().size() > 1) {
							throw new InvalidParameterException("Only 1 type of preference is allowed for a prospect");
						}
						if (!"WW".equalsIgnoreCase(request.getPreferenceDataRequest().getPreference().get(0).getTypePreference())) {
							throw new InvalidParameterException("Only the type 'WW' is allowed as a type of preference for prospect");
						}
						if (request.getPreferenceDataRequest().getPreference().get(0).getPreferenceData() != null
								&& !request.getPreferenceDataRequest().getPreference().get(0).getPreferenceData().isEmpty()
								&& !"PA".equalsIgnoreCase(request.getPreferenceDataRequest().getPreference().get(0).getPreferenceData().get(0).getKey())) {
							throw new InvalidParameterException("Only preferred airport(preferredAirport) field is allowed for a prospect preference");
						}
					}
				}
			}else if(ProcessEnum.A.getCode().equals(request.getProcess())) {   // Traitement par defaut pour les alert
				if(request.getAlertRequest() != null) {
					alertHelper.checkInputAlert(IndividuRequestTransformV7.transformToAlertDTO(request.getAlertRequest()),
							(request.getComunicationPreferencesRequest() != null && !request.getComunicationPreferencesRequest().isEmpty()));
					alertHelper.checkComprefAndAlert(request.getAlertRequest(), request.getComunicationPreferencesRequest());
				}
				// Dans le cas ou on essaye de creer des alertes sans le process A (Hors process)
			}else if(request.getAlertRequest() != null && !ProcessEnum.A.getCode().equals(request.getProcess())){
				throw new InvalidParameterException("Create or update Alert not allowed withless Process A");
			}
			else {
				
				
				// Traitement par defaut pour les non prospects

				// applicationCode never used;
				//				String applicationCode = "";
				//
				//				if (request.getRequestor() != null && request.getRequestor().getApplicationCode() !=null) {
				//					applicationCode = request.getRequestor().getApplicationCode();
				//				}

				// On differencie ici si il s'agit d'une creation ou d'un update
				if (request.getProcess() == null || (request.getProcess() != null && !ProcessEnum.E.getCode().equals(request.getProcess()))) {
					String identifier = checkIndividualInput(request);
					// IF IDENTIFIER IS FILLED IN => OK
					if(StringUtils.isNotEmpty(identifier)) {
						return;
					}
				}
				//		// WE WANT TO CREATE A NEW INDIVIDUAL => MANDATORY FIELDS ARE BIRTH DATE (EXCEPT FOR FIDELIO)
				//		// STATUS, CIVILITY,LAST NAME SC, FIRST NAME SC
				//		if(!applicationCode.equals(FIDELIO) && individualInformations.getBirthDate()==null){
				//			throw new MissingParameterException("The field birth date is mandatory");
				//		}

				// INDVIDUAL CREATION => CAN NOT CREATE EXTRA DATA WHILE CREATING AN INDIVIDUAL. ONLY EMAILS,TELECOM AND POSTAL ADDRESSES CAN BE CREATED
				if(request.getComunicationPreferencesRequest().isEmpty() && request.getPrefilledNumbersRequest().isEmpty() &&
						request.getExternalIdentifierRequest().isEmpty() && request.getAccountDelegationDataRequest()==null &&
						request.getMarketingDataRequest()==null && request.getPreferenceDataRequest() == null) {
					return;
				}

				if (request.getProcess() != null && ProcessEnum.E.getCode().equals(request.getProcess())) {
					checkExternalIdentifierDataKey(request);
				}

				// TODO : Remplacer SOCIAL par TypeEmunContext
				if(!request.getExternalIdentifierRequest().isEmpty() && (request.getProcess() != null  && !ProcessEnum.E.getCode().equals(request.getProcess()) )){
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

				if (request.getPreferenceDataRequest() != null) {
					throw new InvalidParameterException("Unable to create an individual with preferences");
				}
			}
		}
	}

	/**
	 * Check fields about an individual
	 * @param request
	 * @return
	 * @throws MissingParameterException
	 */
	protected String checkIndividualInput(CreateUpdateIndividualRequest request) throws MissingParameterException {

		if(request==null || request.getIndividualRequest()==null || request.getIndividualRequest().getIndividualInformations()==null) {
			throw new MissingParameterException("The individual informations are mandatory");
		}

		IndividualInformationsV3 individualInformations = request.getIndividualRequest().getIndividualInformations();

		// REPIND-1671 : Check the Constraint : scivilite in ('M.','MISS','MR','MRS','MS') 
		if (!StringUtils.isEmpty(individualInformations.getCivility())){							// Check if CIVILITY have been filled
			
			individualInformations.setCivility(individualInformations.getCivility().trim().toUpperCase());
			
			RefTableREF_CIVILITE iRefTableREF_CIVILITE = RefTableREF_CIVILITE.instance();
			if (! iRefTableREF_CIVILITE.estValide(individualInformations.getCivility(), "")) {		// Check if this value is correct or not
				
				log.warn("The field civility not valid '{}'",individualInformations.getCivility());
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
				
				log.warn("The field title not valid '{}'",civilian.getTitleCode());
				throw new MissingParameterException("The field title not valid");					// We must raised an error because of ORACLE violated constraint
			}
		}
		
		// REPIND-1671 : In case of UPDATE we skipped all the Mandatory check made after that
		if(StringUtils.isNotEmpty(individualInformations.getIdentifier())) {
			return individualInformations.getIdentifier();
		}

		if(StringUtils.isEmpty(individualInformations.getStatus())){
			throw new MissingParameterException("The field status is mandatory");
		}

		if(StringUtils.isEmpty(individualInformations.getCivility())){
			throw new MissingParameterException("The field civility is mandatory");
		}

		if(StringUtils.isEmpty(individualInformations.getLastNameSC())){
			throw new MissingParameterException("The field lastname is mandatory");
		}

		if(StringUtils.isEmpty(individualInformations.getFirstNameSC())){
			throw new MissingParameterException("The field firstname is mandatory");
		}

		if (request.getProcess() != null && ProcessEnum.T.equals(ProcessEnum.valueOf(request.getProcess()))) {
			if ((request.getTelecomRequest() == null || request.getTelecomRequest().isEmpty())
					&& (request.getEmailRequest() == null || request.getEmailRequest().isEmpty())) {
				throw new MissingParameterException("A bloc \"contact\" (Telecom and/or email) is mandatory for traveler process");
			}

			if (request.getTelecomRequest() != null && !request.getTelecomRequest().isEmpty()) {
				for (TelecomRequest telRequest : request.getTelecomRequest()) {
					if (telRequest.getTelecom() != null) {
						boolean exc = telRequest.getTelecom().getCountryCode() != null ? true : false;
						exc &= telRequest.getTelecom().getPhoneNumber() != null ? true : false;
						if (!exc) {
							throw new MissingParameterException("Phone number and country code are mandatory in a Telecom bloc");
						}
					}
				}
			}
			if (request.getEmailRequest() != null && !request.getEmailRequest().isEmpty()) {
				for (EmailRequest emRequest : request.getEmailRequest()) {
					if (emRequest.getEmail() != null && emRequest.getEmail().getEmail() == null) {
						throw new MissingParameterException("Email is mandatory in an Email bloc");
					}
				}
			}

		}

		return "";
	}

	protected void checkExternalIdentifierDataKey(CreateUpdateIndividualRequest request) throws InvalidParameterException {
		for (ExternalIdentifierRequest eir : request.getExternalIdentifierRequest()) {
			List<String> keys = new LinkedList<>();
			for (ExternalIdentifierData eid : eir.getExternalIdentifierData()) {
				if (keys.contains(eid.getKey())) {
					throw new InvalidParameterException("The external identifier data key must be unique for a given external identifier");
				}
				keys.add(eid.getKey());
			}
		}
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
				boolean hasSpecialChars = !travelCompanion.getLastName().matches("^^[A-Za-z\\s]+$");
				if (hasSpecialChars){
					throw new java.security.InvalidParameterException("The Travel Companion lastname must not contain any special characters (only letters and spaces allowed)");
				}
			}
		}
	}

	/**
	 * NORMALIZE POSTAL ADDRESSES : checking postal addresses and normalization step via SOFT COMPUTING
	 *
	 * @param request
	 * @throws JrafDomainException
	 */
	// REPIND-1687 : Remove duplicate normalization
//	protected CreateUpdateIndividualResponse normalizePostalAddress(CreateUpdateIndividualRequest request) throws JrafDomainException {
//
//		// GET POSTAL ADDRESS TO BE NORMALIZED
//		Set<AdressePostaleDTO> adressePostaleDTOSet = S08924RequestTransformV7.transformToAdressePostaleDTO(request.getPostalAddressRequest());
//
//		// SOFT RESPONSE
//		return addressNormalizationHelperV7.checkNormalizedAddress(adressePostaleDTOSet);
//	}

	/**
	 * NORMALIZE TELECOMS : checking telecom data and normalization step
	 *
	 * @param request
	 * @throws JrafDomainException
	 */
	protected List<TelecomsDTO> normalizeTelecoms(CreateUpdateIndividualRequest request) throws JrafDomainException {

		// GET TELECOM TO BE NORMALIZED
		List<TelecomsDTO> telecomFromWSList = IndividuRequestTransformV7.transformToTelecomsDTO(request.getTelecomRequest());

		// CHECKING INPUT : V6 ONLY: USAGE CODE MANAGEMENT
		telecomDS.checkTelecomsByUsageCode(telecomFromWSList);

		// REPIND-643 : we do not want deleted telecoms to be normalized anymore
		List<TelecomsDTO> toBeDeletedTelecomList = new ArrayList<>();
		List<TelecomsDTO> toBeNormalizedTelecomList = new ArrayList<>();

		if(telecomFromWSList != null) {
			for(TelecomsDTO t : telecomFromWSList) {
				if(MediumStatusEnum.REMOVED.toString().equals(t.getSstatut_medium())) {
					toBeDeletedTelecomList.add(t);
				} else {
					toBeNormalizedTelecomList.add(t);
				}
			}
		}

		// TELECOM NORMALIZATION STEP
		List<TelecomsDTO> normalizedTelecomList = telecomDS.normalizePhoneNumber(toBeNormalizedTelecomList);

		// REPIND-643 : we still have to add the non-normalized deleted telecoms to the list
		if(normalizedTelecomList == null) {
			normalizedTelecomList = new ArrayList<>();
		}
		normalizedTelecomList.addAll(toBeDeletedTelecomList);

		return normalizedTelecomList;
	}

	/**
	 * CREATE INDIVIDUAL DATA
	 *
	 * @param request
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws BusinessException
	 */
	protected String createIndividualData(CreateUpdateIndividualRequest request, CreateUpdateIndividualResponse response) throws JrafDomainException, JrafApplicativeException, BusinessErrorBlocBusinessException {

		String gin = null;

		// CREATE/UPDATE AN INDIVIDUAL
		if (request.getRequestor() == null || request.getProcess() == null) {
			return createIndividualDataByDefault(request, response);					// Individual by default type = NULL
		} else {
			switch (ProcessEnum.valueOf(request.getProcess())) {
			case T:
				// Traveler creation OK
				IndividuDTO indDTO = IndividuRequestTransformV7.transformRequestToIndividuDTO(request);
				gin = individuDS.createAnIndividualTraveler(indDTO);

				return gin;
			case E:
				IndividuDTO indDTOE = IndividuRequestTransformV7.transformRequestToIndividuDTO(request);

				// Par defait, on lance tout de meme la creation pour les autres External Identifier non GIGYA
				return individuDS.createAnIndividualExternal(indDTOE);
			case A:
				/*** Recherche d'individu(s) ou de prospect existants pour cet email (reconciliation) ***/
				ProspectIndividuDTO prospectIndivDTO = createOrUpdateProspectHelperV7.findIndividual(request);
				IndividuDTO findProspectDTO = null;
				
				// Transform email
				if (request.getEmailRequest() != null) {
					List<EmailRequest> updatedEmailList = new ArrayList<EmailRequest>();
					for (EmailRequest emailWS : request.getEmailRequest()) {
						EmailRequest updatedEmail = IndividuRequestTransformV7.transformEmailProspectToEmailIndividu(emailWS);
						updatedEmailList.add(updatedEmail);
					}
					request.getEmailRequest().clear();
					request.getEmailRequest().addAll(updatedEmailList);
				}
				
				// Si individu trouvé dans SIC, on update et on retourne avec le gin
				if (prospectIndivDTO != null) {
					CreateUpdateIndividualRequest updateIndividu = createOrUpdateProspectHelperV7.updateIndividualProspect(prospectIndivDTO, request);
					request.setProcess(null);
					
					alertHelper.updateIndividualForAlert(updateIndividu, response);
					return prospectIndivDTO.getGin().toString();
				}
				// Si individu non trouvé on recherche coté prospects
				findProspectDTO = createOrUpdateProspectHelperV7.searchProspectByEmail(request);

				/*** Si on retrouve un prospect on update et on retourne le gin***/
				if (findProspectDTO != null) {
					IndividuDTO updateProspect = null;
					if (!"X".equals(findProspectDTO.getStatutIndividu())) {
						updateProspect = IndividuTransformV7.wsdlProspectToProspectDto(request);
						updateProspect = IndividuTransformV7.wsdlSignatureToDtoCreation(updateProspect, request.getRequestor());
						updateProspect = IndividuTransformV7.wsdlSignatureToDtoUpdate(updateProspect, request.getRequestor());
						chekProspectMandatory(updateProspect, findProspectDTO);

						try {
							// update prospect
							gin = createOrUpdateProspectHelperV7.updateProspect(updateProspect, findProspectDTO, response, request);

							// update prospect's telecom
							createOrUpdateProspectHelperV7.updateProspectTelecoms(request, gin);

						} catch (Exception e){
							log.error(LoggerUtils.buidErrorMessage(e), e);
							throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
						}
					}
					else {
						throw new NotFoundException("Deleted prospect");
					}

					return findProspectDTO.getSgin().toString();
				}else{
					// Aucun individu trouvé, on crée un nouveau prospect
					IndividuDTO createProspect = null;
					createProspect = IndividuTransformV7.wsdlProspectToProspectDto(request);
					createProspect = IndividuTransformV7.wsdlStatusToDtoCreation(createProspect);
					createProspect = IndividuTransformV7.wsdlSignatureToDtoCreation(createProspect, request.getRequestor());
					createProspect = IndividuTransformV7.wsdlSignatureToDtoUpdate(createProspect, request.getRequestor());
					return createOrUpdateProspectHelperV7.createProspect(createProspect, response);
				}

			case W:
				//  ************ new process ****************
				/*** Recherche d'individu(s) ou de prospect existants pour cet email (reconciliation) ***/
				ProspectIndividuDTO prospectIndivDto = createOrUpdateProspectHelperV7.findIndividual(request);
				IndividuDTO findProspectDto = null;
				
				// Transform email
				if (request.getEmailRequest() != null) {
					List<EmailRequest> updatedEmailList = new ArrayList<EmailRequest>();
					for (EmailRequest emailWS : request.getEmailRequest()) {
						EmailRequest updatedEmail = IndividuRequestTransformV7.transformEmailProspectToEmailIndividu(emailWS);
						updatedEmailList.add(updatedEmail);
					}
					request.getEmailRequest().clear();
					request.getEmailRequest().addAll(updatedEmailList);
				}
				
				// Si individu reconcilié on fait un update
				if (prospectIndivDto != null) {
					CreateUpdateIndividualRequest updateIndividu = createOrUpdateProspectHelperV7.updateIndividualProspect(prospectIndivDto, request);
					request.setProcess(null);

					updateIndividualDataByDefault(updateIndividu, response);
					gin = prospectIndivDto.getGin().toString();

				}
				// Si individu non reconcilié on recherche coté prospects
				else {
					findProspectDto = createOrUpdateProspectHelperV7.searchProspectByEmail(request);
				}

				/*** Si on retrouve un prospect on fait l'update ***/
				if (findProspectDto != null) {
					IndividuDTO updateProspect = null;
					if (!"X".equals(findProspectDto.getStatutIndividu())) {
						updateProspect = IndividuTransformV7.wsdlProspectToProspectDto(request);
						updateProspect = IndividuTransformV7.wsdlSignatureToDtoCreation(updateProspect, request.getRequestor());
						updateProspect = IndividuTransformV7.wsdlSignatureToDtoUpdate(updateProspect, request.getRequestor());
						chekProspectMandatory(updateProspect, findProspectDto);

						try {
							// update prospect
							gin = createOrUpdateProspectHelperV7.updateProspect(updateProspect, findProspectDto, response, request);

							// update prospect's telecom
							createOrUpdateProspectHelperV7.updateProspectTelecoms(request, gin);

						} catch (MaximumSubscriptionsException e){
							throw new MaximumSubscriptionsException(e.getMessage());
						} catch (Exception e){
							log.error(LoggerUtils.buidErrorMessage(e), e);
							throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
						}
					}
					else {
						throw new NotFoundException("Deleted prospect");
					}
				}
				else if (prospectIndivDto == null) {
					// Aucun individu trouvé, on crée un nouveau prospect
					IndividuDTO createProspect = null;
					createProspect = IndividuTransformV7.wsdlProspectToProspectDto(request);
					createProspect = IndividuTransformV7.wsdlStatusToDtoCreation(createProspect);
					createProspect = IndividuTransformV7.wsdlSignatureToDtoCreation(createProspect, request.getRequestor());
					createProspect = IndividuTransformV7.wsdlSignatureToDtoUpdate(createProspect, request.getRequestor());
					gin = createOrUpdateProspectHelperV7.createProspect(createProspect, response);

					// Cas 1 : MORALES - New Prospect
					if (response != null) {

						InformationResponse informationResponse = new InformationResponse();
						Information info = new Information();
						info.setInformationCode(""+0);
						info.setInformationDetails("New prospect created.");
						informationResponse.setInformation(info);
						response.getInformationResponse().add(informationResponse);

					}

					createOrUpdateProspectHelperV7.updateProspectTelecoms(request, gin);
				}

				return gin;

				// ************ end new process ***************

			case I:
			default:
				return createIndividualDataByDefault(request, response);			// Individual by default type = I
			}
		}

		//		CreateModifyIndividualResponseDTO responseDTO = individuDS.createModifyIndividualDS(requestDTO);	// Hibernate
	}

	// On cherche si il y a un ExternalIdentifier existant, si oui, on renvoi le GIN, sinon, on leve une Exception
	private String existExternalIdentifier(List<ExternalIdentifierRequest> leir) throws JrafDomainException  {

		String ginFoundForExistingExternalIdentifier = "";

		// Recherche d'un GIGYA existant
		for (ExternalIdentifierRequest lei : leir) {

			ExternalIdentifier ei = lei.getExternalIdentifier();

			// On ne teste QUE les GIGYA
			if (ei != null && refTypExtIDDS.get(ei.getType()) != null &&
					!"".equals(ei.getIdentifier())
					) {
				String foundGIN = externalIdentifierDS.existExternalIdentifierByGIGYA(ei.getIdentifier());

				if (!"".equals(foundGIN)) {
					if ("".equals(ginFoundForExistingExternalIdentifier)) {
						ginFoundForExistingExternalIdentifier += foundGIN;
					} else {
						ginFoundForExistingExternalIdentifier = " " + foundGIN;
					}
				}
			}
		}

		// Il y a plusieurs GIN, sont ils les memes ?
		if (ginFoundForExistingExternalIdentifier.contains(" ")) {

			String[] existingExternalIdentifierTab = ginFoundForExistingExternalIdentifier.split(" ");

			// Si il y a plusieurs GIN
			if (existingExternalIdentifierTab.length > 1) {

				boolean sontIlsLesMemes = true;
				String existingExternalIdentifierGINIdentifie = existingExternalIdentifierTab[0];
				for (String existingExternalIdentifierGIN : existingExternalIdentifierTab) {
					sontIlsLesMemes &= existingExternalIdentifierGIN.equals(existingExternalIdentifierGINIdentifie);
				}

				// On a plusieur GIN associé à nos GIGYA et ils sont tous les mêmes
				if (sontIlsLesMemes) {
					// Cas ideal
				} else {
					// On a un souci car les GIGYA envoyés dans la request sont associés a plusieur GIN différent
					// On leve une erreur - 713 INVALID EXTERNAL IDENTIFIER
					throw new ExternalIdentifierLinkedToDifferentIndividualException(ginFoundForExistingExternalIdentifier);
				}
				// Si il n y a que un seul GIN
			} else {
				// Cas ideal
			}
		}

		return ginFoundForExistingExternalIdentifier;
	}

	private String createIndividualDataByDefault(CreateUpdateIndividualRequest request, CreateUpdateIndividualResponse response) throws JrafApplicativeException, JrafDomainException {

		// TRANSFORM REQUEST
		CreateUpdateIndividualRequestDTO requestDTO = createOrUpdateIndividualMapperV7.wsRequestV7ToCommon(request);
		CreateModifyIndividualResponseDTO responseDTO = createOrUpdateIndividualMapperV7.wsResponseV7ToCommon(response);
		
		log.debug("requestDTO : {}",requestDTO);
		// PRE-CREATION OF INDIVIDUAL (FB enrollment / upgrade B2C)
//		createOrUpdateAnIndividualHelperV7.createIndividual(oldrequestDTO, response);
		createOrUpdateAnIndividualHelper.prepareIndividualCreation(requestDTO, responseDTO);
		// S08924 call
//		responseDTO = individuDS.createModifyIndividual(oldrequestDTO);
		responseDTO = individuDS.createOrUpdateIndividual(requestDTO);
		// GET GIN AFTER CREATION
		return getGin(responseDTO);
	}

	/**
	 * UPDATE INDIVIDUAL DATA : Individual data, postal address, email, communication preferences
	 *
	 * @param request
	 * @throws JrafDomainException
	 * @throws BusinessErrorBlocBusinessException
	 * @throws BusinessException
	 */
	protected void updateIndividualData(CreateUpdateIndividualRequest request, CreateUpdateIndividualResponse response) throws JrafDomainException, BusinessErrorBlocBusinessException {
		if (request.getRequestor() == null || request.getProcess() == null) {
			updateIndividualDataByDefault(request, response);
		} else {
			switch (ProcessEnum.valueOf(request.getProcess())) {
			case A:
				IndividuDTO foundIndividual = null;

				IndividuDTO prospectDTOTransform = null;

				// On recherche si un individu existe avec le GIN
				// ET on recherche coté prospect si le GIN existe
				if (request.getIndividualRequest() != null && request.getIndividualRequest().getIndividualInformations() != null && request.getIndividualRequest().getIndividualInformations().getIdentifier() != null) {
					foundIndividual = individuDS.getIndProspectByGin(request.getIndividualRequest().getIndividualInformations().getIdentifier());

					// Si individu trouvé on update
					if (foundIndividual != null && !foundIndividual.getType().equals(ProcessEnum.W.getCode())) {
						request.setProcess(null);
						
						// Transform email
						if (request.getEmailRequest() != null) {
							List<EmailRequest> updatedEmailList = new ArrayList<EmailRequest>();
							for (EmailRequest emailWS : request.getEmailRequest()) {
								EmailRequest updatedEmail = IndividuRequestTransformV7.transformEmailProspectToEmailIndividu(emailWS);
								updatedEmailList.add(updatedEmail);
							}
							request.getEmailRequest().clear();
							request.getEmailRequest().addAll(updatedEmailList);
						}
						
						request.setPreferenceDataRequest(IndividuTransformV7.dtoProspectToDtoPreference(request));
						alertHelper.updateIndividualForAlert(request, response);
					}
					// Sinon si c'est un prospect on l'update
					else if (foundIndividual != null && foundIndividual.getType().equals(ProcessEnum.W.getCode())) {
						prospectDTOTransform = IndividuTransformV7.wsdlProspectToProspectDto(request);

						// Si pas d'individu trouvé et si le prospect existe on l'update
						if (!"X".equals(foundIndividual.getStatutIndividu())) {
							prospectDTOTransform = IndividuTransformV7.wsdlSignatureToDtoCreation(prospectDTOTransform, request.getRequestor());
							prospectDTOTransform = IndividuTransformV7.wsdlSignatureToDtoUpdate(prospectDTOTransform, request.getRequestor());
							chekProspectMandatory(prospectDTOTransform, foundIndividual);

							try {
								// update prospect
								createOrUpdateProspectHelperV7.updateProspect(prospectDTOTransform, foundIndividual, response, request);

								// update prospect's telecom
								createOrUpdateProspectHelperV7.updateProspectTelecoms(request, prospectDTOTransform.getSgin());

							} catch (Exception e){
								log.error(LoggerUtils.buidErrorMessage(e), e);
								throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
							}
						}
						else {
							// Remettre le prospect en Valide
							prospectDTOTransform.setStatutIndividu("V");
							prospectDTOTransform = IndividuTransformV7.wsdlSignatureToDtoCreation(prospectDTOTransform, request.getRequestor());
							prospectDTOTransform = IndividuTransformV7.wsdlSignatureToDtoUpdate(prospectDTOTransform, request.getRequestor());
							chekProspectMandatory(prospectDTOTransform, foundIndividual);

							try {
								// update prospect
								createOrUpdateProspectHelperV7.updateProspect(prospectDTOTransform, foundIndividual, response, request);

								// update prospect's telecom
								createOrUpdateProspectHelperV7.updateProspectTelecoms(request, prospectDTOTransform.getSgin());

							} catch (Exception e){
								log.error(LoggerUtils.buidErrorMessage(e), e);
								throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
							}
						}
					}
					else {
						throw new NotFoundException("Individual not found");
					}
				}
				else {
					throw new MissingParameterException("Missing individual identifier");
				}

				break;
			case T:
				// Traveler update - La mise à jour d un Traveler est effectuée maintenant dans le CreateOrUpdateRoleContract
				// IndividuDTO indDTO = IndividuRequestTransformV7.transformRequestToIndividuDTO(request);
				// Business rules to define for updateATravelerIndividual
				// individuDS.updateAnIndividualTraveler
				// For instance nothing to do (existing individual is more reliable)
				
				// REPIND-1023:
				// Exception not found must be trown if the gin is not found for process T
				if (request!= null && request.getIndividualRequest() != null &&	
					request.getIndividualRequest().getIndividualInformations() != null &&
					request.getIndividualRequest().getIndividualInformations().getIdentifier() != null) {
					
					String gin = request.getIndividualRequest().getIndividualInformations().getIdentifier();
					if (!individuDS.isExisting(gin)) {
						throw new NotFoundException("GIN not found ");
					}
				}
				
				break;
			case E:
				// External Identifier Update - On ne met rien à jour dans l'individu sur on ne change qu'un External Only de type E
				// TODO : Mettre a jour le CIVILITE, NOM et PRENOM
				IndividuDTO indDTO = IndividuRequestTransformV7.transformRequestToIndividuDTO(request);

				IndividuRequestTransformV7.addSignatureToIndividuDTO(request.getRequestor(), indDTO);

				individuDS.updateAnIndividualExternal(indDTO);

				// indDTO = individuDS.get(indDTO)

				break;
			case W:
				IndividuDTO findIndividu = null;

				IndividuDTO individuProspectDTO = null;

				// On recherche si un individu existe avec le GIN et statut différent de X
				// ET on recherche coté prospect si le GIN existe
				findIndividu = individuDS.getIndProspectByGin(request.getIndividualRequest().getIndividualInformations().getIdentifier());

				// Si individu trouvé on update
				if (findIndividu != null && !findIndividu.getType().equals(ProcessEnum.W.getCode())) {
					request.setProcess(null);
					
					// Transform email
					if (request.getEmailRequest() != null) {
						List<EmailRequest> updatedEmailList = new ArrayList<EmailRequest>();
						for (EmailRequest emailWS : request.getEmailRequest()) {
							EmailRequest updatedEmail = IndividuRequestTransformV7.transformEmailProspectToEmailIndividu(emailWS);
							updatedEmailList.add(updatedEmail);
						}
						request.getEmailRequest().clear();
						request.getEmailRequest().addAll(updatedEmailList);
					}

					request.setPreferenceDataRequest(IndividuTransformV7.dtoProspectToDtoPreference(request));
					updateIndividualDataByDefault(request, response);
				}
				// Si pas d'individu trouvé et si le prospect existe on l'update
				else if (findIndividu != null && findIndividu.getType().equals(ProcessEnum.W.getCode())) {

					individuProspectDTO = IndividuTransformV7.wsdlProspectToProspectDto(request);

					if (!"X".equals(findIndividu.getStatutIndividu())) {
						individuProspectDTO = IndividuTransformV7.wsdlSignatureToDtoCreation(individuProspectDTO, request.getRequestor());
						individuProspectDTO = IndividuTransformV7.wsdlSignatureToDtoUpdate(individuProspectDTO, request.getRequestor());
						chekProspectMandatory(individuProspectDTO, findIndividu);

						try {
							// update prospect
							createOrUpdateProspectHelperV7.updateProspect(individuProspectDTO, findIndividu, response, request);

							// update prospect's telecom
							createOrUpdateProspectHelperV7.updateProspectTelecoms(request, individuProspectDTO.getSgin());

						}  catch (MaximumSubscriptionsException e){
							throw new MaximumSubscriptionsException(e.getMessage());
						} catch (Exception e){
							log.error(LoggerUtils.buidErrorMessage(e), e);
							//throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
							throw new JrafDomainException(e.getMessage());
						}
					}
					else {
						// Remettre le prospect en Valide
						individuProspectDTO.setStatutIndividu("V");
						individuProspectDTO = IndividuTransformV7.wsdlSignatureToDtoCreation(individuProspectDTO, request.getRequestor());
						individuProspectDTO = IndividuTransformV7.wsdlSignatureToDtoUpdate(individuProspectDTO, request.getRequestor());
						chekProspectMandatory(individuProspectDTO, findIndividu);

						try {
							// update prospect
							createOrUpdateProspectHelperV7.updateProspect(individuProspectDTO, findIndividu, response, request);

							// update prospect's telecom
							createOrUpdateProspectHelperV7.updateProspectTelecoms(request, individuProspectDTO.getSgin().toString());

						} catch (Exception e){
							log.error(LoggerUtils.buidErrorMessage(e), e);
							//throw businessExceptionHelperV7.createBusinessException(BusinessErrorCodeEnum.ERROR_905, null);
							throw new JrafDomainException(e.getMessage());
						}
					}
				}
				else {
					throw new NotFoundException("Individual not found");
				}

				break;

			default:
				updateIndividualDataByDefault(request, response);
				break;
			}
		}

	}

	private void updateIndividualDataByDefault(CreateUpdateIndividualRequest request, CreateUpdateIndividualResponse response) throws JrafDomainException {
		IndividuDTO individuDTO = IndividuRequestTransformV7.transformToIndividuDTO(request);
		SignatureDTO signatureAPP = IndividuRequestTransformV7.transformToSignatureAPP(request.getRequestor());
		List<PostalAddressDTO> postalAddressDTOList = IndividuRequestTransformV7.transformToPostalAddressDTO(request.getPostalAddressRequest(), signatureAPP);
		List<EmailDTO> emailDTOList = IndividuRequestTransformV7.transformToEmailDTO(request.getEmailRequest());

		//accountDS.updateIndividual(individuDTO, postalAddressDTOList, emailDTOList, null, signatureAPP);

		List<CommunicationPreferencesDTO> comPrefsDTOList = IndividuRequestTransformV7.transformToComPrefsDTO(request.getComunicationPreferencesRequest(),request.getRequestor());
			
		communicationPreferencesHelper.crmOptinComPrefs(comPrefsDTOList, response);

		// GETTING UPDATE MODE FOR COMMUNICATION PREFERENCES
		UpdateModeEnum updateModeCommPrefs = UpdateModeEnum.getEnum(request.getUpdateCommunicationPrefMode());
		// GETTING NEWSLETTER MEDIASENDING FLAG
		String newsletterMediaSending = StringUtils.isNotEmpty(request.getNewsletterMediaSending()) ? request.getNewsletterMediaSending() : "";
		// GETTING MYACCOUNT STATUS
		String status = StringUtils.isNotEmpty(request.getStatus()) ? request.getStatus() : null;
		
		// CHECKING VALIDITY RELATED TO COMMUNICATIONS PREFERENCES
		communicationPreferencesHelper.checkCommPrefList(comPrefsDTOList, updateModeCommPrefs);
		
		// Preferences
		List<PreferenceDTO> preferenceDTOList = IndividuRequestTransformV7.transformToPreferenceDTO(request.getPreferenceDataRequest());

		if (preferenceDTOList == null) {
			preferenceDTOList = new ArrayList<>();
		}
		// Marketing data for travel doc
		IndividuRequestTransformV7.transformBDMToPreferenceDTO(preferenceDTOList, request.getMarketingDataRequest(), signatureAPP);

		individuDTO.setPreferenceDTO(preferenceDTOList);

		ReturnDetailsDTO resultDetails = myAccountDS.updateMyAccountCustomer(individuDTO, postalAddressDTOList, null, emailDTOList, null, comPrefsDTOList, newsletterMediaSending, signatureAPP, status, updateModeCommPrefs.toString(), false, true, null, null);
		log.info(resultDetails.toString());

		if(resultDetails != null && resultDetails.getDetailedCode() != null) {
			InformationResponse informationResponse = new InformationResponse();
			Information info = new Information();
			info.setInformationCode(""+resultDetails.getDetailedCode());
			info.setInformationDetails(resultDetails.getLabelCode());
			informationResponse.setInformation(info);
			response.getInformationResponse().add(informationResponse);
		}
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

	private void chekProspectMandatory(IndividuDTO updateProspect, IndividuDTO prospectDTO)  {
		if (prospectDTO.getPostaladdressdto() == null && updateProspect.getPostaladdressdto() != null) {
			if (updateProspect.getPostaladdressdto().get(0) != null) {
				if (updateProspect.getPostaladdressdto().get(0).getScode_medium() == null || updateProspect.getPostaladdressdto().get(0).getSstatut_medium() == null) {
					IndividuTransformV7.wsdlStatusToDtoCreation(updateProspect);
				}
			}
		}
		if (prospectDTO.getTelecoms() == null && updateProspect.getTelecoms() != null) {
			if (updateProspect.getTelecoms().iterator().next() != null) {
				if(updateProspect.getTelecoms().iterator().next().getScode_medium() == null || updateProspect.getTelecoms().iterator().next().getSstatut_medium() == null || updateProspect.getTelecoms().iterator().next().getSterminal() == null) {
					IndividuTransformV7.wsdlStatusToDtoCreation(updateProspect);
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
