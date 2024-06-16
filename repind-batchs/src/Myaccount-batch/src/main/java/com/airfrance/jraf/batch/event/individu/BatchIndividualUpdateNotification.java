package com.airfrance.jraf.batch.event.individu;

import com.airfrance.jraf.batch.common.BatchArgs;
import com.airfrance.jraf.batch.common.IBatch;
import com.airfrance.jraf.batch.common.type.BatchSicUpdateNotificationArgsEnum;
import com.airfrance.jraf.batch.config.WebConfigBatchEvent;
import com.airfrance.jraf.batch.individu.exception.BatchException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.tracking.TriggerChangeIndividusDTO;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.service.tracking.internal.TriggerChangeDS;
import com.airfrance.repind.service.tracking.internal.TriggerChangeIndividusDS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Service("batchIndividualUpdateNotification")
public class BatchIndividualUpdateNotification extends BatchArgs {

    // log
    final static Log log = LogFactory.getLog(BatchIndividualUpdateNotification.class);

    @Autowired
    protected TriggerChangeDS triggerChangeDS;
    
    @Autowired
    protected TriggerChangeIndividusDS triggerChangeIndividusDS;
	
    @Autowired
	protected VariablesDS variablesDS;
    
    @Autowired
    private SendNotifyEventIndividualTask sendNotifyEventIndividualTask;

    @Autowired
    private UpdateStatusTask updateStatusTask;

    static final String WARN = "[WARN]";
    private static final String INFO = "[INFO]";
    static final String ERROR = "[ERROR]";
    private static final String LOG_EXTENSION = ".log";
    public static final String ARGS_SEPARATOR = "-";
    public static final String SEPARATOR = ";";
    
    private static final String LOGFILE_ERROR_IND = "BatchIndividualUpdateNotification_error_";
    private static final String LOGFILE_OUTPUT_IND = "BatchIndividualUpdateNotification_output_";    
    
    private static final String LOGFILE_ERROR_SECURITY = "BatchIndividualUpdateNotificationSecurity_error_";
    private static final String LOGFILE_OUTPUT_SECURITY = "BatchIndividualUpdateNotificationSecurity_output_";  
    
    // Directory of logged file.
    
    public  String EVENTS_IND_DATA_DIR = "/app/REPIND/data/EVENT_NOTIFICATIONS/";
    // REPIND-513
    public  String EVENTS_SECURITY_DATA_DIR = "/app/REPIND/data/EVENT_NOTIFICATIONS_SECURITY/";

    // Argument
    protected  boolean local             = false;
    
    private  String localPath = "C:\\tmp\\Event\\";
    
    // Buffer for log file
    static BufferedWriter bfwOutput;
    static BufferedWriter bfwError;
    
    // Buffer for log file Security - REPIND-513
    static BufferedWriter bfwOutputSecurity;
    static BufferedWriter bfwErrorSecurity;
    
    static int nbNEIEventSent = 0;
    static int nbNAEIEventSent = 0;
    static int nbNSEIEventSent = 0;

    static boolean hasError = false;
    static boolean debugLogActivated = false;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat sdfLog = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
    private String dateJour = sdf.format(new Date());

    private static final String APP_NAME = "REPIND";

    private static LinkedHashMap<String, ChangeData> ginAssociatedToBlockDTOListHm = new LinkedHashMap<>();
    private static LinkedHashMap<String, List<Long>> ginLinkedToTriggerChangeIndIdListHm = new LinkedHashMap<>();
    private static LinkedBlockingQueue<List<Long>> idBlockToUpdate = new LinkedBlockingQueue<>();

    public static void main(String[] args) { 
        log.info( "Lancement de BatchIndividualUpdateNotification..." ); 
        
        AnnotationConfigApplicationContext ctx = null;
        
        try { 
        	       
        	System.setProperty("logback.path.applicationName", APP_NAME);
            ctx = new AnnotationConfigApplicationContext(WebConfigBatchEvent.class);
            IBatch batch = (IBatch) ctx.getBean("batchIndividualUpdateNotification");
            ((BatchIndividualUpdateNotification) batch).parseArgs(args);
			log.info("Execution du batch...");
            
        } catch (Exception e) { 
            log.fatal( "Erreur lors de l'execution de BatchIndividualUpdateNotification" ); 
            log.fatal(e); 
            
            if (ctx != null) {
            	ctx.close();
            }
            System. exit (1); 
        }
    }
    
