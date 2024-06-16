package com.afklm.rigui.services.reference.internal;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.exception.jraf.JrafDomainNoRollbackException;
import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;
import com.afklm.rigui.enums.TableReferenceEnum;
import com.afklm.rigui.dao.reference.RefPermissionsRepository;
import com.afklm.rigui.dto.reference.*;
import com.afklm.rigui.entity.reference.RefPermissions;
import com.afklm.rigui.entity.reference.RefPermissionsId;
import com.afklm.rigui.util.service.PaginationResult;
import com.afklm.rigui.util.service.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class RefPermissionsDS {
		
    /** logger */
    //private static final Log log = LogFactory.getLog(RefPermissionsQuestionDS.class);
    
    @Autowired
    private RefPermissionsRepository refPermissionsRepository;

    @Transactional(readOnly=true)
	public RefPermissionsDTO getById(RefPermissionsIdDTO refPermissionsIdDTO) throws JrafDomainException {

		RefPermissionsId id = RefPermissionsIdTransform.dto2Bo(refPermissionsIdDTO);
		
		Optional<RefPermissions> entity = refPermissionsRepository.findById(id);
		
		if (!entity.isPresent()) {
			return null;
		}
		
		return RefPermissionsTransform.bo2Dto(entity.get());
	}
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void create(RefPermissionsDTO refPermissionsDTO) throws JrafDomainException {
		
		RefPermissions entity = RefPermissionsTransform.dto2Bo(refPermissionsDTO);
		
		refPermissionsRepository.saveAndFlush(entity);
		
		RefPermissionsTransform.bo2Dto(entity, refPermissionsDTO);
	}
	
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void delete(RefPermissionsDTO refPermissionsDTO) throws JrafDomainException {
		
		RefPermissions entity = RefPermissionsTransform.dto2Bo(refPermissionsDTO);
		
		refPermissionsRepository.deleteById(entity.getRefPermissionsId());
	}
	

    @Transactional(readOnly=true)
	public List<RefComPrefDgtDTO> getAllComPrefDGTByPermissionsQuestionId(int permissionQuestionId) throws JrafDomainException {
		
		List<RefPermissions> listRefPermissions = refPermissionsRepository.getAllComPrefDGTByPermissionsQuestionId(permissionQuestionId);
		
		if (listRefPermissions == null) {
			return null;
		}
		
		List<RefComPrefDgtDTO> listRefComPrefDgt = new ArrayList<RefComPrefDgtDTO>();
		
		for (RefPermissions refPermissions : listRefPermissions) {
			RefPermissionsDTO dto = new RefPermissionsDTO();
			RefPermissionsTransform.bo2Dto(refPermissions, dto);
			listRefComPrefDgt.add(dto.getRefPermissionsId().getRefComPrefDgt());
		}
		
		return listRefComPrefDgt;
	}

    @Transactional(readOnly=true)
    public PaginationResult<RefPermissionsDTO> providePermissionsWithPagination(int index, int maxResults) throws JrafDomainException {
    	PaginationResult<RefPermissionsDTO> paginationResult = new PaginationResult<RefPermissionsDTO>();
    	
    	Long totalResultsFound = refPermissionsRepository.countPermissions();

    	PaginationUtils.getIndexAndMaxResultsForPagination(paginationResult, index, maxResults, TableReferenceEnum.REF_PERMISSIONS, totalResultsFound);

    	List<Integer> resultPermissionsId = refPermissionsRepository.providePermissionsQuestionIdWithPagination(PageRequest.of(paginationResult.getIndex(), paginationResult.getMaxResults()));

		if(resultPermissionsId != null && resultPermissionsId.size() > 0) {
			
	    	List<RefPermissions> listRefPermissions = refPermissionsRepository.providePermissionsWithPagination(resultPermissionsId);
	    	
	    	for (RefPermissions refPermissions : listRefPermissions) {
	    		paginationResult.getListResults().add(RefPermissionsTransform.bo2Dto(refPermissions));
	    	}
	    	
		}
    	
    	
    	return paginationResult;
    }
}
