package com.afklm.rigui.dto.reference;

/*PROTECTED REGION ID(_x3sOcE4jEeS-eLH--0fARw i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.reference.RefLanguage;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefLanguageTransform.java</p>
 * transformation bo <-> dto pour un(e) RefLanguage
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefLanguageTransform {

    /*PROTECTED REGION ID(_x3sOcE4jEeS-eLH--0fARw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefLanguageTransform() {
    }
    /**
     * dto -> bo for a RefLanguage
     * @param refLanguageDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefLanguage dto2BoLight(RefLanguageDTO refLanguageDTO) throws JrafDomainException {
        // instanciation du BO
        RefLanguage refLanguage = new RefLanguage();
        dto2BoLight(refLanguageDTO, refLanguage);

        // on retourne le BO
        return refLanguage;
    }

    /**
     * dto -> bo for a refLanguage
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refLanguageDTO dto
     * @param refLanguage bo
     */
    public static void dto2BoLight(RefLanguageDTO refLanguageDTO, RefLanguage refLanguage) {
    
        /*PROTECTED REGION ID(dto2BoLight_x3sOcE4jEeS-eLH--0fARw) ENABLED START*/
        
        dto2BoLightImpl(refLanguageDTO,refLanguage);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refLanguage
     * @param refLanguageDTO dto
     * @param refLanguage bo
     */
    private static void dto2BoLightImpl(RefLanguageDTO refLanguageDTO, RefLanguage refLanguage){
    
        // property of RefLanguageDTO
        refLanguage.setLanguageCode(refLanguageDTO.getLanguageCode());
        refLanguage.setLabelFR(refLanguageDTO.getLabelFR());
        refLanguage.setLabelEN(refLanguageDTO.getLabelEN());
        refLanguage.setIataCode(refLanguageDTO.getIataCode());
    
    }

    /**
     * bo -> dto for a refLanguage
     * @param pRefLanguage bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefLanguageDTO bo2DtoLight(RefLanguage pRefLanguage) throws JrafDomainException {
        // instanciation du DTO
        RefLanguageDTO refLanguageDTO = new RefLanguageDTO();
        bo2DtoLight(pRefLanguage, refLanguageDTO);
        // on retourne le dto
        return refLanguageDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refLanguage bo
     * @param refLanguageDTO dto
     */
    public static void bo2DtoLight(
        RefLanguage refLanguage,
        RefLanguageDTO refLanguageDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_x3sOcE4jEeS-eLH--0fARw) ENABLED START*/

        bo2DtoLightImpl(refLanguage, refLanguageDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refLanguage bo
     * @param refLanguageDTO dto
     */
    private static void bo2DtoLightImpl(RefLanguage refLanguage,
        RefLanguageDTO refLanguageDTO){
    

        // simple properties
        refLanguageDTO.setLanguageCode(refLanguage.getLanguageCode());
        refLanguageDTO.setLabelFR(refLanguage.getLabelFR());
        refLanguageDTO.setLabelEN(refLanguage.getLabelEN());
        refLanguageDTO.setIataCode(refLanguage.getIataCode());
    
    }
    
    /*PROTECTED REGION ID(_x3sOcE4jEeS-eLH--0fARw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

