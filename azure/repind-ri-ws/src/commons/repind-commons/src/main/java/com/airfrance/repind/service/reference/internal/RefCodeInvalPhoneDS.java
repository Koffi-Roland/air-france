package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.reference.RefCodeInvalPhoneRepository;
import com.airfrance.repind.dto.reference.RefCodeInvalPhoneDTO;
import com.airfrance.repind.dto.reference.RefCodeInvalPhoneTransform;
import com.airfrance.repind.entity.reference.RefCodeInvalPhone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RefCodeInvalPhoneDS {

    @Autowired
    private RefCodeInvalPhoneRepository refCodeInvalPhoneRepository;

    @Transactional(readOnly=true)
    public RefCodeInvalPhoneDTO get(RefCodeInvalPhoneDTO dto) throws JrafDomainException {
        return get(dto.getId());
    }

    @Transactional(readOnly=true)
    public RefCodeInvalPhoneDTO get(Serializable oid) throws JrafDomainException {
    	Optional<RefCodeInvalPhone> refCodeInvalPhone = refCodeInvalPhoneRepository.findById(((RefCodeInvalPhone)oid).getId());
        
        // transformation light bo -> dto
        if (!refCodeInvalPhone.isPresent()) {
        	return null;
        }
        return RefCodeInvalPhoneTransform.bo2DtoLight(refCodeInvalPhone.get());
    }

	
	public List<RefCodeInvalPhoneDTO> findByExample(RefCodeInvalPhoneDTO refCodeInvalPhoneDTO) throws JrafDomainException {
		RefCodeInvalPhone email = RefCodeInvalPhoneTransform.dto2BoLight(refCodeInvalPhoneDTO);
		List<RefCodeInvalPhoneDTO> result = new ArrayList<>();
		for (RefCodeInvalPhone found : refCodeInvalPhoneRepository.findAll(Example.of(email))) {
			result.add(RefCodeInvalPhoneTransform.bo2DtoLight(found));
			}
		return result;
	}
}
