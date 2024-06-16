package com.airfrance.batch.kpicalculation;

import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.kpicalculation.config.KPICalculationConfig;
import com.airfrance.batch.kpicalculation.service.KPICalculationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BatchKPICalculation implements IBatch{
    private static final Log log = LogFactory.getLog(BatchKPICalculation.class);

    @Autowired
    private KPICalculationService service;

    public static void main(String[] args) throws Exception {

        log.info("Start of BatchKPICalculation");
        try{
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(KPICalculationConfig.class);
            IBatch service = (IBatch) ctx.getBean("batchKpiCalculation");
            service.execute();
        }catch (Exception e) {
            log.error("Batch execution failed.. ", e);
            System.exit(1);
        }
        System.exit(0);
        log.info("End BatchKPICalculation.");
    }


    @Override
    public void execute() throws Exception {
        service.execute();
    }
}
