package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.TableReferenceEnum;
import com.airfrance.repind.dao.reference.RefComPrefMediaRepository;
import com.airfrance.repind.dto.reference.RefComPrefMediaDTO;
import com.airfrance.repind.dto.reference.RefComPrefMediaTransform;
import com.airfrance.repind.entity.reference.RefComPrefMedia;
import com.airfrance.repind.util.service.PaginationResult;
import com.airfrance.repind.util.service.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RefComPrefMediaDS {


	@Autowired
	private RefComPrefMediaRepository refComPrefMediaRepository;

    @Transactional(readOnly=true)
	public PaginationResult<RefComPrefMediaDTO> provideComPrefMediaWithPagination(int index, int maxResults) throws JrafDomainException {
		
		PaginationResult<RefComPrefMediaDTO> paginationResult = new PaginationResult<RefComPrefMediaDTO>();
		
		Long totalResultsFound = refComPrefMediaRepository.count();

    	PaginationUtils.getIndexAndMaxResultsForPagination(paginationResult, index, maxResults, TableReferenceEnum.REF_COMPREF_MEDIA, totalResultsFound);
    	
    	List<RefComPrefMedia> list = refComPrefMediaRepository.findAllByOrderByCodeMedia(PageRequest.of(paginationResult.getIndex(), paginationResult.getMaxResults()));
    	
    	for (RefComPrefMedia refComPrefMedia : list) {
    		paginationResult.getListResults().add(RefComPrefMediaTransform.bo2DtoLight(refComPrefMedia));
    	}
    	
    	return paginationResult;
		
	}


}
