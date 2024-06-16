package com.afklm.rigui.dto.individu;

/*PROTECTED REGION ID(_couCUDLAEeCru6uc_4I2vA i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.entity.individu.AccountData;
import com.afklm.rigui.entity.individu.Individu;
import com.afklm.rigui.exception.jraf.JrafDomainException;

import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : AccountDataTransform.java</p>
 * transformation bo <-> dto pour un(e) AccountData
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class AccountDataTransform {
    /**
     * Constructeur prive
     */
    private AccountDataTransform() {
    }
    /**
     * dto -> bo pour une AccountData
     * @param accountDataDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static AccountData dto2BoLight(AccountDataDTO accountDataDTO) throws JrafDomainException {
        // instanciation du BO
        AccountData accountData = new AccountData();
        dto2BoLight(accountDataDTO, accountData);

        // on retourne le BO
        return accountData;
    }
    
    public static AccountData dto2BoLightIndividual(AccountDataDTO accountDataDTO) throws JrafDomainException {
        // instanciation du BO
        AccountData accountData = new AccountData();
        dto2BoLightIndividual(accountDataDTO, accountData);

        // on retourne le BO
        return accountData;
    }

    public static AccountData dto2BoIndividual(AccountDataDTO accountDataDTO) throws JrafDomainException {
        // instanciation du BO
        AccountData accountData = new AccountData();
        dto2BoIndividual(accountDataDTO, accountData);

        // on retourne le BO
        return accountData;
    }


    /**
     * dto -> bo pour une accountData
     * @param accountDataDTO dto
     * @param accountData bo
     */
    public static void dto2BoLight(AccountDataDTO accountDataDTO, AccountData accountData) throws JrafDomainException{
        // property of AccountDataDTO
        accountData.setId(accountDataDTO.getId());
        accountData.setVersion(accountDataDTO.getVersion());
        accountData.setAccountIdentifier(accountDataDTO.getAccountIdentifier());
        accountData.setSgin(accountDataDTO.getSgin());
        accountData.setFbIdentifier(accountDataDTO.getFbIdentifier());
        accountData.setEmailIdentifier(accountDataDTO.getEmailIdentifier());
        accountData.setPersonnalizedIdentifier(accountDataDTO.getPersonnalizedIdentifier());
        accountData.setStatus(accountDataDTO.getStatus());
        accountData.setPassword(accountDataDTO.getPassword());
        accountData.setPasswordToChange(accountDataDTO.getPasswordToChange());
        accountData.setTemporaryPwd(accountDataDTO.getTemporaryPwd());
        accountData.setTemporaryPwdEndDate(accountDataDTO.getTemporaryPwdEndDate());
        accountData.setSecretQuestion(accountDataDTO.getSecretQuestion()==null?null:accountDataDTO.getSecretQuestion().getBytes(StandardCharsets.UTF_8));
        accountData.setSecretQuestionAnswer(accountDataDTO.getSecretQuestionAnswer());
        accountData.setNbFailureAuthentification(accountDataDTO.getNbFailureAuthentification());
        accountData.setNbFailureSecretQuestionAns(accountDataDTO.getNbFailureSecretQuestionAns());
        accountData.setEnrolmentPointOfSell(accountDataDTO.getEnrolmentPointOfSell());
        accountData.setCarrier(accountDataDTO.getCarrier());
        accountData.setLastConnexionDate(accountDataDTO.getLastConnexionDate());
        accountData.setLastPwdResetDate(accountDataDTO.getLastPwdResetDate());
        accountData.setAccountDeletionDate(accountDataDTO.getAccountDeletionDate());
        accountData.setSiteCreation(accountDataDTO.getSiteCreation());
        accountData.setSignatureCreation(accountDataDTO.getSignatureCreation());
        accountData.setDateCreation(accountDataDTO.getDateCreation());
        accountData.setSiteModification(accountDataDTO.getSiteModification());
        accountData.setSignatureModification(accountDataDTO.getSignatureModification());
        accountData.setDateModification(accountDataDTO.getDateModification());
        accountData.setIpAddress(accountDataDTO.getIpAddress());
        accountData.setAccountUpgradeDate(accountDataDTO.getAccountUpgradeDate());
        accountData.setAccountLockedDate(accountDataDTO.getAccountLockedDate());
        accountData.setSecretQuestionAnswerUpper(accountDataDTO.getSecretQuestionAnswerUpper());
        accountData.setLastSecretAnswModification(accountDataDTO.getLastSecretAnswModification());
        accountData.setSocialNetworkId(accountDataDTO.getSocialNetworkId());
        accountData.setLastSocialNetworkLogonDate(accountDataDTO.getLastSocialNetworkLogonDate());
        accountData.setLastSocialNetworkUsed(accountDataDTO.getLastSocialNetworkUsed());
        accountData.setLastSocialNetworkId(accountDataDTO.getLastSocialNetworkId());

        if (accountDataDTO.getIndividudto() != null) {
            accountData.setIndividu(IndividuTransform.dto2BoLight(accountDataDTO.getIndividudto()));
        }
    }

    public static void dto2BoLightIndividual(AccountDataDTO accountDataDTO, AccountData accountData) throws JrafDomainException {
        // property of AccountDataDTO
        accountData.setId(accountDataDTO.getId());
        accountData.setVersion(accountDataDTO.getVersion());
        accountData.setAccountIdentifier(accountDataDTO.getAccountIdentifier());
        accountData.setSgin(accountDataDTO.getSgin());
        accountData.setFbIdentifier(accountDataDTO.getFbIdentifier());
        accountData.setEmailIdentifier(accountDataDTO.getEmailIdentifier());
        accountData.setPersonnalizedIdentifier(accountDataDTO.getPersonnalizedIdentifier());
        accountData.setStatus(accountDataDTO.getStatus());
        accountData.setPassword(accountDataDTO.getPassword());
        accountData.setPasswordToChange(accountDataDTO.getPasswordToChange());
        accountData.setTemporaryPwd(accountDataDTO.getTemporaryPwd());
        accountData.setTemporaryPwdEndDate(accountDataDTO.getTemporaryPwdEndDate());
        accountData.setSecretQuestion(accountDataDTO.getSecretQuestion()==null?null:accountDataDTO.getSecretQuestion().getBytes(StandardCharsets.UTF_8));
        accountData.setSecretQuestionAnswer(accountDataDTO.getSecretQuestionAnswer());
        accountData.setNbFailureAuthentification(accountDataDTO.getNbFailureAuthentification());
        accountData.setNbFailureSecretQuestionAns(accountDataDTO.getNbFailureSecretQuestionAns());
        accountData.setEnrolmentPointOfSell(accountDataDTO.getEnrolmentPointOfSell());
        accountData.setCarrier(accountDataDTO.getCarrier());
        accountData.setLastConnexionDate(accountDataDTO.getLastConnexionDate());
        accountData.setLastPwdResetDate(accountDataDTO.getLastPwdResetDate());
        accountData.setAccountDeletionDate(accountDataDTO.getAccountDeletionDate());
        accountData.setSiteCreation(accountDataDTO.getSiteCreation());
        accountData.setSignatureCreation(accountDataDTO.getSignatureCreation());
        accountData.setDateCreation(accountDataDTO.getDateCreation());
        accountData.setSiteModification(accountDataDTO.getSiteModification());
        accountData.setSignatureModification(accountDataDTO.getSignatureModification());
        accountData.setDateModification(accountDataDTO.getDateModification());
        accountData.setIpAddress(accountDataDTO.getIpAddress());
        accountData.setAccountUpgradeDate(accountDataDTO.getAccountUpgradeDate());
        accountData.setAccountLockedDate(accountDataDTO.getAccountLockedDate());
        accountData.setSecretQuestionAnswerUpper(accountDataDTO.getSecretQuestionAnswerUpper());
        accountData.setLastSecretAnswModification(accountDataDTO.getLastSecretAnswModification());
        accountData.setSocialNetworkId(accountDataDTO.getSocialNetworkId());
        accountData.setLastSocialNetworkLogonDate(accountDataDTO.getLastSocialNetworkLogonDate());
        accountData.setLastSocialNetworkUsed(accountDataDTO.getLastSocialNetworkUsed());
        accountData.setLastSocialNetworkId(accountDataDTO.getLastSocialNetworkId());
        
        if (accountDataDTO.getIndividudto() != null) {
        	accountData.setIndividu(IndividuTransform.dto2BoLight(accountDataDTO.getIndividudto()));
        }
    }

    public static void dto2BoIndividual(AccountDataDTO accountDataDTO, AccountData accountData) throws JrafDomainException {
        // property of AccountDataDTO
        accountData.setId(accountDataDTO.getId());
        accountData.setVersion(accountDataDTO.getVersion());
        accountData.setAccountIdentifier(accountDataDTO.getAccountIdentifier());
        accountData.setSgin(accountDataDTO.getSgin());
        accountData.setFbIdentifier(accountDataDTO.getFbIdentifier());
        accountData.setEmailIdentifier(accountDataDTO.getEmailIdentifier());
        accountData.setPersonnalizedIdentifier(accountDataDTO.getPersonnalizedIdentifier());
        accountData.setStatus(accountDataDTO.getStatus());
        accountData.setPassword(accountDataDTO.getPassword());
        accountData.setPasswordToChange(accountDataDTO.getPasswordToChange());
        accountData.setTemporaryPwd(accountDataDTO.getTemporaryPwd());
        accountData.setTemporaryPwdEndDate(accountDataDTO.getTemporaryPwdEndDate());
        accountData.setSecretQuestion(accountDataDTO.getSecretQuestion()==null?null:accountDataDTO.getSecretQuestion().getBytes(StandardCharsets.UTF_8));
        accountData.setSecretQuestionAnswer(accountDataDTO.getSecretQuestionAnswer());
        accountData.setNbFailureAuthentification(accountDataDTO.getNbFailureAuthentification());
        accountData.setNbFailureSecretQuestionAns(accountDataDTO.getNbFailureSecretQuestionAns());
        accountData.setEnrolmentPointOfSell(accountDataDTO.getEnrolmentPointOfSell());
        accountData.setCarrier(accountDataDTO.getCarrier());
        accountData.setLastConnexionDate(accountDataDTO.getLastConnexionDate());
        accountData.setLastPwdResetDate(accountDataDTO.getLastPwdResetDate());
        accountData.setAccountDeletionDate(accountDataDTO.getAccountDeletionDate());
        accountData.setSiteCreation(accountDataDTO.getSiteCreation());
        accountData.setSignatureCreation(accountDataDTO.getSignatureCreation());
        accountData.setDateCreation(accountDataDTO.getDateCreation());
        accountData.setSiteModification(accountDataDTO.getSiteModification());
        accountData.setSignatureModification(accountDataDTO.getSignatureModification());
        accountData.setDateModification(accountDataDTO.getDateModification());
        accountData.setIpAddress(accountDataDTO.getIpAddress());
        accountData.setAccountUpgradeDate(accountDataDTO.getAccountUpgradeDate());
        accountData.setAccountLockedDate(accountDataDTO.getAccountLockedDate());
        accountData.setSecretQuestionAnswerUpper(accountDataDTO.getSecretQuestionAnswerUpper());
        accountData.setLastSecretAnswModification(accountDataDTO.getLastSecretAnswModification());
        accountData.setSocialNetworkId(accountDataDTO.getSocialNetworkId());
        accountData.setLastSocialNetworkLogonDate(accountDataDTO.getLastSocialNetworkLogonDate());
        accountData.setLastSocialNetworkUsed(accountDataDTO.getLastSocialNetworkUsed());
        accountData.setLastSocialNetworkId(accountDataDTO.getLastSocialNetworkId());
        
        if (accountDataDTO.getIndividudto() != null) {
        	accountData.setIndividu(IndividuTransform.dto2BoLight(accountDataDTO.getIndividudto()));
        }
    }

    /**
     * bo -> dto pour une accountData
     * @param pAccountData bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static AccountDataDTO bo2DtoLight(AccountData pAccountData) throws JrafDomainException {
        // instanciation du DTO
        AccountDataDTO accountDataDTO = new AccountDataDTO();
        bo2DtoLight(pAccountData, accountDataDTO);
        // on retourne le dto
        return accountDataDTO;
    }


    /**
     * Transforme un business object en DTO.
     * Methode dite light.
     * @param accountData bo
     * @param accountDataDTO dto
     */
    public static void bo2DtoLight(
        AccountData accountData,
        AccountDataDTO accountDataDTO) {


        // simple properties
        accountDataDTO.setId(accountData.getId());
        accountDataDTO.setVersion(accountData.getVersion());
        accountDataDTO.setAccountIdentifier(accountData.getAccountIdentifier());
        accountDataDTO.setSgin(accountData.getSgin());
        accountDataDTO.setFbIdentifier(accountData.getFbIdentifier());
        accountDataDTO.setEmailIdentifier(accountData.getEmailIdentifier());
        accountDataDTO.setPersonnalizedIdentifier(accountData.getPersonnalizedIdentifier());
        accountDataDTO.setStatus(accountData.getStatus());
        accountDataDTO.setPassword(accountData.getPassword());
        accountDataDTO.setPasswordToChange(accountData.getPasswordToChange());
        accountDataDTO.setTemporaryPwd(accountData.getTemporaryPwd());
        accountDataDTO.setTemporaryPwdEndDate(accountData.getTemporaryPwdEndDate());
        accountDataDTO.setSecretQuestion(accountData.getSecretQuestion()==null?null:new String(accountData.getSecretQuestion(),StandardCharsets.UTF_8));
        accountDataDTO.setSecretQuestionAnswer(accountData.getSecretQuestionAnswer());
        accountDataDTO.setNbFailureAuthentification(accountData.getNbFailureAuthentification());
        accountDataDTO.setNbFailureSecretQuestionAns(accountData.getNbFailureSecretQuestionAns());
        accountDataDTO.setEnrolmentPointOfSell(accountData.getEnrolmentPointOfSell());
        accountDataDTO.setCarrier(accountData.getCarrier());
        accountDataDTO.setLastConnexionDate(accountData.getLastConnexionDate());
        accountDataDTO.setLastPwdResetDate(accountData.getLastPwdResetDate());
        accountDataDTO.setAccountDeletionDate(accountData.getAccountDeletionDate());
        accountDataDTO.setSiteCreation(accountData.getSiteCreation());
        accountDataDTO.setSignatureCreation(accountData.getSignatureCreation());
        accountDataDTO.setDateCreation(accountData.getDateCreation());
        accountDataDTO.setSiteModification(accountData.getSiteModification());
        accountDataDTO.setSignatureModification(accountData.getSignatureModification());
        accountDataDTO.setDateModification(accountData.getDateModification());
        accountDataDTO.setIpAddress(accountData.getIpAddress());
        accountDataDTO.setAccountUpgradeDate(accountData.getAccountUpgradeDate());
        accountDataDTO.setAccountLockedDate(accountData.getAccountLockedDate());
        accountDataDTO.setSecretQuestionAnswerUpper(accountData.getSecretQuestionAnswerUpper());
        accountDataDTO.setLastSecretAnswModification(accountData.getLastSecretAnswModification());
        accountDataDTO.setSocialNetworkId(accountData.getSocialNetworkId());
        accountDataDTO.setLastSocialNetworkLogonDate(accountData.getLastSocialNetworkLogonDate());
        accountDataDTO.setLastSocialNetworkUsed(accountData.getLastSocialNetworkUsed());
        accountDataDTO.setLastSocialNetworkId(accountData.getLastSocialNetworkId());
    }
    
    /*PROTECTED REGION ID(_couCUDLAEeCru6uc_4I2vA u m - Tr) ENABLED START*/
    /**
	 * Ask both method : dto2BoLight & dto2BoLink
	 * @param accountDataDTO
	 * @param accountData
	 */
	public static void dto2Bo(AccountDataDTO accountDataDTO, AccountData accountData) throws JrafDomainException {
		dto2BoLight(accountDataDTO, accountData);
		dto2BoLink(accountDataDTO, accountData);
	}

	/**
	 * Ask both method : bo2DtoLight & bo2DtoLink
	 * @param accountDataDTO
	 * @param accountData
	 * @throws JrafDomainException 
	 */
	public static void bo2Dto(AccountData accountData, AccountDataDTO accountDataDTO) throws JrafDomainException {
		bo2DtoLight(accountData, accountDataDTO);
		bo2DtoLink(accountDataDTO, accountData);
	}
	
	public static void bo2DtoLightIndividu(AccountData accountData, AccountDataDTO accountDataDTO) throws JrafDomainException {
		bo2DtoLight(accountData, accountDataDTO);
		bo2DtoLinkIndividu(accountDataDTO, accountData);
	}
	
	/**
	 * Ask both method : dto2BoLight & dto2BoLink
	 * @param accountDataDTO
	 * @return
	 * @throws JrafDomainException
	 */
	public static AccountData dto2Bo(AccountDataDTO accountDataDTO) throws JrafDomainException {
		//instanciation du BO
		AccountData accountData = new AccountData();
		dto2Bo(accountDataDTO, accountData);

		// on retourne le BO
		return accountData;
	}

	/**
	 * Ask both method : bo2DtoLight & bo2DtoLink
	 * @param accountData
	 * @return
	 * @throws JrafDomainException
	 */
	public static AccountDataDTO bo2Dto(AccountData accountData) throws JrafDomainException {
		// instanciation du DTO
		AccountDataDTO accountDataDTO = new AccountDataDTO();
		bo2Dto(accountData, accountDataDTO);
		// on retourne le dto
		return accountDataDTO;
	}
	
	public static AccountDataDTO bo2DtoLightIndividu(AccountData accountData) throws JrafDomainException {
		// instanciation du DTO
		AccountDataDTO accountDataDTO = new AccountDataDTO();
		bo2DtoLightIndividu(accountData, accountDataDTO);
		// on retourne le dto
		return accountDataDTO;
	}
    
	public static void dto2BoLink(AccountDataDTO accountDataDTO, AccountData accountData) throws JrafDomainException {
		if (accountDataDTO.getIndividudto() != null) {
	    	accountData.setIndividu(IndividuTransform.dto2Bo(accountDataDTO.getIndividudto()));			
		}

	}
	
	public static void bo2DtoLink(AccountDataDTO accountDataDTO, AccountData accountData) throws JrafDomainException {
		if(accountData.getIndividu() != null) {
			accountDataDTO.setIndividudto(IndividuTransform.bo2Dto(accountData.getIndividu()));
        }
	}
	
	public static void bo2DtoLinkIndividu(AccountDataDTO accountDataDTO, AccountData accountData) throws JrafDomainException {
		if(accountData.getIndividu() != null) {
			accountDataDTO.setIndividudto(IndividuTransform.bo2DtoLight(accountData.getIndividu()));
        }
	}

    public static void dto2BoLink(EntityManager entityManager, AccountDataDTO accountDataDTO, AccountData accountData) {
		String gin = null;
		if(accountDataDTO.getIndividudto() !=null && accountDataDTO.getIndividudto().getSgin() != null) {
			gin = accountDataDTO.getIndividudto().getSgin();
		} else if(accountDataDTO.getSgin() !=null ) {
			gin = accountDataDTO.getSgin();
		}

		if ( gin != null) {
	    	Individu individu = entityManager.getReference(Individu.class, gin);
	    	accountData.setIndividu(individu);			
		}

	}

public static List<AccountDataDTO> bo2Dto(List<AccountData> accountDataList) throws JrafDomainException {
		
		if(accountDataList==null) {
			return null;
		}
		
		List<AccountDataDTO> accountDataDTOList = new ArrayList<>();

		for(AccountData accountData : accountDataList) {
			AccountDataDTO accountDataDTO = bo2Dto(accountData);
			accountDataDTOList.add(accountDataDTO);
		}
		
		return accountDataDTOList;
		
	}
	
	
    /*PROTECTED REGION END*/
}

