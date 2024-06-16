package com.airfrance.repind.util;

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.ref.type.CommunicationPreferencesConstantValues;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.individu.myaccountcustomerdata.ReturnDetailsDTO;
import com.airfrance.repind.exception.InvalidGlobalOptinException;
import com.airfrance.repind.exception.MaximumSubscriptionsException;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// REPIND-555 : Migration of Prospect 
// Initial code was on SICUTF8 COMMONS package. But, nothing seems to justify this location 
// Only the used of ProspectCommunicationPreferencesDTO, So migration project change used to CommunicationPreferencesDTO 
public class CommunicationPreferencesManagment {

	public CommunicationPreferencesManagment(){
		
	}
	
	public static ReturnDetailsDTO updateProspectCommunicationPreferences(Set<CommunicationPreferencesDTO> commPrefListFromDB, Set<CommunicationPreferencesDTO> commPrefListFromWS, boolean globalOptin) throws JrafDomainException {
		ReturnDetailsDTO returnDetailsDTO = updateProspectCommunicationPreferencesWithoutValidateGlobalOptin(commPrefListFromDB, commPrefListFromWS);

		if(globalOptin){
			validateGlobalOptin(commPrefListFromDB);
	    	editDateOptInGlobal(commPrefListFromDB);
		}
    	return returnDetailsDTO;
	}
	
	public static ReturnDetailsDTO updateProspectCommunicationPreferencesWithoutValidateGlobalOptin(Set<CommunicationPreferencesDTO> commPrefListFromDB, Set<CommunicationPreferencesDTO> commPrefListFromWS) throws JrafDomainException {
    	
		// initialisation de la reponse
		ReturnDetailsDTO response = new ReturnDetailsDTO();
    	response=setReturnDetailsOnAProspect(1);
		
    	// on a des comm prefs à analyser
    	if(commPrefListFromDB!=null && !commPrefListFromDB.isEmpty() && commPrefListFromWS!=null && !commPrefListFromWS.isEmpty()){
    		
	    	Set<CommunicationPreferencesDTO> commPrefToAddList = new HashSet<CommunicationPreferencesDTO>();
	    	
	    	// parcours des comms prefs en entrée
	    	for(CommunicationPreferencesDTO commPrefFromWS : commPrefListFromWS){
	    		
	    		boolean foundCommPref = false;
	    		
	    		// parcours des comms prefs en base
	    		for(CommunicationPreferencesDTO commPrefFromDB : commPrefListFromDB){
	    			
	    			// correction d'anomalies
	    			if(commPrefFromDB.getDomain()==null) {
	    				commPrefFromDB.setDomain("S");
	    			}
	    			
	    			// correction d'anomalies : OptIn ?
	    			if(commPrefFromDB.getSubscribe()!=null && "O".equalsIgnoreCase(commPrefFromDB.getSubscribe())) {
	    				commPrefFromDB.setSubscribe("Y");
	    			}
	    			
	    			// concordance comm prefs (domain + comm group type + comm type) -> update
	    			if(commPrefFromDB.getDomain().equalsIgnoreCase(commPrefFromWS.getDomain()) 
	        		&& commPrefFromDB.getComGroupType().equalsIgnoreCase(commPrefFromWS.getComGroupType())
	        		&& commPrefFromDB.getComType().equalsIgnoreCase(commPrefFromWS.getComType())){
	    						
	    				boolean noChangesForMarketLang=false;
	    				foundCommPref=true; // comm pref trouvée
	    				
	        			//recherche de concordance des M/L
	    				if(commPrefFromDB.getMarketLanguageDTO()!=null && !commPrefFromDB.getMarketLanguageDTO().isEmpty()
	    				&& commPrefFromWS.getMarketLanguageDTO()!=null && !commPrefFromWS.getMarketLanguageDTO().isEmpty()){
	    					
	    					Set<MarketLanguageDTO> marketLangToAddList = new HashSet<MarketLanguageDTO>();
	    					
	    					// parcours des market languages en entrée
	    					for(MarketLanguageDTO marketLangFromWS : commPrefFromWS.getMarketLanguageDTO()){
	    								 
	    						boolean foundMarketLang=false;
	    						noChangesForMarketLang=false;
	    						
	    						// parcours des market languages en base
	    						for(MarketLanguageDTO marketLangFromDB : commPrefFromDB.getMarketLanguageDTO()){
	    							
	    							// concordance market language (language) -> update
	        						if(marketLangFromDB.getMarket().equalsIgnoreCase(marketLangFromWS.getMarket()) && marketLangFromDB.getLanguage().equalsIgnoreCase(marketLangFromWS.getLanguage())){
	        							
	        							// concordance optin -> rien à faire
	        							if(marketLangFromDB.getOptIn().equalsIgnoreCase(marketLangFromWS.getOptIn())){
	        								
	        								//marché langue existant aucune modif à faire sur com_pref et ml.
	        								noChangesForMarketLang=true;
	        								
	        								if("N".equalsIgnoreCase(marketLangFromWS.getOptIn())) {
	        									response=setReturnDetailsOnAProspect(7);
	        								}
        									
	        								if("Y".equalsIgnoreCase(marketLangFromWS.getOptIn())) {
	        									response=setReturnDetailsOnAProspect(4);
	        								}
        									
	        								//attention on update quand meme les localisation et telecoms...
	        									
	        							}
	        							
	        							//mise à jour du marche/langue avec l'input (REPLACE mode)
        								foundMarketLang=true;
        							
        								updateMarketLanguage(marketLangFromWS, marketLangFromDB);

	        						}
	        					}
	    							    						
	    						// pas de MAJ et changement
	    						if(!foundMarketLang && !noChangesForMarketLang) {
	    							marketLangToAddList.add(marketLangFromWS); // market languages à MAJ
	    						}
	    					}
	    					
	    					// on a des market languages à MAJ -> update
	    					if(marketLangToAddList!=null && !marketLangToAddList.isEmpty()){
	    						if (CommunicationPreferencesConstantValues.DOMAIN_F.equalsIgnoreCase(commPrefFromDB.getDomain())) {
	    							commPrefFromDB.getMarketLanguageDTO().clear();
	    						}
    							commPrefFromDB.getMarketLanguageDTO().addAll(marketLangToAddList);
    							checkNumberMarketLanguage(commPrefFromDB);
    						}
	    					
	    				}
	    				
	    				// si aucun M/L en DB on ajoute celui en entrée (il n'y en a qu'un mais bon on check quand meme)
	    				else{ 
	    					if(commPrefFromWS.getMarketLanguageDTO()!=null){
	    						commPrefFromDB.setMarketLanguageDTO(commPrefFromWS.getMarketLanguageDTO());
	    						checkNumberMarketLanguage(commPrefFromDB);
	    					}
	    				}
	    						
	    				// concordance COM_PREF: UPDATE DES DONNEES (si le m/l a été modifié) (REPLACE mode)
    					updateCommunicationPreferences(commPrefFromWS, commPrefFromDB);
	   					
	    			}
	   			}
	    		
	    		// non concordance COM_PREF on ajoute la COM_PREF de l'entrée
	    		if(!foundCommPref) {
					commPrefToAddList.add(commPrefFromWS); // nouvelle comm prefs
	    		}
	 		}
	    	
	   		if(commPrefToAddList!=null && !commPrefToAddList.isEmpty()) {
    			commPrefListFromDB.addAll(commPrefToAddList); // update comm prefs
	   		}
	   	}
    	// aucune COM_PREF existe => on ajoute celles en entrées
		else {
			if(commPrefListFromWS!=null){
	   			commPrefListFromDB.addAll(commPrefListFromWS);
			}
 		}
    	
    	return response;
    }

