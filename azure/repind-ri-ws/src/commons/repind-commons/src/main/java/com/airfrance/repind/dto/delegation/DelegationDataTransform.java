package com.airfrance.repind.dto.delegation;

/*PROTECTED REGION ID(_KcW4EJSNEeOwS89XbJiNOw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.individu.IndividuTransform;
import com.airfrance.repind.entity.delegation.DelegationData;
import com.airfrance.repind.entity.delegation.DelegationDataInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/*PROTECTED REGION END*/

/**
 * <p>Title : DelegationDataTransform.java</p>
 * transformation bo <-> dto pour un(e) DelegationData
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class DelegationDataTransform {

    /*PROTECTED REGION ID(_KcW4EJSNEeOwS89XbJiNOw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
	   
    /**
     * private constructor
     */
    private DelegationDataTransform() {
    }
    /**
     * dto -> bo for a DelegationData
     * @param delegationDataDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static DelegationData dto2BoLight(DelegationDataDTO delegationDataDTO) throws JrafDomainException {
        // instanciation du BO
        DelegationData delegationData = new DelegationData();
        dto2BoLight(delegationDataDTO, delegationData);

        // on retourne le BO
        return delegationData;
    }
    
    public static DelegationData dto2BoLightForUpdate(DelegationDataDTO delegationDataDTO) throws JrafDomainException {
        // instanciation du BO
        DelegationData delegationData = new DelegationData();
        dto2BoLightForUpdate(delegationDataDTO, delegationData);

        // on retourne le BO
        return delegationData;
    }

    /**
     * dto -> bo for a delegationData
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param delegationDataDTO dto
     * @param delegationData bo
     */
    public static void dto2BoLight(DelegationDataDTO delegationDataDTO, DelegationData delegationData) {
    
        /*PROTECTED REGION ID(dto2BoLight_KcW4EJSNEeOwS89XbJiNOw) ENABLED START*/
        
        dto2BoLightImpl(delegationDataDTO,delegationData);
        
        /*PROTECTED REGION END*/
    }
    
    public static void dto2BoLightForUpdate(DelegationDataDTO delegationDataDTO, DelegationData delegationData) throws JrafDomainException {
        
        dto2BoLightForUpdateImpl(delegationDataDTO,delegationData);
        
    }
    
    /**
     * dto -> bo implementation for a delegationData
     * @param delegationDataDTO dto
     * @param delegationData bo
     * @throws JrafDomainException 
     */
    private static void dto2BoLightImpl(DelegationDataDTO delegationDataDTO, DelegationData delegationData) {
        
        // property of DelegationDataDTO
        delegationData.setDelegationId(delegationDataDTO.getDelegationId());
        delegationData.setStatus(delegationDataDTO.getStatus());
        delegationData.setType(delegationDataDTO.getType());
        delegationData.setCreationSite(delegationDataDTO.getCreationSite());
        delegationData.setCreationDate(delegationDataDTO.getCreationDate());
        delegationData.setCreationSignature(delegationDataDTO.getCreationSignature());
        delegationData.setModificationSite(delegationDataDTO.getModificationSite());
        delegationData.setModificationSignature(delegationDataDTO.getModificationSignature());
        delegationData.setModificationDate(delegationDataDTO.getModificationDate());
        delegationData.setSender(delegationDataDTO.getSender()); 
        
    }
    
    private static void dto2BoLightForUpdateImpl(DelegationDataDTO delegationDataDTO, DelegationData delegationData) throws JrafDomainException {
    
        // property of DelegationDataDTO
        delegationData.setDelegationId(delegationDataDTO.getDelegationId());
        delegationData.setStatus(delegationDataDTO.getStatus());
        delegationData.setType(delegationDataDTO.getType());
        delegationData.setCreationSite(delegationDataDTO.getCreationSite());
        delegationData.setCreationDate(delegationDataDTO.getCreationDate());
        delegationData.setCreationSignature(delegationDataDTO.getCreationSignature());
        delegationData.setModificationSite(delegationDataDTO.getModificationSite());
        delegationData.setModificationSignature(delegationDataDTO.getModificationSignature());
        delegationData.setModificationDate(delegationDataDTO.getModificationDate());
        delegationData.setSender(delegationDataDTO.getSender()); 
        
        if (delegationDataDTO.getDelegateDTO() != null) {
        	delegationData.setDelegate(IndividuTransform.dto2BoLight(delegationDataDTO.getDelegateDTO()));
        }
        if (delegationDataDTO.getDelegatorDTO() != null) {
        	delegationData.setDelegator(IndividuTransform.dto2BoLight(delegationDataDTO.getDelegatorDTO()));
        }
        
        if (delegationDataDTO.getDelegationDataInfoDTO() != null) {
    		delegationData.setDelegationDataInfo(new HashSet<DelegationDataInfo>());
    		for (DelegationDataInfoDTO delegationDataInfoDTO : delegationDataDTO.getDelegationDataInfoDTO()) {
    			delegationData.getDelegationDataInfo().add(DelegationDataInfoTransform.dto2Bo(delegationDataInfoDTO));
    		}
    	}
    }

    /**
     * bo -> dto for a delegationData
     * @param pDelegationData bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static DelegationDataDTO bo2DtoLight(DelegationData pDelegationData) throws JrafDomainException {
        // instanciation du DTO
        DelegationDataDTO delegationDataDTO = new DelegationDataDTO();
        bo2DtoLight(pDelegationData, delegationDataDTO);
        // on retourne le dto
        return delegationDataDTO;
    }

    public static DelegationDataDTO bo2DtoLightForUpdate(DelegationData pDelegationData) throws JrafDomainException {
        // instanciation du DTO
        DelegationDataDTO delegationDataDTO = new DelegationDataDTO();
        bo2DtoLightForUpdate(pDelegationData, delegationDataDTO);
        // on retourne le dto
        return delegationDataDTO;
    }
    
    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param delegationData bo
     * @param delegationDataDTO dto
     */
    public static void bo2DtoLight(
        DelegationData delegationData,
        DelegationDataDTO delegationDataDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_KcW4EJSNEeOwS89XbJiNOw) ENABLED START*/

        bo2DtoLightImpl(delegationData, delegationDataDTO);

        /*PROTECTED REGION END*/
    }
    
    public static void bo2DtoLightForUpdate(DelegationData delegationData, DelegationDataDTO delegationDataDTO) throws JrafDomainException {

        bo2DtoLightForUpdateImpl(delegationData, delegationDataDTO);

    }
    
    /**
     * cd Transform a business object to DTO. Implementation method
     * @param delegationData bo
     * @param delegationDataDTO dto
     */
    private static void bo2DtoLightImpl(DelegationData delegationData,
        DelegationDataDTO delegationDataDTO){
    

        // simple properties
        delegationDataDTO.setDelegationId(delegationData.getDelegationId());
        delegationDataDTO.setStatus(delegationData.getStatus());
        delegationDataDTO.setType(delegationData.getType());
        delegationDataDTO.setCreationSite(delegationData.getCreationSite());
        delegationDataDTO.setCreationDate(delegationData.getCreationDate());
        delegationDataDTO.setCreationSignature(delegationData.getCreationSignature());
        delegationDataDTO.setModificationSite(delegationData.getModificationSite());
        delegationDataDTO.setModificationSignature(delegationData.getModificationSignature());
        delegationDataDTO.setModificationDate(delegationData.getModificationDate());
        delegationDataDTO.setSender(delegationData.getSender());
    }
    
    private static void bo2DtoLightForUpdateImpl(DelegationData delegationData, DelegationDataDTO delegationDataDTO) throws JrafDomainException {

        // simple properties
        delegationDataDTO.setDelegationId(delegationData.getDelegationId());
        delegationDataDTO.setStatus(delegationData.getStatus());
        delegationDataDTO.setType(delegationData.getType());
        delegationDataDTO.setCreationSite(delegationData.getCreationSite());
        delegationDataDTO.setCreationDate(delegationData.getCreationDate());
        delegationDataDTO.setCreationSignature(delegationData.getCreationSignature());
        delegationDataDTO.setModificationSite(delegationData.getModificationSite());
        delegationDataDTO.setModificationSignature(delegationData.getModificationSignature());
        delegationDataDTO.setModificationDate(delegationData.getModificationDate());
        delegationDataDTO.setSender(delegationData.getSender());
        
        if (delegationData.getDelegate() != null) {
        	delegationDataDTO.setDelegateDTO(IndividuTransform.bo2DtoLight(delegationData.getDelegate()));
        }
        if (delegationData.getDelegator() != null) {
        	delegationDataDTO.setDelegatorDTO(IndividuTransform.bo2DtoLight(delegationData.getDelegator()));
        }
        
        if (delegationData.getDelegationDataInfo() != null) {
    		delegationDataDTO.setDelegationDataInfoDTO(new HashSet<DelegationDataInfoDTO>());
    		for (DelegationDataInfo delegationDataInfo : delegationData.getDelegationDataInfo()) {
    			delegationDataDTO.getDelegationDataInfoDTO().add(DelegationDataInfoTransform.bo2Dto(delegationDataInfo));
    		}
    	}
    }
    
    /*PROTECTED REGION ID(_KcW4EJSNEeOwS89XbJiNOw u m - Tr) ENABLED START*/

    /**
	 * Transform delegation data
	 */
	public static Set<DelegationData> dto2BoLight(List<DelegationDataDTO> delegationDataDTOList) throws JrafDomainException {
		
		if(delegationDataDTOList==null) {
			return null;
		}
		
		Set<DelegationData> delegationDataList = new HashSet<DelegationData>();
		
		for(DelegationDataDTO delegationDataDTO : delegationDataDTOList) {
			delegationDataList.add(dto2BoLight(delegationDataDTO));
		}
		
		return delegationDataList;
	}
	
	public static Set<DelegationData> dto2BoLightForUpdate(List<DelegationDataDTO> delegationDataDTOList) throws JrafDomainException {
		
		if(delegationDataDTOList==null) {
			return null;
		}
		
		Set<DelegationData> delegationDataList = new HashSet<DelegationData>();
		
		for(DelegationDataDTO delegationDataDTO : delegationDataDTOList) {
			delegationDataList.add(dto2BoLightForUpdate(delegationDataDTO));
		}
		
		return delegationDataList;
	}
	
	/**
	 * Transform delegation data
	 */
	public static List<DelegationDataDTO> bo2DtoLight(List<DelegationData> delegationDataList) throws JrafDomainException {
		
		if(delegationDataList==null) {
			return null;
		}
		
		List<DelegationDataDTO> delegationDataDTOList = new ArrayList<DelegationDataDTO>();
		
		for(DelegationData delegationData : delegationDataList) {
			delegationDataDTOList.add(bo2DtoLight(delegationData));
		}
		
		return delegationDataDTOList;
	}
	
	public static List<DelegationDataDTO> bo2DtoLightForUpdate(Set<DelegationData> delegationDataList) throws JrafDomainException {
		
		if(delegationDataList==null) {
			return null;
		}
		
		List<DelegationDataDTO> delegationDataDTOList = new ArrayList<DelegationDataDTO>();
		
		for(DelegationData delegationData : delegationDataList) {
			delegationDataDTOList.add(bo2DtoLightForUpdate(delegationData));
		}
		
		return delegationDataDTOList;
	}
	
    /**
     * Transform delegation data
     */
    public static List<DelegationDataDTO> bo2DtoLight(Set<DelegationData> delegationDataList) throws JrafDomainException {

        return bo2DtoLight(new ArrayList<DelegationData>(delegationDataList));
    }
	
	/**
     * Transform delegation data with Individual link
     */
    public static DelegationData dto2Bo(DelegationDataDTO delegationDataDTO) throws JrafDomainException {
        DelegationData delegationData = new DelegationData();
        dto2Bo(delegationDataDTO, delegationData);
        return delegationData;
    }

    /**
     * Transform delegation data with Individual link
     */
    public static void dto2Bo(DelegationDataDTO delegationDataDTO, DelegationData delegationData) throws JrafDomainException {
    	dto2BoLight(delegationDataDTO, delegationData);
        dto2BoLink(delegationDataDTO, delegationData);
    }
    
    /**
     * Transform delegation data with Individual link
     */
    public static DelegationDataDTO bo2Dto(DelegationData pDelegationData) throws JrafDomainException {
        DelegationDataDTO delegationDataDTO = new DelegationDataDTO();
        bo2Dto(pDelegationData, delegationDataDTO);
        return delegationDataDTO;
    }
    
    /**
     * Transform delegation data with Individual link
     */
    public static void bo2Dto(DelegationData delegationData, DelegationDataDTO delegationDataDTO) throws JrafDomainException {
        bo2DtoLight(delegationData, delegationDataDTO);
        bo2DtoLink(delegationData, delegationDataDTO);
    }
    
    /**
     * Transform delegation data with Individual link
     */
	public static List<DelegationDataDTO> bo2Dto(List<DelegationData> delegationDataList) throws JrafDomainException {
		
		if(delegationDataList==null) {
			return null;
		}
		
		List<DelegationDataDTO> delegationDataDTOList = new ArrayList<DelegationDataDTO>();
		
		for(DelegationData delegationData : delegationDataList) {
			delegationDataDTOList.add(bo2Dto(delegationData));
		}
		
		return delegationDataDTOList;
		
	}
	
    /**
     * Transform delegation data with Individual link
     */
	public static List<DelegationDataDTO> bo2Dto(Set<DelegationData> delegationDataList) throws JrafDomainException {
		
		if(delegationDataList==null) {
			return null;
		}
		
		List<DelegationDataDTO> delegationDataDTOList = new ArrayList<DelegationDataDTO>();
		
		for(DelegationData delegationData : delegationDataList) {
			delegationDataDTOList.add(bo2Dto(delegationData));
		}
		
		return delegationDataDTOList;
		
	}
              
    private static void dto2BoLink(DelegationDataDTO delegationDataDTO, DelegationData delegationData) throws JrafDomainException{

    	if(delegationDataDTO==null) {
    		return;
    	}
    	
    	if (delegationDataDTO.getDelegateDTO() != null) {
			delegationData.setDelegate(IndividuTransform.dto2BoLight(delegationDataDTO.getDelegateDTO()));
    	}
	    	
	    if (delegationDataDTO != null && delegationDataDTO.getDelegatorDTO() != null) {
	    	delegationData.setDelegator(IndividuTransform.dto2BoLight(delegationDataDTO.getDelegatorDTO()));
	    }
	    
	    if (delegationDataDTO.getDelegationDataInfoDTO() != null) {
    		delegationData.setDelegationDataInfo(new HashSet<DelegationDataInfo>());
    		for (DelegationDataInfoDTO delegationDataInfoDTO : delegationDataDTO.getDelegationDataInfoDTO()) {
    			delegationData.getDelegationDataInfo().add(DelegationDataInfoTransform.dto2Bo(delegationDataInfoDTO));
    		}
    	}
    }
    
    private static void bo2DtoLink(DelegationData delegationData, DelegationDataDTO delegationDataDTO) throws JrafDomainException{

    	if(delegationData==null) {
    		return;
    	}
    	
    	if (delegationData.getDelegate() != null) {
			delegationDataDTO.setDelegateDTO(IndividuTransform.bo2DtoLight(delegationData.getDelegate()));
    	}
	    	
    	if (delegationData.getDelegator() != null) {
    		delegationDataDTO.setDelegatorDTO(IndividuTransform.bo2DtoLight(delegationData.getDelegator()));
    	}
    	
    	if (delegationData.getDelegationDataInfo() != null) {
    		delegationDataDTO.setDelegationDataInfoDTO(new HashSet<DelegationDataInfoDTO>());
    		for (DelegationDataInfo delegationDataInfo : delegationData.getDelegationDataInfo()) {
    			delegationDataDTO.getDelegationDataInfoDTO().add(DelegationDataInfoTransform.bo2Dto(delegationDataInfo));
    		}
    	}
	    	
    }
    
	public static Set<DelegationData> dto2Bo(List<DelegationDataDTO> delegationListDTO) throws JrafDomainException {
		if(delegationListDTO==null) {
			return null;
		}
		
		Set<DelegationData> delegationDataList = new HashSet<DelegationData>();
		
		for(DelegationDataDTO delegationDataDTO : delegationListDTO) {
			delegationDataList.add(dto2Bo(delegationDataDTO));
		}
		
		return delegationDataList;
	}
	
	public static DelegationData dto2BoForCreation(DelegationDataDTO delegationDataDTO) throws JrafDomainException {
		if (delegationDataDTO == null) {
			return null;
		}
		
		// transform dto to bo
		DelegationData delegationData = dto2BoLight(delegationDataDTO);
		
		dto2BoLink(delegationDataDTO, delegationData);
		
        // link data to identifier
        dto2BoLinkDataForCreation(delegationDataDTO, delegationData);
		
		return delegationData;
	}
	
    private static void dto2BoLinkDataForCreation(DelegationDataDTO delegationDataDTO, DelegationData delegationData) throws JrafDomainException {
    	
    	if(delegationDataDTO==null || delegationData==null) {
    		return;
    	}
    	
    	// get external identifier data list DTO
    	Set<DelegationDataInfoDTO> delegationDataInfoListDTO = delegationDataDTO.getDelegationDataInfoDTO();
    	
        if(delegationDataInfoListDTO==null || delegationDataInfoListDTO.isEmpty()) {
        	return;
        }
    	
    	// link data to identifier
    	Set<DelegationDataInfo> delegationDataInfoList = DelegationDataInfoTransform.dto2BoLight(delegationDataInfoListDTO);
    	delegationData.setDelegationDataInfo(new HashSet<DelegationDataInfo>());
        for (DelegationDataInfo ddi : delegationDataInfoList) {
        	delegationData.getDelegationDataInfo().add(ddi);
        }
    	
        // link identifier to data
        for(DelegationDataInfo delegationDataInfo : delegationDataInfoList) {
        	delegationDataInfo.setDelegationData(delegationData);
        }
    }
       
    /*PROTECTED REGION END*/
}
