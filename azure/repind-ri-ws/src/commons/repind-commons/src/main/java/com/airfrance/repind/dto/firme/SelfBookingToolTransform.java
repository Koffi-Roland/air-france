package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_3DfAEGQrEeSRQ7C-gEfj8g i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.firme.SelfBookingTool;

/*PROTECTED REGION END*/

/**
 * <p>Title : SelfBookingToolTransform.java</p>
 * transformation bo <-> dto pour un(e) SelfBookingTool
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class SelfBookingToolTransform {

    /*PROTECTED REGION ID(_3DfAEGQrEeSRQ7C-gEfj8g u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private SelfBookingToolTransform() {
    }
    /**
     * dto -> bo for a SelfBookingTool
     * @param selfBookingToolDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static SelfBookingTool dto2BoLight(SelfBookingToolDTO selfBookingToolDTO) throws JrafDomainException {
        // instanciation du BO
        SelfBookingTool selfBookingTool = new SelfBookingTool();
        dto2BoLight(selfBookingToolDTO, selfBookingTool);

        // on retourne le BO
        return selfBookingTool;
    }

    /**
     * dto -> bo for a selfBookingTool
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param selfBookingToolDTO dto
     * @param selfBookingTool bo
     */
    public static void dto2BoLight(SelfBookingToolDTO selfBookingToolDTO, SelfBookingTool selfBookingTool) {
    
        /*PROTECTED REGION ID(dto2BoLight_3DfAEGQrEeSRQ7C-gEfj8g) ENABLED START*/
        
        dto2BoLightImpl(selfBookingToolDTO,selfBookingTool);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a selfBookingTool
     * @param selfBookingToolDTO dto
     * @param selfBookingTool bo
     */
    private static void dto2BoLightImpl(SelfBookingToolDTO selfBookingToolDTO, SelfBookingTool selfBookingTool){
    
        // property of SelfBookingToolDTO
        selfBookingTool.setGin(selfBookingToolDTO.getGin());
        selfBookingTool.setPortalGdsCode(selfBookingToolDTO.getPortalGdsCode());
        selfBookingTool.setSbtCode(selfBookingToolDTO.getSbtCode());
    
    }

    /**
     * bo -> dto for a selfBookingTool
     * @param pSelfBookingTool bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static SelfBookingToolDTO bo2DtoLight(SelfBookingTool pSelfBookingTool) throws JrafDomainException {
        // instanciation du DTO
        SelfBookingToolDTO selfBookingToolDTO = new SelfBookingToolDTO();
        bo2DtoLight(pSelfBookingTool, selfBookingToolDTO);
        // on retourne le dto
        return selfBookingToolDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param selfBookingTool bo
     * @param selfBookingToolDTO dto
     */
    public static void bo2DtoLight(
        SelfBookingTool selfBookingTool,
        SelfBookingToolDTO selfBookingToolDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_3DfAEGQrEeSRQ7C-gEfj8g) ENABLED START*/

        bo2DtoLightImpl(selfBookingTool, selfBookingToolDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param selfBookingTool bo
     * @param selfBookingToolDTO dto
     */
    private static void bo2DtoLightImpl(SelfBookingTool selfBookingTool,
        SelfBookingToolDTO selfBookingToolDTO){
    

        // simple properties
        selfBookingToolDTO.setGin(selfBookingTool.getGin());
        selfBookingToolDTO.setPortalGdsCode(selfBookingTool.getPortalGdsCode());
        selfBookingToolDTO.setSbtCode(selfBookingTool.getSbtCode());
    
    }
    
    /*PROTECTED REGION ID(_3DfAEGQrEeSRQ7C-gEfj8g u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

