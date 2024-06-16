package com.airfrance.repind.service.adresse.internal;


import com.airfrance.ref.SiteEnum;
import com.airfrance.ref.exception.*;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.MediumCodeEnum;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dao.adresse.EmailRepository;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.adresse.EmailTransform;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.service.internal.unitservice.role.RoleUS;
import com.airfrance.repind.service.marketing.HandleCommunication;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.util.EmailUtils;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.*;

/*PROTECTED REGION END*/

/**
 * <p>
 * Title : EmailDS.java
 * </p>
 * 
 * <p>
 * Copyright : Copyright (c) 2009
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
@Service
public class EmailDS {

	/** logger */
	private static final Log log = LogFactory.getLog(EmailDS.class);

	/** the Entity Manager */
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	/** reference sur le dao principal */
	@Autowired
	private EmailRepository emailRepository;

	/** roleDS pour sharedEmail. */
    @Autowired
    @Qualifier("roleDS")
    private RoleDS roleDS;
    

    
    /** Reference sur le unit service RoleUS **/
    @Autowired
    private RoleUS roleUS;

	@Autowired
	ApplicationContext appContext;
    
	public long countAll(EmailDTO dto) throws JrafDomainException {
		Email email = EmailTransform.dto2BoLight(dto);
		return emailRepository.count(Example.of(email));
	}

	public void create(EmailDTO emailDTO) throws JrafDomainException {
		Email email = EmailTransform.dto2BoLight(emailDTO);
		emailRepository.saveAndFlush(email);
		EmailTransform.bo2DtoLight(email, emailDTO);
	}



	


	
	@Deprecated
	public void remove(EmailDTO dto) throws JrafDomainException {
		remove(dto.getSain());
	}

	public void remove(String id) throws JrafDomainException {
		emailRepository.deleteById(id);
	}

	public void update(EmailDTO emailDTO) throws JrafDomainException {
		Email email = emailRepository.getOne(emailDTO.getSain());
		EmailTransform.dto2BoLight(emailDTO, email);
		emailRepository.saveAndFlush(email);
	}

	public List<EmailDTO> findByExample(EmailDTO dto) throws JrafDomainException {
		Email email = EmailTransform.dto2BoLight(dto);
		List<EmailDTO> result = new ArrayList<>();
		for (Email found : emailRepository.findAll(Example.of(email))) {
			result.add(EmailTransform.bo2DtoLight(found));
			}
		return result;
	}
	
	public List<EmailDTO> findByEmail(String email) throws JrafDomainException {
		List<EmailDTO> result = new ArrayList<>();
		for (Email found : emailRepository.findByEmail(email)) {
			result.add(EmailTransform.bo2DtoLight(found));
		}
		return result;
	}

	@Deprecated
	public EmailDTO get(EmailDTO dto) throws JrafDomainException {
		return get(dto.getSain());
	}

	public EmailDTO get(String id) throws JrafDomainException {
		Optional<Email> email = emailRepository.findById(id);
		if (!email.isPresent()) {
			return null;
		}
		return EmailTransform.bo2DtoLight(email.get());
	}

	public List<EmailDTO> search(String email) throws JrafDomainException {
		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setEmail(email);
		return findByExample(emailDTO);
	}

	public EmailRepository getEmailRepository() {
		return emailRepository;
	}

	public void setEmailRepository(EmailRepository emailRepository) {
		this.emailRepository = emailRepository;
	}
	/**
	 * @return EntityManager
	 */
	public EntityManager getEntityManager() {
		/* PROTECTED REGION ID(_FsiuMMcXEd-9odQwMtrUbQgem ) ENABLED START */
		return entityManager;
		/* PROTECTED REGION END */
	}

	/**
	 * @param entityManager
	 *            EntityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/* PROTECTED REGION ID(_FsiuMMcXEd-9odQwMtrUbQ u m) ENABLED START */

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly=true)
    public boolean checkSharedFlyingBlueEmail(String email, String sGin) throws JrafDomainException {

        return roleUS.existFrequencePlusContractSharingSameValidEmail(email, sGin);
    }

	/**
	 * {@inheritDoc}
	 */
    @Transactional(readOnly=true)
	public boolean emailExist(String sgin, String email) throws JrafDomainException {
		EmailDTO currentEmail = new EmailDTO();
		currentEmail.setEmail(email.toLowerCase());
		currentEmail.setSgin(sgin);
		currentEmail.setStatutMedium(MediumStatusEnum.VALID.toString());
		return countAll(currentEmail) != 0;
	}

	/**
	 * {@inheritDoc} 
	 */
	// REPIND-555: Prospect migration
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void invalidOnEmail(EmailDTO emailDTO, String signature) throws JrafDomainException {

		Email emailBO = new Email();

		EmailTransform.dto2BoLight(emailDTO,emailBO);
		emailBO.setSignatureModification(signature);
		emailBO.setSiteModification(SiteEnum.BATCHQVI.toString());
						
		emailRepository.invalidOnEmail(emailBO);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isEmailFBMember(String email, String gin) throws JrafDomainException {

		boolean result = false;

		EmailDTO currentEmail = new EmailDTO();
		currentEmail.setEmail(email.toLowerCase());
		currentEmail.setStatutMedium("V"); // email valide
		List<EmailDTO> dtoEmailFounds = null;
		dtoEmailFounds = findByExample(currentEmail);

		// Si on trouve des emails correspondants en base
		// on vérifie qu'il n'y en a pas plusieurs de type FP
		if (!dtoEmailFounds.isEmpty()) {
			EmailDS.log.info("isEmailFBMember: email founds : " + email);
			for (EmailDTO currentEmailDTO : dtoEmailFounds) {
				if ("V".equalsIgnoreCase(currentEmailDTO.getStatutMedium())) {
					String currGIN = currentEmailDTO.getSgin();
					if (currGIN != null && !"".equals(currGIN.trim())) {
						if (!currGIN.equalsIgnoreCase(gin)) {
							RoleContratsDTO currentRoleContrat = new RoleContratsDTO();
							currentRoleContrat.setGin(currGIN);
							currentRoleContrat.setTypeContrat("FP");
							Integer count = roleDS.countAll(currentRoleContrat);
							if (count > 0) {
								result = true;
							}
							if (result == false) {
								currentRoleContrat.setTypeContrat("MA");
								count = roleDS.countAll(currentRoleContrat);
								if (count > 0) {
									result = true;
							}
						}
					}
				}
				}
			}// end for dtoEmailFounds
		}// end if(!dtoEmailFounds.isEmpty())
		return result;
	}

    @Transactional(readOnly=true)
	public List<EmailDTO> findEmail(String gin) throws JrafDomainException {
		
		if(StringUtils.isEmpty(gin)) {
			throw new IllegalArgumentException("Unable to find email without GIN");
		}
		
		List<Email> emailList = emailRepository.findEmail(gin);
		
		if(emailList==null) {
			return null;
		}
		
		return EmailTransform.bo2Dto(emailList);
	}
    
    @Transactional(readOnly=true)
   	public List<EmailDTO> findByGinAndCodeMedium(String gin, String codeMedium) throws JrafDomainException {
   		
   		if(StringUtils.isEmpty(gin)) {
   			throw new IllegalArgumentException("Unable to find email without GIN");
   		}
   		
   		if(StringUtils.isEmpty(gin)) {
   			throw new IllegalArgumentException("Unable to find email without CODE MEDIUM");
   		}
   		
   		List<Email> emailList = emailRepository.findByGinAndCodeMedium(gin, codeMedium);
   		
   		if(emailList==null) {
   			return null;
   		}
   		
   		return EmailTransform.bo2Dto(emailList);
   	}
	
	/*
	 * Check if the email address given in the parameter is already owned by a
	 * different GIN than the one given in parameter
	 */
    @Transactional(readOnly=true)
	public boolean isEmailShared(String email, String originGin) throws JrafDomainException {
		if (StringUtils.isBlank(email) || StringUtils.isBlank(originGin)) {
			throw new InvalidParameterException("email or gin blank");
		}
		EmailDTO currentEmail = new EmailDTO();
		currentEmail.setEmail(email);
		currentEmail.setStatutMedium(MediumStatusEnum.VALID.toString());
		List<EmailDTO> emails = findByExample(currentEmail);
		if (emails == null) {
			return false;
		}
		for (EmailDTO currentEmailDTO : emails){				
			if (!StringUtils.equals(originGin, currentEmailDTO.getSgin())) {
				return true;
			}			
		}
		return false;
	}
	
	public boolean isValidAndExistForGin(String email, String gin) throws JrafDomainException {
		if (StringUtils.isBlank(gin)) {
			throw new MissingParameterException("GIN must not be blank");
		}
	
		// Email fourni non valide
		if (!SicStringUtils.isValidEmail(email)){
			return false;
		}
		
		// recherche de l'email et comparaison des GINs
		EmailDTO currentEmail = new EmailDTO();
		currentEmail.setEmail(email);
		currentEmail.setStatutMedium(MediumStatusEnum.VALID.toString());
		currentEmail.setSgin(gin);
		List<EmailDTO> dtoEmailFounds = findByExample(currentEmail);

		if(dtoEmailFounds == null || dtoEmailFounds.isEmpty()) {
			return false;
		}
		return true;		
	}
	
	public List<EmailDTO> findValidHomeEmail(String gin) throws JrafDomainException {
		if (StringUtils.isBlank(gin)) {
			throw new MissingParameterException("GIN must not be blank");
		}
		
		// recherche de l'email et comparaison des GINs
		EmailDTO currentEmail = new EmailDTO();
		currentEmail.setStatutMedium(MediumStatusEnum.VALID.toString());
		currentEmail.setCodeMedium(MediumCodeEnum.HOME.toString());
		currentEmail.setSgin(gin);
		List<EmailDTO> dtoEmailFounds = findByExample(currentEmail);

		if (dtoEmailFounds == null || dtoEmailFounds.isEmpty()) {
			return new ArrayList<EmailDTO>();
		}
		
		return dtoEmailFounds;		
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateEmail(String gin, List<EmailDTO> emailListFromWS, SignatureDTO signatureAPP) throws JrafDomainException {
		if(emailListFromWS == null || emailListFromWS.isEmpty()) {return;}
		for (EmailDTO emailFromWS : emailListFromWS) {
			// Get all emails of the individual for the code medium
			/*EmailDTO mail = new EmailDTO();
			mail.setSgin(gin);
			mail.setCodeMedium(emailFromWS.getCodeMedium());
			List<EmailDTO> emailFromDBList = findByExample(mail);*/
			List<EmailDTO> emailFromDBList = findByGinAndCodeMedium(gin, emailFromWS.getCodeMedium());
			
			emailFromWS.setSgin(gin);
		
			if (emailFromWS.getCodeMedium() == null) {
				// Code medium null = ERROR_117
				throw new MissingMediumCodeException("Medium code is mandatory for email creation or update ");
			} 	
			// Not status 'X'
			if (!MediumStatusEnum.REMOVED.toString().equalsIgnoreCase(emailFromWS.getStatutMedium())) {
				processCreateOrUpdateEmail(gin, emailFromWS, emailFromDBList, signatureAPP);
				continue;
			}
			// Status 'X'
			processDeletion(emailFromWS, emailFromDBList, signatureAPP);
		}
	}
		
	// REPIND-1398 : Private method could not be Transactional, it has no effect.
	// @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	private void processCreateOrUpdateEmail(String gin, EmailDTO emailFromWS, List<EmailDTO> emailFromDBList, SignatureDTO signatureAPP) throws JrafDomainException {
		if (emailFromWS == null || emailFromWS.getEmail() == null) {
			throw new MissingMailingDataException("Email is null ");
		}
		if(StringUtils.isEmpty(emailFromWS.getCodeMedium())) {
			throw new MissingMediumCodeException("Medium code is mandatory for email creation or update ");
		}

		String email = emailFromWS.getEmail();
		// Check email format
		if (!EmailUtils.isValidEmail(email)) {
			throw new InvalidMailingException("Invalid email ");
		}
		
		if(	!StringUtils.equals(emailFromWS.getStatutMedium(), MediumStatusEnum.REMOVED.toString()) &&
			!StringUtils.equals(emailFromWS.getStatutMedium(), MediumStatusEnum.VALID.toString()) &&
			!StringUtils.equals(emailFromWS.getStatutMedium(), MediumStatusEnum.INVALID.toString())) {
			throw new InvalidParameterException("Invalid email status");
		}
		
		// Check NAT
		EmailUtils.checkNAT(emailFromWS);
		 
		// Avoid duplicate emails by using this boolean
		boolean emailUpdated = false;
		boolean emailChange = false;
		String old_email = "";
		
		if (emailFromDBList != null && !emailFromDBList.isEmpty()) {
			
			for (EmailDTO mailFromDB : emailFromDBList) {

				// Mail from DB Status different from X and different email
				if (!MediumStatusEnum.REMOVED.toString().equalsIgnoreCase(mailFromDB.getStatutMedium()) 
						&& !mailFromDB.getEmail().equalsIgnoreCase(emailFromWS.getEmail())) {
					
					if(MediumStatusEnum.VALID.toString().equalsIgnoreCase(mailFromDB.getStatutMedium())){
						old_email = mailFromDB.getEmail();
					}
					
					// Delete old email if different
					deleteOldEmails(mailFromDB, signatureAPP);
					
					emailChange = true;
				}
				// mail from DB Status different from X and same email
				else if (!MediumStatusEnum.REMOVED.toString().equalsIgnoreCase(mailFromDB.getStatutMedium())
						&& mailFromDB.getEmail().equalsIgnoreCase(emailFromWS.getEmail())){
					
					mailFromDB.setAutorisationMailing(emailFromWS.getAutorisationMailing());
					mailFromDB.setStatutMedium(emailFromWS.getStatutMedium());
					prepareSignForUpdate(mailFromDB, signatureAPP);
					
					emailRepository.saveAndFlush(EmailTransform.dto2BoLight(mailFromDB));
					log.info("Email '" + email + "' already exist... update done ");
					
					old_email = emailFromWS.getEmail();
					
					emailUpdated = true;
				}
			}
		}
		
		if (!emailUpdated) {
			emailFromWS.setVersion(1);
			emailFromWS.setDateCreation(new Date());
			emailFromWS.setSignatureCreation(signatureAPP.getSignature());
			emailFromWS.setSiteCreation(signatureAPP.getSite());
			emailFromWS.setDateModification(new Date());
			emailFromWS.setSignatureModification(signatureAPP.getSignature());
			emailFromWS.setSiteModification(signatureAPP.getSite());
			
			emailRepository.saveAndFlush(EmailTransform.dto2BoLight(emailFromWS));
			log.debug("Email created ");
			
			if(emailChange && !signatureAPP.getSignature().equalsIgnoreCase("BATCH_QVI") && StringUtils.isNotEmpty(old_email)){
				// call CRMPUSH for campagne email_change
				HandleCommunication handleCommunication = new HandleCommunication(appContext);
				List<String> cinList = roleDS.getFPNumberByGin(gin);
				String cin = "";
				if (!CollectionUtils.isEmpty(cinList)) {
					cin = cinList.get(0);
				}
				handleCommunication.askHandleCommEmailChange(gin, cin, "", old_email, emailFromWS.getEmail(), "REPIND", "B2C");
				log.debug("notification_email_change send");
			}
		}	
		
	}

	private void deleteOldEmails(EmailDTO mailFromDBDto, SignatureDTO signatureAPP) throws JrafDomainException {
		mailFromDBDto.setStatutMedium(MediumStatusEnum.REMOVED.toString());
		prepareSignForUpdate(mailFromDBDto, signatureAPP);
		emailRepository.saveAndFlush(EmailTransform.dto2BoLight(mailFromDBDto));
	}

	private void prepareSignForUpdate(EmailDTO email, SignatureDTO signatureAPP) {
		if (email.getDateCreation() == null) {
			email.setDateCreation(new Date());
		}
		if (email.getSignatureCreation() == null) {
			email.setSignatureCreation(signatureAPP.getSignature());
		}
		if (email.getSiteCreation() == null) {
			email.setSiteCreation(signatureAPP.getSite());
		}
		
		email.setDateModification(new Date());
		email.setSignatureModification(signatureAPP.getSignature());
		email.setSiteModification(signatureAPP.getSite());
		
	}

	private void processDeletion(EmailDTO emailFromWS, List<EmailDTO> emailFromDBList, SignatureDTO signatureAPP) throws JrafDomainException {
		boolean updateDone = false;
		
		if (emailFromDBList != null && emailFromWS.getEmail() != null) {
			for (EmailDTO mailFromDB: emailFromDBList) {
				if (mailFromDB.getEmail() != null && emailFromWS.getEmail().equalsIgnoreCase(mailFromDB.getEmail())) {
					deleteOldEmails(mailFromDB, signatureAPP);
					updateDone = true;
				}
			}
		}
		
		// Log action of creation of deleted email
		if (!updateDone) {
			deleteOldEmails(emailFromWS, signatureAPP);
		}
	}
	
	public int getNumberProEmailByGin(String gin) throws JrafDomainException {
		return emailRepository.getNumberProOrPersoEmailByGinAndCodeMedium(gin, "P").intValue();
	}
	public int getNumberPersoEmailByGin(String gin) throws JrafDomainException {
		return emailRepository.getNumberProOrPersoEmailByGinAndCodeMedium(gin, "D").intValue();
	}





	public List<EmailDTO> findValidEmail(@NotNull String gin) throws JrafDomainException {
		List<EmailDTO> dtoEmailFounds;

		// recherche de l'email et comparaison des GINs
		EmailDTO currentEmail = new EmailDTO();
		currentEmail.setStatutMedium(MediumStatusEnum.VALID.toString());
		currentEmail.setSgin(gin);
		return findByExample(currentEmail);
	}

	/**
	 * Creates an email for a personne morale
	 *
	 * @param emailDTO email to be created
	 * @param agenceBO agency to associate email
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createOrUpdateAgencyEmail(EmailDTO emailDTO, Agence agenceBO) throws JrafDomainException {
		if (emailDTO != null && agenceBO != null) {
			List<String> medium = Arrays.asList(emailDTO.getCodeMedium());
			List<EmailDTO> dbList = findByGinPmCodeMedium(agenceBO.getGin(), medium);

			if (CollectionUtils.isEmpty(dbList)) {
				// No email in DB
				createPmEmail(emailDTO, agenceBO);
			}
			else {
				for (EmailDTO dbLoop : dbList) {
					if (MediumStatusEnum.VALID.equals(dbLoop.getStatutMedium())
							&& dbLoop.getEmail() != null
							&& !dbLoop.getEmail().equals(emailDTO.getEmail())) {

						// Different email ==> update
						emailDTO.setSain(dbLoop.getSain());
						emailDTO.setVersion(dbLoop.getVersion());
						emailDTO.setPersonneMorale(dbLoop.getPersonneMorale());
						emailDTO.setDateCreation(dbLoop.getDateCreation());
						emailDTO.setSignatureCreation(dbLoop.getSignatureCreation());
						emailDTO.setSiteCreation(dbLoop.getSiteCreation());

						Email emailToSave = EmailTransform.dto2BoLink(emailDTO);
						emailToSave.setPersonneMorale(agenceBO);
						emailRepository.saveAndFlush(emailToSave);
					}
				}
			}
		}
	}

	/**
	 * Find the email for a PersonneMorale for the specified GIN and CodeMedium
	 *
	 * @param gin      the GIN of PersonneMorale
	 * @param codeList list of CodeMedium
	 * @return the list of address found
	 */
	private List<EmailDTO> findByGinPmCodeMedium(String gin, List<String> codeList) throws JrafDomainException {
		List<Email> result = emailRepository.findByGinPmCodeMedium(gin, codeList);
		if (result != null && !result.isEmpty()) {
			return EmailTransform.bo2Dto(result);
		}
		return Collections.emptyList();
	}

	/**
	 * Creates an email for a personne morale
	 *
	 * @param emailPm email to be created
	 * @throws JrafDomainException
	 */
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String createPmEmail(EmailDTO emailPm, PersonneMorale pm) throws JrafDomainException {
		if (pm == null || StringUtils.isEmpty(pm.getGin())) {
			throw new MissingParameterException("PM data is missing");
		}

		Email toBeCreated = EmailTransform.dto2BoLight(emailPm);
		toBeCreated.setPersonneMorale(pm);
		Email created = emailRepository.saveAndFlush(toBeCreated);
		return created.getSain();
	}
}
