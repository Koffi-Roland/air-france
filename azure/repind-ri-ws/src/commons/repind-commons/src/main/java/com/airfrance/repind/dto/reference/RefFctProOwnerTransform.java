package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_ryYjkDyXEeeO_ZXPvPFEyw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefFctProOwner;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefFctProOwnerTransform.java</p>
 * transformation bo <-> dto pour un(e) RefFctProOwner
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefFctProOwnerTransform {

    /*PROTECTED REGION ID(_ryYjkDyXEeeO_ZXPvPFEyw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefFctProOwnerTransform() {
    }
    /**
     * dto -> bo for a RefFctProOwner
     * @param refFctProOwnerDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefFctProOwner dto2BoLight(RefFctProOwnerDTO refFctProOwnerDTO) throws JrafDomainException {
        // instanciation du BO
        RefFctProOwner refFctProOwner = new RefFctProOwner();
        dto2BoLight(refFctProOwnerDTO, refFctProOwner);

        // on retourne le BO
        return refFctProOwner;
    }

    /**
     * dto -> bo for a refFctProOwner
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refFctProOwnerDTO dto
     * @param refFctProOwner bo
     */
    public static void dto2BoLight(RefFctProOwnerDTO refFctProOwnerDTO, RefFctProOwner refFctProOwner) {
    
        /*PROTECTED REGION ID(dto2BoLight_ryYjkDyXEeeO_ZXPvPFEyw) ENABLED START*/
        
        dto2BoLightImpl(refFctProOwnerDTO,refFctProOwner);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refFctProOwner
     * @param refFctProOwnerDTO dto
     * @param refFctProOwner bo
     */
    private static void dto2BoLightImpl(RefFctProOwnerDTO refFctProOwnerDTO, RefFctProOwner refFctProOwner){
    
        // property of RefFctProOwnerDTO
        refFctProOwner.setId(refFctProOwnerDTO.getId());
        refFctProOwner.setDateCreation(refFctProOwnerDTO.getDateCreation());
        refFctProOwner.setSiteCreation(refFctProOwnerDTO.getSiteCreation());
        refFctProOwner.setSignatureCreation(refFctProOwnerDTO.getSignatureCreation());
        refFctProOwner.setDateModification(refFctProOwnerDTO.getDateModification());
        refFctProOwner.setSiteModification(refFctProOwnerDTO.getSiteModification());
        refFctProOwner.setSignatureModification(refFctProOwnerDTO.getSignatureModification());
    
    }

    /**
     * bo -> dto for a refFctProOwner
     * @param pRefFctProOwner bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefFctProOwnerDTO bo2DtoLight(RefFctProOwner pRefFctProOwner) throws JrafDomainException {
        // instanciation du DTO
        RefFctProOwnerDTO refFctProOwnerDTO = new RefFctProOwnerDTO();
        bo2DtoLight(pRefFctProOwner, refFctProOwnerDTO);
        // on retourne le dto
        return refFctProOwnerDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refFctProOwner bo
     * @param refFctProOwnerDTO dto
     */
    public static void bo2DtoLight(
        RefFctProOwner refFctProOwner,
        RefFctProOwnerDTO refFctProOwnerDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_ryYjkDyXEeeO_ZXPvPFEyw) ENABLED START*/

        bo2DtoLightImpl(refFctProOwner, refFctProOwnerDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refFctProOwner bo
     * @param refFctProOwnerDTO dto
     */
    private static void bo2DtoLightImpl(RefFctProOwner refFctProOwner,
        RefFctProOwnerDTO refFctProOwnerDTO){
    

        // simple properties
        refFctProOwnerDTO.setId(refFctProOwner.getId());

        refFctProOwnerDTO.setDateCreation(refFctProOwner.getDateCreation());
        refFctProOwnerDTO.setSiteCreation(refFctProOwner.getSiteCreation());
        refFctProOwnerDTO.setSignatureCreation(refFctProOwner.getSignatureCreation());
        refFctProOwnerDTO.setDateModification(refFctProOwner.getDateModification());
        refFctProOwnerDTO.setSiteModification(refFctProOwner.getSiteModification());
        refFctProOwnerDTO.setSignatureModification(refFctProOwner.getSignatureModification());
    
    }
    
    /*PROTECTED REGION ID(_ryYjkDyXEeeO_ZXPvPFEyw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

