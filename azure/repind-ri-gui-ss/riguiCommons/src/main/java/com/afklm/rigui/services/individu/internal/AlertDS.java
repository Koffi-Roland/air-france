package com.afklm.rigui.services.individu.internal;


import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.exception.jraf.JrafDomainNoRollbackException;
import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;
import com.afklm.rigui.dao.individu.AlertDataRepository;
import com.afklm.rigui.dao.individu.AlertRepository;
import com.afklm.rigui.dto.individu.AlertDTO;
import com.afklm.rigui.dto.individu.AlertDataDTO;
import com.afklm.rigui.dto.individu.AlertDataTransform;
import com.afklm.rigui.dto.individu.AlertTransform;
import com.afklm.rigui.entity.individu.Alert;
import com.afklm.rigui.entity.individu.AlertData;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AlertDS {

    /** logger */
    private static final Log log = LogFactory.getLog(AlertDS.class);

	@Autowired
	protected AlertDataDS alertDataDS;
	

	@Autowired
	private AlertDataRepository alertDataRepository;

	/**
	 * @return the alertDataRepository
	 */
	public AlertDataRepository getAlertDataRepository() {
		return alertDataRepository;
	}

	/**
	 * @param alertDataRepository
	 *            the alertDataRepository to set
	 */
	public void setAlertDataRepository(AlertDataRepository alertDataRepository) {
		this.alertDataRepository = alertDataRepository;
	}

	/** main repository */
    @Autowired
	private AlertRepository alertRepository;
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(AlertDTO alertDTO) throws JrafDomainException {
        Alert alert = null;
        alert = AlertTransform.dto2BoLight(alertDTO);
		alertRepository.saveAndFlush(alert);
        alertDTO = updateAlertData(alertDTO, alert);
        AlertTransform.bo2DtoLight(alert, alertDTO);
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(AlertDTO dto) throws JrafDomainException {
        Alert alert = null;
        alert = AlertTransform.dto2BoLight(dto);
		alertRepository.deleteById(alert.getAlertId());
    }
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(Integer id) throws JrafDomainException {
		alertRepository.deleteById(id);
    }

    @Transactional(readOnly=true)
    public List<AlertDTO> findAll() throws JrafDomainException {
        List boFounds = null;
        List<AlertDTO> dtoFounds = null;
        AlertDTO dto = null;
        Alert alert = null;

		boFounds = alertRepository.findAll();
        if (boFounds != null) {
            dtoFounds = new ArrayList<AlertDTO>(boFounds.size());
            Iterator i = boFounds.iterator();
            while (i.hasNext()) {
                alert = (Alert) i.next();
                dto = AlertTransform.bo2DtoLight(alert);
                dtoFounds.add(dto);
            }
        }
        return dtoFounds;
    }

    @Transactional(readOnly=true)
    public Integer countAll(AlertDTO dto) throws JrafDomainException {
        Integer count = null;
        Alert alert = null;
        alert = AlertTransform.dto2BoLight(dto);
		count = (int) alertRepository.count(Example.of(alert));
        return count;
    }

    @Transactional(readOnly=true)
    public List<AlertDTO> findByExample(AlertDTO dto) throws JrafDomainException {
        Alert alert = AlertTransform.dto2BoLight(dto);
        List<AlertDTO> result = new ArrayList<AlertDTO>();
		for (Alert alertElement : alertRepository.findAll(Example.of(alert))) {
        	result.add(AlertTransform.bo2DtoLight(alertElement));
        }
        return result;
    }

    @Transactional(readOnly=true)
    public List<AlertDTO> findByWhereClause(AlertDTO dto) throws JrafDomainException {
        Alert alert = AlertTransform.dto2BoLight(dto);
        List<AlertDTO> result = new ArrayList<AlertDTO>();
		for (Alert alertElement : alertRepository.findAll(Example.of(alert))) {
        	result.add(AlertTransform.bo2DtoLight(alertElement));
        }
        return result;
    }

    @Transactional(readOnly=true)
    public AlertDTO get(AlertDTO dto) throws JrafDomainException {
        AlertDTO alertDTO = null;
		Optional<Alert> alert = alertRepository.findById(dto.getAlertId());
        
		if (alert.isPresent()) {
			alertDTO = AlertTransform.bo2DtoLight(alert.get());
        }
        return alertDTO;
    }

    @Transactional(readOnly=true)
	public AlertDTO get(Integer id) throws JrafDomainException {
        AlertDTO alertDTO = null;
		Optional<Alert> alert = alertRepository.findById(id);
        
		if (alert.isPresent()) {
			alertDTO = AlertTransform.bo2DtoLight(alert.get());
        }
        return alertDTO;
    }

    private AlertDTO updateAlertData(AlertDTO alertDTO, Alert alert) throws JrafDomainException {
    	for (AlertDataDTO alertDataDTO : alertDTO.getAlertDataDTO()) {
			AlertData alertData = AlertDataTransform.dto2BoLight(alertDataDTO);
			alertData.setAlert(alert);
			getAlertDataRepository().saveAndFlush(alertData);
		}

    	return alertDTO;
	}

    /** 
     * findAlert
     * @param gin in String
     * @throws JrafDomainException en cas d'exception
     */
    @Transactional(readOnly=true)
    public List<AlertDTO> findAlert(String gin) throws JrafDomainException {
    	if(StringUtils.isEmpty(gin)) {
			throw new IllegalArgumentException("Unable to find alert without GIN");
		}
		
		List<Alert> alertList = alertRepository.findBySgin(gin);
		
		if (alertList == null || alertList.isEmpty()) {
			return null;
		}
		
		List<AlertDTO> alertDTOList = new ArrayList<AlertDTO>();
		for (Alert alert: alertList) {
			alert.setAlertdata(alertDataRepository.findByAlert(alert.getAlertId()));
			alertDTOList.add(AlertTransform.bo2DtoLight(alert));
		}
		
		return alertDTOList;
    }

    /*
     * Output global for all alert For a specific ComPrefID
     * @see com.afklm.rigui.service.individu.IAlertDS#optoutByComPrefId(int)
     */
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void optoutByComPrefId(int comPrefId) throws JrafDomainException {
		List<Alert> listAlert = alertRepository.findByComPrefId(Integer.toString(comPrefId));
		if (listAlert != null && !listAlert.isEmpty()) {
			for (Alert alert : listAlert) {
				alert.setOptIn("N");
				alertRepository.saveAndFlush(alert);
			}
		}
	}
    
    public int countWhere(AlertDTO alertDTO) throws JrafDomainException {
    	Alert alert = AlertTransform.dto2BoLight(alertDTO);
		int count = (int) alertRepository.count(Example.of(alert));
    	return count;
    }
    
    public int getNumberOptinAlertsByGin(String gin) throws JrafDomainException {
		return alertRepository.countBySginByOptIn(gin, "Y");
    }
    
    public int getNumberOptoutAlertsByGin(String gin) throws JrafDomainException {
		return alertRepository.countBySginByOptIn(gin, "N");
    }
}
