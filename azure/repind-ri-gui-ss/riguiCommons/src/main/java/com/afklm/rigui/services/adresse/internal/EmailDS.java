package com.afklm.rigui.services.adresse.internal;


import com.afklm.rigui.enums.SiteEnum;
import com.afklm.rigui.exception.*;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.exception.jraf.JrafDomainNoRollbackException;
import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;
import com.afklm.rigui.enums.MediumCodeEnum;
import com.afklm.rigui.enums.MediumStatusEnum;
import com.afklm.rigui.dao.adresse.EmailRepository;
import com.afklm.rigui.dao.individu.AccountDataRepository;
import com.afklm.rigui.dto.adresse.EmailDTO;
import com.afklm.rigui.dto.adresse.EmailTransform;
import com.afklm.rigui.dto.individu.SignatureDTO;
import com.afklm.rigui.dto.role.RoleContratsDTO;
import com.afklm.rigui.entity.adresse.Email;
import com.afklm.rigui.entity.individu.AccountData;
import com.afklm.rigui.services.internal.unitservice.role.RoleUS;
import com.afklm.rigui.services.marketing.HandleCommunication;
import com.afklm.rigui.services.role.internal.RoleDS;
import com.afklm.rigui.util.EmailUtils;
import com.afklm.rigui.util.SicStringUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	/** reference sur le dao principal */
	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private AccountDataRepository accountDataRepository;

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
		Email email = emailRepository.findById(emailDTO.getSain()).get();
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

	public boolean isindividualMyAccount(String gin){
		AccountData account = accountDataRepository.findBySgin(gin);
		return account != null;
	}

	public boolean isIndividualFB(String gin) {
		AccountData account = accountDataRepository.findBySgin(gin);
		boolean isFlyingBlue = StringUtils.isNotEmpty(gin);

		// Return true if gin is either an account or an FB identifier
		return account != null || isFlyingBlue;
	}

	public boolean isEmailAssociatedWithOtherIndividual(String gin, String email) {
		// Check if any account with the provided email exists
		AccountData existingAccount = accountDataRepository.findByEmailIdentifier(email);

		// If no account with email exists, return false
		if (existingAccount == null) {
			return false;
		}

		// If the existing account has a different GIN than the input GIN, return true
		return !existingAccount.getSgin().equals(gin);
	}
}
