package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_M5E2QHIPEeeRrZw0c1ut0g i - Tr) ENABLED START*/

// add not generated imports here
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefDelegationInfoKeyType;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefPreferenceKeyTypeTransform.java</p>
 * transformation bo <-> dto pour un(e) RefPreferenceKeyType
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefDelegationInfoKeyTypeTransform {

    /*PROTECTED REGION ID(_M5E2QHIPEeeRrZw0c1ut0g u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefDelegationInfoKeyTypeTransform() {
    }
    /**
     * dto -> bo for a RefPreferenceKeyType
     * @param refPreferenceKeyTypeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefDelegationInfoKeyType dto2BoLight(RefDelegationInfoKeyTypeDTO refDelegationInfoKeyTypeDTO) throws JrafDomainException {
        // instanciation du BO
        RefDelegationInfoKeyType refDelegationInfoKeyType = new RefDelegationInfoKeyType();
        dto2BoLight(refDelegationInfoKeyTypeDTO, refDelegationInfoKeyType);

        // on retourne le BO
        return refDelegationInfoKeyType;
    }

    /**
     * dto -> bo for a refPreferenceKeyType
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refPreferenceKeyTypeDTO dto
     * @param refPreferenceKeyType bo
     */
    public static void dto2BoLight(RefDelegationInfoKeyTypeDTO refDelegationInfoKeyTypeDTO, RefDelegationInfoKeyType refDelegationInfoKeyType) {
    
        /*PROTECTED REGION ID(dto2BoLight_M5E2QHIPEeeRrZw0c1ut0g) ENABLED START*/
        
        dto2BoLightImpl(refDelegationInfoKeyTypeDTO,refDelegationInfoKeyType);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refPreferenceKeyType
     * @param refPreferenceKeyTypeDTO dto
     * @param refPreferenceKeyType bo
     */
    private static void dto2BoLightImpl(RefDelegationInfoKeyTypeDTO refDelegationInfoKeyTypeDTO, RefDelegationInfoKeyType refDelegationInfoKeyType){
        refDelegationInfoKeyType.setKey(refDelegationInfoKeyTypeDTO.getKey());
        refDelegationInfoKeyType.setType(refDelegationInfoKeyTypeDTO.getType());
        refDelegationInfoKeyType.setMinLength(refDelegationInfoKeyTypeDTO.getMinLength());
        refDelegationInfoKeyType.setMaxLength(refDelegationInfoKeyTypeDTO.getMaxLength());
        refDelegationInfoKeyType.setDataType(refDelegationInfoKeyTypeDTO.getDataType());
        refDelegationInfoKeyType.setCondition(refDelegationInfoKeyTypeDTO.getCondition());
    }

    /**
     * bo -> dto for a refPreferenceKeyType
     * @param pRefPreferenceKeyType bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefDelegationInfoKeyTypeDTO bo2DtoLight(RefDelegationInfoKeyType pRefDelegationInfoKeyType) throws JrafDomainException {
        // instanciation du DTO
        RefDelegationInfoKeyTypeDTO refDelegationInfoKeyTypeDTO = new RefDelegationInfoKeyTypeDTO();
        bo2DtoLight(pRefDelegationInfoKeyType, refDelegationInfoKeyTypeDTO);
        // on retourne le dto
        return refDelegationInfoKeyTypeDTO;
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
        RefDelegationInfoKeyType refDelegationInfoKeyType,
        RefDelegationInfoKeyTypeDTO refDelegationInfoKeyTypeDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_M5E2QHIPEeeRrZw0c1ut0g) ENABLED START*/

        bo2DtoLightImpl(refDelegationInfoKeyType, refDelegationInfoKeyTypeDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refPreferenceKeyType bo
     * @param refPreferenceKeyTypeDTO dto
     */
    private static void bo2DtoLightImpl(RefDelegationInfoKeyType refDelegationInfoKeyType,
        RefDelegationInfoKeyTypeDTO refDelegationInfoKeyTypeDTO){
    	// simple properties
        refDelegationInfoKeyTypeDTO.setKey(refDelegationInfoKeyType.getKey());
        refDelegationInfoKeyTypeDTO.setType(refDelegationInfoKeyType.getType());
        refDelegationInfoKeyTypeDTO.setMinLength(refDelegationInfoKeyType.getMinLength());
        refDelegationInfoKeyTypeDTO.setMaxLength(refDelegationInfoKeyType.getMaxLength());
        refDelegationInfoKeyTypeDTO.setDataType(refDelegationInfoKeyType.getDataType());
        refDelegationInfoKeyTypeDTO.setCondition(refDelegationInfoKeyType.getCondition());
    
    }
    
    /*PROTECTED REGION ID(_M5E2QHIPEeeRrZw0c1ut0g u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

