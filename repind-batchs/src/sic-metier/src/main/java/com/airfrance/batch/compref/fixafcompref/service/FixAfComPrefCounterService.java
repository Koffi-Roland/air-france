package com.airfrance.batch.compref.fixafcompref.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FixAfComPrefCounterService {

    private final AtomicInteger ginSuccessCount = new AtomicInteger();
    private final AtomicInteger numberOfLine = new AtomicInteger();
    private final AtomicInteger fbSuccessCount = new AtomicInteger();
    private final AtomicInteger maSuccessCount = new AtomicInteger();

    public void addGinSuccessCounter(){
        ginSuccessCount.incrementAndGet();
    }

    public void addNumberOfLineCounter(){
        numberOfLine.incrementAndGet();
    }

    public void addFbSuccessCounter(){
        fbSuccessCount.incrementAndGet();
    }

    public void addMaSuccessCounter(){
        maSuccessCount.incrementAndGet();
    }

    public String printCounter(){
        return "Success Flying blue created/updated : " + fbSuccessCount + " \n"
                + "Success MyAccount created/updated : " + maSuccessCount + " \n"
                + "Number of file lines : " + numberOfLine + " \n";
    }
}
