package com.afklm.batch.mergeduplicatescore;

import com.afklm.batch.mergeduplicatescore.service.MergeDuplicatesScoreService;
import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.common.enums.BatchMergeDuplicateScoreEnum;
import com.airfrance.batch.common.exception.ParametersException;
import com.afklm.batch.mergeduplicatescore.config.MergeDuplicatesScoreConfig;
import com.airfrance.batch.common.utils.IConstants;
import com.airfrance.batch.common.utils.RequirementEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class BatchMergeDuplicatesScore implements IBatch {

	private static String inputPath;
	private static String outputPath;
	private static final String TXT = ".txt";
	private static final String CSV = ".csv";
	private static final String DAY = new SimpleDateFormat("ddMMyyyy").format(new Date());
	private static final String RESULT_FILENAME = "merged_duplicates_result_" + DAY + CSV ;
	private static final String LOG_FILENAME = "merged_duplicates_reject_" + DAY + TXT;
	private static final String METRIC_FILENAME = "merged_duplicates_metric_" + DAY + TXT;

	@Autowired
	private MergeDuplicatesScoreService mergeDupService;

	public static void main(String[] args) throws Exception {

		log.info("Start of BatchMergeDuplicatesScore");
		parseArgs(args);
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MergeDuplicatesScoreConfig.class)) {
			IBatch batch = (IBatch) ctx.getBean("batchMergeDuplicatesScore");
			batch.execute();
		}
		log.info("End BatchMergeDuplicatesScore.");

	}

	@Override
	public void execute() throws Exception {
		mergeDupService.execute(inputPath, outputPath, RESULT_FILENAME, LOG_FILENAME, METRIC_FILENAME);
	}


	private static void parseArgs(String[] args) throws ParametersException {
		if (ArrayUtils.isEmpty(args)){
			printHelp();
			throw new ParametersException("No arguments to the batch");
		}

		List<BatchMergeDuplicateScoreEnum> argsProvided = new ArrayList<>();
		for (int i = 0; i < args.length; i++) {
			String s = args[i];

			if (s.contains(IConstants.ARGS_SEPARATOR)) {
				String currentArg = s.split(IConstants.ARGS_SEPARATOR)[1];
				try {
					BatchMergeDuplicateScoreEnum currArgEnum = BatchMergeDuplicateScoreEnum.valueOf(currentArg);
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
							BatchMergeDuplicatesScore.sh -option:
								-I input  Directory : [MANDATORY]
								-O output Directory : [MANDATORY]"""
					);
				}

			}
		}

		// Check if all mandatory arguments are filled
		for (BatchMergeDuplicateScoreEnum currEnum : BatchMergeDuplicateScoreEnum.values()) {
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
				BatchMergeDuplicatesScore.sh -option:
					-I input  Directory : [MANDATORY]
					-O output Directory : [MANDATORY]
				"""
		);
	}
}
