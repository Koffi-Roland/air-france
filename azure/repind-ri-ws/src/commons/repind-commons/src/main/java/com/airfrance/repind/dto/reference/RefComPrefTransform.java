package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_Y7gEIDSeEeaR_YJoHRGtPg i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefComPref;
import com.airfrance.repind.entity.reference.RefComPrefMedia;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefComPrefTransform.java</p>
 * transformation bo <-> dto pour un(e) RefComPref
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefComPrefTransform {

    /*PROTECTED REGION ID(_Y7gEIDSeEeaR_YJoHRGtPg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefComPrefTransform() {
    }
    /**
     * dto -> bo for a RefComPref
     * @param refComPrefDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefComPref dto2BoLight(RefComPrefDTO refComPrefDTO) throws JrafDomainException {
        // instanciation du BO
        RefComPref refComPref = new RefComPref();
        dto2BoLight(refComPrefDTO, refComPref);

        // on retourne le BO
        return refComPref;
    }

    /**
     * dto -> bo for a refComPref
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refComPrefDTO dto
     * @param refComPref bo
     */
    public static void dto2BoLight(RefComPrefDTO refComPrefDTO, RefComPref refComPref) {
    
        /*PROTECTED REGION ID(dto2BoLight_Y7gEIDSeEeaR_YJoHRGtPg) ENABLED START*/
        
        dto2BoLightImpl(refComPrefDTO,refComPref);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refComPref
     * @param refComPrefDTO dto
     * @param refComPref bo
     */
    private static void dto2BoLightImpl(RefComPrefDTO refComPrefDTO, RefComPref refComPref){
    
        // property of RefComPrefDTO
        refComPref.setRefComprefId(refComPrefDTO.getRefComprefId());
        refComPref.setDescription(refComPrefDTO.getDescription());
        refComPref.setMandatoryOptin(refComPrefDTO.getMandatoryOptin());
        refComPref.setMarket(refComPrefDTO.getMarket());
        refComPref.setDefaultLanguage1(refComPrefDTO.getDefaultLanguage1());
        refComPref.setDefaultLanguage2(refComPrefDTO.getDefaultLanguage2());
        refComPref.setDefaultLanguage3(refComPrefDTO.getDefaultLanguage3());
        refComPref.setDefaultLanguage4(refComPrefDTO.getDefaultLanguage4());
        refComPref.setDefaultLanguage5(refComPrefDTO.getDefaultLanguage5());
        refComPref.setDefaultLanguage6(refComPrefDTO.getDefaultLanguage6());
        refComPref.setDefaultLanguage7(refComPrefDTO.getDefaultLanguage7());
        refComPref.setDefaultLanguage8(refComPrefDTO.getDefaultLanguage8());
        refComPref.setDefaultLanguage9(refComPrefDTO.getDefaultLanguage9());
        refComPref.setDefaultLanguage10(refComPrefDTO.getDefaultLanguage10());
        refComPref.setFieldA(refComPrefDTO.getFieldA());
        refComPref.setFieldN(refComPrefDTO.getFieldN());
        refComPref.setFieldT(refComPrefDTO.getFieldT());
        refComPref.setSignatureModification(refComPrefDTO.getSignatureModification());
        refComPref.setSiteModification(refComPrefDTO.getSiteModification());
        refComPref.setDateModification(refComPrefDTO.getDateModification());
        refComPref.setSignatureCreation(refComPrefDTO.getSignatureCreation());
        refComPref.setSiteCreation(refComPrefDTO.getSiteCreation());
        refComPref.setDateCreation(refComPrefDTO.getDateCreation());
    
    }

    /**
     * bo -> dto for a refComPref
     * @param pRefComPref bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefComPrefDTO bo2DtoLight(RefComPref pRefComPref) throws JrafDomainException {
        // instanciation du DTO
        RefComPrefDTO refComPrefDTO = new RefComPrefDTO();
        bo2DtoLight(pRefComPref, refComPrefDTO);
        // on retourne le dto
        return refComPrefDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refComPref bo
     * @param refComPrefDTO dto
     */
    public static void bo2DtoLight(
        RefComPref refComPref,
        RefComPrefDTO refComPrefDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_Y7gEIDSeEeaR_YJoHRGtPg) ENABLED START*/

        bo2DtoLightImpl(refComPref, refComPrefDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refComPref bo
     * @param refComPrefDTO dto
     */
    private static void bo2DtoLightImpl(RefComPref refComPref,
        RefComPrefDTO refComPrefDTO){
    

        // simple properties
        refComPrefDTO.setRefComprefId(refComPref.getRefComprefId());
        refComPrefDTO.setDescription(refComPref.getDescription());
        refComPrefDTO.setMandatoryOptin(refComPref.getMandatoryOptin());
        refComPrefDTO.setMarket(refComPref.getMarket());
        refComPrefDTO.setDefaultLanguage1(refComPref.getDefaultLanguage1());
        refComPrefDTO.setDefaultLanguage2(refComPref.getDefaultLanguage2());
        refComPrefDTO.setDefaultLanguage3(refComPref.getDefaultLanguage3());
        refComPrefDTO.setDefaultLanguage4(refComPref.getDefaultLanguage4());
        refComPrefDTO.setDefaultLanguage5(refComPref.getDefaultLanguage5());
        refComPrefDTO.setDefaultLanguage6(refComPref.getDefaultLanguage6());
        refComPrefDTO.setDefaultLanguage7(refComPref.getDefaultLanguage7());
        refComPrefDTO.setDefaultLanguage8(refComPref.getDefaultLanguage8());
        refComPrefDTO.setDefaultLanguage9(refComPref.getDefaultLanguage9());
        refComPrefDTO.setDefaultLanguage10(refComPref.getDefaultLanguage10());
        refComPrefDTO.setFieldA(refComPref.getFieldA());
        refComPrefDTO.setFieldN(refComPref.getFieldN());
        refComPrefDTO.setFieldT(refComPref.getFieldT());
        refComPrefDTO.setSignatureModification(refComPref.getSignatureModification());
        refComPrefDTO.setSiteModification(refComPref.getSiteModification());
        refComPrefDTO.setDateModification(refComPref.getDateModification());
        refComPrefDTO.setSignatureCreation(refComPref.getSignatureCreation());
        refComPrefDTO.setSiteCreation(refComPref.getSiteCreation());
        refComPrefDTO.setDateCreation(refComPref.getDateCreation());
    
    }
    
    /*PROTECTED REGION ID(_Y7gEIDSeEeaR_YJoHRGtPg u m - Tr) ENABLED START*/
    public static RefComPrefDTO bo2Dto(RefComPref refComPref) throws JrafDomainException {
    	 	// instanciation du DTO
    		RefComPrefDTO refComPrefDTO = new RefComPrefDTO();
    		bo2Dto(refComPref, refComPrefDTO);
    		// on retourne le dto
			return refComPrefDTO;
        }
    
    public static void bo2Dto(
            RefComPref refComPref,
            RefComPrefDTO refComPrefDTO) throws JrafDomainException {

            bo2DtoImpl(refComPref, refComPrefDTO);


        }
    
    private static void bo2DtoImpl(RefComPref refComPref,
            RefComPrefDTO refComPrefDTO) throws JrafDomainException{
        

            // simple properties
            refComPrefDTO.setRefComprefId(refComPref.getRefComprefId());
            refComPrefDTO.setDomain(RefComPrefDomainTransform.bo2DtoLight(refComPref.getDomain()));
            refComPrefDTO.setComGroupeType(RefComPrefGTypeTransform.bo2DtoLight(refComPref.getComGroupeType()));
            refComPrefDTO.setComType(RefComPrefTypeTransform.bo2DtoLight(refComPref.getComType()));
            refComPrefDTO.setDescription(refComPref.getDescription());
            refComPrefDTO.setMandatoryOptin(refComPref.getMandatoryOptin());
            refComPrefDTO.setMarket(refComPref.getMarket());
            refComPrefDTO.setDefaultLanguage1(refComPref.getDefaultLanguage1());
            refComPrefDTO.setDefaultLanguage2(refComPref.getDefaultLanguage2());
            refComPrefDTO.setDefaultLanguage3(refComPref.getDefaultLanguage3());
            refComPrefDTO.setDefaultLanguage4(refComPref.getDefaultLanguage4());
            refComPrefDTO.setDefaultLanguage5(refComPref.getDefaultLanguage5());
            refComPrefDTO.setDefaultLanguage6(refComPref.getDefaultLanguage6());
            refComPrefDTO.setDefaultLanguage7(refComPref.getDefaultLanguage7());
            refComPrefDTO.setDefaultLanguage8(refComPref.getDefaultLanguage8());
            refComPrefDTO.setDefaultLanguage9(refComPref.getDefaultLanguage9());
            refComPrefDTO.setDefaultLanguage10(refComPref.getDefaultLanguage10());
            refComPrefDTO.setFieldA(refComPref.getFieldA());
            refComPrefDTO.setFieldN(refComPref.getFieldN());
            refComPrefDTO.setFieldT(refComPref.getFieldT());
            refComPrefDTO.setMedia(RefComPrefMediaTransform.bo2DtoLight(
            		( refComPref.getMedia()==null ? new RefComPrefMedia() : refComPref.getMedia() )));
        
        }
    /*PROTECTED REGION END*/
}

