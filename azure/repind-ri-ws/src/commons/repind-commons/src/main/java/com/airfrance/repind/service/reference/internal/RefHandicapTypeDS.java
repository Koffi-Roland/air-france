package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.TableReferenceEnum;
import com.airfrance.repind.dao.reference.RefHandicapTypeRepository;
import com.airfrance.repind.dto.reference.RefHandicapTypeDTO;
import com.airfrance.repind.dto.reference.RefHandicapTypeTransform;
import com.airfrance.repind.entity.reference.RefHandicapType;
import com.airfrance.repind.util.service.PaginationResult;
import com.airfrance.repind.util.service.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RefHandicapTypeDS {
		
    /** logger */
    //private static final Log log = LogFactory.getLog(RefPermissionsQuestionDS.class);
    
    @Autowired
    private RefHandicapTypeRepository refHandicapTypeRepository;

    @Transactional(readOnly=true)
    public PaginationResult<RefHandicapTypeDTO> provideHandicapTypeWithPagination(int index, int maxResults) throws JrafDomainException {
    	PaginationResult<RefHandicapTypeDTO> paginationResult = new PaginationResult<RefHandicapTypeDTO>();
    	
    	Long totalResultsFound = refHandicapTypeRepository.count();

    	PaginationUtils.getIndexAndMaxResultsForPagination(paginationResult, index, maxResults, TableReferenceEnum.REF_HANDICAP_TYPE, totalResultsFound);
    	
    	List<RefHandicapType> listRefHandicapType = refHandicapTypeRepository.findAllByOrderByCode(PageRequest.of(paginationResult.getIndex(), paginationResult.getMaxResults()));
    	
    	for (RefHandicapType refHandicapType : listRefHandicapType) {
    		paginationResult.getListResults().add(RefHandicapTypeTransform.bo2Dto(refHandicapType));
    	}
    	
    	return paginationResult;
    }
}
