package com.afklm.rigui.services.reference.internal;

import com.afklm.rigui.dao.reference.RefPreferenceKeyRepository;
import com.afklm.rigui.dto.reference.RefPreferenceKeyDTO;
import com.afklm.rigui.dto.reference.RefPreferenceKeyTransform;
import com.afklm.rigui.entity.reference.RefPreferenceKey;
import com.afklm.rigui.exception.jraf.JrafDomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RefPreferenceKeyDS {

    @Autowired
    private RefPreferenceKeyRepository refPreferenceKeyRepository;

    @Transactional(readOnly=true)
    public RefPreferenceKeyDTO get(RefPreferenceKeyDTO dto) throws JrafDomainException {
       return get(dto.getCodeKey());
    }

    @Transactional(readOnly=true)
    public RefPreferenceKeyDTO get(String id) throws JrafDomainException {
    	 Optional<RefPreferenceKey> refPreferenceKey = refPreferenceKeyRepository.findById(id);
         if (!refPreferenceKey.isPresent()) {
         	return null;
         }
         return RefPreferenceKeyTransform.bo2DtoLight(refPreferenceKey.get());
    }


    public RefPreferenceKeyRepository getRefPreferenceKeyRepository() {
		return refPreferenceKeyRepository;
	}

	public void setRefPreferenceKeyRepository(RefPreferenceKeyRepository refPreferenceKeyRepository) {
		this.refPreferenceKeyRepository = refPreferenceKeyRepository;
	}
}
