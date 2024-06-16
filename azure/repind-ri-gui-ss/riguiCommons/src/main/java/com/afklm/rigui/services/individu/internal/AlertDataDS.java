package com.afklm.rigui.services.individu.internal;

import com.afklm.rigui.exception.jraf.JrafDomainException;
import com.afklm.rigui.exception.jraf.JrafDomainNoRollbackException;
import com.afklm.rigui.exception.jraf.JrafDomainRollbackException;
import com.afklm.rigui.dao.individu.AlertDataRepository;
import com.afklm.rigui.dto.individu.AlertDataDTO;
import com.afklm.rigui.dto.individu.AlertDataTransform;
import com.afklm.rigui.entity.individu.AlertData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
@Service
public class AlertDataDS {

    /** logger */
    private static final Log log = LogFactory.getLog(AlertDataDS.class);

	/** main repository */
    @Autowired
	private AlertDataRepository alertDataRepository;

    @Transactional(readOnly=true)
    public Integer countWhere(AlertDataDTO dto) throws JrafDomainException {
        Integer count = null;
        AlertData alertData = null;
        alertData = AlertDataTransform.dto2BoLight(dto);
		count = (int) alertDataRepository.count(Example.of(alertData));
        return count;
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(AlertDataDTO alertDataDTO) throws JrafDomainException {
        AlertData alertData = null;
        alertData = AlertDataTransform.dto2BoLight(alertDataDTO);
		alertDataRepository.saveAndFlush(alertData);
        AlertDataTransform.bo2DtoLight(alertData, alertDataDTO);
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void remove(AlertDataDTO dto) throws JrafDomainException {
        AlertData alertData = null;
        alertData = AlertDataTransform.dto2BoLight(dto);
		alertDataRepository.delete(alertData);
    }
    
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void remove(Integer id) throws JrafDomainException {
		alertDataRepository.deleteById(id);
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void update(AlertDataDTO alertDataDTO) throws JrafDomainException {
       AlertData alertData = null;
		alertData = alertDataRepository.findById(alertDataDTO.getAlertDataId()).get();
       AlertDataTransform.dto2BoLight(alertDataDTO, alertData);
    }

    @Transactional(readOnly=true)
    public List<AlertDataDTO> findAll() throws JrafDomainException {
        List boFounds = null;
        List<AlertDataDTO> dtoFounds = null;
        AlertDataDTO dto = null;
        AlertData alertData = null;

		boFounds = alertDataRepository.findAll();
        if (boFounds != null) {
            dtoFounds = new ArrayList<AlertDataDTO>(boFounds.size());
            Iterator i = boFounds.iterator();
            while (i.hasNext()) {
                alertData = (AlertData) i.next();
                dto = AlertDataTransform.bo2DtoLight(alertData);
                dtoFounds.add(dto);
            }
        }
        
        return dtoFounds;
    }

    @Transactional(readOnly=true)
    public Integer count() throws JrafDomainException {
		List boFounds = alertDataRepository.findAll();
        return boFounds.size();
    }

    @Transactional(readOnly=true)
    public List<AlertDataDTO> findByExample(AlertDataDTO dto) throws JrafDomainException {
        AlertData alertData = AlertDataTransform.dto2BoLight(dto);
        List<AlertDataDTO> result = new ArrayList<AlertDataDTO>();
		for (AlertData alerDatatElement : alertDataRepository.findAll(Example.of(alertData))) {
        	result.add(AlertDataTransform.bo2DtoLight(alerDatatElement));
        }
        return result;
    }

    @Transactional(readOnly=true)
    public AlertDataDTO get(AlertDataDTO dto) throws JrafDomainException {
        AlertDataDTO alertDataDTO = null;
		Optional<AlertData> alertData = alertDataRepository.findById(dto.getAlertDataId());

		if (alertData.isPresent()) {
			alertDataDTO = AlertDataTransform.bo2DtoLight(alertData.get());
        }
        
        return alertDataDTO;
    }

    @Transactional(readOnly=true)
	public AlertDataDTO get(Integer id) throws JrafDomainException {
        AlertDataDTO alertDataDTO = null;
		Optional<AlertData> alertData = alertDataRepository.findById(id);

		if (alertData.isPresent()) {
			alertDataDTO = AlertDataTransform.bo2DtoLight(alertData.get());
        }
        
        return alertDataDTO;
    }
}
