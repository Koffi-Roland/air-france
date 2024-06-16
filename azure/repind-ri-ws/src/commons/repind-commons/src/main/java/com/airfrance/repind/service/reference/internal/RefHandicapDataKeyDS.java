package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.TableReferenceEnum;
import com.airfrance.repind.dao.reference.RefHandicapDataKeyRepository;
import com.airfrance.repind.dto.reference.RefHandicapDataKeyDTO;
import com.airfrance.repind.dto.reference.RefHandicapDataKeyTransform;
import com.airfrance.repind.entity.reference.RefHandicapDataKey;
import com.airfrance.repind.util.service.PaginationResult;
import com.airfrance.repind.util.service.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RefHandicapDataKeyDS {
		
    /** logger */
    //private static final Log log = LogFactory.getLog(RefPermissionsQuestionDS.class);
    
    @Autowired
    private RefHandicapDataKeyRepository refHandicapDataKeyRepository;

    @Transactional(readOnly=true)
    public PaginationResult<RefHandicapDataKeyDTO> provideHandicapDataKeyWithPagination(int index, int maxResults) throws JrafDomainException {
    	PaginationResult<RefHandicapDataKeyDTO> paginationResult = new PaginationResult<RefHandicapDataKeyDTO>();
    	
    	Long totalResultsFound = refHandicapDataKeyRepository.count();

    	PaginationUtils.getIndexAndMaxResultsForPagination(paginationResult, index, maxResults, TableReferenceEnum.REF_HANDICAP_DATA_KEY, totalResultsFound);
    	
    	List<RefHandicapDataKey> listRefHandicapDataKey = refHandicapDataKeyRepository.findAllByOrderByCode(PageRequest.of(paginationResult.getIndex(), paginationResult.getMaxResults()));
    	
    	for (RefHandicapDataKey refHandicapDataKey : listRefHandicapDataKey) {
    		paginationResult.getListResults().add(RefHandicapDataKeyTransform.bo2Dto(refHandicapDataKey));
    	}
    	
    	return paginationResult;
    }
}
