package com.airfrance.repind.dto.consent;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.consent.Consent;
import com.airfrance.repind.entity.consent.ConsentData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class ConsentTransform {
    /**
     * private constructor
     */
    private ConsentTransform() {
    }
    
    /**
     * dto -> bo for a Consent
     * @param consentDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Consent dto2BoLight(ConsentDTO consentDTO) throws JrafDomainException {
        // instanciation du BO
        Consent consent = new Consent();
        dto2BoLight(consentDTO, consent);

        // on retourne le BO
        return consent;
    }

    /**
     * dto -> bo for a consent
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param consentDTO dto
     * @param consent bo
     * @throws JrafDomainException 
     */
    public static void dto2BoLight(ConsentDTO consentDTO, Consent consent) throws JrafDomainException {
    
        /*PROTECTED REGION ID(dto2BoLight_A1c1sHZ_EeauaOCU3jazIg) ENABLED START*/
        
        dto2BoLightImpl(consentDTO,consent);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a consent
     * @param consentDTO dto
     * @param consent bo
     * @throws JrafDomainException 
     */
    private static void dto2BoLightImpl(ConsentDTO consentDTO, Consent consent) throws JrafDomainException{
    
        // property of ConsentDTO
        consent.setConsentId(consentDTO.getConsentId());
        consent.setGin(consentDTO.getGin());
        consent.setType(consentDTO.getType());
        consent.setDateCreation(consentDTO.getDateCreation());
        consent.setSignatureCreation(consentDTO.getSignatureCreation());
        consent.setSiteCreation(consentDTO.getSiteCreation());
        consent.setDateModification(consentDTO.getDateModification());
        consent.setSignatureModification(consentDTO.getSignatureModification());
        consent.setSiteModification(consentDTO.getSiteModification());
        
        if (consentDTO.getConsentDataDTO() != null && !consentDTO.getConsentDataDTO().isEmpty()) {
        	Set<ConsentData> datas = new HashSet<ConsentData>();
        	for (ConsentDataDTO consentDataDto : consentDTO.getConsentDataDTO()) {
        		ConsentData data = ConsentDataTransform.dto2BoLight(consentDataDto);
        		data.setConsent(consent);
        		datas.add(data);
        	}
        	consent.setConsentData(datas);
        }
    }

    /**
     * bo -> dto for a consent
     * @param pConsent bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ConsentDTO bo2DtoLight(Consent pConsent) throws JrafDomainException {
        // instanciation du DTO
        ConsentDTO consentDTO = new ConsentDTO();
        bo2DtoLight(pConsent, consentDTO);
        // on retourne le dto
        return consentDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param consent bo
     * @param consentDTO dto
     * @throws JrafDomainException 
     */
    public static void bo2DtoLight(
        Consent consent,
        ConsentDTO consentDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(bo2DtoLight_A1c1sHZ_EeauaOCU3jazIg) ENABLED START*/

        bo2DtoLightImpl(consent, consentDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param consent bo
     * @param consentDTO dto
     * @throws JrafDomainException 
     */
    private static void bo2DtoLightImpl(Consent consent,
        ConsentDTO consentDTO) throws JrafDomainException{
    

        // simple properties
        consentDTO.setConsentId(consent.getConsentId());
        consentDTO.setGin(consent.getGin());
        consentDTO.setType(consent.getType());
        consentDTO.setDateCreation(consent.getDateCreation());
        consentDTO.setSignatureCreation(consent.getSignatureCreation());
        consentDTO.setSiteCreation(consent.getSiteCreation());
        consentDTO.setDateModification(consent.getDateModification());
        consentDTO.setSignatureModification(consent.getSignatureModification());
        consentDTO.setSiteModification(consent.getSiteModification());
        
        if (consent.getConsentData() != null && !consent.getConsentData().isEmpty()) {
        	Set<ConsentDataDTO> datas = new HashSet<ConsentDataDTO>();
        	for (ConsentData consentData : consent.getConsentData()) {
        		ConsentDataDTO data = ConsentDataTransform.bo2DtoLight(consentData);
        		data.setConsentDTO(consentDTO);
        		datas.add(data);
        	}
        	consentDTO.setConsentDataDTO(datas);
        }
    
    }
    
    /*PROTECTED REGION ID(_A1c1sHZ_EeauaOCU3jazIg u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    public static List<ConsentDTO> bo2Dto(Set<Consent> listConsent) throws JrafDomainException {
    	if (listConsent != null) {
    		List<ConsentDTO> listConsentDTO = new ArrayList<ConsentDTO>();
    		for (Consent consentBO : listConsent) {
    			ConsentDTO consentDTO = bo2DtoLight(consentBO);
    			listConsentDTO.add(consentDTO);
    		}
    		return listConsentDTO;
    	} 
    	else {
    		return null;
    	}
    }
    
    public static List<Consent> dto2Bo(List<ConsentDTO> listConsentDTO) throws JrafDomainException {
    	if (listConsentDTO != null) {
    		List<Consent> listConsent = new ArrayList<Consent>();
    		for (ConsentDTO consentDTO : listConsentDTO) {
    			Consent consent = dto2BoLight(consentDTO);
    			listConsent.add(consent);
    		}
    		return listConsent;
    	} 
    	else {
    		return null;
    	}
    }
    /*PROTECTED REGION END*/
}

