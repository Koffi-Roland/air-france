package com.airfrance.repind.entity.agence;

import java.util.HashSet;
import java.util.Set;

public enum SearchByIdEnum {
	/** enum value*/
	ALL("TA"),
	/** enum value*/
    GIN("GIN"),
    /** enum value*/
    ARC("AR"),
    /** enum value*/
    FORMERAGENCYNUMBER("AN"),
    /** enum value*/
    IATA("IA"),
    /** enum value*/
    ATAF("AT"),
    /** enum value*/
    KLMAGENTCODE("BI"),
    /** enum value*/
    AGENCYWITHOUTAGREEMENT("AG"),
    /** enum value*/
    ATLA("AS"),
    /** enum value*/
    G2SWITCHWORKS("A1"),
    /** enum value*/
    CONCORDE("CE"),
    /** enum value*/
    COVIA("CO"),
    /** enum value*/
    FANTASIA("FA"),
    /** enum value*/
    GEMINI("GE"),
    /** enum value*/
    JETSET("JT"),
    /** enum value*/
    TOPAS("KE"),
    /** enum value*/
    KOMMAS("KM"),
    /** enum value*/
    NEWHORIZONS("NH"),
    /** enum value*/
    PARS("PA"),
    /** enum value*/
    RETZ("RZ"),
    /** enum value*/
    SODA("SD"),
    /** enum value*/
    SYSTEMONE("SO"),
    /** enum value*/
    TARMAQ("TQ"),
    /** enum value*/
    TOURISTSTECHNOLOGY("TT"),
    /** enum value*/
    AMADEUS("1A"),
    /** enum value*/
    ABACUS("1B"),
    /** enum value*/
    TRAVELSKY("1E"),
    /** enum value*/
    INFINI("1F"),
    /** enum value*/
    GALILEO("1G"),
    /** enum value*/
    AXESS("1J"),
    /** enum value*/
    NAVITAIRE("1N"),
    /** enum value*/
    WORLDSPAN("1P"),
    /** enum value*/
    ITA("1U"),
    /** enum value*/
    APOLLO("1V"),
    /** enum value*/
    SABRE("1S"),
    /** enum value*/
    SABRE_OLD("1W"),
    /** enum value*/
    EDS("1Y");
    
    private final static Set<String> allValues = new HashSet<String>(SearchByIdEnum.values().length);
    
    private final String code;
    
    static{
        for(SearchByIdEnum v: SearchByIdEnum.values())
        	allValues.add(v.getCode());
    }

    public static boolean contains( String value ){
        return allValues.contains(value);
    }
    
    public final static String listValid(){
    	String ret="IDENTIFICATION TYPES ACCEPTED : ";
    	for(SearchByIdEnum v: SearchByIdEnum.values()){
    		ret+=(v.getCode() + "; ");
    	}
    	return ret; 
    }
    
    private SearchByIdEnum(String value)
    {
    	this.code = value;
    }
    
    public String getCode()
    {
    	return code;
    }
    
    public static SearchByIdEnum getEnumByString(String code){
    	for(SearchByIdEnum e : SearchByIdEnum.values()){
    		if(e.code.equalsIgnoreCase(code)) return e;
    	}
    	return null;
    }

}
