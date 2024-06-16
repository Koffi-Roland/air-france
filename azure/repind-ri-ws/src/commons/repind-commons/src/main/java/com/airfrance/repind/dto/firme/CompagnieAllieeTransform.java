package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_08z57WkzEeGhB9497mGnHw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.firme.CompagnieAlliee;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : CompagnieAllieeTransform.java</p>
 * transformation bo <-> dto pour un(e) CompagnieAlliee
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class CompagnieAllieeTransform {

    /*PROTECTED REGION ID(_08z57WkzEeGhB9497mGnHw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private CompagnieAllieeTransform() {
    }
    /**
     * dto -> bo for a CompagnieAlliee
     * @param compagnieAllieeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static CompagnieAlliee dto2BoLight(CompagnieAllieeDTO compagnieAllieeDTO) throws JrafDomainException {
        // instanciation du BO
        CompagnieAlliee compagnieAlliee = new CompagnieAlliee();
        dto2BoLight(compagnieAllieeDTO, compagnieAlliee);

        // on retourne le BO
        return compagnieAlliee;
    }

    /**
     * dto -> bo for a compagnieAlliee
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param compagnieAllieeDTO dto
     * @param compagnieAlliee bo
     */
    public static void dto2BoLight(CompagnieAllieeDTO compagnieAllieeDTO, CompagnieAlliee compagnieAlliee) {
    
        /*PROTECTED REGION ID(dto2BoLight_08z57WkzEeGhB9497mGnHw) ENABLED START*/
        
        dto2BoLightImpl(compagnieAllieeDTO,compagnieAlliee);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a compagnieAlliee
     * @param compagnieAllieeDTO dto
     * @param compagnieAlliee bo
     */
    private static void dto2BoLightImpl(CompagnieAllieeDTO compagnieAllieeDTO, CompagnieAlliee compagnieAlliee){
    
        // property of CompagnieAllieeDTO
        compagnieAlliee.setCle(compagnieAllieeDTO.getCle());
        compagnieAlliee.setCodeCie(compagnieAllieeDTO.getCodeCie());
    
    }

    /**
     * bo -> dto for a compagnieAlliee
     * @param pCompagnieAlliee bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static CompagnieAllieeDTO bo2DtoLight(CompagnieAlliee pCompagnieAlliee) throws JrafDomainException {
        // instanciation du DTO
        CompagnieAllieeDTO compagnieAllieeDTO = new CompagnieAllieeDTO();
        bo2DtoLight(pCompagnieAlliee, compagnieAllieeDTO);
        // on retourne le dto
        return compagnieAllieeDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param compagnieAlliee bo
     * @param compagnieAllieeDTO dto
     */
    public static void bo2DtoLight(
        CompagnieAlliee compagnieAlliee,
        CompagnieAllieeDTO compagnieAllieeDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_08z57WkzEeGhB9497mGnHw) ENABLED START*/

        bo2DtoLightImpl(compagnieAlliee, compagnieAllieeDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param compagnieAlliee bo
     * @param compagnieAllieeDTO dto
     */
    private static void bo2DtoLightImpl(CompagnieAlliee compagnieAlliee,
        CompagnieAllieeDTO compagnieAllieeDTO){
    

        // simple properties
        compagnieAllieeDTO.setCle(compagnieAlliee.getCle());
        compagnieAllieeDTO.setCodeCie(compagnieAlliee.getCodeCie());
    
    }
    
    /*PROTECTED REGION ID(_08z57WkzEeGhB9497mGnHw u m - Tr) ENABLED START*/
    /**
     * @param bos
     * @return dtos
     * @throws JrafDomainException
     */
    public static List<CompagnieAllieeDTO> bo2DtoLight(Set<CompagnieAlliee> bos) throws JrafDomainException {
        
        if (bos != null) {
            
            List<CompagnieAllieeDTO> dtos = new ArrayList<CompagnieAllieeDTO>();
            for (CompagnieAlliee bo : bos) {
                
                dtos.add(bo2DtoLight(bo));
            }
            return dtos;
            
        } else {
            
            return null;
        }
    }
    /*PROTECTED REGION END*/
}

