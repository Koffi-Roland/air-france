package com.afklm.batch.migklsub.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MigrationKLSubCounterService {

    private final AtomicInteger ginCinSuccessCount = new AtomicInteger();
    private final AtomicInteger ginEmailSuccessCount = new AtomicInteger();
    private final AtomicInteger fbSuccessCount = new AtomicInteger();
    private final AtomicInteger maSuccessCount = new AtomicInteger();
    private final AtomicInteger duplicateLine = new AtomicInteger();
    private final AtomicInteger numberOfLine = new AtomicInteger();

    public void addGinCinSuccessCounter(){
        ginCinSuccessCount.incrementAndGet();
    }

    public void addGinEmailSuccessCount(){
        ginEmailSuccessCount.incrementAndGet();
    }

    public void addFbSuccessCount(){
        fbSuccessCount.incrementAndGet();
    }

    public void addMaSuccessCount(){
        maSuccessCount.incrementAndGet();
    }

    public void addDuplicateLine(){
        duplicateLine.incrementAndGet();
    }

    public void addNumberOfLine(){
        numberOfLine.incrementAndGet();
    }

    public String printCounter(){
        return "Success Gin and Cin : " + ginCinSuccessCount + " \n"
        		+ "Success Gin and Email : " + ginEmailSuccessCount + " \n"
        		+ "Success Flying blue created or updated : " + fbSuccessCount + " \n"
        		+ "Success MyAccount created or updated : " + maSuccessCount + " \n"
        		+ "Duplicate lines : " + duplicateLine + " \n"
        		+ "Number of file lines : " + numberOfLine + " \n";
    }
}
