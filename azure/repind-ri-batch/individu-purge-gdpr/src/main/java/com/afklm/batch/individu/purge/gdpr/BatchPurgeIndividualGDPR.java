package com.afklm.batch.individu.purge.gdpr;

import com.airfrance.batch.common.BatchArgs;
import com.airfrance.batch.common.config.Neo4jProperties;
import com.airfrance.batch.common.config.WebConfigBatchRepind;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.environnement.VariablesDTO;
import com.airfrance.repind.dto.individu.ForgottenIndividualDTO;
import com.airfrance.repind.dto.metric.ProcessTypeEnum;
import com.airfrance.repind.dto.metric.ProcessingMetricDTO;
import com.airfrance.repind.dto.metric.StatusEnum;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.service.individu.internal.ForgottenIndividualDS;
import com.airfrance.repind.service.individu.internal.PurgeIndividualDS;
import com.airfrance.repind.util.AspectLogger.Loggable;
import com.airfrance.repind.util.ProcessingMetricLogger;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.zip.GZIPOutputStream;

@Service
public class BatchPurgeIndividualGDPR extends BatchArgs implements InitializingBean {

	private final static Log log = LogFactory.getLog(BatchPurgeIndividualGDPR.class);
	private static String PATH = "";
	private static final Set<PosixFilePermission> PERMISSIONS_FILE = EnumSet.of(PosixFilePermission.OWNER_READ,
			PosixFilePermission.OWNER_WRITE, PosixFilePermission.GROUP_READ, PosixFilePermission.OTHERS_READ);
	
	private static final Set<PosixFilePermission> PERMISSIONS_DIRECTORY = EnumSet.of(PosixFilePermission.OWNER_READ,
			PosixFilePermission.OWNER_WRITE, PosixFilePermission.GROUP_READ, PosixFilePermission.OTHERS_READ,
			PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.OTHERS_EXECUTE);

	private final static String FORGOTTEN_PERIODINDAYS = "FORGOTTEN_PERIODINDAYS";

	private static Neo4jProperties neo4jProperties;

	@Autowired
	private PurgeIndividualDS purgeIndividualDS;
	
	@Autowired
	private ForgottenIndividualDS forgottenIndividualDS;
	
	@Autowired
	private VariablesDS variableDS;

	public static void main(String[] args) {
		try {
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(WebConfigBatchRepind.class);
			BatchPurgeIndividualGDPR purge = BatchPurgeIndividualGDPR.getInstance();
			neo4jProperties = ctx.getBean(Neo4jProperties.class);
			BatchPurgeIndividualGDPR.log.info("Batch parse args...");
			purge.parseArgs(args);
			BatchPurgeIndividualGDPR.log.info("Batch execution...");
			purge.execute();
			ctx.close();
		} catch (Exception j) {
			BatchPurgeIndividualGDPR.log.fatal(j);
			System.exit(1);
		}
		BatchPurgeIndividualGDPR.log.info(BatchArgs.BATCH_EXECUTED_SUCCESSFULLY);
		System.exit(0);
	}

	@Override
	protected void printHelp() {
		System.out.println("You must indicate the value for this argument : --env-report");
	}

	@Override
	protected void parseArgs(String[] args) throws MissingParameterException, InvalidParameterException {
		for (int i = 0; i < args.length; i++) {
			if (StringUtils.equalsIgnoreCase(args[i], "--number-database-pool")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException(
							"Expecting a numerical value for option 'number-database-pool'");
				}
				final int numberDatabasePool = Integer.parseInt(args[i + 1]);
				purgeIndividualDS.setNumberDatabasePool(numberDatabasePool);
			}
			
			if (StringUtils.equalsIgnoreCase(args[i], "--number-database-cpu-core")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException(
							"Expecting a numerical value for option 'number-database-cpu-core'");
				}
				final int numberDatabaseCpuCore = Integer.parseInt(args[i + 1]);
				purgeIndividualDS.setNumberDatabaseCPUCore(numberDatabaseCpuCore);
			}

