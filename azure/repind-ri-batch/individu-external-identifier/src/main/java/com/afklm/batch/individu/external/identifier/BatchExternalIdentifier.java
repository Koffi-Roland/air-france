/**
 * 
 */
package com.afklm.batch.individu.external.identifier;



import com.airfrance.batch.common.BatchArgs;
import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.common.config.WebConfigBatchRepind;
import com.airfrance.batch.common.enums.BatchExternalIdentifierArgsEnum;
import com.airfrance.batch.common.enums.RequirementEnum;
import com.airfrance.batch.common.exception.BatchException;
import com.airfrance.batch.common.helper.BatchExternalIdentifierHelper;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.IdentifierOptionTypeEnum;
import com.airfrance.repind.dto.external.ExternalIdentifierDTO;
import com.airfrance.repind.dto.external.ExternalIdentifierDataDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdRequestDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdResponseDTO;
import com.airfrance.repind.service.external.internal.ExternalIdentifierDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.internal.unitservice.individu.MyAccountUS;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author t730890
 *
 */
@Service("externalIdentifierBatch")
public class BatchExternalIdentifier extends BatchArgs {


	private static final Log LOG = LogFactory.getLog(BatchExternalIdentifier.class);

	private static final String ARGS_SEPARATOR = "-";
	private static final String SEPARATOR = ",";
	private static final String CSV = ".csv";
	private static final String EXTERNAL_IDENTIFIER_DATA_DIR = "/app/REPIND/data/EXTERNAL_IDENTIFIER/";
	private static final String HEAD_LINE_OUTPUT = "LINE_NUMBER;GIN;STATUS\n";
	private static final String NOT_FOUND = "NOT FOUND";
	// Input file should respect following : GIN,SOCIAL_ID,MEDIA_TYPE,PNM_AIRLINE,PNM_NAME
	private static final String REGEX_RECORD_NUMBER = ".*[\\\\/][A-Z]+_(.*)";

	private static final String FACEBOOK_ID = "FACEBOOK_ID";
	private static final String TWITTER_ID = "TWITTER_ID";
	private static final String WECHAT_ID = "WECHAT_ID";
	private static final String WHATSAPP_ID = "WHATSAPP_ID";
	private static final String CREATELINES_FOR_TEMP_TRAVEL_DB = "createLines for temporary TRAVEL DB treatment";
	private static final String WHATSAPP = "WHATSAPP";
	private static final String FACEBOOK = "FACEBOOK";
	private static final String WECHAT = "WECHAT";
	private static final String MOBILE = "MOBILE";
	private static final String TWITTER = "TWITTER";
	private static final String WARN = "WARN : ";
	
	private boolean commit = false;
	// TODO : delete this attribute
	private boolean lineError = false;
	private String fileToProcess;
	private int versionOfFile = 5;
	private List<String> extTypeIds;
	
	// Report data
	private HashMap<String, Integer> extTypeIdsReport = new HashMap<String, Integer>();
	private long reportNbOK = 0;
	private long reportNbError = 0;
	private long reportNbAlready = 0;
	private long reportNbNotFound = 0;
	private long reportNbInvalidLine = 0;
	private long reportNbTotal = 0;

    // Buffer for log file
    private BufferedWriter bfwReportCsv;
	
	@Autowired
	private IndividuDS individuDS;

	@Autowired
	private MyAccountUS myAccountUS;

	@Autowired
	private ExternalIdentifierDS eids;

	@Autowired
	private BatchExternalIdentifierHelper helper;
	
	@Autowired
	@PersistenceContext(name = "entityManagerFactoryRepind")
	private EntityManager entityManagerFactory;
	
