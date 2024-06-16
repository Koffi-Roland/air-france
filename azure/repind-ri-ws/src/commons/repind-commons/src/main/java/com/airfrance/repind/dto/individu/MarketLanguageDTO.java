package com.airfrance.repind.dto.individu;

/*PROTECTED REGION ID(_pbbOsJw_EeKZIdxk1nMK9w i) ENABLED START*/

// add not generated imports here
import org.apache.commons.lang.builder.ToStringBuilder;

/*PROTECTED REGION END*/

/**
 * <p>Title : MarketLanguageDTO</p>
 * 
 * <p>Copyright : Copyright (c) 2009</p>
 * <p>Company : AIRFRANCE</p>
 */
public class MarketLanguageDTO  {
        
    /**
     * marketLanguageId
     */
    private Integer marketLanguageId;
        
        
    /**
     * market
     */
    private String market;
        
        
    /**
     * language
     */
    private String language;
        
        
    /**
     * optIn
     */
    private String optIn;
        
        
    /**
     * media1
     */
    private String media1;
        
        
    /**
     * media2
     */
    private String media2;
        
        
    /**
     * media3
     */
    private String media3;
        
        
    /**
     * media4
     */
    private String media4;
        
        
    /**
     * media5
     */
    private String media5;
        
        
    /**
     * dateOfConsent
     */
    private java.util.Date dateOfConsent;
        
        
    /**
     * creationDate
     */
    private java.util.Date creationDate;
        
        
    /**
     * creationSignature
     */
    private String creationSignature;
        
        
    /**
     * creationSite
     */
    private String creationSite;
        
        
    /**
     * modificationDate
     */
    private java.util.Date modificationDate;
        
        
    /**
     * modificationSignature
     */
    private String modificationSignature;
        
        
    /**
     * modificationSite
     */
    private String modificationSite;
        

    /*PROTECTED REGION ID(_pbbOsJw_EeKZIdxk1nMK9w u var) ENABLED START*/
    // add your custom variables here if necessary
    /*PROTECTED REGION END*/

    
    
    /** 
     * default constructor 
     */
    public MarketLanguageDTO() {
    
    }
    
    
    /** 
     * full constructor
     * @param pMarket market
     * @param pMarketLanguageId marketLanguageId
     * @param pLanguage language
     * @param pOptIn optIn
     * @param pMedia1 media1
     * @param pMedia2 media2
     * @param pMedia3 media3
     * @param pMedia4 media4
     * @param pMedia5 media5
     * @param pDateOfConsent dateOfConsent
     * @param pCreationDate creationDate
     * @param pCreationSignature creationSignature
     * @param pCreationSite creationSite
     * @param pModificationDate modificationDate
     * @param pModificationSignature modificationSignature
     * @param pModificationSite modificationSite
     */
    public MarketLanguageDTO(String pMarket, Integer pMarketLanguageId, String pLanguage, String pOptIn, String pMedia1, String pMedia2, String pMedia3, String pMedia4, String pMedia5, java.util.Date pDateOfConsent, java.util.Date pCreationDate, String pCreationSignature, String pCreationSite, java.util.Date pModificationDate, String pModificationSignature, String pModificationSite) {
        this.market = pMarket;
        this.marketLanguageId = pMarketLanguageId;
        this.language = pLanguage;
        this.optIn = pOptIn;
        this.media1 = pMedia1;
        this.media2 = pMedia2;
        this.media3 = pMedia3;
        this.media4 = pMedia4;
        this.media5 = pMedia5;
        this.dateOfConsent = pDateOfConsent;
        this.creationDate = pCreationDate;
        this.creationSignature = pCreationSignature;
        this.creationSite = pCreationSite;
        this.modificationDate = pModificationDate;
        this.modificationSignature = pModificationSignature;
        this.modificationSite = pModificationSite;
    }

    /**
     *
     * @return creationDate
     */
    public java.util.Date getCreationDate() {
        return this.creationDate;
    }

    /**
     *
     * @param pCreationDate creationDate value
     */
    public void setCreationDate(java.util.Date pCreationDate) {
        this.creationDate = pCreationDate;
    }

    /**
     *
     * @return creationSignature
     */
    public String getCreationSignature() {
        return this.creationSignature;
    }

    /**
     *
     * @param pCreationSignature creationSignature value
     */
    public void setCreationSignature(String pCreationSignature) {
        this.creationSignature = pCreationSignature;
    }

    /**
     *
     * @return creationSite
     */
    public String getCreationSite() {
        return this.creationSite;
    }

    /**
     *
     * @param pCreationSite creationSite value
     */
    public void setCreationSite(String pCreationSite) {
        this.creationSite = pCreationSite;
    }

    /**
     *
     * @return dateOfConsent
     */
    public java.util.Date getDateOfConsent() {
        return this.dateOfConsent;
    }