	/**
     * Parser les arguments.
     * 
     * @param args
     *            : {@link }.
     * @throws Exception
     *             : {@link Exception}.
     */
    @Override
    protected void parseArgs(String[] args) throws BatchException {
        
    	if (args != null && args.length != 0) {
			
	        List<BatchSicUpdateNotificationArgsEnum> argsProvided = new ArrayList<>();
	        for (int i = 0; i < args.length; i++) {
	            String s = args[i];
	
	            if (s.contains("-")) {
	                // Type of arg
	                String currentArg = s.split(ARGS_SEPARATOR)[1];
	
	                try {
	                    BatchSicUpdateNotificationArgsEnum currArgEnum = BatchSicUpdateNotificationArgsEnum.valueOf(currentArg);
	                    argsProvided.add(currArgEnum);
	
	                    switch (currArgEnum) {
	                    case l:
	                        local = true;
	                        i++;
	                        localPath= args[i];
	                        break;
	                    default:
	                        break;
	                    }
	                }
	                catch (IllegalArgumentException e) {
	                	printHelpBatch();
	                    throw new BatchException(ARGUMENT_NOT_VALID);
	                }
	            }
	        }
    	}
    }
    
    @Override
    @Scheduled(fixedDelay=20000)
    public void execute() throws JrafDomainException {
    	
        // Cas du test sur le serveur local (path des logs)
        if(local){
            EVENTS_IND_DATA_DIR = localPath;
            EVENTS_SECURITY_DATA_DIR = localPath;
        }
        
        openOutLogs();
        openErrorLogs();
        
        try {
            
        	LogBatchOutput("START Batch for individual");
        	Date startNEIEventManagementDate = new Date();
        	neiEventManagement();
        	Date endNEIEventManagementDate = new Date();
        	
        	LogBatchOutput("END Batch for individual. Total Duration : " + getDifferenceBetweenTwoDates(startNEIEventManagementDate,endNEIEventManagementDate));
            
            bfwOutput.flush();
            bfwError.flush();
 
        } catch (Exception e) {
            LogBatchOutput("Error during BatchIndividualUpdateNotification treatment : " + e.getMessage());
            log.error(e);
         // Close logs    
        } finally {
        	closeOutLogs();
            
        	closeErrorLogs();
        }
    }
    
    
    public void neiEventManagement() throws JrafDomainException
    {
        List<Long> triggerChangeIdList = new ArrayList<>();

        //Reset...
        hasError = false;
        nbNEIEventSent = 0;
        nbNAEIEventSent = 0;
        nbNSEIEventSent = 0;
        resetThreadsValues();

        List<LinkedBlockingQueue<TriggerChangeIndividusDTO>> queueTriggerChangeIndividusDTO = new ArrayList<>();
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        ginAssociatedToBlockDTOListHm = new LinkedHashMap<>();
        ginLinkedToTriggerChangeIndIdListHm = new LinkedHashMap<>();
        idBlockToUpdate= new LinkedBlockingQueue<>();

        LinkedBlockingQueue<TriggerChangeIndividusDTO> securityQueue = new LinkedBlockingQueue<>();
        debugLogActivated = Boolean.parseBoolean(variablesDS.getEnv("BATCH_EVENT_RI_DEBUG_LOG_ACTIVATED", "false"));
        String maxBlockSize = variablesDS.getEnv("BATCH_EVENT_RI_MAX_BLOCK_SIZE", "120000");
        int maxIdInSQLRequest = variablesDS.getEnv("BATCH_EVENT_RI_MAX_ID_IN_SQL_UPDATE", 10);

        LogBatchOutput("Debug log activated = " + debugLogActivated + ". Read " + maxBlockSize + " lines.");
        try {
			bfwOutput.flush();
		} catch (IOException e) {
			log.error(e);
		}
        
        // Get records from TRIGGER_CHANGE_INDIVIDUS table with CHANGE_STATUS = "N"
        Date startReadTableDate = new Date();
        List<TriggerChangeIndividusDTO> dtoIndFound = triggerChangeIndividusDS.findChanges(maxBlockSize);
        Date endReadTableDate = new Date();

        
        if(dtoIndFound != null && !dtoIndFound.isEmpty())
        {
            LogBatchOutput(dtoIndFound.size() + " record(s) to treat found in TRIGGER_CHANGE_INDIVIDUS table. Total Duration : " + getDifferenceBetweenTwoDates(startReadTableDate,endReadTableDate));
            try {
				bfwOutput.flush();
			} catch (IOException e) {
				log.error(e);
			}

            String currentGin = null;
            for(TriggerChangeIndividusDTO triggerDTO : dtoIndFound) {
                if(currentGin==null || !triggerDTO.getGin().equals(currentGin)) {
                    currentGin = triggerDTO.getGin();
                    queueTriggerChangeIndividusDTO.add(new LinkedBlockingQueue<>());
                }
                triggerChangeIdList.add(triggerDTO.getId());
                queueTriggerChangeIndividusDTO.get(queueTriggerChangeIndividusDTO.size()-1).add(triggerDTO);
            }

            LogBatchOutput(queueTriggerChangeIndividusDTO.size() + " differents GIN to treat.");
            int nbGinPerThreads = Integer.parseInt(variablesDS.getEnv("BATCH_EVENT_RI_NB_GIN_PER_THREAD", "4000"));
            int corePoolSize = Integer.parseInt(variablesDS.getEnv("BATCH_EVENT_RI_CORE_POOL_SIZE", "1"));

            ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setCorePoolSize(corePoolSize);
            taskExecutor.setMaxPoolSize(15);
            taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
            taskExecutor.initialize();

            int nbThreads = queueTriggerChangeIndividusDTO.size();

            for (int i = 0; i < nbThreads; i++) {
                PrepareNotifyEventIndividualTask prepareNotifyEventIndividualTask = new PrepareNotifyEventIndividualTask();
                prepareNotifyEventIndividualTask.setQueue(queueTriggerChangeIndividusDTO.get(i));
                prepareNotifyEventIndividualTask.setTaskName("Task_preparation_" + i);
                taskExecutor.execute(prepareNotifyEventIndividualTask);
            }

            for (;;) {
                int count = taskExecutor.getActiveCount();
                if(debugLogActivated){
                    LogBatchOutput("Active Threads for preparation : " + count);
                    try {
                        bfwOutput.flush();
                    } catch (IOException e) {
                        log.error(e);
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e);
                }
                if (count == 0) {
                    break;
                }
            }

            // Number of event to send
            LogBatchOutput(ginAssociatedToBlockDTOListHm.size() + " event(s) to send.");
            try {
 				bfwOutput.flush();
 			} catch (IOException e) {
 				log.error(e);
 			}
        	
            if(ginLinkedToTriggerChangeIndIdListHm.size() != ginAssociatedToBlockDTOListHm.size()){
            	LogBatchError(ERROR + " Inconsistency in number of event to send.", null);
            	try {
     				bfwError.flush();
     			} catch (IOException e) {
     				log.error(e);
     			}
            }

            if(ginAssociatedToBlockDTOListHm.size() > 0) {
                LogBatchOutput("Start sending individual events.");
                try {
    				bfwOutput.flush();
    			} catch (IOException e) {
    				log.error(e);
    			}
            }

            // REPIND-513
            if(securityQueue.size() > 0) {
                LogBatchOutputForSecurity("Start sending security events.");
                try {
    				bfwOutputSecurity.flush();
    			} catch (IOException e) {
    				log.error(e);
    			}
            }
            
            queue.addAll(ginAssociatedToBlockDTOListHm.keySet()); 
            
            Date startSendNotifyEventForIndividualDate = new Date();
            
            int nbThreadsForNSEI = 0;
            
            LogBatchOutput("Max GIN per Threads = " + nbGinPerThreads);
            try {
				bfwOutput.flush();
			} catch (IOException e) {
				log.error(e);
			}
            
            if ((queue.size()/nbGinPerThreads) < taskExecutor.getMaxPoolSize()) {
            	nbThreads = queue.size()/nbGinPerThreads;
            	if(nbThreads == 0)
            		nbThreads = 1;
            }

            // REPIND-513
            // For NotifySecurityEventForIndividual
            // If number of total threads is bigger than max pool size then :
            // number threads for NSE = 80% of the max pool size
            // number threads for NSEI = 20% of the max pool size
            // In case of problem, remove thread for NSEI
            if ((nbThreads + (securityQueue.size()/nbGinPerThreads)) < taskExecutor.getMaxPoolSize()) {
            	nbThreadsForNSEI = securityQueue.size()/nbGinPerThreads;
            	if(nbThreadsForNSEI == 0) {
            		nbThreadsForNSEI = 1;
            	}
            }
            
            LogBatchOutput("nb threads used = " + nbThreads);
            LogBatchOutputForSecurity("nb threads used = " + nbThreadsForNSEI);
            try {
				bfwOutput.flush();
				bfwOutputSecurity.flush();
			} catch (IOException e) {
				log.error(e);
			}

            //Reset task executor to avoid bug with the blocking queue.
            taskExecutor.shutdown();

            taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setCorePoolSize(corePoolSize);
            taskExecutor.setMaxPoolSize(15);
            taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
            taskExecutor.setQueueCapacity(nbGinPerThreads);
            taskExecutor.initialize();

            nbThreads = taskExecutor.getMaxPoolSize()-1;

            updateStatusTask.setEntriesQueue(idBlockToUpdate);
            updateStatusTask.setIdListMaxSize(maxIdInSQLRequest);
            updateStatusTask.setTaskName("Task_Update_Status_1");
            taskExecutor.execute(updateStatusTask);

            for (int i = 0; i < nbThreads; i++) {            	
            	sendNotifyEventIndividualTask.setGinLinkedToTriggerChangeIndIdListHm(ginAssociatedToBlockDTOListHm);
            	sendNotifyEventIndividualTask.setQueue(queue);
            	sendNotifyEventIndividualTask.setTaskName("Task_Send_Event_" + i);
            	taskExecutor.execute(sendNotifyEventIndividualTask);
            }
            
            for (;;) {
        		int count = taskExecutor.getActiveCount();
        		if(debugLogActivated){
	        		// LogBatchOutput("Active Threads for sending: " + count);
	        		try {
	     				bfwOutput.flush();
	     			} catch (IOException e) {
	     				log.error(e);
	     			}
        		}
        		try {
        			Thread.sleep(1000);
        		} catch (InterruptedException e) {
        			log.error(e);
        		}
        		if (count == 0) {
        			break;
        		}
                else if (count == 1){ //So the only remaining thread is the update of trigger status
                    triggerChangeIdList.removeAll(SendNotifyEventIndividualTask.ids); // We have to update duplicated blocks status even if we don't send them in events
                    idBlockToUpdate.add(triggerChangeIdList);
                    idBlockToUpdate.add(new ArrayList<>()); // An empty list means that the thread have to stop.
                }
        	}

            Date endSendNotifyEventForIndividualDate = new Date();
            
            if(nbNEIEventSent > 0 || nbNSEIEventSent > 0 || nbNAEIEventSent > 0)
            {
            	
            	
            	String diffDate = getDifferenceBetweenTwoDates(startSendNotifyEventForIndividualDate,endSendNotifyEventForIndividualDate);
            	if(nbNEIEventSent > 0) {
                    LogBatchOutput(nbNEIEventSent + " NEI event(s) sent");
                    try {
         				bfwOutput.flush();
         			} catch (IOException e) {
         				log.error(e);
         			}
            	}
            	if(nbNAEIEventSent > 0) {
                    LogBatchOutput(nbNAEIEventSent + " NAEI event(s) sent");
                    try {
         				bfwOutput.flush();
         			} catch (IOException e) {
         				log.error(e);
         			}
            	}
            	if(nbNSEIEventSent > 0) {
                    LogBatchOutputForSecurity(nbNSEIEventSent + " NSEI event(s) sent");
                    try {
         				bfwOutputSecurity.flush();
         			} catch (IOException e) {
         				log.error(e);
         			}
            	}
            	
            	LogBatchOutput("Sent Duration : " + diffDate);
            }
            else
            {
            	 LogBatchOutput("No event sent.");
                 try {
      				bfwOutput.flush();
      			} catch (IOException e) {
      				log.error(e);
      			}
            }



            try {
 				bfwOutput.flush();
 			} catch (IOException e) {
 				log.error(e);
 			}

            taskExecutor.shutdown();
        	
            if(ginAssociatedToBlockDTOListHm.size() > 0){
                LogBatchOutput("End sending individual events.");
                try {
     				bfwOutput.flush();
     			} catch (IOException e) {
     				log.error(e);
     			}
            }
        } else
        {
            LogBatchOutput(INFO + " No record found in TRIGGER_CHANGE_INDIVIDUS table.");
        }
        
        if(getHasError())
        {
            LogBatchOutput("Error(s) occured. See error log file.");
            try {
 				bfwError.flush();
 			} catch (IOException e) {
 				log.error(e);
 			}
        	
        }
    }
       
