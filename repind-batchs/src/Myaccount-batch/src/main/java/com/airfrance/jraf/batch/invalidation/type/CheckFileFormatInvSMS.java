package com.airfrance.jraf.batch.invalidation.type;

import com.airfrance.ref.exception.telecom.*;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.airfrance.jraf.batch.invalidation.type.ConstInvalidation.INVALIDATION_ACTION;

/**
 * COntrole le format de fichier d'invalidation SMS.
 * 
 * @author t251684
 * 
 */
public class CheckFileFormatInvSMS extends CheckFileFormat {
    private int invalErrorCodeAppNb = 0;
    private int invalErrorCodeEmptyNb = 0;   
    private int invalErrorCodeTooLongNb = 0;
    private int invalErrorCodeTooShortNb = 0;
    
    public static final int MAX_PHONE_NUMBER = 60;

    private static final String CRMPUSH = "CRMP";
    private static final String ROC = "ROC";

    public String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    @Override
    public boolean isHeaderValid(String application) {
        if (!StringUtils.isEmpty(application) && (isHeaderCRMPUSH(application) || isHeaderROC(application) )) {
            return true;
        }
        return false;
    }

    /**
     * Vrai si le header est CRMPUSH.
     * @param application : application contenu dans header.
     * @return vrai si CRMP.
     */
    public boolean isHeaderCRMPUSH(String application)
    {
        return application.equalsIgnoreCase(CRMPUSH);
    }

    /**
     * Vrai si le header est ROC.
     * @param application : application contenu dans header.
     * @return vrai si ROC.
     */
    public boolean isHeaderROC(String application)
    {
        return application.equalsIgnoreCase(ROC);
    }
    
    /**
     * PhoneNumber Valid.
     * 
     * @param phoneNumber
     * @return
     */
    public boolean isPhoneNumberValid(String phoneNumber) {
        return (!StringUtils.isEmpty(phoneNumber)) && (phoneNumber.length() < MAX_PHONE_NUMBER);
    }

    @Override
    public boolean isActionValid(String action) {
        return INVALIDATION_ACTION.equals(action.trim().toUpperCase());
    }

    /**
     * Verifier le Communication Return Code.
     * 
     * @param errorCode String
     *            : communication return code.
     * @return vrai si le code est correct.
     */
    public boolean isCommunicationReturnCodeCorrect(String errorCode) throws InvalidationTelecomException {

        if(StringUtils.isEmpty(errorCode))
        {
            throw new InvalErrorCodeEmptyException(errorCode);
        }
        else{
            
            String strErrorCode = String.valueOf(errorCode.charAt(0));
            
            try {
                
                ErrorCodeSMSEnum errorCodeSMSEnum = ErrorCodeSMSEnum.valueOf(strErrorCode);
                
                switch (errorCodeSMSEnum) {
                
                case R:
                    // Exemple : R3002000
                    checkErrorCodeROC(errorCode);
                    break;
                case C:
                    // Exemple : C6101
                    checkErrorCodeCRMPUSH(errorCode);
                    break;
                default:
                    invalErrorCodeAppNb++;
                    throw new InvalErrorCodeAppException(errorCode);
                }
            }
            catch(IllegalArgumentException e)
            {
                invalErrorCodeAppNb++;
                throw new InvalErrorCodeAppException(errorCode);
            }
    
            return true;
        }
    }

    /**
     * Verifier le code erreur CRMPUSH.
     * @param errorCode : code erreur.
     * @throws InvalidationTelecomException : Code Erreur trop long ou trop court.
     */
    private void checkErrorCodeCRMPUSH(String errorCode) throws InvalidationTelecomException {
        if(errorCode.length() < (ErrorCodeSMSConstante.CODE_APP_LENGTH + ErrorCodeSMSConstante.STAT_FAIL_LENGTH +
                ErrorCodeSMSConstante.CODE_ERREUR_WORLDLINE_LENGTH)){
            invalErrorCodeTooShortNb++;
            throw new InvalErrorCodeTooShortException(errorCode);
        }
        else if(errorCode.length() > (ErrorCodeSMSConstante.CODE_APP_LENGTH + ErrorCodeSMSConstante.STAT_FAIL_LENGTH +
                ErrorCodeSMSConstante.CODE_ERREUR_WORLDLINE_LENGTH)){
            invalErrorCodeTooLongNb++;
            throw new InvalErrorCodeTooLongException(errorCode);
        }
    }

    /**
     * Verifier le code erreur ROC.
     * @param errorCode : code erreur.
     * @throws InvalidationTelecomException  : Code Erreur trop long ou trop court.
     */
    private void checkErrorCodeROC(String errorCode) throws InvalidationTelecomException {
        if(errorCode.length() < (ErrorCodeSMSConstante.CODE_APP_LENGTH + ErrorCodeSMSConstante.CODE_MEDIA_LENGTH +
                ErrorCodeSMSConstante.CODE_STATUT_LENGTH + ErrorCodeSMSConstante.CODE_ERREUR_XMEDIA_LENGTH)){
            invalErrorCodeTooShortNb++;
            throw new InvalErrorCodeTooShortException(errorCode);
        }else if (errorCode.length() > (ErrorCodeSMSConstante.CODE_APP_LENGTH + ErrorCodeSMSConstante.CODE_MEDIA_LENGTH +
                ErrorCodeSMSConstante.CODE_STATUT_LENGTH + ErrorCodeSMSConstante.CODE_ERREUR_XMEDIA_LENGTH)){
            invalErrorCodeTooLongNb++;
            throw new InvalErrorCodeTooLongException(errorCode);
        }
        
    }

    public int getInvalErrorCodeAppNb() {
        return invalErrorCodeAppNb;
    }

    public void setInvalErrorCodeAppNb(int invalErrorCodeAppNb) {
        this.invalErrorCodeAppNb = invalErrorCodeAppNb;
    }

    public int getInvalErrorCodeEmptyNb() {
        return invalErrorCodeEmptyNb;
    }

    public void setInvalErrorCodeEmptyNb(int invalErrorCodeEmptyNb) {
        this.invalErrorCodeEmptyNb = invalErrorCodeEmptyNb;
    }

    public int getInvalErrorCodeTooLongNb() {
        return invalErrorCodeTooLongNb;
    }

    public void setInvalErrorCodeTooLongNb(int invalErrorCodeTooLongNb) {
        this.invalErrorCodeTooLongNb = invalErrorCodeTooLongNb;
    }

    public int getInvalErrorCodeTooShortNb() {
        return invalErrorCodeTooShortNb;
    }

    public void setInvalErrorCodeTooShortNb(int invalErrorCodeTooShortNb) {
        this.invalErrorCodeTooShortNb = invalErrorCodeTooShortNb;
    }

    @Override
    public boolean isCommunicationReturnCodeValid(String crcv) {
        // TODO Module de remplacement de méthode auto-généré
        return false;
    }
}
