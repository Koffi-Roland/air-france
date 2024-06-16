package com.airfrance.repind.entity.zone.enums;


/*PROTECTED REGION ID(_aksVQOQREeSFL6CIIk0fDw i) ENABLED START*/

// add not generated imports here

import java.util.HashMap;
import java.util.Map;

/*PROTECTED REGION END*/

/**
 * <p>Title : PrivilegedLinkEnum.java</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public enum PrivilegedLinkEnum {


    
    /**
     * YES
     */
    YES("O"),
    
    /**
     * NO
     */
    NO("N");

    /** enumLiteralMap */
    private static Map<String, PrivilegedLinkEnum> enumLiteralMap = null;


    /** value */
    private String value;

    /*PROTECTED REGION ID(_aksVQOQREeSFL6CIIk0fDw u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
       
        
    /**
     * full Constructor
     * @param value parameter
     */
    private PrivilegedLinkEnum(String value) {
        this.value = value;
    }

    /**
     * Return an PrivilegedLinkEnum instance according to the literal value
     * @param literal String value that represent the key value value of an enum.
     *   the key value is the sum of each field
     * @return the enum find or null if no enum found.
     */
    public static synchronized PrivilegedLinkEnum fromLiteral(String literal) {
        if (enumLiteralMap == null) {
            enumLiteralMap = new HashMap<String, PrivilegedLinkEnum>();
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


    /*PROTECTED REGION ID(_aksVQOQREeSFL6CIIk0fDw u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
