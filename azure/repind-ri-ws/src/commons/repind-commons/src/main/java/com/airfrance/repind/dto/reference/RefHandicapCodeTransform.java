package com.airfrance.repind.dto.reference;

import com.airfrance.repind.entity.reference.RefHandicapCode;

/**
 * <p>Title : RefHandicapCodeTransform.java</p>
 * transformation bo <-> dto pour un(e) RefLanguage
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefHandicapCodeTransform {
    
    /**
     * private constructor
     */
    private RefHandicapCodeTransform() {
    	
    }

    /**
     * dto -> bo implementation for a refLanguage
     * @param refHandicapCodeDTO dto
     * @param refHandicapCode bo
     */
    public static RefHandicapCode dto2Bo(RefHandicapCodeDTO refHandicapCodeDTO) {
    	
    	if (refHandicapCodeDTO == null) {
    		return null;
    	}
    	
    	RefHandicapCode refHandicapCode = new RefHandicapCode();
    
    	refHandicapCode.setCode(refHandicapCodeDTO.getCode());
    	refHandicapCode.setLabelFR(refHandicapCodeDTO.getLabelFR());
    	refHandicapCode.setLabelEN(refHandicapCodeDTO.getLabelEN());
    	
    	return refHandicapCode;
    }

    /**
     * Transform a business object to DTO. Implementation method
     * @param refHandicapCode bo
     * @param refHandicapCodeDTO dto
     */
    public static RefHandicapCodeDTO bo2Dto(RefHandicapCode refHandicapCode) {
    	
    	if (refHandicapCode == null) {
    		return null;
    	}
    	
    	RefHandicapCodeDTO refHandicapCodeDTO = new RefHandicapCodeDTO();
   
    	refHandicapCodeDTO.setCode(refHandicapCode.getCode());
    	refHandicapCodeDTO.setLabelFR(refHandicapCode.getLabelFR());
    	refHandicapCodeDTO.setLabelEN(refHandicapCode.getLabelEN());
    	
    	return refHandicapCodeDTO;
    }
}