	private static void updateCommunicationPreferences(CommunicationPreferencesDTO commPrefFromWS, CommunicationPreferencesDTO commPrefFromDB) {
		
		// getDateOfConsent
		if(commPrefFromWS.getDateOptin()!=null) {
			commPrefFromDB.setDateOptin(commPrefFromWS.getDateOptin());
		} else {
			commPrefFromDB.setDateOptin(new Date());
		}
		
		if(commPrefFromWS.getChannel()!=null) {
			commPrefFromDB.setChannel(commPrefFromWS.getChannel());
		}
		
		if(commPrefFromWS.getDateOfEntry()!=null) {
			commPrefFromDB.setDateOfEntry(commPrefFromWS.getDateOfEntry());
		}

		if(commPrefFromWS.getOptinPartners()!=null) {
			commPrefFromDB.setOptinPartners(commPrefFromWS.getOptinPartners());
		}
		// getDateOfConsentPartners
		if(commPrefFromWS.getDateOptinPartners()!=null) {
			commPrefFromDB.setDateOptinPartners(commPrefFromWS.getDateOptinPartners());
		}
		
		if(commPrefFromWS.getMedia1()!=null) {
			commPrefFromDB.setMedia1(commPrefFromWS.getMedia1());
		} else {
			commPrefFromDB.setMedia1(null);
		}
		
		if(commPrefFromWS.getMedia2()!=null) {
			commPrefFromDB.setMedia2(commPrefFromWS.getMedia2());
		} else {
			commPrefFromDB.setMedia2(null);
		}
		
		if(commPrefFromWS.getMedia3()!=null) {
			commPrefFromDB.setMedia3(commPrefFromWS.getMedia3());
		} else {
			commPrefFromDB.setMedia3(null);
		}
		
		if(commPrefFromWS.getMedia4()!=null) {
			commPrefFromDB.setMedia4(commPrefFromWS.getMedia4());
		} else {
			commPrefFromDB.setMedia4(null);
		}
		
		if(commPrefFromWS.getMedia5()!=null) {
			commPrefFromDB.setMedia5(commPrefFromWS.getMedia5());
		} else {
			commPrefFromDB.setMedia5(null);
		}
		
		if(commPrefFromWS.getModificationSignature()!=null) {
			commPrefFromDB.setModificationSignature(commPrefFromWS.getModificationSignature());
		}

		if(commPrefFromWS.getModificationSite()!=null) {
			commPrefFromDB.setModificationSite(commPrefFromWS.getModificationSite());
		}

		if(commPrefFromWS.getModificationDate()!=null) {
			commPrefFromDB.setModificationDate(commPrefFromWS.getModificationDate());
		}
		
		// maj de l'optin // getOptIn
		if(commPrefFromWS.getSubscribe()!=null) {
			commPrefFromDB.setSubscribe(commPrefFromWS.getSubscribe());
		}
		
	}

