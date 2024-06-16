package com.afklm.rigui.services.reference.internal;

import com.afklm.rigui.dao.reference.RefDelegationInfoTypeRepository;
import com.afklm.rigui.dto.reference.RefDelegationInfoTypeDTO;
import com.afklm.rigui.dto.reference.RefDelegationInfoTypeTransform;
import com.afklm.rigui.entity.reference.RefDelegationInfoType;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

@Service
public class RefDelegationInfoTypeDS {

    /** main dao */
    @Autowired
    private RefDelegationInfoTypeRepository refDelegationInfoTypeRepository;

    @Transactional(readOnly=true)
    public RefDelegationInfoTypeDTO get(RefDelegationInfoTypeDTO dto) throws JrafDomainException {
      return get(dto.getCode());
    }

    @Transactional(readOnly=true)
    public RefDelegationInfoTypeDTO get(Serializable oid) throws JrafDomainException {
    	  Optional<RefDelegationInfoType> refDelegationInfoType = refDelegationInfoTypeRepository.findById(((RefDelegationInfoType)oid).getCode());
          if (!refDelegationInfoType.isPresent()) {
          	return null;
          }
          return RefDelegationInfoTypeTransform.bo2DtoLight(refDelegationInfoType.get());
    }
}
