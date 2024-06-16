package com.airfrance.repind.service.reference.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.TableReferenceEnum;
import com.airfrance.repind.dao.reference.RefPreferenceKeyTypeRepository;
import com.airfrance.repind.dto.reference.RefPreferenceKeyTypeDTO;
import com.airfrance.repind.dto.reference.RefPreferenceKeyTypeTransform;
import com.airfrance.repind.entity.reference.RefPreferenceKeyType;
import com.airfrance.repind.util.service.PaginationResult;
import com.airfrance.repind.util.service.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RefPreferenceKeyTypeDS {
	
    @Autowired
    private RefPreferenceKeyTypeRepository refPreferenceKeyTypeRepository;

    @Transactional(readOnly=true)
    public RefPreferenceKeyTypeDTO get(RefPreferenceKeyTypeDTO dto) throws JrafDomainException {
       return get(dto.getRefId());
    }
    @Transactional(readOnly=true)
    public RefPreferenceKeyTypeDTO get(Integer id) throws JrafDomainException {
    	 Optional<RefPreferenceKeyType> refPreferenceKeyType = refPreferenceKeyTypeRepository.findById(id);
         if (!refPreferenceKeyType.isPresent()) {
         	return null;
         }
         return RefPreferenceKeyTypeTransform.bo2DtoLight(refPreferenceKeyType.get());
    }
    
	public RefPreferenceKeyTypeRepository getRefPreferenceKeyTypeRepository() {
		return refPreferenceKeyTypeRepository;
	}
	public void setRefPreferenceKeyTypeRepository(RefPreferenceKeyTypeRepository refPreferenceKeyTypeRepository) {
		this.refPreferenceKeyTypeRepository = refPreferenceKeyTypeRepository;
	}
	
	
    @Transactional(readOnly=true)
    public PaginationResult<RefPreferenceKeyType> provideRefpreferenceKeyTypeWithPagination(int index, int maxResults) throws JrafDomainException {
    	PaginationResult<RefPreferenceKeyType> paginationResult = new PaginationResult<RefPreferenceKeyType>();
    	
    	Long totalResultsFound = refPreferenceKeyTypeRepository.count();

    	PaginationUtils.getIndexAndMaxResultsForPagination(paginationResult, index, maxResults, TableReferenceEnum.REF_PREFERENCE_KEY_TYPE, totalResultsFound);
    	
    	List<RefPreferenceKeyType> listRefPreferenceKeyType = refPreferenceKeyTypeRepository.provideRefPreferenceKeyTypeWithPagination(PageRequest.of(paginationResult.getIndex(), paginationResult.getMaxResults()));
    	
    	for (RefPreferenceKeyType refPreferenceKeyType : listRefPreferenceKeyType) {
    		paginationResult.getListResults().add(refPreferenceKeyType);
    	}
    	
    	return paginationResult;
    }    
    
}
