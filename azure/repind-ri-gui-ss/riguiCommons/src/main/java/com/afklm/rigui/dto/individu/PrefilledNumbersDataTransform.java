package com.afklm.rigui.dto.individu;

/*PROTECTED REGION ID(_EUk2QJ2qEeWBdds6EPJFhg i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.individu.PrefilledNumbers;
import com.afklm.rigui.entity.individu.PrefilledNumbersData;

import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : PrefilledNumbersDataTransform.java</p>
 * transformation bo <-> dto pour un(e) PrefilledNumbersData
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class PrefilledNumbersDataTransform {

    /*PROTECTED REGION ID(_EUk2QJ2qEeWBdds6EPJFhg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private PrefilledNumbersDataTransform() {
    }
    /**
     * dto -> bo for a PrefilledNumbersData
     * @param prefilledNumbersDataDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static PrefilledNumbersData dto2BoLight(PrefilledNumbersDataDTO prefilledNumbersDataDTO) throws JrafDomainException {
        // instanciation du BO
        PrefilledNumbersData prefilledNumbersData = new PrefilledNumbersData();
        dto2BoLight(prefilledNumbersDataDTO, prefilledNumbersData);

        // on retourne le BO
        return prefilledNumbersData;
    }

    /**
     * dto -> bo for a prefilledNumbersData
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param prefilledNumbersDataDTO dto
     * @param prefilledNumbersData bo
     */
    public static void dto2BoLight(PrefilledNumbersDataDTO prefilledNumbersDataDTO, PrefilledNumbersData prefilledNumbersData) {
    
        /*PROTECTED REGION ID(dto2BoLight_EUk2QJ2qEeWBdds6EPJFhg) ENABLED START*/
        
        dto2BoLightImpl(prefilledNumbersDataDTO,prefilledNumbersData);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a prefilledNumbersData
     * @param prefilledNumbersDataDTO dto
     * @param prefilledNumbersData bo
     */
    private static void dto2BoLightImpl(PrefilledNumbersDataDTO prefilledNumbersDataDTO, PrefilledNumbersData prefilledNumbersData){
    
        // property of PrefilledNumbersDataDTO
        prefilledNumbersData.setPrefilledNumbersDataId(prefilledNumbersDataDTO.getPrefilledNumbersDataId());
        prefilledNumbersData.setKey(prefilledNumbersDataDTO.getKey());
        prefilledNumbersData.setValue(prefilledNumbersDataDTO.getValue());
    
    }

    /**
     * bo -> dto for a prefilledNumbersData
     * @param pPrefilledNumbersData bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static PrefilledNumbersDataDTO bo2DtoLight(PrefilledNumbersData pPrefilledNumbersData) throws JrafDomainException {
        // instanciation du DTO
        PrefilledNumbersDataDTO prefilledNumbersDataDTO = new PrefilledNumbersDataDTO();
        bo2DtoLight(pPrefilledNumbersData, prefilledNumbersDataDTO);
        // on retourne le dto
        return prefilledNumbersDataDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param prefilledNumbersData bo
     * @param prefilledNumbersDataDTO dto
     */
    public static void bo2DtoLight(
        PrefilledNumbersData prefilledNumbersData,
        PrefilledNumbersDataDTO prefilledNumbersDataDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_EUk2QJ2qEeWBdds6EPJFhg) ENABLED START*/

        bo2DtoLightImpl(prefilledNumbersData, prefilledNumbersDataDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param prefilledNumbersData bo
     * @param prefilledNumbersDataDTO dto
     */
    private static void bo2DtoLightImpl(PrefilledNumbersData prefilledNumbersData,
        PrefilledNumbersDataDTO prefilledNumbersDataDTO){
    

        // simple properties
        prefilledNumbersDataDTO.setPrefilledNumbersDataId(prefilledNumbersData.getPrefilledNumbersDataId());
        prefilledNumbersDataDTO.setKey(prefilledNumbersData.getKey());
        prefilledNumbersDataDTO.setValue(prefilledNumbersData.getValue());
    
    }
    
    /*PROTECTED REGION ID(_EUk2QJ2qEeWBdds6EPJFhg u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    public static Set<PrefilledNumbersData> dto2BoLight(Set<PrefilledNumbersDataDTO> prefilledNumbersDataDTOList, PrefilledNumbers prefilledNumbers) throws JrafDomainException {
    	Set<PrefilledNumbersData> prefilledNumbersDataList = null;
//    	if(prefilledNumbersDataDTOList != null && !prefilledNumbersDataDTOList.isEmpty()) {
    	if(prefilledNumbersDataDTOList != null) {
    		prefilledNumbersDataList = new HashSet<PrefilledNumbersData>();
    		for(PrefilledNumbersDataDTO prefilledNumbersDataDTOLoop : prefilledNumbersDataDTOList) {
    			PrefilledNumbersData prefilledNumbersData = PrefilledNumbersDataTransform.dto2BoLight(prefilledNumbersDataDTOLoop);
    			prefilledNumbersData.setPrefilledNumbers(prefilledNumbers);
    			prefilledNumbersDataList.add(prefilledNumbersData);
    		}
    	}
		return prefilledNumbersDataList;
    }
    
    public static Set<PrefilledNumbersDataDTO> bo2DtoLight(Set<PrefilledNumbersData> prefilledNumbersDataList, PrefilledNumbersDTO prefilledNumbersDTO) throws JrafDomainException {
    	Set<PrefilledNumbersDataDTO> prefilledNumbersDataDTOList = null;
//    	if(prefilledNumbersDataList != null && !prefilledNumbersDataList.isEmpty()) {
    	if(prefilledNumbersDataList != null) {
    		prefilledNumbersDataDTOList = new HashSet<PrefilledNumbersDataDTO>();
    		for(PrefilledNumbersData prefilledNumbersDataLoop : prefilledNumbersDataList) {
    			PrefilledNumbersDataDTO prefilledNumbersData = PrefilledNumbersDataTransform.bo2DtoLight(prefilledNumbersDataLoop);
    			prefilledNumbersData.setPrefilledNumbersDTO(prefilledNumbersDTO);
    			prefilledNumbersDataDTOList.add(prefilledNumbersData);
    		}
    	}
		return prefilledNumbersDataDTOList;
    }
    /*PROTECTED REGION END*/
}

