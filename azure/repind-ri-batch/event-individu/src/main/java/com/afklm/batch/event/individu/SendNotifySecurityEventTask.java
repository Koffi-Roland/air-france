package com.afklm.batch.event.individu;

import com.afklm.soa.stubs.w001690.v1.NotifySecurityEventForIndividualV1;
import com.afklm.soa.stubs.w001690.v1.data.EventBlock;
import com.afklm.soa.stubs.w001690.v1.data.NotifySecurityEventForIndividualMsg;
import com.afklm.soa.stubs.w001690.v1.securityevent.DetailsBlock;
import com.airfrance.batch.common.enums.EventTypeEnum;
import com.airfrance.batch.common.enums.KeySecurityEventEnum;
import com.airfrance.batch.common.exception.BatchException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.reference.RefDetailsKeyDTO;
import com.airfrance.repind.dto.reference.RefTypeEventDTO;
import com.airfrance.repind.dto.tracking.TriggerChangeIndividusDTO;
import com.airfrance.repind.entity.reference.RefTypeEvent;
import com.airfrance.repind.service.reference.internal.RefDetailsKeyDS;
import com.airfrance.repind.service.reference.internal.RefTypeEventDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author LINARES Thibaut
 * Task in charge of send a Security Event
 * 
 * CONTEXT: Publish a notification event as soon as a security data update (like a change of password) 
 * is done for an individual in table ACCOUNT_DATA. 
 * 
 * OBJECTIVE is:
 *  - Notify as soon as an update is done on security data for an account data (like password) 
 *  This event makes users aware of an update of individual security data
 * 
 * STEPS:
 *  - BatchSicUpdateNotification read the table TRIGGER_CHANGE_INDIVIDUS and get all rows with CHANGE_STATUS equal to 'N'
 *  - For each TRIGGER_CHANGE_INDIVIDUS :
 *  	- Determine the type of the event by comparing the differences between the columns CHANGE_BEFORE and CHANGE_AFTER
 *  	- Generate an event based on the type of change
 *  	- Send the event in a MQ queue
 * 
 * USAGE:
 * Task call by BatchIndividualUpdateNotification with arg "-i"
 * 
 */
@DependsOn("notifySecurityEventForIndividual-v1")
@Component
public class SendNotifySecurityEventTask implements Runnable {

    final static Log log = LogFactory.getLog(SendNotifySecurityEventTask.class);
    
    @Autowired
	protected RefTypeEventDS typeEventDS;

    @Autowired
	protected RefDetailsKeyDS detailsKeyDS;
    
	@Autowired
	@Qualifier("notifySecurityEventForIndividual-v1")
	private NotifySecurityEventForIndividualV1 notifySecurityEventForIndividualV1;
	
	private LinkedBlockingQueue<TriggerChangeIndividusDTO> queue;
	
	private List<RefTypeEventDTO> refTypeEventList;
	
	private String taskName;
		
	private final static String DELETE_STATUS = "D";
	private final static String CLOSE_STATUS = "C";
	private final static String EXPIRED_STATUS = "E";
	private final static String TYPE_NOT_FOUND = "Type not found";
	private final static String DETAILS_TO_PROVIDE = "Details to provide not found";
	
	@Override
	public void run() {
		if(BatchIndividualUpdateNotification.debugLogActivated){
			BatchIndividualUpdateNotification.LogBatchOutputForSecurity("Start "+taskName);
			try {
				BatchIndividualUpdateNotification.bfwOutputSecurity.flush();
 			} catch (IOException eio) {
 				BatchIndividualUpdateNotification.log.error(eio);
 			}
		}
		
		try {
			// Get all event type presents in DB
			refTypeEventList = typeEventDS.findAll();
		} catch (JrafDomainException e) {
			log.error(e);
		}
		// For each TriggerChangeIndividus in the queue, build and send an event 
		while (queue.size()>0) {
			TriggerChangeIndividusDTO tci = null;
			try {
				tci = queue.take();
				
				sendNotifySecurityEvent(tci, taskName); 
				
			} catch (InterruptedException|JrafDomainException e) { 
				BatchIndividualUpdateNotification.setHasError(true);
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				log.error(e);

				// REPIND-1003 : Repair blocker
				String gin = "";
				if(tci != null) {
					gin = tci.getGin();
				}
				BatchIndividualUpdateNotification.logBatchErrorForSecurity(taskName + "--> " + BatchIndividualUpdateNotification.ERROR + " Error during sendNotifySecurityEvent for gin = " + gin + " : ", sw);
				try {
					BatchIndividualUpdateNotification.bfwErrorSecurity.flush();
	 			} catch (IOException eio) {
	 				BatchIndividualUpdateNotification.log.error(eio);
	 			}
			}
		}
		if(BatchIndividualUpdateNotification.debugLogActivated){
			BatchIndividualUpdateNotification.LogBatchOutputForSecurity("End "+taskName);
			try {
				BatchIndividualUpdateNotification.bfwOutputSecurity.flush();
 			} catch (IOException eio) {
 				BatchIndividualUpdateNotification.log.error(eio);
 			}
		}
	}

