package com.airfrance.batch.compref.fixafcompref.logger;

import com.airfrance.batch.compref.fixafcompref.enums.FileNameEnum;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class FixAfComPrefLogger {

    private String[] title;

    private String csvFileName;

    private String path;

    public FixAfComPrefLogger(String csvFileName, String iPath , String[] iTitle) {
        this.csvFileName = csvFileName;
        this.title = iTitle;
        this.path = iPath;
    }

    public void write(FileNameEnum iFileNameEnum, String[] iData){
        File csvOutputFile = getFile(iFileNameEnum.getValue());
        try {
            synchronized (csvOutputFile.getCanonicalPath().intern()){
                createFile(csvOutputFile);
                write(csvOutputFile , iData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFile(File csvOutputFile) throws IOException {
        if(!csvOutputFile.exists()){
            write(csvOutputFile , title );
        };
    }

    private void write(File iCsvFile , String[] iData) throws IOException {
        if(iData != null){
            StringJoiner stringJoiner = new StringJoiner("," , "" , "\n");
            Stream.of(iData).forEach(stringJoiner::add);
            write(iCsvFile , stringJoiner.toString());
        }
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
