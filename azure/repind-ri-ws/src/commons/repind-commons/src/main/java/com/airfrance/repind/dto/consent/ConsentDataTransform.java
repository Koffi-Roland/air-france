package com.airfrance.repind.dto.consent;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.consent.ConsentData;

public final class ConsentDataTransform {

    /**
     * private constructor
     */
    private ConsentDataTransform() {
    }
    /**
     * dto -> bo for a ConsentData
     * @param consentDataDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static ConsentData dto2BoLight(ConsentDataDTO consentDataDTO) throws JrafDomainException {
        // instanciation du BO
        ConsentData consentData = new ConsentData();
        dto2BoLight(consentDataDTO, consentData);

        // on retourne le BO
        return consentData;
    }

    /**
     * dto -> bo for a consentData
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param consentDataDTO dto
     * @param consentData bo
     * @throws JrafDomainException 
     */
    public static void dto2BoLight(ConsentDataDTO consentDataDTO, ConsentData consentData) throws JrafDomainException {
    
        dto2BoLightImpl(consentDataDTO,consentData);
        
    }
    
    /**
     * dto -> bo implementation for a consentData
     * @param consentDataDTO dto
     * @param consentData bo
     * @throws JrafDomainException 
     */
    private static void dto2BoLightImpl(ConsentDataDTO consentDataDTO, ConsentData consentData) throws JrafDomainException{
    
        consentData.setConsentDataId(consentDataDTO.getConsentDataId());
        consentData.setDateConsent(consentDataDTO.getDateConsent());
        consentData.setType(consentDataDTO.getType());
        consentData.setIsConsent(consentDataDTO.getIsConsent());
        consentData.setDateCreation(consentDataDTO.getDateCreation());
        consentData.setSignatureCreation(consentDataDTO.getSignatureCreation());
        consentData.setSiteCreation(consentDataDTO.getSiteCreation());
        consentData.setDateModification(consentDataDTO.getDateModification());
        consentData.setSignatureModification(consentDataDTO.getSignatureModification());
        consentData.setSiteModification(consentDataDTO.getSiteModification());
    
    }

    /**
     * bo -> dto for a consentData
     * @param pConsentData bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ConsentDataDTO bo2DtoLight(ConsentData pConsentData) throws JrafDomainException {
        ConsentDataDTO consentDataDTO = new ConsentDataDTO();
        bo2DtoLight(pConsentData, consentDataDTO);
        return consentDataDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param consentData bo
     * @param consentDataDTO dto
     */
    public static void bo2DtoLight(
        ConsentData consentData,
        ConsentDataDTO consentDataDTO) {

        bo2DtoLightImpl(consentData, consentDataDTO);

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param consentData bo
     * @param consentDataDTO dto
     */
    private static void bo2DtoLightImpl(ConsentData consentData,
        ConsentDataDTO consentDataDTO){
    

        // simple properties
        consentDataDTO.setConsentDataId(consentData.getConsentDataId());
        consentDataDTO.setDateConsent(consentData.getDateConsent());
        consentDataDTO.setType(consentData.getType());
        consentDataDTO.setIsConsent(consentData.getIsConsent());
        consentDataDTO.setDateCreation(consentData.getDateCreation());
        consentDataDTO.setSignatureCreation(consentData.getSignatureCreation());
        consentDataDTO.setSiteCreation(consentData.getSiteCreation());
        consentDataDTO.setDateModification(consentData.getDateModification());
        consentDataDTO.setSignatureModification(consentData.getSignatureModification());
        consentDataDTO.setSiteModification(consentData.getSiteModification());
    
    }
    
}

