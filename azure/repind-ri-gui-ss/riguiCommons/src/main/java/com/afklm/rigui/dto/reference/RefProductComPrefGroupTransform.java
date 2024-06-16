package com.afklm.rigui.dto.reference;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.reference.RefProductComPrefGroup;

public final class RefProductComPrefGroupTransform {

    /**
     * private constructor
     */
    private RefProductComPrefGroupTransform() {
    }
    
    public static RefProductComPrefGroup dto2Bo(RefProductComPrefGroupDTO refProductComPrefGroupDTO) throws JrafDomainException {
    	
    	RefProductComPrefGroup refProductComPrefGroup = new RefProductComPrefGroup();
    	
    	refProductComPrefGroup.setDateCreation(refProductComPrefGroupDTO.getDateCreation());
    	refProductComPrefGroup.setDateModification(refProductComPrefGroupDTO.getDateModification());
    	refProductComPrefGroup.setRefProductComPrefGroupId(RefProductComPrefGroupIdTransform.dto2Bo(refProductComPrefGroupDTO.getRefProductComPrefGroupId()));
    	refProductComPrefGroup.setSignatureCreation(refProductComPrefGroupDTO.getSignatureCreation());
    	refProductComPrefGroup.setSignatureModification(refProductComPrefGroupDTO.getSignatureModification());
    	refProductComPrefGroup.setSiteCreation(refProductComPrefGroupDTO.getSiteCreation());
    	refProductComPrefGroup.setSiteModification(refProductComPrefGroupDTO.getSiteModification());
    	
    	return refProductComPrefGroup;
    }
    
    public static RefProductComPrefGroupDTO bo2Dto(RefProductComPrefGroup refProductComPrefGroup) throws JrafDomainException {
    	
    	RefProductComPrefGroupDTO refProductComPrefGroupDTO = new RefProductComPrefGroupDTO();
    	bo2Dto(refProductComPrefGroup, refProductComPrefGroupDTO);
    	
    	return refProductComPrefGroupDTO;
    }
    
    public static void bo2Dto(RefProductComPrefGroup refProductComPrefGroup, RefProductComPrefGroupDTO refProductComPrefGroupDTO) throws JrafDomainException {
    	
    	if (refProductComPrefGroupDTO == null) {
    		refProductComPrefGroupDTO = new RefProductComPrefGroupDTO();
    	}
    	
    	refProductComPrefGroupDTO.setDateCreation(refProductComPrefGroup.getDateCreation());
    	refProductComPrefGroupDTO.setDateModification(refProductComPrefGroup.getDateModification());
    	refProductComPrefGroupDTO.setRefProductComPrefGroupId(RefProductComPrefGroupIdTransform.bo2Dto(refProductComPrefGroup.getRefProductComPrefGroupId()));
    	refProductComPrefGroupDTO.setSignatureCreation(refProductComPrefGroup.getSignatureCreation());
    	refProductComPrefGroupDTO.setSignatureModification(refProductComPrefGroup.getSignatureModification());
    	refProductComPrefGroupDTO.setSiteCreation(refProductComPrefGroup.getSiteCreation());
    	refProductComPrefGroupDTO.setSiteModification(refProductComPrefGroup.getSiteModification());
    }
}

