package com.airfrance.batch.invalidation;

import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.config.invalidation.BatchInvalidationConfig;
import com.airfrance.batch.invalidation.type.BatchInvalidationArgs;
import com.airfrance.batch.invalidation.type.CheckFileFormatInvEmail;
import com.airfrance.batch.invalidation.type.GenerateLogInvalidationEmail;
import com.airfrance.batch.invalidation.type.InvalFileFieldIndex;
import com.airfrance.ref.exception.compref.CommunicationPreferencesNotFoundException;
import com.airfrance.ref.exception.compref.MarketLanguageNotFoundException;
import com.airfrance.ref.exception.email.EmailNotFoundException;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dto.adresse.EmailDTO;
import com.airfrance.repind.dto.individu.IndividuDTO;
import com.airfrance.repind.service.adresse.internal.EmailDS;
import com.airfrance.repind.service.individu.internal.CommunicationPreferencesDS;
import com.airfrance.repind.service.individu.internal.IndividuDS;
import com.airfrance.repind.util.SicStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.airfrance.batch.invalidation.type.CheckFileFormat.*;
import static com.airfrance.batch.invalidation.type.CheckFileFormatInvEmail.*;
import static com.airfrance.batch.invalidation.type.ConstInvalidation.*;

/**
 * @author M406368
 * Batch in charge of email invalidation for CRMPush R3
 *
 * CONTEXT: A file is sent each day from FBSPUSH to RI application. Communication return codes
 * managed by eMessage/UNICA which could have an impact on Referential Individual data are:
 *     - 3  = SEND FAILURE
 *     - 6  = HARD BOUNCE
 *     - 8  = SPAM
 *     - 10 = UNSUBSCRIBE
 *
 * OBJECTIVES are:
 *  - Update individual/prospect communication preferences in RI
 *  - Update individual/prospect market language in RI
 *  - Invalidate email addresses in RI
 *
 *  STEPS:
 *  - File format checking (data validity, coherence)
 *  - Email invalidation for SEND FAILURE and HARD BOUNCE communication return codes
 *  - Global opt-out for Communication preferences if SPAM
 *  - Specific opt-out for Market Language if UNSUBSCRIBE
 *  - Synthesis + generation of log files:
 *
 * USAGE:
 * BatchInvalidationEmail.sh -option:
 *  -f file.csv    : [MANDATORY]: the file to be read
 *  -t trace level : trace mode on [OPTIONAL], define the level of information display
 *  -C             : commit mode on [OPTIONAL], only check validity of input file is done if commit mode = off
 *  -l path        : [OPTIONAL] to redirect generated log files in the specified path
 *  -force        : [OPTIONAL] to force commit mode even if the check validity is not OK
 */

@Service
public class BatchInvalidationEmail extends BatchInvalidationArgs {

    // LOGGER
    private static final Log log = LogFactory.getLog(BatchInvalidationEmail.class);
    protected GenerateLogInvalidationEmail generateLog = new GenerateLogInvalidationEmail();
    protected CheckFileFormatInvEmail checkFileFormat = new CheckFileFormatInvEmail();

    @Autowired
    private EmailDS emailDS;

    @Autowired
    private IndividuDS individuDS;

    @Autowired
    private CommunicationPreferencesDS communicationPreferencesDS;

    private static final String BATCH_NAME = "BatchInvalidationEmail.sh";

    protected int nbLinesTotal = 0;
    protected int currentLine = 1;
    protected int currentL = 0;

    private boolean isOk = true;

    // SEND FAILURE
    private long nbInvalidEmailsSendFailureForIndividuals = 0;
    private long totalInvalidEmailsSendFailureForIndividuals = 0;
    private long nbInvalidEmailsSendFailureForProspects = 0;
    private long totalInvalidEmailsSendFailureForProspects = 0;

    // HARD BOUNCE
    private long nbInvalidEmailsHardBounceForIndividuals = 0;
    private long totalInvalidEmailsHardBounceForIndividuals = 0;
    private long nbInvalidEmailsHardBounceForProspects = 0;
    private long totalInvalidEmailsHardBounceForProspects = 0;

    // SPAM
    private long nbUnsubscribeCommunicationPreferenceForIndividuals = 0;
    private long totalUnsubscribeCommunicationPreferenceForIndividuals = 0;
    private long totalUnsubscribeCommunicationPreferenceForProspects = 0;

    // UNSUBSCRIBE
    private long nbUnsubscribeMarketLanguageForIndividuals = 0;
    private long totalUnsubscribeMarketLanguageForIndividuals = 0;
    private long totalUnsubscribeMarketLanguageForProspects = 0;

    // STATS FOR INVALIDATION AND UNSUBSCRIPTION
    private int nbInvalidationNotTreated = 0;
    private int nbSpamNotTreated = 0;
    private int nbUnsubscribeNotTreated = 0;

    private int nbInvalidationAlreadyTreatedForIndivdiduals = 0;
    private int nbInvalidationAlreadyTreatedForProspects = 0;
    private int nbSpamAlreadyTreatedForIndividuals = 0;
    private int nbSpamAlreadyTreatedForProspects = 0;
    private int nbUnsubscribeAlreadyTreatedForIndividuals = 0;
    private int nbUnsubscribeAlreadyTreatedForProspects = 0;

    private boolean hasBeenTreatedCommunicationPreferencesForIndividuals = false;
    private boolean hasBeenTreatedMarketLanguageForIndividuals = false;

