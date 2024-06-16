package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.RefOwnerRepository;
import com.airfrance.repind.dto.reference.RefOwnerDTO;
import com.airfrance.repind.dto.reference.RefOwnerTransform;
import com.airfrance.repind.entity.reference.RefOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RefOwnerDS {
    @Autowired
    private RefOwnerRepository refOwnerRepository;
    
    public long countAll(RefOwnerDTO dto) throws JrafDomainException {
    	RefOwner email = RefOwnerTransform.dto2BoLight(dto);
		return refOwnerRepository.count(Example.of(email));
	}

    @Transactional(readOnly=true)
    public RefOwnerDTO get(RefOwnerDTO dto) throws JrafDomainException {
        return get(dto.getOwnerId());
    }

    @Transactional(readOnly=true)
    public RefOwnerDTO get(Integer id) throws JrafDomainException {
    	Optional<RefOwner> refOwner = refOwnerRepository.findById(id);
        if (!refOwner.isPresent()) {
        	return null;
        }
        return RefOwnerTransform.bo2DtoLight(refOwner.get());
    }

	public RefOwnerRepository getRefOwnerRepository() {
		return refOwnerRepository;
	}

	public void setRefOwnerRepository(RefOwnerRepository refOwnerRepository) {
		this.refOwnerRepository = refOwnerRepository;
	}
    
}
