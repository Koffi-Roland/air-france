package com.airfrance.repind.util;

import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.RequestAlreadyDoneException;
import com.airfrance.ref.exception.ValidContractFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.ContextEnum;
import com.airfrance.ref.type.ForgetEnum;
import com.airfrance.repind.dao.individu.MarketLanguageRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.PostalAddressDTO;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.delegation.DelegationDataDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.individu.*;
import com.airfrance.repind.dto.preference.PreferenceDTO;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.dto.ws.createupdateindividual.CreateUpdateIndividualRequestDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.adresse.internal.PostalAddressDS;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.delegation.internal.DelegationDataDS;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.*;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.service.profil.internal.ProfilMereDS;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.service.role.internal.RoleGPDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ForgetMeUtils {
	/** logger */
    private static final Log log = LogFactory.getLog(ForgetMeUtils.class);
	
	private static final String FORGET_STATUS = ForgetEnum.STATUS.getCode();
	private static final String CTX_FORGET_ASKED = ForgetEnum.ASKED.getCode();
	private static final String CTX_FORGET_CONFIRM = ForgetEnum.CONFIRMED.getCode();
	private static final String CTX_FORGET_FORCED = ForgetEnum.FORCED.getCode();

	@Autowired
	protected AccountDataDS accountDatatDS;
	
	@Autowired
	protected PostalAddressDS addressDS;

	@Autowired
	protected ProfilMereDS profilMereDS;
	
	@Autowired 
	protected AlertDS alertDS;

	@Autowired
	protected BusinessRoleDS businessRoleDS;
	
	@Autowired
	protected CommunicationPreferencesDS communicationPreferenceDS;
	
	@Autowired
	protected DelegationDataDS delegationDS;
	
	@Autowired
	protected EmailDS emailDS;
	
	@Autowired
	protected ExternalIdentifierDS externalIdentifierDS;
	
	@Autowired
	protected ForgottenIndividualDS forgottenIndividualDS;
	
	@Autowired
	protected IndividuDS individuDS;
	
	@Autowired
	protected MarketLanguageRepository marketLanguageRepository;
	
	@Autowired
	protected PreferenceDS preferenceDS;
	
	@Autowired
	protected PrefilledNumbersDS prefilledDS;
	
	@Autowired
	protected RoleDS roleContractDS;
	
	@Autowired
	protected TelecomDS telecomDS;

	@Autowired
	protected RoleGPDS roleGPDS;
	
	public boolean isRequestForDeletion(String context) {
		List<String> gdprContextList = new ArrayList<>();
		gdprContextList.add(ContextEnum.FA.getLibelle());
		gdprContextList.add(ContextEnum.FC.getLibelle());
		gdprContextList.add(ContextEnum.FF.getLibelle());
		
		return gdprContextList.contains(context);
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public boolean processRequestToBeForgotten(CreateUpdateIndividualRequestDTO requestDTO) throws JrafDomainException {
		if (requestDTO != null && requestDTO.getRequestorDTO() != null
				&& requestDTO.getRequestorDTO().getContext() != null) {
			String context = requestDTO.getRequestorDTO().getContext();
		
			if(checkField(requestDTO)) {
				ContextEnum ctx = ContextEnum.getEnumFromLibelle(context);
				String gin = getGin(requestDTO);
				checkExistGin(gin);

				switch (ctx) {
			
				case FA:
					checkRequestAlreadyDone(gin, ctx);
					processForgetAsked(requestDTO);
					return true;
			
				case FC:
					checkRequestAlreadyDone(gin, ctx);
					processForgetConfirmed(requestDTO, ctx);
					return true;
					
				case FF:
					checkRequestAlreadyDone(gin, ctx);
					processForgetForced(requestDTO, ctx);
					return true;
					
				default:
					return false;
				}
			} else {
				throw new MissingParameterException("Missing GIN (null or empty)");
			}
		} else {
			return false;
		}
	}

	/**
	 * Check if individual existing in our database.
	 * Search by gin
	 *
	 * @param gin
	 *            individual's gin
	 * @throws JrafDomainException
	 *             - NotFoundException
	 */
	protected void checkExistGin(String gin) throws JrafDomainException {
		if(!individuDS.isExisting(gin)){
			throw new NotFoundException("Gin not found");
		}
	}

	/**
	 * Check if individual existing in our table FORGOTTEN_INDIVIDUAL.
	 * Search by gin
	 *
	 * @param gin
	 *            individual's gin
	 * @throws JrafDomainException
	 *             - NotFoundException
	 */
	protected void checkRequestAlreadyDone(String gin, ContextEnum context) throws JrafDomainException {
		if(profilMereDS.isAfStaff(gin)) {
			throw new ValidContractFoundException("Staff member cannot be deleted");
		}
		
		if(roleGPDS.findByGIN(gin) != null 
				&& roleGPDS.findByGIN(gin).size() > 0 ){
			throw new ValidContractFoundException("Partner of an staff member cannot be deleted");
		}
		
		if (ContextEnum.FA.equals(context)) {
			if(forgottenIndividualDS.isExisting(gin)){
				throw new RequestAlreadyDoneException("Request to be forgotten already done");
			}
		}
		
		if (ContextEnum.FC.equals(context)) {
			ForgottenIndividualDTO indToFind = new ForgottenIndividualDTO();
			List<ForgottenIndividualDTO> foundIndividual = null;
			
			indToFind.setIdentifier(gin);
			indToFind.setIdentifierType("GIN");
			indToFind.setContext(ForgetMeUtils.CTX_FORGET_CONFIRM);
			foundIndividual = forgottenIndividualDS.findByExample(indToFind);
			
			if (foundIndividual != null && !foundIndividual.isEmpty()) {
				throw new RequestAlreadyDoneException("Request to be forgotten already done");
			}
			
			
		}
		
		if(ContextEnum.FF.equals(context)){
			ForgottenIndividualDTO indToFind = new ForgottenIndividualDTO();
			List<ForgottenIndividualDTO> foundIndividual = null;
			
			indToFind.setIdentifier(gin);
			indToFind.setIdentifierType("GIN");
			indToFind.setContext(ForgetMeUtils.CTX_FORGET_FORCED);
			foundIndividual = forgottenIndividualDS.findByExample(indToFind);
			
			if (foundIndividual != null && !foundIndividual.isEmpty()) {
				throw new RequestAlreadyDoneException("Request to be forgotten already done");
			}
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void processForgetForced(CreateUpdateIndividualRequestDTO requestDTO, ContextEnum ctx) throws JrafDomainException {
		// same business rules that ForgetConfirmed
		processForgetConfirmed(requestDTO, ctx);
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void processForgetConfirmed(CreateUpdateIndividualRequestDTO requestDTO, ContextEnum ctx) throws JrafDomainException {
		ForgetMeUtils.log.info("START processForgetConfirmed(..");
		
		String gin;
		
		if (requestDTO.getIndividualRequestDTO() != null
				&& requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO() != null) {
			gin = requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier();
		} else {
			throw new MissingParameterException("Individual identifier is null or empty");
		}

		IndividuDTO foundIndividu = individuDS.getAnyIndividualByGin(gin);
		if (foundIndividu != null) {
			SignatureDTO signature = prepareSignatureForUpdate(requestDTO);
			
			// Preliminary check: no active contract
			// REPIND-1642 : disable this control
			//checkForgetConfirmEligibility(gin);
			
			// Step 1: update status to 'F'
			updateIndividualStatusToForget(foundIndividu, signature);
			
			// Step 2: delete external identifiers
			deleteExternalIdentifierToForget(foundIndividu);

			// Save action on individual status and external id
			individuDS.updateIndividualData(foundIndividu);
			
			individuDS.refresh(foundIndividu);
			
			// Step 3: delete account data
			updateAccountDataToForget(foundIndividu.getAccountdatadto(), signature);
			
			// Step 4: delete RoleContract MyAccount
			updateMyAccountToForget(foundIndividu.getRolecontratsdto(), signature);
			
			// Step 5: optout compref
			optoutCommunicationPreferencesDTO(foundIndividu.getCommunicationpreferencesdto(), signature);
			
			// Step 6: invalidate contact (tel + adr + email)
			updateIndividualContactStatusToForget(foundIndividu, signature);
			
			// Step 7: delete preferences
			deletePreferenceToForget(foundIndividu.getPreferenceDTO());
			
			// Step 8: delete prefilled numbers
			deletePrefilledNumberToForget(gin);
			
			// Step 9: delete delegation data
			updateDelegationDataToForget(foundIndividu, signature);
			
			// Step 10: delete alert
			updateIndividualAlertToForget(foundIndividu.getAlertDTO(), signature);

			
			if("FF".equals(ctx.getCode())){
				saveOrUpdateDemand(gin, ForgetMeUtils.CTX_FORGET_FORCED, signature);
			}else{
				saveOrUpdateDemand(gin, ForgetMeUtils.CTX_FORGET_CONFIRM, signature);
			}
		}
		
		ForgetMeUtils.log.info("END processForgetConfirmed(..");
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void deleteExternalIdentifierToForget(IndividuDTO foundIndividu) throws JrafDomainException {
		if (foundIndividu.getExternalIdentifierList() != null) {
			List<ExternalIdentifierDTO> extIdList = foundIndividu.getExternalIdentifierList();
			extIdList.clear();
			foundIndividu.setExternalIdentifierList(extIdList);
		}
	}
			
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void deletePreferenceToForget(List<PreferenceDTO> preferenceList) throws JrafDomainException {
		if (preferenceList != null) {
			for (PreferenceDTO prefDTO : preferenceList) {
				preferenceDS.remove(prefDTO);
			}
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateMyAccountToForget(Set<RoleContratsDTO> roleContratsDTOList, SignatureDTO signature) throws JrafDomainException {
			
		if (roleContratsDTOList != null) {
			ForgetMeUtils.log.debug("START delete My Account into updateMyAccountToForget(...");
			for (RoleContratsDTO rc : roleContratsDTOList) {
				if ("MA".equalsIgnoreCase(rc.getTypeContrat())
						&& (!"A".equalsIgnoreCase(rc.getEtat()) && !"X".equalsIgnoreCase(rc.getEtat()))) {
					if (rc.getDateFinValidite() == null || rc.getDateFinValidite().after(new Date())) {
						rc.setEtat("X");
						rc.setDateModification(signature.getDate());
						rc.setSiteModification(signature.getSite());
						rc.setSignatureModification(signature.getSignature());
						
						roleContractDS.update(rc);
					}
				}
			}
			ForgetMeUtils.log.debug("END delete My Account into updateMyAccountToForget(...");
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void deletePrefilledNumberToForget(String gin) throws JrafDomainException {
		prefilledDS.clearPrefilledNumbers(gin);
	}
			
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateDelegationDataToForget(IndividuDTO foundIndividu, SignatureDTO signature) throws JrafDomainException {
		
		// Close delegate link
		if (foundIndividu.getDelegateListDTO() != null && !foundIndividu.getDelegateListDTO().isEmpty()) {
			ForgetMeUtils.log.debug("START delete Delegate into updateDelegationDataToForget(...");
			for (DelegationDataDTO delegateDTO : foundIndividu.getDelegateListDTO()) {
				if (!"D".equalsIgnoreCase(delegateDTO.getStatus())) {
					delegateDTO.setStatus("D");
					delegateDTO.setModificationDate(signature.getDate());
					delegateDTO.setModificationSite(signature.getSite());
					delegateDTO.setModificationSignature(signature.getSignature());
				
					delegationDS.updateDelegationStatus(delegateDTO);
				}
			}
			ForgetMeUtils.log.debug("END delete Delegate into updateDelegationDataToForget(...");
		}
		
		// Close delegator link
		if (foundIndividu.getDelegatorListDTO() != null && !foundIndividu.getDelegatorListDTO().isEmpty()) {
			ForgetMeUtils.log.debug("START delete Delegator into updateDelegationDataToForget(...");
			for (DelegationDataDTO delegatorDTO : foundIndividu.getDelegatorListDTO()) {
				if (!"D".equalsIgnoreCase(delegatorDTO.getStatus())) {
					delegatorDTO.setStatus("D");
					delegatorDTO.setModificationDate(signature.getDate());
					delegatorDTO.setModificationSite(signature.getSite());
					delegatorDTO.setModificationSignature(signature.getSignature());
					
					delegationDS.updateDelegationStatus(delegatorDTO);
				}
			}
			ForgetMeUtils.log.debug("END delete Delegator into updateDelegationDataToForget(...");
		}
	}



	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateAccountDataToForget(AccountDataDTO accountdatadto, SignatureDTO signature) throws JrafDomainException {
		if (accountdatadto != null && !"D".equalsIgnoreCase(accountdatadto.getStatus())) {
			accountdatadto.setStatus("D");
			accountdatadto.setDateModification(signature.getDate());
			accountdatadto.setSiteModification(signature.getSite());
			accountdatadto.setSignatureModification(signature.getSignature());
			
			accountDatatDS.update(accountdatadto);
		}
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateIndividualAlertToForget(Set<AlertDTO> alertDTOList, SignatureDTO signature) throws JrafDomainException {
		if (alertDTOList != null && !alertDTOList.isEmpty()) {
			for (AlertDTO alertDto : alertDTOList) {
				if (!"N".equalsIgnoreCase(alertDto.getOptIn())) {
					alertDto.setOptIn("N");
					alertDto.setModificationDate(signature.getDate());
					alertDto.setModificationSite(signature.getSite());
					alertDto.setModificationSignature(signature.getSignature());
					
					alertDS.update(alertDto);
				}
			}
		}
	}

	private SignatureDTO prepareSignatureForUpdate(CreateUpdateIndividualRequestDTO requestDTO) {
		SignatureDTO signature = new SignatureDTO();
		signature.setDate(new Date());

		if (requestDTO.getRequestorDTO() != null) {
			signature.setSignature(requestDTO.getRequestorDTO().getSignature());
			signature.setApplicationCode(requestDTO.getRequestorDTO().getApplicationCode());
			signature.setSite(requestDTO.getRequestorDTO().getSite());
		}
		return signature;
	}

	/**
	 * Delete all contact (adress, email and telecom) and optout email
	 * 
	 * @param foundIndividu
	 * @param signature
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateIndividualContactStatusToForget(IndividuDTO foundIndividu, SignatureDTO signature) throws JrafDomainException {
		if (foundIndividu != null) {
			// Telecom
			if (foundIndividu.getTelecoms() != null && !foundIndividu.getTelecoms().isEmpty()) {
				for (TelecomsDTO telDto : foundIndividu.getTelecoms()) {
					if (!"X".equalsIgnoreCase(telDto.getSstatut_medium())) {
						telDto.setSstatut_medium("X");
						telDto.setDdate_modification(signature.getDate());
						telDto.setSsite_modification(signature.getSite());
						telDto.setSsignature_modification(signature.getSignature());

						telecomDS.update(telDto);
					}
				}
			}

			// Emails
			if (foundIndividu.getEmaildto() != null && !foundIndividu.getEmaildto().isEmpty()) {
				for (EmailDTO emailDto : foundIndividu.getEmaildto()) {
					if (!"X".equalsIgnoreCase(emailDto.getStatutMedium())
							|| !"N".equalsIgnoreCase(emailDto.getAutorisationMailing())) {
						emailDto.setAutorisationMailing("N");
						emailDto.setStatutMedium("X");
						emailDto.setDateModification(signature.getDate());
						emailDto.setSiteModification(signature.getSite());
						emailDto.setSignatureModification(signature.getSignature());
						
						emailDS.update(emailDto);
					}
				}
			}
			
			// Postal address
			if (foundIndividu.getPostaladdressdto() != null && !foundIndividu.getPostaladdressdto().isEmpty()) {
				for (PostalAddressDTO adrDto: foundIndividu.getPostaladdressdto()) {
					if (!"X".equalsIgnoreCase(adrDto.getSstatut_medium())) {
						adrDto.setSstatut_medium("X");
						adrDto.setDdate_modification(signature.getDate());
						adrDto.setSsite_modification(signature.getSite());
						adrDto.setSsignature_modification(signature.getSignature());
						
						addressDS.update(adrDto);
					}
				}
			}
		}
	}

	/**
	 * Change the individual status and individual type to 'F'
	 * 
	 * @param foundIndividu
	 * @param signature
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateIndividualStatusToForget(IndividuDTO foundIndividu, SignatureDTO signature) throws JrafDomainException {
		log.debug("START update individual status into updateIndividualStatusToForget(...");
		
			foundIndividu.setStatutIndividu(FORGET_STATUS);
			foundIndividu.setType(FORGET_STATUS);
			foundIndividu.setDateModification(signature.getDate());
			foundIndividu.setSiteModification(signature.getSite());
			foundIndividu.setSignatureModification(signature.getSignature());
		
		log.debug("END update individual status into updateIndividualStatusToForget(...");
	}

	/**
	 * Check in database if no open contract and role associated to the GIN
	 * 
	 * @param gin
	 * @throws JrafDomainException
	 */
	public void checkForgetConfirmEligibility(String gin) throws JrafDomainException {
		List<BusinessRoleDTO> bRoleListDTO = businessRoleDS.findValidCorpBusinessRoleByIndividualGIN(gin);
		List<RoleContratsDTO> rContratsListDTO = roleContractDS.findValidRoleContractNotMyA(gin);
		
		if (rContratsListDTO != null && ! rContratsListDTO.isEmpty()) {
			throw new ValidContractFoundException("Valid or open contract found");
		}
		
		if (bRoleListDTO != null && !bRoleListDTO.isEmpty()) {
			throw new ValidContractFoundException("Valid or open contract found");
		}
	}

	protected boolean checkField(CreateUpdateIndividualRequestDTO request){
		if(request.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier() != null 
				&& !request.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier().equals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Main method for the process : FORGET_ASKED
	 *
	 * @param requestDTO
	 *            the webservice's request
	 * @throws JrafDomainException
	 */
	private void processForgetAsked(CreateUpdateIndividualRequestDTO requestDTO) throws JrafDomainException{
		SignatureDTO signature = prepareSignatureForUpdate(requestDTO);
		String gin = getGin(requestDTO);
		List<CommunicationPreferencesDTO> communicationPreferencesDTO = communicationPreferenceDS
				.findCommunicationPreferences(gin);

		optoutCommunicationPreferencesDTO(communicationPreferencesDTO, signature);
		optoutEmails(gin, signature);
		
		IndividuDTO foundIndividu = individuDS.getAnyIndividualByGin(gin);
		if (foundIndividu != null) {
			updateIndividualAlertToForget(foundIndividu.getAlertDTO(), signature);
		}
		
		saveOrUpdateDemand(gin, ForgetMeUtils.CTX_FORGET_ASKED, signature);
	}

	/**
	 * Method to log into Database the FORGET STATUS
	 * 
	 * @param sign
	 * @param gin
	 *            the webservice's request
	 * @throws JrafDomainException
	 */
	@Transactional
	public void saveOrUpdateDemand(String gin, String contextCode, SignatureDTO sign) throws JrafDomainException{
		ForgottenIndividualDTO forgottenIndividualDTO = new ForgottenIndividualDTO();
		
		forgottenIndividualDTO.setIdentifierType("GIN");
		forgottenIndividualDTO.setIdentifier(gin);

		List<ForgottenIndividualDTO> individualFromDBList = forgottenIndividualDS.findByIdentifier(gin, "GIN");
		ForgottenIndividualDTO individualToSave = null;
		
		if (individualFromDBList != null && !individualFromDBList.isEmpty()) {
			if (individualFromDBList.size() > 1) {
				throw new JrafDomainException("Multiple individual found with the same identifier and type ");
			}
			
			individualToSave = individualFromDBList.get(0);
			individualToSave.setContext(contextCode);
			individualToSave.setModificationDate(new Date());
			individualToSave.setApplicationCode(sign.getApplicationCode());
			individualToSave.setSignature(sign.getSignature());
			individualToSave.setSite(sign.getSite());
			
			forgottenIndividualDS.update(individualToSave);		
			
		} else {
			individualToSave = new ForgottenIndividualDTO();
			individualToSave.setIdentifierType("GIN");
			individualToSave.setIdentifier(gin);
			individualToSave.setContext(contextCode);
			individualToSave.setModificationDate(new Date());
			individualToSave.setApplicationCode(sign.getApplicationCode());
			individualToSave.setSignature(sign.getSignature());
			individualToSave.setSite(sign.getSite());
			
			forgottenIndividualDS.create(individualToSave);
		}
	}
	
	/**
	 * This method is aimed to optout all emails associed to individual   
	 * 
	 * @param gin
	 *            individual's gin number
	 * @param sign
	 *            signatureDTO useful for update
	 * @throws JrafDomainException
	 */
	protected void optoutEmails(String gin, SignatureDTO sign) throws JrafDomainException{
		List<EmailDTO> emailsDTO = emailDS.findEmail(gin);
		for(EmailDTO email : emailsDTO){
			email.setAutorisationMailing("N");
			email.setDateModification(new Date());
			email.setSiteModification(sign.getSite());
			email.setSignatureModification(sign.getSignature());
			
			emailDS.update(email);
		}
	}
	
	/**
	 * This method is aimed to optout all marketLanguage associed to communication
	 * preference
	 * 
	 * @param marketLanguageDTO
	 * @param sign
	 *            signatureDTO useful for update
	 * @throws JrafDomainException
	 */
	protected void optoutMarketLanguage(MarketLanguageDTO marketLanguageDTO, SignatureDTO sign)
			throws JrafDomainException {
		if(!marketLanguageDTO.getOptIn().equals("N")){
			marketLanguageDTO.setOptIn("N");
			marketLanguageDTO.setModificationDate(new Date());
			marketLanguageDTO.setModificationSignature(sign.getSignature());
			marketLanguageDTO.setModificationSite(sign.getSite());

			marketLanguageRepository.updateMarketLanguage(MarketLanguageTransform.dto2Bo(marketLanguageDTO));
		}
	}
	
	/**
	 * This method is aimed to optout all communicationPreference associed to
	 * individual
	 * 
	 * @param communicationPreferencesDTO
	 *            communicationPreference associed to indivual
	 * @param sign
	 *            signatureDTO useful for update
	 * @throws JrafDomainException
	 */
	@Transactional
	public void optoutCommunicationPreferencesDTO(List<CommunicationPreferencesDTO> communicationPreferencesDTO,
			SignatureDTO sign) throws JrafDomainException {
		if (communicationPreferencesDTO != null && !communicationPreferencesDTO.isEmpty()) {
			Set<MarketLanguageDTO> marketLanguagesDTO;
			for(CommunicationPreferencesDTO comPref : communicationPreferencesDTO) {
					comPref.setSubscribe("N");

					marketLanguagesDTO = comPref.getMarketLanguageDTO();

					if (marketLanguagesDTO != null && !marketLanguagesDTO.isEmpty()) {
						for(MarketLanguageDTO marketLanguageDTO : marketLanguagesDTO) {
							optoutMarketLanguage(marketLanguageDTO, sign);
						}
					}
					comPref.setModificationDate(sign.getDate());
					comPref.setModificationSite(sign.getSite());
					comPref.setModificationSignature(sign.getSignature());
					
					communicationPreferenceDS.update(comPref);
				}
			}
		}

	public String getGin(CreateUpdateIndividualRequestDTO requestDTO){
		return requestDTO.getIndividualRequestDTO().getIndividualInformationsDTO().getIdentifier();
	}
}
