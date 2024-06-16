package com.airfrance.repind.service.internal.unitservice.tracking;

import com.airfrance.repind.dao.tracking.TriggerChangeRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Title : TriggerChangeUS.java</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
@Service
public class TriggerChangeUS {

    /** logger */
    private static final Log log = LogFactory.getLog(TriggerChangeUS.class);

    /*PROTECTED REGION ID(_Wi_gMPJQEeSRYp0GhZuE2g u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /** references on associated DAOs */
    @Autowired
    private TriggerChangeRepository triggerChangeRepository;

    /**
     * empty constructor
     */
    public TriggerChangeUS() {
    }

	public TriggerChangeRepository getTriggerChangeRepository() {
		return triggerChangeRepository;
	}

	public void setTriggerChangeRepository(TriggerChangeRepository triggerChangeRepository) {
		this.triggerChangeRepository = triggerChangeRepository;
	}

    /*PROTECTED REGION ID(_Wi_gMPJQEeSRYp0GhZuE2g u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/
}
