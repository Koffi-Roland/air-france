package com.afklm.rigui.dto.role;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.role.BusinessRole;
import com.afklm.rigui.entity.role.RoleUCCR;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*PROTECTED REGION END*/

/**
 * <p>Title : RoleUCCRTransform.java</p>
 * transformation bo <-> dto pour un(e) RoleUCCR
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RoleUCCRTransform {

    /*PROTECTED REGION ID(_6Uuz0FMVEeat9YXqXExedw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
	private static Log LOGGER  = LogFactory.getLog(RoleUCCRTransform.class);
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RoleUCCRTransform() {
    }
    /**
     * dto -> bo for a RoleUCCR
     * @param roleUCCRDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RoleUCCR dto2BoLight(RoleUCCRDTO roleUCCRDTO) throws JrafDomainException {
        // instanciation du BO
        RoleUCCR roleUCCR = new RoleUCCR();
        dto2BoLight(roleUCCRDTO, roleUCCR);

        // on retourne le BO
        return roleUCCR;
    }

    /**
     * dto -> bo for a roleUCCR
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param roleUCCRDTO dto
     * @param roleUCCR bo
     */
    public static void dto2BoLight(RoleUCCRDTO roleUCCRDTO, RoleUCCR roleUCCR) {
    
        /*PROTECTED REGION ID(dto2BoLight_6Uuz0FMVEeat9YXqXExedw) ENABLED START*/
        
        dto2BoLightImpl(roleUCCRDTO,roleUCCR);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a roleUCCR
     * @param roleUCCRDTO dto
     * @param roleUCCR bo
     */
    private static void dto2BoLightImpl(RoleUCCRDTO roleUCCRDTO, RoleUCCR roleUCCR){
    
        // property of RoleUCCRDTO
        roleUCCR.setCleRole(roleUCCRDTO.getCleRole());
        roleUCCR.setUccrID(roleUCCRDTO.getUccrID());
        roleUCCR.setCeID(roleUCCRDTO.getCeID());
        roleUCCR.setGin(roleUCCRDTO.getGin());
        roleUCCR.setType(roleUCCRDTO.getType());
        roleUCCR.setEtat(roleUCCRDTO.getEtat());
        roleUCCR.setDebutValidite(roleUCCRDTO.getDebutValidite());
        roleUCCR.setFinValidite(roleUCCRDTO.getFinValidite());
        roleUCCR.setDateCreation(roleUCCRDTO.getDateCreation());
        roleUCCR.setSignatureCreation(roleUCCRDTO.getSignatureCreation());
        roleUCCR.setSiteCreation(roleUCCRDTO.getSiteCreation());
        roleUCCR.setDateModification(roleUCCRDTO.getDateModification());
        roleUCCR.setSignatureModification(roleUCCRDTO.getSignatureModification());
        roleUCCR.setSiteModification(roleUCCRDTO.getSiteModification());
    
    }

    /**
     * bo -> dto for a roleUCCR
     * @param pRoleUCCR bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RoleUCCRDTO bo2DtoLight(RoleUCCR pRoleUCCR) throws JrafDomainException {
        // instanciation du DTO
        RoleUCCRDTO roleUCCRDTO = new RoleUCCRDTO();
        bo2DtoLight(pRoleUCCR, roleUCCRDTO);
        // on retourne le dto
        return roleUCCRDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param roleUCCR bo
     * @param roleUCCRDTO dto
     */
    public static void bo2DtoLight(
        RoleUCCR roleUCCR,
        RoleUCCRDTO roleUCCRDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_6Uuz0FMVEeat9YXqXExedw) ENABLED START*/

        bo2DtoLightImpl(roleUCCR, roleUCCRDTO);
        
        bo2DtoLink(roleUCCR, roleUCCRDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param roleUCCR bo
     * @param roleUCCRDTO dto
     */
    private static void bo2DtoLightImpl(RoleUCCR roleUCCR,
        RoleUCCRDTO roleUCCRDTO){
    

        // simple properties
        roleUCCRDTO.setCleRole(roleUCCR.getCleRole());
        roleUCCRDTO.setUccrID(roleUCCR.getUccrID());
        roleUCCRDTO.setCeID(roleUCCR.getCeID());
        roleUCCRDTO.setGin(roleUCCR.getGin());
        roleUCCRDTO.setType(roleUCCR.getType());
        roleUCCRDTO.setEtat(roleUCCR.getEtat());
        roleUCCRDTO.setDebutValidite(roleUCCR.getDebutValidite());
        roleUCCRDTO.setFinValidite(roleUCCR.getFinValidite());
        roleUCCRDTO.setDateCreation(roleUCCR.getDateCreation());
        roleUCCRDTO.setSignatureCreation(roleUCCR.getSignatureCreation());
        roleUCCRDTO.setSiteCreation(roleUCCR.getSiteCreation());
        roleUCCRDTO.setDateModification(roleUCCR.getDateModification());
        roleUCCRDTO.setSignatureModification(roleUCCR.getSignatureModification());
        roleUCCRDTO.setSiteModification(roleUCCR.getSiteModification());
    
    }
    
    /*PROTECTED REGION ID(_6Uuz0FMVEeat9YXqXExedw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /**
     * @param roleUCCR
     * @param roleUCCRDTO
     * @return
     */
    public static void bo2DtoLink(RoleUCCR roleUCCR, RoleUCCRDTO roleUCCRDTO) {
    	
    	BusinessRole businessRole = roleUCCR.getBusinessRole();
    	
    	if(businessRole!=null) {
			try {
				BusinessRoleDTO businessRoleDTO = BusinessRoleTransform.bo2DtoLight(businessRole);
				roleUCCRDTO.setBusinessRole(businessRoleDTO);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
    	}
    	
    }
    
    /**
     * @param roleUCCRDTO
     * @param roleUCCR
     * @return
     */
    public static void dto2BoLink(RoleUCCRDTO roleUCCRDTO,RoleUCCR roleUCCR) {
    	
    	BusinessRoleDTO businessRoleDTO = roleUCCRDTO.getBusinessRole();
    	
    	if(businessRoleDTO!=null) {
			try {
				BusinessRole businessRole = BusinessRoleTransform.dto2BoLight(businessRoleDTO);
				roleUCCR.setBusinessRole(businessRole);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
    	}
    }
    /*PROTECTED REGION END*/
}