    public static void main(String[] args) {

        try {

            AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BatchInvalidationConfig.class);

            // GETTING CLASS
            IBatch batch = (IBatch) ctx.getBean("batchInvalidationEmail");
            ((BatchInvalidationEmail) batch).setBatchName(BATCH_NAME);

            // PARSING FILE
            ((BatchInvalidationEmail) batch).parseArgs(args);

            log.info(LOG_FILE_EXECUTION);

            if (getFileToProcess() != null) {
                currentFileTraited = getFileToProcess();
                batch.execute();
            }

            ctx.close();
            log.info(BATCH_EXECUTED_SUCCESSFULLY);

        } catch (JrafDomainException j) {
            log.fatal(j);
            System.exit(1);
        } catch (Exception e) {
            log.fatal(LOG_FILE_ERROR);
            log.fatal(e);
            System.exit(1);
        }
        System.exit(0);
    }

    @Override
    public void execute() throws JrafDomainException {

        log.info("Opening input file");

        if (this.getLocal()) {
            generateLog.setDataDir(this.localPath);
        }
        logBatchInvalidation(generateLog.getDataDir());
        generateLog.setFileSuffix(currentFileTraited);

        logBatchInvalidation("Execute methode\n");
        logBatchInvalidation("Opening input file: " + currentFileTraited + "\n");

        try {

            generateLog.openLogFiles();
            validateFile();

            if((this.isOk() || this.getForce()) && (this.getCommit() || this.getForce())){
                if(this.isOk()){
                    logBatchInvalidation(VALIDITY_OK);
                }
                if(!this.isOk() && this.getForce()){
                    logBatchInvalidation(FORCE_VALIDITY);
                }
                processFile();
            }
        } catch (Exception e) {
            log.error(e);
        }
    }
    private void validateFile() throws IOException {
        this.openBufferReader(currentFileTraited);

        int count = 0;
        String line;

        while ((line = this.getBfr().readLine()) != null) {
            count++;
            checkFile(count, line);
        }

        logBatchInvalidation("Total Lines: " + nbLinesTotal + "\n");
        this.getBfr().close();
        logBatchInvalidation(END_CHECK_VALIDITY);
    }

    private void checkFile(Integer count, String line) throws IOException{

        String[] oneData = line.split(SEPARATOR);
        // Header
        if (count == 1 && !checkFileFormat.isHeaderValid(oneData[0].trim())) {
            printValidity(FIELD_NOT_VALID + HEADER + "\n");
            this.setOk(false);
        }

        if (count > 1) {
            currentL = nbLinesTotal + 1;
            for (int i = 0; i < oneData.length; i++) {
                String currentData = "";
                if (oneData[i] != null){
                    currentData = oneData[i].trim();
                }

                if(!(checkAction(currentL, i, currentData) &&
                        checkCommunicationReturnCode(currentL, i, currentData, oneData[0]) &&
                        checkContactType(currentL, i, currentData) &&
                        checkContact(currentL, i, currentData, oneData[2]) &&
                        checkDomainOrCommunicationGroupTypeOrCommunicationType(currentL, i, currentData, oneData[1]) &&
                        checkMarket(currentL, i, currentData, oneData[1]) &&
                        checkLanguage(currentL, i, currentData, oneData[1]) &&
                        checkGin(currentL, i, currentData, oneData[1]) &&
                        checkFBIdentifier(currentL, i, currentData) &&
                        checkAccountIdentifier(currentL, i, currentData) &&
                        checkCause(currentL, i, currentData))){
                    this.setOk(false);
                    printRejectedLines(line);

                }
            }
        }
        nbLinesTotal++;
        generateLog.getBfwValidity().flush();
    }

    /**
     * Check Action.
     * @param currentLine : current line
     * @param index : index of current line
     * @param action : field value to be checked
     * @throws IOException : Exception for input validity file
     */
    private boolean checkAction(int currentLine, int index, String action) throws IOException {

        if (index == InvalFileFieldIndex.ACTION_INDEX && StringUtils.isEmpty(action)) {
            printValidity(ERROR_LABEL + currentLine + MANDATORY_FIELD + ACTION + "\n");
            return false;
        }
        if (index == InvalFileFieldIndex.ACTION_INDEX && action.length() >= 2) {
            printValidity(ERROR_LABEL + currentLine + FIELD_OVERFLOW + ACTION + "\n");
            return false;
        }
        if (index == InvalFileFieldIndex.ACTION_INDEX && !checkFileFormat.isActionValid(action)) {
            printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + ACTION + "\n");
            return false;
        }
        return true;
    }

    /**
     * Check Communication return code
     * @param currentLine : current line
     * @param index : index of current line
     * @param returnCode : field value to be checked
     * @param action : used to check coherence
     * @throws IOException : Exception for input validity file
     */
    private boolean checkCommunicationReturnCode(int currentLine, int index, String returnCode, String action) throws IOException {

        if (index == InvalFileFieldIndex.COMRETURNCODE_INDEX && StringUtils.isEmpty(returnCode)) {
            printValidity(ERROR_LABEL + currentLine + MANDATORY_FIELD + COMMUNICATION_RETURN_CODE + "\n");
            return false;
        }
        // SPECIFIC TO EMAIL INVALIDATION
        if (index == InvalFileFieldIndex.COMRETURNCODE_INDEX && returnCode.length() > 3) {
            printValidity(ERROR_LABEL + currentLine + FIELD_OVERFLOW + COMMUNICATION_RETURN_CODE + "\n");
            return false;
        }
        if (index == InvalFileFieldIndex.COMRETURNCODE_INDEX && !checkFileFormat.isCommunicationReturnCodeValid(returnCode)) {
            printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + COMMUNICATION_RETURN_CODE + "\n");
            return false;
        }
        if (index == InvalFileFieldIndex.COMRETURNCODE_INDEX && !checkFileFormat.isCommunicationReturnCodeCoherent(returnCode, action)) {
            printValidity(ERROR_LABEL + currentLine + FIELD_INCOHERENCE_ACTION + COMMUNICATION_RETURN_CODE + "\n");
            return false;
        }
        return true;
    }

    /**
     * Check Contact type
     * @param currentLine : current line
     * @param index : index of current line
     * @param contactType : field value to be checked
     * @throws IOException : Exception for input validity file
     */
    private boolean checkContactType(int currentLine, int index, String contactType) throws IOException {

        if (index == InvalFileFieldIndex.CONTACTTYPE_INDEX && StringUtils.isEmpty(contactType)) {
            printValidity(ERROR_LABEL + currentLine + MANDATORY_FIELD + CONTACT_TYPE + "\n");
            return false;
        }
        if (index == InvalFileFieldIndex.CONTACTTYPE_INDEX && contactType.length() > 1) {
            printValidity(ERROR_LABEL + currentLine + FIELD_OVERFLOW + CONTACT_TYPE + "\n");
            return false;
        }
        if (index == InvalFileFieldIndex.CONTACTTYPE_INDEX && !checkFileFormat.isContactValid(contactType)) {
            printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + CONTACT_TYPE + "\n");
            return false;
        }
        return true;
    }

    /**
     * Check Contact
     * @param currentLine : current line
     * @param index : index of current line
     * @param contact : field value to be checked
     * @param contactType : used to check coherence 'E' with email
     * @throws IOException : Exception for input validity file
     */
    private boolean checkContact(int currentLine, int index, String contact, String contactType) throws IOException {

        if (index == InvalFileFieldIndex.CONTACT_INDEX && StringUtils.isEmpty(contact)) {
            printValidity(ERROR_LABEL + currentLine + MANDATORY_FIELD + CONTACT + "\n");
            return false;
        }
        if (index == InvalFileFieldIndex.CONTACT_INDEX && contact.length() > 60) {
            printValidity(ERROR_LABEL + currentLine + FIELD_OVERFLOW + CONTACT + "\n");
            return false;
        }
        if (index == InvalFileFieldIndex.CONTACT_INDEX && !checkFileFormat.isEmailCoherentWithContact(contact, contactType)) {
            printValidity(ERROR_LABEL + currentLine + FIELD_INCOHERENCE_EMAIL + CONTACT + "\n");
            return false;
        }
        return true;
    }

    /**
     * Check Domain or CommunicationGroupType or CommunicationType
     * @param currentLine : current line
     * @param index : index of current line
     * @param data : domain/comGroupType or comType field value to be checked
     * @param returnCode : used to check coherence : must be equal to 8 or 10 (only coherent in that case - functional rule)
     * @throws IOException : Exception for input validity file
     */
    private boolean checkDomainOrCommunicationGroupTypeOrCommunicationType(int currentLine, int index, String data, String returnCode) throws IOException {

        if(((index == InvalFileFieldIndex.DOMAIN_COMPREF_INDEX) || (index == InvalFileFieldIndex.COMRETURNCODE_INDEX) || (index == InvalFileFieldIndex.COMTYPE_COMPREF_INDEX)) && checkFileFormat.isCommunication8or10(returnCode)){
            if(StringUtils.isEmpty(data)) {
                if (index == InvalFileFieldIndex.DOMAIN_COMPREF_INDEX) {
                    printValidity(ERROR_LABEL + currentLine + MANDATORY_FIELD + DOMAIN + "\n");
                }
                if (index == InvalFileFieldIndex.COMRETURNCODE_INDEX) {
                    printValidity(ERROR_LABEL + currentLine + MANDATORY_FIELD + COMM_GROUP_TYPE + "\n");
                }
                if (index == InvalFileFieldIndex.COMTYPE_COMPREF_INDEX) {
                    printValidity(ERROR_LABEL + currentLine + MANDATORY_FIELD + COM_TYPE + "\n");
                }
                return false;
            }
            if (data.length() > 7) {
                if (index == InvalFileFieldIndex.DOMAIN_COMPREF_INDEX) {
                    printValidity(ERROR_LABEL + currentLine + FIELD_OVERFLOW + DOMAIN + "\n");
                }
                if (index == InvalFileFieldIndex.COMRETURNCODE_INDEX) {
                    printValidity(ERROR_LABEL + currentLine + FIELD_OVERFLOW + COMM_GROUP_TYPE + "\n");
                }
                if (index == InvalFileFieldIndex.COMTYPE_COMPREF_INDEX) {
                    printValidity(ERROR_LABEL + currentLine + FIELD_OVERFLOW + COM_TYPE + "\n");
                }
                return false;
            }
        }
        return true;
    }


    /**
     * Check Market
     * @param currentLine : current line
     * @param index : index of current line
     * @param market : field value to be checked
     * @param returnCode : used to check coherence : must be equal to 10 (only coherent in that case - functional rule)
     * @throws IOException : Exception for input validity file
     */
    private boolean checkMarket(int currentLine, int index, String market, String returnCode) throws IOException {

        if ((index == InvalFileFieldIndex.MARKET_COMPREF_INDEX) && checkFileFormat.isCommunication10(returnCode)) {
            if (StringUtils.isEmpty(market)) {
                printValidity(ERROR_LABEL + currentLine + MANDATORY_FIELD + MARKET + "\n");
                return false;
            }
            if (market.length() > 3) {
                printValidity(ERROR_LABEL + currentLine + FIELD_OVERFLOW + MARKET + "\n");
                return false;
            }
            if (!StringUtils.isEmpty(market) && !checkFileFormat.isMarketValid(market)) {
                printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + MARKET + "\n");
                return false;
            }
        }
        return true;
    }

    /**
     * Check Language
     * @param currentLine : current line
     * @param index : index of current line
     * @param language : field value to be checked
     * @param returnCode : used to check coherence : must be equal to 10 (only coherent in that case - functional rule)
     * @throws IOException : Exception for input validity file
     */
    private boolean checkLanguage(int currentLine, int index, String language, String returnCode) throws IOException {

        if ((index == InvalFileFieldIndex.LANGUAGE_COMPREF_INDEX) && checkFileFormat.isCommunication10(returnCode)) {
            if (StringUtils.isEmpty(language)) {
                printValidity(ERROR_LABEL + currentLine + MANDATORY_FIELD + LANGUAGE + "\n");
                return false;
            }
            if (language.length() > 2) {
                printValidity(ERROR_LABEL + currentLine + FIELD_OVERFLOW + LANGUAGE + "\n");
                return false;
            }
            if (!StringUtils.isEmpty(language) && !checkFileFormat.isCodeLangueValid(language)) {
                printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + LANGUAGE + "\n");
                return false;
            }
        }
        return true;
    }

    /**
     * Check GIN
     * @param currentLine : current line
     * @param index : index of current line
     * @param gin : field value to be checked
     * @param returnCode : used to check coherence : must be equal to 10 (only coherent in that case - functional rule)
     * @throws IOException : Exception for input validity file
     */
    private boolean checkGin(int currentLine, int index, String gin, String returnCode) throws IOException {

        if (index == InvalFileFieldIndex.GIN_INDEX && checkFileFormat.isCommunication10(returnCode)) {
            if (StringUtils.isEmpty(gin)) {
                printValidity(ERROR_LABEL + currentLine + MANDATORY_FIELD + GIN + "\n");
                return false;
            }
            if (!checkFileFormat.isClientNumberValid(gin)) {
                printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + GIN + "\n");
                return false;
            }
        }
        return true;
    }

    /**
     * Check FB Identifier.
     * @param currentLine : current line
     * @param index : index of current line
     * @param currentData : field value to be checked
     * @throws IOException : Exception for input validity file
     */
    private boolean checkFBIdentifier(int currentLine, int index, String currentData) throws IOException {

        if (index == InvalFileFieldIndex.FBIDENTIFIER_INDEX && !StringUtils.isEmpty(currentData) && !checkFileFormat.isClientNumberValid(currentData)) {
            printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + FB + "\n");
            return false;
        }
        return true;
    }

    /**
     * Check Account Identifier.
     * @param currentLine : current line
     * @param index : index of current line
     * @param currentData : field value to be checked
     * @throws IOException : Exception for input validity file
     */
    private boolean checkAccountIdentifier(int currentLine, int index, String currentData) throws IOException {

        if (index == InvalFileFieldIndex.ACCOUNTIDENTIFIER_INDEX && !StringUtils.isEmpty(currentData) && !checkFileFormat.isAccountNumberValid(currentData)) {
            printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + ACCOUNT + "\n");
            return false;
        }
        return true;
    }

    /**
     * Check Cause.
     * @param currentLine : current line
     * @param index : index of current line
     * @param cause : field value to be checked
     * @throws IOException : Exception for input validity file
     */
    private boolean checkCause(int currentLine, int index, String cause) throws IOException {

        if (index == InvalFileFieldIndex.CAUSE_INDEX && !StringUtils.isEmpty(cause) && cause.length() > MAX_EMAIL) {
            printValidity(ERROR_LABEL + currentLine + FIELD_OVERFLOW +CAUSE + "\n");
            return false;
        }
        return true;
    }

    /**
     * Display output : screen and check validity logs
     * @param str : string to display
     * @throws IOException : Exception for input validity file
     */
    private void printValidity(String str) throws IOException
    {
        logBatchInvalidation(str);
        if(generateLog.getBfwValidity() != null){
            generateLog.getBfwValidity().write(str);
        }
    }

    private void printRejectedLines(String line) throws IOException
    {
        if(generateLog.getBfwRejectLine() != null){
            generateLog.getBfwRejectLine().write(line+"\n");
            generateLog.getBfwRejectLine().flush();
        }
    }

    private void processFile() throws IOException{

        String line;
        this.openBufferReader(currentFileTraited);
        int currentLine = -1;
        while ((line = this.getBfr().readLine()) != null) {
            currentLine++;
            // Skip header
            if(currentLine == 0){
                continue;
            }
            invalidAndUnsubscribe(line, currentLine);
        }
        displayStats();
        generateLog.closeLogFiles();
    }



    /**
     * invalidAndUnsubscribe: do the logic stuff to invalidate mails and to unsubscribe to communication preferences/market language
     * @throws IOException for openBufferReader method
     */
    public void invalidAndUnsubscribe(String line, int currentLine) throws IOException{

        hasBeenTreatedCommunicationPreferencesForIndividuals = false;
        hasBeenTreatedMarketLanguageForIndividuals = false;

        String[] oneData = line.split(SEPARATOR);

        if(oneData.length != 0) {

            String email = oneData[InvalFileFieldIndex.CONTACT_INDEX].trim();
            String gin = oneData[InvalFileFieldIndex.GIN_INDEX].trim();
            String returnCode = oneData[InvalFileFieldIndex.COMRETURNCODE_INDEX].trim();

            reportLog(email, gin, returnCode, currentLine);


            boolean isIndividual = true;

            /// SEND FAILURE or HARD BOUNCE////////
            if (SEND_FAILURE.equals(returnCode) || HARD_BOUNCE.equals(returnCode)) {

                // SIC part - individuals
                try{
                    invalidOnEmail(oneData, returnCode, currentLine);
                }
                // EMAIL NOT FOUND IN SIC
                catch(EmailNotFoundException e) {
                    logBatch("line "+currentLine +";"+e.getMessage()+";"+e.getEmail()+"\n", WARN);
                    nbInvalidationNotTreated++;
                    logBatch("line " + currentLine+";"+line+"\n", ERROR);
                    log.error(e);
                }
                // TECHNICAL ERROR
                catch(JrafDomainException e){
                    logBatch("line "+currentLine +";"+TECHNICAL_INVALIDATION_INDIVIDUAL+";"+email+";"+e.getMessage()+"\n", WARN);
                    nbInvalidationNotTreated++;
                    logBatch("line " + currentLine+";"+line+"\n", ERROR);
                    log.error(e);
                }
            }
            /// SPAM ////////
            else if (SPAM.equals(returnCode)) {
                // SIC part - individuals
                try{

                    // Get individual for check if it's an individual or a prospect
                    IndividuDTO indDTO = individuDS.getByGin(gin);

                    if(indDTO != null && indDTO.getType().equals("W")) {
                        isIndividual = false;
                    }

                    unsubscribeCommPref(oneData, isIndividual, currentLine);
                }
                // COMMUNICATION PREFERENCES NOT FOUND
                catch(CommunicationPreferencesNotFoundException e){
                    log.error(e);
                    logBatch("line "+currentLine +";"+e.getMessage()+";"+e.getGin()+";"+e.getDomain()+";"+e.getComGroupType()+";"+e.getComType()+"\n", WARN);
                    nbSpamNotTreated++;
                    logBatch("line " + currentLine+";"+line+"\n", ERROR);
                }
                // TECHNICAL WARN
                catch(JrafDomainException e){
                    log.error(e);
                    if(isIndividual) {
                        logBatch("line "+currentLine +";"+TECHNICAL_CP_OPTOUT_INDIVIDUAL+";"+SPAM+";"+email+";"+oneData[4].trim()+";"+oneData[5].trim()+";"+oneData[6].trim()+";"+e.getMessage()+"\n", WARN);
                    } else {
                        logBatch("line "+currentLine +";"+TECHNICAL_CP_OPTOUT_PROSPECT+";"+SPAM+";"+email+";"+oneData[4].trim()+";"+oneData[5].trim()+";"+oneData[6].trim()+";"+e.getMessage()+"\n",WARN);
                    }
                    nbSpamNotTreated++;
                    logBatch("line " + currentLine+";"+line+"\n", ERROR);
                }
            }

            /// UNSUBSCRIBE ////////
            else {
                // SIC part - individuals
                try{

                    // Get individual for check if it's an individual or a prospect
                    IndividuDTO indDTO = individuDS.getByGin(gin);

                    if(indDTO != null && indDTO.getType().equals("W")) {
                        isIndividual = false;
                    }

                    unsubscribeMarketLanguage(oneData, isIndividual, currentLine);
                }
                // COMMUNICATION PREFERENCES NOT FOUND
                catch(CommunicationPreferencesNotFoundException e){
                    log.error(e);
                    logBatch("line "+currentLine +";"+e.getMessage()+";"+e.getGin()+";"+e.getDomain()+";"+e.getComGroupType()+";"+e.getComType()+"\n",WARN);
                    nbUnsubscribeNotTreated++;
                    logBatch("line " + currentLine+";"+line+"\n", ERROR);
                }
                // MARKET LANGUAGE NOT FOUND
                catch(MarketLanguageNotFoundException e){
                    log.error(e);
                    logBatch("line "+currentLine +";"+e.getMessage()+";"+e.getComPrefId()+";"+e.getMarket()+";"+e.getLanguage()+"\n",WARN);
                    nbUnsubscribeNotTreated++;
                    logBatch("line " + currentLine+";"+line+";\n", ERROR);
                }
                // TECHNICAL WARN
                catch(JrafDomainException e){
                    log.error(e);
                    if(isIndividual) {
                        logBatch("line "+currentLine +";"+TECHNICAL_ML_OPTOUT_INDIVIDUAL+";"+UNSUBSCRIBE+";"+oneData[7].trim()+";"+oneData[4].trim()+";"+oneData[5].trim()+";"+oneData[6].trim()+";"+e.getMessage()+"\n",WARN);
                    }
                    else {
                        logBatch("line "+currentLine +";"+TECHNICAL_ML_OPTOUT_PROSPECT+";"+UNSUBSCRIBE+";"+oneData[7].trim()+";"+oneData[4].trim()+";"+oneData[5].trim()+";"+oneData[6].trim()+";"+e.getMessage()+"\n",WARN);
                    }
                    nbUnsubscribeNotTreated++;
                    logBatch("line " + currentLine+";"+line+"\n", ERROR);
                }

            }
        }
    }

    /**
     * logBatchInvalidation: write info on screen and logger
     * @param message
     * @param type (as Array) according to trace level messages
     */
    private void logBatchInvalidation(String message, String ... type){
        if (this.trace) {
            System.out.print(message);
            if(type.length == 0)
                log.info(message);
        }
    }

    /**
     * logBatch: log each treatment done in report file + reject if error and some extra logging
     * @param message
     * @param type (as Array)
     * @throws IOException
     */
    private void logBatch(String message, String ... type) throws IOException{
        logBatchInvalidation(message, type);
        // IF !ERROR
        if((type.length > 0 && WARN.equals(type[0])) || type.length == 0){
            generateLog.getBfwReport().write(message);
        }
        if(type.length > 0){
            if(WARN.equals(type[0])){
                log.warn(message);
            }
            else if(ERROR.equals(type[0])){
                generateLog.getBfwRejectLine().write(message);
                log.error(message);
            }
        }
    }

    /**
     * Display synthesis when input file treatment is done
     * @throws IOException
     * for caught exception
     */
    private void displayStats() throws IOException {

        StringBuilder synthesis = new StringBuilder();
        long nbInvalidationIndividual = totalInvalidEmailsSendFailureForIndividuals + totalInvalidEmailsHardBounceForIndividuals;
        long nbInvalidationProspect = totalInvalidEmailsSendFailureForProspects + totalInvalidEmailsHardBounceForProspects;

        synthesis.append("\n");
        synthesis.append("Emails INVALIDATION:\n");
        synthesis.append("Nb of invalidation done in SIC EMAILS: ").append(nbInvalidationIndividual).append("\n");
        synthesis.append("Nb of invalidation in SIC_UTF8 PROSPECT: ").append(nbInvalidationProspect).append("\n");
        synthesis.append("Nb of untreated invalidation in SIC/SIC_UTF8: ").append(this.nbInvalidationNotTreated).append("\n");
        synthesis.append("Invalidation already done in SIC: ").append(this.nbInvalidationAlreadyTreatedForIndivdiduals).append("\n");
        synthesis.append("Invalidation already done in SIC_UTF8: ").append(this.nbInvalidationAlreadyTreatedForProspects).append("\n");

        synthesis.append("----\n");

        synthesis.append("Global opt-out in COMMUNICATION PREFERENCES:\n");
        synthesis.append("Nb of global opt-outs set in SIC COMMUNICATION PREFERENCES: ").append(this.totalUnsubscribeCommunicationPreferenceForIndividuals).append("\n");
        synthesis.append("Nb of global opt-outs set in SIC_UTF8 COMMUNICATION PREFERENCES: ").append(this.totalUnsubscribeCommunicationPreferenceForProspects).append("\n");
        synthesis.append("Nb of untreated global opt-outs in SIC/SIC_UTF8: ").append(this.nbSpamNotTreated).append("\n");
        synthesis.append("Global optout already set in SIC: ").append(this.nbSpamAlreadyTreatedForIndividuals).append("\n");
        synthesis.append("Global optout already set in SIC_UTF8: " + this.nbSpamAlreadyTreatedForProspects+"\n");

        synthesis.append("----\n");

        synthesis.append("Specific opt-out in MARKET LANGUAGE:\n");
        synthesis.append("Nb of specific opt-outs in SIC MARKET LANGUAGE: ").append(this.totalUnsubscribeMarketLanguageForIndividuals).append("\n");
        synthesis.append("Nb of specific opt-outs in SIC_UTF8 MARKET LANGUAGE: ").append(this.totalUnsubscribeMarketLanguageForProspects).append("\n");
        synthesis.append("Nb of untreated specific opt-outs in SIC/SIC_UTF8: ").append(this.nbUnsubscribeNotTreated).append("\n");
        synthesis.append("Specific optout already set in SIC: ").append(this.nbUnsubscribeAlreadyTreatedForIndividuals).append("\n");
        synthesis.append("Specific optout already set in SIC_UTF8: ").append(this.nbUnsubscribeAlreadyTreatedForProspects).append("\n");

        synthesis.append("------------------------------------------------\n");

        generateLog.getBfwSynthesis().write(synthesis.toString());
        log.info(synthesis.toString());
    }

    /**
     * Display current line treated with current statistics
     * @param email : email from input file
     * @param gin : gin from input file
     * @param returnCode : return code from input file
     * @param currentLine
     */
    private void reportLog(String email, String gin, String returnCode, int currentLine) {


        String reportLine = "Treatment of line  " + currentLine + " at time : " + new Date() + "\n" +
                "Email:  " + email + "\n" +
                "Gin: " + gin + "\n" +
                "Return code:  " + returnCode + "\n" +
                "INVALIDATION :\n" +
                (this.totalInvalidEmailsSendFailureForIndividuals + this.totalInvalidEmailsHardBounceForIndividuals) + " invalidation done for Individuals / " +
                (this.totalInvalidEmailsSendFailureForProspects + this.totalInvalidEmailsHardBounceForProspects) + " invalidation done for Prospects\n" +
                this.nbInvalidationNotTreated + " Not treated\n" +
                this.nbInvalidationAlreadyTreatedForIndivdiduals + " invalidation already done for Individuals / " +
                this.nbInvalidationAlreadyTreatedForProspects + " invalidation already done for Prospects\n" +
                "COMMUNICATION PREFERENCES :\n" +
                (this.totalUnsubscribeCommunicationPreferenceForIndividuals + this.totalUnsubscribeCommunicationPreferenceForProspects) + " global optout set / " +
                this.nbSpamNotTreated + " Not treated\n" +
                this.nbSpamAlreadyTreatedForIndividuals + " global optout already set for Individuals / " +
                this.nbSpamAlreadyTreatedForProspects + " global optout already set for Prospects\n" +
                "MARKET LANGUAGE :\n" +
                (this.totalUnsubscribeMarketLanguageForIndividuals + this.totalUnsubscribeMarketLanguageForProspects) + " specific optout set / " +
                this.nbUnsubscribeNotTreated + " Not treated\n" +
                this.nbUnsubscribeAlreadyTreatedForIndividuals + " specific optout already set for Individuals / " +
                this.nbUnsubscribeAlreadyTreatedForProspects + " specific optout already set for Prospects\n" +
                "##################\n";
        System.out.print(reportLine);
    }

    /**
     * invalidOnEmail: according to communication return code found in input file, invalid an email with different signature.
     * Can apply in SIC/SIC_UTF8 depending on 'isIndividual' flag. Log the results
     * @param line: current line from input file
     * @param returnCode
     * @param currentLine
     * @throws EmailNotFoundException
     * @throws IOException
     * @throws JrafDomainException
     */
    private void invalidOnEmail(String[] line, String returnCode, int currentLine) throws EmailNotFoundException, IOException, JrafDomainException{

        String email = line[InvalFileFieldIndex.CONTACT_INDEX].trim();
        email = SicStringUtils.normalizeEmail(email);

        // SIC Individual
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmail(email);

        // REPIND-555: Change method to return all email for know if email belongs to an individual or a prospect
        List<EmailDTO> emailsDTO = emailDS.search(emailDTO.getEmail());

        if(emailsDTO == null){
            throw new EmailNotFoundException("SIC: Email not found in EMAILS",emailDTO.getEmail());
        }

        this.nbInvalidEmailsSendFailureForIndividuals = 0;
        this.nbInvalidEmailsSendFailureForProspects = 0;
        this.nbInvalidEmailsHardBounceForIndividuals = 0;
        this.nbInvalidEmailsHardBounceForProspects = 0;

        // REPIND-555: Prospect migration
        for (EmailDTO eDTO : emailsDTO) {

            boolean isIndividual = true;

            if (eDTO.getSgin()!=null) {

                // Get individual for check if it's an individual or a prospect
                // Need this for log
                IndividuDTO indDTO = individuDS.getByGin(eDTO.getSgin());

                if(indDTO != null && indDTO.getType().equals("W")) {
                    isIndividual = false;
                }

                if(MediumStatusEnum.VALID.toString().equals(eDTO.getStatutMedium()) || MediumStatusEnum.TEMPORARY.toString().equals(eDTO.getStatutMedium()) || MediumStatusEnum.SUSPENDED.toString().equals(eDTO.getStatutMedium())) {

                    // SEND FAILURE
                    if(SEND_FAILURE.equals(returnCode)){

                        emailDS.invalidOnEmail(eDTO, "InvEmail " + this.signatureType.toString() + " 3");
                        if(isIndividual) {
                            this.nbInvalidEmailsSendFailureForIndividuals++;
                        } else {
                            this.nbInvalidEmailsSendFailureForProspects++;
                        }

                    }
                    // HARD BOUNCE
                    else {
                        emailDS.invalidOnEmail(eDTO, "InvEmail " + this.signatureType.toString() + " 6");
                        if(isIndividual) {
                            this.nbInvalidEmailsHardBounceForIndividuals++;
                        } else {
                            this.nbInvalidEmailsHardBounceForProspects++;
                        }
                    }

                }
            }
        }

        if(SEND_FAILURE.equals(returnCode)){

            // IF NO INVALIDATION DONE BECAUSE IT HAS ALREADY BEEN DONE OR BECAUSE EMAIL ARE OUT OF FUNCTIONAL SCOPE
            if(this.nbInvalidEmailsSendFailureForIndividuals == 0){
                logBatch("line "+currentLine +";"+INVALIDATION_INDIVIDUAL_ALREADY_SET+";"+SEND_FAILURE+";"+email+"\n");
                this.nbInvalidationAlreadyTreatedForIndivdiduals++;
            }
            else{
                this.totalInvalidEmailsSendFailureForIndividuals += this.nbInvalidEmailsSendFailureForIndividuals;
                logBatch("line "+currentLine +";"+INVALIDATION_INDIVIDUAL+";"+SEND_FAILURE+";"+email+";"+this.nbInvalidEmailsSendFailureForIndividuals+"\n");
            }

            if(this.nbInvalidEmailsSendFailureForProspects == 0){
                logBatch("line "+currentLine +";"+INVALIDATION_PROSPECT_ALREADY_SET+";"+SEND_FAILURE+";"+email+"\n");
                this.nbInvalidationAlreadyTreatedForProspects++;
            }
            else{
                this.totalInvalidEmailsSendFailureForProspects += this.nbInvalidEmailsSendFailureForProspects;
                logBatch("line "+currentLine +";"+INVALIDATION_PROSPECT+";"+SEND_FAILURE+";"+email+";"+nbInvalidEmailsSendFailureForProspects+"\n");
            }

        }
        // HARD BOUNCE
        else {

            if(this.nbInvalidEmailsHardBounceForIndividuals == 0){
                logBatch("line "+currentLine +";"+INVALIDATION_INDIVIDUAL_ALREADY_SET+";"+HARD_BOUNCE+";"+email+"\n");
                this.nbInvalidationAlreadyTreatedForIndivdiduals++;
            }
            else{
                this.totalInvalidEmailsHardBounceForIndividuals += this.nbInvalidEmailsHardBounceForIndividuals;
                logBatch("line "+currentLine +";"+INVALIDATION_INDIVIDUAL+";"+HARD_BOUNCE+";"+email+";"+this.nbInvalidEmailsHardBounceForIndividuals+"\n");
            }

            if(this.nbInvalidEmailsHardBounceForProspects == 0){
                logBatch("line "+currentLine +";"+INVALIDATION_PROSPECT_ALREADY_SET+";"+HARD_BOUNCE+";"+email+"\n");
                this.nbInvalidationAlreadyTreatedForProspects++;
            }
            else{
                this.totalInvalidEmailsHardBounceForProspects += this.nbInvalidEmailsHardBounceForProspects;
                logBatch("line "+currentLine +";"+INVALIDATION_PROSPECT+";"+HARD_BOUNCE+";"+email+";"+nbInvalidEmailsHardBounceForProspects+"\n");
            }

        }

//     // SIC Individual
//        if(isIndividual){
//            EmailDTO emailDTO = new EmailDTO();
//            emailDTO.setEmail(email);
//
//            // SEND FAILURE
//            if(SEND_FAILURE.equals(returnCode)){
//
//                this.nbInvalidEmailsSendFailureForIndividuals = emailDS.invalidOnEmail(emailDTO, "InvEmail " + this.signatureType.toString() + " 3");
//
//                // IF NO INVALIDATION DONE BECAUSE IT HAS ALREADY BEEN DONE OR BECAUSE EMAIL ARE OUT OF FUNCTIONAL SCOPE
//                if(this.nbInvalidEmailsSendFailureForIndividuals == 0){
//                    logBatch("line "+currentLine +";"+INVALIDATION_INDIVIDUAL_ALREADY_SET+";"+SEND_FAILURE+";"+email+"\n");
//                    this.nbInvalidationAlreadyTreatedForIndivdiduals++;
//                }
//                else{
//                    this.totalInvalidEmailsSendFailureForIndividuals += this.nbInvalidEmailsSendFailureForIndividuals;
//                    logBatch("line "+currentLine +";"+INVALIDATION_INDIVIDUAL+";"+SEND_FAILURE+";"+email+";"+this.nbInvalidEmailsSendFailureForIndividuals+"\n");
//                }
//
//            }
//            // HARD BOUNCE
//            else {
//                this.nbInvalidEmailsHardBounceForIndividuals = emailDS.invalidOnEmail(emailDTO, "InvEmail " + this.signatureType.toString() + " 6");
//
//                if(this.nbInvalidEmailsHardBounceForIndividuals == 0){
//                    logBatch("line "+currentLine +";"+INVALIDATION_INDIVIDUAL_ALREADY_SET+";"+HARD_BOUNCE+";"+email+"\n");
//                    this.nbInvalidationAlreadyTreatedForIndivdiduals++;
//                }
//                else{
//                    this.totalInvalidEmailsHardBounceForIndividuals += this.nbInvalidEmailsHardBounceForIndividuals;
//                    logBatch("line "+currentLine +";"+INVALIDATION_INDIVIDUAL+";"+HARD_BOUNCE+";"+email+";"+this.nbInvalidEmailsHardBounceForIndividuals+"\n");
//                }
//            }
//        }
//        // SIC UTF8
//        else{
//            ProspectDTO prospectDTO = new ProspectDTO();
//            prospectDTO.setEmail(email);
//
//            // SEND FAILURE
//            if(SEND_FAILURE.equals(returnCode)){
//
//                this.nbInvalidEmailsSendFailureForProspects = prospectDS.invalidOnEmail(prospectDTO,  "InvEmail " + this.signatureType.toString() + " 3");
//
//                if(this.nbInvalidEmailsSendFailureForProspects == 0){
//                    logBatch("line "+currentLine +";"+INVALIDATION_PROSPECT_ALREADY_SET+";"+SEND_FAILURE+";"+email+"\n");
//                    this.nbInvalidationAlreadyTreatedForProspects++;
//                }
//                else{
//                    this.totalInvalidEmailsSendFailureForIndividuals += this.nbInvalidEmailsSendFailureForIndividuals;
//                    logBatch("line "+currentLine +";"+INVALIDATION_PROSPECT+";"+SEND_FAILURE+";"+email+";"+nbInvalidEmailsSendFailureForProspects+"\n");
//                }
//            }
//            // HARD BOUNCE
//            else {
//                this.nbInvalidEmailsHardBounceForProspects = prospectDS.invalidOnEmail(prospectDTO, "InvEmail " + this.signatureType.toString() + " 6");
//
//                if(this.nbInvalidEmailsHardBounceForProspects == 0){
//                    logBatch("line "+currentLine +";"+INVALIDATION_PROSPECT_ALREADY_SET+";"+HARD_BOUNCE+";"+email+"\n");
//                    this.nbInvalidationAlreadyTreatedForProspects++;
//                }
//                else{
//                    this.totalInvalidEmailsHardBounceForProspects += this.nbInvalidEmailsHardBounceForProspects;
//                    logBatch("line "+currentLine +";"+INVALIDATION_PROSPECT+";"+HARD_BOUNCE+";"+email+";"+nbInvalidEmailsHardBounceForProspects+"\n");
//                }
//            }
//        }

    }

    /**
     * unsubscribeCommPref: according to 'isIndividual' flag, set a global opt-out for a communication preferences in SIC or SIC_UTF8.
     * Log the results
     * @param line: current line from input file
     * @param isIndividual
     * @param currentLine
     * @throws EmailNotFoundException
     * @throws CommunicationPreferencesNotFoundException
     * @throws IOException
     * @throws JrafDomainException
     */
    private void unsubscribeCommPref(String[] line, boolean isIndividual, int currentLine) throws EmailNotFoundException, CommunicationPreferencesNotFoundException, IOException, JrafDomainException{

        String gin = line[InvalFileFieldIndex.GIN_INDEX].trim();
        String domain = line[InvalFileFieldIndex.DOMAIN_COMPREF_INDEX].trim();
        String comGroupType = line[InvalFileFieldIndex.COMGROUPTYPE_COMPREF_INDEX].trim();
        String comType = line[InvalFileFieldIndex.COMTYPE_COMPREF_INDEX].trim();

        // SIC Individual
        // REPIND-555 : Migration prospect
        if(!hasBeenTreatedCommunicationPreferencesForIndividuals){
            this.nbUnsubscribeCommunicationPreferenceForIndividuals = communicationPreferencesDS.unsubscribeCommPref(gin, domain, comGroupType, comType);
            hasBeenTreatedCommunicationPreferencesForIndividuals = true;

            if(this.nbUnsubscribeCommunicationPreferenceForIndividuals == 0){
                if(isIndividual) {
                    logBatch("line "+currentLine +";"+CP_OPTOUT_INDIVIDUAL_ALREADY_SET+";"+SPAM+";"+gin+";"+domain+";"+comGroupType+";"+comType+"\n");
                    this.nbSpamAlreadyTreatedForIndividuals++;
                } else {
                    logBatch("line "+currentLine +";"+CP_OPTOUT_PROSPECT_ALREADY_SET+";"+SPAM+";"+gin+";"+domain+";"+comGroupType+";"+comType+"\n");
                    this.nbSpamAlreadyTreatedForProspects++;
                }
            }
            else{
                if(isIndividual) {
                    this.totalUnsubscribeCommunicationPreferenceForIndividuals += this.nbUnsubscribeCommunicationPreferenceForIndividuals;
                    logBatch("line "+currentLine +";"+CP_OPTOUT_INDIVIDUAL+";"+SPAM+";"+gin+";"+domain+";"+comGroupType+";"+comType+";"+nbUnsubscribeCommunicationPreferenceForIndividuals+"\n");
                } else {
                    this.totalUnsubscribeCommunicationPreferenceForProspects += this.nbUnsubscribeCommunicationPreferenceForIndividuals;
                    logBatch("line "+currentLine +";"+CP_OPTOUT_PROSPECT+";"+SPAM+";"+gin+";"+domain+";"+comGroupType+";"+comType+";"+nbUnsubscribeCommunicationPreferenceForIndividuals+"\n");
                }

            }
        }

//
//       // SIC Individual
//       if(isIndividual){
//           EmailDTO emailDTO = new EmailDTO();
//           emailDTO.setEmail(email);
//
//           // SEND FAILURE
//           if(SEND_FAILURE.equals(returnCode)){
//
//               this.nbInvalidEmailsSendFailureForIndividuals = emailDS.invalidOnEmail(emailDTO, "InvEmail " + this.signatureType.toString() + " 3");
//
//               // IF NO INVALIDATION DONE BECAUSE IT HAS ALREADY BEEN DONE OR BECAUSE EMAIL ARE OUT OF FUNCTIONAL SCOPE
//               if(this.nbInvalidEmailsSendFailureForIndividuals == 0){
//                   logBatch("line "+currentLine +";"+INVALIDATION_INDIVIDUAL_ALREADY_SET+";"+SEND_FAILURE+";"+email+"\n");
//                   this.nbInvalidationAlreadyTreatedForIndivdiduals++;
//               }
//               else{
//                   this.totalInvalidEmailsSendFailureForIndividuals += this.nbInvalidEmailsSendFailureForIndividuals;
//                   logBatch("line "+currentLine +";"+INVALIDATION_INDIVIDUAL+";"+SEND_FAILURE+";"+email+";"+this.nbInvalidEmailsSendFailureForIndividuals+"\n");
//               }
//
//           }
//           // HARD BOUNCE
//           else {
//               this.nbInvalidEmailsHardBounceForIndividuals = emailDS.invalidOnEmail(emailDTO, "InvEmail " + this.signatureType.toString() + " 6");
//
//               if(this.nbInvalidEmailsHardBounceForIndividuals == 0){
//                   logBatch("line "+currentLine +";"+INVALIDATION_INDIVIDUAL_ALREADY_SET+";"+HARD_BOUNCE+";"+email+"\n");
//                   this.nbInvalidationAlreadyTreatedForIndivdiduals++;
//               }
//               else{
//                   this.totalInvalidEmailsHardBounceForIndividuals += this.nbInvalidEmailsHardBounceForIndividuals;
//                   logBatch("line "+currentLine +";"+INVALIDATION_INDIVIDUAL+";"+HARD_BOUNCE+";"+email+";"+this.nbInvalidEmailsHardBounceForIndividuals+"\n");
//               }
//           }
//       }
//       // SIC UTF8
//       else{
//           ProspectDTO prospectDTO = new ProspectDTO();
//           prospectDTO.setEmail(email);
//
//           // SEND FAILURE
//           if(SEND_FAILURE.equals(returnCode)){
//
//               this.nbInvalidEmailsSendFailureForProspects = prospectDS.invalidOnEmail(prospectDTO,  "InvEmail " + this.signatureType.toString() + " 3");
//
//               if(this.nbInvalidEmailsSendFailureForProspects == 0){
//                   logBatch("line "+currentLine +";"+INVALIDATION_PROSPECT_ALREADY_SET+";"+SEND_FAILURE+";"+email+"\n");
//                   this.nbInvalidationAlreadyTreatedForProspects++;
//               }
//               else{
//                   this.totalInvalidEmailsSendFailureForIndividuals += this.nbInvalidEmailsSendFailureForIndividuals;
//                   logBatch("line "+currentLine +";"+INVALIDATION_PROSPECT+";"+SEND_FAILURE+";"+email+";"+nbInvalidEmailsSendFailureForProspects+"\n");
//               }
//           }
//           // HARD BOUNCE
//           else {
//               this.nbInvalidEmailsHardBounceForProspects = prospectDS.invalidOnEmail(prospectDTO, "InvEmail " + this.signatureType.toString() + " 6");
//
//               if(this.nbInvalidEmailsHardBounceForProspects == 0){
//                   logBatch("line "+currentLine +";"+INVALIDATION_PROSPECT_ALREADY_SET+";"+HARD_BOUNCE+";"+email+"\n");
//                   this.nbInvalidationAlreadyTreatedForProspects++;
//               }
//               else{
//                   this.totalInvalidEmailsHardBounceForProspects += this.nbInvalidEmailsHardBounceForProspects;
//                   logBatch("line "+currentLine +";"+INVALIDATION_PROSPECT+";"+HARD_BOUNCE+";"+email+";"+nbInvalidEmailsHardBounceForProspects+"\n");
//               }
//           }
//       }

    }

    /**
     * unsubscribeMarketLanguage: according to 'isIndividual' flag, set a global opt-out for a communication preferences in SIC or SIC_UTF8.
     * @param line
     * @param isIndividual
     * @param currentLine
     * @throws EmailNotFoundException
     * @throws CommunicationPreferencesNotFoundException
     * @throws MarketLanguageNotFoundException
     * @throws IOException
     * @throws JrafDomainException
     */
    private void unsubscribeMarketLanguage(String[] line, boolean isIndividual, int currentLine) throws EmailNotFoundException, CommunicationPreferencesNotFoundException, MarketLanguageNotFoundException, IOException, JrafDomainException{

        String gin = line[InvalFileFieldIndex.GIN_INDEX].trim();
        String domain = line[InvalFileFieldIndex.DOMAIN_COMPREF_INDEX].trim();
        String comGroupType = line[InvalFileFieldIndex.COMGROUPTYPE_COMPREF_INDEX].trim();
        String comType = line[InvalFileFieldIndex.COMTYPE_COMPREF_INDEX].trim();
        String market = line[InvalFileFieldIndex.MARKET_COMPREF_INDEX].trim();
        String language = line[InvalFileFieldIndex.LANGUAGE_COMPREF_INDEX].trim();

        // SIC Individual
        // REPIND-555 : Migration prospect
        if(!hasBeenTreatedMarketLanguageForIndividuals){
            this.nbUnsubscribeMarketLanguageForIndividuals = communicationPreferencesDS.unsubscribeMarketLanguage(gin, domain, comGroupType, comType, market, language);
            hasBeenTreatedMarketLanguageForIndividuals = true;

            if(this.nbUnsubscribeMarketLanguageForIndividuals == 0){
                if(isIndividual) {
                    logBatch("line "+currentLine +";"+ML_OPTOUT_INDIVIDUAL_ALREADY_SET+";"+UNSUBSCRIBE+";"+gin+";"+domain+";"+comGroupType+";"+comType+";"+market+";"+language+"\n");
                    this.nbUnsubscribeAlreadyTreatedForIndividuals++;
                } else {
                    logBatch("line "+currentLine +";"+ML_OPTOUT_PROSPECT_ALREADY_SET+";"+UNSUBSCRIBE+";"+gin+";"+domain+";"+comGroupType+";"+comType+";"+market+";"+language+"\n");
                    this.nbUnsubscribeAlreadyTreatedForProspects++;
                }
            }
            else{
                if(isIndividual) {
                    this.totalUnsubscribeMarketLanguageForIndividuals += this.nbUnsubscribeMarketLanguageForIndividuals;
                    logBatch("line "+currentLine +";"+ML_OPTOUT_INDIVIDUAL+";"+UNSUBSCRIBE+";"+gin+";"+domain+";"+comGroupType+";"+comType+";"+nbUnsubscribeMarketLanguageForIndividuals+"\n");
                } else {
                    this.totalUnsubscribeMarketLanguageForProspects += this.nbUnsubscribeMarketLanguageForIndividuals;
                    logBatch("line "+currentLine +";"+ML_OPTOUT_PROSPECT+";"+UNSUBSCRIBE+";"+gin+";"+domain+";"+comGroupType+";"+comType+";"+nbUnsubscribeMarketLanguageForIndividuals+"\n");
                }
            }
        }

//       // SIC Individual
//       if(isIndividual && !hasBeenTreatedMarketLanguageForIndividuals){
//           this.nbUnsubscribeMarketLanguageForIndividuals = communicationPreferencesDS.unsubscribeMarketLanguage(gin, domain, comGroupType, comType, market, language);
//           hasBeenTreatedMarketLanguageForIndividuals = true;
//
//           if(this.nbUnsubscribeMarketLanguageForIndividuals == 0){
//               logBatch("line "+currentLine +";"+ML_OPTOUT_INDIVIDUAL_ALREADY_SET+";"+UNSUBSCRIBE+";"+gin+";"+domain+";"+comGroupType+";"+comType+";"+market+";"+language+"\n");
//               this.nbUnsubscribeAlreadyTreatedForIndividuals++;
//           }
//           else{
//               this.totalUnsubscribeMarketLanguageForIndividuals += this.nbUnsubscribeMarketLanguageForIndividuals;
//               logBatch("line "+currentLine +";"+ML_OPTOUT_INDIVIDUAL+";"+UNSUBSCRIBE+";"+gin+";"+domain+";"+comGroupType+";"+comType+";"+nbUnsubscribeMarketLanguageForIndividuals+"\n");
//           }
//       }
//
//       // SIC UTF8
//       else if(!isIndividual && !hasBeenTreatedMarketLanguageForIndividuals){
//           this.nbUnsubscribeCommunicationPreferenceForProspects = prospectCommunicationPreferencesDS.unsubscribeMarketLanguage(gin, domain, comGroupType, comType, market, language);
//
//           if(this.nbUnsubscribeMarketLanguageForProspects == 0){
//               logBatch("line "+currentLine +";"+ML_OPTOUT_PROSPECT_ALREADY_SET+";"+UNSUBSCRIBE+";"+gin+";"+domain+";"+comGroupType+";"+comType+";"+market+";"+language+"\n");
//               this.nbUnsubscribeAlreadyTreatedForProspects++;
//           }
//           else{
//               this.totalUnsubscribeCommunicationPreferenceForProspects += this.nbUnsubscribeCommunicationPreferenceForProspects;
//               logBatch("line "+currentLine +";"+ML_OPTOUT_PROSPECT+";"+UNSUBSCRIBE+";"+gin+";"+domain+";"+comGroupType+";"+comType+";"+nbUnsubscribeMarketLanguageForProspects+"\n");
//           }
//        }

    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean isOk) {
        this.isOk = isOk;
    }

    /**
     * @return the emailDS
     */
    public EmailDS getEmailDS() {
        return emailDS;
    }

    /**
     * @param emailDS the emailDS to set
     */
    public void setEmailDS(EmailDS emailDS) {
        this.emailDS = emailDS;
    }

    /**
     * @return the individuDS
     */
    public IndividuDS getIndividuDS() {
        return individuDS;
    }

    /**
     * @param individuDS the individuDS to set
     */
    public void setIndividuDS(IndividuDS individuDS) {
        this.individuDS = individuDS;
    }

    /**
     * @return the communicationPreferencesDS
     */
    public CommunicationPreferencesDS getCommunicationPreferencesDS() {
        return communicationPreferencesDS;
    }

    /**
     * @param communicationPreferencesDS the communicationPreferencesDS to set
     */
    public void setCommunicationPreferencesDS(CommunicationPreferencesDS communicationPreferencesDS) {
        this.communicationPreferencesDS = communicationPreferencesDS;
    }

    /**
     * @return the nbInvalidEmailsSendFailureForIndividuals
     */
    public long getNbInvalidEmailsSendFailureForIndividuals() {
        return nbInvalidEmailsSendFailureForIndividuals;
    }

    /**
     * @param nbInvalidEmailsSendFailureForIndividuals the nbInvalidEmailsSendFailureForIndividuals to set
     */
    public void setNbInvalidEmailsSendFailureForIndividuals(long nbInvalidEmailsSendFailureForIndividuals) {
        this.nbInvalidEmailsSendFailureForIndividuals = nbInvalidEmailsSendFailureForIndividuals;
    }

    /**
     * @return the totalInvalidEmailsSendFailureForIndividuals
     */
    public long getTotalInvalidEmailsSendFailureForIndividuals() {
        return totalInvalidEmailsSendFailureForIndividuals;
    }

    /**
     * @param totalInvalidEmailsSendFailureForIndividuals the totalInvalidEmailsSendFailureForIndividuals to set
     */
    public void setTotalInvalidEmailsSendFailureForIndividuals(long totalInvalidEmailsSendFailureForIndividuals) {
        this.totalInvalidEmailsSendFailureForIndividuals = totalInvalidEmailsSendFailureForIndividuals;
    }

    /**
     * @return the nbInvalidEmailsSendFailureForProspects
     */
    public long getNbInvalidEmailsSendFailureForProspects() {
        return nbInvalidEmailsSendFailureForProspects;
    }

    /**
     * @param nbInvalidEmailsSendFailureForProspects the nbInvalidEmailsSendFailureForProspects to set
     */
    public void setNbInvalidEmailsSendFailureForProspects(long nbInvalidEmailsSendFailureForProspects) {
        this.nbInvalidEmailsSendFailureForProspects = nbInvalidEmailsSendFailureForProspects;
    }

    /**
     * @return the totalInvalidEmailsSendFailureForProspects
     */
    public long getTotalInvalidEmailsSendFailureForProspects() {
        return totalInvalidEmailsSendFailureForProspects;
    }

    /**
     * @param totalInvalidEmailsSendFailureForProspects the totalInvalidEmailsSendFailureForProspects to set
     */
    public void setTotalInvalidEmailsSendFailureForProspects(long totalInvalidEmailsSendFailureForProspects) {
        this.totalInvalidEmailsSendFailureForProspects = totalInvalidEmailsSendFailureForProspects;
    }

    /**
     * @return the nbInvalidEmailsHardBounceForIndividuals
     */
    public long getNbInvalidEmailsHardBounceForIndividuals() {
        return nbInvalidEmailsHardBounceForIndividuals;
    }

    /**
     * @param nbInvalidEmailsHardBounceForIndividuals the nbInvalidEmailsHardBounceForIndividuals to set
     */
    public void setNbInvalidEmailsHardBounceForIndividuals(long nbInvalidEmailsHardBounceForIndividuals) {
        this.nbInvalidEmailsHardBounceForIndividuals = nbInvalidEmailsHardBounceForIndividuals;
    }

    /**
     * @return the totalInvalidEmailsHardBounceForIndividuals
     */
    public long getTotalInvalidEmailsHardBounceForIndividuals() {
        return totalInvalidEmailsHardBounceForIndividuals;
    }

    /**
     * @param totalInvalidEmailsHardBounceForIndividuals the totalInvalidEmailsHardBounceForIndividuals to set
     */
    public void setTotalInvalidEmailsHardBounceForIndividuals(long totalInvalidEmailsHardBounceForIndividuals) {
        this.totalInvalidEmailsHardBounceForIndividuals = totalInvalidEmailsHardBounceForIndividuals;
    }

    /**
     * @return the nbInvalidEmailsHardBounceForProspects
     */
    public long getNbInvalidEmailsHardBounceForProspects() {
        return nbInvalidEmailsHardBounceForProspects;
    }

    /**
     * @param nbInvalidEmailsHardBounceForProspects the nbInvalidEmailsHardBounceForProspects to set
     */
    public void setNbInvalidEmailsHardBounceForProspects(long nbInvalidEmailsHardBounceForProspects) {
        this.nbInvalidEmailsHardBounceForProspects = nbInvalidEmailsHardBounceForProspects;
    }

    /**
     * @return the totalInvalidEmailsHardBounceForProspects
     */
    public long getTotalInvalidEmailsHardBounceForProspects() {
        return totalInvalidEmailsHardBounceForProspects;
    }

    /**
     * @param totalInvalidEmailsHardBounceForProspects the totalInvalidEmailsHardBounceForProspects to set
     */
    public void setTotalInvalidEmailsHardBounceForProspects(long totalInvalidEmailsHardBounceForProspects) {
        this.totalInvalidEmailsHardBounceForProspects = totalInvalidEmailsHardBounceForProspects;
    }

    /**
     * @return the nbUnsubscribeCommunicationPreferenceForIndividuals
     */
    public long getNbUnsubscribeCommunicationPreferenceForIndividuals() {
        return nbUnsubscribeCommunicationPreferenceForIndividuals;
    }

    /**
     * @param nbUnsubscribeCommunicationPreferenceForIndividuals the nbUnsubscribeCommunicationPreferenceForIndividuals to set
     */
    public void setNbUnsubscribeCommunicationPreferenceForIndividuals(
            long nbUnsubscribeCommunicationPreferenceForIndividuals) {
        this.nbUnsubscribeCommunicationPreferenceForIndividuals = nbUnsubscribeCommunicationPreferenceForIndividuals;
    }

    /**
     * @return the totalUnsubscribeCommunicationPreferenceForIndividuals
     */
    public long getTotalUnsubscribeCommunicationPreferenceForIndividuals() {
        return totalUnsubscribeCommunicationPreferenceForIndividuals;
    }

    /**
     * @param totalUnsubscribeCommunicationPreferenceForIndividuals the totalUnsubscribeCommunicationPreferenceForIndividuals to set
     */
    public void setTotalUnsubscribeCommunicationPreferenceForIndividuals(
            long totalUnsubscribeCommunicationPreferenceForIndividuals) {
        this.totalUnsubscribeCommunicationPreferenceForIndividuals = totalUnsubscribeCommunicationPreferenceForIndividuals;
    }

    /**
     * @return the totalUnsubscribeCommunicationPreferenceForProspects
     */
    public long getTotalUnsubscribeCommunicationPreferenceForProspects() {
        return totalUnsubscribeCommunicationPreferenceForProspects;
    }

    /**
     * @param totalUnsubscribeCommunicationPreferenceForProspects the totalUnsubscribeCommunicationPreferenceForProspects to set
     */
    public void setTotalUnsubscribeCommunicationPreferenceForProspects(
            long totalUnsubscribeCommunicationPreferenceForProspects) {
        this.totalUnsubscribeCommunicationPreferenceForProspects = totalUnsubscribeCommunicationPreferenceForProspects;
    }

    /**
     * @return the nbUnsubscribeMarketLanguageForIndividuals
     */
    public long getNbUnsubscribeMarketLanguageForIndividuals() {
        return nbUnsubscribeMarketLanguageForIndividuals;
    }

    /**
     * @param nbUnsubscribeMarketLanguageForIndividuals the nbUnsubscribeMarketLanguageForIndividuals to set
     */
    public void setNbUnsubscribeMarketLanguageForIndividuals(long nbUnsubscribeMarketLanguageForIndividuals) {
        this.nbUnsubscribeMarketLanguageForIndividuals = nbUnsubscribeMarketLanguageForIndividuals;
    }

    /**
     * @return the totalUnsubscribeMarketLanguageForIndividuals
     */
    public long getTotalUnsubscribeMarketLanguageForIndividuals() {
        return totalUnsubscribeMarketLanguageForIndividuals;
    }

    /**
     * @param totalUnsubscribeMarketLanguageForIndividuals the totalUnsubscribeMarketLanguageForIndividuals to set
     */
    public void setTotalUnsubscribeMarketLanguageForIndividuals(long totalUnsubscribeMarketLanguageForIndividuals) {
        this.totalUnsubscribeMarketLanguageForIndividuals = totalUnsubscribeMarketLanguageForIndividuals;
    }

    /**
     * @return the totalUnsubscribeMarketLanguageForProspects
     */
    public long getTotalUnsubscribeMarketLanguageForProspects() {
        return totalUnsubscribeMarketLanguageForProspects;
    }

    /**
     * @param totalUnsubscribeMarketLanguageForProspects the totalUnsubscribeMarketLanguageForProspects to set
     */
    public void setTotalUnsubscribeMarketLanguageForProspects(long totalUnsubscribeMarketLanguageForProspects) {
        this.totalUnsubscribeMarketLanguageForProspects = totalUnsubscribeMarketLanguageForProspects;
    }

    /**
     * @return the nbInvalidationNotTreated
     */
    public int getNbInvalidationNotTreated() {
        return nbInvalidationNotTreated;
    }

    /**
     * @param nbInvalidationNotTreated the nbInvalidationNotTreated to set
     */
    public void setNbInvalidationNotTreated(int nbInvalidationNotTreated) {
        this.nbInvalidationNotTreated = nbInvalidationNotTreated;
    }

    /**
     * @return the nbSpamNotTreated
     */
    public int getNbSpamNotTreated() {
        return nbSpamNotTreated;
    }

    /**
     * @param nbSpamNotTreated the nbSpamNotTreated to set
     */
    public void setNbSpamNotTreated(int nbSpamNotTreated) {
        this.nbSpamNotTreated = nbSpamNotTreated;
    }

    /**
     * @return the nbUnsubscribeNotTreated
     */
    public int getNbUnsubscribeNotTreated() {
        return nbUnsubscribeNotTreated;
    }

    /**
     * @param nbUnsubscribeNotTreated the nbUnsubscribeNotTreated to set
     */
    public void setNbUnsubscribeNotTreated(int nbUnsubscribeNotTreated) {
        this.nbUnsubscribeNotTreated = nbUnsubscribeNotTreated;
    }

    /**
     * @return the nbInvalidationAlreadyTreatedForIndivdiduals
     */
    public int getNbInvalidationAlreadyTreatedForIndivdiduals() {
        return nbInvalidationAlreadyTreatedForIndivdiduals;
    }

    /**
     * @param nbInvalidationAlreadyTreatedForIndivdiduals the nbInvalidationAlreadyTreatedForIndivdiduals to set
     */
    public void setNbInvalidationAlreadyTreatedForIndivdiduals(int nbInvalidationAlreadyTreatedForIndivdiduals) {
        this.nbInvalidationAlreadyTreatedForIndivdiduals = nbInvalidationAlreadyTreatedForIndivdiduals;
    }

    /**
     * @return the nbInvalidationAlreadyTreatedForProspects
     */
    public int getNbInvalidationAlreadyTreatedForProspects() {
        return nbInvalidationAlreadyTreatedForProspects;
    }

    /**
     * @param nbInvalidationAlreadyTreatedForProspects the nbInvalidationAlreadyTreatedForProspects to set
     */
    public void setNbInvalidationAlreadyTreatedForProspects(int nbInvalidationAlreadyTreatedForProspects) {
        this.nbInvalidationAlreadyTreatedForProspects = nbInvalidationAlreadyTreatedForProspects;
    }

    /**
     * @return the nbSpamAlreadyTreatedForIndividuals
     */
    public int getNbSpamAlreadyTreatedForIndividuals() {
        return nbSpamAlreadyTreatedForIndividuals;
    }

    /**
     * @param nbSpamAlreadyTreatedForIndividuals the nbSpamAlreadyTreatedForIndividuals to set
     */
    public void setNbSpamAlreadyTreatedForIndividuals(int nbSpamAlreadyTreatedForIndividuals) {
        this.nbSpamAlreadyTreatedForIndividuals = nbSpamAlreadyTreatedForIndividuals;
    }

    /**
     * @return the nbSpamAlreadyTreatedForProspects
     */
    public int getNbSpamAlreadyTreatedForProspects() {
        return nbSpamAlreadyTreatedForProspects;
    }

    /**
     * @param nbSpamAlreadyTreatedForProspects the nbSpamAlreadyTreatedForProspects to set
     */
    public void setNbSpamAlreadyTreatedForProspects(int nbSpamAlreadyTreatedForProspects) {
        this.nbSpamAlreadyTreatedForProspects = nbSpamAlreadyTreatedForProspects;
    }

    /**
     * @return the nbUnsubscribeAlreadyTreatedForIndividuals
     */
    public int getNbUnsubscribeAlreadyTreatedForIndividuals() {
        return nbUnsubscribeAlreadyTreatedForIndividuals;
    }

    /**
     * @param nbUnsubscribeAlreadyTreatedForIndividuals the nbUnsubscribeAlreadyTreatedForIndividuals to set
     */
    public void setNbUnsubscribeAlreadyTreatedForIndividuals(int nbUnsubscribeAlreadyTreatedForIndividuals) {
        this.nbUnsubscribeAlreadyTreatedForIndividuals = nbUnsubscribeAlreadyTreatedForIndividuals;
    }

    /**
     * @return the nbUnsubscribeAlreadyTreatedForProspects
     */
    public int getNbUnsubscribeAlreadyTreatedForProspects() {
        return nbUnsubscribeAlreadyTreatedForProspects;
    }

    /**
     * @param nbUnsubscribeAlreadyTreatedForProspects the nbUnsubscribeAlreadyTreatedForProspects to set
     */
    public void setNbUnsubscribeAlreadyTreatedForProspects(int nbUnsubscribeAlreadyTreatedForProspects) {
        this.nbUnsubscribeAlreadyTreatedForProspects = nbUnsubscribeAlreadyTreatedForProspects;
    }

    /**
     * @return the hasBeenTreatedCommunicationPreferencesForIndividuals
     */
    public boolean isHasBeenTreatedCommunicationPreferencesForIndividuals() {
        return hasBeenTreatedCommunicationPreferencesForIndividuals;
    }

    /**
     * @param hasBeenTreatedCommunicationPreferencesForIndividuals the hasBeenTreatedCommunicationPreferencesForIndividuals to set
     */
    public void setHasBeenTreatedCommunicationPreferencesForIndividuals(
            boolean hasBeenTreatedCommunicationPreferencesForIndividuals) {
        this.hasBeenTreatedCommunicationPreferencesForIndividuals = hasBeenTreatedCommunicationPreferencesForIndividuals;
    }

    /**
     * @return the hasBeenTreatedMarketLanguageForIndividuals
     */
    public boolean isHasBeenTreatedMarketLanguageForIndividuals() {
        return hasBeenTreatedMarketLanguageForIndividuals;
    }

    /**
     * @param hasBeenTreatedMarketLanguageForIndividuals the hasBeenTreatedMarketLanguageForIndividuals to set
     */
    public void setHasBeenTreatedMarketLanguageForIndividuals(boolean hasBeenTreatedMarketLanguageForIndividuals) {
        this.hasBeenTreatedMarketLanguageForIndividuals = hasBeenTreatedMarketLanguageForIndividuals;
    }
}