	/* (non-Javadoc)
	 * @see com.airfrance.jraf.batch.common.BatchArgs#execute()
	 */
	@Override
	public void execute() throws JrafDomainException { 
		int lineNum = 1;
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy-HHmmss");
		String dDay = sdf.format(new Date());

		try {
			// Get path to external Identifier directory and create directories if they do not exist
			Path directory = Paths.get(EXTERNAL_IDENTIFIER_DATA_DIR);
			Files.createDirectories(directory);

			List<String> lines = new ArrayList<String>();
			StringBuilder report = new StringBuilder();
			
			// Report file is created
			// Get path to CSV file
			// String inputFileRecordNumber = getInputFileRecordNumber(fileToProcess);
			Path loc = Paths.get(EXTERNAL_IDENTIFIER_DATA_DIR + "BatchExternalIdentifier_report_" + dDay + CSV);
			
			// StringBuilder report = new StringBuilder();
			// report.append(HEAD_LINE_OUTPUT);
			// Files.write(loc, HEAD_LINE_OUTPUT.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);						
			
            File reportCsv = new File(loc.toString());
            bfwReportCsv = new BufferedWriter(new FileWriter(reportCsv, true));
            bfwReportCsv.write(HEAD_LINE_OUTPUT);			
			
			// Treat datas on line (insert, update individual, etc...)
			// Init type extId
			this.extTypeIds = helper.getAllTypeExtId();

			// REPIND-1264 : Prepare a report 
			PreFillAllExtIdOnReport();			
            
			boolean isFileProcess = fileToProcess != null && !"".equals(fileToProcess) && !fileToProcess.contains("SOCRI"); 
			
			if (isFileProcess) {
			
				// BEFORE WE READ A FILE
				
				// Get path to input file
				Path toRead = Paths.get(fileToProcess);
				// Get all lines from input file
				lines = Files.readAllLines(toRead, StandardCharsets.ISO_8859_1);
			
			} else {
				// AFTER WE READ A TEMPORARY TABLE
			
				// REPIND-1279 : Change the way of loading data				
				// SQL Loader have been launch by the BatchExternalIdentifier.sh				
				
				// We check if there is some NOT FOUND GIN from TMP to INDIVIDUS_ALL
				_checkNotFound();
				
				// We check if there is some GIN that have no SOCIAL data loaded 
				_checkNewLines();
				
				
				lines = _checkSocTable();
				versionOfFile = 0;			// We do not have to transform the file
				
				
				_checkNbNotFound(report);
				bfwReportCsv.write(report.toString());
				
				report = new StringBuilder();
				_checkNbSuccessInsert(report);
				bfwReportCsv.write(report.toString());
			}		
//			LOG.debug("Temporary process to include KLM file : start");
			// List<String> linesToTreat = temporaryLineTreatmentFromKLM(line); 		// FICHIER ISSU DE KLM
			// List<String> linesToTreat = temporaryLineTreatmentFromTravelerDB(line);		// FICHIER ISSU DE TRAVELDB EXTRACTION 1
			// List<String> linesToTreat = temporaryLineTreatmentFromTravelerDB2(line);		// FICHIER ISSU DE TRAVELDB EXTRACTION 2
			// List<String> linesToTreat = temporaryLineTreatmentFromTravelerDB3(line);		// FICHIER ISSU DE TRAVELDB EXTRACTION 3
			
			// TODO Review this treatment once definitive input file format is known
			for (String line : lines) {
				switch (versionOfFile) {
				case 0:
					
				break;
				case 1:
					
				break;
				case 2:
					
				break;
				case 3:
					
				break;
				case 5:
					line = temporaryLineTreatmentFromTravelerDB4(line);
				break;
				}
				
//				if (linesToTreat.length == 0) {
//					report.append(lineNum).append(";;MISSING EXTERNAL IDENTIFIER\n");
//				}
				LOG.debug("Normal process to include KLM file : start");
//				for (String extLineToInsert : linesToTreat) {
//					treatLine(extLineToInsert, lineNum, report);
					report = new StringBuilder();	
					treatLine(line, lineNum, report, isFileProcess);
					// Files.write(loc, report.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
					bfwReportCsv.write(report.toString());
//				}
				lineNum++;
				LOG.debug("Normal process to include KLM file : end");
			}

			LOG.debug("Temporary process to include KLM file : end");

			// Get path to CSV file
			// Path loc = Paths.get(EXTERNAL_IDENTIFIER_DATA_DIR + "BatchExternalIdentifier_report_" + dDay + CSV);
			// Files.write(loc, report.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
			
			bfwReportCsv.close();
			
			LogAnExtIdReport();			
			
		} catch (IOException e) {
			throw new JrafDomainException(e);
		}
	}

	private List<String> _checkSocTable() {

		final Session session = ((Session) entityManagerFactory.getDelegate()).getSessionFactory().openSession();
		// session.createSQLQuery("ALTER SESSION ENABLE PARALLEL DML").executeUpdate();
		// session.beginTransaction();

		List<Object[]> objList = session.createSQLQuery("select ID, GIN, SOCIAL_ID, PNM_AIRLINE, PNM_NAME, MEDIA_TYPE, ACTION from SIC2.TMP_SOC_ID soc where soc.ACTION IS NULL").list();
		List<String> strList = new ArrayList<String>();
		
		// We transform to the correct structure		
		// GIN / IDENTIFIER / TYPE / AIRLINE / NAME / FB / MYACCOUNT
		for (Object[] obj : objList) {
			strList.add(obj[1] + SEPARATOR + obj[2] + SEPARATOR + obj[5] + SEPARATOR + obj[3] + SEPARATOR + obj[4] + SEPARATOR + SEPARATOR + obj[0]);
			// if (strList.size() > 0) {
				// LOG.debug("String : " + strList.get(strList.size() - 1));
			// }
		}
		
		// session.getTransaction().commit();
		session.close();
		// for (final Future<?> thread : _runAllAnnotatedWith(PurgeIndividualInsertDetectedInd.class)) {
		// 	thread.get();
		// }
		
		return strList;
	}

	private void _checkNbNotFound(StringBuilder sb) {

		final Session session = ((Session) entityManagerFactory.getDelegate()).getSessionFactory().openSession();
		// session.createSQLQuery("ALTER SESSION ENABLE PARALLEL DML").executeUpdate();
		// session.beginTransaction();

		List<Object[]> objList = session.createSQLQuery("select ID, GIN from SIC2.TMP_SOC_ID soc where soc.ACTION = 4").list();
		
		// We transform to the correct structure		
		// GIN / IDENTIFIER / TYPE / AIRLINE / NAME / FB / MYACCOUNT
		for (Object[] obj : objList) {
			sb.append(obj[0]).append(";").append(obj[1]).append(";PROVIDED GIN ").append(NOT_FOUND).append("\n");
			this.reportNbNotFound++;
			this.reportNbTotal++;
		}
		
		// session.getTransaction().commit();
		session.close();
		// for (final Future<?> thread : _runAllAnnotatedWith(PurgeIndividualInsertDetectedInd.class)) {
		// 	thread.get();
		// }
	}

