package com.airfrance.repind.dto.external;

/*PROTECTED REGION ID(_YvniAU4kEeS-eLH--0fARw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.external.ExternalIdentifierData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : ExternalIdentifierDataTransform.java</p>
 * transformation bo <-> dto pour un(e) ExternalIdentifierData
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ExternalIdentifierDataTransform {

    /*PROTECTED REGION ID(_YvniAU4kEeS-eLH--0fARw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ExternalIdentifierDataTransform() {
    }
    /**
     * dto -> bo for a ExternalIdentifierData
     * @param externalIdentifierDataDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static ExternalIdentifierData dto2BoLight(ExternalIdentifierDataDTO externalIdentifierDataDTO) throws JrafDomainException {
        // instanciation du BO
        ExternalIdentifierData externalIdentifierData = new ExternalIdentifierData();
        dto2BoLight(externalIdentifierDataDTO, externalIdentifierData);

        // on retourne le BO
        return externalIdentifierData;
    }

    /**
     * dto -> bo for a externalIdentifierData
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param externalIdentifierDataDTO dto
     * @param externalIdentifierData bo
     */
    public static void dto2BoLight(ExternalIdentifierDataDTO externalIdentifierDataDTO, ExternalIdentifierData externalIdentifierData) {
    
        /*PROTECTED REGION ID(dto2BoLight_YvniAU4kEeS-eLH--0fARw) ENABLED START*/
        
        dto2BoLightImpl(externalIdentifierDataDTO,externalIdentifierData);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a externalIdentifierData
     * @param externalIdentifierDataDTO dto
     * @param externalIdentifierData bo
     */
    private static void dto2BoLightImpl(ExternalIdentifierDataDTO externalIdentifierDataDTO, ExternalIdentifierData externalIdentifierData){
    
        // property of ExternalIdentifierDataDTO
        externalIdentifierData.setIdentifierDataId(externalIdentifierDataDTO.getIdentifierDataId());
        externalIdentifierData.setKey(externalIdentifierDataDTO.getKey());
        externalIdentifierData.setValue(externalIdentifierDataDTO.getValue());
        externalIdentifierData.setCreationDate(externalIdentifierDataDTO.getCreationDate());
        externalIdentifierData.setCreationSignature(externalIdentifierDataDTO.getCreationSignature());
        externalIdentifierData.setCreationSite(externalIdentifierDataDTO.getCreationSite());
        externalIdentifierData.setModificationDate(externalIdentifierDataDTO.getModificationDate());
        externalIdentifierData.setModificationSignature(externalIdentifierDataDTO.getModificationSignature());
        externalIdentifierData.setModificationSite(externalIdentifierDataDTO.getModificationSite());
    
    }

    /**
     * bo -> dto for a externalIdentifierData
     * @param pExternalIdentifierData bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ExternalIdentifierDataDTO bo2DtoLight(ExternalIdentifierData pExternalIdentifierData) throws JrafDomainException {
        // instanciation du DTO
        ExternalIdentifierDataDTO externalIdentifierDataDTO = new ExternalIdentifierDataDTO();
        bo2DtoLight(pExternalIdentifierData, externalIdentifierDataDTO);
        // on retourne le dto
        return externalIdentifierDataDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param externalIdentifierData bo
     * @param externalIdentifierDataDTO dto
     */
    public static void bo2DtoLight(
        ExternalIdentifierData externalIdentifierData,
        ExternalIdentifierDataDTO externalIdentifierDataDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_YvniAU4kEeS-eLH--0fARw) ENABLED START*/

        bo2DtoLightImpl(externalIdentifierData, externalIdentifierDataDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param externalIdentifierData bo
     * @param externalIdentifierDataDTO dto
     */
    private static void bo2DtoLightImpl(ExternalIdentifierData externalIdentifierData,
        ExternalIdentifierDataDTO externalIdentifierDataDTO){
    

        // simple properties
        externalIdentifierDataDTO.setIdentifierDataId(externalIdentifierData.getIdentifierDataId());
        externalIdentifierDataDTO.setKey(externalIdentifierData.getKey());
        externalIdentifierDataDTO.setValue(externalIdentifierData.getValue());
        externalIdentifierDataDTO.setCreationDate(externalIdentifierData.getCreationDate());
        externalIdentifierDataDTO.setCreationSignature(externalIdentifierData.getCreationSignature());
        externalIdentifierDataDTO.setCreationSite(externalIdentifierData.getCreationSite());
        externalIdentifierDataDTO.setModificationDate(externalIdentifierData.getModificationDate());
        externalIdentifierDataDTO.setModificationSignature(externalIdentifierData.getModificationSignature());
        externalIdentifierDataDTO.setModificationSite(externalIdentifierData.getModificationSite());
    
    }
    
    /*PROTECTED REGION ID(_YvniAU4kEeS-eLH--0fARw u m - Tr) ENABLED START*/

    public static List<ExternalIdentifierDataDTO> bo2DtoLight(List<ExternalIdentifierData> externalIdentifierDataList) throws JrafDomainException {
    	
    	if(externalIdentifierDataList==null || externalIdentifierDataList.isEmpty()) {
    		return null;
    	}
    	
    	List<ExternalIdentifierDataDTO> externalIdentifierDataDTOList = new ArrayList<ExternalIdentifierDataDTO>();
    	
    	for(ExternalIdentifierData externalIdentifierData : externalIdentifierDataList) {
    		ExternalIdentifierDataDTO externalIdentifierDataDTO = bo2DtoLight(externalIdentifierData);
    		externalIdentifierDataDTOList.add(externalIdentifierDataDTO);
    	}
    	
    	return externalIdentifierDataDTOList;
    	
    }
    
    public static List<ExternalIdentifierDataDTO> bo2DtoLight(Set<ExternalIdentifierData> externalIdentifierDataList) throws JrafDomainException {
        
        if(externalIdentifierDataList==null || externalIdentifierDataList.isEmpty()) {
                return null;
        }
        
        List<ExternalIdentifierDataDTO> externalIdentifierDataDTOList = new ArrayList<ExternalIdentifierDataDTO>();
        
        for(ExternalIdentifierData externalIdentifierData : externalIdentifierDataList) {
                ExternalIdentifierDataDTO externalIdentifierDataDTO = bo2DtoLight(externalIdentifierData);
                externalIdentifierDataDTOList.add(externalIdentifierDataDTO);
        }
        
        return externalIdentifierDataDTOList;
        
    }
    
    public static List<ExternalIdentifierData> dto2BoLight(List<ExternalIdentifierDataDTO> externalIdentifierDataDTOList) throws JrafDomainException {
    	
    	if(externalIdentifierDataDTOList==null || externalIdentifierDataDTOList.isEmpty()) {
    		return null;
    	}
    	
    	List<ExternalIdentifierData> externalIdentifierDataList = new ArrayList<ExternalIdentifierData>();
    	
    	for(ExternalIdentifierDataDTO externalIdentifierDataDTO : externalIdentifierDataDTOList) {
    		ExternalIdentifierData externalIdentifierData = dto2BoLight(externalIdentifierDataDTO);
    		externalIdentifierDataList.add(externalIdentifierData);
    	}
    	
    	return externalIdentifierDataList;
    	
    }
    
    public static void dto2BoLight(List<ExternalIdentifierDataDTO> externalIdentifierDataDTOList, List<ExternalIdentifierData> externalIdentifierDataList) throws JrafDomainException {
    	
    	if(externalIdentifierDataDTOList==null || externalIdentifierDataDTOList.isEmpty()) {
    		return;
    	}
    	
    	// if no external identifier at all, we create the list
    	if(externalIdentifierDataList==null) {
    		externalIdentifierDataList = new ArrayList<ExternalIdentifierData>();
    	}
    	
    	for(ExternalIdentifierDataDTO externalIdentifierDataDTO : externalIdentifierDataDTOList) {
    		ExternalIdentifierData externalIdentifierData = dto2BoLight(externalIdentifierDataDTO);
    		externalIdentifierDataList.add(externalIdentifierData);
    	}
    	
    }
    
    public static void dto2BoLight(List<ExternalIdentifierDataDTO> externalIdentifierDataDTOList, Set<ExternalIdentifierData> externalIdentifierDataList) throws JrafDomainException {
        
        if(externalIdentifierDataDTOList==null || externalIdentifierDataDTOList.isEmpty()) {
                return;
        }
        
        // if no external identifier at all, we create the list
        if(externalIdentifierDataList==null) {
                externalIdentifierDataList = new HashSet<ExternalIdentifierData>();
        }
        
        for(ExternalIdentifierDataDTO externalIdentifierDataDTO : externalIdentifierDataDTOList) {
                ExternalIdentifierData externalIdentifierData = dto2BoLight(externalIdentifierDataDTO);
                externalIdentifierDataList.add(externalIdentifierData);
        }
        
    }
    
    /*PROTECTED REGION END*/
}

