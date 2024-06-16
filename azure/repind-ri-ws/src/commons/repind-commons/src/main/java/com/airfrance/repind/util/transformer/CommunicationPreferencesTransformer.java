package com.airfrance.repind.util.transformer;

import com.airfrance.ref.exception.InvalidParameterException;
import com.airfrance.ref.type.CommunicationPreferencesConstantValues;
import com.airfrance.repind.dto.individu.CommunicationPreferencesDTO;
import com.airfrance.repind.dto.individu.MarketLanguageDTO;
import com.airfrance.repind.dto.individu.SignatureDTO;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.MarketLanguage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;


/**
 * @author e6349052
 */
public class CommunicationPreferencesTransformer {
    private static final Log log = LogFactory.getLog(CommunicationPreferencesTransformer.class);

	/**
	 * 
	 * @param comPrefDTO
	 * @return
	 */
	public static Set<CommunicationPreferencesDTO> individu2prospect(List<com.airfrance.repind.dto.individu.CommunicationPreferencesDTO> listeComPrefDTO)
	{
		Set<CommunicationPreferencesDTO> result = new HashSet<CommunicationPreferencesDTO>();
		if( listeComPrefDTO != null )
		{
			for (com.airfrance.repind.dto.individu.CommunicationPreferencesDTO comPrefDTO : listeComPrefDTO)
			{
				CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
				if(comPrefDTO.getDomain()!=null)
				{
					cp.setDomain( comPrefDTO.getDomain() );
				}
				if(comPrefDTO.getComGroupType()!=null)
				{
					cp.setComGroupType( comPrefDTO.getComGroupType());
				}
				if(comPrefDTO.getComType()!=null)
				{
					cp.setComType( comPrefDTO.getComType());
				}
				if(comPrefDTO.getMedia1()!=null)
				{
					cp.setMedia1( comPrefDTO.getMedia1());
				}
				if(comPrefDTO.getMedia2()!=null)
				{
					cp.setMedia2(comPrefDTO.getMedia2());
				}
				if(comPrefDTO.getMedia3()!=null)
				{
					cp.setMedia3(comPrefDTO.getMedia3());
				}
				if(comPrefDTO.getMedia4()!=null)
				{
					cp.setMedia4(comPrefDTO.getMedia4());
				}
				if(comPrefDTO.getMedia5()!=null)
				{
					cp.setMedia5(comPrefDTO.getMedia5());
				}
				if(comPrefDTO.getCreationDate()!=null)
				{
					cp.setCreationDate(comPrefDTO.getCreationDate());
				}
				if(comPrefDTO.getCreationSignature()!=null)
				{
					cp.setCreationSignature(comPrefDTO.getCreationSignature());
				}
				if(comPrefDTO.getCreationSite()!=null)
				{
					cp.setCreationSite(comPrefDTO.getCreationSite());
				}
				if(comPrefDTO.getDateOptin()!=null)
				{
					cp.setDateOptin(comPrefDTO.getDateOptin());
				}
				if(comPrefDTO.getDateOptinPartners()!=null)
				{
					cp.setDateOptinPartners(comPrefDTO.getDateOptinPartners());
				}
				if(comPrefDTO.getDateOfEntry()!=null)
				{
					cp.setDateOfEntry(comPrefDTO.getDateOfEntry());
				}
				if(comPrefDTO.getModificationDate()!=null)
				{
					cp.setModificationDate(comPrefDTO.getModificationDate());
				}
				if(comPrefDTO.getModificationSignature()!=null)
				{
					cp.setModificationSignature(comPrefDTO.getModificationSignature());
				}
				if(comPrefDTO.getModificationSite()!=null )
				{
					cp.setModificationSite(comPrefDTO.getModificationSite());
				}
				if(comPrefDTO.getSubscribe()!=null)
				{
					cp.setSubscribe(comPrefDTO.getSubscribe());
				}
				if(comPrefDTO.getOptinPartners()!=null)
				{
					cp.setOptinPartners(comPrefDTO.getOptinPartners());
				}
				if(comPrefDTO.getChannel()!=null)
				{
					cp.setChannel(comPrefDTO.getChannel());
				}

				Set<MarketLanguageDTO> listeML = new HashSet<MarketLanguageDTO>();
				if(comPrefDTO.getMarketLanguageDTO() !=null){
					for (com.airfrance.repind.dto.individu.MarketLanguageDTO mlDTO : comPrefDTO.getMarketLanguageDTO() )
					{
						MarketLanguageDTO ml = new MarketLanguageDTO();
						if(mlDTO.getMedia1()!=null)
						{
							ml.setMedia1(mlDTO.getMedia1());
						}
						if(mlDTO.getMedia2()!=null)
						{
							ml.setMedia2(mlDTO.getMedia2());
						}
						if(mlDTO.getMedia3()!=null)
						{
							ml.setMedia3(mlDTO.getMedia3());
						}
						if(mlDTO.getMedia4()!=null)
						{
							ml.setMedia4(mlDTO.getMedia4());
						}
						if(mlDTO.getMedia5()!=null)
						{
							ml.setMedia5(mlDTO.getMedia5());
						}
						if(mlDTO.getCreationDate()!=null)
						{
							ml.setCreationDate(mlDTO.getCreationDate());
						}
						if(mlDTO.getCreationSignature()!=null)
						{
							ml.setCreationSignature(mlDTO.getCreationSignature());
						}
						if(mlDTO.getCreationSite()!=null)
						{
							ml.setCreationSite(mlDTO.getCreationSite());
						}
						if(mlDTO.getDateOfConsent()!=null)
						{
							ml.setDateOfConsent(mlDTO.getDateOfConsent());
						}
						if(mlDTO.getLanguage()!=null)
						{
							ml.setLanguage(mlDTO.getLanguage());
						}
						if(mlDTO.getMarket()!=null)
						{
							ml.setMarket(mlDTO.getMarket());
						}
						if(mlDTO.getModificationDate()!=null)
						{
							ml.setModificationDate(mlDTO.getModificationDate());
						}
						if(mlDTO.getModificationSignature()!=null)
						{
							ml.setModificationSignature(mlDTO.getModificationSignature());
						}
						if(mlDTO.getModificationSite()!=null)
						{
							ml.setModificationSite(mlDTO.getModificationSite());
						}
						if(mlDTO.getOptIn()!=null)
						{
							ml.setOptIn(mlDTO.getOptIn());
						}
						listeML.add(ml);
					}
					cp.setMarketLanguageDTO(listeML);
				}
				result.add(cp);
			}
		}
		return result;
	}
	