    static synchronized void addBlocksToSend(LinkedHashMap<String, ChangeData> ginListToBlockDTO, LinkedHashMap<String, List<Long>> ginListToTriggerChangeIdList) {
        for(String gin : ginListToBlockDTO.keySet()) {
            ginAssociatedToBlockDTOListHm.put(gin, ginListToBlockDTO.get(gin));
        }
        for(String gin : ginListToTriggerChangeIdList.keySet()) {
            ginLinkedToTriggerChangeIndIdListHm.put(gin, ginListToTriggerChangeIdList.get(gin));
        }
    }
 
    static void addIdToUpdate(List<Long> idList) {
        if(!idList.isEmpty()) {
            idBlockToUpdate.add(idList);
        }
    }

    static void resetThreadsValues() {
        SendNotifyEventIndividualTask.resetIds();
        UpdateStatusTask.resetCounter();
    }

    /**
     * Write error on screen and log file for security.
     * @param error : text of Error.
     */
    static synchronized void logBatchErrorForSecurity(String error, StringWriter sw) {
        log.info(error);
        try {
        	if(sw != null) {
        		bfwErrorSecurity.write(sdfLog.format(new Date()) + " - " + error + "\n" + sw.getBuffer().toString());
        	}
        	else {
        		bfwErrorSecurity.write(sdfLog.format(new Date()) + " - " + error);
        	}
        	bfwErrorSecurity.newLine();
        } catch (IOException e) {
            log.error(e);
           System.err.append("unable to write error Log " + e.getMessage());
        }
    }
    
