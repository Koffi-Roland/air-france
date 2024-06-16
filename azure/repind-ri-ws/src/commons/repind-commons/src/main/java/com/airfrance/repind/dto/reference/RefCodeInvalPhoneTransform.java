package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_Ub7wgE4iEeS-eLH--0fARw i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.RefCodeInvalPhone;

/*PROTECTED REGION END*/

/**
 * <p>Title : RefCodeInvalPhoneTransform.java</p>
 * transformation bo <-> dto pour un(e) RefCodeInvalPhone
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class RefCodeInvalPhoneTransform {

    /*PROTECTED REGION ID(_Ub7wgE4iEeS-eLH--0fARw u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private RefCodeInvalPhoneTransform() {
    }
    /**
     * dto -> bo for a RefCodeInvalPhone
     * @param refCodeInvalPhoneDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static RefCodeInvalPhone dto2BoLight(RefCodeInvalPhoneDTO refCodeInvalPhoneDTO) throws JrafDomainException {
        // instanciation du BO
        RefCodeInvalPhone refCodeInvalPhone = new RefCodeInvalPhone();
        dto2BoLight(refCodeInvalPhoneDTO, refCodeInvalPhone);

        // on retourne le BO
        return refCodeInvalPhone;
    }

    /**
     * dto -> bo for a refCodeInvalPhone
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refCodeInvalPhoneDTO dto
     * @param refCodeInvalPhone bo
     */
    public static void dto2BoLight(RefCodeInvalPhoneDTO refCodeInvalPhoneDTO, RefCodeInvalPhone refCodeInvalPhone) {
    
        /*PROTECTED REGION ID(dto2BoLight_Ub7wgE4iEeS-eLH--0fARw) ENABLED START*/
        
        dto2BoLightImpl(refCodeInvalPhoneDTO,refCodeInvalPhone);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a refCodeInvalPhone
     * @param refCodeInvalPhoneDTO dto
     * @param refCodeInvalPhone bo
     */
    private static void dto2BoLightImpl(RefCodeInvalPhoneDTO refCodeInvalPhoneDTO, RefCodeInvalPhone refCodeInvalPhone){
    
        // property of RefCodeInvalPhoneDTO
        refCodeInvalPhone.setId(refCodeInvalPhoneDTO.getId());
        refCodeInvalPhone.setCodeError(refCodeInvalPhoneDTO.getCodeError());
        refCodeInvalPhone.setDescription(refCodeInvalPhoneDTO.getDescription());
    
    }

    /**
     * bo -> dto for a refCodeInvalPhone
     * @param pRefCodeInvalPhone bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static RefCodeInvalPhoneDTO bo2DtoLight(RefCodeInvalPhone pRefCodeInvalPhone) throws JrafDomainException {
        // instanciation du DTO
        RefCodeInvalPhoneDTO refCodeInvalPhoneDTO = new RefCodeInvalPhoneDTO();
        bo2DtoLight(pRefCodeInvalPhone, refCodeInvalPhoneDTO);
        // on retourne le dto
        return refCodeInvalPhoneDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param refCodeInvalPhone bo
     * @param refCodeInvalPhoneDTO dto
     */
    public static void bo2DtoLight(
        RefCodeInvalPhone refCodeInvalPhone,
        RefCodeInvalPhoneDTO refCodeInvalPhoneDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_Ub7wgE4iEeS-eLH--0fARw) ENABLED START*/

        bo2DtoLightImpl(refCodeInvalPhone, refCodeInvalPhoneDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param refCodeInvalPhone bo
     * @param refCodeInvalPhoneDTO dto
     */
    private static void bo2DtoLightImpl(RefCodeInvalPhone refCodeInvalPhone,
        RefCodeInvalPhoneDTO refCodeInvalPhoneDTO){
    

        // simple properties
        refCodeInvalPhoneDTO.setId(refCodeInvalPhone.getId());
        refCodeInvalPhoneDTO.setCodeError(refCodeInvalPhone.getCodeError());
        refCodeInvalPhoneDTO.setDescription(refCodeInvalPhone.getDescription());
    
    }
    
    /*PROTECTED REGION ID(_Ub7wgE4iEeS-eLH--0fARw u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

