package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_rSe8EP2MEeaexJbSRqCy0Q i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefOwner;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefOwnerTransform.java</p>
 * transformation bo <-> dto pour un(e) RefOwner
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefOwnerTransform {

    /*PROTECTED REGION ID(_rSe8EP2MEeaexJbSRqCy0Q u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefOwnerTransform() {
    }
    /**
     * dto -> bo for a RefOwner
     * @param refOwnerDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefOwner dto2BoLight(RefOwnerDTO refOwnerDTO) throws JrafDomainException {
        // instanciation du BO
        RefOwner refOwner = new RefOwner();
        dto2BoLight(refOwnerDTO, refOwner);

        // on retourne le BO
        return refOwner;
    }

    /**
     * dto -> bo for a refOwner
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refOwnerDTO dto
     * @param refOwner bo
     */
    public static void dto2BoLight(RefOwnerDTO refOwnerDTO, RefOwner refOwner) {
    
        /*PROTECTED REGION ID(dto2BoLight_rSe8EP2MEeaexJbSRqCy0Q) ENABLED START*/
        
        dto2BoLightImpl(refOwnerDTO,refOwner);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refOwner
     * @param refOwnerDTO dto
     * @param refOwner bo
     */
    private static void dto2BoLightImpl(RefOwnerDTO refOwnerDTO, RefOwner refOwner){
    
        // property of RefOwnerDTO
        refOwner.setOwnerId(refOwnerDTO.getOwnerId());
        refOwner.setAppCode(refOwnerDTO.getAppCode());
        refOwner.setConsumerId(refOwnerDTO.getConsumerId());
        refOwner.setDateCreation(refOwnerDTO.getDateCreation());
        refOwner.setSiteCreation(refOwnerDTO.getSiteCreation());
        refOwner.setSignatureCreation(refOwnerDTO.getSignatureCreation());
        refOwner.setDateModification(refOwnerDTO.getDateModification());
        refOwner.setSiteModification(refOwnerDTO.getSiteModification());
        refOwner.setSignatureModification(refOwnerDTO.getSignatureModification());
    
    }

    /**
     * bo -> dto for a refOwner
     * @param pRefOwner bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefOwnerDTO bo2DtoLight(RefOwner pRefOwner) throws JrafDomainException {
        // instanciation du DTO
        RefOwnerDTO refOwnerDTO = new RefOwnerDTO();
        bo2DtoLight(pRefOwner, refOwnerDTO);
        // on retourne le dto
        return refOwnerDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refOwner bo
     * @param refOwnerDTO dto
     */
    public static void bo2DtoLight(
        RefOwner refOwner,
        RefOwnerDTO refOwnerDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_rSe8EP2MEeaexJbSRqCy0Q) ENABLED START*/

        bo2DtoLightImpl(refOwner, refOwnerDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refOwner bo
     * @param refOwnerDTO dto
     */
    private static void bo2DtoLightImpl(RefOwner refOwner,
        RefOwnerDTO refOwnerDTO){
    

        // simple properties
        refOwnerDTO.setOwnerId(refOwner.getOwnerId());
        refOwnerDTO.setAppCode(refOwner.getAppCode());
        refOwnerDTO.setConsumerId(refOwner.getConsumerId());
        refOwnerDTO.setDateCreation(refOwner.getDateCreation());
        refOwnerDTO.setSiteCreation(refOwner.getSiteCreation());
        refOwnerDTO.setSignatureCreation(refOwner.getSignatureCreation());
        refOwnerDTO.setDateModification(refOwner.getDateModification());
        refOwnerDTO.setSiteModification(refOwner.getSiteModification());
        refOwnerDTO.setSignatureModification(refOwner.getSignatureModification());
    
    }
    
    /*PROTECTED REGION ID(_rSe8EP2MEeaexJbSRqCy0Q u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

