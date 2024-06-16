package com.airfrance.repind.dto.tracking;

/*PROTECTED REGION ID(_rjayYJYCEea6T-Kx4YCVeA i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.tracking.TriggerChangeAutMailing;

/*PROTECTED REGION END*/

/**
 * <p>Title : TriggerChangeAutMailingTransform.java</p>
 * transformation bo <-> dto pour un(e) TriggerChangeAutMailing
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class TriggerChangeAutMailingTransform {

    /*PROTECTED REGION ID(_rjayYJYCEea6T-Kx4YCVeA u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private TriggerChangeAutMailingTransform() {
    }
    /**
     * dto -> bo for a TriggerChangeAutMailing
     * @param triggerChangeAutMailingDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static TriggerChangeAutMailing dto2BoLight(TriggerChangeAutMailingDTO triggerChangeAutMailingDTO) throws JrafDomainException {
        // instanciation du BO
        TriggerChangeAutMailing triggerChangeAutMailing = new TriggerChangeAutMailing();
        dto2BoLight(triggerChangeAutMailingDTO, triggerChangeAutMailing);

        // on retourne le BO
        return triggerChangeAutMailing;
    }

    /**
     * dto -> bo for a triggerChangeAutMailing
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param triggerChangeAutMailingDTO dto
     * @param triggerChangeAutMailing bo
     */
    public static void dto2BoLight(TriggerChangeAutMailingDTO triggerChangeAutMailingDTO, TriggerChangeAutMailing triggerChangeAutMailing) {
    
        /*PROTECTED REGION ID(dto2BoLight_rjayYJYCEea6T-Kx4YCVeA) ENABLED START*/
        
        dto2BoLightImpl(triggerChangeAutMailingDTO,triggerChangeAutMailing);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a triggerChangeAutMailing
     * @param triggerChangeAutMailingDTO dto
     * @param triggerChangeAutMailing bo
     */
    private static void dto2BoLightImpl(TriggerChangeAutMailingDTO triggerChangeAutMailingDTO, TriggerChangeAutMailing triggerChangeAutMailing){
    
        // property of TriggerChangeAutMailingDTO
        triggerChangeAutMailing.setId(triggerChangeAutMailingDTO.getId());
        triggerChangeAutMailing.setGin(triggerChangeAutMailingDTO.getGin());
        triggerChangeAutMailing.setGinPM(triggerChangeAutMailingDTO.getGinPM());
        triggerChangeAutMailing.setEmailId(triggerChangeAutMailingDTO.getEmailId());
        triggerChangeAutMailing.setChangeType(triggerChangeAutMailingDTO.getChangeType());
        triggerChangeAutMailing.setChangeError(triggerChangeAutMailingDTO.getChangeError());
        triggerChangeAutMailing.setSignatureModification(triggerChangeAutMailingDTO.getSignatureModification());
        triggerChangeAutMailing.setSiteModification(triggerChangeAutMailingDTO.getSiteModification());
        triggerChangeAutMailing.setDateChange(triggerChangeAutMailingDTO.getDateChange());
        triggerChangeAutMailing.setChangeStatus(triggerChangeAutMailingDTO.getChangeStatus());
        triggerChangeAutMailing.setOldValue(triggerChangeAutMailingDTO.getOldValue());
        triggerChangeAutMailing.setNewValue(triggerChangeAutMailingDTO.getNewValue());
    
    }

    /**
     * bo -> dto for a triggerChangeAutMailing
     * @param pTriggerChangeAutMailing bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static TriggerChangeAutMailingDTO bo2DtoLight(TriggerChangeAutMailing pTriggerChangeAutMailing) throws JrafDomainException {
        // instanciation du DTO
        TriggerChangeAutMailingDTO triggerChangeAutMailingDTO = new TriggerChangeAutMailingDTO();
        bo2DtoLight(pTriggerChangeAutMailing, triggerChangeAutMailingDTO);
        // on retourne le dto
        return triggerChangeAutMailingDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param triggerChangeAutMailing bo
     * @param triggerChangeAutMailingDTO dto
     */
    public static void bo2DtoLight(
        TriggerChangeAutMailing triggerChangeAutMailing,
        TriggerChangeAutMailingDTO triggerChangeAutMailingDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_rjayYJYCEea6T-Kx4YCVeA) ENABLED START*/

        bo2DtoLightImpl(triggerChangeAutMailing, triggerChangeAutMailingDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param triggerChangeAutMailing bo
     * @param triggerChangeAutMailingDTO dto
     */
    private static void bo2DtoLightImpl(TriggerChangeAutMailing triggerChangeAutMailing,
        TriggerChangeAutMailingDTO triggerChangeAutMailingDTO){
    

        // simple properties
        triggerChangeAutMailingDTO.setId(triggerChangeAutMailing.getId());
        triggerChangeAutMailingDTO.setGin(triggerChangeAutMailing.getGin());
        triggerChangeAutMailingDTO.setGinPM(triggerChangeAutMailing.getGinPM());
        triggerChangeAutMailingDTO.setEmailId(triggerChangeAutMailing.getEmailId());
        triggerChangeAutMailingDTO.setChangeType(triggerChangeAutMailing.getChangeType());
        triggerChangeAutMailingDTO.setChangeError(triggerChangeAutMailing.getChangeError());
        triggerChangeAutMailingDTO.setSignatureModification(triggerChangeAutMailing.getSignatureModification());
        triggerChangeAutMailingDTO.setSiteModification(triggerChangeAutMailing.getSiteModification());
        triggerChangeAutMailingDTO.setDateChange(triggerChangeAutMailing.getDateChange());
        triggerChangeAutMailingDTO.setChangeStatus(triggerChangeAutMailing.getChangeStatus());
        triggerChangeAutMailingDTO.setOldValue(triggerChangeAutMailing.getOldValue());
        triggerChangeAutMailingDTO.setNewValue(triggerChangeAutMailing.getNewValue());
    
    }
    
    /*PROTECTED REGION ID(_rjayYJYCEea6T-Kx4YCVeA u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

