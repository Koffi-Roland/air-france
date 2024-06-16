package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_vNfwQHtPEeaSlc6Hkl1VQg i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefPreferenceKey;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefPreferenceKeyTransform.java</p>
 * transformation bo <-> dto pour un(e) RefPreferenceKey
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefPreferenceKeyTransform {

    /*PROTECTED REGION ID(_vNfwQHtPEeaSlc6Hkl1VQg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefPreferenceKeyTransform() {
    }
    /**
     * dto -> bo for a RefPreferenceKey
     * @param refPreferenceKeyDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefPreferenceKey dto2BoLight(RefPreferenceKeyDTO refPreferenceKeyDTO) throws JrafDomainException {
        // instanciation du BO
        RefPreferenceKey refPreferenceKey = new RefPreferenceKey();
        dto2BoLight(refPreferenceKeyDTO, refPreferenceKey);

        // on retourne le BO
        return refPreferenceKey;
    }

    /**
     * dto -> bo for a refPreferenceKey
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refPreferenceKeyDTO dto
     * @param refPreferenceKey bo
     */
    public static void dto2BoLight(RefPreferenceKeyDTO refPreferenceKeyDTO, RefPreferenceKey refPreferenceKey) {
    
        /*PROTECTED REGION ID(dto2BoLight_vNfwQHtPEeaSlc6Hkl1VQg) ENABLED START*/
        
        dto2BoLightImpl(refPreferenceKeyDTO,refPreferenceKey);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refPreferenceKey
     * @param refPreferenceKeyDTO dto
     * @param refPreferenceKey bo
     */
    private static void dto2BoLightImpl(RefPreferenceKeyDTO refPreferenceKeyDTO, RefPreferenceKey refPreferenceKey){
    
        // property of RefPreferenceKeyDTO
        refPreferenceKey.setCodeKey(refPreferenceKeyDTO.getCodeKey());
        refPreferenceKey.setLibelle(refPreferenceKeyDTO.getLibelle());
        refPreferenceKey.setLibelleEng(refPreferenceKeyDTO.getLibelleEng());
    
    }

    /**
     * bo -> dto for a refPreferenceKey
     * @param pRefPreferenceKey bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefPreferenceKeyDTO bo2DtoLight(RefPreferenceKey pRefPreferenceKey) throws JrafDomainException {
        // instanciation du DTO
        RefPreferenceKeyDTO refPreferenceKeyDTO = new RefPreferenceKeyDTO();
        bo2DtoLight(pRefPreferenceKey, refPreferenceKeyDTO);
        // on retourne le dto
        return refPreferenceKeyDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refPreferenceKey bo
     * @param refPreferenceKeyDTO dto
     */
    public static void bo2DtoLight(
        RefPreferenceKey refPreferenceKey,
        RefPreferenceKeyDTO refPreferenceKeyDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_vNfwQHtPEeaSlc6Hkl1VQg) ENABLED START*/

        bo2DtoLightImpl(refPreferenceKey, refPreferenceKeyDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refPreferenceKey bo
     * @param refPreferenceKeyDTO dto
     */
    private static void bo2DtoLightImpl(RefPreferenceKey refPreferenceKey,
        RefPreferenceKeyDTO refPreferenceKeyDTO){
    

        // simple properties
        refPreferenceKeyDTO.setCodeKey(refPreferenceKey.getCodeKey());
        refPreferenceKeyDTO.setLibelle(refPreferenceKey.getLibelle());
        refPreferenceKeyDTO.setLibelleEng(refPreferenceKey.getLibelleEng());
    
    }
    
    /*PROTECTED REGION ID(_vNfwQHtPEeaSlc6Hkl1VQg u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

