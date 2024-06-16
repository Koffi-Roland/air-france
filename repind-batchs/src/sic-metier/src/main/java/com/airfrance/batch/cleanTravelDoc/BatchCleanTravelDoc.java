package com.airfrance.batch.cleanTravelDoc;

import com.airfrance.batch.cleanTravelDoc.config.CleanTravelDocConfig;
import com.airfrance.batch.cleanTravelDoc.service.CleanTravelDocService;
import com.airfrance.batch.common.IBatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BatchCleanTravelDoc implements IBatch {

    private static final Log log = LogFactory.getLog(BatchCleanTravelDoc.class);

    @Autowired
    private CleanTravelDocService service;

    public static void main(String[] args) throws Exception {

        log.info("Start of BatchCleanTravelDoc");

        try {
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CleanTravelDocConfig.class);
            IBatch that = (IBatch) ctx.getBean("batchCleanTravelDoc");
            that.execute();
        } catch (Exception e) {
            log.error("Batch execution failed.. ", e);
            System.exit(1);
        }
        System.exit(0);
        log.info("End BatchCleanTravelDoc.");

    }

    @Override
    public void execute() throws Exception {
        service.execute();
    }
}
