package com.airfrance.repind.service.role.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.role.RoleGPRepository;
import com.airfrance.repind.dto.role.RoleGPDTO;
import com.airfrance.repind.dto.role.RoleGPTransform;
import com.airfrance.repind.entity.role.RoleGP;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleGPDS {

    @Autowired
    private RoleGPRepository roleGPRepository;

	public RoleGPDTO get(Integer id) throws JrafDomainException {
		Optional<RoleGP> roleGP = roleGPRepository.findById(id);
		if (!roleGP.isPresent()) {
			return null;
		}
		
		return RoleGPTransform.bo2DtoLight(roleGP.get());
	}

	public RoleGPRepository getRoleGPRepository() {
		return roleGPRepository;
	}

	public void setRoleGPRepository(RoleGPRepository roleGPRepository) {
		this.roleGPRepository = roleGPRepository;
	}
	

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(RoleGPDTO roleGPDTO) throws JrafDomainException {
        RoleGP roleGP = null;

        roleGP = RoleGPTransform.dto2BoLight(roleGPDTO);

        roleGPRepository.saveAndFlush(roleGP);

        RoleGPTransform.bo2DtoLight(roleGP, roleGPDTO);
    }


    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(RoleGPDTO dto) throws JrafDomainException {

       Optional<RoleGP> roleGP = roleGPRepository.findById(dto.getRoleKey());
            
       if (roleGP.isPresent()) {
    	// Checking the optimistic strategy
    	   if (!(roleGP.get().getVersion().equals(dto.getVersion()))) {
               throw new JrafDomainRollbackException("Optimistic failure on RoleGP : "
                + roleGP);
           }
    	   
    	   RoleGPTransform.dto2BoLight(dto,roleGP.get());
    	   
    	   roleGPRepository.delete(roleGP.get());
       }
    }
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(Serializable oid) throws JrafDomainException {
    	roleGPRepository.deleteById((Integer) oid);
    }

    @Transactional(readOnly=true)
    public List<RoleGPDTO> findAll() throws JrafDomainException {
    	
    	
    	List<RoleGP> roleGP = roleGPRepository.findAll();
    	
    	List<RoleGPDTO> dtoFounds =  new ArrayList<RoleGPDTO>();
    	
    	for (RoleGP entity : roleGP) {
    		RoleGPDTO dto = RoleGPTransform.bo2DtoLight(entity);
    		dtoFounds.add(dto);
    	}
    	
    	return dtoFounds;
    }


    @Transactional(readOnly=true)
    public Integer count() throws JrafDomainException {
    	return (int) roleGPRepository.count();
    }
 


    @Transactional(readOnly=true)
    public RoleGPDTO get(RoleGPDTO dto) throws JrafDomainException {
		Optional<RoleGP> roleGP = roleGPRepository.findById(dto.getRoleKey());
		if (!roleGP.isPresent()) {
			return null;
		}
		return RoleGPTransform.bo2DtoLight(roleGP.get());
    }

    
    public List<RoleGPDTO> findByGIN(String gin) throws JrafDomainException {
    	if (StringUtils.isEmpty(gin)) {
    		return null;
    	}
    	List<RoleGPDTO> result = null;

    	List<RoleGP> roleFromDB = roleGPRepository.findByGin(gin);
    	
    	if (roleFromDB != null) {
    		result = new ArrayList<>();
    		for (RoleGP rgp : roleFromDB) {
    			result.add(RoleGPTransform.bo2DtoLight(rgp));
    		}
    	}
    	
    	return result;
    	
    }

	public List<RoleGPDTO> findByIdentifier(String matricule) throws JrafDomainException {
		if (StringUtils.isEmpty(matricule)) {
    		return null;
    	}
		
		List<RoleGPDTO> result = null;
		
		List<RoleGP> roleFromDB = roleGPRepository.findByMatricule(matricule);
		
		if (roleFromDB != null) {
    		result = new ArrayList<>();
    		for (RoleGP rgp : roleFromDB) {
    			result.add(RoleGPTransform.bo2DtoLight(rgp));
    		}
    	}
		
		return result;
	}

	public Object[] getRefOriginByCode(String organism) throws JrafDomainException {
		if (StringUtils.isNotEmpty(organism)) {
			Object[] result = roleGPRepository.getRefOriginByCode(organism);
			
			Object[] resultf = result;
			if(result != null && result.length > 0) {
				resultf = (Object[]) result[0];
			}
			return resultf;
		}
		
		return null;
	}
	/*PROTECTED REGION END*/
}
