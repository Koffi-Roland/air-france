package com.airfrance.repind.dto.firme;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.firme.NumeroIdent;
import com.airfrance.repind.entity.firme.NumeroIdentLight;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : NumeroIdentTransform.java</p>
 * transformation bo <-> dto pour un(e) NumeroIdent
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class NumeroIdentTransform {

    /*PROTECTED REGION ID(_bu8PsLdcEeCrCZp8iGNNVw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
	private static Log LOGGER = LogFactory.getLog(NumeroIdentTransform.class);
	
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private NumeroIdentTransform() {
    }
    
    /**
     * dto -> bo for a NumeroIdent
     * @param numeroIdentDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static NumeroIdent dto2(NumeroIdentDTO numeroIdentDTO) throws JrafDomainException {
        // instanciation du BO
        NumeroIdent numeroIdent = new NumeroIdent();
        dto2BoLight(numeroIdentDTO, numeroIdent);

        // on retourne le BO
        return numeroIdent;
    }
    
    /**
     * dto -> bo for a NumeroIdent
     * @param numeroIdentDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static NumeroIdent dto2BoLight(NumeroIdentDTO numeroIdentDTO) throws JrafDomainException {
        // instanciation du BO
        NumeroIdent numeroIdent = new NumeroIdent();
        dto2BoLight(numeroIdentDTO, numeroIdent);

        // on retourne le BO
        return numeroIdent;
    }

    /**
     * dto -> bo for a numeroIdent
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param numeroIdentDTO dto
     * @param numeroIdent bo
     */
    public static void dto2BoLight(NumeroIdentDTO numeroIdentDTO, NumeroIdent numeroIdent) {
    
        /*PROTECTED REGION ID(dto2BoLight_bu8PsLdcEeCrCZp8iGNNVw) ENABLED START*/
        
        dto2BoLightImpl(numeroIdentDTO,numeroIdent);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a numeroIdent
     * @param numeroIdentDTO dto
     * @param numeroIdent bo
     */
    private static void dto2BoLightImpl(NumeroIdentDTO numeroIdentDTO, NumeroIdent numeroIdent){
    
        // property of NumeroIdentDTO
        numeroIdent.setKey(numeroIdentDTO.getKey());
        numeroIdent.setStatut(numeroIdentDTO.getStatut());
        numeroIdent.setNumero(numeroIdentDTO.getNumero());
        numeroIdent.setType(numeroIdentDTO.getType());
        numeroIdent.setLibelle(numeroIdentDTO.getLibelle());
        numeroIdent.setDateOuverture(numeroIdentDTO.getDateOuverture());
        numeroIdent.setDateFermeture(numeroIdentDTO.getDateFermeture());
        numeroIdent.setDateModification(numeroIdentDTO.getDateModification());
    }
    
    /* -------------- */

    /**
     * bo -> dto for a numeroIdent
     * @param pNumeroIdent bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static NumeroIdentDTO bo2DtoLight(NumeroIdent pNumeroIdent) throws JrafDomainException {
        // instanciation du DTO
        NumeroIdentDTO numeroIdentDTO = new NumeroIdentDTO();
        bo2DtoLight(pNumeroIdent, numeroIdentDTO);
        // on retourne le dto
        return numeroIdentDTO;
    }


	/**
	 * Transforms a List of NumeroIdentLight to a Set of NumeroIdentDTO
	 * 
	 * @param pNumeroIdent bo
	 * 
	 * @return dto
	 * 
	 * @throws JrafDomainException if the DTO type is not supported
	 */
	public static Set<NumeroIdentDTO> boList2DtoLightSet(List<NumeroIdentLight> numeroIdentLightList)
			throws JrafDomainException {

		// Prepare result
		Set<NumeroIdentDTO> numIdentDTOSet = null;

		if (numeroIdentLightList != null) {
			// If emailSet is not null, then initialize return Set
			numIdentDTOSet = new HashSet<NumeroIdentDTO>();

			// Add converted DTOs one by one to the result Set
			for (NumeroIdentLight numeroIdentLight : numeroIdentLightList) {
				numIdentDTOSet.add(bo2DtoLight(numeroIdentLight));
			}
		}

		return numIdentDTOSet;
	}


	/**
	 * bo -> dto for a NumeroIdentLight
	 * 
	 * @param pNumeroIdent bo
	 * 
	 * @return dto
	 * 
	 * @throws JrafDomainException if the DTO type is not supported
	 * 
	 */
	public static NumeroIdentDTO bo2DtoLight(NumeroIdentLight numeroIdentLight) throws JrafDomainException {

		// Instantiate DTO du DTO
		NumeroIdentDTO numeroIdentDTO = new NumeroIdentDTO();

		// Fill it with data
        numeroIdentDTO.setKey(numeroIdentLight.getKey());
        numeroIdentDTO.setStatut(numeroIdentLight.getStatut());
        numeroIdentDTO.setNumero(numeroIdentLight.getNumero());
        numeroIdentDTO.setType(numeroIdentLight.getType());
        numeroIdentDTO.setLibelle(numeroIdentLight.getLibelle());
        numeroIdentDTO.setDateOuverture(numeroIdentLight.getDateOuverture());
        numeroIdentDTO.setDateFermeture(numeroIdentLight.getDateFermeture());
        numeroIdentDTO.setDateModification(numeroIdentLight.getDateModification());
        
		return numeroIdentDTO;
	}


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param numeroIdent bo
     * @param numeroIdentDTO dto
     */
    public static void bo2DtoLight(
        NumeroIdent numeroIdent,
        NumeroIdentDTO numeroIdentDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_bu8PsLdcEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoLightImpl(numeroIdent, numeroIdentDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param numeroIdent bo
     * @param numeroIdentDTO dto
     */
    private static void bo2DtoLightImpl(NumeroIdent numeroIdent,
        NumeroIdentDTO numeroIdentDTO){
    

        // simple properties
        numeroIdentDTO.setKey(numeroIdent.getKey());
        numeroIdentDTO.setStatut(numeroIdent.getStatut());
        numeroIdentDTO.setNumero(numeroIdent.getNumero());
        numeroIdentDTO.setType(numeroIdent.getType());
        numeroIdentDTO.setLibelle(numeroIdent.getLibelle());
        numeroIdentDTO.setDateOuverture(numeroIdent.getDateOuverture());
        numeroIdentDTO.setDateFermeture(numeroIdent.getDateFermeture());
        numeroIdentDTO.setDateModification(numeroIdent.getDateModification());
    
    }
    
    /*PROTECTED REGION ID(_bu8PsLdcEeCrCZp8iGNNVw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    
    /**
     * bo -> dto for a numeroIdent with its links
     * @param pNumeroIdent bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static NumeroIdentDTO bo2Dto(NumeroIdent pNumeroIdent) throws JrafDomainException {
        // instanciation du DTO
        NumeroIdentDTO numeroIdentDTO = new NumeroIdentDTO();
        bo2Dto(pNumeroIdent, numeroIdentDTO);
        // on retourne le dto
        return numeroIdentDTO;
    }


    /**
     * Transform a business object to DTO.
     * "with links method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * calls bo2DtoLink to get its links
     * @param numeroIdent bo
     * @param numeroIdentDTO dto
     */
    public static void bo2Dto(
        NumeroIdent numeroIdent,
        NumeroIdentDTO numeroIdentDTO) {

        bo2DtoLightImpl(numeroIdent, numeroIdentDTO);
        bo2DtoLink(numeroIdent, numeroIdentDTO);
    }
    
    /**
     * @param numeroIdent bo
     * @param numeroIdentDTO dto
     */
    public static void bo2DtoLink(NumeroIdent numeroIdent, NumeroIdentDTO numeroIdentDTO) {
        // Lien avec PERS_MORALE
        if (numeroIdent.getPersonneMorale() != null) {
            try {
                LOGGER.warn("Lien avec PERS_MORALE");
                numeroIdentDTO.setPersonneMorale(PersonneMoraleTransform.bo2DtoLight(numeroIdent.getPersonneMorale()));
            } catch (JrafDomainException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e);
                }
            }
        }       
    }
    
    /**
     * @param listNumeroIDentDTO
     * @return listNumeroIdent
     * @throws JrafDomainException
     */
    public static Set<NumeroIdent> dto2Bo(Set<NumeroIdentDTO> listNumeroIdentDTO) throws JrafDomainException {
    	if(listNumeroIdentDTO != null) 
    	{
    		Set<NumeroIdent> listNumeroIdent = new LinkedHashSet<NumeroIdent>();
    		for(NumeroIdentDTO numeroidentdto : listNumeroIdentDTO) 
    		{
    			listNumeroIdent.add(dto2BoLight(numeroidentdto));
    		}
    		return listNumeroIdent;
    	} 
    	else 
    	{
    		return null;
    	}
    }

    /**
     * @param listNumeroIdent
     * @return listNumeroIDentDTO
     * @throws JrafDomainException
     */
    public static Set<NumeroIdentDTO> bo2Dto(Set<NumeroIdent> listNumeroIdent) throws JrafDomainException {
    	if(listNumeroIdent != null) 
    	{
    		Set<NumeroIdentDTO> listNumeroIdentDTO = new LinkedHashSet<NumeroIdentDTO>();
    		for(NumeroIdent numeroident : listNumeroIdent) 
    		{
    			listNumeroIdentDTO.add(bo2DtoLight(numeroident));
    		}
    		return listNumeroIdentDTO;
    	} 
    	else 
    	{
    		return null;
    	}
    }
    

    /**
     * Converts a NumeroIdent entity to DTO.
     * 
     * @param numIdentList
     * 
     * @return
     * 
     * @throws JrafDomainException if a problem happens with the conversion of an element.
     */
	public static Set<NumeroIdentDTO> bo2DtoLight(List<NumeroIdent> numIdentList) throws JrafDomainException {

		// Prepare result
		Set<NumeroIdentDTO> numIdentDTOSet = null;

		if (numIdentList != null) {
			// If emailSet is not null, then initialize return Set
			numIdentDTOSet = new HashSet<NumeroIdentDTO>();

			// Add converted DTOs one by one to the result Set
			for (NumeroIdent numeroIdent : numIdentList) {
				numIdentDTOSet.add(bo2DtoLight(numeroIdent));
			}
		}

		return numIdentDTOSet;
	}

    
    
    /*PROTECTED REGION END*/
}

