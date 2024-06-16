package com.afklm.batch.fixafcompref;

import com.afklm.batch.fixafcompref.config.FixAfComPrefConfig;
import com.airfrance.batch.common.IBatch;
import com.afklm.batch.fixafcompref.enums.BatchFixAfComPrefArgsEnum;
import com.afklm.batch.fixafcompref.service.FixAfComPrefService;
import com.airfrance.batch.common.enums.RequirementEnum;
import com.airfrance.batch.common.utils.IConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BatchFixAfComPref implements IBatch {

    private static String csvFileName;
    private static String inputPath;
    private static String outputPath;

    @Autowired
    private FixAfComPrefService service;

    public static void main(String[] args) {
        log.info("Lancement du batch...");

        try{
            parseArgs(args);
            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(FixAfComPrefConfig.class);
            IBatch service = (IBatch) ctx.getBean("batchFixAfComPref");
            service.execute();
        }catch (Exception e) {
            log.error("Erreur lors de l'execution du batch" , e);
            System.exit(1);
        }
        System.exit(0);
    }

    @Override
    public void execute() throws Exception {
        service.execute( csvFileName , inputPath , outputPath);
    }

    protected static void printHelp() {
        System.out.println("\n###\n" +
                "USER GUIDE: \nBatchFixAfComPref.sh -option:\n " +
                "-f file.csv : [MANDATORY]\n "+
                "-p input path   : [MANDATORY]");
    }

    protected static void parseArgs(String[] args) throws Exception {
        if (args == null || args.length == 0 || args.length % 2 != 0) {
            printHelp();
            throw new Exception("No arguments to the batch");
        }

        List<BatchFixAfComPrefArgsEnum> argsProvided = new ArrayList<>();
        for(int i = 0; i < args.length; i++){
            String s = args[i];

            if (s.contains(IConstants.ARGS_SEPARATOR)) {
                // Type of arg
                String currentArg = s.split(IConstants.ARGS_SEPARATOR)[1];

                try {

                    BatchFixAfComPrefArgsEnum currArgEnum = BatchFixAfComPrefArgsEnum
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

        for (BatchFixAfComPrefArgsEnum currEnum : BatchFixAfComPrefArgsEnum
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
