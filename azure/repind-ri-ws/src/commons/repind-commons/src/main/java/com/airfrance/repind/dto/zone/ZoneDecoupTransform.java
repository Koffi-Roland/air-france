package com.airfrance.repind.dto.zone;

/*PROTECTED REGION ID(_PsYf8LdgEeCrCZp8iGNNVw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.entity.zone.ZoneComm;
import com.airfrance.repind.entity.zone.ZoneDecoup;
import com.airfrance.repind.entity.zone.ZoneFinanciere;
import com.airfrance.repind.entity.zone.ZoneVente;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

/*PROTECTED REGION END*/

/**
 * <p>Title : ZoneDecoupTransform.java</p>
 * transformation bo <-> dto pour un(e) ZoneDecoup
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ZoneDecoupTransform {

    /*PROTECTED REGION ID(_PsYf8LdgEeCrCZp8iGNNVw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ZoneDecoupTransform() {
    }
    /**
     * dto -> bo for a ZoneDecoup
     * @param zoneDecoupDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static ZoneDecoup dto2BoLight(ZoneDecoupDTO zoneDecoupDTO) throws JrafDomainException {
        // instanciation du BO
        ZoneDecoup zoneDecoup = null;
        Class dtoClass = zoneDecoupDTO.getClass();
    if(dtoClass.equals(ZoneCommDTO.class)) {
         zoneDecoup = new ZoneComm();
         ZoneCommTransform.dto2BoLight((ZoneCommDTO)zoneDecoupDTO, (ZoneComm)zoneDecoup);
    }
    if(dtoClass.equals(ZoneVenteDTO.class)) {
         zoneDecoup = new ZoneVente();
         ZoneVenteTransform.dto2BoLight((ZoneVenteDTO)zoneDecoupDTO, (ZoneVente)zoneDecoup);
    }
    if(dtoClass.equals(ZoneFinanciereDTO.class)) {
         zoneDecoup = new ZoneFinanciere();
         ZoneFinanciereTransform.dto2BoLight((ZoneFinanciereDTO)zoneDecoupDTO, (ZoneFinanciere)zoneDecoup);
    }
        if(dtoClass == null) {
            throw new JrafDomainRollbackException("Type dto inconnu : " + dtoClass);
        }


        // on retourne le BO
        return zoneDecoup;
    }

    /**
     * dto -> bo for a zoneDecoup
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param zoneDecoupDTO dto
     * @param zoneDecoup bo
     */
    public static void dto2BoLight(ZoneDecoupDTO zoneDecoupDTO, ZoneDecoup zoneDecoup) {
    
        /*PROTECTED REGION ID(dto2BoLight_PsYf8LdgEeCrCZp8iGNNVw) ENABLED START*/
        
        dto2BoLightImpl(zoneDecoupDTO,zoneDecoup);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a zoneDecoup
     * @param zoneDecoupDTO dto
     * @param zoneDecoup bo
     */
    private static void dto2BoLightImpl(ZoneDecoupDTO zoneDecoupDTO, ZoneDecoup zoneDecoup){
    
        // property of ZoneDecoupDTO
        zoneDecoup.setGin(zoneDecoupDTO.getGin());
        zoneDecoup.setStatut(zoneDecoupDTO.getStatut());
        zoneDecoup.setDateMaj(zoneDecoupDTO.getDateMaj());
        zoneDecoup.setSignatureMaj(zoneDecoupDTO.getSignatureMaj());
        zoneDecoup.setNature(zoneDecoupDTO.getNature());
        zoneDecoup.setSousType(zoneDecoupDTO.getSousType());
        zoneDecoup.setDateOuverture(zoneDecoupDTO.getDateOuverture());
        zoneDecoup.setDateFermeture(zoneDecoupDTO.getDateFermeture());
    
    }

    /**
     * bo -> dto for a zoneDecoup
     * @param pZoneDecoup bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ZoneDecoupDTO bo2DtoLight(ZoneDecoup pZoneDecoup) throws JrafDomainException {
        // instanciation du DTO
        ZoneDecoupDTO zoneDecoupDTO = null;
        ZoneDecoup zoneDecoup = null;
        Class boClass=null;
        if (pZoneDecoup instanceof HibernateProxy) {
            boClass=Hibernate.getClass(pZoneDecoup);
            zoneDecoup = (ZoneDecoup) ((HibernateProxy) pZoneDecoup).getHibernateLazyInitializer()
                    .getImplementation();
        } else {
            boClass = pZoneDecoup.getClass();
            zoneDecoup = pZoneDecoup;
        }
        if(boClass.equals(ZoneComm.class)) {
            zoneDecoupDTO = new ZoneCommDTO();
            ZoneCommTransform.bo2DtoLight((ZoneComm)zoneDecoup, (ZoneCommDTO)zoneDecoupDTO);
        }
        if(boClass.equals(ZoneVente.class)) {
            zoneDecoupDTO = new ZoneVenteDTO();
            ZoneVenteTransform.bo2DtoLight((ZoneVente)zoneDecoup, (ZoneVenteDTO)zoneDecoupDTO);
        }
        if(boClass.equals(ZoneFinanciere.class)) {
            zoneDecoupDTO = new ZoneFinanciereDTO();
            ZoneFinanciereTransform.bo2DtoLight((ZoneFinanciere)zoneDecoup, (ZoneFinanciereDTO)zoneDecoupDTO);
        }
        if(zoneDecoupDTO == null) {
            throw new JrafDomainRollbackException("Type bo inconnu : " + zoneDecoup.getClass());
        }
        
        // on retourne le dto
        return zoneDecoupDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param zoneDecoup bo
     * @param zoneDecoupDTO dto
     */
    public static void bo2DtoLight(
        ZoneDecoup zoneDecoup,
        ZoneDecoupDTO zoneDecoupDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_PsYf8LdgEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoLightImpl(zoneDecoup, zoneDecoupDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param zoneDecoup bo
     * @param zoneDecoupDTO dto
     */
    private static void bo2DtoLightImpl(ZoneDecoup zoneDecoup,
        ZoneDecoupDTO zoneDecoupDTO){
    

        // simple properties
        zoneDecoupDTO.setGin(zoneDecoup.getGin());
        zoneDecoupDTO.setStatut(zoneDecoup.getStatut());
        zoneDecoupDTO.setDateMaj(zoneDecoup.getDateMaj());
        zoneDecoupDTO.setSignatureMaj(zoneDecoup.getSignatureMaj());
        zoneDecoupDTO.setNature(zoneDecoup.getNature());
        zoneDecoupDTO.setSousType(zoneDecoup.getSousType());
        zoneDecoupDTO.setDateOuverture(zoneDecoup.getDateOuverture());
        zoneDecoupDTO.setDateFermeture(zoneDecoup.getDateFermeture());
    
    }

    /**
     * bo -> dto for a zoneDecoup
     * @param pZoneDecoup bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ZoneDecoupDTO bo2Dto(ZoneDecoup pZoneDecoup) throws JrafDomainException {
        // instanciation du DTO
        ZoneDecoupDTO zoneDecoupDTO = null;
        ZoneDecoup zoneDecoup = null;
        Class boClass=null;
        if (pZoneDecoup instanceof HibernateProxy) {
            boClass=Hibernate.getClass(pZoneDecoup);
            zoneDecoup = (ZoneDecoup) ((HibernateProxy) pZoneDecoup).getHibernateLazyInitializer()
                    .getImplementation();
        } else {
            boClass = pZoneDecoup.getClass();
            zoneDecoup = pZoneDecoup;
        }
        if(boClass.equals(ZoneVente.class)) {
            zoneDecoupDTO = new ZoneVenteDTO();
            ZoneVenteTransform.bo2Dto((ZoneVente)zoneDecoup, (ZoneVenteDTO)zoneDecoupDTO);
        }
        if(boClass.equals(ZoneComm.class)) {
            zoneDecoupDTO = new ZoneCommDTO();
            ZoneCommTransform.bo2Dto((ZoneComm)zoneDecoup, (ZoneCommDTO)zoneDecoupDTO);
        }

        // on retourne le dto
        return zoneDecoupDTO;
    }
}