			if (StringUtils.equalsIgnoreCase(args[i], "--purge-mode")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException("Expecting a numerical value for option 'purge-mode'");
				}
				final int purgeMode = Integer.parseInt(args[i + 1]);
				try {
					purgeIndividualDS.setMode(purgeMode);
				} catch (InvalidParameterException e) {
					log.error(e);
					printHelp();
					throw new MissingParameterException("Expected a purge mode");
				}
			}

			if (StringUtils.equalsIgnoreCase(args[i], "--batch-size")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException("Expecting a numerical value for option 'batch-size'");
				}
				final int batchSize = Integer.parseInt(args[i + 1]);
				purgeIndividualDS.setBatchSize(batchSize);
			}
			
			if (StringUtils.equalsIgnoreCase(args[i], "--maximum-number-delete")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException("Expecting a numerical value for option 'maximum-number-delete'");
				}
				final int maximumNumberDeletion = Integer.parseInt(args[i + 1]);
				purgeIndividualDS.setMaximumNumberDeletion(maximumNumberDeletion);
			}
			
			if (StringUtils.equalsIgnoreCase(args[i], "--tempo-inactive-rule")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException("Expecting a negative numerical value for option 'tempo-inactive-rule'");
				}
				final int tempoInactiveRule = Integer.parseInt(args[i + 1]);
				if(tempoInactiveRule > 0){
					throw new MissingParameterException("tempo-inactive-rule should be a negative numerical value");
				}
				purgeIndividualDS.set_tempo_inactive_rule(tempoInactiveRule);
			}
			
			if (StringUtils.equalsIgnoreCase(args[i], "--tempo-ouptout-compref-sales-rule")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException("Expecting a negative numerical value for option 'tempo-ouptout-compref-sales-rule'");
				}
				final int tempoOptoutComprefSalesRule = Integer.parseInt(args[i + 1]);
				if(tempoOptoutComprefSalesRule > 0) {
					throw new InvalidParameterException("tempo-ouptout-compref-sales-rule should be a negative numerical value");
				}
				purgeIndividualDS.set_tempo_optout_compref_sales_rule(tempoOptoutComprefSalesRule);
			}
			
			if (StringUtils.equalsIgnoreCase(args[i], "--tempo-delay")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException("Expecting a negative numerical value for option 'tempo-delay'");
				}
				final int tempoDelay = Integer.parseInt(args[i + 1]);
				if(tempoDelay > 0){
					throw new InvalidParameterException("tempo-delay should be a negative numerical value");
				}
				purgeIndividualDS.set_tempo_delay(tempoDelay);
			}
			
			if (StringUtils.equalsIgnoreCase(args[i], "--tempo-cycle-life")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException("Expecting a positive numerical value for option 'tempo-delay'");
				}
				final int tempoCycleLife = Integer.parseInt(args[i + 1]);
				if(tempoCycleLife < 0){
					throw new MissingParameterException("tempo-cycle-life should be a positive numerical value");
				}
				purgeIndividualDS.set_tempo_cycle_life(tempoCycleLife);
			}
			
			if (StringUtils.equalsIgnoreCase(args[i], "--max_records_cbs_report")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException("Expecting a positive numerical value for option 'max_records_cbs_report'");
				}
				final int maxRecordsCbsReport = Integer.parseInt(args[i + 1]);
				if(maxRecordsCbsReport < 0){
					throw new MissingParameterException("max_records_cbs_report should be a positive numerical value");
				}
				purgeIndividualDS.set_max_records_cbs_report(maxRecordsCbsReport);
			}
			
			if (StringUtils.equalsIgnoreCase(args[i], "--cpu_thread_cbs")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException("Expecting a positive numerical value for option 'cpu_thread_cbs'");
				}
				final int cpuThreadCbs = Integer.parseInt(args[i + 1]);
				if(cpuThreadCbs < 0){
					throw new MissingParameterException("cpu_thread_cbs should be a positive numerical value");
				}
				purgeIndividualDS.set_cpu_thread_cbs(cpuThreadCbs);
			}
			
			if (StringUtils.equalsIgnoreCase(args[i], "--exec-mode")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException("Expecting a numerical value for option 'execMode'");
				}
				final int execMode = Integer.parseInt(args[i + 1]);
				purgeIndividualDS.set_execMode(execMode);			
			}
			if (StringUtils.equalsIgnoreCase(args[i], "--env-report")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException("An environment for reporting must be filled 'env-report'");
				}
				String env = args[i + 1];
				BatchPurgeIndividualGDPR.PATH = "/app/" + env + "/data/PURGE_GDPR/";		
			} 
		}
	} 

	@Loggable
	private String _saveFile(final List<String> details, String type)
			throws IOException, MissingParameterException {
		if (details == null || details.isEmpty()) {
			return "";
		}

		final Date date = new Date();
		
		final String filename = BatchPurgeIndividualGDPR.PATH + "" + date.getTime() + "_" + type + ".gz";
		final String concatened = StringUtils.join(details, "\n");
		
		BatchPurgeIndividualGDPR.log.info("filename : " + filename);
		_writeFile(filename, BatchPurgeIndividualGDPR._compress(concatened), BatchPurgeIndividualGDPR.PERMISSIONS_FILE);
		return filename;
	}

	@Override
	@Loggable
	public void execute() throws JrafDomainException {
		
		int periodInDays = getPeriodInDays();
		
		List<ForgottenIndividualDTO> forgottenIndividualDTOs = forgottenIndividualDS.getIndividualForPhysicalPurge(periodInDays);
		BatchPurgeIndividualGDPR.log.info("size forgotten : " + forgottenIndividualDTOs.size());
		
		List<String> gins = new ArrayList<String>();
		final PhysicalPurgeResult result = new PhysicalPurgeResult();
		
		for(ForgottenIndividualDTO f : forgottenIndividualDTOs){
			gins.add(f.getIdentifier());
		}
		
		try {
			result.errors = purgeIndividualDS.physicalDeletion(gins, true);
			result.ginPurged = new ArrayList<>(gins);
			for (final Map.Entry<List<String>, String> entry : result.errors.entrySet()) {
				for (final String gin : entry.getKey()) {
					result.ginPurged.remove(gin);
				}
			}

			result.ginPurged.forEach(purgedGin ->
					ProcessingMetricLogger
							.log(ProcessingMetricDTO.builder()
									.withTrigger("BatchPurgeIndividualGDPR")
									.withProcessType(ProcessTypeEnum.FORGETME)
									.withGin(purgedGin)
									.withMessage("operation success")
									.withStatus(StatusEnum.SUCCESS)
									.build())
			);

			result.errors.forEach((ginList, errorMessage) ->
					ginList.forEach(errorGin ->
							ProcessingMetricLogger
									.log(ProcessingMetricDTO.builder()
											.withTrigger("BatchPurgeIndividualGDPR")
											.withProcessType(ProcessTypeEnum.FORGETME)
											.withGin(errorGin)
											.withMessage(errorMessage)
											.withStatus(StatusEnum.FAIL)
											.build())
					)
			);


			//Deletion of forgotten individus in Neo4j DB
			for(String gin : gins) {
				purgeIndividualDS.deleteForgottenGininNeo4j(neo4jProperties.dataSourceNeo4j(), gin);
			}
		} catch (ExecutionException | InterruptedException e) {
			BatchPurgeIndividualGDPR.log.info(e);
			System.exit(1);			
		}

		try {
			_saveFile(result.ginPurged, "deleted_fgt");
			
		} catch (IOException e1) {
			BatchPurgeIndividualGDPR.log.info(e1);
			System.exit(1);
		}
	}
	
	private int getPeriodInDays() throws JrafDomainException {
		VariablesDTO variablesDTO = variableDS.getByEnvKey(FORGOTTEN_PERIODINDAYS);
		
		if (variablesDTO != null) {
			if (!StringUtils.isEmpty(variablesDTO.getEnvValue())) {
				int periodInDays = Integer.parseInt(variablesDTO.getEnvValue());
				if (periodInDays >= 0) {
					return periodInDays;
				} else {
					throw new JrafDomainException("Period In Days (Forgotten) cannot not be negative.");
				}
			} else {
				throw new JrafDomainException("Period In Days (Forgotten) cannot not be empty.");
			}
		} else {
			throw new JrafDomainException("Unable to get Period In Days (Forgotten) value from DB.");
		}
	}
	
	@Loggable
	private static byte[] _compress(final String str) throws IOException, MissingParameterException {
		if (StringUtils.isBlank(str)) {
			return new byte[] {};
		}
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return out.toByteArray();
	}

	private void _writeFile(final String filename, final byte[] content, Set<PosixFilePermission> permissionsFile)
			throws IOException {
		Files.deleteIfExists(Paths.get(filename));
		if (SystemUtils.IS_OS_UNIX) {
			Files.setPosixFilePermissions(Paths.get(BatchPurgeIndividualGDPR.PATH),
					BatchPurgeIndividualGDPR.PERMISSIONS_DIRECTORY);
			Files.createFile(Paths.get(filename),
					PosixFilePermissions.asFileAttribute(permissionsFile));
		} else {
			Files.createFile(Paths.get(filename));
		}
		try (FileOutputStream fos = new FileOutputStream(filename)) {
			fos.write(content);
			fos.close();
		}
		if (SystemUtils.IS_OS_UNIX) {
			Files.setPosixFilePermissions(Paths.get(filename), permissionsFile);
		}

	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		BatchPurgeIndividualGDPR.instance = this;
	}

	private static BatchPurgeIndividualGDPR instance;

	public static BatchPurgeIndividualGDPR getInstance() {
		return BatchPurgeIndividualGDPR.instance;
	}
	
	private final class PhysicalPurgeResult {
		private List<String> ginPurged;
		private Map<List<String>, String> errors;
		private List<String> cbsReport;
		private List<String> cbsReportDetails;

		private PhysicalPurgeResult() {
		}
	}
}
