package com.airfrance.batch.cleanupcreditcards;

import com.airfrance.batch.cleanupcreditcards.config.CleanupCreditCardsConfig;
import com.airfrance.batch.cleanupcreditcards.service.CleanupCreditCardsService;
import com.airfrance.batch.common.IBatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BatchCleanupCreditCards  implements IBatch{
    private static final Log log = LogFactory.getLog(BatchCleanupCreditCards.class);

    @Autowired
    private CleanupCreditCardsService service;

    public static void main(String[] args) throws Exception {

        log.info("Start of BatchCleanupCreditCards");

        try{
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CleanupCreditCardsConfig.class);
            IBatch service = (IBatch) ctx.getBean("batchCleanupCreditCards");
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
