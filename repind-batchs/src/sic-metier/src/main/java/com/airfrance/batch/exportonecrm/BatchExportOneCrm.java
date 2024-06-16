package com.airfrance.batch.exportonecrm;

import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.exportonecrm.config.ExportOneCrmConfig;
import com.airfrance.batch.exportonecrm.service.ExportOneCrmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class BatchExportOneCrm implements IBatch {

    @Autowired
    private ExportOneCrmService service;

    public static void main(String[] args) throws Exception {

        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ExportOneCrmConfig.class)) {
            IBatch batch = (IBatch) ctx.getBean("batchExportOneCrm");
            batch.execute();
        }
    }

    @Override
    public void execute() throws Exception {
        log.info("[+] Start of BatchExportOneCrm");

        BatchStatus statusJob = service.execute();

        if(statusJob == BatchStatus.COMPLETED) {
            log.info("[+] End BatchExportOneCrm.");
            System.exit(0);
        } else {
            log.error("[-] BatchExportOneCrm failed. job status: {}", statusJob);
            System.exit(1);
        }


    }
}
