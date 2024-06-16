package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_xl2ywGoMEearcuraYqCMoA i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefAlert;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefAlertTransform.java</p>
 * transformation bo <-> dto pour un(e) RefAlert
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefAlertTransform {

    /*PROTECTED REGION ID(_xl2ywGoMEearcuraYqCMoA u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefAlertTransform() {
    }
    /**
     * dto -> bo for a RefAlert
     * @param refAlertDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefAlert dto2BoLight(RefAlertDTO refAlertDTO) throws JrafDomainException {
        // instanciation du BO
        RefAlert refAlert = new RefAlert();
        dto2BoLight(refAlertDTO, refAlert);

        // on retourne le BO
        return refAlert;
    }

    /**
     * dto -> bo for a refAlert
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refAlertDTO dto
     * @param refAlert bo
     */
    public static void dto2BoLight(RefAlertDTO refAlertDTO, RefAlert refAlert) {
    
        /*PROTECTED REGION ID(dto2BoLight_xl2ywGoMEearcuraYqCMoA) ENABLED START*/
        
        dto2BoLightImpl(refAlertDTO,refAlert);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refAlert
     * @param refAlertDTO dto
     * @param refAlert bo
     */
    private static void dto2BoLightImpl(RefAlertDTO refAlertDTO, RefAlert refAlert){
    
        // property of RefAlertDTO
        refAlert.setKey(refAlertDTO.getKey());
        refAlert.setMandatory(refAlertDTO.getMandatory());
        refAlert.setType(refAlertDTO.getType());
        refAlert.setUsage(refAlertDTO.getUsage());
        refAlert.setValue(refAlertDTO.getValue());
    
    }

    /**
     * bo -> dto for a refAlert
     * @param pRefAlert bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefAlertDTO bo2DtoLight(RefAlert pRefAlert) throws JrafDomainException {
        // instanciation du DTO
        RefAlertDTO refAlertDTO = new RefAlertDTO();
        bo2DtoLight(pRefAlert, refAlertDTO);
        // on retourne le dto
        return refAlertDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refAlert bo
     * @param refAlertDTO dto
     */
    public static void bo2DtoLight(
        RefAlert refAlert,
        RefAlertDTO refAlertDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_xl2ywGoMEearcuraYqCMoA) ENABLED START*/

        bo2DtoLightImpl(refAlert, refAlertDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refAlert bo
     * @param refAlertDTO dto
     */
    private static void bo2DtoLightImpl(RefAlert refAlert,
        RefAlertDTO refAlertDTO){
    

        // simple properties
        refAlertDTO.setKey(refAlert.getKey());
        refAlertDTO.setMandatory(refAlert.getMandatory());
        refAlertDTO.setType(refAlert.getType());
        refAlertDTO.setUsage(refAlert.getUsage());
        refAlertDTO.setValue(refAlert.getValue());
    
    }
    
    /*PROTECTED REGION ID(_xl2ywGoMEearcuraYqCMoA u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

