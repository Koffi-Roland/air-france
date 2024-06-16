package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_qT8TcDSaEeaR_YJoHRGtPg i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefComPrefType;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefComPrefTypeTransform.java</p>
 * transformation bo <-> dto pour un(e) RefComPrefType
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefComPrefTypeTransform {

    /*PROTECTED REGION ID(_qT8TcDSaEeaR_YJoHRGtPg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefComPrefTypeTransform() {
    }
    /**
     * dto -> bo for a RefComPrefType
     * @param refComPrefTypeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefComPrefType dto2BoLight(RefComPrefTypeDTO refComPrefTypeDTO) throws JrafDomainException {
        // instanciation du BO
        RefComPrefType refComPrefType = new RefComPrefType();
        dto2BoLight(refComPrefTypeDTO, refComPrefType);

        // on retourne le BO
        return refComPrefType;
    }

    /**
     * dto -> bo for a refComPrefType
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refComPrefTypeDTO dto
     * @param refComPrefType bo
     */
    public static void dto2BoLight(RefComPrefTypeDTO refComPrefTypeDTO, RefComPrefType refComPrefType) {
    
        /*PROTECTED REGION ID(dto2BoLight_qT8TcDSaEeaR_YJoHRGtPg) ENABLED START*/
        
        dto2BoLightImpl(refComPrefTypeDTO,refComPrefType);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refComPrefType
     * @param refComPrefTypeDTO dto
     * @param refComPrefType bo
     */
    private static void dto2BoLightImpl(RefComPrefTypeDTO refComPrefTypeDTO, RefComPrefType refComPrefType){
    
        // property of RefComPrefTypeDTO
        refComPrefType.setCodeType(refComPrefTypeDTO.getCodeType());
        refComPrefType.setLibelleType(refComPrefTypeDTO.getLibelleType());
        refComPrefType.setLibelleTypeEN(refComPrefTypeDTO.getLibelleTypeEN());
        refComPrefType.setSignatureModification(refComPrefTypeDTO.getSignatureModification());
        refComPrefType.setSiteModification(refComPrefTypeDTO.getSiteModification());
        refComPrefType.setDateModification(refComPrefTypeDTO.getDateModification());
        refComPrefType.setSignatureCreation(refComPrefTypeDTO.getSignatureCreation());
        refComPrefType.setSiteCreation(refComPrefTypeDTO.getSiteCreation());
        refComPrefType.setDateCreation(refComPrefTypeDTO.getDateCreation());
    
    }

    /**
     * bo -> dto for a refComPrefType
     * @param pRefComPrefType bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefComPrefTypeDTO bo2DtoLight(RefComPrefType pRefComPrefType) throws JrafDomainException {
        // instanciation du DTO
        RefComPrefTypeDTO refComPrefTypeDTO = new RefComPrefTypeDTO();
        bo2DtoLight(pRefComPrefType, refComPrefTypeDTO);
        // on retourne le dto
        return refComPrefTypeDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refComPrefType bo
     * @param refComPrefTypeDTO dto
     */
    public static void bo2DtoLight(
        RefComPrefType refComPrefType,
        RefComPrefTypeDTO refComPrefTypeDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_qT8TcDSaEeaR_YJoHRGtPg) ENABLED START*/

        bo2DtoLightImpl(refComPrefType, refComPrefTypeDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refComPrefType bo
     * @param refComPrefTypeDTO dto
     */
    private static void bo2DtoLightImpl(RefComPrefType refComPrefType,
        RefComPrefTypeDTO refComPrefTypeDTO){
    

        // simple properties
        refComPrefTypeDTO.setCodeType(refComPrefType.getCodeType());
        refComPrefTypeDTO.setLibelleType(refComPrefType.getLibelleType());
        refComPrefTypeDTO.setLibelleTypeEN(refComPrefType.getLibelleTypeEN());
        refComPrefTypeDTO.setSignatureModification(refComPrefType.getSignatureModification());
        refComPrefTypeDTO.setSiteModification(refComPrefType.getSiteModification());
        refComPrefTypeDTO.setDateModification(refComPrefType.getDateModification());
        refComPrefTypeDTO.setSignatureCreation(refComPrefType.getSignatureCreation());
        refComPrefTypeDTO.setSiteCreation(refComPrefType.getSiteCreation());
        refComPrefTypeDTO.setDateCreation(refComPrefType.getDateCreation());
    
    }
    
    /*PROTECTED REGION ID(_qT8TcDSaEeaR_YJoHRGtPg u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

