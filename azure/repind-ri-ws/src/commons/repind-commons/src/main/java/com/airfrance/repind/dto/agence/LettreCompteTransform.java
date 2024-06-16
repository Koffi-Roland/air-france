package com.airfrance.repind.dto.agence;

/*PROTECTED REGION ID(_0VRuBWk1EeGhB9497mGnHw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.agence.LettreCompte;

import java.util.ArrayList;
import java.util.List;

/*PROTECTED REGION END*/

/**
 * <p>Title : LettreCompteTransform.java</p>
 * transformation bo <-> dto pour un(e) LettreCompte
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class LettreCompteTransform {

    /*PROTECTED REGION ID(_0VRuBWk1EeGhB9497mGnHw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private LettreCompteTransform() {
    }
    /**
     * dto -> bo for a LettreCompte
     * @param lettreCompteDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static LettreCompte dto2BoLight(LettreCompteDTO lettreCompteDTO) throws JrafDomainException {
        // instanciation du BO
        LettreCompte lettreCompte = new LettreCompte();
        dto2BoLight(lettreCompteDTO, lettreCompte);

        // on retourne le BO
        return lettreCompte;
    }

    /**
     * dto -> bo for a lettreCompte
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param lettreCompteDTO dto
     * @param lettreCompte bo
     */
    public static void dto2BoLight(LettreCompteDTO lettreCompteDTO, LettreCompte lettreCompte) {
    
        /*PROTECTED REGION ID(dto2BoLight_0VRuBWk1EeGhB9497mGnHw) ENABLED START*/
        
        dto2BoLightImpl(lettreCompteDTO,lettreCompte);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a lettreCompte
     * @param lettreCompteDTO dto
     * @param lettreCompte bo
     */
    private static void dto2BoLightImpl(LettreCompteDTO lettreCompteDTO, LettreCompte lettreCompte){
    
        // property of LettreCompteDTO
        lettreCompte.setIcle(lettreCompteDTO.getIcle());
        lettreCompte.setSgin(lettreCompteDTO.getSgin());
        lettreCompte.setSlettreComptoir(lettreCompteDTO.getSlettreComptoir());
    
    }

    /**
     * bo -> dto for a lettreCompte
     * @param pLettreCompte bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static LettreCompteDTO bo2DtoLight(LettreCompte pLettreCompte) throws JrafDomainException {
        // instanciation du DTO
        LettreCompteDTO lettreCompteDTO = new LettreCompteDTO();
        bo2DtoLight(pLettreCompte, lettreCompteDTO);
        // on retourne le dto
        return lettreCompteDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param lettreCompte bo
     * @param lettreCompteDTO dto
     */
    public static void bo2DtoLight(
        LettreCompte lettreCompte,
        LettreCompteDTO lettreCompteDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_0VRuBWk1EeGhB9497mGnHw) ENABLED START*/

        bo2DtoLightImpl(lettreCompte, lettreCompteDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param lettreCompte bo
     * @param lettreCompteDTO dto
     */
    private static void bo2DtoLightImpl(LettreCompte lettreCompte,
        LettreCompteDTO lettreCompteDTO){
    

        // simple properties
        lettreCompteDTO.setIcle(lettreCompte.getIcle());
        lettreCompteDTO.setSgin(lettreCompte.getSgin());
        lettreCompteDTO.setSlettreComptoir(lettreCompte.getSlettreComptoir());
    
    }
    
    /*PROTECTED REGION ID(_0VRuBWk1EeGhB9497mGnHw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    
    /**
     * @param listLettreCompteDTO
     * @return
     * @throws JrafDomainException
     */
    public static List<LettreCompte> dto2Bo(List<LettreCompteDTO> listLettreCompteDTO) throws JrafDomainException {
    	if(listLettreCompteDTO != null) 
    	{
    		List<LettreCompte> listLettreCompte = new ArrayList<LettreCompte>();
    		for(LettreCompteDTO lettrecomptedto : listLettreCompteDTO) 
    		{
    			listLettreCompte.add(dto2BoLight(lettrecomptedto));
    		}
    		return listLettreCompte;
    	} 
    	else 
    	{
    		return null;
    	}
    }

    /**
     * @param listLettreCompte
     * 
     * @throws JrafDomainException
     */
    public static List<LettreCompteDTO> bo2Dto(List<LettreCompte> listLettreCompte) throws JrafDomainException {
    	if(listLettreCompte != null) 
    	{
    		List<LettreCompteDTO> listLettreCompteDTO = new ArrayList<LettreCompteDTO>();
    		for(LettreCompte lettrecompte : listLettreCompte) 
    		{
    			listLettreCompteDTO.add(bo2DtoLight(lettrecompte));
    		}
    		return listLettreCompteDTO;
    	} 
    	else 
    	{
    		return null;
    	}
    }
    /*PROTECTED REGION END*/
}

