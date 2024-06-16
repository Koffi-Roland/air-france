package com.airfrance.repind.entity.firme.enums;

import java.util.HashSet;
import java.util.Set;

public enum SearchByIdEnum {
	/** enum value*/
    GIN,
    /** enum value*/
    SR,
    ///** enum value*/
    // SI
    /** enum value*/
    ST,
    /** enum value*/
    EN,
    /** enum value*/
    KV,
    /** enum value*/
    DU,
    /** enum value*/
    AR,
    /** enum value*/
    UC,
    /** enum value*/
    IV,
    /** enum value*/
    AN,
    /** enum value*/
    IA,
    /** enum value*/
    AT,
    /** enum value*/
    EA,
    /** enum value*/
    BI,
    /** enum value*/
    AG,
    /** enum value*/
    TV,
    /** enum value*/
    NCSC,
    /** enum value*/
    NC
    ;
    
    private final static Set<String> allValues = new HashSet<String>(SearchByIdEnum.values().length);
    
    static{
        for(SearchByIdEnum v: SearchByIdEnum.values())
        	allValues.add(v.name());
    }

    public static boolean contains( String value ){
        return allValues.contains(value);
    }
    
    public final static String listValid(){
    	String ret="IDENTIFICATION TYPES ACCEPTED : ";
    	for(SearchByIdEnum v: SearchByIdEnum.values()){
    		ret+=(v.name() + "; ");
    	}
    	return ret; 
    }


}
