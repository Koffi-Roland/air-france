package com.airfrance.jraf.batch.contract;

import com.afklm.repindpp.paymentpreference.service.internal.PaymentPreferencesDS;
import com.airfrance.jraf.batch.common.BatchArgs;
import com.airfrance.jraf.batch.common.IBatch;
import com.airfrance.jraf.batch.common.type.RequirementEnum;
import com.airfrance.jraf.batch.config.WebConfigBatchRepind;
import com.airfrance.jraf.batch.individu.helper.BatchExternalIdentifierHelper;
import com.airfrance.jraf.batch.individu.type.BatchExternalIdentifierArgsEnum;
import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.exception.MissingParameterException;
import com.airfrance.ref.exception.RefException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.IdentifierOptionTypeEnum;
import com.airfrance.repind.dto.individu.AccountDataDTO;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdRequestDTO;
import com.airfrance.repind.dto.individu.provideginforuserid.ProvideGinForUserIdResponseDTO;
import com.airfrance.repind.dto.role.RoleContratsDTO;
import com.airfrance.repind.entity.preference.Preference;
import com.airfrance.repind.service.individu.internal.AccountDataDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.service.internal.unitservice.individu.MyAccountUS;
import com.airfrance.repind.service.preference.internal.PreferenceDS;
import com.airfrance.repind.service.role.internal.BusinessRoleDS;
import com.airfrance.repind.service.role.internal.RoleDS;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author t412211
 *
 */
@Service("purgeFlyingBlueBatch")
public class BatchPurgeFlyingBlue extends BatchArgs {


    private static final Log LOG = LogFactory.getLog(BatchPurgeFlyingBlue.class);

    private static final String ARGS_SEPARATOR = "-";
    private static final String CSV = ".csv";
    private static final String PURGE_FLYING_BLUE_DATA_DIR = "/app/REPIND/data/ISIS/";
    private static final String HEAD_LINE_OUTPUT = "LINE_NUMBER;GIN;STATUS\n";
    private static final String NOT_FOUND = "NOT FOUND";

    private boolean commit = false;
    private boolean lineError = false;
    private String fileToProcess;
    private List<String> extTypeIds;

    // Buffer for log file
    private BufferedWriter bfwReportCsv;

    private BufferedWriter bfwCatchUpCsv;

    @Autowired
    private MyAccountUS myAccountUS;

    @Autowired
    private IndividuDS individuDS;

    @Autowired
    private BatchExternalIdentifierHelper helper;

    @Autowired
    protected AccountDataDS accountDataDS;

    @Autowired
    protected BusinessRoleDS businessRoleDS;

    @Autowired
    protected CommunicationPreferencesDS commPrefDS;

    @Autowired
    private RoleDS roleDS;

    @Autowired
    private PreferenceDS preferenceDS;

    @Autowired
    private PaymentPreferencesDS paymentPreferencesDS;

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
            Path directory = Paths.get(PURGE_FLYING_BLUE_DATA_DIR);
            Files.createDirectories(directory);

            List<String> lines;
            StringBuilder report;
            StringBuilder catchUp;

            // Report file is created
            // Get path to CSV file
            Path loc = Paths.get(PURGE_FLYING_BLUE_DATA_DIR + "BatchPurgeFlyingBlue_report_" + dDay + CSV);
            Path catchUpLoc = Paths.get(PURGE_FLYING_BLUE_DATA_DIR + "PHAPUR_rattrapage_" + dDay + CSV);

            File reportCsv = new File(loc.toString());
            File catchUpCsv = new File(catchUpLoc.toString());

            bfwCatchUpCsv = new BufferedWriter(new FileWriter(catchUpCsv, true));
            bfwReportCsv = new BufferedWriter(new FileWriter(reportCsv, true));
            bfwReportCsv.write(HEAD_LINE_OUTPUT);

            // Treat datas on line (insert, update individual, etc...)
            // Init type extId
            this.extTypeIds = helper.getAllTypeExtId();

            // Get path to input file
            Path toRead = Paths.get(fileToProcess);
            // Get all lines from input file
            lines = Files.readAllLines(toRead, StandardCharsets.ISO_8859_1);

