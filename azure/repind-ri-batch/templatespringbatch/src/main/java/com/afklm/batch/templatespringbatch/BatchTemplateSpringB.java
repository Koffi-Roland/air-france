package com.afklm.batch.templatespringbatch;

import com.airfrance.batch.common.IBatch;
import com.afklm.batch.templatespringbatch.config.TemplateSpringBatchConfig;
import com.afklm.batch.templatespringbatch.service.TemplateSpringBatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BatchTemplateSpringB implements IBatch{
    private static final Log log = LogFactory.getLog(BatchTemplateSpringB.class);

    @Autowired
    private TemplateSpringBatchService service;

    public static void main(String[] args) throws Exception {

        log.info("Start of BatchTemplateSpringB");
        try{
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TemplateSpringBatchConfig.class);
            IBatch service = (IBatch) ctx.getBean("batchTemplateSpringB");
            service.execute();
        }catch (Exception e) {
            log.error("Batch execution failed.. ", e);
            System.exit(1);
        }
        System.exit(0);
        log.info("End BatchCleanupCreditCards.");
    }


    @Override
    public void execute() throws Exception {
        service.execute();
    }
}
