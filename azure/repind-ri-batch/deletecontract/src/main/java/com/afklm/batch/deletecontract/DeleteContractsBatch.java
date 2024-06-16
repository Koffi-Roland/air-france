package com.afklm.batch.deletecontract;

import com.afklm.batch.deletecontract.config.DeleteContractsBatchConfig;
import com.afklm.batch.deletecontract.service.DeleteContractsBatchService;
import com.airfrance.batch.common.IBatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DeleteContractsBatch implements IBatch{
    private static final Log log = LogFactory.getLog(DeleteContractsBatch.class);

    @Autowired
    private DeleteContractsBatchService service;

    public static void main(String[] args) throws Exception {

        log.info("Start of DeleteContractsBatch");
        try{
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DeleteContractsBatchConfig.class);
            IBatch service = (IBatch) ctx.getBean("deleteContractsBatch");
            service.execute();
        }catch (Exception e) {
            log.error("Batch execution failed.. ", e);
            System.exit(1);
        }
        System.exit(0);
        log.info("End DeleteContractsBatch");
    }


    @Override
    public void execute() throws Exception {
        service.execute();
    }
}
