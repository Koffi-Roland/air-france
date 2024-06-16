package com.airfrance.repind.dto.reference;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefComPrefDgt;
import com.airfrance.repind.entity.reference.RefComPrefMl;

import java.util.ArrayList;
import java.util.List;

public final class RefComPrefDgtTransform {

    /**
     * private constructor
     */
    private RefComPrefDgtTransform() {
    }
    
    public static RefComPrefDgt dto2Bo(RefComPrefDgtDTO refComPrefDgtDTO) throws JrafDomainException {
    	
    	if (refComPrefDgtDTO == null) {
    		return null;
    	}
    	
    	RefComPrefDgt refComPrefDgt = new RefComPrefDgt();
    	
    	refComPrefDgt.setRefComPrefDgtId(refComPrefDgtDTO.getRefComPrefDgtId());
    	refComPrefDgt.setDomain(RefComPrefDomainTransform.dto2BoLight(refComPrefDgtDTO.getDomain()));
    	refComPrefDgt.setGroupType(RefComPrefGTypeTransform.dto2BoLight(refComPrefDgtDTO.getGroupType()));
    	refComPrefDgt.setType(RefComPrefTypeTransform.dto2BoLight(refComPrefDgtDTO.getType()));
    	refComPrefDgt.setDescription(refComPrefDgtDTO.getDescription());
    	refComPrefDgt.setDateCreation(refComPrefDgtDTO.getDateCreation());
    	refComPrefDgt.setDateModification(refComPrefDgtDTO.getDateModification());
    	refComPrefDgt.setSignatureCreation(refComPrefDgtDTO.getSignatureCreation());
    	refComPrefDgt.setSignatureModification(refComPrefDgtDTO.getSignatureModification());
    	refComPrefDgt.setSiteCreation(refComPrefDgtDTO.getSiteCreation());
    	refComPrefDgt.setSiteModification(refComPrefDgtDTO.getSiteModification());
    	
    	return refComPrefDgt;
    }
    
    public static RefComPrefDgtDTO bo2Dto(RefComPrefDgt refComPrefDgt) throws JrafDomainException {
    	
    	RefComPrefDgtDTO refComPrefDgtDTO = new RefComPrefDgtDTO();
    	bo2Dto(refComPrefDgt, refComPrefDgtDTO);
    	
    	return refComPrefDgtDTO;
    }
    
    public static void bo2Dto(RefComPrefDgt refComPrefDgt, RefComPrefDgtDTO refComPrefDgtDTO) throws JrafDomainException {
    	
    	if (refComPrefDgtDTO == null) {
    		refComPrefDgtDTO = new RefComPrefDgtDTO();
    	}
    	
    	refComPrefDgtDTO.setRefComPrefDgtId(refComPrefDgt.getRefComPrefDgtId());
    	refComPrefDgtDTO.setDomain(RefComPrefDomainTransform.bo2DtoLight(refComPrefDgt.getDomain()));
    	refComPrefDgtDTO.setGroupType(RefComPrefGTypeTransform.bo2DtoLight(refComPrefDgt.getGroupType()));
    	refComPrefDgtDTO.setType(RefComPrefTypeTransform.bo2DtoLight(refComPrefDgt.getType()));
    	refComPrefDgtDTO.setDescription(refComPrefDgt.getDescription());
    	refComPrefDgtDTO.setDateCreation(refComPrefDgt.getDateCreation());
    	refComPrefDgtDTO.setDateModification(refComPrefDgt.getDateModification());
    	refComPrefDgtDTO.setSignatureCreation(refComPrefDgt.getSignatureCreation());
    	refComPrefDgtDTO.setSignatureModification(refComPrefDgt.getSignatureModification());
    	refComPrefDgtDTO.setSiteCreation(refComPrefDgt.getSiteCreation());
    	refComPrefDgtDTO.setSiteModification(refComPrefDgt.getSiteModification());	
    }
    
