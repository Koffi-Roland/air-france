package com.airfrance.repind.dto.zone;

/*PROTECTED REGION ID(_hs9CwEANEeS2wtWjh0gEaw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.zone.ZoneFinanciere;

/*PROTECTED REGION END*/

/**
 * <p>Title : ZoneFinanciereTransform.java</p>
 * transformation bo <-> dto pour un(e) ZoneFinanciere
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ZoneFinanciereTransform {

    /*PROTECTED REGION ID(_hs9CwEANEeS2wtWjh0gEaw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ZoneFinanciereTransform() {
    }
    /**
     * dto -> bo for a ZoneFinanciere
     * @param zoneFinanciereDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static ZoneFinanciere dto2BoLight(ZoneFinanciereDTO zoneFinanciereDTO) throws JrafDomainException {
        return (ZoneFinanciere)ZoneDecoupTransform.dto2BoLight(zoneFinanciereDTO);
    }

    /**
     * dto -> bo for a zoneFinanciere
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param zoneFinanciereDTO dto
     * @param zoneFinanciere bo
     */
    public static void dto2BoLight(ZoneFinanciereDTO zoneFinanciereDTO, ZoneFinanciere zoneFinanciere) {
    
        /*PROTECTED REGION ID(dto2BoLight_hs9CwEANEeS2wtWjh0gEaw) ENABLED START*/
        
        dto2BoLightImpl(zoneFinanciereDTO,zoneFinanciere);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a zoneFinanciere
     * @param zoneFinanciereDTO dto
     * @param zoneFinanciere bo
     */
    private static void dto2BoLightImpl(ZoneFinanciereDTO zoneFinanciereDTO, ZoneFinanciere zoneFinanciere){
    
        // superclass property
        ZoneDecoupTransform.dto2BoLight(zoneFinanciereDTO, zoneFinanciere);
        // property of ZoneFinanciereDTO
        zoneFinanciere.setCodePays(zoneFinanciereDTO.getCodePays());
        zoneFinanciere.setCodeUF(zoneFinanciereDTO.getCodeUF());
        zoneFinanciere.setZoneGeo(zoneFinanciereDTO.getZoneGeo());
        zoneFinanciere.setCodeVille(zoneFinanciereDTO.getCodeVille());
    
    }

    /**
     * bo -> dto for a zoneFinanciere
     * @param zoneFinanciere bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ZoneFinanciereDTO bo2DtoLight(ZoneFinanciere zoneFinanciere) throws JrafDomainException {
        return (ZoneFinanciereDTO)ZoneDecoupTransform.bo2DtoLight(zoneFinanciere);
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param zoneFinanciere bo
     * @param zoneFinanciereDTO dto
     */
    public static void bo2DtoLight(
        ZoneFinanciere zoneFinanciere,
        ZoneFinanciereDTO zoneFinanciereDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_hs9CwEANEeS2wtWjh0gEaw) ENABLED START*/

        bo2DtoLightImpl(zoneFinanciere, zoneFinanciereDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param zoneFinanciere bo
     * @param zoneFinanciereDTO dto
     */
    private static void bo2DtoLightImpl(ZoneFinanciere zoneFinanciere,
        ZoneFinanciereDTO zoneFinanciereDTO){
    
        // superclass property
        ZoneDecoupTransform.bo2DtoLight(zoneFinanciere, zoneFinanciereDTO);

        // simple properties
        zoneFinanciereDTO.setCodePays(zoneFinanciere.getCodePays());
        zoneFinanciereDTO.setCodeUF(zoneFinanciere.getCodeUF());
        zoneFinanciereDTO.setZoneGeo(zoneFinanciere.getZoneGeo());
        zoneFinanciereDTO.setCodeVille(zoneFinanciere.getCodeVille());
    
    }
    
    /*PROTECTED REGION ID(_hs9CwEANEeS2wtWjh0gEaw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

