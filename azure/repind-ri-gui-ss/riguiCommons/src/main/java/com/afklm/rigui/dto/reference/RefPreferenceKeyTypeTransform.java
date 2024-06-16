package com.afklm.rigui.dto.reference;

/*PROTECTED REGION ID(_M5E2QHIPEeeRrZw0c1ut0g i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.reference.RefPreferenceKeyType;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefPreferenceKeyTypeTransform.java</p>
 * transformation bo <-> dto pour un(e) RefPreferenceKeyType
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefPreferenceKeyTypeTransform {

    /*PROTECTED REGION ID(_M5E2QHIPEeeRrZw0c1ut0g u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefPreferenceKeyTypeTransform() {
    }
    /**
     * dto -> bo for a RefPreferenceKeyType
     * @param refPreferenceKeyTypeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefPreferenceKeyType dto2BoLight(RefPreferenceKeyTypeDTO refPreferenceKeyTypeDTO) throws JrafDomainException {
        // instanciation du BO
        RefPreferenceKeyType refPreferenceKeyType = new RefPreferenceKeyType();
        dto2BoLight(refPreferenceKeyTypeDTO, refPreferenceKeyType);

        // on retourne le BO
        return refPreferenceKeyType;
    }

    /**
     * dto -> bo for a refPreferenceKeyType
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refPreferenceKeyTypeDTO dto
     * @param refPreferenceKeyType bo
     */
    public static void dto2BoLight(RefPreferenceKeyTypeDTO refPreferenceKeyTypeDTO, RefPreferenceKeyType refPreferenceKeyType) {
    
        /*PROTECTED REGION ID(dto2BoLight_M5E2QHIPEeeRrZw0c1ut0g) ENABLED START*/
        
        dto2BoLightImpl(refPreferenceKeyTypeDTO,refPreferenceKeyType);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refPreferenceKeyType
     * @param refPreferenceKeyTypeDTO dto
     * @param refPreferenceKeyType bo
     */
    private static void dto2BoLightImpl(RefPreferenceKeyTypeDTO refPreferenceKeyTypeDTO, RefPreferenceKeyType refPreferenceKeyType){
    
        // property of RefPreferenceKeyTypeDTO
        refPreferenceKeyType.setRefId(refPreferenceKeyTypeDTO.getRefId());
        refPreferenceKeyType.setKey(refPreferenceKeyTypeDTO.getKey());
        refPreferenceKeyType.setType(refPreferenceKeyTypeDTO.getType());
        refPreferenceKeyType.setMinLength(refPreferenceKeyTypeDTO.getMinLength());
        refPreferenceKeyType.setMaxLength(refPreferenceKeyTypeDTO.getMaxLength());
        refPreferenceKeyType.setDataType(refPreferenceKeyTypeDTO.getDataType());
        refPreferenceKeyType.setCondition(refPreferenceKeyTypeDTO.getCondition());
    
    }

    /**
     * bo -> dto for a refPreferenceKeyType
     * @param pRefPreferenceKeyType bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefPreferenceKeyTypeDTO bo2DtoLight(RefPreferenceKeyType pRefPreferenceKeyType) throws JrafDomainException {
        // instanciation du DTO
        RefPreferenceKeyTypeDTO refPreferenceKeyTypeDTO = new RefPreferenceKeyTypeDTO();
        bo2DtoLight(pRefPreferenceKeyType, refPreferenceKeyTypeDTO);
        // on retourne le dto
        return refPreferenceKeyTypeDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refPreferenceKeyType bo
     * @param refPreferenceKeyTypeDTO dto
     */
    public static void bo2DtoLight(
        RefPreferenceKeyType refPreferenceKeyType,
        RefPreferenceKeyTypeDTO refPreferenceKeyTypeDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_M5E2QHIPEeeRrZw0c1ut0g) ENABLED START*/

        bo2DtoLightImpl(refPreferenceKeyType, refPreferenceKeyTypeDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refPreferenceKeyType bo
     * @param refPreferenceKeyTypeDTO dto
     */
    private static void bo2DtoLightImpl(RefPreferenceKeyType refPreferenceKeyType,
        RefPreferenceKeyTypeDTO refPreferenceKeyTypeDTO){
    

        // simple properties
        refPreferenceKeyTypeDTO.setRefId(refPreferenceKeyType.getRefId());
        refPreferenceKeyTypeDTO.setKey(refPreferenceKeyType.getKey());
        refPreferenceKeyTypeDTO.setType(refPreferenceKeyType.getType());
        refPreferenceKeyTypeDTO.setMinLength(refPreferenceKeyType.getMinLength());
        refPreferenceKeyTypeDTO.setMaxLength(refPreferenceKeyType.getMaxLength());
        refPreferenceKeyTypeDTO.setDataType(refPreferenceKeyType.getDataType());
        refPreferenceKeyTypeDTO.setCondition(refPreferenceKeyType.getCondition());
    
    }
    
    /*PROTECTED REGION ID(_M5E2QHIPEeeRrZw0c1ut0g u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

