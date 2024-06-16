package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.ref.type.TableReferenceEnum;
import com.airfrance.repind.dao.reference.RefPermissionsQuestionRepository;
import com.airfrance.repind.dto.reference.RefPermissionsQuestionDTO;
import com.airfrance.repind.dto.reference.RefPermissionsQuestionTransform;
import com.airfrance.repind.entity.reference.RefPermissionsQuestion;
import com.airfrance.repind.util.service.PaginationResult;
import com.airfrance.repind.util.service.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class RefPermissionsQuestionDS {
		
    /** logger */
    //private static final Log log = LogFactory.getLog(RefPermissionsQuestionDS.class);
    
    @Autowired
    private RefPermissionsQuestionRepository refPermissionsQuestionRepository;

    @Transactional(readOnly=true)
	public RefPermissionsQuestionDTO getById(Integer id) {
		
    	Optional<RefPermissionsQuestion> entity = refPermissionsQuestionRepository.findById(id);
		
		if (!entity.isPresent()) {
			return null;
		}
		
		return RefPermissionsQuestionTransform.bo2Dto(entity.get());
	}
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(RefPermissionsQuestionDTO refPermissionsQuestionDTO) throws InvalidParameterException {
		
		RefPermissionsQuestion entity = RefPermissionsQuestionTransform.dto2Bo(refPermissionsQuestionDTO);
		
		refPermissionsQuestionRepository.saveAndFlush(entity);
		
		RefPermissionsQuestionTransform.bo2Dto(entity, refPermissionsQuestionDTO);
	}
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void update(RefPermissionsQuestionDTO refPermissionsQuestionDTO) throws InvalidParameterException {
		
		RefPermissionsQuestionDTO refPermissionsQuestionDTOFromDB = getById(refPermissionsQuestionDTO.getId());
		
		if (refPermissionsQuestionDTOFromDB == null) {
			throw new InvalidParameterException("entity does not exists");
		}
		
		RefPermissionsQuestionTransform.updateDto(refPermissionsQuestionDTO, refPermissionsQuestionDTOFromDB);
		
		RefPermissionsQuestion entity = RefPermissionsQuestionTransform.dto2Bo(refPermissionsQuestionDTOFromDB);
		
		refPermissionsQuestionRepository.saveAndFlush(entity);
	}
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void delete(RefPermissionsQuestionDTO refPermissionsQuestionDTO) throws InvalidParameterException {	
    	refPermissionsQuestionRepository.deleteById(refPermissionsQuestionDTO.getId());
	}

    @Transactional(readOnly=true)
    public PaginationResult<RefPermissionsQuestionDTO> providePermissionsQuestionWithPagination(int index, int maxResults) throws JrafDomainException {
    	PaginationResult<RefPermissionsQuestionDTO> paginationResult = new PaginationResult<RefPermissionsQuestionDTO>();
    	
    	Long totalResultsFound = refPermissionsQuestionRepository.count();

    	PaginationUtils.getIndexAndMaxResultsForPagination(paginationResult, index, maxResults, TableReferenceEnum.REF_PERMISSIONS_QUESTION, totalResultsFound);
    	
    	List<RefPermissionsQuestion> listRefPermissionsQuestion = refPermissionsQuestionRepository.findAllByOrderById(PageRequest.of(paginationResult.getIndex(), paginationResult.getMaxResults()));
    	
    	for (RefPermissionsQuestion refPermissionsQuestion : listRefPermissionsQuestion) {
    		paginationResult.getListResults().add(RefPermissionsQuestionTransform.bo2Dto(refPermissionsQuestion));
    	}
    	
    	return paginationResult;
    }
}
