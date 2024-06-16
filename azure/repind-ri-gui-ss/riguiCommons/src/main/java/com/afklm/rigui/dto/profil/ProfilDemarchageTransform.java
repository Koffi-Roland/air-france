package com.afklm.rigui.dto.profil;

/*PROTECTED REGION ID(_URgWwGkyEeGhB9497mGnHw i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.profil.ProfilDemarchage;

/*PROTECTED REGION END*/

/**
 * <p>Title : ProfilDemarchageTransform.java</p>
 * transformation bo <-> dto pour un(e) ProfilDemarchage
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ProfilDemarchageTransform {

    /*PROTECTED REGION ID(_URgWwGkyEeGhB9497mGnHw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ProfilDemarchageTransform() {
    }
    /**
     * dto -> bo for a ProfilDemarchage
     * @param profilDemarchargeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static ProfilDemarchage dto2BoLight(ProfilDemarchargeDTO profilDemarchargeDTO) throws JrafDomainException {
        // instanciation du BO
        ProfilDemarchage profilDemarchage = new ProfilDemarchage();
        dto2BoLight(profilDemarchargeDTO, profilDemarchage);

        // on retourne le BO
        return profilDemarchage;
    }

    /**
     * dto -> bo for a profilDemarchage
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param profilDemarchargeDTO dto
     * @param profilDemarchage bo
     */
    public static void dto2BoLight(ProfilDemarchargeDTO profilDemarchargeDTO, ProfilDemarchage profilDemarchage) {
    
        /*PROTECTED REGION ID(dto2BoLight_URgWwGkyEeGhB9497mGnHw) ENABLED START*/
        
        dto2BoLightImpl(profilDemarchargeDTO,profilDemarchage);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a profilDemarchage
     * @param profilDemarchargeDTO dto
     * @param profilDemarchage bo
     */
    private static void dto2BoLightImpl(ProfilDemarchargeDTO profilDemarchargeDTO, ProfilDemarchage profilDemarchage){
    
        // property of ProfilDemarchargeDTO
        profilDemarchage.setCle(profilDemarchargeDTO.getCle());
        profilDemarchage.setDemarchage(profilDemarchargeDTO.getDemarchage());
        profilDemarchage.setTypeMailing(profilDemarchargeDTO.getTypeMailing());
        profilDemarchage.setVitrine(profilDemarchargeDTO.getVitrine());
        profilDemarchage.setEmplacement(profilDemarchargeDTO.getEmplacement());
        profilDemarchage.setDateTy(profilDemarchargeDTO.getDateTy());
        profilDemarchage.setDateRetraitTy(profilDemarchargeDTO.getDateRetraitTy());
        profilDemarchage.setJourOuverture(profilDemarchargeDTO.getJourOuverture());
        profilDemarchage.setHeureOuv(profilDemarchargeDTO.getHeureOuv());
        profilDemarchage.setHeureFerm(profilDemarchargeDTO.getHeureFerm());
        profilDemarchage.setLanguePar(profilDemarchargeDTO.getLanguePar());
        profilDemarchage.setLangueEcr(profilDemarchargeDTO.getLangueEcr());
        profilDemarchage.setSecteurGeo(profilDemarchargeDTO.getSecteurGeo());
    
    }

    /**
     * bo -> dto for a profilDemarchage
     * @param pProfilDemarchage bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ProfilDemarchargeDTO bo2DtoLight(ProfilDemarchage pProfilDemarchage) throws JrafDomainException {
        // instanciation du DTO
        ProfilDemarchargeDTO profilDemarchargeDTO = new ProfilDemarchargeDTO();
        bo2DtoLight(pProfilDemarchage, profilDemarchargeDTO);
        // on retourne le dto
        return profilDemarchargeDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param profilDemarchage bo
     * @param profilDemarchargeDTO dto
     */
    public static void bo2DtoLight(
        ProfilDemarchage profilDemarchage,
        ProfilDemarchargeDTO profilDemarchargeDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_URgWwGkyEeGhB9497mGnHw) ENABLED START*/

        bo2DtoLightImpl(profilDemarchage, profilDemarchargeDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param profilDemarchage bo
     * @param profilDemarchargeDTO dto
     */
    private static void bo2DtoLightImpl(ProfilDemarchage profilDemarchage,
        ProfilDemarchargeDTO profilDemarchargeDTO){
    

        // simple properties
        profilDemarchargeDTO.setCle(profilDemarchage.getCle());
        profilDemarchargeDTO.setDemarchage(profilDemarchage.getDemarchage());
        profilDemarchargeDTO.setTypeMailing(profilDemarchage.getTypeMailing());
        profilDemarchargeDTO.setVitrine(profilDemarchage.getVitrine());
        profilDemarchargeDTO.setEmplacement(profilDemarchage.getEmplacement());
        profilDemarchargeDTO.setDateTy(profilDemarchage.getDateTy());
        profilDemarchargeDTO.setDateRetraitTy(profilDemarchage.getDateRetraitTy());
        profilDemarchargeDTO.setJourOuverture(profilDemarchage.getJourOuverture());
        profilDemarchargeDTO.setHeureOuv(profilDemarchage.getHeureOuv());
        profilDemarchargeDTO.setHeureFerm(profilDemarchage.getHeureFerm());
        profilDemarchargeDTO.setLanguePar(profilDemarchage.getLanguePar());
        profilDemarchargeDTO.setLangueEcr(profilDemarchage.getLangueEcr());
        profilDemarchargeDTO.setSecteurGeo(profilDemarchage.getSecteurGeo());
    
    }
    
    /*PROTECTED REGION ID(_URgWwGkyEeGhB9497mGnHw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

