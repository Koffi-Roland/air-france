package com.airfrance.repind.entity.firme;

import java.util.ArrayList;
import java.util.List;

public enum ScopeToProvideFirmEnum {
	/** enum value*/
    AGENCIES,
    /** enum value*/
    ALL,
    /** enum value*/
    COMMERCIAL_ZONES,
    /** enum value*/
    CONTRACTS,
    /** enum value*/
    EMAILS,
    /** enum value*/
    FINANCIAL_ZONES,
    /** enum value*/
    KEY_NUMBERS,
    /** enum value*/
    MARKET_CHOICES,
    /** enum value*/
    NUMBERS,
    /** enum value*/
    POSTAL_ADDRESSES,
    /** enum value*/
    SALES_ZONES,
    /** enum value*/
    SBT,
    /** enum value*/
    SYNONYMS,
    /** enum value*/
    TELECOMS,
    /** enum value*/
	SERVICES;
    
    private final static List<String> allValues = new ArrayList<String>(ScopeToProvideFirmEnum.values().length);
    
    static{
        for(ScopeToProvideFirmEnum v: ScopeToProvideFirmEnum.values())
        	allValues.add(v.name());
    }
    
    
}
