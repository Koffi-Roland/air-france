package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_XVH24BeIEeKJFbgRY_ODIg i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefVilleIata;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefVilleIataTransform.java</p>
 * transformation bo <-> dto pour un(e) RefVilleIata
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefVilleIataTransform {

    /*PROTECTED REGION ID(_XVH24BeIEeKJFbgRY_ODIg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * Constructeur prive
     */
    private RefVilleIataTransform() {
    }
    /**
     * dto -> bo pour une RefVilleIata
     * @param refVilleIataDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefVilleIata dto2BoLight(RefVilleIataDTO refVilleIataDTO) throws JrafDomainException {
        // instanciation du BO
        RefVilleIata refVilleIata = new RefVilleIata();
        dto2BoLight(refVilleIataDTO, refVilleIata);

        // on retourne le BO
        return refVilleIata;
    }

    /**
     * dto -> bo pour une refVilleIata
     * @param refVilleIataDTO dto
     * @param refVilleIata bo
     */
    public static void dto2BoLight(RefVilleIataDTO refVilleIataDTO, RefVilleIata refVilleIata) {
        // property of RefVilleIataDTO
        refVilleIata.setScodeVille(refVilleIataDTO.getScodeVille());
        refVilleIata.setLongitude(refVilleIataDTO.getLongitude());
        refVilleIata.setEscContinent(refVilleIataDTO.getEscContinent());
        refVilleIata.setTerritoire(refVilleIataDTO.getTerritoire());
        refVilleIata.setSpntVente(refVilleIataDTO.getSpntVente());
        refVilleIata.setLibelleEn(refVilleIataDTO.getLibelleEn());
        refVilleIata.setLibelle(refVilleIataDTO.getLibelle());
        refVilleIata.setCodeVilleDeprecated(refVilleIataDTO.getCodeVilleDeprecated());
        refVilleIata.setCodeProvEtat(refVilleIataDTO.getCodeProvEtat());
        refVilleIata.setDateModif(refVilleIataDTO.getDateModif());
        refVilleIata.setDateFin(refVilleIataDTO.getDateFin());
        refVilleIata.setDateDebut(refVilleIataDTO.getDateDebut());
        refVilleIata.setLatitude(refVilleIataDTO.getLatitude());
    }

    /**
     * bo -> dto pour une refVilleIata
     * @param pRefVilleIata bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefVilleIataDTO bo2DtoLight(RefVilleIata pRefVilleIata) throws JrafDomainException {
        // instanciation du DTO
        RefVilleIataDTO refVilleIataDTO = new RefVilleIataDTO();
        bo2DtoLight(pRefVilleIata, refVilleIataDTO);
        // on retourne le dto
        return refVilleIataDTO;
    }


    /**
     * Transforme un business object en DTO.
     * Methode dite light.
     * @param refVilleIata bo
     * @param refVilleIataDTO dto
     */
    public static void bo2DtoLight(
        RefVilleIata refVilleIata,
        RefVilleIataDTO refVilleIataDTO) {


        // simple properties
        refVilleIataDTO.setScodeVille(refVilleIata.getScodeVille());
        refVilleIataDTO.setLongitude(refVilleIata.getLongitude());
        refVilleIataDTO.setEscContinent(refVilleIata.getEscContinent());
        refVilleIataDTO.setTerritoire(refVilleIata.getTerritoire());
        refVilleIataDTO.setSpntVente(refVilleIata.getSpntVente());
        refVilleIataDTO.setLibelleEn(refVilleIata.getLibelleEn());
        refVilleIataDTO.setLibelle(refVilleIata.getLibelle());
        refVilleIataDTO.setCodeVilleDeprecated(refVilleIata.getCodeVilleDeprecated());
        refVilleIataDTO.setCodeProvEtat(refVilleIata.getCodeProvEtat());
        refVilleIataDTO.setDateModif(refVilleIata.getDateModif());
        refVilleIataDTO.setDateFin(refVilleIata.getDateFin());
        refVilleIataDTO.setDateDebut(refVilleIata.getDateDebut());
        refVilleIataDTO.setLatitude(refVilleIata.getLatitude());


    }
    
    /*PROTECTED REGION ID(_XVH24BeIEeKJFbgRY_ODIg u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

