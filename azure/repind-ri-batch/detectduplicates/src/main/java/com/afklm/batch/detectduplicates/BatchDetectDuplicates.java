package com.afklm.batch.detectduplicates;

import com.afklm.batch.detectduplicates.config.DetectDuplicatesConfig;
import com.afklm.batch.detectduplicates.enums.BatchDetectDuplicatesEnum;
import com.afklm.batch.detectduplicates.service.DetectDuplicatesService;
import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.common.exception.ParametersException;
import com.airfrance.batch.common.exception.ThreadBusinessProcessException;
import com.airfrance.batch.common.utils.IConstants;
import com.airfrance.batch.common.utils.RequirementEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class BatchDetectDuplicates implements IBatch {
    private static final String DAY = new SimpleDateFormat("ddMMyyyy").format(new Date());
    private static final String CSV = ".csv";
    private static String outputDir;
    private static final String RESULT_FILENAME = "detect_duplicates_result_" + DAY + CSV;

    @Autowired
    private DetectDuplicatesService detectDuplicatesService;

    @Autowired
    private JdbcTemplate jdbcTemplateSicOds;

    public static void main(String[] args) throws Exception {
        log.info("Start of BatchDetectDuplicates");
        parseArgs(args);
        log.info(String.format("OutputDir : %s, outputfile: %s", outputDir, RESULT_FILENAME));
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(DetectDuplicatesConfig.class)) {
            IBatch batch = (IBatch) ctx.getBean("batchDetectDuplicates");
            batch.execute();
        }
        log.info("End BatchDetectDuplicates.");
    }

    @Override
    public void execute() throws Exception {
        Instant start = Instant.now();
        detectDuplicatesService.selectDuplicatedCandidatesByNomPrenom();

        detectDuplicatesService.launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/drop.sql");
        detectDuplicatesService.launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_same_nomprenom.sql");
        detectDuplicatesService.launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_same_nomprenom_gin.sql");
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(() -> {
            try {
                detectDuplicatesService.launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_email_sgin_no_dup.sql");
                detectDuplicatesService.launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_same_nomprenom_email.sql");
            } catch (IOException e) {
                throw new ThreadBusinessProcessException(e.getMessage());
            }
        });
        executor.execute(() -> {
            try {
                detectDuplicatesService.launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_telecom_sgin_no_dup.sql");
                detectDuplicatesService.launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_same_nomprenom_telecom.sql");
            } catch (IOException e) {
                throw new ThreadBusinessProcessException(e.getMessage());
            }
        });
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

        detectDuplicatesService.launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_same_nomprenom_and_telecom_and_email.sql");

        executor = Executors.newCachedThreadPool();
        executor.execute(() -> {
            try {
                detectDuplicatesService.launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/copy_no_duplicate_element_email_and_telecom.sql");
                detectDuplicatesService.removeDuplicatesRow();
            } catch (IOException e) {
                throw new ThreadBusinessProcessException(e.getMessage());
            }
        });
        executor.execute(() -> {
            try {
                detectDuplicatesService.launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/table_same_nomprenom_and_email_or_telecom.sql");
                detectDuplicatesService.launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/copy_no_duplicate_element.sql");
                detectDuplicatesService.removeDuplicatesRowForEmailOrTelecom();
            } catch (IOException e) {
                throw new ThreadBusinessProcessException(e.getMessage());
            }
        });
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

        detectDuplicatesService.launchPlSqlScript("metier/scripts/SQL/DETECT_DUPLICATES/update_table_same_nomprenom_candidates.sql");

        detectDuplicatesService.writeResultFile(outputDir, RESULT_FILENAME);

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        log.info(String.format("Total duration : %d sec", duration.getSeconds()));
    }


    protected static void parseArgs(String[] args) throws ParametersException {
        if (args == null || args.length == 0 || args.length % 2 != 0) {
            printHelp();
            throw new ParametersException("No arguments to the batch");
        }

        List<BatchDetectDuplicatesEnum> argsProvided = new ArrayList<>();
        for(int i = 0; i < args.length; i++){
            String s = args[i];
            if (s.contains(IConstants.ARGS_SEPARATOR)) {
                // Type of arg
                String currentArg = s.split(IConstants.ARGS_SEPARATOR)[1];
                try {
                    BatchDetectDuplicatesEnum currArgEnum = BatchDetectDuplicatesEnum
                            .valueOf(currentArg);
                    argsProvided.add(currArgEnum);
                    if (currArgEnum == BatchDetectDuplicatesEnum.O) {
                        outputDir = args[i + 1];
                    }
                }
                catch (IllegalArgumentException e) {
                    printHelp();
                    throw new ParametersException("argument are not valid\\n" +
                            "USER GUIDE: \nBatchDetectDuplicates.sh -option:\n " +
                            "-O path of output directory : [MANDATORY]");
                }
            }
        }
        // Check if all mandatory args are filled in
        for (BatchDetectDuplicatesEnum currEnum : BatchDetectDuplicatesEnum
                .values()) {
            if (currEnum.getRequirement().equals(RequirementEnum.MANDATORY) && !argsProvided.contains(currEnum)) {
                    printHelp();
                    throw new ParametersException("Mandatory argument missing : " + currEnum);
            }
        }
    }

    protected static void printHelp() {
        log.info("\n########HELP########\n" +
                "USER GUIDE: \nBatchDetectDuplicates.sh -option:\n " +
                "-O path of output directory");
    }
}
