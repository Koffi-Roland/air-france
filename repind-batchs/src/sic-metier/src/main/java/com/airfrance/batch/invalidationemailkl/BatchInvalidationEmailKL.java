package com.airfrance.batch.invalidationemailkl;

import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.common.enums.ArgsEnum;
import com.airfrance.batch.common.exception.ParametersException;
import com.airfrance.batch.invalidationemailkl.config.InvalidationEmailKlConfig;
import com.airfrance.batch.invalidationemailkl.service.FileService;
import com.airfrance.batch.invalidationemailkl.service.InvalidationEmailKLSummaryService;
import com.airfrance.batch.invalidationemailkl.service.InvalidationEmailKlService;
import com.airfrance.batch.utils.IConstants;
import com.airfrance.batch.utils.RequirementEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BatchInvalidationEmailKL implements IBatch {
    private static final String INVALIDATION_JOB = "invalidationEmailJob";
    private static String inputPath;
    private static String outputPath;

    @Autowired
    private InvalidationEmailKlService service;
    @Autowired
    private InvalidationEmailKLSummaryService invalidationEmailKLSummaryService;
    @Autowired
    private FileService fileService;

    public static void main(String[] args) throws Exception {
        log.info("Start of BatchInvalidationEmailKL");
        parseArgs(args);
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(InvalidationEmailKlConfig.class)) {
            IBatch batch = (IBatch) ctx.getBean("batchInvalidationEmailKL");
            batch.execute();
        }
    }


    @Override
    public void execute() throws Exception {
        BatchStatus job = service.execute(inputPath, outputPath, INVALIDATION_JOB);

        if(job == BatchStatus.COMPLETED) {
            invalidationEmailKLSummaryService.writeListToFile(outputPath);
            invalidationEmailKLSummaryService.generateMetricFile(outputPath);
            fileService.deleteFileFromBaseDirectory(fileService.getSourceInput());
            log.info("[+] End BatchInvalidationEmailKL.");
            System.exit(0);
        } else {
            log.error("[-] BatchInvalidationEmailKL failed. Job status: {}", job);
            System.exit(1);
        }
    }


    private static void parseArgs(String[] args) throws ParametersException {
        if (ArrayUtils.isEmpty(args)){
            printHelp();
            throw new ParametersException("No arguments to the batch");
        }

        List<ArgsEnum> argsProvided = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String s = args[i];

            if (s.contains(IConstants.ARGS_SEPARATOR)) {
                String currentArg = s.split(IConstants.ARGS_SEPARATOR)[1];
                try {
                    ArgsEnum currArgEnum = ArgsEnum.valueOf(currentArg);
                    argsProvided.add(currArgEnum);

                    switch (currArgEnum) {
                        case I:
                            inputPath = args[i + 1];
                            break;
                        case O:
                            outputPath = args[i + 1];
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
        for (ArgsEnum currEnum : ArgsEnum.values()) {
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
			BatchInvalidationEmailKl.sh -option:
			-I input  Directory : [MANDATORY]
			-O output Directory : [MANDATORY]
			"""
        );
    }
}
