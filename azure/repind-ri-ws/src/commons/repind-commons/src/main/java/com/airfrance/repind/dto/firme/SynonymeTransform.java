package com.airfrance.repind.dto.firme;

/*PROTECTED REGION ID(_eCK2kLdcEeCrCZp8iGNNVw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.firme.PersonneMorale;
import com.airfrance.repind.entity.firme.Synonyme;
import com.airfrance.repind.util.SicStringUtils;

import javax.persistence.EntityManager;
import java.util.LinkedHashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : SynonymeTransform.java</p>
 * transformation bo <-> dto pour un(e) Synonyme
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class SynonymeTransform {

    /*PROTECTED REGION ID(_eCK2kLdcEeCrCZp8iGNNVw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private SynonymeTransform() {
    }
    /**
     * dto -> bo for a Synonyme
     * @param synonymeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Synonyme dto2BoLight(SynonymeDTO synonymeDTO) throws JrafDomainException {
        // instanciation du BO
        Synonyme synonyme = new Synonyme();
        dto2BoLight(synonymeDTO, synonyme);

        // on retourne le BO
        return synonyme;
    }

    /**
     * dto -> bo for a synonyme
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param synonymeDTO dto
     * @param synonyme bo
     */
    public static void dto2BoLight(SynonymeDTO synonymeDTO, Synonyme synonyme) {
    
        /*PROTECTED REGION ID(dto2BoLight_eCK2kLdcEeCrCZp8iGNNVw) ENABLED START*/
        
        dto2BoLightImpl(synonymeDTO,synonyme);
        
        synonyme.setNom(SicStringUtils.toUpperCaseWithoutAccentsForFirms(synonymeDTO.getNom()));
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a synonyme
     * @param synonymeDTO dto
     * @param synonyme bo
     */
    private static void dto2BoLightImpl(SynonymeDTO synonymeDTO, Synonyme synonyme){
    
        // property of SynonymeDTO
        synonyme.setCle(synonymeDTO.getCle());
        synonyme.setStatut(synonymeDTO.getStatut());
        synonyme.setNom(synonymeDTO.getNom());
        synonyme.setType(synonymeDTO.getType());
        synonyme.setDateModificationSnom(synonymeDTO.getDateModificationSnom());
    
    }

    /**
     * bo -> dto for a synonyme
     * @param pSynonyme bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static SynonymeDTO bo2DtoLight(Synonyme pSynonyme) throws JrafDomainException {
        // instanciation du DTO
        SynonymeDTO synonymeDTO = new SynonymeDTO();
        bo2DtoLight(pSynonyme, synonymeDTO);
        // on retourne le dto
        return synonymeDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param synonyme bo
     * @param synonymeDTO dto
     */
    public static void bo2DtoLight(
        Synonyme synonyme,
        SynonymeDTO synonymeDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_eCK2kLdcEeCrCZp8iGNNVw) ENABLED START*/

        bo2DtoLightImpl(synonyme, synonymeDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param synonyme bo
     * @param synonymeDTO dto
     */
    private static void bo2DtoLightImpl(Synonyme synonyme,
        SynonymeDTO synonymeDTO){
    

        // simple properties
        synonymeDTO.setCle(synonyme.getCle());
        synonymeDTO.setStatut(synonyme.getStatut());
        synonymeDTO.setNom(synonyme.getNom());
        synonymeDTO.setType(synonyme.getType());
        synonymeDTO.setDateModificationSnom(synonyme.getDateModificationSnom());
    
    }
    
    /*PROTECTED REGION ID(_eCK2kLdcEeCrCZp8iGNNVw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    
    /**
     * @param listSynonymeDTO
     * @return
     * @throws JrafDomainException
     */
    public static Set<Synonyme> dto2Bo(Set<SynonymeDTO> listSynonymeDTO) throws JrafDomainException {
    	if(listSynonymeDTO != null) 
    	{
    		Set<Synonyme> listSynonyme = new LinkedHashSet<Synonyme>();
    		for(SynonymeDTO SynonymeDTO : listSynonymeDTO) 
    		{
    			listSynonyme.add(dto2BoLight(SynonymeDTO));
    		}
    		return listSynonyme;
    	} 
    	else 
    	{
    		return null;
    	}
    }

    /**
     * @param listSynonyme
     * @return
     * @throws JrafDomainException
     */
    public static Set<SynonymeDTO> bo2Dto(Set<Synonyme> listSynonyme) throws JrafDomainException {
    	if(listSynonyme != null) 
    	{
    		Set<SynonymeDTO> listSynonymeDTO = new LinkedHashSet<SynonymeDTO>();
    		for(Synonyme synonyme : listSynonyme) 
    		{
    			listSynonymeDTO.add(bo2DtoLight(synonyme));
    		}
    		return listSynonymeDTO;
    	} 
    	else 
    	{
    		return null;
    	}
    }
    
    public static void dto2BoLink(EntityManager entityManager, SynonymeDTO dto, Synonyme bo) throws JrafDomainException {
        
        if (dto.getPersonneMorale() != null && dto.getPersonneMorale().getGin() != null) {
            
            PersonneMorale proxyPersonneMorale = entityManager.getReference(PersonneMorale.class, dto.getPersonneMorale().getGin());
            bo.setPersonneMorale(proxyPersonneMorale);
        }
    }
    /*PROTECTED REGION END*/
}

