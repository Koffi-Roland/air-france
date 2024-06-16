package com.afklm.rigui.services.reference.internal;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.dao.reference.RefPreferenceDataKeyRepository;
import com.afklm.rigui.dto.reference.RefPreferenceDataKeyDTO;
import com.afklm.rigui.dto.reference.RefPreferenceDataKeyTransform;
import com.afklm.rigui.entity.reference.RefPreferenceDataKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RefPreferenceDataKeyDS {

    @Autowired
    private RefPreferenceDataKeyRepository refPreferenceDataKeyRepository;

    @Transactional(readOnly=true)
    public RefPreferenceDataKeyDTO get(RefPreferenceDataKeyDTO dto) throws JrafDomainException {
      return get(dto.getCode());
    }

    @Transactional(readOnly=true)
    public RefPreferenceDataKeyDTO get(String id) throws JrafDomainException {
    	  Optional<RefPreferenceDataKey> refPreferenceDataKey =  refPreferenceDataKeyRepository.findById(id);
          if (!refPreferenceDataKey.isPresent()) {
          	return null;
          }
          return RefPreferenceDataKeyTransform.bo2DtoLight(refPreferenceDataKey.get());
    }
    
	public RefPreferenceDataKeyRepository getRefPreferenceDataKeyRepository() {
		return refPreferenceDataKeyRepository;
	}

	public void setRefPreferenceDataKeyRepository(RefPreferenceDataKeyRepository refPreferenceDataKeyRepository) {
		this.refPreferenceDataKeyRepository = refPreferenceDataKeyRepository;
	}

	
	public List<RefPreferenceDataKeyDTO> findAll() throws JrafDomainException {
			List<RefPreferenceDataKeyDTO> result = new ArrayList<>();
			for (RefPreferenceDataKey found : refPreferenceDataKeyRepository.findAll()) {
				result.add(RefPreferenceDataKeyTransform.bo2DtoLight(found));
				}
			return result;
	}
}
