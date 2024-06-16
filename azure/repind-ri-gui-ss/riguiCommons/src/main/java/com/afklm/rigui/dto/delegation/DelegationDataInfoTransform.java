package com.afklm.rigui.dto.delegation;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.delegation.DelegationDataInfo;

import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : DelegationDataInfoTransform.java</p>
 * transformation bo <-> dto pour un(e) DelegationDataInfo
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class DelegationDataInfoTransform {

    /*PROTECTED REGION ID(_HIwDAOZmEee2NuY-gHh1Ow u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private DelegationDataInfoTransform() {
    }
    /**
     * dto -> bo for a DelegationDataInfo
     * @param delegationDataInfoDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static DelegationDataInfo dto2BoLight(DelegationDataInfoDTO delegationDataInfoDTO) throws JrafDomainException {
        // instanciation du BO
        DelegationDataInfo delegationDataInfo = new DelegationDataInfo();
        dto2BoLight(delegationDataInfoDTO, delegationDataInfo);

        // on retourne le BO
        return delegationDataInfo;
    }

    /**
     * dto -> bo for a delegationDataInfo
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param delegationDataInfoDTO dto
     * @param delegationDataInfo bo
     * @throws JrafDomainException 
     */
    public static void dto2BoLight(DelegationDataInfoDTO delegationDataInfoDTO, DelegationDataInfo delegationDataInfo) throws JrafDomainException {
    
        /*PROTECTED REGION ID(dto2BoLight_HIwDAOZmEee2NuY-gHh1Ow) ENABLED START*/
        
        dto2BoLightImpl(delegationDataInfoDTO,delegationDataInfo);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a delegationDataInfo
     * @param delegationDataInfoDTO dto
     * @param delegationDataInfo bo
     * @throws JrafDomainException 
     */
    private static void dto2BoLightImpl(DelegationDataInfoDTO delegationDataInfoDTO, DelegationDataInfo delegationDataInfo) throws JrafDomainException{
    
        // property of DelegationDataInfoDTO
        delegationDataInfo.setDelegationDataInfoId(delegationDataInfoDTO.getDelegationDataInfoId());
        delegationDataInfo.setType(delegationDataInfoDTO.getType());
        delegationDataInfo.setKey(delegationDataInfoDTO.getKey());
        delegationDataInfo.setTypeGroupId(delegationDataInfoDTO.getTypeGroupId());
        delegationDataInfo.setValue(delegationDataInfoDTO.getValue());
        delegationDataInfo.setDateCreation(delegationDataInfoDTO.getDateCreation());
        delegationDataInfo.setSiteCreation(delegationDataInfoDTO.getSiteCreation());
        delegationDataInfo.setSignatureCreation(delegationDataInfoDTO.getSignatureCreation());
        delegationDataInfo.setDateModification(delegationDataInfoDTO.getDateModification());
        delegationDataInfo.setSiteModification(delegationDataInfoDTO.getSiteModification());
        delegationDataInfo.setSignatureModification(delegationDataInfoDTO.getSignatureModification());
        
    }

    /**
     * bo -> dto for a delegationDataInfo
     * @param pDelegationDataInfo bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static DelegationDataInfoDTO bo2DtoLight(DelegationDataInfo pDelegationDataInfo) throws JrafDomainException {
        // instanciation du DTO
        DelegationDataInfoDTO delegationDataInfoDTO = new DelegationDataInfoDTO();
        bo2DtoLight(pDelegationDataInfo, delegationDataInfoDTO);
        // on retourne le dto
        return delegationDataInfoDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param delegationDataInfo bo
     * @param delegationDataInfoDTO dto
     * @throws JrafDomainException 
     */
    public static void bo2DtoLight(
        DelegationDataInfo delegationDataInfo,
        DelegationDataInfoDTO delegationDataInfoDTO) throws JrafDomainException {

        /*PROTECTED REGION ID(bo2DtoLight_HIwDAOZmEee2NuY-gHh1Ow) ENABLED START*/

        bo2DtoLightImpl(delegationDataInfo, delegationDataInfoDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param delegationDataInfo bo
     * @param delegationDataInfoDTO dto
     * @throws JrafDomainException 
     */
    private static void bo2DtoLightImpl(DelegationDataInfo delegationDataInfo,
        DelegationDataInfoDTO delegationDataInfoDTO) throws JrafDomainException{
    

        // simple properties
        delegationDataInfoDTO.setDelegationDataInfoId(delegationDataInfo.getDelegationDataInfoId());
        delegationDataInfoDTO.setType(delegationDataInfo.getType());
        delegationDataInfoDTO.setKey(delegationDataInfo.getKey());
        delegationDataInfoDTO.setValue(delegationDataInfo.getValue());
        delegationDataInfoDTO.setTypeGroupId(delegationDataInfo.getTypeGroupId());
        delegationDataInfoDTO.setDateCreation(delegationDataInfo.getDateCreation());
        delegationDataInfoDTO.setSiteCreation(delegationDataInfo.getSiteCreation());
        delegationDataInfoDTO.setSignatureCreation(delegationDataInfo.getSignatureCreation());
        delegationDataInfoDTO.setDateModification(delegationDataInfo.getDateModification());
        delegationDataInfoDTO.setSiteModification(delegationDataInfo.getSiteModification());
        delegationDataInfoDTO.setSignatureModification(delegationDataInfo.getSignatureModification());
        
    }
    
    public static Set<DelegationDataInfo> dto2BoLight(Set<DelegationDataInfoDTO> delegationDataInfoDTOList) throws JrafDomainException {
    	
    	if(delegationDataInfoDTOList==null || delegationDataInfoDTOList.isEmpty()) {
    		return null;
    	}
    	
    	Set<DelegationDataInfo> delegationDataInfoList = new HashSet<DelegationDataInfo>();
    	
    	for(DelegationDataInfoDTO delegationDataInfoDTO : delegationDataInfoDTOList) {
    		DelegationDataInfo delegationDataInfo = dto2BoLight(delegationDataInfoDTO);
    		delegationDataInfoList.add(delegationDataInfo);
    	}
    	
    	return delegationDataInfoList;
    }
    
    /*PROTECTED REGION ID(_HIwDAOZmEee2NuY-gHh1Ow u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    
    public static DelegationDataInfo dto2Bo(DelegationDataInfoDTO delegationDataInfoDTO) throws JrafDomainException {
        // instanciation du BO
        DelegationDataInfo delegationDataInfo = new DelegationDataInfo();
        dto2Bo(delegationDataInfoDTO, delegationDataInfo);

        // on retourne le BO
        return delegationDataInfo;
    }
    
    public static void dto2Bo(DelegationDataInfoDTO delegationDataInfoDTO, DelegationDataInfo delegationDataInfo) throws JrafDomainException {
        
        dto2BoImpl(delegationDataInfoDTO,delegationDataInfo);
        
    }
    
    private static void dto2BoImpl(DelegationDataInfoDTO delegationDataInfoDTO, DelegationDataInfo delegationDataInfo) throws JrafDomainException{
        
        // property of DelegationDataInfoDTO
        delegationDataInfo.setDelegationDataInfoId(delegationDataInfoDTO.getDelegationDataInfoId());
        delegationDataInfo.setType(delegationDataInfoDTO.getType());
        delegationDataInfo.setKey(delegationDataInfoDTO.getKey());
        delegationDataInfo.setTypeGroupId(delegationDataInfoDTO.getTypeGroupId());
        delegationDataInfo.setValue(delegationDataInfoDTO.getValue());
        delegationDataInfo.setDateCreation(delegationDataInfoDTO.getDateCreation());
        delegationDataInfo.setSiteCreation(delegationDataInfoDTO.getSiteCreation());
        delegationDataInfo.setSignatureCreation(delegationDataInfoDTO.getSignatureCreation());
        delegationDataInfo.setDateModification(delegationDataInfoDTO.getDateModification());
        delegationDataInfo.setSiteModification(delegationDataInfoDTO.getSiteModification());
        delegationDataInfo.setSignatureModification(delegationDataInfoDTO.getSignatureModification());
        
        dto2BoLink(delegationDataInfoDTO, delegationDataInfo);
    }
    
    private static void dto2BoLink(DelegationDataInfoDTO delegationDataInfoDTO, DelegationDataInfo delegationDataInfo) throws JrafDomainException {    	
    	if (delegationDataInfoDTO != null && delegationDataInfoDTO.getDelegationDataDto() != null) {
    		delegationDataInfo.setDelegationData(DelegationDataTransform.dto2BoLight(delegationDataInfoDTO.getDelegationDataDto()));
    	}
    }
    
    public static DelegationDataInfoDTO bo2Dto(DelegationDataInfo pDelegationDataInfo) throws JrafDomainException {
        // instanciation du DTO
        DelegationDataInfoDTO delegationDataInfoDTO = new DelegationDataInfoDTO();
        bo2Dto(pDelegationDataInfo, delegationDataInfoDTO);
        // on retourne le dto
        return delegationDataInfoDTO;
    }

    
    public static void bo2Dto(DelegationDataInfo delegationDataInfo, DelegationDataInfoDTO delegationDataInfoDTO) throws JrafDomainException {

            /*PROTECTED REGION ID(bo2DtoLight_HIwDAOZmEee2NuY-gHh1Ow) ENABLED START*/

            bo2DtoImpl(delegationDataInfo, delegationDataInfoDTO);

            /*PROTECTED REGION END*/

        }
    
    private static void bo2DtoImpl(DelegationDataInfo delegationDataInfo, DelegationDataInfoDTO delegationDataInfoDTO) throws JrafDomainException { 
        
            // simple properties
            delegationDataInfoDTO.setDelegationDataInfoId(delegationDataInfo.getDelegationDataInfoId());
            delegationDataInfoDTO.setType(delegationDataInfo.getType());
            delegationDataInfoDTO.setKey(delegationDataInfo.getKey());
            delegationDataInfoDTO.setValue(delegationDataInfo.getValue());
            delegationDataInfoDTO.setTypeGroupId(delegationDataInfo.getTypeGroupId());
            delegationDataInfoDTO.setDateCreation(delegationDataInfo.getDateCreation());
            delegationDataInfoDTO.setSiteCreation(delegationDataInfo.getSiteCreation());
            delegationDataInfoDTO.setSignatureCreation(delegationDataInfo.getSignatureCreation());
            delegationDataInfoDTO.setDateModification(delegationDataInfo.getDateModification());
            delegationDataInfoDTO.setSiteModification(delegationDataInfo.getSiteModification());
            delegationDataInfoDTO.setSignatureModification(delegationDataInfo.getSignatureModification());
            
            bo2DtoLink(delegationDataInfo, delegationDataInfoDTO);
    }
    
    private static void bo2DtoLink(DelegationDataInfo delegationDataInfo, DelegationDataInfoDTO delegationDataInfoDTO) throws JrafDomainException {    	
    	if (delegationDataInfo != null && delegationDataInfo.getDelegationData() != null) {
    		delegationDataInfoDTO.setDelegationDataDto(DelegationDataTransform.bo2DtoLight(delegationDataInfo.getDelegationData()));
    	}
    }
    /*PROTECTED REGION END*/
}