	public static List<CommunicationPreferencesDTO> individu2prospectList(List<com.airfrance.repind.dto.individu.CommunicationPreferencesDTO> listeComPrefDTO)
	{
		Set<CommunicationPreferencesDTO> result = new HashSet<CommunicationPreferencesDTO>();
		if( listeComPrefDTO != null )
		{
			for (com.airfrance.repind.dto.individu.CommunicationPreferencesDTO comPrefDTO : listeComPrefDTO)
			{
				CommunicationPreferencesDTO cp = new CommunicationPreferencesDTO();
				if(comPrefDTO.getDomain()!=null)
				{
					cp.setDomain( comPrefDTO.getDomain() );
				}
				if(comPrefDTO.getComGroupType()!=null)
				{
					cp.setComGroupType( comPrefDTO.getComGroupType());
				}
				if(comPrefDTO.getComType()!=null)
				{
					cp.setComType( comPrefDTO.getComType());
				}
				if(comPrefDTO.getMedia1()!=null)
				{
					cp.setMedia1( comPrefDTO.getMedia1());
				}
				if(comPrefDTO.getMedia2()!=null)
				{
					cp.setMedia2(comPrefDTO.getMedia2());
				}
				if(comPrefDTO.getMedia3()!=null)
				{
					cp.setMedia3(comPrefDTO.getMedia3());
				}
				if(comPrefDTO.getMedia4()!=null)
				{
					cp.setMedia4(comPrefDTO.getMedia4());
				}
				if(comPrefDTO.getMedia5()!=null)
				{
					cp.setMedia5(comPrefDTO.getMedia5());
				}
				if(comPrefDTO.getCreationDate()!=null)
				{
					cp.setCreationDate(comPrefDTO.getCreationDate());
				}
				if(comPrefDTO.getCreationSignature()!=null)
				{
					cp.setCreationSignature(comPrefDTO.getCreationSignature());
				}
				if(comPrefDTO.getCreationSite()!=null)
				{
					cp.setCreationSite(comPrefDTO.getCreationSite());
				}
				if(comPrefDTO.getDateOptin()!=null)
				{
					cp.setDateOptin(comPrefDTO.getDateOptin());
				}
				if(comPrefDTO.getDateOptinPartners()!=null)
				{
					cp.setDateOptinPartners(comPrefDTO.getDateOptinPartners());
				}
				if(comPrefDTO.getDateOfEntry()!=null)
				{
					cp.setDateOfEntry(comPrefDTO.getDateOfEntry());
				}
				if(comPrefDTO.getModificationDate()!=null)
				{
					cp.setModificationDate(comPrefDTO.getModificationDate());
				}
				if(comPrefDTO.getModificationSignature()!=null)
				{
					cp.setModificationSignature(comPrefDTO.getModificationSignature());
				}
				if(comPrefDTO.getModificationSite()!=null )
				{
					cp.setModificationSite(comPrefDTO.getModificationSite());
				}
				if(comPrefDTO.getSubscribe()!=null)
				{
					cp.setSubscribe(comPrefDTO.getSubscribe());
				}
				if(comPrefDTO.getOptinPartners()!=null)
				{
					cp.setOptinPartners(comPrefDTO.getOptinPartners());
				}
				if(comPrefDTO.getChannel()!=null)
				{
					cp.setChannel(comPrefDTO.getChannel());
				}

				Set<MarketLanguageDTO> listeML = new HashSet<MarketLanguageDTO>();
				if(comPrefDTO.getMarketLanguageDTO() !=null){
					for (com.airfrance.repind.dto.individu.MarketLanguageDTO mlDTO : comPrefDTO.getMarketLanguageDTO() )
					{
						MarketLanguageDTO ml = new MarketLanguageDTO();
						if(mlDTO.getMedia1()!=null)
						{
							ml.setMedia1(mlDTO.getMedia1());
						}
						if(mlDTO.getMedia2()!=null)
						{
							ml.setMedia2(mlDTO.getMedia2());
						}
						if(mlDTO.getMedia3()!=null)
						{
							ml.setMedia3(mlDTO.getMedia3());
						}
						if(mlDTO.getMedia4()!=null)
						{
							ml.setMedia4(mlDTO.getMedia4());
						}
						if(mlDTO.getMedia5()!=null)
						{
							ml.setMedia5(mlDTO.getMedia5());
						}
						if(mlDTO.getCreationDate()!=null)
						{
							ml.setCreationDate(mlDTO.getCreationDate());
						}
						if(mlDTO.getCreationSignature()!=null)
						{
							ml.setCreationSignature(mlDTO.getCreationSignature());
						}
						if(mlDTO.getCreationSite()!=null)
						{
							ml.setCreationSite(mlDTO.getCreationSite());
						}
						if(mlDTO.getDateOfConsent()!=null)
						{
							ml.setDateOfConsent(mlDTO.getDateOfConsent());
						}
						if(mlDTO.getLanguage()!=null)
						{
							ml.setLanguage(mlDTO.getLanguage());
						}
						if(mlDTO.getMarket()!=null)
						{
							ml.setMarket(mlDTO.getMarket());
						}
						if(mlDTO.getModificationDate()!=null)
						{
							ml.setModificationDate(mlDTO.getModificationDate());
						}
						if(mlDTO.getModificationSignature()!=null)
						{
							ml.setModificationSignature(mlDTO.getModificationSignature());
						}
						if(mlDTO.getModificationSite()!=null)
						{
							ml.setModificationSite(mlDTO.getModificationSite());
						}
						if(mlDTO.getOptIn()!=null)
						{
							ml.setOptIn(mlDTO.getOptIn());
						}
						listeML.add(ml);
					}
					cp.setMarketLanguageDTO(listeML);
				}
				result.add(cp);
			}
		}
		
		return new ArrayList<CommunicationPreferencesDTO>(result);
	}
	
