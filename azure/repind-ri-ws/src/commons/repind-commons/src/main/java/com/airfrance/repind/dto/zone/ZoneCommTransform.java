package com.airfrance.repind.dto.zone;

/*PROTECTED REGION ID(_OgFr8LdgEeCrCZp8iGNNVw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.zone.LienIntCpZd;
import com.airfrance.repind.entity.zone.LienZvZc;
import com.airfrance.repind.entity.zone.ZoneComm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : ZoneCommTransform.java</p>
 * transformation bo <-> dto pour un(e) ZoneComm
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ZoneCommTransform {

    /*PROTECTED REGION ID(_OgFr8LdgEeCrCZp8iGNNVw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ZoneCommTransform() {
    }
    /**
     * dto -> bo for a ZoneComm
     * @param zoneCommDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static ZoneComm dto2BoLight(ZoneCommDTO zoneCommDTO) throws JrafDomainException {
        return (ZoneComm)ZoneDecoupTransform.dto2BoLight(zoneCommDTO);
    }

    /**
     * dto -> bo for a zoneComm
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param zoneCommDTO dto
     * @param zoneComm bo
     */
    public static void dto2BoLight(ZoneCommDTO zoneCommDTO, ZoneComm zoneComm) {
    
        /*PROTECTED REGION ID(dto2BoLight_OgFr8LdgEeCrCZp8iGNNVw) ENABLED START*/
        
        dto2BoLightImpl(zoneCommDTO,zoneComm);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a zoneComm
     * @param zoneCommDTO dto
     * @param zoneComm bo
     */
    private static void dto2BoLightImpl(ZoneCommDTO zoneCommDTO, ZoneComm zoneComm){
    
        // superclass property
        ZoneDecoupTransform.dto2BoLight(zoneCommDTO, zoneComm);
        // property of ZoneCommDTO
        zoneComm.setZc1(zoneCommDTO.getZc1());
        zoneComm.setZc2(zoneCommDTO.getZc2());
        zoneComm.setZc3(zoneCommDTO.getZc3());
        zoneComm.setZc4(zoneCommDTO.getZc4());
        zoneComm.setZc5(zoneCommDTO.getZc5());
    
    }

    /**
     * bo -> dto for a zoneComm
     * @param zoneComm bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ZoneCommDTO bo2DtoLight(ZoneComm zoneComm) throws JrafDomainException {
        return (ZoneCommDTO)ZoneDecoupTransform.bo2DtoLight(zoneComm);
    }

    /**
     * bo -> dto for a zoneComm list
     * @param zoneComm bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static List<ZoneCommDTO> bo2DtoLight(List<ZoneComm> zoneComm) throws JrafDomainException {
        List<ZoneCommDTO> result = new ArrayList<>();
        for (ZoneComm comm : zoneComm) {
            result.add((ZoneCommDTO)ZoneDecoupTransform.bo2DtoLight(comm));
        }
        return result;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param zoneComm bo
     * @param zoneCommDTO dto
     */
    public static void bo2DtoLight(
        ZoneComm zoneComm,
        ZoneCommDTO zoneCommDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_OgFr8LdgEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoLightImpl(zoneComm, zoneCommDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param zoneComm bo
     * @param zoneCommDTO dto
     */
    private static void bo2DtoLightImpl(ZoneComm zoneComm,
        ZoneCommDTO zoneCommDTO){
    
        // superclass property
        ZoneDecoupTransform.bo2DtoLight(zoneComm, zoneCommDTO);

        // simple properties
        zoneCommDTO.setZc1(zoneComm.getZc1());
        zoneCommDTO.setZc2(zoneComm.getZc2());
        zoneCommDTO.setZc3(zoneComm.getZc3());
        zoneCommDTO.setZc4(zoneComm.getZc4());
        zoneCommDTO.setZc5(zoneComm.getZc5());
    
    }

    /**
     * Transform a business object to DTO.
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param zoneComm bo
     * @param zoneCommDTO dto
     * @throws JrafDomainException
     */
    public static void bo2Dto(
            ZoneComm zoneComm,
            ZoneCommDTO zoneCommDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(bo2DtoLight_OgFr8LdgEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoImpl(zoneComm, zoneCommDTO);

        /*PROTECTED REGION END*/

    }

    /**
     * Transform a business object to DTO. Implementation method
     * @param zoneComm bo
     * @param zoneCommDTO dto
     * @throws JrafDomainException
     */
    private static void bo2DtoImpl(ZoneComm zoneComm,
                                   ZoneCommDTO zoneCommDTO) throws JrafDomainException{

        // superclass property
        ZoneDecoupTransform.bo2DtoLight(zoneComm, zoneCommDTO);

        Set<LienIntCpZdDTO> lienIntCpZdDTOSet = new HashSet<>();
        if (!zoneComm.getLiensIntCpZd().isEmpty()) {
            for (LienIntCpZd lienIntCpZd : zoneComm.getLiensIntCpZd()) {
                LienIntCpZdDTO lienIntCpZdDTO = new LienIntCpZdDTO();
                LienIntCpZdTransform.bo2Dto(lienIntCpZd, lienIntCpZdDTO);
                lienIntCpZdDTOSet.add(lienIntCpZdDTO);
            }
        }

        Set<LienZvZcDTO> lienZvZcDTOSet = new HashSet<>();
        if (!zoneComm.getLiensZv().isEmpty()) {
            for (LienZvZc lienZvZc : zoneComm.getLiensZv()) {
                LienZvZcDTO lienZvZcDTO = new LienZvZcDTO(lienZvZc.getCle(),lienZvZc.getType(),lienZvZc.getDateOuverture(),lienZvZc.getDateFermeture());
                lienZvZcDTOSet.add(lienZvZcDTO);
            }
        }

        zoneCommDTO.setLiensZv(lienZvZcDTOSet);
        // simple properties
        zoneCommDTO.setLiensIntCpZd(lienIntCpZdDTOSet);
        zoneCommDTO.setZc1(zoneComm.getZc1());
        zoneCommDTO.setZc2(zoneComm.getZc2());
        zoneCommDTO.setZc3(zoneComm.getZc3());
        zoneCommDTO.setZc4(zoneComm.getZc4());
        zoneCommDTO.setZc5(zoneComm.getZc5());

    }
}

