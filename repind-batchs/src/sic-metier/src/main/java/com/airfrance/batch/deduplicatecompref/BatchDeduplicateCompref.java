package com.airfrance.batch.deduplicatecompref;

import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.common.enums.ArgsDeduplicateEnum;
import com.airfrance.batch.common.exception.ParametersException;
import com.airfrance.batch.deduplicatecompref.config.DeduplicateComprefConfig;
import com.airfrance.batch.deduplicatecompref.service.DeduplicateComprefService;
import com.airfrance.batch.utils.IConstants;
import com.airfrance.batch.utils.RequirementEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Deduplicate communication preference batch
 */
@Slf4j(topic = "BatchDeduplicateCompref")
public class BatchDeduplicateCompref implements IBatch{

    private static final String  DEDUPLICATE_COMPREF_JOB = "deduplicateComprefJob";
    private static   String domain;
    private static   String comGroupType;
    private static   String comType;



    /**
     * Deduplicate communication preferences service - inject by spring
     */
    @Autowired
    private  DeduplicateComprefService deduplicateComprefService;

    public static void main(String[] args) throws Exception {

        log.info("Start of Deduplicate communication preferences batch");
        parseArgs(args);
        try{
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DeduplicateComprefConfig.class);
            IBatch service = (IBatch) ctx.getBean("batchDeduplicateCompref");
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

        // Execute job1 : Deduplicate communication preferences and delete market language
        BatchStatus statusJobDeduplicateCompref =  deduplicateComprefService.execute(comType,comGroupType,domain,DEDUPLICATE_COMPREF_JOB);

        if(statusJobDeduplicateCompref == BatchStatus.COMPLETED) {
            log.info("[+] End BatchDeduplicateCompref.");
            System.exit(0);
        } else {
            log.error("[-] BatchDeduplicateCompref failed. deduplicate job status: {}", statusJobDeduplicateCompref);
            System.exit(1);
        }
    }

    /**
     * Parse Arguments
     *
     * @param args arguments
     * @throws ParametersException Parameters Exception
     */
    private static void parseArgs(String[] args) throws ParametersException {
        if (ArrayUtils.isEmpty(args)){
            printHelp();
            throw new ParametersException("No arguments to the batch");
        }

        List<ArgsDeduplicateEnum> argsProvided = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String s = args[i];

            if (s.contains(IConstants.ARGS_SEPARATOR)) {
                String currentArg = s.split(IConstants.ARGS_SEPARATOR)[1];
                try {
                    ArgsDeduplicateEnum currArgEnum = ArgsDeduplicateEnum.valueOf(currentArg);
                    argsProvided.add(currArgEnum);

                    switch (currArgEnum) {
                        case G:
                            comGroupType = args[i + 1];
                            break;
                        case D:
                            domain = args[i + 1];
                            break;
                        case T:
                            comType = args[i + 1];
                            break;
                        default:
                            break;
                    }
                }

                catch (IllegalArgumentException e) {
                    log.error(e.getMessage());
                    printHelp();
                    throw new ParametersException(
                            """
                            argument are not valid
                            USER GUIDE:
                            BatchDeduplicateCompref.sh -option:
                            -G com_group_type : [MANDATORY]
                            -D domain : [MANDATORY]
                            -T com_type : [MANDATORY]
                            """
                    );
                }

            }
        }

        // Check if all mandatory arguments are filled
        for (ArgsDeduplicateEnum currEnum : ArgsDeduplicateEnum.values()) {
            if (RequirementEnum.MANDATORY.name().equals(currEnum.getRequirement().name())
                    && !argsProvided.contains(currEnum)) {
                printHelp();
                throw new ParametersException("Mandatory argument missing : " + currEnum);

            }
        }
    }

    /**
     * Print help for the batch deduplicate communication preferences
     */
    protected static void printHelp() {
        log.info("""
			########HELP########
			USER GUIDE:
			BatchDeduplicateCompref.sh -option:
			-G com_group_type : [MANDATORY]
			-D domain : [MANDATORY]
			-T com_type : [MANDATORY]
			"""
        );
    }
}
