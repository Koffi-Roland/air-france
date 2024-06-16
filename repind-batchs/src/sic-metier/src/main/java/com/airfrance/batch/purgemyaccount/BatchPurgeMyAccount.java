package com.airfrance.batch.purgemyaccount;

import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.purgemyaccount.config.PurgeMyAccountConfig;
import com.airfrance.batch.purgemyaccount.service.PurgeMyAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class BatchPurgeMyAccount implements IBatch {
    private static final String PHYSICAL_DELETE_JOB = "purgeMyAccountPhysicalJob";
    private static final String LOGICAL_DELETE_JOB = "purgeMyAccountLogicalJob";


    @Autowired
    private PurgeMyAccountService service;

    public static void main(String[] args) throws Exception {

        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(PurgeMyAccountConfig.class)) {
            IBatch batch = (IBatch) ctx.getBean("batchPurgeMyAccount");
            batch.execute();
        }
    }

    @Override
    public void execute() throws Exception {
        log.info("[+] Start of BatchPurgeMyAccount");

        // Execute job1 : purgeMyAccountPhysicalJob
        BatchStatus statusJobPhysical = service.execute(PHYSICAL_DELETE_JOB);

        // Execute job2 : purgeMyAccountLogicalJob
        BatchStatus statusJobLogical = service.execute(LOGICAL_DELETE_JOB);
        if(statusJobPhysical == BatchStatus.COMPLETED && statusJobLogical == BatchStatus.COMPLETED) {
            log.info("[+] End BatchPurgeMyAccount.");
            System.exit(0);
        } else {
            log.error("[-] BatchPurgeMyAccount failed. Physical job status: {}, Logical job status: {}", statusJobPhysical, statusJobLogical);
            System.exit(1);
        }


    }
}
