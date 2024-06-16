package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_X3pCEAThEee_Y7_I5loOBA i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefTypeEvent;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefTypeEventTransform.java</p>
 * transformation bo <-> dto pour un(e) RefTypeEvent
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefTypeEventTransform {

    /*PROTECTED REGION ID(_X3pCEAThEee_Y7_I5loOBA u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefTypeEventTransform() {
    }
    /**
     * dto -> bo for a RefTypeEvent
     * @param refTypeEventDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefTypeEvent dto2BoLight(RefTypeEventDTO refTypeEventDTO) throws JrafDomainException {
        // instanciation du BO
        RefTypeEvent refTypeEvent = new RefTypeEvent();
        dto2BoLight(refTypeEventDTO, refTypeEvent);

        // on retourne le BO
        return refTypeEvent;
    }

    /**
     * dto -> bo for a refTypeEvent
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refTypeEventDTO dto
     * @param refTypeEvent bo
     */
    public static void dto2BoLight(RefTypeEventDTO refTypeEventDTO, RefTypeEvent refTypeEvent) {
    
        /*PROTECTED REGION ID(dto2BoLight_X3pCEAThEee_Y7_I5loOBA) ENABLED START*/
        
        dto2BoLightImpl(refTypeEventDTO,refTypeEvent);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refTypeEvent
     * @param refTypeEventDTO dto
     * @param refTypeEvent bo
     */
    private static void dto2BoLightImpl(RefTypeEventDTO refTypeEventDTO, RefTypeEvent refTypeEvent){
    
        // property of RefTypeEventDTO
        refTypeEvent.setCode(refTypeEventDTO.getCode());
        refTypeEvent.setLabelFR(refTypeEventDTO.getLabelFR());
        refTypeEvent.setLabelEN(refTypeEventDTO.getLabelEN());
    
    }

    /**
     * bo -> dto for a refTypeEvent
     * @param pRefTypeEvent bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefTypeEventDTO bo2DtoLight(RefTypeEvent pRefTypeEvent) throws JrafDomainException {
        // instanciation du DTO
        RefTypeEventDTO refTypeEventDTO = new RefTypeEventDTO();
        bo2DtoLight(pRefTypeEvent, refTypeEventDTO);
        // on retourne le dto
        return refTypeEventDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refTypeEvent bo
     * @param refTypeEventDTO dto
     */
    public static void bo2DtoLight(
        RefTypeEvent refTypeEvent,
        RefTypeEventDTO refTypeEventDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_X3pCEAThEee_Y7_I5loOBA) ENABLED START*/

        bo2DtoLightImpl(refTypeEvent, refTypeEventDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refTypeEvent bo
     * @param refTypeEventDTO dto
     */
    private static void bo2DtoLightImpl(RefTypeEvent refTypeEvent,
        RefTypeEventDTO refTypeEventDTO){
    

        // simple properties
        refTypeEventDTO.setCode(refTypeEvent.getCode());
        refTypeEventDTO.setLabelFR(refTypeEvent.getLabelFR());
        refTypeEventDTO.setLabelEN(refTypeEvent.getLabelEN());
    
    }
    
    /*PROTECTED REGION ID(_X3pCEAThEee_Y7_I5loOBA u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

