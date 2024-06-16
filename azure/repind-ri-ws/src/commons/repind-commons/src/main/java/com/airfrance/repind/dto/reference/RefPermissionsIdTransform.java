package com.airfrance.repind.dto.reference;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefPermissionsId;

public final class RefPermissionsIdTransform {

    /**
     * private constructor
     */
    private RefPermissionsIdTransform() {
    }
    
    public static RefPermissionsId dto2Bo(RefPermissionsIdDTO refPermissionsIdDTO) throws JrafDomainException {
    	
    	RefPermissionsId refPermissionsId = new RefPermissionsId();
    	
    	refPermissionsId.setQuestionId(RefPermissionsQuestionTransform.dto2Bo(refPermissionsIdDTO.getQuestionId()));
    	refPermissionsId.setRefComPrefDgt(RefComPrefDgtTransform.dto2Bo(refPermissionsIdDTO.getRefComPrefDgt()));
    	
    	return refPermissionsId;
    }
    
    public static RefPermissionsIdDTO bo2Dto(RefPermissionsId refPermissionsId) throws JrafDomainException {
    	
    	RefPermissionsIdDTO refPermissionsIdDTO = new RefPermissionsIdDTO();
    	bo2Dto(refPermissionsId, refPermissionsIdDTO);
    	
    	return refPermissionsIdDTO;
    }
    
    public static void bo2Dto(RefPermissionsId refPermissionsId, RefPermissionsIdDTO refPermissionsIdDTO) throws JrafDomainException {
    	
    	if (refPermissionsIdDTO == null) {
    		refPermissionsIdDTO = new RefPermissionsIdDTO();
    	}
    	
    	refPermissionsIdDTO.setQuestionId(RefPermissionsQuestionTransform.bo2Dto(refPermissionsId.getQuestionId()));
    	refPermissionsIdDTO.setRefComPrefDgt(RefComPrefDgtTransform.bo2Dto(refPermissionsId.getRefComPrefDgt()));   	
    }
    
    public static void updateDto(RefPermissionsIdDTO refPermissionsIdDTO, RefPermissionsIdDTO refPermissionsIdDTOFromFB) {
            	    	
    	refPermissionsIdDTOFromFB.setQuestionId(refPermissionsIdDTO.getQuestionId());
    	refPermissionsIdDTOFromFB.setRefComPrefDgt(refPermissionsIdDTO.getRefComPrefDgt());
    }
}

