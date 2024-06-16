package com.airfrance.repind.service.ws.internal.helpers;

import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.compref.CommunicationPreferenceException;
import com.airfrance.ref.exception.compref.MarketLanguageException;
import com.airfrance.ref.exception.jraf.*;
import com.airfrance.ref.type.CommunicationPreferencesConstantValues;
import com.airfrance.ref.type.ContractDataKeyEnum;
import com.airfrance.ref.type.NATFieldsEnum;
import com.airfrance.ref.type.YesNoFlagEnum;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dao.adresse.PostalAddressRepository;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dao.individu.CommunicationPreferencesRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.individu.MarketLanguageRepository;
import com.airfrance.repind.dao.profil.ProfilsRepository;
import com.airfrance.repind.dao.reference.RefComPrefCountryMarketRepository;
import com.airfrance.repind.dao.reference.RefComPrefRepository;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.ws.RequestorDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractRequestDTO;
import com.airfrance.repind.dto.ws.createupdaterolecontract.v1.CreateUpdateRoleContractResponseDTO;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.entity.profil.Profils;
import com.airfrance.repind.entity.reference.RefComPref;
import com.airfrance.repind.entity.reference.RefComPrefDomain;
import com.airfrance.repind.entity.reference.RefComPrefGType;
import com.airfrance.repind.entity.reference.RefComPrefType;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.individu.internal.MyAccountDS;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.util.SicStringUtils;
import com.airfrance.repind.util.transformer.CommunicationPreferenceTransformV1;
import com.airfrance.repind.util.transformer.FlyingBlueContractHelperResponse;
import com.airfrance.repind.util.transformer.RefComPrefResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("CreateOrUpdateFlyingBlueContractHelper")
public class CreateOrUpdateFlyingBlueContractHelper {

	private Log logger = LogFactory.getLog(CreateOrUpdateFlyingBlueContractHelper.class);

	/** warning message */
	private static final String CANNOT_CREATE_COMMUNICATION_PREFERENCES = "Cannot create communication preferences";

	private static String FBRecognitionActivated  = "FBRECOGNITION_ACTIVATED";

	private static final String TYPE_TRAVELER = "T";

	private static final String TYPE_INDIVIDU = "I";

	@Autowired
	protected PostalAddressRepository postalAddressRepository;

	@Autowired
	protected RefComPrefCountryMarketRepository refComPrefCountryMarketRepository;

	@Autowired
	private RefComPrefRepository refComPrefRepository;

	@Autowired
	private MarketLanguageRepository marketLanguageRepository;

	@Autowired
	private RoleDS roleDS;

	@Autowired
	private MyAccountDS myAccountDS;

	@Autowired
	private AccountDataRepository accountDataRepository;

	@Autowired
	private ProfilsRepository profilsRepository;

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	protected IndividuDS individuDS;

	@Autowired
	private CommunicationPreferencesRepository communicationPreferencesRepository;

	@Autowired
	private CommunicationPreferencesDS communicationPreferencesDS;

	@Autowired
	protected @Lazy ActionManager actionManager;

	@Autowired
	protected BusinessRoleDS businessRoleDS;

	@Autowired
	private IndividuRepository individuRepository;

	/**
	 * Creating role contract and business role 
	 * Updating or creating communication preferences 
	 * 
	 * @param request
	 * @return
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class)
	public FlyingBlueContractHelperResponse createRoleFP(CreateUpdateRoleContractRequestDTO request) throws JrafDomainException {
		return createRoleFP(request, true);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public FlyingBlueContractHelperResponse createRoleFP(CreateUpdateRoleContractRequestDTO request, boolean isFBRecognitionActivate) throws JrafDomainException {
		logger.debug("START createOrUpdateRoleFP : " + System.currentTimeMillis());

		// Check input
		checkRoleContract(request);
		Map<String, String> mapFBData = null;
		if (isFBRecognitionActivate) {
			mapFBData = checkMandatoryFlyingBlueData(request.getContractRequest().getContractData());
		}

		// prepare response
		WarningResponseDTO warningResponse = new WarningResponseDTO();
		List<WarningDTO> listWarning = new ArrayList<WarningDTO>();

		String gin = request.getGin();
		ContractV2DTO contract = request.getContractRequest().getContract();
		String contractNumber = SicStringUtils.addingZeroToTheLeft(contract.getContractNumber(), 12);
		request.getContractRequest().getContract().setContractNumber(contractNumber);
		String productType = contract.getProductType();
		String contractType = contract.getContractType();

		List<CommunicationPreferences> communicationPreferencesResult = new ArrayList<CommunicationPreferences>();

		// Check if contract already exists
		RoleContratsDTO roleContracts = roleDS.findRoleContratsFP(contractNumber);

		if (roleContracts != null) {
			throw new ContractExistException("");
		} else {
			// Creation of signature
			SignatureDTO signature = createSignature(request);

			// Creation mother class of role contract
			BusinessRoleDTO businessRole = createBusinessRole(gin, contract, signature);

			// Creation of role contract
			RoleContratsDTO roleContract = createRoleContracts(mapFBData, gin, contract, signature,request.getRequestor(), isFBRecognitionActivate);


			if (ruleFamilleTraitement(contractType, productType)) {
				businessRole.setType(contractType);
			} else {
				roleContract.setFamilleTraitement(contractType);
				roleContract.setTypeContrat(productType);
				businessRole.setType("C");
			}

			// Persist
			roleDS.createRoleContractFP(gin, roleContract, businessRole, signature);

			/* REPIND-2348 : Check and add email_identifier and account_identifier */
			AccountDataDTO accountDataDTO = myAccountDS.provideMyAccountDataByGin(gin);
			if (accountDataDTO == null) {
				// No Accout Data -> We create it
				Individu individu = individuRepository.getOne(gin);
				AccountData	accountdata = roleDS.createAccountData(roleContract, signature, individu);
				accountDataRepository.saveAndFlush(accountdata);
			}

