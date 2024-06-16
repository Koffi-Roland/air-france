package com.airfrance.repind.dto.reference;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefComPrefMl;

public final class RefComPrefMlTransform {

    /**
     * private constructor
     */
    private RefComPrefMlTransform() {
    }
    
    public static RefComPrefMl dto2Bo(RefComPrefMlDTO refComPrefMlDTO) throws JrafDomainException {
    	
    	RefComPrefMl refComPrefMl = new RefComPrefMl();
    	
    	refComPrefMl.setDateCreation(refComPrefMlDTO.getDateCreation());
    	refComPrefMl.setDateModification(refComPrefMlDTO.getDateModification());
    	refComPrefMl.setDefaultLanguage1(refComPrefMlDTO.getDefaultLanguage1());
    	refComPrefMl.setDefaultLanguage10(refComPrefMlDTO.getDefaultLanguage10());
    	refComPrefMl.setDefaultLanguage2(refComPrefMlDTO.getDefaultLanguage2());
    	refComPrefMl.setDefaultLanguage3(refComPrefMlDTO.getDefaultLanguage3());
    	refComPrefMl.setDefaultLanguage4(refComPrefMlDTO.getDefaultLanguage4());
    	refComPrefMl.setDefaultLanguage5(refComPrefMlDTO.getDefaultLanguage5());
    	refComPrefMl.setDefaultLanguage6(refComPrefMlDTO.getDefaultLanguage6());
    	refComPrefMl.setDefaultLanguage7(refComPrefMlDTO.getDefaultLanguage7());
    	refComPrefMl.setDefaultLanguage8(refComPrefMlDTO.getDefaultLanguage8());
    	refComPrefMl.setDefaultLanguage9(refComPrefMlDTO.getDefaultLanguage9());
    	refComPrefMl.setFieldA(refComPrefMlDTO.getFieldA());
    	refComPrefMl.setFieldN(refComPrefMlDTO.getFieldN());
    	refComPrefMl.setFieldT(refComPrefMlDTO.getFieldT());
    	refComPrefMl.setMandatoryOption(refComPrefMlDTO.getMandatoryOption());
    	refComPrefMl.setMarket(refComPrefMlDTO.getMarket());
    	refComPrefMl.setMedia(refComPrefMlDTO.getMedia());
    	refComPrefMl.setRefComPrefMlId(refComPrefMlDTO.getRefComPrefMlId());
    	refComPrefMl.setSignatureCreation(refComPrefMlDTO.getSignatureCreation());
    	refComPrefMl.setSignatureModification(refComPrefMlDTO.getSignatureModification());
    	refComPrefMl.setSiteCreation(refComPrefMlDTO.getSiteCreation());
    	refComPrefMl.setSiteModification(refComPrefMlDTO.getSiteModification());
    	refComPrefMl.setRefComPrefDgt(RefComPrefDgtTransform.dto2Bo(refComPrefMlDTO.getRefComPrefDgt()));
    	    	
    	return refComPrefMl;
    }
    
    public static RefComPrefMlDTO bo2Dto(RefComPrefMl refComPrefMl) throws JrafDomainException {
    	
    	RefComPrefMlDTO refComPrefMlDTO = new RefComPrefMlDTO();
    	bo2Dto(refComPrefMl, refComPrefMlDTO);
    	
    	return refComPrefMlDTO;    	
    }
    
