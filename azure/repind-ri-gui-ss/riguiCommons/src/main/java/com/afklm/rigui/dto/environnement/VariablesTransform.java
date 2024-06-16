package com.afklm.rigui.dto.environnement;

/*PROTECTED REGION ID(_dtMF_jVcEeGby45oHEwUrg i - Tr) ENABLED START*/

// add not generated imports here

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.entity.environnement.Variables;

/*PROTECTED REGION END*/

/**
 * <p>Title : VariablesTransform.java</p>
 * transformation bo <-> dto pour un(e) Variables
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class VariablesTransform {

    /*PROTECTED REGION ID(_dtMF_jVcEeGby45oHEwUrg u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * Constructeur prive
     */
    private VariablesTransform() {
    }
    /**
     * dto -> bo pour une Variables
     * @param variablesDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Variables dto2BoLight(VariablesDTO variablesDTO) throws JrafDomainException {
        // instanciation du BO
        Variables variables = new Variables();
        dto2BoLight(variablesDTO, variables);

        // on retourne le BO
        return variables;
    }

    /**
     * dto -> bo pour une variables
     * @param variablesDTO dto
     * @param variables bo
     */
    public static void dto2BoLight(VariablesDTO variablesDTO, Variables variables) {
        // property of VariablesDTO
        variables.setEnvKey(variablesDTO.getEnvKey());
        variables.setEnvValue(variablesDTO.getEnvValue());
    }

    /**
     * bo -> dto pour une variables
     * @param pVariables bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static VariablesDTO bo2DtoLight(Variables pVariables) throws JrafDomainException {
        // instanciation du DTO
        VariablesDTO variablesDTO = new VariablesDTO();
        bo2DtoLight(pVariables, variablesDTO);
        // on retourne le dto
        return variablesDTO;
    }


    /**
     * Transforme un business object en DTO.
     * Methode dite light.
     * @param variables bo
     * @param variablesDTO dto
     */
    public static void bo2DtoLight(
        Variables variables,
        VariablesDTO variablesDTO) {


        // simple properties
        variablesDTO.setEnvKey(variables.getEnvKey());
        variablesDTO.setEnvValue(variables.getEnvValue());


    }
    
    /*PROTECTED REGION ID(_dtMF_jVcEeGby45oHEwUrg u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}

