package com.airfrance.repind.service.role.internal;

import com.airfrance.ref.exception.ContractExistException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.ContractConstantValues;
import com.airfrance.ref.type.RoleDoctorEnum;
import com.airfrance.repind.dao.role.*;
import com.airfrance.repind.dto.role.*;
import com.airfrance.repind.entity.role.*;
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

	@PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

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

	/**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        /*PROTECTED REGION ID(_k7Fj4EjcEeaaO77HTw9BUwgem ) ENABLED START*/
        return entityManager;
        /*PROTECTED REGION END*/
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /*PROTECTED REGION ID(_k7Fj4EjcEeaaO77HTw9BUw u m) ENABLED START*/
    // add your custom methods here if necessary
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value="transactionManagerRepind")
	public String createABusinessRole(BusinessRoleDTO dto) throws JrafDomainException {
    	// Verification en bdd pour les UCCRs pour ne pas generer de doublons
    	if (dto.getRoleUCCRDTO() != null) {
    		checkExistingBusinessRole(dto);
    	}
    	
    	// Verification en bdd pour les GP pour ne pas generer de doublons
    	if (dto.getRoleGPDTO() != null) {
    		checkExistingBusinessRole(dto);
    	}
    	
		BusinessRole br = new BusinessRole();
		BusinessRoleTransform.dto2BoLight(dto, br);
		businessRoleRepository.saveAndFlush(br);
		// Save child entities of Business Role
		createChildEntities(dto, br);

		return (br.getCleRole().toString());
	}

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

	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value="transactionManagerRepind")
	public String updateATravelerBusinessRole(BusinessRoleDTO dto) throws JrafDomainException {
		// TODO : TEST UPDATE
		BusinessRole br = businessRoleRepository.getOne(dto.getCleRole());
		RoleTravelers rt = br.getRoleTravelers();
		br.setDateModification(dto.getDateModification());
		br.setSignatureModification(dto.getSignatureModification());
		br.setSiteModification(dto.getSiteModification());
		rt.setDateModification(dto.getDateModification());
		rt.setSignatureModification(dto.getSignatureModification());
		rt.setSiteModification(dto.getSiteModification());
		rt.setLastRecognitionDate(dto.getRoleTravelers().getLastRecognitionDate());
		rt.setMatchingRecognitionCode(dto.getRoleTravelers().getMatchingRecognitionCode());
		businessRoleRepository.saveAndFlush(br);
		
		return br.getCleRole().toString();
	}

	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value="transactionManagerRepind")
	public String updateARoleUCCRBusinessRole(BusinessRoleDTO dto) throws JrafDomainException {
		BusinessRole br = businessRoleRepository.getOne(dto.getCleRole());
		RoleUCCR roleUCCR = br.getRoleUCCR();
		br.setDateModification(dto.getDateModification());
		br.setSignatureModification(dto.getSignatureModification());
		br.setSiteModification(dto.getSiteModification());
		br.setGinInd(dto.getGinInd());
		RoleUCCRDTO roleUCCRDTO = dto.getRoleUCCRDTO();
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
        businessRoleRepository.saveAndFlush(br);
		
		return br.getCleRole().toString();
	}
	
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value="transactionManagerRepind")
	public String updateARoleGPBusinessRole(BusinessRoleDTO dto) throws JrafDomainException {
		BusinessRole br = businessRoleRepository.getOne(dto.getCleRole());
		RoleGP roleGP = br.getRoleGP();
		br.setDateModification(dto.getDateModification());
		br.setSignatureModification(dto.getSignatureModification());
		br.setSiteModification(dto.getSiteModification());
		br.setGinInd(dto.getGinInd());
		RoleGPDTO roleGPDTO = dto.getRoleGPDTO();
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
		businessRoleRepository.saveAndFlush(br);
		
		return br.getCleRole().toString();
	}

	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public String endValidityRoleGPBusinessRole(BusinessRoleDTO dto) throws JrafDomainException {
		BusinessRole br = businessRoleRepository.getOne(dto.getCleRole());
		RoleGP roleGP = br.getRoleGP();
		br.setDateModification(dto.getDateModification());
		br.setSignatureModification(dto.getSignatureModification());
		br.setSiteModification(dto.getSiteModification());
		br.setGinInd(dto.getGinInd());
		RoleGPDTO roleGPDTO = dto.getRoleGPDTO();
		roleGP.setCleRole(roleGPDTO.getRoleKey());
		roleGP.setMatricule(roleGPDTO.getMatricule());
		roleGP.setVersion(roleGPDTO.getVersion());
		roleGP.setEtat(roleGPDTO.getState());
		roleGP.setDateDebValidite(roleGPDTO.getEntryCompanyDate());
		roleGP.setDateFinValidite(new Date());
		roleGP.setType(roleGPDTO.getType());
		roleGP.setCodeOrigin(roleGPDTO.getOrganism());
		roleGP.setCodeCie(roleGPDTO.getManagingCompany());
		roleGP.setCodePays(roleGPDTO.getCountryCode());
		roleGP.setTypology(roleGPDTO.getTypology());
		roleGP.setOrdreIdentifiant(roleGPDTO.getIdentifierOrder());
		businessRoleRepository.saveAndFlush(br);
		
		return br.getCleRole().toString();
	}
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void deleteBusinessRole(BusinessRoleDTO dto) throws JrafDomainException {
		businessRoleRepository.deleteById(dto.getCleRole());
	}

	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void removeByGinAndType(String gin, String type) throws JrafDomainException {
		
		businessRoleRepository.deleteByGinIndAndType(gin, type);
		
	}
	
	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class, value="transactionManagerRepind")
	public String updateARoleContractBusinessRole(BusinessRoleDTO dto) throws JrafDomainException {
		BusinessRole br = businessRoleRepository.getOne(dto.getCleRole());
		br.setDateModification(dto.getDateModification());
		br.setSignatureModification(dto.getSignatureModification());
		br.setSiteModification(dto.getSiteModification());
		br.setGinInd(dto.getGinInd());
		br.setType(dto.getType());
		businessRoleRepository.saveAndFlush(br);
		return br.getCleRole().toString();
	}

	
	public BusinessRoleDTO getBusinessRoleByCleRole(Integer cleRole) throws JrafDomainException {
		BusinessRole businessRole = businessRoleRepository.findByCleRole(cleRole);

		return BusinessRoleTransform.bo2Dto(businessRole);
	}

	/**
	 * Return ONLY first BusinessRole findByGinPmAndNumeroContrat(String ginPM,
	 * String contractNumber);
	 * 
	 * @param ginPM
	 * @param contractNumber
	 * @return
	 * @throws JrafDomainException
	 * @throws NoResultException
	 * @throws NonUniqueResultException
	 */
	public BusinessRoleDTO getBusinessRoleByGinPmAndContractNumber(String ginPM, String contractNumber)
			throws JrafDomainException, NoResultException, NonUniqueResultException {
		List<BusinessRole> businessRole = businessRoleRepository.findByGinPmAndNumeroContrat(ginPM, contractNumber);
		
		if (businessRole == null) {
			throw new NoResultException();
		}
		
		return BusinessRoleTransform.bo2Dto(businessRole.get(0));
	}

	public List<BusinessRoleDTO> getAllBusinessRoleByGinPmAndContractNumber(String ginPM, String contractNumber)
			throws JrafDomainException, NoResultException, NonUniqueResultException {
		List<BusinessRole> businessRole = businessRoleRepository.findByGinPmAndNumeroContrat(ginPM, contractNumber);

		if (businessRole == null) {
			throw new NoResultException();
		}

		return BusinessRoleTransform.bo2Dto(businessRole);
	}



	
	public List<BusinessRoleDTO> findAll(BusinessRoleDTO dto) throws JrafDomainException {
		BusinessRole br = BusinessRoleTransform.dto2BoLight(dto);
		List<BusinessRoleDTO> result = new ArrayList<>();
		for (BusinessRole found : businessRoleRepository.findAll(Example.of(br))) {
			result.add(BusinessRoleTransform.bo2DtoLight(found));
			}
		return result;
	}
	
    

	
	public List<BusinessRoleDTO> findValidCorpBusinessRoleByIndividualGIN(String gin) throws JrafDomainException {
		List<BusinessRoleDTO> resultList = null;
		if (gin != null) {
			List<BusinessRole> brList = businessRoleRepository.findValidByIndividualGin(gin);

			if (brList != null && !brList.isEmpty()) {
				resultList = new ArrayList<BusinessRoleDTO>();
				for (BusinessRole foundBR : brList) {
					resultList.add(BusinessRoleTransform.bo2Dto(foundBR));
				}
			}
		}
		return resultList;
	}
	
	public List<String> getSginIndByContractNumber(String contractNumber) {
		return businessRoleRepository.getSginIndByContractNumber(contractNumber);
	}

	public Set<BusinessRoleDTO> findByIndividualGIN(String gin) throws JrafDomainException {
		Set<BusinessRoleDTO> resultList = new HashSet<BusinessRoleDTO>();
		if (gin != null) {
			List<BusinessRole> brList = businessRoleRepository.findByGinInd(gin);

			if (brList != null && !brList.isEmpty()) {
				for (BusinessRole foundBR : brList) {
					resultList.add(BusinessRoleTransform.bo2Dto(foundBR));
				}
			}
		}
		return resultList;
	}

	public List<BusinessRoleDTO> findByGinAndType(String gin, String type) throws JrafDomainException {
		if (StringUtils.isEmpty(gin) || StringUtils.isEmpty(type)) {
			return null;
		}
		
		List<BusinessRole> result = businessRoleRepository.findByGinIndAndType(gin, type);
		
		if (result != null) {
			return BusinessRoleTransform.bo2Dto(result);
		}
		else {
			return null;
		}
	}

	public List<BusinessRoleDTO> getRoleGPByGin(String gin) throws JrafDomainException {
		List<BusinessRoleDTO> resultList = null;
		if (gin != null) {
			List<BusinessRole> brList = businessRoleRepository.findByGinIndAndType(gin, ContractConstantValues.CONTRACT_TYPE_GP);

			if (brList != null && !brList.isEmpty()) {
				resultList = new ArrayList<BusinessRoleDTO>();
				for (BusinessRole foundBR : brList) {
					resultList.add(BusinessRoleTransform.bo2Dto(foundBR));
				}
			}
		}
		return resultList;
	}
	
	public List<String> getContractTypeByContractNumber(String contractNumber) {
		return businessRoleRepository.getContractTypeByContractNumber(contractNumber);
	}
	
	/**
	 * Finds for specified PersonneMorale and the type of business role.
	 * 
	 * @param gin   GIN of PersonneMorale
	 * @param types the types of business roles
	 * @return the list of business roles matching the criteria
	 * @throws JrafDomainException
	 */
	public List<BusinessRoleDTO> findByGinPmAndType(@NotNull String gin, @NotNull List<String> types)
			throws JrafDomainException {
		List<BusinessRole> result = businessRoleRepository.findByGinPmAndType(gin, types);

		if (result != null && !result.isEmpty()) {
			return BusinessRoleTransform.bo2Dto(result);
		}
		return Collections.emptyList();
	}

	/**
	 * Finds for specified gin and the doctor type of business role.
	 *
	 * @param gin   GIN of individual
	 * @return the list of business roles matching the criteria
	 * @throws JrafDomainException
	 */
	public List<BusinessRoleDTO> getDoctorRoleByGinInd(@NotNull String gin) throws JrafDomainException {
		List<BusinessRole> result = businessRoleRepository.findByGinIndAndType(gin, RoleDoctorEnum.D.getCode());
		if (result != null && !result.isEmpty()) {
			return BusinessRoleTransform.bo2Dto(result);
		}
		return Collections.emptyList();
	}
}