	public static List<com.airfrance.repind.dto.individu.CommunicationPreferencesDTO> prospect2individu(Set<CommunicationPreferencesDTO> listeComPrefDTO)
	{
		List<com.airfrance.repind.dto.individu.CommunicationPreferencesDTO> result = new ArrayList<com.airfrance.repind.dto.individu.CommunicationPreferencesDTO>();
		for (CommunicationPreferencesDTO comPrefDTO : listeComPrefDTO)
		{
			com.airfrance.repind.dto.individu.CommunicationPreferencesDTO cp = new com.airfrance.repind.dto.individu.CommunicationPreferencesDTO();
			if(comPrefDTO.getDomain()!=null)
			{
				cp.setDomain( comPrefDTO.getDomain() );
			}
			if(comPrefDTO.getComGroupType()!=null)
			{
				cp.setComGroupType( comPrefDTO.getComGroupType());
			}
			if(comPrefDTO.getComType()!=null)
			{
				cp.setComType( comPrefDTO.getComType());
			}
			if(comPrefDTO.getMedia1()!=null)
			{
				cp.setMedia1( comPrefDTO.getMedia1());
			}
			if(comPrefDTO.getMedia2()!=null)
			{
				cp.setMedia2(comPrefDTO.getMedia2());
			}
			if(comPrefDTO.getMedia3()!=null)
			{
				cp.setMedia3(comPrefDTO.getMedia3());
			}
			if(comPrefDTO.getMedia4()!=null)
			{
				cp.setMedia4(comPrefDTO.getMedia4());
			}
			if(comPrefDTO.getMedia5()!=null)
			{
				cp.setMedia5(comPrefDTO.getMedia5());
			}
			if(comPrefDTO.getCreationDate()!=null)
			{
				cp.setCreationDate(comPrefDTO.getCreationDate());
			}
			if(comPrefDTO.getCreationSignature()!=null)
			{
				cp.setCreationSignature(comPrefDTO.getCreationSignature());
			}
			if(comPrefDTO.getCreationSite()!=null)
			{
				cp.setCreationSite(comPrefDTO.getCreationSite());
			}
			if(comPrefDTO.getDateOptin()!=null)
			{
				cp.setDateOptin(comPrefDTO.getDateOptin());
			}
			if(comPrefDTO.getDateOptinPartners()!=null)
			{
				cp.setDateOptinPartners(comPrefDTO.getDateOptinPartners());
			}
			if(comPrefDTO.getDateOfEntry()!=null)
			{
				cp.setDateOfEntry(comPrefDTO.getDateOfEntry());
			}
			if(comPrefDTO.getModificationDate()!=null)
			{
				cp.setModificationDate(comPrefDTO.getModificationDate());
			}
			if(comPrefDTO.getModificationSignature()!=null)
			{
				cp.setModificationSignature(comPrefDTO.getModificationSignature());
			}
			if(comPrefDTO.getModificationSite()!=null)
			{
				cp.setModificationSite(comPrefDTO.getModificationSite());
			}
			if(comPrefDTO.getSubscribe()!=null)
			{
				cp.setSubscribe(comPrefDTO.getSubscribe());
			}
			if(comPrefDTO.getOptinPartners()!=null)
			{
				cp.setOptinPartners(comPrefDTO.getOptinPartners());
			}
			if(comPrefDTO.getChannel()!=null)
			{
				cp.setChannel(comPrefDTO.getChannel());
			}
			
			Set<com.airfrance.repind.dto.individu.MarketLanguageDTO> listeML = new HashSet<com.airfrance.repind.dto.individu.MarketLanguageDTO>();
			if(comPrefDTO.getMarketLanguageDTO()!=null){
				for (MarketLanguageDTO mlDTO : comPrefDTO.getMarketLanguageDTO() )
				{
					com.airfrance.repind.dto.individu.MarketLanguageDTO ml = new com.airfrance.repind.dto.individu.MarketLanguageDTO();
					if(mlDTO.getMedia1()!=null)
					{
						ml.setMedia1(mlDTO.getMedia1());
					}
					if(mlDTO.getMedia2()!=null)
					{
						ml.setMedia2(mlDTO.getMedia2());
					}
					if(mlDTO.getMedia3()!=null)
					{
						ml.setMedia3(mlDTO.getMedia3());
					}
					if(mlDTO.getMedia4()!=null)
					{
						ml.setMedia4(mlDTO.getMedia4());
					}
					if(mlDTO.getMedia5()!=null)
					{
						ml.setMedia5(mlDTO.getMedia5());
					}
					if(mlDTO.getCreationDate()!=null)
					{
						ml.setCreationDate(mlDTO.getCreationDate());
					}
					if(mlDTO.getCreationSignature()!=null)
					{
						ml.setCreationSignature(mlDTO.getCreationSignature());
					}
					if(mlDTO.getCreationSite()!=null)
					{
						ml.setCreationSite(mlDTO.getCreationSite());
					}
					if(mlDTO.getDateOfConsent()!=null)
					{
						ml.setDateOfConsent(mlDTO.getDateOfConsent());
					}
					if(mlDTO.getLanguage()!=null)
					{
						ml.setLanguage(mlDTO.getLanguage());
					}
					if(mlDTO.getMarket()!=null)
					{
						ml.setMarket(mlDTO.getMarket());
					}
					if(mlDTO.getModificationDate()!=null)
					{
						ml.setModificationDate(mlDTO.getModificationDate());
					}
					if(mlDTO.getModificationSignature()!=null)
					{
						ml.setModificationSignature(mlDTO.getModificationSignature());
					}
					if(mlDTO.getModificationSite()!=null)
					{
						ml.setModificationSite(mlDTO.getModificationSite());
					}
					if(mlDTO.getOptIn()!=null)
					{
						ml.setOptIn(mlDTO.getOptIn());
					}
					
					listeML.add(ml);
				}
				cp.setMarketLanguageDTO(listeML);
			}
			result.add(cp);
		}
		return result;
	}

