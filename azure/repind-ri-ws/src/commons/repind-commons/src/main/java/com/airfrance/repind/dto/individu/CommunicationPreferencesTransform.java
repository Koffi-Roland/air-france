package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_rVOfAEDiEeCSW-5bkMJFig i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.individu.CommunicationPreferences;
import com.airfrance.repind.entity.individu.MarketLanguage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*PROTECTED REGION END*/

/**
 * <p>Title : CommunicationPreferencesTransform.java</p>
 * transformation bo <-> dto pour un(e) CommunicationPreferences
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class CommunicationPreferencesTransform {

    /*PROTECTED REGION ID(_rVOfAEDiEeCSW-5bkMJFig u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * Constructeur prive
     */
    private CommunicationPreferencesTransform() {
    }
    /**
     * dto -> bo pour une CommunicationPreferences
     * @param communicationPreferencesDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static CommunicationPreferences dto2BoLight(CommunicationPreferencesDTO communicationPreferencesDTO) throws JrafDomainException {
        // instanciation du BO
        CommunicationPreferences communicationPreferences = new CommunicationPreferences();
        dto2BoLight(communicationPreferencesDTO, communicationPreferences);

        // on retourne le BO
        return communicationPreferences;
    }

    /**
     * dto -> bo pour une CommunicationPreferences from Prospect
     * @param communicationPreferencesDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
/*    
    public static CommunicationPreferences dto2BoLight(ProspectCommunicationPreferencesDTO communicationPreferencesDTO) throws JrafDomainException {
        // instanciation du BO
        CommunicationPreferences communicationPreferences = new CommunicationPreferences();
        dto2BoLight(communicationPreferencesDTO, communicationPreferences);

        // on retourne le BO
        return communicationPreferences;
    }
*/
    /**
     * dto -> bo pour une communicationPreferences
     * @param communicationPreferencesDTO dto
     * @param communicationPreferences bo
     */
    public static void dto2BoLight(CommunicationPreferencesDTO communicationPreferencesDTO, CommunicationPreferences communicationPreferences) {
        // property of CommunicationPreferencesDTO
        communicationPreferences.setAccountIdentifier(communicationPreferencesDTO.getAccountIdentifier());
        communicationPreferences.setComPrefId(communicationPreferencesDTO.getComPrefId());
        communicationPreferences.setDomain(communicationPreferencesDTO.getDomain());
        communicationPreferences.setGin(communicationPreferencesDTO.getGin());
        communicationPreferences.setDateOptin(communicationPreferencesDTO.getDateOptin());
        communicationPreferences.setDateOptinPartners(communicationPreferencesDTO.getDateOptinPartners());
        communicationPreferences.setDateOfEntry(communicationPreferencesDTO.getDateOfEntry());
        communicationPreferences.setCreationDate(communicationPreferencesDTO.getCreationDate());
        communicationPreferences.setModificationDate(communicationPreferencesDTO.getModificationDate());
        communicationPreferences.setModificationSignature(communicationPreferencesDTO.getModificationSignature());
        communicationPreferences.setModificationSite(communicationPreferencesDTO.getModificationSite());
        communicationPreferences.setOptinPartners(communicationPreferencesDTO.getOptinPartners());
        communicationPreferences.setCreationSignature(communicationPreferencesDTO.getCreationSignature());
        communicationPreferences.setCreationSite(communicationPreferencesDTO.getCreationSite());
        communicationPreferences.setChannel(communicationPreferencesDTO.getChannel());
        communicationPreferences.setComGroupType(communicationPreferencesDTO.getComGroupType());
        communicationPreferences.setComType(communicationPreferencesDTO.getComType());
        communicationPreferences.setSubscribe(communicationPreferencesDTO.getSubscribe());
        communicationPreferences.setMedia1(communicationPreferencesDTO.getMedia1());
        communicationPreferences.setMedia2(communicationPreferencesDTO.getMedia2());
        communicationPreferences.setMedia3(communicationPreferencesDTO.getMedia3());
        communicationPreferences.setMedia4(communicationPreferencesDTO.getMedia4());
        communicationPreferences.setMedia5(communicationPreferencesDTO.getMedia5());
    }

    /**
     * dto -> bo pour une communicationPreferences from Prospect
     * @param communicationPreferencesDTO dto
     * @param communicationPreferences bo
     */