    /**
     *
     * @param pDateOfConsent dateOfConsent value
     */
    public void setDateOfConsent(java.util.Date pDateOfConsent) {
        this.dateOfConsent = pDateOfConsent;
    }

    /**
     *
     * @return language
     */
    public String getLanguage() {
        return this.language;
    }

    /**
     *
     * @param pLanguage language value
     */
    public void setLanguage(String pLanguage) {
        this.language = pLanguage;
    }

    /**
     *
     * @return market
     */
    public String getMarket() {
        return this.market;
    }

    /**
     *
     * @param pMarket market value
     */
    public void setMarket(String pMarket) {
        this.market = pMarket;
    }

    /**
     *
     * @return marketLanguageId
     */
    public Integer getMarketLanguageId() {
        return this.marketLanguageId;
    }

    /**
     *
     * @param pMarketLanguageId marketLanguageId value
     */
    public void setMarketLanguageId(Integer pMarketLanguageId) {
        this.marketLanguageId = pMarketLanguageId;
    }

    /**
     *
     * @return media1
     */
    public String getMedia1() {
        return this.media1;
    }

    /**
     *
     * @param pMedia1 media1 value
     */
    public void setMedia1(String pMedia1) {
        this.media1 = pMedia1;
    }

    /**
     *
     * @return media2
     */
    public String getMedia2() {
        return this.media2;
    }

    /**
     *
     * @param pMedia2 media2 value
     */
    public void setMedia2(String pMedia2) {
        this.media2 = pMedia2;
    }

    /**
     *
     * @return media3
     */
    public String getMedia3() {
        return this.media3;
    }

    /**
     *
     * @param pMedia3 media3 value
     */
    public void setMedia3(String pMedia3) {
        this.media3 = pMedia3;
    }

    /**
     *
     * @return media4
     */
    public String getMedia4() {
        return this.media4;
    }

    /**
     *
     * @param pMedia4 media4 value
     */
    public void setMedia4(String pMedia4) {
        this.media4 = pMedia4;
    }

    /**
     *
     * @return media5
     */
    public String getMedia5() {
        return this.media5;
    }

    /**
     *
     * @param pMedia5 media5 value
     */
    public void setMedia5(String pMedia5) {
        this.media5 = pMedia5;
    }

    /**
     *
     * @return modificationDate
     */
    public java.util.Date getModificationDate() {
        return this.modificationDate;
    }

    /**
     *
     * @param pModificationDate modificationDate value
     */
    public void setModificationDate(java.util.Date pModificationDate) {
        this.modificationDate = pModificationDate;
    }

    /**
     *
     * @return modificationSignature
     */
    public String getModificationSignature() {
        return this.modificationSignature;
    }

    /**
     *
     * @param pModificationSignature modificationSignature value
     */
    public void setModificationSignature(String pModificationSignature) {
        this.modificationSignature = pModificationSignature;
    }

    /**
     *
     * @return modificationSite
     */
    public String getModificationSite() {
        return this.modificationSite;
    }

    /**
     *
     * @param pModificationSite modificationSite value
     */
    public void setModificationSite(String pModificationSite) {
        this.modificationSite = pModificationSite;
    }

    /**
     *
     * @return optIn
     */
    public String getOptIn() {
        return this.optIn;
    }

    /**
     *
     * @param pOptIn optIn value
     */
    public void setOptIn(String pOptIn) {
        this.optIn = pOptIn;
    }

    /**
     *
     * @return object as string
     */
    public String toString() {
        String result = null;
        /*PROTECTED REGION ID(toString_pbbOsJw_EeKZIdxk1nMK9w) ENABLED START*/
        result = toStringImpl();
        /*PROTECTED REGION END*/
        return result;
    }

    /**
     *
     * @return object as string
     */
    public String toStringImpl() {
        return new ToStringBuilder(this)
            .append("market", getMarket())
            .append("marketLanguageId", getMarketLanguageId())
            .append("language", getLanguage())
            .append("optIn", getOptIn())
            .append("media1", getMedia1())
            .append("media2", getMedia2())
            .append("media3", getMedia3())
            .append("media4", getMedia4())
            .append("media5", getMedia5())
            .append("dateOfConsent", getDateOfConsent())
            .append("creationDate", getCreationDate())
            .append("creationSignature", getCreationSignature())
            .append("creationSite", getCreationSite())
            .append("modificationDate", getModificationDate())
            .append("modificationSignature", getModificationSignature())
            .append("modificationSite", getModificationSite())
            .toString();
    }

    /*PROTECTED REGION ID(_pbbOsJw_EeKZIdxk1nMK9w u m) ENABLED START*/
    // add your custom methods here if necessary
    /*PROTECTED REGION END*/

}
