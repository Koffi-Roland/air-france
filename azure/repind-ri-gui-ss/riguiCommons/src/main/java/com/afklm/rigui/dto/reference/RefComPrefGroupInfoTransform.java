package com.afklm.rigui.dto.reference;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.reference.RefComPrefGroupInfo;

public final class RefComPrefGroupInfoTransform {

    /**
     * private constructor
     */
    private RefComPrefGroupInfoTransform() {
    }
    
    public static RefComPrefGroupInfo dto2Bo(RefComPrefGroupInfoDTO refComPrefGroupInfoDTO) throws JrafDomainException {
    	
    	if (refComPrefGroupInfoDTO == null) {
    		return null;
    	}
    	
    	RefComPrefGroupInfo refComPrefGroupInfo = new RefComPrefGroupInfo();
    	
    	refComPrefGroupInfo.setCode(refComPrefGroupInfoDTO.getCode());
    	refComPrefGroupInfo.setDateCreation(refComPrefGroupInfoDTO.getDateCreation());
    	refComPrefGroupInfo.setDateModification(refComPrefGroupInfoDTO.getDateModification());
    	refComPrefGroupInfo.setDefaultOption(refComPrefGroupInfoDTO.getDefaultOption());
    	refComPrefGroupInfo.setId(refComPrefGroupInfoDTO.getId());
    	refComPrefGroupInfo.setLibelleEN(refComPrefGroupInfoDTO.getLibelleEN());
    	refComPrefGroupInfo.setLibelleFR(refComPrefGroupInfoDTO.getLibelleFR());
    	refComPrefGroupInfo.setMandatoryOption(refComPrefGroupInfoDTO.getMandatoryOption());
    	refComPrefGroupInfo.setSignatureCreation(refComPrefGroupInfoDTO.getSignatureCreation());
    	refComPrefGroupInfo.setSignatureModification(refComPrefGroupInfoDTO.getSignatureModification());
    	refComPrefGroupInfo.setSiteCreation(refComPrefGroupInfoDTO.getSiteCreation());
    	refComPrefGroupInfo.setSiteModification(refComPrefGroupInfoDTO.getSiteModification());
    	    	
    	return refComPrefGroupInfo;
    }
    
    public static RefComPrefGroupInfoDTO bo2Dto(RefComPrefGroupInfo refComPrefGroupInfo) throws JrafDomainException {
    	
    	RefComPrefGroupInfoDTO refComPrefGroupInfoDTO = new RefComPrefGroupInfoDTO();
    	bo2Dto(refComPrefGroupInfo, refComPrefGroupInfoDTO);
    	
    	return refComPrefGroupInfoDTO;
    }
    
    public static void bo2Dto(RefComPrefGroupInfo refComPrefGroupInfo, RefComPrefGroupInfoDTO refComPrefGroupInfoDTO) throws JrafDomainException {
    	
    	if (refComPrefGroupInfoDTO == null) {
    		refComPrefGroupInfoDTO = new RefComPrefGroupInfoDTO();
    	}
    	
    	refComPrefGroupInfoDTO.setCode(refComPrefGroupInfo.getCode());
    	refComPrefGroupInfoDTO.setDateCreation(refComPrefGroupInfo.getDateCreation());
    	refComPrefGroupInfoDTO.setDateModification(refComPrefGroupInfo.getDateModification());
    	refComPrefGroupInfoDTO.setDefaultOption(refComPrefGroupInfo.getDefaultOption());
    	refComPrefGroupInfoDTO.setId(refComPrefGroupInfo.getId());
    	refComPrefGroupInfoDTO.setLibelleEN(refComPrefGroupInfo.getLibelleEN());
    	refComPrefGroupInfoDTO.setLibelleFR(refComPrefGroupInfo.getLibelleFR());
    	refComPrefGroupInfoDTO.setMandatoryOption(refComPrefGroupInfo.getMandatoryOption());
    	refComPrefGroupInfoDTO.setSignatureCreation(refComPrefGroupInfo.getSignatureCreation());
    	refComPrefGroupInfoDTO.setSignatureModification(refComPrefGroupInfo.getSignatureModification());
    	refComPrefGroupInfoDTO.setSiteCreation(refComPrefGroupInfo.getSiteCreation());
    	refComPrefGroupInfoDTO.setSiteModification(refComPrefGroupInfo.getSiteModification());
    }
    
    
    
    public static void updateDto(RefComPrefGroupInfoDTO refComPrefGroupInfoDTO, RefComPrefGroupInfoDTO refComPrefGroupInfoDTOFromDB) {

    	refComPrefGroupInfoDTOFromDB.setCode(refComPrefGroupInfoDTO.getCode());
    	refComPrefGroupInfoDTOFromDB.setDateModification(refComPrefGroupInfoDTO.getDateModification());
    	refComPrefGroupInfoDTOFromDB.setDefaultOption(refComPrefGroupInfoDTO.getDefaultOption());
    	refComPrefGroupInfoDTOFromDB.setId(refComPrefGroupInfoDTO.getId());
    	refComPrefGroupInfoDTOFromDB.setLibelleEN(refComPrefGroupInfoDTO.getLibelleEN());
    	refComPrefGroupInfoDTOFromDB.setLibelleFR(refComPrefGroupInfoDTO.getLibelleFR());
    	refComPrefGroupInfoDTOFromDB.setMandatoryOption(refComPrefGroupInfoDTO.getMandatoryOption());
    	refComPrefGroupInfoDTOFromDB.setSignatureModification(refComPrefGroupInfoDTO.getSignatureModification());
    	refComPrefGroupInfoDTOFromDB.setSiteModification(refComPrefGroupInfoDTO.getSiteModification());
    }
}

