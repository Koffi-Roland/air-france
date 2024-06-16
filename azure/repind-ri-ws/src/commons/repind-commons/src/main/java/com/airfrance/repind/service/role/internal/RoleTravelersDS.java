package com.airfrance.repind.service.role.internal;

import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.NotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.individu.IndividuRepository;
import com.airfrance.repind.dao.role.BusinessRoleRepository;
import com.airfrance.repind.dao.role.RoleTravelersRepository;
import com.airfrance.repind.dto.role.BusinessRoleDTO;
import com.airfrance.repind.dto.role.BusinessRoleTransform;
import com.airfrance.repind.dto.role.RoleTravelersDTO;
import com.airfrance.repind.dto.role.RoleTravelersTransform;
import com.airfrance.repind.entity.role.BusinessRole;
import com.airfrance.repind.entity.role.RoleTravelers;
import com.airfrance.repind.service.internal.unitservice.role.RoleUS;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class RoleTravelersDS {
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

    /** unit service : RoleUS **/
    @Autowired
    private RoleUS roleUS;

    /** main dao */
    @Autowired
    private RoleTravelersRepository roleTravelersRepository;

    @Autowired
    private BusinessRoleRepository businessRoleRepository;
  
    
    @Autowired
    private IndividuRepository individuRepository;
   

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(RoleTravelersDTO roleTravelersDTO) throws JrafDomainException {

        RoleTravelers roleTravelers = null;

        // transformation light dto -> bo
        roleTravelers = RoleTravelersTransform.dto2BoLight(roleTravelersDTO);
        
        // Check business role
		Integer cleRole = null;
		if (roleTravelersDTO.getBusinessRole() != null
				&& roleTravelersDTO.getBusinessRole().getCleRole() != null) {
			cleRole = roleTravelersDTO.getBusinessRole().getCleRole();
		} else if (roleTravelersDTO.getCleRole() != null) {
			cleRole = roleTravelersDTO.getCleRole();
		}
		if (cleRole == null) {
			throw new MissingParameterException(
					"Business Role is mandatory for a role contrat");
		} else {
			Optional<BusinessRole> businessRole = businessRoleRepository.findById(cleRole);
			if (!businessRole.isPresent()) {
				throw new NotFoundException("Business Role not found for a role contrat");
			} else {
				roleTravelers.setBusinessRole(businessRole.get());
			}
		}
		
        // Check individual gin
		String gin = null;
		if (roleTravelersDTO.getBusinessRole() != null
				&& roleTravelersDTO.getBusinessRole().getGinInd() != null) {
			gin = roleTravelersDTO.getBusinessRole().getGinInd();
		} else if (roleTravelersDTO.getGin() != null) {
			gin = roleTravelersDTO.getGin();
		}
		if (gin == null) {
			throw new MissingParameterException(
					"Individual is mandatory for a role contrat");
/*		} else {
			Individu individu = getIndividuDao().get(gin);
			if (individu == null) {
				throw new IndivudualNotFoundException("Individual not found for a role contrat");
			} else {
				roleTravelers.setIndividu(individu);
			}
*/			
		}

        // creation en base
        // Appel create de l'Abstract
		roleTravelers.setCleRole(roleTravelersRepository.getSequence().intValue());
		roleTravelersRepository.saveAndFlush(roleTravelers);

        // Version update and Id update if needed
        RoleTravelersTransform.bo2DtoLight(roleTravelers, roleTravelersDTO);
       /*PROTECTED REGION END*/
    }
    
	public void remove(RoleTravelersDTO dto) throws JrafDomainException {
    	RoleTravelers roleTravelers = roleTravelersRepository.getOne(dto.getCleRole());
    	BusinessRole businessRole = roleTravelers.getBusinessRole();
 
    	if(businessRole != null)
         {
    		businessRoleRepository.delete(businessRole);
         }
    	 roleTravelersRepository.deleteById(dto.getCleRole());

	}


	
	@Deprecated
	public RoleTravelersDTO get(RoleTravelersDTO dto) throws JrafDomainException {
		return get(dto.getCleRole());
	}

	public RoleTravelersDTO get(Integer id) throws JrafDomainException {
		Optional<RoleTravelers> roleTravelers = roleTravelersRepository.findById(id);
		if (!roleTravelers.isPresent()) {
			return null;
		}
		RoleTravelersDTO roleTravelersDTO = RoleTravelersTransform.bo2Dto(roleTravelers.get());
		 if (roleTravelers.get().getBusinessRole() != null) {
             BusinessRoleDTO businessRoleDTO = BusinessRoleTransform.bo2DtoLight(roleTravelers.get().getBusinessRole());
             roleTravelersDTO.setBusinessRole(businessRoleDTO);                
         }
		 return roleTravelersDTO;
		 
	}
	
    public RoleTravelersRepository getRoleTravelersRepository() {
		return roleTravelersRepository;
	}

	public void setRoleTravelersRepository(RoleTravelersRepository roleTravelersRepository) {
		this.roleTravelersRepository = roleTravelersRepository;
	}

	/**
     * Getter
     * @return IRoleUS
     */
    public RoleUS getRoleUS() {
        return roleUS;
    }

    /**
     * Setter
     * @param roleUS the IRoleUS 
     */
    public void setRoleUS(RoleUS roleUS) {
        this.roleUS = roleUS;
    }

    /**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        /*PROTECTED REGION ID(_EyW1QEjOEeaaO77HTw9BUwgem ) ENABLED START*/
        return entityManager;
        /*PROTECTED REGION END*/
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /*PROTECTED REGION ID(_EyW1QEjOEeaaO77HTw9BUw u m) ENABLED START*/
    
   
 	public BusinessRoleRepository getBusinessRoleRepository() {
		return businessRoleRepository;
	}

	public void setBusinessRoleRepository(BusinessRoleRepository businessRoleRepository) {
		this.businessRoleRepository = businessRoleRepository;
	}

    public IndividuRepository getIndividuRepository() {
		return individuRepository;
	}

	public void setIndividuRepository(IndividuRepository individuRepository) {
		this.individuRepository = individuRepository;
	}

    @Transactional(readOnly=true)
 	public List<RoleTravelersDTO> findRoleTravelers(String gin) throws JrafDomainException {
 		
 		if(StringUtils.isEmpty(gin)) {
 			throw new IllegalArgumentException("Unable to find role Travelers without GIN");
 		}
 		
 		List<RoleTravelers> roleTravelersList = roleTravelersRepository.findRoleTravelers(gin);
 		
 		if(roleTravelersList==null) {
 			return null;
 		}
 		
 		return RoleTravelersTransform.bo2Dto(roleTravelersList);
 	}

    @Transactional(readOnly=true)
	public RoleTravelersDTO getRoleTravelersByGin(String gin) throws JrafDomainException {
		
		if(StringUtils.isEmpty(gin)) {
			throw new IllegalArgumentException("Unable to find role Travelers without GIN");
		}
		
		RoleTravelers roleTraveler = roleTravelersRepository.getRoleTravelers(gin);
		
		if(roleTraveler == null) {
			return null;
		}
		
		return RoleTravelersTransform.bo2Dto(roleTraveler);
	} 	
}
