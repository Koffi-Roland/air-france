package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.TableReferenceEnum;
import com.airfrance.repind.dao.reference.RefComPrefTypeRepository;
import com.airfrance.repind.dto.reference.RefComPrefTypeDTO;
import com.airfrance.repind.dto.reference.RefComPrefTypeTransform;
import com.airfrance.repind.entity.reference.RefComPrefType;
import com.airfrance.repind.util.service.PaginationResult;
import com.airfrance.repind.util.service.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RefComPrefTypeDS {

	@Autowired
	private RefComPrefTypeRepository refComPrefTypeRepository;

    @Transactional(readOnly=true)
	public PaginationResult<RefComPrefTypeDTO> provideComPrefTypeWithPagination(int index, int maxResults) throws JrafDomainException {
		PaginationResult<RefComPrefTypeDTO> paginationResult = new PaginationResult<RefComPrefTypeDTO>();
		
		Long totalResultsFound = refComPrefTypeRepository.count();

    	PaginationUtils.getIndexAndMaxResultsForPagination(paginationResult, index, maxResults, TableReferenceEnum.REF_COMPREF_TYPE, totalResultsFound);
    	
    	List<RefComPrefType> list = refComPrefTypeRepository.findAllByOrderByCodeType(PageRequest.of(paginationResult.getIndex(), paginationResult.getMaxResults()));
    	
    	for (RefComPrefType refComPrefType : list) {
    		paginationResult.getListResults().add(RefComPrefTypeTransform.bo2DtoLight(refComPrefType));
    	}
    	
    	return paginationResult;
	}


}
