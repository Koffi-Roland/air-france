package com.afklm.rigui.dto.role;

/*PROTECTED REGION ID(_8xkqQPcZEd-Kx8TJdz7fGw i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.role.BusinessRole;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : BusinessRoleTransform.java</p>
 * transformation bo <-> dto pour un(e) BusinessRole
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class BusinessRoleTransform {

	/*PROTECTED REGION ID(_8xkqQPcZEd-Kx8TJdz7fGw u var - Tr) ENABLED START*/
	// add your custom variables here if necessary

	private static Log LOGGER  = LogFactory.getLog(BusinessRoleTransform.class);

	/*PROTECTED REGION END*/

	/**
	 * private constructor
	 */
	private BusinessRoleTransform() {
	}
	/**
	 * dto -> bo for a BusinessRole
	 * @param businessRoleDTO dto
	 * @return bo
	 * @throws JrafDomainException if the DTO type is not supported
	 */
	public static BusinessRole dto2BoLight(BusinessRoleDTO businessRoleDTO) throws JrafDomainException {
		// instanciation du BO
		BusinessRole businessRole = new BusinessRole();
		dto2BoLight(businessRoleDTO, businessRole);

		// on retourne le BO
		return businessRole;
	}

	/**
	 * dto -> bo for a businessRole
	 * calls dto2BoLightImpl in a protected region so the user can override this without
	 * losing benefit of generation if attributes vary in future
	 * @param businessRoleDTO dto
	 * @param businessRole bo
	 */
	public static void dto2BoLight(BusinessRoleDTO businessRoleDTO, BusinessRole businessRole) {

		/*PROTECTED REGION ID(dto2BoLight_8xkqQPcZEd-Kx8TJdz7fGw) ENABLED START*/

		dto2BoLightImpl(businessRoleDTO,businessRole);

		/*PROTECTED REGION END*/
	}

	/**
	 * dto -> bo implementation for a businessRole
	 * @param businessRoleDTO dto
	 * @param businessRole bo
	 */
	private static void dto2BoLightImpl(BusinessRoleDTO businessRoleDTO, BusinessRole businessRole){

		// property of BusinessRoleDTO
		businessRole.setCleRole(businessRoleDTO.getCleRole());
		businessRole.setGinInd(businessRoleDTO.getGinInd());
		businessRole.setGinPm(businessRoleDTO.getGinPm());
		businessRole.setNumeroContrat(businessRoleDTO.getNumeroContrat());
		businessRole.setType(businessRoleDTO.getType());
		businessRole.setDateCreation(businessRoleDTO.getDateCreation());
		businessRole.setSiteCreation(businessRoleDTO.getSiteCreation());
		businessRole.setSignatureCreation(businessRoleDTO.getSignatureCreation());
		businessRole.setDateModification(businessRoleDTO.getDateModification());
		businessRole.setSiteModification(businessRoleDTO.getSiteModification());
		businessRole.setSignatureModification(businessRoleDTO.getSignatureModification());

	}

	/**
	 * bo -> dto for a businessRole
	 * @param pBusinessRole bo
	 * @throws JrafDomainException if the DTO type is not supported
	 * @return dto
	 */
	public static BusinessRoleDTO bo2DtoLight(BusinessRole pBusinessRole) throws JrafDomainException {
		// instanciation du DTO
		BusinessRoleDTO businessRoleDTO = new BusinessRoleDTO();
		bo2DtoLight(pBusinessRole, businessRoleDTO);
		// on retourne le dto
		return businessRoleDTO;
	}


	/**
	 * Transform a business object to DTO.
	 * "light method".
	 * calls bo2DtoLightImpl in a protected region so the user can override this without
	 * losing benefit of generation if attributes vary in future
	 * @param businessRole bo
	 * @param businessRoleDTO dto
	 */
	public static void bo2DtoLight(
			BusinessRole businessRole,
			BusinessRoleDTO businessRoleDTO) {

		/*PROTECTED REGION ID(bo2DtoLight_8xkqQPcZEd-Kx8TJdz7fGw) ENABLED START*/

		bo2DtoLightImpl(businessRole, businessRoleDTO);

		/*PROTECTED REGION END*/

	}

	/**
	 * Transform a business object to DTO. Implementation method
	 * @param businessRole bo
	 * @param businessRoleDTO dto
	 */
	private static void bo2DtoLightImpl(BusinessRole businessRole,
			BusinessRoleDTO businessRoleDTO){


		// simple properties
		businessRoleDTO.setCleRole(businessRole.getCleRole());
		businessRoleDTO.setGinInd(businessRole.getGinInd());
		businessRoleDTO.setGinPm(businessRole.getGinPm());
		businessRoleDTO.setNumeroContrat(businessRole.getNumeroContrat());
		businessRoleDTO.setType(businessRole.getType());
		businessRoleDTO.setDateCreation(businessRole.getDateCreation());
		businessRoleDTO.setSiteCreation(businessRole.getSiteCreation());
		businessRoleDTO.setSignatureCreation(businessRole.getSignatureCreation());
		businessRoleDTO.setDateModification(businessRole.getDateModification());
		businessRoleDTO.setSiteModification(businessRole.getSiteModification());
		businessRoleDTO.setSignatureModification(businessRole.getSignatureModification());

	}

	/*PROTECTED REGION ID(_8xkqQPcZEd-Kx8TJdz7fGw u m - Tr) ENABLED START*/
	// add your custom methods here if necessary

	/**
	 * @param listBusinessRole
	 * @return
	 * @throws JrafDomainException
	 */
	public static Set<BusinessRoleDTO> bo2Dto(Set<BusinessRole> listBusinessRole) throws JrafDomainException {
		if(listBusinessRole != null)
		{
			Set<BusinessRoleDTO> listBusinessRoleDTO = new LinkedHashSet<BusinessRoleDTO>();
			for(BusinessRole businessRole : listBusinessRole)
			{
				listBusinessRoleDTO.add(bo2Dto(businessRole));
			}
			return listBusinessRoleDTO;
		}
		else
		{
			return null;
		}
	}
	
	public static List<BusinessRoleDTO> bo2Dto(List<BusinessRole> listBusinessRole) throws JrafDomainException {
		if(listBusinessRole != null)
		{
			List<BusinessRoleDTO> listBusinessRoleDTO = new ArrayList<BusinessRoleDTO>();
			for(BusinessRole businessRole : listBusinessRole)
			{
				listBusinessRoleDTO.add(bo2Dto(businessRole));
			}
			return listBusinessRoleDTO;
		}
		else
		{
			return null;
		}
	}

	/**
	 * @param businessRole
	 * @return
	 * @throws JrafDomainException
	 */
	public static BusinessRoleDTO bo2Dto(BusinessRole businessRole) throws JrafDomainException {
		if(businessRole != null)
		{
			BusinessRoleDTO businessRoleDTO = bo2DtoLight(businessRole);
			bo2DtoLink(businessRole, businessRoleDTO);
			return businessRoleDTO;
		}
		else
		{
			return null;
		}
	}

	public static void bo2DtoLink(BusinessRole businessRole, BusinessRoleDTO businessRoleDTO) throws JrafDomainException {

		// Lien avec RoleRCS
		if(businessRole.getRoleRcs() != null)
		{
			try
			{
				// LOGGER.warn("Lien avec ROLE_RCS");
				businessRoleDTO.setRoleRcs(RoleRcsTransform.bo2DtoLight(businessRole.getRoleRcs()));
			}
			catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}

		}


		// Lien avec Role traveler
		if (businessRole.getRoleTravelers() != null) {
			try 
			{
				businessRoleDTO.setRoleTravelers(RoleTravelersTransform.bo2Dto(businessRole.getRoleTravelers()));
			} catch (JrafDomainException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}

		// Lien avec Role UCCR
		if (businessRole.getRoleUCCR() != null) {
			try 
			{
				businessRoleDTO.setRoleUCCRDTO(RoleUCCRTransform.bo2DtoLight(businessRole.getRoleUCCR()));
			} catch (JrafDomainException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}

		// Lien avec RoleGP
		if(businessRole.getRoleGP() != null)
		{
			try
			{
				businessRoleDTO.setRoleGPDTO(RoleGPTransform.bo2DtoLight(businessRole.getRoleGP()));
			}
			catch (JrafDomainException e) {
				if(LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}
	}



	/**
	 * @param listBusinessRoleDTO
	 * @return
	 * @throws JrafDomainException
	 */
	public static List<BusinessRole> dto2Bo(Set<BusinessRoleDTO> listBusinessRoleDTO) throws JrafDomainException {
		if(listBusinessRoleDTO != null) {
			List<BusinessRole> listBusinessRole = new ArrayList<BusinessRole>();
			for(BusinessRoleDTO BusinessRoleDTO : listBusinessRoleDTO) {
				listBusinessRole.add(dto2BoLight(BusinessRoleDTO));
			}
			return listBusinessRole;
		} else {
			return null;
		}
	}

	public static BusinessRole dto2BoLink(BusinessRoleDTO dto, BusinessRole bo) {

		// Lien avec RoleRCS
		if(dto.getRoleRcs() != null) {
			try {
				// LOGGER.warn("Lien avec ROLE_RCS");
				bo.setRoleRcs(RoleRcsTransform.dto2BoLight(dto.getRoleRcs()));
			} catch (JrafDomainException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}

		// Lien avec Role traveler
		if (dto.getRoleTravelers() != null) {
			try
			{
				bo.setRoleTravelers(RoleTravelersTransform.dto2BoLight(dto.getRoleTravelers()));
				bo.getRoleTravelers().setBusinessRole(bo);
			} catch (JrafDomainException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}

		// Lien avec Role UCCR
		if (dto.getRoleUCCRDTO() != null) {
			try
			{
				bo.setRoleUCCR(RoleUCCRTransform.dto2BoLight(dto.getRoleUCCRDTO()));
				bo.getRoleUCCR().setBusinessRole(bo);
			} catch (JrafDomainException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}

		// Lien avec Role GP
		if (dto.getRoleGPDTO() != null) {
			try
			{
				bo.setRoleGP(RoleGPTransform.dto2BoLight(dto.getRoleGPDTO()));
				bo.getRoleGP().setBusinessRole(bo);
			} catch (JrafDomainException e) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(e);
				}
			}
		}

		return bo;
	}


	/*PROTECTED REGION END*/
}

