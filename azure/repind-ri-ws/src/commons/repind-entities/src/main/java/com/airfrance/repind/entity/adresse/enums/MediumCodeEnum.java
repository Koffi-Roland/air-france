package com.airfrance.repind.entity.adresse.enums;


/*PROTECTED REGION ID(_PG9L4Oz_EeSZJLRw8bGRiQ i) ENABLED START*/

// add not generated imports here

import java.util.HashMap;
import java.util.Map;

/*PROTECTED REGION END*/

/**
 * <p>Title : MediumCodeEnum.java</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public enum MediumCodeEnum {


    
    /**
     * HOME
     */
    HOME("D"),
    
    /**
     * HOTLINE
     */
    HOTLINE("R"),
    
    /**
     * BUSINESS
     */
    BUSINESS("P"),
    
    /**
     * LOCALISATION
     */
    LOCALISATION("L"),
    
    /**
     * BILLING
     */
    BILLING("F"),
    
    /**
     * MAILING
     */
    MAILING("M"),
    
    /**
     * BILLING_ADRESS
     */
    BILLING_ADRESS("E"),
    
    /**
     * ACCOUNTING
     */
    ACCOUNTING("C"),
    
    /**
     * GROUP
     */
    GROUP("G"),
    
    /**
     * TRAVEL_NEGOCIATOR
     */
    TRAVEL_NEGOCIATOR("N"),
    
    /**
     * PAYMENT
     */
    PAYMENT("V"),
    
    /**
     * DECISION_MAKER
     */
    DECISION_MAKER("T");

    /** enumLiteralMap */
    private static Map<String, MediumCodeEnum> enumLiteralMap = null;


    /** value */
    private String value;

    /*PROTECTED REGION ID(_PG9L4Oz_EeSZJLRw8bGRiQ u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
       
        
    /**
     * full Constructor
     * @param value parameter
     */
    private MediumCodeEnum(String value) {
        this.value = value;
    }

    /**
     * Return an MediumCodeEnum instance according to the literal value
     * @param literal String value that represent the key value value of an enum.
     *   the key value is the sum of each field
     * @return the enum find or null if no enum found.
     */
    public static synchronized MediumCodeEnum fromLiteral(String literal) {
        if (enumLiteralMap == null) {
            enumLiteralMap = new HashMap<String, MediumCodeEnum>();
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
    
    public static MediumCodeEnum fromString(String value) {
		for (MediumCodeEnum b : MediumCodeEnum.values()) {
			if (b.value.equalsIgnoreCase(value)) {
				return b;
			}
	    }
	    return null;
	}


    /*PROTECTED REGION ID(_PG9L4Oz_EeSZJLRw8bGRiQ u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
