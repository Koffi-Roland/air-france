package com.airfrance.repind.dto.individu;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.individu.Alert;
import com.airfrance.repind.entity.individu.AlertData;

import java.util.HashSet;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : AlertDataTransform.java</p>
 * transformation bo <-> dto pour un(e) AlertData
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class AlertDataTransform {

    /*PROTECTED REGION ID(_sjMsoFiyEea7Yu0D-4113Q u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private AlertDataTransform() {
    }
    /**
     * dto -> bo for a AlertData
     * @param alertDataDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static AlertData dto2BoLight(AlertDataDTO alertDataDTO) throws JrafDomainException {
        // instanciation du BO
        AlertData alertData = new AlertData();
        dto2BoLight(alertDataDTO, alertData);

        // on retourne le BO
        return alertData;
    }

    /**
     * dto -> bo for a alertData
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param alertDataDTO dto
     * @param alertData bo
     */
    public static void dto2BoLight(AlertDataDTO alertDataDTO, AlertData alertData) {
    
        /*PROTECTED REGION ID(dto2BoLight_sjMsoFiyEea7Yu0D-4113Q) ENABLED START*/
        
        dto2BoLightImpl(alertDataDTO,alertData);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a alertData
     * @param alertDataDTO dto
     * @param alertData bo
     */
    private static void dto2BoLightImpl(AlertDataDTO alertDataDTO, AlertData alertData){
    
        // property of AlertDataDTO
        alertData.setAlertDataId(alertDataDTO.getAlertDataId());
        alertData.setKey(alertDataDTO.getKey());
        alertData.setValue(alertDataDTO.getValue());
    
    }

    /**
     * bo -> dto for a alertData
     * @param pAlertData bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static AlertDataDTO bo2DtoLight(AlertData pAlertData) throws JrafDomainException {
        // instanciation du DTO
        AlertDataDTO alertDataDTO = new AlertDataDTO();
        bo2DtoLight(pAlertData, alertDataDTO);
        // on retourne le dto
        return alertDataDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param alertData bo
     * @param alertDataDTO dto
     */
    public static void bo2DtoLight(
        AlertData alertData,
        AlertDataDTO alertDataDTO) {

        /*PROTECTED REGION ID(bo2DtoLight_sjMsoFiyEea7Yu0D-4113Q) ENABLED START*/

        bo2DtoLightImpl(alertData, alertDataDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param alertData bo
     * @param alertDataDTO dto
     */
    private static void bo2DtoLightImpl(AlertData alertData,
        AlertDataDTO alertDataDTO){
    

        // simple properties
        alertDataDTO.setAlertDataId(alertData.getAlertDataId());
        alertDataDTO.setKey(alertData.getKey());
        alertDataDTO.setValue(alertData.getValue());
    
    }
    
    /*PROTECTED REGION ID(_sjMsoFiyEea7Yu0D-4113Q u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    public static Set<AlertData> dto2BoLight(Set<AlertDataDTO> alertDataDTOList, Alert alert) throws JrafDomainException {
  		Set<AlertData> alertDataSet = null;
  		if (alertDataDTOList != null && !alertDataDTOList.isEmpty()) {
  			alertDataSet = new HashSet<AlertData>();
  			for (AlertDataDTO alertDataDTOLoop : alertDataDTOList) {
  				AlertData alertData = AlertDataTransform.dto2BoLight(alertDataDTOLoop);
  				alertData.setAlert(alert);
  				alertDataSet.add(alertData);
  			}
  		}
      	return alertDataSet;
      }

      public static Set<AlertDataDTO> bo2DtoLight(Set<AlertData> alertDataSet) throws JrafDomainException {
      	Set<AlertDataDTO> alertDataDTOSet = null;
      	if (alertDataSet != null && !alertDataSet.isEmpty()) {
      		alertDataDTOSet = new HashSet<AlertDataDTO>();
      		for (AlertData alertDataLoop : alertDataSet) {
      			AlertDataDTO alertDataDTO = AlertDataTransform.bo2DtoLight(alertDataLoop);
      			alertDataDTOSet.add(alertDataDTO);
      		}
      	}
      	return alertDataDTOSet;
      }
      
    /*PROTECTED REGION END*/
}

