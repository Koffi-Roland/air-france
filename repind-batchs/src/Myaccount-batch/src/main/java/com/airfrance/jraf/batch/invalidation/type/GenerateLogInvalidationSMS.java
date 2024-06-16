package com.airfrance.jraf.batch.invalidation.type;


public class GenerateLogInvalidationSMS extends GenerateLog {

    protected static final String invSMSDataDir = "/app/REPIND/data/TELECOMS/INVALIDATION/";
    private static final String logName = "BatchInvalidationSMS";
      
    public GenerateLogInvalidationSMS ()
    {
        super(invSMSDataDir, logName);
    }
    
    public GenerateLogInvalidationSMS (String dir, String logName)
    {
        super(dir, logName);
    }

}