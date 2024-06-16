package com.airfrance.repind.service.delegation.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.delegation.DelegationDataInfoRepository;
import com.airfrance.repind.dto.delegation.DelegationDataInfoDTO;
import com.airfrance.repind.dto.delegation.DelegationDataInfoTransform;
import com.airfrance.repind.entity.delegation.DelegationDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DelegationDataInfoDS {

	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;

	@Autowired
	private DelegationDataInfoRepository delegationDataInfoRepository;

    @Transactional(readOnly=true)
	public Integer countWhere(DelegationDataInfoDTO dto) throws JrafDomainException {
		DelegationDataInfo delegationDataInfo = DelegationDataInfoTransform.dto2BoLight(dto);
		return (int) delegationDataInfoRepository.count(Example.of(delegationDataInfo));
	}

	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(DelegationDataInfoDTO delegationDataInfoDTO) throws JrafDomainException {
		DelegationDataInfo delegationDataInfo = DelegationDataInfoTransform.dto2BoLight(delegationDataInfoDTO);
		delegationDataInfoRepository.saveAndFlush(delegationDataInfo);
		DelegationDataInfoTransform.bo2DtoLight(delegationDataInfo, delegationDataInfoDTO);
	}

	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(DelegationDataInfoDTO dto) throws JrafDomainException {
		DelegationDataInfo delegationDataInfo = DelegationDataInfoTransform.dto2BoLight(dto);
		delegationDataInfoRepository.deleteById(delegationDataInfo.getDelegationDataInfoId());
	}

	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(Serializable oid) throws JrafDomainException {
		delegationDataInfoRepository.deleteById((int) oid);
	}

	
	@Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(DelegationDataInfoDTO delegationDataInfoDTO) throws JrafDomainException {
		Optional<DelegationDataInfo> delegationDataInfo = delegationDataInfoRepository.findById(delegationDataInfoDTO.getDelegationDataInfoId());
		DelegationDataInfoTransform.dto2BoLight(delegationDataInfoDTO, delegationDataInfo.get());
	}


    @Transactional(readOnly=true)
	public List<DelegationDataInfoDTO> findByExample(DelegationDataInfoDTO dto) throws JrafDomainException {
		DelegationDataInfo delegationDataInfo = DelegationDataInfoTransform.dto2BoLight(dto);
		List<DelegationDataInfoDTO> results = new ArrayList<>();
		for (DelegationDataInfo result : delegationDataInfoRepository.findAll(Example.of(delegationDataInfo))) {
			results.add(DelegationDataInfoTransform.bo2DtoLight(result));
		}
		return results;
	}


    @Transactional(readOnly=true)
	public DelegationDataInfoDTO get(DelegationDataInfoDTO dto) throws JrafDomainException {
		return get(dto.getDelegationDataInfoId());
	}

    @Transactional(readOnly=true)
	public DelegationDataInfoDTO get(Serializable oid) throws JrafDomainException {
		Optional<DelegationDataInfo> delegationDataInfo = delegationDataInfoRepository.findById((int) oid);
		if (!delegationDataInfo.isPresent())
			return null;
		
		return DelegationDataInfoTransform.bo2DtoLight(delegationDataInfo.get());
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
