package com.airfrance.batch.prospect;

import com.airfrance.batch.config.prospect.AlimentationProspectConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static com.airfrance.batch.prospect.helper.AlimentationProspectConstant.ARGS_SEPARATOR;
import static com.airfrance.batch.prospect.helper.AlimentationProspectConstant.ARG_INPUT;

@Slf4j
public class BatchAlimentationProspect {

    private static String inputFile = "";

    /**
     * Launch application
     *
     * @param args is arguments
     */
    public static void main(String[] args) {
        // if no argument or first arg is help
        if (ArrayUtils.isEmpty(args)) {
            log.info(buildHelp());
            System.exit(0);
        }
        else {
            try {
                parseArgs(args);
                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AlimentationProspectConfig.class);
                JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
                Job job = (Job) context.getBean("alimentationProspectJob");
                JobParametersBuilder jobBuilder = new JobParametersBuilder();
                jobBuilder.addString("inputFile", inputFile);
                JobParameters jobParameters = jobBuilder.toJobParameters();

                JobExecution execution = jobLauncher.run(job, jobParameters);
            } catch (Exception e) {
                log.error("Failed to run batch");
                e.printStackTrace();
            }
        }
    }

    private static void parseArgs(String[] args) throws RuntimeException {
        for(int i = 0; i < args.length; i++){
            String s = args[i];

            if (s.contains(ARGS_SEPARATOR)) {
                // Type of arg
                String currentArg = s.split(ARGS_SEPARATOR)[1];

                try {
                    switch (currentArg) {
                        case ARG_INPUT:
                            inputFile = args[i + 1];
                            break;
                        default:
                            break;
                    }
                }

                catch (IllegalArgumentException e) {
                    buildHelp();
                    throw new RuntimeException("Argument not valid");
                }

            }
        }
    }

    private static String buildHelp() {
        return(
                "\n########HELP########\n" +
                "USER GUIDE: \nBatchAlimentationProspect\n " +
                "-inputFile Full input file path");
    }
}
