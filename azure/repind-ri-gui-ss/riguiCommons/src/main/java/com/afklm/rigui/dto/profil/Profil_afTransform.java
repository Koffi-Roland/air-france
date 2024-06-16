package com.afklm.rigui.dto.profil;

/*PROTECTED REGION ID(_S9GWyTUfEeCq6pHdxM8RnQ i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.profil.Profil_af;
import com.afklm.rigui.entity.profil.Profil_mere;

import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : Profil_afTransform.java</p>
 * transformation bo <-> dto pour un(e) Profil_af
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class Profil_afTransform {

    /*PROTECTED REGION ID(_S9GWyTUfEeCq6pHdxM8RnQ u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private Profil_afTransform() {
    }
    /**
     * dto -> bo for a Profil_af
     * @param profil_afDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Profil_af dto2BoLight(Profil_afDTO profil_afDTO) throws JrafDomainException {
        // instanciation du BO
        Profil_af profil_af = new Profil_af();
        dto2BoLight(profil_afDTO, profil_af);

        // on retourne le BO
        return profil_af;
    }

    /**
     * dto -> bo for a profil_af
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param profil_afDTO dto
     * @param profil_af bo
     */
    public static void dto2BoLight(Profil_afDTO profil_afDTO, Profil_af profil_af) {
    
        /*PROTECTED REGION ID(dto2BoLight_S9GWyTUfEeCq6pHdxM8RnQ) ENABLED START*/
        
        dto2BoLightImpl(profil_afDTO,profil_af);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a profil_af
     * @param profil_afDTO dto
     * @param profil_af bo
     */
    private static void dto2BoLightImpl(Profil_afDTO profil_afDTO, Profil_af profil_af){
    
        // property of Profil_afDTO
        profil_af.setIcle_prf(profil_afDTO.getIcle_prf());
        profil_af.setSmatricule(profil_afDTO.getSmatricule());
        profil_af.setSrang(profil_afDTO.getSrang());
        profil_af.setSadr_notes(profil_afDTO.getSadr_notes());
        profil_af.setSpasswrd(profil_afDTO.getSpasswrd());
        profil_af.setSnom_prf_hab(profil_afDTO.getSnom_prf_hab());
        profil_af.setSfonction(profil_afDTO.getSfonction());
        profil_af.setSreference_r(profil_afDTO.getSreference_r());
        profil_af.setStypologie(profil_afDTO.getStypologie());
        profil_af.setScode_origine(profil_afDTO.getScode_origine());
        profil_af.setScode_cie(profil_afDTO.getScode_cie());
        profil_af.setScode_status(profil_afDTO.getScode_status());
    
    }

    /**
     * bo -> dto for a profil_af
     * @param pProfil_af bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static Profil_afDTO bo2DtoLight(Profil_af pProfil_af) throws JrafDomainException {
        // instanciation du DTO
        Profil_afDTO profil_afDTO = new Profil_afDTO();
        bo2DtoLight(pProfil_af, profil_afDTO);
        // on retourne le dto
        return profil_afDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param profil_af bo
     * @param profil_afDTO dto
     */
    public static void bo2DtoLight(
        Profil_af profil_af,
        Profil_afDTO profil_afDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_S9GWyTUfEeCq6pHdxM8RnQ) ENABLED START*/

        bo2DtoLightImpl(profil_af, profil_afDTO);
        
        bo2DtoLink(profil_af, profil_afDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param profil_af bo
     * @param profil_afDTO dto
     */
    private static void bo2DtoLightImpl(Profil_af profil_af,
        Profil_afDTO profil_afDTO){
    

        // simple properties
        profil_afDTO.setIcle_prf(profil_af.getIcle_prf());
        profil_afDTO.setSmatricule(profil_af.getSmatricule());
        profil_afDTO.setSrang(profil_af.getSrang());
        profil_afDTO.setSadr_notes(profil_af.getSadr_notes());
        profil_afDTO.setSpasswrd(profil_af.getSpasswrd());
        profil_afDTO.setSnom_prf_hab(profil_af.getSnom_prf_hab());
        profil_afDTO.setSfonction(profil_af.getSfonction());
        profil_afDTO.setSreference_r(profil_af.getSreference_r());
        profil_afDTO.setStypologie(profil_af.getStypologie());
        profil_afDTO.setScode_origine(profil_af.getScode_origine());
        profil_afDTO.setScode_cie(profil_af.getScode_cie());
        profil_afDTO.setScode_status(profil_af.getScode_status());
    
    }
    
    /*PROTECTED REGION ID(_S9GWyTUfEeCq6pHdxM8RnQ u m - Tr) ENABLED START*/

    /**
     * @param listProfil_af
     * @return
     * @throws JrafDomainException
     */
    public static Set<Profil_afDTO> bo2Dto(Set<Profil_af> listProfil_afs) throws JrafDomainException {
    	if(listProfil_afs != null) {
    		Set<Profil_afDTO> listProfil_afsDTO = new HashSet<Profil_afDTO>();
    		for(Profil_af profil_af : listProfil_afs) {
    			listProfil_afsDTO.add(bo2DtoLight(profil_af));
    		}
    		return listProfil_afsDTO;
    	} else {
    		return null;
    	}
    }

    /**
     * @param listProfil_afsDTO
     * @return
     * @throws JrafDomainException
     */
    public static Set<Profil_af> dto2Bo(Set<Profil_afDTO> listProfil_afsDTO) throws JrafDomainException {
    	if(listProfil_afsDTO != null) {
    		Set<Profil_af> listProfil_afs = new HashSet<Profil_af>();
    		for(Profil_afDTO profil_afdto : listProfil_afsDTO) {
    			listProfil_afs.add(dto2BoLight(profil_afdto));
    		}
    		return listProfil_afs;
    	} else {
    		return null;
    	}
    }
	/**
	 * @param profil_af
	 * @param profil_afDTO
	 * @return
	 */
	public static void bo2DtoLink(Profil_af profil_af, Profil_afDTO profil_afDTO) {

		Profil_mere profil_mere = profil_af.getProfil_mere();

		if(profil_mere!=null) {
			try {
				Profil_mereDTO profil_mereDTO = Profil_mereTransform.bo2DtoLight(profil_mere);
				profil_afDTO.setProfil_mereDTO(profil_mereDTO);
			} catch (JrafDomainException e) {
//				LOGGER.error(e);
			}
		}

	}

	/**
	 * @param profil_afDTO
	 * @param profil_af
	 * @return
	 */
	public static void dto2BoLink(Profil_afDTO profil_afDTO,Profil_af profil_af) {

		Profil_mereDTO profil_mereDTO = profil_afDTO.getProfil_mereDTO();

		if(profil_mereDTO!=null) {
			try {
				Profil_mere profil_mere = Profil_mereTransform.dto2BoLight(profil_mereDTO);
				profil_af.setProfil_mere(profil_mere);
			} catch (JrafDomainException e) {
//				LOGGER.error(e);
			}
		}
	}
	/*PROTECTED REGION END*/
}