	private void _checkNbSuccessInsert(StringBuilder sb) {

		final Session session = ((Session) entityManagerFactory.getDelegate()).getSessionFactory().openSession();
		// session.createSQLQuery("ALTER SESSION ENABLE PARALLEL DML").executeUpdate();
		// session.beginTransaction();

		List<Object[]> objList = session.createSQLQuery("select ID, GIN from SIC2.TMP_SOC_ID soc where soc.ACTION = 3").list();

		// We transform to the correct structure		
		// GIN / IDENTIFIER / TYPE / AIRLINE / NAME / FB / MYACCOUNT
		for (Object[] obj : objList) {			
			if (this.commit) {
				sb.append(obj[0]).append(";").append(obj[1]).append(";SUCCESSFULLY INSERTED\n");							
			} else {
				sb.append(obj[0]).append(";").append(obj[1]).append(";INSERTION NOT COMMITED\n");
			}
			this.reportNbOK++;
			this.reportNbTotal++;
		}
		
		// session.getTransaction().commit();
		session.close();
		// for (final Future<?> thread : _runAllAnnotatedWith(PurgeIndividualInsertDetectedInd.class)) {
		// 	thread.get();
		// }
	}
	
	
	/* 
	 * SQLLDR have been made on TMP_SOC_ID table. All the ACTION column is NULL
	 * CheckNotFound will check if the given GIN could be find on INDIVIDUAL
	 * If it is not the case, we put 4 in ACTION column for 404 NOT FOUND  
	 */
	private void _checkNotFound() {

		final Session session = ((Session) entityManagerFactory.getDelegate()).getSessionFactory().openSession();
		// session.createSQLQuery("ALTER SESSION ENABLE PARALLEL DML").executeUpdate();
		session.beginTransaction();

		session.createSQLQuery("update SIC2.TMP_SOC_ID soc SET soc.ACTION = 4 where soc.ACTION IS NULL and NOT EXISTS (select 1 from SIC2.INDIVIDUS_ALL ia where ia.SGIN = soc.GIN)").executeUpdate();
		if (this.commit) {
			session.getTransaction().commit();							
		} else {
			session.getTransaction().rollback();
		}
		session.close();
		// for (final Future<?> thread : _runAllAnnotatedWith(PurgeIndividualInsertDetectedInd.class)) {
		// 	thread.get();
		// }
	}

	/* 
	 * SQLLDR have been made on TMP_SOC_ID table. All the ACTION column is NULL or is NOT FOUND (4)
	 * CheckNewLines will check if the given GIN could be find on INDIVIDUAL
	 * In that case, we put 3 in ACTION column to create the New lines on EXTERNAL_IDENTIFIER and EXTERNAL_IDENTIFIER_DATA  
	 */
	private void _checkNewLines() {

		final Session session = ((Session) entityManagerFactory.getDelegate()).getSessionFactory().openSession();
		// session.createSQLQuery("ALTER SESSION ENABLE PARALLEL DML").executeUpdate();
		session.beginTransaction();
		
		// Flag the TMP table with 3 value
		session.createSQLQuery("update SIC2.TMP_SOC_ID soc SET soc.ACTION = 3 where ACTION IS NULL AND NOT EXISTS (select 1 from SIC2.EXTERNAL_IDENTIFIER ei where ei.SGIN = soc.GIN)").executeUpdate();

		// Insert the EXTERNAL_IDENTIFIER data
		session.createSQLQuery("insert into SIC2.EXTERNAL_IDENTIFIER (IDENTIFIER_ID, SGIN, IDENTIFIER, TYPE, LAST_SEEN_DATE, CREATION_DATE, CREATION_SIGNATURE, CREATION_SITE, MODIFICATION_DATE, MODIFICATION_SIGNATURE,MODIFICATION_SITE) select SIC2.SEQ_EXTERNAL_IDENTIFIER.NEXTVAL, soc.GIN, soc.SOCIAL_ID, soc.MEDIA_TYPE, SYSDATE, SYSDATE, 'BATCH_QVI', 'QVI', SYSDATE, 'BATCH_QVI', 'QVI' from SIC2.TMP_SOC_ID soc where ACTION = 3 AND NOT EXISTS (select 1 from SIC2.EXTERNAL_IDENTIFIER ei where ei.SGIN = soc.GIN)").executeUpdate();

		// Insert the EXTERNAL_IDENTIFIER_DATA data for USED_BY_AF/KL
		session.createSQLQuery("insert into SIC2.EXTERNAL_IDENTIFIER_DATA (IDENTIFIER_DATA_ID, IDENTIFIER_ID, KEY, VALUE, CREATION_DATE, CREATION_SIGNATURE, CREATION_SITE, MODIFICATION_DATE, MODIFICATION_SIGNATURE, MODIFICATION_SITE) select SIC2.SEQ_EXTERNAL_IDENTIFIER_DATA.NEXTVAL, ei.IDENTIFIER_ID, 'USED_BY_' || soc.PNM_AIRLINE, 'Y', SYSDATE, 'BATCH_QVI', 'QVI', SYSDATE, 'BATCH_QVI', 'QVI' from SIC2.TMP_SOC_ID soc, EXTERNAL_IDENTIFIER ei where soc.ACTION = 3 and ei.SGIN = soc.GIN").executeUpdate();

		// Insert the EXTERNAL_IDENTIFIER_DATA data for PNM_NAME
		session.createSQLQuery("insert into SIC2.EXTERNAL_IDENTIFIER_DATA (IDENTIFIER_DATA_ID, IDENTIFIER_ID, KEY, VALUE, CREATION_DATE, CREATION_SIGNATURE, CREATION_SITE, MODIFICATION_DATE, MODIFICATION_SIGNATURE, MODIFICATION_SITE) select SIC2.SEQ_EXTERNAL_IDENTIFIER_DATA.NEXTVAL, ei.IDENTIFIER_ID, 'PNM_NAME', soc.PNM_NAME , SYSDATE, 'BATCH_QVI', 'QVI', SYSDATE, 'BATCH_QVI', 'QVI' from SIC2.TMP_SOC_ID soc, EXTERNAL_IDENTIFIER ei where soc.ACTION = 3 and ei.SGIN = soc.GIN").executeUpdate();

		if (this.commit) {
			session.getTransaction().commit();							
		} else {
			session.getTransaction().rollback();
		}
		session.close();
		// for (final Future<?> thread : _runAllAnnotatedWith(PurgeIndividualInsertDetectedInd.class)) {
		// 	thread.get();
		// }
	}
	
