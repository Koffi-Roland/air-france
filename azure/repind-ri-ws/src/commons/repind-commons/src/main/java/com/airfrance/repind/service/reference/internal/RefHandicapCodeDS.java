package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.TableReferenceEnum;
import com.airfrance.repind.dao.reference.RefHandicapCodeRepository;
import com.airfrance.repind.dto.reference.RefHandicapCodeDTO;
import com.airfrance.repind.dto.reference.RefHandicapCodeTransform;
import com.airfrance.repind.entity.reference.RefHandicapCode;
import com.airfrance.repind.util.service.PaginationResult;
import com.airfrance.repind.util.service.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RefHandicapCodeDS {
		
    /** logger */
    //private static final Log log = LogFactory.getLog(RefPermissionsQuestionDS.class);
    
    @Autowired
    private RefHandicapCodeRepository refHandicapCodeRepository;

    @Transactional(readOnly=true)
    public PaginationResult<RefHandicapCodeDTO> provideHandicapCodeWithPagination(int index, int maxResults) throws JrafDomainException {
    	PaginationResult<RefHandicapCodeDTO> paginationResult = new PaginationResult<RefHandicapCodeDTO>();
    	
    	Long totalResultsFound = refHandicapCodeRepository.count();

    	PaginationUtils.getIndexAndMaxResultsForPagination(paginationResult, index, maxResults, TableReferenceEnum.REF_HANDICAP_CODE, totalResultsFound);
    	
    	List<RefHandicapCode> listRefHandicapCode = refHandicapCodeRepository.findAllByOrderByCode(PageRequest.of(paginationResult.getIndex(), paginationResult.getMaxResults()));
    	
    	for (RefHandicapCode refHandicapCode : listRefHandicapCode) {
    		paginationResult.getListResults().add(RefHandicapCodeTransform.bo2Dto(refHandicapCode));
    	}
    	
    	return paginationResult;
    }
}
