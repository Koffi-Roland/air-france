package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.reference.RefComPrefGroupRepository;
import com.airfrance.repind.dao.reference.RefProductComPrefGroupRepository;
import com.airfrance.repind.dto.reference.*;
import com.airfrance.repind.entity.reference.RefComPrefGroup;
import com.airfrance.repind.entity.reference.RefProduct;
import com.airfrance.repind.entity.reference.RefProductComPrefGroup;
import com.airfrance.repind.entity.reference.RefProductComPrefGroupId;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class RefProductComPrefGroupDS {
		
    /** logger */
    //private static final Log log = LogFactory.getLog(RefPermissionsQuestionDS.class);
    
    @Autowired
    private RefProductComPrefGroupRepository refProductComPrefGroupRepository;
    
    @Autowired
    private RefComPrefGroupRepository refComPrefGroupRepository;

    @Transactional(readOnly=true)
	public RefProductComPrefGroupDTO getById(RefProductComPrefGroupIdDTO refProductComPrefGroupIdDTO) throws JrafDomainException {
		
		RefProductComPrefGroupId id = RefProductComPrefGroupIdTransform.dto2Bo(refProductComPrefGroupIdDTO);
		
		Optional<RefProductComPrefGroup> entity = refProductComPrefGroupRepository.findById(id);
		
		if (!entity.isPresent()) {
			return null;
		}
		
		return RefProductComPrefGroupTransform.bo2Dto(entity.get());
	}
	

    @Transactional(readOnly=true)
	public RefProductComPrefGroupDTO getByExample(RefProductComPrefGroupDTO refProductComPrefGroupDTO) throws JrafDomainException, NonUniqueResultException {

		RefProductComPrefGroup entity = RefProductComPrefGroupTransform.dto2Bo(refProductComPrefGroupDTO);
		
		entity = refProductComPrefGroupRepository.findOne(Example.of(entity)).get();
		
		if (entity == null) {
			return null;
		}
	
		return RefProductComPrefGroupTransform.bo2Dto(entity);
	}
	

    @Transactional(readOnly=true)
	public List<RefComPrefGroupDTO> getAllRefComPrefGroupByProductId(RefProductDTO refProductDTO) throws JrafDomainException {
		
		RefProduct refProduct = RefProductTransform.dto2BoLight(refProductDTO);
		
		List<RefProductComPrefGroup> listRefProductComPrefGroup = refProductComPrefGroupRepository.getAllRefComPrefGroupByProductId(refProduct.getProductId());
		
		List<RefComPrefGroupDTO> listRefComPrefGroup = new ArrayList<RefComPrefGroupDTO>();
		
		for (RefProductComPrefGroup refProductComPrefGroup : listRefProductComPrefGroup) {
			List<RefComPrefGroup> listRefComPrefGroupAdd = refComPrefGroupRepository.getAllRefComPrefGroupByGroupInfo(refProductComPrefGroup.getRefProductComPrefGroupId().getRefComPrefGroupInfo().getId());
			if (listRefComPrefGroupAdd != null) {
				for (RefComPrefGroup refComPrefGroup : listRefComPrefGroupAdd) {
					RefComPrefGroupDTO dto = new RefComPrefGroupDTO();
					RefComPrefGroupTransform.bo2Dto(refComPrefGroup, dto);
					listRefComPrefGroup.add(dto);
				}
			}
		}
		
		return listRefComPrefGroup;
	}
	

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(RefProductComPrefGroupDTO refProductComPrefGroupDTO) throws JrafDomainException {
		
		RefProductComPrefGroup entity = RefProductComPrefGroupTransform.dto2Bo(refProductComPrefGroupDTO);
		
		refProductComPrefGroupRepository.saveAndFlush(entity);
		
		RefProductComPrefGroupTransform.bo2Dto(entity, refProductComPrefGroupDTO);
	}
	
	
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void delete(RefProductComPrefGroupDTO refProductComPrefGroupDTO) throws JrafDomainException {
		
		RefProductComPrefGroup entity = RefProductComPrefGroupTransform.dto2Bo(refProductComPrefGroupDTO);
		
		refProductComPrefGroupRepository.deleteById(entity.getRefProductComPrefGroupId());
	}
}
