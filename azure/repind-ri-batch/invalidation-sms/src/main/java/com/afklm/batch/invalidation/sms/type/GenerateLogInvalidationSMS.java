package com.afklm.batch.invalidation.sms.type;


import com.airfrance.batch.common.GenerateLog;

public class GenerateLogInvalidationSMS extends GenerateLog {

    protected static final String invSMSDataDir = "/app/REPIND/data/TELECOMS/INVALIDATION/";
    private static final String logName = "BatchInvalidationSMS";
      
    public GenerateLogInvalidationSMS()
    {
        super(invSMSDataDir, logName);
    }
    
    public GenerateLogInvalidationSMS(String dir, String logName)
    {
        super(dir, logName);
    }

}
