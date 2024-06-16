package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(__G-z4FiREea7Yu0D-4113Q i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.individu.Alert;
import com.airfrance.repind.entity.individu.AlertData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : AlertTransform.java</p>
 * transformation bo <-> dto pour un(e) Alert
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class AlertTransform {

    /*PROTECTED REGION ID(__G-z4FiREea7Yu0D-4113Q u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * private constructor
     */
    private AlertTransform() {
    }
    /**
     * dto -> bo for a Alert
     * @param alertDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static Alert dto2BoLight(AlertDTO alertDTO) throws JrafDomainException {
        // instanciation du BO
        Alert alert = new Alert();
        dto2BoLight(alertDTO, alert);

        // on retourne le BO
        return alert;
    }

    /**
     * dto -> bo for a alert
     * calls dto2BoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param alertDTO dto
     * @param alert bo
     */
    public static void dto2BoLight(AlertDTO alertDTO, Alert alert) {
    
        /*PROTECTED REGION ID(dto2BoLight__G-z4FiREea7Yu0D-4113Q) ENABLED START*/
        
        dto2BoLightImpl(alertDTO,alert);
        
        /*PROTECTED REGION END*/
    }
    
    /**
     * dto -> bo implementation for a alert
     * @param alertDTO dto
     * @param alert bo
     */
    private static void dto2BoLightImpl(AlertDTO alertDTO, Alert alert){
    
        // property of AlertDTO
        alert.setAlertId(alertDTO.getAlertId());
        alert.setSgin(alertDTO.getSgin());
        alert.setCreationDate(alertDTO.getCreationDate());
        alert.setCreationSignature(alertDTO.getCreationSignature());
        alert.setCreationSite(alertDTO.getCreationSite());
        alert.setModificationDate(alertDTO.getModificationDate());
        alert.setModificationSignature(alertDTO.getModificationSignature());
        alert.setModificationSite(alertDTO.getModificationSite());
        alert.setType(alertDTO.getType());
        alert.setOptIn(alertDTO.getOptIn());
    
    }

    /**
     * bo -> dto for a alert
     * @param pAlert bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static AlertDTO bo2DtoLight(Alert pAlert) throws JrafDomainException {
        // instanciation du DTO
        AlertDTO alertDTO = new AlertDTO();
        bo2DtoLight(pAlert, alertDTO);
        // on retourne le dto
        return alertDTO;
    }


    /**
     * Transform a business object to DTO.
     * "light method".
     * calls bo2DtoLightImpl in a protected region so the user can override this without
     * losing benefit of generation if attributes vary in future
     * @param alert bo
     * @param alertDTO dto
     */
    public static void bo2DtoLight(
        Alert alert,
        AlertDTO alertDTO) {

        /*PROTECTED REGION ID(bo2DtoLight__G-z4FiREea7Yu0D-4113Q) ENABLED START*/

        bo2DtoLightImpl(alert, alertDTO);

        /*PROTECTED REGION END*/

    }
    
    /**
     * Transform a business object to DTO. Implementation method
     * @param alert bo
     * @param alertDTO dto
     */
    private static void bo2DtoLightImpl(Alert alert,
        AlertDTO alertDTO){
    

        // simple properties
        alertDTO.setAlertId(alert.getAlertId());
        alertDTO.setSgin(alert.getSgin());
        alertDTO.setCreationDate(alert.getCreationDate());
        alertDTO.setCreationSignature(alert.getCreationSignature());
        alertDTO.setCreationSite(alert.getCreationSite());
        alertDTO.setModificationDate(alert.getModificationDate());
        alertDTO.setModificationSignature(alert.getModificationSignature());
        alertDTO.setModificationSite(alert.getModificationSite());
        alertDTO.setType(alert.getType());
        alertDTO.setOptIn(alert.getOptIn());
        
        if (alert.getAlertdata() != null && !alert.getAlertdata().isEmpty()) {
        	Set<AlertDataDTO> alertDataDTOList = new HashSet<AlertDataDTO>();
        	for (AlertData alertData : alert.getAlertdata()) {
        		AlertDataDTO alertDataDTO = new AlertDataDTO();
        		alertDataDTO.setKey(alertData.getKey());
        		alertDataDTO.setValue(alertData.getValue());
        		alertDataDTOList.add(alertDataDTO);
        	}
        	alertDTO.setAlertDataDTO(alertDataDTOList);
        }
    }
    
    /*PROTECTED REGION ID(__G-z4FiREea7Yu0D-4113Q u m - Tr) ENABLED START*/
    // add your custom methods here if necessary
    public static Set<Alert> dto2BoLight(Set<AlertDTO> listAlertDTO) throws JrafDomainException {
    	if (listAlertDTO != null) {
    		Set<Alert> alertSet = new HashSet<Alert>();
    		for (AlertDTO alertDTO : listAlertDTO) {
    			Alert alert = new Alert();
    			dto2BoLight(alertDTO, alert);
    			alert.setAlertdata(AlertDataTransform.dto2BoLight(alertDTO.getAlertDataDTO(), alert));
    			alertSet.add(alert);
    		}
    		return alertSet;
    	}
    	return null;
    }

    public static List<AlertDTO> bo2DtoLight(Set<Alert> list) throws JrafDomainException {
    	return bo2Dto(new ArrayList<Alert>(list));
    }
    
    public static List<AlertDTO> bo2Dto(List<Alert> alertList) throws JrafDomainException {
    	if (alertList != null) {
    		List<AlertDTO> listAlertDTO = new ArrayList<AlertDTO>();
    		for (Alert alert : alertList) {
    			AlertDTO alertDTO = new AlertDTO();
    			bo2DtoLight(alert, alertDTO);
    			alertDTO.setAlertDataDTO(AlertDataTransform.bo2DtoLight(alert.getAlertdata()));
    			listAlertDTO.add(alertDTO);
    		}
    		return listAlertDTO;
    	}
    	return null;
    	
    }
    
    public static Set<AlertDTO> bo2Dto(Set<Alert> list) throws JrafDomainException {
    	if (list != null) {
    		Set<AlertDTO> listAlertDTO = new HashSet<AlertDTO>();
    		for (Alert alert : list) {
    			AlertDTO alertDTO = new AlertDTO();
    			bo2DtoLight(alert, alertDTO);
    			alertDTO.setAlertDataDTO(AlertDataTransform.bo2DtoLight(alert.getAlertdata()));
    			listAlertDTO.add(alertDTO);
    		}
    		return listAlertDTO;
    	}
    	return null;
    }
    /*PROTECTED REGION END*/
}

