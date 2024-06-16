package com.afklm.batch.invalidation.sms;

import com.afklm.batch.invalidation.sms.type.GenerateLogInvalidationSMS;
import com.afklm.repind.ws.w000443.data.schema591279.Telecom;
import com.airfrance.batch.common.BatchInvalidationArgs;
import com.airfrance.batch.common.IBatch;
import com.airfrance.batch.common.config.WebConfigBatchRepind;
import com.airfrance.batch.common.utils.CheckFileFormatInvSMS;
import com.airfrance.batch.common.utils.InvalFileFieldIndex;

import com.airfrance.ref.SiteEnum;
import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.exception.telecom.InvalBDException;
import com.airfrance.ref.exception.telecom.InvalNotFoundException;
import com.airfrance.ref.exception.telecom.InvalidationTelecomException;
import com.airfrance.ref.type.MediumStatusEnum;
import com.airfrance.repind.dto.adresse.TelecomsDTO;
import com.airfrance.repind.dto.reference.RefCodeInvalPhoneDTO;
import com.airfrance.repind.entity.adresse.Telecoms;
import com.airfrance.repind.service.adresse.internal.TelecomDS;
import com.airfrance.repind.service.reference.internal.RefCodeInvalPhoneDS;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.airfrance.batch.common.utils.CheckFileFormat.*;

/**
 * Batch Invalidation des SMS (Phone Number).
 * @author t251684
 *
 */
@Service("batchInvalidationSMS")
public class BatchInvalidationSMS extends BatchInvalidationArgs {


    private static final int HEADER_LINE_INDEX = 1;

    private static final String ORIGIN_FIELD_EMPTY_HEADER = "Origin field is empty on header line.";

    private static final String TAG_NAME = "batchInvalidationSMS";
  
    private static final String UNABLE_TO_INVALIDATE_TELECOM = "Unable to invalidate Telecom ";
    private static final String UNABLE_TO_FIND_TELECOM = "Unable to find telecom ";
    
    public final static Log log = LogFactory.getLog(BatchInvalidationSMS.class);
    protected GenerateLogInvalidationSMS generateLog = new GenerateLogInvalidationSMS();
    protected CheckFileFormatInvSMS checkFileFormat = new CheckFileFormatInvSMS();

    @Autowired
    private TelecomDS telecomDS;

	@Autowired
    private RefCodeInvalPhoneDS refCodeInvalPhoneDS;
    
    private static final String BATCH_NAME = "BatchInvalidationSMS.sh";
    private static final String SIGNATURE_INVALID_SMS_ROC = "INVALPHONEROC"; // 15 caracteres maximum
    private static final String SIGNATURE_INVALID_SMS_CRMP = "INVALPHONECRMP"; // 15 caracteres maximum

    protected Integer nbLinesTotal = 0;
    private boolean isOk = true;
    private boolean isRoc = false;

    // Statistique et integration continue.
    private int invalErrorCodeAppNb = 0;
    private int invalErrorCodeEmptyNb = 0;
    private int invalErrorCodeTooLongNb = 0;
    private int invalErrorCodeTooShortNb = 0;
    private int nbOK = 0;
    private int nbKO = 0;
    
    // Statistique pour log de synthese
    private int total = 0;
    /**
     * Nombre de telecom traités.
     */
    private int nbProcess = 0;
    /**
     * Nombre de telecom en echec.
     */
    private int nbFailed = 0;
   
    /**
     * Nombre de telecom non trouvé en db.
     */
    private int nbNotFound = 0;
    /**
     * Nombre de telecom impossible à invalider.
     */
    private int nbBD = 0;
    /**
     * Nombre d'erreur code non invalidant.
     */
    private int nbErrorNotInv = 0;
    /**
     * Nombre de code au format invalide.
     */
    private int nbCodeNotValid = 0;
    /**
     * Nombre de ligne au format invalide.
     */
    private int nbNotCheck = 0;