	/**
     * sendNotifySecurityEvent : Send of Event NotifySecurity
     * Create a new EventBlock and send it with NotifySecurityEventMsg
     * @param tci
     * @param taskName
     */
    protected void sendNotifySecurityEvent(TriggerChangeIndividusDTO tci, String taskName) throws JrafDomainException 
    {
    	NotifySecurityEventForIndividualMsg nseiMsg = new NotifySecurityEventForIndividualMsg();

        EventBlock event = null;
        
        // Get Gin for log
        String gin = tci.getGin();
        
        try {
        	if(BatchIndividualUpdateNotification.debugLogActivated){
        		BatchIndividualUpdateNotification.LogBatchOutputForSecurity(taskName + "--> " + "START getEventBlock for GIN : " + gin);
        		try {
        			BatchIndividualUpdateNotification.bfwOutputSecurity.flush();
     			} catch (IOException eio) {
     				BatchIndividualUpdateNotification.log.error(eio);
     			}
        	}
        	// Get EventBlock to send
        	event = getEventBlock(tci);
        	if(BatchIndividualUpdateNotification.debugLogActivated){
        		BatchIndividualUpdateNotification.LogBatchOutputForSecurity(taskName + "--> " + "END getEventBlock for GIN : " + gin);
        		try {
        			BatchIndividualUpdateNotification.bfwOutputSecurity.flush();
     			} catch (IOException eio) {
     				BatchIndividualUpdateNotification.log.error(eio);
     			}
        	}
        } catch (Exception e) {
        	BatchIndividualUpdateNotification.setHasError(true);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			log.error(e);
			BatchIndividualUpdateNotification.logBatchErrorForSecurity(taskName + "--> " + BatchIndividualUpdateNotification.ERROR + " Error during getEventBlock for gin = " + gin + " : ", sw);
			try {
				BatchIndividualUpdateNotification.bfwErrorSecurity.flush();
 			} catch (IOException eio) {
 				BatchIndividualUpdateNotification.log.error(eio);
 			}
        }
        
        // If event is not null, send event
        if(event != null)
        {
	        
	        nseiMsg.setEventBlock(event);
	        
	        // Send NSEI event
	        try {
	        	if(BatchIndividualUpdateNotification.debugLogActivated){
	        		BatchIndividualUpdateNotification.LogBatchOutputForSecurity(taskName + "--> " + "START Send NSEI event for GIN : " + gin);
		        	try {
		        		BatchIndividualUpdateNotification.bfwOutputSecurity.flush();
	     			} catch (IOException eio) {
	     				BatchIndividualUpdateNotification.log.error(eio);
	     			}
	        	}
	        	
	        	notifySecurityEventForIndividualV1.notifySecurityEvent(nseiMsg);
	            
	        	// Increment number of NSEI Event send
	        	BatchIndividualUpdateNotification.incrementNbNSEIEventSent();
	            if(BatchIndividualUpdateNotification.debugLogActivated){
	            	BatchIndividualUpdateNotification.LogBatchOutputForSecurity(taskName + "--> " + "END Send NSEI event for GIN : " + gin);
		            try {
		            	BatchIndividualUpdateNotification.bfwOutputSecurity.flush();
	     			} catch (IOException eio) {
	     				BatchIndividualUpdateNotification.log.error(eio);
	     			}
	        	}
	        } catch (Exception e) {
	        	BatchIndividualUpdateNotification.setHasError(true);
	            StringWriter sw = new StringWriter();
	            PrintWriter pw = new PrintWriter(sw);
	            e.printStackTrace(pw);
	            log.error(e);
	            BatchIndividualUpdateNotification.logBatchErrorForSecurity(taskName + "--> " + BatchIndividualUpdateNotification.ERROR + " Error during sending notifySecurityEvent for gin = " + gin, sw);
	            try {
	            	BatchIndividualUpdateNotification.bfwErrorSecurity.flush();
	 			} catch (IOException eio) {
	 				BatchIndividualUpdateNotification.log.error(eio);
	 			}
	        }
        }
    }

