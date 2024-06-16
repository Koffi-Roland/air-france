package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_dKrScPRNEeaosIe0wwvjyw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefGenericCodeLabelsType;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefGenericCodeLabelsTypeTransform.java</p>
 * transformation bo <-> dto pour un(e) RefGenericCodeLabelsType
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefGenericCodeLabelsTypeTransform {

    /*PROTECTED REGION ID(_dKrScPRNEeaosIe0wwvjyw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefGenericCodeLabelsTypeTransform() {
    }
    /**
     * dto -> bo for a RefGenericCodeLabelsType
     * @param refGenericCodeLabelsTypeDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefGenericCodeLabelsType dto2BoLight(RefGenericCodeLabelsTypeDTO refGenericCodeLabelsTypeDTO) throws JrafDomainException {
        // instanciation du BO
        RefGenericCodeLabelsType refGenericCodeLabelsType = new RefGenericCodeLabelsType();
        dto2BoLight(refGenericCodeLabelsTypeDTO, refGenericCodeLabelsType);

        // on retourne le BO
        return refGenericCodeLabelsType;
    }

    /**
     * dto -> bo for a refGenericCodeLabelsType
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refGenericCodeLabelsTypeDTO dto
     * @param refGenericCodeLabelsType bo
     */
    public static void dto2BoLight(RefGenericCodeLabelsTypeDTO refGenericCodeLabelsTypeDTO, RefGenericCodeLabelsType refGenericCodeLabelsType) {
    
        /*PROTECTED REGION ID(dto2BoLight_dKrScPRNEeaosIe0wwvjyw) ENABLED START*/
        
        dto2BoLightImpl(refGenericCodeLabelsTypeDTO,refGenericCodeLabelsType);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refGenericCodeLabelsType
     * @param refGenericCodeLabelsTypeDTO dto
     * @param refGenericCodeLabelsType bo
     */
    private static void dto2BoLightImpl(RefGenericCodeLabelsTypeDTO refGenericCodeLabelsTypeDTO, RefGenericCodeLabelsType refGenericCodeLabelsType){
    
        // property of RefGenericCodeLabelsTypeDTO
        refGenericCodeLabelsType.setCode(refGenericCodeLabelsTypeDTO.getCode());
        refGenericCodeLabelsType.setLabelFr(refGenericCodeLabelsTypeDTO.getLabelFr());
        refGenericCodeLabelsType.setLabelEn(refGenericCodeLabelsTypeDTO.getLabelEn());
    
    }

    /**
     * bo -> dto for a refGenericCodeLabelsType
     * @param pRefGenericCodeLabelsType bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefGenericCodeLabelsTypeDTO bo2DtoLight(Object pRefGenericCodeLabelsType) throws JrafDomainException {
        // instanciation du DTO
        RefGenericCodeLabelsTypeDTO refGenericCodeLabelsTypeDTO = new RefGenericCodeLabelsTypeDTO();
        bo2DtoLight(pRefGenericCodeLabelsType, refGenericCodeLabelsTypeDTO);
        // on retourne le dto
        return refGenericCodeLabelsTypeDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refGenericCodeLabelsType bo
     * @param refGenericCodeLabelsTypeDTO dto
     */
    public static void bo2DtoLight(
        Object refGenericCodeLabelsType,
        RefGenericCodeLabelsTypeDTO refGenericCodeLabelsTypeDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_dKrScPRNEeaosIe0wwvjyw) ENABLED START*/

        bo2DtoLightImpl(refGenericCodeLabelsType, refGenericCodeLabelsTypeDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refGenericCodeLabelsType bo
     * @param refGenericCodeLabelsTypeDTO dto
     */
    private static void bo2DtoLightImpl(Object refGenericCodeLabelsType,
        RefGenericCodeLabelsTypeDTO refGenericCodeLabelsTypeDTO){

    	// Check if the class is an Array
    	if (refGenericCodeLabelsType.getClass().isArray()) {
    		
    		// Convert Generic container
    		Object[] refGenericCodeLabelsTypeArr =  (Object[]) refGenericCodeLabelsType ;
    		
    		// Check if we can find a CODE
    		if (refGenericCodeLabelsTypeArr.length > 0 && refGenericCodeLabelsTypeArr[0] != null) {
    			refGenericCodeLabelsTypeDTO.setCode(refGenericCodeLabelsTypeArr[0].toString());
    		} else {
    			refGenericCodeLabelsTypeDTO.setCode("");
    		}
    		
    		// Check if we can find a LABEL FR
    		if (refGenericCodeLabelsTypeArr.length > 1 && refGenericCodeLabelsTypeArr[1] != null) {
    			refGenericCodeLabelsTypeDTO.setLabelFr(refGenericCodeLabelsTypeArr[1].toString());
    		} else {
    			refGenericCodeLabelsTypeDTO.setLabelFr("");
    		}
    		
    		// Check if we can find a LABEL EN
    		if (refGenericCodeLabelsTypeArr.length > 2 && refGenericCodeLabelsTypeArr[2] != null) {
    			refGenericCodeLabelsTypeDTO.setLabelEn(refGenericCodeLabelsTypeArr[2].toString());
    		} else {
    			refGenericCodeLabelsTypeDTO.setLabelEn("");
    		}
    		
    		// TODO : Tester tous les cas
    	}
    }
    
    /*PROTECTED REGION ID(_dKrScPRNEeaosIe0wwvjyw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

