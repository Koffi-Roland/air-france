package com.afklm.batch.individu.purge;

import com.airfrance.batch.common.BatchArgs;
import com.airfrance.batch.common.config.Neo4jProperties;
import com.airfrance.batch.common.config.WebConfigBatchRepind;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.dto.environnement.VariablesDTO;
import com.airfrance.repind.dto.metric.ProcessTypeEnum;
import com.airfrance.repind.dto.metric.ProcessingMetricDTO;
import com.airfrance.repind.dto.metric.StatusEnum;
import com.airfrance.repind.service.environnement.internal.VariablesDS;
import com.airfrance.repind.service.individu.internal.PurgeIndividualDS;
import com.airfrance.repind.service.individu.internal.PurgeIndividualDS.BackupWriteFileCallback;
import com.airfrance.repind.service.individu.internal.PurgeIndividualResult;
import com.airfrance.repind.util.AspectLogger.Loggable;
import com.airfrance.repind.util.ProcessingMetricLogger;
import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.*;
import java.util.zip.GZIPOutputStream;

@Service("batchPurgeIndividual")
public class BatchPurgeIndividual extends BatchArgs implements InitializingBean, BackupWriteFileCallback {

	private final static Log log = LogFactory.getLog(BatchPurgeIndividual.class);
	private static String PATH = "";
	private final static String REPORT_CBS = "report_cbs";
	private final static String REPORT_CBS_DETAILS = "report_cbs_details";
	private final static String PERIODINDAYS = "PROVIDECUSTOMER360_PERIODINDAYS";
	private final static String PERIODINDAYSOCP = "PROTECTIONOCP_PERIODINDAYS";
	private final static String DELAYINMS = "BATCH_PURGE_RI_DELAY_FOR_CBS360";
	private final static long DELAYINMS_BY_DEFAULT = 0;

	private static Neo4jProperties neo4jProperties;

	private static final Set<PosixFilePermission> PERMISSIONS_FILE = EnumSet.of(PosixFilePermission.OWNER_READ,
			PosixFilePermission.OWNER_WRITE, PosixFilePermission.GROUP_READ, PosixFilePermission.OTHERS_READ);

	private static final Set<PosixFilePermission> PERMISSIONS_FILE_BACKUP = EnumSet.of(PosixFilePermission.OWNER_READ,
			PosixFilePermission.OWNER_WRITE, PosixFilePermission.GROUP_READ);

	private static final Set<PosixFilePermission> PERMISSIONS_DIRECTORY = EnumSet.of(PosixFilePermission.OWNER_READ,
			PosixFilePermission.OWNER_WRITE, PosixFilePermission.GROUP_READ, PosixFilePermission.OTHERS_READ,
			PosixFilePermission.GROUP_EXECUTE, PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.OTHERS_EXECUTE);

	private static BatchPurgeIndividual instance;

