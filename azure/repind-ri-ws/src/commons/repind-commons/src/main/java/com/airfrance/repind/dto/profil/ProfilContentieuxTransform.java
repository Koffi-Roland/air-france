package com.airfrance.repind.dto.profil;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.profil.ProfilContentieux;

public class ProfilContentieuxTransform {

	public static ProfilContentieuxDTO bo2DtoLight(ProfilContentieux profilContentieux) throws JrafDomainException {
		    // instanciation du DTO
	        ProfilContentieuxDTO profilContentieuxDTO = new ProfilContentieuxDTO();
	        bo2DtoLight(profilContentieux, profilContentieuxDTO);
	        // on retourne le dto
	        return profilContentieuxDTO;
	    }
		

	 /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param profilContentieux bo
     * @param profilContentieuxDTO dto
     */
    public static void bo2DtoLight(
    		ProfilContentieux profil,
        ProfilContentieuxDTO profilDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_URgWwGkyEeGhB9497mGnHw) ENABLED START*/

        bo2DtoLightImpl(profil, profilDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param profilDemarchage bo
     * @param profilDemarchargeDTO dto
     */
    private static void bo2DtoLightImpl(ProfilContentieux profil,
    		ProfilContentieuxDTO profilDTO){
    

        // simple properties
    	profilDTO.setCle(profil.getCle());
    	profilDTO.setAgrementAF(profil.getAgrementAF());
    	profilDTO.setDateDebutDefaut(profil.getDateDebutDefaut());
    	profilDTO.setDateDebutRed(profil.getDateDebutRed());
    	profilDTO.setDateFinDefaut(profil.getDateFinDefaut());
    	profilDTO.setDateFinRed(profil.getDateFinRed());
    	profilDTO.setDateLiquidation(profil.getDateLiquidation());
    	profilDTO.setDateRepriseEmission(profil.getDateRepriseEmission());
    	profilDTO.setDateSuspenEmission(profil.getDateSuspenEmission());
    	profilDTO.setDefautPaiement(profil.getDefautPaiement());
    	profilDTO.setMiseEnCash(profil.getMiseEnCash());
    	profilDTO.setRedressement(profil.getRedressement());   
    
    }


	public static ProfilContentieux dto2BoLight(
			ProfilContentieuxDTO profilContentieux) {
		// TODO Auto-generated method stub
		return null;
	}
}