    /**
     * Write error on screen and log file for security.
     * @param info : text of info.
     */
    static synchronized void LogBatchOutputForSecurity(String info) {
        log.info(info);
        try {
            bfwOutputSecurity.write(sdfLog.format(new Date()) + " - " + info);
            bfwOutputSecurity.newLine();
        } catch (IOException e) {
            log.error(e);
           System.err.append("unable to write info Log " + e.getMessage());
        }
    }
    
    /**
     * Write error on screen and log file.
     * @param error : text of Error.
     */
    static synchronized void LogBatchError(String error, StringWriter sw) {
        log.info(error);
        try {
        	if(sw != null) {
        		bfwError.write(sdfLog.format(new Date()) + " - " + error + "\n" + sw.getBuffer().toString());
        	}
        	else {
        		bfwError.write(sdfLog.format(new Date()) + " - " + error);
        	}
            bfwError.newLine();
        } catch (IOException e) {
            log.error(e);
           System.err.append("unable to write error Log " + e.getMessage());
        }
    }
    
    /**
     * Write error on screen and log file.
     * @param info : text of info.
     */
    static synchronized void LogBatchOutput(String info) {
        log.info(info);
        try {
            bfwOutput.write(sdfLog.format(new Date()) + " - " + info);
            bfwOutput.newLine();
        } catch (IOException e) {
            log.error(e);
           System.err.append("unable to write info Log " + e.getMessage());
        }
    }
    
