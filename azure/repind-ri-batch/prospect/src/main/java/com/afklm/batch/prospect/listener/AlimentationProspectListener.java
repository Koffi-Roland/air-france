package com.afklm.batch.prospect.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class AlimentationProspectListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        // nothing before step
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
