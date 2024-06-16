package com.afklm.rigui.dto.role;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.role.BusinessRole;
import com.afklm.rigui.entity.role.RoleGP;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*PROTECTED REGION END*/

/**
 * <p>Title : RoleGPTransform.java</p>
 * transformation bo <-> dto pour un(e) RoleGP
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RoleGPTransform {

	/*PROTECTED REGION ID(_6Uuz0FMVEeat9YXqXExedw u var - Tr) ENABLED START*/
	// add your custom variables here if necessary
	private static Log LOGGER  = LogFactory.getLog(RoleGPTransform.class);
	/*PROTECTED REGION END*/

	/**
	 * private constructor
	 */
	private RoleGPTransform() {
	}
	/**
	 * dto -> bo for a RoleGP
	 * @param roleGPDTO dto
	 * @return bo
	 * @throws JrafDomainException if the DTO type is not supported
	 */
	public static RoleGP dto2BoLight(RoleGPDTO roleGPDTO) throws JrafDomainException {
		// instanciation du BO
		RoleGP roleGP = new RoleGP();
		dto2BoLight(roleGPDTO, roleGP);

		// on retourne le BO
		return roleGP;
	}

	/**
	 * dto -> bo for a roleGP
	 * calls dto2BoLightImpl in a protected region so the user can override this without
	 * losing benefit of generation if attributes vary in future
	 * @param roleGPDTO dto
	 * @param roleGP bo
	 */
	public static void dto2BoLight(RoleGPDTO roleGPDTO, RoleGP roleGP) {

		/*PROTECTED REGION ID(dto2BoLight_6Uuz0FMVEeat9YXqXExedw) ENABLED START*/

		dto2BoLightImpl(roleGPDTO,roleGP);

		/*PROTECTED REGION END*/
	}

	/**
	 * dto -> bo implementation for a roleGP
	 * @param roleGPDTO dto
	 * @param roleGP bo
	 */
	private static void dto2BoLightImpl(RoleGPDTO roleGPDTO, RoleGP roleGP){

		// property of RoleGPDTO
		roleGP.setCleRole(roleGPDTO.getRoleKey());
		roleGP.setMatricule(roleGPDTO.getMatricule());
		roleGP.setVersion(roleGPDTO.getVersion());
		roleGP.setEtat(roleGPDTO.getState());
		roleGP.setDateDebValidite(roleGPDTO.getEntryCompanyDate());
		roleGP.setDateFinValidite(roleGPDTO.getExpiryCardDate());
		roleGP.setType(roleGPDTO.getType());
		roleGP.setCodeOrigin(roleGPDTO.getOrganism());
		roleGP.setCodeCie(roleGPDTO.getManagingCompany());
		roleGP.setCodePays(roleGPDTO.getCountryCode());
		roleGP.setTypology(roleGPDTO.getTypology());
		roleGP.setOrdreIdentifiant(roleGPDTO.getIdentifierOrder());

	}

	/**
	 * bo -> dto for a roleGP
	 * @param pRoleGP bo
	 * @throws JrafDomainException if the DTO type is not supported
	 * @return dto
	 */
	public static RoleGPDTO bo2DtoLight(RoleGP pRoleGP) throws JrafDomainException {
		// instanciation du DTO
		RoleGPDTO roleGPDTO = new RoleGPDTO();
		bo2DtoLight(pRoleGP, roleGPDTO);
		// on retourne le dto
		return roleGPDTO;
	}


	/**
	 * Transform a business object to DTO.
	 * "light method".
	 * calls bo2DtoLightImpl in a protected region so the user can override this without
	 * losing benefit of generation if attributes vary in future
	 * @param roleGP bo
	 * @param roleGPDTO dto
	 */
	public static void bo2DtoLight(
			RoleGP roleGP,
			RoleGPDTO roleGPDTO) {

		/*PROTECTED REGION ID(bo2DtoLight_6Uuz0FMVEeat9YXqXExedw) ENABLED START*/

		bo2DtoLightImpl(roleGP, roleGPDTO);

		bo2DtoLink(roleGP, roleGPDTO);

		/*PROTECTED REGION END*/

	}

	/**
	 * Transform a business object to DTO. Implementation method
	 * @param roleGP bo
	 * @param roleGPDTO dto
	 */
	private static void bo2DtoLightImpl(RoleGP roleGP,
			RoleGPDTO roleGPDTO){


		// simple properties
		// property of RoleGPDTO
		roleGPDTO.setRoleKey(roleGP.getCleRole());
		roleGPDTO.setMatricule(roleGP.getMatricule());
		roleGPDTO.setVersion(roleGP.getVersion());
		roleGPDTO.setState(roleGP.getEtat());
		roleGPDTO.setEntryCompanyDate(roleGP.getDateDebValidite());
		roleGPDTO.setExpiryCardDate(roleGP.getDateFinValidite());
		roleGPDTO.setType(roleGP.getType());
		roleGPDTO.setOrganism(roleGP.getCodeOrigin());
		roleGPDTO.setManagingCompany(roleGP.getCodeCie());
		roleGPDTO.setCountryCode(roleGP.getCodePays());
		roleGPDTO.setTypology(roleGP.getTypology());
		roleGPDTO.setIdentifierOrder(roleGP.getOrdreIdentifiant());

	}

	/*PROTECTED REGION ID(_6Uuz0FMVEeat9YXqXExedw u m - Tr) ENABLED START*/
	// add your custom methods here if necessary
	/**
	 * @param roleGP
	 * @param roleGPDTO
	 * @return
	 */
	public static void bo2DtoLink(RoleGP roleGP, RoleGPDTO roleGPDTO) {

		BusinessRole businessRole = roleGP.getBusinessRole();

		if(businessRole!=null) {
			try {
				BusinessRoleDTO businessRoleDTO = BusinessRoleTransform.bo2DtoLight(businessRole);
				roleGPDTO.setBusinessRole(businessRoleDTO);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}

	}

	/**
	 * @param roleGPDTO
	 * @param roleGP
	 * @return
	 */
	public static void dto2BoLink(RoleGPDTO roleGPDTO,RoleGP roleGP) {

		BusinessRoleDTO businessRoleDTO = roleGPDTO.getBusinessRole();

		if(businessRoleDTO!=null) {
			try {
				BusinessRole businessRole = BusinessRoleTransform.dto2BoLight(businessRoleDTO);
				roleGP.setBusinessRole(businessRole);
			} catch (JrafDomainException e) {
				LOGGER.error(e);
			}
		}
	}
	/*PROTECTED REGION END*/
}