/*    
    public static void dto2BoLight(ProspectCommunicationPreferencesDTO prospectCommunicationPreferencesDTO, CommunicationPreferences communicationPreferences) {
        // property of CommunicationPreferencesDTO
        communicationPreferences.setComPrefId(prospectCommunicationPreferencesDTO.getComPrefId());
        communicationPreferences.setDomain(prospectCommunicationPreferencesDTO.getDomain());
        communicationPreferences.setGin(prospectCommunicationPreferencesDTO.getGin().toString());
        communicationPreferences.setDateOptin(prospectCommunicationPreferencesDTO.getDateOfConsent());
        communicationPreferences.setDateOptinPartners(prospectCommunicationPreferencesDTO.getDateOfConsentPartners());
        communicationPreferences.setDateOfEntry(prospectCommunicationPreferencesDTO.getDateOfEntry());
        communicationPreferences.setCreationDate(prospectCommunicationPreferencesDTO.getCreationDate());
        communicationPreferences.setModificationDate(prospectCommunicationPreferencesDTO.getModificationDate());
        communicationPreferences.setModificationSignature(prospectCommunicationPreferencesDTO.getModificationSignature());
        communicationPreferences.setModificationSite(prospectCommunicationPreferencesDTO.getModificationSite());
        communicationPreferences.setOptinPartners(prospectCommunicationPreferencesDTO.getOptinPartners());
        communicationPreferences.setCreationSignature(prospectCommunicationPreferencesDTO.getCreationSignature());
        communicationPreferences.setCreationSite(prospectCommunicationPreferencesDTO.getCreationSite());
        // TODO : Absence du champ Channel
        //        communicationPreferences.setChannel(prospectCommunicationPreferencesDTO.get);
        communicationPreferences.setComGroupType(prospectCommunicationPreferencesDTO.getCommunicationGroupType());
        communicationPreferences.setComType(prospectCommunicationPreferencesDTO.getCommunicationType());
        communicationPreferences.setSubscribe(prospectCommunicationPreferencesDTO.getOptIn());
        communicationPreferences.setMedia1(prospectCommunicationPreferencesDTO.getCommunicationMedia1());
        communicationPreferences.setMedia2(prospectCommunicationPreferencesDTO.getCommunicationMedia2());
        communicationPreferences.setMedia3(prospectCommunicationPreferencesDTO.getCommunicationMedia3());
        communicationPreferences.setMedia4(prospectCommunicationPreferencesDTO.getCommunicationMedia4());
        communicationPreferences.setMedia5(prospectCommunicationPreferencesDTO.getCommunicationMedia5());
    }
*/    
    /**
     * bo -> dto pour une communicationPreferences
     * @param pCommunicationPreferences bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static CommunicationPreferencesDTO bo2DtoLight(CommunicationPreferences pCommunicationPreferences) throws JrafDomainException {
        // instanciation du DTO
        CommunicationPreferencesDTO communicationPreferencesDTO = new CommunicationPreferencesDTO();
        bo2DtoLight(pCommunicationPreferences, communicationPreferencesDTO);
        // on retourne le dto
        return communicationPreferencesDTO;
    }


    /**
     * Transforme un business object en DTO.
     * Methode dite light.
     * @param communicationPreferences bo
     * @param communicationPreferencesDTO dto
     */
    public static void bo2DtoLight(
        CommunicationPreferences communicationPreferences,
        CommunicationPreferencesDTO communicationPreferencesDTO) {


        // simple properties
        communicationPreferencesDTO.setAccountIdentifier(communicationPreferences.getAccountIdentifier());
        communicationPreferencesDTO.setComPrefId(communicationPreferences.getComPrefId());
        communicationPreferencesDTO.setDomain(communicationPreferences.getDomain());
        communicationPreferencesDTO.setGin(communicationPreferences.getGin());
        communicationPreferencesDTO.setDateOptin(communicationPreferences.getDateOptin());
        communicationPreferencesDTO.setDateOptinPartners(communicationPreferences.getDateOptinPartners());
        communicationPreferencesDTO.setDateOfEntry(communicationPreferences.getDateOfEntry());
        communicationPreferencesDTO.setCreationDate(communicationPreferences.getCreationDate());
        communicationPreferencesDTO.setModificationDate(communicationPreferences.getModificationDate());
        communicationPreferencesDTO.setModificationSignature(communicationPreferences.getModificationSignature());
        communicationPreferencesDTO.setModificationSite(communicationPreferences.getModificationSite());
        communicationPreferencesDTO.setOptinPartners(communicationPreferences.getOptinPartners());
        communicationPreferencesDTO.setCreationSignature(communicationPreferences.getCreationSignature());
        communicationPreferencesDTO.setCreationSite(communicationPreferences.getCreationSite());
        communicationPreferencesDTO.setChannel(communicationPreferences.getChannel());
        communicationPreferencesDTO.setComGroupType(communicationPreferences.getComGroupType());
        communicationPreferencesDTO.setComType(communicationPreferences.getComType());
        communicationPreferencesDTO.setSubscribe(communicationPreferences.getSubscribe());
        communicationPreferencesDTO.setMedia1(communicationPreferences.getMedia1());
        communicationPreferencesDTO.setMedia2(communicationPreferences.getMedia2());
        communicationPreferencesDTO.setMedia3(communicationPreferences.getMedia3());
        communicationPreferencesDTO.setMedia4(communicationPreferences.getMedia4());
        communicationPreferencesDTO.setMedia5(communicationPreferences.getMedia5());


    }
    
    /*PROTECTED REGION ID(_rVOfAEDiEeCSW-5bkMJFig u m - Tr) ENABLED START*/
    /**
     * @param listCommunicationPreferences
     * @return
     * @throws JrafDomainException
     */
    public static List<CommunicationPreferencesDTO> bo2Dto(List<CommunicationPreferences> listCommunicationPreferences) throws JrafDomainException {
    	if(listCommunicationPreferences != null) {
    		List<CommunicationPreferencesDTO> listCommunicationPreferencesDTO = new ArrayList<CommunicationPreferencesDTO>();
    		for(CommunicationPreferences communicationPreferences : listCommunicationPreferences) {
    			CommunicationPreferencesDTO comPrefDto = bo2DtoLight(communicationPreferences);
    			if(communicationPreferences.getMarketLanguage()!=null){
    				Set<MarketLanguageDTO> listMlDto = new HashSet<MarketLanguageDTO>();
    				for(MarketLanguage ml:communicationPreferences.getMarketLanguage()){
    					listMlDto.add(MarketLanguageTransform.bo2Dto(ml));
    				}
    				comPrefDto.setMarketLanguageDTO(listMlDto);
    			}
    			listCommunicationPreferencesDTO.add(comPrefDto);
    		}
    		return listCommunicationPreferencesDTO;
    	} else {
    		return null;
    	}
    }
    
    /**
     * @param listCommunicationPreferences
     * @return
     * @throws JrafDomainException
     */
    public static List<CommunicationPreferencesDTO> bo2Dto(Set<CommunicationPreferences> listCommunicationPreferences) throws JrafDomainException {
    	
    	if (listCommunicationPreferences == null) {
    		return null;
    	}
    
        return bo2Dto(new ArrayList<CommunicationPreferences>(listCommunicationPreferences));
    }

    /**
     * @param listCommunicationPreferencesDTO
     * @return
     * @throws JrafDomainException
     */
    public static Set<CommunicationPreferences> dto2Bo(List<CommunicationPreferencesDTO> listCommunicationPreferencesDTO) throws JrafDomainException {
    	if(listCommunicationPreferencesDTO != null) {
    		Set<CommunicationPreferences> listCommunicationPreferences = new HashSet<CommunicationPreferences>();
    		for(CommunicationPreferencesDTO communicationPreferencesDTO : listCommunicationPreferencesDTO) {
    			CommunicationPreferences comPrefBo = dto2BoLight(communicationPreferencesDTO);
    			if(communicationPreferencesDTO.getMarketLanguageDTO()!=null){
    				Set<MarketLanguage> listMl = new HashSet<MarketLanguage>();
    				for(MarketLanguageDTO mlDto: communicationPreferencesDTO.getMarketLanguageDTO()){
    					//listMl.add(MarketLanguageTransform.dto2Bo(mlDto));
    					//Need to set the comPrefId manually because the DTO does not contains this field
    					//In case of Merge without the comPrefId, we lose the marketLanguage
    					MarketLanguage ml = MarketLanguageTransform.dto2Bo(mlDto);
    					ml.setComPrefId(comPrefBo.getComPrefId());
    					listMl.add(ml);
    				}
    				comPrefBo.setMarketLanguage(listMl);
    			}
    			listCommunicationPreferences.add(comPrefBo);
    		}
    		return listCommunicationPreferences;
    	} else {
    		return null;
    	}
    }

    /**
     * @param listCommunicationPreferencesDTO from Prospect
     * @return
     * @throws JrafDomainException
     */
