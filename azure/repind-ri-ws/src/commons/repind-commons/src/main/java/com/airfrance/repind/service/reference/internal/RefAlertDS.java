package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.RefAlertRepository;
import com.airfrance.repind.dto.reference.RefAlertDTO;
import com.airfrance.repind.dto.reference.RefAlertTransform;
import com.airfrance.repind.entity.reference.RefAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RefAlertDS {
	
    @Autowired
    private RefAlertRepository refAlertRepository;


    @Transactional(readOnly=true)
    public RefAlertDTO get(RefAlertDTO dto) throws JrafDomainException {
       return get(dto.getKey());
    }

    @Transactional(readOnly=true)
    public RefAlertDTO get(Serializable oid) throws JrafDomainException {
    	 Optional<RefAlert> refAlert = refAlertRepository.findById(((RefAlert)oid).getKey());
         // transformation light bo -> dto
         if (!refAlert.isPresent()) {
         	return null;
         }
         return RefAlertTransform.bo2DtoLight(refAlert.get());
    }

	public List<RefAlertDTO> findAll(RefAlertDTO searchRef) throws JrafDomainException {
		RefAlert email = RefAlertTransform.dto2BoLight(searchRef);
		List<RefAlertDTO> result = new ArrayList<>();
		
		for (RefAlert found : refAlertRepository.findAll(Example.of(email))) {
			result.add(RefAlertTransform.bo2DtoLight(found));
			}
		return result;
	}

	
	public List<RefAlertDTO> findAll() throws JrafDomainException {
		List<RefAlertDTO> result = new ArrayList<>();
		for (RefAlert found : refAlertRepository.findAll()) {
			result.add(RefAlertTransform.bo2DtoLight(found));
			}
		return result;
	}

}
