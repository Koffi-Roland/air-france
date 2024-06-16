package com.airfrance.repind.dto.identifier;

/*PROTECTED REGION ID(_4wcyIN2EEeav9oUtVVZbTQ i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.identifier.InternalIdentifier;

/*PROTECTED REGION END*/

/**
 * <p>Title : InternalIdentifierTransform.java</p>
 * transformation bo <-> dto pour un(e) InternalIdentifier
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class InternalIdentifierTransform {

    /*PROTECTED REGION ID(_4wcyIN2EEeav9oUtVVZbTQ u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private InternalIdentifierTransform() {
    }
    /**
     * dto -> bo for a InternalIdentifier
     * @param internalIdentifierDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static InternalIdentifier dto2BoLight(InternalIdentifierDTO internalIdentifierDTO) throws JrafDomainException {
        // instanciation du BO
        InternalIdentifier internalIdentifier = new InternalIdentifier();
        dto2BoLight(internalIdentifierDTO, internalIdentifier);

        // on retourne le BO
        return internalIdentifier;
    }

    /**
     * dto -> bo for a internalIdentifier
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param internalIdentifierDTO dto
     * @param internalIdentifier bo
     */
    public static void dto2BoLight(InternalIdentifierDTO internalIdentifierDTO, InternalIdentifier internalIdentifier) {
    
        /*PROTECTED REGION ID(dto2BoLight_4wcyIN2EEeav9oUtVVZbTQ) ENABLED START*/
        
        dto2BoLightImpl(internalIdentifierDTO,internalIdentifier);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a internalIdentifier
     * @param internalIdentifierDTO dto
     * @param internalIdentifier bo
     */
    private static void dto2BoLightImpl(InternalIdentifierDTO internalIdentifierDTO, InternalIdentifier internalIdentifier){
    
        // property of InternalIdentifierDTO
        internalIdentifier.setIdentifierId(internalIdentifierDTO.getIdentifierId());
        internalIdentifier.setIdentifier(internalIdentifierDTO.getIdentifier());
        internalIdentifier.setType(internalIdentifierDTO.getType());
        internalIdentifier.setLastSeenDate(internalIdentifierDTO.getLastSeenDate());
        internalIdentifier.setCreationDate(internalIdentifierDTO.getCreationDate());
        internalIdentifier.setCreationSignature(internalIdentifierDTO.getCreationSignature());
        internalIdentifier.setCreationSite(internalIdentifierDTO.getCreationSite());
        internalIdentifier.setModificationDate(internalIdentifierDTO.getModificationDate());
        internalIdentifier.setModificationSignature(internalIdentifierDTO.getModificationSignature());
        internalIdentifier.setModificationSite(internalIdentifierDTO.getModificationSite());
    
    }

    /**
     * bo -> dto for a internalIdentifier
     * @param pInternalIdentifier bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static InternalIdentifierDTO bo2DtoLight(InternalIdentifier pInternalIdentifier) throws JrafDomainException {
        // instanciation du DTO
        InternalIdentifierDTO internalIdentifierDTO = new InternalIdentifierDTO();
        bo2DtoLight(pInternalIdentifier, internalIdentifierDTO);
        // on retourne le dto
        return internalIdentifierDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param internalIdentifier bo
     * @param internalIdentifierDTO dto
     */
    public static void bo2DtoLight(
        InternalIdentifier internalIdentifier,
        InternalIdentifierDTO internalIdentifierDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_4wcyIN2EEeav9oUtVVZbTQ) ENABLED START*/

        bo2DtoLightImpl(internalIdentifier, internalIdentifierDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param internalIdentifier bo
     * @param internalIdentifierDTO dto
     */
    private static void bo2DtoLightImpl(InternalIdentifier internalIdentifier,
        InternalIdentifierDTO internalIdentifierDTO){
    

        // simple properties
        internalIdentifierDTO.setIdentifierId(internalIdentifier.getIdentifierId());
        internalIdentifierDTO.setIdentifier(internalIdentifier.getIdentifier());
        internalIdentifierDTO.setType(internalIdentifier.getType());
        internalIdentifierDTO.setLastSeenDate(internalIdentifier.getLastSeenDate());
        internalIdentifierDTO.setCreationDate(internalIdentifier.getCreationDate());
        internalIdentifierDTO.setCreationSignature(internalIdentifier.getCreationSignature());
        internalIdentifierDTO.setCreationSite(internalIdentifier.getCreationSite());
        internalIdentifierDTO.setModificationDate(internalIdentifier.getModificationDate());
        internalIdentifierDTO.setModificationSignature(internalIdentifier.getModificationSignature());
        internalIdentifierDTO.setModificationSite(internalIdentifier.getModificationSite());
    
    }
    
    /*PROTECTED REGION ID(_4wcyIN2EEeav9oUtVVZbTQ u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

