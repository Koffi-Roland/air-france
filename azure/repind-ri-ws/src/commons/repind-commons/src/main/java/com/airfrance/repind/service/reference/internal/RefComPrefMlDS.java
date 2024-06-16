package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.reference.RefComPrefMlRepository;
import com.airfrance.repind.dto.reference.RefComPrefMlDTO;
import com.airfrance.repind.dto.reference.RefComPrefMlTransform;
import com.airfrance.repind.entity.reference.RefComPrefMl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class RefComPrefMlDS {
		
    /** logger */
    //private static final Log log = LogFactory.getLog(RefPermissionsQuestionDS.class);
    
    @Autowired
    private RefComPrefMlRepository refComPrefMlRepository;

    @Transactional(readOnly=true)
	public RefComPrefMlDTO getById(Integer id) throws JrafDomainException {
		
    	Optional<RefComPrefMl> entity = refComPrefMlRepository.findById(id);
		
		if (!entity.isPresent()) {
			return null;
		}
		
		return RefComPrefMlTransform.bo2Dto(entity.get());
	}
	
	
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(RefComPrefMlDTO refComPrefMlDTO) throws JrafDomainException {
		
		RefComPrefMl entity = RefComPrefMlTransform.dto2Bo(refComPrefMlDTO);
		
		refComPrefMlRepository.saveAndFlush(entity);
		
		RefComPrefMlTransform.bo2Dto(entity,refComPrefMlDTO);
	}
	
	
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(RefComPrefMlDTO refComPrefMlDTO) throws JrafDomainException {
		
		RefComPrefMlDTO refComPrefMlDTOfromDB = getById(refComPrefMlDTO.getRefComPrefMlId());
		
		if (refComPrefMlDTOfromDB == null) {
			throw new InvalidParameterException("entity does not exists");
		}
		
		RefComPrefMlTransform.updateDto(refComPrefMlDTO, refComPrefMlDTOfromDB);
		
		RefComPrefMl entity = RefComPrefMlTransform.dto2Bo(refComPrefMlDTOfromDB);
		
		refComPrefMlRepository.saveAndFlush(entity);
	}
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void delete(RefComPrefMlDTO refComPrefMlDTO) throws JrafDomainException {
		
		RefComPrefMl entity = RefComPrefMlTransform.dto2Bo(refComPrefMlDTO);
		
		refComPrefMlRepository.deleteById(entity.getRefComPrefMlId());
	}
}
