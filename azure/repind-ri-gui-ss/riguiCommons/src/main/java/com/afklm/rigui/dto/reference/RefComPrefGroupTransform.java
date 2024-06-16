package com.afklm.rigui.dto.reference;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.reference.RefComPrefGroup;

public final class RefComPrefGroupTransform {

    /**
     * private constructor
     */
    private RefComPrefGroupTransform() {
    }
    
    public static RefComPrefGroup dto2Bo(RefComPrefGroupDTO refComPrefGroupDTO) throws JrafDomainException {
    	
    	RefComPrefGroup refComPrefGroup = new RefComPrefGroup();
    	
    	refComPrefGroup.setDateCreation(refComPrefGroupDTO.getDateCreation());
    	refComPrefGroup.setDateModification(refComPrefGroupDTO.getDateModification());
    	refComPrefGroup.setRefComPrefGroupId(RefComPrefGroupIdTransform.dto2Bo(refComPrefGroupDTO.getRefComPrefGroupId()));
    	refComPrefGroup.setSignatureCreation(refComPrefGroupDTO.getSignatureCreation());
    	refComPrefGroup.setSignatureModification(refComPrefGroupDTO.getSignatureModification());
    	refComPrefGroup.setSiteCreation(refComPrefGroupDTO.getSiteCreation());
    	refComPrefGroup.setSiteModification(refComPrefGroupDTO.getSiteModification());
    	
    	return refComPrefGroup;
    }
    
    public static RefComPrefGroupDTO bo2Dto(RefComPrefGroup refComPrefGroup) throws JrafDomainException {
    	
    	RefComPrefGroupDTO refComPrefGroupDTO = new RefComPrefGroupDTO();
    	bo2Dto(refComPrefGroup, refComPrefGroupDTO);
    	
    	return refComPrefGroupDTO;
    }
    
    public static void bo2Dto(RefComPrefGroup refComPrefGroup, RefComPrefGroupDTO refComPrefGroupDTO) throws JrafDomainException {
    	
    	if (refComPrefGroupDTO == null) {
    		refComPrefGroupDTO = new RefComPrefGroupDTO();
    	}
    	
    	refComPrefGroupDTO.setDateCreation(refComPrefGroup.getDateCreation());
    	refComPrefGroupDTO.setDateModification(refComPrefGroup.getDateModification());
    	refComPrefGroupDTO.setRefComPrefGroupId(RefComPrefGroupIdTransform.bo2Dto(refComPrefGroup.getRefComPrefGroupId()));
    	refComPrefGroupDTO.setSignatureCreation(refComPrefGroup.getSignatureCreation());
    	refComPrefGroupDTO.setSignatureModification(refComPrefGroup.getSignatureModification());
    	refComPrefGroupDTO.setSiteCreation(refComPrefGroup.getSiteCreation());
    	refComPrefGroupDTO.setSiteModification(refComPrefGroup.getSiteModification());
    }
}

