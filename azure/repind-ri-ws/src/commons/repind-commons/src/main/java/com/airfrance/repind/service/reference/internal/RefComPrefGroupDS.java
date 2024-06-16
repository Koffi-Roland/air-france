package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.reference.RefComPrefGroupRepository;
import com.airfrance.repind.dto.reference.RefComPrefGroupDTO;
import com.airfrance.repind.dto.reference.RefComPrefGroupIdDTO;
import com.airfrance.repind.dto.reference.RefComPrefGroupIdTransform;
import com.airfrance.repind.dto.reference.RefComPrefGroupTransform;
import com.airfrance.repind.entity.reference.RefComPrefGroup;
import com.airfrance.repind.entity.reference.RefComPrefGroupId;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class RefComPrefGroupDS {
		
    /** logger */
    //private static final Log log = LogFactory.getLog(RefPermissionsQuestionDS.class);
    
    @Autowired
    private RefComPrefGroupRepository refComPrefGroupRepository;

    @Transactional(readOnly=true)
	public RefComPrefGroupDTO getById(RefComPrefGroupIdDTO refComPrefGroupIdDTO) throws JrafDomainException {
		
		RefComPrefGroupId id = RefComPrefGroupIdTransform.dto2Bo(refComPrefGroupIdDTO);
		
		Optional<RefComPrefGroup> entity = refComPrefGroupRepository.findById(id);
		
		if (!entity.isPresent()) {
			return null;
		}
		
		return RefComPrefGroupTransform.bo2Dto(entity.get());
	}
	

    @Transactional(readOnly=true)
	public RefComPrefGroupDTO getByExample(RefComPrefGroupDTO refComPrefGroupDTO) throws JrafDomainException, NonUniqueResultException {

		RefComPrefGroup entity = RefComPrefGroupTransform.dto2Bo(refComPrefGroupDTO);
		
		entity = refComPrefGroupRepository.findOne(Example.of(entity)).get();
		
		if (entity == null) {
			return null;
		}
	
		return RefComPrefGroupTransform.bo2Dto(entity);
	}	

	
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(RefComPrefGroupDTO refComPrefGroupDTO) throws JrafDomainException {
		
		RefComPrefGroup entity = RefComPrefGroupTransform.dto2Bo(refComPrefGroupDTO);
		
		refComPrefGroupRepository.saveAndFlush(entity);
		
		RefComPrefGroupTransform.bo2Dto(entity, refComPrefGroupDTO);
	}
	

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void delete(RefComPrefGroupDTO refComPrefGroupDTO) throws JrafDomainException {
		
		RefComPrefGroup entity = RefComPrefGroupTransform.dto2Bo(refComPrefGroupDTO);
		
		refComPrefGroupRepository.deleteById(entity.getRefComPrefGroupId());
	}
}