	private static final void compareAndUpdateMarketLanguage(Set<MarketLanguage> marketLanguagesOrigin,Set<MarketLanguage> marketLanguagesUpdate,SignatureDTO signature, String domain) throws InvalidParameterException{
		if(marketLanguagesOrigin == null || marketLanguagesUpdate == null){
			throw new InvalidParameterException("Market language set are null");
		}
		if(signature == null){
			throw new InvalidParameterException("Signature is null");
		}
		
		for(MarketLanguage marketLanguageUpdate: marketLanguagesUpdate){
			boolean needInsertMarketLanguage = true;
			
			//Update Market language
			for(MarketLanguage marketLanguageOrigin: marketLanguagesOrigin){
				
				if(
					!StringUtils.equalsIgnoreCase(marketLanguageOrigin.getLanguage(), marketLanguageUpdate.getLanguage()) ||
					!StringUtils.equalsIgnoreCase(marketLanguageOrigin.getMarket(), marketLanguageUpdate.getMarket())
				){ 
					continue;
				}
				needInsertMarketLanguage = false;
				mlBo2MlBo(marketLanguageOrigin,marketLanguageUpdate);
				addCreationInformationsSignatureForMlBo(marketLanguageOrigin,signature);
			}
			
			//Insert Market language
			if(needInsertMarketLanguage){
				if (CommunicationPreferencesConstantValues.DOMAIN_F.equalsIgnoreCase(domain)) {
					marketLanguagesOrigin.clear();
				}
				marketLanguagesOrigin.add(marketLanguageUpdate);
			}	
		}
	}
	
