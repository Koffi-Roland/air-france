package com.airfrance.repind.service.ws.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MaximumSubscriptionsException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.external.ExternalIdentifierLinkedToDifferentIndividualException;
import com.airfrance.ref.exception.jraf.*;
import com.airfrance.ref.type.*;
import com.airfrance.repind.dao.individu.MarketLanguageRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.adresse.Usage_mediumDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.PrefilledNumbersDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.createmodifyindividual.AdressePostaleDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.*;
import com.airfrance.repind.dto.individu.myaccountcustomerdata.ReturnDetailsDTO;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.ws.MarketLanguageDTO;
import com.airfrance.repind.dto.ws.*;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.exception.AddressNormalizationCustomException;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.consent.internal.ConsentDS;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.individu.internal.UsageClientsDS;
import com.airfrance.repind.service.reference.internal.RefTypExtIDDS;
import com.airfrance.repind.service.ws.internal.helpers.*;
import com.airfrance.repind.util.EmailUtils;
import com.airfrance.repind.util.transformer.IndividuTransform;
import com.airfrance.repind.util.transformer.S08924RequestTransform;
import com.airfrance.repindutf8.service.utf.internal.UtfException;
import com.airfrance.repindutf8.util.SicUtf8StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.*;

@Service
public class CreateOrUpdateIndividualDS{

	private static final String ERROR_MULTIPLE_INDIVIDUAL_FOUND = "Multiple individual found: ";

	private Log LOGGER = LogFactory.getLog(CreateOrUpdateIndividualDS.class);

	@Autowired
	protected IndividuDS individuDS;

	@Autowired
	protected MyAccountDS myAccountDS;

	@Autowired
	protected ExternalIdentifierDS externalIdentifierDS;

	@Autowired
	protected DelegationDataDS delegationDataDS;

	@Autowired
	protected RefTypExtIDDS refTypExtIDDS;

	@Autowired
	protected EmailDS emailDS;

	@Autowired
	private AccountDataDS accountDataDS;

	@Autowired
	protected TelecomDS telecomDS;

	@Autowired
	private UsageClientsDS usageClientsDS;
	
	@Autowired
	private ConsentDS consentDS;

	@Autowired
	protected AddressNormalizationHelper addressNormalizationHelper;

	@Autowired
	protected CommunicationPreferencesHelper communicationPreferencesHelper;
	
	@Autowired
	protected CreateOrUpdateAnIndividualHelper createOrUpdateAnIndividualHelper;

	@Autowired
	protected CreateOrUpdateProspectHelper createOrUpdateProspectHelper;
	
	@Autowired
	protected CreateOrUpdateKidSoloHelper createOrUpdateKidSoloHelper;

	@Autowired
	protected PostalAddressHelper postalAddressHelper;

	@Autowired
	protected PrefilledNumbersHelper prefilledNumbersHelper;

	@Autowired
	protected AlertHelper alertHelper;

	@Autowired
	protected UltimateHelperV8 ultimateHelper;

	@Autowired
	private UtfHelper utfHelper;
	
	@Autowired
	private MarketLanguageRepository marketLanguageRepository;

	private final String WEBSERVICE_IDENTIFIER = "W000442";
	private final String SITE = "VLB";

