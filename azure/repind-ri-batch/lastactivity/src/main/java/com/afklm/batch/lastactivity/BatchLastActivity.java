package com.afklm.batch.lastactivity;

import com.afklm.batch.lastactivity.config.LastActivityConfig;
import com.afklm.batch.lastactivity.service.LastActivityService;
import com.airfrance.batch.common.IBatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Last activity batch
 */
public class BatchLastActivity implements IBatch{
    private static final Log log = LogFactory.getLog(BatchLastActivity.class);

    /**
     * Last activity service - inject by spring
     */
    @Autowired
    private LastActivityService lastActivityService;

    public static void main(String[] args) throws Exception {

        log.info("Start of Last activity batch");
        try{
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(LastActivityConfig.class);
            IBatch service = (IBatch) ctx.getBean("batchLastActivity");
            service.execute();
        }catch (Exception e) {
            log.error("Batch execution failed.. ", e);
            System.exit(1);
        }
        System.exit(0);
        log.info("End BatchCleanup.");
    }

    /**
     * Execution of the batch
     *
     * @throws Exception exception
     */
    @Override
    public void execute() throws Exception {
        lastActivityService.execute();
    }
}
