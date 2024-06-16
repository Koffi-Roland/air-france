package com.airfrance.repind.dto.tracking;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.IndividuTransform;
import com.airfrance.repind.dto.reference.RefPermissionsQuestionTransform;
import com.airfrance.repind.entity.tracking.TrackingPermissions;

public final class TrackingPermissionsTransform {

    /**
     * private constructor
     */
    private TrackingPermissionsTransform() {
    	
    }
    
    public static TrackingPermissions dto2Bo(TrackingPermissionsDTO trackingPermissionsDTO) throws JrafDomainException {
    	
    	TrackingPermissions trackingPermissions = new TrackingPermissions();
    	
    	trackingPermissions.setConsent(trackingPermissionsDTO.getConsent());
    	trackingPermissions.setDateConsent(trackingPermissionsDTO.getDateConsent());
    	trackingPermissions.setDateCreation(trackingPermissionsDTO.getDateCreation());
    	trackingPermissions.setDateModification(trackingPermissionsDTO.getDateModification());
    	trackingPermissions.setId(trackingPermissionsDTO.getId());    	
    	trackingPermissions.setSignatureCreation(trackingPermissionsDTO.getSignatureCreation());
    	trackingPermissions.setSignatureModification(trackingPermissionsDTO.getSignatureModification());
    	trackingPermissions.setSiteCreation(trackingPermissionsDTO.getSiteCreation());
    	trackingPermissions.setSiteModification(trackingPermissionsDTO.getSiteModification());
    	
    	if (trackingPermissionsDTO.getSgin() != null) {
    		trackingPermissions.setSgin(IndividuTransform.dto2Bo(trackingPermissionsDTO.getSgin()));
    	}
    	if (trackingPermissionsDTO.getPermissionQuestionId() != null) {
    		trackingPermissions.setPermissionQuestionId(RefPermissionsQuestionTransform.dto2Bo(trackingPermissionsDTO.getPermissionQuestionId()));
    	}
    	
    	return trackingPermissions;
    }
    
    public static TrackingPermissionsDTO bo2Dto(TrackingPermissions trackingPermissions) throws JrafDomainException {
    	
    	TrackingPermissionsDTO trackingPermissionsDTO = new TrackingPermissionsDTO();
    	bo2Dto(trackingPermissions, trackingPermissionsDTO);
    	
    	return trackingPermissionsDTO;
    }
    
    public static void bo2Dto(TrackingPermissions trackingPermissions, TrackingPermissionsDTO trackingPermissionsDTO) throws JrafDomainException {
    	
    	if (trackingPermissionsDTO == null) {
    		trackingPermissionsDTO = new TrackingPermissionsDTO();
    	}
   	
    	trackingPermissionsDTO.setConsent(trackingPermissions.getConsent());
    	trackingPermissionsDTO.setDateConsent(trackingPermissions.getDateConsent());
    	trackingPermissionsDTO.setDateCreation(trackingPermissions.getDateCreation());
    	trackingPermissionsDTO.setDateModification(trackingPermissions.getDateModification());
    	trackingPermissionsDTO.setId(trackingPermissions.getId());
    	trackingPermissionsDTO.setSignatureCreation(trackingPermissions.getSignatureCreation());
    	trackingPermissionsDTO.setSignatureModification(trackingPermissions.getSignatureModification());
    	trackingPermissionsDTO.setSiteCreation(trackingPermissions.getSiteCreation());
    	trackingPermissionsDTO.setSiteModification(trackingPermissions.getSiteModification());
    	
    	if (trackingPermissions.getSgin() != null) {
    		trackingPermissionsDTO.setSgin(IndividuTransform.bo2Dto(trackingPermissions.getSgin()));
    	}
    	if (trackingPermissions.getPermissionQuestionId() != null) {
    		trackingPermissionsDTO.setPermissionQuestionId(RefPermissionsQuestionTransform.bo2Dto(trackingPermissions.getPermissionQuestionId()));
    	}
    }
    
    public static void updateDto(TrackingPermissionsDTO trackingPermissionsDTO, TrackingPermissionsDTO trackingPermissionsDTOFromDB) {
    	
    	trackingPermissionsDTOFromDB.setPermissionQuestionId(trackingPermissionsDTO.getPermissionQuestionId());
    	trackingPermissionsDTOFromDB.setConsent(trackingPermissionsDTO.getConsent());
    	trackingPermissionsDTOFromDB.setDateConsent(trackingPermissionsDTO.getDateConsent());
    	trackingPermissionsDTOFromDB.setDateCreation(trackingPermissionsDTO.getDateCreation());
    	trackingPermissionsDTOFromDB.setDateModification(trackingPermissionsDTO.getDateModification());
    	trackingPermissionsDTOFromDB.setId(trackingPermissionsDTO.getId());
    	trackingPermissionsDTOFromDB.setSgin(trackingPermissionsDTO.getSgin());
    	trackingPermissionsDTOFromDB.setSignatureCreation(trackingPermissionsDTO.getSignatureCreation());
    	trackingPermissionsDTOFromDB.setSignatureModification(trackingPermissionsDTO.getSignatureModification());
    	trackingPermissionsDTOFromDB.setSiteCreation(trackingPermissionsDTO.getSiteCreation());
    	trackingPermissionsDTOFromDB.setSiteModification(trackingPermissionsDTO.getSiteModification());
    }
}

