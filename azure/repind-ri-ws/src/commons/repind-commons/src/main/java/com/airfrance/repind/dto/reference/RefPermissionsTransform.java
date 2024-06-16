package com.airfrance.repind.dto.reference;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefPermissions;

public final class RefPermissionsTransform {

    /**
     * private constructor
     */
    private RefPermissionsTransform() {
    }
    
    public static RefPermissions dto2Bo(RefPermissionsDTO refPermissionsDTO) throws JrafDomainException {
    	
    	if (refPermissionsDTO == null) {
    		return null;
    	}
    	
    	RefPermissions refPermissions = new RefPermissions();
    	
    	refPermissions.setRefPermissionsId(RefPermissionsIdTransform.dto2Bo(refPermissionsDTO.getRefPermissionsId()));
    	refPermissions.setDateCreation(refPermissionsDTO.getDateCreation());
    	refPermissions.setDateModification(refPermissionsDTO.getDateModification());
    	refPermissions.setSignatureCreation(refPermissionsDTO.getSignatureCreation());
    	refPermissions.setSignatureModification(refPermissionsDTO.getSignatureModification());
    	refPermissions.setSiteCreation(refPermissionsDTO.getSiteCreation());
    	refPermissions.setSiteModification(refPermissionsDTO.getSiteModification());
    	
    	return refPermissions;
    }
    
    public static RefPermissionsDTO bo2Dto(RefPermissions refPermissions) throws JrafDomainException {
    	
    	RefPermissionsDTO refPermissionsDTO = new RefPermissionsDTO();
    	bo2Dto(refPermissions, refPermissionsDTO);
    	
    	return refPermissionsDTO;
    }
    
    public static void bo2Dto(RefPermissions refPermissions, RefPermissionsDTO refPermissionsDTO) throws JrafDomainException {
    	
    	if (refPermissionsDTO == null) {
    		refPermissionsDTO = new RefPermissionsDTO();
    	}
    	
    	refPermissionsDTO.setRefPermissionsId(RefPermissionsIdTransform.bo2Dto(refPermissions.getRefPermissionsId()));
    	refPermissionsDTO.setDateCreation(refPermissions.getDateCreation());
    	refPermissionsDTO.setDateModification(refPermissions.getDateModification());
    	refPermissionsDTO.setSignatureCreation(refPermissions.getSignatureCreation());
    	refPermissionsDTO.setSignatureModification(refPermissions.getSignatureModification());
    	refPermissionsDTO.setSiteCreation(refPermissions.getSiteCreation());
    	refPermissionsDTO.setSiteModification(refPermissions.getSiteModification());
    }
}

