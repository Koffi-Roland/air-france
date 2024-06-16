package com.afklm.rigui.dto.profil;

/*PROTECTED REGION ID(_S9GW1jUfEeCq6pHdxM8RnQ i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.profil.Profils;

import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : ProfilsTransform.java</p>
 * transformation bo <-> dto pour un(e) Profils
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ProfilsTransform {

    /*PROTECTED REGION ID(_S9GW1jUfEeCq6pHdxM8RnQ u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ProfilsTransform() {
    }
    /**
     * dto -> bo for a Profils
     * @param profilsDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Profils dto2BoLight(ProfilsDTO profilsDTO) throws JrafDomainException {
        // instanciation du BO
        Profils profils = new Profils();
        if (profilsDTO != null) {
        	dto2BoLight(profilsDTO, profils);
        	// on retourne le BO
        	return profils;
        } 
        else {
        	return null;
        }

    }

    /**
     * dto -> bo for a profils
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param profilsDTO dto
     * @param profils bo
     */
    public static void dto2BoLight(ProfilsDTO profilsDTO, Profils profils) {
    
        /*PROTECTED REGION ID(dto2BoLight_S9GW1jUfEeCq6pHdxM8RnQ) ENABLED START*/
        
        dto2BoLightImpl(profilsDTO,profils);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a profils
     * @param profilsDTO dto
     * @param profils bo
     */
    private static void dto2BoLightImpl(ProfilsDTO profilsDTO, Profils profils){
    
        // property of ProfilsDTO
        profils.setIversion(profilsDTO.getIversion());
        profils.setSmailing_autorise(profilsDTO.getSmailing_autorise());
        profils.setSrin(profilsDTO.getSrin());
        profils.setSsolvabilite(profilsDTO.getSsolvabilite());
        profils.setScode_professionnel(profilsDTO.getScode_professionnel());
        profils.setScode_maritale(profilsDTO.getScode_maritale());
        profils.setScode_langue(profilsDTO.getScode_langue());
        profils.setSgin(profilsDTO.getSgin());
        profils.setScode_fonction(profilsDTO.getScode_fonction());
        profils.setInb_enfants(profilsDTO.getInb_enfants());
        profils.setSsegment(profilsDTO.getSsegment());
        profils.setSetudiant(profilsDTO.getSetudiant());
    
    }

    /**
     * bo -> dto for a profils
     * @param pProfils bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ProfilsDTO bo2DtoLight(Profils pProfils) throws JrafDomainException {
        // instanciation du DTO
        ProfilsDTO profilsDTO = new ProfilsDTO();
        
        if (pProfils != null) {
        	bo2DtoLight(pProfils, profilsDTO);
        	// on retourne le dto
        	return profilsDTO;
        }
        else {
        	return null;
        }
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param profils bo
     * @param profilsDTO dto
     */
    public static void bo2DtoLight(
        Profils profils,
        ProfilsDTO profilsDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_S9GW1jUfEeCq6pHdxM8RnQ) ENABLED START*/

        bo2DtoLightImpl(profils, profilsDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param profils bo
     * @param profilsDTO dto
     */
    private static void bo2DtoLightImpl(Profils profils,
        ProfilsDTO profilsDTO){
    

        // simple properties
        profilsDTO.setIversion(profils.getIversion());
        profilsDTO.setSmailing_autorise(profils.getSmailing_autorise());
        profilsDTO.setSrin(profils.getSrin());
        profilsDTO.setSsolvabilite(profils.getSsolvabilite());
        profilsDTO.setScode_professionnel(profils.getScode_professionnel());
        profilsDTO.setScode_maritale(profils.getScode_maritale());
        profilsDTO.setScode_langue(profils.getScode_langue());
        profilsDTO.setSgin(profils.getSgin());
        profilsDTO.setScode_fonction(profils.getScode_fonction());
        profilsDTO.setInb_enfants(profils.getInb_enfants());
        profilsDTO.setSsegment(profils.getSsegment());
        profilsDTO.setSetudiant(profils.getSetudiant());
    
    }
    
    /*PROTECTED REGION ID(_S9GW1jUfEeCq6pHdxM8RnQ u m - Tr) ENABLED START*/

    /**
     * @param listProfils
     * @return
     * @throws JrafDomainException
     */
    public static Set<ProfilsDTO> bo2Dto(Set<Profils> listProfils) throws JrafDomainException {
    	if(listProfils != null) {
    		Set<ProfilsDTO> listProfilsDTO = new HashSet<ProfilsDTO>();
    		for(Profils profil : listProfils) {
    			listProfilsDTO.add(bo2DtoLight(profil));
    		}
    		return listProfilsDTO;
    	} else {
    		return null;
    	}
    }

    /**
     * @param listProfilsDTO
     * @return
     * @throws JrafDomainException
     */
    public static Set<Profils> dto2Bo(Set<ProfilsDTO> listProfilsDTO) throws JrafDomainException {
    	if(listProfilsDTO != null) {
    		Set<Profils> listProfils = new HashSet<Profils>();
    		for(ProfilsDTO profilsdto : listProfilsDTO) {
    			listProfils.add(dto2BoLight(profilsdto));
    		}
    		return listProfils;
    	} else {
    		return null;
    	}
    }
    /*PROTECTED REGION END*/
}

