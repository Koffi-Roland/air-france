package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.reference.RefComPrefDgtRepository;
import com.airfrance.repind.dto.reference.RefComPrefDgtDTO;
import com.airfrance.repind.dto.reference.RefComPrefDgtTransform;
import com.airfrance.repind.entity.reference.RefComPrefDgt;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class RefComPrefDgtDS {
		
    /** logger */
    //private static final Log log = LogFactory.getLog(RefPermissionsQuestionDS.class);
    
    @Autowired
    private RefComPrefDgtRepository refComPrefDgtRepository;

	/**
	 * Method get, get a RefComPrefDGT from DB using is ID
	 * 
	 * @param refComPrefDgtDTO
	 * @return
	 * @throws JrafDomainException
	 */
    @Transactional(readOnly=true)
	public RefComPrefDgtDTO get(Integer id) throws JrafDomainException {
		
    	Optional<RefComPrefDgt> entity = refComPrefDgtRepository.findById(id);
		
		if (!entity.isPresent()) {
			return null;
		}
		
		return RefComPrefDgtTransform.bo2Dto(entity.get());
	}
	
    /**
     * Method find, find a RefComPrefDGT from DB by an example
     * 
     * @param refComPrefDgtDTO
     * @return
     * @throws JrafDomainException
     */
    @Transactional(readOnly=true)
	public RefComPrefDgtDTO findByDGT(String domain, String groupType, String type) throws JrafDomainException {
		
		List<RefComPrefDgt> result = refComPrefDgtRepository.findByDGT(domain, groupType, type);
		
		RefComPrefDgt entity = null;

		if(result == null || result.isEmpty()) {
			return null;
		} else if (result.size() > 1) {
    		throw new NonUniqueResultException(result.size());
    	} else {
    		entity = result.get(0);
    	}
		
		return RefComPrefDgtTransform.bo2Dto(entity);
	}
    
	/**
	 * This method get all data associated to RefComPref
	 * 
	 * @param refComPrefDgtDTO
	 * @return
	 * @throws JrafDomainException
	 */
    @Transactional(readOnly=true)
	public RefComPrefDgtDTO getWithLinkedData(Integer id) throws JrafDomainException {

		RefComPrefDgt entity = refComPrefDgtRepository.getOne(id);
		
		if (entity == null) {
			return null;
		}
			
		return RefComPrefDgtTransform.bo2DtoWithLinkedData(entity);
	}
    
	/**
	 * This method get all data associated to RefComPref
	 * 
	 * @param refComPrefDgtDTO
	 * @return
	 * @throws JrafDomainException
	 */
    @Transactional(readOnly=true)
	public RefComPrefDgtDTO getWithLinkedData(RefComPrefDgtDTO refComPrefDgtDTO) throws JrafDomainException {

		RefComPrefDgt entity = RefComPrefDgtTransform.dto2Bo(refComPrefDgtDTO);
		
		entity = refComPrefDgtRepository.findOne(Example.of(entity)).get();
		
		if (entity == null) {
			return null;
		}
			
		return RefComPrefDgtTransform.bo2DtoWithLinkedData(entity);
	}
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(RefComPrefDgtDTO refComPrefDgtDTO) throws JrafDomainException {
		
		RefComPrefDgt entity = RefComPrefDgtTransform.dto2Bo(refComPrefDgtDTO);
		
		refComPrefDgtRepository.saveAndFlush(entity);
		
		RefComPrefDgtTransform.bo2Dto(entity, refComPrefDgtDTO);
	}
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void createWithMarketLanguage(RefComPrefDgtDTO refComPrefDgtDTO) throws JrafDomainException {
		
		RefComPrefDgt entity = RefComPrefDgtTransform.dto2BoWithLinkedData(refComPrefDgtDTO);
		
		refComPrefDgtRepository.saveAndFlush(entity);
		
		RefComPrefDgtTransform.bo2DtoWithLinkedData(entity, refComPrefDgtDTO);
	}
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(RefComPrefDgtDTO refComPrefDgtDTO) throws JrafDomainException {
		
		RefComPrefDgtDTO refComPrefDgtDTOFromDB = get(refComPrefDgtDTO.getRefComPrefDgtId());
		
		if (refComPrefDgtDTOFromDB == null) {
			throw new InvalidParameterException("entity does not exists");
		}
		
		RefComPrefDgtTransform.updateDto(refComPrefDgtDTO, refComPrefDgtDTOFromDB);
		
		RefComPrefDgt entity = RefComPrefDgtTransform.dto2Bo(refComPrefDgtDTOFromDB);
		
		refComPrefDgtRepository.saveAndFlush(entity);
	}
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void delete(RefComPrefDgtDTO refComPrefDgtDTO) throws JrafDomainException {
    	refComPrefDgtRepository.deleteById(refComPrefDgtDTO.getRefComPrefDgtId());
	}
}
