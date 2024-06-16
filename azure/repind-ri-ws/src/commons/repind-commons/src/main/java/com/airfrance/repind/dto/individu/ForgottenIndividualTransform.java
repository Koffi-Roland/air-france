package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_zw_nENOAEee-fsR_-mfQcQ i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.individu.ForgottenIndividual;

/*PROTECTED REGION END*/

/**
 * <p>Title : ForgottenIndividualTransform.java</p>
 * transformation bo <-> dto pour un(e) ForgottenIndividual
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ForgottenIndividualTransform {

    /*PROTECTED REGION ID(_zw_nENOAEee-fsR_-mfQcQ u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ForgottenIndividualTransform() {
    }
    /**
     * dto -> bo for a ForgottenIndividual
     * @param forgottenIndividualDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static ForgottenIndividual dto2BoLight(ForgottenIndividualDTO forgottenIndividualDTO) throws JrafDomainException {
        // instanciation du BO
        ForgottenIndividual forgottenIndividual = new ForgottenIndividual();
        dto2BoLight(forgottenIndividualDTO, forgottenIndividual);

        // on retourne le BO
        return forgottenIndividual;
    }

    /**
     * dto -> bo for a forgottenIndividual
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param forgottenIndividualDTO dto
     * @param forgottenIndividual bo
     */
    public static void dto2BoLight(ForgottenIndividualDTO forgottenIndividualDTO, ForgottenIndividual forgottenIndividual) {
    
        /*PROTECTED REGION ID(dto2BoLight_zw_nENOAEee-fsR_-mfQcQ) ENABLED START*/
        
        dto2BoLightImpl(forgottenIndividualDTO,forgottenIndividual);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a forgottenIndividual
     * @param forgottenIndividualDTO dto
     * @param forgottenIndividual bo
     */
    private static void dto2BoLightImpl(ForgottenIndividualDTO forgottenIndividualDTO, ForgottenIndividual forgottenIndividual){
    
        // property of ForgottenIndividualDTO
        forgottenIndividual.setForgottenIndividualId(forgottenIndividualDTO.getForgottenIndividualId());
        forgottenIndividual.setIdentifier(forgottenIndividualDTO.getIdentifier());
        forgottenIndividual.setIdentifierType(forgottenIndividualDTO.getIdentifierType());
        forgottenIndividual.setContext(forgottenIndividualDTO.getContext());
        forgottenIndividual.setDeletionDate(forgottenIndividualDTO.getDeletionDate());
        forgottenIndividual.setSignature(forgottenIndividualDTO.getSignature());
        forgottenIndividual.setApplicationCode(forgottenIndividualDTO.getApplicationCode());
        forgottenIndividual.setSite(forgottenIndividualDTO.getSite());
        forgottenIndividual.setModificationDate(forgottenIndividualDTO.getModificationDate());
    
    }

    /**
     * bo -> dto for a forgottenIndividual
     * @param pForgottenIndividual bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ForgottenIndividualDTO bo2DtoLight(ForgottenIndividual pForgottenIndividual) throws JrafDomainException {
        // instanciation du DTO
        ForgottenIndividualDTO forgottenIndividualDTO = new ForgottenIndividualDTO();
        bo2DtoLight(pForgottenIndividual, forgottenIndividualDTO);
        // on retourne le dto
        return forgottenIndividualDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param forgottenIndividual bo
     * @param forgottenIndividualDTO dto
     */
    public static void bo2DtoLight(
        ForgottenIndividual forgottenIndividual,
        ForgottenIndividualDTO forgottenIndividualDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_zw_nENOAEee-fsR_-mfQcQ) ENABLED START*/

        bo2DtoLightImpl(forgottenIndividual, forgottenIndividualDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param forgottenIndividual bo
     * @param forgottenIndividualDTO dto
     */
    private static void bo2DtoLightImpl(ForgottenIndividual forgottenIndividual,
        ForgottenIndividualDTO forgottenIndividualDTO){
    

        // simple properties
        forgottenIndividualDTO.setForgottenIndividualId(forgottenIndividual.getForgottenIndividualId());
        forgottenIndividualDTO.setIdentifier(forgottenIndividual.getIdentifier());
        forgottenIndividualDTO.setIdentifierType(forgottenIndividual.getIdentifierType());
        forgottenIndividualDTO.setContext(forgottenIndividual.getContext());
        forgottenIndividualDTO.setDeletionDate(forgottenIndividual.getDeletionDate());
        forgottenIndividualDTO.setSignature(forgottenIndividual.getSignature());
        forgottenIndividualDTO.setApplicationCode(forgottenIndividual.getApplicationCode());
        forgottenIndividualDTO.setSite(forgottenIndividual.getSite());
        forgottenIndividualDTO.setModificationDate(forgottenIndividual.getModificationDate());
    
    }
    
    /*PROTECTED REGION ID(_zw_nENOAEee-fsR_-mfQcQ u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

