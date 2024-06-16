package com.afklm.batch.migklsub.policy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

@Slf4j
public class DailySkipPolicy implements SkipPolicy {

    @Override
    public boolean shouldSkip(Throwable throwable, int i) throws SkipLimitExceededException {
        log.info("Should skip");
        if(throwable instanceof RuntimeException){
            return true;
        }
        return false;
    }
}
