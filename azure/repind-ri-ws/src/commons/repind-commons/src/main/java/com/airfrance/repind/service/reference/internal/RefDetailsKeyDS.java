package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.RefDetailsKeyRepository;
import com.airfrance.repind.dto.reference.RefDetailsKeyDTO;
import com.airfrance.repind.dto.reference.RefDetailsKeyTransform;
import com.airfrance.repind.entity.reference.RefDetailsKey;
import com.airfrance.repind.entity.reference.RefTypeEvent;
import com.airfrance.repind.exception.TooManyResultsDaoException;
import com.airfrance.repind.exception.TooManyResultsDomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class RefDetailsKeyDS {

    @Autowired
    private RefDetailsKeyRepository refDetailsKeyRepository;


    @Transactional(readOnly=true)
    public RefDetailsKeyDTO get(RefDetailsKeyDTO dto) throws JrafDomainException {
        return get(dto.getCode());
    }

    @Transactional(readOnly=true)
    public RefDetailsKeyDTO get(Serializable oid) throws JrafDomainException {
    	Optional<RefDetailsKey> refDetailsKey = refDetailsKeyRepository.findById(((RefDetailsKey)oid).getCode());
        if (!refDetailsKey.isPresent()) {
        	return null;
        }
        return RefDetailsKeyTransform.bo2DtoLight(refDetailsKey.get());
    }

    @Transactional(readOnly=true)
	public List<RefDetailsKeyDTO> findByTypeEvent(RefTypeEvent type) throws JrafDomainException {
    
    	List<RefDetailsKeyDTO> dto = null;
    	
    	try {
    		List<RefDetailsKey> list = refDetailsKeyRepository.findRefDetailsKeyByTypeEvent(type);
    		dto = RefDetailsKeyTransform.bo2DtoLight(list);
	    }
	    catch (TooManyResultsDaoException e){
	    	throw new TooManyResultsDomainException(e.getMessage());
	    }
    	
    	return dto;
    }
}
