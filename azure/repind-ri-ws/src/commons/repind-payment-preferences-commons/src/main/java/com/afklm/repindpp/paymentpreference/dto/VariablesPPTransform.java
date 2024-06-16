package com.afklm.repindpp.paymentpreference.dto;

import com.afklm.repindpp.paymentpreference.entity.EnvVarPp;
import com.afklm.repindpp.paymentpreference.entity.EnvVarPpId;
import com.airfrance.ref.exception.jraf.JrafDomainException;
public final class VariablesPPTransform {

    private VariablesPPTransform() {
    }
    /**
     * dto -> bo for a VariablesPP
     * @param variablesPPDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static EnvVarPp dto2BoLight(VariablesPPDTO variablesPPDTO) throws JrafDomainException {
        // instanciation du BO
    	EnvVarPp variablesPP = new EnvVarPp();
        dto2BoLight(variablesPPDTO, variablesPP);

        // on retourne le BO
        return variablesPP;
    }

    /**
     * dto -> bo for a variablesPP
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param variablesPPDTO dto
     * @param variablesPP bo
     */
    public static void dto2BoLight(VariablesPPDTO variablesPPDTO, EnvVarPp variablesPP) {
    
        /*PROTECTED REGION ID(dto2BoLight_raxtgFkoEeS7ba9Foqodfw) ENABLED START*/
        
        dto2BoLightImpl(variablesPPDTO,variablesPP);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a variablesPP
     * @param variablesPPDTO dto
     * @param variablesPP bo
     */
    private static void dto2BoLightImpl(VariablesPPDTO variablesPPDTO, EnvVarPp variablesPP){
         variablesPP.setId(new EnvVarPpId(variablesPPDTO.getEnvKeyPP(),variablesPPDTO.getEnvValuePP()));
    }

    /**
     * bo -> dto for a variablesPP
     * @param pVariablesPP bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static VariablesPPDTO bo2DtoLight(EnvVarPp pVariablesPP) throws JrafDomainException {
        // instanciation du DTO
        VariablesPPDTO variablesPPDTO = new VariablesPPDTO();
        bo2DtoLight(pVariablesPP, variablesPPDTO);
        // on retourne le dto
        return variablesPPDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param variablesPP bo
     * @param variablesPPDTO dto
     */
    public static void bo2DtoLight(
    		EnvVarPp variablesPP,
        VariablesPPDTO variablesPPDTO) {
        bo2DtoLightImpl(variablesPP, variablesPPDTO);
    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param variablesPP bo
     * @param variablesPPDTO dto
     */
    private static void bo2DtoLightImpl(EnvVarPp variablesPP,
        VariablesPPDTO variablesPPDTO){
        variablesPPDTO.setEnvKeyPP(variablesPP.getId().getEnvKeyPp());
        variablesPPDTO.setEnvValuePP(variablesPP.getId().getEnvValuePp());
    
    }
}