    public static RefComPrefDgt dto2BoWithLinkedData(RefComPrefDgtDTO refComPrefDgtDTO) throws JrafDomainException {
    	
    	if (refComPrefDgtDTO == null) {
    		return null;
    	}
    	
    	RefComPrefDgt refComPrefDgt = new RefComPrefDgt();
    	
    	refComPrefDgt.setRefComPrefDgtId(refComPrefDgtDTO.getRefComPrefDgtId());
    	refComPrefDgt.setDomain(RefComPrefDomainTransform.dto2BoLight(refComPrefDgtDTO.getDomain()));
    	refComPrefDgt.setGroupType(RefComPrefGTypeTransform.dto2BoLight(refComPrefDgtDTO.getGroupType()));
    	refComPrefDgt.setType(RefComPrefTypeTransform.dto2BoLight(refComPrefDgtDTO.getType()));
    	refComPrefDgt.setDescription(refComPrefDgtDTO.getDescription());
    	refComPrefDgt.setDateCreation(refComPrefDgtDTO.getDateCreation());
    	refComPrefDgt.setDateModification(refComPrefDgtDTO.getDateModification());
    	refComPrefDgt.setSignatureCreation(refComPrefDgtDTO.getSignatureCreation());
    	refComPrefDgt.setSignatureModification(refComPrefDgtDTO.getSignatureModification());
    	refComPrefDgt.setSiteCreation(refComPrefDgtDTO.getSiteCreation());
    	refComPrefDgt.setSiteModification(refComPrefDgtDTO.getSiteModification());
    	
    	if (refComPrefDgtDTO.getRefComPrefMls() != null && !refComPrefDgtDTO.getRefComPrefMls().isEmpty()) {
    		List<RefComPrefMl> listRefComPrefMl = new ArrayList<>();
    		for (RefComPrefMlDTO refComPrefMlDTO : refComPrefDgtDTO.getRefComPrefMls()) {
    			RefComPrefMl refComPrefMl = RefComPrefMlTransform.dto2Bo(refComPrefMlDTO);
    			refComPrefMl.setRefComPrefDgt(refComPrefDgt);
    			listRefComPrefMl.add(refComPrefMl);
    		}
    		refComPrefDgt.setRefComPrefMls(listRefComPrefMl);
    	} else {
    		refComPrefDgt.setRefComPrefMls(new ArrayList<RefComPrefMl>());
    	}
    	
    	return refComPrefDgt;
    }
    
    public static RefComPrefDgtDTO bo2DtoWithLinkedData(RefComPrefDgt refComPrefDgt) throws JrafDomainException {
    	
    	RefComPrefDgtDTO refComPrefDgtDTO = new RefComPrefDgtDTO();
    	bo2DtoWithLinkedData(refComPrefDgt, refComPrefDgtDTO);
    	
    	return refComPrefDgtDTO;
    }
    
    public static void bo2DtoWithLinkedData(RefComPrefDgt refComPrefDgt, RefComPrefDgtDTO refComPrefDgtDTO) throws JrafDomainException {
    	
    	if (refComPrefDgtDTO == null) {
    		refComPrefDgtDTO = new RefComPrefDgtDTO();
    	}
    	
    	refComPrefDgtDTO.setRefComPrefDgtId(refComPrefDgt.getRefComPrefDgtId());
    	refComPrefDgtDTO.setDomain(RefComPrefDomainTransform.bo2DtoLight(refComPrefDgt.getDomain()));
    	refComPrefDgtDTO.setGroupType(RefComPrefGTypeTransform.bo2DtoLight(refComPrefDgt.getGroupType()));
    	refComPrefDgtDTO.setType(RefComPrefTypeTransform.bo2DtoLight(refComPrefDgt.getType()));
    	refComPrefDgtDTO.setDescription(refComPrefDgt.getDescription());
    	refComPrefDgtDTO.setDateCreation(refComPrefDgt.getDateCreation());
    	refComPrefDgtDTO.setDateModification(refComPrefDgt.getDateModification());
    	refComPrefDgtDTO.setSignatureCreation(refComPrefDgt.getSignatureCreation());
    	refComPrefDgtDTO.setSignatureModification(refComPrefDgt.getSignatureModification());
    	refComPrefDgtDTO.setSiteCreation(refComPrefDgt.getSiteCreation());
    	refComPrefDgtDTO.setSiteModification(refComPrefDgt.getSiteModification());
    	
    	if (refComPrefDgt.getRefComPrefMls() != null && !refComPrefDgt.getRefComPrefMls().isEmpty()) {
    		List<RefComPrefMlDTO> listRefComPrefMlDTO = new ArrayList<>();
    		for (RefComPrefMl refComPrefMl : refComPrefDgt.getRefComPrefMls()) {
    			RefComPrefMlDTO refComPrefMlDTO = new RefComPrefMlDTO();
    			RefComPrefMlTransform.bo2Dto(refComPrefMl,refComPrefMlDTO);
    			refComPrefMlDTO.setRefComPrefDgt(refComPrefDgtDTO);
    			listRefComPrefMlDTO.add(refComPrefMlDTO);
    		}
    		refComPrefDgtDTO.setRefComPrefMls(listRefComPrefMlDTO);   		
    	} else {
    		refComPrefDgtDTO.setRefComPrefMls(new ArrayList<RefComPrefMlDTO>());
    	}	
    }

