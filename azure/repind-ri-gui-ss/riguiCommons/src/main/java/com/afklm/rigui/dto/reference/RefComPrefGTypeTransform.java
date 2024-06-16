package com.afklm.rigui.dto.reference;

/*PROTECTED REGION ID(_1dFyIDSaEeaR_YJoHRGtPg i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.reference.RefComPrefGType;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefComPrefGTypeTransform.java</p>
 * transformation bo <-> dto pour un(e) RefComPrefGType
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefComPrefGTypeTransform {

    /*PROTECTED REGION ID(_1dFyIDSaEeaR_YJoHRGtPg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefComPrefGTypeTransform() {
    }
    /**
     * dto -> bo for a RefComPrefGType
     * @param refComPrefGTypeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefComPrefGType dto2BoLight(RefComPrefGTypeDTO refComPrefGTypeDTO) throws JrafDomainException {
        // instanciation du BO
        RefComPrefGType refComPrefGType = new RefComPrefGType();
        dto2BoLight(refComPrefGTypeDTO, refComPrefGType);

        // on retourne le BO
        return refComPrefGType;
    }

    /**
     * dto -> bo for a refComPrefGType
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refComPrefGTypeDTO dto
     * @param refComPrefGType bo
     */
    public static void dto2BoLight(RefComPrefGTypeDTO refComPrefGTypeDTO, RefComPrefGType refComPrefGType) {
    
        /*PROTECTED REGION ID(dto2BoLight_1dFyIDSaEeaR_YJoHRGtPg) ENABLED START*/
        
        dto2BoLightImpl(refComPrefGTypeDTO,refComPrefGType);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refComPrefGType
     * @param refComPrefGTypeDTO dto
     * @param refComPrefGType bo
     */
    private static void dto2BoLightImpl(RefComPrefGTypeDTO refComPrefGTypeDTO, RefComPrefGType refComPrefGType){
    
        // property of RefComPrefGTypeDTO
        refComPrefGType.setCodeGType(refComPrefGTypeDTO.getCodeGType());
        refComPrefGType.setLibelleGType(refComPrefGTypeDTO.getLibelleGType());
        refComPrefGType.setLibelleGTypeEN(refComPrefGTypeDTO.getLibelleGTypeEN());
        refComPrefGType.setSignatureModification(refComPrefGTypeDTO.getSignatureModification());
        refComPrefGType.setSiteModification(refComPrefGTypeDTO.getSiteModification());
        refComPrefGType.setDateModification(refComPrefGTypeDTO.getDateModification());
        refComPrefGType.setSignatureCreation(refComPrefGTypeDTO.getSignatureCreation());
        refComPrefGType.setSiteCreation(refComPrefGTypeDTO.getSiteCreation());
        refComPrefGType.setDateCreation(refComPrefGTypeDTO.getDateCreation());
    
    }

    /**
     * bo -> dto for a refComPrefGType
     * @param pRefComPrefGType bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefComPrefGTypeDTO bo2DtoLight(RefComPrefGType pRefComPrefGType) throws JrafDomainException {
        // instanciation du DTO
        RefComPrefGTypeDTO refComPrefGTypeDTO = new RefComPrefGTypeDTO();
        bo2DtoLight(pRefComPrefGType, refComPrefGTypeDTO);
        // on retourne le dto
        return refComPrefGTypeDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refComPrefGType bo
     * @param refComPrefGTypeDTO dto
     */
    public static void bo2DtoLight(
        RefComPrefGType refComPrefGType,
        RefComPrefGTypeDTO refComPrefGTypeDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_1dFyIDSaEeaR_YJoHRGtPg) ENABLED START*/

        bo2DtoLightImpl(refComPrefGType, refComPrefGTypeDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refComPrefGType bo
     * @param refComPrefGTypeDTO dto
     */
    private static void bo2DtoLightImpl(RefComPrefGType refComPrefGType,
        RefComPrefGTypeDTO refComPrefGTypeDTO){
    

        // simple properties
        refComPrefGTypeDTO.setCodeGType(refComPrefGType.getCodeGType());
        refComPrefGTypeDTO.setLibelleGType(refComPrefGType.getLibelleGType());
        refComPrefGTypeDTO.setLibelleGTypeEN(refComPrefGType.getLibelleGTypeEN());
        refComPrefGTypeDTO.setSignatureModification(refComPrefGType.getSignatureModification());
        refComPrefGTypeDTO.setSiteModification(refComPrefGType.getSiteModification());
        refComPrefGTypeDTO.setDateModification(refComPrefGType.getDateModification());
        refComPrefGTypeDTO.setSignatureCreation(refComPrefGType.getSignatureCreation());
        refComPrefGTypeDTO.setSiteCreation(refComPrefGType.getSiteCreation());
        refComPrefGTypeDTO.setDateCreation(refComPrefGType.getDateCreation());
    
    }
    
    /*PROTECTED REGION ID(_1dFyIDSaEeaR_YJoHRGtPg u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

