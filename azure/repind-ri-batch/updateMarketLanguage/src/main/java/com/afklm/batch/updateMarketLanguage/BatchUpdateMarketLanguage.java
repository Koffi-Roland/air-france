package com.afklm.batch.updateMarketLanguage;

import com.airfrance.batch.common.IBatch;
import com.afklm.batch.updateMarketLanguage.config.UpdateMarketLanguageConfig;
import com.afklm.batch.updateMarketLanguage.enums.BatchUpdateMarketLanguageEnum;
import com.afklm.batch.updateMarketLanguage.service.ExecutionService;
import com.airfrance.batch.common.utils.IConstants;
import com.airfrance.batch.common.utils.RequirementEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BatchUpdateMarketLanguage implements  IBatch{

    public static String inputFile;
    public static String outputFile;

    @Autowired
    private ExecutionService executionService;

    public static void main(String[] args) throws Exception {
        log.info("Start of Batch Update Market Language");

        parseArgs(args);

        try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(UpdateMarketLanguageConfig.class)) {
            IBatch batch = (IBatch) ctx.getBean("batchUpdateMarketLanguage");
            batch.execute();
        }
        log.info("End BatchUpdateMarketLanguage.");
    }

    @Override
    public void execute() throws Exception {
        executionService.execute(inputFile, outputFile);
    }

    protected static void parseArgs(String[] args) throws Exception {
        if (args == null || args.length == 0 || args.length % 2 != 0) {
            printHelp();
            throw new Exception("No arguments to the batch");
        }

        List<BatchUpdateMarketLanguageEnum> argsProvided = new ArrayList<>();
        for(int i = 0; i < args.length; i++){
            String s = args[i];
            if (s.contains(IConstants.ARGS_SEPARATOR)) {
                // Type of arg
                String currentArg = s.split(IConstants.ARGS_SEPARATOR)[1];
                try {
                    BatchUpdateMarketLanguageEnum currArgEnum = BatchUpdateMarketLanguageEnum
                            .valueOf(currentArg);
                    argsProvided.add(currArgEnum);
                    switch (currArgEnum) {
                        case f:
                            inputFile = args[i + 1];
                            i++;
                            break;
                        case o:
                            outputFile = args[i + 1];
                            i++;
                            break;
                        default:
                            break;
                    }
                }
                catch (IllegalArgumentException e) {
                    printHelp();
                    throw new Exception("argument are not valid\\n" +
                            "USER GUIDE: \nBatchUpdateMarketLangugage.sh -option:\n " +
                            "-f Full input file path : [MANDATORY]");
                }
            }
        }
        // Check if all mandatory args are filled in
        for (BatchUpdateMarketLanguageEnum currEnum : BatchUpdateMarketLanguageEnum
                .values()) {
            if (currEnum.getRequirement().equals(RequirementEnum.MANDATORY)) {
                if (!argsProvided.contains(currEnum)) {
                    printHelp();
                    throw new Exception("Mandatory argument missing : " + currEnum);
                }
            }
        }
    }

    protected static void printHelp() {
        System.out.println("\n########HELP########\n" +
                "USER GUIDE: \nBatchUpdateMarketLangugage.sh -option:\n " +
                "-f Full input file path");
    }
}
