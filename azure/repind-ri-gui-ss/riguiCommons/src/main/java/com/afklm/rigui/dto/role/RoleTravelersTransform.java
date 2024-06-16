package com.afklm.rigui.dto.role;

/*PROTECTED REGION ID(_4up_kEjdEeaaO77HTw9BUw i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.role.BusinessRole;
import com.afklm.rigui.entity.role.RoleTravelers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : RoleTravelersTransform.java</p>
 * transformation bo <-> dto pour un(e) RoleTravelers
 * <p>Copyright : Copyright (c) 2016</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RoleTravelersTransform {
    private static Log LOGGER  = LogFactory.getLog(RoleTravelersTransform.class);

    
    /**
     * private constructor
     */
    private RoleTravelersTransform() {
    }
    /**
     * dto -> bo for a RoleTravelers
     * @param roleTravelersDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RoleTravelers dto2BoLight(RoleTravelersDTO roleTravelersDTO) throws JrafDomainException {
        // instanciation du BO
        RoleTravelers roleTravelers = new RoleTravelers();
        dto2BoLight(roleTravelersDTO, roleTravelers);
        // dto2BoLink(roleTravelersDTO, roleTravelers);      //TODO cette ligne ne devrait pas être dans cette méthode
        // on retourne le BO
        return roleTravelers;
    }

    /**
     * dto -> bo for a roleTravelers
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param roleTravelersDTO dto
     * @param roleTravelers bo
     */
    public static void dto2BoLight(RoleTravelersDTO roleTravelersDTO, RoleTravelers roleTravelers) {
    
        /*PROTECTED REGION ID(dto2BoLight_4up_kEjdEeaaO77HTw9BUw) ENABLED START*/
        
        dto2BoLightImpl(roleTravelersDTO,roleTravelers);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a roleTravelers
     * @param roleTravelersDTO dto
     * @param roleTravelers bo
     */
    private static void dto2BoLightImpl(RoleTravelersDTO roleTravelersDTO, RoleTravelers roleTravelers){
    
        // property of RoleTravelersDTO
        roleTravelers.setCleRole(roleTravelersDTO.getCleRole());
        roleTravelers.setGin(roleTravelersDTO.getGin());
        roleTravelers.setLastRecognitionDate(roleTravelersDTO.getLastRecognitionDate());
        roleTravelers.setMatchingRecognitionCode(roleTravelersDTO.getMatchingRecognitionCode());
        roleTravelers.setDateCreation(roleTravelersDTO.getDateCreation());
        roleTravelers.setSignatureCreation(roleTravelersDTO.getSignatureCreation());
        roleTravelers.setSiteCreation(roleTravelersDTO.getSiteCreation());
        roleTravelers.setDateModification(roleTravelersDTO.getDateModification());
        roleTravelers.setSignatureModification(roleTravelersDTO.getSignatureModification());
        roleTravelers.setSiteModification(roleTravelersDTO.getSiteModification());
    }

    /**
     * bo -> dto for a roleTravelers
     * @param pRoleTravelers bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RoleTravelersDTO bo2Dto(RoleTravelers pRoleTravelers) throws JrafDomainException {
        // instanciation du DTO
        RoleTravelersDTO roleTravelersDTO = new RoleTravelersDTO();
        bo2DtoLight(pRoleTravelers, roleTravelersDTO);
        bo2DtoLink(pRoleTravelers, roleTravelersDTO);
        // on retourne le dto
        return roleTravelersDTO;
    }
    
    /**
     * bo -> dto for a roleTravelers
     * @param pRoleTravelers bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RoleTravelersDTO bo2DtoLight(RoleTravelers pRoleTravelers) throws JrafDomainException {
        // instanciation du DTO
        RoleTravelersDTO roleTravelersDTO = new RoleTravelersDTO();
        bo2DtoLight(pRoleTravelers, roleTravelersDTO);
        // on retourne le dto
        return roleTravelersDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param roleTravelers bo
     * @param roleTravelersDTO dto
     */
    public static void bo2DtoLight(
        RoleTravelers roleTravelers,
        RoleTravelersDTO roleTravelersDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_4up_kEjdEeaaO77HTw9BUw) ENABLED START*/

        bo2DtoLightImpl(roleTravelers, roleTravelersDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param roleTravelers bo
     * @param roleTravelersDTO dto
     */
    private static void bo2DtoLightImpl(RoleTravelers roleTravelers,
        RoleTravelersDTO roleTravelersDTO){
    

        // simple properties
        roleTravelersDTO.setCleRole(roleTravelers.getCleRole());
        roleTravelersDTO.setGin(roleTravelers.getGin());
        roleTravelersDTO.setLastRecognitionDate(roleTravelers.getLastRecognitionDate());
        roleTravelersDTO.setMatchingRecognitionCode(roleTravelers.getMatchingRecognitionCode());
        roleTravelersDTO.setDateCreation(roleTravelers.getDateCreation());
        roleTravelersDTO.setSignatureCreation(roleTravelers.getSignatureCreation());
        roleTravelersDTO.setSiteCreation(roleTravelers.getSiteCreation());
        roleTravelersDTO.setDateModification(roleTravelers.getDateModification());
        roleTravelersDTO.setSignatureModification(roleTravelers.getSignatureModification());
        roleTravelersDTO.setSiteModification(roleTravelers.getSiteModification());
    }
    
    /*PROTECTED REGION ID(bo2DtoLightImpl_4up_kEjdEeaaO77HTw9BUw u m - Tr) ENABLED START*/

    /**
     * @param listRoleTravelers
     * @return
     * @throws JrafDomainException
     */
    public static List<RoleTravelersDTO> bo2Dto(List<RoleTravelers> listRoleTravelers) throws JrafDomainException {
    	if(listRoleTravelers != null) {
    		List<RoleTravelersDTO> listRoleTravelersDTO = new ArrayList<RoleTravelersDTO>();
    		for(RoleTravelers roleContrat : listRoleTravelers) {
    			listRoleTravelersDTO.add(bo2Dto(roleContrat));
    		}
    		return listRoleTravelersDTO;
    	} else {
    		return null;
    	}
    }
    
    /**
     * @param listRoleTravelers
     * @return
     * @throws JrafDomainException
     */
    public static Set<RoleTravelersDTO> bo2Dto(Set<RoleTravelers> listRoleTravelers) throws JrafDomainException {
    	if(listRoleTravelers != null) {
    		Set<RoleTravelersDTO> listRoleTravelersDTO = new HashSet<RoleTravelersDTO>();
    		for(RoleTravelers roleContrat : listRoleTravelers) {
    			listRoleTravelersDTO.add(bo2Dto(roleContrat));
    		}
    		return listRoleTravelersDTO;
    	} else {
    		return null;
    	}
    }

    /**
     * @param listRoleTravelersDTO
     * @return
     * @throws JrafDomainException
     */
    public static Set<RoleTravelers> dto2Bo(Set<RoleTravelersDTO> listRoleTravelersDTO) throws JrafDomainException {
    	if(listRoleTravelersDTO != null) {
    		Set<RoleTravelers> listRoleTravelers = new HashSet<RoleTravelers>();
    		for(RoleTravelersDTO roleContratdto : listRoleTravelersDTO) {
    			listRoleTravelers.add(dto2BoLight(roleContratdto));
    		}
    		return listRoleTravelers;
    	} else {
    		return null;
    	}
    }
    
    /**
     * @param roleTravelers
     * @param roleTravelersDTO
     * @return
     */
    public static void bo2DtoLink(RoleTravelers roleTravelers, RoleTravelersDTO roleTravelersDTO) {
    	
    	BusinessRole businessRole = roleTravelers.getBusinessRole();
    	
    	if(businessRole!=null) {
			try {
				BusinessRoleDTO businessRoleDTO = BusinessRoleTransform.bo2DtoLight(businessRole);
				roleTravelersDTO.setBusinessRole(businessRoleDTO);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
    	}
    	
/*    	Individu individu = roleTravelers.getIndividu();
    	
    	if(individu!=null) {
			try {
				IndividuDTO individuDTO = IndividuTransform.bo2DtoLight(individu);
				roleTravelersDTO.setIndividudto(individuDTO);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
    	}
*/
    }
    
    /**
     * @param roleTravelersDTO
     * @param roleTravelers
     * @return
     */
    public static void dto2BoLink(RoleTravelersDTO roleTravelersDTO,RoleTravelers roleTravelers) {
    	
    	BusinessRoleDTO businessRoleDTO = roleTravelersDTO.getBusinessRole();
    	
    	if(businessRoleDTO!=null) {
			try {
				BusinessRole businessRole = BusinessRoleTransform.dto2BoLight(businessRoleDTO);
				roleTravelers.setBusinessRole(businessRole);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
    	}
    }
    /*PROTECTED REGION END*/
}