     /**
     * Methode pour effectuer le batch en Local.
     * @param args : argument du batch.
     */
    public static void main(String[] args) {
        try{            
        	
        	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(WebConfigBatchRepind.class);
        	
            // Recuperation de la classe de test
            IBatch batch = (IBatch) ctx.getBean(TAG_NAME);
            ((BatchInvalidationSMS) batch).setBatchName(BATCH_NAME);

            // parsing file
            log.info(LOG_FILE_READ_ARGS);
            ((BatchInvalidationSMS) batch).parseArgs(args);
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
    
    /**
     * Execution du Batch.
     */
    @Override
    public void execute() throws JrafDomainException {
        if (this.local) {
            generateLog.setDataDir(this.localPath);
        }
        generateLog.setFileSuffix(currentFileTraited);
        
        log.info("Opening input file: " + currentFileTraited + "\n");

        try {
            generateLog.openLogFiles();
            processFile();
            
            if (isOk) {
                log.info(VALIDITY_OK);
            }
            
            if(total > 0){
                total--;
            }
            
            generateLog.getBfwSynthesis().write("SMS INVALIDATION\n");
            generateLog.getBfwSynthesis().write("Total                              : " + total +"\n");
            generateLog.getBfwSynthesis().write("Nb of invalidation done            : " + nbProcess +"\n");
            generateLog.getBfwSynthesis().write("Nb of number unable to invalidate  : " + nbBD +"\n");
            generateLog.getBfwSynthesis().write("Nb of error with invalid format    : " + nbCodeNotValid + "\n");
            generateLog.getBfwSynthesis().write("Nb of error code not found         : " + nbNotFound +"\n");
            generateLog.getBfwSynthesis().write("Nb of line not valid               : " + nbNotCheck +"\n");
            

            generateLog.closeLogFiles();

            
        } catch (FileNotFoundException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        }

    }
    /**
     * Traiter le fichier pour invalidation.
     * 
     * @throws IOException : Erreur Ecriture Log.
     */
    private void processFile() throws IOException {
        String line;
        this.openBufferReader(currentFileTraited);
        int currentLine = 0;
        while ((line = this.getBfr().readLine()) != null) {
            currentLine++;
            // Passer la ligne en entete.
            if(currentLine == HEADER_LINE_INDEX){ 
                processHeader(line);
                continue;
            }
            String[] oneData = line.split(SEPARATOR);
            
            if(checkFile(currentLine, oneData)){
                processSMS(line, currentLine);
            }
            else{
                ++nbNotCheck ;
            }

        }
        total = currentLine;
        
        print("Total Lines: " + nbLinesTotal + "\n");
        
        //Fermeture
        generateLog.getBfwValidity().flush();

        this.getBfr().close();
        log.info(END_CHECK_VALIDITY);
    }
    /**
     * Recuperer l'origine de la demande d'invalidation.
     * 
     * @param line : header line.
     */
    private void processHeader(String line) {
        String[] oneData = line.split(SEPARATOR);        
        String origin = oneData[0];     
        if(origin == null){
            log.warn(ORIGIN_FIELD_EMPTY_HEADER);
        }
        else if(checkFileFormat.isHeaderCRMPUSH(origin))
        {
            isRoc = false;
        }
        else if(checkFileFormat.isHeaderROC(origin))
        {
            isRoc = true;
        }
    }
    
    
    /**
     * Traiter une ligne SMS pour invalidation.
     * 
     * @param line : ligne à traités.
     * @param currentLine : nombre de ligne.
     * @throws IOException : Erreur Ecriture Log.
     */
    private void processSMS(String line, int currentLine) throws IOException {

        String[] oneData = line.split(SEPARATOR);        
        String errorCode = oneData[InvalFileFieldIndex.COMRETURNCODE_INDEX];
        String normInterPhoneNumber = oneData[InvalFileFieldIndex.CONTACT_INDEX];
        try {   
            if(getCommit())
            {
                if(checkFileFormat.isCommunicationReturnCodeCorrect(errorCode))
                {
                    isInvalid(errorCode);
                    invalidatePhoneNumber(normInterPhoneNumber);
                    ++nbProcess;
                }
                else
                {
                    ++nbCodeNotValid;
                }
            }

        } 
        catch (InvalidationTelecomException e) {
            if(e instanceof InvalNotFoundException)
            {
                ++nbNotFound;
            }
            else if (e instanceof InvalBDException)
            {
                ++nbBD;
            }
            generateLog.getBfwReject().write("Line " + currentLine + " : " + e.getMessage() + "\n");
            generateLog.getBfwRejectLine().write(line + "\n");
            ++nbFailed;
        }
    }
    /**
     * Verifie si le code erreur est invalidant.
     * @param errorCode : error Code concerné.
     * @return vrai si errorCode est invalidant.
     * @throws InvalidationTelecomException : Impossible de trouver ErrorCode.
     */
    private void isInvalid(String errorCode) throws InvalidationTelecomException {

        RefCodeInvalPhoneDTO refCodeInvalPhoneDTO = new RefCodeInvalPhoneDTO();
        
        if(errorCode != null)
        {
            refCodeInvalPhoneDTO.setCodeError(errorCode);

            try {
                List<RefCodeInvalPhoneDTO> resultDTO =  this.refCodeInvalPhoneDS.findByExample(refCodeInvalPhoneDTO);
                if (resultDTO != null && resultDTO.size() >= 1)
                {
                }
                else{
                    throw new InvalNotFoundException(errorCode);
                }
            } catch (JrafDomainException e) {
                log.error(e);
                throw new InvalNotFoundException(errorCode);
            }
        }
    }
    /**
     * Invalidation des {@link Telecoms}.
     * @param normInterPhoneNumber : telephone à invalider.
     * @throws InvalidationTelecomException : Ecriture impossible en BD.
     */
    private void invalidatePhoneNumber(String normInterPhoneNumber) throws InvalidationTelecomException {
        TelecomsDTO telecomsDTO;
        telecomsDTO = new TelecomsDTO();
        telecomsDTO.setSnorm_inter_phone_number(normInterPhoneNumber);
        telecomsDTO.setSstatut_medium(MediumStatusEnum.VALID.toString());
        List<TelecomsDTO> telecomsDTOs = findTelecomsByInterPhoneNumber(telecomsDTO);
        if(telecomsDTOs != null && telecomsDTOs.size() > 0){
            invalidateTelecoms(telecomsDTOs);
        }
        else
        {
            log.warn( "Unable to find telecom with phone number " + normInterPhoneNumber );
            throw new InvalBDException(normInterPhoneNumber);
        }
    }
    
    /**
     * Invalidation des {@link Telecoms}.
     * @param telecomsDTOs : {@link List} des {@link TelecomsDTO} à invalider.
     * @throws InvalidationTelecomException 
     */
    private void invalidateTelecoms(List<TelecomsDTO> telecomsDTOs) throws InvalidationTelecomException {
        for (TelecomsDTO tel : telecomsDTOs) {
               try {
                tel.setDdate_modification(new Date());
                tel.setDdate_invalidation(new Date());
                if(isRoc){
                    tel.setSsignature_modification(SIGNATURE_INVALID_SMS_ROC);
                }
                else{
                    tel.setSsignature_modification(SIGNATURE_INVALID_SMS_CRMP);

                }
                tel.setSsite_modification(SiteEnum.BATCHQVI.toString());
                telecomDS.invalidateTelecoms(tel);
            } catch (JrafDomainException e) {
                   log.error(e);
                log.error( UNABLE_TO_INVALIDATE_TELECOM + e.getMessage()); 
                throw new InvalBDException(tel.getSnorm_inter_phone_number());
            }
        }
    }
    
    /**
     * Trouver le {@link Telecom} par numero international (E164).
     * @param telecomsDTO : {@link TelecomsDTO} contenant le international phone number (E164).
     * @return {@link List} des {@link TelecomsDTO}.
     */
    private List<TelecomsDTO> findTelecomsByInterPhoneNumber(TelecomsDTO telecomsDTO) {

        List<TelecomsDTO> telecoms = null;
        try {
            telecoms = telecomDS.findByExample(telecomsDTO);
        } catch (JrafDomainException e) {
            log.error(e);
                log.error(UNABLE_TO_FIND_TELECOM + e.getMessage()); 
        }
        return telecoms;
    }
    
    /**
     * Verifie l'integrité du fichier passé en entrée.
     *
     * @param count : compteur du nombre de ligne.
     * @param oneData : ligne du fichier parse sous forme de tableau.
     * @return chaine pour les logs. 
     * @throws IOException : Exception sur l'ouverture du fichier.
     */
    private boolean checkFile( Integer count, String[] oneData) throws IOException {

        isOk = true;
        // Header
        if (count == 1 && !checkFileFormat.isHeaderValid(oneData[0].trim())) {
            printValidity(ERROR_LABEL + count + FIELD_NOT_VALID + HEADER + "\n");
            isOk = false;
        }
        
        if (count > 1) {
            int currentLine = nbLinesTotal + 1;
            boolean lineOK = true;
            for (int i = 0; i < oneData.length; i++) {
                String currentData = "";
                if (oneData[i] != null){
                    currentData = oneData[i].trim();
                }
                
                if(!(checkAction(currentLine, i, currentData) &&
                checkCommunicationReturnCode(oneData, currentLine, i) &&
                checkContactType(currentLine, i, currentData) &&
                checkContact(currentLine, i, currentData) &&                  
                checkGin(currentLine, i, currentData) &&
                checkFBIdentifier(currentLine, i, currentData) &&
                checkAccountIdentifier(currentLine, i, currentData) &&
                checkCause(currentLine, i, currentData))){
                    lineOK = false;
                    isOk = false;
                }

            }
            
            if(lineOK){
                nbOK++;
                generateLog.getBfwValidity().write("line "+ currentLine + " is Valid.\n");
            }
            else{
                nbKO++;
                generateLog.getBfwValidity().write("line "+ currentLine + " is NOT Valid.\n");

            }
        }
        nbLinesTotal++;
        
        return isOk;
    }
    
    /**
     * Check Cause Integrity.
     * @param currentLine : ligne courante.
     * @param index : index de la ligne courante.
     * @param cause : valeur du champs a verifier.
     * @throws IOException : Exception Fichier log.
     */
    private boolean checkCause(int currentLine, int index, String cause) throws IOException {
        
        if (index == InvalFileFieldIndex.CAUSE_INDEX && !StringUtils.isEmpty(cause) && cause.length() > CheckFileFormatInvSMS.MAX_PHONE_NUMBER) {
            printValidity(ERROR_LABEL + currentLine + FIELD_OVERFLOW +CAUSE + "\n");
            return false;
        }
        
        return true;
    }
    
    /**
     * Check Account Identifier.
     * @param currentLine : ligne courante.
     * @param i : index de la ligne courante.
     * @param currentData : valeur du champs a verifier.
     * @throws IOException : Exception Fichier log.
     */
    private boolean checkAccountIdentifier(int currentLine, int i, String currentData) throws IOException {
        if (i == InvalFileFieldIndex.ACCOUNTIDENTIFIER_INDEX){

            if(!checkFileFormat.isAccountNumberValid(currentData)) {
                printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + ACCOUNT + "\n");
                return false;
            }
        }
        return true;

    }
    
    /**
     * Check FB Identifier.
     * @param currentLine : ligne courante.
     * @param i : index de la ligne courante.
     * @param currentData : valeur du champs a verifier.
     * @throws IOException : Exception Fichier log.
     */
    private boolean checkFBIdentifier(int currentLine, int i, String currentData) throws IOException {
        if (i == InvalFileFieldIndex.FBIDENTIFIER_INDEX){ 

            if(!checkFileFormat.isClientNumberValid(currentData)) {
                printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + FB + "\n");
                return false;
            }
        }
        return true;

    }
    /**
     * Check GIN.
     * @param currentLine : ligne courante.
     * @param index : index de la ligne courante.
     * @param gin : valeur du champs a verifier.
     * @throws IOException : Exception Fichier log.
     */
    private boolean checkGin(int currentLine, int index, String gin) throws IOException {
        if (index == InvalFileFieldIndex.GIN_INDEX) {

            if (!checkFileFormat.isClientNumberValid(gin)) {
                printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + GIN + "\n");
                return false;
            }
        }
        
