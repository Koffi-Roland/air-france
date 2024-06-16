package com.afklm.rigui.dto.reference;

/*PROTECTED REGION ID(_1QsU4DSaEeaR_YJoHRGtPg i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.reference.RefComPrefMedia;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefComPrefMediaTransform.java</p>
 * transformation bo <-> dto pour un(e) RefComPrefMedia
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefComPrefMediaTransform {

    /*PROTECTED REGION ID(_1QsU4DSaEeaR_YJoHRGtPg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefComPrefMediaTransform() {
    }
    /**
     * dto -> bo for a RefComPrefMedia
     * @param refComPrefMediaDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefComPrefMedia dto2BoLight(RefComPrefMediaDTO refComPrefMediaDTO) throws JrafDomainException {
        // instanciation du BO
        RefComPrefMedia refComPrefMedia = new RefComPrefMedia();
        dto2BoLight(refComPrefMediaDTO, refComPrefMedia);

        // on retourne le BO
        return refComPrefMedia;
    }

    /**
     * dto -> bo for a refComPrefMedia
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refComPrefMediaDTO dto
     * @param refComPrefMedia bo
     */
    public static void dto2BoLight(RefComPrefMediaDTO refComPrefMediaDTO, RefComPrefMedia refComPrefMedia) {
    
        /*PROTECTED REGION ID(dto2BoLight_1QsU4DSaEeaR_YJoHRGtPg) ENABLED START*/
        
        dto2BoLightImpl(refComPrefMediaDTO,refComPrefMedia);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refComPrefMedia
     * @param refComPrefMediaDTO dto
     * @param refComPrefMedia bo
     */
    private static void dto2BoLightImpl(RefComPrefMediaDTO refComPrefMediaDTO, RefComPrefMedia refComPrefMedia){
    
        // property of RefComPrefMediaDTO
        refComPrefMedia.setCodeMedia(refComPrefMediaDTO.getCodeMedia());
        refComPrefMedia.setLibelleMedia(refComPrefMediaDTO.getLibelleMedia());
        refComPrefMedia.setLibelleMediaEN(refComPrefMediaDTO.getLibelleMediaEN());
        refComPrefMedia.setSignatureModification(refComPrefMediaDTO.getSignatureModification());
        refComPrefMedia.setSiteModification(refComPrefMediaDTO.getSiteModification());
        refComPrefMedia.setDateModification(refComPrefMediaDTO.getDateModification());
        refComPrefMedia.setSignatureCreation(refComPrefMediaDTO.getSignatureCreation());
        refComPrefMedia.setSiteCreation(refComPrefMediaDTO.getSiteCreation());
        refComPrefMedia.setDateCreation(refComPrefMediaDTO.getDateCreation());
    
    }

    /**
     * bo -> dto for a refComPrefMedia
     * @param pRefComPrefMedia bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefComPrefMediaDTO bo2DtoLight(RefComPrefMedia pRefComPrefMedia) throws JrafDomainException {
        // instanciation du DTO
        RefComPrefMediaDTO refComPrefMediaDTO = new RefComPrefMediaDTO();
        bo2DtoLight(pRefComPrefMedia, refComPrefMediaDTO);
        // on retourne le dto
        return refComPrefMediaDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refComPrefMedia bo
     * @param refComPrefMediaDTO dto
     */
    public static void bo2DtoLight(
        RefComPrefMedia refComPrefMedia,
        RefComPrefMediaDTO refComPrefMediaDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_1QsU4DSaEeaR_YJoHRGtPg) ENABLED START*/

        bo2DtoLightImpl(refComPrefMedia, refComPrefMediaDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refComPrefMedia bo
     * @param refComPrefMediaDTO dto
     */
    private static void bo2DtoLightImpl(RefComPrefMedia refComPrefMedia,
        RefComPrefMediaDTO refComPrefMediaDTO){
    

        // simple properties
        refComPrefMediaDTO.setCodeMedia(refComPrefMedia.getCodeMedia());
        refComPrefMediaDTO.setLibelleMedia(refComPrefMedia.getLibelleMedia());
        refComPrefMediaDTO.setLibelleMediaEN(refComPrefMedia.getLibelleMediaEN());
        refComPrefMediaDTO.setSignatureModification(refComPrefMedia.getSignatureModification());
        refComPrefMediaDTO.setSiteModification(refComPrefMedia.getSiteModification());
        refComPrefMediaDTO.setDateModification(refComPrefMedia.getDateModification());
        refComPrefMediaDTO.setSignatureCreation(refComPrefMedia.getSignatureCreation());
        refComPrefMediaDTO.setSiteCreation(refComPrefMedia.getSiteCreation());
        refComPrefMediaDTO.setDateCreation(refComPrefMedia.getDateCreation());
    
    }
    
    /*PROTECTED REGION ID(_1QsU4DSaEeaR_YJoHRGtPg u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