    public static void bo2Dto(RefComPrefMl refComPrefMl, RefComPrefMlDTO refComPrefMlDTO) throws JrafDomainException {
    	
		if (refComPrefMlDTO == null) {
			refComPrefMlDTO = new RefComPrefMlDTO();
		}
        	
    	refComPrefMlDTO.setDateCreation(refComPrefMl.getDateCreation());
    	refComPrefMlDTO.setDateModification(refComPrefMl.getDateModification());
    	refComPrefMlDTO.setDefaultLanguage1(refComPrefMl.getDefaultLanguage1());
    	refComPrefMlDTO.setDefaultLanguage10(refComPrefMl.getDefaultLanguage10());
    	refComPrefMlDTO.setDefaultLanguage2(refComPrefMl.getDefaultLanguage2());
    	refComPrefMlDTO.setDefaultLanguage3(refComPrefMl.getDefaultLanguage3());
    	refComPrefMlDTO.setDefaultLanguage4(refComPrefMl.getDefaultLanguage4());
    	refComPrefMlDTO.setDefaultLanguage5(refComPrefMl.getDefaultLanguage5());
    	refComPrefMlDTO.setDefaultLanguage6(refComPrefMl.getDefaultLanguage6());
    	refComPrefMlDTO.setDefaultLanguage7(refComPrefMl.getDefaultLanguage7());
    	refComPrefMlDTO.setDefaultLanguage8(refComPrefMl.getDefaultLanguage8());
    	refComPrefMlDTO.setDefaultLanguage9(refComPrefMl.getDefaultLanguage9());
    	refComPrefMlDTO.setFieldA(refComPrefMl.getFieldA());
    	refComPrefMlDTO.setFieldN(refComPrefMl.getFieldN());
    	refComPrefMlDTO.setFieldT(refComPrefMl.getFieldT());
    	refComPrefMlDTO.setMandatoryOption(refComPrefMl.getMandatoryOption());
    	refComPrefMlDTO.setMarket(refComPrefMl.getMarket());
    	refComPrefMlDTO.setMedia(refComPrefMl.getMedia());
    	refComPrefMlDTO.setRefComPrefMlId(refComPrefMl.getRefComPrefMlId());
    	refComPrefMlDTO.setSignatureCreation(refComPrefMl.getSignatureCreation());
    	refComPrefMlDTO.setSignatureModification(refComPrefMl.getSignatureModification());
    	refComPrefMlDTO.setSiteCreation(refComPrefMl.getSiteCreation());
    	refComPrefMlDTO.setSiteModification(refComPrefMl.getSiteModification());
    	refComPrefMlDTO.setRefComPrefDgt(RefComPrefDgtTransform.bo2Dto(refComPrefMl.getRefComPrefDgt()));	
    }
    
    public static void updateDto(RefComPrefMlDTO refComPrefMlDTO, RefComPrefMlDTO refComPrefMlDTOFromDB) {
            	    	
    	refComPrefMlDTOFromDB.setDateModification(refComPrefMlDTO.getDateModification());
    	refComPrefMlDTOFromDB.setDefaultLanguage1(refComPrefMlDTO.getDefaultLanguage1());
    	refComPrefMlDTOFromDB.setDefaultLanguage10(refComPrefMlDTO.getDefaultLanguage10());
    	refComPrefMlDTOFromDB.setDefaultLanguage2(refComPrefMlDTO.getDefaultLanguage2());
    	refComPrefMlDTOFromDB.setDefaultLanguage3(refComPrefMlDTO.getDefaultLanguage3());
    	refComPrefMlDTOFromDB.setDefaultLanguage4(refComPrefMlDTO.getDefaultLanguage4());
    	refComPrefMlDTOFromDB.setDefaultLanguage5(refComPrefMlDTO.getDefaultLanguage5());
    	refComPrefMlDTOFromDB.setDefaultLanguage6(refComPrefMlDTO.getDefaultLanguage6());
    	refComPrefMlDTOFromDB.setDefaultLanguage7(refComPrefMlDTO.getDefaultLanguage7());
    	refComPrefMlDTOFromDB.setDefaultLanguage8(refComPrefMlDTO.getDefaultLanguage8());
    	refComPrefMlDTOFromDB.setDefaultLanguage9(refComPrefMlDTO.getDefaultLanguage9());
    	refComPrefMlDTOFromDB.setFieldA(refComPrefMlDTO.getFieldA());
    	refComPrefMlDTOFromDB.setFieldN(refComPrefMlDTO.getFieldN());
    	refComPrefMlDTOFromDB.setFieldT(refComPrefMlDTO.getFieldT());
    	refComPrefMlDTOFromDB.setMandatoryOption(refComPrefMlDTO.getMandatoryOption());
    	refComPrefMlDTOFromDB.setMarket(refComPrefMlDTO.getMarket());
    	refComPrefMlDTOFromDB.setMedia(refComPrefMlDTO.getMedia());
    	refComPrefMlDTOFromDB.setRefComPrefMlId(refComPrefMlDTO.getRefComPrefMlId());
    	refComPrefMlDTOFromDB.setSignatureModification(refComPrefMlDTO.getSignatureModification());
    	refComPrefMlDTOFromDB.setSiteModification(refComPrefMlDTO.getSiteModification());
    }
}

