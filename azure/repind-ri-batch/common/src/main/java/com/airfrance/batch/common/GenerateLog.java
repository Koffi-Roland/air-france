package com.airfrance.batch.common;

import java.io.*;

public abstract class GenerateLog {

    //public String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    private String logName;
    private String dataDir;
    private String fileSuffix;

    // FILE NAMES
    private static final String LOGFILE_REJECTED_LINE = "_rejected_lines_";
    private static final String LOGFILE_REJECT = "_reject_";
    private static final String LOGFILE_SYNTHESIS = "_Synthesis_";
    private static final String LOGFILE_REPORT = "_Report_";
    private static final String LOG_FILE_INPUT_VALIDITY = "_input_validity_";

    // EXTENSIONS and DELIMITER
    private static final String CSV = ".csv";
    private static final String TXT = ".txt";
    private static final String SLASH = "/";
    private static final String UNDERSCORE = "_";

    // LOG FILE NAMES
    protected File synthesis;
    protected File reject;
    protected File rejected_lines;
    protected File report;
    protected File csv;
    protected File checkValidity;
    protected File inputFile;

    // BUFFER FOR LOG FILES
    protected BufferedWriter bfwSynthesis;
    protected BufferedWriter bfwReject;
    protected BufferedWriter bfwRejectLine;
    protected BufferedWriter bfwReport;
    protected BufferedWriter bfwValidity;

    public void openLogFiles() throws FileNotFoundException, IOException {
        this.synthesis = new File(getDataDir()+getLogName()+LOGFILE_SYNTHESIS+getFileSuffix()+TXT);
        this.reject = new File(getDataDir()+getLogName() + LOGFILE_REJECT +getFileSuffix()+TXT);
        this.rejected_lines = new File(getDataDir()+getLogName() +LOGFILE_REJECTED_LINE+getFileSuffix()+TXT);
        this.report = new File(getDataDir()+ getLogName() + LOGFILE_REPORT+getFileSuffix()+CSV);
        this.checkValidity = new File(getDataDir() + getLogName() + LOG_FILE_INPUT_VALIDITY +getFileSuffix()+TXT);

        this.bfwSynthesis = new BufferedWriter(new FileWriter(this.synthesis, true));
        this.bfwReject = new BufferedWriter(new FileWriter(this.reject, true));
        this.bfwRejectLine = new BufferedWriter(new FileWriter(this.rejected_lines, true));
        this.bfwReport = new BufferedWriter(new FileWriter(this.report, true));
        this.bfwValidity = new BufferedWriter(new FileWriter(this.checkValidity, true));
    }


    public void closeLogFiles() throws IOException{
        this.bfwReject.close();
        this.bfwRejectLine.close();
        this.bfwSynthesis.close();
        this.bfwReport.close();
        this.bfwValidity.close();

    }

    public GenerateLog (){
    }

    public GenerateLog (String dir, String logName)
    {
        setDataDir(dir);
        setLogName(logName);
    }

    public File getSynthesis() {
        return synthesis;
    }

    public File getReject() {
        return reject;
    }

    public File getRejected_lines() {
        return rejected_lines;
    }

    public File getReport() {
        return report;
    }

    public File getCsv() {
        return csv;
    }

    public BufferedWriter getBfwSynthesis() {
        return bfwSynthesis;
    }
    public BufferedWriter getBfwReject() {
        return bfwReject;
    }
    public BufferedWriter getBfwRejectLine() {
        return bfwRejectLine;
    }
    public BufferedWriter getBfwReport() {
        return bfwReport;
    }

    public BufferedWriter getBfwValidity() {
        return bfwValidity;
    }

    public void setDataDir(String invEmailDataDir) {
        this.dataDir = invEmailDataDir;
    }

    public String getDataDir() {
        return this.dataDir;
    }
    public void setLogName(String logName) {
        this.logName = logName;
    }
    public String getLogName() {
        return logName;
    }

    public void setFileSuffix(String fileSuffix) {
        // RECEIVED: something like /app/REPIND/data/.../<fileName>_<date>
        String[] s = fileSuffix.split(SLASH);
        this.fileSuffix = (s[s.length-1].split(UNDERSCORE))[1];
    }
    public String getFileSuffix() {
        return fileSuffix;
    }
}
