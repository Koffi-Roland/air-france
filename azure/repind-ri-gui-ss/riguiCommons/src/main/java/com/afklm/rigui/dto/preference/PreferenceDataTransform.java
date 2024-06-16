package com.afklm.rigui.dto.preference;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.preference.PreferenceData;

/*PROTECTED REGION END*/

/**
 * <p>Title : PreferenceDataTransform.java</p>
 * transformation bo <-> dto pour un(e) PreferenceData
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class PreferenceDataTransform {

    /*PROTECTED REGION ID(_4JtogE9iEeevnICwxQHWbw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private PreferenceDataTransform() {
    }
    /**
     * dto -> bo for a PreferenceData
     * @param preferenceDataDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static PreferenceData dto2BoLight(PreferenceDataDTO preferenceDataDTO) throws JrafDomainException {
        // instanciation du BO
        PreferenceData preferenceData = new PreferenceData();
        dto2BoLight(preferenceDataDTO, preferenceData);

        // on retourne le BO
        return preferenceData;
    }

    /**
     * dto -> bo for a preferenceData
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param preferenceDataDTO dto
     * @param preferenceData bo
     * @throws JrafDomainException 
     */
    public static void dto2BoLight(PreferenceDataDTO preferenceDataDTO, PreferenceData preferenceData) throws JrafDomainException {
    
        /*PROTECTED REGION ID(dto2BoLight_4JtogE9iEeevnICwxQHWbw) ENABLED START*/
        
        dto2BoLightImpl(preferenceDataDTO,preferenceData);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a preferenceData
     * @param preferenceDataDTO dto
     * @param preferenceData bo
     * @throws JrafDomainException 
     */
    private static void dto2BoLightImpl(PreferenceDataDTO preferenceDataDTO, PreferenceData preferenceData) throws JrafDomainException{
    
        // property of PreferenceDataDTO
        preferenceData.setPreferenceDataId(preferenceDataDTO.getPreferenceDataId());
        preferenceData.setKey(preferenceDataDTO.getKey());
        preferenceData.setValue(preferenceDataDTO.getValue());
        
        // REPIND-1734 : Add Signature for PREFERENCE DATA
        preferenceData.setSignatureCreation(preferenceDataDTO.getSignatureCreation());
        preferenceData.setSiteCreation(preferenceDataDTO.getSiteCreation());
        preferenceData.setDateCreation(preferenceDataDTO.getDateCreation());
        preferenceData.setSignatureModification(preferenceDataDTO.getSignatureModification());
        preferenceData.setSiteModification(preferenceDataDTO.getSiteModification());
        preferenceData.setDateModification(preferenceDataDTO.getDateModification());
    }

    /**
     * bo -> dto for a preferenceData
     * @param pPreferenceData bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static PreferenceDataDTO bo2DtoLight(PreferenceData pPreferenceData) throws JrafDomainException {
        // instanciation du DTO
        PreferenceDataDTO preferenceDataDTO = new PreferenceDataDTO();
        bo2DtoLight(pPreferenceData, preferenceDataDTO);
        // on retourne le dto
        return preferenceDataDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param preferenceData bo
     * @param preferenceDataDTO dto
     */
    public static void bo2DtoLight(
        PreferenceData preferenceData,
        PreferenceDataDTO preferenceDataDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_4JtogE9iEeevnICwxQHWbw) ENABLED START*/

        bo2DtoLightImpl(preferenceData, preferenceDataDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param preferenceData bo
     * @param preferenceDataDTO dto
     */
    private static void bo2DtoLightImpl(PreferenceData preferenceData,
        PreferenceDataDTO preferenceDataDTO){
    

        // simple properties
        preferenceDataDTO.setPreferenceDataId(preferenceData.getPreferenceDataId());
        preferenceDataDTO.setKey(preferenceData.getKey());
        preferenceDataDTO.setValue(preferenceData.getValue());
    
    }
    
    /*PROTECTED REGION ID(_4JtogE9iEeevnICwxQHWbw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