        return true;
    }
    /**
     * Check Contact.
     * @param currentLine : ligne courante.
     * @param index : index de la ligne courante.
     * @param contact : valeur du champs a verifier.
     * @throws IOException : Exception Fichier log.
     */
    private boolean checkContact(int currentLine, int index, String contact) throws IOException {
        if (index == InvalFileFieldIndex.CONTACT_INDEX)
        {
            if(StringUtils.isEmpty(contact))
            {
                printValidity(ERROR_LABEL + currentLine + MANDATORY_FIELD + CONTACT + "\n");
                return false;
            }
            else if(!checkFileFormat.isPhoneNumberValid(contact)) {
                printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + CONTACT + "\n");
                return false;
            }
        }
        return true;
    }
    /**
     * Check Contact Type.
     * @param currentLine : ligne courante.
     * @param index : index de la ligne courante.
     * @param contactType : valeur du champs a verifier.
     * @throws IOException : Exception Fichier log.
     */
    private boolean checkContactType(int currentLine, int index, String contactType) throws IOException {
        if (index == InvalFileFieldIndex.CONTACTTYPE_INDEX)
        {
            if(StringUtils.isEmpty(contactType))
            {
                printValidity(ERROR_LABEL + currentLine + MANDATORY_FIELD + CONTACT_TYPE + "\n");
                return false;
            }
            
            if(!checkFileFormat.isContactValid(contactType)) {
                printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + CONTACT_TYPE + "\n");
                return false;
            }
        }
        return true;

    }
    /**
     * Check Communication Return Code.
     * @param oneData : ligne courante.
     * @param currentL : index de la ligne courante.
     * @param i : valeur du champs a verifier.
     * @throws IOException : Exception Fichier log.
     */
    private boolean checkCommunicationReturnCode(String[] oneData, int currentL, int i) throws IOException {
        try {
            if(i == InvalFileFieldIndex.COMRETURNCODE_INDEX){
                if ( oneData[i].length() < 1) {
                    invalErrorCodeEmptyNb++;
                    printValidity(ERROR_LABEL + currentL + MANDATORY_FIELD + COMMUNICATION_RETURN_CODE + "\n");
                    return false;
                }
                
                if (!checkFileFormat.isCommunicationReturnCodeCorrect(oneData[i])) {
                    printValidity(ERROR_LABEL + currentL + FIELD_NOT_VALID + COMMUNICATION_RETURN_CODE + "\n");
                    return false;
                }
            }

        } catch (InvalidationTelecomException e) {
            log.error(e);
            printValidity(ERROR_LABEL + currentL + FIELD_NOT_VALID + e.getMessage() + "\n");
            return false;
        }
        return true;

    }
    /**
     * Check Action.
     * @param currentLine : ligne courante.
     * @param index : index de la ligne courante.
     * @param action : valeur du champs a verifier.
     * @throws IOException : Exception Fichier log.
     */
    private boolean checkAction(int currentLine, int index, String action) throws IOException {
        if (index == InvalFileFieldIndex.ACTION_INDEX){
            if (StringUtils.isEmpty(action)) {
                printValidity(ERROR_LABEL + currentLine + MANDATORY_FIELD + ACTION + "\n");
                return false;
            }
            if (action.length() >= 2) {
                printValidity(ERROR_LABEL + currentLine + FIELD_OVERFLOW + ACTION + "\n");
                return false;
            }
            if (!checkFileFormat.isActionValid(action)) {
                printValidity(ERROR_LABEL + currentLine + FIELD_NOT_VALID + ACTION + "\n");
                return false;
            }    
        }
        return true;
    }
    
    /**
     * Afficher dans la console si en mode trace.
     * @param print : chaine a afficher.
     */
    public void print(String print) {
        if (this.trace) {
            System.out.print(print);
            log.info(print);
        }
    }
    
    /**
     * Afficher dans la consoloe et dans les logs de validation.
     * @param str : chaine a afficher.
     * @throws IOException : Exception lié au log de validation.
     */
    private void printValidity(String str) throws IOException
    {
        print(str);
        
        if(generateLog.getBfwValidity() != null){
            generateLog.getBfwValidity().write(str);
        }
        
    }
    //---------------------------------------------------------
    // Getter et setter
    public CheckFileFormatInvSMS getCheckFileFormat() {
        return checkFileFormat;
    }
    public void setCheckFileFormat(CheckFileFormatInvSMS checkFileFormat) {
        this.checkFileFormat = checkFileFormat;
    }
    public int getInvalErrorCodeAppNb() {
        return checkFileFormat.getInvalErrorCodeAppNb();
    }
    public int getInvalErrorCodeEmptyNb() {
        return this.invalErrorCodeEmptyNb;
    }

    public int getInvalErrorCodeTooLongNb() {
        return this.invalErrorCodeTooLongNb;
    }

    public int getInvalErrorCodeTooShortNb() {
        return checkFileFormat.getInvalErrorCodeTooShortNb();
    }

    public boolean isOk() {
        return isOk;
    }
    public int getNbOK() {
        return nbOK;
    }
    public void setNbOK(int nbOK) {
        this.nbOK = nbOK;
    }
    public int getNbKO() {
        return nbKO;
    }
    public void setNbKO(int nbKO) {
        this.nbKO = nbKO;
    }
    
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNbProcess() {
        return nbProcess;
    }

    public void setNbProcess(int nbProcess) {
        this.nbProcess = nbProcess;
    }

    public int getNbNotFound() {
        return nbNotFound;
    }

    public void setNbNotFound(int nbNotFound) {
        this.nbNotFound = nbNotFound;
    }

    public int getNbBD() {
        return nbBD;
    }

    public void setNbBD(int nbBD) {
        this.nbBD = nbBD;
    }

    public int getNbCodeNotValid() {
        return nbCodeNotValid;
    }

    public void setNbCodeNotValid(int nbCodeNotValid) {
        this.nbCodeNotValid = nbCodeNotValid;
    }

    public int getNbNotCheck() {
        return nbNotCheck;
    }

    public void setNbNotCheck(int nbNotCheck) {
        this.nbNotCheck = nbNotCheck;
    }
    
    public TelecomDS getTelecomDS() {
		return telecomDS;
	}

	public void setTelecomDS(TelecomDS telecomDS) {
		this.telecomDS = telecomDS;
	}

	public RefCodeInvalPhoneDS getRefCodeInvalPhoneDS() {
		return refCodeInvalPhoneDS;
	}

	public void setRefCodeInvalPhoneDS(RefCodeInvalPhoneDS refCodeInvalPhoneDS) {
		this.refCodeInvalPhoneDS = refCodeInvalPhoneDS;
	}
}
