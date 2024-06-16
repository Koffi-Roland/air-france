package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.TableReferenceEnum;
import com.airfrance.repind.dao.reference.RefComPrefGTypeRepository;
import com.airfrance.repind.dto.reference.RefComPrefGTypeDTO;
import com.airfrance.repind.dto.reference.RefComPrefGTypeTransform;
import com.airfrance.repind.entity.reference.RefComPrefGType;
import com.airfrance.repind.util.service.PaginationResult;
import com.airfrance.repind.util.service.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RefComPrefGTypeDS {

	@Autowired
	private RefComPrefGTypeRepository refComPrefGTypeRepository;

    @Transactional(readOnly=true)
	public PaginationResult<RefComPrefGTypeDTO> provideComPrefGTypeWithPagination(int index, int maxResults) throws JrafDomainException {
		PaginationResult<RefComPrefGTypeDTO> paginationResult = new PaginationResult<RefComPrefGTypeDTO>();
		
		Long totalResultsFound = refComPrefGTypeRepository.count();

    	PaginationUtils.getIndexAndMaxResultsForPagination(paginationResult, index, maxResults, TableReferenceEnum.REF_COMPREF_GTYPE, totalResultsFound);
    	
    	List<RefComPrefGType> list = refComPrefGTypeRepository.findAllByOrderByCodeGType(PageRequest.of(paginationResult.getIndex(), paginationResult.getMaxResults()));
    	
    	for (RefComPrefGType refComPrefGType : list) {
    		paginationResult.getListResults().add(RefComPrefGTypeTransform.bo2DtoLight(refComPrefGType));
    	}
    	
    	return paginationResult;
	}


}