    /*public static RefComPrefDgtDTO bo2DtoWithLinkedData(RefComPrefDgt refComPrefDgt, RefComPrefDgtDTO refComPrefDgtDTO) throws JrafDomainException {
    	
    	if (refComPrefDgt == null) {
    		return null;
    	}
    	
    	refComPrefDgtDTO.setRefComPrefDgtId(refComPrefDgt.getRefComPrefDgtId());
    	refComPrefDgtDTO.setDomain(RefComPrefDomainTransform.bo2DtoLight(refComPrefDgt.getDomain()));
    	refComPrefDgtDTO.setGroupType(RefComPrefGTypeTransform.bo2DtoLight(refComPrefDgt.getGroupType()));
    	refComPrefDgtDTO.setType(RefComPrefTypeTransform.bo2DtoLight(refComPrefDgt.getType()));
    	refComPrefDgtDTO.setDescription(refComPrefDgt.getDescription());
    	refComPrefDgtDTO.setDateCreation(refComPrefDgt.getDateCreation());
    	refComPrefDgtDTO.setDateModification(refComPrefDgt.getDateModification());
    	refComPrefDgtDTO.setSignatureCreation(refComPrefDgt.getSignatureCreation());
    	refComPrefDgtDTO.setSignatureModification(refComPrefDgt.getSignatureModification());
    	refComPrefDgtDTO.setSiteCreation(refComPrefDgt.getSiteCreation());
    	refComPrefDgtDTO.setSiteModification(refComPrefDgt.getSiteModification());
    	
    	if (refComPrefDgt.getRefComPrefMls() != null && !refComPrefDgt.getRefComPrefMls().isEmpty()) {
    		List<RefComPrefMlDTO> listRefComPrefMlDTO = new ArrayList<>();
    		for (RefComPrefMl refComPrefMl : refComPrefDgt.getRefComPrefMls()) {
    			RefComPrefMlDTO refComPrefMlDTO = new RefComPrefMlDTO();
    			RefComPrefMlTransform.bo2Dto(refComPrefMl,refComPrefMlDTO);
    			refComPrefMlDTO.setRefComPrefDgt(refComPrefDgtDTO);
    			listRefComPrefMlDTO.add(refComPrefMlDTO);
    		}
    		refComPrefDgtDTO.setRefComPrefMls(listRefComPrefMlDTO);   		
    	} else {
    		refComPrefDgtDTO.setRefComPrefMls(new ArrayList<RefComPrefMlDTO>());
    	}
    	
    	return refComPrefDgtDTO; 	
    }*/
    
    public static void updateDto(RefComPrefDgtDTO refComPrefDgtDTO, RefComPrefDgtDTO refComPrefDgtDTOFromDB) { 	
    	
    	refComPrefDgtDTOFromDB.setRefComPrefDgtId(refComPrefDgtDTO.getRefComPrefDgtId());
    	refComPrefDgtDTOFromDB.setDomain(refComPrefDgtDTO.getDomain());
    	refComPrefDgtDTOFromDB.setGroupType(refComPrefDgtDTO.getGroupType());
    	refComPrefDgtDTOFromDB.setType(refComPrefDgtDTO.getType());
    	refComPrefDgtDTOFromDB.setDescription(refComPrefDgtDTO.getDescription());
    	refComPrefDgtDTOFromDB.setDateModification(refComPrefDgtDTO.getDateModification());
    	refComPrefDgtDTOFromDB.setSignatureModification(refComPrefDgtDTO.getSignatureModification());
    	refComPrefDgtDTOFromDB.setSiteModification(refComPrefDgtDTO.getSiteModification());
    }
}