	public static final void compareAndUpdate(Set<CommunicationPreferences> comPrefsOrigin,Set<CommunicationPreferences> comPrefsUpdate,SignatureDTO signature) throws InvalidParameterException{
		
		if(comPrefsOrigin == null || comPrefsUpdate == null){
			throw new InvalidParameterException("Communication preferences set are null");
		}
		if(signature == null){
			throw new InvalidParameterException("Signature is null");
		}
					
		for(CommunicationPreferences comPrefUpdate : comPrefsUpdate){
			boolean needInsertComPref = true;
			
			if(comPrefUpdate.getDomain()==null){
				comPrefUpdate.setDomain("S");
			}
			
			//Update comPref
			for(CommunicationPreferences comPrefOrigin :  comPrefsOrigin){
					
				if(comPrefOrigin.getDomain()==null){
					comPrefOrigin.setDomain("S");
				}
				if(
					!StringUtils.equalsIgnoreCase(comPrefOrigin.getComGroupType(), comPrefUpdate.getComGroupType()) ||
					!StringUtils.equalsIgnoreCase(comPrefOrigin.getComType(), comPrefUpdate.getComType()) ||
					!StringUtils.equalsIgnoreCase(comPrefOrigin.getDomain(), comPrefUpdate.getDomain())
				){ 
					continue;
				}
				needInsertComPref = false;
				compareAndUpdateMarketLanguage(comPrefOrigin.getMarketLanguage(), comPrefUpdate.getMarketLanguage(), signature, comPrefOrigin.getDomain());
				cpBo2CpBo(comPrefOrigin,comPrefUpdate);

			}
			
			//Create comPref
			if(needInsertComPref){
				comPrefsOrigin.add(comPrefUpdate);
			}	
		}
    }
    
