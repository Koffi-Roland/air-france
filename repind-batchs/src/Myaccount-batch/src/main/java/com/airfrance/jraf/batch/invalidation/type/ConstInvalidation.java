package com.airfrance.jraf.batch.invalidation.type;

public class ConstInvalidation {

    // PARSING STUFF
    public static final String SEND_FAILURE = "003";
    public static final String HARD_BOUNCE = "006";
    public static final String SPAM = "008";
    public static final String UNSUBSCRIBE = "010";
    
    public static final String UNSUBSCRIBE_ACTION ="U";
    public static final String INVALIDATION_ACTION ="I";
    
    public static String EMAIL = "E";
    public static String PHONE = "T";
   
    // SIGNATURE FOR INVALIDATION AND UNSUBSCRIPTION
    public static final String SIGNATURE_SEND_FAILURE = "InvEmail FBSP 3";
    public static final String SIGNATURE_HARD_BOUNCE = "InvEmail FBSP 6";
    
    public static final String SIGNATURE_SPAM = "OptOut FBSP 8";
    public static final String SIGNATURE_UNSUBSCRIBE = "OptOut FBSP 10";
        
    // LABELS FOR LOGS 
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";
       
    //OK FOR EMAIL INVALIDATION
    public static final String INVALIDATION_INDIVIDUAL = "SIC: INVALIDATION DONE";
    public static final String INVALIDATION_INDIVIDUAL_ALREADY_SET = "SIC: INVALIDATION ALREADY DONE or NOT IN FUNCTIONAL PERIMETER";
    public static final String INVALIDATION_PROSPECT = "SIC_UTF8: INVALIDATION DONE";
    public static final String INVALIDATION_PROSPECT_ALREADY_SET = "SIC_UTF8: INVALIDATION ALREADY DONE or NOT IN FUNCTIONAL PERIMETER";
      
    // FOR COMMUNICATION PREFERENCES
    public static final String CP_OPTOUT_INDIVIDUAL = "SIC: GLOBAL OPTOUT SET TO 'N'";
    public static final String CP_OPTOUT_INDIVIDUAL_ALREADY_SET = "SIC: GLOBAL OPTOUT ALREADY SET";
    public static final String CP_OPTOUT_PROSPECT = "SIC_UTF8: GLOBAL OPTOUT SET TO 'N'";
    public static final String CP_OPTOUT_PROSPECT_ALREADY_SET = "SIC_UTF8: GLOBAL OPTOUT ALREADY SET";
            
    // FOR MARKET LANGUAGE
    public static final String ML_OPTOUT_INDIVIDUAL = "SIC: SPECIFIC OPTOUT SET TO 'N'";
    public static final String ML_OPTOUT_INDIVIDUAL_ALREADY_SET = "SIC: SPECIFIC OPTOUT ALREADY SET";
    public static final String ML_OPTOUT_PROSPECT = "SIC_UTF8: SPECIFIC OPTOUT SET TO 'N'";
    public static final String ML_OPTOUT_PROSPECT_ALREADY_SET = "SIC_UTF8: SPECIFIC OPTOUT ALREADY SET";
        
    //TECHNICAL KO
    public static final String TECHNICAL_INVALIDATION_INDIVIDUAL = "SIC: TECHNICAL ERROR DURING EMAIL INV";
    public static final String TECHNICAL_CP_OPTOUT_INDIVIDUAL = "SIC: TECHNICAL ERROR DURING UNSUBSCRIPTION OF CP";
    public static final String TECHNICAL_ML_OPTOUT_INDIVIDUAL = "SIC: TECHNICAL ERROR DURING UNSUBSCRIPTION OF ML";
    
    public static final String TECHNICAL_INVALIDATION_PROSPECT = "SIC_UTF8: TECHNICAL ERROR DURING EMAIL INV";
    public static final String TECHNICAL_CP_OPTOUT_PROSPECT = "SIC_UTF8: TECHNICAL ERROR DURING UNSUBSCRIPTION OF CP";
    public static final String TECHNICAL_ML_OPTOUT_PROSPECT = "SIC_UTF8: TECHNICAL ERROR DURING UNSUBSCRIPTION OF ML";
}
