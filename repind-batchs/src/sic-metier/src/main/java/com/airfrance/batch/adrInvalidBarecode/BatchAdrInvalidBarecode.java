package com.airfrance.batch.adrInvalidBarecode;

import com.airfrance.batch.adrInvalidBarecode.config.AdrInvalidBarecodeConfig;
import com.airfrance.batch.adrInvalidBarecode.service.AdrInvalidBarecodeService;
import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.common.exception.ParametersException;
import com.airfrance.batch.automaticmerge.enums.BatchAutomaticMergeEnum;
import com.airfrance.batch.utils.IConstants;
import com.airfrance.batch.utils.RequirementEnum;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BatchAdrInvalidBarecode implements IBatch{
    private static final Log log = LogFactory.getLog(BatchAdrInvalidBarecode.class);

    private static String inputPath;
    private static String outputPath;
    private static final String TXT = ".txt";
    private static final String CSV = ".csv";
    private static final String DAY = new SimpleDateFormat("ddMMyyyy").format(new Date());
    private static final String RESULT_FILENAME = "invalid_barecode_result_";
    private static final String FILENAME_PATTERN ="RINVA1_";
    private static final String METRIC_FILENAME = "invalid_barecode_metric_";
    @Autowired
    private AdrInvalidBarecodeService adrInvalidBarecodeService;

    public static void main(String[] args) throws Exception {

        log.info("Start of BatchAdrInvalidBarecode");
        parseArgs(args);
        try{
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AdrInvalidBarecodeConfig.class);
            IBatch service = (IBatch) ctx.getBean("batchAdrInvalidBarecode");
            service.execute();
        }catch (Exception e) {
            log.error("Batch execution failed.. ", e);
            System.exit(1);
        }
        System.exit(0);
        log.info("End BatchAdrInvalidBarecode.");
    }


    @Override
    public void execute() throws Exception {
        Date startBatch = new Date();
        List<Path> paths = findByFileNamePatern(inputPath,FILENAME_PATTERN);
        if (paths == null ||paths.isEmpty()){
            throw new ParametersException("no file to be processed");
        }else{
            paths.stream().forEach((path) ->
                    adrInvalidBarecodeService.execute(path.getFileName().toString(),
                            inputPath,
                            outputPath,
                            RESULT_FILENAME+path.getFileName().toString()+"_"+ DAY + CSV ,
                            METRIC_FILENAME+path.getFileName().toString()+"_"+ DAY + TXT, startBatch));
        }
    }

    private static void parseArgs(String[] args) throws ParametersException {
        if (ArrayUtils.isEmpty(args)){
            printHelp();
            throw new ParametersException("No arguments to the batch");
        }

        List<BatchAutomaticMergeEnum> argsProvided = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            String s = args[i];

            if (s.contains(IConstants.ARGS_SEPARATOR)) {
                String currentArg = s.split(IConstants.ARGS_SEPARATOR)[1];
                try {
                    BatchAutomaticMergeEnum currArgEnum = BatchAutomaticMergeEnum.valueOf(currentArg);
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
                            BatchAdrInvalidBarecode.sh -option:
                                -I input  Directory : [MANDATORY]
                                -O output Directory : [MANDATORY]"""
                    );
                }

            }
        }

        // Check if all mandatory arguments are filled
        for (BatchAutomaticMergeEnum currEnum : BatchAutomaticMergeEnum.values()) {
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
				BatchAdrInvalidBarecode.sh -option:
					-I input  Directory : [MANDATORY]
					-O output Directory : [MANDATORY]
				"""
        );
    }

    public static List<Path> findByFileNamePatern(String outputPath, String resultFileName)
            throws IOException {

        Path path = Paths.get(outputPath);

        List<Path> result;
        try (Stream<Path> pathStream = Files.find(path,
                Integer.MAX_VALUE,
                (p, basicFileAttributes) ->
                        p.getFileName().toString().startsWith(resultFileName))
        ) {
            result = pathStream.collect(Collectors.toList());
        }
        return result;

    }
}
