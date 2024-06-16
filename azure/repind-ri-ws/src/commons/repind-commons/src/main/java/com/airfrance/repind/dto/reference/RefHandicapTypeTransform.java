package com.airfrance.repind.dto.reference;

import com.airfrance.repind.entity.reference.RefHandicapType;

/**
 * <p>Title : RefHandicapTypeTransform.java</p>
 * transformation bo <-> dto pour un(e) RefLanguage
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefHandicapTypeTransform {
    
    /**
     * private constructor
     */
    private RefHandicapTypeTransform() {
    	
    }

    /**
     * dto -> bo implementation for a refLanguage
     * @param refHandicapTypeDTO dto
     * @param refHandicapType bo
     */
    public static RefHandicapType dto2Bo(RefHandicapTypeDTO refHandicapTypeDTO) {
    	
    	if (refHandicapTypeDTO == null) {
    		return null;
    	}
    
    	RefHandicapType refHandicapType = new RefHandicapType();
    	
    	refHandicapType.setCode(refHandicapTypeDTO.getCode());
    	refHandicapType.setLabelFR(refHandicapTypeDTO.getLabelFR());
    	refHandicapType.setLabelEN(refHandicapTypeDTO.getLabelEN());
    	
    	return refHandicapType;
    }

    /**
     * Transform a business object to DTO. Implementation method
     * @param refHandicapType bo
     * @param refHandicapTypeDTO dto
     */
    public static RefHandicapTypeDTO bo2Dto(RefHandicapType refHandicapType) {
    	
    	if (refHandicapType == null) {
    		return null;
    	}
    	
    	RefHandicapTypeDTO refHandicapTypeDTO = new RefHandicapTypeDTO();
   
    	refHandicapTypeDTO.setCode(refHandicapType.getCode());
    	refHandicapTypeDTO.setLabelFR(refHandicapType.getLabelFR());
    	refHandicapTypeDTO.setLabelEN(refHandicapType.getLabelEN());
    	
    	return refHandicapTypeDTO;
    }
}

