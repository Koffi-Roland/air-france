package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.TableReferenceEnum;
import com.airfrance.repind.dao.reference.RefComPrefDomainRepository;
import com.airfrance.repind.dto.reference.RefComPrefDomainDTO;
import com.airfrance.repind.dto.reference.RefComPrefDomainTransform;
import com.airfrance.repind.entity.reference.RefComPrefDomain;
import com.airfrance.repind.util.service.PaginationResult;
import com.airfrance.repind.util.service.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RefComPrefDomainDS {


	@Autowired
	private RefComPrefDomainRepository refComPrefDomainRepository;


    @Transactional(readOnly=true)
	public PaginationResult<RefComPrefDomainDTO> provideComPrefDomainWithPagination(int index, int maxResults) throws JrafDomainException {
		PaginationResult<RefComPrefDomainDTO> paginationResult = new PaginationResult<RefComPrefDomainDTO>();
		
		Long totalResultsFound = refComPrefDomainRepository.count();

    	PaginationUtils.getIndexAndMaxResultsForPagination(paginationResult, index, maxResults, TableReferenceEnum.REF_COMPREF_DOMAIN, totalResultsFound);
    	
    	List<RefComPrefDomain> list = refComPrefDomainRepository.findAllByOrderByCodeDomain(PageRequest.of(paginationResult.getIndex(), paginationResult.getMaxResults()));
    	
    	for (RefComPrefDomain refComPrefDomain : list) {
    		paginationResult.getListResults().add(RefComPrefDomainTransform.bo2DtoLight(refComPrefDomain));
    	}
    	
    	return paginationResult;
	}


}
