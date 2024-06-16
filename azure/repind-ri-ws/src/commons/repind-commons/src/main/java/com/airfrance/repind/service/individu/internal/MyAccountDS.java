package com.airfrance.repind.service.individu.internal;


import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.*;
import com.airfrance.ref.util.UList;
import com.airfrance.repind.dao.individu.AccountDataRepository;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.reference.RefComPrefCountryMarketRepository;
import com.airfrance.repind.dao.role.BusinessRoleRepository;
import com.airfrance.repind.dao.role.RoleContratsRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.compref.InformationDTO;
import com.airfrance.repind.dto.environnement.VariablesDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.individu.adh.individualinformation.IndividualInformationRequestDTO;
import com.airfrance.repind.dto.individu.createmodifyindividual.CreateModifyIndividualResponseDTO;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerRequestDTO;
import com.airfrance.repind.dto.individu.enrollmyaccountdata.MyAccountCustomerResponseDTO;
import com.airfrance.repind.dto.individu.myaccountcustomerdata.MyAccountComDataResponseDTO;
import com.airfrance.repind.dto.individu.myaccountcustomerdata.ReturnDetailsDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdRequestDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdResponseDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.BusinessRoleTransform;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.role.RoleContratsTransform;
import com.airfrance.repind.dto.tracking.TrackingDTO;
import com.airfrance.repind.dto.ws.CommunicationPreferencesRequestDTO;
import com.airfrance.repind.dto.ws.IndividualProfilDTO;
import com.airfrance.repind.dto.ws.RequestorDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.entity.individu.AccountData;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.Individu;
import com.airfrance.repind.entity.individu.MarketLanguage;
import com.airfrance.repind.entity.role.BusinessRole;
import com.airfrance.repind.entity.role.RoleContrats;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.internal.unitservice.individu.IndividuUS;
import com.airfrance.repind.service.internal.unitservice.individu.MyAccountUS;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.service.reference.internal.RefTypExtIDDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.service.role.internal.RoleUCCRDS;
import com.airfrance.repind.service.ws.internal.helpers.CommunicationPreferencesHelper;
import com.airfrance.repind.util.AspectLogger.LoggableNoParams;
import com.airfrance.repind.util.*;
import com.airfrance.repind.util.mapper.EnrollMyAccountMapper;
import com.airfrance.repind.util.transformer.CommunicationPreferencesTransformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class MyAccountDS {

	private static final String ACCOUNT_WITH_DELETED_STATUS = "ACCOUNT WITH DELETED STATUS";

	/** logger */
	private static final Log log = LogFactory.getLog(MyAccountDS.class);
	private static final Log splunkAuthenticate = LogFactory.getLog("splunkAuthenticate");
	//private static final Log SPLUNKBDM = LogFactory.getLog("splunk-callmarketingpreferences");

	/** reference sur le dao principal */
	@Autowired
	private AccountDataRepository accountDataRepository;

	@Autowired
	private AccountDataDS myAccountDataDS;

	@Autowired
	private RoleDS roleDS;

	@Autowired
	private RoleUCCRDS roleUCCRDS;

	@Autowired
	private VariablesDS variablesDS;

	/** Reference sur le unit service :IIndividuDS **/
	@Autowired
	private IndividuDS individuDS;

	/** Reference sur le unit service :IIndividuDS **/
	@Autowired
	private EmailDS emailDS;

	@Autowired
	private PreferenceDS preferenceDS;

	@Autowired
	private CommunicationPreferencesDS communicationPreferencesDS;
	
	/** references on associated DAOs */

	@Autowired
	private IndividuRepository individuRepository;

	@Autowired
	protected RefComPrefCountryMarketRepository refComPrefCountryMarketRepository;
	
	@Autowired
	private RoleContratsRepository roleContratsRepository;
	
	@Autowired
	private BusinessRoleRepository businessRoleRepository;

	@Autowired
	protected ExternalIdentifierDS externalIdentifierDS;	// Use to search GIGYA if not found in AccountDataDS

	@Autowired
	private RefTypExtIDDS refTypExtIDDS;

	/** Reference sur le unit service IndividuUS **/
	@Autowired
	private IndividuUS individuUS;

	/** Reference sur le unit service MyAccountUS **/
	@Autowired
	private MyAccountUS myAccountUS;

	@Autowired
	private UsageClientsDS usageClientsDS;

	@Autowired
	protected CommunicationPreferencesHelper communicationPreferencesHelper;
	
	@Autowired
	private EnrollMyAccountMapper enrollMyAccountMapper;
	
	private String usageCodeMyAccount = "RPD";
	private final String SIGN_WS = "Check MYACCNT";
	private final String SITE_WS = "SIC WS";
	private final String MODIF_SIGNATURE_CODE = "M";

	/*PROTECTED REGION END*/

	/**
	 * Constructeur vide
	 */
	public MyAccountDS() {
	}

	/**
	 * Search Individual Data on INDIVIDUS_ALL.
	 * @param request in IndividualInformationRequestDTO
	 * @return The searchIndividualInformation as <code>IndividuDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(readOnly=true)
	public IndividuDTO searchIndividualInformationAll(IndividualInformationRequestDTO request) throws JrafDomainException {

		if (request.getPopulationTargeted() == null) {
			request.setPopulationTargeted("A");
		}

		return searchIndividualInformation(request);
	}
	/**
	 * Search Individual Data on INDIVIDUS.
	 * @param request in IndividualInformationRequestDTO
	 * @return The searchIndividualInformation as <code>IndividuDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(readOnly=true)
	public IndividuDTO searchIndividualInformation(IndividualInformationRequestDTO request) throws JrafDomainException {
		/*PROTECTED REGION ID(_t3NBATKTEeCdBYomX6pnCg) ENABLED START*/
		try {			
			if(log.isDebugEnabled()) {
				log.debug("MyAccountDS:searchIndividualInformation : BEGIN" );
			}

			if(log.isDebugEnabled()) {
				log.debug("Id TYPE  : " + request.getOption());
				log.debug("Id VALUE : " + request.getIdentificationNumber());
				log.debug("Id TARGET : " + request.getPopulationTargeted());
			}

			if (request.getPopulationTargeted() == null) {
				// On recherche les Individus I
				request.setPopulationTargeted("I");
			}

			if(log.isDebugEnabled()) {
				log.debug("Look for the individual with the contract number.");
			}

			IndividuDTO individuDTO = new IndividuDTO();

			IdentifierOptionTypeEnum option = IdentifierOptionTypeEnum.getEnumMandatory(request.getOption());

			String gin = "";

			switch(option) {

			case FLYING_BLUE:
			case SUSCRIBER:
			case AMEX:
			case SAPHIR:
			case ALL_CONTRACTS:
				
				RoleContratsDTO roleContrat = new RoleContratsDTO();
				List<RoleContratsDTO> listContrat = roleDS.findAll(request.getIdentificationNumber(), false);
				if(log.isDebugEnabled()) {
					log.debug("Look for the individual, number of GIN found with the contract number : " + listContrat.size());
				}

				// No contract data found -> test with padding (FB contracts)
				if(listContrat==null || listContrat.isEmpty()) {

					String FPcompleted = org.apache.commons.lang.StringUtils.leftPad(request.getIdentificationNumber(), 12, "0");
					listContrat = roleDS.findAll(FPcompleted);

				}

				// REPIND-255 : On trace une donnee a forte valeur ajoute (TYPE+ID)
				// Not only 1 contract data found -> stop
				if(listContrat==null) {
					if (request != null) {
						log.warn("Contract NULL for " + request.getOption() + "=" + request.getIdentificationNumber() + ", return null.");
					}
					break;
				} else if (listContrat.size() != 1) {
					if (request != null) {

						String identificationNumber = request.getIdentificationNumber();

						// REPIND-1175 : PCIDSS Check length of input number in order to mask CCNR
						// TODO : Do not check only lenght but search if it is a CCNR for correct algo to mask only in this case.
						identificationNumber = maskingPCIDSS(identificationNumber, true);
						log.warn(listContrat.size() + " Contract found for " + request.getOption() + "=" + identificationNumber + ", return null.");
					}
					break;
				}

				// if(listContrat==null || listContrat.size() != 1) {
				//	 log.error("Found "+listContrat.size() + " GIN for the contract number, return null.");
				// 	 break;
				// }

				roleContrat = listContrat.get(0);

				if(roleContrat==null || roleContrat.getGin() == null ) {
					break;
				}

				gin = roleContrat.getGin();

				break;

			case ANY_MYACCOUNT:

				ProvideGinForUserIdResponseDTO responseForMyAccountDatas = null;
				ProvideGinForUserIdRequestDTO  requestMyAccountDatas = new ProvideGinForUserIdRequestDTO();

				requestMyAccountDatas.setIdentifier(request.getIdentificationNumber());
				requestMyAccountDatas.setIdentifierType("MA");

				// Call DS method to get Gin with Any MyAccount identifiers
				responseForMyAccountDatas = provideGinForUserId(requestMyAccountDatas);

				if(responseForMyAccountDatas != null)
				{
					// If Gin found in AccountData
					if(responseForMyAccountDatas.getGin() != null) {
						gin = responseForMyAccountDatas.getGin();
						// Gin not found on account data Table
					} else {
						if(log.isDebugEnabled()) {
							log.debug("The accountDataDTO was not found in the table ACCOUNTDATA.");
						}
						if(log.isDebugEnabled()) {
							log.debug("MyAccountDS:searchIndividualInformation : END" );
						}
						return null;
					}
				}

				break;

			case GIN:
				gin = request.getIdentificationNumber();
				break;
			case UCCRID:
				gin = roleUCCRDS.getGinByUCCRID(request.getIdentificationNumber());
				break;
			case GIGYAID:
				AccountDataDTO accounData = new AccountDataDTO();
				accounData.setSocialNetworkId(request.getIdentificationNumber());
				// TODO delete this control once GIGYA migration in EID from SN is done
				List<AccountDataDTO> accountDataDTOList = myAccountDataDS.findByExample(accounData);
				// Not only 1 contract data found -> stop
				if(accountDataDTOList == null || accountDataDTOList.size() != 1) {
					String extId = refTypExtIDDS.getExtIdByOption(option.toString());
					gin = searchInExternalIdentifier(extId, request.getIdentificationNumber());
					break;
				}
				accounData = accountDataDTOList.get(0);
				if(accounData==null || accounData.getSgin() == null ) {
					break;
				}
				gin = accounData.getSgin();
				break;

			case FACEBOOKID:
			case TWITTERID:
			default:
				// XXX Default has been added in case we add others ext IDs for provide.
				String extId = refTypExtIDDS.getExtIdByOption(option.toString());
				gin = extId != null ? searchInExternalIdentifier(extId, request.getIdentificationNumber()) : null;
				break;

			}

			// REPIND-255 : Nettoyage des traces Logs - On a deja trace ce cas plus haut
			if(org.apache.commons.lang.StringUtils.isEmpty(gin)) {
				// log.warn("GIN empty for the type of option '" + request.getOption() +"'.");
				return null;
			}

			individuDTO = new IndividuDTO();
			individuDTO.setSgin(gin);
			individuDTO.setType(request.getPopulationTargeted());	// I by default, T for Traveler
			individuDTO = individuDS.getIndividualOnly(individuDTO);

			//			/*
			//			 * TODO
			//			 * To adapt for prospect migration
			//			 *
			//			 */
			//			// REPIND-854 : Prepare prospect migration
			//			if(individuDTO != null && !individuDTO.getType().equals("W")) {
			return individuDTO;
			//			} else {
			//				return null;
			//			}
		} catch (UnsupportedOperationException e) {
			if(log.isErrorEnabled()) {
				log.error("Error during execute searchIndividualInformation : ", e);
			}
			throw new UnsupportedOperationException();
		}

		/*PROTECTED REGION END*/
	}

	/**
	 * Masking using PCIDSS or custom method
	 */
	// REPIND-1175 : PCIDSS Check length of input number in order to mask CCNR
	// Boolean seems to be a masking since first char (FALSE) or not (TRUE)  
	public String maskingPCIDSS(String fieldPreferences,boolean f){
		return SicStringUtils.maskingPCIDSS(fieldPreferences, f);
	}

	// Search in EXTERNAL_IDENTIFIER
	private String searchInExternalIdentifier(String identifier_type, String identifier_id) throws JrafDomainException {
		String gin = null;
		// Search In External Indentifier table
		List<ExternalIdentifierDTO> externalIdentifierDTOList = externalIdentifierDS.findByTypeAndId(identifier_type, identifier_id);

		// Not only 1 external ID found -> stop
		if(externalIdentifierDTOList == null || externalIdentifierDTOList.size() != 1) {
			int size = 0;
			if(externalIdentifierDTOList != null){
				size = externalIdentifierDTOList.size();
			}
			log.error("Found " + size + " GIN for the GIGYAID " + identifier_id + ", return null.");
			return null;
		}

		ExternalIdentifierDTO externalIdentifierDTO = externalIdentifierDTOList.get(0);
		if(externalIdentifierDTO==null || externalIdentifierDTO.getGin() == null ) {
			return null;
		}
		gin = externalIdentifierDTO.getGin();

		return gin;
	}

	/**
	 * deleteMyAccountCustomerConnectionData
	 * @param connectionData in MyAccountUpdateConnectionDataDTO
	 * @return The deleteMyAccountCustomerConnectionData as <code>String</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String deleteMyAccountCustomerConnectionData(MyAccountUpdateConnectionDataDTO connectionData) throws JrafDomainException {
		/*PROTECTED REGION ID(_PqlGoD6iEeCVaIg6zMycQQ) ENABLED START*/
		AccountDataDTO accountData = new AccountDataDTO();
		accountData.setSgin(connectionData.getGin());
		List<AccountDataDTO> accountDataList = myAccountDataDS.findByExample(accountData);

		if(log.isInfoEnabled()) {
			log.info("MyAccountDS:deleteMyAccountCustomerConnectionData : BEGIN" );
		}

		if (accountDataList.isEmpty()) {
			throw new NotFoundException("Account Data not found with gin "+connectionData.getGin());
		}

		AccountDataDTO foundAccount = accountDataList.get(0);
		if(connectionData.getStatus()!=null)
		{
			if(connectionData.getStatus().equals("C") || connectionData.getStatus().equals("D"))
			{
				throw new NotFoundException("Account Data gin: "+connectionData.getGin()+ " already deleted");
			}
		}
		foundAccount.setStatus("D");
		Date now = new Date();
		foundAccount.setAccountDeletionDate(now);
		foundAccount.setDateModification(now);
		if (connectionData.getSignature()!=null) {
			foundAccount.setSignatureModification(connectionData.getSignature().getSignature());
			foundAccount.setSiteModification(connectionData.getSignature().getSite());
		}
		myAccountDataDS.update(foundAccount);

		RoleContratsDTO roleContrat = new RoleContratsDTO();
		roleContrat.setGin(connectionData.getGin());
		roleContrat.setNumeroContrat(foundAccount.getAccountIdentifier());
		roleContrat.setTypeContrat("MA");
		List<RoleContratsDTO> roleContratList = roleDS.findAll(roleContrat);

		if (roleContratList.isEmpty()) {
			log.error("Role Contract not found with gin "+connectionData.getGin()+" contract number "+foundAccount.getAccountIdentifier());
		} else {
			RoleContratsDTO foundRoleContrat = roleContratList.get(0);
			foundRoleContrat.setEtat("A");
			foundRoleContrat.setDateModification(now);
			if (connectionData.getSignature()!=null) {
				foundRoleContrat.setSignatureModification(connectionData.getSignature().getSignature());
				foundRoleContrat.setSiteModification(connectionData.getSignature().getSite());
			}
			roleDS.update(foundRoleContrat);
		}

		if(log.isInfoEnabled()) {
			log.info("MyAccountDS:deleteMyAccountCustomerConnectionData : END" );
		}
		return accountData.getSgin();
		/*PROTECTED REGION END*/
	}


	/**
	 * provideGinForUserId
	 * @param request in ProvideGinForUserIdRequestDTO
	 * @return The provideGinForUserId as <code>ProvideGinForUserIdResponseDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(readOnly=true)
	public ProvideGinForUserIdResponseDTO provideGinForUserId(ProvideGinForUserIdRequestDTO request) throws JrafDomainException {
		/*PROTECTED REGION ID(_OCAREEAoEeCIPKo0Ux_GSA) ENABLED START*/

		return myAccountUS.provideGinForUserId(request);
		/*PROTECTED REGION END*/
	}

	/**
	 * provideGinForUserId
	 * @param request in ProvideGinForUserIdRequestDTO
	 * @return The provideGinForUserId as <code>ProvideGinForUserIdResponseDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public ProvideGinForUserIdResponseDTO provideGinForUserIdV2(ProvideGinForUserIdRequestDTO request) throws JrafDomainException {
		/*PROTECTED REGION ID(_OCAREEAoEeCIPKo0Ux_GSA) ENABLED START*/

		return myAccountUS.provideGinForUserIdV2(request);
		/*PROTECTED REGION END*/
	}


	/**
	 * provideMyAccountDataByGin
	 * @param gin in String
	 * @return The provideMyAccountDataByGin as <code>AccountDataDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(readOnly=true)
	public AccountDataDTO provideMyAccountDataByGin(String gin) throws JrafDomainException {
		/*PROTECTED REGION ID(_uFSsUUC1EeC326x2eLSwkA) ENABLED START*/
		try {
			log.info("MyAccountDS:provideMyAccountDataByGin : BEGIN" );
			log.debug("MyAccountDS:provideMyAccountDataByGin with gin " + gin);

			List<AccountDataDTO> dtoFounds = null;
			AccountDataDTO currentAccount = new AccountDataDTO();
			currentAccount.setSgin(gin);

			dtoFounds = myAccountDataDS.findOnlyAccountByGin(gin);

			if(dtoFounds != null && dtoFounds.size() >= 1) {
				log.debug("Found 1 AccountDataDTO : " + dtoFounds.get(0));
				log.debug("MyAccountDS:provideMyAccountDataByGin : END" );

				return dtoFounds.get(0);

			} else if(dtoFounds != null){
				log.warn("Found " + dtoFounds.size() + " AccountDataDTO. We should found 1. Verify the problem.");
			}

			log.info("MyAccountDS:provideMyAccountDataByGin : END" );

			return null;
		} catch (UnsupportedOperationException e) {
			log.error("MyAccountDS:provideMyAccountDataByGin : Error during execute   : ", e);
			throw new UnsupportedOperationException();
		}
		/*PROTECTED REGION END*/
	}


	/**
	 * enrollMyAccountCustomer
	 * @param request in MyAccountCustomerRequestDTO
	 * @return The enrollMyAccountCustomer as <code>MyAccountCustomerResponseDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public MyAccountCustomerResponseDTO enrollMyAccountCustomer(MyAccountCustomerRequestDTO request, boolean createComPref, boolean groupContext) throws JrafDomainException {
		/*PROTECTED REGION ID(_HMN2AEM-EeCk2djT-5OeOA) ENABLED START*/
	
		if(log.isInfoEnabled()) {
			log.info("MyAccountDS:enrollMyAccountCustomer : BEGIN" );
		}

		MyAccountCustomerResponseDTO result = new MyAccountCustomerResponseDTO();

		// **********************************
		// Check signature validity
		// **********************************
		if (request.getSignature() != null) {
			Date today = Calendar.getInstance().getTime();
			request.getSignature().setDate(today);
			request.getSignature().setHeure(SicDateUtils.computeHour(today));
			request.getSignature().setTypeSignature("C");
		} 
		else {
			throw new MissingParameterException("Missing signature");
		}

		// *********
		// Transforming Signature to SignatureDTO
		// ********
		com.airfrance.repind.dto.individu.SignatureDTO signatureDto = new com.airfrance.repind.dto.individu.SignatureDTO();
		if(request.getSignature().getDate()!=null) {
			signatureDto.setDate(request.getSignature().getDate());
		} 
		else {
			throw new MissingParameterException("Missing signature");
		}
		if(request.getSignature().getSignature()!=null) {
			signatureDto.setSignature(request.getSignature().getSignature());
		} 
		else {
			throw new MissingParameterException("Missing signature");
		}
		if(request.getSignature().getSite()!=null) {
			signatureDto.setSite(request.getSignature().getSite());
		} 
		else {
			throw new MissingParameterException("Missing signature");
		}


		// **********************************
		// Check EMAIL validity
		// **********************************

		RoleContratsDTO roleContratMyAccount = null;
		if(request.getGin() != null) {                                                
			// Search an account data not cancelled with same email but different GIN and fail if exist
			// If an account with same mail but different GIN and with D status we removed it from data base
			AccountData accountData = accountDataRepository.findByEmailIdentifier(request.getEmailIdentifier().toLowerCase());
			if(accountData != null) {
				if(!accountData.getSgin().equals(request.getGin())) {
					if(!"D".equals(accountData.getStatus())) {
						throw new AlreadyExistException("Email identifier already used");
					} else if("D".equals(accountData.getStatus())) {
						accountDataRepository.deleteBySgin(accountData.getSgin());
					}
				}
				// An account data already exist, we now search a MyAccount contract not cancelled and fail if exist.
				// Otherwise we keep found cancelled MyAccount contract for update.
				roleContratMyAccount = getRoleContractMyAccount(request.getGin());
			}
		} 
		else {
			// Search an Account Data with Email and fail if exist
			int accountFound = accountDataRepository.countByEmail(request.getEmailIdentifier().toLowerCase());
			if (accountFound > 0) {
				throw new AlreadyExistException("Email identifier already used");
			}
		}

		// Bussiness Rule for LastName and FirstName, Only iso latin caracters
		if (!NormalizedStringUtils.isNormalizableString(request.getLastName())) {
			throw new InvalidParameterException("Invalid character in lastname");
		}
		if (!NormalizedStringUtils.isNormalizableString(request.getFirstName())	) {
			throw new InvalidParameterException("Invalid character in firstname");
		}
		
		// Search EMAIL for flying blue members
		EmailDTO currentEmail = new EmailDTO();
		currentEmail.setEmail(request.getEmailIdentifier().toLowerCase());
		List<EmailDTO> dtoEmailFounds = emailDS.findByEmail(currentEmail.getEmail());

		// Si on trouve des emails correspondants en base
		// on vÃ©rifie qu'il n'y en a pas plusieurs de type FP
		if(!dtoEmailFounds.isEmpty()) {
			if(log.isInfoEnabled()) {
				log.info("EnrollMyAccountCustomer: email founds : " + request.getEmailIdentifier());
			}
			for (EmailDTO currentEmailDTO : dtoEmailFounds) {
				String currStatutMedium = currentEmailDTO.getStatutMedium();
				String currGIN = currentEmailDTO.getSgin();
				String reqGIN = request.getGin();
				//Si le GIN est renseignÃ© on va chercher une correspondance seulement
				// sur le mail d'un GIN diffÃ©rent.
				if(currStatutMedium != null && !StatutEmailEnum.X.getStatus().equals(currStatutMedium) &&
						currGIN != null && !"".equals(currGIN.trim()) && (reqGIN == null ||
						(reqGIN != null && !reqGIN.trim().equals(currGIN.trim()))) )
				{
					RoleContratsDTO currentRoleContrat = new RoleContratsDTO();
					currentRoleContrat.setGin(currGIN);
					currentRoleContrat.setTypeContrat("FP");
					Integer count = roleDS.countAll(currentRoleContrat);
					if ( count > 0) {
						if(log.isInfoEnabled()) {
							log.info("EnrollMyAccountCustomer: email already used by Flying blue member : " + request.getEmailIdentifier());
						}
						throw new ContractExistException("Email identifier already used");
					}
				}
			}// end for dtoEmailFounds
		}// end if(!dtoEmailFounds.isEmpty())
		else {
			//REPIND-914: Avoid duplicate emails when creating a MyAccount (WS EnrollMyAccount / B2C)
			if (request.getGin() != null) {
				//REPIND-914: Put the wrong address into H Statut
				currentEmail.setSgin(request.getGin());
				currentEmail.setStatutMedium("V");
				currentEmail.setCodeMedium("D");
				currentEmail.setEmail(null);
				List<EmailDTO> dtoEmailCommFound = emailDS.findByExample(currentEmail);
				if (dtoEmailCommFound != null && !dtoEmailCommFound.isEmpty()) {
					currentEmail = dtoEmailCommFound.get(0);
					currentEmail.setStatutMedium("H");
					currentEmail.setDateModification(Calendar.getInstance().getTime());
					currentEmail.setSignatureModification(request.getSignature().getSignature());
					currentEmail.setSiteModification(request.getSignature().getSite());
					emailDS.update(currentEmail);
				}
				//REPIND-914: Create new address relating to the email_identifier
				EmailDTO newEmail = new EmailDTO();
				newEmail.setVersion(0);
				newEmail.setDateCreation(Calendar.getInstance().getTime());
				newEmail.setDateModification(Calendar.getInstance().getTime());
				newEmail.setSgin(request.getGin());
				newEmail.setSignatureCreation(request.getSignature().getSignature());
				newEmail.setSignatureModification(request.getSignature().getSignature());
				newEmail.setSiteCreation(request.getSignature().getSite());
				newEmail.setSiteModification(request.getSignature().getSite());
				newEmail.setCodeMedium("D");
				newEmail.setStatutMedium("V");
				newEmail.setEmail(SicStringUtils.normalizeEmail(request.getEmailIdentifier()));

				// REPIND-260 : Amelioration de la couverture des TU - getOptIn() peut etre NULL
				if(createComPref){
					if (request.getOptIn() != null && request.getOptIn()) {
						newEmail.setAutorisationMailing("T"); // AF + PATNER
					} else {
						newEmail.setAutorisationMailing("N"); // NONE
					}
				}else{
					//TODO: setAutorisationMailing for v3
					newEmail.setAutorisationMailing("N");
				}
				emailDS.create(newEmail);
			}
		}
		// Search individual with GIN if provided
		Individu individu = null;
		if(request.getGin() != null) {
			IndividuDTO individuDTO = individuDS.getByGin(request.getGin());
			if(individuDTO != null && !individuDTO.getType().equals("W")) {
				individu = IndividuTransform.dto2Bo(individuDTO);
			}
		}

		// Search individual with email, firstname and lastname
		if(individu == null) {
			List individualFound = individuDS.findBy(request.getFirstName(), request.getLastName(), request.getEmailIdentifier(), false, false);
			//TODO: fix - it's > 1
			if (individualFound.size() > 2) {
				throw new SeveralIndividualException("More than one individual found with " + request.getFirstName() +" "+ request.getLastName()  +" "+ request.getEmailIdentifier());
			}
			if (individualFound.size() > 0) {
				// Use individual found
				individu = (Individu)individualFound.get(0);
				if(individu != null && individu.getType().equals("W")) {
					individu = null;
				}
			}
		}
		// **********************************
		// Check if Prospet Exist
		// **********************************
		String modeUpdateCp = UpdateModeEnum.UPDATE.toString();
		List<CommunicationPreferencesDTO> commPrefsExist = new ArrayList<>();
		String prospectGin = this.getProspectComPref(request.getEmailIdentifier().toLowerCase(),commPrefsExist);

		// **********************************
		//  Create Individus if not Exist
		// **********************************
		if (individu == null) {
			// Individual Creation
			log.info("Creation de l'individu");
			
			CreateUpdateIndividualRequestDTO requestDTO = enrollMyAccountMapper.wsRequestToIndividualCommon(request); 

			// Requestor
			if (requestDTO.getRequestorDTO() != null) {
				if (requestDTO.getRequestorDTO().getApplicationCode() == null || "".equals(requestDTO.getRequestorDTO().getApplicationCode())) {
					requestDTO.getRequestorDTO().setApplicationCode(usageCodeMyAccount);
				}
			}
			else {
				RequestorDTO requete = new RequestorDTO();
				requete.setApplicationCode(usageCodeMyAccount);
				requestDTO.setRequestorDTO(requete);
			}
			
			if (requestDTO.getIndividualRequestDTO() != null && requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO() != null) {
				
				// If enrollement with "M_" MADAM OR MISTER  change it to "M." for database
				String civility = requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO().getCivility();
				if (civility == null) {
					throw new MissingParameterException("The field civility is mandatory");
				} else if ("M_".equalsIgnoreCase(civility) || "M".equalsIgnoreCase(civility)) {
					requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO().setCivility("M.");
				}
				
				requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO().setStatus("P");
				
				// REPIND-2066: fix issue on language
				String lang = requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO().getLanguageCode();
				if (lang == null || EnrollConstant.EMPTY_STRING.equals(lang)) {
					lang = EnrollConstant.LANG_ENGLISH;
				}
				IndividualProfilDTO profilDTO = new IndividualProfilDTO();
				profilDTO.setLanguageCode(lang);
				
				requestDTO.getIndividualRequestDTO().setIndividualProfilDTO(profilDTO);
			}
			
			// Add empty compref to avoi NPE
			List<CommunicationPreferencesRequestDTO> comprefListDummy = new ArrayList<CommunicationPreferencesRequestDTO>();
			requestDTO.setCommunicationPreferencesRequestDTO(comprefListDummy);

			CreateModifyIndividualResponseDTO responseDTO = individuDS.createOrUpdateIndividual(requestDTO);
			
			log.info("Individu créé : " + responseDTO.getGin());
			
			individu = individuRepository.findBySgin(responseDTO.getGin());
			
			if (individu == null) {
				throw new JrafDomainException("Error in creating individual");
			}

			// ==== Create or update EMAIL DATA ====

			List<EmailDTO> emailListFromWS = transformToEmailDTO(request);
			emailDS.updateEmail(responseDTO.getGin(), emailListFromWS, signatureDto);

			try {
				if(log.isDebugEnabled()) {
					log.debug("updateNomPrenomSC !!");
				}
				//getIndividuDao().updateNomPrenomSC(reponseAdh.getIndividu().getNumeroClient(), request.getLastName(), request.getFirstName());
			} catch(Exception e) {
				log.info("Impossible de modifier nom/prenomSC");
				// e.printStackTrace();
				log.fatal(e);
			}

		} 
		else if (dtoEmailFounds.isEmpty()) {

		}

		// *********************************************
		// Create Account and Communication Preferences
		// *********************************************
		if (individu.getRolecontrats() != null && individu.getRolecontrats().size() > 0 && isMyAccountNotDeleted(individu.getRolecontrats())) {
			throw new ContractExistException("Individual have already a contract");
		} 
		else {
			// Create an usage Client for individual
			usageClientsDS.add(individu.getSgin(), usageCodeMyAccount, request.getSignature().getDate());
			
			//REPIND-1532: Calling from Enroll V1 or V2, we do not use Group for creating comprefs
			if (!groupContext) {
				// Create a communication Preference
				//TODO:
				//ajout des comPref existantes (rÃ©cupÃ©rÃ© du prospect)
				// REPIND-260 : Amelioration de la couverture des TU - getOptIn() peut etre NULL
				// REPIND-1232, disable the creation of communication Preferences for the v3 (EnrollMyAccountCustomer)
				if(createComPref){
					if(request.getOptIn() != null && request.getOptIn()){
						List<CommunicationPreferencesDTO> commPrefs = new ArrayList<>();
						CommunicationPreferencesDTO commPref = new CommunicationPreferencesDTO();
						commPref.setComGroupType("N");
						commPref.setComType("AF");
						commPref.setSubscribe("Y");
						commPref.setDomain("S");
						commPref.setDateOptin(new Date());
						commPref.setChannel("MYA");
						MarketLanguageDTO ml = new MarketLanguageDTO();
						ml.setOptIn("Y");
						ml.setMarket(request.getPointOfSell());
						ml.setLanguage(request.getLanguage());
						ml.setDateOfConsent(new Date());
						Set<MarketLanguageDTO> mls = new HashSet<>();
						mls.add(ml);
						commPref.setMarketLanguageDTO(mls);
						commPrefs.add(commPref);

						if(!commPrefsExist.isEmpty()){
							//Set <CommunicationPreferences> commPrefExist = CommunicationPreferencesTransform.dto2Bo(commPrefsExist); //on rÃ©cupÃ¨re les comprefs existantes sur le prospect
							Set <CommunicationPreferences> commPrefNew = CommunicationPreferencesTransform.dto2Bo(commPrefs); // on rÃ©cupÃ¨re les nouvelles comprefs Ã  ajouter
							//CommunicationPreferencesTransformer.compareAndUpdate(commPrefExist, commPrefNew, signatureDto); // on update les commprefs existante en modifiant ou ajoutant les nouvelles valeurs
							commPrefs =  CommunicationPreferencesTransform.bo2Dto(commPrefNew); // puis on place le rÃ©sultat dans l'objet commPref
						}
						if(individu.getAccountData() != null) {
							individu.getAccountData().setIndividu(individu);
							individu.setVersion(individuRepository.findBySgin(individu.getSgin()).getVersion());
						}
						updateCommunicationPreferences(individu,commPrefs,modeUpdateCp,signatureDto,false, true);
					}

					else{
						if(!commPrefsExist.isEmpty()) {
							updateCommunicationPreferences(individu,commPrefsExist,modeUpdateCp,signatureDto,false, true);
						}
					}
				}
			}
			
			AccountDataDTO accountData = null;
			if(individu.getAccountData() != null) {
				// We modify the account Data
				accountData = AccountDataTransform.bo2Dto(individu.getAccountData());
			} else {
				// Create an account Data
				accountData = new AccountDataDTO();
				accountData.setSgin(individu.getSgin());
				accountData.setDateCreation(request.getSignature().getDate());
				accountData.setNbFailureAuthentification(0);
				accountData.setNbFailureSecretQuestionAns(0);
				accountData.setPasswordToChange(null);
			}
			// Case of an account data only Flying Blue
			if(accountData.getAccountIdentifier() == null) {
				accountData.setAccountIdentifier(myAccountDataDS.getMyAccountIdentifier());
			}
			accountData.setEmailIdentifier(request.getEmailIdentifier());
			accountData.setDateModification(request.getSignature().getDate());
			accountData.setLastConnexionDate(Calendar.getInstance().getTime());
			accountData.setEnrolmentPointOfSell(request.getPointOfSell());
			accountData.setCarrier(request.getWebsite());

			// Add new Valid status to account because of new connection parameters
			accountData.setStatus("V");

			if (request.getPassword() != null && request.getPassword().length() > 0) {
				try {
					accountData.setPassword(
							EncryptionUtils.hashPBKDF2WithHmacSHA1(individu.getSgin(), request.getPassword()));
				} catch (JrafApplicativeException e) {
					// e.printStackTrace();
					log.fatal(e);
					throw new JrafDomainRollbackException(e);
				}
			}
			if(individu.getAccountData() == null) {
				myAccountDataDS.create(accountData);
				accountData.setPasswordToChange(null);
			}
			myAccountDataDS.update(accountData);

			String version = null;
			if(roleContratMyAccount == null) {
				// Search an existing MyAccount because the client change his email address.
				roleContratMyAccount = getMyAccountNotDeleted(individu.getRolecontrats());
			}
			if(roleContratMyAccount != null) {
				// Update existing Role Contract
				updateRoleContractMyAccount(roleContratMyAccount, request);
				version = String.valueOf(roleContratMyAccount.getVersion());
			} else {
				// Create Role Contract
				roleDS.createMyAccountContract(individu.getSgin(), accountData.getAccountIdentifier(), request.getWebsite(), request.getSignature());
			}
			
			//REPIND-1532: Calling from Enroll V3, we use Group for creating comprefs
			if (groupContext && !StringUtils.equalsIgnoreCase(request.getWebsite(), "BB")) {
				//We keep a trace of previous comprefs before deleting them and update after group
				List<CommunicationPreferencesDTO> comprefsExisting = communicationPreferencesDS.findCommunicationPreferences(individu.getSgin());
				if (!UList.isNullOrEmpty(comprefsExisting)) {
					individu.getCommunicationpreferences().clear();
					individuRepository.saveAndFlush(individu);
				}
				
				//REPIND-1508: Create Compref via Group
				String channel = request.getChannel();
				if (channel == null) channel = "B2C";
				Map<InformationDTO, CommunicationPreferencesDTO> resultGroup = communicationPreferencesDS.createComPrefBasedOnAGroup(individu.getSgin(), "MA", null, request.getPointOfSell(), request.getLanguage(), request.getSignature().getSignature(), request.getSignature().getSite(), channel);
				for (Map.Entry<InformationDTO, CommunicationPreferencesDTO> entry : resultGroup.entrySet()) {
				    if (entry.getKey().getCode().equalsIgnoreCase("1")) {
				    	log.error("ENROLLMYACCOUNT: Compref " + entry.getKey().getName() + " not created for gin " + individu.getSgin() + " -> " + entry.getKey().getDetails());
				    }
				}
				
				//Restore previous existing comprefs
				if (!UList.isNullOrEmpty(comprefsExisting)) {
					updateComprefEnroll(individu, comprefsExisting);
				}
			}
			
			// Construct reponse
			result.setAccountID(accountData.getAccountIdentifier());
			result.setContractRole(accountData.getAccountIdentifier());
			result.setGin(individu.getSgin());
			result.setContractType("C");
			result.setContractStatus("C");
			result.setEmail(accountData.getEmailIdentifier());
			result.setProductType("MA");
			result.setVersion((version != null) ? version : "0");
			result.setValidityStartDate(request.getSignature().getDate());
		}


		log.info("Individu crÃ©Ã© ou trouvÃ© : "+individu.getSgin());

		if(log.isInfoEnabled()) {
			log.info("MyAccountDS:enrollMyAccountCustomer : END" );
		}
		return result;
		/*PROTECTED REGION END*/
	}
	
	// REPIND-1546 : Critical SONAR muste be public or without Trasactional
	// @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	@LoggableNoParams
	private void updateComprefEnroll(Individu individu, List<CommunicationPreferencesDTO> comprefsExisting) throws JrafDomainException {
		individu = individuRepository.findBySgin(individu.getSgin());
		for (CommunicationPreferencesDTO comprefExisting : comprefsExisting) {
			boolean isComprefAlreadyPresent = false;
			for (CommunicationPreferences compref : individu.getCommunicationpreferences()) {
				//Same Compref
				if (compref.getDomain().equalsIgnoreCase(comprefExisting.getDomain()) && compref.getComGroupType().equalsIgnoreCase(comprefExisting.getComGroupType()) && compref.getComType().equalsIgnoreCase(comprefExisting.getComType())) {
					isComprefAlreadyPresent = true;
					if (!UList.isNullOrEmpty(comprefExisting.getMarketLanguageDTO())) {
						for (MarketLanguageDTO marketLanguageExisting : comprefExisting.getMarketLanguageDTO()) {
							boolean isMLAlreadyPresent = false;
							if (!UList.isNullOrEmpty(compref.getMarketLanguage())) {
								for (MarketLanguage marketLanguage : compref.getMarketLanguage()) {
									//Same Market Language
									if (marketLanguage.getMarket().equalsIgnoreCase(marketLanguageExisting.getMarket()) && marketLanguage.getLanguage().equalsIgnoreCase(marketLanguageExisting.getLanguage())) {
										isMLAlreadyPresent = true;
										//We keep previous compref info
										compref.setCreationDate(comprefExisting.getCreationDate());
										compref.setCreationSignature(comprefExisting.getCreationSignature());
										compref.setCreationSite(comprefExisting.getCreationSite());
										compref.setModificationDate(comprefExisting.getModificationDate());
										compref.setModificationSignature(comprefExisting.getModificationSignature());
										compref.setModificationSite(comprefExisting.getModificationSite());
										compref.setSubscribe(comprefExisting.getSubscribe());
										
										//We keep previous market language info
										marketLanguage.setCreationDate(marketLanguageExisting.getCreationDate());
										marketLanguage.setCreationSignature(marketLanguageExisting.getCreationSignature());
										marketLanguage.setCreationSite(marketLanguageExisting.getCreationSite());
										marketLanguage.setModificationDate(marketLanguageExisting.getModificationDate());
										marketLanguage.setModificationSignature(marketLanguageExisting.getModificationSignature());
										marketLanguage.setModificationSite(marketLanguageExisting.getModificationSite());
										marketLanguage.setOptIn(marketLanguageExisting.getOptIn());
										
									}
								}
								if (!isMLAlreadyPresent) {
									marketLanguageExisting.setMarketLanguageId(null);
									compref.getMarketLanguage().add(MarketLanguageTransform.dto2Bo(marketLanguageExisting));
								}
							}
						}
					}
				}
			}
			if (!isComprefAlreadyPresent) {
				comprefExisting.setComPrefId(null);
				for (MarketLanguageDTO ml : comprefExisting.getMarketLanguageDTO()) {
					ml.setMarketLanguageId(null);
				}
				individu.getCommunicationpreferences().add(CommunicationPreferencesTransform.dto2Bo2(comprefExisting));
			}
		}
		
		individuRepository.saveAndFlush(individu);	
	}

	private List<EmailDTO> transformToEmailDTO(MyAccountCustomerRequestDTO request) {
		List<EmailDTO> resultList = new ArrayList<EmailDTO>();

		if (request != null && request.getEmailIdentifier() != null) {
			EmailDTO email = new EmailDTO();
			email.setEmail(request.getEmailIdentifier());
			email.setCodeMedium("D");
			email.setStatutMedium("V");
			// REPIND-260 : Amelioration de la couverture des TU - getOptIn() peut etre NULL
			if (request.getOptIn() != null && request.getOptIn()) {
				email.setAutorisationMailing("T"); // AF + PATNER
			} else {
				email.setAutorisationMailing("N"); // NONE
			}
			resultList.add(email);
		}

		return resultList;
	}

	private RoleContratsDTO getRoleContractMyAccount(String gin) throws JrafDomainException {
		RoleContratsDTO currentRoleContrat = new RoleContratsDTO();
		currentRoleContrat.setGin(gin);
		currentRoleContrat.setTypeContrat("MA");
		List<RoleContratsDTO> roleContratsDTOList = roleDS.findAll(currentRoleContrat);
		if(!roleContratsDTOList.isEmpty()) {
			for(RoleContratsDTO roleContratsDTOLoop : roleContratsDTOList) {
				if (!"A".equals(roleContratsDTOLoop.getEtat())) {
					throw new ContractExistException("Email identifier already used");
				} else {
					return roleContratsDTOLoop;
				}
			}
		}
		return null;
	}

	protected void updateRoleContractMyAccount(RoleContratsDTO roleContratMyAccount, MyAccountCustomerRequestDTO request) throws JrafDomainException {
		roleContratMyAccount.setEtat("C");
		roleContratMyAccount.setDateDebutValidite(request.getSignature().getDate());
		roleContratMyAccount.setSignatureModification(request.getSignature().getSignature());
		roleContratMyAccount.setSiteModification(request.getSignature().getSite());

		BusinessRoleDTO businessRoleDTO = roleContratMyAccount.getBusinessroledto();

		List<BusinessRole> businessRoleList = businessRoleRepository.findAll(Example.of(BusinessRoleTransform.dto2BoLight(businessRoleDTO)));

		BusinessRole businessRole = businessRoleList.get(0);
		businessRole.setDateModification(request.getSignature().getDate());
		businessRole.setSignatureModification(request.getSignature().getSignature());
		businessRole.setSiteModification(request.getSignature().getSite());
		businessRoleRepository.saveAndFlush(businessRole);

		roleDS.update(roleContratMyAccount);
	}

	private boolean isMyAccountNotDeleted(Set<RoleContrats> rolecontrats) {
		for(RoleContrats rolecontratLoop : rolecontrats) {
			if(ProductTypeEnum.MY_ACCOUNT.toString().equals(rolecontratLoop.getTypeContrat())
					&& !"A".equals(rolecontratLoop.getEtat())) {
				return true;
			}
		}
		return false;
	}

	private RoleContratsDTO getMyAccountNotDeleted(Set<RoleContrats> rolecontrats) throws JrafDomainException {
		if(rolecontrats != null && rolecontrats.size() > 0) {
			for(RoleContrats rolecontratLoop : rolecontrats) {
				if(ProductTypeEnum.MY_ACCOUNT.toString().equals(rolecontratLoop.getTypeContrat())
						&& "A".equals(rolecontratLoop.getEtat())) {
					return RoleContratsTransform.bo2Dto(rolecontratLoop);
				}
			}
		}
		return null;
	}

	/**
	 * searchMyAccountComData
	 * @param request in String
	 * @param option in String
	 * @return The searchMyAccountComData as <code>MyAccountComDataResponseDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public MyAccountComDataResponseDTO searchMyAccountComData(String request, String option) throws JrafDomainException {
		/*PROTECTED REGION ID(_Fh1mUFnQEeCBGMNfpbqgjw) ENABLED START*/
		try {

			if(log.isDebugEnabled()) {
				log.debug("Option   : " + option);
				log.debug("Id VALUE : " + request);
			}

			MyAccountComDataResponseDTO response = new MyAccountComDataResponseDTO();
			//    		AccountDataDTO myAccountDataDTO = null;
			//
			//    		if("AC".equals(option) || "FP".equals(option) || "RP".equals(option) || "S".equals(option) || "AX".equals(option)) {
			//        		if(log.isDebugEnabled()) {
			//        			log.debug("Look for the individual with the contract number.");
			//        		}
			//
			//    			RoleContratsDTO roleContrat = new RoleContratsDTO();
			//    			roleContrat.setNumeroContrat(request);
			//    			List<RoleContratsDTO> listContrat = roleDS.findByExample(roleContrat);
			//        		if(log.isDebugEnabled()) {
			//        			log.debug("Look for the individual, number of GIN found with the contract number : " + listContrat.size());
			//        		}
			//    			if(listContrat.size() == 1) {
			//    				roleContrat = listContrat.get(0);
			//    			} else if(log.isWarnEnabled()) {
			//    	    		log.warn("Found "+listContrat.size() + " GIN for the contract number, return null.");
			//    			}
			//
			//    			if(!option.equals(roleContrat.getTypeContrat())) {
			//    				if(log.isWarnEnabled()) {
			//        	    		log.warn("The contract type in not good.");
			//        	    		log.warn("Research type " + option + " but found type " + roleContrat.getTypeContrat());
			//    				}
			//    				return null;
			//    			}
			//
			//    			if(roleContrat != null && roleContrat.getGin() != null) {
			//    	    		myAccountDataDTO = provideMyAccountDataByGin(roleContrat.getGin());
			//    			}
			//    		} else if("AI".equals(option)) {
			//    			AccountDataDTO accountDataDTO = new AccountDataDTO();
			//    			accountDataDTO.setAccountIdentifier(request);
			//
			//    			List<AccountDataDTO> listAccountData = myAccountDataDS.findByExample(accountDataDTO);
			//
			//    			if(listAccountData.size() == 1) {
			//    				accountDataDTO = listAccountData.get(0);
			//    			} else {
			//    				if(log.isWarnEnabled()) {
			//    					log.warn("We found " + listAccountData.size() + " accountDataDTO but we should found 1, verify the problem.");
			//    				}
			//    				return null;
			//    			}
			//
			//    			if(accountDataDTO != null) {
			//    				myAccountDataDTO = accountDataDTO;
			//    			} else if(log.isDebugEnabled()){
			//    				log.debug("The accountDataDTO was not found in the table ACCOUNTDATA.");
			//    			}
			//
			//    		} else if("GIN".equals(option)){
			//        		if(log.isDebugEnabled()) {
			//        			log.debug("Look for the individual with the gin.");
			//        		}
			//
			//    			AccountDataDTO accountDataDTO = new AccountDataDTO();
			//    			accountDataDTO.setSgin(request);
			//
			//    			List<AccountDataDTO> listAccountData = myAccountDataDS.findByExample(accountDataDTO);
			//
			//    			if(listAccountData.size() == 1) {
			//    				accountDataDTO = listAccountData.get(0);
			//    			} else {
			//    				if(log.isWarnEnabled()) {
			//    					log.warn("We found " + listAccountData.size() + " accountDataDTO but we should found 1, verify the problem.");
			//    				}
			//    				return null;
			//    			}
			//
			//    			if(accountDataDTO != null) {
			//    				myAccountDataDTO = accountDataDTO;
			//    			} else if(log.isDebugEnabled()){
			//    				log.debug("The accountDataDTO was not found in the table ACCOUNTDATA.");
			//    			}
			//
			//    		} else if(log.isWarnEnabled()){
			//    			log.warn("The type of option '" + option +"' is unknown.");
			//    		}
			//
			//    		if(myAccountDataDTO == null) {
			//    			if(log.isWarnEnabled()) {
			//    				log.warn("Not found.");
			//    			}
			//    			return null;
			//    		} else {
			//    			response.setMyaccountdatadto(IndividuTransform.createMyAccountDataDTO(myAccountDataDTO));
			//    			if(myAccountDataDTO.getIndividudto() != null) {
			//    				IndividualInformationResponseDTO iiResponse = IndividuTransform.createIndividualResponse(myAccountDataDTO.getIndividudto(), SearchByIdentifierEnum.All);
			//    				if(iiResponse != null && iiResponse.getIndividu() != null && iiResponse.getIndividu().getInfosindividu() != null) {
			//    					response.setInfosindividu(iiResponse.getIndividu().getInfosindividu());
			//    				}
			//    			}
			//    		}


			return response;

		} catch (UnsupportedOperationException e) {
			if(log.isErrorEnabled()) {
				log.error("Error during execute searchIndividualInformation : ", e);
			}
			throw new UnsupportedOperationException();
		}

		/*PROTECTED REGION END*/
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public ReturnDetailsDTO updateMyAccountCustomer(IndividuDTO individuFromWS, List<PostalAddressDTO> postalAddressListFromWS, List<TelecomsDTO> telecomListFromWS, List<EmailDTO> emailListFromWS, MyAccountUpdateConnectionDataDTO myAccountDataFromWS, List<CommunicationPreferencesDTO> commPrefsListFromWS, String newsletterMediaSending, com.airfrance.repind.dto.individu.SignatureDTO signatureFromWS, String accountStatus, String commPrefsUpdateMode, String wantedUpdatedLanguage, String consumerId) throws JrafDomainException {
		return updateMyAccountCustomer(individuFromWS, postalAddressListFromWS, telecomListFromWS, emailListFromWS, myAccountDataFromWS, commPrefsListFromWS, newsletterMediaSending, signatureFromWS, accountStatus, commPrefsUpdateMode, true, true, wantedUpdatedLanguage, consumerId);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public ReturnDetailsDTO updateMyAccountCustomerData(IndividuDTO individuFromWS, List<PostalAddressDTO> postalAddressListFromWS, List<TelecomsDTO> telecomListFromWS, List<EmailDTO> emailListFromWS, MyAccountUpdateConnectionDataDTO myAccountDataFromWS, List<CommunicationPreferencesDTO> commPrefsListFromWS, String newsletterMediaSending, com.airfrance.repind.dto.individu.SignatureDTO signatureFromWS, String accountStatus, String commPrefsUpdateMode, String wantedUpdatedLanguage, String consumerId) throws JrafDomainException {
		return updateMyAccountCustomer(individuFromWS, postalAddressListFromWS, telecomListFromWS, emailListFromWS, myAccountDataFromWS, commPrefsListFromWS, newsletterMediaSending, signatureFromWS, accountStatus, commPrefsUpdateMode, true, false, wantedUpdatedLanguage, consumerId);
	}

	/**
	 * updateMyAccountCustomer
	 * @param individuFromWS in IndividuDTO
	 * @param postalAddressListFromWS in PostalAddressDTO
	 * @param telecomListFromWS in TelecomsDTO
	 * @param emailListFromWS in EmailDTO
	 * @param myAccountDataFromWS in MyAccountUpdateConnectionDataDTO
	 * @param commPrefsListFromWS in CommunicationPreferencesDTO
	 * @param newsletterMediaSending in String
	 * @param signatureFromWS in SignatureDTO
	 * @param accountStatus in String
	 * @param commPrefsUpdateMode in String
	 * @return The updateMyAccountCustomer as <code>ReturnDetailsDTO</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public ReturnDetailsDTO updateMyAccountCustomer(IndividuDTO individuFromWS, List<PostalAddressDTO> postalAddressListFromWS, List<TelecomsDTO> telecomListFromWS, List<EmailDTO> emailListFromWS, MyAccountUpdateConnectionDataDTO myAccountDataFromWS, List<CommunicationPreferencesDTO> commPrefsListFromWS, String newsletterMediaSending, com.airfrance.repind.dto.individu.SignatureDTO signatureFromWS, String accountStatus, String commPrefsUpdateMode, boolean checkGlobalOptin, boolean bdmProcess, String wantedUpdatedLanguage, String consumerId) throws JrafDomainException {


		ReturnDetailsDTO responseForWS = new ReturnDetailsDTO();

		log.debug("MyAccountDS:updateMyAccountCustomer : BEGIN" );

		// Empty individu -> stop
		if (individuFromWS == null && postalAddressListFromWS == null && telecomListFromWS == null && emailListFromWS == null && myAccountDataFromWS == null && commPrefsListFromWS == null) {
			log.debug("MyAccountDS:updateMyAccountCustomer : END" );
			return responseForWS;
		}

		// Individu is missing
		if(individuFromWS == null) {
			throw new MissingParameterException("Individual data is mandatory");
		}

		String gin = individuFromWS.getSgin();
		
		// Get individual from DB
//		IndividuDTO individuFromDB = individuDS.getByGin(gin);
		IndividuDTO individuFromDB = individuDS.getCompleteDataByGin(gin);

/*		Code have been move because we should not be in the same transaction that S08924 call
 * 
 * 		// REPIND-919 : Switch a W or T into I in case of Postal Address is Usage ISI ! 
		if (individuFromDB != null && ("W".equals(individuFromDB.getType()) || "T".equals(individuFromDB.getType()))) {
			
			// On detecte que l on a pas d ADR POST en base de donnees mais que l on en a une en parametre WS
			if ((individuFromDB.getPostaladdressdto() == null || individuFromDB.getPostaladdressdto().size() == 0) && postalAddressListFromWS != null) {
				
				// On cherche si cette adresse est ISI Mailing M
				for (PostalAddressDTO pad : postalAddressListFromWS) {
					
					if (pad != null && pad.getUsage_mediumdto() != null) {
						
						for (Usage_mediumDTO umd : pad.getUsage_mediumdto()) {
						
							// On recherche une address ISI mailling M
							if (umd != null && "ISI".equals(umd.getScode_application()) && 
									("M".equals(umd.getSrole1()) || "M".equals(umd.getSrole2()) || 
									"M".equals(umd.getSrole3()) || "M".equals(umd.getSrole4()) || "M".equals(umd.getSrole5()))) {
								
								// On switch l individu W ou T en I
								individuDS.switchToIndividual(gin);
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
*/		
		// No individual in DB
		if (individuFromDB == null || "W".equals(individuFromDB.getType())) {
			throw new IndivudualNotFoundException("Individual does not exist");
		}

		boolean isFlyingBlue = isFlyingBlue(individuFromDB);
		
		//REPIND-1215 : detect a change in ISI adress country code or spoken language
		//to update the FB communication preferences' market/language
		//REPIND-1680; We have to put the recalculation outside the control in order to trigger it without any restrictions but,
		//this have to be done before the update individual because after the language profil will be updated so no diff between IndividuFromDB and IndividuFromWS
		if (isFlyingBlue) {
			try {
				communicationPreferencesHelper.updateFBMarketLanguagesWhenMoving(individuFromWS, postalAddressListFromWS, commPrefsListFromWS, individuFromDB, signatureFromWS);
			} catch(JrafDomainException e) {
				log.warn("MyAccountDS:updateMyAccountCustomer : Detection of a change of country of residence (or spoken language) but the FB communication preferences could not be updated : " + e.getMessage());
			}
		}

//		if (individuFromWS.getCivilite()!=null || individuFromWS.getNomSC()!=null || individuFromWS.getPrenomSC()!=null || individuFromWS.getDateNaissance()!=null || postalAddressListFromWS != null || telecomListFromWS != null || individuFromWS.getCodeLangue() != null) {
		if (individuFromWS.getCivilite()!=null || individuFromWS.getNomSC()!=null || individuFromWS.getPrenomSC()!=null || individuFromWS.getDateNaissance()!=null || individuFromWS.getCodeLangue() != null || (individuFromWS.getProfilsdto() != null && individuFromWS.getProfilsdto().getScode_langue() != null)) {

			individuDS.updateIndividual(individuFromDB, individuFromWS, signatureFromWS);

//			CreateModifyIndividualResquestDTO resquestForADH = new CreateModifyIndividualResquestDTO();

		}
		
		//REPIND-1573 - Update all Compref and Market Language for FlyingBlue domain in one request
		responseForWS = updateAllMarketLanguageOnFBdomain(commPrefsListFromWS, signatureFromWS, commPrefsUpdateMode, responseForWS, individuFromDB, wantedUpdatedLanguage, consumerId);

		// Update communication preferences
		responseForWS = updateCommunicationPreferences(emailListFromWS, commPrefsListFromWS, signatureFromWS,
				commPrefsUpdateMode, responseForWS, individuFromDB, isFlyingBlue, checkGlobalOptin);

		// Update preferences
		if (bdmProcess) {
			preferenceDS.updateIndividualPreferencesOldProcess(individuFromWS, individuFromDB, signatureFromWS);
		}
		else {
			preferenceDS.updateIndividualPreferences(individuFromWS, individuFromDB, signatureFromWS);
		}
		log.debug("MyAccountDS:updateMyAccountCustomer : END" );
		return responseForWS;
	}

	/**
	 * deleteMyAccountCustomer
	 * @param individu in IndividuDTO
	 * @param address in PostalAddressDTO
	 * @param telecom in TelecomsDTO
	 * @param email in EmailDTO
	 * @param myAccountData in MyAccountUpdateConnectionDataDTO
	 * @param signature in SignatureDTO
	 * @return The deleteMyAccountCustomer as <code>Boolean</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public Boolean deleteMyAccountCustomer(IndividuDTO individu, List<PostalAddressDTO> address, List<TelecomsDTO> telecom, List<EmailDTO> email, MyAccountUpdateConnectionDataDTO myAccountData, com.airfrance.repind.dto.individu.SignatureDTO signature) throws JrafDomainException {
		/*PROTECTED REGION ID(_H6zFIFxqEeC2fu5mfEzrgA) ENABLED START*/

		if(log.isInfoEnabled()) {
			log.info("MyAccountDS:deleteMyAccountCustomer : BEGIN" );
		}

		Individu individuBo = individuRepository.findBySgin(individu.getSgin());
		if (individuBo == null) {
			throw new IndivudualNotFoundException("Individual not exist");
		}


		individuBo.setSignatureModification("REPIND");
		individuBo.setDateModification(new Date());

		// delete My Account Data Customer
		MyAccountUpdateConnectionDataDTO connectionData = new MyAccountUpdateConnectionDataDTO();
		connectionData.setGin(individuBo.getSgin());
		if (signature!=null) {
			com.airfrance.repind.dto.individu.SignatureDTO dataSignature = new com.airfrance.repind.dto.individu.SignatureDTO();
			dataSignature.setSignature(signature.getSignature());
			dataSignature.setSite(signature.getSite());
			connectionData.setSignature(dataSignature );
		}
		deleteMyAccountCustomerConnectionData(connectionData);
		if(log.isInfoEnabled()) {
			log.info("MyAccountDS:deleteMyAccountCustomer : END" );
		}
		return true;
		/*PROTECTED REGION END*/
	}

	public static boolean isSecretAnswerValid(String hashedSecretAnswerUpper, String hashSecretAnswerLower, String answer, String gin) throws NoSuchAlgorithmException, UnsupportedEncodingException, JrafApplicativeException, InvalidParameterException {
		if(StringUtils.isBlank(gin)) {throw new InvalidParameterException("gin must not be blank");}
		if(StringUtils.isBlank(answer)) {throw new InvalidParameterException("answer must not be null");}

		String cryptedAnswerUpper = EncryptionUtils.hashPBKDF2WithHmacSHA1(gin,answer.toUpperCase());
		if(StringUtils.equals(hashedSecretAnswerUpper,cryptedAnswerUpper)) {return true;}
		cryptedAnswerUpper = EncryptionUtils.SHA1(answer.toUpperCase());
		if(StringUtils.equals(hashedSecretAnswerUpper, cryptedAnswerUpper)) {return true;}

		String cryptedAnswer = EncryptionUtils.hashPBKDF2WithHmacSHA1(gin, answer);
		if(StringUtils.equals(hashSecretAnswerLower, cryptedAnswer)) {return true;}
		cryptedAnswer = EncryptionUtils.SHA1(answer);
		if(StringUtils.equals(hashSecretAnswerLower, cryptedAnswer)) {return true;}

		return false;		
	}


	/*PROTECTED REGION ID(_O-KYwDNuEeC7FOJn0ffLCA u m) ENABLED START*/
	/**
	 * getProspectComPref
	 * @param email in String
	 * @param listComPref in CommunicationPreferencesDTO
	 * @return The getProspectComPref as <code>String</code>
	 * @throws JrafDomainException en cas d'exception
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String getProspectComPref(String email, List<CommunicationPreferencesDTO> listComPref) throws JrafDomainException {
		// Search if a prospect exists for its email addresses
		// Get ComPref and delete the prospect
		if(listComPref == null) {
			listComPref = new ArrayList<>();
		}
		String gin = "";
		// Search if a prospect exists for its email addresses
		List<IndividuDTO> listFoundProspect = null;
		listFoundProspect = individuDS.findProspectByEmail(email);

		IndividuDTO foundProspect = new IndividuDTO();
		if(listFoundProspect != null && listFoundProspect.size() > 0) {
			foundProspect = listFoundProspect.get(0);
		}

		if (foundProspect != null && foundProspect.getSgin() != null && foundProspect.getType().equals("W")) {
			// yes -> Get communication preferences of the prospect and the GIN number
			if (!foundProspect.getStatutIndividu().equalsIgnoreCase("X")) {
				gin = foundProspect.getSgin().toString();
				List<CommunicationPreferencesDTO> comPrefs = foundProspect.getCommunicationpreferencesdto();
				for (CommunicationPreferencesDTO comPref : comPrefs) {
					
					// REPIND-1783 : We report from Prospect found by EMAIL only Newsletter SALES ! 
					if (comPref != null && "S".equals(comPref.getDomain()) && "N".equals(comPref.getComGroupType())) {
						listComPref.add(comPref);
					}
				}
				// yes -> Delete Prospect
				// REPIND-555: Don't delete prospect if it's enroll
				//this.getIndividuDS().deleteAProspect(foundProspect);
				//prospectDS.deleteAProspectAndCommit(foundProspect);

				//prospectDAO.saveOrUpdate(foundProspect2);
			}
		}
		return gin;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public ReturnDetailsDTO updateCommunicationPreferences(Individu individuFromDB, List<CommunicationPreferencesDTO> commPrefsFromWS, String commPrefsUpdateMode, com.airfrance.repind.dto.individu.SignatureDTO signatureFromWS, Boolean isFlyingBlue, boolean checkGlobalOptin) throws JrafDomainException{
		ReturnDetailsDTO returnDetailsDTO = new ReturnDetailsDTO();

		// Nothing to do -> quit
		if (commPrefsFromWS==null || commPrefsFromWS.isEmpty()) {
			return returnDetailsDTO;
		}
		
		List<CommunicationPreferencesDTO> commPrefsFromDB = null;
		if (!UList.isNullOrEmpty(individuFromDB.getCommunicationpreferences()) && "U".equalsIgnoreCase(commPrefsUpdateMode)) {
			commPrefsFromDB = CommunicationPreferencesTransform.bo2Dto(individuFromDB.getCommunicationpreferences());
		}

		UpdateModeEnum updateModeCommPrefs = UpdateModeEnum.getEnum(commPrefsUpdateMode);

		Map<InformationDTO, CommunicationPreferencesDTO> response = null;

		switch (updateModeCommPrefs) {
			 // REPIND-1736: Replace mode is replaced by a Delete mode
			 case REPLACE :
				// Clear communication preferences
				/*if(individuFromDB.getCommunicationpreferences()!=null){
					individuFromDB.getCommunicationpreferences().clear();
					individuRepository.saveAndFlush(individuFromDB);
				}*/
				 break;

			case UPDATE :
				for (CommunicationPreferencesDTO communicationPreferencesDTO : commPrefsFromWS) {
					// Ultimate deletion
					if (isUltimateDeletion(communicationPreferencesDTO)) {
						communicationPreferencesDS.deleteUltimateCompref(individuFromDB.getSgin(), individuFromDB);

					} else { // Com pref update
						response = communicationPreferencesDS.createUpdateComPrefFromDGT(individuFromDB.getSgin(),
								communicationPreferencesDTO, signatureFromWS.getSite(), signatureFromWS.getSignature());
					}
				}
				break;

			case DELETE :
				communicationPreferencesDS.deleteComPrefFromDGT(individuFromDB.getSgin(), commPrefsFromWS);
		}
		String returnCode = null;
		if(response != null)
		{
			returnCode = getInformationResponse(response, commPrefsFromDB, commPrefsFromWS);

		}
		
		if (returnCode != null) {
			if (returnCode.equals("1") && isFlyingBlue) {
				returnDetailsDTO.setDetailedCode("2");
				returnDetailsDTO.setLabelCode("Existing FB Individual Found, subscription on this market/language ok.");
			}
			if (returnCode.equals("1") && !isFlyingBlue) {
				returnDetailsDTO.setDetailedCode("3");
				returnDetailsDTO.setLabelCode("Existing MyAccount Found, subscription on this market/language ok.");
			}

			if (returnCode.equals("4") && isFlyingBlue) {
				returnDetailsDTO.setDetailedCode("5");
				returnDetailsDTO.setLabelCode("Existing FB Individual Found, optin already set on this market/language.");
			}
			if (returnCode.equals("4") && !isFlyingBlue) {
				returnDetailsDTO.setDetailedCode("6");
				returnDetailsDTO.setLabelCode("Existing MyAccount Found, optin already set on this market/language.");
			}

			if (returnCode.equals("7") && isFlyingBlue) {
				returnDetailsDTO.setDetailedCode("8");
				returnDetailsDTO.setLabelCode("Existing FB Individual Found, Market/language not found or already optout.");
			}
			if (returnCode.equals("7") && !isFlyingBlue) {
				returnDetailsDTO.setDetailedCode("9");
				returnDetailsDTO.setLabelCode("Existing MyAccount Found, Market/language not found or already optout.");
			}
		}
		
		return returnDetailsDTO;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public ReturnDetailsDTO updateCommunicationPreferencesSharepoint(Individu individuFromDB, List<CommunicationPreferencesDTO> commPrefsFromWS, String commPrefsUpdateMode, com.airfrance.repind.dto.individu.SignatureDTO signatureFromWS, Boolean isFlyingBlue, boolean checkGlobalOptin) throws JrafDomainException{
		ReturnDetailsDTO returnDetailsDTO = new ReturnDetailsDTO();

		// Nothing to do -> quit
		if (commPrefsFromWS==null || commPrefsFromWS.isEmpty()) {
			return returnDetailsDTO;
		}

		List<CommunicationPreferencesDTO> commPrefsFromDB = null;
		if (!UList.isNullOrEmpty(individuFromDB.getCommunicationpreferences()) && "U".equalsIgnoreCase(commPrefsUpdateMode)) {
			commPrefsFromDB = CommunicationPreferencesTransform.bo2Dto(individuFromDB.getCommunicationpreferences());
		}

		UpdateModeEnum updateModeCommPrefs = UpdateModeEnum.getEnum(commPrefsUpdateMode);

		Map<InformationDTO, CommunicationPreferencesDTO> response = null;

		switch (updateModeCommPrefs) {
			// REPIND-1736: Replace mode is replaced by a Delete mode
			case REPLACE :
				// Clear communication preferences
				/*if(individuFromDB.getCommunicationpreferences()!=null){
					individuFromDB.getCommunicationpreferences().clear();
					individuRepository.saveAndFlush(individuFromDB);
				}*/
				break;

			case UPDATE :
				for (CommunicationPreferencesDTO communicationPreferencesDTO : commPrefsFromWS) {
					// Ultimate deletion
					if (isUltimateDeletion(communicationPreferencesDTO)) {
						communicationPreferencesDS.deleteUltimateCompref(individuFromDB.getSgin(), individuFromDB);

					} else { // Com pref update
						response = communicationPreferencesDS.createUpdateComPrefFromDGTSharepoint(individuFromDB.getSgin(),
								communicationPreferencesDTO, signatureFromWS.getSite(), signatureFromWS.getSignature());
					}
				}
				break;

			case DELETE :
				communicationPreferencesDS.deleteComPrefFromDGT(individuFromDB.getSgin(), commPrefsFromWS);
		}
		String returnCode = null;
		if(response != null)
		{
			returnCode = getInformationResponse(response, commPrefsFromDB, commPrefsFromWS);

		}

		if (returnCode != null) {
			if (returnCode.equals("1") && isFlyingBlue) {
				returnDetailsDTO.setDetailedCode("2");
				returnDetailsDTO.setLabelCode("Existing FB Individual Found, subscription on this market/language ok.");
			}
			if (returnCode.equals("1") && !isFlyingBlue) {
				returnDetailsDTO.setDetailedCode("3");
				returnDetailsDTO.setLabelCode("Existing MyAccount Found, subscription on this market/language ok.");
			}

			if (returnCode.equals("4") && isFlyingBlue) {
				returnDetailsDTO.setDetailedCode("5");
				returnDetailsDTO.setLabelCode("Existing FB Individual Found, optin already set on this market/language.");
			}
			if (returnCode.equals("4") && !isFlyingBlue) {
				returnDetailsDTO.setDetailedCode("6");
				returnDetailsDTO.setLabelCode("Existing MyAccount Found, optin already set on this market/language.");
			}

			if (returnCode.equals("7") && isFlyingBlue) {
				returnDetailsDTO.setDetailedCode("8");
				returnDetailsDTO.setLabelCode("Existing FB Individual Found, Market/language not found or already optout.");
			}
			if (returnCode.equals("7") && !isFlyingBlue) {
				returnDetailsDTO.setDetailedCode("9");
				returnDetailsDTO.setLabelCode("Existing MyAccount Found, Market/language not found or already optout.");
			}
		}

		return returnDetailsDTO;
	}

	/**
	 * 
	 * @param cpDTO
	 * @return True if
	 *         <ol>
	 *         <li>The communication pref is an ultimate</li>
	 *         <li>The optin is equal to 'X'</li>
	 *         <li>The com group type is equal to 'S'</li>
	 *         <li>The group type is equal to 'UL_PS'</li>
	 *         </ol>
	 */
	private boolean isUltimateDeletion(CommunicationPreferencesDTO cpDTO) {

		if ("U".equals(cpDTO.getDomain()) && "X".equalsIgnoreCase(cpDTO.getSubscribe())
				&& "S".equals(cpDTO.getComGroupType()) && "UL_PS".equals(cpDTO.getComType())) {
			return true;
		}

		return false;
	}

	private String getInformationResponse(Map<InformationDTO, CommunicationPreferencesDTO> response, List<CommunicationPreferencesDTO> comprefsDB, List<CommunicationPreferencesDTO> comprefsWS) {
		
		if (UList.isNullOrEmpty(response)) {
			return null;
		}
		
		InformationDTO information = (InformationDTO) response.keySet().toArray()[0];
		
		String comprefName = information.getName().replaceAll(" - ", "");
		CommunicationPreferencesDTO comprefWS = null;
		CommunicationPreferencesDTO comprefDB = null;
		
		if (UList.isNullOrEmpty(comprefsDB)) {
			if (information.getCode().equals("0")) {
				return "1";
			} else {
				return "7";
			}
		} else {
			for (CommunicationPreferencesDTO comprefWSLoop : comprefsWS) {
				if (comprefName.equalsIgnoreCase(comprefWSLoop.getDomain() + comprefWSLoop.getComGroupType() + comprefWSLoop.getComType())) {
					comprefWS = comprefWSLoop;
				}
			}
			
			for (CommunicationPreferencesDTO comprefDBLoop : comprefsDB) {
				if (comprefName.equalsIgnoreCase(comprefDBLoop.getDomain() + comprefDBLoop.getComGroupType() + comprefDBLoop.getComType())) {
					comprefDB = comprefDBLoop;
				}
			}
			
			if (comprefWS != null && comprefDB != null) {
				if (!UList.isNullOrEmpty(comprefWS.getMarketLanguageDTO()) && !UList.isNullOrEmpty(comprefDB.getMarketLanguageDTO())) {
					List<MarketLanguageDTO> mlWS = new ArrayList<MarketLanguageDTO>(comprefWS.getMarketLanguageDTO());
					List<MarketLanguageDTO> mlDB = new ArrayList<MarketLanguageDTO>(comprefDB.getMarketLanguageDTO());
					
					if (mlWS.get(0).getOptIn().equalsIgnoreCase(mlDB.get(0).getOptIn())) {
						if (mlWS.get(0).getOptIn().equalsIgnoreCase("Y")) {
							return "4";
						}
						if (mlWS.get(0).getOptIn().equalsIgnoreCase("N")) {
							return "7";
						}
					}
				}
			}
			
			return "1";
		}
	}

	// Set new compref optin date and channel with the existing compref if they are equals
	public void reuseIndividualInitialComPrefData(Set<CommunicationPreferences> commPrefsFromDB, List<CommunicationPreferencesDTO> commPrefsFromWS) {
		if (commPrefsFromDB != null && !commPrefsFromDB.isEmpty() && commPrefsFromWS != null && !commPrefsFromWS.isEmpty()) {
			for (CommunicationPreferencesDTO dtoFromWS : commPrefsFromWS) {
				for (CommunicationPreferences boFromDB : commPrefsFromDB) {
					compareAndUpdateNewComPref(boFromDB, dtoFromWS);
				}
			}
		}
	}

	private void compareAndUpdateNewComPref(CommunicationPreferences boFromDB, CommunicationPreferencesDTO dtoFromWS) {
		if (boFromDB != null && dtoFromWS != null) {
			if (sameComPref(boFromDB, dtoFromWS)) {
				// Same initial optin & request optin   ===> we keep the initial date of optin
				if ((boFromDB.getSubscribe().equalsIgnoreCase("Y") && dtoFromWS.getSubscribe().equalsIgnoreCase("Y")) || (boFromDB.getSubscribe().equalsIgnoreCase("N") && dtoFromWS.getSubscribe().equalsIgnoreCase("N"))) {
					if (boFromDB.getDateOptin() != null) {
						dtoFromWS.setDateOptin(boFromDB.getDateOptin());
					}
					if (boFromDB.getChannel() != null) {
						dtoFromWS.setChannel(boFromDB.getChannel());
					}
					if (boFromDB.getDateOptinPartners() != null) {
						dtoFromWS.setDateOptinPartners(boFromDB.getDateOptinPartners());
					}

					// Also check market/language
					if (boFromDB.getMarketLanguage() != null && dtoFromWS.getMarketLanguageDTO() != null) {
						compareAndUpdateMarketLanguage(boFromDB.getMarketLanguage(), dtoFromWS.getMarketLanguageDTO());
					}
				}
			}
		}
	}

	private void compareAndUpdateMarketLanguage(Set<MarketLanguage> mlFromDB, Set<MarketLanguageDTO> mlFromWS) {
		for (MarketLanguageDTO mlDtoFromWS: mlFromWS) {
			for (MarketLanguage mlDtoFromDB : mlFromDB) {
				if (sameMarketLanguage(mlDtoFromDB, mlDtoFromWS)) {
					// Same initial optin & request optin  ===> we keep the initial date of optin
					if ((mlDtoFromDB.getOptIn().equalsIgnoreCase("Y") && mlDtoFromWS.getOptIn().equalsIgnoreCase("Y")) || (mlDtoFromDB.getOptIn().equalsIgnoreCase("N") && mlDtoFromWS.getOptIn().equalsIgnoreCase("N"))) {
						if (mlDtoFromDB.getDateOfConsent() != null) {
							// Reuse of initial date of consent from DB
							mlDtoFromWS.setDateOfConsent(mlDtoFromDB.getDateOfConsent());
						}
					}
				}
			}
		}
	}

	private boolean sameMarketLanguage(MarketLanguage mlDtoFromDB, MarketLanguageDTO mlDtoFromWS) {
		boolean sameMarket = false;

		if (mlDtoFromDB.getMarket() != null && mlDtoFromWS.getMarket() != null) {
			sameMarket = mlDtoFromDB.getLanguage().equalsIgnoreCase(mlDtoFromWS.getLanguage());
		}

		if (sameMarket && mlDtoFromDB.getLanguage() != null && mlDtoFromWS.getLanguage() != null) {
			return mlDtoFromDB.getLanguage().equalsIgnoreCase(mlDtoFromWS.getLanguage());
		}
		else {
			return false;
		}
	}

	private boolean sameComPref(CommunicationPreferences boFromDB, CommunicationPreferencesDTO dtoFromWS) {
		boolean areEquals = false;
		if (boFromDB != null && dtoFromWS != null) {
			if (boFromDB.getComType() != null && dtoFromWS.getComType() != null) {
				areEquals = boFromDB.getComType().equalsIgnoreCase(dtoFromWS.getComType());
				if (!areEquals) {
					return areEquals;
				}
			}
			if (boFromDB.getComGroupType() != null && dtoFromWS.getComGroupType() != null) {
				areEquals = boFromDB.getComGroupType().equalsIgnoreCase(dtoFromWS.getComGroupType());
				if (!areEquals) {
					return areEquals;
				}
			}
			if (boFromDB.getDomain() != null && dtoFromWS.getDomain() != null) {
				areEquals = boFromDB.getDomain().equalsIgnoreCase(dtoFromWS.getDomain());
				if (!areEquals) {
					return areEquals;
				}
			}
		}
		return areEquals;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateCommunicationPreferencesForNewIndividual(String gin, List<CommunicationPreferencesDTO> commPrefsFromWS, String commPrefsUpdateMode, com.airfrance.repind.dto.individu.SignatureDTO signatureFromWS, Boolean isFlyingBlue) throws JrafDomainException {
		updateCommunicationPreferencesForNewIndividual(gin, commPrefsFromWS, commPrefsUpdateMode, signatureFromWS, isFlyingBlue, true);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateCommunicationPreferencesForNewIndividual(String gin, List<CommunicationPreferencesDTO> commPrefsFromWS, String commPrefsUpdateMode, com.airfrance.repind.dto.individu.SignatureDTO signatureFromWS, Boolean isFlyingBlue, boolean checkGlobalOptin) throws JrafDomainException {
		Individu individuFromDB = individuRepository.findBySgin(gin);
		updateCommunicationPreferences(individuFromDB, commPrefsFromWS, commPrefsUpdateMode, signatureFromWS, isFlyingBlue,checkGlobalOptin);
	}

	public AccountDataDS getMyAccountDataDS() {
		return myAccountDataDS;
	}

	public void setMyAccountDataDS(AccountDataDS myAccountDataDS) {
		this.myAccountDataDS = myAccountDataDS;
	}

	public RoleDS getMyRoleContratDS() {
		return roleDS;
	}

	public void setMyRoleContratDS(RoleDS myRoleContratDS) {
		this.roleDS = myRoleContratDS;
	}

	/**
	 * @return individuDS
	 */
	public IndividuDS getIndividuDS() {
		return individuDS;
	}

	/**
	 * @param individuDS individuDS Ã  dÃ©finir
	 */
	public void setIndividuDS(IndividuDS individuDS) {
		this.individuDS = individuDS;
	}

	/**
	 * Getter
	 * @return IIndividuUS
	 */
	public IndividuUS getIndividuUS() {
		return individuUS;
	}

	/**
	 * Setter
	 * @param individuUS the IIndividuUS
	 */
	public void setIndividuUS(IndividuUS individuUS) {
		this.individuUS = individuUS;
	}

	public Set<WarningDTO> checkValidAccount(String sgin) throws JrafDomainException
	{
		Set<WarningDTO> warnings = new HashSet<>();

		if(sgin != null)
		{
			if(log.isDebugEnabled()) {
				log.debug("MyAccountDS:checkValidAccount : BEGIN" );
			}

			List<Map<String,?>> accountDataPropertiesList = accountDataRepository.findSimplePropertiesByGin(sgin);
			Map<String,?> accountDataProperties = accountDataPropertiesList.isEmpty() ? null : accountDataPropertiesList.get(0);
			if (accountDataProperties != null) {

				// Check Type of Individual
				String type = (String) accountDataProperties.get("type");

				// If Individual with contract
				if ("I".equals(type)) {
					// Warning Managment
					// information sur les warnings
					// On verifie la validitÃ© de l'account
					String accountStatus = (String) accountDataProperties.get("status");

					// check sur status :
					// expirÃ©
					if (accountStatus.equals("E")) {
						WarningDTO warning = new WarningDTO();
						warning.setWarningCode("ACCOUNT WITH EXPIRED STATUS");
						warnings.add(warning);
					}

					// deletÃ©
					if (accountStatus.equals("D")) {
						WarningDTO warning = new WarningDTO();
						warning.setWarningCode(MyAccountDS.ACCOUNT_WITH_DELETED_STATUS);
						warnings.add(warning);
					}
				}

				// check sur account bloquÃ©
				Integer nbFailureAuthentification = (Integer) accountDataProperties.get("nbFailureAuthentification");
				if (nbFailureAuthentification != null && nbFailureAuthentification == 3) {
					WarningDTO warning = new WarningDTO();
					warning.setWarningCode("ACCOUNT LOCKED");
					warnings.add(warning);
				}

				// On vÃ©rifie la validitÃ© de l'individu
				String individuStatus = (String) accountDataProperties.get("individu_statutIndividu");
				if (individuStatus != null) {

					// cancelÃ©
					if (individuStatus.equals("X")) {
						WarningDTO warning = new WarningDTO();
						warning.setWarningCode("INDIVIDUAL CANCELLED");
						warnings.add(warning);
					}

					// dÃ©cÃ©dÃ©
					if (individuStatus.equals("D")) {
						WarningDTO warning = new WarningDTO();
						warning.setWarningCode("INDIVIDUAL DEAD");
						warnings.add(warning);
					}

					// mergÃ©
					String ginFusion = (String) accountDataProperties.get("individu_ginFusion");
					if (individuStatus.equals("T") && ginFusion != null && !("").equals(ginFusion)) {
						WarningDTO warning = new WarningDTO();
						warning.setWarningCode("INDIVIDUAL MERGED");
						warning.setWarningDetails(ginFusion);
						warnings.add(warning);
					}
				}

				// On verifie la validitÃ© du contrat si c'est un flying Blue
				String fbIdentifier = (String) accountDataProperties.get("fbIdentifier");
				if (fbIdentifier != null) {

					List<Map<String,?>> contracts = roleContratsRepository.findSimplePropertiesByGin(sgin);
					if (contracts.size() == 2) {

						for (Map<String,?> contract : contracts) {

							String typeContrat = (String) contract.get("typeContrat");
							String etatContrat = (String) contract.get("etat");
							Date datefin = (Date) contract.get("dateFinValidite");
							Date now = Calendar.getInstance().getTime();

							if ("FP".equals(typeContrat)) {

								if (!"C".equals(etatContrat) && !"P".equals(etatContrat)) {

									WarningDTO warning = new WarningDTO();
									warning.setWarningCode("INVALID FB CONTRACT");
									warnings.add(warning);

								} else if (datefin != null) {

									if (datefin.compareTo(now) < 0) {

										WarningDTO warning = new WarningDTO();
										warning.setWarningCode("CLOSED FB CONTRACT");
										warnings.add(warning);
									}
								}
							}
						}
					}
				}
			} else {
				/**
				 * REPIND-1487: If the Individual doesn't have an account data,
				 * we get it an check if it's merged. If it's merged, we add
				 * also the warning.
				 */
				Individu individualProperties = individuRepository.findBySgin(sgin);
				if ("T".equals(individualProperties.getStatutIndividu()) && individualProperties.getGinFusion() != null
						&& !("").equals(individualProperties.getGinFusion())) {
					WarningDTO warning = new WarningDTO();
					warning.setWarningCode("INDIVIDUAL MERGED");
					warning.setWarningDetails(individualProperties.getGinFusion());
					warnings.add(warning);
				}
			}
		}

		if(log.isDebugEnabled()) {
			log.debug("MyAccountDS:checkValidAccount : END" );
		}
		return warnings;
	}

	/**
	 * Renvoi si le sgin designant un Individu est un account Pur (non Flying blue).
	 * @param sgin : identifiant de l'individu.
	 * @return vrai si account Pur.
	 * @throws JrafDomainException
	 */
	@Transactional
	public boolean isAccountPur(String sgin) throws JrafDomainException {
		boolean isAccountPur = true;

		AccountDataDTO oldAccountDATAdto = this.provideMyAccountDataByGin(sgin);
		if(oldAccountDATAdto != null && oldAccountDATAdto.getFbIdentifier() != null)
		{
			isAccountPur = false;
		}
		return isAccountPur;
	}
	
	public EmailDS getEmailDS() {
		return emailDS;
	}

	public void setEmailDS(EmailDS emailDS) {
		this.emailDS = emailDS;
	}
	
	public AccountDataRepository getAccountDataRepository() {
		return accountDataRepository;
	}

	public void setAccountDataRepository(AccountDataRepository accountDataRepository) {
		this.accountDataRepository = accountDataRepository;
	}
	
	public IndividuRepository getIndividuRepository() {
		return individuRepository;
	}

	public void setIndividuRepository(IndividuRepository individuRepository) {
		this.individuRepository = individuRepository;
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public ReturnDetailsDTO updateCommunicationPreferences(
			List<EmailDTO> emailListFromWS, List<CommunicationPreferencesDTO> commPrefsListFromWS, 
			com.airfrance.repind.dto.individu.SignatureDTO signatureFromWS, String commPrefsUpdateMode,
			ReturnDetailsDTO responseForWS, IndividuDTO individuFromDB, boolean isFlyingBlue, boolean checkGlobalOptin) throws JrafDomainException {

		if(emailListFromWS!=null && !emailListFromWS.isEmpty() && emailListFromWS.get(0)!=null && emailListFromWS.get(0).getEmail()!=null){

			if(commPrefsListFromWS == null) {
				commPrefsListFromWS = new ArrayList<>();
			}

			// REPIND-1783 : Get prospect Com Pref if there is no ComPref SALES on Database for the Updated Individual !   
			if (individuFromDB != null && individuFromDB.getCommunicationpreferencesdto() != null && individuFromDB.getCommunicationpreferencesdto().size() > 0) {
			
				boolean isSalesComPrefFound = false;

				for (CommunicationPreferencesDTO  cpd : individuFromDB.getCommunicationpreferencesdto()) {
					// We search a SALES for this Individual
					if (cpd != null && "S".equals(cpd.getDomain()) && "N".equals(cpd.getComGroupType())) {
						isSalesComPrefFound = true;
					}
				}
				
				// If we do not find any Sales Com Pref, we will try to catch some on Prospect scope for this EMAIL
				if (!isSalesComPrefFound) {
					getProspectComPref(emailListFromWS.get(0).getEmail(), commPrefsListFromWS);
				}
				
			}
			
		}
		
		commPrefsListFromWS = CommunicationPreferencesTransformer.individu2prospectList(commPrefsListFromWS);
		Individu individuBO = new Individu();
		if(individuFromDB != null){
			individuBO = individuRepository.findBySgin(individuFromDB.getSgin());

		}
		ReturnDetailsDTO newResponseForWS = updateCommunicationPreferences(individuBO, commPrefsListFromWS, commPrefsUpdateMode, signatureFromWS, isFlyingBlue, checkGlobalOptin);

		//REPIND-1573 - We return previous information code generated during updating all FB Compref in one request (code 10 or 11)
		if (!StringUtils.isEmpty(responseForWS.getDetailedCode()) && !StringUtils.isEmpty(responseForWS.getLabelCode())) {
			return responseForWS;
		}
		
		return newResponseForWS;
	}

	private boolean isFlyingBlue(IndividuDTO individuFromDB) {

		boolean isFlyingBlue = false;

		for (RoleContratsDTO roleContrat : individuFromDB.getRolecontratsdto()) {
			if (roleContrat.getBusinessroledto().getType().equalsIgnoreCase("C") && roleContrat.getTypeContrat().equalsIgnoreCase("FP")) {
				isFlyingBlue = true;
			}
		}

		return isFlyingBlue;
	}

public List<ProspectIndividuDTO> findAnIndividualByEmail(String email) throws JrafDomainException {
		
		List<ProspectIndividuDTO> listProspect = new ArrayList<ProspectIndividuDTO>();
		
		List<Object[]> results = accountDataRepository.getAProspectIndividuIdentification(email);
		for (Object[] object : results) {
			ProspectIndividuDTO prospectIndividuDTO = new ProspectIndividuDTO();
			prospectIndividuDTO.setEmail(object[0].toString());
			prospectIndividuDTO.setGin(object[1].toString());
			if(object[2] != null) {
				prospectIndividuDTO.setCin(object[2].toString());
			}
			listProspect.add(prospectIndividuDTO);
		}
		
		return listProspect;
	}

	public boolean isValidAccountType(String identifier) {
		return accountDataRepository.getAllAccountType().contains(identifier);
	}



	public boolean isValidContextIdentifier(String context) {
		return accountDataRepository.getAllContextInternalIdentifier().contains(context);
	}

	public ProvideGinForUserIdResponseDTO provideGinByUserIdNoCheckSharedEmail(ProvideGinForUserIdRequestDTO request) throws JrafDomainException {
		// TODO Auto-generated method stub
		return myAccountUS.provideGinByUserIdNoCheckSharedEmail(request);
	}
	
	//REPIND-1573 - Update all Compref and Market Language for FlyingBlue domain in one request
	//Feature available only for consumer (CAPI, CBS) and for CreateOrUpdate V8
	//V6 and V7 are not providing the consumerId, so the code will be not triggered
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public ReturnDetailsDTO updateAllMarketLanguageOnFBdomain(List<CommunicationPreferencesDTO> commPrefsListFromWS, com.airfrance.repind.dto.individu.SignatureDTO signatureFromWS, String commPrefsUpdateMode, ReturnDetailsDTO responseForWS, IndividuDTO individuFromDB, String wantedUpdatedLanguage, String consumerId) throws JrafDomainException {
		if (!UList.isNullOrEmpty(commPrefsListFromWS) && commPrefsListFromWS.size() == 1 && CommunicationPreferencesConstantValues.FB_ESS.equalsIgnoreCase(commPrefsListFromWS.get(0).getComType()) && "U".equalsIgnoreCase(commPrefsUpdateMode)) {
			
			//CreateOrUpdate V8
			if (!StringUtils.isEmpty(consumerId) && getConsumersForFBPropagation().contains(consumerId.toUpperCase())) {
				CommunicationPreferencesDTO cpFromWS = commPrefsListFromWS.get(0);
				
				if (cpFromWS.getMarketLanguageDTO().size() > 1) {
					responseForWS.setDetailedCode("10");
					responseForWS.setLabelCode("When updating all Market Language for FlyingBlue Domain, only one Market Language combinaison is allowed");
					//We remove this compref from input request in order to not be processed by the next steps of process
					commPrefsListFromWS.clear();
					return responseForWS;
				}
				
				MarketLanguageDTO mlFromWS = null;
				for (MarketLanguageDTO ml : cpFromWS.getMarketLanguageDTO()) {
					mlFromWS = ml;
				}
				
				if(mlFromWS != null) {
					
					if (!UList.isNullOrEmpty(individuFromDB.getCommunicationpreferencesdto())) {
						for (CommunicationPreferencesDTO cp : individuFromDB.getCommunicationpreferencesdto()) {
							if (CommunicationPreferencesConstantValues.DOMAIN_F.equalsIgnoreCase(cp.getDomain())) {
								for (MarketLanguageDTO ml : cp.getMarketLanguageDTO()) {
									ml.setMarket(mlFromWS.getMarket());
									ml.setLanguage(mlFromWS.getLanguage());
									ml.setModificationDate(new Date());
									ml.setModificationSignature(signatureFromWS.getSignature());
									ml.setModificationSite(signatureFromWS.getSite());
								}
							}
						}
					}
					
					if (!wantedUpdatedLanguage.equalsIgnoreCase(mlFromWS.getLanguage())) {
						responseForWS.setDetailedCode("11");
						responseForWS.setLabelCode("Language " + wantedUpdatedLanguage + " not available for Market " + mlFromWS.getMarket() + ". Default language " + mlFromWS.getLanguage() + " was set");
					}
					
				}
				
				individuDS.updateComPrefOfIndividual(individuFromDB);
			} else {
				if (!StringUtils.isEmpty(consumerId)) {
					responseForWS.setDetailedCode("12");
					responseForWS.setLabelCode("Your application is not authorized to perform this update on Flying Blue Communication Preferences");
				}
				//We remove this compref from input request in order to not be processed by the next steps of process. CreateOrUpdate V6 and V7 here
				commPrefsListFromWS.clear();
				return responseForWS;
			}
		
		}
		
		return responseForWS;
	}
	
	/**
	 * Updates the last connection date of an account for the specified gin
	 * 
	 * @param gin                   the identifier of the account to be updated
	 * @param lastConnection        last connection date to be updated
	 * @param siteModification      site which initiated the modification
	 * @param signatureModification process which initiated the modification
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateLastConnectionDate(@NotNull String gin, @NotNull Date lastConnection, String siteModification,
			String signatureModification) {
		List<AccountData> accountDataList = accountDataRepository.findAllByGin(gin);
		if (!CollectionUtils.isEmpty(accountDataList)) {
			Date today = new Date();
			for (AccountData account : accountDataList) {
				account.setLastConnexionDate(lastConnection);
				account.setSiteModification(siteModification);
				account.setSignatureModification(signatureModification);
				account.setDateModification(today);
				accountDataRepository.saveAndFlush(account);
			}
		} else {
			log.warn("GIN not found : " + gin);
		}
	}
	
	private List<String> getConsumersForFBPropagation() throws JrafDomainException {
		List<String> listConsumers = new ArrayList<String>();
		
		VariablesDTO var = variablesDS.getByEnvKey("CONSUMER_COMPREF_FB_PROPAGATION");
		
		if (var != null && !StringUtils.isEmpty(var.getEnvValue())) {
			listConsumers = Arrays.asList(var.getEnvValue().split(";"));
		}
		
		return listConsumers;
	}
	
	@XmlType(name = "MyActIdentifierTypeEnum", namespace = "http://www.af-klm.com/services/passenger/SicDataType-v1/xsd")
	@XmlEnum
	public enum MyActIdentifierTypeEnum {

	    FP,
	    RP,
	    AX,
	    S,
	    MA,
	    EM,
	    AC,
	    // REPIND-1590 : Add SN for SocialNetwork and UC for UCCR
	    SN,
	    UC;

	    public String value() {
	        return name();
	    }

	    public static MyActIdentifierTypeEnum fromValue(String v) {
	        return valueOf(v);
	    }

	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void checkAndCompleteAccountDataIdentifier(@NotNull AccountDataDTO accountDataDTO) throws JrafDomainException {
		String currentAccountID = accountDataDTO.getAccountIdentifier();
		String emailID = accountDataDTO.getEmailIdentifier();
		String gin = accountDataDTO.getSgin();

		// on ajoute AccountId s'il est absent
		if(currentAccountID == null) {
			// Check if MA contract exists
			List<RoleContratsDTO> myaContractList = roleDS.findValidRoleContractMya(gin);
			if (CollectionUtils.isEmpty(myaContractList)) {
				accountDataDTO.setAccountIdentifier(myAccountDataDS.getMyAccountIdentifier());
				accountDataDTO.setSignatureModification(SIGN_WS);
				accountDataDTO.setSiteModification(SITE_WS);
				accountDataDTO.setDateModification(Calendar.getInstance().getTime());

				// Create signature
				SignatureDTO signature = new com.airfrance.repind.dto.individu.SignatureDTO();
				signature.setSignature(SIGN_WS);
				signature.setSite(SITE_WS);
				signature.setDate(Calendar.getInstance().getTime());
				signature.setTypeSignature(MODIF_SIGNATURE_CODE);

				/** REPIND-1566 **/
				String codeCompany = roleDS.getCompanyCodeFromFBContract(gin);

				// Create MyAccount Contract
				roleDS.createMyAccountContract(accountDataDTO.getSgin(), accountDataDTO.getAccountIdentifier(), codeCompany, signature);
			}
			else {
				String myaContractNumber = myaContractList.get(0).getNumeroContrat();
				accountDataDTO.setAccountIdentifier(myaContractNumber);
				accountDataDTO.setSignatureModification(SIGN_WS);
				accountDataDTO.setSiteModification(SITE_WS);
				accountDataDTO.setDateModification(Calendar.getInstance().getTime());
			}
		}

		// on ajoute un email ID s'il est absent et qu'il n'est pas deja utilisé
		if(emailID == null)	{
			EmailDTO currentEmail = new EmailDTO();
			List<EmailDTO> dtoEmailFounds = emailDS.findValidEmail(gin);

			// s'il y a qu'un seul email dans la table email
			if(dtoEmailFounds.size() == 1 )	{
				currentEmail = dtoEmailFounds.get(0);
				if(currentEmail != null && currentEmail.getEmail() != null)	{
					AccountDataDTO currentAccount = new AccountDataDTO();
					currentAccount.setEmailIdentifier(currentEmail.getEmail());
					List<AccountDataDTO> dtoFounds = null;
					dtoFounds = myAccountDataDS.findByExample(currentAccount);
					if(dtoFounds.isEmpty())	{
						accountDataDTO.setEmailIdentifier(currentEmail.getEmail());
					}
				}
			}
		}

		// Dans le cas ou l'account etait en statut expiré (E)
		// on refait passer l'account en valide
		if(AccountDataStatusEnum.ACCOUNT_EXPIRED.code().equals(accountDataDTO.getStatus()))	{
			accountDataDTO.setStatus(AccountDataStatusEnum.ACCOUNT_VALID.code());
		}

		// mis a jour de l'account
		accountDataDTO.setLastConnexionDate(Calendar.getInstance().getTime());
		myAccountDataDS.update(accountDataDTO);
	}
}
