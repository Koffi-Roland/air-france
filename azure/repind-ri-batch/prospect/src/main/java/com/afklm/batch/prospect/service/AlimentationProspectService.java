package com.afklm.batch.prospect.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Setter
@Getter
public class AlimentationProspectService {

    private String filePath;
    private final AtomicInteger SUCCESS = new AtomicInteger();
    private final AtomicInteger FAILED = new AtomicInteger();
    private final AtomicInteger TOTAL = new AtomicInteger();
    private final List<String> ERROR_MESSAGES = new ArrayList<>();

    public void addSuccessCount(){
        SUCCESS.incrementAndGet();
    }

    public void addFailedCount(){
        FAILED.incrementAndGet();
    }

    public void addTotalCount(){
        TOTAL.incrementAndGet();
    }

    public void storeMessage(String error) {
        ERROR_MESSAGES.add(error);
    }

    public String printCounter(){
        return "Processed creation or update : " + SUCCESS + " \n"
                + "Failed lines : " + FAILED + " \n"
                + "Number of file lines : " + TOTAL + " \n"
                + "\n"
                + " ** ERROR REPORT ** \n"
                + printError(ERROR_MESSAGES);
    }

    private String printError(List<String> errors) {
        String error = "";
        for (String err : errors) {
            error += err + "\n";
        }
        return error;
    }
}
