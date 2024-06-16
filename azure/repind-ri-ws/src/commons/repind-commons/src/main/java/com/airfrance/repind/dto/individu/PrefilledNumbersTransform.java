package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_G06wMPLPEeKCpfUhWpPN4g i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.individu.PrefilledNumbers;
import com.airfrance.repind.entity.individu.PrefilledNumbersData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : PrefilledNumbersTransform.java</p>
 * transformation bo <-> dto pour un(e) PrefilledNumbers
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class PrefilledNumbersTransform {

    /*PROTECTED REGION ID(_G06wMPLPEeKCpfUhWpPN4g u var - Tr) ENABLED START*/
	private static Log LOGGER  = LogFactory.getLog(PrefilledNumbersTransform.class);
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private PrefilledNumbersTransform() {
    }
    /**
     * dto -> bo for a PrefilledNumbers
     * @param prefilledNumbersDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static PrefilledNumbers dto2BoLight(PrefilledNumbersDTO prefilledNumbersDTO) throws JrafDomainException {
        // instanciation du BO
        PrefilledNumbers prefilledNumbers = new PrefilledNumbers();
        dto2BoLight(prefilledNumbersDTO, prefilledNumbers);

        // on retourne le BO
        return prefilledNumbers;
    }

    /**
     * dto -> bo for a prefilledNumbers
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param prefilledNumbersDTO dto
     * @param prefilledNumbers bo
     */
    public static void dto2BoLight(PrefilledNumbersDTO prefilledNumbersDTO, PrefilledNumbers prefilledNumbers) {
    
        /*PROTECTED REGION ID(dto2BoLight_G06wMPLPEeKCpfUhWpPN4g) ENABLED START*/
        
        dto2BoLightImpl(prefilledNumbersDTO,prefilledNumbers);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a prefilledNumbers
     * @param prefilledNumbersDTO dto
     * @param prefilledNumbers bo
     */
    private static void dto2BoLightImpl(PrefilledNumbersDTO prefilledNumbersDTO, PrefilledNumbers prefilledNumbers){
    
        // property of PrefilledNumbersDTO
        prefilledNumbers.setPrefilledNumbersId(prefilledNumbersDTO.getPrefilledNumbersId());
        prefilledNumbers.setContractNumber(prefilledNumbersDTO.getContractNumber());
        prefilledNumbers.setContractType(prefilledNumbersDTO.getContractType());
        prefilledNumbers.setSgin(prefilledNumbersDTO.getSgin());
        prefilledNumbers.setCreationDate(prefilledNumbersDTO.getCreationDate());
        prefilledNumbers.setCreationSignature(prefilledNumbersDTO.getCreationSignature());
        prefilledNumbers.setCreationSite(prefilledNumbersDTO.getCreationSite());
        prefilledNumbers.setModificationDate(prefilledNumbersDTO.getModificationDate());
        prefilledNumbers.setModificationSignature(prefilledNumbersDTO.getModificationSignature());
        prefilledNumbers.setModificationSite(prefilledNumbersDTO.getModificationSite());
    
    }

    /**
     * bo -> dto for a prefilledNumbers
     * @param pPrefilledNumbers bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static PrefilledNumbersDTO bo2DtoLight(PrefilledNumbers pPrefilledNumbers) throws JrafDomainException {
        // instanciation du DTO
        PrefilledNumbersDTO prefilledNumbersDTO = new PrefilledNumbersDTO();
        bo2DtoLight(pPrefilledNumbers, prefilledNumbersDTO);
        // on retourne le dto
        return prefilledNumbersDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param prefilledNumbers bo
     * @param prefilledNumbersDTO dto
     */
    public static void bo2DtoLight(
        PrefilledNumbers prefilledNumbers,
        PrefilledNumbersDTO prefilledNumbersDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_G06wMPLPEeKCpfUhWpPN4g) ENABLED START*/

        bo2DtoLightImpl(prefilledNumbers, prefilledNumbersDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param prefilledNumbers bo
     * @param prefilledNumbersDTO dto
     */
    private static void bo2DtoLightImpl(PrefilledNumbers prefilledNumbers,
        PrefilledNumbersDTO prefilledNumbersDTO){
    

        // simple properties
        prefilledNumbersDTO.setPrefilledNumbersId(prefilledNumbers.getPrefilledNumbersId());
        prefilledNumbersDTO.setContractNumber(prefilledNumbers.getContractNumber());
        prefilledNumbersDTO.setContractType(prefilledNumbers.getContractType());
        prefilledNumbersDTO.setSgin(prefilledNumbers.getSgin());
        prefilledNumbersDTO.setCreationDate(prefilledNumbers.getCreationDate());
        prefilledNumbersDTO.setCreationSignature(prefilledNumbers.getCreationSignature());
        prefilledNumbersDTO.setCreationSite(prefilledNumbers.getCreationSite());
        prefilledNumbersDTO.setModificationDate(prefilledNumbers.getModificationDate());
        prefilledNumbersDTO.setModificationSignature(prefilledNumbers.getModificationSignature());
        prefilledNumbersDTO.setModificationSite(prefilledNumbers.getModificationSite());
    
    }
    
    /*PROTECTED REGION ID(_G06wMPLPEeKCpfUhWpPN4g u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    public static Set<PrefilledNumbers> dto2BoLight(List<PrefilledNumbersDTO> listPrefilledNumbersDTO) {
    	if(listPrefilledNumbersDTO != null) {
    		Set<PrefilledNumbers> listPrefilledNumbers = new HashSet<PrefilledNumbers>();
    		for(PrefilledNumbersDTO prefilledNumbersDTO : listPrefilledNumbersDTO) {
    			PrefilledNumbers prefilledNumbers = new PrefilledNumbers();
    	        dto2BoLight(prefilledNumbersDTO, prefilledNumbers);
    	        try {
    				Set<PrefilledNumbersData> pndSet = PrefilledNumbersDataTransform.dto2BoLight(prefilledNumbersDTO.getPrefilledNumbersDataDTO(), prefilledNumbers);
    				prefilledNumbers.setPrefilledNumbersData(pndSet);
    			} catch (JrafDomainException e) {
    				// e.printStackTrace();
    				LOGGER.fatal(e);
    			}
    	        listPrefilledNumbers.add(prefilledNumbers);
    		}
    		return listPrefilledNumbers;
    	} else {
    		return null;
    	}
    }

    public static List<PrefilledNumbersDTO> bo2DtoLight(Set<PrefilledNumbers> listPrefilledNumbers) {
    	
        return bo2DtoLight(new ArrayList<PrefilledNumbers>(listPrefilledNumbers));
    }
    
    public static List<PrefilledNumbersDTO> bo2DtoLight(List<PrefilledNumbers> listPrefilledNumbers) {
    	if(listPrefilledNumbers != null) {
    		List<PrefilledNumbersDTO> listPrefilledNumbersDTO = new ArrayList<PrefilledNumbersDTO>();
    		for(PrefilledNumbers prefilledNumbers : listPrefilledNumbers) {
    			PrefilledNumbersDTO prefilledNumbersDTO = new PrefilledNumbersDTO();
    			bo2DtoLight(prefilledNumbers, prefilledNumbersDTO);
    			try {
    				Set<PrefilledNumbersDataDTO> pndDTOSet = PrefilledNumbersDataTransform.bo2DtoLight(prefilledNumbers.getPrefilledNumbersData(), prefilledNumbersDTO);
    				prefilledNumbersDTO.setPrefilledNumbersDataDTO(pndDTOSet);
    			} catch (JrafDomainException e) {
    				// e.printStackTrace();
    				LOGGER.fatal(e);
    			}
    			listPrefilledNumbersDTO.add(prefilledNumbersDTO);
    		}
    		return listPrefilledNumbersDTO;
    	} else {
    		return null;
    	}
    }
	
    public static List<PrefilledNumbersDTO> bo2Dto(Set<PrefilledNumbers> prefilledNumbersList) {
		if(prefilledNumbersList != null) {
    		List<PrefilledNumbersDTO> prefilledNumbersDTOList = new ArrayList<PrefilledNumbersDTO>();
    		for(PrefilledNumbers prefilledNumbers : prefilledNumbersList) {
    			PrefilledNumbersDTO prefilledNumbersDTO = new PrefilledNumbersDTO();
    			bo2DtoLight(prefilledNumbers, prefilledNumbersDTO);
    			try {
    				Set<PrefilledNumbersDataDTO> pndDTOSet = PrefilledNumbersDataTransform.bo2DtoLight(prefilledNumbers.getPrefilledNumbersData(), prefilledNumbersDTO);
    				prefilledNumbersDTO.setPrefilledNumbersDataDTO(pndDTOSet);
    			} catch (JrafDomainException e) {
    				// e.printStackTrace();
    				LOGGER.fatal(e);
    			}
    			prefilledNumbersDTOList.add(prefilledNumbersDTO);
    		}
    		return prefilledNumbersDTOList;
    	} else {
    		return null;
    	}
	}
	
	public static Set<PrefilledNumbers> dto2Bo(List<PrefilledNumbersDTO> prefilledNumbersDTOList) {
		if(prefilledNumbersDTOList != null) {
    		Set<PrefilledNumbers> prefilledNumbersList = new HashSet<PrefilledNumbers>();
    		for(PrefilledNumbersDTO prefilledNumbersDTO : prefilledNumbersDTOList) {
    			PrefilledNumbers prefilledNumbers = new PrefilledNumbers();
    			dto2BoLight(prefilledNumbersDTO, prefilledNumbers);
    			try {
    				Set<PrefilledNumbersData> pndSet = PrefilledNumbersDataTransform.dto2BoLight(prefilledNumbersDTO.getPrefilledNumbersDataDTO(), prefilledNumbers);
    				prefilledNumbers.setPrefilledNumbersData(pndSet);
    			} catch (JrafDomainException e) {
    				// e.printStackTrace();
    				LOGGER.fatal(e);
    			}
    			prefilledNumbersList.add(prefilledNumbers);
    		}
    		return prefilledNumbersList;
    	} else {
    		return null;
    	}
	}

	/*PROTECTED REGION END*/
}

