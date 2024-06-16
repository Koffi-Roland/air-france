package com.airfrance.repind.service.identifier.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.identifier.InternalIdentifierRepository;
import com.airfrance.repind.dto.identifier.InternalIdentifierDTO;
import com.airfrance.repind.dto.identifier.InternalIdentifierTransform;
import com.airfrance.repind.entity.identifier.InternalIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

@Service
public class InternalIdentifierDS {
    @Autowired
    private InternalIdentifierRepository internalIdentifierRepository;

    @Transactional(readOnly=true)
    public InternalIdentifierDTO get(InternalIdentifierDTO dto) throws JrafDomainException {
		return get(dto.getIdentifierId());
    }

    @Transactional(readOnly=true)
    public InternalIdentifierDTO get(Serializable oid) throws JrafDomainException {
    	Optional<InternalIdentifier> email = internalIdentifierRepository.findById((Long) oid);
		if (!email.isPresent())
			return null;
    	
		return InternalIdentifierTransform.bo2DtoLight(email.get());
    }
}
