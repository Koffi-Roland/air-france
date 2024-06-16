package com.afklm.rigui.dto.reference;

/*PROTECTED REGION ID(_glmJUHBQEeeA-oB3G9fmBA i - Tr) ENABLED START*/

// add not generated imports here
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.reference.RefDelegationInfoType;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefDelegationInfoTypeTransform.java</p>
 * transformation bo <-> dto pour un(e) RefDelegationInfoType
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefDelegationInfoTypeTransform {

    /*PROTECTED REGION ID(_glmJUHBQEeeA-oB3G9fmBA u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefDelegationInfoTypeTransform() {
    }
    /**
     * dto -> bo for a RefPreferenceType
     * @param refPreferenceTypeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefDelegationInfoType dto2BoLight(RefDelegationInfoTypeDTO refDelegationInfoTypeDTO) throws JrafDomainException {
        // instanciation du BO
        RefDelegationInfoType refDelegationInfoType = new RefDelegationInfoType();
        dto2BoLight(refDelegationInfoTypeDTO, refDelegationInfoType);

        // on retourne le BO
        return refDelegationInfoType;
    }

    /**
     * dto -> bo for a refPreferenceType
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refPreferenceTypeDTO dto
     * @param refPreferenceType bo
     */
    public static void dto2BoLight(RefDelegationInfoTypeDTO refDelegationInfoTypeDTO, RefDelegationInfoType refDelegationInfoType) {
    
        /*PROTECTED REGION ID(dto2BoLight_glmJUHBQEeeA-oB3G9fmBA) ENABLED START*/
        
        dto2BoLightImpl(refDelegationInfoTypeDTO,refDelegationInfoType);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refPreferenceType
     * @param refPreferenceTypeDTO dto
     * @param refPreferenceType bo
     */
    private static void dto2BoLightImpl(RefDelegationInfoTypeDTO refDelegationInfoTypeDTO, RefDelegationInfoType refDelegationInfoType){
    
        // property of RefPreferenceTypeDTO
        refDelegationInfoType.setCode(refDelegationInfoTypeDTO.getCode());
        refDelegationInfoType.setLibelleFr(refDelegationInfoTypeDTO.getLibelleFR());
        refDelegationInfoType.setLibelleEn(refDelegationInfoTypeDTO.getLibelleEN());
    
    }

    /**
     * bo -> dto for a refPreferenceType
     * @param pRefPreferenceType bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefDelegationInfoTypeDTO bo2DtoLight(RefDelegationInfoType pRefDelegationInfoType) throws JrafDomainException {
        // instanciation du DTO
        RefDelegationInfoTypeDTO refDelegationInfoTypeDTO = new RefDelegationInfoTypeDTO();
        bo2DtoLight(pRefDelegationInfoType, refDelegationInfoTypeDTO);
        // on retourne le dto
        return refDelegationInfoTypeDTO;
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
        RefDelegationInfoType refDelegationInfoType,
        RefDelegationInfoTypeDTO refDelegationInfoTypeDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_glmJUHBQEeeA-oB3G9fmBA) ENABLED START*/

        bo2DtoLightImpl(refDelegationInfoType, refDelegationInfoTypeDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refPreferenceType bo
     * @param refPreferenceTypeDTO dto
     */
    private static void bo2DtoLightImpl(RefDelegationInfoType refDelegationInfoType,
        RefDelegationInfoTypeDTO refDelegationInfoTypeDTO){

        // simple properties
        refDelegationInfoTypeDTO.setCode(refDelegationInfoType.getCode());
        refDelegationInfoTypeDTO.setLibelleFR(refDelegationInfoType.getLibelleFr());
        refDelegationInfoTypeDTO.setLibelleEN(refDelegationInfoType.getLibelleEn());
    
    }
    
    /*PROTECTED REGION ID(_glmJUHBQEeeA-oB3G9fmBA u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