            for (String line : lines) {

                report = new StringBuilder();
                catchUp= new StringBuilder();
                treatLine(line, lineNum, report,catchUp, false);
                bfwReportCsv.write(report.toString());
                bfwCatchUpCsv.write(catchUp.toString());
                lineNum++;
            }

            bfwReportCsv.close();
            bfwCatchUpCsv.close();

        } catch (IOException e) {
            throw new JrafDomainException(e);
        }
    }


    /* (non-Javadoc)
     * @see com.airfrance.jraf.batch.common.BatchArgs#parseArgs(java.lang.String[])
     */
    @Override
    protected void parseArgs(String[] args) throws RefException {
        if (args == null || args.length == 0) {
            printHelp();
            throw new MissingParameterException(NO_ARGUMENTS_TO_THE_BATCH);
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
                    throw new InvalidParameterException(ARGUMENT_NOT_VALID);
                }
            }
        }

        for (BatchExternalIdentifierArgsEnum currEnum : BatchExternalIdentifierArgsEnum.values()) {
            if (currEnum.getRequirement().equals(RequirementEnum.MANDATORY)) {
                if (!argsProvided.contains(currEnum)) {
                    printHelp();
                    throw new MissingParameterException(MANDATORY_ARGUMENT_MISSING);
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
                + "USER GUIDE: \nBatchPurgeFlyingBlue.sh -option:\n"
                + " -f  file.csv : file to load [MANDATORY]\n"
                + " -C 			 : commit mode on [OPTIONAL], only check validity of input file is done if commit mode = off \n");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {

            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(WebConfigBatchRepind.class);

            IBatch batch = (IBatch) ctx.getBean("purgeFlyingBlueBatch");

            LOG.info("Reading args...");
            ((BatchPurgeFlyingBlue) batch).parseArgs(args);
            LOG.info("Batch execution...");

            if (((BatchPurgeFlyingBlue) batch).getFileToProcess() != null) {
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
    protected void treatLine(String pLine, int pLineNum, StringBuilder lineReport,StringBuilder lineCatchUp) {
        treatLine(pLine, pLineNum, lineReport, lineCatchUp,true);
    }

    protected void treatLine(String pLine, int pLineNum, StringBuilder lineReport,StringBuilder lineCatchUp, boolean isFileProcess) {
        LOG.debug("Compute line: " + pLine);

        String cin = "";

        // Add line num to lineReport
        lineReport.append(pLineNum);

        // On ne traite pas la premiere ligne						// CHANGEMENT
        // On doit avoir assez de ccaractere pour retirer le GIN 	// UNE LIGNE = UN CIN
        if (pLine != null && pLine.length() >= 12) {

            try {
                cin = pLine.trim();                    // On trouve le CIN	// UNE LIGNE = UN CIN

                // On check si c est bien un numeric
                if (cin != null && SicStringUtils.isNumeric(cin) && cin.length() == 12) {

                    if (this.lineError) {
                        LOG.info("cin:" + cin);
                        lineReport.append(cin);
                    } else {
                        LOG.info("cin:" + cin);
                        if (this.commit) {

                            String errMsgPurge = "";
                            String gin = "";

                            RoleContratsDTO roleContractFB = roleDS.findRoleContractByNumContract(cin);        // On recupère le CIN et GIN
                            if (roleContractFB == null) {
                                errMsgPurge += "FB CONTRACT NOT FOUND ";

                            } else {
                                //- Catch the GIN linked to this FB Contract number
                                gin = roleContractFB.getGin();


                                String numContract = roleContractFB.getNumeroContrat();
                                //- DELETE this FB Contract linked to this CIN
                                roleDS.remove(roleContractFB);

                                //Get Account associated to roleContractFB
                                AccountDataDTO accountDataFB = accountDataDS.findByFbIdentifier(numContract);
                                //Check if accountDataFB exists before process
                                if (accountDataFB != null) {
                                    String accountIdentifier = accountDataFB.getAccountIdentifier();
                                    //Get Contact MA associated to accountDataFB
                                    RoleContratsDTO roleContratsDTO = roleDS.findContractByCinAndType(accountIdentifier, "MA");
                                    //check if roleContratsDTO exists before deletion
                                    if (roleContratsDTO != null) {
                                        roleDS.remove(roleContratsDTO);
                                    }
                                    //delete Account Data
                                    accountDataDS.deleteById(accountDataFB.getId());


                                }
                                //Get other contacts FP
                                List<RoleContratsDTO> roleContractsFB = roleDS.findRoleContrats(gin, "FP");

                                // REPIND-1652 : If we find other contact FP, Do not Delete COM PREF
                                if (roleContractsFB != null && roleContractsFB.isEmpty()) {
                                    //- DELETE all Communication Preference of Domain F (FlyingBlue)
                                    List<CommunicationPreferencesDTO> listComPref = commPrefDS.findComPrefIdByDomain(gin, "F");

                                    if (listComPref != null) {
                                        for (CommunicationPreferencesDTO comPref : listComPref) {

                                            // Si une CommPref Exist
                                            if (comPref != null) {
                                                commPrefDS.remove(comPref);
                                            }
                                        }
                                    }
                                    //Delete TCC and TDC preferences
                                    List<Preference> preferencesTcc = preferenceDS.findByGinAndType(gin, "TCC");
                                    List<Preference> preferencesTdc = preferenceDS.findByGinAndType(gin, "TDC");
                                    List<Long> allPreferencesIdToDelete = Stream.concat(preferencesTcc.stream(), preferencesTdc.stream())
                                            .map(preference -> preference.getPreferenceId())
                                            .collect(Collectors.toList());
                                    if (allPreferencesIdToDelete != null) {
                                        LOG.info("Found "+ allPreferencesIdToDelete.size() +" TCC AND TDC prefs to delete. Their preferences data will also be deleted");
                                        for (Long prefId : allPreferencesIdToDelete) {

                                            // Si une CommPref Exist
                                            if (prefId != null) {
                                                preferenceDS.deleteAllPreferenceData(String.valueOf(prefId));
                                                preferenceDS.remove(preferenceDS.findByPreferenceId(prefId));
                                            }
                                        }
                                    }

                                    //Delete Payement Preferences
                                    List<String> paymentIdsList = paymentPreferencesDS
                                            .findByGin(gin).stream()
                                            .map(paymentDetailsEntity -> String.valueOf(paymentDetailsEntity.getPaymentId()))
                                            .collect(Collectors.toList());
                                    if(paymentIdsList != null){
                                        LOG.info("Found "+paymentIdsList.size()+" payment details to delete");
                                        for(String payementId : paymentIdsList){
                                            paymentPreferencesDS.deletePaymentPreferencesByPaymentId(payementId);
                                        }
                                    }
                                }

                            }
                            lineReport.append(";").append(cin).append(";SUCCESSFULLY DELETED ").append(errMsgPurge).append("\n");

                        } else {
                            lineReport.append(";").append(cin).append(";INSERTION NOT COMMITED\n");
                            lineCatchUp.append(cin).append("\n");
                        }
                    }
                } else {
                    lineReport.append(";").append(cin).append(";GIN NOT VALID\n");
                }
            } catch (Exception jde) {
                LOG.error("Insertion aborted for line " + lineReport, jde);
                lineCatchUp.append(cin).append("\n");
                lineReport.append(";").append(cin).append(";INSERTION ABORTED "+ jde +"\n");
            }
        } else {
            lineReport.append(";;INVALID LINE '" + pLine + "' (" + ((pLine != null) ? pLine.length() : "0") + ")\n");
        }
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


                return ";;PROVIDED GIN " + NOT_FOUND + " " + gin + "\n";
            }
            return indDTO.getSgin();
        }

        if (StringUtils.isNotBlank(fb)) {
            // Get gin using fb
            ginRet = provideGin(fb, IdentifierOptionTypeEnum.FLYING_BLUE.toString());
        } else if (StringUtils.isNotBlank(myA)) {
            // Get gin using myA
            ginRet = provideGin(myA, "MA");
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


}