			myAccountDS.checkAndCompleteAccountDataIdentifier(accountDataDTO);
		}

		//If Context is empty, we use the NAT to generate the communications preferences
		if (StringUtils.isEmpty(request.getRequestor().getContext())) {
			String market = null;
			try {
				market = communicationPreferencesDS.getMarketForComPref(gin);	
			} catch (MarketLanguageException e) {
				return buildFlyingBlueResponse(warningResponse, listWarning, communicationPreferencesResult, contractNumber, CANNOT_CREATE_COMMUNICATION_PREFERENCES, e.getMessage());
			}

			// Filter of RefComPref from $Market and *MarketWorld
			RefComPrefResponse refCompPrefResponse = getCommunicationPreferences(market, "F", CommunicationPreferencesConstantValues.FB_ESS, CommunicationPreferencesConstantValues.MARKET_WORLD);

			warningResponse = refCompPrefResponse.getWarningResponse();


			if (warningResponse != null && !UList.isNullOrEmpty(warningResponse.getWarning())) {
				return buildFlyingBlueResponse(warningResponse, listWarning, communicationPreferencesResult, contractNumber,
						null, null);
			}
			
			Optional<Profils> profils = profilsRepository.findById(gin);
	
			if (!profils.isPresent()) {
				return buildFlyingBlueResponse(warningResponse, listWarning, communicationPreferencesResult, contractNumber, CANNOT_CREATE_COMMUNICATION_PREFERENCES, "No profil found for gin :" + gin);
			}

			Email email = null;
			List<Email> resEmails = emailRepository.findDirectEmail(gin);
			
			if(!UList.isNullOrEmpty(resEmails)) {
				email = resEmails.get(0);
			}
			
			//REPIND-1158
			/*if (email == null) {
				return buildFlyingBlueResponse(warningResponse, listWarning, communicationPreferencesResult, contractNumber,
						CANNOT_CREATE_COMMUNICATION_PREFERENCES, "No valid direct email found for gin :" + gin);
			}*/
			
			createUpdateCommunicationsPreference(request, gin, communicationPreferencesResult, market, refCompPrefResponse, email, profils.get());
		}


		logger.debug("END createOrUpdateRoleFP : " + System.currentTimeMillis());

		return buildFlyingBlueResponse(warningResponse, listWarning, communicationPreferencesResult, contractNumber, null, null);
	}

	/**
	 * Updating role contract and business role + updating or creating
	 * communication preferences
	 * 
	 * @param request
	 * @return
	 * @throws JrafDomainException
	 * @throws JrafApplicativeException
	 * @throws AccountNotFoundException 
	 */
	public FlyingBlueContractHelperResponse updateRoleFP(CreateUpdateRoleContractRequestDTO request) throws JrafDomainException, JrafApplicativeException, AccountNotFoundException {
		return updateRoleFP(request, true);
	}
	public FlyingBlueContractHelperResponse updateRoleFP(CreateUpdateRoleContractRequestDTO request, boolean isFBRecognitionActivate)
			throws JrafDomainException, JrafApplicativeException, AccountNotFoundException {
		
		logger.debug("START updateRoleFP : " + System.currentTimeMillis());

		// Check input
		checkRoleContract(request);
		// Check FP mandatory input
		Map<String, String> mapFBData = null;
		if (isFBRecognitionActivate) {			
			mapFBData = checkMandatoryFlyingBlueData(request.getContractRequest().getContractData());
		}

		// Prepare FB response
		ContractV2DTO contract = request.getContractRequest().getContract();

		String contractNumber = SicStringUtils.addingZeroToTheLeft(contract.getContractNumber(), 12);
		request.getContractRequest().getContract().setContractNumber(contractNumber);

		FlyingBlueContractHelperResponse response = new FlyingBlueContractHelperResponse();
		response.setNumberContract(contractNumber);

		String contractType = contract.getContractType();
		String productType = contract.getProductType();
		String contractStatus = contract.getContractStatus();
		Date todayDate = Calendar.getInstance().getTime();

		BusinessRoleDTO businessRoleDTO = null;
		RoleContratsDTO roleContract = null;
		try {
			roleContract = roleDS.findRoleContratsFP(contractNumber);
			if (roleContract != null) {
				businessRoleDTO = businessRoleDS.getBusinessRoleByCleRole(roleContract.getCleRole());
			}
		} catch (JrafDaoException e) {
			logger.error("CreateOrUpdateFlyingBlueContractHelper : Contract not found : contract number <"
					+ contractNumber + "> not found ");
		}

		// If we doesn't found a contract we create it
		if (businessRoleDTO == null && roleContract == null) {
			request.setActionCode("C");
			CreateUpdateRoleContractResponseDTO roleContractCreation = actionManager.processByActionCode(request);
			response.setNumberContract(roleContractCreation.getContractNumber());
			return response;
		} else {
			updateRoleContract(request, mapFBData, contract, contractNumber, contractType, productType, contractStatus,
					todayDate, businessRoleDTO, roleContract);

			// prepare response
			WarningResponseDTO warningResponse = new WarningResponseDTO();
			List<WarningDTO> listWarning = new ArrayList<WarningDTO>();

			logger.debug("END createOrUpdateRoleFP : " + System.currentTimeMillis());

			return buildFlyingBlueResponse(warningResponse, listWarning, null, contractNumber,
					null, null);
		}
	}


	/**
	 * 
	 * Delete FB Contract and unsubscribe from communication preferences
	 * 
	 * @param request
	 * @return
	 * @throws JrafDomainException
	 */
	public Boolean deleteRoleFP(CreateUpdateRoleContractRequestDTO request) throws JrafDomainException {

		logger.debug("START deleteRoleFP : " + System.currentTimeMillis());

		// Check input
		checkRoleContract(request);

		SignatureDTO signature = createSignature(request);
		ContractV2DTO contract = request.getContractRequest().getContract();
		String contractNumber = SicStringUtils.addingZeroToTheLeft(contract.getContractNumber(), 12);
		request.getContractRequest().getContract().setContractNumber(contractNumber);

		RoleContratsDTO roleContract = roleDS.findRoleContratsFP(contractNumber);
		
		if (roleContract != null) {
			String gin = request.getGin();
			BusinessRoleDTO businessRole = businessRoleDS.getBusinessRoleByCleRole(roleContract.getCleRole());

			if (businessRole.getGinInd() == null) {
				logger.error("SIC: Gin not found in BUSINESS ROLE = " + contractNumber);
				throw new NotFoundException("SIC: Gin not found in BUSINESS ROLE = " + contractNumber);
			} else if (gin != null && !gin.equals(businessRole.getGinInd())) {
				logger.error("Incoherent data for Role contract = " + contractNumber);
				throw new NotFoundException("Incoherent data for Role contract = " + contractNumber);
			} else {
				roleDS.deleteRoleContract(roleContract);
				businessRoleDS.deleteBusinessRole(businessRole);
				
				// DESACTIVATION DU CODE REPIND-1480 (PROCHAINE MEP - MAI 2019 ?)
				// Delete MyAccount contract and Authentication data
				/*AccountDataDTO accountdataDTO = accountDataDS.getByGin(request.getGin());
				accountDataDS.delete(accountdataDTO.getId());
				
				RoleContratsDTO roleContractMyAccount = roleDS.findRoleContractByNumContract(accountdataDTO.getAccountIdentifier());
				
				if (roleContractMyAccount != null) {
					BusinessRoleDTO businessRoleMyAccount = businessRoleDS.getBusinessRoleByCleRole(roleContractMyAccount.getCleRole());
					roleDS.deleteRoleContract(roleContractMyAccount);
					
					if(businessRoleMyAccount != null){
						businessRoleDS.deleteBusinessRole(businessRoleMyAccount);
						
					}
				}*/
				
				unsubscribeComPref(businessRole.getGinInd(), signature);				
			}

		} else {
			throw new ContractNotFoundException("");
		}
		return true;
	}

	/**
	 * Building response of the helper list of comPref + warning + contract
	 * number
	 * 
	 * @param warningResponse
	 * @param listWarning
	 * @param communicationPreferencesResult
	 * @param contractNumber
	 * @param label
	 * @param details
	 * @return
	 */
	private FlyingBlueContractHelperResponse buildFlyingBlueResponse(WarningResponseDTO warningResponse,
			List<WarningDTO> listWarning, List<CommunicationPreferences> communicationPreferencesResult,
			String contractNumber, String label, String details) {
		FlyingBlueContractHelperResponse response = new FlyingBlueContractHelperResponse();
		response.setComPrefResponse(CommunicationPreferenceTransformV1
				.transformToCommunicationPreferenceResponse(communicationPreferencesResult));
		response.setNumberContract(contractNumber);
		response.setWarningResponse(warningResponse);

		if (label != null && details != null) {
			WarningDTO warning = new WarningDTO();
			warning.setWarningCode(label);
			warning.setWarningDetails(details);
			listWarning.add(warning);
			if (warningResponse!=null) {
				warningResponse.getWarning().addAll(listWarning);
				response.setWarningResponse(warningResponse);
			} else {
				WarningResponseDTO newwarningResponse = new WarningResponseDTO();
				newwarningResponse.getWarning().addAll(listWarning);
				response.setWarningResponse(newwarningResponse);
			}


		}
		return response;
	}

	/**
	 * Method to get RefComPref of @param comTypeFBEss from @param market and
	 * the other RefComPref of @param marketWorld first than $Market if it
	 * doesn't exist
	 * 
	 * @param market
	 * @param domain
	 * @param comTypeFBEss
	 * @param marketWorld
	 * @return
	 * @throws JrafDaoException
	 * @throws CommunicationPreferenceException
	 */
	private RefComPrefResponse getCommunicationPreferences(String market, String domain, String comTypeFBEss,
			String marketWorld) throws JrafDaoException, CommunicationPreferenceException {

		RefComPrefResponse response = new RefComPrefResponse();
		List<RefComPref> listeComPrefResult = new ArrayList<RefComPref>();
		WarningResponseDTO warningResponse = new WarningResponseDTO();

		List<RefComPref> listeComPref = refComPrefRepository.findComPerfByMarketComType(market, domain, comTypeFBEss);
		Map<String, RefComPref> mapComType = new HashMap<String, RefComPref>();

		if (!UList.isNullOrEmpty(listeComPref)) {
			if (listeComPref.size() == 1) {
				mapComType.put(comTypeFBEss, listeComPref.get(0));
			} else if (listeComPref.size() > 1) {
				WarningDTO warning = new WarningDTO();
				warning.setWarningCode(CANNOT_CREATE_COMMUNICATION_PREFERENCES);
				warning.setWarningDetails("Too many communications preferences are defined");
				warningResponse.getWarning().add(warning);
				response.setWarningResponse(warningResponse);
				return response;
			}
		} else {
			WarningDTO warning = new WarningDTO();
			warning.setWarningCode(CANNOT_CREATE_COMMUNICATION_PREFERENCES);
			warning.setWarningDetails("No communication preferences is defined ");
			warningResponse.getWarning().add(warning);
			response.setWarningResponse(warningResponse);
			return response;

		}

		List<RefComPref> comPrefMarketWorld = refComPrefRepository.findComPerfByAllMarkets(market, domain, comTypeFBEss,
				marketWorld);

		if (!UList.isNullOrEmpty(comPrefMarketWorld)) {
			for (RefComPref refComPref : comPrefMarketWorld) {
				RefComPrefType comTypeMarkets = refComPref.getComType();
				String codeType = comTypeMarkets.getCodeType();

				if (refComPref.getMarket().equals(market) || !mapComType.containsKey(codeType)) {
					mapComType.put(codeType, refComPref);
				}
			}

		}

		listeComPrefResult.addAll(mapComType.values());
		response.setRefComPrefList(listeComPrefResult);
		response.setCreateComPref(true);
		response.setMapRefComType(mapComType);

		return response;
	}

	/**
	 * Looping on RefComPreferences to create comPrefences
	 * 
	 * @param request
	 * @param gin
	 * @param communicationPreferencesResult
	 * @param market
	 * @param refCompPrefResponse
	 * @param profils
	 * @throws JrafDomainException 
	 */
	private void createUpdateCommunicationsPreference(CreateUpdateRoleContractRequestDTO request, String gin,
			List<CommunicationPreferences> communicationPreferencesResult, String market, RefComPrefResponse refCompPrefResponse, Email email, Profils profils) throws JrafDomainException,NotFoundException {

		Map<String, RefComPref> mapComType = refCompPrefResponse.getMapRefComType();
		List<RefComPref> listeRefCompPref = refCompPrefResponse.getRefComPrefList();
		Set<CommunicationPreferences> setCommunicationPreferences = new HashSet<CommunicationPreferences>();
		if (!UList.isNullOrEmpty(listeRefCompPref) && refCompPrefResponse.isCreateComPref()) {
			Date dateCreation = new Date();
			RequestorDTO requestor = request.getRequestor();
			for (RefComPref refCompRef : listeRefCompPref) {
				RefComPref refComPrefFBEss = mapComType.get(CommunicationPreferencesConstantValues.FB_ESS);

				//REPIND-1158
				//String optin = ruleNAT(refCompRef, email);
				String optin = ruleNAT(refCompRef, profils);

				String language = communicationPreferencesDS.getLanguageForComPref(refCompRef, refComPrefFBEss, profils);

				CommunicationPreferences communicationPreference;
				RefComPref refComPreferences = checkMarketWorld(refCompRef,refComPrefFBEss);

				RefComPrefType comType = refComPreferences.getComType();
				RefComPrefGType comGroupeType = refComPreferences.getComGroupeType();
				RefComPrefDomain domain = refComPreferences.getDomain();
				
				communicationPreference = communicationPreferencesRepository.findComPrefId(gin,

				domain.getCodeDomain(), comGroupeType.getCodeGType(), comType.getCodeType());

				if (communicationPreference == null) {
					communicationPreference = insertCommunicationPreference(gin, market, requestor, optin, language,
							dateCreation, comType, comGroupeType, domain);
					setCommunicationPreferences.add(communicationPreference);
				} else {
					communicationPreference = updateCommunicationPreference(requestor, language, dateCreation,
							optin, communicationPreference);
				}
				communicationPreferencesResult.add(communicationPreference);
			}

			//REPIND-934 : create telemarketing compref if needed
			CommunicationPreferences telemarketingCP = createTelemarketingCommunicationPreferences(gin, dateCreation, requestor);
			if(telemarketingCP != null) {
				setCommunicationPreferences.add(telemarketingCP);
				communicationPreferencesResult.add(telemarketingCP);
			}


			if (!UList.isNullOrEmpty(setCommunicationPreferences)) {
				individuDS.updateIndividualComPref(setCommunicationPreferences, gin);
			}

		}		
	}



	private CommunicationPreferences insertCommunicationPreference(String gin, String market, RequestorDTO requestor,
			String optinNAT, String defaultLanguage, Date dateCreation, RefComPrefType comType,
			RefComPrefGType comGroupeType, RefComPrefDomain domain) throws JrafDaoException {
		CommunicationPreferences communicationPreferences;
		communicationPreferences = createCommunicationPreferences(gin, comType, comGroupeType, domain, requestor,
				dateCreation, optinNAT);

		MarketLanguage marketLanguage = createMarketLanguage(market, defaultLanguage, optinNAT, requestor,
				dateCreation);
		Set<MarketLanguage> pMarketLanguage = new HashSet<MarketLanguage>();
		pMarketLanguage.add(marketLanguage);
		communicationPreferences.setMarketLanguage(pMarketLanguage);
		return communicationPreferences;
	}

	private CommunicationPreferences updateCommunicationPreference(RequestorDTO requestor, String defaultLanguage, Date dateCreation,
			String optin, CommunicationPreferences communicationPreferences) throws JrafDaoException {
		Set<MarketLanguage> marketsLanguage = communicationPreferences.getMarketLanguage();
		communicationPreferences.setSubscribe(optin);
		communicationPreferences.setModificationDate(dateCreation);
		communicationPreferences.setModificationSignature(requestor.getSignature());
		communicationPreferences.setModificationSite(requestor.getSite());

		communicationPreferencesRepository.updateComPref(communicationPreferences);

		if (!marketsLanguage.isEmpty()) {
			for (MarketLanguage marketLanguage : marketsLanguage) {
				marketLanguage.setLanguage(defaultLanguage);
				marketLanguage.setOptIn(optin);
				marketLanguage.setModificationDate(dateCreation);
				marketLanguage.setModificationSignature(requestor.getSignature());
				marketLanguage.setModificationSite(requestor.getSite());
				marketLanguageRepository.updateMarketLanguage(marketLanguage);
			}
		}

		return communicationPreferences;
	}

	private RefComPref checkMarketWorld(RefComPref refCompRef, RefComPref refComPrefFBEss) {
		if (CommunicationPreferencesConstantValues.MARKET_WORLD.equals(refCompRef.getMarket())) {
			refCompRef.setMarket(refComPrefFBEss.getMarket());
		}
		return refCompRef;
	}

	/**
	 * Initialize Communication preferences {@value CommunicationPreferences}
	 * 
	 * @param gin
	 * @param comType
	 * @param comGroupeType
	 * @param domain
	 * @param requestor
	 * @param dateCreation
	 * @param optinNAT
	 * @return
	 */
	private CommunicationPreferences createCommunicationPreferences(String gin, RefComPrefType comType,
			RefComPrefGType comGroupeType, RefComPrefDomain domain, RequestorDTO requestor, Date dateCreation,
			String optinNAT) {
		CommunicationPreferences communicationPreferences;
		communicationPreferences = new CommunicationPreferences();
		communicationPreferences.setGin(gin);
		communicationPreferences.setMedia1("E");
		communicationPreferences.setComType(comType.getCodeType());
		communicationPreferences.setComGroupType(comGroupeType.getCodeGType());
		communicationPreferences.setDomain(domain.getCodeDomain());
		communicationPreferences.setCreationSignature(requestor.getSignature());
		communicationPreferences.setCreationSite(requestor.getSite());
		communicationPreferences.setCreationDate(dateCreation);
		communicationPreferences.setModificationSignature(requestor.getSignature());
		communicationPreferences.setModificationSite(requestor.getSite());
		communicationPreferences.setModificationDate(dateCreation);
		communicationPreferences.setSubscribe(optinNAT);
		return communicationPreferences;
	}

	/**
	 * initialize Market Language {@value MarketLanguage}
	 * 
	 * @param market
	 * @param language
	 * @param optinNAT
	 * @param requestor
	 * @param dateCreation
	 * @return
	 * @throws JrafDaoException
	 */
	private MarketLanguage createMarketLanguage(final String market, final String language, final String optinNAT,
			RequestorDTO requestor, Date dateCreation) throws JrafDaoException {
		MarketLanguage marketLanguageToInsert = new MarketLanguage();
		marketLanguageToInsert.setCommunicationMedia1("E");
		marketLanguageToInsert.setMarket(market);
		marketLanguageToInsert.setLanguage(language);
		marketLanguageToInsert.setOptIn(optinNAT);
		marketLanguageToInsert.setCreationDate(dateCreation);
		marketLanguageToInsert.setCreationSignature(requestor.getSignature());
		marketLanguageToInsert.setCreationSite(requestor.getSite());
		marketLanguageToInsert.setModificationDate(dateCreation);
		marketLanguageToInsert.setModificationSignature(requestor.getSignature());
		marketLanguageToInsert.setModificationSite(requestor.getSite());
		return marketLanguageToInsert;
	}


	/**
	 * unsubscribe from FP communication preferences + Market Language in case of
	 * deleting roleContract
	 * 
	 * @param gin
	 * @param signature
	 * @throws JrafDomainException
	 */
	private void unsubscribeComPref(String gin, SignatureDTO signature) throws JrafDomainException {
		List<CommunicationPreferences> listCommunicationPref = communicationPreferencesRepository
				.findByGin(gin);
		if (!UList.isNullOrEmpty(listCommunicationPref)) {
			for (CommunicationPreferences comPref : listCommunicationPref) {
				if ("F".equals(comPref.getDomain())) { // unsubscribe only if if it's a FP compref
					communicationPreferencesDS.unsubscribeCommPrefFB(gin, comPref.getDomain(), comPref.getComGroupType(),
							comPref.getComType(), signature);
				}
			}
		}
	}


	/**
	 * If needed, create the additional telemarketing communication preferences
	 * 
	 * @return
	 * @throws JrafDaoException 
	 */
	public CommunicationPreferences createTelemarketingCommunicationPreferences(String gin, Date dateCreation, RequestorDTO requestor) throws JrafDaoException {

		CommunicationPreferences telemarketingCommunicationPreference = communicationPreferencesRepository.findComPrefId(gin, CommunicationPreferencesConstantValues.DOMAIN_P, CommunicationPreferencesConstantValues.GROUP_TYPE_T, CommunicationPreferencesConstantValues.COM_TYPE_TEL);
		if (telemarketingCommunicationPreference == null) {
			telemarketingCommunicationPreference = new CommunicationPreferences();
			telemarketingCommunicationPreference.setGin(gin);
			telemarketingCommunicationPreference.setComType(CommunicationPreferencesConstantValues.COM_TYPE_TEL);
			telemarketingCommunicationPreference.setComGroupType(CommunicationPreferencesConstantValues.GROUP_TYPE_T);
			telemarketingCommunicationPreference.setDomain(CommunicationPreferencesConstantValues.DOMAIN_P);
			telemarketingCommunicationPreference.setCreationSignature(requestor.getSignature());
			telemarketingCommunicationPreference.setCreationSite(requestor.getSite());
			telemarketingCommunicationPreference.setCreationDate(dateCreation);
			telemarketingCommunicationPreference.setModificationSignature(requestor.getSignature());
			telemarketingCommunicationPreference.setModificationSite(requestor.getSite());
			telemarketingCommunicationPreference.setModificationDate(dateCreation);
			telemarketingCommunicationPreference.setSubscribe(YesNoFlagEnum.NO.toString());

			return telemarketingCommunicationPreference;
		}		
		return null;
	}

	/**
	 * Updating RoleContract and business role
	 * 
	 * @param request
	 * @param mapFBData
	 * @param contract
	 * @param contractNumber
	 * @param contractType
	 * @param productType
	 * @param contractStatus
	 * @param todayDate
	 * @param businessRoleDTO
	 * @param roleContract
	 * @throws JrafDomainException
	 */
	private void updateRoleContract(CreateUpdateRoleContractRequestDTO request, Map<String, String> mapFBData,
			ContractV2DTO contract, String contractNumber, String contractType, String productType, String contractStatus,
			Date todayDate, BusinessRoleDTO businessRoleDTO, RoleContratsDTO roleContract) throws JrafDomainException {
		if(request == null){
			throw new InvalidParameterException("request must not be null");
		}

		if(businessRoleDTO == null){
			throw new InvalidParameterException("business role must not be null");
		}

		businessRoleDTO.setDateModification(todayDate);
		businessRoleDTO.setSignatureModification(request.getRequestor().getSignature());
		businessRoleDTO.setSiteModification(request.getRequestor().getSite());

		if (ruleFamilleTraitement(contractType, productType)) {
			businessRoleDTO.setType(contractType);
		} else {
			roleContract.setFamilleTraitement(contractType);
			roleContract.setTypeContrat(productType);
			businessRoleDTO.setType("C");

			// Requestor
			roleContract.setSignatureModification(request.getRequestor().getSignature());
			roleContract.setSiteModification(request.getRequestor().getSite());
			roleContract.setDateModification(todayDate);
			Date startDate = contract.getValidityStartDate();
			if (startDate == null) {
				startDate = todayDate;
			}
			Date endDate = contract.getValidityEndDate();
			// Contract
			roleContract.setDateDebutValidite(startDate);
			roleContract.setDateFinValidite(endDate);
			roleContract.setEtat(contractStatus);
			roleContract.setNumeroContrat(contractNumber);

			// Contract data : REPIND-1312
			if (mapFBData != null) { 
				roleContract.setTier(mapFBData.get(ContractDataKeyEnum.TIERLEVEL.getKey().toUpperCase()));
				roleContract.setMemberType(mapFBData.get(ContractDataKeyEnum.MEMBERTYPE.getKey().toUpperCase()));
				roleContract.setSoldeMiles(Integer.valueOf(mapFBData.get(ContractDataKeyEnum.MILESBALANCE.getKey().toUpperCase())));
				roleContract.setMilesQualif(Integer.valueOf(mapFBData.get(ContractDataKeyEnum.QUALIFYINGMILES.getKey().toUpperCase())));
				roleContract.setSegmentsQualif(Integer.valueOf(mapFBData.get(ContractDataKeyEnum.QUALIFYINGSEGMENTS.getKey().toUpperCase())));
			}

			if (request.getGin() != null && !request.getGin().equals(businessRoleDTO.getGinInd())) {
				// We check if the individual exist
				IndividuDTO individuDTO = individuDS.getByGin(request.getGin());
				// If individual is not found we return a NotFoundException
				if (individuDTO == null) {
					logger.error(
							"CreateOrUpdateFlyingBlueContractHelper : Individual not found, Gin = " + request.getGin());
					throw new NotFoundException("Gin = " + request.getGin());
				}
				businessRoleDTO.setGinInd(request.getGin());
				roleContract.setGin(request.getGin());
			}

			roleDS.updateARoleContract(roleContract);
			businessRoleDS.updateARoleContractBusinessRole(businessRoleDTO);
		}

		/* REPIND-2348 : Check and add email_identifier and account_identifier */
		AccountDataDTO accountDataDTO = myAccountDS.provideMyAccountDataByGin(request.getGin());
		if (accountDataDTO == null) {       // No Accout Data -> We create it
			Individu individu = individuRepository.getOne(request.getGin());
			SignatureDTO signature = createSignature(request);
			AccountData	accountdata = roleDS.createAccountData(roleContract, signature, individu);
			accountDataRepository.saveAndFlush(accountdata);
		}

		myAccountDS.checkAndCompleteAccountDataIdentifier(accountDataDTO);
	}

	/**
	 * Building response of the helper list of comPref + warning + contract
	 * number
	 * 
	 * @param warningResponse
	 * @param listWarning
	 * @param communicationPreferencesResult
	 * @param contractNumber
	 * @param label
	 * @param details
	 * @return
	 */
	private FlyingBlueContractHelperResponse buildFlyingBlueResponse(String contractNumber, String label, String details) {

		FlyingBlueContractHelperResponse response = new FlyingBlueContractHelperResponse();
		response.setNumberContract(contractNumber);

		return response;
	}

	/**
	 * Business rule NAT fields for initializing subscribe field in
	 * {@value CommunicationPreferences} and optin for {@value MarketLanguage}
	 * only creation
	 * 
	 * @param refCompRef
	 * @param profils
	 * @return
	 */
	protected String ruleNAT(RefComPref refCompRef, Profils profils) {
		String mailingAutorise = profils.getSmailing_autorise();
		String fieldA = refCompRef.getFieldA();
		String fieldT = refCompRef.getFieldT();
		String fieldN = refCompRef.getFieldN();
		if (NATFieldsEnum.AIRFRANCE.getValue().equals(mailingAutorise)) {
			return fieldA;
		}
		if (NATFieldsEnum.ALL.getValue().equals(mailingAutorise)) {
			return fieldT;
		}
		if (NATFieldsEnum.NONE.getValue().equals(mailingAutorise)) {
			return fieldN;
		}
		return null;
	}

	/**
	 * Business rule NAT fields for initializing subscribe field in
	 * {@value CommunicationPreferences} and optin for {@value MarketLanguage}
	 * only creation
	 * 
	 * @param refCompRef
	 * @param email
	 * @return
	 */
	protected String ruleNAT(RefComPref refCompRef, Email email) {
		String mailingAutorise = email.getAutorisationMailing();
		String fieldA = refCompRef.getFieldA();
		String fieldT = refCompRef.getFieldT();
		String fieldN = refCompRef.getFieldN();
		if (NATFieldsEnum.AIRFRANCE.getValue().equals(mailingAutorise)) {
			return fieldA;
		}
		if (NATFieldsEnum.ALL.getValue().equals(mailingAutorise)) {
			return fieldT;
		}
		if (NATFieldsEnum.NONE.getValue().equals(mailingAutorise)) {
			return fieldN;
		}
		return null;
	}


	/**
	 * Creating contract type Flying Blue
	 * 
	 * @param mapFBData
	 * @param gin
	 * @param signature
	 * @param requestor 
	 * @param listWarning
	 * @return
	 */
	private RoleContratsDTO createRoleContracts(Map<String, String> mapFBData, String gin, ContractV2DTO contract, SignatureDTO signature, RequestorDTO requestor) {
		return createRoleContracts(mapFBData, gin, contract, signature, requestor, true);
	}
	private RoleContratsDTO createRoleContracts(Map<String, String> mapFBData, String gin, ContractV2DTO contract, SignatureDTO signature, RequestorDTO requestor, boolean isFBRecognitionActivate) {		
		RoleContratsDTO roleContract = new RoleContratsDTO();
		String contractNumber = contract.getContractNumber();
		String contractStatus = contract.getContractStatus();

		Date startDate = contract.getValidityStartDate();
		if (startDate == null) {
			startDate = signature.getDate();
		}
		Date endDate = contract.getValidityEndDate();

		// Contract
		roleContract.setDateDebutValidite(startDate);
		roleContract.setDateFinValidite(endDate);
		roleContract.setSignatureCreation(signature.getSignature());
		roleContract.setSiteCreation(signature.getSite());
		roleContract.setDateCreation(signature.getDate());
		roleContract.setDateModification(signature.getDate());
		roleContract.setSignatureModification(signature.getSignature());
		roleContract.setSiteModification(signature.getSite());

		roleContract.setEtat(contractStatus);
		roleContract.setGin(gin);
		roleContract.setNumeroContrat(contractNumber);
		
		if (isFBRecognitionActivate) {
			roleContract.setCodeCompagnie(contract.getCompanyCode());
		}
		// Contract data
		if (mapFBData != null) {
			roleContract.setTier(mapFBData.get(ContractDataKeyEnum.TIERLEVEL.getKey().toUpperCase()));
			roleContract.setMemberType(mapFBData.get(ContractDataKeyEnum.MEMBERTYPE.getKey().toUpperCase()));
			roleContract
			.setSoldeMiles(Integer.valueOf(mapFBData.get(ContractDataKeyEnum.MILESBALANCE.getKey().toUpperCase())));
			roleContract.setMilesQualif(
					Integer.valueOf(mapFBData.get(ContractDataKeyEnum.QUALIFYINGMILES.getKey().toUpperCase())));
			roleContract.setSegmentsQualif(
					Integer.valueOf(mapFBData.get(ContractDataKeyEnum.QUALIFYINGSEGMENTS.getKey().toUpperCase())));
		}
		return roleContract;
	}

	/**
	 * Initialize {@value BusinessRoleDTO}
	 * 
	 * @param gin
	 * @param contract
	 * @param signature
	 * @return
	 */
	private BusinessRoleDTO createBusinessRole(String gin, ContractV2DTO contract, SignatureDTO signature) {
		String contractNumber = contract.getContractNumber();

		BusinessRoleDTO businessRole = new BusinessRoleDTO();
		businessRole.setDateCreation(signature.getDate());
		businessRole.setSignatureCreation(signature.getSignature());
		businessRole.setSiteCreation(signature.getSite());
		businessRole.setNumeroContrat(contractNumber);
		businessRole.setGinInd(gin);
		return businessRole;
	}

	/**
	 * initialize {@value SignatureDTO}
	 * 
	 * @param request
	 * @return
	 */
	private SignatureDTO createSignature(CreateUpdateRoleContractRequestDTO request) {
		SignatureDTO signature = new SignatureDTO();
		signature.setSignature(request.getRequestor().getSignature());
		signature.setSite(request.getRequestor().getSite());
		signature.setDate(Calendar.getInstance().getTime());

		// REPIND-876 : Change process for Hachiko
		signature.setApplicationCode(request.getRequestor().getApplicationCode());

		return signature;
	}

	/**
	 * Business rule to implement SFAMILLE_TRAITEMENT 
	 * if contractType and productType are mentionned 
	 * 
	 * @param contract
	 * @return
	 */
	private boolean ruleFamilleTraitement(String contractType, String productType) {

		if (StringUtils.isEmpty(contractType) || StringUtils.isEmpty(productType)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Checking mandatory data for flying blue
	 * 
	 * @param listeContractData
	 * @return
	 * @throws MissingParameterException
	 * @throws InvalidParameterException
	 */
	protected Map<String, String> checkMandatoryFlyingBlueData(List<ContractDataDTO> listeContractData) throws MissingParameterException, InvalidParameterException {

		String memberType = null;
		String milesBalance = null;
		String qualifyingMiles = null;
		String qualifyingSegments = null;
		String tierLevel = null;

		Map<String, String> mapFBData = new HashMap<String, String>();

		if (listeContractData != null && !listeContractData.isEmpty()) {
			for (ContractDataDTO contractData : listeContractData) {

				if (ContractDataKeyEnum.TIERLEVEL.getKey().equalsIgnoreCase(contractData.getKey())) {
					tierLevel = contractData.getValue();
				}
				if (ContractDataKeyEnum.MEMBERTYPE.getKey().equalsIgnoreCase(contractData.getKey())) {
					memberType = contractData.getValue();
				}
			
				if (ContractDataKeyEnum.MILESBALANCE.getKey().equalsIgnoreCase(contractData.getKey())) {
					milesBalance = contractData.getValue();
				}
				if (ContractDataKeyEnum.QUALIFYINGMILES.getKey().equalsIgnoreCase(contractData.getKey())) {
					qualifyingMiles = contractData.getValue();
				}
				if (ContractDataKeyEnum.QUALIFYINGSEGMENTS.getKey().equalsIgnoreCase(contractData.getKey())) {
					qualifyingSegments = contractData.getValue();
				}
			
				if (!(contractData == null || contractData.getKey() == null)) {
					mapFBData.put(contractData.getKey().toUpperCase(), contractData.getValue());
				}
			}
		}

		if (tierLevel == null) {
			logger.error("Missing Parameter : TierLevel is Mandatory");
			throw new MissingParameterException("TierLevel is Mandatory");
		}
		if (memberType == null) {
			logger.error("Missing Parameter : MemberType is Mandatory");
			throw new MissingParameterException("MemberType is Mandatory");
		}
		if (milesBalance == null) {
			logger.error("Missing Parameter : MilesBalance is Mandatory");
			throw new MissingParameterException("MilesBalance is Mandatory");
		} else if (checkTypeData(milesBalance)) {
			logger.error("Invalid parameter : MilesBalance should be a number");
			throw new InvalidParameterException("MilesBalance should be a number");
		}

		if (qualifyingMiles == null) {
			logger.error("Missing Parameter : QualifyingMiles is Mandatory");
			throw new MissingParameterException("QualifyingMiles is Mandatory");
		} else if (checkTypeData(qualifyingMiles)) {
			logger.error("Invalid parameter : QualifyingMiles should be a number");
			throw new InvalidParameterException("QualifyingMiles should be a number");
		}

		if (qualifyingSegments == null) {
			logger.error("Missing Parameter : QualifyingSegments is Mandatory");
			throw new MissingParameterException("QualifyingSegments is Mandatory");
		} else if (checkTypeData(qualifyingSegments)) {
			logger.error("Invalid parameter : QualifyingSegments should be a number");
			throw new InvalidParameterException("QualifyingSegments should be a number");
		}

		return mapFBData;
	}

	/**
	 * Cheking the type of Integer data
	 * 
	 * @param pValue
	 * @return
	 */
	private boolean checkTypeData(String pValue) {
		try {
			// REPIND-1398 : Correction for SONAR
			Integer notUse = Integer.valueOf(pValue);
		} catch (NumberFormatException ex) {
			return true;
		}
		return false;
	}

	/**
	 * Checking number contract
	 * 
	 * @param request
	 * @throws MissingParameterException
	 */
	private void checkRoleContract(CreateUpdateRoleContractRequestDTO request) throws MissingParameterException {
		if (request.getContractRequest().getContract().getContractNumber() == null
				|| request.getContractRequest().getContract().getContractNumber().isEmpty()) {
			logger.error("Missing Parameter : contractNumber is Mandatory");
			throw new MissingParameterException("contractNumber is Mandatory");
		}
	}

	public PostalAddressRepository getPostalAddressRepository() {
		return postalAddressRepository;
	}

	public void setPostalAddressRepository(PostalAddressRepository postalAddressRepository) {
		this.postalAddressRepository = postalAddressRepository;
	}

	public RoleDS getRoleDS() {
		return roleDS;
	}

	public RefComPrefCountryMarketRepository getRefComPrefCountryMarketRepository() {
		return refComPrefCountryMarketRepository;
	}

	public void setRefComPrefCountryMarketRepository(RefComPrefCountryMarketRepository refComPrefCountryMarketRepository) {
		this.refComPrefCountryMarketRepository = refComPrefCountryMarketRepository;
	}

	public RefComPrefRepository getRefComPrefRepository() {
		return refComPrefRepository;
	}

	public void setRefComPrefRepository(RefComPrefRepository refComPrefRepository) {
		this.refComPrefRepository = refComPrefRepository;
	}

	public MarketLanguageRepository getMarketLanguageRepository() {
		return marketLanguageRepository;
	}

	public void setMarketLanguageRepository(MarketLanguageRepository marketLanguageRepository) {
		this.marketLanguageRepository = marketLanguageRepository;
	}

	public void setRoleDS(RoleDS roleDS) {
		this.roleDS = roleDS;
	}

	public CommunicationPreferencesRepository getCommunicationPreferencesRepository() {
		return communicationPreferencesRepository;
	}

	public void setCommunicationPreferencesRepository(
			CommunicationPreferencesRepository communicationPreferencesRepository) {
		this.communicationPreferencesRepository = communicationPreferencesRepository;
	}

	public ProfilsRepository getProfilsRepository() {
		return profilsRepository;
	}

	public void setProfilsRepository(ProfilsRepository profilsRepository) {
		this.profilsRepository = profilsRepository;
	}

	public IndividuDS getIndividuDS() {
		return individuDS;
	}

	public CommunicationPreferencesDS getCommunicationPreferencesDS() {
		return communicationPreferencesDS;
	}

	public BusinessRoleDS getBusinessRoleDS() {
		return businessRoleDS;
	}

	public void setIndividuDS(IndividuDS individuDS) {
		this.individuDS = individuDS;
	}

	public void setCommunicationPreferencesDS(CommunicationPreferencesDS communicationPreferencesDS) {
		this.communicationPreferencesDS = communicationPreferencesDS;
	}

	public void setBusinessRoleDS(BusinessRoleDS businessRoleDS) {
		this.businessRoleDS = businessRoleDS;
	}

	public boolean checkIfGinIsTraveler(CreateUpdateRoleContractRequestDTO request) throws NotFoundException {
		Individu individu = individuRepository.findBySgin(request.getGin());
		if(individu == null){
			logger.error("Individual not found : Gin = " + request.getGin());
			throw new NotFoundException("Gin = " + request.getGin());
		}
		return TYPE_TRAVELER.equals(individu.getType());
	}

	@Transactional
	public void UpdateGinTravelerToIndividu(CreateUpdateRoleContractRequestDTO request){
		Individu individu = individuRepository.findBySgin(request.getGin());
		individuRepository.updateTypeByGin(request.getGin(), TYPE_INDIVIDU);
		logger.info(individu);

	}
}
