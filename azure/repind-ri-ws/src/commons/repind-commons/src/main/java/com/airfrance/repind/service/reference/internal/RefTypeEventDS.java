package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.RefTypeEventRepository;
import com.airfrance.repind.dto.reference.RefTypeEventDTO;
import com.airfrance.repind.dto.reference.RefTypeEventTransform;
import com.airfrance.repind.entity.reference.RefTypeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RefTypeEventDS {

    @Autowired
    private RefTypeEventRepository refTypeEventRepository;


    @Transactional(readOnly=true)
    public RefTypeEventDTO get(RefTypeEventDTO dto) throws JrafDomainException {
      return get(dto.getCode());
    }

    @Transactional(readOnly=true)
    public RefTypeEventDTO get(String code) throws JrafDomainException {
    	  Optional<RefTypeEvent> refTypeEvent = refTypeEventRepository.findById(code);
	      if (!refTypeEvent.isPresent()) {
	      	return null;
	      }
	      return RefTypeEventTransform.bo2DtoLight(refTypeEvent.get());
    }
    
	public RefTypeEventRepository getRefTypeEventRepository() {
		return refTypeEventRepository;
	}

	public void setRefTypeEventRepository(RefTypeEventRepository refTypeEventRepository) {
		this.refTypeEventRepository = refTypeEventRepository;
	}


    @Transactional(readOnly=true)
	public List<RefTypeEventDTO> findAll() throws InvalidParameterException, JrafDomainException {
		List<RefTypeEventDTO> result = new ArrayList<>();
		for (RefTypeEvent found : refTypeEventRepository.findAll()) {
			result.add(RefTypeEventTransform.bo2DtoLight(found));
		}
		return result;
	}
}
