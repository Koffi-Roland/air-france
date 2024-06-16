package com.airfrance.repind.dto.reference;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefProductComPrefGroupId;

public final class RefProductComPrefGroupIdTransform {

    /**
     * private constructor
     */
    private RefProductComPrefGroupIdTransform() {
    }
    
    public static RefProductComPrefGroupId dto2Bo(RefProductComPrefGroupIdDTO refProductComPrefGroupIdDTO) throws JrafDomainException {
    	
    	RefProductComPrefGroupId refProductComPrefGroupId = new RefProductComPrefGroupId();
    	
    	refProductComPrefGroupId.setRefProduct(RefProductTransform.dto2BoLight(refProductComPrefGroupIdDTO.getRefProduct()));
    	refProductComPrefGroupId.setRefComPrefGroupInfo(RefComPrefGroupInfoTransform.dto2Bo(refProductComPrefGroupIdDTO.getRefComPrefGroupInfo()));
    	
    	return refProductComPrefGroupId;
    }
    
    public static RefProductComPrefGroupIdDTO bo2Dto(RefProductComPrefGroupId refProductComPrefGroupId) throws JrafDomainException {
    	
    	RefProductComPrefGroupIdDTO refProductComPrefGroupIdDTO = new RefProductComPrefGroupIdDTO();
    	bo2Dto(refProductComPrefGroupId, refProductComPrefGroupIdDTO);
    	
    	return refProductComPrefGroupIdDTO;
    }
    
    public static void bo2Dto(RefProductComPrefGroupId refProductComPrefGroupId, RefProductComPrefGroupIdDTO refProductComPrefGroupIdDTO) throws JrafDomainException {
    	
    	if (refProductComPrefGroupIdDTO == null) {
    		refProductComPrefGroupIdDTO = new RefProductComPrefGroupIdDTO();
    	}
    	
    	refProductComPrefGroupIdDTO.setRefProduct(RefProductTransform.bo2DtoLight(refProductComPrefGroupId.getRefProduct()));
    	refProductComPrefGroupIdDTO.setRefComPrefGroupInfo(RefComPrefGroupInfoTransform.bo2Dto(refProductComPrefGroupId.getRefComPrefGroupInfo()));
    }
}