	/**
	 * Building an EventBlock from a TriggerChangeIndividusDTO
	 * Get type of event with values before change and values after change
	 * Get DetailsBlock to provide with the type
	 * 
	 * @param tci
	 * @throws Exception 
	 */
    protected EventBlock getEventBlock(TriggerChangeIndividusDTO tci) throws BatchException, JrafDomainException {
    	
        EventBlock event = new EventBlock();
        event.setGin(tci.getGin());

		// Split values before/after by "|" in an array
        String[] valuesBefore = tci.getChangeBefore().split("\\|");
        String[] valuesAfter = tci.getChangeAfter().split("\\|");
        
        // Add date of generated event
	    event.setDate(new Date());
		String type = getTypeEvent(valuesBefore, valuesAfter);
		
		// Search and check if the type exists in DB
		boolean found = false;
		for (int i = 0; i < refTypeEventList.size(); i++) {
			if(refTypeEventList.get(i).getCode().equals(type)) {
				found = true;
			}
		}
		
		// If type is not found in DB, return null and not send an event
		if(!found) {
			return null;
		}
		
        event.setType(type);

        // Get details to provide
        List<RefDetailsKeyDTO> detailsToProvide = getDetailsToProvide(type);

        if(detailsToProvide != null) {
			
	        // For each details to provide, build a DetailsBlock to send if value is not null
	        for (int i = 0; i < detailsToProvide.size(); i++) {

				String value = valuesAfter[detailsToProvide.get(i).getDetailsKeyID()];
				
				// If value is not null, add new DetailsBlock to the event
				if(value != null && !"".equalsIgnoreCase(value)) {
					DetailsBlock detailsBlock = new DetailsBlock();
					detailsBlock.setKey(detailsToProvide.get(i).getCode());
					detailsBlock.setValue(value);
					event.getEventDetails().add(detailsBlock);
				}
			}
        }
        else {

            if(BatchIndividualUpdateNotification.debugLogActivated){
            	BatchIndividualUpdateNotification.LogBatchOutputForSecurity(taskName + "--> " + DETAILS_TO_PROVIDE + " for GIN : " + tci.getGin());
	            try {
	            	BatchIndividualUpdateNotification.bfwOutputSecurity.flush();
     			} catch (IOException eio) {
     				BatchIndividualUpdateNotification.log.error(eio);
     			}
        	}
            
        }
        
        
		return event;
		
    }


