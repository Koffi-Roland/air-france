package com.afklm.sicwscustomeridentification.requestsbuilders;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title : IdentificationTypeEnum</p>
 * 
 * <p>Copyright : Copyright (c) 2015</p>
 * <p>Company : AIRFRANCE</p>
 */
public enum IdentificationTypeEnum {
    
	/**
     * TOUT NUMERO AGENCE
     */
    ALL_NUMBER("TA"),
    /**
     * AMADEUS
     */
    AMADEUS("1A"),
    
    /**
     * ABACUS
     */
    ABACUS("1B"),
    
    /**
     * TRAVELSKY
     */
    TRAVELSKY("1E"),    
    
    /**
     * INFINI
     */
    INFINI("1F"),    
    
    /**
     * GALILEO
     */
    GALILEO("1G"),
    
    /**
     * AXESS
     */
    AXESS("1J"),
    
    /**
     * NAVITAIRE
     */
    NAVITAIRE("1N"),
    
    /**
     * WORLDSPAN
     */
    WORLDSPAN("1P"),
    
    /**
     * ITA
     */
    ITA("1U"),
    
    /**
     * APOLLO
     */
    APOLLO("1V"),
    
    /**
     * SABRE
     */
    SABRE("1S"),
    
    /**
     * EDS
     */
    EDS("1Y"),
    
    /**
     * G2SWITCHWORKS
     */
    G2SWITCHWORKS("A1"),
    
    /**
     * ALL_CONTRACTS
     */
    ALL_CONTRACTS("AC"),    
    
    /**
     * ARC_AGENCY (env. 11 000)
     */
    AGENCY_WITHOUT_AGREEMENT("AG"),
    
    /**
     * ANY_MYACCOUNT
     */
    ANY_MYACCOUNT("AI"),
    
    /**
     * FORMER_AGENCY_NUMBER (36 !!!)
     */
    FORMER_AGENCY_NUMBER("AN"),
    
    /**
     * ARC_AGENCY (env. 22 000)
     */
    ARC_AGENCY("AR"),
    
    /**
     * ATAF_AGENCY (617)
     */
    ATAF_AGENCY("AT"),
    
    /**
     * AMEX
     */
    AMEX("AX"),
    
    /**
     * KLM_AGENT_CODE (env. 70 000)
     */
    KLM_AGENT_CODE("BI"),
    
    /**
     * COVIA
     */
    COVIA("CO"),
    
    /**
     * DUNS (env. 24 000)
     */
    DUNS("DU"),
    
    /**
     * EAN (11 !!!)
     */
    EAN("EA"),
    
    /**
     * REGISTRATION (364)
     */
    REGISTRATION("EN"),
    
    /**
     * FLYING_BLUE
     */
    FLYING_BLUE("FP"),
    
    /**
     * GEMINI
     */
    GEMINI("GE"),
    
    /**
     * GIN
     */
    GIN("GIN"),
    
    /**
     * IATA_AGENCY (env. 150 000)
     */
    IATA_AGENCY("IA"),
    
    /**
     * PARTITA_IVA (env. 14 000)
     */
    PARTITA_IVA("IV"),
    
    /**
     * TOPAS
     */
    TOPAS("KE"),
    
    /**
     * KVK_NUMBER (env. 26 000)
     */
    KVK_NUMBER("KV"),
    
    /**
     * NCSC
     */
    NCSC("NCSC"),
    
    /**
     * SUSCRIBER
     */
    SUSCRIBER("RP"),
    
    /**
     * SAPHIR
     */
    SAPHIR("S"),
    
    /**
     * SODA
     */
    SODA("SD"),
    
    /**
     * SIREN (env. 4 000)
     */
    SIREN("SR"),
    
    /**
     * SIRET
     */
    SIRET("ST"),
    
    /**
     * VAT (630)
     */
    VAT("TV"),
    
    /**
     * UCC_CODE (env. 44 000)
     */
    UCC_CODE("UC");

    /** enumLiteralMap */
    private static Map<String, IdentificationTypeEnum> enumLiteralMap = null;


    /** value */
    private String value;       
        
    /**
     * full Constructor
     * @param value parameter
     */
    private IdentificationTypeEnum(String value) {
        this.value = value;
    }

    /**
     * Return an IdentificationTypeEnum instance according to the literal value
     * @param literal String value that represent the key value value of an enum.
     *   the key value is the sum of each field
     * @return the enum find or null if no enum found.
     */
    public static synchronized IdentificationTypeEnum fromLiteral(String literal) {
        if (enumLiteralMap == null) {
            enumLiteralMap = new HashMap<String, IdentificationTypeEnum>();
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


    /*PROTECTED REGION ID(_deOagOOKEeSFL6CIIk0fDz u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
