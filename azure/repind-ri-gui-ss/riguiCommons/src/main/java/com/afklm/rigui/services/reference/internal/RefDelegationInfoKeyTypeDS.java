package com.afklm.rigui.services.reference.internal;

import com.afklm.rigui.exception.InvalidParameterException;
import com.afklm.rigui.exception.jraf.JrafDaoException;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.dao.reference.RefDelegationInfoKeyTypeRepository;
import com.afklm.rigui.dto.reference.RefDelegationInfoKeyTypeDTO;
import com.afklm.rigui.dto.reference.RefDelegationInfoKeyTypeTransform;
import com.afklm.rigui.entity.reference.RefDelegationInfoKeyType;
import com.afklm.rigui.util.SicDateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class RefDelegationInfoKeyTypeDS  {

	/** logger */
	private static final Log log = LogFactory.getLog(RefPreferenceKeyTypeDS.class);

	@Autowired
	private RefDelegationInfoKeyTypeRepository refDelegationInfoKeyTypeRepository;

    @Transactional(readOnly=true)
	public RefDelegationInfoKeyTypeDTO get(RefDelegationInfoKeyTypeDTO dto) throws JrafDomainException {
		return get(dto.getRefId());
	}

    @Transactional(readOnly=true)
	public RefDelegationInfoKeyTypeDTO get(Serializable oid) throws JrafDomainException {
		Optional<RefDelegationInfoKeyType> refDelegationInfoKeyType = refDelegationInfoKeyTypeRepository.findById(((RefDelegationInfoKeyType)oid).getRefId());

		// transformation light bo -> dto
		if (!refDelegationInfoKeyType.isPresent()) {
			return null;
		}
		return RefDelegationInfoKeyTypeTransform.bo2DtoLight(refDelegationInfoKeyType.get());
	}

}
