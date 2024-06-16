package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.RefTypExtIDRepository;
import com.airfrance.repind.dto.reference.RefTypExtIDDTO;
import com.airfrance.repind.dto.reference.RefTypExtIDTransform;
import com.airfrance.repind.entity.reference.RefTypExtID;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RefTypExtIDDS {

    @Autowired
    private RefTypExtIDRepository refTypExtIDRepository;

    @Transactional(readOnly=true)
    public RefTypExtIDDTO get(RefTypExtIDDTO dto) throws JrafDomainException {
       return get(dto.getExtID());
    }

    @Transactional(readOnly=true)
    public RefTypExtIDDTO get(String id) throws JrafDomainException {
    	 Optional<RefTypExtID> refTypExtID = refTypExtIDRepository.findById(id);
         if (!refTypExtID.isPresent()) {
         	return null;
         }
         return RefTypExtIDTransform.bo2DtoLight(refTypExtID.get());
    }


    @Transactional(readOnly=true)
    public String getExtIdByOption(String option) throws JrafDomainException {
    	List<RefTypExtID> rteids = option != null ? refTypExtIDRepository.findByOption(option) : null;
    	if (rteids != null && !rteids.isEmpty()) {
    		return rteids.get(0).getExtID();
    	}
    	return null;
    }
    

    @Transactional(readOnly=true)
    public boolean isValid(String type) throws JrafDomainException {
    	
    	if (StringUtils.isEmpty(type)) {
    		return false; 
    	}
    	
    	return get(type) != null;
    }

	
	public List<RefTypExtIDDTO> findAll() throws InvalidParameterException, JrafDomainException {
		List<RefTypExtIDDTO> result = new ArrayList<>();
		for (RefTypExtID found : refTypExtIDRepository.findAll()) {
			result.add(RefTypExtIDTransform.bo2DtoLight(found));
			}
		return result;
	}
}
