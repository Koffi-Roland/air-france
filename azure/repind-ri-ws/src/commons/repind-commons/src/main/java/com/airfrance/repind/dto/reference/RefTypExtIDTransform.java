package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_q0ISUI-SEeamIv5tbprkCg i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefTypExtID;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefTypExtIDTransform.java</p>
 * transformation bo <-> dto pour un(e) RefTypExtID
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefTypExtIDTransform {

    /*PROTECTED REGION ID(_q0ISUI-SEeamIv5tbprkCg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefTypExtIDTransform() {
    }
    /**
     * dto -> bo for a RefTypExtID
     * @param refTypExtIDDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefTypExtID dto2BoLight(RefTypExtIDDTO refTypExtIDDTO) throws JrafDomainException {
        // instanciation du BO
        RefTypExtID refTypExtID = new RefTypExtID();
        dto2BoLight(refTypExtIDDTO, refTypExtID);

        // on retourne le BO
        return refTypExtID;
    }

    /**
     * dto -> bo for a refTypExtID
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refTypExtIDDTO dto
     * @param refTypExtID bo
     */
    public static void dto2BoLight(RefTypExtIDDTO refTypExtIDDTO, RefTypExtID refTypExtID) {
    
        /*PROTECTED REGION ID(dto2BoLight_q0ISUI-SEeamIv5tbprkCg) ENABLED START*/
        
        dto2BoLightImpl(refTypExtIDDTO,refTypExtID);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refTypExtID
     * @param refTypExtIDDTO dto
     * @param refTypExtID bo
     */
    private static void dto2BoLightImpl(RefTypExtIDDTO refTypExtIDDTO, RefTypExtID refTypExtID){
    
        // property of RefTypExtIDDTO
        refTypExtID.setExtID(refTypExtIDDTO.getExtID());
        refTypExtID.setLibelle(refTypExtIDDTO.getLibelle());
        refTypExtID.setOption(refTypExtIDDTO.getOption());
    
    }

    /**
     * bo -> dto for a refTypExtID
     * @param pRefTypExtID bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefTypExtIDDTO bo2DtoLight(RefTypExtID pRefTypExtID) throws JrafDomainException {
        // instanciation du DTO
        RefTypExtIDDTO refTypExtIDDTO = new RefTypExtIDDTO();
        bo2DtoLight(pRefTypExtID, refTypExtIDDTO);
        // on retourne le dto
        return refTypExtIDDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refTypExtID bo
     * @param refTypExtIDDTO dto
     */
    public static void bo2DtoLight(
        RefTypExtID refTypExtID,
        RefTypExtIDDTO refTypExtIDDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_q0ISUI-SEeamIv5tbprkCg) ENABLED START*/

        bo2DtoLightImpl(refTypExtID, refTypExtIDDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refTypExtID bo
     * @param refTypExtIDDTO dto
     */
    private static void bo2DtoLightImpl(RefTypExtID refTypExtID,
        RefTypExtIDDTO refTypExtIDDTO){
    

        // simple properties
        refTypExtIDDTO.setExtID(refTypExtID.getExtID());
        refTypExtIDDTO.setLibelle(refTypExtID.getLibelle());
        refTypExtIDDTO.setOption(refTypExtID.getOption());
    
    }
    
    /*PROTECTED REGION ID(_q0ISUI-SEeamIv5tbprkCg u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