    /**
     * 
     * @param cp1
     * @param cp2
     */
    public static final void cpBo2CpBo(CommunicationPreferences cp1, CommunicationPreferences cp2){
    	if(cp2.getSubscribe()!=null)
    		cp1.setSubscribe(cp2.getSubscribe());
    	if(cp2.getDateOptin()!=null)
    		cp1.setDateOptin(cp2.getDateOptin());
    	
    	if(cp2.getOptinPartners()!=null)
    		cp1.setOptinPartners(cp2.getOptinPartners());
    	if(cp2.getDateOptinPartners()!=null)
    		cp1.setDateOptinPartners(cp2.getDateOptinPartners());
    	
    	if(cp2.getChannel()!=null)
    		cp1.setChannel(cp2.getChannel());
    	
    	if(cp2.getDateOfEntry()!=null)
    		cp1.setDateOfEntry(cp2.getDateOfEntry());
    	
    	if(cp2.getMedia1()!=null)
    		cp1.setMedia1(cp2.getMedia1());
    	else
    		cp1.setMedia1(null);
    	if(cp2.getMedia2()!=null)
    		cp1.setMedia2(cp2.getMedia2());
    	else
    		cp1.setMedia2(null);
    	if(cp2.getMedia3()!=null)
    		cp1.setMedia3(cp2.getMedia3());
    	else
    		cp1.setMedia3(null);
    	if(cp2.getMedia4()!=null)
    		cp1.setMedia4(cp2.getMedia4());
    	else
    		cp1.setMedia4(null);
    	if(cp2.getMedia5()!=null)
    		cp1.setMedia5(cp2.getMedia5());
    	else
    		cp1.setMedia5(null);
    	if(cp2.getModificationDate()!=null)
    		cp1.setModificationDate(cp2.getModificationDate());
    	else
    		cp1.setModificationDate(null);
    	if(cp2.getModificationSite()!=null)
    		cp1.setModificationSite(cp2.getModificationSite());
    	else
    		cp1.setModificationSite(null);
    	if(cp2.getModificationSignature()!=null)
    		cp1.setModificationSignature(cp2.getModificationSignature());
    	else
    		cp1.setModificationSignature(null);
    	
    }
    
    /**
     * 
     * @param ml1
     * @param ml2
     */
    public static final void mlBo2MlBo(MarketLanguage ml1, MarketLanguage ml2){
    	if(ml2.getOptIn()!=null)
    		ml1.setOptIn(ml2.getOptIn());
    	
    	if(ml2.getDateOfConsent()!=null)
    		ml1.setDateOfConsent(ml2.getDateOfConsent());
    		
    	if(ml2.getCommunicationMedia1()!=null)
    		ml1.setCommunicationMedia1(ml2.getCommunicationMedia1());
    	else
    		ml1.setCommunicationMedia1(null);
    	if(ml2.getCommunicationMedia2()!=null)
    		ml1.setCommunicationMedia2(ml2.getCommunicationMedia2());
    	else
    		ml1.setCommunicationMedia2(null);
    	if(ml2.getCommunicationMedia3()!=null)
    		ml1.setCommunicationMedia3(ml2.getCommunicationMedia3());
    	else
    		ml1.setCommunicationMedia3(null);
    	if(ml2.getCommunicationMedia4()!=null)
    		ml1.setCommunicationMedia4(ml2.getCommunicationMedia4());
    	else
    		ml1.setCommunicationMedia4(null);
    	if(ml2.getCommunicationMedia5()!=null)
    		ml1.setCommunicationMedia5(ml2.getCommunicationMedia5());
    	else
    		ml1.setCommunicationMedia5(null);
    	if(ml2.getModificationDate()!=null)
    		ml1.setModificationDate(ml2.getModificationDate());
    	else
    		ml1.setModificationDate(null);
    	if(ml2.getModificationSite()!=null)
    		ml1.setModificationSite(ml2.getModificationSite());
    	else
    		ml1.setModificationSite(null);
    	if(ml2.getModificationSignature()!=null)
    		ml1.setModificationSignature(ml2.getModificationSignature());
    	else
    		ml1.setModificationSignature(null);
    }
    
