package com.airfrance.jraf.batch.common;


import com.airfrance.ref.exception.RefException;
import org.springframework.context.ApplicationContext;

public abstract class BatchArgs implements IBatch {
    
    protected String batchName;

    private static ApplicationContext context;

    
    protected static final String BATCH_EXECUTED_SUCCESSFULLY = "Batch executed successfully.";
    protected static final String MANDATORY_ARGUMENT_MISSING = "Mandatory argument missing : ";
    protected static final String NO_ARGUMENTS_TO_THE_BATCH = "No arguments to the batch";
    protected static final String ARGUMENT_NOT_VALID = "Argument not valid";
    
    protected abstract void parseArgs(String[] args) throws RefException;
    protected abstract void printHelp();
        
    public static void setContext(ApplicationContext context) {
        BatchArgs.context = context;
    }

    public static ApplicationContext getContext() {
        return context;
    }
    
    protected void setBatchName(String batchName) {
        this.batchName = batchName;
    }
    protected String getBatchName(){
        return this.batchName;
    }
}
