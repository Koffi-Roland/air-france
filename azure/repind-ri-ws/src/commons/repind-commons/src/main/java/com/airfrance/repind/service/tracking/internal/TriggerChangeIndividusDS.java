package com.airfrance.repind.service.tracking.internal;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.tracking.TriggerChangeIndividusRepository;
import com.airfrance.repind.dto.tracking.TriggerChangeIndividusDTO;
import com.airfrance.repind.dto.tracking.TriggerChangeIndividusTransform;
import com.airfrance.repind.entity.tracking.TriggerChangeIndividus;
import com.airfrance.repind.exception.TooManyResultsDaoException;
import com.airfrance.repind.exception.TooManyResultsDomainException;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class TriggerChangeIndividusDS {

    final TriggerChangeIndividusRepository triggerChangeIndividusRepository;
	final VariablesDS variablesDS;
	final EntityManager entityManager;


	public TriggerChangeIndividusDS(@Qualifier("entityManagerFactoryRepind") EntityManager entityManager, TriggerChangeIndividusRepository triggerChangeIndividusRepository, VariablesDS variablesDS) {
		this.entityManager = entityManager;
		this.triggerChangeIndividusRepository = triggerChangeIndividusRepository;
		this.variablesDS = variablesDS;
	}

    @Transactional(readOnly=true)
    public Optional<TriggerChangeIndividus> get(TriggerChangeIndividus bo) throws JrafDomainException {
        return triggerChangeIndividusRepository.findById(bo.getId());
    }
    
    
    public TriggerChangeIndividusRepository getTriggerChangeIndividusRepository() {
		return triggerChangeIndividusRepository;
	}

    @Transactional(readOnly=true)
	public List<TriggerChangeIndividusDTO> findChanges(String maxBlockSize) throws JrafDomainException {
    
    	List<TriggerChangeIndividusDTO> dto = null;
    	
    	try {
    		List<TriggerChangeIndividus> list = triggerChangeIndividusRepository.findTriggerChangeIndividus(maxBlockSize);
    		dto = TriggerChangeIndividusTransform.bo2DtoLight(list);
	    }
	    catch (TooManyResultsDaoException e){
	    	throw new TooManyResultsDomainException(e.getMessage());
	    }
    	
    	return dto;
    }

    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateStatus(List<Long> tcIdList) throws JrafDomainException {

		triggerChangeIndividusRepository.updateChangeStatusIn("P", tcIdList);
		//entityManager.getTransaction().commit();
    }

	public List<TriggerChangeIndividus> findByGinAndChangeTableAndChangeTypeAndChangeStatus(String gin,
																							String changeTable,
																							String changeType,
																			  String changeStatus){
		return triggerChangeIndividusRepository.findByGinAndChangeTableAndChangeTypeAndChangeStatus(gin, changeTable,
				changeType, changeStatus);
	}
}
