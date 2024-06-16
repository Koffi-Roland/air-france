package com.airfrance.repind.dto.reference;

/*PROTECTED REGION ID(_XUgy4BeIEeKJFbgRY_ODIg i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.reference.Error;

/*PROTECTED REGION END*/

/**
 * <p>Title : ErrorTransform.java</p>
 * transformation bo <-> dto pour un(e) Error
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class ErrorTransform {

    /*PROTECTED REGION ID(_XUgy4BeIEeKJFbgRY_ODIg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * Constructeur prive
     */
    private ErrorTransform() {
    }
    /**
     * dto -> bo pour une Error
     * @param errorDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Error dto2BoLight(ErrorDTO errorDTO) throws JrafDomainException {
        // instanciation du BO
        Error error = new Error();
        dto2BoLight(errorDTO, error);

        // on retourne le BO
        return error;
    }

    /**
     * dto -> bo pour une error
     * @param errorDTO dto
     * @param error bo
     */
    public static void dto2BoLight(ErrorDTO errorDTO, Error error) {
        // property of ErrorDTO
        error.setErrorCode(errorDTO.getErrorCode());
        error.setLabelFR(errorDTO.getLabelFR());
        error.setLabelUK(errorDTO.getLabelUK());
    }

    /**
     * bo -> dto pour une error
     * @param pError bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static ErrorDTO bo2DtoLight(Error pError) throws JrafDomainException {
        // instanciation du DTO
        ErrorDTO errorDTO = new ErrorDTO();
        bo2DtoLight(pError, errorDTO);
        // on retourne le dto
        return errorDTO;
    }


    /**
     * Transforme un business object en DTO.
     * Methode dite light.
     * @param error bo
     * @param errorDTO dto
     */
    public static void bo2DtoLight(
        Error error,
        ErrorDTO errorDTO) {


        // simple properties
        errorDTO.setErrorCode(error.getErrorCode());
        errorDTO.setLabelFR(error.getLabelFR());
        errorDTO.setLabelUK(error.getLabelUK());


    }
    
    /*PROTECTED REGION ID(_XUgy4BeIEeKJFbgRY_ODIg u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