	// REPIND-1264 : Prepare a report
	private  void PreFillAllExtIdOnReport() {
		// HashMap<String, Integer> extTypeIdsReport = new HashMap<String, Integer>();
		
		for (String extTypeId : this.extTypeIds) {
//			if (this.extTypeIdsReport.containsKey(extTypeId)) {
				this.extTypeIdsReport.put(extTypeId, 0);
//			}
		}
		
		// return extTypeIdsReport;	
	}	

	private void FillAnExtIdOnReport(String extTypeId) {
		
		// On check que le repot contient bien la valeur
		if (this.extTypeIdsReport.containsKey(extTypeId)) {
			
			// On parcours la liste des reports
			for (Entry<String, Integer> extTypeIdSet : this.extTypeIdsReport.entrySet()) {
				
				// Si on trouve la clé pour ce SocialID
				if (extTypeIdSet.getKey().equals(extTypeId)) {
					
					// On recupere la valeur 
					Integer extTypeIdSetValue = extTypeIdSet.getValue();
					
					// On la pose a Zero ou on l incremente
					if (extTypeIdSetValue == null) {
						extTypeIdSet.setValue(0);	
					} else {
						extTypeIdSetValue++;
						extTypeIdSet.setValue(extTypeIdSetValue);
					}
					// On met a jour la clé/valeur
					// this.extTypeIdsReport.put(extTypeIdSet.getKey(), extTypeIdSet.getValue());
				}
			}
		} 
		else {
			this.extTypeIdsReport.put(extTypeId, 0);
		}
	}

	private void LogAnExtIdReport() {
		

		LOG.info("REPORT : OK            " +  this.reportNbOK);
		
		for (Entry<String, Integer> extTypeIdSet : this.extTypeIdsReport.entrySet()) {
			if (extTypeIdSet.getValue() != null && !"0".equals(extTypeIdSet.getValue().toString())) {
				LOG.info("           " + extTypeIdSet.getKey() + " " +  extTypeIdSet.getValue());
			}
		}

		LOG.info("REPORT : ERROR         " +  this.reportNbError);
		LOG.info("REPORT : ALREADY EXIST " +  this.reportNbAlready);
		LOG.info("REPORT : NOT FOUND     " +  this.reportNbNotFound);
		LOG.info("REPORT : INVALID LINE  " +  this.reportNbInvalidLine);
		LOG.info("REPORT : --------------------------- ");
		LOG.info("REPORT : TOTAL         " +  this.reportNbTotal);		
	}
	
