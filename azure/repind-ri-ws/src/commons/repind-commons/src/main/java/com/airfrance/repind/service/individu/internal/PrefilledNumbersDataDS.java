package com.airfrance.repind.service.individu.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.individu.PrefilledNumbersDataRepository;
import com.airfrance.repind.dto.individu.PrefilledNumbersDataDTO;
import com.airfrance.repind.dto.individu.PrefilledNumbersDataTransform;
import com.airfrance.repind.entity.individu.PrefilledNumbersData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrefilledNumbersDataDS {
    @Autowired
	private PrefilledNumbersDataRepository prefilledNumbersDataRepository;
    
	@Deprecated
	public PrefilledNumbersDataDTO get(PrefilledNumbersDataDTO dto) throws JrafDomainException {
		return get(dto.getPrefilledNumbersDataId());
	}

	public PrefilledNumbersDataDTO get(Integer id) throws JrafDomainException {
		Optional<PrefilledNumbersData> email = prefilledNumbersDataRepository.findById(id);
		if (!email.isPresent()) {
			return null;
		}
		return PrefilledNumbersDataTransform.bo2DtoLight(email.get());
	}
    
}
