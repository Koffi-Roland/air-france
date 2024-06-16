package com.airfrance.batch.invalidation.type;

public class GenerateLogInvalidationEmail extends GenerateLog {

    protected static final String invEmailDataDir = "/app/REPIND/data/QUALIF_ADRESSES/CRMPUSH_R3/";
    private static final String logName = "BatchInvalidationEmail";

    public GenerateLogInvalidationEmail ()
    {
        super(invEmailDataDir,logName);
    }

    public GenerateLogInvalidationEmail (String dir, String logName)
    {
        super(dir, logName);
    }
}
