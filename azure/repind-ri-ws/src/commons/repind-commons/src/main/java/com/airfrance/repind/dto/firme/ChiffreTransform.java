package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_V7UEMLdcEeCrCZp8iGNNVw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.firme.Chiffre;

import java.util.LinkedHashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : ChiffreTransform.java</p>
 * transformation bo <-> dto pour un(e) Chiffre
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ChiffreTransform {

    /*PROTECTED REGION ID(_V7UEMLdcEeCrCZp8iGNNVw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ChiffreTransform() {
    }
    /**
     * dto -> bo for a Chiffre
     * @param chiffreDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Chiffre dto2BoLight(ChiffreDTO chiffreDTO) throws JrafDomainException {
        // instanciation du BO
        Chiffre chiffre = new Chiffre();
        dto2BoLight(chiffreDTO, chiffre);

        // on retourne le BO
        return chiffre;
    }

    /**
     * dto -> bo for a chiffre
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param chiffreDTO dto
     * @param chiffre bo
     */
    public static void dto2BoLight(ChiffreDTO chiffreDTO, Chiffre chiffre) {
    
        /*PROTECTED REGION ID(dto2BoLight_V7UEMLdcEeCrCZp8iGNNVw) ENABLED START*/
        
        dto2BoLightImpl(chiffreDTO,chiffre);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a chiffre
     * @param chiffreDTO dto
     * @param chiffre bo
     */
    private static void dto2BoLightImpl(ChiffreDTO chiffreDTO, Chiffre chiffre){
    
        // property of ChiffreDTO
        chiffre.setKey(chiffreDTO.getKey());
        chiffre.setType(chiffreDTO.getType());
        chiffre.setStatut(chiffreDTO.getStatut());
        chiffre.setMontant(chiffreDTO.getMontant());
        chiffre.setMonnaie(chiffreDTO.getMonnaie());
        chiffre.setLibelle(chiffreDTO.getLibelle());
        chiffre.setDateFin(chiffreDTO.getDateFin());
        chiffre.setDateDebut(chiffreDTO.getDateDebut());
        chiffre.setDateMaj(chiffreDTO.getDateMaj());
    
    }

    /**
     * bo -> dto for a chiffre
     * @param pChiffre bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ChiffreDTO bo2DtoLight(Chiffre pChiffre) throws JrafDomainException {
        // instanciation du DTO
        ChiffreDTO chiffreDTO = new ChiffreDTO();
        bo2DtoLight(pChiffre, chiffreDTO);
        // on retourne le dto
        return chiffreDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param chiffre bo
     * @param chiffreDTO dto
     */
    public static void bo2DtoLight(
        Chiffre chiffre,
        ChiffreDTO chiffreDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_V7UEMLdcEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoLightImpl(chiffre, chiffreDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param chiffre bo
     * @param chiffreDTO dto
     */
    private static void bo2DtoLightImpl(Chiffre chiffre,
        ChiffreDTO chiffreDTO){
    

        // simple properties
        chiffreDTO.setKey(chiffre.getKey());
        chiffreDTO.setType(chiffre.getType());
        chiffreDTO.setStatut(chiffre.getStatut());
        chiffreDTO.setMontant(chiffre.getMontant());
        chiffreDTO.setMonnaie(chiffre.getMonnaie());
        chiffreDTO.setLibelle(chiffre.getLibelle());
        chiffreDTO.setDateFin(chiffre.getDateFin());
        chiffreDTO.setDateDebut(chiffre.getDateDebut());
        chiffreDTO.setDateMaj(chiffre.getDateMaj());
    
    }
    
    /*PROTECTED REGION ID(_V7UEMLdcEeCrCZp8iGNNVw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    
    /**
     * @param listChiffreDTO
     * @return
     * @throws JrafDomainException
     */
    public static Set<Chiffre> dto2Bo(Set<ChiffreDTO> listChiffreDTO) throws JrafDomainException {
    	if(listChiffreDTO != null) 
    	{
    		Set<Chiffre> listChiffre = new LinkedHashSet<Chiffre>();
    		for(ChiffreDTO ChiffreDTO : listChiffreDTO) 
    		{
    			listChiffre.add(dto2BoLight(ChiffreDTO));
    		}
    		return listChiffre;
    	} 
    	else 
    	{
    		return null;
    	}
    }

    /**
     * @param listChiffre
     * @return
     * @throws JrafDomainException
     */
    public static Set<ChiffreDTO> bo2Dto(Set<Chiffre> listChiffre) throws JrafDomainException {
    	if(listChiffre != null) 
    	{
    		Set<ChiffreDTO> listChiffreDTO = new LinkedHashSet<ChiffreDTO>();
    		for(Chiffre Chiffre : listChiffre) 
    		{
    			listChiffreDTO.add(bo2DtoLight(Chiffre));
    		}
    		return listChiffreDTO;
    	} 
    	else 
    	{
    		return null;
    	}
    }
    
    /*PROTECTED REGION END*/
}

