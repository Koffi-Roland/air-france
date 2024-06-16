package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.reference.RefComPrefGroupInfoRepository;
import com.airfrance.repind.dto.reference.RefComPrefGroupInfoDTO;
import com.airfrance.repind.dto.reference.RefComPrefGroupInfoTransform;
import com.airfrance.repind.entity.reference.RefComPrefGroupInfo;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class RefComPrefGroupInfoDS {
		
    /** logger */
    //private static final Log log = LogFactory.getLog(RefPermissionsQuestionDS.class);
    
    @Autowired
    private RefComPrefGroupInfoRepository refComPrefGroupInfoRepository;

    @Transactional(readOnly=true)
	public RefComPrefGroupInfoDTO getById(Integer id) throws JrafDomainException {
		
    	Optional<RefComPrefGroupInfo> entity = refComPrefGroupInfoRepository.findById(id);
		
		if (!entity.isPresent()) {
			return null;
		}
			
		return RefComPrefGroupInfoTransform.bo2Dto(entity.get());
	}
	

    @Transactional(readOnly=true)
	public RefComPrefGroupInfoDTO getByExample(RefComPrefGroupInfoDTO refComPrefGroupInfoDTO) throws JrafDomainException, NonUniqueResultException {

		RefComPrefGroupInfo entity = RefComPrefGroupInfoTransform.dto2Bo(refComPrefGroupInfoDTO);
		
		Optional<RefComPrefGroupInfo> result = refComPrefGroupInfoRepository.findOne(Example.of(entity));
		
		if (!result.isPresent()) {
			return null;
		}
		
		return RefComPrefGroupInfoTransform.bo2Dto(entity);
	}	

	
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(RefComPrefGroupInfoDTO refComPrefGroupInfoDTO) throws JrafDomainException {
		
		RefComPrefGroupInfo entity = RefComPrefGroupInfoTransform.dto2Bo(refComPrefGroupInfoDTO);
		
		refComPrefGroupInfoRepository.saveAndFlush(entity);
		
		RefComPrefGroupInfoTransform.bo2Dto(entity, refComPrefGroupInfoDTO);
	}
	
	
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(RefComPrefGroupInfoDTO refComPrefGroupInfoDTO) throws JrafDomainException {
		
		RefComPrefGroupInfoDTO refComPrefGroupInfoDTOFromDB = getById(refComPrefGroupInfoDTO.getId());
		
		if (refComPrefGroupInfoDTOFromDB == null) {
			throw new InvalidParameterException("entity does not exists");
		}
		
		RefComPrefGroupInfoTransform.updateDto(refComPrefGroupInfoDTO, refComPrefGroupInfoDTOFromDB);
		
		RefComPrefGroupInfo entity = RefComPrefGroupInfoTransform.dto2Bo(refComPrefGroupInfoDTOFromDB);
		
		refComPrefGroupInfoRepository.saveAndFlush(entity);
	}
	

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void delete(RefComPrefGroupInfoDTO refComPrefGroupInfoDTO) throws JrafDomainException {
    	refComPrefGroupInfoRepository.deleteById(refComPrefGroupInfoDTO.getId());
	}
}
