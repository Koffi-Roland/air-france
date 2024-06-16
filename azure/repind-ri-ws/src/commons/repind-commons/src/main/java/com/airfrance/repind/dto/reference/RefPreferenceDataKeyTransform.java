package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_BdyjYHz_EeeM7eE6bvH4Tw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefPreferenceDataKey;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefPreferenceDataKeyTransform.java</p>
 * transformation bo <-> dto pour un(e) RefPreferenceDataKey
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefPreferenceDataKeyTransform {

    /*PROTECTED REGION ID(_BdyjYHz_EeeM7eE6bvH4Tw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefPreferenceDataKeyTransform() {
    }
    /**
     * dto -> bo for a RefPreferenceDataKey
     * @param refPreferenceDataKeyDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefPreferenceDataKey dto2BoLight(RefPreferenceDataKeyDTO refPreferenceDataKeyDTO) throws JrafDomainException {
        // instanciation du BO
        RefPreferenceDataKey refPreferenceDataKey = new RefPreferenceDataKey();
        dto2BoLight(refPreferenceDataKeyDTO, refPreferenceDataKey);

        // on retourne le BO
        return refPreferenceDataKey;
    }

    /**
     * dto -> bo for a refPreferenceDataKey
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refPreferenceDataKeyDTO dto
     * @param refPreferenceDataKey bo
     */
    public static void dto2BoLight(RefPreferenceDataKeyDTO refPreferenceDataKeyDTO, RefPreferenceDataKey refPreferenceDataKey) {
    
        /*PROTECTED REGION ID(dto2BoLight_BdyjYHz_EeeM7eE6bvH4Tw) ENABLED START*/
        
        dto2BoLightImpl(refPreferenceDataKeyDTO,refPreferenceDataKey);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refPreferenceDataKey
     * @param refPreferenceDataKeyDTO dto
     * @param refPreferenceDataKey bo
     */
    private static void dto2BoLightImpl(RefPreferenceDataKeyDTO refPreferenceDataKeyDTO, RefPreferenceDataKey refPreferenceDataKey){
    
        // property of RefPreferenceDataKeyDTO
        refPreferenceDataKey.setCode(refPreferenceDataKeyDTO.getCode());
        refPreferenceDataKey.setLibelleFr(refPreferenceDataKeyDTO.getLibelleFr());
        refPreferenceDataKey.setLibelleEn(refPreferenceDataKeyDTO.getLibelleEn());
        refPreferenceDataKey.setNormalizedKey(refPreferenceDataKeyDTO.getNormalizedKey());
    
    }

    /**
     * bo -> dto for a refPreferenceDataKey
     * @param pRefPreferenceDataKey bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefPreferenceDataKeyDTO bo2DtoLight(RefPreferenceDataKey pRefPreferenceDataKey) throws JrafDomainException {
        // instanciation du DTO
        RefPreferenceDataKeyDTO refPreferenceDataKeyDTO = new RefPreferenceDataKeyDTO();
        bo2DtoLight(pRefPreferenceDataKey, refPreferenceDataKeyDTO);
        // on retourne le dto
        return refPreferenceDataKeyDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refPreferenceDataKey bo
     * @param refPreferenceDataKeyDTO dto
     */
    public static void bo2DtoLight(
        RefPreferenceDataKey refPreferenceDataKey,
        RefPreferenceDataKeyDTO refPreferenceDataKeyDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_BdyjYHz_EeeM7eE6bvH4Tw) ENABLED START*/

        bo2DtoLightImpl(refPreferenceDataKey, refPreferenceDataKeyDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refPreferenceDataKey bo
     * @param refPreferenceDataKeyDTO dto
     */
    private static void bo2DtoLightImpl(RefPreferenceDataKey refPreferenceDataKey,
        RefPreferenceDataKeyDTO refPreferenceDataKeyDTO){
    

        // simple properties
        refPreferenceDataKeyDTO.setCode(refPreferenceDataKey.getCode());
        refPreferenceDataKeyDTO.setLibelleFr(refPreferenceDataKey.getLibelleFr());
        refPreferenceDataKeyDTO.setLibelleEn(refPreferenceDataKey.getLibelleEn());
        refPreferenceDataKeyDTO.setNormalizedKey(refPreferenceDataKey.getNormalizedKey());
    
    }
    
    /*PROTECTED REGION ID(_BdyjYHz_EeeM7eE6bvH4Tw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

