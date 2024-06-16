package com.airfrance.repind.dto.firme;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.firme.*;
import com.airfrance.repind.util.SicStringUtils;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : EtablissementTransform.java</p>
 * transformation bo <-> dto pour un(e) Etablissement
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class EtablissementTransform {

    /*PROTECTED REGION ID(_YbWYALdcEeCrCZp8iGNNVw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private EtablissementTransform() {
    }
    /**
     * dto -> bo for a Etablissement
     * @param etablissementDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Etablissement dto2BoLight(EtablissementDTO etablissementDTO) throws JrafDomainException {
        return (Etablissement)PersonneMoraleTransform.dto2BoLight(etablissementDTO);
    }

    /**
     * dto -> bo for a etablissement
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param etablissementDTO dto
     * @param etablissement bo
     */
    public static void dto2BoLight(EtablissementDTO etablissementDTO, Etablissement etablissement) {
    
        /*PROTECTED REGION ID(dto2BoLight_YbWYALdcEeCrCZp8iGNNVw) ENABLED START*/
        
        dto2BoLightImpl(etablissementDTO,etablissement);
        
        etablissement.setRem(SicStringUtils.toUpperCaseWithoutAccentsForFirms(etablissementDTO.getRem()));
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a etablissement
     * @param etablissementDTO dto
     * @param etablissement bo
     */
    private static void dto2BoLightImpl(EtablissementDTO etablissementDTO, Etablissement etablissement){
    
        // superclass property
        PersonneMoraleTransform.dto2BoLight(etablissementDTO, etablissement);
        // property of EtablissementDTO
        etablissement.setType(etablissementDTO.getType());
        etablissement.setGinAgence(etablissementDTO.getGinAgence());
        etablissement.setSiret(etablissementDTO.getSiret());
        etablissement.setSiegeSocial(etablissementDTO.getSiegeSocial());
        etablissement.setCe(etablissementDTO.getCe());
        etablissement.setRem(etablissementDTO.getRem());
    
    }

    /**
     * bo -> dto for a etablissement
     * @param etablissement bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static EtablissementDTO bo2DtoLight(Etablissement etablissement) throws JrafDomainException {
        return (EtablissementDTO)PersonneMoraleTransform.bo2DtoLight(etablissement);
    }
    
    /**
     * This method add missing segmentation value in return
     * bo -> dto for a etablissement
     * @param etablissement bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static EtablissementDTO bo2DtoLightForBatch(Etablissement etablissement) throws JrafDomainException {
        return (EtablissementDTO)PersonneMoraleTransform.bo2DtoLightForBatch(etablissement);
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param etablissement bo
     * @param etablissementDTO dto
     * @throws JrafDomainException 
     */
    public static void bo2DtoLight(
        Etablissement etablissement,
        EtablissementDTO etablissementDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(bo2DtoLight_YbWYALdcEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoLightImpl(etablissement, etablissementDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * This method add missing segmentation value in return
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param etablissement bo
     * @param etablissementDTO dto
     * @throws JrafDomainException 
     */
    public static void bo2DtoLightForBatch(
        Etablissement etablissement,
        EtablissementDTO etablissementDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(bo2DtoLight_YbWYALdcEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoLightImplForBatch(etablissement, etablissementDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param etablissement bo
     * @param etablissementDTO dto
     * @throws JrafDomainException 
     */
    private static void bo2DtoLightImpl(Etablissement etablissement,
        EtablissementDTO etablissementDTO) throws JrafDomainException{
    
        // superclass property
        PersonneMoraleTransform.bo2DtoLight(etablissement, etablissementDTO);

        // simple properties
        etablissementDTO.setType(etablissement.getType());
        etablissementDTO.setGinAgence(etablissement.getGinAgence());
        etablissementDTO.setSiret(etablissement.getSiret());
        etablissementDTO.setSiegeSocial(etablissement.getSiegeSocial());
        etablissementDTO.setCe(etablissement.getCe());
        etablissementDTO.setRem(etablissement.getRem());
    }
    
    /**
     * This method add missing segmentation value in return
     * Transform a business object to DTO. Implementation method
     * @param etablissement bo
     * @param etablissementDTO dto
     * @throws JrafDomainException 
     */
    private static void bo2DtoLightImplForBatch(Etablissement etablissement,
        EtablissementDTO etablissementDTO) throws JrafDomainException{
    
        // superclass property
        PersonneMoraleTransform.bo2DtoLightForBatch(etablissement, etablissementDTO);

        // simple properties
        etablissementDTO.setType(etablissement.getType());
        etablissementDTO.setGinAgence(etablissement.getGinAgence());
        etablissementDTO.setSiret(etablissement.getSiret());
        etablissementDTO.setSiegeSocial(etablissement.getSiegeSocial());
        etablissementDTO.setCe(etablissement.getCe());
        etablissementDTO.setRem(etablissement.getRem());
    }


    /**
     * Adds children to etablissement DTO if they were loaded
     * in the etablissement entity.
     * 
     * @param etablissement
     * @param etablissementDTO
     */
	public static void bo2DtoEnfants(Etablissement etablissement, EtablissementDTO etablissementDTO) {
		
		if (!Hibernate.isInitialized(etablissement.getEnfants())){
    		etablissementDTO.setEnfants(null);
    	}
    	else if (etablissement.getEnfants() != null) {
    		// Initialize list of children
    		Set<PersonneMorale> enfantsList = etablissement.getEnfants();
    		
    		if (enfantsList != null) {
    			
    			// Initialize list of enfants for DTO
    			etablissementDTO.setEnfants(new HashSet<PersonneMoraleDTO>());
    			
    			// Add services to DTO children
        		for (PersonneMorale enfant : enfantsList) {
        			if (enfant != null) {
    					PersonneMoraleDTO enfantDTO = null;

        				// Only GIN and name are required
    					if (enfant instanceof Service) {
        					enfantDTO = new ServiceDTO();
        				}else if (enfant instanceof Groupe) {
        					enfantDTO = new GroupeDTO();
        				} else if (enfant instanceof Entreprise) {
        					enfantDTO = new EntrepriseDTO();
        				} else {
        					enfantDTO = new EtablissementDTO();
        				}

        				enfantDTO.setNom(enfant.getNom());
        				enfantDTO.setGin(enfant.getGin());
        	    		etablissementDTO.getEnfants().add(enfantDTO);
        			}
        		}
    		}
    	}
	}
	
	
	/**
	 * Transforms a BO to a DTO.
	 * 
	 * @param personneMoraleLightToConvert
	 * @param personneMoraleDTO
	 */
	public static void bo2DtoLight(EtablissementLight etablissementLight,
			EtablissementDTO etablissementDTO) {

		if (etablissementLight != null) {
			// Make the conversion
	        PersonneMoraleTransform.bo2DtoLight(etablissementLight, etablissementDTO);
	        etablissementDTO.setType(etablissementLight.getType());
	        etablissementDTO.setGinAgence(etablissementLight.getGinAgence());
	        etablissementDTO.setSiret(etablissementLight.getSiret());
	        etablissementDTO.setSiegeSocial(etablissementLight.getSiegeSocial());
	        etablissementDTO.setCe(etablissementLight.getCe());
	        etablissementDTO.setRem(etablissementLight.getRem());
		}
	}


    /*PROTECTED REGION ID(_YbWYALdcEeCrCZp8iGNNVw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