	public CreateModifyIndividualResponseDTO createOrUpdateV8(CreateUpdateIndividualRequestDTO request, CreateModifyIndividualResponseDTO response)
			throws JrafDomainException, UltimateException, UtfException, ParseException, JrafApplicativeException {

		// Normalize SC input & save UTF data REPIND-579
		IndividualInformationsDTO individualInformation = new IndividualInformationsDTO();
		if (request.getIndividualRequestDTO() != null) {
			individualInformation = request.getIndividualRequestDTO().getIndividualInformationsDTO();
		}
		final String utfFirstNameSC = individualInformation.getFirstNameSC();
		final String utfLastNameSC = individualInformation.getLastNameSC();
		UtfHelper.NormalizeSCNames(individualInformation);

		// GETTING GIN FROM INPUT -> IF DEFINED IT IS AN UPDATE, ELSE THIS IS A CREATION
		String gin = getGin(request);

		// GET application code from input before potential modification by PostalAddress management below
		String codeAppliMetier = "";
		if (request.getRequestorDTO() != null && request.getRequestorDTO().getApplicationCode() != null) {
			 codeAppliMetier = request.getRequestorDTO().getApplicationCode();
		}


		// INDIVIDUAL CREATION (GIN not filled) -> S08924
		boolean isCreated = false;
		response.setSuccess(true);

		if(StringUtils.isEmpty(gin)) {
			
			response = create(request, response);
			gin = response.getGin();
			LOGGER.info("#### CREATED " + gin);
			isCreated = true;
		}
		// INDIVIDUAL UPDATE (GIN filled) -> S08924
		else {
			
			response = update(request, response);
			LOGGER.info("#### UPDATED " + gin);
		}

		/* ================= UTF STRINGS PROCESSING ========== */
		// REPIND-1767 : Add the detection process in order to store with an UTF8 detection before
		if (request != null) {
		    if (SicUtf8StringUtils.isNonASCII(utfFirstNameSC + utfLastNameSC) || SicUtf8StringUtils.isHTML(utfFirstNameSC) || SicUtf8StringUtils.isHTML(utfLastNameSC)) {
		    	utfHelper.processIndividualOnUTF8(request.getUtfRequestDTO(), gin, request.getRequestorDTO(), utfFirstNameSC, utfLastNameSC);	
		    }
	
		    if (request.getEmailRequestDTO() != null) {
			    for (EmailRequestDTO uftRequestEmail : request.getEmailRequestDTO()) {	    	
			    	if (uftRequestEmail != null &&  uftRequestEmail.getEmailDTO() != null) {	    		
			    		String utfEmailSC = uftRequestEmail.getEmailDTO().getEmail();
			    		if (SicUtf8StringUtils.isNonASCII(utfEmailSC)) {
			    			utfHelper.processEmailOnUTF8(request.getUtfRequestDTO(), gin, request.getRequestorDTO(), utfEmailSC);	
			    		}
			    	}
			    }
		    }
	
		    if (request.getPostalAddressRequestDTO() != null) {
			    for (PostalAddressRequestDTO uftRequestPostalAddress : request.getPostalAddressRequestDTO()) {	    	
			    	if (uftRequestPostalAddress != null &&  uftRequestPostalAddress.getPostalAddressContentDTO() != null) {
			    		String uftPostalAddressCorpo = uftRequestPostalAddress.getPostalAddressContentDTO().getCorporateName();
			    		String uftPostalAddressNumAndStreet = uftRequestPostalAddress.getPostalAddressContentDTO().getNumberAndStreet();
			    		String uftPostalAddressAdditionalInfo = uftRequestPostalAddress.getPostalAddressContentDTO().getAdditionalInformation();
			    		String uftPostalAddressZipCode = uftRequestPostalAddress.getPostalAddressContentDTO().getZipCode();
			    		String uftPostalAddressDistrict = uftRequestPostalAddress.getPostalAddressContentDTO().getDistrict();
			    		String uftPostalAddressState = uftRequestPostalAddress.getPostalAddressContentDTO().getStateCode();
			    		String uftPostalAddressCity = uftRequestPostalAddress.getPostalAddressContentDTO().getCity();
			    		
			    		String uftPostalAddress = uftPostalAddressCorpo + uftPostalAddressNumAndStreet +uftPostalAddressState + uftPostalAddressCity + uftPostalAddressAdditionalInfo + uftPostalAddressZipCode + uftPostalAddressDistrict;

			    		if (SicUtf8StringUtils.isNonASCII(uftPostalAddress)) {
//			    			        CORPO           NUM STREET  ADD INFO      CITY    ZIPCODE STATE DISTRICT  COUNTRY
			    			utfHelper.processPostalAddressOnUTF8(request.getUtfRequestDTO(), gin, request.getRequestorDTO(),
			    					uftPostalAddressCorpo, uftPostalAddressNumAndStreet, uftPostalAddressAdditionalInfo, uftPostalAddressCity, uftPostalAddressZipCode, uftPostalAddressState, uftPostalAddressDistrict);	
			    		}
			    	}
			    }
		    }
		}
	
		/* ================= USAGE CLIENTS PROCESSING ============= */
		usageClientsDS.add(gin, codeAppliMetier);

		SignatureDTO signatureAPP = IndividuTransform.transformToSignatureAPP(request.getRequestorDTO());
		SignatureDTO signatureWS = IndividuTransform.transformToSignatureWS(WEBSERVICE_IDENTIFIER, SITE);
							
		if (request.getProcess() == null || !ProcessEnum.W.getCode().equalsIgnoreCase(request.getProcess())) {

			// ===== TELECOM DATA ====================================================================

			// NORMALIZE TELECOM
			List<TelecomsDTO> telecomFromWSList = normalizeTelecoms(request);
			telecomDS.updateNormalizedTelecomWithUsageCode(gin, telecomFromWSList, signatureAPP, signatureWS);

			// ===== EMAILS ==========================================================================
			List<EmailDTO> emailListFromWS = IndividuTransform.transformToEmailDTO(request.getEmailRequestDTO());
			EmailUtils.setDefaultValueEmails(emailListFromWS);
			emailDS.updateEmail(gin, emailListFromWS, signatureAPP);
			accountDataDS.updateLoginEmail(gin, emailListFromWS, signatureAPP, request.getStatus());

			// ===== POSTAL ADDRESS ==================================================================
			List<PostalAddressDTO> postalAddressList = null;
			
			if (request.getPostalAddressRequestDTO() != null) {
				postalAddressList = new ArrayList<>();
				boolean adrHandlingSuccess;
				
				for (PostalAddressRequestDTO parDto : request.getPostalAddressRequestDTO()) {
					postalAddressList.add(PostalAddressRequestTransform.transformRequestToDto(parDto, signatureAPP));
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
					LOGGER.error("Fail to create or update postal address");
					
					InformationResponseDTO infosResp = new InformationResponseDTO();
					InformationDTO information = new InformationDTO();
					
					information.setInformationCode("140");
					information.setInformationDetails("Fail to create or update postal address");
					infosResp.setInformation(information);
					if (response.getInformationResponse() != null) {
						response.getInformationResponse().add(infosResp);
					}
					else {
						Set<InformationResponseDTO> infosList = new HashSet<InformationResponseDTO>();
						infosList.add(infosResp);
						response.setInformationResponse(infosList);
					}

					// Custom Repsonse for error normalization
					response.getPostalAddressResponse().add(adrBlock);

					return response;
				}
			}

			// ===== DELEGATION DATA =================================================================

			String managingCompany = null;
			if(request.getRequestorDTO() != null) {
				managingCompany = request.getRequestorDTO().getManagingCompany();
			}

			List<DelegationDataDTO> delegateList = IndividuTransform.transformToDelegate(request.getAccountDelegationDataRequestDTO(), gin);
			List<DelegationDataDTO> delegatorList = IndividuTransform.transformToDelegator(request.getAccountDelegationDataRequestDTO(), gin);

			// =================================================
			// ========= ULTIMATE ==============================
			// =================================================
			ultimateHelper.createUltimateFamilyLinks(delegatorList, signatureAPP, managingCompany, gin);
			ultimateHelper.createUltimateFamilyLinks(delegateList, signatureAPP, managingCompany, gin);

			List<DelegationDataDTO> nonUltimateDelegateList = UltimateHelperV8.NonUltimateDelegations(delegateList);
			List<DelegationDataDTO> nonUltimateDelegatorList = UltimateHelperV8.NonUltimateDelegations(delegatorList);

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

			LOGGER.info(gin);
			// ===== EXTERNAL IDENTIFIERS ============================================================
			List<com.airfrance.repind.dto.external.ExternalIdentifierDTO> externalIdentifierListFromWS = IndividuTransform.transformToExternalIdentifierDTO(request.getExternalIdentifierRequestDTO());
			externalIdentifierDS.updateExternalIdentifier(gin, externalIdentifierListFromWS, signatureAPP, request.getProcess());

			// ===== PREFILLED NUMBERS ===============================================================

			UpdateModeEnum updateModePrefilled = UpdateModeEnum.getEnum(request.getUpdatePrefilledNumbersMode());
			List<PrefilledNumbersDTO> prefilledNumbersDTO = IndividuTransform.wsdlTOPrefilledNumbersdto(request.getPrefilledNumbersRequestDTO());
			processPrefilledNumbers(gin, prefilledNumbersDTO, updateModePrefilled, signatureAPP);
			
			// ===== ALERT ============================================================================
			callAlertProcessing(gin, request, signatureAPP);
		}

		// ===== BUILDING RESPONSE ==================================================================
		if (isCreated && consentDS.ginHasNoConsents(gin)) {
			
			//REPIND-1647: Create Default Consent when creating new individual
			try {
				consentDS.createDefaultConsents(gin, request.getRequestorDTO().getSignature());
			} catch (Exception e) {
				LOGGER.error(e);
			}
			
			response.setGin(gin);
		}
		
		return response;
		
	}
	
