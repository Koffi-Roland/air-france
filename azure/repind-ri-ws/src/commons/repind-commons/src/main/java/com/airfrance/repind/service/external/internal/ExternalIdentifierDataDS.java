package com.airfrance.repind.service.external.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dao.external.ExternalIdentifierDataRepository;
import com.airfrance.repind.dto.external.ExternalIdentifierDataDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDataTransform;
import com.airfrance.repind.entity.external.ExternalIdentifierData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExternalIdentifierDataDS {
	
	@Autowired
	private ExternalIdentifierDataRepository externalIdentifierDataRepository;


    @Transactional(readOnly=true)
	public List<ExternalIdentifierDataDTO> findExternalIdentifierData(long identifierId) throws JrafDomainException {

		List<ExternalIdentifierData> externalIdentifierDataList = externalIdentifierDataRepository.findExternalIdentifierData(identifierId);
		
		return ExternalIdentifierDataTransform.bo2DtoLight(externalIdentifierDataList);
	}

}
