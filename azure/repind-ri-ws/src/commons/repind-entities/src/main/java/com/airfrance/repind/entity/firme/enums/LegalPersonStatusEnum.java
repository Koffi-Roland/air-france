package com.airfrance.repind.entity.firme.enums;


/*PROTECTED REGION ID(_s56vMOQIEeSFL6CIIk0fDw i) ENABLED START*/

// add not generated imports here

import java.util.HashMap;
import java.util.Map;

/*PROTECTED REGION END*/

/**
 * <p>Title : LegalPersonStatusEnum.java</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public enum LegalPersonStatusEnum {


    
    /**
     * ACTIVE
     */
    ACTIVE("A"),
    
    /**
     * INVALID_ADDRESS
     */
    INVALID_ADDRESS("AI"),
    
    /**
     * MERGED
     */
    MERGED("FU"),
    
    /**
     * TEMPORARY
     */
    TEMPORARY("P"),
    
    /**
     * QUEUE
     */
    QUEUE("Q"),
    
    /**
     * STRUCK_OFF
     */
    STRUCK_OFF("R"),
    
    /**
     * CLOSED
     */
    CLOSED("X");

    /** enumLiteralMap */
    private static Map<String, LegalPersonStatusEnum> enumLiteralMap = null;


    /** value */
    private String value;

    /*PROTECTED REGION ID(_s56vMOQIEeSFL6CIIk0fDw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
       
        
    /**
     * full Constructor
     * @param value parameter
     */
    private LegalPersonStatusEnum(String value) {
        this.value = value;
    }

    /**
     * Return an LegalPersonStatusEnum instance according to the literal value
     * @param literal String value that represent the key value value of an enum.
     *   the key value is the sum of each field
     * @return the enum find or null if no enum found.
     */
    public static synchronized LegalPersonStatusEnum fromLiteral(String literal) {
        if (enumLiteralMap == null) {
            enumLiteralMap = new HashMap<String, LegalPersonStatusEnum>();
            for (int i = 0; i < values().length; i++) {
                enumLiteralMap.put(values()[i].toLiteral(), values()[i]);
            }
        }
        return enumLiteralMap.get(literal);
    }

    /**
     * Return a string value to use as a key to retrieve the enum from the Map
     * @return The concatenation of each field of the enum
     */
    public String toLiteral() {
         return value;
     
    }


    /*PROTECTED REGION ID(_s56vMOQIEeSFL6CIIk0fDw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
