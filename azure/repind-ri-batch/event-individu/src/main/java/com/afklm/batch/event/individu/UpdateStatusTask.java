package com.afklm.batch.event.individu;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.service.tracking.internal.TriggerChangeIndividusDS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static com.afklm.batch.event.individu.BatchIndividualUpdateNotification.LogBatchOutput;

@Component
public class UpdateStatusTask implements Runnable{

    @Autowired
    private TriggerChangeIndividusDS triggerChangeIndividusDS;

    private LinkedBlockingQueue<List<Long>> entriesQueue;

    private String taskName;

    private boolean isFinished;

    private static int nbRecordsUpdated = 0;

    //Number of blocks in 1 request
    private static int ID_LIST_MAX_SIZE = 10;
    private static int ORACLE_MAX_REQUEST_SIZE = 1000;

    public void setEntriesQueue(LinkedBlockingQueue<List<Long>> queue) {
        this.entriesQueue = queue;
    }

    public void setTaskName(String name) {
        this.taskName = name;
    }

    public void setIdListMaxSize(int newSize) {
        if(newSize <= ORACLE_MAX_REQUEST_SIZE) {
            ID_LIST_MAX_SIZE = newSize;
        }
        else {
            LogBatchOutput("The list size can't be greater than 1000 because of database limits. The new value will be reduced to 1000");
            ID_LIST_MAX_SIZE = ORACLE_MAX_REQUEST_SIZE;
        }

    }

    @Override
    public void run() {
        LogBatchOutput("Start " + taskName);
        isFinished = false;
        // Update treated records status
        while(!isFinished) {
            try {
                List<Long> entry = entriesQueue.take();
                if(!entry.isEmpty()) {
                    // Set TRIGGER_CHANGE_INDIVIDUS records as treated
                    if(entry.size()>ID_LIST_MAX_SIZE) { //Handle Oracle Limit
                        for(int i=0; i<entry.size(); i+=ID_LIST_MAX_SIZE) {
                            List<Long> temp =  entry.subList(i, Math.min(i+ID_LIST_MAX_SIZE, entry.size()));
                            triggerChangeIndividusDS.updateStatus(temp);
                        }
                    }
                    else {
                        triggerChangeIndividusDS.updateStatus(entry);
                    }

                    LogBatchOutput(taskName + " ---> " + entry.size() + " record updated to change status 'P' in TRIGGER_CHANGE_INDIVIDUS table" );
                    nbRecordsUpdated += entry.size();
                    LogBatchOutput(nbRecordsUpdated + " updates have been made so far.");
                }
                else {
                    isFinished = true;
                }
            } catch (InterruptedException | JrafDomainException e) {
                e.printStackTrace();
            }
        }
        LogBatchOutput(taskName + " ---> End of database updating. A total of " + nbRecordsUpdated + " records has been updated.");
    }

    public static void resetCounter() {
        nbRecordsUpdated = 0;
    }
}
