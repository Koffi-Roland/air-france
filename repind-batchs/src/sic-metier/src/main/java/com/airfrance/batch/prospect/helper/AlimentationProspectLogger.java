package com.airfrance.batch.prospect.helper;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AlimentationProspectLogger {

    private String csvFileName;
    private String path;

    public AlimentationProspectLogger(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public void write(String fileName, String data){
        File csvOutputFile = getFile(fileName);
        try {
            synchronized (csvOutputFile.getCanonicalPath().intern()){
                createFile(csvOutputFile);
                write(csvOutputFile, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFile(File csvOutputFile) throws IOException {
        if(!csvOutputFile.exists()){
            write(csvOutputFile, "");
        };
    }


    public void write(File iCsvFile , String iData) throws IOException {
        try(FileWriter pw = new FileWriter(iCsvFile, true)) {
            pw.append(iData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile(String iFileName){
        return new File(path + iFileName + "_" + csvFileName);
    }
}
