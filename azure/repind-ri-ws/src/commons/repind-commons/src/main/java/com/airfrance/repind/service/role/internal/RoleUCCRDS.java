package com.airfrance.repind.service.role.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dao.role.BusinessRoleRepository;
import com.airfrance.repind.dao.role.RoleUCCRRepository;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.BusinessRoleTransform;
import com.airfrance.repind.dto.role.RoleUCCRDTO;
import com.airfrance.repind.dto.role.RoleUCCRTransform;
import com.airfrance.repind.entity.role.BusinessRole;
import com.airfrance.repind.entity.role.RoleUCCR;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleUCCRDS {
	@PersistenceContext(unitName="entityManagerFactoryRepind")
	private EntityManager entityManager;


	/** main dao */
	@Autowired
	private RoleUCCRRepository roleUCCRRepository;

	@Autowired
	private BusinessRoleRepository businessRoleRepository;


	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(RoleUCCRDTO dto) throws JrafDomainException {
		RoleUCCR roleUCCR = new RoleUCCR();
		BusinessRole businessRole = null;

		// chargement du bo
		roleUCCR = roleUCCRRepository.getOne(dto.getCleRole());


		// transformation light dto -> bo
		RoleUCCRTransform.dto2BoLight(dto,roleUCCR);

		// suppression en base
		roleUCCRRepository.delete(roleUCCR);

		// Suppresion des données business
		businessRole = roleUCCR.getBusinessRole();
		if(businessRole != null)
		{
			businessRoleRepository.delete(businessRole);
		}
	}

	public RoleUCCRDTO get(Integer id) throws JrafDomainException {
		Optional<RoleUCCR> roleUCCR = roleUCCRRepository.findById(id);
		if (!roleUCCR.isPresent()) {
			return null;
		}
		return RoleUCCRTransform.bo2DtoLight(roleUCCR.get());
	}

	public RoleUCCRRepository getRoleUCCRRepository() {
		return roleUCCRRepository;
	}

	public void setRoleUCCRRepository(RoleUCCRRepository roleUCCRRepository) {
		this.roleUCCRRepository = roleUCCRRepository;
	}


	public BusinessRoleRepository getBusinessRoleRepository() {
		return businessRoleRepository;
	}

	public void setBusinessRoleRepository(BusinessRoleRepository businessRoleRepository) {
		this.businessRoleRepository = businessRoleRepository;
	}

	/**
	 * getBusinessRoleByUCCRIDAndCEID
	 * @param String UCCRID, String corporateEnvironmentID.
	 * @return BusinessRoleDTO
	 * @throws JrafDomainException en cas d'exception
	 */

    @Transactional(readOnly=true)
	public BusinessRoleDTO getBusinessRoleByUCCRIDAndCEID(String uccrID, String corporateEnvironmentID) throws JrafDomainException {
		BusinessRole businessRole = businessRoleRepository.getBusinessRoleByUCCRIDAndCEID(uccrID, corporateEnvironmentID);
		return BusinessRoleTransform.bo2Dto(businessRole);
	}

	/**
	 * countNbOfUCCRIDWByGINAndCEID
	 * @param String GIN, String CEID.
	 * @return number of UCCRID
	 * @throws JrafDomainException
	 */

    @Transactional(readOnly=true)
	public Long countNbOfUCCRIDWByGINAndCEID(String gin, String ceid) throws JrafDomainException {
		return roleUCCRRepository.countNbOfUCCRIDWByGINAndCEID(gin, ceid);
	}

	/**
	 * getGinByUCCRID
	 * @param String UCCRID.
	 * @return GIN
	 * @throws JrafDomainException
	 */

    @Transactional(readOnly=true)
	public String getGinByUCCRID(String uccrid) throws JrafDomainException {
		return roleUCCRRepository.getGinByUCCRID(uccrid);
	}


    @Transactional(readOnly=true)
	public List<RoleUCCRDTO> findByGin(String gin) throws JrafDomainException {
		if (StringUtils.isBlank(gin)) {
			throw new InvalidParameterException("gin must not be blank");
		}
		List<RoleUCCRDTO> result = new ArrayList<>();
		for (RoleUCCR role : roleUCCRRepository.getByGin(gin)) {
			result.add(RoleUCCRTransform.bo2DtoLight(role));
		}
		return result;
	}

	
	public List<RoleUCCRDTO> findByExample(RoleUCCRDTO bo) throws JrafDomainException {
		RoleUCCR roleUCCR = RoleUCCRTransform.dto2BoLight(bo);
		List<RoleUCCRDTO> result = new ArrayList<>();
		for (RoleUCCR found : roleUCCRRepository.findAll(Example.of(roleUCCR))) {
			result.add(RoleUCCRTransform.bo2DtoLight(found));
			}
		return result;
	}

	public List<RoleUCCRDTO> getRoleUccrFromBusinessRole(List<BusinessRoleDTO> businessRoleUccrList) {
    	if (CollectionUtils.isNotEmpty(businessRoleUccrList)) {
    		return businessRoleUccrList
					.stream()
					.filter(br -> (br.getRoleUCCRDTO() != null
							&& !MediumStatusEnum.REMOVED.toString().equalsIgnoreCase(br.getRoleUCCRDTO().getEtat())))
					.map(brUccr -> {return brUccr.getRoleUCCRDTO();})
					.collect(Collectors.toList());


		}
    	else {
    		return null;
		}
	}
}
