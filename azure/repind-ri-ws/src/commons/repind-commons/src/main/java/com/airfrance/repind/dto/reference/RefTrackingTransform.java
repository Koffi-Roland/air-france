package com.airfrance.repind.dto.reference;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefTracking;

public final class RefTrackingTransform {

    /**
     * private constructor
     */
    private RefTrackingTransform() {
    }
    
    public static RefTracking dto2Bo(RefTrackingDTO refTrackingDTO) throws JrafDomainException {
    	
    	if (refTrackingDTO == null) {
    		return null;
    	}
    	
    	RefTracking refTracking = new RefTracking();
    	
    	refTracking.setRefTrackingId(refTrackingDTO.getRefTrackingId());
    	refTracking.setCode(refTrackingDTO.getCode());
    	refTracking.setValue(refTrackingDTO.getValue());
    	refTracking.setValueNormalized(refTrackingDTO.getValueNormalized());
    	refTracking.setDescription(refTrackingDTO.getDescription());
    	refTracking.setIcon(refTrackingDTO.getIcon());
    	
    	return refTracking;
    }
    
    public static RefTrackingDTO bo2Dto(RefTracking refTracking) throws JrafDomainException {
    	
    	if (refTracking == null) {
    		return null;
    	}
    	
    	RefTrackingDTO refTrackingDTO = new RefTrackingDTO();
    	bo2Dto(refTracking, refTrackingDTO);
    	
    	return refTrackingDTO;
    }
    
    public static void bo2Dto(RefTracking refTracking, RefTrackingDTO refTrackingDTO) throws JrafDomainException {
    	
    	if (refTrackingDTO == null) {
    		refTrackingDTO = new RefTrackingDTO();
    	}
    	
    	refTrackingDTO.setRefTrackingId(refTracking.getRefTrackingId());
    	refTrackingDTO.setCode(refTracking.getCode());
    	refTrackingDTO.setValue(refTracking.getValue());
    	refTrackingDTO.setValueNormalized(refTracking.getValueNormalized());
    	refTrackingDTO.setDescription(refTracking.getDescription());
    	refTrackingDTO.setIcon(refTracking.getIcon());
    }
}

