package com.afklm.batch.injestadhocdata;

import com.afklm.batch.injestadhocdata.config.InjectAdhocDataConfig;
import com.afklm.batch.injestadhocdata.enums.ArgumentEnum;
import com.afklm.batch.injestadhocdata.service.InjestAdhocDataService;
import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.common.enums.RequirementEnum;
import com.airfrance.batch.common.utils.IConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static com.afklm.batch.injestadhocdata.helper.Constant.MANDATORY_ARGUMENT_MISSING;
import static com.afklm.batch.injestadhocdata.helper.Constant.USER_GUIDE;

@Slf4j
public class BatchInjestAdhocData implements IBatch {

	private static String filename;
	private static String inputPath;
	private static String outputPath;

	@Autowired
	private InjestAdhocDataService service;

	public static void main(String[] args) {
		log.debug("Starting BatchInjestAdhocData.. ");
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				InjectAdhocDataConfig.class)) {
			parseArgs(args);
			IBatch service = (IBatch) context.getBean("batchInjestAdhocData");
			service.execute();
		} catch (Exception e) {
			log.error("Batch execution failed.. ", e);
			System.exit(1);
		}
		System.exit(0);
	}

	@Override
	public void execute() throws Exception {
		service.execute(filename, inputPath, outputPath);
	}

	private static void printHelp() {
		log.info(USER_GUIDE);
	}

	private static void parseArgs(String[] args) {
		if (ArrayUtils.isEmpty(args)) {
			printHelp();
			System.exit(0);
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
					case f:
						filename = args[i + 1];
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
					throw new IllegalArgumentException("Argument not valid");
				}

			}
		}
		// Check if all mandatory arguments are filled
		for (ArgumentEnum currEnum : ArgumentEnum.values()) {
			if (RequirementEnum.MANDATORY.name().equals(currEnum.getRequirement().name())
					&& !argsProvided.contains(currEnum)) {
				printHelp();
				throw new IllegalArgumentException(MANDATORY_ARGUMENT_MISSING + currEnum);
			}
		}
	}
}