	/**
	 * NORMALIZE POSTAL ADDRESSES : checking postal addresses and normalization step via SOFT COMPUTING
	 *
	 * @param request
	 * @throws JrafDomainException
	 */
	protected CreateModifyIndividualResponseDTO normalizePostalAddress(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {

		// GET POSTAL ADDRESS TO BE NORMALIZED
		Set<AdressePostaleDTO> adressePostaleDTOSet = S08924RequestTransform.transformToAdressePostaleDTO(request.getPostalAddressRequestDTO());

		// SOFT RESPONSE
		return addressNormalizationHelper.checkNormalizedAddress(adressePostaleDTOSet);
	}

	// Vérifie si il y a des alertes dans la request à traiter pour Insert/Update
	private void callAlertProcessing(String gin, CreateUpdateIndividualRequestDTO request, SignatureDTO signature) throws JrafDomainException, ParseException {
		if (request != null && request.getCommunicationPreferencesRequestDTO() != null && !request.getCommunicationPreferencesRequestDTO().isEmpty()) {
			alertHelper.processAlert(gin, request, signature);
		}
	}

	/**
	 * NORMALIZE TELECOMS : checking telecom data and normalization step
	 *
	 * @param request
	 * @throws JrafDomainException
	 */
	protected List<TelecomsDTO> normalizeTelecoms(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {

		// GET TELECOM TO BE NORMALIZED
		List<TelecomsDTO> telecomFromWSList = IndividuTransform.transformToTelecomsDTO(request.getTelecomRequestDTO());

		// CHECKING INPUT : V6 ONLY: USAGE CODE MANAGEMENT
		telecomDS.checkTelecomsByUsageCode(telecomFromWSList);

		// TELECOM NORMALIZATION STEP
		List<TelecomsDTO> normalizedTelecomList = telecomDS.normalizePhoneNumber(telecomFromWSList);

		return normalizedTelecomList;
	}
	@Transactional(rollbackFor = JrafDomainRollbackException.class)
	public CreateModifyIndividualResponseDTO create(CreateUpdateIndividualRequestDTO request, CreateModifyIndividualResponseDTO response)
			throws JrafDomainException, JrafApplicativeException {
		String gin = null;
		
		// TODO : Tester le create avec deux Emails dans la request
		
		// CREATE/UPDATE AN INDIVIDUAL
		if (request.getRequestorDTO() == null || request.getProcess() == null) {
			gin = createIndividualDataByDefault(request, response);					// Individual by default type = NULL
		} else {
			switch (ProcessEnum.valueOf(request.getProcess())) {
			case T:
				// Traveler creation OK
				gin = createIndividualDataTraveler(request);

				break;
			case E:
				gin = createIndividualDataExternalIdentifier(request);

				break;
			case A:
				response = createIndividualDataAlert(request, response);

				break;
			case W:
				response = createIndividualDataWhiteWinger(request, response);
				
				break;
			case K:				
				gin = createIndividualDataKidSolo(request);
				
				break;
				// ************ end new process ***************
			case C:
				// REPIND-1808 : Create a Caller for CCP
				gin = createIndividualDataCaller(request);

				break;
			case I:
			default:
				gin = createIndividualDataByDefault(request, response);			// Individual by default type = I
				break;
			}
		}
		
		if(response != null && StringUtils.isBlank(response.getGin())) {
			response.setGin(gin);
		}
		
		return response;
		
	}

	public CreateModifyIndividualResponseDTO update(CreateUpdateIndividualRequestDTO request, CreateModifyIndividualResponseDTO response)
			throws JrafDomainException {
		if (request.getRequestorDTO() == null || request.getProcess() == null) {
			response = updateIndividualDataByDefault(request, response);
		} else {
			switch (ProcessEnum.valueOf(request.getProcess())) {
			case A:
				response = updateIndividualDataAlert(request, response);

				break;
			case T:
				// REPIND-1023:
				// Exception not found must be trown if the gin is not found for process T
				if (request!= null && request.getIndividualRequestDTO() != null &&	
					request.getIndividualRequestDTO().getIndividualInformationsDTO() != null &&
					request.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier() != null) {
					
					String gin = request.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier();
					if (!individuDS.isExisting(gin)) {
						throw new NotFoundException("GIN not found ");
					}
				}
				break;
			case E:
				String gin = updateIndividualDataExternalIdentifier(request);
				
				break;
			case W:
				response = updateIndividualDataWhiteWinger(request, response);

				break;
				
			case K:
				updateIndividualDataKidSolo(request);
				break;
				
			// REPIND-1490 : To use for crisis events
			case H:	
				updateIndividualDataCrisisEvent(request);
				break;
				
			default:
				response = updateIndividualDataByDefault(request, response);
				break;
			}
		}
		
		return response;
		
	}
	

	private String createIndividualDataByDefault(CreateUpdateIndividualRequestDTO requestDTO, CreateModifyIndividualResponseDTO responseDTO) throws JrafApplicativeException, JrafDomainException {
		
		LOGGER.debug("requestDTO : " + requestDTO);

		// PRE-CREATION OF INDIVIDUAL (FB enrollment / upgrade B2C)
		createOrUpdateAnIndividualHelper.prepareIndividualCreation(requestDTO, responseDTO);

		// S08924 call
		// REPIND-1270 ==> No more call to S08924
		responseDTO = individuDS.createOrUpdateIndividual(requestDTO);
		
		// GET GIN AFTER CREATION
		return getGin(responseDTO);
	}

	private String getGin(CreateUpdateIndividualRequestDTO request) {

		if(request==null || request.getIndividualRequestDTO()==null || request.getIndividualRequestDTO().getIndividualInformationsDTO()==null) {
			return null;
		}

		return request.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier();
	}

	private String getGin(CreateModifyIndividualResponseDTO responseDTO) {

		if(responseDTO==null || responseDTO.getIndividu()==null) {
			return null;
		}
		
		responseDTO.setGin(responseDTO.getIndividu().getNumeroClient());
		return responseDTO.getIndividu().getNumeroClient();
	}
	
	// Creation de Traveler
	private String createIndividualDataTraveler(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {
		
		IndividuDTO individuDTO = IndividuTransform.transformRequestToIndividuDTO(request , individuDS);
		
		return individuDS.createAnIndividualTraveler(individuDTO);
	}

	// Creation de Caller
	private String createIndividualDataCaller(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {
		
		IndividuDTO individuDTO = IndividuTransform.transformRequestToIndividuDTO(request , individuDS);
		
		return individuDS.createAnIndividualCaller(individuDTO);
	}

	private String createIndividualDataExternalIdentifier(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {
		
		IndividuDTO individuDTO = IndividuTransform.transformRequestToIndividuDTO(request , individuDS);
		
		List <ExternalIdentifierRequestDTO> externalIdentifierRequest = request.getExternalIdentifierRequestDTO();
		
		String gin = "";
		// Tester de la presence d'un GIGYA dans la Request
		if (externalIdentifierRequest != null && externalIdentifierRequest.size() > 0) {
			
			// Recherche d'un GIGYA existant sur SOCIAL NETWORK DATA
			String ginFoundForExistingSocialNetworkData = existSocialNetWorkData(externalIdentifierRequest);

			if (ginFoundForExistingSocialNetworkData == null || "".equals(ginFoundForExistingSocialNetworkData)) {

				// Recherche d'un GIGYA existant sur EXTERNAL IDENTIFIER
				String ginFoundForExistingExternalIdentifier = existExternalIdentifier(externalIdentifierRequest);

				if (ginFoundForExistingExternalIdentifier == null || "".equals(ginFoundForExistingExternalIdentifier)) {

					// On lance la creation
					gin = individuDS.createAnIndividualExternal(individuDTO);
				} else {
					gin = ginFoundForExistingExternalIdentifier;
				}
			} else {
				gin = ginFoundForExistingSocialNetworkData;
			}

		} else {
			// Par defait, on lance tout de meme la creation pour les autres External Identifier non GIGYA
			gin = individuDS.createAnIndividualExternal(individuDTO);
		}
		return gin;
	}
	
	private String updateIndividualDataExternalIdentifier(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {

		// External Identifier Update - On ne met rien à jour dans l'individu sur on ne change qu'un External Only de type E
		IndividuDTO indDTO = IndividuTransform.transformRequestToIndividuDTO(request , individuDS);

		IndividuTransform.addSignatureToIndividuDTO(request.getRequestorDTO(), indDTO);

		return individuDS.updateAnIndividualExternal(indDTO);

	}

	private CreateModifyIndividualResponseDTO createIndividualDataAlert(CreateUpdateIndividualRequestDTO request, CreateModifyIndividualResponseDTO response) throws JrafDomainException {
		String gin = null;
		
		/*** Recherche d'individu(s) ou de prospect existants pour cet email (reconciliation) ***/
		ProspectIndividuDTO prospectIndivDTO = createOrUpdateProspectHelper.findIndividual(request);
		IndividuDTO findProspectDTO = null;
		// Si individu trouvé dans SIC, on update et on retourne avec le gin
		if (prospectIndivDTO != null) {
			CreateUpdateIndividualRequestDTO updateIndividu = createOrUpdateProspectHelper.updateIndividualProspect(prospectIndivDTO, request);
			request.setProcess(null);
			alertHelper.updateIndividualForAlert(updateIndividu);
			gin = prospectIndivDTO.getGin().toString();
			
		} else {
			// Si individu non trouvé on recherche coté prospects
			findProspectDTO = createOrUpdateProspectHelper.searchProspectByEmail(request);

			/*** Si on retrouve un prospect on update et on retourne le gin***/
			if (findProspectDTO != null) {
				IndividuDTO updateProspect = null;
				if (!"X".equals(findProspectDTO.getStatutIndividu())) {
					updateProspect = IndividuTransform.transformToProspectDTO(request);
					updateProspect = IndividuTransform.transformSignatureToDtoProspectCreation(updateProspect, request.getRequestorDTO());
					updateProspect = IndividuTransform.transformSignatureToDtoProspectUpdate(updateProspect, request.getRequestorDTO());
					checkProspectMandatory(updateProspect, findProspectDTO);

					try {
						// update prospect
						gin = createOrUpdateProspectHelper.updateProspect(updateProspect, findProspectDTO, response, request);

						// update prospect's telecom
						createOrUpdateProspectHelper.updateProspectTelecoms(request, gin);
  
					}  catch (MaximumSubscriptionsException e){
						throw new MaximumSubscriptionsException(e.getMessage());
					} 
				}
				else {
					throw new NotFoundException("Deleted prospect");
				}

				gin = findProspectDTO.getSgin().toString();
			} else {
				// Aucun individu trouvé, on crée un nouveau prospect
				IndividuDTO createProspect = null;
				createProspect = IndividuTransform.transformToProspectDTO(request);
				createProspect = IndividuTransform.transformStatusToDtoProspectCreation(createProspect);
				createProspect = IndividuTransform.transformSignatureToDtoProspectCreation(createProspect, request.getRequestorDTO());
				createProspect = IndividuTransform.transformSignatureToDtoProspectUpdate(createProspect, request.getRequestorDTO());
				CreateModifyIndividualResponseDTO cmir = createOrUpdateProspectHelper.createProspect(createProspect, response); 
				gin = cmir.getGin();
			}
		}
		
		// TODO : Centralizer cela en mettre le GIN dans la ReponseDTO lorsqu on là un peu plus haut
		// On met a jour le GIN dans la Response.
		if (response != null && gin != null && !"".equals(gin)) {
			response.setGin(gin);
		}
		
		return response;
	}
	
	private CreateModifyIndividualResponseDTO updateIndividualDataAlert(CreateUpdateIndividualRequestDTO request, CreateModifyIndividualResponseDTO response) throws JrafDomainException {
		IndividuDTO foundIndividual = null;

		IndividuDTO prospectDTOTransform = null;

		// On recherche si un individu existe avec le GIN
		// ET on recherche coté prospect si le GIN existe
		if (request.getIndividualRequestDTO() != null && request.getIndividualRequestDTO().getIndividualInformationsDTO() != null && request.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier() != null) {
			foundIndividual = individuDS.getIndProspectByGin(request.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier());

			// Si individu trouvé on update
			if (foundIndividual != null && !foundIndividual.getType().equals(ProcessEnum.W.getCode())) {
				request.setProcess(null);
				alertHelper.updateIndividualForAlert(request);
			}
			// Sinon si c'est un prospect on l'update
			else if (foundIndividual != null && foundIndividual.getType().equals(ProcessEnum.W.getCode())) {
				prospectDTOTransform = IndividuTransform.transformToProspectDTO(request);

				// Si pas d'individu trouvé et si le prospect existe on l'update
				if (!"X".equals(foundIndividual.getStatutIndividu())) {
					prospectDTOTransform = IndividuTransform.transformSignatureToDtoProspectCreation(prospectDTOTransform, request.getRequestorDTO());
					prospectDTOTransform = IndividuTransform.transformSignatureToDtoProspectUpdate(prospectDTOTransform, request.getRequestorDTO());
					checkProspectMandatory(prospectDTOTransform, foundIndividual);

					try {
						// update prospect
						createOrUpdateProspectHelper.updateProspect(prospectDTOTransform, foundIndividual, response, request);

						// update prospect's telecom
						createOrUpdateProspectHelper.updateProspectTelecoms(request, prospectDTOTransform.getSgin());

					}  catch (MaximumSubscriptionsException e){
						throw new MaximumSubscriptionsException(e.getMessage());
					} 
				}
				else {
					// Remettre le prospect en Valide
					prospectDTOTransform.setStatutIndividu("V");
					prospectDTOTransform = IndividuTransform.transformSignatureToDtoProspectCreation(prospectDTOTransform, request.getRequestorDTO());
					prospectDTOTransform = IndividuTransform.transformSignatureToDtoProspectUpdate(prospectDTOTransform, request.getRequestorDTO());
					checkProspectMandatory(prospectDTOTransform, foundIndividual);

					try {
						// update prospect
						createOrUpdateProspectHelper.updateProspect(prospectDTOTransform, foundIndividual, response, request);

						// update prospect's telecom
						createOrUpdateProspectHelper.updateProspectTelecoms(request, prospectDTOTransform.getSgin());

					}  catch (MaximumSubscriptionsException e){
						throw new MaximumSubscriptionsException(e.getMessage());
					} 
				}
			}
			else {
				throw new NotFoundException("Individual not found");
			}
		}
		else {	// UT : Cas impossible car on va dans le CREATE si tout est vide ou null  
			throw new MissingParameterException("Missing individual identifier");
		}
		
		return response;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public CreateModifyIndividualResponseDTO createIndividualDataWhiteWinger(CreateUpdateIndividualRequestDTO request, CreateModifyIndividualResponseDTO response) throws JrafDomainException {
		
		String gin = null;
		
		//  ************ new process ****************
		// REPIND-872: SFMC - Search by email + name
		ProspectIndividuDTO prospectIndivDto = null;
		IndividuDTO findProspectDto = null;
		
		if ("KL_PROSPECT".equals(request.getRequestorDTO().getContext())) {
			IndividuDTO indToFind = createOrUpdateProspectHelper.searchByMulticriteria(request);
			
			if(indToFind != null && indToFind.getType().equals("W")) {
				findProspectDto = indToFind;
			} else if(indToFind != null) {
				prospectIndivDto = new ProspectIndividuDTO();
				prospectIndivDto.setGin(indToFind.getSgin());
				if(indToFind.getEmaildto() != null && indToFind.getEmaildto().size() > 0) {
					prospectIndivDto.setEmail(indToFind.getEmaildto().iterator().next().getEmail());
				}
				if(indToFind.getAccountdatadto() != null) {
					prospectIndivDto.setCin(indToFind.getAccountdatadto().getFbIdentifier());
				}
				
			}
		// REPIND-1006 : We do not need to check reconciliation during INITIALISATION phase
		} else if ("KL_INIT".equals(request.getRequestorDTO().getContext())) {
			
			
		} else {
			/*** Recherche d'individu(s) ou de prospect existants pour cet email (reconciliation) ***/
			prospectIndivDto = createOrUpdateProspectHelper.findIndividual(request);
		}
		
		// Si individu reconcilié on fait un update
		if (prospectIndivDto != null) {
			CreateUpdateIndividualRequestDTO updateIndividu = createOrUpdateProspectHelper.updateIndividualProspect(prospectIndivDto, request);
			
			response = updateIndividualDataByDefault(updateIndividu, response);
			
			gin = prospectIndivDto.getGin().toString();
			
			// REPIND-765 : Update des données UTF si elle existe
			// update prospect's Utf
			createOrUpdateProspectHelper.updateUtfIndividu(utfHelper, request.getUtfRequestDTO(), gin, request.getRequestorDTO().getSignature(),request.getRequestorDTO().getSite());    			    			
	
		}
		// Si individu non reconcilié on recherche coté prospects
		else {
			// REPIND-1006 : We do not search for an existing individual - We are on INITIALISATION process
			if(!"KL_PROSPECT".equals(request.getRequestorDTO().getContext()) && !"KL_INIT".equals(request.getRequestorDTO().getContext())) {
				findProspectDto = createOrUpdateProspectHelper.searchProspectByEmail(request);
			}
		}
		/*** Si on retrouve un prospect on fait l'update ***/
		if (findProspectDto != null) {
			IndividuDTO updateProspect = null;
			if (!"X".equals(findProspectDto.getStatutIndividu())) {
				updateProspect = IndividuTransform.transformToProspectDTO(request);
				updateProspect = IndividuTransform.transformSignatureToDtoProspectCreation(updateProspect, request.getRequestorDTO());
				updateProspect = IndividuTransform.transformSignatureToDtoProspectUpdate(updateProspect, request.getRequestorDTO());
				checkProspectMandatory(updateProspect, findProspectDto);
	
				try {
					
					// REPIND-765 : Try to catch OverFlow Last/First name to store them on UTF8 part
					createOrUpdateProspectHelper.checkFirstNameLastNameOverFlow(updateProspect, request);
					
					// update prospect
					gin = createOrUpdateProspectHelper.updateProspect(updateProspect, findProspectDto, response, request);
					
					// update prospect's telecom
					createOrUpdateProspectHelper.updateProspectTelecoms(request, findProspectDto.getSgin());
	
					// REPIND-765 : Reconciliation d'un Prospect deja connu du RI
					// update prospect's Utf
					createOrUpdateProspectHelper.updateUtfIndividu(utfHelper, request.getUtfRequestDTO(), gin, request.getRequestorDTO().getSignature(),request.getRequestorDTO().getSite());					
					
				} catch (MaximumSubscriptionsException e){
					throw new MaximumSubscriptionsException(e.getMessage());
				}
			}
			else {
				throw new NotFoundException("Deleted prospect");
			}
		}
		else if (prospectIndivDto == null) {
			// Aucun individu trouvé, on crée un nouveau prospect
			IndividuDTO createProspect = mapRequestProspectToIndividuProspect(request);

			// REPIND-765 : Try to catch OverFlow Last/First name to store them on UTF8 part
			createOrUpdateProspectHelper.checkFirstNameLastNameOverFlow(createProspect, request);
			
			CreateModifyIndividualResponseDTO cmir = createOrUpdateProspectHelper.createProspect(createProspect, response); 
			gin = cmir.getGin();
	
			// Cas 1 : MORALES - New Prospect
			if (response != null) {
	
				InformationResponseDTO informationResponse = new InformationResponseDTO();
				InformationDTO info = new InformationDTO();
				info.setInformationCode(""+0);
				info.setInformationDetails("New prospect created.");
				informationResponse.setInformation(info);
				response.getInformationResponse().add(informationResponse);
			}
	
			createOrUpdateProspectHelper.updateProspectTelecoms(request, gin);
		}
		
		// On met a jour le GIN dans la Response.
		if (response != null && gin != null && !"".equals(gin)) {
			response.setGin(gin);
		}
		
		return response;
	}

	private IndividuDTO mapRequestProspectToIndividuProspect(CreateUpdateIndividualRequestDTO request) throws InvalidParameterException {
		IndividuDTO createProspect;
		createProspect = IndividuTransform.transformToProspectDTO(request);
		createProspect = IndividuTransform.transformStatusToDtoProspectCreation(createProspect);
		createProspect = IndividuTransform.transformSignatureToDtoProspectCreation(createProspect, request.getRequestorDTO());
		createProspect = IndividuTransform.transformSignatureToDtoProspectUpdate(createProspect, request.getRequestorDTO());

		if (!request.getCommunicationPreferencesRequestDTO().isEmpty() && !request.getCommunicationPreferencesRequestDTO().iterator().next().getCommunicationPreferencesDTO().getMarketLanguageDTO().isEmpty() && !createProspect.getCommunicationpreferencesdto().isEmpty() && !createProspect.getCommunicationpreferencesdto().iterator().next().getMarketLanguageDTO().isEmpty()) {

			MarketLanguageDTO marketLanguageDTO = request.getCommunicationPreferencesRequestDTO().iterator().next().getCommunicationPreferencesDTO().getMarketLanguageDTO().iterator().next();
			com.airfrance.repind.dto.individu.MarketLanguageDTO marketLanguageDTOTransform = createProspect.getCommunicationpreferencesdto().iterator().next().getMarketLanguageDTO().iterator().next();
			if (!CollectionUtils.isEmpty(marketLanguageDTO.getSignatureDTOList())) {
				for (com.airfrance.repind.dto.ws.SignatureDTO signatureDTO : marketLanguageDTO.getSignatureDTOList()) {
					if (signatureDTO.getSignatureType().equals("C")) {
						marketLanguageDTOTransform.setCreationSignature(signatureDTO.getSignature());
						marketLanguageDTOTransform.setCreationSite(signatureDTO.getSignatureSite());
						marketLanguageDTOTransform.setCreationDate(signatureDTO.getDate());
					} else if (signatureDTO.getSignatureType().equals("M")) {
						marketLanguageDTOTransform.setModificationSignature(signatureDTO.getSignature());
						marketLanguageDTOTransform.setModificationSite(signatureDTO.getSignatureSite());
						marketLanguageDTOTransform.setModificationDate(signatureDTO.getDate());
					}
				}
			}

		}
		return createProspect;
	}

	private CreateModifyIndividualResponseDTO updateIndividualDataWhiteWinger(CreateUpdateIndividualRequestDTO request, CreateModifyIndividualResponseDTO response) throws JrafDomainException {
		
		IndividuDTO findIndividu = null;

		IndividuDTO individuProspectDTO = null;

		// On recherche si un individu existe avec le GIN et statut différent de X
		// ET on recherche coté prospect si le GIN existe
		findIndividu = individuDS.getIndProspectByGin(request.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier());

		// Si individu trouvé on update
		if (findIndividu != null && !findIndividu.getType().equals(ProcessEnum.W.getCode())) {
			request.setProcess(null);
			
			// Transform email request
				
			if (request.getEmailRequestDTO() != null) {
				List<EmailRequestDTO> updatedEmailList = new ArrayList<EmailRequestDTO>();
				for (EmailRequestDTO emailWS : request.getEmailRequestDTO()) {
					EmailRequestDTO updatedEmail = null;
					
					
					if (emailWS.getEmailDTO() != null) {
						updatedEmail = new EmailRequestDTO();
						com.airfrance.repind.dto.ws.EmailDTO mail = new com.airfrance.repind.dto.ws.EmailDTO();
						//REPIND-1288: Put in lower case email
						if (!StringUtils.isEmpty(emailWS.getEmailDTO().getEmail())) {
							mail.setEmail(emailWS.getEmailDTO().getEmail().toLowerCase());
						} else {
							mail.setEmail(emailWS.getEmailDTO().getEmail());
						}
						
						if (emailWS.getEmailDTO().getEmailOptin() != null) {
							mail.setEmailOptin(emailWS.getEmailDTO().getEmailOptin());
						}
						else {
							mail.setEmailOptin(NATFieldsEnum.NONE.getValue());
						}
						
						if (emailWS.getEmailDTO().getMediumCode() != null) {
							mail.setMediumCode(emailWS.getEmailDTO().getMediumCode());
						}
						else {
							mail.setMediumCode(MediumCodeEnum.HOME.toString());
						}
						
						if (emailWS.getEmailDTO().getMediumStatus() != null) {
							mail.setMediumStatus(emailWS.getEmailDTO().getMediumStatus());
						}
						else {
							mail.setMediumStatus(MediumStatusEnum.VALID.toString());
						}
						
						updatedEmail.setEmailDTO(mail);
					}
					
					updatedEmailList.add(updatedEmail);
				}
				request.setEmailRequestDTO(updatedEmailList);
			}

			request.setPreferenceRequestDTO(IndividuTransform.getPreferenceRequestProspectToIndiv(request));
			updateIndividualDataByDefault(request, response);
		}
		// Si pas d'individu trouvé et si le prospect existe on l'update
		else if (findIndividu != null && findIndividu.getType().equals(ProcessEnum.W.getCode())) {

			individuProspectDTO = IndividuTransform.transformToProspectDTO(request);

			if (!"X".equals(findIndividu.getStatutIndividu())) {
				individuProspectDTO = IndividuTransform.transformSignatureToDtoProspectCreation(individuProspectDTO, request.getRequestorDTO());
				individuProspectDTO = IndividuTransform.transformSignatureToDtoProspectUpdate(individuProspectDTO, request.getRequestorDTO());
				checkProspectMandatory(individuProspectDTO, findIndividu);

				try {
					// update prospect
					createOrUpdateProspectHelper.updateProspect(individuProspectDTO, findIndividu, response, request);

					// update prospect's telecom
					createOrUpdateProspectHelper.updateProspectTelecoms(request, individuProspectDTO.getSgin());

				}  catch (MaximumSubscriptionsException e){
					throw new MaximumSubscriptionsException(e.getMessage());
				}
			}
			else {
				// Remettre le prospect en Valide
				individuProspectDTO.setStatutIndividu("V");
				individuProspectDTO = IndividuTransform.transformSignatureToDtoProspectCreation(individuProspectDTO, request.getRequestorDTO());
				individuProspectDTO = IndividuTransform.transformSignatureToDtoProspectUpdate(individuProspectDTO, request.getRequestorDTO());
				checkProspectMandatory(individuProspectDTO, findIndividu);

				try {
					// update prospect
					createOrUpdateProspectHelper.updateProspect(individuProspectDTO, findIndividu, response, request);

					// update prospect's telecom
					createOrUpdateProspectHelper.updateProspectTelecoms(request, individuProspectDTO.getSgin().toString());

				}  catch (MaximumSubscriptionsException e){
					throw new MaximumSubscriptionsException(e.getMessage());
				}
			}
		}
		else {
			throw new NotFoundException("Individual not found");
		}
		
		return response;
	}


	private String createIndividualDataKidSolo(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {
		
		IndividuDTO individuDTO = IndividuTransform.transformRequestToIndividuDTO(request , individuDS);
		
		String gin = null;
		gin = createOrUpdateKidSoloHelper.createIndividual(individuDTO);
				
		return gin;
	}

	private void updateIndividualDataKidSolo(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {
			IndividuDTO individuRDTO = IndividuTransform.transformRequestToIndividuDTO(request , individuDS);
			createOrUpdateKidSoloHelper.updateIndividual(individuRDTO);
	}
	
	private void updateIndividualDataCrisisEvent(CreateUpdateIndividualRequestDTO request) throws JrafDomainException {
		
		IndividuDTO individuRDTO = IndividuTransform.transformRequestToIndividuDTO(request , individuDS);
		individuDS.switchToHidden(individuRDTO.getSgin(), individuRDTO.getSiteModification(), individuRDTO.getSignatureModification());

	}

	// On cherche si il y a un SocialNetworkData existant, si oui, on renvoi le GIN, sinon, on leve une Exception
	private String existSocialNetWorkData(List<ExternalIdentifierRequestDTO> leir) throws JrafDomainException  {

		String ginFoundForExistingSocialNetworkData = "";

		// Il y a plusieurs GIN, sont ils les memes ?
		if (ginFoundForExistingSocialNetworkData.contains(" ")) {

			String[] existingExternalIdentifierTab = ginFoundForExistingSocialNetworkData.split(" ");

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
					throw new ExternalIdentifierLinkedToDifferentIndividualException(ginFoundForExistingSocialNetworkData);
				}
				// Si il n y a que un seul GIN
			} else {
				// Cas ideal
			}
		}
		return ginFoundForExistingSocialNetworkData;
	}

	// On cherche si il y a un ExternalIdentifier existant, si oui, on renvoi le GIN, sinon, on leve une Exception
	private String existExternalIdentifier(List<ExternalIdentifierRequestDTO> leir) throws JrafDomainException  {

		String ginFoundForExistingExternalIdentifier = "";

		// Recherche d'un GIGYA existant
		for (ExternalIdentifierRequestDTO lei : leir) {

			ExternalIdentifierDTO ei = lei.getExternalIdentifierDTO();

			// On ne teste QUE les GIGYA
			if (ei != null && refTypExtIDDS.get(ei.getType()) != null &&
					!"".equals(ei.getIdentifier())
					) {
				// TODO : Visiblement on ne renvoi jamais plusieurs GIN
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

	private CreateModifyIndividualResponseDTO updateIndividualDataByDefault(CreateUpdateIndividualRequestDTO request, CreateModifyIndividualResponseDTO response) throws InvalidParameterException, JrafDomainException {
		IndividuDTO individuDTO = IndividuTransform.transformToIndividuDTO(request);
		List<PostalAddressDTO> postalAddressDTOList = IndividuTransform.transformToPostalAddressDTO(request.getPostalAddressRequestDTO());
		List<EmailDTO> emailDTOList = IndividuTransform.transformToEmailDTO(request.getEmailRequestDTO());
		SignatureDTO signatureAPP = IndividuTransform.transformToSignatureAPP(request.getRequestorDTO());
		
		List<CommunicationPreferencesDTO> comPrefsDTOList = IndividuTransform.transformToComPrefsDTO(request.getCommunicationPreferencesRequestDTO(),request.getRequestorDTO());
		
		String wantedUpdatedLanguage = communicationPreferencesHelper.retrieveWantedMarketLanguage(comPrefsDTOList);
		
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
		List<PreferenceDTO> preferenceDTOList = IndividuTransform.transformToPreferenceDTO(request.getPreferenceRequestDTO(), request.getRequestorDTO());
		individuDTO.setPreferenceDTO(preferenceDTOList);

		String gin = null;
		
		if (request != null && request.getIndividualRequestDTO() != null && request.getIndividualRequestDTO().getIndividualInformationsDTO() != null && request.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier() != null && !"".equals(request.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier())) {
			
			gin = request.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier(); 
		
			// Get individual from DB
			IndividuDTO individuFromDB = individuDS.getIndividualOnlyByGin(gin);
			
			// REPIND-919 : Switch a W or T into I in case of Postal Address is Usage ISI ! 
			if (individuFromDB != null && ("W".equals(individuFromDB.getType()) || "T".equals(individuFromDB.getType()) || "E".equals(individuFromDB.getType()))) {
				
				// FAUX : On detecte que l on a pas d ADR POST en base de donnees mais que l on en a une en parametre WS
				// On cherche juste si on a une adresse en Entree
				if (postalAddressDTOList != null && postalAddressDTOList.size() > 0) {
					
					// On cherche si cette adresse est ISI Mailing M
					for (PostalAddressDTO pad : postalAddressDTOList) {
						
						if (pad != null && pad.getUsage_mediumdto() != null) {
							
							for (Usage_mediumDTO umd : pad.getUsage_mediumdto()) {
							
								// On recherche une address ISI mailling M
								if (umd != null && "ISI".equals(umd.getScode_application()) && 
										("M".equals(umd.getSrole1()) || "M".equals(umd.getSrole2()) || 
										"M".equals(umd.getSrole3()) || "M".equals(umd.getSrole4()) || "M".equals(umd.getSrole5()))) {
									
									// On switch l individu W ou T en I
									// On en profite pour mettre aussi a jour le NOM PRENOM si il est NULL en base de données
									// REPIND-1598 : We will create a default PROFILE that will be updated with data transmit by FB after that
									String lang = null;
									String optin = null;
									if (request != null && request.getIndividualRequestDTO() != null && request.getIndividualRequestDTO().getIndividualProfilDTO() != null) {
										lang = request.getIndividualRequestDTO().getIndividualProfilDTO().getLanguageCode();
										optin = request.getIndividualRequestDTO().getIndividualProfilDTO().getEmailOptin();
									}
									
									individuDS.switchToIndividual(gin, 
											request.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC(), request.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC(), request.getIndividualRequestDTO().getIndividualInformationsDTO().getBirthDate(),
											lang, optin);
									// On raffraichi l individu que l'on vient de modifier
									individuFromDB = individuDS.refresh(individuFromDB);
									// On continue les traitement
									break;
								}
							}
						}
					}
				}
			}
			
			// REPIND-1897: Define context for CSM batch processing
			if (individuFromDB != null && "W".equals(individuFromDB.getType())) {
				// Check value of context : BATCH_CSM_QVI stands for CSM Batch
				if (request.getRequestorDTO() != null && request.getRequestorDTO().getContext() != null && ContextEnum.CSM.getLibelle().equalsIgnoreCase(request.getRequestorDTO().getContext())) {
					request.getRequestorDTO().setContext(ContextEnum.W.getCode());
					response = createIndividualDataWhiteWinger(request, response);
					return response;
				}
			}
		}
		
		ReturnDetailsDTO resultDetails = myAccountDS.updateMyAccountCustomerData(individuDTO, postalAddressDTOList, null, emailDTOList, null, comPrefsDTOList, newsletterMediaSending, signatureAPP, status, updateModeCommPrefs.toString(), wantedUpdatedLanguage, request.getConsumerId());
		LOGGER.debug(resultDetails);

		if (resultDetails != null && resultDetails.getDetailedCode() != null) {
			InformationResponseDTO informationResponse = new InformationResponseDTO();
			InformationDTO info = new InformationDTO();
			info.setInformationCode(""+resultDetails.getDetailedCode());
			info.setInformationDetails(resultDetails.getLabelCode());
			informationResponse.setInformation(info);
			response.getInformationResponse().add(informationResponse);
		}
		
		return response;
	}

	/**
	 * PROCESS PREFILLED NUMBERS TO UPDATE
	 *
	 * @param gin
	 * @param updateMode
	 * @param signature
	 * @throws JrafDomainException
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

	private void checkProspectMandatory(IndividuDTO updateProspect, IndividuDTO prospectDTO) {
		if (prospectDTO.getPostaladdressdto() == null && updateProspect.getPostaladdressdto() != null) {
			if (updateProspect.getPostaladdressdto().get(0) != null) {
				if (updateProspect.getPostaladdressdto().get(0).getScode_medium() == null || updateProspect.getPostaladdressdto().get(0).getSstatut_medium() == null) {
					IndividuTransform.transformStatusToDtoProspectCreation(updateProspect);
				}
			}
		}
		if (prospectDTO.getTelecoms() == null && updateProspect.getTelecoms() != null) {
			if (updateProspect.getTelecoms().iterator().next() != null) {
				if(updateProspect.getTelecoms().iterator().next().getScode_medium() == null || updateProspect.getTelecoms().iterator().next().getSstatut_medium() == null || updateProspect.getTelecoms().iterator().next().getSterminal() == null) {
					IndividuTransform.transformStatusToDtoProspectCreation(updateProspect);
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

	/**
	 * Creation or Update of an individual or prospect with newsletter
	 * @param input CreateUpdateIndividualRequestDTO
	 * @param email String (unique email to identify the individual or prospect)
	 * @return the GIN created or updated
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String CreateOrUpdateProspectFromBatch(@NotNull CreateUpdateIndividualRequestDTO input, @NotNull String email) throws RuntimeException {

		String responseGin = "";
		String inputGin = "";

		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();

		try {
			// Initialize reconciliation data
			if (input.getIndividualRequestDTO() != null && input.getIndividualRequestDTO().getIndividualInformationsDTO() != null) {
				inputGin = input.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier();
			}

			IndividuDTO prospectFromInput = mapRequestProspectToIndividuProspect(input);

			if (StringUtils.isBlank(inputGin)) {
				response = createProspectFromBatch(input, email);
			}
			else {
				response = updateProspectFromBatch(inputGin, input, prospectFromInput);
			}
			responseGin = response != null ? response.getGin() : "";
		} catch (JrafDomainException e) {
			LOGGER.error("Error on prospect " + email + " - " + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

		return responseGin;
	}
	
	/**
	 * Creates a new subscription or updates the existing subscription
	 * 
	 * @param input  the input from file
	 * @param email  the email of the individual to be created or updated
	 * @param gin    gin present in the file
	 * @param market market for which subscription is to be created
	 * @return the gin of the individual created or updated
	 * @throws JrafDomainException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String createOrUpdateSubscription(@NotNull CreateUpdateIndividualRequestDTO input, @NotNull String email,
			String gin, @NotNull String market) throws JrafDomainException {
		CreateModifyIndividualResponseDTO response = null;

		if (StringUtils.isBlank(gin)) {
			gin = findMatchingIndividual(email, input);
		}

		IndividuDTO request = mapRequestProspectToIndividuProspect(input);

		// No matching individual found - create
		if (StringUtils.isBlank(gin)) {
			createOrUpdateProspectHelper.checkFirstNameLastNameOverFlow(request, input);
			response = createOrUpdateProspectHelper.createProspect(request,
					new CreateModifyIndividualResponseDTO());
		} // Update existing
		else {
			// Deletes the oldest market language (earliest modification date) if the number
			// of M/L for the individual is already 5
			deleteMarketLanguage(gin, market);
			response = updateProspectFromBatch(gin, input, request);
		}

		return (response != null ? response.getGin() : StringUtils.EMPTY);
	}

	/**
	 * Creates a new subscription or updates the existing subscription, specific for Adhoc Sharepoint Batch
	 *
	 * @param input  the input from file
	 * @param email  the email of the individual to be created or updated
	 * @param gin    gin present in the file
	 * @param market market for which subscription is to be created
	 * @return the gin of the individual created or updated
	 * @throws JrafDomainException
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String createOrUpdateSubscriptionSharepoint(@NotNull CreateUpdateIndividualRequestDTO input, @NotNull String email,
													   String gin, @NotNull String market) throws JrafDomainException {
		CreateModifyIndividualResponseDTO response = null;

		if (StringUtils.isBlank(gin)) {
			gin = findMatchingIndividualSharepoint(email, input);
		}

		IndividuDTO request = mapRequestProspectToIndividuProspect(input);

		// No matching individual found - create
		if (StringUtils.isBlank(gin)) {
			createOrUpdateProspectHelper.checkFirstNameLastNameOverFlow(request, input);
			response = createOrUpdateProspectHelper.createProspect(request,
					new CreateModifyIndividualResponseDTO());
		} // Update existing
		else {
			// Deletes the oldest market language (earliest modification date) if the number
			// of M/L for the individual is already 5
			deleteMarketLanguage(gin, market);
			response = updateProspectFromSharepoint(gin, input, request);
		}

		return (response != null ? response.getGin() : StringUtils.EMPTY);
	}

	/**
	 * This methods deletes the oldest market language if the number of M/L for the
	 * individual is already 5.
	 * <ul>
	 * <li>if an individual already has Market Language value for the Market, then
	 * it will be overridden by the WS so do-nothing.</li>
	 * 
	 * <li>if optin status "N" is found, then for optins with status "N" check the
	 * modification date. Remove the oldest (with earliest modification date)</li>
	 * 
	 * <li>else if only "Y" found, remove the oldest (with earliest modification
	 * date)</li>
	 * </ul>
	 * 
	 * @param gin    identifier of the individual
	 * @param market market for which new subscription is being created
	 * @throws JrafDaoException
	 */
	private void deleteMarketLanguage(String gin, String market) throws JrafDaoException {
		List<MarketLanguage> marketLanguages = marketLanguageRepository.findMarketLanguageByGin(gin);
		if (!CollectionUtils.isEmpty(marketLanguages) && marketLanguages.size() >= 5) {
			Integer oldestOptinN = null;
			boolean overrideLanguage = false;
			for (MarketLanguage ml : marketLanguages) {
				if (market.equals(ml.getMarket())) {
					overrideLanguage = true;
					break;
				} else if (oldestOptinN == null && YesNoFlagEnum.NO.toString().equals(ml.getOptIn())) {
					oldestOptinN = ml.getMarketLanguageId();
				}
			}
			if (!overrideLanguage) {
				Integer mlToBeDeleted = oldestOptinN == null ? marketLanguages.get(0).getMarketLanguageId()
						: oldestOptinN;
				marketLanguageRepository.deleteById(mlToBeDeleted);
				marketLanguageRepository.flush();
			}
		}
	}
	
	/**
	 * Finds the matching individual based on email address, first name, last name,
	 * and DOB
	 * 
	 * @param email the email of the individual
	 * @param input input having first name, last name and DOB
	 * @return the gin of the individual based on the email
	 * @throws JrafDomainException
	 */
	private String findMatchingIndividual(@NotNull String email, @NotNull CreateUpdateIndividualRequestDTO input)
			throws JrafDomainException {
		List<IndividuDTO> foundIndividuals = individuDS.findIndividuByEmail(email);
		IndividuDTO uniqueIndividual = createOrUpdateAnIndividualHelper
				.filterUniqueProspectOrIndividual(foundIndividuals, email, StringUtils.EMPTY, StringUtils.EMPTY);
		if (uniqueIndividual != null) {
			if (individuDS.isAnIndividualType(uniqueIndividual)) {
				// Personal data should not be overridden
				blankInput(input, uniqueIndividual);
			}
			return uniqueIndividual.getSgin();
		} else {
			String firstName = input.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC();
			String lastName = input.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC();
			if (StringUtils.isBlank(firstName) && StringUtils.isBlank(lastName)) {
				LOGGER.warn(ERROR_MULTIPLE_INDIVIDUAL_FOUND + email);
				throw new JrafDomainException(ERROR_MULTIPLE_INDIVIDUAL_FOUND + email);
			}

			Date birthDate = input.getIndividualRequestDTO().getIndividualInformationsDTO().getBirthDate();
			List<IndividuDTO> matchingNames = createOrUpdateAnIndividualHelper.filterNamesAndBirthdate(foundIndividuals,
					firstName, lastName, birthDate);
			if (CollectionUtils.isEmpty(matchingNames)) {
				return null;
			} else if (matchingNames.size() == 1) {
				return matchingNames.get(0).getSgin();
			} else {
				LOGGER.warn(ERROR_MULTIPLE_INDIVIDUAL_FOUND + email);
				throw new JrafDomainException(ERROR_MULTIPLE_INDIVIDUAL_FOUND + email);
			}
		}
	}

	private String findMatchingIndividualSharepoint(@NotNull String email, @NotNull CreateUpdateIndividualRequestDTO input)
			throws JrafDomainException {
		List<IndividuDTO> foundIndividuals = individuDS.findIndividuByEmail(email);
		if (CollectionUtils.isEmpty(foundIndividuals)){
			return null;
		}
		IndividuDTO uniqueIndividual = createOrUpdateAnIndividualHelper
				.filterUniqueProspectOrIndividual(foundIndividuals, email, StringUtils.EMPTY, StringUtils.EMPTY);
		if (uniqueIndividual != null) {
			if (individuDS.isAnIndividualType(uniqueIndividual)) {
				// Personal data should not be overridden
				blankInput(input, uniqueIndividual);
			}
			return uniqueIndividual.getSgin();
		} else {
			String firstName = input.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC();
			String lastName = input.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC();
			if (StringUtils.isBlank(firstName) && StringUtils.isBlank(lastName)) {
				LOGGER.warn(ERROR_MULTIPLE_INDIVIDUAL_FOUND + email);
				throw new JrafDomainException(ERROR_MULTIPLE_INDIVIDUAL_FOUND + email);
			}

			Date birthDate = input.getIndividualRequestDTO().getIndividualInformationsDTO().getBirthDate();
			List<IndividuDTO> matchingNames = createOrUpdateAnIndividualHelper.filterNamesAndBirthdate(foundIndividuals,
					firstName, lastName, birthDate);
			if (CollectionUtils.isEmpty(matchingNames)) {
				return null;
			} else if (matchingNames.size() == 1) {
				return matchingNames.get(0).getSgin();
			} else {
				LOGGER.warn(ERROR_MULTIPLE_INDIVIDUAL_FOUND + email);
				throw new JrafDomainException(ERROR_MULTIPLE_INDIVIDUAL_FOUND + email);
			}
		}
	}
	
	private CreateModifyIndividualResponseDTO updateProspectFromBatch(String existingGin, CreateUpdateIndividualRequestDTO input, IndividuDTO prospectFromInput) throws JrafDomainException {
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();

		IndividuDTO findProspectDto = new IndividuDTO();
		findProspectDto.setSgin(existingGin);

		//Get individu with data exisitng in RI DB
		Individu individu = createOrUpdateProspectHelper.individuBeforeUpdate(existingGin);
		String civility = individu.getCivilite();
		Date birthDate = individu.getDateNaissance();
		// update prospect
		createOrUpdateProspectHelper.checkFirstNameLastNameOverFlow(prospectFromInput, input);
		String responseGin = createOrUpdateProspectHelper.updateProspect(prospectFromInput, findProspectDto, new CreateModifyIndividualResponseDTO(), input);

		//Check if Individu have valid contract and update with correct data
		createOrUpdateProspectHelper.individuAfterUpdate(existingGin, birthDate, civility);

		// update prospect's telecom
		createOrUpdateProspectHelper.updateProspectTelecoms(input, existingGin);

		response.setGin(responseGin);
		return response;
	}

	private CreateModifyIndividualResponseDTO updateProspectFromSharepoint(String existingGin, CreateUpdateIndividualRequestDTO input, IndividuDTO prospectFromInput) throws JrafDomainException {
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();

		IndividuDTO findProspectDto = new IndividuDTO();
		findProspectDto.setSgin(existingGin);

		//Get individu with data exisitng in RI DB
		Individu individu = createOrUpdateProspectHelper.individuBeforeUpdate(existingGin);
		String civility = individu.getCivilite();
		Date birthDate = individu.getDateNaissance();
		String nom = individu.getNom();
		String prenom = individu.getPrenom();
		String gender = individu.getSexe();

		Date modificationDate = individu.getDateModification();
		String modificationSignature = individu.getSignatureModification();
		String modificationSite = individu.getSiteModification();

		// update prospect
		createOrUpdateProspectHelper.checkFirstNameLastNameOverFlow(prospectFromInput, input);
		String responseGin = createOrUpdateProspectHelper.updateProspectSharepoint(prospectFromInput, findProspectDto, new CreateModifyIndividualResponseDTO(), input);

		//Check if Individu have valid contract and update with correct data
		createOrUpdateProspectHelper.individuAfterUpdateSharepoint(existingGin, birthDate, civility, nom, prenom, gender,
				modificationDate, modificationSignature, modificationSite);

		// update prospect's telecom
		createOrUpdateProspectHelper.updateProspectTelecoms(input, existingGin);

		response.setGin(responseGin);
		return response;
	}

	private CreateModifyIndividualResponseDTO createProspectFromBatch(CreateUpdateIndividualRequestDTO input, String email) throws JrafDomainException {
		CreateModifyIndividualResponseDTO response = new CreateModifyIndividualResponseDTO();
		boolean createIndividual = false;
		boolean updateIndividual = false;
		IndividuDTO foundGin = null;

		// Initialize reconciliation data
		String lastName = "";
		String firstName = "";
		Date birthDate = null;
		String cinReco = ""; //reconciliation CIN
		String ginReco = ""; //reconciliation GIN

		if (input.getIndividualRequestDTO() != null && input.getIndividualRequestDTO().getIndividualInformationsDTO() != null) {
			lastName = input.getIndividualRequestDTO().getIndividualInformationsDTO().getLastNameSC();
			firstName = input.getIndividualRequestDTO().getIndividualInformationsDTO().getFirstNameSC();
			birthDate = input.getIndividualRequestDTO().getIndividualInformationsDTO().getBirthDate();
		}
		if (input.getRequestorDTO() != null) {
			cinReco = input.getRequestorDTO().getReconciliationDataCIN();
			ginReco = input.getRequestorDTO().getLoggedGin();
		}

		// No GIN, try to find unique individual that matches input
		List<IndividuDTO> foundIndividuals = individuDS.findIndividuByEmail(email);

		if (CollectionUtils.isEmpty(foundIndividuals)) {
			createIndividual = true;
		}
		else {
			foundGin = createOrUpdateAnIndividualHelper.filterUniqueProspectOrIndividual(foundIndividuals, email, cinReco, ginReco);
			if (foundGin != null) {
				updateIndividual = true;
				if (individuDS.isAnIndividualType(foundGin)) {
					blankInput(input, foundGin);
				}
			}
			else {
				// Check empty data
				if (StringUtils.isBlank(firstName) && StringUtils.isBlank(lastName)) {
					LOGGER.warn(ERROR_MULTIPLE_INDIVIDUAL_FOUND + email);
					throw new JrafDomainException(ERROR_MULTIPLE_INDIVIDUAL_FOUND + email);
				}

				// Try to match unique individual with names
				List<IndividuDTO> matchingNames =
						createOrUpdateAnIndividualHelper.filterNamesAndBirthdate(foundIndividuals, firstName, lastName, birthDate);

				if (CollectionUtils.isEmpty(matchingNames)) {
					createIndividual = true;
				}
				else if (matchingNames.size() == 1) {
					foundGin = matchingNames.get(0);
					// Switch to update mode
					updateIndividual = true;
				}
				else {
					LOGGER.warn(ERROR_MULTIPLE_INDIVIDUAL_FOUND + email);
					throw new JrafDomainException(ERROR_MULTIPLE_INDIVIDUAL_FOUND + email);
				}
			}
		}

		IndividuDTO prospectFromInput = mapRequestProspectToIndividuProspect(input);
		if (createIndividual) {

			createOrUpdateProspectHelper.checkFirstNameLastNameOverFlow(prospectFromInput, input);
			return createOrUpdateProspectHelper.createProspect(prospectFromInput, new CreateModifyIndividualResponseDTO());
		}
		if (updateIndividual) {
			return updateProspectFromBatch(foundGin.getSgin(), input, prospectFromInput);
		}
		else {
			return null;
		}
	}

	private void blankInput(CreateUpdateIndividualRequestDTO input, IndividuDTO foundGin) {
		if (StringUtils.isNotBlank(foundGin.getPrenom())) {
			input.getIndividualRequestDTO().getIndividualInformationsDTO().setFirstNameSC(foundGin.getPrenom());
		}
		if (StringUtils.isNotBlank(foundGin.getNom())) {
			input.getIndividualRequestDTO().getIndividualInformationsDTO().setLastNameSC(foundGin.getNom());
		}

	}
}
