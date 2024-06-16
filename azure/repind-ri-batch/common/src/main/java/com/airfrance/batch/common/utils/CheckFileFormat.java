package com.airfrance.batch.common.utils;

import com.airfrance.ref.exception.jraf.JrafApplicativeException;
import com.airfrance.repind.util.SicStringUtils;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class CheckFileFormat {

    public static String ERROR_LABEL = "Error in line ";
    public static String INFO_LABEL = "[INFO] No update";

    public static String END_CHECK_VALIDITY = "END CHECK VALIDITY\n";
    public static String VALIDITY_OK = "VALIDITY IS OK\n";
    public static String FORCE_VALIDITY = "VALIDITY NOT OK BUT FORCE MODE ON\n";

    public static String HEADER = "Header";
    public static String ACTION = "Action";
    public static String MAIL = "Email";
    public static String COMMUNICATION_RETURN_CODE = "Communication return code";
    public static String CONTACT_TYPE = "Contact type";
    public static String CONTACT = "Contact";

    public static String GIN = "GIN";
    public static String FB = "FB identifier";
    public static String ACCOUNT = "Acc identifier";
    public static String CAUSE = "Cause";

    public static String MANDATORY_FIELD = " MANDATORY FIELD: ";
    public static String FIELD_OVERFLOW = " FIELD OVERFLOW: ";
    public static String FIELD_NOT_VALID = " FIELD NOT VALID: ";
    public static String FIELD_INCOHERENCE_ACTION = " FIELD NOT COHERENT WITH " +ACTION + ": ";

    public abstract boolean isHeaderValid(String applicationName);

    public abstract boolean isActionValid(String action);


    public boolean isContactValid(String contact) {
        String[] list = { ConstInvalidation.EMAIL, ConstInvalidation.PHONE };
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(list));
        return (arrayList.contains(contact.trim().toUpperCase()));
    }

    public abstract boolean isCommunicationReturnCodeValid(String crcv);

    public boolean isClientNumberValid(String clientNumber) {
        try {
            return (SicStringUtils.isValidFbIdentifier(clientNumberToString(clientNumber)));
        } catch (JrafApplicativeException e) {
            return false;
        }
    }

    public boolean isAccountNumberValid(String accountNumber) {
        try {
            return (SicStringUtils.isValidMyAccntIdentifier(accountNumber));
        } catch (JrafApplicativeException e) {
            return false;
        }
    }

    public static String clientNumberToString(String clientNumber) {
        if (clientNumber != null) {
            String gin = clientNumber;
            while (gin.length() < 12) {
                gin = '0' + gin;
            }
            return gin;
        }
        return null;
    }
}
