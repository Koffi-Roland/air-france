package com.afklm.rigui.dto.role;

/*PROTECTED REGION ID(_0IHkgDqREeS2wtWjh0gEaw i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.role.RoleRcs;

/*PROTECTED REGION END*/

/**
 * <p>Title : RoleRcsTransform.java</p>
 * transformation bo <-> dto pour un(e) RoleRcs
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RoleRcsTransform {

    /*PROTECTED REGION ID(_0IHkgDqREeS2wtWjh0gEaw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RoleRcsTransform() {
    }
    /**
     * dto -> bo for a RoleRcs
     * @param roleRcsDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RoleRcs dto2BoLight(RoleRcsDTO roleRcsDTO) throws JrafDomainException {
        // instanciation du BO
        RoleRcs roleRcs = new RoleRcs();
        dto2BoLight(roleRcsDTO, roleRcs);

        // on retourne le BO
        return roleRcs;
    }

    /**
     * dto -> bo for a roleRcs
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param roleRcsDTO dto
     * @param roleRcs bo
     */
    public static void dto2BoLight(RoleRcsDTO roleRcsDTO, RoleRcs roleRcs) {
    
        /*PROTECTED REGION ID(dto2BoLight_0IHkgDqREeS2wtWjh0gEaw) ENABLED START*/
        
        dto2BoLightImpl(roleRcsDTO,roleRcs);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a roleRcs
     * @param roleRcsDTO dto
     * @param roleRcs bo
     */
    private static void dto2BoLightImpl(RoleRcsDTO roleRcsDTO, RoleRcs roleRcs){
    
        // property of RoleRcsDTO
        roleRcs.setCle(roleRcsDTO.getCle());
        roleRcs.setType(roleRcsDTO.getType());
        roleRcs.setSousType(roleRcsDTO.getSousType());
        roleRcs.setFamilleTraitement(roleRcsDTO.getFamilleTraitement());
        roleRcs.setCause(roleRcsDTO.getCause());
        roleRcs.setNumeroAffaire(roleRcsDTO.getNumeroAffaire());
        roleRcs.setDateOuverture(roleRcsDTO.getDateOuverture());
        roleRcs.setDateFermeture(roleRcsDTO.getDateFermeture());
        roleRcs.setDateCreation(roleRcsDTO.getDateCreation());
        roleRcs.setSiteCreation(roleRcsDTO.getSiteCreation());
        roleRcs.setSignatureCreation(roleRcsDTO.getSignatureCreation());
        roleRcs.setDateModification(roleRcsDTO.getDateModification());
        roleRcs.setSiteModification(roleRcsDTO.getSiteModification());
        roleRcs.setSignatureModification(roleRcsDTO.getSignatureModification());
    
    }

    /**
     * bo -> dto for a roleRcs
     * @param pRoleRcs bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RoleRcsDTO bo2DtoLight(RoleRcs pRoleRcs) throws JrafDomainException {
        // instanciation du DTO
        RoleRcsDTO roleRcsDTO = new RoleRcsDTO();
        bo2DtoLight(pRoleRcs, roleRcsDTO);
        // on retourne le dto
        return roleRcsDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param roleRcs bo
     * @param roleRcsDTO dto
     */
    public static void bo2DtoLight(
        RoleRcs roleRcs,
        RoleRcsDTO roleRcsDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_0IHkgDqREeS2wtWjh0gEaw) ENABLED START*/

        bo2DtoLightImpl(roleRcs, roleRcsDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param roleRcs bo
     * @param roleRcsDTO dto
     */
    private static void bo2DtoLightImpl(RoleRcs roleRcs,
        RoleRcsDTO roleRcsDTO){
    

        // simple properties
        roleRcsDTO.setCle(roleRcs.getCle());
        roleRcsDTO.setType(roleRcs.getType());
        roleRcsDTO.setSousType(roleRcs.getSousType());
        roleRcsDTO.setFamilleTraitement(roleRcs.getFamilleTraitement());
        roleRcsDTO.setCause(roleRcs.getCause());
        roleRcsDTO.setNumeroAffaire(roleRcs.getNumeroAffaire());
        roleRcsDTO.setDateOuverture(roleRcs.getDateOuverture());
        roleRcsDTO.setDateFermeture(roleRcs.getDateFermeture());
        roleRcsDTO.setDateCreation(roleRcs.getDateCreation());
        roleRcsDTO.setSiteCreation(roleRcs.getSiteCreation());
        roleRcsDTO.setSignatureCreation(roleRcs.getSignatureCreation());
        roleRcsDTO.setDateModification(roleRcs.getDateModification());
        roleRcsDTO.setSiteModification(roleRcs.getSiteModification());
        roleRcsDTO.setSignatureModification(roleRcs.getSignatureModification());
    
    }
    
    /*PROTECTED REGION ID(_0IHkgDqREeS2wtWjh0gEaw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

