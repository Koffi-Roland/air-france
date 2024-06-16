package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_pbbOsJw_EeKZIdxk1nMK9w i - Tr) ENABLED START*/

// add not generated imports here

import com.airfrance.ref.exception.jraf.JrafDomainException;
import com.airfrance.repind.entity.individu.MarketLanguage;

/*PROTECTED REGION END*/

/**
 * <p>Title : MarketLanguageTransform.java</p>
 * transformation bo <-> dto pour un(e) MarketLanguage
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public final class MarketLanguageTransform {

    /*PROTECTED REGION ID(_pbbOsJw_EeKZIdxk1nMK9w u var - Tr) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/
    
    /**
     * Constructeur prive
     */
    private MarketLanguageTransform() {
    }
    /**
     * dto -> bo pour une MarketLanguage
     * @param marketLanguageDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
    public static MarketLanguage dto2BoLight(MarketLanguageDTO marketLanguageDTO) throws JrafDomainException {
        // instanciation du BO
        MarketLanguage marketLanguage = new MarketLanguage();
        dto2BoLight(marketLanguageDTO, marketLanguage);

        // on retourne le BO
        return marketLanguage;
    }

    /**
     * dto -> bo pour une MarketLanguage from Prospect
     * @param marketLanguageDTO dto
     * @return bo
     * @throws JrafDomainException if the DTO type is not supported
     */
/*    
    public static MarketLanguage dto2BoLight(ProspectMarketLanguageDTO prospectMarketLanguageDTO) throws JrafDomainException {
        // instanciation du BO
        MarketLanguage marketLanguage = new MarketLanguage();
        dto2BoLight(prospectMarketLanguageDTO, marketLanguage);

        // on retourne le BO
        return marketLanguage;
    }
*/
    /**
     * dto -> bo pour une marketLanguage
     * @param marketLanguageDTO dto
     * @param marketLanguage bo
     */
    public static void dto2BoLight(MarketLanguageDTO marketLanguageDTO, MarketLanguage marketLanguage) {
        // property of MarketLanguageDTO
        marketLanguage.setMarket(marketLanguageDTO.getMarket());
        marketLanguage.setMarketLanguageId(marketLanguageDTO.getMarketLanguageId());
        marketLanguage.setLanguage(marketLanguageDTO.getLanguage());
        marketLanguage.setOptIn(marketLanguageDTO.getOptIn());
        marketLanguage.setDateOfConsent(marketLanguageDTO.getDateOfConsent());
        marketLanguage.setCreationDate(marketLanguageDTO.getCreationDate());
        marketLanguage.setCreationSignature(marketLanguageDTO.getCreationSignature());
        marketLanguage.setCreationSite(marketLanguageDTO.getCreationSite());
        marketLanguage.setModificationDate(marketLanguageDTO.getModificationDate());
        marketLanguage.setModificationSignature(marketLanguageDTO.getModificationSignature());
        marketLanguage.setModificationSite(marketLanguageDTO.getModificationSite());
    }

    /**
     * dto -> bo pour une marketLanguage from Prospect
     * @param marketLanguageDTO dto
     * @param marketLanguage bo
     */
