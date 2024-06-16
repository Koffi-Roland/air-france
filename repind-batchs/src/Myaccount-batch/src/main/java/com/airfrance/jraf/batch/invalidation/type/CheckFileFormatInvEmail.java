package com.airfrance.jraf.batch.invalidation.type;

import com.airfrance.repind.entity.refTable.RefTableLANGUES;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static com.airfrance.jraf.batch.invalidation.type.ConstInvalidation.*;

public class CheckFileFormatInvEmail extends CheckFileFormat {
    
    // For R3 platform of CRMPUSH
    private static final String FBSPUSH = "FBSPUSH";
    // For R1 & R2 platforms of CRMPUSH
    private static final String CRMPUSH = "CRMPUSH";
    
    public static String MARKET = "Market of communication preferences";
    public static String LANGUAGE = "Language of communication preferences";
    public static String DOMAIN = "Domain of communication preferences";
    public static String COMM_GROUP_TYPE = "Comm group type";
    public static String COM_TYPE = "Comm type";

    public static String FIELD_INCOHERENCE_EMAIL = " FIELD NOT COHERENT WITH " + MAIL + ": ";
       
    public static int MAX_EMAIL = 60;
    
    @Override
    public boolean isHeaderValid(String applicationName) {
        if (!StringUtils.isEmpty(applicationName) && (isHeaderCRMPUSH(applicationName) || isHeaderFBSPUSH(applicationName))) {
            return true;
        }
        return false;
    }
    
    /**
     * True if header is FBSPUSH
     * @param application : Application name within the header
     * @return true if FBSPUSH
     */
    public boolean isHeaderFBSPUSH(String application)
    {
        return application.equalsIgnoreCase(FBSPUSH);
    }

    /**
     * True if header is CRMPUSH
     * @param application : Application name within the header
     * @return true if CRMPUSH
     */
    public boolean isHeaderCRMPUSH(String application)
    {
        return application.equalsIgnoreCase(CRMPUSH);
    }
    
    @Override
    public boolean isActionValid(String action) {
        String[] list = { UNSUBSCRIBE_ACTION, INVALIDATION_ACTION };
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(list));
        return (arrayList.contains(action.trim().toUpperCase()));
    }

    @Override
    public boolean isCommunicationReturnCodeValid(String crcv) {
        String[] list = { SEND_FAILURE, HARD_BOUNCE, SPAM, UNSUBSCRIBE };
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(list));
        return (arrayList.contains(crcv.trim().toUpperCase()));
    }
    
    public boolean isCommunication8or10(String crcv) {
        String[] list = { SPAM, UNSUBSCRIBE };
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(list));
        return (arrayList.contains(crcv.trim().toUpperCase()));
    }

    public boolean isCommunication10(String crcv) {
        String[] list = { UNSUBSCRIBE };
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(list));
        return (arrayList.contains(crcv.trim().toUpperCase()));
    }

    public boolean isCommunicationReturnCodeCoherent(String crcv, String action) {
        if (crcv.equals(SEND_FAILURE) || crcv.equals(HARD_BOUNCE)) {
            if (!action.equalsIgnoreCase(INVALIDATION_ACTION))
                return false;
            else
                return true;
        } else if (crcv.equals(SPAM) || crcv.equals(UNSUBSCRIBE)) {
            if (!action.equalsIgnoreCase(UNSUBSCRIBE_ACTION))
                return false;
            else
                return true;
        } else
            return false;
    }    
  
    public boolean isEmailCoherentWithContact(String email, String contact){
        if(contact.equals(EMAIL)){
            if(!StringUtils.isEmpty(contact)){
                return true;
            }
            else
                return false;
            }
        return false;
    }
    
    

    public boolean isCodeLangueValid(String langue) {
        return RefTableLANGUES.instance().estValide(langue, "");
    }

    public boolean isMarketValid(String market) {
        String[] list = { "AC", "AE", "AM", "AN", "AR", "AT", "AU", "AW", "B01", "BE", "BF", "BG", "BH", "BI", "BJ", "BQ", "BR", "BZ", "CA", "CD", "CG", "CH", "CI", "CL", "CM", "CN", "CO", "CR",
                "CU", "CW", "CY", "CZ", "DE", "DJ", "DK", "DO", "DZ", "EC", "EE", "EG", "ES", "ET", "FI", "FR", "GA", "GB", "GF", "GH", "GP", "GR", "GT", "HK", "HN", "HR", "HT", "HU", "ID", "IE",
                "IL", "IN", "IQ", "IR", "IS", "IT", "JO", "JP", "KE", "KH", "KR", "KW", "KZ", "LB", "LR", "LT", "LU", "LV", "LY", "MA", "MC", "MF", "MG", "ML", "MQ", "MU", "MW", "MX", "MY", "NC",
                "NE", "NG", "NL", "NO", "NZ", "OM", "PA", "PE", "PF", "PH", "PK", "PL", "PT", "QA", "RE", "RO", "RS", "RU", "RW", "SA", "SC", "SD", "SE", "SG", "SI", "SK", "SL", "SN", "SR", "SV",
                "SX", "SY", "TG", "TH", "TN", "TR", "TW", "TZ", "UA", "UG", "US", "UY", "UZ", "VE", "VN", "X01", "X02", "X03", "X04", "X05", "X06", "X07", "X08", "X09", "XX", "YY", "ZA", "ZM" };
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(list));
        return (arrayList.contains(market.trim().toUpperCase()));
    }

  }