    public TriggerChangeDS getTriggerChangeDS() {
        return triggerChangeDS;
    }

    public void setTriggerChangeDS(TriggerChangeDS triggerChangeDS) {
        this.triggerChangeDS = triggerChangeDS;
    }
    
    public TriggerChangeIndividusDS getTriggerChangeIndividusDS() {
        return triggerChangeIndividusDS;
    }

    public void setTriggerChangeIndividusDS(TriggerChangeIndividusDS triggerChangeIndividusDS) {
        this.triggerChangeIndividusDS = triggerChangeIndividusDS;
    }
	
	public VariablesDS getVariablesDS() {
		return variablesDS;
	}

	public void setVariablesDS(VariablesDS variablesDS) {
		this.variablesDS = variablesDS;
	}

    /**
     * Help message of batch.
     */
    public static void printHelpBatch() {
    	System.out.println("\n###\n" + "USER GUIDE: \nBatchSicUpdateNotification.sh -option:\n" 
                + "-l path  	: local path[OPTIONAL]\n"  
                + "-n number  	: instance number[OPTIONAL]\n"
                + "-i       	: individual event [OPTIONAL]\n"
                + "-f       	: Agency Company Or Member Event [OPTIONAL] \n ");
    }

	@Override
	protected void printHelp() {
		System.out.println("\n###\n" + "USER GUIDE: \nBatchSicUpdateNotification.sh -option:\n"
                + "-l path  	: local path[OPTIONAL]\n"  
                + "-i       	: individual event [OPTIONAL]\n"
                + "-f       	: Agency Company Or Member Event [OPTIONAL] \n "
                + "-s       	: Security Event [OPTIONAL] \n ");
		
	} 
	