    /**
     * 
     * @param comPref
     * @param signature
     */
    public static void addModificationInformationsSignatureForDto(List<com.airfrance.repind.dto.individu.CommunicationPreferencesDTO> comPref,SignatureDTO signature){
    	if(comPref!=null){
	    	for(com.airfrance.repind.dto.individu.CommunicationPreferencesDTO cp : comPref){
	    		if(cp.getMarketLanguageDTO() !=null){
		    		for(com.airfrance.repind.dto.individu.MarketLanguageDTO ml: cp.getMarketLanguageDTO()){
		    			ml.setModificationDate(new Date());
		    			ml.setModificationSite(signature.getSite());
		            	ml.setModificationSignature(signature.getSignature());
		            	if(ml.getCreationDate()==null)ml.setCreationDate(new Date());
		            	if(ml.getCreationSite()==null)ml.setCreationSite(signature.getSite());
		            	if(ml.getCreationSignature()==null)ml.setCreationSignature(signature.getSignature());
		    		}
	    		}
	        	cp.setModificationDate(new Date());
	        	cp.setModificationSite(signature.getSite());
	        	cp.setModificationSignature(signature.getSignature());
	        	if(cp.getCreationDate()==null)cp.setCreationDate(new Date());
	        	if(cp.getCreationSite()==null)cp.setCreationSite(signature.getSite());
	        	if(cp.getCreationSignature()==null)cp.setCreationSignature(signature.getSignature());
	    	}
    	}
    }
    
    public static void addCreationInformationsSignatureForDto(List<com.airfrance.repind.dto.individu.CommunicationPreferencesDTO> comPref,SignatureDTO signature){
    	if(comPref!=null && !comPref.isEmpty()){
	    	for(com.airfrance.repind.dto.individu.CommunicationPreferencesDTO cp : comPref){
	    		if(cp.getMarketLanguageDTO() !=null && !cp.getMarketLanguageDTO().isEmpty()){
		    		for(com.airfrance.repind.dto.individu.MarketLanguageDTO ml: cp.getMarketLanguageDTO()){
		    			ml.setCreationDate(new Date());
		    			ml.setModificationDate(new Date());
		    			ml.setCreationSite(signature.getSite());
		    			ml.setModificationSite(signature.getSite());
		            	ml.setCreationSignature(signature.getSignature());
		            	ml.setModificationSignature(signature.getSignature());
		    		}
	    		}
	        	cp.setCreationDate(new Date());
	        	cp.setModificationDate(new Date());
	        	cp.setCreationSite(signature.getSite());
	        	cp.setModificationSite(signature.getSite());
	        	cp.setCreationSignature(signature.getSignature());
	        	cp.setModificationSignature(signature.getSignature());
    	}
        	
    	}
    	
    }
    
    public static void addCreationInformationsSignatureForMlBo(com.airfrance.repind.entity.individu.MarketLanguage ml,SignatureDTO signature){
    	if(ml.getCreationDate()==null){
    		ml.setCreationDate(new Date());
    		ml.setModificationDate(new Date());
    	}
	    if(ml.getCreationSite()==null){
	    	ml.setCreationSite(signature.getSite());
	    	ml.setModificationSite(signature.getSite());
	    }
	    if(ml.getCreationSignature()==null){
	    	ml.setCreationSignature(signature.getSignature());
	    	ml.setModificationSignature(signature.getSignature());
	    }
    }
    
    
    
    
    
    
    
    
}
