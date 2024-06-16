package com.airfrance.repind.service.reference.internal;

import com.airfrance.repind.dao.reference.RefFctProOwnerRepository;
import com.airfrance.repind.dto.reference.RefFctProOwnerTransform;
import com.airfrance.repind.entity.reference.RefFctProOwner;
import com.airfrance.repind.entity.reference.RefFctProOwnerId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RefFctProOwnerDS {

    @Autowired
    private RefFctProOwnerRepository refFctProOwnerRepository;

    @Autowired
    private RefOwnerDS refOwnerDS;
  

    @Transactional(readOnly=true)
    public com.airfrance.repind.dto.reference.RefFctProOwnerDTO get(com.airfrance.repind.dto.reference.RefFctProOwnerDTO dto) throws com.airfrance.ref.exception.jraf.JrafDomainException {
    		return get(dto.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly=true)
    public com.airfrance.repind.dto.reference.RefFctProOwnerDTO get(RefFctProOwnerId id) throws com.airfrance.ref.exception.jraf.JrafDomainException {
    	Optional<RefFctProOwner> refFctProOwner = refFctProOwnerRepository.findById(id);
        if (!refFctProOwner.isPresent()) {
        	return null;
        }
        return RefFctProOwnerTransform.bo2DtoLight(refFctProOwner.get());
    }
    
	public RefFctProOwnerRepository getRefFctProOwnerRepository() {
		return refFctProOwnerRepository;
	}

	public void setRefFctProOwnerRepository(RefFctProOwnerRepository refFctProOwnerRepository) {
		this.refFctProOwnerRepository = refFctProOwnerRepository;
	}



}