	/**
	 * Compare array valuesBefore and array valuesAfter
	 * Return a type in function of changes between valuesBefore and valuesAfter
	 * 
	 * @param valuesBefore
	 * @param valuesAfter
	 */
    protected String getTypeEvent(String[] valuesBefore, String[] valuesAfter) {
    	
        if(!valuesBefore[KeySecurityEventEnum.PASSWORD.getKey()].equals(valuesAfter[KeySecurityEventEnum.PASSWORD.getKey()]) 
        		&& valuesBefore[KeySecurityEventEnum.LAST_PWD_RESET_DATE.getKey()].equals(valuesAfter[KeySecurityEventEnum.LAST_PWD_RESET_DATE.getKey()]) ) {
        	return EventTypeEnum.PASSWORD_CHANGE.toString();
        }
        if(!valuesBefore[KeySecurityEventEnum.LAST_PWD_RESET_DATE.getKey()].equals(valuesAfter[KeySecurityEventEnum.LAST_PWD_RESET_DATE.getKey()])) {
        	return EventTypeEnum.PASSWORD_RESET.toString();
        }
        if(!valuesBefore[KeySecurityEventEnum.SECRET_QUESTION.getKey()].equals(valuesAfter[KeySecurityEventEnum.SECRET_QUESTION.getKey()])
        		&& valuesBefore[KeySecurityEventEnum.SECRET_QUESTION_ANSWER.getKey()].equals(valuesAfter[KeySecurityEventEnum.SECRET_QUESTION_ANSWER.getKey()])) {
        	return EventTypeEnum.SECRET_QUESTION_UPDATE.toString();
        }
        if(!valuesBefore[KeySecurityEventEnum.SECRET_QUESTION_ANSWER.getKey()].equals(valuesAfter[KeySecurityEventEnum.SECRET_QUESTION_ANSWER.getKey()])
        		&& valuesBefore[KeySecurityEventEnum.SECRET_QUESTION.getKey()].equals(valuesAfter[KeySecurityEventEnum.SECRET_QUESTION.getKey()])) {
        	return EventTypeEnum.SECRET_ANSWER_UPDATE.toString();
        }
        if(!valuesBefore[KeySecurityEventEnum.SECRET_QUESTION.getKey()].equals(valuesAfter[KeySecurityEventEnum.SECRET_QUESTION.getKey()])
        		&& !valuesBefore[KeySecurityEventEnum.SECRET_QUESTION_ANSWER.getKey()].equals(valuesAfter[KeySecurityEventEnum.SECRET_QUESTION_ANSWER.getKey()])) {
        	return EventTypeEnum.SECRET_QUESTION_AND_ANSWER_UPDATE.toString();
        }
        if(!valuesBefore[KeySecurityEventEnum.ACCOUNT_LOCKED_DATE.getKey()].equals(valuesAfter[KeySecurityEventEnum.ACCOUNT_LOCKED_DATE.getKey()])) {
        	return EventTypeEnum.ACCOUNT_LOCKED.toString();
        }
        if(!valuesBefore[KeySecurityEventEnum.STATUS.getKey()].equals(valuesAfter[KeySecurityEventEnum.STATUS.getKey()])
        		&& (valuesAfter[KeySecurityEventEnum.STATUS.getKey()].equals(DELETE_STATUS) || valuesAfter[KeySecurityEventEnum.STATUS.getKey()].equals(CLOSE_STATUS)
        				|| valuesAfter[KeySecurityEventEnum.STATUS.getKey()].equals(EXPIRED_STATUS))) {
        	return EventTypeEnum.DISABLED_ACCOUNT.toString();
        }
        
        return null;
    	
    }
    
    
	/**
	 * Get list of fields to return related to type of event
	 * 
	 * @param type
	 * @throws JrafDomainException 
	 */
    protected List<RefDetailsKeyDTO> getDetailsToProvide(String type) throws JrafDomainException {
    	
    	RefTypeEvent refTypeEvent = new RefTypeEvent();
    	refTypeEvent.setCode(type);
    	
    	return detailsKeyDS.findByTypeEvent(refTypeEvent);
		
    }


	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @param queue the queue to set
	 */
	public void setQueue(LinkedBlockingQueue<TriggerChangeIndividusDTO> queue) {
		this.queue = queue;
	}
	
	/**
	 * @return the notifySecurityEventForIndividualV1
	 */
	public NotifySecurityEventForIndividualV1 getNotifySecurityEventForIndividualV1() {
		return notifySecurityEventForIndividualV1;
	}

	/**
	 * @param notifySecurityEventForIndividualV1 the notifySecurityEventForIndividualV1 to set
	 */
	public void setNotifySecurityEventForIndividualV1(NotifySecurityEventForIndividualV1 notifySecurityEventForIndividualV1) {
		this.notifySecurityEventForIndividualV1 = notifySecurityEventForIndividualV1;
	}

	/**
	 * @return the refTypeEventList
	 */
	public List<RefTypeEventDTO> getRefTypeEventList() {
		return refTypeEventList;
	}

	/**
	 * @param refTypeEventList the refTypeEventList to set
	 */
	public void setRefTypeEventList(List<RefTypeEventDTO> refTypeEventList) {
		this.refTypeEventList = refTypeEventList;
	}

	/**
	 * @return the typeEventDS
	 */
	public RefTypeEventDS getTypeEventDS() {
		return typeEventDS;
	}

	/**
	 * @param typeEventDS the typeEventDS to set
	 */
	public void setTypeEventDS(RefTypeEventDS typeEventDS) {
		this.typeEventDS = typeEventDS;
	}

	/**
	 * @return the detailsKeyDS
	 */
	public RefDetailsKeyDS getDetailsKeyDS() {
		return detailsKeyDS;
	}

	/**
	 * @param detailsKeyDS the detailsKeyDS to set
	 */
	public void setDetailsKeyDS(RefDetailsKeyDS detailsKeyDS) {
		this.detailsKeyDS = detailsKeyDS;
	}
	
	
}
