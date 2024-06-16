package com.afklm.rigui.services.role.internal;

import com.afklm.rigui.exception.ContractExistException;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.exception.jraf.JrafDomainNoRollbackException;
import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;
import com.afklm.rigui.enums.ContractConstantValues;
import com.afklm.rigui.enums.RoleDoctorEnum;
import com.afklm.rigui.dao.role.*;
import com.afklm.rigui.dto.role.*;
import com.afklm.rigui.entity.role.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class BusinessRoleDS {

	private final static Log LOG = LogFactory.getLog(BusinessRoleDS.class);

    @Autowired
    private BusinessRoleRepository businessRoleRepository;


    @Autowired
	private RoleTravelersRepository roleTravelersRepository;

    @Autowired
	private RoleGPRepository roleGPRepository;

    @Autowired
	private RoleUCCRRepository roleUCCRRepository;

    @Autowired
	private RoleRcsRepository roleRcsRepository;
    
	@Deprecated
	public BusinessRoleDTO get(BusinessRoleDTO dto) throws JrafDomainException {
		return get(dto.getCleRole());
	}

	public BusinessRoleDTO get(Integer id) throws JrafDomainException {
		Optional<BusinessRole> br = businessRoleRepository.findById(id);
		if (!br.isPresent()) {
			return null;
		}
		return BusinessRoleTransform.bo2DtoLight(br.get());
	}


    public BusinessRoleRepository getBusinessRoleRepository() {
		return businessRoleRepository;
	}

	public void setBusinessRoleRepository(BusinessRoleRepository businessRoleRepository) {
		this.businessRoleRepository = businessRoleRepository;
	}

    /*PROTECTED REGION ID(_k7Fj4EjcEeaaO77HTw9BUw u m) ENABLED START*/
    // add your custom methods here if necessary

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value="transactionManagerRepind")
	public void createChildEntities(BusinessRoleDTO dto, BusinessRole businessRole) {

		// Lien avec Role traveler
		if (dto.getRoleTravelers() != null) {
			try	{
				RoleTravelers bo = RoleTravelersTransform.dto2BoLight(dto.getRoleTravelers());
				bo.setBusinessRole(businessRole);
				roleTravelersRepository.saveAndFlush(bo);
			} catch (JrafDomainException e) {
				LOG.error(e);
			}
		}

		// Lien avec Role UCCR
		if (dto.getRoleUCCRDTO() != null) {
			try	{
				RoleUCCR bo = RoleUCCRTransform.dto2BoLight(dto.getRoleUCCRDTO());
				bo.setBusinessRole(businessRole);roleUCCRRepository.saveAndFlush(bo);

			} catch (JrafDomainException e) {
				LOG.error(e);
			}
		}

		// Lien avec Role GP
		if (dto.getRoleGPDTO() != null) {
			try	{
				RoleGP bo = RoleGPTransform.dto2BoLight(dto.getRoleGPDTO());
				bo.setBusinessRole(businessRole);
				roleGPRepository.saveAndFlush(bo);
			} catch (JrafDomainException e) {
				LOG.error(e);
			}
		}

		// lien avec Role RCS
		if (dto.getRoleRcs() != null) {
			try {
				RoleRcs bo = RoleRcsTransform.dto2BoLight(dto.getRoleRcs());
				bo.setBusinessRole(businessRole);
				roleRcsRepository.saveAndFlush(bo);
			} catch (JrafDomainException e) {
				LOG.error(e);
			}
		}
	}

	private void checkExistingBusinessRole(BusinessRoleDTO dto) throws JrafDomainException {
		if (dto.getNumeroContrat() != null && !"".equals(dto.getNumeroContrat()) && dto.getType() != null && !"".equals(dto.getType())) {
			BusinessRole br = new BusinessRole();
			br.setType(dto.getType());
			br.setNumeroContrat(dto.getNumeroContrat());
			List<BusinessRole> brFromDb = businessRoleRepository.findAll(Example.of(br));
			
			if (!brFromDb.isEmpty()) {
				throw new ContractExistException("Contract number " + dto.getNumeroContrat() + " of type " + dto.getType().toUpperCase() + " already exists.");
			}
		}
	}

	public List<BusinessRoleDTO> findAll(BusinessRoleDTO dto) throws JrafDomainException {
		BusinessRole br = BusinessRoleTransform.dto2BoLight(dto);
		List<BusinessRoleDTO> result = new ArrayList<>();
		for (BusinessRole found : businessRoleRepository.findAll(Example.of(br))) {
			result.add(BusinessRoleTransform.bo2DtoLight(found));
			}
		return result;
	}

	public List<String> getSginIndByContractNumber(String contractNumber) {
		return businessRoleRepository.getSginIndByContractNumber(contractNumber);
	}


	public List<String> getContractTypeByContractNumber(String contractNumber) {
		return businessRoleRepository.getContractTypeByContractNumber(contractNumber);
	}

}
