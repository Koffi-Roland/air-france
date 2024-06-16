package com.afklm.rigui.dto.reference;

/*PROTECTED REGION ID(_glmJUHBQEeeA-oB3G9fmBA i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.reference.RefPreferenceType;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefPreferenceTypeTransform.java</p>
 * transformation bo <-> dto pour un(e) RefPreferenceType
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefPreferenceTypeTransform {

    /*PROTECTED REGION ID(_glmJUHBQEeeA-oB3G9fmBA u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefPreferenceTypeTransform() {
    }
    /**
     * dto -> bo for a RefPreferenceType
     * @param refPreferenceTypeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefPreferenceType dto2BoLight(RefPreferenceTypeDTO refPreferenceTypeDTO) throws JrafDomainException {
        // instanciation du BO
        RefPreferenceType refPreferenceType = new RefPreferenceType();
        dto2BoLight(refPreferenceTypeDTO, refPreferenceType);

        // on retourne le BO
        return refPreferenceType;
    }

    /**
     * dto -> bo for a refPreferenceType
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refPreferenceTypeDTO dto
     * @param refPreferenceType bo
     */
    public static void dto2BoLight(RefPreferenceTypeDTO refPreferenceTypeDTO, RefPreferenceType refPreferenceType) {
    
        /*PROTECTED REGION ID(dto2BoLight_glmJUHBQEeeA-oB3G9fmBA) ENABLED START*/
        
        dto2BoLightImpl(refPreferenceTypeDTO,refPreferenceType);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refPreferenceType
     * @param refPreferenceTypeDTO dto
     * @param refPreferenceType bo
     */
    private static void dto2BoLightImpl(RefPreferenceTypeDTO refPreferenceTypeDTO, RefPreferenceType refPreferenceType){
    
        // property of RefPreferenceTypeDTO
        refPreferenceType.setCode(refPreferenceTypeDTO.getCode());
        refPreferenceType.setLibelleFR(refPreferenceTypeDTO.getLibelleFR());
        refPreferenceType.setLibelleEN(refPreferenceTypeDTO.getLibelleEN());
    
    }

    /**
     * bo -> dto for a refPreferenceType
     * @param pRefPreferenceType bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefPreferenceTypeDTO bo2DtoLight(RefPreferenceType pRefPreferenceType) throws JrafDomainException {
        // instanciation du DTO
        RefPreferenceTypeDTO refPreferenceTypeDTO = new RefPreferenceTypeDTO();
        bo2DtoLight(pRefPreferenceType, refPreferenceTypeDTO);
        // on retourne le dto
        return refPreferenceTypeDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refPreferenceType bo
     * @param refPreferenceTypeDTO dto
     */
    public static void bo2DtoLight(
        RefPreferenceType refPreferenceType,
        RefPreferenceTypeDTO refPreferenceTypeDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_glmJUHBQEeeA-oB3G9fmBA) ENABLED START*/

        bo2DtoLightImpl(refPreferenceType, refPreferenceTypeDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refPreferenceType bo
     * @param refPreferenceTypeDTO dto
     */
    private static void bo2DtoLightImpl(RefPreferenceType refPreferenceType,
        RefPreferenceTypeDTO refPreferenceTypeDTO){
    

        // simple properties
        refPreferenceTypeDTO.setCode(refPreferenceType.getCode());
        refPreferenceTypeDTO.setLibelleFR(refPreferenceType.getLibelleFR());
        refPreferenceTypeDTO.setLibelleEN(refPreferenceType.getLibelleEN());
    
    }
    
    /*PROTECTED REGION ID(_glmJUHBQEeeA-oB3G9fmBA u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

