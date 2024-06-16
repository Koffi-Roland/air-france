package com.airfrance.repind.util;

import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repindutf8.dto.prospect.prospectservices.ReturnDetailsDTO;

import java.util.Set;

public class CommunicationPreferencesUtils {

	public CommunicationPreferencesUtils(){
		
	}
	
	
	
	
/*	 public static void checkNumberProspectMarketLanguage(ProspectCommunicationPreferencesDTO cp) throws MaximumSubscriptionsException{
	    	if(cp.getMarketLanguageDTO().size()>10) {
	    		throw new MaximumSubscriptionsException("MAXIMUM NUMBER OF SUBSCRIBED NEWSLETER SALES REACHED");
	    	}
	    }
*/	    
	    /**
	     * Fonction permettant d'affecter les paramètres de l'objet ReturnDetails en fonction du numéro d'erreur
	     */
	    private static ReturnDetailsDTO setReturnDetailsOnAProspect(int number){
	    	
	    	ReturnDetailsDTO response = new ReturnDetailsDTO();
	    	
	    	if(number==4){
	    		response.setDetailedCode(""+number);
	    		response.setLabelCode("Existing Prospect Found, optin already set on this market/language.");
	    	}
	    	
	    	if(number==1){
	    		response.setDetailedCode(""+number);
	    		response.setLabelCode("Existing Prospect Found, subscription on this market/language ok.");
	    	}
	    	
	    	if(number==7){
	    		response.setDetailedCode(""+number);
	    		response.setLabelCode("Existing Prospect Found, Market/language not found or already optout.");
	    	}
	    	
			return response;
	    }
	    
	    /**
	     * Cette fonction permet de setter la date d'optin global à la date du plus vieux marché langue dont optin=Y
	     * @param commPrefListFromDB
	     */
/*	    public static void editDateOptInGlobal(Set<ProspectCommunicationPreferencesDTO> commPrefListFromDB){
	    	
	    	if(commPrefListFromDB==null) {
	    		return;
	    	}
	    	
    		for(ProspectCommunicationPreferencesDTO commPrefFromDB : commPrefListFromDB){
	    		
    			Date globalDate = null;
	    		
    			if(commPrefFromDB.getDateOfConsent()!=null) {
	    			globalDate = commPrefFromDB.getDateOfConsent();
    			}
	    		
    			if(commPrefFromDB.getMarketLanguageDTO()!=null){
	    			
    				for(ProspectMarketLanguageDTO marketLangFromDB : commPrefFromDB.getMarketLanguageDTO()){
	    				
    					if("Y".equalsIgnoreCase(marketLangFromDB.getOptIn())){
	    					
    						if(globalDate!=null){
	    						if(marketLangFromDB.getDateOfConsent()!=null && marketLangFromDB.getDateOfConsent().compareTo(globalDate)==-1) {
	    							globalDate = marketLangFromDB.getDateOfConsent();
	    						}
	    					}
	    					else{
	    						if(marketLangFromDB.getDateOfConsent()!=null) {
	    							globalDate = marketLangFromDB.getDateOfConsent();
	    						}
	    					}
	    				}
	    			}
	    		}	
	    		if((globalDate!=null && commPrefFromDB.getDateOfConsent()!=null && globalDate.compareTo(commPrefFromDB.getDateOfConsent())==-1)
	    				|(commPrefFromDB.getDateOfConsent()==null && globalDate!=null)) {
	    			commPrefFromDB.setDateOfConsent(globalDate);
	    		}
	    	}
	    }
*/	    
	    /**
	     * This method is aimed to validate global optin for all CP
	     */
	    public static void validateGlobalOptin(Set<CommunicationPreferencesDTO> commPrefsList) throws com.airfrance.repind.exception.InvalidGlobalOptinException {
	    	
	    	// no comm prefs -> nothing to do
	    	if(commPrefsList==null || commPrefsList.isEmpty()) {
	    		return;
	    	}
	    	
	    	// validate global optin for all comm prefs
	    	for(CommunicationPreferencesDTO commPrefs : commPrefsList) {
	    		validateGlobalOptin(commPrefs);
	    	}
	    	
	    }
	    
	    /**
	     * This method is aimed to validate global optin for a specific CP
	     */
	    public static void validateGlobalOptin(CommunicationPreferencesDTO commPrefs) throws com.airfrance.repind.exception.InvalidGlobalOptinException {
	    	
	    	Set<MarketLanguageDTO> marketLangList = commPrefs.getMarketLanguageDTO();
	    	
	    	// no market lang -> nothing to do
	    	if(marketLangList==null || marketLangList.isEmpty()) {
	    		return;
	    	}
	    	
	    	// global optin is valid -> nothing to do
	    	if(isValidCommPrefOptin(commPrefs)) {
	    		return;
	    	}
	    	
	    	// global optin YES -> error 932
	    	if ("Y".equalsIgnoreCase(commPrefs.getSubscribe()) && // REPIND-1706 : SAUF POUR PROMO ALERT
    			!("S".equals(commPrefs.getDomain()) && "P".equals(commPrefs.getComGroupType()) && "AF".equals(commPrefs.getComType()))
	    	) {
	    		throw new com.airfrance.repind.exception.InvalidGlobalOptinException("global optin is not consistent with market languages optin");
	    	}
	    	
	    	String globalOptin = commPrefs.getSubscribe();
	    
	    	// propagation of the global optin to all the market lang
	    	for(MarketLanguageDTO marketLang : marketLangList) {
	    		marketLang.setOptIn(globalOptin);
	    	}
	    	
	    }
	    
	    /**
	     * This method is aimed to check if the global optin is consistent with the market languages optin
	     */
	    public static boolean isValidCommPrefOptin(CommunicationPreferencesDTO commPrefFromDB){
	    	
	    	boolean globalOptin = false;
			
	    	// global optin = YES
	    	if("N".equalsIgnoreCase(commPrefFromDB.getSubscribe())) {
	    		globalOptin = isValidGlobalOptinNo(commPrefFromDB);
			}
			
	    	// global optin = NO
	    	if("Y".equalsIgnoreCase(commPrefFromDB.getSubscribe())) {
	    		globalOptin = isValidGlobalOptinYes(commPrefFromDB);
			}
			
	    	return globalOptin;
	    }
	    
	    /**
	     * A correct NO global optin has all its market language Optin are set to NO
	     */
	    public static boolean isValidGlobalOptinNo(CommunicationPreferencesDTO commPrefFromDB) {
	    	
			for(MarketLanguageDTO mlExist : commPrefFromDB.getMarketLanguageDTO()){
				
				if("Y".equalsIgnoreCase(mlExist.getOptIn())) {
					return false;
				}
				
			}
	    	
			return true;
	    	
	    }
	    
	    
	    /**
	     * A correct YES global optin has at least 1 YES optin in market language
	     */
	    public static boolean isValidGlobalOptinYes(CommunicationPreferencesDTO commPrefFromDB) {
	    	
			for(MarketLanguageDTO marketLangFromDB : commPrefFromDB.getMarketLanguageDTO()){
				
				if("Y".equalsIgnoreCase(marketLangFromDB.getOptIn())) {
					return true;
				}
				
			}
			
			return false;
	    	
	    }
	    
	    
}
