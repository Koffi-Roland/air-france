package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_iy51wAThEee_Y7_I5loOBA i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefDetailsKey;

import java.util.ArrayList;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefDetailsKeyTransform.java</p>
 * transformation bo <-> dto pour un(e) RefDetailsKey
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefDetailsKeyTransform {

    /*PROTECTED REGION ID(_iy51wAThEee_Y7_I5loOBA u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefDetailsKeyTransform() {
    }
    /**
     * dto -> bo for a RefDetailsKey
     * @param refDetailsKeyDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefDetailsKey dto2BoLight(RefDetailsKeyDTO refDetailsKeyDTO) throws JrafDomainException {
        // instanciation du BO
        RefDetailsKey refDetailsKey = new RefDetailsKey();
        dto2BoLight(refDetailsKeyDTO, refDetailsKey);

        // on retourne le BO
        return refDetailsKey;
    }

    /**
     * dto -> bo for a refDetailsKey
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refDetailsKeyDTO dto
     * @param refDetailsKey bo
     */
    public static void dto2BoLight(RefDetailsKeyDTO refDetailsKeyDTO, RefDetailsKey refDetailsKey) {
    
        /*PROTECTED REGION ID(dto2BoLight_iy51wAThEee_Y7_I5loOBA) ENABLED START*/
        
        dto2BoLightImpl(refDetailsKeyDTO,refDetailsKey);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refDetailsKey
     * @param refDetailsKeyDTO dto
     * @param refDetailsKey bo
     */
    private static void dto2BoLightImpl(RefDetailsKeyDTO refDetailsKeyDTO, RefDetailsKey refDetailsKey){
    
        // property of RefDetailsKeyDTO
        refDetailsKey.setRefDetailsKeyId(refDetailsKeyDTO.getRefDetailsKeyId());
        refDetailsKey.setCode(refDetailsKeyDTO.getCode());
        refDetailsKey.setLabelFR(refDetailsKeyDTO.getLabelFR());
        refDetailsKey.setLabelEN(refDetailsKeyDTO.getLabelEN());
        refDetailsKey.setDetailsKeyID(refDetailsKeyDTO.getDetailsKeyID());
    
    }

    /**
     * bo -> dto for a refDetailsKey
     * @param pRefDetailsKey bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefDetailsKeyDTO bo2DtoLight(RefDetailsKey pRefDetailsKey) throws JrafDomainException {
        // instanciation du DTO
        RefDetailsKeyDTO refDetailsKeyDTO = new RefDetailsKeyDTO();
        bo2DtoLight(pRefDetailsKey, refDetailsKeyDTO);
        // on retourne le dto
        return refDetailsKeyDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refDetailsKey bo
     * @param refDetailsKeyDTO dto
     */
    public static void bo2DtoLight(
        RefDetailsKey refDetailsKey,
        RefDetailsKeyDTO refDetailsKeyDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_iy51wAThEee_Y7_I5loOBA) ENABLED START*/

        bo2DtoLightImpl(refDetailsKey, refDetailsKeyDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refDetailsKey bo
     * @param refDetailsKeyDTO dto
     */
    private static void bo2DtoLightImpl(RefDetailsKey refDetailsKey,
        RefDetailsKeyDTO refDetailsKeyDTO){
    

        // simple properties
        refDetailsKeyDTO.setRefDetailsKeyId(refDetailsKey.getRefDetailsKeyId());
        refDetailsKeyDTO.setCode(refDetailsKey.getCode());
        refDetailsKeyDTO.setLabelFR(refDetailsKey.getLabelFR());
        refDetailsKeyDTO.setLabelEN(refDetailsKey.getLabelEN());
        refDetailsKeyDTO.setDetailsKeyID(refDetailsKey.getDetailsKeyID());
    
    }
    
    /*PROTECTED REGION ID(_iy51wAThEee_Y7_I5loOBA u m - Tr) ENABLED START*/
    /**
     * bo -> dto for a list of RefDetailsKey
     * @param pRefDetailsKey bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static List<RefDetailsKeyDTO> bo2DtoLight(List<RefDetailsKey> bos) throws JrafDomainException {       
        if (bos != null) {
            
            List<RefDetailsKeyDTO> dtos = new ArrayList<RefDetailsKeyDTO>();
            for (RefDetailsKey bo : bos) {
                
                dtos.add(bo2DtoLight(bo));
            }
            return dtos;
            
        } else {
            
            return null;
        }
    }
    /*PROTECTED REGION END*/
}

