package com.afklm.batch.injestadhocdata.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("initStepExecutionListener")
@Slf4j
@StepScope
public class InitStepExecutionListener implements StepExecutionListener {

    @Value("#{stepExecutionContext['fileName']}")
    private String filename;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("StepExecutionListener - beforeStep : {} {}",stepExecution.getStepName() , filename);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getFailureExceptions().forEach(e -> log.info("Exception after step : {}",e));
        log.info("After Step : {} ",stepExecution.getStatus());
        return stepExecution.getExitStatus();
    }
}
