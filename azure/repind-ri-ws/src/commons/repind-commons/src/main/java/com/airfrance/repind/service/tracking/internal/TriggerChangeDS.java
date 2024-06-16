package com.airfrance.repind.service.tracking.internal;


import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.jraf.JrafDomainNoRollbackException;
import com.airfrance.ref.exception.jraf.JrafDomainRollbackException;
import com.airfrance.repind.dao.tracking.TriggerChangeRepository;
import com.airfrance.repind.dto.tracking.TriggerChangeDTO;
import com.airfrance.repind.dto.tracking.TriggerChangeTransform;
import com.airfrance.repind.entity.tracking.TriggerChange;
import com.airfrance.repind.exception.TooManyResultsDaoException;
import com.airfrance.repind.exception.TooManyResultsDomainException;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.service.internal.unitservice.tracking.TriggerChangeUS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@org.springframework.stereotype.Service
public class TriggerChangeDS {

    /** logger */
    private static final Log log = LogFactory.getLog(TriggerChangeDS.class);

    /** the Entity Manager*/
    @PersistenceContext(unitName="entityManagerFactoryRepind")
    private EntityManager entityManager;

    /** unit service : TriggerChangeUS **/
    @Autowired
    private TriggerChangeUS triggerChangeUS;
    
    @Autowired 
    private VariablesDS variablesDS;

    /** main dao */
    @Autowired
    private TriggerChangeRepository triggerChangeRepository;



    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
    public void create(TriggerChangeDTO triggerChangeDTO) throws JrafDomainException {
         TriggerChange triggerChange = TriggerChangeTransform.dto2BoLight(triggerChangeDTO);

        // create in database (call the abstract class)
         triggerChangeRepository.saveAndFlush(triggerChange);

        // Version update and Id update if needed
        TriggerChangeTransform.bo2DtoLight(triggerChange, triggerChangeDTO);
    }


    public TriggerChangeRepository getTriggerChangeRepository() {
		return triggerChangeRepository;
	}


	public void setTriggerChangeRepository(TriggerChangeRepository triggerChangeRepository) {
		this.triggerChangeRepository = triggerChangeRepository;
	}


	/**
     * Getter
     * @return ITriggerChangeUS
     */
    public TriggerChangeUS getTriggerChangeUS() {
        return triggerChangeUS;
    }

    /**
     * Setter
     * @param triggerChangeUS the ITriggerChangeUS 
     */
    public void setTriggerChangeUS(TriggerChangeUS triggerChangeUS) {
        this.triggerChangeUS = triggerChangeUS;
    }

    /**
     * @return EntityManager
     */ 
    public EntityManager getEntityManager() {
        /*PROTECTED REGION ID(_sSaNQPJAEeSRYp0GhZuE2ggem ) ENABLED START*/
        return entityManager;
        /*PROTECTED REGION END*/
    }

    /**
     *  @param entityManager EntityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /*PROTECTED REGION ID(_sSaNQPJAEeSRYp0GhZuE2g u m) ENABLED START*/

    @Transactional(readOnly=true)
	public List<TriggerChangeDTO> findChanges() throws JrafDomainException {
    
    	List<TriggerChangeDTO> dto = null;
    	
    	try {
    		int maxRows = Integer.parseInt(variablesDS.getEnv("BATCH_EVENT_RF_MAX_ROWS_PER_REQUEST", "5000"));
    		List<TriggerChange> list = triggerChangeRepository.findTriggerChange(PageRequest.of(0, maxRows));
    		dto = TriggerChangeTransform.bo2DtoLight(list);
	    }
	    catch (TooManyResultsDaoException e){
	    	throw new TooManyResultsDomainException(e.getMessage());
	    }
    	
    	return dto;
    }
    


    @Transactional(rollbackFor = JrafDomainRollbackException.class, noRollbackFor = JrafDomainNoRollbackException.class)
	public void updateStatus(List<Long> tcIdList) throws JrafDomainException {
    	
    	for(Long tcId : tcIdList)
    	{
    		TriggerChange tc = triggerChangeRepository.getOne(tcId);
    		tc.setChangeStatus("P");
    		triggerChangeRepository.saveAndFlush(tc);
    	}
    }
    /*PROTECTED REGION END*/
}
