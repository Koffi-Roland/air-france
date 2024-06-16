package com.airfrance.batch.compref.migklsub;

import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.config.migklsub.MigrationKLSubConfig;
import com.airfrance.batch.compref.migklsub.enums.BatchMigrationKLSubscriptionsArgsEnum;
import com.airfrance.batch.compref.migklsub.enums.ModeEnum;
import com.airfrance.batch.compref.migklsub.enums.RequirementEnum;
import com.airfrance.batch.compref.migklsub.service.MigrationKLSubService;
import com.airfrance.batch.utils.IConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BatchMigrationKLSubscriptions implements IBatch {

    private static String csvFileName;
    private static String inputPath;
    private static String outputPath;
    private static ModeEnum modeEnum;

    @Autowired
    private MigrationKLSubService migrationKLSubService;

    public static void main(String[] args) {
        log.info("Lancement du batch...");

        try{
            parseArgs(args);
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MigrationKLSubConfig.class);
            IBatch service = (IBatch) ctx.getBean("batchMigrationKLSubscriptions");
            service.execute();
        }catch (Exception e) {
            log.error("Erreur lors de l'execution du batch" , e);
            System.exit(1);
        }
        System.exit(0);
    }

    @Override
    public void execute() throws Exception {
        migrationKLSubService.execute(csvFileName , inputPath , outputPath ,  modeEnum);
    }

    protected static void printHelp() {
        System.out.println("\n###\n" +
                "USER GUIDE: \nBatchMigrationKLSubscriptions.sh -option:\n " +
                "-f file.csv : [MANDATORY]\n "+
                "-p input path   : [MANDATORY]\n "+
                "-o output path   : [MANDATORY]\n "+
                "-m mode : i(init) or d(daily) [MANDATORY]");
    }

    protected static void parseArgs(String[] args) throws Exception {
        if (args == null || args.length == 0 || args.length % 2 != 0) {
            printHelp();
            throw new Exception("No arguments to the batch");
        }

        List<BatchMigrationKLSubscriptionsArgsEnum> argsProvided = new ArrayList<>();
        for(int i = 0; i < args.length; i++){
            String s = args[i];

            if (s.contains(IConstants.ARGS_SEPARATOR)) {
                // Type of arg
                String currentArg = s.split(IConstants.ARGS_SEPARATOR)[1];

                try {

                    BatchMigrationKLSubscriptionsArgsEnum currArgEnum = BatchMigrationKLSubscriptionsArgsEnum
                            .valueOf(currentArg);
                    argsProvided.add(currArgEnum);

                    switch (currArgEnum) {

                        case f:
                            csvFileName = args[i + 1];
                            break;
                        case p:
                            inputPath = args[i + 1];
                            break;
                        case o:
                            outputPath = args[i + 1];
                            break;
                        case m:
                            modeEnum = args[i + 1].equalsIgnoreCase(IConstants.INIT_PARAM) ? ModeEnum.INIT : ModeEnum.DAILY;
                            break;
                        default:
                            break;
                    }
                }

                catch (IllegalArgumentException e) {
                    printHelp();
                    throw new Exception("Argument not valid");
                }

            }
        }
        // Check if all mandatory args are filled in

        for (BatchMigrationKLSubscriptionsArgsEnum currEnum : BatchMigrationKLSubscriptionsArgsEnum
                .values()) {

            if (currEnum.getRequirement().equals(RequirementEnum.MANDATORY)) {

                if (!argsProvided.contains(currEnum)) {
                    printHelp();
                    throw new Exception("Mandatory argument missing : "
                            + currEnum);
                }
            }
        }
    }
}
