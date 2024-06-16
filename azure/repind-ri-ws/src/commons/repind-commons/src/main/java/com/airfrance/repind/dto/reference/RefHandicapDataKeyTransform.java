package com.airfrance.repind.dto.reference;

import com.airfrance.repind.entity.reference.RefHandicapDataKey;

/**
 * <p>Title : RefHandicapDataKeyTransform.java</p>
 * transformation bo <-> dto pour un(e) RefLanguage
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefHandicapDataKeyTransform {
    
    /**
     * private constructor
     */
    private RefHandicapDataKeyTransform() {
    	
    }

    /**
     * dto -> bo implementation for a refLanguage
     * @param refHandicapDataKeyDTO dto
     * @param refHandicapDataKey bo
     */
    public static RefHandicapDataKey dto2Bo(RefHandicapDataKeyDTO refHandicapDataKeyDTO) {
    	
    	if (refHandicapDataKeyDTO == null) {
    		return null;
    	}
    	
    	RefHandicapDataKey refHandicapDataKey = new RefHandicapDataKey();
    
    	refHandicapDataKey.setCode(refHandicapDataKeyDTO.getCode());
    	refHandicapDataKey.setLabelFR(refHandicapDataKeyDTO.getLabelFR());
    	refHandicapDataKey.setLabelEN(refHandicapDataKeyDTO.getLabelEN());
    	
    	return refHandicapDataKey;
    }

    /**
     * Transform a business object to DTO. Implementation method
     * @param refHandicapDataKey bo
     * @param refHandicapDataKeyDTO dto
     */
    public static RefHandicapDataKeyDTO bo2Dto(RefHandicapDataKey refHandicapDataKey) {
    	
    	if (refHandicapDataKey == null) {
    		return null;
    	}
    	
    	RefHandicapDataKeyDTO refHandicapDataKeyDTO = new RefHandicapDataKeyDTO();
   
    	refHandicapDataKeyDTO.setCode(refHandicapDataKey.getCode());
    	refHandicapDataKeyDTO.setLabelFR(refHandicapDataKey.getLabelFR());
    	refHandicapDataKeyDTO.setLabelEN(refHandicapDataKey.getLabelEN());
    	
    	return refHandicapDataKeyDTO;
    }
}