	protected void closeOutLogs(){
		if(bfwOutput != null) {
        	try {
        		bfwOutput.close();

				if(bfwOutputSecurity != null) {
					bfwOutputSecurity.close();
				}
        	} catch (IOException ex) {
                log.error(ex);
                // ignore ... any significant errors should already have been
                // reported via an IOException from the final flush.
            }
    	}
	}
	
	protected void closeErrorLogs(){
		if(bfwError != null) {
    		try {
	            bfwError.close();
	            
				File f2 = new File(EVENTS_IND_DATA_DIR + LOGFILE_ERROR_IND + dateJour + LOG_EXTENSION);
				if(f2.length() == 0)
				{
				    f2.delete();
				}
				if(bfwErrorSecurity != null) {
					bfwErrorSecurity.close();
				}
				File f3 = new File(EVENTS_SECURITY_DATA_DIR + LOGFILE_ERROR_SECURITY + dateJour + LOG_EXTENSION);
				if(f3.length() == 0)
				{
				    f3.delete();
				}
    		} catch (IOException ex) {
                log.error(ex);
                // ignore ... any significant errors should already have been
                // reported via an IOException from the final flush.
            }
    	}
	}
	
	protected void openOutLogs(){
		File output;
		File outputSecurity = null;
    	
    	output = new File(EVENTS_IND_DATA_DIR + LOGFILE_OUTPUT_IND + dateJour+ LOG_EXTENSION);
    	outputSecurity = new File(EVENTS_SECURITY_DATA_DIR + LOGFILE_OUTPUT_SECURITY + dateJour+ LOG_EXTENSION);
    	try {
    		bfwOutput = new BufferedWriter(new FileWriter(output, true));
    		if(outputSecurity != null) {
    			bfwOutputSecurity = new BufferedWriter(new FileWriter(outputSecurity, true));
    		}
    	} catch (IOException e) {
    		log.error(e);
    	}
	}
	
	protected void openErrorLogs(){
    	File error;
		File errorSecurity = null;
    	
        error = new File(EVENTS_IND_DATA_DIR + LOGFILE_ERROR_IND + dateJour + LOG_EXTENSION);
        errorSecurity = new File(EVENTS_SECURITY_DATA_DIR + LOGFILE_ERROR_SECURITY + dateJour+ LOG_EXTENSION);
        try {
        	bfwError = new BufferedWriter(new FileWriter(error, true));
        	if(errorSecurity != null) {
        		bfwErrorSecurity = new BufferedWriter(new FileWriter(errorSecurity, true));
        	}
        } catch (IOException e) {
        	log.error(e);
        }
	}
	
	protected String getDifferenceBetweenTwoDates(Date startDate, Date endDate){
		
		//milliseconds
		long different = endDate.getTime() - startDate.getTime();
		
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		
		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;
		
		long elapsedSeconds = different / secondsInMilli;
		
		long elapsedMilli = different % secondsInMilli;
		
		return elapsedMinutes + " minutes, "+ elapsedSeconds + " seconds, " + elapsedMilli + " milliseconds. (total in milli : " + different + ")";
	}

	public static boolean getHasError() {
		return hasError;
	}

	public static void setHasError(Boolean hasErrorVal) {
		hasError = hasErrorVal;
	}

    public static synchronized void incrementNbNEIEventSent() {
		nbNEIEventSent++;
	}

    public static synchronized void incrementNbNAEIEventSent() {
		nbNAEIEventSent++;
	}

    public static synchronized void incrementNbNSEIEventSent() {
		nbNSEIEventSent++;
	}
}