	@SuppressWarnings("unused")
	private void _deleteOldFiles() {
		File directory = new File(BatchPurgeIndividual.PATH);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -6);
		for (File file : directory.listFiles()) {
			if (file.lastModified() < c.getTimeInMillis()) {
				file.delete();
			}
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

	public static BatchPurgeIndividual getInstance() {
		return BatchPurgeIndividual.instance;
	}

	public static void main(final String[] args) {
		try {
			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(WebConfigBatchRepind.class);
			final BatchPurgeIndividual purge = (BatchPurgeIndividual) ctx.getBean("batchPurgeIndividual");
			neo4jProperties = ctx.getBean(Neo4jProperties.class);
			BatchPurgeIndividual.log.info("Batch parse args...");
			purge.parseArgs(args);
			BatchPurgeIndividual.log.info("Batch execution...");
			purge.execute();
			ctx.close();
		} catch (final Exception j) {
			BatchPurgeIndividual.log.fatal(j);
			System.exit(1);
		}
		BatchPurgeIndividual.log.info(BatchArgs.BATCH_EXECUTED_SUCCESSFULLY);
		System.exit(0);
	}

	@Autowired
	private PurgeIndividualDS purgeIndividualDS;

	@Autowired
	private VariablesDS variableDS;

	@Loggable
	private String _saveFile(final List<String> details, String type)
			throws IOException, MissingParameterException {
		if (details == null || details.isEmpty()) {
			return "";
		}

		final Date date = new Date();

		final String filename = BatchPurgeIndividual.PATH + date.getTime() + "_" + type + ".gz";
		final String concatened = StringUtils.join(details, "\n");

		_writeFile(filename, BatchPurgeIndividual._compress(concatened), BatchPurgeIndividual.PERMISSIONS_FILE);
		return filename;
	}

	@Loggable
	private String _saveFile(final List<String> gins, final boolean isPurge)
			throws IOException, MissingParameterException {
		if (gins == null || gins.isEmpty()) {
			return "";
		}

		final Date date = new Date();
		String type = "selected";
		if (isPurge) {
			type = "deleted";
		}

		final String filename = BatchPurgeIndividual.PATH + date.getTime() + "_" + type + ".gz";
		final String concatened = StringUtils.join(gins, "\n");

		_writeFile(filename, BatchPurgeIndividual._compress(concatened), BatchPurgeIndividual.PERMISSIONS_FILE);
		return filename;
	}

	@Loggable
	private String _saveFile(final Map<List<String>, String> errorPhysicalPurge)
			throws IOException, MissingParameterException {
		if (errorPhysicalPurge == null || errorPhysicalPurge.isEmpty()) {
			return "";
		}
		final Date date = new Date();
		final String filename = BatchPurgeIndividual.PATH + date.getTime() + "_error_physical_purge.gz";
		String result = "";
		for (final Map.Entry<List<String>, String> entry : errorPhysicalPurge.entrySet()) {
			result += "\n\n" + entry.getValue();
		}
		_writeFile(filename, BatchPurgeIndividual._compress(result), BatchPurgeIndividual.PERMISSIONS_FILE);
		return filename;
	}

	@Override
	public void writeBackupFile(final String filename, final Map<String, String> content)
			throws MissingParameterException, IOException {
		String string = Joiner.on("\n===============================\n").withKeyValueSeparator("\n").join(content);
		_writeFile(filename, BatchPurgeIndividual._compress(string), BatchPurgeIndividual.PERMISSIONS_FILE_BACKUP);
	}

	private void _writeFile(final String filename, final byte[] content, Set<PosixFilePermission> permissionsFile)
			throws IOException {
		Files.deleteIfExists(Paths.get(filename));
		if (SystemUtils.IS_OS_UNIX) {
			Files.setPosixFilePermissions(Paths.get(BatchPurgeIndividual.PATH),
					BatchPurgeIndividual.PERMISSIONS_DIRECTORY);
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
		BatchPurgeIndividual.instance = this;
	}

	@Override
	@Loggable
	public void execute() throws JrafDomainException {
		PurgeIndividualResult result = null;
		try {

			//REPIND-1078: We get the periodInDays (for ProvideCustomer360) from the DB
			int periodInDays = getProvideCustomer360_periodInDays();
			purgeIndividualDS.set_period_in_days(periodInDays);

			int periodInDaysOCP = getProtectionByOCP_periodInDays();
			purgeIndividualDS.set_period_in_days_ocp(periodInDaysOCP);

			// REPIND-1533 : We get the delay to call CBS (for ProvideCustomer360) from the DB
			long delayOnCBSCallInMs = getProvideCustomer360_delayInMs();
			purgeIndividualDS.set_delay_in_ms(delayOnCBSCallInMs);

			result = purgeIndividualDS.process();

			//Deletion of forgotten individus in Neo4j DB
			for(String gin : result.getGinsPurged()) {
				purgeIndividualDS.deleteForgottenGininNeo4j(neo4jProperties.dataSourceNeo4j(), gin);
			}


			result.getGinsPurged().forEach(purgedGin ->
					ProcessingMetricLogger
							.log(ProcessingMetricDTO.builder()
									.withTrigger("BatchPurgeIndividual")
									.withProcessType(ProcessTypeEnum.PURGE)
									.withGin(purgedGin)
									.withMessage("operation success")
									.withStatus(StatusEnum.SUCCESS)
									.build())
			);

			result.getErrorsPhysicalPurge().forEach((ginList, errorMessage) ->
					ginList.forEach(errorGin ->
							ProcessingMetricLogger
									.log(ProcessingMetricDTO.builder()
											.withTrigger("BatchPurgeIndividual")
											.withProcessType(ProcessTypeEnum.PURGE)
											.withGin(errorGin)
											.withMessage(errorMessage)
											.withStatus(StatusEnum.FAIL)
											.build())
					)
			);

			final String filenamePurged = _saveFile(result.getGinsPurged(), true);
			final String filenameToPurgeLater = _saveFile(result.getGinsToPurgeLater(), false);
			final String filenamePhysicalPurgeError = _saveFile(result.getErrorsPhysicalPurge());
			final int numberOfIndividualSelected = result.getGinsToPurgeLater().size();

			_saveFile(result.get_cbsReport(), REPORT_CBS);
			_saveFile(result.get_cbsReportDetails(), REPORT_CBS_DETAILS);

			final String content = filenameToPurgeLater + ";" + filenamePurged + ";" + filenamePhysicalPurgeError + ";" + numberOfIndividualSelected;
			_writeFile(BatchPurgeIndividual.PATH + "report", content.getBytes(),
					BatchPurgeIndividual.PERMISSIONS_FILE);
		} catch (final Exception e) {
			BatchPurgeIndividual.log.fatal(e);
			System.exit(1);
		}
	}

	@Override
	protected void parseArgs(final String[] args) throws MissingParameterException, InvalidParameterException {
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

			if (StringUtils.equalsIgnoreCase(args[i], "--backup") || StringUtils.equalsIgnoreCase(args[i], "-b")) {
				purgeIndividualDS.setBackupWriteFileCallback(this);
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

			if (StringUtils.equalsIgnoreCase(args[i], "--tempo-inactive-rule-external")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException("Expecting a negative numerical value for option 'tempo-inactive-rule-external'");
				}
				final int tempoInactiveRuleExternal = Integer.parseInt(args[i + 1]);
				if(tempoInactiveRuleExternal > 0){
					throw new MissingParameterException("tempo-inactive-rule-external should be a negative numerical value");
				}
				purgeIndividualDS.set_tempo_inactive_rule_external(tempoInactiveRuleExternal);
			}

			if (StringUtils.equalsIgnoreCase(args[i], "--tempo-inactive-rule-merged")) {
				if (args.length - 1 == i) {
					printHelp();
					throw new MissingParameterException("Expecting a negative numerical value for option 'tempo-inactive-rule-merged'");
				}
				final int tempoInactiveRuleMerged = Integer.parseInt(args[i + 1]);
				if(tempoInactiveRuleMerged > 0){
					throw new MissingParameterException("tempo-inactive-rule-merged should be a negative numerical value");
				}
				purgeIndividualDS.set_tempo_inactive_rule_merged(tempoInactiveRuleMerged);
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
					throw new MissingParameterException("Expecting a positive numerical value for option '--tempo-cycle-life'");
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
				BatchPurgeIndividual.PATH = "/app/" + env + "/data/PURGE/";
			}

			if (StringUtils.equalsIgnoreCase(args[i], "--help") || StringUtils.equalsIgnoreCase(args[i], "-h")) {
				printHelp();
				System.exit(2);
			}
		}
	}

	@Override
	protected void printHelp() {
		System.out.println("NAME");
		System.out.println("\tPurge Individual");
		System.out.println("OPTIONS");
		System.out.println("\t--number-database-pool\t\tnumber of maximum simultaneous database connexion");
		System.out.println("\t--number-database-cpu-core\tnumber of maximum database CPU core used");
		System.out.println("\t--purge-mode\t 1: BRUTAL_BULK_DELETE; 2: SAFE_DELETE");
		System.out.println("\t--batch-size\t Number of delete before commit");
		System.out.println("\t--maximum-number-delete\t Maximum number of individual purged during this run");
		System.out.println("\t--tempo-inactive-rule / -h\t Number of day for rule : get all individu that got modified in the last X days (default : 90 days)");
		System.out.println("\t--tempo-ouptout-compref-sales-rule / -h\t Number of day for rule : comPref sales 'S' opt-out for at least X days (default : 180 days)");
		System.out.println("\t--tempo-delay / -h\t Number of day for rule : constraint to avoid deleting individual too soon (default : 30 days)");
		System.out.println("\t--tempo-cycle-life / -h\t Number of day for rule : After some times, test account stop to be used, so need the purge to handle that case (default : 400 days)");
		System.out.println("\t--tempo-cycle-life-protected-ocp / -h\t Number of day for rule : After some times, gin protected by OCP stop to be protected (default : 30 days)");
		System.out.println("\t--exec-mode / -h\t 1 : generate the physical deletion file without generating a selection file // 0 : generate the selection file without doing a physical purge (default : 0)");
		System.out.println("\t--cpu_thread_cbs\t Number of threads used to call CBS (ProvideCustomer360) (default: 8)");
		System.out.println("\t--max_records_cbs_report\t Maximum of records that we track for each activity of a gin // 0 : unlimited (by default)");
		System.out.println("\t--env-report / -h\t Environment where report files will be generated");
		System.out.println("\t--help / -h\t Print this help");
		System.out.println("\t--backup / -b\t Activate applicative backup when the purge mode is SAFE_DELETE");
		System.out.println("DEFINITIONS");
		System.out.println(
				"\tBRUTAL_BULK_DELETE: https://confluence.devnet.klm.com/display/repind/Physical+deletion+of+an+individual");
		System.out.println(
				"\tSAFE_DELETE: https://confluence.devnet.klm.com/display/repind/Physical+deletion+of+an+individual");
		System.out.println("EXAMPLE");
		System.out.println(
				"\t--number-database-cpu-core 4 --number-database-pool 12 --purge-mode 2 --batch-size 1000");
	}

	private int getProvideCustomer360_periodInDays() throws JrafDomainException {
		VariablesDTO variablesDTO = variableDS.getByEnvKey(PERIODINDAYS);

		if (variablesDTO != null) {
			if (!StringUtils.isEmpty(variablesDTO.getEnvValue())) {
				int periodInDays = Integer.parseInt(variablesDTO.getEnvValue());
				if (periodInDays >= 0) {
					return periodInDays;
				} else {
					throw new JrafDomainException("Period In Days (ProvideCustomer360) cannot not be negative.");
				}
			} else {
				throw new JrafDomainException("Period In Days (ProvideCustomer360) cannot not be empty.");
			}
		} else {
			throw new JrafDomainException("Unable to get Period In Days (ProvideCustomer360) value from DB.");
		}
	}

	private int getProtectionByOCP_periodInDays() throws JrafDomainException {
		VariablesDTO variablesDTO = variableDS.getByEnvKey(PERIODINDAYSOCP);

		if (variablesDTO != null) {
			if (!StringUtils.isEmpty(variablesDTO.getEnvValue())) {
				int periodInDays = Integer.parseInt(variablesDTO.getEnvValue());

				return periodInDays;
			} else {
				throw new JrafDomainException("Period In Days (ProtectionByOCP) cannot not be empty.");
			}
		} else {
			throw new JrafDomainException("Unable to get Period In Days (ProtectionByOCP) value from DB.");
		}
	}

	private long getProvideCustomer360_delayInMs() throws JrafDomainException {
		VariablesDTO variablesDTO = variableDS.getByEnvKey(DELAYINMS);

		if (variablesDTO != null) {
			if (!StringUtils.isEmpty(variablesDTO.getEnvValue())) {
				long delayOnCBSCallInMs = Long.parseLong(variablesDTO.getEnvValue());
				if (delayOnCBSCallInMs >= 0) {
					return delayOnCBSCallInMs;
				} else {
					throw new JrafDomainException("Delay In Ms (ProvideCustomer360) cannot not be negative.");
				}
			} else {
				return DELAYINMS_BY_DEFAULT;	// Default value
			}
		} else {
			return DELAYINMS_BY_DEFAULT;	// Default value
		}
	}
}
