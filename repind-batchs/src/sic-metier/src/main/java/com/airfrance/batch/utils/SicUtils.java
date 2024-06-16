package com.airfrance.batch.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.airfrance.batch.utils.IConstants.ACTIVE;
import static com.airfrance.batch.utils.IConstants.BATCH_QVI;

/**
 * Sic Utils class
 */
public class SicUtils {

     /**
     * Method to encode String to Date
     * Using dd/MM/yyyy format to encode
     * @param iString
     * @return
     */
    public static Date encodeDate(String iFormat , String iString){
        Date date = null;
        if(StringUtils.isNotEmpty(iString) && StringUtils.isNotEmpty(iFormat)){
            try {
                date = new SimpleDateFormat(iFormat).parse(iString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * Encode status using string input
     * @param iString as string input
     * @return Y if iString = active
     *         Otherwise N
     */
    public static String encodeStatus(String iString){
        return ACTIVE.equalsIgnoreCase(iString) ? "Y" : "N";
    }

    /**
     * Encode subscription using string input
     * @param iString as string input
     * @return Y if iString = 1
     *         Otherwise N
     */
    public static String encodeSubscription(String iString){
        return "1".equalsIgnoreCase(iString) ? "Y" : "N";
    }

    /**
     * Encode Cin from String to String but add 00 at the beginning in case of the Cin provided is on 10 char
     * @param iString cin provided
     * @return cin on 12 char
     */
    public static String encodeCin(String iString){
        return iString != null ? iString.length() == 12 ? iString : "00"+iString : null;
    }

    /**
     * Encode Creation Site Date on 10 char
     * @param iString to encode
     * @return if iString lenght is > 10 return iString truncated
     */
    public static String encodeCreationSiteDate(String iString){
        return StringUtils.isNotEmpty(iString) ? iString.length() > 10 ? iString.substring(0 , 10) : iString : BATCH_QVI;
    }

    /**
     * return the subString of the sub if the number of char is greater than the given size
     * @param iString string to truncate
     * @param iSize number of char to truncate
     * @return
     */
    public static String subString(String iString , int iSize){
        return StringUtils.isNotEmpty(iString) ? iString.length() > iSize ? iString.substring(0 , iSize) : iString : iString;
    }

}
