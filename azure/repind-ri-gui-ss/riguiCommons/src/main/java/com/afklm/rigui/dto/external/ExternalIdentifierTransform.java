package com.afklm.rigui.dto.external;

/*PROTECTED REGION ID(_Yvnh8E4kEeS-eLH--0fARw i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.dto.individu.IndividuTransform;
import com.afklm.rigui.entity.external.ExternalIdentifier;
import com.afklm.rigui.entity.external.ExternalIdentifierData;
import com.afklm.rigui.entity.individu.Individu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : ExternalIdentifierTransform.java</p>
 * transformation bo <-> dto pour un(e) ExternalIdentifier
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ExternalIdentifierTransform {

    /*PROTECTED REGION ID(_Yvnh8E4kEeS-eLH--0fARw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private ExternalIdentifierTransform() {
    }
    /**
     * dto -> bo for a ExternalIdentifier
     * @param externalIdentifierDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static ExternalIdentifier dto2BoLight(ExternalIdentifierDTO externalIdentifierDTO) throws JrafDomainException {
        // instanciation du BO
        ExternalIdentifier externalIdentifier = new ExternalIdentifier();
        dto2BoLight(externalIdentifierDTO, externalIdentifier);

        // on retourne le BO
        return externalIdentifier;
    }

    /**
     * dto -> bo for a externalIdentifier
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param externalIdentifierDTO dto
     * @param externalIdentifier bo
     */
    public static void dto2BoLight(ExternalIdentifierDTO externalIdentifierDTO, ExternalIdentifier externalIdentifier) {
    
        /*PROTECTED REGION ID(dto2BoLight_Yvnh8E4kEeS-eLH--0fARw) ENABLED START*/
        
        dto2BoLightImpl(externalIdentifierDTO,externalIdentifier);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a externalIdentifier
     * @param externalIdentifierDTO dto
     * @param externalIdentifier bo
     */
    private static void dto2BoLightImpl(ExternalIdentifierDTO externalIdentifierDTO, ExternalIdentifier externalIdentifier){
    
        // property of ExternalIdentifierDTO
        externalIdentifier.setIdentifierId(externalIdentifierDTO.getIdentifierId());
        externalIdentifier.setGin(externalIdentifierDTO.getGin());
        externalIdentifier.setIdentifier(externalIdentifierDTO.getIdentifier());
        externalIdentifier.setType(externalIdentifierDTO.getType());
        externalIdentifier.setLastSeenDate(externalIdentifierDTO.getLastSeenDate());
        externalIdentifier.setCreationDate(externalIdentifierDTO.getCreationDate());
        externalIdentifier.setCreationSignature(externalIdentifierDTO.getCreationSignature());
        externalIdentifier.setCreationSite(externalIdentifierDTO.getCreationSite());
        externalIdentifier.setModificationDate(externalIdentifierDTO.getModificationDate());
        externalIdentifier.setModificationSignature(externalIdentifierDTO.getModificationSignature());
        externalIdentifier.setModificationSite(externalIdentifierDTO.getModificationSite());
    
    }

    /**
     * bo -> dto for a externalIdentifier
     * @param pExternalIdentifier bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ExternalIdentifierDTO bo2DtoLight(ExternalIdentifier pExternalIdentifier) throws JrafDomainException {
        // instanciation du DTO
        ExternalIdentifierDTO externalIdentifierDTO = new ExternalIdentifierDTO();
        bo2DtoLight(pExternalIdentifier, externalIdentifierDTO);
        // on retourne le dto
        return externalIdentifierDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param externalIdentifier bo
     * @param externalIdentifierDTO dto
     */
    public static void bo2DtoLight(
        ExternalIdentifier externalIdentifier,
        ExternalIdentifierDTO externalIdentifierDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_Yvnh8E4kEeS-eLH--0fARw) ENABLED START*/

        bo2DtoLightImpl(externalIdentifier, externalIdentifierDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param externalIdentifier bo
     * @param externalIdentifierDTO dto
     */
    private static void bo2DtoLightImpl(ExternalIdentifier externalIdentifier,
        ExternalIdentifierDTO externalIdentifierDTO){
    

        // simple properties
        externalIdentifierDTO.setIdentifierId(externalIdentifier.getIdentifierId());
        externalIdentifierDTO.setGin(externalIdentifier.getGin());
        externalIdentifierDTO.setIdentifier(externalIdentifier.getIdentifier());
        externalIdentifierDTO.setType(externalIdentifier.getType());
        externalIdentifierDTO.setLastSeenDate(externalIdentifier.getLastSeenDate());
        externalIdentifierDTO.setCreationDate(externalIdentifier.getCreationDate());
        externalIdentifierDTO.setCreationSignature(externalIdentifier.getCreationSignature());
        externalIdentifierDTO.setCreationSite(externalIdentifier.getCreationSite());
        externalIdentifierDTO.setModificationDate(externalIdentifier.getModificationDate());
        externalIdentifierDTO.setModificationSignature(externalIdentifier.getModificationSignature());
        externalIdentifierDTO.setModificationSite(externalIdentifier.getModificationSite());
    
    }
    
    /*PROTECTED REGION ID(_Yvnh8E4kEeS-eLH--0fARw u m - Tr) ENABLED START*/

    public static List<ExternalIdentifierDTO> bo2DtoForProvide(List<ExternalIdentifier> externalIdentifierList) throws JrafDomainException {
    	
    	if(externalIdentifierList==null) {
    		return null;
    	}
    	
    	// create a new list
    	List<ExternalIdentifierDTO> externalIdentifierDTOList = new ArrayList<ExternalIdentifierDTO>();
    	
    	// transform to DTO and add to list all external identifiers
    	for(ExternalIdentifier externalIdentifier : externalIdentifierList) {
    		ExternalIdentifierDTO externalIdentifierDTO = bo2DtoForProvide(externalIdentifier);
    		externalIdentifierDTOList.add(externalIdentifierDTO);
    	}
    	
    	return externalIdentifierDTOList;
    }
    
    public static ExternalIdentifierDTO bo2DtoForProvide(ExternalIdentifier externalIdentifier) throws JrafDomainException {
    	
    	if(externalIdentifier==null) {
    		return null;
    	}
    	
    	// create DTO
    	ExternalIdentifierDTO externalIdentifierDTO = new ExternalIdentifierDTO();
    	
    	// transform to DTO with external identifier data
    	bo2DtoForProvide(externalIdentifier, externalIdentifierDTO);
    	
    	return externalIdentifierDTO;
    }
    
    public static void  bo2DtoForProvide(ExternalIdentifier externalIdentifier, ExternalIdentifierDTO externalIdentifierDTO) throws JrafDomainException {
    	
    	if(externalIdentifier==null || externalIdentifierDTO==null) {
    		return;
    	}
    	
    	// transform to DTO
    	bo2DtoLight(externalIdentifier, externalIdentifierDTO);
    	
    	// link external identifier data 
		bo2DtoLinkData(externalIdentifier, externalIdentifierDTO);
    	
    }
    
    public static ExternalIdentifier dto2BoForCreation(ExternalIdentifierDTO externalIdentifierDTO) throws JrafDomainException {
    	
    	if(externalIdentifierDTO==null) {
    		return null;
    	}
    	
    	// transform dto to bo
        ExternalIdentifier externalIdentifier = dto2BoLight(externalIdentifierDTO);
        
        // link individual to identifier
        dto2BoLinkIndividual(externalIdentifierDTO, externalIdentifier);
        
        // link data to identifier
        dto2BoLinkDataForCreation(externalIdentifierDTO, externalIdentifier);
        
        return externalIdentifier;
    	
    }
    
    public static void dto2BoForUpdate(ExternalIdentifierDTO externalIdentifierDTO, ExternalIdentifier externalIdentifier) throws JrafDomainException {
    	
    	if(externalIdentifier==null || externalIdentifierDTO==null) {
    		return;
    	}
    	
    	// transform dto to bo
        dto2BoLight(externalIdentifierDTO, externalIdentifier);
        
        // link data to identifier
        dto2BoLinkDataForUpdate(externalIdentifierDTO, externalIdentifier);
        
    }
    
    public static ExternalIdentifier dto2BoForFind(ExternalIdentifierDTO externalIdentifierDTO) throws JrafDomainException {
    
    	if(externalIdentifierDTO==null) {
    		return null;
    	}
    	
    	// transform dto to bo
        ExternalIdentifier externalIdentifier = dto2BoLight(externalIdentifierDTO);
        
        // link individual to identifier
        dto2BoLinkIndividual(externalIdentifierDTO, externalIdentifier);
        
        return externalIdentifier;
    }
    
    private static void dto2BoLinkIndividual(ExternalIdentifierDTO externalIdentifierDTO, ExternalIdentifier externalIdentifier) throws JrafDomainException {
    	
    	if(externalIdentifierDTO==null || externalIdentifier==null) {
    		return;
    	}
    	
    	// link individual to identifier
        Individu individu = IndividuTransform.dto2BoLight(externalIdentifierDTO.getIndividu());
        externalIdentifier.setIndividu(individu);
    	
    }
    
    private static void dto2BoLinkDataForCreation(ExternalIdentifierDTO externalIdentifierDTO, ExternalIdentifier externalIdentifier) throws JrafDomainException {
    	
    	if(externalIdentifierDTO==null || externalIdentifier==null) {
    		return;
    	}
    	
    	// get external identifier data list DTO
    	List<ExternalIdentifierDataDTO> externalIdentifierDataListDTO = externalIdentifierDTO.getExternalIdentifierDataList();
    	
        if(externalIdentifierDataListDTO==null || externalIdentifierDataListDTO.isEmpty()) {
        	return;
        }
    	
    	// link data to identifier
    	List<ExternalIdentifierData> externalIdentifierDataList = ExternalIdentifierDataTransform.dto2BoLight(externalIdentifierDataListDTO);
        externalIdentifier.setExternalIdentifierDataList(new HashSet<ExternalIdentifierData>());
        for (ExternalIdentifierData eid : externalIdentifierDataList) {
            externalIdentifier.getExternalIdentifierDataList().add(eid);
        }
    	
        // link identifier to data
        for(ExternalIdentifierData externalIndentifierData : externalIdentifierDataList) {
        	externalIndentifierData.setExternalIdentifier(externalIdentifier);
        }
    	
    }
    
    private static void dto2BoLinkDataForUpdate(ExternalIdentifierDTO externalIdentifierDTO, ExternalIdentifier externalIdentifier) throws JrafDomainException {
    	
    	if(externalIdentifierDTO==null || externalIdentifier==null) {
    		return;
    	}
    	
    	// get external identifier data list DTO
    	List<ExternalIdentifierDataDTO> externalIdentifierDataListDTO = externalIdentifierDTO.getExternalIdentifierDataList();
    	
        if(externalIdentifierDataListDTO==null || externalIdentifierDataListDTO.isEmpty()) {
        	return;
        }
        
        // get external identifier data list BO
    	Set<ExternalIdentifierData> externalIdentifierDataList = externalIdentifier.getExternalIdentifierDataList();
                
    	// link data to identifier
    	ExternalIdentifierDataTransform.dto2BoLight(externalIdentifierDataListDTO, externalIdentifierDataList);
        
        // link identifier to data
        for(ExternalIdentifierData externalIndentifierData : externalIdentifierDataList) {
        	externalIndentifierData.setExternalIdentifier(externalIdentifier);
        }
    	
    }
    
    private static void bo2DtoLinkData(ExternalIdentifier externalIdentifier, ExternalIdentifierDTO externalIdentifierDTO) throws JrafDomainException {

    	if(externalIdentifier==null || externalIdentifierDTO==null) {
    		return;
    	}
    	
    	// link data to identifier
    	List<ExternalIdentifierDataDTO> externalIndentifierDataDTOList = ExternalIdentifierDataTransform.bo2DtoLight(externalIdentifier.getExternalIdentifierDataList());
    	externalIdentifierDTO.setExternalIdentifierDataList(externalIndentifierDataDTOList);
    	
    }

    public static ExternalIdentifier dto2Bo(ExternalIdentifierDTO externalIdentifierDTO) throws JrafDomainException {
        // instanciation du BO
        ExternalIdentifier externalIdentifier = new ExternalIdentifier();
        dto2BoLight(externalIdentifierDTO, externalIdentifier);
        HashSet<ExternalIdentifierData> eidList = externalIdentifierDTO.getExternalIdentifierDataList() != null ?
                new HashSet<>(ExternalIdentifierDataTransform.dto2BoLight(externalIdentifierDTO.getExternalIdentifierDataList())) : null;
        externalIdentifier.setExternalIdentifierDataList(eidList);

        // on retourne le BO
        return externalIdentifier;
    }


    
    /*PROTECTED REGION END*/
}

