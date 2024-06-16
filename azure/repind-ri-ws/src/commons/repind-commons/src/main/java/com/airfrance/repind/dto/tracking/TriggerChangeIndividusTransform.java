package com.airfrance.repind.dto.tracking;

/*PROTECTED REGION ID(_h5O18DlFEeaXzKXbfHYeHw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.tracking.TriggerChangeIndividus;

import java.util.ArrayList;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : TriggerChangeIndividusTransform.java</p>
 * transformation bo <-> dto pour un(e) TriggerChangeIndividus
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class TriggerChangeIndividusTransform {

    /*PROTECTED REGION ID(_h5O18DlFEeaXzKXbfHYeHw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private TriggerChangeIndividusTransform() {
    }
    /**
     * dto -> bo for a TriggerChangeIndividus
     * @param triggerChangeIndividusDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static TriggerChangeIndividus dto2BoLight(TriggerChangeIndividusDTO triggerChangeIndividusDTO) throws JrafDomainException {
        // instanciation du BO
        TriggerChangeIndividus triggerChangeIndividus = new TriggerChangeIndividus();
        dto2BoLight(triggerChangeIndividusDTO, triggerChangeIndividus);

        // on retourne le BO
        return triggerChangeIndividus;
    }

    /**
     * dto -> bo for a triggerChangeIndividus
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param triggerChangeIndividusDTO dto
     * @param triggerChangeIndividus bo
     */
    public static void dto2BoLight(TriggerChangeIndividusDTO triggerChangeIndividusDTO, TriggerChangeIndividus triggerChangeIndividus) {
    
        /*PROTECTED REGION ID(dto2BoLight_h5O18DlFEeaXzKXbfHYeHw) ENABLED START*/
        
        dto2BoLightImpl(triggerChangeIndividusDTO,triggerChangeIndividus);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a triggerChangeIndividus
     * @param triggerChangeIndividusDTO dto
     * @param triggerChangeIndividus bo
     */
    private static void dto2BoLightImpl(TriggerChangeIndividusDTO triggerChangeIndividusDTO, TriggerChangeIndividus triggerChangeIndividus){
    
        // property of TriggerChangeIndividusDTO
        triggerChangeIndividus.setId(triggerChangeIndividusDTO.getId());
        triggerChangeIndividus.setGin(triggerChangeIndividusDTO.getGin());
        triggerChangeIndividus.setChangeTable(triggerChangeIndividusDTO.getChangeTable());
        triggerChangeIndividus.setChangeTableId(triggerChangeIndividusDTO.getChangeTableId());
        triggerChangeIndividus.setChangeType(triggerChangeIndividusDTO.getChangeType());
        triggerChangeIndividus.setChangeTime(triggerChangeIndividusDTO.getChangeTime());
        triggerChangeIndividus.setChangeError(triggerChangeIndividusDTO.getChangeError());
        triggerChangeIndividus.setSignatureModification(triggerChangeIndividusDTO.getSignatureModification());
        triggerChangeIndividus.setSiteModification(triggerChangeIndividusDTO.getSiteModification());
        triggerChangeIndividus.setDateChange(triggerChangeIndividusDTO.getDateChange());
        triggerChangeIndividus.setChangeStatus(triggerChangeIndividusDTO.getChangeStatus());
        triggerChangeIndividus.setChangeBefore(triggerChangeIndividusDTO.getChangeBefore());
        triggerChangeIndividus.setChangeAfter(triggerChangeIndividusDTO.getChangeAfter());
    
    }

    /**
     * bo -> dto for a triggerChangeIndividus
     * @param pTriggerChangeIndividus bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static TriggerChangeIndividusDTO bo2DtoLight(TriggerChangeIndividus pTriggerChangeIndividus) throws JrafDomainException {
        // instanciation du DTO
        TriggerChangeIndividusDTO triggerChangeIndividusDTO = new TriggerChangeIndividusDTO();
        bo2DtoLight(pTriggerChangeIndividus, triggerChangeIndividusDTO);
        // on retourne le dto
        return triggerChangeIndividusDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param triggerChangeIndividus bo
     * @param triggerChangeIndividusDTO dto
     */
    public static void bo2DtoLight(
        TriggerChangeIndividus triggerChangeIndividus,
        TriggerChangeIndividusDTO triggerChangeIndividusDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_h5O18DlFEeaXzKXbfHYeHw) ENABLED START*/

        bo2DtoLightImpl(triggerChangeIndividus, triggerChangeIndividusDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param triggerChangeIndividus bo
     * @param triggerChangeIndividusDTO dto
     */
    private static void bo2DtoLightImpl(TriggerChangeIndividus triggerChangeIndividus,
        TriggerChangeIndividusDTO triggerChangeIndividusDTO){
    

        // simple properties
        triggerChangeIndividusDTO.setId(triggerChangeIndividus.getId());
        triggerChangeIndividusDTO.setGin(triggerChangeIndividus.getGin());
        triggerChangeIndividusDTO.setChangeTable(triggerChangeIndividus.getChangeTable());
        triggerChangeIndividusDTO.setChangeTableId(triggerChangeIndividus.getChangeTableId());
        triggerChangeIndividusDTO.setChangeType(triggerChangeIndividus.getChangeType());
        triggerChangeIndividusDTO.setChangeTime(triggerChangeIndividus.getChangeTime());
        triggerChangeIndividusDTO.setChangeError(triggerChangeIndividus.getChangeError());
        triggerChangeIndividusDTO.setSignatureModification(triggerChangeIndividus.getSignatureModification());
        triggerChangeIndividusDTO.setSiteModification(triggerChangeIndividus.getSiteModification());
        triggerChangeIndividusDTO.setDateChange(triggerChangeIndividus.getDateChange());
        triggerChangeIndividusDTO.setChangeStatus(triggerChangeIndividus.getChangeStatus());
        triggerChangeIndividusDTO.setChangeBefore(triggerChangeIndividus.getChangeBefore());
        triggerChangeIndividusDTO.setChangeAfter(triggerChangeIndividus.getChangeAfter());
    
    }
    
    /*PROTECTED REGION ID(_h5O18DlFEeaXzKXbfHYeHw u m - Tr) ENABLED START*/
    /**
     * bo -> dto for a list of TriggerChange
     * @param pTriggerChange bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static List<TriggerChangeIndividusDTO> bo2DtoLight(List<TriggerChangeIndividus> bos) throws JrafDomainException {       
        if (bos != null) {
            
            List<TriggerChangeIndividusDTO> dtos = new ArrayList<TriggerChangeIndividusDTO>();
            for (TriggerChangeIndividus bo : bos) {
                
                dtos.add(bo2DtoLight(bo));
            }
            return dtos;
            
        } else {
            
            return null;
        }
    }
    /*PROTECTED REGION END*/
}

