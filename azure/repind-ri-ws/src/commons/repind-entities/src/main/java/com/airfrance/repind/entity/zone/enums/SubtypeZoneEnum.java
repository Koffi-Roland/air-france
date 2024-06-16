package com.airfrance.repind.entity.zone.enums;


/*PROTECTED REGION ID(_I0t9cOQaEeSFL6CIIk0fDw i) ENABLED START*/

// add not generated imports here

import java.util.HashMap;
import java.util.Map;

/*PROTECTED REGION END*/

/**
 * <p>Title : SubtypeZoneEnum.java</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public enum SubtypeZoneEnum {


    
    /**
     * SALES_FORCES
     */
    SALES_FORCES("FV"),
    
    /**
     * GROUPS
     */
    GROUPS("GR"),
    
    /**
     * MARKETING
     */
    MARKETING("MK"),
    
    /**
     * GLOBAL_ACCOUNT
     */
    GLOBAL_ACCOUNT("MM"),
    
    /**
     * MULTIMARKET
     */
    MULTIMARKET("CM"),
    
    /**
     * COMPTES_NATIONAUX
     */
    COMPTES_NATIONAUX("CN"),
    
    /**
     * TOUR_OPERATOR
     */
    TOUR_OPERATOR("TO"),
    
    /**
     * DIRECT_SALES
     */
    DIRECT_SALES("VD"),
    
    /**
     * SME
     */
    SME("SM");

    /** enumLiteralMap */
    private static Map<String, SubtypeZoneEnum> enumLiteralMap = null;


    /** value */
    private String value;

    /*PROTECTED REGION ID(_I0t9cOQaEeSFL6CIIk0fDw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
       
        
    /**
     * full Constructor
     * @param value parameter
     */
    private SubtypeZoneEnum(String value) {
        this.value = value;
    }

    /**
     * Return an SubtypeZoneEnum instance according to the literal value
     * @param literal String value that represent the key value value of an enum.
     *   the key value is the sum of each field
     * @return the enum find or null if no enum found.
     */
    public static synchronized SubtypeZoneEnum fromLiteral(String literal) {
        if (enumLiteralMap == null) {
            enumLiteralMap = new HashMap<String, SubtypeZoneEnum>();
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


    /*PROTECTED REGION ID(_I0t9cOQaEeSFL6CIIk0fDw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
