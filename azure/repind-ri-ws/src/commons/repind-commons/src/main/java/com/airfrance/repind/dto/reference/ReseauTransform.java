package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_RkSYBWkxEeGhB9497mGnHw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.Reseau;

/*PROTECTED REGION END*/

/**
 * <p>Title : ReseauTransform.java</p>
 * transformation bo <-> dto pour un(e) Reseau
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ReseauTransform {

    /*PROTECTED REGION ID(_RkSYBWkxEeGhB9497mGnHw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ReseauTransform() {
    }
    /**
     * dto -> bo for a Reseau
     * @param reseauDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Reseau dto2BoLight(ReseauDTO reseauDTO) throws JrafDomainException {
        // instanciation du BO
        Reseau reseau = new Reseau();
        dto2BoLight(reseauDTO, reseau);

        // on retourne le BO
        return reseau;
    }

    /**
     * dto -> bo for a reseau
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param reseauDTO dto
     * @param reseau bo
     */
    public static void dto2BoLight(ReseauDTO reseauDTO, Reseau reseau) {
    
        /*PROTECTED REGION ID(dto2BoLight_RkSYBWkxEeGhB9497mGnHw) ENABLED START*/
        
        dto2BoLightImpl(reseauDTO,reseau);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a reseau
     * @param reseauDTO dto
     * @param reseau bo
     */
    private static void dto2BoLightImpl(ReseauDTO reseauDTO, Reseau reseau){
    
        // property of ReseauDTO
        reseau.setDateCreation(reseauDTO.getDateCreation());
        reseau.setDateFermeture(reseauDTO.getDateFermeture());
        reseau.setCode(reseauDTO.getCode());
        reseau.setNature(reseauDTO.getNature());
        reseau.setNom(reseauDTO.getNom());
        reseau.setPays(reseauDTO.getPays());
        reseau.setType(reseauDTO.getType());
    
    }

    /**
     * bo -> dto for a reseau
     * @param pReseau bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ReseauDTO bo2DtoLight(Reseau pReseau) throws JrafDomainException {
        // instanciation du DTO
        ReseauDTO reseauDTO = new ReseauDTO();
        bo2DtoLight(pReseau, reseauDTO);
        // on retourne le dto
        return reseauDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param reseau bo
     * @param reseauDTO dto
     */
    public static void bo2DtoLight(
        Reseau reseau,
        ReseauDTO reseauDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_RkSYBWkxEeGhB9497mGnHw) ENABLED START*/

        bo2DtoLightImpl(reseau, reseauDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param reseau bo
     * @param reseauDTO dto
     */
    private static void bo2DtoLightImpl(Reseau reseau,
        ReseauDTO reseauDTO){
    

        // simple properties
        reseauDTO.setDateCreation(reseau.getDateCreation());
        reseauDTO.setDateFermeture(reseau.getDateFermeture());
        reseauDTO.setCode(reseau.getCode());
        reseauDTO.setNature(reseau.getNature());
        reseauDTO.setNom(reseau.getNom());
        reseauDTO.setPays(reseau.getPays());
        reseauDTO.setType(reseau.getType());
    
    }
    
    /*PROTECTED REGION ID(_RkSYBWkxEeGhB9497mGnHw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