	private static void updateMarketLanguage(MarketLanguageDTO marketLangFromWS, MarketLanguageDTO marketLangFromDB) {
		
		marketLangFromDB.setOptIn(marketLangFromWS.getOptIn()); 
		
		if(marketLangFromWS.getDateOfConsent()!=null) {
			marketLangFromDB.setDateOfConsent(marketLangFromWS.getDateOfConsent());
		} else {
			marketLangFromDB.setDateOfConsent(new Date());
		}
		
		if(marketLangFromWS.getMedia1()!=null) {
			marketLangFromDB.setMedia1(marketLangFromWS.getMedia1());
		} else {
			marketLangFromDB.setMedia1(null);
		}
		
		if(marketLangFromWS.getMedia2()!=null) {
			marketLangFromDB.setMedia2(marketLangFromWS.getMedia2());
		} else {
			marketLangFromDB.setMedia2(null);
		}
		
		if(marketLangFromWS.getMedia3()!=null) {
			marketLangFromDB.setMedia3(marketLangFromWS.getMedia3());
		} else {
			marketLangFromDB.setMedia3(null);
		}
		
		if(marketLangFromWS.getMedia4()!=null) {
			marketLangFromDB.setMedia4(marketLangFromWS.getMedia4());
		} else {
			marketLangFromDB.setMedia4(null);
		}
		
		if(marketLangFromWS.getMedia5()!=null) {
			marketLangFromDB.setMedia5(marketLangFromWS.getMedia5());
		} else {
			marketLangFromDB.setMedia5(null);
		}
		
		if(marketLangFromWS.getModificationSignature()!=null) {
			marketLangFromDB.setModificationSignature(marketLangFromWS.getModificationSignature());
		}

		if(marketLangFromWS.getModificationSite()!=null) {
			marketLangFromDB.setModificationSite(marketLangFromWS.getModificationSite());
		}

		if(marketLangFromWS.getModificationDate()!=null) {
			marketLangFromDB.setModificationDate(marketLangFromWS.getModificationDate());
		}
	}
	
	
	
	 public static void checkNumberMarketLanguage(CommunicationPreferencesDTO cp) throws MaximumSubscriptionsException{
	    	if(cp.getMarketLanguageDTO().size()>10) {
	    		throw new MaximumSubscriptionsException("MAXIMUM NUMBER OF SUBSCRIBED NEWSLETER SALES REACHED");
	    	}
	    }
	    
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
	    public static void editDateOptInGlobal(Set<CommunicationPreferencesDTO> commPrefListFromDB){
	    	
	    	if(commPrefListFromDB==null) {
	    		return;
	    	}
	    	
    		for(CommunicationPreferencesDTO commPrefFromDB : commPrefListFromDB){
	    		
    			Date globalDate = null;
	    		
    			if(commPrefFromDB.getDateOptin()!=null) {		// TODO : OR DateOfEnty ??
	    			globalDate = commPrefFromDB.getDateOptin();
    			}
	    		
    			if(commPrefFromDB.getMarketLanguageDTO()!=null){
	    			
    				for(MarketLanguageDTO marketLangFromDB : commPrefFromDB.getMarketLanguageDTO()){
	    				
    					if("Y".equalsIgnoreCase(marketLangFromDB.getOptIn())){
	    					
    						if(globalDate!=null){
	    						if(marketLangFromDB.getDateOfConsent()!=null && marketLangFromDB.getDateOfConsent().compareTo(globalDate) < 0) {
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
	    		if((globalDate!=null && commPrefFromDB.getDateOptin()!=null && globalDate.compareTo(commPrefFromDB.getDateOptin()) < 0)
	    				||(commPrefFromDB.getDateOptin()==null && globalDate!=null)) {
	    			commPrefFromDB.setDateOptin(globalDate);
	    		}
	    	}
	    }
	    
	    /**
	     * This method is aimed to validate global optin for all CP
	     */
	    public static void validateGlobalOptin(Set<CommunicationPreferencesDTO> commPrefsList) throws InvalidGlobalOptinException {
	    	
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
	    public static void validateGlobalOptin(CommunicationPreferencesDTO commPrefs) throws InvalidGlobalOptinException {
	    	
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
	    	if("Y".equalsIgnoreCase(commPrefs.getSubscribe()) && 		// REPIND-1706 : SAUF POUR PROMO ALERT
	    			!("S".equals(commPrefs.getDomain()) && "P".equals(commPrefs.getComGroupType()) && "AF".equals(commPrefs.getComType()))
	    	) {
	    		throw new InvalidGlobalOptinException("global optin is not consistent with market languages optin");
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