	/* (non-Javadoc)
	 * @see com.airfrance.jraf.batch.common.BatchArgs#parseArgs(java.lang.String[])
	 */
	@Override
	protected void parseArgs(String[] args) throws BatchException {
		if (args == null || args.length == 0) {
			printHelp();
			throw new BatchException(NO_ARGUMENTS_TO_THE_BATCH);
		}

		List<BatchExternalIdentifierArgsEnum> argsProvided = new ArrayList<BatchExternalIdentifierArgsEnum>();
		for (int i = 0; i < args.length; ++i) {
			String s = args[i];

			if (s.contains("-")) {
				String currentArg = s.split(ARGS_SEPARATOR)[1];

				try {
					BatchExternalIdentifierArgsEnum currArgEnum = BatchExternalIdentifierArgsEnum.valueOf(currentArg);
					argsProvided.add(currArgEnum);

					switch (currArgEnum) {
					case f:
						this.setFileToProcess(args[i++ + 1]);
						break;
					case C:
						this.commit = true;
						break;
					default:
						break;
					}

				} catch (IllegalArgumentException e) {
					printHelp();
					throw new BatchException(ARGUMENT_NOT_VALID);
				}
			}
		}

		for (BatchExternalIdentifierArgsEnum currEnum : BatchExternalIdentifierArgsEnum.values()) {
			if (currEnum.getRequirement().equals(RequirementEnum.MANDATORY)) {
				if (!argsProvided.contains(currEnum)) {
					printHelp();
					throw new BatchException(MANDATORY_ARGUMENT_MISSING);
				}
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.batch.common.BatchArgs#printHelp()
	 */
	@Override
	protected void printHelp() {
		System.out.println("\n###\n"
				+ "USER GUIDE: \nBatchExternalIdentifier.sh -option:\n"
				+ " -f  file.csv : file to load [MANDATORY]\n"
				+ " -C 			 : commit mode on [OPTIONAL], only check validity of input file is done if commit mode = off \n");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(WebConfigBatchRepind.class);

			IBatch batch = (IBatch) ctx.getBean("externalIdentifierBatch");

			LOG.info("Reading args...");
			((BatchExternalIdentifier) batch).parseArgs(args);
			LOG.info("Batch execution...");

			if (((BatchExternalIdentifier) batch).getFileToProcess() != null) {
				batch.execute();
			}

			ctx.close();
			LOG.info(BATCH_EXECUTED_SUCCESSFULLY);

		} catch (JrafDomainException jde) {
			LOG.fatal(jde);
			System.exit(1);
		} catch (Exception e) {
			LOG.fatal("Error during test execution");
			LOG.fatal(e);
			System.exit(1);
		}
		System.exit(0);
	}

	/**
	 * General treatment for the line
	 * 
	 * GIN / IDENTIFIER / TYPE / AIRLINE / NAME / FB / MYACCOUNT
	 * 
	 * @param pLine : line to analyze then to insert in DB
	 * @param pLineNum : line number in input file
	 * @throws JrafDomainException 
	 */
	protected void treatLine(String pLine, int pLineNum, StringBuilder lineReport) {
		treatLine(pLine, pLineNum, lineReport, true);
	}
	
	protected void treatLine(String pLine, int pLineNum, StringBuilder lineReport, boolean isFileProcess) {
		LOG.debug("Compute line: " + pLine);
		String[] externalToAdd = pLine.split(SEPARATOR);
		String gin = "";
		this.reportNbTotal++;

		try {
			// Add line num to lineReport
			if (isFileProcess) {
				lineReport.append(pLineNum);
			} else {
				String pLineNumDb = externalToAdd[6].trim();
				lineReport.append(pLineNumDb);
			}

//			if (externalToAdd.length == SIZE_LINE_INPUT) { // Checks line size
			if (externalToAdd.length >= 4) { // Checks line size
				String typeExt = externalToAdd[2].trim();
				if (typeExt != null && !"".equals(typeExt) && !typeExt.contains("_ID")) {	// On rajoute l'identifiant pour colelr a la base de données... 
					typeExt += "_ID";	
				}
				String externalId = externalToAdd[1].trim();	
				String ginFromFile = externalToAdd[0].trim();
				String fbFromFile = "";
				String myaFromFile = "";
				String airline = externalToAdd[3].trim();
				String name = externalToAdd[4].trim();
								
				if (checkMandatoriesColumn(typeExt, externalId, lineReport)) {

					// Line treatment
					// Checks if external is in the base
					gin = extIdAlreadyExists(externalId, typeExt, airline);
					// Checks if social is in the base
					if (StringUtils.isBlank(gin)) {
						// Checks if individual exists (search by GIN, then by FB, then by MyA)
						if (StringUtils.isBlank(gin)) {
							gin = getIndividualGin(ginFromFile, formatFB(fbFromFile), myaFromFile);
						}
					}

					if (this.lineError) {
						// LOG.info("gin:" + gin);
						lineReport.append(gin);
					} else {
						LOG.info("gin:" + gin);
						if (this.commit) { // Create or update Individual if necessary then create or update External Identifier
							gin = helper.createOrUpdateIndividualAndExtId(gin, externalId, typeExt, airline, name);
							
							lineReport.append(";").append(gin).append(";SUCCESSFULLY INSERTED\n");							
						} else {
							lineReport.append(";").append(gin).append(";INSERTION NOT COMMITED\n");
						}
						FillAnExtIdOnReport(typeExt);
						this.reportNbOK++;
					}
				} else {					
					this.reportNbError++;
				}
			} else {
				this.reportNbInvalidLine++;
				lineReport.append(";;INVALID LINE\n");
			}
		} catch (JrafDomainException jde) {
			LOG.error("Insertion aborted for line " + lineReport, jde);
			lineReport.append(";;INSERTION ABORTED\n");
			this.reportNbError++;
		}
	}


	/**
	 * Checks if external identifier already exists in the DB
	 * @param external
	 * @param type
	 * @return the gin if exists
	 * @throws JrafDomainException 
	 */
	protected String extIdAlreadyExists(String external, String type, String airline) throws JrafDomainException {
		this.lineError = false;
		String existingGIN = "";
		try {
			// Gets the external ID
			ExternalIdentifierDTO extIDDTO = eids.existExternalIdentifier(external, type);
			if (extIDDTO != null) {
				// On check si il y a un AIrline si il est le même
				if (airline != null && !"".equals(airline)) {

					if (extIDDTO.getExternalIdentifierDataList() == null) {
						
					} else {
						// Checks if current external has the status "USED_BY_KL" to "Y"				
						for (ExternalIdentifierDataDTO eidDTO : extIDDTO.getExternalIdentifierDataList()) {
							if (("USED_BY_" + airline).equals(eidDTO.getKey()) && "Y".equals(eidDTO.getValue())) {
								this.lineError = true;		// Is it an error to catch an existing Identifier ?
								this.reportNbAlready++;
								return ";" +extIDDTO.getGin()+";EXTERNAL ALREADY EXISTS\n";				// SAME AIRLINE
							}
						}
					}
//					this.lineError = true;		// Is it an error to catch an existing Identifier ?
//					return ";" +extIDDTO.getGin()+";EXTERNAL ALREADY EXISTS\n";						// DIFFERENT AIRLINE										
				}
				// External is not used for KL, we get the GIN of individual
				existingGIN = extIDDTO.getGin();
			}
		} catch (JrafDomainException e) {
			// TODO : change code error for this case
			if (e.getMessage().contains("External identifier linked to different individual")) {
				this.lineError = true;
				this.reportNbError++;
				return ";;EXTERNAL LINKED TO DIFFERENT INDIVIDUAL\n";
			}
			throw e;
		}

		return existingGIN;
	}

	/**
	 * Get the gin individual if exists. Returns a string containing NOT FOUND if an error is met
	 * @param gin
	 * @param fb
	 * @param myA
	 * @return
	 * @throws JrafDomainException
	 */
	protected String getIndividualGin(String gin, String fb, String myA) throws JrafDomainException {

		String ginRet = "";
		this.lineError = false;

		// Check individual GIN validity.
		if (StringUtils.isNotBlank(gin)) {
			IndividuDTO indDTO = individuDS.getByGin(gin);
			if (indDTO == null) {
				this.lineError = true;
				
				this.reportNbNotFound++;
				
				return ";;PROVIDED GIN " + NOT_FOUND + " " + gin + "\n";
			}
			return indDTO.getSgin();
		}

		if (StringUtils.isNotBlank(fb)) {
			// Get gin using fb
			ginRet = provideGin(fb, IdentifierOptionTypeEnum.FLYING_BLUE.toString());
		} else if (StringUtils.isNotBlank(myA)) {
			// Get gin using myA
			ginRet = provideGin(myA, "MA");//IdentifierOptionTypeEnum.ANY_MYACCOUNT.toString());
		}

		return ginRet;
	}

	/**
	 * 
	 * Call The MyAccountUS to get the gin from Flying Blue or MyAccount
	 * 
	 * @param identifier
	 * @param type
	 * @return
	 * @throws JrafDomainException
	 */
	private String provideGin(String identifier, String type) throws JrafDomainException {
		ProvideGinForUserIdRequestDTO requestMyAccountDatas = new ProvideGinForUserIdRequestDTO();
		requestMyAccountDatas.setIdentifier(identifier);
		requestMyAccountDatas.setIdentifierType(type);

		ProvideGinForUserIdResponseDTO pgfuir = myAccountUS.provideGinForUserId(requestMyAccountDatas);
		if (pgfuir != null && StringUtils.isNotBlank(pgfuir.getFoundIdentifier())) {
			return pgfuir.getGin();
		} else {
			this.lineError = true;
			return ";;PROVIDED " + type + " " + NOT_FOUND + " " + identifier + "\n";
		}
	}

	/**
	 * Divide the line to obtain a line for an external
	 * @param realLine
	 * @return
	 */
	private List<String> temporaryLineTreatmentFromKLM(String realLine) {

		List<String> realLines = new ArrayList<String>();
		realLine += ";END";
		String[] lineToDivide = realLine.split(SEPARATOR);

		// Flying blue
		String flyingBlueNumber = lineToDivide[97] != null ? lineToDivide[97].trim() : "";
		String gin = lineToDivide[99] != null ? lineToDivide[99].trim() : "";

		// Externals
		String inst_userId = lineToDivide[103] != null ? lineToDivide[103].trim(): "";
		String kko_userId = lineToDivide[105] != null ? lineToDivide[105].trim() : "";
		String klt_userId = lineToDivide[107] != null ? lineToDivide[107].trim() : "";
		String fb_userId = lineToDivide[126] != null ? lineToDivide[126].trim() : "";
		String hyv_userId = lineToDivide[132] != null ? lineToDivide[132].trim() : "";
		String lkin_userId = lineToDivide[140] != null ? lineToDivide[140].trim() : "";
		String twt_userId = lineToDivide[155] != null ? lineToDivide[155].trim() : "";
		String sinaWeibo_userId = lineToDivide[166] != null ? lineToDivide[166].trim() : "";
		String weChat_userId = lineToDivide[173] != null ? lineToDivide[173].trim() : "";
		String whatsapp_userId = lineToDivide[176] != null ? lineToDivide[176].trim() : "";

		// Create realLines for treatment
		LOG.debug("createLines for temporary KLM treatment");

		StringBuilder lineInst = buildTempLine("INSTAGRAM_ID", inst_userId, gin, flyingBlueNumber);
		if (StringUtils.isNotBlank(lineInst)) {
			realLines.add(lineInst.toString());
			LOG.debug(lineInst);
		}
		
		StringBuilder lineKKO = buildTempLine("KAKAO_ID", kko_userId, gin, flyingBlueNumber);
		if (StringUtils.isNotBlank(lineKKO)) {
			realLines.add(lineKKO.toString());
			LOG.debug(lineKKO);
		}
		
		StringBuilder lineKLT = buildTempLine("KLOOT_ID", klt_userId, gin, flyingBlueNumber);
		if (StringUtils.isNotBlank(lineKLT)) {
			realLines.add(lineKLT.toString());
			LOG.debug(lineKLT);
		}
		
		StringBuilder lineFB = buildTempLine(FACEBOOK_ID, fb_userId, gin, flyingBlueNumber);
		if (StringUtils.isNotBlank(lineFB)) {
			realLines.add(lineFB.toString());
			LOG.debug(lineFB);
		}

		StringBuilder lineHYV = buildTempLine("HYVES_ID", hyv_userId, gin, flyingBlueNumber);
		if (StringUtils.isNotBlank(lineHYV)) {
			realLines.add(lineHYV.toString());
			LOG.debug(lineHYV);
		}
		
		StringBuilder lineLkn = buildTempLine("LINKEDIN_ID", lkin_userId, gin, flyingBlueNumber);
		if (StringUtils.isNotBlank(lineLkn)) {
			realLines.add(lineLkn.toString());
			LOG.debug(lineLkn);
		}

		StringBuilder lineTwt = buildTempLine(TWITTER_ID, twt_userId, gin, flyingBlueNumber);
		if (StringUtils.isNotBlank(lineTwt)) {
			realLines.add(lineTwt.toString());
			LOG.debug(lineTwt);
		}

		StringBuilder lineSWB = buildTempLine("SINAWEIBO_ID", sinaWeibo_userId, gin, flyingBlueNumber);
		if (StringUtils.isNotBlank(lineSWB)) {
			realLines.add(lineSWB.toString());
			LOG.debug(lineSWB);
		}

		StringBuilder lineWCT= buildTempLine(WECHAT_ID, weChat_userId, gin, flyingBlueNumber);
		if (StringUtils.isNotBlank(lineWCT)) {
			realLines.add(lineWCT.toString());
			LOG.debug(lineWCT);
		}

		StringBuilder lineWAP = buildTempLine(WHATSAPP_ID, whatsapp_userId, gin, flyingBlueNumber);
		if (StringUtils.isNotBlank(lineWAP)) {
			realLines.add(lineWAP.toString());
			LOG.debug(lineWAP);
		}

		return realLines;
	}

	/**
	 * Divide the line to obtain a line for an external
	 * @param realLine
	 * @return
	 */
	private List<String> temporaryLineTreatmentFromTravelerDB(String realLine) {

		List<String> realLines = new ArrayList<String>();
		realLine += ";END";
		String[] lineToDivide = realLine.split(SEPARATOR);

		// GIN
		String gin = lineToDivide[1] != null ? lineToDivide[1].trim().replace("\"", "") : "";
		

		// Externals
		String type = lineToDivide[8] != null ? lineToDivide[8].trim().replace("\"", "") : "";
		String value = lineToDivide[4] != null ? lineToDivide[4].trim().replace("\"", "") : "";

		// Create realLines for treatment
		LOG.debug(CREATELINES_FOR_TEMP_TRAVEL_DB);

		StringBuilder lineInst = new StringBuilder();
		
		switch (type) {
		
			case WHATSAPP:
				lineInst = buildTempLine(WHATSAPP_ID, value, gin, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
			break;
		

			case FACEBOOK:
				lineInst = buildTempLine(FACEBOOK_ID, value, gin, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
			break;				

			case WECHAT:
				lineInst = buildTempLine(WECHAT_ID, value, gin, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
			break;	
			
			case MOBILE:		// WE DO NOT CARE ABOUT MOBILE BECAUSE THIS IS PNM_ID
/*				lineInst = buildTempLine("MOBILE_ID", value, gin, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
*/			break;	

			case TWITTER:
				lineInst = buildTempLine(TWITTER_ID, value, gin, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
			break;	

			default:

				LOG.debug(WARN + type + " et " + value);
				break;
		}
		

		return realLines;
	}
	
	
	/**
	 * Divide the line to obtain a line for an external
	 * @param realLine
	 * @return
	 */
	private List<String> temporaryLineTreatmentFromTravelerDB2(String realLine) {

		List<String> realLines = new ArrayList<String>();
		realLine += ";END";
		String[] lineToDivide = realLine.split(SEPARATOR);

		// GIN
		String gin = lineToDivide[2] != null ? lineToDivide[2].trim().replace("\"", "") : "";
		

		// Externals
		String type = lineToDivide[9] != null ? lineToDivide[9].trim().replace("\"", "") : "";
		String value = lineToDivide[5] != null ? lineToDivide[5].trim().replace("\"", "") : "";

		// Create realLines for treatment
		LOG.debug(CREATELINES_FOR_TEMP_TRAVEL_DB);

		StringBuilder lineInst = new StringBuilder();
		
		switch (type) {
		
			case WHATSAPP:
				lineInst = buildTempLine(WHATSAPP_ID, value, gin, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
			break;
		

			case FACEBOOK:
				lineInst = buildTempLine(FACEBOOK_ID, value, gin, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
			break;				

			case WECHAT:
				lineInst = buildTempLine(WECHAT_ID, value, gin, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
			break;	
			
			case MOBILE:	// WE DO NOT CARE ABOUT MOBILE BECAUSE THIS IS PNM_ID
/*				lineInst = buildTempLine("MOBILE_ID", value, gin, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
*/			break;	

			case TWITTER:
				lineInst = buildTempLine(TWITTER_ID, value, gin, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
			break;	

			default:

				LOG.debug(WARN + type + " et " + value);
				break;
		}
		

		return realLines;
	}

	/**
	 * Divide the line to obtain a line for an external
	 * @param realLine
	 * @return
	 */
	private List<String> temporaryLineTreatmentFromTravelerDB3(String realLine) {

		List<String> realLines = new ArrayList<String>();
		realLine += ",END";
		String[] lineToDivide = realLine.split(SEPARATOR);

		
		
		// GIN
		String gin = lineToDivide[0] != null ? lineToDivide[0].trim().replace("\"", "") : "";
		

		// Externals
		String type = lineToDivide[2] != null ? lineToDivide[2].trim().replace("\"", "") : "";
		String value = lineToDivide[1] != null ? lineToDivide[1].trim().replace("\"", "") : "";
		
		// External Data
		String airline = lineToDivide[3] != null ? lineToDivide[3].trim().replace("\"", "") : "";

		// Create realLines for treatment
		LOG.debug(CREATELINES_FOR_TEMP_TRAVEL_DB);

		StringBuilder lineInst = new StringBuilder();
		
		switch (type) {
		
			case WHATSAPP:
				lineInst = buildTempLine(WHATSAPP_ID, value, gin, "", airline, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
			break;
		

			case FACEBOOK:
				lineInst = buildTempLine(FACEBOOK_ID, value, gin, "", airline, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
			break;				

			case WECHAT:
				lineInst = buildTempLine(WECHAT_ID, value, gin, "", airline, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
			break;	
			
			case MOBILE:	// WE DO NOT CARE ABOUT MOBILE BECAUSE THIS IS PNM_ID
/*				lineInst = buildTempLine("MOBILE_ID", value, gin, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
*/			break;	

			case TWITTER:
				lineInst = buildTempLine(TWITTER_ID, value, gin, "", airline, "");
				if (StringUtils.isNotBlank(lineInst)) {
					realLines.add(lineInst.toString());
					LOG.debug(lineInst);
				}			
			break;	

			default:

				LOG.debug(WARN + type + " et " + value);
				break;
		}
		

		return realLines;
	}

	/**
	 * Divide the line to obtain a line for an external
	 * @param realLine
	 * @return
	 */
	private String temporaryLineTreatmentFromTravelerDB4(String realLine) {

		String returnLines = "";
		realLine += ",END";
		String[] lineToDivide = realLine.split(SEPARATOR);
		
		// GIN
		String gin = lineToDivide[0] != null ? lineToDivide[0].trim().replace("\"", "") : "";

		// Externals
		String type = lineToDivide[4] != null ? lineToDivide[4].trim().replace("\"", "") : "";
		String value = lineToDivide[1] != null ? lineToDivide[1].trim().replace("\"", "") : "";
		
		// External Data
		String airline = lineToDivide[2] != null ? lineToDivide[2].trim().replace("\"", "") : "";		
		String name = lineToDivide[3] != null ? lineToDivide[3].trim().replace("\"", "") : "";

		// Create realLines for treatment
		LOG.debug("createLines for temporary TRAVEL DB treatment version 4");

		StringBuilder lineInst = new StringBuilder();
		
		switch (type) {
		
			// GIN / IDENTIFIER / TYPE / AIRLINE / NAME / FB / MYACCOUNT
		
			case WHATSAPP_ID:
			case FACEBOOK_ID:
			case WECHAT_ID:
			case TWITTER_ID:
				lineInst = buildTempLine(gin, value, type, airline, name, "");
				if (StringUtils.isNotBlank(lineInst)) {
					returnLines += lineInst + SEPARATOR;
					LOG.debug(lineInst);
				}
			break;

			default:
				LOG.debug(WARN + type + " et " + value);
			break;
		}

		return returnLines;
	}
	
	/**
	 * Build the line to treat
	 * @param extType
	 * @param extId
	 * @param fbNum
	 * @return
	 */
	private StringBuilder buildTempLine(String extType, String extId, String gin, String fbNum) {
		return buildTempLine(extType, extId, gin, fbNum, null, null);
	}

	/**
	 * Build the line to treat
	 * @return lineFB
	 */
	private StringBuilder buildTempLine(String field1, String field2, String field3, String field4, String field5, String field6) {
		StringBuilder lineFB = new StringBuilder();
		if (StringUtils.isNotBlank(field2)) {
			lineFB.append(field1).append(SEPARATOR).append(field2).append(SEPARATOR).append(field3).append(SEPARATOR)
			.append(field4).append(SEPARATOR).append(field5).append(SEPARATOR).append(field6).append(SEPARATOR)
			.append("END");
		}
		return lineFB;
	}
	
	/**
	 * Check mandatories column
	 * @param extType
	 * @param extID
	 * @param lineReport
	 * @return <strong>true</strong> if all mandatories field are ok, <strong>false</strong> else.
	 */
	private boolean checkMandatoriesColumn(String extType, String extID, StringBuilder lineReport) {

		if (StringUtils.isBlank(extType)) { // Checks if external type is present
			lineReport.append(";;MISSING EXTERNAL TYPE\n");
			return false;
		} else if (StringUtils.isBlank(extID)) { // Checks if external identifier is present
			lineReport.append(";;MISSING EXTERNAL IDENTIFIER\n");
			return false;
		} else if (!extTypeIds.contains(extType)) { // Checks if external type is valid
			lineReport.append(";;INVALID EXTERNAL TYPE '");
			// Add some additional information
			lineReport.append(extType);			
			lineReport.append("'\n");
			
			return false;
		} else {
			return true;
		}
	}

	private String formatFB(String fbFromFile) {
		if (StringUtils.isNotBlank(fbFromFile) && fbFromFile.length() == 10) {
			return "00" + fbFromFile;
		}
		return fbFromFile;

	}
	
	@Deprecated		// Batch take TMP_SOC_ID table in DB and not a file anymore
	private String getInputFileRecordNumber(String path) {
		if (path == null) {
			return "";
		}
		Pattern pattern = Pattern.compile(REGEX_RECORD_NUMBER);
		Matcher matcher = pattern.matcher(path);
		
		if (matcher.find()) {
			return matcher.group(1) + "_";
		} else {
			return "";
		}
	}

	public int getVersionOfFile() {
		return versionOfFile;
	}

	public void setVersionOfFile(int versionOfFile) {
		this.versionOfFile = versionOfFile;
	}

	/**
	 * Getter
	 * @return the fileToProcess
	 */
	public String getFileToProcess() {
		return this.fileToProcess;
	}

	/**
	 * Setter
	 * @param fileName the file to set
	 */
	public void setFileToProcess(String fileName) {
		this.fileToProcess = fileName;
	}

	/**
	 * Getter
	 * @return
	 */
	public boolean getLineError() {
		return lineError;
	}

	/**
	 * Setter
	 * @param commit
	 */
	public void setCommit(boolean commit) {
		this.commit = commit;
	}

	/**
	 * Setter
	 * @param extTypeIds
	 */
	public void setExtTypeIds(List<String> extTypeIds) {
		this.extTypeIds = extTypeIds;
	}

	public long getReportNbOK() {
		return reportNbOK;
	}

	public void setReportNbOK(long reportNbOK) {
		this.reportNbOK = reportNbOK;
	}

	public long getReportNbError() {
		return reportNbError;
	}

	public void setReportNbError(long reportNbError) {
		this.reportNbError = reportNbError;
	}

	public long getReportNbAlready() {
		return reportNbAlready;
	}

	public void setReportNbAlready(long reportNbAlready) {
		this.reportNbAlready = reportNbAlready;
	}

	public long getReportNbNotFound() {
		return reportNbNotFound;
	}

	public void setReportNbNotFound(long reportNbNotFound) {
		this.reportNbNotFound = reportNbNotFound;
	}

	public long getReportNbInvalidLine() {
		return reportNbInvalidLine;
	}

	public void setReportNbInvalidLine(long reportNbInvalidLine) {
		this.reportNbInvalidLine = reportNbInvalidLine;
	}

	public long getReportNbTotal() {
		return reportNbTotal;
	}
	
	public void setReportNbTotal(long reportNbTotal) {
		this.reportNbTotal = reportNbTotal;
	}
	
	public long getReportNbTotalCalculated() {
		return this.getReportNbAlready() + this.getReportNbError() + this.getReportNbInvalidLine() + this.getReportNbNotFound() + this.getReportNbOK();
	}
	
}
