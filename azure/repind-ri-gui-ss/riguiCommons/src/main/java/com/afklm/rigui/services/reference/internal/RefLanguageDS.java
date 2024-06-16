package com.afklm.rigui.services.reference.internal;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.dao.reference.RefLanguageRepository;
import com.afklm.rigui.dto.reference.RefLanguageDTO;
import com.afklm.rigui.dto.reference.RefLanguageTransform;
import com.afklm.rigui.entity.reference.RefLanguage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

@Service
public class RefLanguageDS {
    @Autowired
    private RefLanguageRepository refLanguageRepository;

    @Transactional(readOnly=true)
    public RefLanguageDTO get(RefLanguageDTO dto) throws JrafDomainException {
       return get(dto.getLanguageCode());
    }

    @Transactional(readOnly=true)
    public RefLanguageDTO get(Serializable oid) throws JrafDomainException {
    	 Optional<RefLanguage> refLanguage = refLanguageRepository.findById(((String)oid));
         if (!refLanguage.isPresent()) {
         	return null;
         }
         return RefLanguageTransform.bo2DtoLight(refLanguage.get());
    }

    @Transactional(readOnly=true)
    public Boolean isValidLanguageCode(String languageCode) throws JrafDomainException {
    	
    	// language code empty -> error
    	if(StringUtils.isEmpty(languageCode)) {
    		return false;
    	}
    	
    	// prepare search
    	RefLanguageDTO refLanguageDTO = new RefLanguageDTO();
    	refLanguageDTO.setLanguageCode(languageCode);
    	
    	// search language in DB
    	return get(refLanguageDTO) != null;
    }
 }