/*    
    public static Set<CommunicationPreferences> dto2Bo(Set<ProspectCommunicationPreferencesDTO> listProspectCommunicationPreferencesDTO) throws JrafDomainException {
    	if(listProspectCommunicationPreferencesDTO != null) {
    		Set<CommunicationPreferences> listCommunicationPreferences = new HashSet<CommunicationPreferences>();
    		for(ProspectCommunicationPreferencesDTO communicationPreferencesDTO : listProspectCommunicationPreferencesDTO) {
    			CommunicationPreferences comPrefBo = dto2BoLight(communicationPreferencesDTO);
    			if(communicationPreferencesDTO.getMarketLanguageDTO()!=null){
    				Set<MarketLanguage> listMl = new HashSet<MarketLanguage>();
    				for(ProspectMarketLanguageDTO mlDto: communicationPreferencesDTO.getMarketLanguageDTO()){
    					listMl.add(MarketLanguageTransform.dto2Bo(mlDto));
    				}
    				comPrefBo.setMarketLanguage(listMl);
    			}
    			listCommunicationPreferences.add(comPrefBo);
    		}
    		return listCommunicationPreferences;
    	} else {
    		return null;
    	}
    }
*/    
    /*
    * @param CommunicationPreferencesDTO
    * @return
    * @throws JrafDomainException
    */
   public static CommunicationPreferences dto2Bo2(CommunicationPreferencesDTO communicationPreferencesDTO) throws JrafDomainException {
	   List<CommunicationPreferencesDTO> cps= new ArrayList<CommunicationPreferencesDTO>();
	   cps.add(communicationPreferencesDTO);
	   return dto2Bo(cps).iterator().next();
   }
    
   /*
    * @param CommunicationPreferencesDTO
    * @return
    * @throws JrafDomainException
    */
   public static CommunicationPreferencesDTO bo2Dto2(CommunicationPreferences communicationPreferences) throws JrafDomainException {
	   List<CommunicationPreferences> cps= new ArrayList<CommunicationPreferences>();
	   cps.add(communicationPreferences);
	   return bo2Dto(cps).get(0);
   }
   
   public static List<CommunicationPreferencesDTO> addLinkToIndividual(List<CommunicationPreferencesDTO> communicationPreferences, String gin) throws JrafDomainException {
	   
	   if (communicationPreferences != null && !communicationPreferences.isEmpty()) {
		   for (CommunicationPreferencesDTO communicationPreferencesDTO : communicationPreferences) {
			   communicationPreferencesDTO.setGin(gin);
		   }
	   }
	   return communicationPreferences;
   }
   
    /*PROTECTED REGION END*/
}

