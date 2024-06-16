package com.airfrance.repind.service.tracking.internal;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.tracking.TriggerChangeAutMailingRepository;
import com.airfrance.repind.dto.tracking.TriggerChangeAutMailingDTO;
import com.airfrance.repind.dto.tracking.TriggerChangeAutMailingTransform;
import com.airfrance.repind.entity.tracking.TriggerChangeAutMailing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TriggerChangeAutMailingDS {
	
    @Autowired
    private TriggerChangeAutMailingRepository triggerChangeAutMailingRepository;
    
   
    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(TriggerChangeAutMailingDTO triggerChangeAutMailingDTO) throws JrafDomainException {
        TriggerChangeAutMailing triggerChangeAutMailing = TriggerChangeAutMailingTransform.dto2BoLight(triggerChangeAutMailingDTO);

        // create in database (call the abstract class)
        triggerChangeAutMailingRepository.saveAndFlush(triggerChangeAutMailing);

        // Version update and Id update if needed
        TriggerChangeAutMailingTransform.bo2DtoLight(triggerChangeAutMailing, triggerChangeAutMailingDTO);
    }


	public TriggerChangeAutMailingRepository getTriggerChangeAutMailingRepository() {
		return triggerChangeAutMailingRepository;
	}


	public void setTriggerChangeAutMailingRepository(TriggerChangeAutMailingRepository triggerChangeAutMailingRepository) {
		this.triggerChangeAutMailingRepository = triggerChangeAutMailingRepository;
	}


	
	public List<TriggerChangeAutMailingDTO> findAll() throws InvalidParameterException, JrafDomainException {
		List<TriggerChangeAutMailingDTO> result = new ArrayList<>();
		for (TriggerChangeAutMailing found : triggerChangeAutMailingRepository.findAll()) {
			result.add(TriggerChangeAutMailingTransform.bo2DtoLight(found));
		}
		return result;
	}

	
	public void remove(TriggerChangeAutMailingDTO triggerChangeAutMailing) throws InvalidParameterException {
		triggerChangeAutMailingRepository.deleteById(triggerChangeAutMailing.getId());
		
	}

	
	public TriggerChangeAutMailingDTO get(TriggerChangeAutMailingDTO newTrigger) throws JrafDomainException {
		return get(newTrigger.getId());
	}
	
	public TriggerChangeAutMailingDTO get(Long id) throws JrafDomainException {
		Optional<TriggerChangeAutMailing> email = triggerChangeAutMailingRepository.findById(id);
		if (!email.isPresent()) {
			return null;
		}
		return TriggerChangeAutMailingTransform.bo2DtoLight(email.get());
	}
}