/*    
    public static void dto2BoLight(ProspectMarketLanguageDTO prospectMarketLanguageDTO, MarketLanguage marketLanguage) {
        // property of MarketLanguageDTO
        marketLanguage.setMarket(prospectMarketLanguageDTO.getMarket());
        marketLanguage.setMarketLanguageId(prospectMarketLanguageDTO.getMarketLanguageId());
        marketLanguage.setLanguage(prospectMarketLanguageDTO.getLanguage());
        marketLanguage.setOptIn(prospectMarketLanguageDTO.getOptIn());
        marketLanguage.setDateOfConsent(prospectMarketLanguageDTO.getDateOfConsent());
        marketLanguage.setCreationDate(prospectMarketLanguageDTO.getCreationDate());
        marketLanguage.setCreationSignature(prospectMarketLanguageDTO.getCreationSignature());
        marketLanguage.setCreationSite(prospectMarketLanguageDTO.getCreationSite());
        marketLanguage.setModificationDate(prospectMarketLanguageDTO.getModificationDate());
        marketLanguage.setModificationSignature(prospectMarketLanguageDTO.getModificationSignature());
        marketLanguage.setModificationSite(prospectMarketLanguageDTO.getModificationSite());
    }
  */  
    /**
     * bo -> dto pour une marketLanguage
     * @param pMarketLanguage bo
     * @throws JrafDomainException if the DTO type is not supported
     * @return dto
     */
    public static MarketLanguageDTO bo2DtoLight(MarketLanguage pMarketLanguage) throws JrafDomainException {
        // instanciation du DTO
        MarketLanguageDTO marketLanguageDTO = new MarketLanguageDTO();
        bo2DtoLight(pMarketLanguage, marketLanguageDTO);
        // on retourne le dto
        return marketLanguageDTO;
    }


    /**
     * Transforme un business object en DTO.
     * Methode dite light.
     * @param marketLanguage bo
     * @param marketLanguageDTO dto
     */
    public static void bo2DtoLight(
        MarketLanguage marketLanguage,
        MarketLanguageDTO marketLanguageDTO) {


        // simple properties
        marketLanguageDTO.setMarket(marketLanguage.getMarket());
        marketLanguageDTO.setMarketLanguageId(marketLanguage.getMarketLanguageId());
        marketLanguageDTO.setLanguage(marketLanguage.getLanguage());
        marketLanguageDTO.setOptIn(marketLanguage.getOptIn());
        marketLanguageDTO.setDateOfConsent(marketLanguage.getDateOfConsent());
        marketLanguageDTO.setCreationDate(marketLanguage.getCreationDate());
        marketLanguageDTO.setCreationSignature(marketLanguage.getCreationSignature());
        marketLanguageDTO.setCreationSite(marketLanguage.getCreationSite());
        marketLanguageDTO.setModificationDate(marketLanguage.getModificationDate());
        marketLanguageDTO.setModificationSignature(marketLanguage.getModificationSignature());
        marketLanguageDTO.setModificationSite(marketLanguage.getModificationSite());


    }
    
    /*PROTECTED REGION ID(_pbbOsJw_EeKZIdxk1nMK9w u m - Tr) ENABLED START*/
    public static MarketLanguage dto2Bo (MarketLanguageDTO marketLanguageDTO) throws JrafDomainException {
    	MarketLanguage marketLanguage = dto2BoLight(marketLanguageDTO);
    	if(marketLanguageDTO.getMedia1()!=null)marketLanguage.setCommunicationMedia1(marketLanguageDTO.getMedia1());
    	if(marketLanguageDTO.getMedia2()!=null)marketLanguage.setCommunicationMedia2(marketLanguageDTO.getMedia2());
    	if(marketLanguageDTO.getMedia3()!=null)marketLanguage.setCommunicationMedia3(marketLanguageDTO.getMedia3());
    	if(marketLanguageDTO.getMedia4()!=null)marketLanguage.setCommunicationMedia4(marketLanguageDTO.getMedia4());
    	if(marketLanguageDTO.getMedia5()!=null)marketLanguage.setCommunicationMedia5(marketLanguageDTO.getMedia5());
    	return marketLanguage;
    }
/*
    // REPIND-555 : 
    public static MarketLanguage dto2Bo (ProspectMarketLanguageDTO prospectMarketLanguageDTO) throws JrafDomainException {
    	MarketLanguage marketLanguage = dto2BoLight(prospectMarketLanguageDTO);
    	if(prospectMarketLanguageDTO.getCommunicationMedia1()!=null)marketLanguage.setCommunicationMedia1(prospectMarketLanguageDTO.getCommunicationMedia1());
    	if(prospectMarketLanguageDTO.getCommunicationMedia2()!=null)marketLanguage.setCommunicationMedia2(prospectMarketLanguageDTO.getCommunicationMedia2());
    	if(prospectMarketLanguageDTO.getCommunicationMedia3()!=null)marketLanguage.setCommunicationMedia3(prospectMarketLanguageDTO.getCommunicationMedia3());
    	if(prospectMarketLanguageDTO.getCommunicationMedia4()!=null)marketLanguage.setCommunicationMedia4(prospectMarketLanguageDTO.getCommunicationMedia4());
    	if(prospectMarketLanguageDTO.getCommunicationMedia5()!=null)marketLanguage.setCommunicationMedia5(prospectMarketLanguageDTO.getCommunicationMedia5());
    	return marketLanguage;
    }
*/    
    public static MarketLanguageDTO bo2Dto(MarketLanguage pMarketLanguage) throws JrafDomainException{
    	MarketLanguageDTO marketLanguageDto = bo2DtoLight(pMarketLanguage);
    	if(pMarketLanguage.getCommunicationMedia1()!=null)marketLanguageDto.setMedia1(pMarketLanguage.getCommunicationMedia1());
    	if(pMarketLanguage.getCommunicationMedia2()!=null)marketLanguageDto.setMedia2(pMarketLanguage.getCommunicationMedia2());
    	if(pMarketLanguage.getCommunicationMedia3()!=null)marketLanguageDto.setMedia3(pMarketLanguage.getCommunicationMedia3());
    	if(pMarketLanguage.getCommunicationMedia4()!=null)marketLanguageDto.setMedia4(pMarketLanguage.getCommunicationMedia4());
    	if(pMarketLanguage.getCommunicationMedia5()!=null)marketLanguageDto.setMedia5(pMarketLanguage.getCommunicationMedia5());
    	return marketLanguageDto;
    }
    
    /*PROTECTED REGION END*/
}

