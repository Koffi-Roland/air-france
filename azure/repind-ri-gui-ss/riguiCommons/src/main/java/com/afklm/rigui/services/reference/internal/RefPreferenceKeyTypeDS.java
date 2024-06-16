package com.afklm.rigui.services.reference.internal;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.enums.TableReferenceEnum;
import com.afklm.rigui.dao.reference.RefPreferenceKeyTypeRepository;
import com.afklm.rigui.dto.reference.RefPreferenceKeyTypeDTO;
import com.afklm.rigui.dto.reference.RefPreferenceKeyTypeTransform;
import com.afklm.rigui.entity.reference.RefPreferenceKeyType;
import com.afklm.rigui.util.service.PaginationResult;
import com.afklm.rigui.util.service.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RefPreferenceKeyTypeDS {
	
    @Autowired
    private RefPreferenceKeyTypeRepository refPreferenceKeyTypeRepository;

    @Transactional(readOnly=true)
    public RefPreferenceKeyTypeDTO get(RefPreferenceKeyTypeDTO dto) throws JrafDomainException {
       return get(dto.getRefId());
    }
    @Transactional(readOnly=true)
    public RefPreferenceKeyTypeDTO get(Integer id) throws JrafDomainException {
    	 Optional<RefPreferenceKeyType> refPreferenceKeyType = refPreferenceKeyTypeRepository.findById(id);
         if (!refPreferenceKeyType.isPresent()) {
         	return null;
         }
         return RefPreferenceKeyTypeTransform.bo2DtoLight(refPreferenceKeyType.get());
    }

}
