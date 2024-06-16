package com.airfrance.repind.dto.zone;

/*PROTECTED REGION ID(_Qmf-0LdgEeCrCZp8iGNNVw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.zone.LienIntCpZd;
import com.airfrance.repind.entity.zone.LienZvZc;
import com.airfrance.repind.entity.zone.ZoneVente;

import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : ZoneVenteTransform.java</p>
 * transformation bo <-> dto pour un(e) ZoneVente
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ZoneVenteTransform {

    /*PROTECTED REGION ID(_Qmf-0LdgEeCrCZp8iGNNVw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ZoneVenteTransform() {
    }
    /**
     * dto -> bo for a ZoneVente
     * @param zoneVenteDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static ZoneVente dto2BoLight(ZoneVenteDTO zoneVenteDTO) throws JrafDomainException {
        return (ZoneVente)ZoneDecoupTransform.dto2BoLight(zoneVenteDTO);
    }

    /**
     * dto -> bo for a zoneVente
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param zoneVenteDTO dto
     * @param zoneVente bo
     */
    public static void dto2BoLight(ZoneVenteDTO zoneVenteDTO, ZoneVente zoneVente) {
    
        /*PROTECTED REGION ID(dto2BoLight_Qmf-0LdgEeCrCZp8iGNNVw) ENABLED START*/
        
        dto2BoLightImpl(zoneVenteDTO,zoneVente);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a zoneVente
     * @param zoneVenteDTO dto
     * @param zoneVente bo
     */
    private static void dto2BoLightImpl(ZoneVenteDTO zoneVenteDTO, ZoneVente zoneVente){
    
        // superclass property
        ZoneDecoupTransform.dto2BoLight(zoneVenteDTO, zoneVente);
        // property of ZoneVenteDTO
        zoneVente.setZv0(zoneVenteDTO.getZv0());
        zoneVente.setZv1(zoneVenteDTO.getZv1());
        zoneVente.setZv2(zoneVenteDTO.getZv2());
        zoneVente.setZv3(zoneVenteDTO.getZv3());
        zoneVente.setZvAlpha(zoneVenteDTO.getZvAlpha());
        zoneVente.setLibZvAlpha(zoneVenteDTO.getLibZvAlpha());
        zoneVente.setCodeMonnaie(zoneVenteDTO.getCodeMonnaie());
    
    }

    /**
     * bo -> dto for a zoneVente
     * @param zoneVente bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ZoneVenteDTO bo2DtoLight(ZoneVente zoneVente) throws JrafDomainException {
        return (ZoneVenteDTO)ZoneDecoupTransform.bo2DtoLight(zoneVente);
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param zoneVente bo
     * @param zoneVenteDTO dto
     */
    public static void bo2DtoLight(
        ZoneVente zoneVente,
        ZoneVenteDTO zoneVenteDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_Qmf-0LdgEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoLightImpl(zoneVente, zoneVenteDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param zoneVente bo
     * @param zoneVenteDTO dto
     */
    private static void bo2DtoLightImpl(ZoneVente zoneVente,
        ZoneVenteDTO zoneVenteDTO){
    
        // superclass property
        ZoneDecoupTransform.bo2DtoLight(zoneVente, zoneVenteDTO);

        // simple properties
        zoneVenteDTO.setZv0(zoneVente.getZv0());
        zoneVenteDTO.setZv1(zoneVente.getZv1());
        zoneVenteDTO.setZv2(zoneVente.getZv2());
        zoneVenteDTO.setZv3(zoneVente.getZv3());
        zoneVenteDTO.setZvAlpha(zoneVente.getZvAlpha());
        zoneVenteDTO.setLibZvAlpha(zoneVente.getLibZvAlpha());
        zoneVenteDTO.setCodeMonnaie(zoneVente.getCodeMonnaie());
    
    }

    /**
     * bo -> dto for a zoneVente light method
     *
     * @param zoneVente bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ZoneVenteDTO bo2Dto(ZoneVente zoneVente) throws JrafDomainException {
        return (ZoneVenteDTO) ZoneDecoupTransform.bo2Dto(zoneVente);
    }

    /**
     * Transform a business object to DTO. calls bo2DtoLightImpl in a protected
     * region so the user can override this without losing benefit of generation if
     * attributes vary in future
     *
     * @param zoneVente    bo
     * @param zoneVenteDTO dto
     * @throws JrafDomainException
     */
    public static void bo2Dto(ZoneVente zoneVente, ZoneVenteDTO zoneVenteDTO) throws JrafDomainException {

        /* PROTECTED REGION ID(bo2DtoLight_Qmf-0LdgEeCrCZp8iGNNVw) ENABLED START */

        bo2DtoImpl(zoneVente, zoneVenteDTO);

        /* PROTECTED REGION END */

    }

    /**
     * Transform a business object to DTO. Implementation method
     *
     * @param zoneVente    bo
     * @param zoneVenteDTO dto
     * @throws JrafDomainException
     */
    private static void bo2DtoImpl(ZoneVente zoneVente, ZoneVenteDTO zoneVenteDTO) throws JrafDomainException {

        // superclass property
        ZoneDecoupTransform.bo2DtoLight(zoneVente, zoneVenteDTO);

        Set<LienIntCpZdDTO> lienIntCpZdDTOSet = new HashSet<>();
        if (!zoneVente.getLiensIntCpZd().isEmpty()) {
            for (LienIntCpZd lienIntCpZd : zoneVente.getLiensIntCpZd()) {
                LienIntCpZdDTO lienIntCpZdDTO = new LienIntCpZdDTO();
                LienIntCpZdTransform.bo2Dto(lienIntCpZd, lienIntCpZdDTO);
                lienIntCpZdDTOSet.add(lienIntCpZdDTO);
            }
        }

        Set<LienZvZcDTO> lienZvZcDTOSet = new HashSet<>();
        if (!zoneVente.getLiensZc().isEmpty()) {
            for (LienZvZc lienZvZc : zoneVente.getLiensZc()) {
                LienZvZcDTO lienZvZcDTO = new LienZvZcDTO(lienZvZc.getCle(),lienZvZc.getType(),lienZvZc.getDateOuverture(),lienZvZc.getDateFermeture());
                lienZvZcDTOSet.add(lienZvZcDTO);
            }
        }

        zoneVenteDTO.setZv0(zoneVente.getZv0());
        zoneVenteDTO.setZv1(zoneVente.getZv1());
        zoneVenteDTO.setZv2(zoneVente.getZv2());
        zoneVenteDTO.setZv3(zoneVente.getZv3());
        zoneVenteDTO.setZvAlpha(zoneVente.getZvAlpha());
        zoneVenteDTO.setLibZvAlpha(zoneVente.getLibZvAlpha());
        zoneVenteDTO.setLiensIntCpZd(lienIntCpZdDTOSet);
        zoneVenteDTO.setLiensZc(lienZvZcDTOSet);
        zoneVenteDTO.setCodeMonnaie(zoneVente.getCodeMonnaie());

    }
}

