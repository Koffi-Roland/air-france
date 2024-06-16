package com.afklm.rigui.dto.reference;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.reference.RefComPrefGroupId;

public final class RefComPrefGroupIdTransform {

    /**
     * private constructor
     */
    private RefComPrefGroupIdTransform() {
    }
    
    public static RefComPrefGroupId dto2Bo(RefComPrefGroupIdDTO refComPrefGroupIdDTO) throws JrafDomainException {
    	
    	RefComPrefGroupId refComPrefGroupId = new RefComPrefGroupId();
    	
    	refComPrefGroupId.setRefComPrefDgt(RefComPrefDgtTransform.dto2Bo(refComPrefGroupIdDTO.getRefComPrefDgt()));
    	refComPrefGroupId.setRefComPrefGroupInfo(RefComPrefGroupInfoTransform.dto2Bo(refComPrefGroupIdDTO.getRefComPrefGroupInfo()));
    	
    	return refComPrefGroupId;
    }
    
    public static RefComPrefGroupIdDTO bo2Dto(RefComPrefGroupId refComPrefGroupId) throws JrafDomainException {
    
    	RefComPrefGroupIdDTO refComPrefGroupIdDTO = new RefComPrefGroupIdDTO();
    	bo2Dto(refComPrefGroupId, refComPrefGroupIdDTO);
    	
    	return refComPrefGroupIdDTO;
    }
    
    public static void bo2Dto(RefComPrefGroupId refComPrefGroupId, RefComPrefGroupIdDTO refComPrefGroupIdDTO) throws JrafDomainException {
    	
    	if (refComPrefGroupIdDTO == null) {
    		refComPrefGroupIdDTO = new RefComPrefGroupIdDTO();
    	}
    	
    	refComPrefGroupIdDTO.setRefComPrefDgt(RefComPrefDgtTransform.bo2Dto(refComPrefGroupId.getRefComPrefDgt()));
    	refComPrefGroupIdDTO.setRefComPrefGroupInfo(RefComPrefGroupInfoTransform.bo2Dto(refComPrefGroupId.getRefComPrefGroupInfo()));
    }
}

