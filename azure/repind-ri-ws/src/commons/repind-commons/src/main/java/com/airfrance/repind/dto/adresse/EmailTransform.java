package com.airfrance.repind.dto.adresse;

/*PROTECTED REGION ID(_n1JlcOA1Ed-ucu7RY88Scg i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.adresse.Email;
import com.airfrance.repind.util.SicStringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : EmailTransform.java</p>
 * transformation bo <-> dto pour un(e) Email
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class EmailTransform {

    /*PROTECTED REGION ID(_n1JlcOA1Ed-ucu7RY88Scg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private EmailTransform() {
    }
    /**
     * dto -> bo for a Email
     * @param emailDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Email dto2BoLight(EmailDTO emailDTO) throws JrafDomainException {
        // instanciation du BO
        Email email = new Email();
        dto2BoLight(emailDTO, email);

        // on retourne le BO
        return email;
    }
    
    /**
     * dto -> bo for a Email
     * @param emailDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Email dto2BoLink(EmailDTO emailDTO) throws JrafDomainException {
        // instanciation du BO
        Email email = new Email();
        dto2BoLink(emailDTO, email);

        // on retourne le BO
        return email;
    }

 
	private static void dto2BoLink(EmailDTO emailDTO, Email email) throws JrafDomainException {


		dto2BoLinkImpl(emailDTO,email);
		
	}
	/**
     * dto -> bo for a email
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param emailDTO dto
     * @param email bo
     */
    public static void dto2BoLight(EmailDTO emailDTO, Email email) {
    
        /*PROTECTED REGION ID(dto2BoLight_n1JlcOA1Ed-ucu7RY88Scg) ENABLED START*/
        
        dto2BoLightImpl(emailDTO,email);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a email
     * @param emailDTO dto
     * @param email bo
     */
    private static void dto2BoLightImpl(EmailDTO emailDTO, Email email){
    
        // property of EmailDTO
        email.setSain(emailDTO.getSain());
        email.setSgin(emailDTO.getSgin());
        email.setVersion(emailDTO.getVersion());
        email.setCodeMedium(emailDTO.getCodeMedium());
        email.setStatutMedium(emailDTO.getStatutMedium());
        email.setEmail(SicStringUtils.normalizeEmail(emailDTO.getEmail()));
        email.setDescriptifComplementaire(emailDTO.getDescriptifComplementaire());
        email.setAutorisationMailing(emailDTO.getAutorisationMailing());
        email.setSignatureModification(emailDTO.getSignatureModification());
        email.setSiteModification(emailDTO.getSiteModification());
        email.setDateModification(emailDTO.getDateModification());
        email.setSignatureCreation(emailDTO.getSignatureCreation());
        email.setSiteCreation(emailDTO.getSiteCreation());
        email.setDateCreation(emailDTO.getDateCreation());
        email.setCleRole(emailDTO.getCleRole());
        email.setCleTemp(emailDTO.getCleTemp());            
    }
    
    /**
     * dto -> bo implementation for a email
     * @param emailDTO dto
     * @param email bo
     * @throws JrafDomainException 
     */
    private static void dto2BoLinkImpl(EmailDTO emailDTO, Email email) throws JrafDomainException{
    
        // property of EmailDTO
        email.setSain(emailDTO.getSain());
        email.setSgin(emailDTO.getSgin());
        email.setVersion(emailDTO.getVersion());
        email.setCodeMedium(emailDTO.getCodeMedium());
        email.setStatutMedium(emailDTO.getStatutMedium());
        email.setEmail(SicStringUtils.normalizeEmail(emailDTO.getEmail()));
        email.setDescriptifComplementaire(emailDTO.getDescriptifComplementaire());
        email.setAutorisationMailing(emailDTO.getAutorisationMailing());
        email.setSignatureModification(emailDTO.getSignatureModification());
        email.setSiteModification(emailDTO.getSiteModification());
        email.setDateModification(emailDTO.getDateModification());
        email.setSignatureCreation(emailDTO.getSignatureCreation());
        email.setSiteCreation(emailDTO.getSiteCreation());
        email.setDateCreation(emailDTO.getDateCreation());
        email.setCleRole(emailDTO.getCleRole());
        email.setCleTemp(emailDTO.getCleTemp());
        

    }

    /**
     * bo -> dto for a email
     * @param pEmail bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static EmailDTO bo2DtoLight(Email pEmail) throws JrafDomainException {
        // instanciation du DTO
        EmailDTO emailDTO = new EmailDTO();
        bo2DtoLight(pEmail, emailDTO);
        // on retourne le dto
        return emailDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param email bo
     * @param emailDTO dto
     */
    public static void bo2DtoLight(
        Email email,
        EmailDTO emailDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_n1JlcOA1Ed-ucu7RY88Scg) ENABLED START*/

        bo2DtoLightImpl(email, emailDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param email bo
     * @param emailDTO dto
     */
    private static void bo2DtoLightImpl(Email email,
        EmailDTO emailDTO){
    

        // simple properties
        emailDTO.setSain(email.getSain());
        emailDTO.setSgin(email.getSgin());
        emailDTO.setVersion(email.getVersion());
        emailDTO.setCodeMedium(email.getCodeMedium());
        emailDTO.setStatutMedium(email.getStatutMedium());
        emailDTO.setEmail(email.getEmail());
        emailDTO.setDescriptifComplementaire(email.getDescriptifComplementaire());
        emailDTO.setAutorisationMailing(email.getAutorisationMailing());
        emailDTO.setSignatureModification(email.getSignatureModification());
        emailDTO.setSiteModification(email.getSiteModification());
        emailDTO.setDateModification(email.getDateModification());
        emailDTO.setSignatureCreation(email.getSignatureCreation());
        emailDTO.setSiteCreation(email.getSiteCreation());
        emailDTO.setDateCreation(email.getDateCreation());
        emailDTO.setCleRole(email.getCleRole());
        emailDTO.setCleTemp(email.getCleTemp());
    
    }
    
    /*PROTECTED REGION ID(_n1JlcOA1Ed-ucu7RY88Scg u m - Tr) ENABLED START*/
    
    public static List<EmailDTO> bo2Dto(List<Email> listEmail) throws JrafDomainException {
    	if(listEmail != null) {
    		List<EmailDTO> listEmailDTO = new ArrayList<EmailDTO>();
    		for(Email email : listEmail) {
    			if (email.getEmail()!=null) email.setEmail(email.getEmail().trim().toLowerCase());
    			listEmailDTO.add(bo2DtoLight(email));
    		}
    		return listEmailDTO;
    	} else {
    		return null;
    	}
    }
    
    /**
     * @param listEmail
     * @return
     * @throws JrafDomainException
     */
    public static Set<EmailDTO> bo2Dto(Set<Email> listEmail) throws JrafDomainException {
    	if(listEmail != null) {
    		Set<EmailDTO> listEmailDTO = new HashSet<EmailDTO>();
    		for(Email email : listEmail) {
    			// REPIND-1288 : Code existant, lorsqu'une adresse mail est trouvée en base de données, elle est mise en minuscule et persistée
    			if (email.getEmail()!=null) email.setEmail(email.getEmail().trim().toLowerCase());
    			listEmailDTO.add(bo2DtoLight(email));
    		}
    		return listEmailDTO;
    	} else {
    		return null;
    	}
    }

    /**
     * @param listEmailDTO
     * @return
     * @throws JrafDomainException
     */
    public static Set<Email> dto2Bo(Set<EmailDTO> listEmailDTO) throws JrafDomainException {
    	if(listEmailDTO != null) {
    		Set<Email> listEmail = new HashSet<Email>();
    		for(EmailDTO emaildto : listEmailDTO) {
    			
    			String email = emaildto.getEmail();
    			
    			if (
    				email != null 				 
    			) {
    				// REPIND-1767 : Do not store Email is not compliant
    				String normalizedEmail = SicStringUtils.normalizeEmailOnlyASCII(email);
    				if  (normalizedEmail == null || "".equals(normalizedEmail)) {
    					return null;
    				}

    				emaildto.setEmail(email.trim().toLowerCase());
    			}
    			listEmail.add(dto2BoLight(emaildto));
    		}
    		return listEmail;
    	} else {
    		return null;
    	}
    }
    
    /**
     * @param String
     * @return
     * @throws JrafDomainException
     */
    public static Set<Email> dto2Bo(String emailFromProspectDTO) throws JrafDomainException {
    	if(emailFromProspectDTO != null) {
    		Set<Email> listEmail = new HashSet<Email>();
    		EmailDTO emaildto = new EmailDTO();
			if (emailFromProspectDTO!=null) emaildto.setEmail(emailFromProspectDTO.trim().toLowerCase());
			listEmail.add(dto2BoLight(emaildto));
    		return listEmail;
    	} else {
    		return null;
    	}
    }
    
    /**
     * Converts a set of Email to a set of EmailDTO.
     * 
     * @param hashSet
     * 
     * @return null if emailSet is null
     * 
     * @throws JrafDomainException if conversion fails for one Email
     */
	public static Set<EmailDTO> bo2DtoLight(Set<Email> emailSet) throws JrafDomainException {

		// Prepare result
		Set<EmailDTO> emailDTOSet = null;
		
		if (emailSet != null) {
			// If emailSet is not null, then initialize return Set
			emailDTOSet = new HashSet<EmailDTO>();
			
			// Add converted DTOs one by one to the result Set
			for (Email email : emailSet) {
				emailDTOSet.add(bo2DtoLight(email));
			}
		}

		return emailDTOSet;
	}

	
    /*PROTECTED REGION END*/
}

