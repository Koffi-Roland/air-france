package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_08z54GkzEeGhB9497mGnHw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.zone.PmZoneTransform;
import com.airfrance.repind.entity.agence.Agence;
import com.airfrance.repind.entity.firme.GestionPM;
import com.airfrance.repind.entity.firme.PersonneMorale;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : GestionPMTransform.java</p>
 * transformation bo <-> dto pour un(e) GestionPM
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class GestionPMTransform {

    /*PROTECTED REGION ID(_08z54GkzEeGhB9497mGnHw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private GestionPMTransform() {
    }
    /**
     * dto -> bo for a GestionPM
     * @param gestionPMDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static GestionPM dto2BoLight(GestionPMDTO gestionPMDTO) throws JrafDomainException {
        // instanciation du BO
        GestionPM gestionPM = new GestionPM();
        dto2BoLight(gestionPMDTO, gestionPM);

        // on retourne le BO
        return gestionPM;
    }

    /**
     * dto -> bo for a gestionPM
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param gestionPMDTO dto
     * @param gestionPM bo
     */
    public static void dto2BoLight(GestionPMDTO gestionPMDTO, GestionPM gestionPM) {
    
        /*PROTECTED REGION ID(dto2BoLight_08z54GkzEeGhB9497mGnHw) ENABLED START*/
        
        dto2BoLightImpl(gestionPMDTO,gestionPM);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a gestionPM
     * @param gestionPMDTO dto
     * @param gestionPM bo
     */
    private static void dto2BoLightImpl(GestionPMDTO gestionPMDTO, GestionPM gestionPM){
    
        // property of GestionPMDTO
        gestionPM.setCle(gestionPMDTO.getCle());
        gestionPM.setVersion(gestionPMDTO.getVersion());
        gestionPM.setDateDebLien(gestionPMDTO.getDateDebLien());
        gestionPM.setDateFinLien(gestionPMDTO.getDateFinLien());
        gestionPM.setDateMaj(gestionPMDTO.getDateMaj());
        gestionPM.setLienZCFirme(gestionPMDTO.getLienZCFirme());
        gestionPM.setPrivilegie(gestionPMDTO.getPrivilegie());
        gestionPM.setSignatureMaj(gestionPMDTO.getSignatureMaj());
        gestionPM.setSiteMaj(gestionPMDTO.getSiteMaj());
        gestionPM.setTypeLien(gestionPMDTO.getTypeLien());
    
    }

    /**
     * bo -> dto for a gestionPM
     * @param pGestionPM bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static GestionPMDTO bo2DtoLight(GestionPM pGestionPM) throws JrafDomainException {
        // instanciation du DTO
        GestionPMDTO gestionPMDTO = new GestionPMDTO();
        bo2DtoLight(pGestionPM, gestionPMDTO);
        // on retourne le dto
        return gestionPMDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param gestionPM bo
     * @param gestionPMDTO dto
     */
    public static void bo2DtoLight(
        GestionPM gestionPM,
        GestionPMDTO gestionPMDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_08z54GkzEeGhB9497mGnHw) ENABLED START*/

        bo2DtoLightImpl(gestionPM, gestionPMDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param gestionPM bo
     * @param gestionPMDTO dto
     */
    private static void bo2DtoLightImpl(GestionPM gestionPM,
        GestionPMDTO gestionPMDTO){
    

        // simple properties
        gestionPMDTO.setCle(gestionPM.getCle());
        gestionPMDTO.setVersion(gestionPM.getVersion());
        gestionPMDTO.setDateDebLien(gestionPM.getDateDebLien());
        gestionPMDTO.setDateFinLien(gestionPM.getDateFinLien());
        gestionPMDTO.setDateMaj(gestionPM.getDateMaj());
        gestionPMDTO.setLienZCFirme(gestionPM.getLienZCFirme());
        gestionPMDTO.setPrivilegie(gestionPM.getPrivilegie());
        gestionPMDTO.setSignatureMaj(gestionPM.getSignatureMaj());
        gestionPMDTO.setSiteMaj(gestionPM.getSiteMaj());
        gestionPMDTO.setTypeLien(gestionPM.getTypeLien());
    
    }
    
    /*PROTECTED REGION ID(_08z54GkzEeGhB9497mGnHw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary    
	
    /**
     * bo -> dto for a gestionPM with links
     * 
     * @param pGestionPM
     *            bo
     * @throws JrafDomainException
     *             if the DTO type is not supported
     * @return dto
     */
    public static GestionPMDTO bo2Dto(GestionPM pGestionPM) throws JrafDomainException {
        // instanciation du DTO
        GestionPMDTO gestionPMDTO = new GestionPMDTO();
        bo2DtoLight(pGestionPM, gestionPMDTO);
        bo2DtoLink(pGestionPM, gestionPMDTO);
        // on retourne le dto
        return gestionPMDTO;
    }
	
    static public void dto2BoLink(EntityManager entityManager, GestionPMDTO gestionPmDTO, GestionPM gestionPm) throws JrafDomainException {
        
        if (gestionPmDTO.getPersonneMoraleGerante() != null && gestionPmDTO.getPersonneMoraleGerante().getGin() != null) {
            
            PersonneMorale proxyPersonneMoraleGerante = entityManager.getReference(PersonneMorale.class, gestionPmDTO.getPersonneMoraleGerante().getGin());
            gestionPm.setPersonneMoraleGerante(proxyPersonneMoraleGerante);
        }
        
        if (gestionPmDTO.getPersonneMoraleGeree() != null && gestionPmDTO.getPersonneMoraleGeree().getGin() != null) {
            
            PersonneMorale proxyPersonneMoraleGeree = entityManager.getReference(PersonneMorale.class, gestionPmDTO.getPersonneMoraleGeree().getGin());
            gestionPm.setPersonneMoraleGeree(proxyPersonneMoraleGeree);
        }
    }
    
static public void bo2DtoLink(GestionPM gestionPm, GestionPMDTO gestionPmDTO ) throws JrafDomainException {
        
        if (gestionPm.getPersonneMoraleGerante() != null ) {
            // On set la personneMoraleGerante avec ses PmZones (utilisées dans le WS ProvideFirmDataV3)
        	gestionPmDTO.setPersonneMoraleGerante(PersonneMoraleTransform.bo2DtoLight(gestionPm.getPersonneMoraleGerante()));
        	gestionPmDTO.getPersonneMoraleGerante().setPmZones(PmZoneTransform.bo2Dto(gestionPm.getPersonneMoraleGerante().getPmZones()));
        	// Lien NumeroIdents
        	gestionPmDTO.getPersonneMoraleGerante().setNumerosIdent(NumeroIdentTransform.bo2Dto(gestionPm.getPersonneMoraleGerante().getNumerosIdent()));
        	// Lien Synonymes 
        	gestionPmDTO.getPersonneMoraleGerante().setSynonymes(SynonymeTransform.bo2Dto(gestionPm.getPersonneMoraleGerante().getSynonymes()));
        }
        
        // Pas besoin dans le ProvideFirmDataV3
        // Besoin dans le ProvideAgencyDataV2
        if (gestionPm.getPersonneMoraleGeree() != null ) {
            // On set la personneMoraleGeree avec ses PmZones (utilisées dans le WS ProvideAgencyDataV2)
        	gestionPmDTO.setPersonneMoraleGeree(PersonneMoraleTransform.bo2DtoLight(gestionPm.getPersonneMoraleGeree()));
        	gestionPmDTO.getPersonneMoraleGeree().setPmZones(PmZoneTransform.bo2Dto(gestionPm.getPersonneMoraleGeree().getPmZones()));
        	// Lien NumeroIdents
        	gestionPmDTO.getPersonneMoraleGeree().setNumerosIdent(NumeroIdentTransform.bo2Dto(gestionPm.getPersonneMoraleGeree().getNumerosIdent()));
        	// Lien Synonymes 
        	gestionPmDTO.getPersonneMoraleGeree().setSynonymes(SynonymeTransform.bo2Dto(gestionPm.getPersonneMoraleGeree().getSynonymes()));
        }
        
    }
    
/**
 * bo -> dto for a set of gestionPM
     * 
     * @param pGestionPM
     *            set of bo
     * @throws JrafDomainException
     *             if the DTO type is not supported
 * @return set of dto
 */
public static Set<GestionPMDTO> bo2Dto(Set<GestionPM> setGestionPM) throws JrafDomainException {
    if (setGestionPM!=null){
		// instanciation du DTO
	    Set<GestionPMDTO> setGestionPMDTO = new LinkedHashSet<GestionPMDTO>();
	    for (GestionPM gestionPM : setGestionPM){	    	
	    	setGestionPMDTO.add(bo2Dto(gestionPM));
	    }
	    // on retourne les dtos
	    return setGestionPMDTO;
        } else {
    	return null;
    }
}

	static public void dto2BoAgencyLink(GestionPMDTO gestionPmDTO, GestionPM gestionPm) throws JrafDomainException {
	    
	    if (gestionPmDTO.getPersonneMoraleGerante() != null && gestionPmDTO.getPersonneMoraleGerante().getNumerosIdent() != null) {
	        
	        PersonneMorale agence = new Agence();
	        agence.setNumerosIdent(NumeroIdentTransform.dto2Bo(gestionPmDTO.getPersonneMoraleGerante().getNumerosIdent()));
	        
	        gestionPm.setPersonneMoraleGerante(agence);
	    }
	    
	    if(gestionPmDTO.getPersonneMoraleGeree() != null && StringUtils.isNotBlank(gestionPmDTO.getPersonneMoraleGeree().getGin())) {
	    	gestionPm.setPersonneMoraleGeree(PersonneMoraleTransform.dto2BoLight(gestionPmDTO.getPersonneMoraleGeree()));
	    }
	    
	}

	/**
     * dto -> bo for a gestionPM calls dto2BoLight in a protected region so the user can override this without
     * 
     * @param gestionPMDTO
     *            dto
     * @param gestionPM
     *            bo
	 */
	public static GestionPM dto2Bo(GestionPMDTO gestionPMDTO) throws JrafDomainException { 
	
        GestionPM gestionPM = new GestionPM();
        gestionPM = dto2BoLight(gestionPMDTO);
        
        dto2BoAgencyLink(gestionPMDTO, gestionPM);
	    
        // on retourne le bo
        return gestionPM;
	}
	
    /**
     * @param listGestionPM
     * @return
     * @throws JrafDomainException
     */
    public static Set<GestionPMDTO> bo2DtoLight(Set<GestionPM> listGestionPM) throws JrafDomainException {
    	if(listGestionPM != null) 
    	{
    		Set<GestionPMDTO> listGestionPMDTO = new LinkedHashSet<GestionPMDTO>();
    		for(GestionPM gestionPM : listGestionPM) 
    		{
    			listGestionPMDTO.add(bo2DtoLight(gestionPM));
    		}
    		return listGestionPMDTO;
    	} 
    	else 
    	{
    		return null;
    	}
    }
    
    /*PROTECTED REGION END*/
}

