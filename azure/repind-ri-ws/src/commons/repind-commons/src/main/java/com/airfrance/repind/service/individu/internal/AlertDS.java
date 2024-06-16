package com.airfrance.repind.service.individu.internal;


import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.individu.AlertDataRepository;
import com.airfrance.repind.dao.individu.AlertRepository;
import com.airfrance.repind.dto.individu.AlertDTO;
import com.airfrance.repind.dto.individu.AlertDataDTO;
import com.airfrance.repind.dto.individu.AlertDataTransform;
import com.airfrance.repind.dto.individu.AlertTransform;
import com.airfrance.repind.entity.individu.Alert;
import com.airfrance.repind.entity.individu.AlertData;
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

    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;
    
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

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void update(AlertDTO alertDTO) throws JrafDomainException {
    	Alert alert = null;

		alert = alertRepository.getOne(alertDTO.getAlertId());
      	alertDTO.setCreationDate(alert.getCreationDate());
      	alertDTO.setCreationSignature(alert.getCreationSignature());
      	alertDTO.setCreationSite(alert.getCreationSite());
      	
      	if(alertDTO.getAlertDataDTO() != null && alertDTO.getAlertDataDTO().size() > 0){
  			
      		StringBuffer buffer = new StringBuffer("select a from AlertData a where alert_id = (:alertId) and key = (:key)");
			Query query = getEntityManager().createQuery(buffer.toString());
			query.setParameter("alertId", alert.getAlertId());
			
      		for (AlertDataDTO alertDataDTO : alertDTO.getAlertDataDTO()) {
    			query.setParameter("key", alertDataDTO.getKey());
    			
    			AlertData alertData = new AlertData();
      			alertData.setAlert(alert);
      			alertData.setKey(alertDataDTO.getKey());
          		alertData.setValue(alertDataDTO.getValue()); //on set la valeur de la key
          		
          		List<AlertData> search = query.getResultList(); //on recheche avant de set la valeur
          		
          		if(search.size() == 1){
          			alertData.setAlertDataId(search.get(0).getAlertDataId());
          		}
          		
				getAlertDataRepository().saveAndFlush(alertData);
			}
      	}
        AlertTransform.dto2BoLight(alertDTO, alert);
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

    /**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
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
     * @see com.airfrance.repind.service.individu.IAlertDS#optoutByComPrefId(int)
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
