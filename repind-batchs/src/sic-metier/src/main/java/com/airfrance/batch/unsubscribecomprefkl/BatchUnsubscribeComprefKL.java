package com.airfrance.batch.unsubscribecomprefkl;

import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.common.exception.ParametersException;
import com.airfrance.batch.unsubscribecomprefkl.config.UnsubscribeComprefKLConfig;
import com.airfrance.batch.unsubscribecomprefkl.enums.ArgumentEnum;
import com.airfrance.batch.unsubscribecomprefkl.service.UnsubscribeComprefKLService;
import com.airfrance.batch.unsubscribecomprefkl.service.UnsubscribeComprefKLSummaryService;
import com.airfrance.batch.utils.IConstants;
import com.airfrance.batch.utils.RequirementEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BatchUnsubscribeComprefKL implements IBatch {
    private static final String INVALIDATION_JOB = "unsubscribeComprefKLJob";
    private static String inputPath;
    private static String outputPath;
    private static String fileName;

    @Autowired
    private UnsubscribeComprefKLService service;
    @Autowired
    private UnsubscribeComprefKLSummaryService unsubscribeComprefKLSummaryService;

    public static void main(String[] args) throws Exception {
        log.info("Start of BatchUnsubscribeComprefKL");
        parseArgs(args);
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(UnsubscribeComprefKLConfig.class)) {
            IBatch batch = (IBatch) ctx.getBean("batchUnsubscribeComprefKL");
            batch.execute();
        }
    }


    @Override
    public void execute() throws Exception {
        BatchStatus job = service.execute(inputPath, outputPath, fileName, INVALIDATION_JOB);

        if(job == BatchStatus.COMPLETED) {
            unsubscribeComprefKLSummaryService.createOutputFolder(Paths.get(outputPath));
            unsubscribeComprefKLSummaryService.createOutputFolder(Paths.get(outputPath).resolve("processed"));
            unsubscribeComprefKLSummaryService.writeListToFile(outputPath);
            unsubscribeComprefKLSummaryService.generateMetricFile(outputPath);
            log.info("[+] End BatchUnsubscribeComprefKL.");
            System.exit(0);
        } else {
            log.error("[-] BatchUnsubscribeComprefKL failed. Job status: {}", job);
            System.exit(1);
        }
    }


    private static void parseArgs(String[] args) throws ParametersException {
        if (ArrayUtils.isEmpty(args)){
            printHelp();
            throw new ParametersException("No arguments to the batch");
        }

        List<ArgumentEnum> argsProvided = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String s = args[i];

            if (s.contains(IConstants.ARGS_SEPARATOR)) {
                String currentArg = s.split(IConstants.ARGS_SEPARATOR)[1];
                try {
                    ArgumentEnum currArgEnum = ArgumentEnum.valueOf(currentArg);
                    argsProvided.add(currArgEnum);

                    switch (currArgEnum) {
                        case I:
                            inputPath = args[i + 1];
                            break;
                        case O:
                            outputPath = args[i + 1];
                            break;
                        case F:
                            fileName = args[i + 1];
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
                            BatchInvalidationEmailKl.sh -option:
                            -I input  Directory : [MANDATORY]
                            -O output Directory : [MANDATORY]"""
                    );
                }

            }
        }

        // Check if all mandatory arguments are filled
        for (ArgumentEnum currEnum : ArgumentEnum.values()) {
            if (RequirementEnum.MANDATORY.name().equals(currEnum.getRequirement().name())
                    && !argsProvided.contains(currEnum)) {
                printHelp();
                throw new ParametersException("Mandatory argument missing : " + currEnum);

            }
        }
    }

    protected static void printHelp() {
        log.info("""
			########HELP########
			USER GUIDE:
			BatchUnsubscribeComprefKL.sh -option:
			-I input  Directory : [MANDATORY]
			-O output Directory : [MANDATORY]
			"""
        );
    }
}
