package com.airfrance.repind.dto.tracking;

/*PROTECTED REGION ID(_NKV-gPI_EeSRYp0GhZuE2g i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.tracking.TriggerChange;

import java.util.ArrayList;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : TriggerChangeTransform.java</p>
 * transformation bo <-> dto pour un(e) TriggerChange
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class TriggerChangeTransform {

    /*PROTECTED REGION ID(_NKV-gPI_EeSRYp0GhZuE2g u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private TriggerChangeTransform() {
    }
    /**
     * dto -> bo for a TriggerChange
     * @param triggerChangeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static TriggerChange dto2BoLight(TriggerChangeDTO triggerChangeDTO) throws JrafDomainException {
        // instanciation du BO
        TriggerChange triggerChange = new TriggerChange();
        dto2BoLight(triggerChangeDTO, triggerChange);

        // on retourne le BO
        return triggerChange;
    }

    /**
     * dto -> bo for a triggerChange
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param triggerChangeDTO dto
     * @param triggerChange bo
     */
    public static void dto2BoLight(TriggerChangeDTO triggerChangeDTO, TriggerChange triggerChange) {
    
        /*PROTECTED REGION ID(dto2BoLight_NKV-gPI_EeSRYp0GhZuE2g) ENABLED START*/
        
        dto2BoLightImpl(triggerChangeDTO,triggerChange);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a triggerChange
     * @param triggerChangeDTO dto
     * @param triggerChange bo
     */
    private static void dto2BoLightImpl(TriggerChangeDTO triggerChangeDTO, TriggerChange triggerChange){
    
        // property of TriggerChangeDTO
        triggerChange.setId(triggerChangeDTO.getId());
        triggerChange.setGin(triggerChangeDTO.getGin());
        triggerChange.setChangeTable(triggerChangeDTO.getChangeTable());
        triggerChange.setChangeTableId(triggerChangeDTO.getChangeTableId());
        triggerChange.setChangeType(triggerChangeDTO.getChangeType());
        triggerChange.setChangeTime(triggerChangeDTO.getChangeTime());
        triggerChange.setChangeBefore(triggerChangeDTO.getChangeBefore());
        triggerChange.setChangeAfter(triggerChangeDTO.getChangeAfter());
        triggerChange.setChangeError(triggerChangeDTO.getChangeError());
        triggerChange.setSignatureCreation(triggerChangeDTO.getSignatureCreation());
        triggerChange.setSiteCreation(triggerChangeDTO.getSiteCreation());
        triggerChange.setDateCreation(triggerChangeDTO.getDateCreation());
    
    }

    /**
     * bo -> dto for a triggerChange
     * @param pTriggerChange bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static TriggerChangeDTO bo2DtoLight(TriggerChange pTriggerChange) throws JrafDomainException {
        // instanciation du DTO
        TriggerChangeDTO triggerChangeDTO = new TriggerChangeDTO();
        bo2DtoLight(pTriggerChange, triggerChangeDTO);
        // on retourne le dto
        return triggerChangeDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param triggerChange bo
     * @param triggerChangeDTO dto
     */
    public static void bo2DtoLight(
        TriggerChange triggerChange,
        TriggerChangeDTO triggerChangeDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_NKV-gPI_EeSRYp0GhZuE2g) ENABLED START*/

        bo2DtoLightImpl(triggerChange, triggerChangeDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param triggerChange bo
     * @param triggerChangeDTO dto
     */
    private static void bo2DtoLightImpl(TriggerChange triggerChange,
        TriggerChangeDTO triggerChangeDTO){
    

        // simple properties
        triggerChangeDTO.setId(triggerChange.getId());
        triggerChangeDTO.setGin(triggerChange.getGin());
        triggerChangeDTO.setChangeTable(triggerChange.getChangeTable());
        triggerChangeDTO.setChangeTableId(triggerChange.getChangeTableId());
        triggerChangeDTO.setChangeType(triggerChange.getChangeType());
        triggerChangeDTO.setChangeTime(triggerChange.getChangeTime());
        triggerChangeDTO.setChangeBefore(triggerChange.getChangeBefore());
        triggerChangeDTO.setChangeAfter(triggerChange.getChangeAfter());
        triggerChangeDTO.setChangeError(triggerChange.getChangeError());
        triggerChangeDTO.setSignatureCreation(triggerChange.getSignatureCreation());
        triggerChangeDTO.setSiteCreation(triggerChange.getSiteCreation());
        triggerChangeDTO.setDateCreation(triggerChange.getDateCreation());
    
    }
    
    /*PROTECTED REGION ID(_NKV-gPI_EeSRYp0GhZuE2g u m - Tr) ENABLED START*/
    
    /**
     * bo -> dto for a list of TriggerChange
     * @param pTriggerChange bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static List<TriggerChangeDTO> bo2DtoLight(List<TriggerChange> bos) throws JrafDomainException {       
        if (bos != null) {
            
            List<TriggerChangeDTO> dtos = new ArrayList<TriggerChangeDTO>();
            for (TriggerChange bo : bos) {
                
                dtos.add(bo2DtoLight(bo));
            }
            return dtos;
            
        } else {
            
            return null;
        }
    }
    /*PROTECTED REGION END*/
}

